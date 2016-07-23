<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div class="modal-body">
	<form class="form-horizontal well">
		<fieldset>
			<div id="legend" class="">
				<legend class="">新增消息类型</legend>
			</div>
			<div class="control-group">

				<!-- Text input-->
				<label class="control-label" for="input01">消息类型</label>
				<div class="controls">
					<input required placeholder="" class="input-xlarge" type="text">
					<p class="help-block"></p>
				</div>
			</div>

			<div class="control-group">

				<!-- Text input-->
				<label class="control-label" for="input01">描述</label>
				<div class="controls">
					<input placeholder="" class="input-xlarge" type="text">
					<p class="help-block"></p>
				</div>
			</div>

			<div class="control-group">

				<!-- Select Basic -->
				<label class="control-label">消息主分类</label>
				<div class="controls">
					<select class="input-xlarge">
						<option>请选择</option>
						<option>提问</option>
						<option>回答</option>
						<option>追问</option>
						<option>系统</option>
					</select>
				</div>

			</div>

			<div class="control-group">
				<label class="control-label">站内消息(Web)</label>
				<div class="controls">
					<!-- Multiple Checkboxes -->
					<label class="checkbox"> <input value="需要" type="checkbox">
						需要
					</label>
				</div>

			</div>

			<div class="control-group">

				<!-- Textarea -->
				<label class="control-label">站内模版(Web)</label>
				<div class="controls">
					<div class="textarea">
						<textarea type="" class=""> </textarea>
					</div>
				</div>
			</div>

			<div class="control-group">
				<label class="control-label">站内消息(App)</label>

				<!-- Multiple Checkboxes -->
				<div class="controls">
					<!-- Inline Checkboxes -->
					<label class="checkbox inline"> <input value="需要"
						type="checkbox"> 需要
					</label>
				</div>

			</div>



			<div class="control-group">

				<!-- Textarea -->
				<label class="control-label">站内模版(App)</label>
				<div class="controls">
					<div class="textarea">
						<textarea type="" class=""> </textarea>
					</div>
				</div>
			</div>

			<div class="control-group">
				<label class="control-label">Push消息</label>

				<!-- Multiple Checkboxes -->
				<div class="controls">
					<!-- Inline Checkboxes -->
					<label class="checkbox inline"> <input value="需要"
						type="checkbox"> 需要
					</label>
				</div>

			</div>

			<div class="control-group">

				<!-- Textarea -->
				<label class="control-label">Push模版</label>
				<div class="controls">
					<div class="textarea">
						<textarea type="" class=""> </textarea>
					</div>
				</div>
			</div>

			<div class="control-group">

				<!-- Select Basic -->
				<label class="control-label">微博消息</label>
				<div class="controls">
					<select class="input-xlarge">
						<option>正常@通知</option>
						<option>集合消息</option>
						<option>不发</option>
					</select>
				</div>

			</div>

			<div class="control-group">
				<label class="control-label">发送通道</label>
				<div class="controls">
					<!-- Inline Radios -->
					<label class="radio inline"> <input checked="checked"
						value="使用标准通道" name="通道配置" type="radio"> 使用标准通道
					</label> <label class="radio inline"> <input value="配置个性化通道"
						name="通道配置" type="radio"> 配置个性化通道
					</label>
				</div>
			</div>

			<div class="control-group">

				<!-- Select Basic -->
				<label class="control-label">发送通道(新浪)</label>
				<div class="controls">
					<select class="input-xlarge">
						<option>新浪微问</option>
						<option>问我</option>
						<option>微博互助</option>
					</select>
					<select class="input-xlarge">
						<option>C1</option>
						<option>C2</option>
						<option>C3</option>
						<option>C4</option>
						<option>C5</option>
						<option>C6</option>
						<option>推广组</option>
					</select>
				</div>

			</div>

			<div class="control-group">

				<!-- Select Basic -->
				<label class="control-label">发送通道(腾讯)</label>
				<div class="controls">
					<select class="input-xlarge">
						<option>腾讯微问</option>
						<option>问我</option>
					</select>
					<select class="input-xlarge">
						<option>C1</option>
						<option>C2</option>
						<option>C3</option>
						<option>C4</option>
						<option>C5</option>
						<option>C6</option>
						<option>推广组</option>
					</select>
				</div>

			</div>

			<div class="control-group">

				<!-- Textarea -->
				<label class="control-label">微博文本模版</label>
				<div class="controls">
					<div class="textarea">
						<textarea type="" class=""> </textarea>
					</div>
				</div>
			</div>

			<div class="control-group">
				<label class="control-label">微博图片模版</label>

				<!-- Button -->
				<div class="controls">
					<button class="btn btn-danger">编辑</button>
				</div>
			</div>
		</fieldset>
	</form>
</div>

<div class="modal-footer">
	<button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
	<button id="submit_text_template" class="btn btn-primary">保存设置</button>
</div>