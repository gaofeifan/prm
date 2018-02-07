package com.pj.partner.service.impl;

import com.pj.aeserviceapi.pojo.ResponseData;
import com.pj.auth.service.AuthUserService;
import com.pj.cache.PartnerDetailsCache;
import com.pj.conf.base.AbstractBaseServiceImpl;
import com.pj.conf.base.BaseMapper;
import com.pj.conf.utils.ThreadEmail;
import com.pj.partner.mapper.PartnerDetailsMapper;
import com.pj.partner.mapper.PartnerDetailsShifFileMapper;
import com.pj.partner.pojo.PartnerAddress;
import com.pj.partner.pojo.PartnerDetails;
import com.pj.partner.pojo.PartnerDetailsShifFile;
import com.pj.partner.pojo.PartnerLinkman;
import com.pj.partner.service.PartnerAddressService;
import com.pj.partner.service.PartnerDetailsService;
import com.pj.partner.service.PartnerDetailsUtilService;
import com.pj.partner.service.PartnerLinkmanService;
import com.pj.user.service.EmailService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.*;

import static com.pj.partner.pojo.PartnerDetails.ageComparator;

/**
 * Created by Administrator on 2017/11/8.
 */

@Service
@Transactional
public class PartnerDetailsServiceImpl extends AbstractBaseServiceImpl<PartnerDetails,Integer> implements PartnerDetailsService {
    @Autowired(required = false)
    private PartnerDetailsMapper partnerDetailsMapper;
    @Autowired
    private PartnerDetailsUtilService partnerDetailsUtilService;
    @Autowired(required = false)
    private PartnerDetailsService partnerDetailsService;

    @Autowired(required = false)
    private EmailService emailService;

    @Autowired
    private PartnerAddressService partnerAddressService;
    @Autowired
    private PartnerLinkmanService partnerLinkmanService;
    @Autowired(required = false)
    private PartnerDetailsShifFileMapper partnerDetailsShifFileMapper;
    @Autowired
    private AuthUserService authUserService;
    @Override
    public BaseMapper<PartnerDetails> getMapper() {
        return partnerDetailsMapper;
    }

    public static final String FIELD_ID = "id";
    public static final String PHONE = "phone";
    public static final String FIELD_DETAILS = "detailsId";

    @Override
    public List<PartnerDetails> selectPartnerDetailsList() {
//        List<PartnerDetails> list = windowsSort(this.partnerDetailsMapper.selectPartnerDetailsList());
        List<PartnerDetails> list = this.partnerDetailsMapper.selectPartnerDetailsList();
        return list;

    }

    @Override
    public List<PartnerDetails> selectListByQuery(String name, Integer offPartner, Integer blacklistPartner ,String partnerCategory) {
      /*  Example example = new Example(PartnerDetails.class);
        Example.Criteria criteria = example.createCriteria();
        if(offPartner != null){
            criteria.andCondition("is_disable =" , offPartner);
        }
        if(blacklistPartner != null){
            criteria.andCondition("is_blacklist =" , blacklistPartner);
        }
        if(StringUtils.isNotBlank(name)){
            criteria.andLike("name",name);
        }
        criteria.andCondition("is_delete =",0);*/
        PartnerDetails pd = new PartnerDetails();
        pd.setIsDisable(offPartner);
        pd.setIsBlacklist(blacklistPartner);
        if(StringUtils.isNotBlank(partnerCategory)){
//            String[] strings = partnerCategory.split(",");
            pd.setPartnerCategory(partnerCategory);
        }
        if(StringUtils.isNoneBlank(name)){
        	pd.setDirName(name);
        }
            List<PartnerDetails> pds = this.partnerDetailsMapper.selectListByQuery(pd);
            
            if(StringUtils.isBlank(name) && StringUtils.isBlank(partnerCategory) && offPartner == 0 && blacklistPartner == 0){
            	return selectPartnerDetailsList();
            }
            //        List<PartnerDetails> pds = this.partnerDetailsMapper.selectByExample(example);
            HashSet<PartnerDetails> data = new HashSet<PartnerDetails>();
            Set<PartnerDetails> details = selectSon(pds, new HashSet<PartnerDetails>(), null);
            for (PartnerDetails p:details) {
                List<PartnerDetails> parentList = this.partnerDetailsMapper.getParentList(p.getId());
                data.addAll(parentList);
            }
            List<PartnerDetails> list=  new ArrayList<PartnerDetails>();
            for (PartnerDetails set:data){
                list.add(set);
            }

  //   return windowsSort(data);
     Collections.sort(list,ageComparator);

        return   list ;
    }
        /**
         *  获取查询最末级的数据
         * @param pds
         * @param endData
         * @param id
         * @return
         */
        public Set<PartnerDetails> selectSon(List<PartnerDetails> pds , Set<PartnerDetails> endData ,Integer id){
            for (PartnerDetails p: pds) {
                if(p.getId().equals(id)){
                    continue;
                }
                id = p.getId();
                List<PartnerDetails> parentList = this.partnerDetailsMapper.getChildList(p.getId());
                parentList.remove(p);
                if(parentList.size() <= 1){
                    endData.add(p);
                }
                selectSon(parentList,endData,id);
            }
            return endData;
        }

