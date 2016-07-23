<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>模版编辑－消息系统</title>
<link rel="stylesheet" type="text/css" href="../static/css/bootstrap.max.css" />
<link rel="stylesheet" type="text/css" href="../static/css/message.max.css" />
<script type="text/javascript" src="../static/js/jquery-1.8.2.max.js"></script>
<script type="text/javascript" src="../static/js/bootstrap.max.js"></script>
<script type="text/javascript" src="../static/js/jquery.json-2.4.max.js"></script>
<script type="text/javascript" src="../static/js/page/share_type.max.js"></script>
<link rel="stylesheet" type="text/css" href="../static/css/bootstrap-responsive.max.css" />
</head>
<body>
<jsp:include page="../common/common_header.jsp"></jsp:include>
<div class="container-fluid">
	<div class="row-fluid">
		<div class="span2">
			<jsp:include page="../common/slide_menu.jsp">
				<jsp:param value="share_view" name="menu"/>
			</jsp:include>
		</div>
		<div class="span10 well">
			<div class="row-fluid projectSelect">
				<ul class="nav nav-pills well">
					<li project="DEFAULT" class="active"><a href="javascript:void(0)"><h5>标准</h5></a></li>
					<li project="WENWO"><a class=""  href="javascript:void(0)"><h5>问我</h5></a></li>
					<li project="WEIWEN"><a class=""  href="javascript:void(0)"><h5>新浪微问</h5></a></li>
					<li project="WEIWENQQ"><a class=""  href="javascript:void(0)"><h5>腾讯微问</h5></a></li>
					<li project="SINAASK"><a class=""  href="javascript:void(0)"><h5>微博互助</h5></a></li>
				</ul>
			</div>
			<div class="row-fluid mainTypeSelect">
				<a id="addNewMsgType" href="/share.do?act=addShare&project=DEFAULT" class="btn btn-large btn-info pull-right">
					添加[<span>标准</span>]分享类型
				</a>
			</div>
			
			<div id="messageTypeList" class="row-fluid">
			<c:if test="${not empty shareList}">
				<jsp:include page="share_list.jsp"></jsp:include>
			</c:if>
			</div>
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