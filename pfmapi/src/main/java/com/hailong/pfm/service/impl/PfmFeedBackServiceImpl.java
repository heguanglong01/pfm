package com.hailong.pfm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hailong.base.dao.PfmFeedBackDao;
import com.hailong.base.entity.BackInfo;
import com.hailong.pfm.service.PfmFeedBackService;

@Service
public class PfmFeedBackServiceImpl implements PfmFeedBackService {
	@Autowired
	private PfmFeedBackDao pfmFeedBackDao;
    
	public int addBatchPfmFeedBack(List<BackInfo> info){
		return this.pfmFeedBackDao.addBathchPfmFeedback(info);
	}
}
