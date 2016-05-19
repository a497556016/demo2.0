package com.demo.common.util.mail;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.mail.MessagingException;

/**
 * 邮件发送测试类
 * 
 * @author Administrator
 * 
 */
public class SendTest
{
    /**
     * <一句话功能简述>; <功能详细描述>;
     * 
     * @param args [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     * @author lWX231520
     */
    public static void main(String[] args)
    {
        EmailAccount fromAccount = EmailAccount.getInstance();
        fromAccount.setEmailAddr("sse_manager@chinasoftinc.com");
        fromAccount.setName("sse_manager");
        fromAccount.setMailHost("smtp.chinasoftinc.com");
        fromAccount.setPwd("sse#1234");

        EmailInfo info = EmailInfo.getInstance();
        info.setTtos("heshaowei@chinasoftinc.com,fuyoucheng@chinasoftinc.com");
        info.setTccs("fuyin@chinasoftinc.com");
        info.setBcc("heshaowei@chinasoftinc.com");
        info.setContent("邮件功能测试");
        info.setSentDate(new Date());
        info.setSubject("邮件功能测试");
        info.setFromAccount(fromAccount);

        try
        {
            EmailTools.sendEmail(info, fromAccount);
        }
        catch (UnsupportedEncodingException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (MessagingException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
