package com.demo.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/web")
public class WebController {
	
	/**
	 * 主页
	 * @return
	 */
	@RequestMapping("/main")
	public String main(){
		return "main";
	}
	
	/**
	 * 权限资源配置
	 * @return
	 */
	@RequestMapping("/resConfig")
	public String resConfig(){
		return "systemMana/resConfig";
	}
	/**
	 * 用户角色配置
	 * @return
	 */
	@RequestMapping("/userRoleConfig")
	public String userRoleConfig(){
		return "systemMana/userRoleConfig";
	}
	/**
	 * 角色资源配置
	 * @return
	 */
	@RequestMapping("/roleResConfig")
	public String roleResConfig(){
		return "systemMana/roleResConfig";
	}
	

	@RequestMapping("/sys_todoWork")
	public String todoWork(HttpServletRequest request,HttpServletResponse response){
		return "workflow/syswork/todoWork";
	}
	
	@RequestMapping("/procList")
	public String procMana(HttpServletRequest request,HttpServletResponse response){
		return "workflow/procList";
	}
	
	@RequestMapping("/sys_doneWork")
	public String doneWork(HttpServletRequest request,HttpServletResponse response){
		return "workflow/syswork/doneWork";
	}
	
	@RequestMapping("/my_todoWork")
	public String myworktodoWork(HttpServletRequest request,HttpServletResponse response){
		return "workflow/mywork/todoWork";
	}
	
	@RequestMapping("/my_doneWork")
	public String myworkdoneWork(HttpServletRequest request,HttpServletResponse response){
		return "workflow/mywork/doneWork";
	}

	@RequestMapping("/leaveApply")
	public String leaveApply(HttpServletRequest request,HttpServletResponse response){
		return "test/workflowtest/leaveApply";
	}
	
	@RequestMapping("/registForm")
	public String registForm(HttpServletRequest request,HttpServletResponse response){
		return "test/extTest/registForm";
	}
	
	@RequestMapping("/userListGrid")
	public String userListGrid(HttpServletRequest request,HttpServletResponse response){
		return "test/extTest/userListGrid";
	}
	
	@RequestMapping("/m_toMIndex")
	public String toMIndex(){
		return "mIndex";
	}
	
	@RequestMapping("/m_tabs")
	public String tabs(){
		return "templates/tabs";
	}
	
	@RequestMapping("/m_home")
	public String home(){
		return "templates/home";
	}
	
	@RequestMapping("/m_vacation")
	public String vacation(){
		return "templates/mainItems/vacation";
	}
	
	@RequestMapping("/m_writeOn")
	public String writeOn(){
		return "templates/mainItems/writeOn";
	}
	
	@RequestMapping("/m_writeOn_content")
	public String writeOnContent(){
		return "templates/mainItems/writeOnContent";
	}
	
	@RequestMapping("/m_writeOn_listData")
	public String writeOnListData(){
		return "templates/mainItems/writeOnListData";
	}
	
	@RequestMapping("/m_myWork")
	public String myWork(){
		return "templates/mainItems/myWork";
	}
	
	@RequestMapping("/m_about")
	public String about(){
		return "templates/about";
	}
	
	@RequestMapping("/m_config")
	public String config(){
		return "templates/config";
	}
}
