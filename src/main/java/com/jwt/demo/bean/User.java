package com.jwt.demo.bean;

import com.alibaba.fastjson.annotation.JSONField;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
/**
 * @author jhye4
 * @date 2020/11/23 16:48
 * 实体类USER
 */
public class User {

    private Long id;
    private String  name;
    private String  email;
    private String  passwd;

    private Integer enable;

    private String  avatar;

    @JSONField(deserialize=false,serialize=false)
    private MultipartFile avatarFile;


    private String token;

    private String enableUrl;



    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswd() {
        return passwd;
    }
    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }


    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public Integer getEnable() {
        return enable;
    }
    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    public MultipartFile getAvatarFile() {
        return avatarFile;
    }
    public String getEnableUrl() {
        return enableUrl;
    }
    public void setEnableUrl(String enableUrl) {
        this.enableUrl = enableUrl;
    }
    public void setAvatarFile(MultipartFile avatarFile) {
        this.avatarFile = avatarFile;
    }

    public String getAvatar() {
        return avatar;
    }
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }




}