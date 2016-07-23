$(function(){
	var send_message = $("#send_message");
	
	send_message.on("click",function(){
		 var param = {};
		 var $send_msg_form = $("#send_msg_form");
		 var $projectType = $send_msg_form.find("#projectType")[0].value;
		 if($projectType == null || $projectType == ""){
			 alert("请选择项目类型");
			 return;
		 }
		 param.projectType = $projectType;
		 var $sendType = $send_msg_form.find("#sendType input:radio:checked").val();
		 if($sendType == null || $sendType == ""){
			 alert("请选择发送类型");
			 return;
		 }
		var _typename = $send_msg_form.find("#typename").val();
		param.typename = _typename;
		 param.sendType = $sendType;
		 var $targetUid = $send_msg_form.find("#targetUid").val();
		 if( $targetUid == "" || $targetUid == null){
			 alert("请填写用户ID");
			 return;
		 }
		 param.targetUid = $targetUid;
		 var $idType = $send_msg_form.find("#idType")[0].value;
		 param.idType = $idType;
		 param.passId = $send_msg_form.find("#passId").val();
		 var $propertyKey = $send_msg_form.find("#propertyKey").val();
		 if($propertyKey.length != 0){
			 var $propertyValue = $send_msg_form.find("#propertyValue").val();
			 var keyLength = $propertyKey.split(",").length;
			 var valueLength = $propertyValue.split(",").length;
			 if($propertyValue == null || $propertyValue == ""){
				 alert("请填写额外信息值");
				 return;
			 }
			 else if(keyLength != valueLength){
				 alert("额外信息键值对不匹配");
				 return;
			 }
			 else{
				 param.propertyKey = $propertyKey;
				 param.propertyValue = $propertyValue;
			 }
		 }
		 $.ajax({
			url:"/message.do?act=sendMsg",
			type:"post",
		 	data:param,
		 	success:function(data){
		 		var json = null;
				try{json =$.parseJSON(data);}catch(err){};
				alert(json.data);
		 	}
		 });
	});
	
	$("#sendType").on("change", ":radio", function(){
		var sendType = $(this).val();
		if(sendType == "message"){
			$(".message-group").css("display","block");
			$(".share-group").css("display","none");
		}
		else if(sendType == "share"){
			$(".message-group").css("display","none");
			$(".share-group").css("display","block");
		}
		else{
			$(".message-group").css("display","none");
			$(".share-group").css("display","none");
		}
	});
	
});

