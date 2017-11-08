package com.pj.user.pojo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by SenevBoy on 2017/11/8.
 * 用户信用等级表
 */
@Entity
public class UserLevel {

    @Id //@id注意选择这个javax.persistence
    @GeneratedValue
    private  Integer  id;

    private  String   level;    // 用户信用等级

    private  Integer   Protocol_type; // 协议类型

    private  Integer   effectiveness;  // 是否有效



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Integer getProtocol_type() {
        return Protocol_type;
    }

    public void setProtocol_type(Integer protocol_type) {
        Protocol_type = protocol_type;
    }

    public Integer getEffectiveness() {
        return effectiveness;
    }

    public void setEffectiveness(Integer effectiveness) {
        this.effectiveness = effectiveness;
    }
}
