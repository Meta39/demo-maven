package com.fu.springbootdemo.global;

import com.fu.springbootdemo.entity.Authorize;
import com.fu.springbootdemo.entity.Role;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * token前端需要的信息
 */
public class TokenFrontInfo implements Serializable {
    private static final long serialVersionUID = 6558796578827818452L;
    private String token; //token
    private Integer tokenTimeout; //token过期时间
    private String username; //用户名
    private List<Role> roles; //当前登录用户所拥有的角色集合
    private List<Authorize> authorizes; //当前登录用户所拥有的权限信息

    private TokenFrontInfo(){}

    //饿汉式单例模式
    private static final TokenFrontInfo tokenFrontInfo = new TokenFrontInfo();

    public static TokenFrontInfo getInstance(){
        return tokenFrontInfo;
    }

    //-----------------------------get/set-------------------------------

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getTokenTimeout() {
        return tokenTimeout;
    }

    public void setTokenTimeout(Integer tokenTimeout) {
        this.tokenTimeout = tokenTimeout;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<Authorize> getAuthorizes() {
        return authorizes;
    }

    public void setAuthorizes(List<Authorize> authorizes) {
        this.authorizes = authorizes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TokenFrontInfo that = (TokenFrontInfo) o;
        return Objects.equals(token, that.token) && Objects.equals(tokenTimeout, that.tokenTimeout) && Objects.equals(username, that.username) && Objects.equals(roles, that.roles) && Objects.equals(authorizes, that.authorizes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(token, tokenTimeout, username, roles, authorizes);
    }

    @Override
    public String toString() {
        return "TokenFrontInfo{" +
                "token='" + token + '\'' +
                ", tokenTimeout=" + tokenTimeout +
                ", username='" + username + '\'' +
                ", roles=" + roles +
                ", authorizes=" + authorizes +
                '}';
    }
}
