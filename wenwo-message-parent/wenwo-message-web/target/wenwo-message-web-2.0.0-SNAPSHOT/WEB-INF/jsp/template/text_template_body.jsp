<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="row-fluid">
	<div class="variable_select span3">
		<jsp:include page="../variable/variable_select_page.jsp"></jsp:include>
	</div>
	<div class="span9">
		<form method="post">
			<fieldset>
				<input style="display: none" id="template_id" value="${empty template ? '' : template.id }"> 
				<input style="display: none" id="message_type_id" value="${messageTypeId}"> 
				<input style="display: none" id="template_info_type" value="${templateInfoType }">
				<div class="controls">
					<div class="textarea">
						<textarea class="variable-focus" id="template_content"
							style="width: 350px; height: 300px" required >${empty template? '' : template.text }</textarea>
					</div>
					<p id="error_info" class="text-error" style="display: none" >请编辑模版内容</p>
				</div>
			</fieldset>
		</form>
	</div>
</div>