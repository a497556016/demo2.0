<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<ion-view view-title="每日签到">
	<ion-content>
		<div class="bar bar-header bar-positive">
			<button class="button button-positive" ng-click="toggleLeft()">
				<i class="icon ion-navicon-round"></i>
			</button>
		  	<h1 class="title">{{title}}</h1>
		</div>
	   	<ion-side-menus>
			<!-- 中间内容 -->
			<ion-side-menu-content id="writeOn_content" style="z-index:99999;">
				<iframe width="100%" height="100%" ng-src="{{contentUrl}}"></iframe>
			</ion-side-menu-content>
	
	      	<ion-side-menu side="left">
		        <ion-content class="has-header">
		          	<ion-list ng-repeat="item in writeItem">
			            <ion-item nav-clear menu-close ng-click="selectItem(item)" ng-class="{active:item.text == title}">
			            	{{item.text}}
			            </ion-item>
		          	</ion-list>
		        </ion-content>
	      	</ion-side-menu>
		</ion-side-menus>
	</ion-content>
</ion-view>
