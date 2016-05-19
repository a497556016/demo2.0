<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<ion-view view-title="请假流程">
       	<ion-content class="padding">
          	<div class="list">
			  <label class="item item-input item-stacked-label">
			    <span class="input-label">请假类型</span>
			   	<input type="text" ng-click="vtModal.show()" ng-model="vacation.vacationType" value="{{vacation.vacationType}}" placeholder="请选择"/>
			  </label>
			  <label class="item item-input item-stacked-label">
			    <span class="input-label">请假开始日期</span>
			    <input onfocus="setdate(this)" onclick="setdate(this)" type="text" ng-model="vacation.vacationBeginDate" value="{{vacation.vacationBeginDate}}" placeholder="请假日期">
			  </label>
			  <label class="item item-input item-stacked-label">
			    <span class="input-label">请假结束日期</span>
			    <input onfocus="setdate(this)" onclick="setdate(this)" type="text" ng-model="vacation.vacationEndDate" value="{{vacation.vacationEndDate}}" placeholder="请假日期">
			  </label>
			  <label class="item item-input item-stacked-label">
			    <span class="input-label">请假原因</span>
				<textarea rows="5" cols="" placeholder="请假原因" ng-model="vacation.vacationReason">{{vacation.vacationReason}}</textarea>
			  </label>
			  <button class="button button-full button-positive" ng-click="submitVacationData()">提交</button>
			  <button class="button button-full button-assertive" ng-click="resetForm()">重置</button>
			</div>
       	</ion-content>
</ion-view>

<script id="vt-modal.html" type="text/ng-template">
  <ion-modal-view>
    <ion-header-bar>
      <h1 class="title">请假类型</h1>
    </ion-header-bar>
    <ion-content>
      <ion-radio ng-repeat="type in vacationTypes"
               ng-value="type.value"
               ng-model="vacation.vacationType">
      	{{ type.name }}
    	</ion-radio>
		<button class="button button-full button-positive" ng-click="vtModal.hide()">确定</button>
    </ion-content>
  </ion-modal-view>
</script>