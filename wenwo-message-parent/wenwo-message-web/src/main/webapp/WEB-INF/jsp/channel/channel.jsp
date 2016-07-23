<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>增加消息类型－消息系统</title>
<link rel="stylesheet" type="text/css" href="../static/css/bootstrap.max.css" />
<link rel="stylesheet" type="text/css" href="../static/css/message.max.css" />
<script type="text/javascript" src="../static/js/jquery-1.8.2.max.js"></script>
<script type="text/javascript" src="../static/js/bootstrap.max.js"></script>
<link rel="stylesheet" type="text/css" href="../static/css/bootstrap-responsive.max.css" />
    <script type="text/javascript">
    
        $(function() {
        	//切换项目类型
        	var project = "${project}";
        	$("#project").find("a[value="+project+"]").addClass("span2 btn-info");
        	
        	$("#project > a").click(function(){
        		$(this).addClass("span2 btn-info").siblings().removeClass("btn-info");
        		var projectType = $(this).attr("value");
        	})
        	
        	$("#channel_add").click(function(){
        		var projectType = $("a[class*='btn-info']").attr("value");
        		var sinaProject = $("select[name='sina_project']").val();
        		var sinaGroup = $("select[name='sina_group']").val();
        		var qqProject = $("select[name='qq_project']").val();
        		var qqGroup = $("select[name='qq_group']").val();
        		var id = $("input[type='hidden']").val();
        		if(sinaProject=="default"){
        		   alert("请选择新浪项目通道");
        		   return false;
        		}
        		if(sinaGroup=="default"){
        		   alert("请选择新浪组通道");
        		   return false;
        		}
        		if(qqProject=="default"){
        		   alert("请选择腾讯项目通道");
        		   return false;
        		}
        		if(qqGroup=="default"){
        		   alert("请选择腾讯组通道");
        		   return false;
        		}
        		$.post("channel.do?act=channelSave&id="+id+"&messageProjectType="+projectType+"&sinaProject="+sinaProject+"&sinaGroup="+sinaGroup+"&qqProject="+qqProject+"&qqGroup="+qqGroup,
        				function(data,textStatus){
		        			if(data == "1"){
		         			  alert("通道添加成功");
		         	     	}else if(data == "2"){
		         	     	  alert("通道修改成功");
		         	     	}else {
		         	     	  alert("通道添加失败");	
		         	     	}  
		         	     },
		         	  "json");
        	})
        });
        
    </script>
