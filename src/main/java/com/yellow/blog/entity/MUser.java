package com.yellow.blog.entity;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.io.Serializable;

/**
 * (MUser)实体类
 *
 * @author makejava
 * @since 2020-09-09 17:34:28
 */
public class MUser implements Serializable {
    private static final long serialVersionUID = 461964683808960095L;

    private Long id;

    @NotBlank(message = "昵称不能为空")
    private String username;

    private String avatar;
    @NotBlank(message = "邮箱不能为空")
    @Email(message ="邮箱格式不正确")
    private String email;

    private String password;

    private Integer status;

    private Date created;

    private Date lastLogin;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

}
