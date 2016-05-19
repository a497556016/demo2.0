package com.demo.common.constant;

public class ConfigKeys
{
    /**
     * 页面分页显示记录数，默认值10，取值范围：正常取值范围的数字
     */
    public final static String SYS_PAGE_SIZE = "sys_page_size";

    /**
     * 分页标签每一篇显示的页数，默认值10，取值范围：正常取值范围的数字
     */
    public final static String SYS_PAGE_NUMBER_SIZE = "sys_page_number_size";

    /**
     * 完整邮箱地址
     */
    public final static String SYS_EMAIL_ADDRESS = "sys_email_address";

    /**
     * 用户名
     */
    public final static String SYS_EMAIL_NAME = "sys_email_name";

    /**
     * 密码
     */
    public final static String SYS_EMAIL_PASSWORD = "sys_email_password";

    /**
     * 邮件服务器 mail.chinasoftinc.com
     */
    public final static String SYS_EMAIL_HOST = "sys_email_host";

    /**
     * 邮件主题
     */
    public final static String SYS_EMAIL_SUBJECT = "sys_email_subject";

    /**
     * 25天邮件内容
     */
    public final static String SYS_EMAIL_CONTENT_QU = "sys_email_content";
    
    /**
     * 中软主页
     */
    public final static String SYS_EMAIL_CONTENT_URL = "sys_email_content_url";
    
    /**
     * 30天邮件内容
     */
    public final static String SYS_EMAIL_CONTENT_UP = "sys_email_content_up";

    /**
     * 定时任务：部门运营经理标示ID，数据库初始化角色数据时务必保持一致，默认值0
     */
    public final static String SYS_TASK_ROLE_OPERATIONS_MANAGER = "sys_task_role_operations_manager";

    /**
     * 定时任务：部门交付经理标示ID，数据库初始化角色数据时务必保持一致，默认值0
     */
    public final static String SYS_TASK_ROLE_DD_MANAGER = "sys_task_role_dd_manager";
    
    /**
     * 定时任务：查询指定最后更新时间（25天）以及状态类别（待跟踪）的商机列表，天数配置，默认值25天
     */
    public final static String SYS_TASK_QUERY_OPLINE_DAY = "sys_task_query_opline_day";

    /**
     * 定时任务：更新指定最后更新时间（30天）以及状态类别（待跟踪）的商机 为流标状态（状态2），默认值30天
     */
    public final static String SYS_TASK_UPDATE_OPLINE_DAY = "sys_task_update_opline_day";
}
