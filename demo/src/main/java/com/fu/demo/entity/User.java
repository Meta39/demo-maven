package com.fu.demo.entity;

import java.util.Date;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    private Long id;//用户ID（唯一）    
    private String name;//用户名（唯一）
    private String password;//UUID密码
    private String salt;//UUID盐（前端校验ID或账号成功时返回给前端）    
    private String publicKey;//公钥
    private String privateKey;//私钥    
    private String phone;//手机号    
    private String eMail;//邮箱地址    
    private Boolean disabled;//禁用：0否，1是    
    private Boolean deleted;//删除：0否，1是    
    private Long creator;//创建人    
    private String remark;//备注    
    private Date createTime;//创建时间    
    private Date lastLoginTime;//最后登录时间
}

