package com.demo.common.util.mail;

/**
 * 邮箱账号
 * 
 * @author leiqiang
 * 
 */
public class EmailAccount
{
    /**
     * 单例模式
     */
    private static EmailAccount instance = null;

    /**
     * 邮件服务器 mail.chinasoftinc.com
     */
    private String mailHost;

    /**
     * 用户名
     */
    private String name;

    /**
     * 密码
     */
    private String pwd;

    /**
     * 完整邮箱地址
     */
    private String emailAddr;

    /**
     * 构造函数私有化
     */
    private EmailAccount()
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
    public static EmailAccount getInstance()
    {
        if (null == instance)
        {
            instance = new EmailAccount();
        }
        return instance;
    }

    public String getEmailAddr()
    {
        return emailAddr;
    }

    public void setEmailAddr(String emailAddr)
    {
        this.emailAddr = emailAddr;
    }

    public String getMailHost()
    {
        return mailHost;
    }

    public void setMailHost(String mailHost)
    {
        this.mailHost = mailHost;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPwd()
    {
        return pwd;
    }

    public void setPwd(String pwd)
    {
        this.pwd = pwd;
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

        retValue = "EmailAccount ( " + super.toString() + TAB + "mailHost = "
                + this.mailHost + TAB + "name = " + this.name + TAB + "pwd = "
                + this.pwd + TAB + "emailAddr = " + this.emailAddr + TAB + " )";

        return retValue;
    }

}
