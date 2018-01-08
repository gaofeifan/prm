package com.pj.conf.utils;

import com.pj.Aspect.BasicProperties;
import com.pj.partner.pojo.PartnerDetails;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


/***
 * @ClassName: ExcelUtils
 * @Description: (这里用一句话描述这个类的作用)
 * @author SenevBoy
 * @date 2018/1/3 16:42   
 **/
public class ExcelUtils {

    public   void customerOutExcel(HttpServletRequest request, HttpServletResponse response,     List<PartnerDetails> listData     ) throws ParseException {
        // --->创建了一个excel文件
        String excelname ="合作伙伴.xls";
        // --->创建了一个excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet(excelname); // --->创建了一个工作簿
        sheet.setDefaultRowHeight((short) 300); // ---->有得时候你想设置统一单元格的高度，就用这个方法
        // 样式1
        HSSFCellStyle style = wb.createCellStyle(); // 样式对象
        HSSFCellStyle style1 = wb.createCellStyle(); // 样式对象
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直
        // 设置标题字体格式
        org.apache.poi.ss.usermodel.Font font = wb.createFont();
        // 设置字体样式
        font.setFontHeightInPoints((short) 12);
        // --->设置字体大小
        font.setFontName("Courier New"); // ---》设置字体，是什么类型例如：宋体
        font.setItalic(false); // --->设置是否是加粗
        style.setFont(font);
        style1.setFont(font); // --->将字体格式加入到style1中
        int line = 5;
        // 设置行宽
        // 第一个参数代表列id(从0开始),第2个参数代表宽度值
        sheet.setColumnWidth(0, 13000);
        sheet.setVerticallyCenter(true);
        HSSFRow line0 = sheet.createRow(0);
        /* 导出执行 */
        // 数组 参数列中文名
        for (int i = 0 ; i < BasicProperties.excelData.length ; i++){
            HSSFCell createCell4 = line0.createCell(i);
            createCell4.setCellValue( BasicProperties.excelData[i]);
            createCell4.setCellStyle(style);
        }

      List<PartnerDetails> fatherDatas = new ArrayList<PartnerDetails>();

        boolean flage = true;

// 循环所有子集 遍历 添加 然后删除重复
        for (PartnerDetails pard : listData){
            if(null == pard.getPId()) {
                pard.setHierarchy(0);
                fatherDatas.add(pard);
            }
        }

        /* wile 遍历 父级 id 与子集集合*/
        int num  = 1;
        while(flage){
            List<PartnerDetails> chilkdList = new ArrayList<PartnerDetails>();
                /*循环所有父集合 查询子集合 排序*/
                for (PartnerDetails father : fatherDatas) {
                    for (PartnerDetails pard : listData){
                        if(null!=pard.getPId()){
                        if(pard.getPId().equals(father.getId())){
                            pard.setHierarchy(num);
                            chilkdList.add(pard);
                        }
                        }
                    }
                }
            num++;

            HashSet<PartnerDetails> child2 = new HashSet<PartnerDetails>();
                //  遍历子集合 循环插入到主集合中
                for (PartnerDetails child : chilkdList) {
                    /*找到其父级所在地址*/
                    for (PartnerDetails pard : listData){
                        if(   child.getPId().equals(pard.getId())){
                            child2.add(pard);
                        }
                    }
                }
            for (PartnerDetails child : chilkdList) {
                for (PartnerDetails chid2 : child2) {
                    if(child.getPId().equals(chid2.getId())){
                        int asd = listData.indexOf(chid2);
                        listData.remove(listData.indexOf(child));
                        listData.add(listData.indexOf(chid2)+1,child);
                    }
                }
            }
                fatherDatas = chilkdList;
                if(chilkdList.size()==0){
                    flage = false;
                }
        }

     //   Collections.reverse(listData);
        int i = 0;

        /*正文数据添加*/
    String name= "";
        for (int j = 0 ; j <listData.size(); j++){
            HSSFRow row = sheet.createRow(j + 1);
            name ="";
                  /*合作伙伴中文名*/
                for (int s = 0 ; s <listData.get(j).getHierarchy(); s++){
                    name +="---";
                }
            HSSFCell cell = row.createCell(i);
            cell.setCellValue(name+listData.get(j).getChineseName());
            style1.setAlignment(XSSFCellStyle.ALIGN_LEFT);
            cell.setCellStyle(style1);
                  /*合作伙伴中文名*/
           //    row.createCell(i+1).setCellValue();

                  /* 黑名单 */
                row.createCell(i+2).setCellValue(listData.get(j).getIsBlacklist());

                  /* 停用 */
                row.createCell(i+3).setCellValue(listData.get(j).getIsDisable());


                  /* 附属功能 */
                  if(null  != listData.get(j).getPartnerCategorys() ){
                      String[] partnerCategorys = listData.get(j).getPartnerCategorys();
                      for (int k = 0 ; k < partnerCategorys.length ; k++){

                          if(partnerCategorys[k].equals("外部客户")){
                           row.createCell(4).setCellValue(partnerCategorys[k]);

                          } else if(partnerCategorys[k].equals("互为代理")){
                               row.createCell(5).setCellValue(partnerCategorys[k]);

                          } else if(partnerCategorys[k].equals("海外代理")){
                              row.createCell(6).setCellValue(partnerCategorys[k]);

                          } else if(partnerCategorys[k].equals("干线承运")){
                             row.createCell(7).setCellValue(partnerCategorys[k]);

                          } else if(partnerCategorys[k].equals("不可控")){
                              row.createCell(8).setCellValue(partnerCategorys[k]);

                          } else if(partnerCategorys[k].equals("延伸服务")){
                             row.createCell(9).setCellValue(partnerCategorys[k]);

                          } else if(partnerCategorys[k].equals("收发货人")){
                          row.createCell(10).setCellValue(partnerCategorys[k]);

                          } else if(partnerCategorys[k].equals("结算对象")){
                          row.createCell(11).setCellValue(partnerCategorys[k]);
                          }
                      }
                  }
        }
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            excelname = com.pj.conf.utils.StringUtils.downloadEncoding(request, excelname);
            response.setHeader("Content-disposition", "attachment;filename=" + excelname);
            response.setContentType("multipart/form-data");
            wb.write(outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void customerOutExcelTest(HttpServletRequest request, HttpServletResponse response, List<PartnerDetails> list) {

    }

}