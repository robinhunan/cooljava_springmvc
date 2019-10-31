package com.service.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mapper.user.UserMapper;
import com.model.user.User;
import com.service.base.CrudService;

@Service
@Transactional
public class UserService extends CrudService<UserMapper, User>{
	
}