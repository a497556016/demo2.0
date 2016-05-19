package com.demo.common.util.mail;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.apache.log4j.Logger;

import com.demo.common.constant.ConfigKeys;
import com.demo.common.init.manager.ConfigManager;


/**
 * 邮件工具类
 * 
 * @author
 * 
 */
public class EmailTools
{
	private static final Logger LOGGER = Logger.getLogger(EmailTools.class);
	
	private static EmailInfo initEmailInfo(EmailAccount emailAccount){
		// 发件人信息初始化
        EmailInfo info = EmailInfo.getInstance();
        info.setSentDate(new Date());
        info.setSubject(ConfigManager
                .getString(ConfigKeys.SYS_EMAIL_SUBJECT));
        info.setFromAccount(emailAccount);
        
        return info;
	}
	
	private static EmailAccount initEmailAccount(){
		// 邮件账号初始化
        EmailAccount fromAccount = EmailAccount.getInstance();
        fromAccount.setEmailAddr(ConfigManager
                .getString(ConfigKeys.SYS_EMAIL_ADDRESS));
        fromAccount.setName(ConfigManager
                .getString(ConfigKeys.SYS_EMAIL_NAME));
        fromAccount.setMailHost(ConfigManager
                .getString(ConfigKeys.SYS_EMAIL_HOST));
        fromAccount.setPwd(ConfigManager
                .getString(ConfigKeys.SYS_EMAIL_PASSWORD));
        return fromAccount;
	}
	
	public static void sendEmail(String emailAddress,String templateName,Map<String, Object> model){
		EmailAccount emailAccount = initEmailAccount();
		EmailInfo emailInfo = initEmailInfo(emailAccount);
		emailInfo.setTtos(emailAddress);
		String content = MailTemplateResolve.getInstance().getResolvedText(templateName, model);
		emailInfo.setContent(content);
		try {
			sendEmail(emailInfo, emailAccount);
		} catch (UnsupportedEncodingException | MessagingException e) {
			LOGGER.error("邮件发送异常",e);
		}
	}
	
	/**
	 * 异步发送邮件
	 */
	public static void asynSendEmail(String emailAddress,String templateName,Map<String, Object> model){
		Thread thread = new Thread(new SendEmailRunnable(emailAddress, templateName, model));
		thread.start();
	}
	
    public static void sendEmail(EmailInfo info, EmailAccount fromAccount)
            throws MessagingException, UnsupportedEncodingException
    {
        // 开始发送邮件
        Session session = getMailSession(fromAccount);

        // 封装message
        MimeMessage message = new MimeMessage(session);

        // 发邮件的出发地（发件人的信箱）
        InternetAddress from = new InternetAddress(fromAccount.getEmailAddr());
        message.setFrom(from);

        // 收件人
        if (null != info.getTtos() && !"".equals(info.getTtos()))
        {
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(info.getTtos()));
        }

        // 抄送人
        if (null != info.getTccs() && !"".equals(info.getTccs()))
        {
            message.setRecipients(Message.RecipientType.CC,
                    InternetAddress.parse(info.getTccs()));
        }

        // 密送人
        if (null != info.getBcc() && !"".equals(info.getBcc()))
        {
            message.setRecipients(Message.RecipientType.BCC,
                    InternetAddress.parse(info.getBcc()));
        }

        // Subject邮件的主题
        message.setSubject(info.getSubject());

        // SentDate 发送时间
        message.setSentDate(info.getSentDate());

        try
        {
            // 把mm作为消息对象的内容
            message.setContent(getMultipart(info));
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        message.saveChanges();

        Transport transport = session.getTransport("smtp");

        // 发邮件人帐户密码,此外是我的帐户密码，使用时请修改。
        transport.connect(fromAccount.getMailHost(), fromAccount.getName(),
                fromAccount.getPwd());
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();

        // 邮件发送成功
        LOGGER.info(info.getTtos()+"邮件发送成功!");
    }

    /**
     * 邮件的具体内容，包含内容和附件等
     * 
     * @param info 邮件信息
     * @return 邮件的多部分内容
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     * @author leiqiang
     * @throws MessagingException
     * @throws UnsupportedEncodingException
     */
    private static Multipart getMultipart(EmailInfo info)
            throws MessagingException, UnsupportedEncodingException
    {
        // 新建一个MimeMultipart对象用来存放BodyPart对象(事实上可以存放多个)
        Multipart mm = new MimeMultipart();

        // 新建一个存放信件内容的BodyPart对象
        BodyPart mdp = new MimeBodyPart();

        // 给BodyPart对象设置内容和格式/编码方式tcontent为邮件内容
        mdp.setContent(info.getContent(), "text/html;charset=utf-8");

        // 添加
        mm.addBodyPart(mdp);

        // 添加附件
        if (null != info.getAffix() && !"".equals(info.getAffix()))
        {
            // 新建一个存放附件内容的BodyPart对象
            BodyPart attachmentPart = new MimeBodyPart();
            DataSource source = new FileDataSource(info.getAffix());
            // 添加附件的内容
            attachmentPart.setDataHandler(new DataHandler(source));

            // 设置附件的名称
            attachmentPart.setFileName(MimeUtility.encodeText(info
                    .getAffixName()));

            mm.addBodyPart(attachmentPart);
        }

        return mm;
    }

    /**
     * 创建session;
     * 
     * @param fromAccount 发件人账号
     * @return session
     * @author leiqiang
     */
    private static Session getMailSession(EmailAccount fromAccount)
    {
        Properties props = new Properties();

        // 这样才能通过验证
        props.setProperty("mail.smtp.auth", "true");

        // smtp协议
        props.setProperty("mail.transport.protocol", "smtp");

        // 发件人使用发邮件的电子信箱服务器
        props.setProperty("mail.host", fromAccount.getMailHost());

        Session s = Session.getInstance(props);

        s.setDebug(true);

        return s;
    }
}