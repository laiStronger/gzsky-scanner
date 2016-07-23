/**
 * 登录
 */
$(function(){
	var logout = $("#logout");
	
	//查询消息
	logout.on("click",function(){
		var url = "../login.do?act=loginout";
		 
		$.ajax({
			 type: "POST",
			 url: url,
			 dataType:"text",
			 success: function(data,textStatus){
			      window.location.href = data;
			 },
			 
			 statusCode: {404: function() {
				 alert('系统错误，请稍后再试');
			 }}
			 
		 });
		 
		 
	});
	
	
	
	
});

