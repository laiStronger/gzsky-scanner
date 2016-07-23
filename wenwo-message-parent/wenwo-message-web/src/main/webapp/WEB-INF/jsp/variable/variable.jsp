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
<script type="text/javascript" src="../static/js/jy.base.max.js"></script>
<script type="text/javascript" src="../static/js/jy.widget.max.js"></script>
<link rel="stylesheet" type="text/css" href="../static/css/bootstrap-responsive.max.css" />
    <script type="text/javascript">
    
        $(function() {
        	var $addBox = $("#add_page");
        	$("#ride_variable").hide();
        	//切换项目类型
        	$("#project > ul > a").click(function(){
        		$(this).addClass("btn btn-info").siblings().removeClass("btn-info");
        		var projectType = $(this).attr("value");
        		$("#list > div:eq(2)").load("variable.do?act=list&messageProjectType="+projectType,function(){
        		    if(projectType=="DEFAULT"){
        		       $("#ride_variable").hide();
        		    }else{
        		       $("#ride_variable").show();
        		    }
        			$("#sel select").val("all");
        		}) 
        	})
        	
        	//查询
        	$("#but").change(function(){
        		var projectType = $("a[class*='active']","#project").attr("value");
        		var variableType = $("#sel select option:selected").val(); 
        		$("#list > div:eq(2)").load("variable.do?act=list&messageProjectType="+projectType+"&variableType="+variableType,function(){
        			
        		})
        	})
        	
        	
        	//删除
        	$("#main_container").on("click","a[name='del']",function(){
        	    if(confirm("确定删除该变量吗")){
        	      var variableId = $(this).attr("value");
        		  var projectType = $("a[class*='active']","#project").attr("value");
        		  var variableType = $("#sel select option:selected").val(); 
                  $("#list > div:eq(2)").load("variable.do?act=delete&id="+variableId+"&messageProjectType="+projectType+"&variableType="+variableType,function(){
        		  });
                }
        	});
        	
        	//编辑
        	$("#main_container").on("click","a[name='editor']",function(e){
        		 var me = $(this);
        		 var tr = me.parent().parent();
        		 var tds = tr.find("td");
        		 var variableName = tds.first().text();
        		 var groupName = tds.eq(1).text();
        		 var velocityName = tds.eq(2).text();
        		 var variableId = $(this).attr("value");
        		 $("input:hidden").val(variableId);
        		 $("input[name='variableName']",$addBox).val(variableName);
        		 if(groupName){
        			 var sels = $("select[name='group']",$addBox);
        			 var opts = sels.get(0).options;
        			 for(var i=0;i<opts.length;i++){
        			 	if(opts[i].value == groupName){
        			 		sels.get(0).options[i].selected = true;
        			 		break;
        			 	};
        			 }
        		}
        		 $("textarea",$addBox).val(velocityName); 
        		 jy.widget.modal.show();
        		 $addBox.show();
        	})
        	
        	//添加
        	$("#main_container").on("click","#confim_variable",function(){
        	     var id = $("input:hidden").val();
        	     var group = $("select[name='group']").val();
        	     var variableName = $("input[name='variableName']").val();
        	     var variableExpess = $("textarea[name='variableExpess']").val();
        	     var projectType = $("a[class*='active']","#project").attr("value");
        		 var variableType = $("#sel select option:selected").val(); 
        	     if(variableName==null || variableName.length==0){
        	        alert("请输入变量名");
        	        return false;
        	     }
        	     if(variableExpess==null || variableExpess.length==0){
        	        alert("请输入变量替换名");
        	        return false;
        	     }
        	     var params = {};
        	     params.id = id;
        	     params.messageProjectType = projectType;
        	     params.variableType = variableType;
        	     params.group = group;
        	     params.variableName = variableName;
        	     params.variableExpess = variableExpess;
        	     $.post("variable.do?act=add",params,function(data){
        	     	$("#list > div:eq(2)").html(data);
        	     	jy.widget.modal.close();
        	     	$("#add_page").hide();
        	     });

        	});
        	//取消
        	$("#main_container").on("click","#cancel_varibale",function(){
        		    jy.widget.modal.close();
        	        $("#add_page").hide(); 
        	});
        	
        	//添加新变量
        	$("#add_variable").click(function(){
        		   //变量初始化
        		   $("input:hidden").attr("value","");
          	       $("select[name='group']  option:first").prop("selected", 'selected');
          	       $("input[name='variableName']").attr("value","");
          	       $("textarea[name='variableExpess']").attr("value","");
        		   
        	       jy.widget.modal.show();
        	       $("#add_page").show(); 
        	});
        	
        	//取消
        	$("#main_container").on("click","#cancel_ridevaribale",function(){
        		 	jy.widget.modal.close();
        	        $("#ride_page").hide(); 
        	});
        	
        	$("#ride_variable").click(function(){
        	       var projectType = $("a[class*='active']","#project").attr("value");
        	       if(projectType=="DEFAULT"){
        	          alert("请选择项目类型");
        	          return false;
        	       }
        	       jy.widget.modal.show();
        	       $("#ride_page").show(); 
        	});
        	
        	
        	$("#main_container").on("change","#ride_group",function(){
        	        var group = $("select[name='ride_group']").val();
        	       var content="<option>选择变量名</option>";
        	        $.getJSON("variable.do?act=getVariableByGroup&variableType="+group,function(data){
        	            $.each(data,function(i,item){
        	              content = content+"<option>"+data[i].name+"</option>";
        	            })
        	            $("#ride_name").html(content);
        	            $("textarea[name='rideVariableExpess']").html("");
        	        })
        	});
        	
        	$("#main_container").on("change","#ride_name",function(){
    	        var name = $("select[name='ride_name']").val();
    	        alert(name);
    	        var params = {};
        	    params.name = name;
        	    $("textarea[name='rideVariableExpess']").val("");
    	        $.post("variable.do?act=getexpress",params,function(data){
    	            alert(data);
    	        	$("textarea[name='rideVariableExpess']").val(data);
    	        })
    	    });
        	
        	$("#main_container").on("click","#ride_confirm_variable",function(){
       	     var id = $("input:hidden").val();
       	     var group = $("select[name='ride_group']").val();
       	     var variableName = $("select[name='ride_name']").val();
       	     var variableExpess = $("textarea[name='rideVariableExpess']").val();
       	     var projectType = $("a[class*='active']","#project").attr("value");
       		 var variableType = $("#sel select option:selected").val(); 
       	     if(variableName==null || variableName.length==0){
       	        alert("请输入变量名");
       	        return false;
       	     }
       	     if(variableExpess==null || variableExpess.length==0){
       	        alert("请输入变量替换名");
       	        return false;
       	     }
       	         var params = {};
        	     params.id = id;
        	     params.messageProjectType = projectType;
        	     params.variableType = variableType;
        	     params.group = group;
        	     params.variableName = variableName;
        	     params.variableExpess = variableExpess;
       	     $.post("variable.do?act=add",params,function(data){
       	     	$("#list > div:eq(2)").html(data);
       	        jy.widget.modal.close();
       	     	$("#ride_page").hide();
       	     });

       	});
        	
        });
        
    </script>
