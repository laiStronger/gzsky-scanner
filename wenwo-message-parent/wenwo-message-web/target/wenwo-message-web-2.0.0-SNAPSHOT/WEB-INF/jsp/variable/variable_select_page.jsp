<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="now" class="java.util.Date"></jsp:useBean>
<c:set var="currentTime" value="${now.time }"></c:set>
<div class="accordion" id="variable_accordion_${currentTime}">
	<c:forEach var="item" items="${variableMap}">
		<div class="accordion-group">
			<div class="accordion-heading">
				<a class="accordion-toggle" data-toggle="collapse"
					data-parent="#variable_accordion_${currentTime}"
					href="#variable_${item.key}_${currentTime}">${item.key.typeName}</a>
			</div>
			<div id="variable_${item.key}_${currentTime}" class="accordion-body collapse">
				<ul>
					<c:forEach var="variable" items="${item.value}">
						<li><a class="variable" href="javascript:void(0)"
							name="${variable.name}" express="${variable.velocityExpress}">${variable.name}(${variable.variableProjectType.projectName})</a></li>
					</c:forEach>
				</ul>
			</div>
		</div>
	</c:forEach>
</div>