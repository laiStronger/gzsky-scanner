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
<title>查询消息－消息系统</title>
<link rel="stylesheet" type="text/css"
	href="../static/css/bootstrap.max.css" />
<link rel="stylesheet" type="text/css"
	href="../static/css/message.max.css" />
<script type="text/javascript" src="../static/js/jquery-1.8.2.max.js"></script>
<script type="text/javascript" src="../static/js/bootstrap.max.js"></script>
<script type="text/javascript" src="../static/js/jquery.json-2.4.max.js"></script>
<script type="text/javascript" src="../static/js/page/search_msg.max.js"></script>
<link rel="stylesheet" type="text/css" href="../static/css/bootstrap-responsive.max.css" />

</head>
<body>
	<jsp:include page="../common/common_header.jsp"></jsp:include>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span2">
				<jsp:include page="../common/slide_menu.jsp">
					<jsp:param value="search_msg" name="menu"/>
				</jsp:include>
			</div>
			<div class="span10">
				<form id="send_msg_form" class="form-horizontal well">
					<fieldset>
						<div id="legend" class="">
							<legend class="">查询消息<span style="color:red;">(仅查询一周以内的消息日志)</span></legend>
						</div>
						
						<!-- 用户ID -->
						<div class="control-group">
							<label class="control-label">用户ID</label>
							<div class="controls">
								<input type="input" id="targetUid"/><span style="color:red;">(问我用户ID或微博UID,***必填项)</span>
							</div>
						</div>
						
						<!-- 消息ID -->
						<div class="control-group">
							<label class="control-label">消息ID</label>
							<div class="controls">
								<input type="input" id="messageId"/>
							</div>
						</div>
						
					</fieldset>
					<a id="search_message" href="javascript:void(0)" class="">查询</a>
					
					<!-- 正常消息 -->
					<div id="messageInsiteDiv" style="display:none;">
					    <h3 align="center">发送日志</h3>
					    <table id="messageInsiteTable" border="1" align="center">
					          <tr>
					               <td width="20%">消息Id</td>
					               <td width="20%">项目类型</td>
					               <td width="20%">消息类型</td>
					               <td width="20%">发送时间</td>
					          </tr>
					    </table>
					</div>
					
					
					<!-- 错误消息 -->
					<div id="messageErrorDiv" style="display:none;">
					    <h3 align="center">错误日志</h3>
					    <table id="messageErrorTable" border="1" align="center">
					          <tr>
					               <td width="200px;">消息Id</td>
					               <td width="200px;">描述</td>
					               <!-- <td width="300px;">错误信息</td> -->
					               <td width="200px;">创建时间</td>
					          </tr>
					    </table>
					</div>
					
				</form>
			</div>
		</div>
	</div>
</body>
</html>