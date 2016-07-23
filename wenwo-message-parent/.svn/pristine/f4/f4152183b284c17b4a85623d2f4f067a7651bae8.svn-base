<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GB18030">
<link rel="stylesheet" type="text/css"
	href="../static/css/bootstrap.max.css" />
<link rel="stylesheet" type="text/css"
	href="../static/css/message.max.css" />
<script type="text/javascript" src="../static/js/jquery-1.8.2.max.js"></script>
<script type="text/javascript" src="../static/js/bootstrap.max.js"></script>
<link rel="stylesheet" type="text/css"
	href="../static/css/bootstrap-responsive.max.css" />
<title>集合消息配置 － 消息系统</title>
</head>
<body>
	<jsp:include page="../common/common_header.jsp"></jsp:include>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span2">
				<jsp:include page="../common/slide_menu.jsp">
					<jsp:param value="coll_template" name="menu"/>
				</jsp:include>
			</div>
			<div class="span10">
				<div class="row-fluid">
					<div id="legend" class="">
						<legend class="">集合消息配置</legend>
					</div>
				</div>
				<div class="row-fluid">
					<div class="span2">
						<jsp:include page="../variable/variable_select_page.jsp"></jsp:include>
					</div>
					<div class="span10">
						<form class="form-horizontal">
							<fieldset>
								<div class="control-group">

									<!-- Textarea -->
									<label class="control-label">模版内容</label>
									<div class="controls">
										<div class="textarea">
											<textarea id="template_content" type=""
												class="variable-focus"
												required> ${empty template ? '' : template.templateContent } </textarea>
										</div>
									</div>
								</div>

								<div class="control-group">
									<!-- Appended input-->
									<label class="control-label">发送周期</label>
									<div class="controls">
										<div class="input-append">
											<input id="period" class="" value="${empty template ? '' : template.period }" type="text"
												required> <span class="add-on">小时</span>
										</div>
										<p class="help-block"></p>
									</div>

								</div>

								<div id="channel">
									<div id="channel_info" style="display: none">
										<input id="sinaAccGroupType_input" value="${empty template ? '' : template.sinaAccGroupType}">
										<input id="qqAccGroupType_input" value="${empty template ? '' : template.qqAccGroupType}">
										<input id="sinaGroupName_input" value="${empty template ? '' : template.sinaGroupName}">
										<input id="qqGroupName_input" value="${empty template ? '' : template.qqGroupName}">
									</div>
									<jsp:include page="../channel/channel_select_page.jsp"></jsp:include>
								</div>
			
								<div class="control-group">
									<div class="controls">
										<a id="save_coll_template"
											template_id="${empty template ? '' : template.id }"
											class="btn btn-success">保存</a>
									</div>
								</div>
			
					</fieldset>
					</form>
				</div>
			</div>
		</div>
	</div>
	</div>
	<script type="text/javascript">
	    $(function() {
	    	var channel_info = $("#channel_info");
	    	var sinaAccGroupType = channel_info.find("#sinaAccGroupType_input").val();
	    	if(sinaAccGroupType){
	    		$("#sinaAccGroupType").val(sinaAccGroupType);
	    	}
	    	var qqAccGroupType = channel_info.find("#qqAccGroupType_input").val();
	    	if(qqAccGroupType){
	    		$("#qqAccGroupType").val(qqAccGroupType);
	    	}
	    	var sinaGroupName = channel_info.find("#sinaGroupName_input").val();
	    	if(sinaGroupName){
	    		$("#sinaGroupName").val(sinaGroupName);
	    	}
	    	var qqGroupName = channel_info.find("#qqGroupName_input").val();
	    	if(qqGroupName){
	    		$("#qqGroupName").val(qqGroupName);
	    	}
	    	
        	$("div.accordion a.variable").on("click", function(e){
        		var var_name = $(this).attr("name");
        		var editing_text = $(".variable-focus");
        		var new_content = editing_text.val() + "{" + var_name + "}";
        		editing_text.val(new_content);
        	});
	    	
	    	$("#save_coll_template").on("click", function(e){
	    		var template_content = $("#template_content").val();
	    		if(!template_content){
	    			return;
	    		}
	    		var period = $("#period").val();
	    		if(!period){
	    			return;
	    		}
	    		var sinaAccGroupType = $("#sinaAccGroupType").val();
	    		if(!sinaAccGroupType){
	    			return;
	    		}
	    		var qqAccGroupType = $("#qqAccGroupType").val();
	    		if(!qqAccGroupType){
	    			return;
	    		}
	    		var sinaGroupName = $("#sinaGroupName").val();
	    		if(!sinaGroupName){
	    			return;
	    		}
	    		var qqGroupName = $("#qqGroupName").val();
	    		if(!qqGroupName){
	    			return;
	    		}
	    		
	    		var templateId = $(this).attr("template_id");
	    		
	    		var param = {"template_content":template_content,
	    				"period":period,
	    				"sinaAccGroupType":sinaAccGroupType,
	    				"qqAccGroupType":qqAccGroupType,
	    				"sinaGroupName":sinaGroupName,
	    				"qqGroupName":qqGroupName,
	    				"templateId":templateId};
	    		
	    		$.ajax({
	    			url:"/template.do?act=saveOrUpdateCollTemplate",
	    			type:"POST",
	    			data:param,
	    			dataType:"JSON",
	    			success:function(data){
	    				alert("保存成功");
	    			},
	    			error:function(data){
	    				
	    			}
	    		});
	    	});
	    });
	</script>
</body>
</html>