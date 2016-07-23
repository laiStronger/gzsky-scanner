<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <title>消息管理系统后台</title>
    <link href="../../../static/css/alogin.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="../../static/js/jquery-1.8.2.max.js"></script>
    <script type="text/javascript" src="../../static/js/page/login.js"></script>

</head>
<body>
    <div class="Main">
        <ul>
            <li class="top"></li>
            <li class="top2"></li>
            <li class="topA"></li>
            <li class="topB"><div style="font-size:30px;padding-top:30px;padding-left:20px;">
			    消息管理系统后台
            </div></li>
            <li class="topC"></li>
            <li class="topD">
                <ul class="login">
				     <form action="/login.do?act=loginInForm" method="post">
				        <li></li>

	                    <li>
	                        <span class="left">用户名：</span> 
	                        <span style="left"><input id="username" name="username" type="text" class="txt" value="<%=request.getAttribute("username") == null ? "" : request.getAttribute("username")%>"/></span>
	                    </li>
	                    
	                    <li>
	                        <span class="left">密 码：</span> 
	                        <span style="left"><input id="pass" name="pass" type="password" class="txt" value="<%=request.getAttribute("pass") == null ? "" : request.getAttribute("pass")%>"/>  </span>
	                    </li>
	                      
	                    <li class="middle_C">
	                         <span class="btn"><input id="login1" type="submit" value="登录"/></span>
	                         <span class="btn" style="color: red;"><%=request.getAttribute("errorTip") == null ? "" : request.getAttribute("errorTip") %></span>
	                    </li>
                    </form>
                    
                </ul>
            </li>
            <li class="topE"></li>
            <li class="middle_A"></li>
            <li class="middle_B"></li>
			  <li class="middle_C">
                         <span class="btn">      
                         </span>
                       </li>
            <li class="middle_D"></li>
            <li class="bottom_A"></li>
            <li class="bottom_B">
  
            </li>
        </ul>
    </div>
</body>
</html>



    