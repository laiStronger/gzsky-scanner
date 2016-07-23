<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>模版编辑－消息系统-新增消息类型</title>
<link rel="stylesheet" type="text/css"
	href="../static/css/bootstrap.max.css" />
<link rel="stylesheet" type="text/css"
	href="../static/css/message.max.css" />
<script type="text/javascript" src="../static/js/jquery-1.8.2.max.js"></script>
<script type="text/javascript" src="../static/js/bootstrap.max.js"></script>
<script type="text/javascript" src="../static/js/jquery.json-2.4.max.js"></script>
<script type="text/javascript"
	src="../static/js/page/pic_template.max.js"></script>

<!-- 使用这个js -->
<script type="text/javascript"
	src="../static/js/page/new_msg_type.max.js"></script>

<link rel="stylesheet" type="text/css"
	href="../static/css/bootstrap-responsive.max.css" />
</head>
<body>
	<jsp:include page="../common/common_header.jsp"></jsp:include>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span2">
				<jsp:include page="../common/slide_menu.jsp">
					<jsp:param value="add_new_msy_type" name="menu" />
				</jsp:include>
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
								<input id="projectType" value="${project_type}" disabled />
							</div>
						</div>

						<div class="control-group">
							<!-- Text input-->
							<label class="control-label" for="input01">新增类型名称</label>
							<div class="controls">
								<input id="messageTypeName" required placeholder=""
									class="input-xlarge" type="text">
							</div>
						</div>

						<div class="control-group">
							<label class="control-label" for="input01">描述</label>
							<div class="controls">
								<textarea id="description" style="width: 350px; height: 130px;">
								</textarea>
								<p class="help-block"></p>
							</div>
						</div>

						<div class="control-group">
							<label class="control-label">消息主分类</label>
							<div class="controls">
								<select id="mainType" class="input-xlarge">
									<option value="">请选择</option>
									<c:forEach var="main_type" items="${main_types }">
										<option value="${main_type}">${main_type.typeName }</option>
									</c:forEach>
								</select>
							</div>
						</div>

						<!-- 站内消息(Web) -->
						<div id="legend" class="">
							<legend class="">模版配置</legend>
						</div>
						<div class="control-group">
							<label class="control-label">站内消息(Web)</label>
							<div class="controls">
								<!-- Multiple Checkboxes -->
								<!-- <label class="checkbox" data-toggle="collapse" data-target="#INSITE_WEB"> -->
								<input id="insiteWebTemplateInfo-isNeed" value="需要"
									type="checkbox"> 需要 </label>
							</div>
						</div>

						<div id="INSITE_WEB" style="display: none;">
							<!-- Textarea -->
							<label class="control-label">站内模版(Web)</label>
							<div class="controls">
								<div class="textarea">
									<textarea id="insiteWebTemplateInfo" type="" class=""
										style="width: 400px; height: 130px;"> </textarea>
									<a info_type="INSITE_WEB"
										class="btn btn-info edit_text_template">可视化编辑</a>
								</div>
							</div>
						</div>

						<!-- 站内消息(App) -->
						<div class="control-group">
							<label class="control-label">站内消息(App)</label>
							<div class="controls">
								<!-- <label  data-toggle="collapse" data-target="#INSITE_APP" class="checkbox inline"> -->
								<input id="insiteAppTemplateInfo-isNeed" value="需要"
									type="checkbox"> 需要 </label>
							</div>

						</div>

						<div id="INSITE_APP" style="display: none;">
							<label class="control-label">站内模版(App)</label>
							<div class="controls">
								<div class="textarea">
									<textarea id="insiteAppTemplateInfo" type="" class=""
										style="width: 400px; height: 130px;"> </textarea>
									<a info_type="INSITE_APP"
										class="btn btn-info edit_text_template">可视化编辑</a>
								</div>
							</div>
						</div>

						<!-- Push消息 -->
						<div class="control-group">
							<label class="control-label">Push消息</label>
							<div class="controls">
								<!-- <label data-toggle="collapse" data-target="#PUSH" class="checkbox inline"> -->
								<input id="pushTemplateInfo-isNeed" value="需要" type="checkbox">
								需要 </label>
							</div>

						</div>

						<div id="PUSH" style="display: none;">
							<label class="control-label">Push模版</label>
							<div class="controls">
								<div class="textarea">
									<textarea id="pushTemplateInfo" type="" class=""
										style="width: 400px; height: 130px;"> </textarea>
									<a info_type="PUSH" class="btn btn-info edit_text_template">可视化编辑</a>
								</div>
							</div>
						</div>

						<div class="control-group">
							<!-- Select Basic -->
							<label class="control-label">微博消息</label>
							<div id="weibo_message_radio" class="controls">
								<label class="radio inline"> <input checked
									value="normal" name="weibo_message" type="radio"> 正常@通知
								</label> <label type="collect" class="radio inline"> <input
									value="collect" name="weibo_message" type="radio"> 集合消息
								</label> <label class="radio inline"> <input value="no_send"
									name="weibo_message" type="radio"> 不发送
								</label>
							</div>

						</div>

						<div id="weibo_message" class="">
							<div class="control-group">
								<div class="control-group">
									<!-- Textarea -->
									<label class="control-label">微博文本模版</label>
									<div class="controls">
										<div id="WEIBO" class="textarea">
											<textarea id="weibo_template_text" type="" class=""
												style="width: 400px; height: 130px;"> </textarea>
											<a info_type="WEIBO" class="btn btn-info edit_text_template">可视化编辑</a>
										</div>
									</div>
								</div>

								<div class="control-group">
									<label class="control-label">发送通道</label>
									<div id="channel_cfg" class="controls">
										<label class="radio inline"> <input checked="checked"
											value="default" name="通道配置" type="radio"> 使用标准通道
										</label> <label class="radio inline"> <input value="specific"
											name="通道配置" type="radio"> 配置个性化通道
										</label>
									</div>
								</div>

								<div id="channel" style="display: none">
									<div class="control-group">
										<label class="control-label">发送通道(新浪)</label>
										<div class="controls">
											<select id="sinaAccGroupType" class="input-xlarge">
												<option value="WENWO">问我</option>
												<option value="WEIWEN">新浪微问</option>
												<option value="SINAASK">新浪互助</option>
												<option value="DOCTOR">微问医生</option>
											</select> <select id="sinaGroupName" class="input-xlarge">
												<option value="C1">C1</option>
												<option value="C2">C2</option>
												<option value="C3">C3</option>
												<option value="C4">C4</option>
												<option value="C5">C5</option>
												<option value="C6">C6</option>
												<option value="推广组">推广组</option>
											</select>
										</div>
									</div>

									<div class="control-group">
										<label class="control-label">发送通道(腾讯)</label>
										<div class="controls">
											<select id="qqAccGroupType" class="input-xlarge">
												<option value="WEIWENQQ">腾讯微问</option>
												<option value="WENWO">问我</option>
											</select> <select id="qqGroupName" class="input-xlarge">
												<option value="C1">C1</option>
												<option value="C2">C2</option>
												<option value="C3">C3</option>
												<option value="C4">C4</option>
												<option value="C5">C5</option>
												<option value="C6">C6</option>
												<option value="推广组">推广组</option>
											</select>
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
						</div>

						<!-- 私信文本模版 -->
						<div class="control-group">
							<!-- Select Basic -->
							<label class="control-label">私信消息</label>
							<div id="primessage_message_radio" class="controls">
								<label class="radio inline"> <input checked
									value="normal" name="pri_message" type="radio"> 正常通知
								</label> <label class="radio inline"> <input value="no_send"
									name="pri_message" type="radio"> 不发送
								</label>
							</div>
						</div>

						<div class="control-group">
							<!-- Textarea -->
							<label class="control-label">私信文本模版</label>
							<div class="controls">
								<div id="PRIMESSAGE" class="textarea">
									<textarea id="primessage_template_text" type="" class=""
										style="width: 400px; height: 130px;"> </textarea>
									<a info_type="PRIMESSAGE"
										class="btn btn-info edit_text_template">可视化编辑</a>
								</div>
							</div>
						</div>
						<!-- 短信通知模板 -->
						<div class="control-group">
							<!-- Select Basic -->
							<label class="control-label">短信消息</label>
							<div id="sms_message_radio" class="controls">
								<label class="radio inline"> <input checked
									value="normal" name="sms_message" type="radio"> 正常通知
								</label> <label class="radio inline"> <input value="no_send"
									name="sms_message" type="radio"> 不发送
								</label>
							</div>
						</div>
						<div class="control-group">
							<!-- Textarea -->
							<label class="control-label">短信文本模版</label>
							<div class="controls">
								<div id="SMS" class="textarea">
									<textarea id="sms_template_text" type="" class=""
										style="width: 400px; height: 130px;"> </textarea>
									<a info_type="SMS" class="btn btn-info edit_text_template">可视化编辑</a>
								</div>
							</div>
						</div>
						<div class="control-group">
							<!-- Select Basic -->
							<label class="control-label">信息模板</label>
							<div id="external_message_radio" class="controls">
								<label class="radio inline"> <input checked
									value="normal" name="external_message" type="radio"> 正常通知
								</label> <label class="radio inline"> <input value="no_send"
									name="external_message" type="radio"> 不发送
								</label>
							</div>
						</div>
						<div class="control-group">
							<!-- Textarea -->
							<label class="control-label">信息模版</label>
							<div class="controls">
								<div id="EXTERNAL" class="textarea">
									<textarea id="external_template_text" type="" class=""
										style="width: 400px; height: 130px;"> </textarea>
									<a info_type="EXTERNAL" class="btn btn-info edit_text_template">可视化编辑</a>
								</div>
							</div>
						</div>
						<!-- 私信文本模版结束 -->
					</fieldset>
					<a id="submit_message_type" href="javascript:void(0)"
						name="message" class="">保存配置</a>
				</form>
			</div>
		</div>
		<!-- 遮罩层 -->
		<div id="text_template" class="modal hide fade" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">×</button>
				<h3></h3>
			</div>
			<jsp:include page="../template/text_template.jsp"></jsp:include>
		</div>
        
		<div id="pic_template" class="modal hide fade" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">×</button>
				<h3></h3>
				<input style="display: none" id="message_type_id">
			</div>
			<jsp:include page="../template/pic_template.jsp"></jsp:include>
		</div>
	</div>


</body>
</html>