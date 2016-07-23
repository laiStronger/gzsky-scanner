$(document).ready(function(){
	//找到下拉框
	var projectTypeSelect = $("#projectType");
	var typeNameSelect = $("#typename");
	
	//给三个下拉框注册事件
	projectTypeSelect.change(function(){
		
		var projectType = projectTypeSelect.val();
		//alert(projectType);
		
		 $.ajax({
			   type: "POST",
			   url: "message.do?act=chainselect",
			   data: "projectType="+projectType,
			   async:true,
			   cache:false,
			   dataType:"text",
			   success: function(data,textStatus){
				     //先清除原来的下拉框
				     var selOpt = $("#typename option");
				     selOpt.remove();

				     //添加下拉框
				     var str='';
				     var tmp = data.split(",");
				     for(var i=0;i<tmp.length;i++){
			             var c=tmp[i];
			             str+='<option value='+c+'>'+c+'</option>';
			             
			         }
				     
				    $("#typename").html(str);
				     
			   },
			   
			   statusCode: {404: function() {
				    alert('page not found');
			   }}

		 });

		 

		
		
	});



	
})
