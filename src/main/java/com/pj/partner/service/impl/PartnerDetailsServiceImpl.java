package com.pj.partner.service.impl;

import com.pj.auth.service.AuthUserService;
import com.pj.cache.PartnerDetailsCache;
import com.pj.conf.base.AbstractBaseServiceImpl;
import com.pj.conf.base.BaseMapper;
import com.pj.conf.utils.RegExpUtils;
import com.pj.partner.mapper.PartnerDetailsMapper;
import com.pj.partner.mapper.PartnerDetailsShifFileMapper;
import com.pj.partner.pojo.PartnerAddress;
import com.pj.partner.pojo.PartnerDetails;
import com.pj.partner.pojo.PartnerDetailsShifFile;
import com.pj.partner.pojo.PartnerLinkman;
import com.pj.partner.service.PartnerAddressService;
import com.pj.partner.service.PartnerDetailsService;
import com.pj.partner.service.PartnerLinkmanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by Administrator on 2017/11/8.
 */

@Service
@Transactional
public class PartnerDetailsServiceImpl extends AbstractBaseServiceImpl<PartnerDetails,Integer> implements PartnerDetailsService {
    @Autowired
    private PartnerDetailsMapper partnerDetailsMapper;
    @Autowired
    private PartnerAddressService partnerAddressService;
    @Autowired
    private PartnerLinkmanService partnerLinkmanService;
    @Autowired
    private PartnerDetailsShifFileMapper partnerDetailsShifFileMapper;
    @Autowired
    private AuthUserService authUserService;
    @Override
    public BaseMapper<PartnerDetails> getMapper() {
        return partnerDetailsMapper;
    }

    @Override
    public List<PartnerDetails> selectPartnerDetailsList() {
        PartnerDetails pd = new PartnerDetails();
        pd.setIsDelete(0);
        List<PartnerDetails> list = windowsSort(this.partnerDetailsMapper.select(pd));
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
//        if(StringUtils.isNotBlank(partnerCategory)){
//            String[] strings = partnerCategory.split(",");
            pd.setPartnerCategory(partnerCategory);
//        }
        pd.setDirName(name);
        List<PartnerDetails> pds = this.partnerDetailsMapper.selectListByQuery(pd);
//        List<PartnerDetails> pds = this.partnerDetailsMapper.selectByExample(example);
        return pds;
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
        this.partnerLinkmanService.deletePartnerLinkmanByDetailsId(record.getId(),email);
        this.partnerAddressService.deletePartnerAddressByDetails(record.getId(),email);
        if(linkmans != null){
            for(PartnerLinkman pl : linkmans){
                pl.setDetailsId(record.getId());
            }
            this.partnerLinkmanService.insertList(linkmans,email);
        }
        if(address != null){
            for (PartnerAddress pa: address ) {
                pa.setId(null);
                pa.setDetailsId(record.getId());
            }
            this.partnerAddressService.insertList(address,email);
        }
       super.updateByPrimaryKey(record);
        deleteParentMnemonicCode(record.getId());
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
    public boolean verifyValueRepeat(Integer id, String fieldName, String fieldValue) {
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
    public void shiftPartnerDetailsFileByIds( Integer id,String email) {
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
            childFds.setPId(id);
            this.partnerDetailsShifFileMapper.updateByPrimaryKey(childFds);
        }
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
                String s1 = ((String) a1.getChineseName()).toLowerCase();
                String s2 = ((String) a2.getChineseName()).toLowerCase();
                return s1.compareTo(s2);
            }
        });

        /**
         * 	排序特殊字符
         */
        TreeSet<PartnerDetails> endTree = new TreeSet<>(new Comparator<PartnerDetails>() {
            @Override
            public int compare(PartnerDetails a1, PartnerDetails a2) {
                int to = a1.getChineseName().compareTo(a2.getChineseName());
                return to;
            }
        });
        List<PartnerDetails> endList = new ArrayList<>();
        List<PartnerDetails> rmList = new ArrayList<>();

        /**
         * 	遍历集合将包含特殊字符添加到特殊字符集合中 同事将该数据添加到需要删除的集合中
         */
        for (PartnerDetails PartnerDetails : set) {
            String name = PartnerDetails.getChineseAbbreviation();
            if (RegExpUtils.verify(name.charAt(0) + "")) {
                endTree.add(PartnerDetails);
                rmList.add(PartnerDetails);
            }
        }
        /**
         * 	删除包含特殊字符数据
         */
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
        return endList;
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
}