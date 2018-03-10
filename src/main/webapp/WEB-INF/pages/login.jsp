<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<%@ include file="/WEB-INF/pages/common/common.jsp" %>

<body>
<form id="postForm" method="post" action="${ctx}/login">
	<div>
		用户名：<input type="text" name="userName" />
	</div>
	<div>
		密码：<input type="password" name="password" />
	</div>
	<div>
		<input type="submit" value="login"/>
	</div>	
	<div><span id="errMsg"></span></div>	
</form>
</body>
<script>
	$(function(){
		var errMsg = "${errMsg}";
		if(errMsg != null && errMsg != ''){
			$('#errMsg').text(errMsg);
		}
	})
</script>
</html>