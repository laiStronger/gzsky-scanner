$(function(){
	var replaceMsg = $("#replaceMsgtoServer");
	
	replaceMsg.on("click",function(){
		var returnVal = window.confirm("是否同步更新到服务器？", "标题");
		if(!returnVal) {
			return;
		}
		
		 $.ajax({
			 type: "POST",
			 url: "message.do?act=updateMsgToServer",
			 //data: "projectType="+projectType,
			 async:true,
			 cache:false,
			 dataType:"text",
			 success: function(data,textStatus){
				 
				 //alert(data);
				 if(data == 'true') {
					 alert("更新成功");
				 } else {
					 alert("更新失败，请稍后再试");
				 }
			 },
			 
			 statusCode: {404: function() {
				 alert('系统错误，请稍后再试');
			 }}
			 
		 });
		 
		 
	});
	
	
});

