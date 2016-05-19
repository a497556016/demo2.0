<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE div PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="<%=request.getContextPath() %>/ionic-v1.3.0/css/ionic.css" rel="stylesheet">
<script src="<%=request.getContextPath() %>/ionic-v1.3.0/js/ionic.bundle.js"></script>
<script src="<%=request.getContextPath() %>/jquery/jquery-1.11.1.js"></script>
<script type="text/javascript">
angular.module('WriteOnContentApp', ['ionic'])
.controller('WriteOnContentCtrl',function($scope,$ionicPopup){
	$scope.writeOn = function(){
		if(navigator.geolocation){
	  		navigator.geolocation.watchPosition(function(position){
				console.log(position);
			}, function(error){
				console.log(error);
				$ionicPopup.alert({
					title : '提示',
					template : error.message
				});
			}, {
				enableHighAccuracy : true,
				maximumAge : 0,
				timeout : 10000
			})
		}else{
			$ionicPopup.alert({
				title : '提示',
				template : '无法获取位置信息'
			});
		}
	}
});
</script>
</head>
<body ng-app="WriteOnContentApp" ng-controller="WriteOnContentCtrl">
	<div style="height:45px;"></div>
	<div style="background-color: #fff;width:100%;height:100%;text-align: center;" align="center">
		<a><img style="width:100%;height:80%;" alt="" src="<%=request.getContextPath() %>/image/daka.jpg" ng-click="writeOn()"></a>
		<div style="width:100%;height:20%;"> 
			<h1>请点击打卡</h1>
		</div>
	</div>
	
</body>
</html>