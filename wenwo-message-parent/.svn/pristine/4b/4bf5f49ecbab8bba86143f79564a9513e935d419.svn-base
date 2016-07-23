<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>模版编辑－消息系统</title>
<link rel="stylesheet" type="text/css"
	href="../static/css/bootstrap.max.css" />
<link rel="stylesheet" type="text/css"
	href="../static/css/message.max.css" />
<script type="text/javascript" src="../static/js/jquery-1.8.2.max.js"></script>
<script type="text/javascript" src="../static/js/bootstrap.max.js"></script>
<script type="text/javascript" src="../static/js/jquery.json-2.4.max.js"></script>
<script type="text/javascript" src="../static/js/page/share.max.js"></script>
<script type="text/javascript" src="../static/js/page/pic_template.max.js"></script>
<link rel="stylesheet" type="text/css" href="../static/css/bootstrap-responsive.max.css" />
</head>
<body>
	<jsp:include page="../common/common_header.jsp"></jsp:include>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span2">
				<jsp:include page="../common/slide_menu.jsp"></jsp:include>
			</div>
			<div class="span10">
				<form id="new_message_type_form" class="form-horizontal well">
					<fieldset>
						<div id="legend" class="">
							<legend class="">新增消息类型</legend>
						</div>
						
						<div class="control-group">
							<!-- Text input-->
							<label class="control-label" for="projectType">项目类型</label>
							<div class="controls">
								<input id="projectType" value="${project_type}" disabled/>
							</div>
						</div>
						
						<div class="control-group">
							<!-- Text input-->
							<label class="control-label" for="input01">新增类型名称</label>
							<div class="controls">
								<input id="messageTypeName" required placeholder="" class="input-xlarge" type="text">
							</div>
						</div>
						<!-- 添加描述 -->
						<div class="control-group">
							<label class="control-label">添加描述</label>
							<div class="controls">
								<input type="input" id="share_description"/>
							</div>
						</div>
		
						<div id="legend" class="">
							<legend class="">模版配置</legend>
						</div>
												
						<div id="weibo_message" class="">
						<div class="control-group">
							<div class="control-group">
							<!-- Textarea -->
							<label class="control-label">微博文本模版</label>
							<div class="controls">
								<div  id="WEIBO" class="textarea">
									<textarea id="weibo_template_text" type="" class=""> </textarea>
									<a info_type="WEIBO" class="btn btn-info edit_text_template">可视化编辑</a>
								</div>
							</div>
							</div>
							<!-- 是否需要转发 -->
						<div class="control-group">
						    <label class="control-label">微博转发</label>
							<div class="controls">
								<!-- Multiple Checkboxes -->
								<label class="checkbox"> <input value="需要" type="checkbox" id="repost">
									需要
								</label>
							</div>
					     </div>
					     <!-- 转发微博模板 -->
					     <div class="control-group" id="RE_WEIBO" style="display:none;">
							<!-- Textarea -->
							<label class="control-label">转发微博文本模版</label>
							<div class="controls">
								<div  class="textarea" id="re_weibo_post">
									<textarea id="re_weibo_template_text" type="" class=""> </textarea>
									<a info_type="re_weibo_post" class="btn btn-info edit_text_template">可视化编辑</a>
								</div>
							</div>
						</div>
					     
						<div class="control-group">
							<label class="control-label">微博图片模版</label>

							<!-- Button -->
							<div class="controls">
								<a id="show_weibo_pic_template" class="btn btn-danger">编辑</a>
							</div>
						</div>
						</div>
					</fieldset>
					<a id="submit_message_type" href="javascript:void(0)" name="share" class="">保存配置</a>
				</form>
			</div>
		</div>
		<!-- 遮罩层 -->
	<div id="text_template" class="modal hide fade" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal"
				aria-hidden="true">×</button>
			<h3></h3>
		</div>
		<jsp:include page="../template/text_template.jsp"></jsp:include>
	</div>
	
	<div id="pic_template" class="modal hide fade" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal"
				aria-hidden="true">×</button>
			<h3></h3>
			<input style="display:none" id="message_type_id">
		</div>
		<jsp:include page="../template/pic_template.jsp"></jsp:include>
	</div>
	</div>
	
	
</body>
</html>