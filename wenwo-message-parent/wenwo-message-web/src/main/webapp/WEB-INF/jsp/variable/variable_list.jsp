<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="row">
				<table id="tbl_wrap" class="table table-bordered table-condensed">
					<thead>
						<tr>
							<th class="span2">变量名</th>
							<th class="span2">变量分组</th>
							<th class="span6">实际velocity变量</th>
							<th class="span2">操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="variable" items="${variables}">
						<tr <c:if test="${variable.status eq 'NEED_LOAD' }">class="warning"</c:if> >
							<td>${variable.name}</td>
							<td>${variable.variableType }</td>
							<td>${variable.velocityExpress}</td>
							<td>
								<a href="javascript:void(0)" class="btn btn-small" name="editor" value="${variable.id}">修改</a>
								<a href="javascript:void(0)" class="btn btn-small" name="del" value="${variable.id}">删除</a>
							</td>
						</tr>
						</c:forEach>
					</tbody>
				</table>
				</div>