    @Override
    public PartnerDetails selectByPrimaryKey(Integer key) {
        PartnerDetails pd = super.selectByPrimaryKey(key);
        PartnerAddress address = new PartnerAddress();
        address.setDetailsId(key);
        List<PartnerAddress> addresss = this.partnerAddressService.select(address);
        pd.setAddressList(addresss);
        PartnerLinkman linkman = new PartnerLinkman();
        linkman.setDetailsId(key);
        List<PartnerLinkman> linkmens = this.partnerLinkmanService.select(linkman);
        pd.setLinkmansList(linkmens);
        Object[] codeList = this.getParentCodeList(pd.getId());
        StringBuilder sb = new StringBuilder();
        for (Object obj : codeList){
            sb.append(obj);
        }
        pd.setCodes(sb.toString());
        return pd;
    }


    @Override
    public void updateByPrimaryKey(PartnerDetails record, HttpServletRequest request, String email){
        record.setIsDelete(0);
        List<PartnerAddress> address = record.getAddressList();
        List<PartnerLinkman> linkmans = record.getLinkmansList();
        /**
         *  将原数据与新数据添加到作用与中
         */
        request.getSession().setAttribute("old_partnerLinkman",this.partnerLinkmanService.selectPartnerLinkmansByDetailsId(record.getId()));
        request.getSession().setAttribute("new_partnerLinkman",linkmans);
        request.getSession().setAttribute("old_partnerAddress",this.partnerAddressService.selectPartnerAddressesByDetailsId(record.getId()));
        request.getSession().setAttribute("new_partnerAddress",address);
        request.getSession().setAttribute("old_partnerDetails",this.partnerDetailsMapper.selectByPrimaryKey(record.getId()));
        request.getSession().setAttribute("new_partnerDetails",record);
//        this.partnerLinkmanService.deletePartnerLinkmanByDetailsId(record.getId(),email);
//        this.partnerAddressService.deletePartnerAddressByDetails(record.getId(),email);
        /*更新联系人*/
        List<PartnerLinkman> partnerLinkmen = new ArrayList<PartnerLinkman>();
        if(linkmans != null){
         partnerLinkmen = this.updatePartnerLinkman(linkmans, record, email, request);
        }
              /*更新联系地址*/
        if(address != null){
            this.updatePartnerAddres(address,record.getId(),email);
        }

        this.updateStatus(record,request,email); /*   更新停用 黑名单状态*/

        super.updateByPrimaryKey(record);   /*    根据主键更新*/

        deleteParentMnemonicCode(record.getId());/* 删除所有父集的助记码*/

        if( partnerLinkmen.size()!=0){
                // 校验手机号 发送邮件
                ThreadEmail thread =  new  ThreadEmail( partnerDetailsService,   authUserService,   emailService,partnerLinkmanService);
                thread.checkPhoneSendEmail(request,linkmans, record ) ;

        }

    }

