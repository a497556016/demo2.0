package com.demo.test.workflowtest.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.test.workflowtest.bean.LeaveApplyInfo;
import com.demo.test.workflowtest.mapper.LeaveApplyMapper;
import com.demo.test.workflowtest.service.LeaveApplyService;

@Service
public class LeaveApplyServiceImpl implements LeaveApplyService {
	private static final Logger LOGGER = Logger.getLogger(LeaveApplyServiceImpl.class);
	
	@Autowired
	private LeaveApplyMapper leaveApplyMapper;

	@Override
	public int save(LeaveApplyInfo leaveApplyInfo) throws Exception {
		int id = leaveApplyMapper.save(leaveApplyInfo);
		return id;
	}

}
