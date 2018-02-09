package com.pj.aeserviceapi.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

/***
 * @ClassName: ResponseData
 * @Description: (返回实体)
 * @author SenevBoy
 * @date 2018/2/6 14:05   
 **/

@Table(name  ="partner_details")
public class ResponseData {
    @GeneratedValue(generator = "JDBC")
    @Id
    @ApiModelProperty(value = "id", required = false)
    private Integer id;


    /**
     * 代码
     */
    @Column
    @ApiModelProperty(value = "代码", required = false)
    private String code;


    /**
     * 中文名称
     */
    @Column
    @ApiModelProperty(value = "中文名称", required = false)
    private String chineseName;


    /**
     * 英文名称
     */
    @Column
    @ApiModelProperty(value = "英文名称", required = false)
    private String englishName;

    /**
     * 助记码
     */
    @Column
    @ApiModelProperty(value = "助记码", required = false)
    private String mnemonicCode;

    @Transient
    @ApiModelProperty(value = "总长度code", required = false)
    private String codes;




    public String getCodes() {
        return codes;
    }

    public void setCodes(String codes) {
        this.codes = codes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getChineseName() {
        return chineseName;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getMnemonicCode() {
        return mnemonicCode;
    }

    public void setMnemonicCode(String mnemonicCode) {
        this.mnemonicCode = mnemonicCode;
    }
}
