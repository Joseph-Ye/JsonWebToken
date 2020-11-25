package com.jwt.demo.service;

import com.jwt.demo.bean.User;
import com.jwt.demo.exception.UserException;
import com.jwt.demo.utils.JwtHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author jhye4
 * @date 2020/11/23 17:26
 */
@Service
public class UserService {

    @Autowired
    private RedisTemplate redisTemplate;

    public User auth(String email, String password){
        if(null == email || null == password){
            throw new UserException(UserException.Type.USER_AUTH_FAIL,"用户认证失败");
        }
        User user = new User();
        user.setEmail(email);
        user.setEnable(1);
        // user.setPasswd(HashUtils.encryPassword(password));
        user.setPasswd(password);

        // new User() manually
        List<User> userList = getUserByQuery(user);
        if(!userList.isEmpty()){
            User retUser = userList.get(0);
            onLogin(retUser);
            return retUser;
        }
        throw new UserException(UserException.Type.USER_AUTH_FAIL,"用户认证失败");
    }

    /**
     * Define only one user;Not select from database
     * means this is a fake function
     * @param user unused
     * @return
     */
    private List<User> getUserByQuery(User user) {
        List<User> userList = new ArrayList<>();

        User customUser = new User();
        customUser.setEmail("1039860161@qq.com");
        customUser.setName("jhye4");
        customUser.setPasswd("123456");

        userList.add(customUser);

        return userList;
    }

    private void onLogin(User user) {
        Map<String, String> claims = new HashMap<>();
        claims.put("email", user.getEmail());
        claims.put("name", user.getName());
        claims.put("ts", Instant.now().getEpochSecond()+"");
        String token =  JwtHelper.genToken(claims);
        System.out.println("用户生成的token为：" + token);
        renewToken(token,user.getEmail());
        user.setToken(token);
    }

    private String renewToken(String token, String email){
        redisTemplate.opsForValue().set(email, token);
        redisTemplate.expire(email, 30, TimeUnit.MINUTES);
        return token;
    }

    public User getLoginedUserByToken(String token){
        Map<String, String> map;
        map = JwtHelper.verifyToken(token);
        String email = map.get("email");
        Long expired = redisTemplate.getExpire(email);
        if(expired > 0L){
            renewToken(token, email);
        }
        renewToken(token, email);
        User user = getUserByEmail(email);
        user.setToken(token);
        return user;
    }

    /**
     * not a real function;
     * @param email unused
     * @return
     */
    private User getUserByEmail(String email) {
        User customUser = new User();
        customUser.setEmail("1039860161@qq.com");
        customUser.setName("jhye4");
        customUser.setPasswd("123456");
        return customUser;
    }

    public void invalidate(String token){
        Map<String, String> map = JwtHelper.verifyToken(token);
        redisTemplate.delete(map.get("email"));
    }
}
