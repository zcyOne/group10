package com.software.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.software.entity.User;
import com.software.service.UserService;
import com.software.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author 眉眼如初
* @description 针对表【user】的数据库操作Service实现
* @createDate 2024-05-08 16:18:06
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




