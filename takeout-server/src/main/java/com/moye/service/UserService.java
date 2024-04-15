package com.moye.service;

import com.moye.dto.UserLoginDTO;
import com.moye.entity.User;

public interface UserService {

    User wxLogin(UserLoginDTO userLoginDTO);
}
