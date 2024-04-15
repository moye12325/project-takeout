package com.moye.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moye.constant.MessageConstant;
import com.moye.dto.UserLoginDTO;
import com.moye.entity.User;
import com.moye.exception.LoginFailedException;
import com.moye.mapper.UserMapper;
import com.moye.properties.WeChatProperties;
import com.moye.service.UserService;
import com.moye.utils.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    public static final String WX_LOGIN_URL = "https://api.weixin.qq.com/sns/jscode2session";

    @Autowired
    private WeChatProperties weChatProperties;

    @Autowired
    private UserMapper userMapper;

    /**
     * 微信登录
     *
     * @param userLoginDTO
     * @return
     */
    @Override
    public User wxLogin(UserLoginDTO userLoginDTO) {

//        //调用微信接口服务，调用微信用户的openid
//        Map<String, String> map = new HashMap<>();
//        map.put("grant_type", "authorization_code");
//        map.put("appid", weChatProperties.getAppid());
//        map.put("secret", weChatProperties.getSecret());
//        map.put("js_code", userLoginDTO.getCode());
//        String json = HttpClientUtil.doGet(WX_LOGIN_URL, map);
//
//        //判断openid是否为空，抛出异常。
//        JSONObject jsonObject = JSON.parseObject(json);
//        String openid = jsonObject.getString("openid");

        String openid = getOpenid(userLoginDTO.getCode());

        if (openid == null || openid.equals("")) {
            throw new LoginFailedException(MessageConstant.LOGIN_FAILED);
        }

        //openid是否为新用户
        User user = userMapper.getByOpenid(openid);

        //没查到即为新用户，自动注册
        if (user == null) {
            //自动注册
            user = User.builder()
                    .openid(openid)
                    .createTime(LocalDateTime.now())
                    .build();
            userMapper.insert(user);
        }

        //返回用户对象
        return user;
    }

    private String getOpenid(String code) {
        //调用微信接口服务，调用微信用户的openid
        Map<String, String> map = new HashMap<>();
        map.put("grant_type", "authorization_code");
        map.put("appid", weChatProperties.getAppid());
        map.put("secret", weChatProperties.getSecret());
        map.put("js_code", code);
        String json = HttpClientUtil.doGet(WX_LOGIN_URL, map);

        //判断openid是否为空，抛出异常。
        JSONObject jsonObject = JSON.parseObject(json);
        String openid = jsonObject.getString("openid");
        return openid;
    }
}
