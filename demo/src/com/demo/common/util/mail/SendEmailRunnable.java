package com.demo.common.util.mail;

import java.util.Map;

import org.apache.log4j.Logger;

public class SendEmailRunnable implements Runnable {
	private static final Logger LOGGER = Logger.getLogger(SendEmailRunnable.class);
	
	private String emailAddress;
	private String nickName;
	private String subjectName;
	private String templateName;
	private Map model;
	
	public SendEmailRunnable(String emailAddress, String nickName,
			String subjectName, String templateName, Map model) {
		super();
		this.emailAddress = emailAddress;
		this.nickName = nickName;
		this.subjectName = subjectName;
		this.templateName = templateName;
		this.model = model;
	}
	
	public SendEmailRunnable(String emailAddress, String templateName, Map model) {
		super();
		this.emailAddress = emailAddress;
		this.templateName = templateName;
		this.model = model;
	}

	@Override
	public void run() {
		try {
			EmailTools.sendEmail(emailAddress, templateName, model);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
		}
	}
	

}
