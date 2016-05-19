<%
	String contextPath = request.getContextPath();
%>
<script type="text/javascript">
	var basePath = '<%=request.getContextPath()%>';
</script>
<link rel="stylesheet" type="text/css" href="<%=contextPath %>/js/extjs/build/classic/theme-triton/resources/theme-triton-all-debug.css">
<%-- <link rel="stylesheet" type="text/css" href="<%=contextPath %>/js/extjs/build/packages/ux/classic/triton/resources/ux-all-debug.css"> --%>
<link rel="stylesheet" type="text/css" href="<%=contextPath %>/style/common.css"/>
<script type="text/javascript" src="<%=contextPath %>/js/extjs/ext-bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath %>/js/extjs/build/classic/locale/locale-zh_CN-debug.js"></script>
<script type="text/javascript" src="<%=contextPath %>/js/jquery/jquery-1.11.1.js"></script>
<script type="text/javascript" src="<%=contextPath %>/js/common/commonFunc.js"></script>
<script type="text/javascript" src="<%=contextPath %>/js/common/commonUtils.js"></script>
<script type="text/javascript">
//初始化一个单例。  任何一个基本标签的快速提示框将开始工作。
Ext.tip.QuickTipManager.init();

// 允许为单例的配置属性设置值
Ext.apply(Ext.tip.QuickTipManager.getQuickTip(), {
    maxWidth: 200,
    minWidth: 100,
    showDelay: 500
});
var curTheme = Ext.util.Cookies.get("curTheme");
if(curTheme){
	Ext.util.CSS.swapStyleSheet("theme",curTheme);
}
</script>