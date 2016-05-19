<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<ion-view view-title="主页">
    <ion-content on-swipe-up="showBottomMenu()">
	       	<ion-slide-box class="slideBox" show-pager="true" does-continue="true">
				<ion-slide>
							<div class="box blue" onclick="alert('1')"><h1>BLUE</h1></div>
				</ion-slide>
				<ion-slide>
							<div class="box yellow"><h1>YELLOW</h1></div>
				</ion-slide>
				<ion-slide>
							<div class="box pink"><h1>PINK</h1></div>
				</ion-slide>
			</ion-slide-box>
			<div class="padding">
				欢迎 <span style="color:blue;">${SYS_USER_INFO.lastName}</span> 登陆移动办公系统！
			</div>
			<div class="row responsive-break" ng-repeat="item1 in items">
		        <div class="col col-25 col-border" style="width: 100%" ng-repeat="item in item1">
		          	<div class="col-demo">
						<div class="myicon" ui-sref="{{item.sref}}">{{item.title}}</div>
					</div> 
		        </div>
		    </div>
    </ion-content>
</ion-view>