    /**
     *  更新停用 黑名单状态
     * @param record
     * @param email
     */
    public void updateStatus(PartnerDetails record, HttpServletRequest request, String email) {
        PartnerDetails details = this.partnerDetailsMapper.selectByPrimaryKey(record.getId());
        List<PartnerDetails> list = this.partnerDetailsMapper.getChildList(details.getId());
        request.getSession().setAttribute("old_state_list",list);
        for (PartnerDetails pd : list){
            if(!pd.getId().equals(details.getId())) {
                if ((!pd.getIsBlacklist().equals(record.getIsBlacklist()))
                        || (!pd.getIsDisable().equals(record.getIsDisable()))
                        || ((null!=pd.getDisableRemark()) && (!pd.getDisableRemark().equals(record.getDisableRemark())))) {
                    pd.setIsBlacklist(record.getIsBlacklist());
                    pd.setIsDisable(record.getIsDisable());
                    pd.setDisableRemark(record.getDisableRemark());
                    this.partnerDetailsUtilService.updateByPrimaryKey(pd,email);
                }
            }
        }
    }
    
    /**
     *  更新联系地址
     * @param address
     * @param id
     * @param email
     */
    private void updatePartnerAddres(List<PartnerAddress> address, Integer id, String email) {
        List<PartnerAddress> deleteAddress = new ArrayList<>();
        List<PartnerAddress> addresses = this.partnerAddressService.selectPartnerAddressesByDetailsId(id);
        for (PartnerAddress pa: addresses ) {
            //  判断新增的集合中是否包含原数据
            boolean b = contains(address, pa.getId(), FIELD_ID);
            //  存在进行更新原数据
            if(b) {
                //  根据id查询新增集合中的数据
                PartnerAddress partnerAddress = get(address, pa.getId().toString(), FIELD_ID);
                if (!pa.equals(partnerAddress)) {
                    if (partnerAddress != null) {
                        this.partnerAddressService.updateByPrimaryKey(partnerAddress, email);
                    }
                }
                //  删除该条更新的数据
                address.remove(partnerAddress);

            } else {
                    // 不存在则数据已删除保存删除的集合中
                    deleteAddress.add(pa);
                }
        }
        //  新增集合中数据都为新增的
        for (PartnerAddress pa: address ) {
            pa.setId(null);
            pa.setDetailsId(id);
        }
        if(address.size()!=0) {
            this.partnerAddressService.insertList(address, email);
        }
        // 将新增集合中不存在的元数据进行删除
        for(PartnerAddress pa : deleteAddress){
            this.partnerAddressService.delete(pa,email);
        }
    }

    /**
     *  更新联系人
     * @param linkmans
     * @param partnerDetails
     * @param request
     */
    private List<PartnerLinkman> updatePartnerLinkman(List<PartnerLinkman> linkmans, PartnerDetails partnerDetails, String email, HttpServletRequest request) {
        List<PartnerLinkman> deleteLinkman = new ArrayList<>();
        // 存储 更新手机好的信息
        List<PartnerLinkman> updateLinkman2 = new ArrayList<>();
        //  查询原数据
        List<PartnerLinkman> oPartnerLinkmen = this.partnerLinkmanService.selectPartnerLinkmansByDetailsId(partnerDetails.getId());
        for(PartnerLinkman pl : oPartnerLinkmen){
            //  判断新增的集合中是否包含原数据
            boolean b = contains(linkmans, pl.getId(), FIELD_ID);
            //  存在进行更新原数据
            if(b){
                //  根据id查询新增集合中的数据 // 并判断是否发生变动
                PartnerLinkman partnerLinkman = get(linkmans, pl.getId().toString(), FIELD_ID);
                boolean equals = pl.equals(partnerLinkman);
                if(partnerLinkman != null  && !equals) {
                        this.partnerLinkmanService.updateByPrimaryKey(partnerLinkman, email);
                        // 判断 手机号是否发生了改变
                    if(!pl.getPhone().equals(partnerLinkman.getPhone())){
                        updateLinkman2.add(partnerLinkman);
                    }
                }
                    //  删除该条更新的数据
                linkmans.remove(partnerLinkman);
            }else{
                // 不存在则数据已删除保存删除的集合中
                deleteLinkman.add(pl);
            }
        }
        //  新增集合中数据都为新增的
        for(PartnerLinkman pl : linkmans){
            pl.setDetailsId(partnerDetails.getId());
        }
        if(linkmans.size()!=0) {
            this.partnerLinkmanService.insertList(linkmans, email);
        }
        // 将新增集合中不存在的元数据进行删除
        for(PartnerLinkman pl : deleteLinkman){
            this.partnerLinkmanService.delete(pl,email);
        }
        // 执行 邮件方法 通知手机号重复
        linkmans.addAll(updateLinkman2);

        return linkmans;
    }

