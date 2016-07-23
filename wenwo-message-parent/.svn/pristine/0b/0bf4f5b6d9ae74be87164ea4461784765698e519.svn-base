<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

  <div class="form-horizontal">
    <fieldset>
      <div id="legend" class="">
        <legend class="">添加新变量</legend>
      </div>
    <div class="control-group">

          <!-- Select Basic -->
          <label class="control-label">组名：</label>
          <div class="controls">
            <select name="group">
							<c:forEach var="variableType" items="${variableTypes }">
								<option value="${variableType}">${variableType.typeName}</option>
							</c:forEach>
					</select>
          </div>

        </div>
    <div class="control-group">

          <!-- Text input-->
          <label class="control-label" for="input01">变量名：</label>
          <div class="controls">
            <input type="text" name="variableName"  class="input-xlarge">
            <p class="help-block"></p>
          </div>
        </div>

    <div class="control-group">

          <!-- Textarea -->
          <label class="control-label">变量替换：</label>
          <div class="controls">
            <div class="textarea">
                  <textarea type="" class="" style="margin: 0px; height: 150px; width: 271px;" name="variableExpess"></textarea>
            </div>
          </div>
        </div>
    <input type="hidden" name="id"/>
    <div class="control-group">
          <label class="control-label"></label>

          <!-- Button -->
          <div class="controls">
            <button class="btn btn-default" id="confim_variable">确定</button>&nbsp;&nbsp;&nbsp;<button class="btn btn-default" id="cancel_varibale">取消</button>
          </div>
        </div>
    </fieldset>
  </div>