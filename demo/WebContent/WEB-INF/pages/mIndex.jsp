<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="mainApp">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no, width=device-width">
    <title>model</title>
    <link href="<%=request.getContextPath() %>/ionic-v1.3.0/css/ionic.css" rel="stylesheet">
    <link href="<%=request.getContextPath() %>/datepicker/css/mobiscroll.custom-2.6.2.min.css" rel="stylesheet">
    <script src="<%=request.getContextPath() %>/ionic-v1.3.0/js/ionic.bundle.js"></script>
    <script src="<%=request.getContextPath() %>/jquery/jquery-1.11.1.js"></script>
    <script src="<%=request.getContextPath() %>/datepicker/js/mobiscroll.custom-2.6.2.min.js"></script>
    <script type="text/javascript">
    	var basePath = '<%=request.getContextPath()%>';
    </script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/app/app.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/app/controller.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/app/service.js"></script>
    <style type="text/css">
    	.slideBox{
    		height : 100px;
    	}
    	
		.slider-slide {
		  color: #000;
		  background-color: #fff;
		  text-align: center;
		  font-family: "HelveticaNeue-Light", "Helvetica Neue Light", "Helvetica Neue", Helvetica, Arial, "Lucida Grande", sans-serif; 
		  font-weight: 300;
		}
		
		.blue {
		  background-color: blue;
		  height : 100%;
		}
		
		.yellow {
		  background-color: yellow;
		  height : 100%;
		}
		
		.pink {
		  background-color: pink;
		  height : 100%;
		}
		
		.myicon{
			background-color: #9f9;
			height: 60px;
			text-align: center;
			padding: 15px;
			font-size: 12px;
			font-weight: 500;
		}
		
		.col-border{
			border: 1px dotted #eee;
		}
		.myicon1{
			background-color: #ddd;
			height: 60px;
			font-weight: 500;
			text-align: center;
			padding: 15px;
			font-family: "HelveticaNeue-Light", "Helvetica Neue Light", "Helvetica Neue", Helvetica, Arial, "Lucida Grande", sans-serif;
		}
    </style>
</head>
<body>
	<ion-nav-bar class="bar-positive">
      <ion-nav-back-button>
      </ion-nav-back-button>
    </ion-nav-bar>
             
    <ion-nav-view></ion-nav-view>
</body>
</html>