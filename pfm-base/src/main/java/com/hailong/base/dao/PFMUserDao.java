package com.hailong.base.dao;

import org.springframework.stereotype.Repository;

import com.hailong.base.entity.PfmUsers;

@Repository
public interface PFMUserDao {
	
    public int addPfmUserDao(PfmUsers user);
	public int updatePfmUser(PfmUsers pfm);
}
