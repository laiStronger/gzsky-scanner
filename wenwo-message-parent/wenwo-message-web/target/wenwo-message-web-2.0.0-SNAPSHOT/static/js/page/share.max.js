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
	
	
	$("#repost").on("click",function(){
		if($("#repost").attr('checked')==undefined){
			$("#RE_WEIBO").hide();
		}else{
			$("#RE_WEIBO").show();
		}
		
	})
	
	
	$("#submit_message_type").on("click", submitMessageType);
	function submitMessageType(){
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
		var weiboTemplateInfo = {};
			var weibo_template_text =  messageTypeForm.find("#weibo_template_text").val();
			if(!weibo_template_text){
				alert("请填写微博模版内容");
				return;
			}
			weiboTemplateInfo.isNeed = false;
			if($("#repost").attr('checked')!=undefined){
				var re_weibo_template_text = $("#re_weibo_template_text").val();
				if(re_weibo_template_text.replace(/^\s*|.*\s$/,"").length == 0){
					alert("微博转发模板为空");
					return ;
				}
				weiboTemplateInfo.isNeed = true;
				weiboTemplateInfo.re_weibo_template_text=re_weibo_template_text;
			}
			
			var share_description = messageTypeForm.find("#share_description").val();
			if(!share_description){
				alert("请填写模板描述");
				return;
			}
			weiboTemplateInfo.weibo_template_description = share_description;
			
			weiboTemplateInfo.weibo_template_text = weibo_template_text;
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
		
		messageType.weiboTemplateInfo = weiboTemplateInfo;
		var param = {"encoded": $.toJSON(messageType)};
		$.ajax({
			url:REQUEST_URLS.URL_ADD_SHARE_MESSAGE_TYPE,
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
	
	$("#projectType").on("change", getVariableSelectPage);
	function getVariableSelectPage(){
		var projectType = $(this).val();
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
});