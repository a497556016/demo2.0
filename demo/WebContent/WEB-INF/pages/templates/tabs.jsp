<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<ion-tabs class="tabs-icon-top tabs-positive">

  	<ion-tab title="主页" icon="ion-home" ui-sref="tabs.home">
    		<ion-nav-view name="home-tab"></ion-nav-view>
  	</ion-tab>

  	<ion-tab title="发现" icon="ion-ios-information" ui-sref="tabs.about">
    		<ion-nav-view name="about-tab"></ion-nav-view>
  	</ion-tab>

  	<ion-tab title="设置" icon="ion-ios-world" ui-sref="tabs.config">
    		<ion-nav-view name="config-tab"></ion-nav-view>
  	</ion-tab>

</ion-tabs>