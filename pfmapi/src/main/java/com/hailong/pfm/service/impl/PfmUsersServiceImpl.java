package com.hailong.pfm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hailong.base.dao.PFMUserDao;
import com.hailong.base.entity.PfmUsers;
import com.hailong.pfm.service.PfmUsersService;

@Service
public class PfmUsersServiceImpl implements PfmUsersService {
	
	@Autowired
	private  PFMUserDao pfmUserDao;
	
	public int addPfmUserDao(PfmUsers user){
		return this.pfmUserDao.addPfmUserDao(user);
	}

	@Override
	public int updatePfmUserDao(PfmUsers pfm) {
		// TODO Auto-generated method stub
		return pfmUserDao.updatePfmUser(pfm);
	}
}
