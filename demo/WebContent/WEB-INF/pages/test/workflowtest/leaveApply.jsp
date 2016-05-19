<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>Insert title here</title>
<jsp:include page="../../common/common.jsp"></jsp:include>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/src/test/workflowtest/leaveApply.js"></script>
<script type="text/javascript">
var lastName = '${sessionScope.SYS_USER_INFO.lastName}';
var personCode = '${sessionScope.SYS_USER_INFO.personCode}';
</script>
</head>
<body>

</body>
</html>