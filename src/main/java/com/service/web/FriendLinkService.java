package com.service.web;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mapper.web.FriendLinkMapper;
import com.model.web.FriendLink;
import com.service.base.CrudService;

@Service
@Transactional
public class FriendLinkService extends CrudService<FriendLinkMapper, FriendLink> {
	

}