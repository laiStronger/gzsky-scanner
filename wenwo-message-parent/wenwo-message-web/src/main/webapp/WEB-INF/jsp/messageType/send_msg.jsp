<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
    if(request.getSession().getAttribute("username") == null){
    	response.sendRedirect("/login.do?act=loginView");
    };
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>发送消息－消息系统</title>
<link rel="stylesheet" type="text/css"
	href="../static/css/bootstrap.max.css" />
<link rel="stylesheet" type="text/css"
	href="../static/css/message.max.css" />
<script type="text/javascript" src="../static/js/jquery-1.8.2.max.js"></script>
<script type="text/javascript" src="../static/js/bootstrap.max.js"></script>
<script type="text/javascript" src="../static/js/jquery.json-2.4.max.js"></script>
<script type="text/javascript" src="../static/js/page/send_msg.max.js"></script>
<script type="text/javascript" src="../static/js/page/chainselect.js"></script>
<link rel="stylesheet" type="text/css" href="../static/css/bootstrap-responsive.max.css" />

</head>
<body>
	<jsp:include page="../common/common_header.jsp"></jsp:include>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span2">
				<jsp:include page="../common/slide_menu.jsp">
					<jsp:param value="send_msg" name="menu"/>
				</jsp:include>
			</div>
			<div class="span10">
				<form id="send_msg_form" class="form-horizontal well">
					<fieldset>
						<div id="legend" class="">
							<legend class="">发送消息</legend>
						</div>
						
						<div class="control-group">
							<!-- Text input-->
							<label class="control-label" for="projectType">项目类型</label>
							<div class="controls">
								<select id="projectType">
									<!-- <option value="ALL">标准</option> -->
									<c:forEach var="subproject_types" items="${subproject_types}">
										<c:if test="${subproject_types != 'VRecommend' && subproject_types != 'LOVE'}">
											<option value="${subproject_types}">${subproject_types.projectName}</option>
										</c:if>
									</c:forEach>
								</select>
							</div>
						</div>
						
						<div class="control-group">
							<!-- Text input-->
							<label class="control-label" for="projectType">请选择发送类型</label>
							<div class="controls" id="sendType">
							<label class="radio">
							<input type="radio" name="optionsRadios" value="message" checked>
							消息类型
							</label>
							<label class="radio">
							<input type="radio" name="optionsRadios"  value="share">
							分享类型
							</label>
								
							</div>
						</div>
						
						<div class="control-group">
							<label class="control-label">类型名称</label>
							<div class="controls">
								<!-- <input type="input" id="typename"/> -->
								<select id="typename">
									<c:forEach var="messageProjectTypes" items="${messageProjectTypes}">
									<option value="${messageProjectTypes.typeName}">${messageProjectTypes.typeName}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						
						<!-- 添加用户ID -->
						<div class="control-group">
							<label class="control-label">用户ID</label>
							<div class="controls">
								<input type="input" id="targetUid"/>
							</div>
						</div>
						
						<div class="control-group">
							<!-- Text input-->
							<label class="control-label" for="projectType">ID类型</label>
							<div class="controls">
								<select id="idType">
									<option value="ALL">请选择ID类型</option>
									<c:forEach var="id_types" items="${id_types}">
									<option value="${id_types}">${id_types}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						
						<!-- 添加id -->
						<div class="control-group">
							<label class="control-label">传递ID</label>
							<div class="controls">
								<input type="input" id="passId"/>
							</div>
						</div>
						
						<!-- 添加额外信息键 -->
						<div class="control-group">
							<label class="control-label">额外信息键</label>
							<div class="controls">
								<input type="input" id="propertyKey" onmousedown="document.getElementById('propertyPromptShow').setAttribute('style','color: red;display: block')"/>
								<span style="display: none" id="propertyPromptShow" >*额外信息多条记录用","标记分开</span>
							</div>
						</div>
						
						<!-- 添加额外信息值 -->
						<div class="control-group">
							<label class="control-label">额外信息值</label>
							<div class="controls">
								<input type="input" id="propertyValue"/>
							</div>
						</div>
						
					</fieldset>
					<a id="send_message" href="javascript:void(0)" class="">发送消息</a>
				</form>
			</div>
		</div>
	</div>
</body>
</html>