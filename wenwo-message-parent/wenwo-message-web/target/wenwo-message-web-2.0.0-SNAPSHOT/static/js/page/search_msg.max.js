$(function(){
	var search_message = $("#search_message");
	
	search_message.on("click",function(){
		 var _messageId = $("#messageId").val();
		 var _targetUid = $("#targetUid").val();
		
		 if(_targetUid == null || _targetUid=="") {
			 alert("请填写用户ID!");
			 return;
		 }
		 
		 var param = {};
		 param.messageId = _messageId;
		 param.targetUid = _targetUid;
		 
		 $.ajax({
			   type: "POST",
			   url: "message.do?act=searchMsg",
			   data: param,
			   dataType:"json",
			   success: function(data,textStatus){
				   pushMessageInsiteData(data);
				   pushMessageErrorData(data);
                 
			   },
			   
			   statusCode: {404: function() {
				    alert('page not found');
			   }}

		 });
		 
	});
		
});

/**
 * 获取发送日志的tr
 * @param messageId
 * @param projectType
 * @param messageType
 * @param createTime
 * @returns {String}
 */
function getMessageInsitetrContent(messageId,projectType,messageType,strCreateTime) {
	if(messageId == undefined) {
		messageId = "";
	}
	if(projectType == undefined) {
		projectType = "";
	}
	if(messageType == undefined) {
		messageType = "";
	}
	if(strCreateTime == undefined) {
		strCreateTime = "";
	}
	
	var content = "<tr>";
	content += "<td>" + messageId + "</td>";
	content += "<td>" + projectType + "</td>";
	content += "<td>" + messageType + "</td>";
	content += "<td>" + strCreateTime + "</td>";
	content += "</tr>";
	return content;
}


/**
 * 获取错误日志的tr
 * @param messageId
 * @param projectType
 * @param messageType
 * @param sendTime
 * @returns {String}
 */
function getMessageErrortrContent(msgId,description,errorMessage,createTime) {
	if(msgId == undefined) {
		msgId = "";
	}
	if(description == undefined) {
		description = "";
	}
	if(errorMessage == undefined) {
		errorMessage = "";
	}
	if(createTime == undefined) {
		createTime = "";
	}
	
	var content = "<tr>";
	content += "<td>" + msgId + "</td>";
	content += "<td>" + description + "</td>";
	//content += "<td>" + errorMessage + "</td>";
	content += "<td>" + createTime + "</td>";
	content += "</tr>";
	return content;
}


/**
 * 正常消息
 * @param data
 */
function pushMessageInsiteData(data) {
	   var messageInsiteList = data.messageInsiteList;
	   var messageInsiteCreateTimeList = data.messageInsiteCreateTimeList;
	   var messageInsiteTable = $("#messageInsiteTable");
	   
	   //先清除原来的tr
	   $("#messageInsiteTable tr").each(function(i){
		   if(i>0){
			   $(this).remove();
		   } 
	   });
	   $("#count1").remove();
	   $("#messageInsiteDiv").css("display","block");
	   
	   //有记录就显示div
	   if(messageInsiteList.length > 0) {
		   //循环添加
		   for(var i=0;i<messageInsiteList.length;i++){
			   var messageId = messageInsiteList[i].messageId;
			   var projectType = messageInsiteList[i].projectType;
			   var messageType = messageInsiteList[i].messageType;
			   var strCreateTime = messageInsiteCreateTimeList[i];
			   var content = getMessageInsitetrContent(messageId,projectType,messageType,strCreateTime);
			   messageInsiteTable.append(content);
		   }
		   //记录数
		   $("#messageInsiteDiv").append("<font id='count1' style=\'display:block; text-align:center\' color=red>共" + messageInsiteList.length + "条</font>");
	   } else {
		   //没记录
		   $("#messageInsiteTable").append("<tr><td colspan='4' align=center><font color=red>没有记录！</font></td></tr>");
	   }
	  
}


/**
 * 错误消息
 * @param data
 */
function pushMessageErrorData(data) {
	   var messageErrorList = data.messageErrorList;
	   var messageErrorCreateTimeList = data.messageErrorCreateTimeList;
	   var messageErrorTable = $("#messageErrorTable");
	   
	 //先清除原来的tr
	   $("#messageErrorTable tr").each(function(i){
		   if(i>0){
			   $(this).remove();
		   } 
	   });
	   $("#count2").remove();
	   $("#messageErrorDiv").css("display","block");
	   
	   //有记录就显示div
	   if(messageErrorList.length > 0) {
		   //循环添加
		   for(var i=0;i<messageErrorList.length;i++){
			   var msgId = messageErrorList[i].msgId;
			   var description = messageErrorList[i].description;
			   var errorMessage = messageErrorList[i].errorMessage;
			   var createTime = messageErrorCreateTimeList[i];
			   
			   var content = getMessageErrortrContent(msgId,description,errorMessage,createTime);
			   messageErrorTable.append(content);
		   }
		   //记录数
		   $("#messageErrorDiv").append("<font id='count2' style=\'display:block; text-align:center\' color=red>共" + messageErrorList.length + "条</font>");
	   } else {
		   //没记录
		   $("#messageErrorTable").append("<tr><td colspan='4' align=center><font color=red>没有记录！</font></td></tr>");
	   }
}


