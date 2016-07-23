<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div class="modal-body">
	<div class="row-fluid">
	<form class="form-horizontal">
	<fieldset>
		<div class="control-group">
			<label class="control-label">发送通道</label>
			<div id="channel_cfg" class="controls">
				<label class="radio inline"> 
					<input value="default" name="channel_type" type="radio"> 使用标准通道
				</label> 
				<label class="radio inline">
					<input value="specific" name="channel_type" type="radio"> 配置个性化通道
				</label>
			</div>
		</div>
		<div id="message_type_channel_config">
		<div  class="control-group">
			<!-- Select Basic -->
			<label class="control-label">标准通道－新浪</label>
			<div class="controls">
				<select class="input-xlarge sina_project" name="sina_project">
					<option value="WENWO">问我</option>
					<option value="WEIWEN">新浪微问</option>
					<option value="SINAASK">新浪互助</option>
					<option value="DOCTOR">微问医生</option>
				</select> <select class="input-xlarge sina_group" name="sina_group">
					<option value="C1">C1</option>
					<option value="C2">C2</option>
					<option value="C3">C3</option>
					<option value="C4">C4</option>
					<option value="C5">C5</option>
					<option value="C6">C6</option>
					<option value="推广组">推广组</option>
				</select>
			</div>
		</div>

		<div class="control-group">
			<!-- Select Basic -->
			<label class="control-label">标准通道－腾讯</label>
			<div class="controls">
				<select class="input-xlarge qq_project" name="qq_project">
					<option value="WEIWENQQ">腾讯微问</option>
					<option value="WENWO">问我</option>
				</select> <select class="input-xlarge qq_group" name="qq_group">
					<option value="C1">C1</option>
					<option value="C2">C2</option>
					<option value="C3">C3</option>
					<option value="C4">C4</option>
					<option value="C5">C5</option>
					<option value="C6">C6</option>
					<option value="推广组">推广组</option>
				</select>
			</div>

		</div>
		</div>
		</fieldset>
		</form>
	</div>
</div>
<div class="modal-footer">
	<button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
	<button id="submit_message_type_channel" class="btn btn-primary">保存设置</button>
</div>