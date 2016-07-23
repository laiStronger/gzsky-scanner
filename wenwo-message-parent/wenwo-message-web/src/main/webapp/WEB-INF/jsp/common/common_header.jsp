<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<script type="text/javascript" src="../static/js/jquery-1.8.2.max.js"></script>
<script type="text/javascript" src="../static/js/bootstrap.max.js"></script>
<script type="text/javascript" src="../static/js/jquery.json-2.4.max.js"></script>
<script type="text/javascript" src="../../static/js/page/login.js"></script> 

<div class="navbar navbar-inverse navbar-fixed-top">
	<div class="navbar-inner">
		<div class="container-fluid">
			<button type="button" class="btn btn-navbar" data-toggle="collapse"
				data-target=".nav-collapse">
				<span class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="brand" href="#"><font style="color: #FFA54A;font-size: 25px;">消息系统管理后台</font></a>
			<div class="nav-collapse collapse">
				<p class="navbar-text pull-right">
					登录用户： <%=request.getSession().getAttribute("username") %>
				    &nbsp;&nbsp;  <a href="" id="logout" name="logout" style="color: yellow;">退出</a>
				</p>
 			</div>
			<!--/.nav-collapse -->
		</div>
	</div>
</div>