    private <T> boolean  contains( List<T> objs, Integer detailsId,String filedName){
        for (T obj : objs){
            Class<?> clazz = obj.getClass();
            try {
                Field field = clazz.getDeclaredField(filedName);
                field.setAccessible(true);
                Object o = field.get(obj);
                if(o != null){
                    String str = o.toString();
                    if(str.equals(detailsId.toString())){
                        return true;
                    }
                }

            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    private <T> T  get( List<T> objs, String detailsId,String filedName){
        for (T obj : objs){
            Class<?> clazz = obj.getClass();
            try {
                Field field = clazz.getDeclaredField(filedName);
                field.setAccessible(true);
                Object o = field.get(obj);
                if(o != null){
                    String str = o.toString();
                    if(str.equals(detailsId )){
                        return obj;
                    }
                }

            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    @Override
    public void insertSelective(PartnerDetails partnerDetails, HttpServletRequest request, String email) {
        partnerDetails.setCreateDate(new Date());
            super.insertSelective(partnerDetails);
        List<PartnerLinkman> linkmans = partnerDetails.getLinkmansList();
        List<PartnerAddress> address = partnerDetails.getAddressList();
        request.getSession().setAttribute("new_partnerAddress",address);
        request.getSession().setAttribute("new_partnerLinkman",linkmans);
        request.getSession().setAttribute("new_partnerDetails",partnerDetails);
        if(linkmans != null){
            for(PartnerLinkman pl : linkmans){
                pl.setDetailsId(partnerDetails.getId());
            }
            this.partnerLinkmanService.insertList(linkmans);
        }
        if(address != null){
            for (PartnerAddress pa: address ) {
                pa.setId(null);
                pa.setDetailsId(partnerDetails.getId());
            }
            this.partnerAddressService.insertList(address);
        }
        deleteParentMnemonicCode(partnerDetails.getId());
    }

    @Override
    public boolean verifyValueRepeat(Integer id, String fieldName, String fieldValue,Integer pId) {
        try {
        boolean b = verifyfeildIsExist(fieldName);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("the field is not exist");
        }
        fieldName = this.toUnderlineJSONString(fieldName);
        Example example = new Example(PartnerDetails.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andCondition(fieldName+"=",fieldValue).andCondition("is_delete = 0");
        if(id != null){
            criteria.andCondition("id != ",id);
        }  
        if(fieldName.equals("code")){
        	if(pId == null){
        		criteria.andIsNull("pId");
        	}else{
        		criteria.andCondition("p_id =",pId);
        	}
        }
        List<PartnerDetails> pds =super.selectByExample(example);
        if(pds.size() != 0){
            return false;
        }
        return true;

    }

   
    private boolean verifyfeildIsExist(String fieldName) throws NoSuchFieldException {
            Field field =  PartnerDetails.class.getDeclaredField(fieldName);
            return true;
    }

    @Override
    public void deletePartnerDetailsById(Integer id, String email) {
        //  判断是否有子集文件 如果没有可以删除
        PartnerDetails partnerDetails = super.selectByPrimaryKey(id);
        partnerDetails.setIsDelete(1);
        super.updateByPrimaryKey(partnerDetails);
    }

    @Override
    public List<PartnerDetailsShifFile> selectShiftFile(Integer[] ids) {
        List<Integer> list = Arrays.asList(ids);
        Example example = new Example(PartnerDetailsShifFile.class);
        example.createCriteria().andIn("id",list);
        return this.partnerDetailsShifFileMapper.selectByExample(example);
    }

    @Override
    public boolean shiftPartnerDetailsFileByIds( Integer id,String email) {
        //  查询转移的文件
        Object o = PartnerDetailsCache.getValueByKey("details");
        List<PartnerDetailsShifFile> shifFileList = (List<PartnerDetailsShifFile>) o;
        List<PartnerDetailsShifFile> deleteFileList = new ArrayList<>();
        //  获取转移文件所有的子集
        for (PartnerDetailsShifFile fds:shifFileList) {
            List<PartnerDetailsShifFile> childList = this.partnerDetailsShifFileMapper.getChildList(fds.getId());
           //   如果子集中存在转移直的目录将其删除
            for (PartnerDetailsShifFile childFds:childList) {
                if(childFds.getId() == id){
                    deleteFileList.add(childFds);
                }
            }
        }
        shifFileList.removeAll(deleteFileList);
        //  更新转移目录的父集
        for (PartnerDetailsShifFile childFds:shifFileList) {

            //校验(根据新的父id和要转移的code查找,若存在则不保存)
            PartnerDetailsShifFile model = new PartnerDetailsShifFile();
            model.setPId(id);
            model.setCode(childFds.getCode());
            PartnerDetailsShifFile exsitDetail = partnerDetailsShifFileMapper.selectOne(model);
            if(exsitDetail != null){
                return false;
            }else{
                childFds.setPId(id);
                this.partnerDetailsShifFileMapper.updateByPrimaryKey(childFds);
            }

        }
        return true;
    }

    @Override
    public boolean isDeletePartnerDetails(Integer id) {
        //  判断是否有子集文件 如果没有可以删除
        PartnerDetails pd = new PartnerDetails();
        pd.setPId(id);
        pd.setIsDelete(0);
        List<PartnerDetails> list = super.select(pd);
        if(list.size() > 0){
            return false;
        }
        return true;
    }


    private List<PartnerDetails> windowsSort( List<PartnerDetails> set){
        /**
         * 	排序  数字 > 字母 > 汉字
         */
        TreeSet<PartnerDetails> tree = new TreeSet<>(new Comparator<PartnerDetails>() {
            @Override
            public int compare(PartnerDetails a1, PartnerDetails a2) {
                return (a1.getCodes()==null?"":a1.getCodes()).compareTo(a2.getCodes()==null?"":a2.getCodes());
            }
        });
        List<String> pds = new ArrayList<String>();
        for (  PartnerDetails se :  set){
            pds.add(se.getCode());
        }
        HashSet<PartnerDetails> pds2 = new HashSet<PartnerDetails>();
        List<PartnerDetails> pds3 = new ArrayList<PartnerDetails>();
        Collections.sort(pds);
        for (  String se2 :  pds){
            for (  PartnerDetails se :  set){
                if(se.getCode().equals(se2)){
                    pds2.add(se);
                }
            }
        }
        pds3.addAll(pds2);
        return pds3;
        /**
         * 	排序特殊字符
         */
      /*  TreeSet<PartnerDetails> endTree = new TreeSet<>(new Comparator<PartnerDetails>() {
            @Override
            public int compare(PartnerDetails a1, PartnerDetails a2) {
                int to = a1.getCodes().compareTo(a2.getCodes());
                return to;
            }
        });
        List<PartnerDetails> endList = new ArrayList<>();
        List<PartnerDetails> rmList = new ArrayList<>();
*/
        /**
         * 	遍历集合将包含特殊字符添加到特殊字符集合中 同事将该数据添加到需要删除的集合中
         */
      /*  for (PartnerDetails PartnerDetails : set) {
            String name = PartnerDetails.getCodes();
            if (RegExpUtils.verify(name.charAt(0) + "")) {
                endTree.add(PartnerDetails);
                rmList.add(PartnerDetails);
            }
        }
        *//**
         * 	删除包含特殊字符数据
         *//*
        set.removeAll(rmList);
        tree.addAll(set);
        Iterator<PartnerDetails> iterator2 = endTree.iterator();
        while (iterator2.hasNext()) {
            endList.add(iterator2.next());
        }
        Iterator<PartnerDetails> iterator = tree.iterator();
        while (iterator.hasNext()) {
            endList.add(iterator.next());
        }
        return endList;*/
    }

    @Override
    public Object[] getParentCodeList(Integer id) {
        List<PartnerDetails> parentList = this.getParentList(id);
        Object[] array = parentList.stream().map(pl -> pl.getCode()).toArray();
        return array;
    }

    public List<PartnerDetails> getParentList(Integer id) {
        return this.partnerDetailsMapper.getParentList(id);
    }

    private String toUnderlineJSONString(String param){
        if (param==null||"".equals(param.trim())){
            return "";
        }
        int len=param.length();
        StringBuilder sb=new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c=param.charAt(i);
            if (Character.isUpperCase(c)){
                sb.append(PartnerDetailsService.UNDERLINE);
                sb.append(Character.toLowerCase(c));
            }else{
                sb.append(c);
            }
        }
        return sb.toString();
    }


    @Override
    public boolean isEditCode(Integer id) {
        List<PartnerDetailsShifFile> list = this.partnerDetailsShifFileMapper.getChildList(id);
        if(list.size() > 1){
            return false;
        }
        return true;
    }

    /**
     *  删除所有父集的助记码
     * @param id
     */
    private void deleteParentMnemonicCode(Integer id){

        List<PartnerDetails> parentList = this.getParentList(id);
        parentList.remove(parentList.size()-1);
        for (PartnerDetails pd : parentList){
            PartnerDetails details = this.partnerDetailsMapper.selectByPrimaryKey(pd.getId());
            details.setMnemonicCode(null);
              this.partnerDetailsMapper.updateByPrimaryKey(details);
        }

    }

    @Override
    public boolean selectIsChild(Integer id) {
        List<PartnerDetailsShifFile> childList = this.partnerDetailsShifFileMapper.getChildList(id);
        if(childList.size() > 1){
            return true;
        }
        return false;
    }

    /*** PRM 支持 AE 项目 对外接口
     * createData 2018年2月6日11:11:25
     * @param
     * @param responseData
     * @return
     */
    @Override
    public List<ResponseData> aeAirlineFindPartnerDateilsList(ResponseData responseData) {

        // 根据 条件查询所有
        List<PartnerDetails> pds = this.partnerDetailsMapper.aeAirlineFindPartnerDateilsList(responseData.getChineseName(),responseData.getEnglishName(),responseData.getMnemonicCode());
          return getDateIls(pds);
    }

    @Override
    public List<ResponseData> aePartnerFindPartnerDateilsList(ResponseData responseData) {
        // 根据 条件查询所有
        List<PartnerDetails> pds = this.partnerDetailsMapper.aePartnerFindPartnerDateilsList( responseData);
        return getDateIls(pds);
    }

    public List<ResponseData> getDateIls(   List<PartnerDetails> pds){
              /* 获取所有父级 */
        Set<PartnerDetails> details = selectSon(pds, new HashSet<PartnerDetails>(), null);
        HashSet<PartnerDetails> data = new HashSet<PartnerDetails>();
        for (PartnerDetails p:details) {
            List<PartnerDetails> parentList = this.partnerDetailsMapper.getParentList(p.getId());
            data.addAll(parentList);
        }
        List<ResponseData> list= new ArrayList<ResponseData>();
        for (PartnerDetails data1:data)
        {
            for (PartnerDetails datass:details){
                if(data1.getId().equals(datass.getId())){
                    ResponseData responseData = new ResponseData();
                    responseData.setId(datass.getId());
                    responseData.setCode(datass.getCode());
                    responseData.setChineseName(datass.getChineseName());
                    responseData.setEnglishName(  datass.getEnglishName());
                    responseData.setCodes(  datass.getCodes());
                    responseData.setMnemonicCode(  datass.getMnemonicCode());
                    list.add(responseData);
                }
            }
        }
        return list;
    }

}