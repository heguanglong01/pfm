package com.hailong.base.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.hailong.base.entity.BackInfo;

@Repository
public interface PfmFeedBackDao {
	
    public int addBathchPfmFeedback(@Param("instList") List<BackInfo> user);
}
