<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="loginApp">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no, width=device-width">
    <title>model</title>
    <link href="<%=request.getContextPath() %>/ionic-v1.3.0/css/ionic.css" rel="stylesheet">
    <link href="<%=request.getContextPath() %>/datepicker/css/mobiscroll.custom-2.6.2.min.css" rel="stylesheet">
    <script src="<%=request.getContextPath() %>/ionic-v1.3.0/js/ionic.bundle.js"></script>
    <script src="<%=request.getContextPath() %>/jquery/jquery-1.11.1.js"></script>
    <script src="<%=request.getContextPath() %>/datepicker/js/mobiscroll.custom-2.6.2.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/mLogin.js"></script>
    
</head>
<body ng-controller="LoginContrl" ng-app="loginApp">
	<script id="loginModal.html" type="text/ng-template">
		<ion-modal-view>
    		<ion-header-bar>
      			<h1 class="title">登陆页面</h1>
    		</ion-header-bar>
    		<ion-content>
				<form novalidate>
    				<div class="list list-inset">
  					<label class="item item-input">
						<span class="input-label">用户名：</span>
    					<input type="text" ng-model="user.username" placeholder="用户名">
  					</label>
  					<label class="item item-input">
						<span class="input-label">密码：</span>
    					<input type="password" ng-model="user.password" placeholder="密码">
  					</label>
					</div>
					<div class="padding">
						<button class="button button-full button-positive" ng-click="login()">登陆</button>
						<button class="button button-full button-positive" ng-click="">注册</button>
  					</div>
					</form>
				
    		</ion-content>
  		</ion-modal-view>
	</script>
</body>
</html>