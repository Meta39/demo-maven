package com.fu.springbootdemo.global;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

/**
 * token内存放的信息
 */
public class TokenInfo implements Serializable {

    private static final long serialVersionUID = 6558796578827818462L;

    private Integer userId;//当前登录用户ID

    private Set<Integer> roleIds;//角色ID集合

    private Set<String> authorizes;//权限集合

    private TokenInfo(){}

    private static final TokenInfo tokenInfo = new TokenInfo();

    public static TokenInfo getTokenInfo(){
        return tokenInfo;
    }

    //---------------------------------get/set------------------------------------------

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Set<Integer> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(Set<Integer> roleIds) {
        this.roleIds = roleIds;
    }

    public Set<String> getAuthorizes() {
        return authorizes;
    }

    public void setAuthorizes(Set<String> authorizes) {
        this.authorizes = authorizes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TokenInfo tokenInfo = (TokenInfo) o;
        return Objects.equals(userId, tokenInfo.userId) && Objects.equals(roleIds, tokenInfo.roleIds) && Objects.equals(authorizes, tokenInfo.authorizes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, roleIds, authorizes);
    }

    @Override
    public String toString() {
        return "TokenInfo{" +
                "userId=" + userId +
                ", roleIds=" + roleIds +
                ", authorizes=" + authorizes +
                '}';
    }

}
