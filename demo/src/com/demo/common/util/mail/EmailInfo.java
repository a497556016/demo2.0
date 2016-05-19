package com.demo.common.util.mail;

import java.util.Date;

/**
 * 邮件相关信息
 * 
 * @author
 * 
 */
public class EmailInfo
{
    /**
     * 单例模式
     */
    private static EmailInfo instance = null;

    /**
     * 发件人
     */
    private EmailAccount fromAccount;

    /**
     * 收件人邮箱列表,多个用","分隔
     */
    private String ttos;

    /**
     * 抄送人邮箱列表,多个用","分隔
     */
    private String tccs;

    /**
     * 密送人邮箱列表,多个用","分隔
     */
    private String bcc;

    /**
     * 附件的正文内容
     */
    private String content;

    /**
     * 附件的存放路径
     */
    private String affix;

    /**
     * 发送时附件的名称
     */
    private String affixName;

    /**
     * 邮件主题
     */
    private String subject;

    /**
     * 发送时间
     */
    private Date sentDate;

    /**
     * 构造函数私有化
     */
    private EmailInfo()
    {

    }

    /**
     * 单例获取实例对象
     * 
     * @Title: getInstance
     * @Description: N/A
     * @return PropertiesManager
     * @author dhf 1815 at 2013-11-24
     */
    public static EmailInfo getInstance()
    {
        if (null == instance)
        {
            instance = new EmailInfo();
        }
        return instance;
    }

    public EmailAccount getFromAccount()
    {
        return fromAccount;
    }

    public void setFromAccount(EmailAccount fromAccount)
    {
        this.fromAccount = fromAccount;
    }

    public String getTtos()
    {
        return ttos;
    }

    public void setTtos(String ttos)
    {
        this.ttos = ttos;
    }

    public String getTccs()
    {
        return tccs;
    }

    public void setTccs(String tccs)
    {
        this.tccs = tccs;
    }

    public String getBcc()
    {
        return bcc;
    }

    public void setBcc(String bcc)
    {
        this.bcc = bcc;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getAffix()
    {
        return affix;
    }

    public void setAffix(String affix)
    {
        this.affix = affix;
    }

    public String getAffixName()
    {
        return affixName;
    }

    public void setAffixName(String affixName)
    {
        this.affixName = affixName;
    }

    public String getSubject()
    {
        return subject;
    }

    public void setSubject(String subject)
    {
        this.subject = subject;
    }

    public Date getSentDate()
    {
        return sentDate;
    }

    public void setSentDate(Date sentDate)
    {
        this.sentDate = sentDate;
    }

    /**
     * Constructs a <code>String</code> with all attributes in name = value
     * format.
     * 
     * @return a <code>String</code> representation of this object.
     */
    public String toString()
    {
        final String TAB = "    ";

        String retValue = "";

        retValue = "EmailInfo ( " + super.toString() + TAB + "fromAccount = "
                + this.fromAccount + TAB + "ttos = " + this.ttos + TAB
                + "tccs = " + this.tccs + TAB + "bcc = " + this.bcc + TAB
                + "content = " + this.content + TAB + "affix = " + this.affix
                + TAB + "affixName = " + this.affixName + TAB + "subject = "
                + this.subject + TAB + "sentDate = " + this.sentDate + TAB
                + " )";

        return retValue;
    }

}