<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="row-fluid" style="width: 1500px;">
	<div class="variable_select span2" style="width:150px;">
	<jsp:include page="../variable/variable_select_page.jsp"></jsp:include>
		<!--<div class="accordion" id="pic_variable_accordion">
			<c:forEach var="item" items="${variableMap}">
				<div class="accordion-group">
					<div class="accordion-heading">
						<a class="accordion-toggle" data-toggle="collapse"
							data-parent="#pic_variable_accordion"
							href="#pic_variable_${item.key}">${item.key.typeName}</a>
					</div>
					<div id="pic_variable_${item.key}" class="accordion-body collapse">
						<ul>
							<c:forEach var="variable" items="${item.value}">
								<li><a class="variable" href="javascript:void(0)" name="${variable.name}"
									express="${variable.velocityExpress}">${variable.name}(${variable.variableProjectType.projectName})</a></li>
							</c:forEach>
						</ul>
					</div>
				</div>
			</c:forEach>
		</div> -->
	</div>
	<div class="span10 well" style="width: 1300px;position:relative;top:5px; float: left;">
		<div class="row_fluid">
			<input style="display: none" id="template_id" value="${not empty pic_template ? pic_template.id : '' }">
			<p style="display:none">
				<span>背景图片:</span> <a id="back_pic_view" picId="${empty pic_template ? '839415807_540.jpg' : pic_template.backGroundPicId}"
					class="offset1" target="_blank"
					href="http://pic.wenwo.com/fimg/${empty pic_template ? '839415807_540.jpg' : pic_template.backGroundPicId}"><img
					style="max-width: 100px; max-height: 100px" alt=""
					src="http://pic.wenwo.com/fimg/${empty pic_template ? '839415807_540.jpg' : pic_template.backGroundPicId}"></a> <span
					class="btn fileinput-button offset1"> <span>修改</span> <input
					id="back-group-pic" type="file" name="back_pic">
				</span>
			<p>
		</div>
		<div class="row_fluid">
			<span>文本块：</span> <a id="newTextBlock" href="javascript:void(0)"
				class="btn  pull-right">添加新的文本块</a>
			<table id="text_block_table"
				class="table table-condensed table-bordered">
				<thead>
					<tr>
						<th>文本框模版</th>
						<th>横坐标</th>
						<th>纵坐标</th>
						<th>字体</th>
						<th>大小</th>
						<th>颜色</th>
						<th width="60px">是否换行</th>
						<th>显示行数</th>
						<th>行宽度</th>
						<th>行间隔</th>
						<th width="110px">操作</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${not empty pic_template && not empty pic_template.textInfos}">
						<c:forEach var="textInfo" items="${pic_template.textInfos}">
							<tr>
								<td><input disabled type="text" required value="${textInfo.text}"></td>
								<td><input disabled class="input-mini" required value="${textInfo.x}"></td>
								<td><input disabled class="input-mini" required value="${textInfo.y}"></td>
								<td><input disabled class="input-mini" required value="${textInfo.font}"></td>
								<td><input disabled class="input-mini" required value="${textInfo.size}"></td>
								<td><input disabled class="input-mini" required value="${textInfo.color}"></td>
								<td width="60px"><input disabled type="checkbox" <c:if test="${textInfo.needLineBreak}">checked</c:if>></td>
								<td><input disabled class="input-mini" required value="${textInfo.showLineNum}"></td>
								<td><input disabled class="input-mini" required value="${textInfo.lineWidth}"></td>
								<td><input disabled class="input-mini" required value="${textInfo.lineHeight}"></td>
								<td width="110px">
									<p>
										<button class="save" style="display: none">确定</button>
										<button class="change-btn">修改</button>
										<button class="delete-btn">删除</button>
									</p>
								</td>
							</tr>
						</c:forEach>
					</c:if>
					<tr id="text_block_line" style="display: none">
						<td><input type="text" required></td>
						<td><input class="input-mini" required></td>
						<td><input class="input-mini" required></td>
						<td><input class="input-mini" required></td>
						<td><input class="input-mini" required></td>
						<td><input class="input-mini" required></td>
						<td width="60px"><input type="checkbox" required></td>
						<td><input class="input-mini" required></td>
						<td><input class="input-mini" required></td>
						<td><input class="input-mini" required></td>
						<td width="110px">
							<p>
								<button class="save">确定</button>
								<button class="change-btn" style="display: none">修改</button>
								<button class="delete-btn">删除</button>
							</p>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="row_fluid">
			<span>图片块：</span> <a id="newPicBlock" href="javascript:void(0)"
				class="btn  pull-right">添加新图片块</a>
			<table id="pic_block_table"
				class="table table-condensed table-bordered">
				<thead>
					<tr>
						<th width="80">图片图片URL模板</th>
						<th width="80">横坐标</th width="80">
						<th width="80">纵坐标</th>
						<th width="80">缺省图片URL</th>
						<th width="80">width</th>
						<th width="80">height</th>
						<th width="80">操作</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${not empty pic_template && not empty pic_template.imageInfos}">
							<c:forEach var="imageInfo" items="${pic_template.imageInfos}">
								<tr>
									<td><input disabled type="text" required value="${imageInfo.url}"></td>
									<td><input disabled class="input-mini" required value="${imageInfo.x}"></td>
									<td><input disabled class="input-mini" required value="${imageInfo.y}"></td>
									<td><input disabled class="input-mini" required value="${imageInfo.defaultUrl}"></td>
									<td><input disabled class="input-mini" required value="${imageInfo.width}"></td>
									<td><input disabled class="input-mini" required value="${imageInfo.height}"></td>
									<td>
										<p>
											<button class="save" style="display: none">确定</button>
											<button class="change-btn">修改</button>
											<button class="delete-btn">删除</button>
										</p>
									</td>
								</tr>
							</c:forEach>
						</c:if>
					<tr id="pic_block_line" style="display: none">
						<td><input type="text"></td>
						<td><input class="input-mini"></td>
						<td><input class="input-mini"></td>
						<td><input class="input-mini"></td>
						<td><input class="input-mini"></td>
						<td><input class="input-mini"></td>
						<td>
							<p>
								<button class="save">确定</button>
								<button class="change-btn" style="display: none">修改</button>
								<button class="delete-btn" style="display: none">删除</button>
							</p>
						</td>
					</tr>
				</tbody>
			</table>
		</div>

		<div class="row_fluid">
			<span>分割线：</span>
			<table id="line_info_table"
				class="table table-condensed table-bordered">
				<thead>
					<tr>
						<th width="80">横坐标</th width="80">
						<th width="80">纵坐标</th>
						<th width="80">线高</th>
						<th width="80">线宽</th>
						<th width="80">颜色</th>
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${empty pic_template }">
						<tr>
							<td><input class="input-mini"></td>
							<td><input class="input-mini"></td>
							<td><input class="input-mini"></td>
							<td><input class="input-mini"></td>
							<td><input class="input-small"></td>
						</tr>
						</c:when>
						<c:otherwise>
						<tr>
							<td><input class="input-mini" value="${pic_template.lineInfo.x }"></td>
							<td><input class="input-mini" value="${pic_template.lineInfo.y }"></td>
							<td><input class="input-mini" value="${pic_template.lineInfo.height }"></td>
							<td><input class="input-mini" value="${pic_template.lineInfo.width }"></td>
							<td><input class="input-small" value="${pic_template.lineInfo.color }"></td>
						</tr>
						</c:otherwise>
					</c:choose>
				</tbody>
			</table>
		</div>
		
		<div class="row_fluid">
			<span>图片剪切配置：</span>
			<div class="control-group">
				<div class="input-append">
					<span class="add-on"><strong>高度</strong></span>
					<input id="cut_info_height" class="" value="${empty pic_template || empty pic_template.cutInfo ? '' : pic_template.cutInfo.height }" type="text"> 
				</div>
				<div class="input-append offset1">
					<span class="add-on"><strong>宽度</strong></span>
					<input id="cut_info_width" class="" value="${empty pic_template || empty pic_template.cutInfo ? '' : pic_template.cutInfo.width }" type="text"> 
				</div>
			</div>
			</div>
		</div>
</div>