$(function(){
	
	var REQUEST_URLS = {
			"URL_GET_TEXT_TEMPLATE_BY_ID":"/template.do?act=getTextTemplate",
			"URL_SUBMIT_TEXT_TEMPLATE":"/template.do?act=submitTextTemplate",
			"URL_SUBMIT_PIC_TEMPLATE":"/template.do?act=savePicTemplate",
			"URL_GET_PIC_TEMPLATE_BY_ID":"/template.do?act=getPicTemplate",
			"URL_GET_MESSAGE_TYPE_LIST":"/message.do?act=getMessageTypeList",
			"URL_GET_TEXT_TEMPLATE_VIEW":"/template.do?act=getTextTemplateView",
			"URL_UPDATE_MESSAGE_TYPE_CHANNEL":"/message.do?act=updateMessageTypeChannel",
			"URL_DELETE_MESSAGE_TYPE":"/message.do?act=deleteMessageType",
			"URL_UPDATE_MESSAGE_TYPE_STATUS":"/message.do?act=updateMessageTypeStatus"
	};
	
	var INFO_TYPE_INDEX = {
			"INSITE_WEB":3,
			"INSITE_APP":4,
			"PUSH":5,
			"WEIBO":6,
			"PRIMESSAGE":7
	};
	
	$("button.delete_message_type").on("click", deleteMessageType);
	function deleteMessageType(){
		if(!confirm("确定删除该消息类型")){
			return;
		}
		var messageTypeId = $(this).attr("type_id");
		var param = {"messageTypeId":messageTypeId};
		$(this).parent().parent().parent().remove();
		$.ajax({
			url:REQUEST_URLS.URL_DELETE_MESSAGE_TYPE,
			type:"POST",
			data:param,
			success:function(result){
			}
		});
	};
	
	
	$("button.stop_start_message_type").on("click", stopOrStartMessageType);
	function stopOrStartMessageType(){
		var oldStatus = $(this).attr("status");
		var newStatus = 'IN_USE';
		if(oldStatus == 'IN_USE'){
			if(!confirm("是否确定停用该消息类型")){
				return;
			}
			
			$(this).text("起用");
			$(this).attr("status","STOP");
			newStatus='STOP';
		}else{
			$(this).text("停用");
			$(this).attr("status","IN_USE");
		}
		var messageTypeId = $(this).attr("type_id");
		var param = {"messageTypeId":messageTypeId, "status":newStatus};
		$.ajax({
			url:REQUEST_URLS.URL_UPDATE_MESSAGE_TYPE_STATUS,
			type:"POST",
			data:param,
			success:function(result){
			}
		});
	}
	
	var projectSelect = $("div.projectSelect");
	projectSelect.on("click", "li", function(){
		var _this = $(this);
		if(projectSelect.data("loading") || _this.hasClass("active")){
			return;
		}
		projectSelect.data("loading", true);
		projectSelect.find("li.active").removeClass("active");
		_this.addClass("active");
		var projectType = _this.attr("project");
		var mainType = mainTypeSelect.find("select").val();
		
		var project_name = _this.find("h5").text();
		$("#addNewMsgType").attr("href","/message.do?act=newMessageTypeView&project=" + projectType);
		$("#addNewMsgType").find("span").text(project_name);
		getMessageTypeList(projectType, mainType);
	});
	
	var mainTypeSelect = $("div.mainTypeSelect");
	mainTypeSelect.on("change", "select", function(){
		if(projectSelect.data("loading")){
			return;
		}
		var _this = $(this);
		var projectType = projectSelect.find("li.active").attr("project");
		var mainType = _this.val();
		_this.attr("disabled", "disabled");
		getMessageTypeList(projectType, mainType);
	});
	
	function getMessageTypeList(projectType, mainType){
		var param = {
				"projectType" : projectType,
				"mainType" : mainType
		};
		
		$.ajax({
			url:REQUEST_URLS.URL_GET_MESSAGE_TYPE_LIST,
			type:"POST",
			data:param,
			success:function(data){
				if(data){
					projectSelect.removeData("loading");
					mainTypeSelect.find("select").removeAttr("disabled");
					$("#messageTypeList").html(data);
					$(".text_template_change").on("click", createOrChangeTextTemplate);
					$(".messageTypeChannel").on("click", changeMessageTypeChannel);
					$("button.weibo_pic_template").on("click", picTemplate);
					$("button.delete_message_type").on("click", deleteMessageType);
					$("button.stop_start_message_type").on("click", stopOrStartMessageType);
				}
			},
			error:function(){
				
			}
		});
	}
	
	$(".messageTypeChannel").on("click", changeMessageTypeChannel);
	var message_type_channel = $("#message_type_channel");
	function changeMessageTypeChannel(){
		var messageTypeTd = $(this).parent().parent().find("td:eq(0)");
		var message_type_id = messageTypeTd.attr('message_type_id');
		message_type_channel.find("#message_type_id").val(message_type_id);
		var typeName = messageTypeTd.text();
		var title = typeName + " - 微博消息通道";
		$("#message_type_channel h3").html(title);
		
		
		var sinaAccGroupType= $(this).attr("sinaAccGroupType");
		var sinaGroupName= $(this).attr("sinaGroupName");
		var qqAccGroupType= $(this).attr("qqAccGroupType");
		var qqGroupName= $(this).attr("qqGroupName");
		
		var channelType = message_type_channel.find("#channel_cfg");
		if(sinaAccGroupType && sinaGroupName && qqAccGroupType && qqGroupName){
			message_type_channel.find("select.sina_project").val(sinaAccGroupType);
			message_type_channel.find("select.sina_group").val(sinaGroupName);
			message_type_channel.find("select.qq_project").val(qqAccGroupType);
			message_type_channel.find("select.qq_group").val(qqGroupName);
			channelType.find("input[value='specific']").attr("checked","checked");
			message_type_channel.find("#message_type_channel_config").show();
		}else{
			channelType.find("input[value='default']").attr("checked","checked");
			message_type_channel.find("#message_type_channel_config").hide();
		}
		
		message_type_channel.find("#channel_cfg .radio").on("change", ":radio", function(){
			var type = $(this).val();
			if(type == "specific"){
				message_type_channel.find("#message_type_channel_config").show();
			}else{
				message_type_channel.find("#message_type_channel_config").hide();
			}
			}); 
		
		message_type_channel.modal();
	}
	
	message_type_channel.on("click", "#submit_message_type_channel", submitMessageTypeChannel);
	
	function submitMessageTypeChannel(){
		
		var channel_type = message_type_channel.find("#channel_cfg input:checked").val();
		var sinaAccGroupType = '';
		var sinaGroupName = '';
		var qqAccGroupType = '';
		var qqGroupName = '';
		
		if(channel_type == "specific"){
			sinaAccGroupType = message_type_channel.find("select.sina_project").val();
			sinaGroupName = message_type_channel.find("select.sina_group").val();
			qqAccGroupType = message_type_channel.find("select.qq_project").val();
			qqGroupName = message_type_channel.find("select.qq_group").val();
		}
		
		var param = {
				"sinaAccGroupType":sinaAccGroupType,
				"sinaGroupName":sinaGroupName,
				"qqAccGroupType":qqAccGroupType,
				"qqGroupName":qqGroupName,
		};
		
		var message_type_id = message_type_channel.find("#message_type_id").val();
		param.messageTypeId = message_type_id;
		$.ajax({
			url:REQUEST_URLS.URL_UPDATE_MESSAGE_TYPE_CHANNEL,
			type:"POST",
			data:param,
			dataType:"JSON",
			success:function(result){
				var messageTypeId = result.data.messageTypeId;
				var messageTypeChannelBtn = $("#" + messageTypeId).find("button.messageTypeChannel");
				messageTypeChannelBtn.addClass("btn-success");
				messageTypeChannelBtn.attr("sinaAccGroupType");
				messageTypeChannelBtn.attr("sinaGroupName");
				messageTypeChannelBtn.attr("qqAccGroupType");
				messageTypeChannelBtn.attr("qqGroupName");
				message_type_channel.modal('hide');
			}
		});
	}
	
	$(".text_template_change").on("click", createOrChangeTextTemplate);
	
	var text_template = $("#text_template");
	
	text_template.on("change", "#template_content", function(){
		text_template.find("#error_info").hide();
	});
	
	function submitTextTemplate(){
		var template_id = text_template.find("#text_template").val();
		var message_type_id = text_template.find("#message_type_id").val();
		var template_content = text_template.find("#template_content").val();
		
		if(!template_content){
			text_template.find("#error_info").show();
			return;
		}
		
		var template_info_type = text_template.find("#template_info_type").val();
		
		var param = {
				"template_id":template_id,
				"message_type_id":message_type_id,
				"template_content":template_content,
				"template_info_type":template_info_type
		};
		
//		var posting = $.post("/template.do?act=submitTextTemplate", param );
		$.ajax({
			url:REQUEST_URLS.URL_SUBMIT_TEXT_TEMPLATE,
			type:"POST",
			data:param,
			dataType:"JSON",
			success:function(result){
				var messageTypeId = result.data.messageTypeId;
				var info_type = result.data.infoType;
				var td = $("#" + messageTypeId).find("td[info_type='" + info_type + "']");
				var templateId = result.data.templateId;
				td.find("button.text_template_change").addClass("btn-success").attr("templateId", templateId);
				text_template.modal('hide');
			},
			error:function(){
				alert("test");
			}
		});
//		posting.done(function(data){
//			alert(data);
//		});
	}
	
	function createOrChangeTextTemplate(){
		var templateId = $(this).attr("templateId");
		var columnIndex = getColumnIndexFromTD($(this).parent());
		
		var table = $("#message_type_table");
		var th = $(table.find("tr:eq(0) th")[columnIndex]);
		var type = th.text();
		var templateInfoType = th.attr("value");
		$("#text_template #template_info_type").val(templateInfoType);
		
		var messageTypeTd = $(this).parent().parent().find("td:eq(0)");
		var typeName = messageTypeTd.text();
		var title = typeName + " - " + type;
		$("#text_template h3").html(title);
		
		var message_type_id = messageTypeTd.attr('message_type_id');
		$("#text_template #message_type_id").val(message_type_id);
		
		//修改template
		$("#text_template #template_id").val(templateId);
		getTextTemplate(templateId, message_type_id, templateInfoType);
	
	}
	
	function getTextTemplate(textTemplateId, message_type_id, templateInfoType){
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
	
	function initTextTemplateVariableView(){
		text_template.off("click", "div.accordion a.variable", addVariableTextTempl);
		text_template.on("click", "div.accordion a.variable", addVariableTextTempl);
	}
	
	function addVariableTextTempl(){
		var name = $(this).attr("name");
		if(!name){
			return;
		}
		
		var content = text_template.find("#template_content");
		
		var currentExpress = content.val();
		
		var newContent = currentExpress + "{" + name + "}";
		content.val(newContent);
	}
	
	function showTextTemplate(data){
		if(data){
			text_template.find("div.modal-body").html(data);
			initTextTemplateVariableView();
			text_template.off("click", "#submit_text_template", submitTextTemplate);
			text_template.on("click", "#submit_text_template", submitTextTemplate);
			text_template.modal('show');
		}else{
			alert(result.error_msg);
		}
		
	}
	
	function getColumnIndexFromTD(table_td){
		var currentTr = table_td.parent();
		return currentTr.find("td").index(table_td);
	}
	
	
	//PIC TEMPLATE
	var pic_template = $("#pic_template");
	$("button.weibo_pic_template").on("click", picTemplate);
	
	function picTemplate(){
		var picTemplateId = $(this).attr("templateId");
		var message_type_id = $(this).parent().attr("message_type_id");
		if(!message_type_id){
			return;
		}
		pic_template.find("#message_type_id").val(message_type_id);
		createOrChangePicTemplate(picTemplateId);
	}
	
	var TEXT_BLOCK_LINE = "";
	var PIC_BLOCK_LINE = "";
	function initPicTemplate(){
		$("#newTextBlock").on("click", newTextBlock);
		$("#newPicBlock").on("click", newPicBlock);
		pic_template.find("button.save").on("click", save);
		pic_template.find("button.delete-btn").on("click", deleteFunction);
		pic_template.find("button.change-btn").on("click", changeFun);
		
		initPicVariableCom();
		
		TEXT_BLOCK_LINE = $("#text_block_table #text_block_line");
		PIC_BLOCK_LINE = $("#pic_block_table #pic_block_line");
		$("#back-group-pic").fileupload({
			dataType: 'json',
			url:"/template.do?act=uploadBackPic",
			add:function(e, data){
				if(confirm("确定替换文件？")){
					data.submit();
				}else{
					return;
				}
			},
			done:function(e, data){
				var result = data.result;
				if(result.is_succ == 'Y'){
					$("#back_pic_view").attr("href", result.data.picUrl);
					$("#back_pic_view").attr("picId", result.data.picId);
					$("#back_pic_view img").attr("src", result.data.picUrl);
				}else{
					alert(dataJson.error_msg);
				}
			}
		});
	}
	
	function initPicVariableCom(){
		pic_template.off("click", "div.accordion a.variable", addVariableForPicTemplate);
		pic_template.on("click", "div.accordion a.variable", addVariableForPicTemplate);
	}
	
	function addVariableForPicTemplate(){
		var var_name = $(this).attr("name");
		var editing_text = pic_template.find("input.variable-focus");
		var new_content = editing_text.val() + "{" + var_name + "}";
		editing_text.val(new_content);
	}
	
	function newTextBlock(){
		var new_line = initNewLine(TEXT_BLOCK_LINE);
		clearFocusInput();
		new_line.insertBefore(TEXT_BLOCK_LINE);
		new_line.find("button.save").on("click", save);
		new_line.find("button.delete-btn").on("click", deleteFunction);
		new_line.find("button.change-btn").on("click", changeFun);
	}
	
	function clearFocusInput(){
		pic_template.find("input.variable-focus").removeClass("variable-focus");
	}
	
	function newPicBlock(){
		var new_line = initNewLine(PIC_BLOCK_LINE);
		clearFocusInput();
		new_line.insertBefore(PIC_BLOCK_LINE);
		new_line.find("button.save").on("click", save);
		new_line.find("button.delete-btn").on("click", deleteFunction);
		new_line.find("button.change-btn").on("click", changeFun);
	}
	
	function deleteFunction(){
		$(this).parent().parent().parent().remove();
	}
	
	function changeFun(){
		clearFocusInput();
		var tr = $(this).parent().parent().parent();
		tr.find("input:visible").removeAttr("disabled");
		tr.find("td:first input").addClass("variable-focus");
		$(this).parent().find("button:hidden").show();
		$(this).hide();
		$(this).parent().find("button.delete-btn").hide();
	}
	
	function save(){
		var tr = $(this).parent().parent().parent();
		
		var isValid = true;
		$.each(tr.find("input:visible"), function(){
			if(!$(this).val()){
				alert("无效输入");
				$(this).focus();
				isValid = false;
				return false;
			}
		});
		
		if(isValid){
			tr.find("input:visible").attr("disabled", "disabled");
			tr.find("td:first input").removeClass("variable-focus");
			$(this).parent().find("button:hidden").show();
			$(this).hide();
		}
	}
	
	function initNewLine(template_line){
		var new_line = template_line.clone();
		new_line.attr("id", "");
		new_line.find("td:eq(0) input").addClass("variable-focus");
		new_line.show();
		return new_line;
	}
	function createOrChangePicTemplate(picTemplateId){
		var param = {"template_id" : picTemplateId};
		var projectType = $("div.projectSelect li.active").attr("project");
		param.projectType = projectType;
		$.ajax({
			url:REQUEST_URLS.URL_GET_PIC_TEMPLATE_BY_ID,
			type:"POST",
			data:param,
			success:function(data){
				pic_template.find(".modal-body").html(data);
				pic_template.modal('show');
				
				initPicTemplate();
			}
		});
	}
	
	pic_template.find("#submit_pic_template").on("click", submit_pic_template);
	
	/**
	 * 提交图片模板
	 */
	function submit_pic_template(){
		var param = new Object();
		
//		var back_pic_id = pic_template.find("#back_pic_view").attr("picId");
//		param.back_pic_id = back_pic_id;
		
		var text_infos = pic_template.find("#text_block_table tbody tr:visible");
		var param_text_info = get_text_info_param(text_infos);
		param.text_info = param_text_info;
		
		var pic_infos = pic_template.find("#pic_block_table tbody tr:visible");
		var param_pic_info = get_pic_info_param(pic_infos);
		param.pic_info = param_pic_info;
		
		var line_info = pic_template.find("#line_info_table tbody tr:eq(0)");
		var param_line_info = get_line_info_param(line_info);
		param.line_info = param_line_info;
		
		var msg_type_id = pic_template.find("#message_type_id").val();
		
		var cut_info_height = pic_template.find("#cut_info_height").val();
		param.cut_info_height = cut_info_height;
		var cut_info_width = pic_template.find("#cut_info_width").val();
		param.cut_info_width = cut_info_width;
		
		//获得pic_template的id
		var picTemplateId1 = pic_template.find("#template_id").val();
		
		var encoded = {"encoded":$.toJSON(param), "msg_type_id":msg_type_id,"picTemplateId":picTemplateId1};
		$.ajax({
			type:"POST",
			dataType:'json',
			url:REQUEST_URLS.URL_SUBMIT_PIC_TEMPLATE,
			data:encoded,
			success:function(data){
				if(data.is_succ == 'Y'){
					var pic_template_id = data.data.templateId;
					var msg_type_id = data.data.messageTypeId;
					$("#" + msg_type_id).find(".weibo_pic_template").attr("templateId", pic_template_id);
					$("#pic_template").modal('hide');
				}else{
					alert(result.error_msg);
				}
			},
			error:function(data){
				
			}
		});
	}
	
	/**
	 * pietemplate的line_info
	 */
	function get_line_info_param(line_info){
		var line_info_param = new Object();
		var x_co = line_info.find("td:eq(0) input").val();
		line_info_param.x = x_co;
		var y_co = line_info.find("td:eq(1) input").val();
		line_info_param.y = y_co;
		var height = line_info.find("td:eq(2) input").val();
		line_info_param.height = height;
		var width = line_info.find("td:eq(3) input").val();
		line_info_param.width = width;
		var color = line_info.find("td:eq(4) input").val();
		line_info_param.color = color;
		return line_info_param;
	}
	
	/**
	 * pietemplate的textinfo
	 */
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
			var needLineBreak = tr.find("td:eq(6) input").attr("checked") == undefined ? false : true;
			
			text_info.needLineBreak = needLineBreak;
			var showLineNum = tr.find("td:eq(7) input").val();
			text_info.showLineNum = showLineNum;
			var lineWidth = tr.find("td:eq(8) input").val();
			text_info.lineWidth = lineWidth;
			var lineHeight = tr.find("td:eq(9) input").val();
			text_info.lineHeight = lineHeight;
			
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
	
});

window.MT=(function () {
	var MT=function () {
	};
	/**04.11.13wencan修改*/
	//初始化消息类型配置按钮
	var messageTypeId = $(".countMessageTypeId");
	var btnCount = 3;
	
	messageTypeId.ready(function(){
		initBtn();
	});
	
	function initBtn(){
		for(var i = 0; i < messageTypeId.size(); i++){
			for(var j = 1; j <= btnCount; j++){
				var need = $("#mneed"+j+"_"+messageTypeId[i].value);
				if(!need[0].checked){
					var alter = $("#malter"+j+"_"+messageTypeId[i].value);
					alter.css("display","none");
				}
				need.on("click",function(){
					var warp = $(this).attr("id").split("need");
					var alter = $("#malter"+warp[1]);
					if($(this)[0].checked){
						alter.css("display","block");
					}
					else{
						alter.css("display","none");
					}
				});
			}
		}
	}
	
	var change_message_type = $(".change_message_type");
	change_message_type.on("click",function(){
		if(confirm("确定修改吗？")){
			var param = {};
			var id = $(this).attr("type_id");
			var weibo_template_web_need = $("#mneed1_"+id)[0].checked;
			if(weibo_template_web_need){
				var weibo_template_web_id = $("#malter1_"+id).attr("templateid");
				if(weibo_template_web_id == undefined || weibo_template_web_id == null){
					alert("请修改站内消息模板（web）");
					return;
				};
				param.weibo_template_web_need = true;
			}
			else{
				param.weibo_template_web_need = false;
			}
			var weibo_template_phone_need = $("#mneed2_"+id)[0].checked;
			if(weibo_template_phone_need){
				var weibo_template_phone_id = $("#malter2_"+id).attr("templateid");
				if(weibo_template_phone_id == undefined || weibo_template_phone_id == null){
					alert("请修改站内消息模板（手机）");
					return;
				};
				param.weibo_template_phone_need = true;
			}
			else{
				param.weibo_template_phone_need = false;
			}
			var weibo_template_phone_push = $("#mneed3_"+id)[0].checked;
			if(weibo_template_phone_push){
				var weibo_template_phone_push = $("#malter3_"+id).attr("templateid");
				if(weibo_template_phone_push == undefined || weibo_template_phone_push == null){
					alert("请修改站内消息模板（手机）");
					return;
				};
				param.weibo_template_phone_push = true;
			}
			else{
				param.weibo_template_phone_push = false;
			}
			
			//微博
			var weibo_weibo_message = $("#mweibo_"+id)[0].checked;
			var weibo_weibo_allnot = $("#mallnot_"+id)[0].checked;
			if(weibo_weibo_message){
				param.weibo_notice = "weibo";
			} else if(weibo_weibo_allnot){
				param.weibo_notice = "allnot";
			} else{
				param.weibo_notice = "allnot";
			}
			
			//私信
			var priMessage_priMessage_message = $("#mpri_"+id)[0].checked;
			var priMessage_priMessage_allnot = $("#mpriallnot_"+id)[0].checked;
			
			if(priMessage_priMessage_message && !priMessage_priMessage_allnot){
				param.priMessage_notice = "priMessage";
			} else{
				param.priMessage_notice = "allnot";
			}
			
			//短信
			var sms_sms_message =$("#msms_"+id)[0].checked;
			var sms_sms_allnot = $("#msmsallnot_"+id)[0].checked;
			
			if(sms_sms_message && !sms_sms_allnot){
				param.sms_notice="sms";
			}else{
				param.sms_notice="allnot";
			}
			
			
			//附加的私信图片
			//......先不加
			
			param.id = id;
			$.ajax({
				url:"/message.do?act=alterMessageType",
				type:"POST",
				data:param,
				success:function(data){
					var json = null;
					try{json =$.parseJSON(data);}catch(err){};
					alert(json.data);
				},
				error:function(){
					alert("修改失败");
				}
			});
		};
	});
});