</head>
<body>
<div class="navbar navbar-inverse navbar-fixed-top">
		<div class="navbar-inner">
				<button type="button" class="btn btn-navbar" data-toggle="collapse"
					data-target=".nav-collapse">
					<span class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="brand" href="#">变量配置</a>
				<div class="nav-collapse collapse">
					<jsp:include page="../common/common_header.jsp"></jsp:include>
				</div>
				<!--/.nav-collapse -->
		</div>
	</div>
	
	<div id="main_container" class="container-fluid" style="top:50px">
		<div class="row-fluid">
			<div class="span2">
				<jsp:include page="../common/slide_menu.jsp">
					<jsp:param value="variable" name="menu"/>
				</jsp:include>
			</div>
			<div class="span10" id="list">
				<div class="row" id="project">
					<ul class="btn-group" data-toggle="buttons-radio">
						<a class="btn active btn-info"  href="javascript:void(0)" value="DEFAULT">标准变量</a>
						<a class="btn"  href="javascript:void(0)" value="WENWO">问我个性化变量</a>
						<a class="btn"  href="javascript:void(0)" value="WEIWEN">新浪微问个性化变量</a>
						<a class="btn"  href="javascript:void(0)" value="WEIWENQQ">腾讯微问个性化变量</a>
						<a class="btn"  href="javascript:void(0)" value="SINAASK">微博互助个性化变量</a>
						<!-- <a class="btn"  href="javascript:void(0)" value="DOCTOR">微问医生个性化变量</a> -->
					</ul>
				</div>
				<hr style="left:0">
				<div class="row">
				    <div class="input-append" id="sel">
		    			<select id="but">
							<option value="all">所有</option>
								<c:forEach var="variableType" items="${variableTypes }">
									<option value="${variableType}">${variableType.typeName}</option>
								</c:forEach>
						</select>
	   				 </div>
					<div class="pull-right">
						<a class="btn btn-large" id="ride_variable">覆盖标准变量</a>
						<a class="btn btn-large" id="add_variable" >添加新变量</a>
						<a class="btn btn-large">更新至消息服务器</a>
					</div>
				</div>
				<div>
				     <jsp:include page="variable_list.jsp"></jsp:include>
				</div>
			</div>
			
		<!-- 遮罩层 -->
		<div id="add_page" style="position:absolute;z-index:1000;display:none;left:300px;top:200px;border-color:#443366;background:#2f96b4;width:550px;">
			 <jsp:include page="addVariable.jsp"></jsp:include>
		</div>
		<div id="ride_page" style="position:absolute;z-index:1000;display:none;left:300px;top:250px;border-color:#443366;background:#2f96b4;width:550px;">
			 <jsp:include page="rideVariable.jsp"></jsp:include>
		</div>
		
	</div>
</body>
</html>