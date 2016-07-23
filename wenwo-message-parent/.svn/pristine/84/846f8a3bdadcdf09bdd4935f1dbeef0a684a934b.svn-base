$(function(){
	
	var REQUEST_URLS = {"EDIT_TEXT_TEMPLATE_PAGE" : "/template.do?act=textTemplateEditPage",
			"URL_GET_TEXT_TEMPLATE_VIEW":"/template.do?act=getTextTemplateView",
				"URL_GET_VARIALBE_SELECT_PAGE":"/variable.do?act=getVariableSelectPage",
					"URL_ADD_NEW_MESSAGE_TYPE":"/message.do?act=addNewMessageType",
					"URL_ADD_SHARE_MESSAGE_TYPE":"/share.do?act=addShareMessageType"
				};
	
	$(".edit_text_template").on("click", editTextTemplate);
	$("#show_weibo_pic_template").on("click", editPicTemplate);
	$("#submit_pic_template").on("click", function(){
		pic_template.modal('hide');
	});
	
	$("#submit_message_type").on("click", submitMessageType);
	function submitMessageType(){
		var type = $(this).attr("name");
		var messageType = {};
		var messageTypeForm = $("#new_message_type_form");
		
		var projectType = messageTypeForm.find("#projectType").val();
		if(!projectType){
			alert("请填选择项目类型");
			return;
		}
		messageType.projectType = projectType;
		
		var typeName = messageTypeForm.find("#messageTypeName").val();
		if(!typeName){
			alert("请填写名称");
			return;
		}
		messageType.typeName = typeName;
		
		
		var description = messageTypeForm.find("#description").val();
		messageType.description = description;
		
		var mainType = messageTypeForm.find("#mainType").val();
		if(!mainType){
			alert("请选择朱分类");
			return;
		}
		messageType.mainType = mainType;
		
		var insiteWebTemplateInfo = {};
		var insiteWebTemplateInfo_isNeed = messageTypeForm.find("#insiteWebTemplateInfo-isNeed").attr("checked");
		insiteWebTemplateInfo.isNeed = (insiteWebTemplateInfo_isNeed == "checked");
		insiteWebTemplateInfo.content = messageTypeForm.find("#insiteWebTemplateInfo").val();
		messageType.insiteWebTemplateInfo = insiteWebTemplateInfo;
		
		var insiteAppTemplateInfo = {};
		var insiteAppTemplateInfo_isNeed = messageTypeForm.find("#insiteAppTemplateInfo-isNeed").attr("checked");
		insiteAppTemplateInfo.isNeed = (insiteAppTemplateInfo_isNeed == "checked");
		insiteAppTemplateInfo.content = messageTypeForm.find("#insiteAppTemplateInfo").val();
		messageType.insiteAppTemplateInfo = insiteAppTemplateInfo;
		
		var pushTemplateInfo = {};
		var pushTemplateInfo_isNeed = messageTypeForm.find("#pushTemplateInfo-isNeed").attr("checked");
		pushTemplateInfo.isNeed = (pushTemplateInfo_isNeed == "checked");
		pushTemplateInfo.content =	messageTypeForm.find("#pushTemplateInfo").val();
		messageType.pushTemplateInfo = pushTemplateInfo;
		
		var weibo_message_send_type = $("#weibo_message_radio").find("input:radio:checked").val();
		
		var isSendCollMessage = false;
		var weiboTemplateInfo = {};
		weiboTemplateInfo.isNeed = false;
		if(weibo_message_send_type == 'normal'){
			weiboTemplateInfo.isNeed = true;
			var weibo_template_text =  messageTypeForm.find("#weibo_template_text").val();
			if(!weibo_template_text){
				alert("请填写微博模版内容");
				return;
			}
			weiboTemplateInfo.weibo_template_text = weibo_template_text;
			
			var channel_cfg = messageTypeForm.find("#channel_cfg").find("input:checked").val();
			if(channel_cfg == 'specific'){
				var messageTypeChannel = {};
				var sinaAccGroupType = messageTypeForm.find("#sinaAccGroupType").val();
				messageTypeChannel.sinaAccGroupType = sinaAccGroupType;
				var sinaGroupName = messageTypeForm.find("#sinaGroupName").val();
				messageTypeChannel.sinaGroupName = sinaGroupName;
				var qqAccGroupType = messageTypeForm.find("#qqAccGroupType").val();
				messageTypeChannel.qqAccGroupType = qqAccGroupType;
				var qqGroupName = messageTypeForm.find("#qqGroupName").val();
				messageTypeChannel.qqGroupName = qqGroupName;
				
				weiboTemplateInfo.messageTypeChannel = messageTypeChannel;
			}
			
			//微博模板
			var pic_template_info = {};
			var pic_template = $("#pic_template");
			var back_pic_id = pic_template.find("#back_pic_view").attr("picId");
			pic_template_info.back_pic_id = back_pic_id;
			
			var text_infos = pic_template.find("#text_block_table tbody tr.valid_tr");
			var param_text_info = get_text_info_param(text_infos);
			pic_template_info.text_info = param_text_info;
			
			var pic_infos = pic_template.find("#pic_block_table tbody tr.valid_tr");
			var param_pic_info = get_pic_info_param(pic_infos);
			pic_template_info.pic_info = param_pic_info;
			
			var line_info = pic_template.find("#line_info_table tbody tr:eq(0)");
			var param_line_info = get_line_info_param(line_info);
			pic_template_info.line_info = param_line_info;
			
			var cut_info_height = pic_template.find("#cut_info_height").val();
			var cut_info_width =  pic_template.find("#cut_info_width").val();
			
			if((cut_info_height && !cut_info_width) || (!cut_info_height && cut_info_width)){
				alert("请填写完整剪切信息");
				return;
			}
			pic_template_info.cut_info_height = cut_info_height;
			pic_template_info.cut_info_width = cut_info_width;
			
			weiboTemplateInfo.pic_template_info = pic_template_info;
		}else if(weibo_message_send_type == 'collect'){
			isSendCollMessage = true;
		}
		
		messageType.isSendCollMessage = isSendCollMessage;
		messageType.weiboTemplateInfo = weiboTemplateInfo;
		
		//私信
		var primessage_message_send_type = $("#primessage_message_radio").find("input:radio:checked").val();
		var priMessageTemplateInfo = {};
		priMessageTemplateInfo.isNeed = false;
		if(primessage_message_send_type == 'normal'){
			priMessageTemplateInfo.isNeed = true;
			var priMessage_template_text =  messageTypeForm.find("#primessage_template_text").val();
			if(!priMessage_template_text){
				alert("请填写私信模版内容");
				return;
			} 
			
			priMessageTemplateInfo.priMessage_template_text = priMessage_template_text;
			
			//这里使用微博模板
			var pri_pic_template_info = {};
			var pic_template = $("#pic_template");
			var back_pic_id = pic_template.find("#back_pic_view").attr("picId");
			pri_pic_template_info.back_pic_id = back_pic_id;
			
			var text_infos = pic_template.find("#text_block_table tbody tr.valid_tr");
			var param_text_info = get_text_info_param(text_infos);
			pri_pic_template_info.text_info = param_text_info;
			
			var pic_infos = pic_template.find("#pic_block_table tbody tr.valid_tr");
			var param_pic_info = get_pic_info_param(pic_infos);
			pri_pic_template_info.pic_info = param_pic_info;
			
			var line_info = pic_template.find("#line_info_table tbody tr:eq(0)");
			var param_line_info = get_line_info_param(line_info);
			pri_pic_template_info.line_info = param_line_info;
			
			var cut_info_height = pic_template.find("#cut_info_height").val();
			var cut_info_width =  pic_template.find("#cut_info_width").val();
			
			if((cut_info_height && !cut_info_width) || (!cut_info_height && cut_info_width)){
				alert("请填写完整剪切信息");
				return;
			}
			pri_pic_template_info.cut_info_height = cut_info_height;
			pri_pic_template_info.cut_info_width = cut_info_width;
			
			priMessageTemplateInfo.pic_template_info = pri_pic_template_info;
		}
		messageType.priMessageTemplateInfo = priMessageTemplateInfo;
		//私信结束
		
		//短信
		var sms_message_send_type=$("#sms_message_radio").find("input:radio:checked").val();
		var smsTemplateInfo= {};
		smsTemplateInfo.isNeed=false;
		if(sms_message_send_type=="normal"){
			smsTemplateInfo.isNeed=true;
			var sms_template_text =  messageTypeForm.find("#sms_template_text").val();	
			smsTemplateInfo.sms_template_text=sms_template_text;
			if(!sms_template_text){
				alert("请填写短信模版内容");
				return;
			} 	
		}
		messageType.smsTemplateInfo=smsTemplateInfo;
		var url = REQUEST_URLS.URL_ADD_NEW_MESSAGE_TYPE;
		if(type=="share"){
			url = REQUEST_URLS.URL_ADD_SHARE_MESSAGE_TYPE;
		}
		var param = {"encoded": $.toJSON(messageType),"type":type};
		$.ajax({
			url:REQUEST_URLS.URL_ADD_NEW_MESSAGE_TYPE,
			type:"POST",
			data:param,
			success:function(result){
				alert(result);
			},
			error:function(data){
				console.log(data);
			}
		});
	}
	
	function get_line_info_param(line_info){
		var line_info_param = new Object();
		var x_co = line_info.find("td:eq(0) input").val();
		if(x_co){
			line_info_param.x = x_co;
		}
		
		var y_co = line_info.find("td:eq(1) input").val();
		if(y_co){
			line_info_param.y = y_co;
		}
		var height = line_info.find("td:eq(2) input").val();
		if(height){
			line_info_param.height = height;
		}
		var width = line_info.find("td:eq(3) input").val();
		if(width){
			line_info_param.width = width;
		}
		var color = line_info.find("td:eq(4) input").val();
		if(color){
			line_info_param.color = color;
		}
		return line_info_param;
	}
	
	function get_text_info_param(text_infos){
		if(text_infos.size() <= 0){
			return null;
		}
		var info_array = new Array();
		$.each(text_infos, function(){
			var text_info = new Object();
			var tr = $(this);
			var text = tr.find("td:eq(0) input").val();
			text_info.text = text;
			var x_co = tr.find("td:eq(1) input").val();
			text_info.x = x_co;
			var y_co = tr.find("td:eq(2) input").val();
			text_info.y = y_co;
			var font = tr.find("td:eq(3) input").val();
			text_info.font = font;
			var size = tr.find("td:eq(4) input").val();
			text_info.size = size;
			var color = tr.find("td:eq(5) input").val();
			text_info.color = color;
			info_array.push(text_info);
		});
		return info_array;
	}
	
	function get_pic_info_param(pic_infos){
		if(pic_infos.size() <= 0){
			return null;
		}
		var info_array = new Array();
		$.each(pic_infos, function(){
			var pic_info = new Object();
			var tr = $(this);
			var url = tr.find("td:eq(0) input").val();
			pic_info.url = url;
			var x_co = tr.find("td:eq(1) input").val();
			pic_info.x = x_co;
			var y_co = tr.find("td:eq(2) input").val();
			pic_info.y = y_co;
			var defaultUrl = tr.find("td:eq(3) input").val();
			pic_info.defaultUrl = defaultUrl;
			var width = tr.find("td:eq(4) input").val();
			pic_info.width = width;
			var height = tr.find("td:eq(5) input").val();
			pic_info.height = height;
			info_array.push(pic_info);
		});
		return info_array;
	}
	
	var text_template = $("#text_template");
	var pic_template = $("#pic_template");
	
	function editPicTemplate(){
		pic_template.pic_template();
		pic_template.modal();
	}
	
	getVariableSelectPage();
	function getVariableSelectPage(){
		var projectType = $("#projectType").val();
		var param = {"projectType": projectType};
		$.ajax({
			url:REQUEST_URLS.URL_GET_VARIALBE_SELECT_PAGE,
			type:"POST",
			data:param,
			success:function(result){
				text_template.find("div.variable_select").html(result);
				text_template.find("div.accordion a.variable").off("click", addVariable);
				text_template.find("div.accordion a.variable").on("click", addVariable);
				
			},
			error:function(data){
				console.log(data);
			}
		});
		
		$.ajax({
			url:REQUEST_URLS.URL_GET_VARIALBE_SELECT_PAGE,
			type:"POST",
			data:param,
			success:function(result){
				pic_template.find("div.variable_select").html(result);
				pic_template.find("div.accordion a.variable").off("click", addPicVariable);
				pic_template.find("div.accordion a.variable").off("click", addPicVariable);
				
			},
			error:function(data){
				console.log(data);
			}
		});
	}
	
	function addPicVariable(e){
		var focus_component = pic_template.find(".variable-focus");
		var variable_name = $(this).attr("name");
		if(!variable_name){
			return;
		}
		var new_val = focus_component.val() + "{" + variable_name + "}";
		focus_component.val(new_val);
	}
	
	function addVariable(e){
		var focus_component = text_template.find(".variable-focus");
		var variable_name = $(this).attr("name");
		if(!variable_name){
			return;
		}
		var new_val = focus_component.val() + "{" + variable_name + "}";
		focus_component.val(new_val);
	}
	

	
	function editTextTemplate(){
		var info_type = $(this).attr("info_type");
		var exist_content = $(this).parent().find("textarea").val();
		text_template.find("#template_content").val(exist_content);
		text_template.find("#template_info_type").val(info_type);
		var project_type = $("#projectType").val();
		if(!project_type){
			alert("请选择项目类型");
			return;
		}
		text_template.modal();
	}
	
	function getTextTemplate(projectType, templateInfoType){
		var param = {"templateId":textTemplateId, "messageTypeId":message_type_id, "templateInfoType":templateInfoType};
		var projectType = $("div.projectSelect li.active").attr("project");
		param.projectType = projectType;
		$.ajax({
			url:REQUEST_URLS.URL_GET_TEXT_TEMPLATE_VIEW,
			type:"POST",
			data:param,
			success:showTextTemplate,
			error:function(data){
				console.log(data);
			}
		});
	}
	
	text_template.on("click", "#submit_text_template", function(){
		var templ_info_type = text_template.find("#template_info_type").val();
		var content = text_template.find("#template_content").val();
		var text = $("#" + templ_info_type);;
		if(text){
			text.find("textarea").val(content);
		}
		text_template.modal('hide');
	});
	
	$("#weibo_message_radio").on("change", ":radio", function(){
		var value = $(this).val();
		if(value != "normal"){
			$("#weibo_message").hide();
		}else{
			$("#weibo_message").show();
		}
	});
	
	$("#channel_cfg .radio").on("change", ":radio", function(){
		var type = $(this).val();
		if(type == "specific"){
			$("#channel").show();
		}else{
			$("#channel").hide();
		}
		
	});
	
	function addParam(requestUrl, key, value){
		requestUrl += "&";
		requestUrl += key;
		requestUrl += "=";
		requestUrl += value;
		return requestUrl;
	}
	
	//选择需要时，弹出textarea
	 $("#insiteWebTemplateInfo-isNeed").change(function() {
		 if ($("#insiteWebTemplateInfo-isNeed").is(":checked"))  {
			 $("#INSITE_WEB").show();
		 } else {
			 $("#INSITE_WEB").hide();
		 }
	 });
	 
	 $("#insiteAppTemplateInfo-isNeed").change(function() {
		 if ($("#insiteAppTemplateInfo-isNeed").is(":checked"))  {
			 $("#INSITE_APP").show();
		 } else {
			 $("#INSITE_APP").hide();
		 }
	 });
	 
	 $("#pushTemplateInfo-isNeed").change(function() {
		 if ($("#pushTemplateInfo-isNeed").is(":checked"))  {
			 $("#PUSH").show();
		 } else {
			 $("#PUSH").hide();
		 }
	 });

	
	
});