</head>
<body>
   <input type="hidden" name="id" value="${projectChannel.id}" />
	<div class="navbar navbar-inverse navbar-fixed-top">
		<div class="navbar-inner">
			<div class="container-fluid">
				<button type="button" class="btn btn-navbar" data-toggle="collapse"
					data-target=".nav-collapse">
					<span class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="brand" href="#">Project name</a>
				<div class="nav-collapse collapse">
					<jsp:include page="../common/common_header.jsp"></jsp:include>
				</div>
				<!--/.nav-collapse -->
			</div>
		</div>
	</div>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span2">
				<jsp:include page="../common/slide_menu.jsp">
					<jsp:param value="channel" name="menu"/>
				</jsp:include>
			</div>
			<div class="span10">
				<div class="offset3">
					<h1>
						通道配置－<small>消息系统</small>
					</h1>
				</div>
				<div class="row-fluid">
				<br>
				<hr class="">
				</div>
				<div class="row span10">
					<div class="row" id="project">
					<a class="btn span2" href="/channel.do?act=channelView&messageProjectType=DEFAULT" value="DEFAULT">全局标准</a>
					<a class="btn span2" href="/channel.do?act=channelView&messageProjectType=WENWO" value="WENWO">问我标准通道</a>
					<a class="btn span2" href="/channel.do?act=channelView&messageProjectType=WEIWEN" value="WEIWEN">新浪标准微问</a>
					<a class="btn span2" href="/channel.do?act=channelView&messageProjectType=WEIWENQQ" 	value="WEIWENQQ">腾讯标准微问</a>
					<a class="btn span2" href="/channel.do?act=channelView&messageProjectType=SINAASK" value="SINAASK">微博标准互助</a>
					<a class="btn span2" href="/channel.do?act=channelView&messageProjectType=DOCTOR" value="DOCTOR">微问医生通道</a>
					</div>
				</div>
				<div class="row-fluid">
				<br>
				<hr class="">
				</div>
				<div class="row-fluid span12">
			<!-- 	<form class="form-horizontal pull-left">  -->
					<fieldset>
						<div class="control-group">

							<!-- Select Basic -->
							<label class="control-label">标准通道－新浪</label>
							<div class="controls">
								<select class="input-xlarge" name="sina_project">
								    <option value="default">选择项目通道</option>
									<option value="WENWO" <c:if test="${projectChannel.sinaAccGroupType == 'WENWO'}">selected</c:if>>问我</option>
									<option value="WEIWEN" <c:if test="${projectChannel.sinaAccGroupType == 'WEIWEN'}">selected</c:if>>微问</option>
									<option value="SINAASK" <c:if test="${projectChannel.sinaAccGroupType == 'SINAASK'}">selected</c:if>>新浪互助</option>
									<option value="DOCTOR" <c:if test="${projectChannel.sinaAccGroupType == 'DOCTOR'}">selected</c:if>>微问医生</option>
								</select>
								<select class="input-xlarge" name="sina_group">
								    <option value="default">选择分组通道</option>
									<option <c:if test="${projectChannel.sinaGroupType == 'C1'}">selected</c:if>>C1</option>
									<option <c:if test="${projectChannel.sinaGroupType == 'C2'}">selected</c:if>>C2</option>
									<option <c:if test="${projectChannel.sinaGroupType == 'C3'}">selected</c:if>>C3</option>
									<option <c:if test="${projectChannel.sinaGroupType == 'C4'}">selected</c:if>>C4</option>
									<option <c:if test="${projectChannel.sinaGroupType == 'C5'}">selected</c:if>>C5</option>
									<option <c:if test="${projectChannel.sinaGroupType == 'C6'}">selected</c:if>>C6</option>
									<option <c:if test="${projectChannel.sinaGroupType == '推广组'}">selected</c:if>>推广组</option>
									<option <c:if test="${projectChannel.sinaGroupType == 'OFFICIAL'}">selected</c:if>>OFFICIAL</option>
								</select>
							</div>

						</div>

						<div class="control-group">

							<!-- Select Basic -->
							<label class="control-label">标准通道－腾讯</label>
							<div class="controls">
								<select class="input-xlarge" name="qq_project">
								    <option value="default">选择项目通道</option>
									<option value="WEIWENQQ" <c:if test="${projectChannel.qqAccGroupType == 'WEIWENQQ'}">selected</c:if>>腾讯微问</option>
									<option value="WENWO" <c:if test="${projectChannel.qqAccGroupType == 'WENWO'}">selected</c:if>>问我</option>
								</select>
								
								<select class="input-xlarge" name="qq_group">
								    <option value="default">选择分组通道</option>
									<option <c:if test="${projectChannel.qqGroupType == 'C1'}">selected</c:if>>C1</option>
									<option <c:if test="${projectChannel.qqGroupType == 'C2'}">selected</c:if>>C2</option>
									<option <c:if test="${projectChannel.qqGroupType == 'C3'}">selected</c:if>>C3</option>
									<option <c:if test="${projectChannel.qqGroupType == 'C4'}">selected</c:if>>C4</option>
									<option <c:if test="${projectChannel.qqGroupType == 'C5'}">selected</c:if>>C5</option>
									<option <c:if test="${projectChannel.qqGroupType == 'C6'}">selected</c:if>>C6</option>
									<option <c:if test="${projectChannel.qqGroupType == '推广组'}">selected</c:if>>推广组</option>
								</select>
							</div>

						</div>

						<div class="control-group">
							<label class="control-label"></label>

							<!-- Button -->
							<div class="controls">
								<button class="btn btn-primary" id="channel_add">保存</button>
							</div>
						</div>
					</fieldset>
		<!-- 		</form>  -->
			</div>
			</div>
		</div>
</body>
</html>