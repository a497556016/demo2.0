
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<jsp:include page="../../common/common.jsp"></jsp:include>
<script src="<%=request.getContextPath() %>/js/src/workflow/todoWork.js"></script>
<script src="<%=request.getContextPath() %>/js/src/workflow/syswork/todoWork.js"></script>
<script>
var userRole = '${user_login_userrole}';

var vrmProcess_check_url = '${vrmProcess_check_url}';

</script>
</head>
<body>

</body>
</html>