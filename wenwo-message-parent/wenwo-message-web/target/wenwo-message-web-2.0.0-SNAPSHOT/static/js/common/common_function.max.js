$(function(){
	/*wencan修改----04.11.13*/
	//侧边栏主菜单高亮设置
	var $nav_list = $(".nav-list").find(".nav-config");
	if($nav_list.val() != undefined || $nav_list.val() != null){
		var menu_name = $("#menu_name");
		$nav_li = $(".nav-list").find("#"+menu_name.val());
		$nav_li.addClass("active");
	}
	
	/*var $replaceMsg = $("#replaceMsg");
	$replaceMsg.on("click",function(){
		$.ajax({
			url:"/message.do?act=replaceMsgToServer",
			type:"post",
			success:function(data){
				alert("更新成功");
			}
		});
	});*/
});