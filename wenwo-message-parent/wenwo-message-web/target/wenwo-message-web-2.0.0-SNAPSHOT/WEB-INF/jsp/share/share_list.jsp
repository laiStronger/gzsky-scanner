<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="span12">
	<table id="message_type_table" class="table table-bordered table-hover table-condensed">
		<thead>
			<tr>
				<th width="80">消息类型</th>
				<th width="150">描述</th>
				<th value="WEIBO">微博通知模板</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="share" items="${shareList}">
			<tr id="${share.id}" <c:if test="${share.status eq 'NEED_LOAD' }">class="warning"</c:if> >
				<td message_type_id="${share.id}">${share.typeName }</td>
				<td width="150">${share.description }</td>
				<td info_type="WEIBO" message_type_id="${share.id}">
						<button templateId="${share.textTemplateId }" class="btn btn-success btn-mini text_template_change">文字</button>
					<button templateId="${share.picTemplateId }" class="btn btn-mini weibo_pic_template">图片</button>
				</td>
				<td>
					<p>
						<button type_id="${share.id}" class="btn btn-mini change_message_type">修改</button>
						<c:choose>
							<c:when test="${share.status == 'IN_USE' }">
								<button type_id="${share.id}" class="btn btn-mini stop_start_message_type" status="${share.status}">停用</button>
							</c:when>
							<c:otherwise>
								<button type_id="${share.id}" class="btn btn-mini stop_start_message_type" status="${share.status}">起用</button>
							</c:otherwise>
						</c:choose>
						
						<button type_id="${share.id}" class="btn btn-mini delete_message_type">删除</button>
					</p>
				</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	
</div>
