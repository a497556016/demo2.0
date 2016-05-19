package com.demo.common.service;

import java.util.List;

import com.demo.common.bean.ComboBean;
import com.demo.common.model.BasicInfo;

public interface CommonService {

	List<ComboBean<BasicInfo>> quertBasicInfo(String type);

}
