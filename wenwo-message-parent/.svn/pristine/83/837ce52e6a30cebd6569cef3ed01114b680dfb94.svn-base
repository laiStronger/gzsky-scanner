<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

  <div class="form-horizontal">
    <fieldset>
      <div id="legend" class="">
        <legend class="">覆盖默认变量</legend>
      </div>
    <div class="control-group">

          <!-- Select Basic -->
          <label class="control-label">组名：</label>
          <div class="controls">
            <select name="ride_group" id="ride_group">
                            <option value="default">请选择组名</option>
							<c:forEach var="variableType" items="${variableTypes }">
								<option value="${variableType}">${variableType.typeName}</option>
							</c:forEach>
					</select>
          </div>

        </div>
    <div class="control-group" >

          <!-- Text input-->
          <label class="control-label">变量名：</label>
          <div class="controls">
            <select name="ride_name" id="ride_name">
							
					</select>
          </div>
        </div>

    <div class="control-group">

          <!-- Textarea -->
          <label class="control-label">变量替换：</label>
          <div class="controls">
            <div class="textarea">
                  <textarea type="" class="" style="margin: 0px; height: 150px; width: 271px;" name="rideVariableExpess"> </textarea>
            </div>
          </div>
        </div>
    <input type="hidden" name="id"/>
    <div class="control-group">
          <label class="control-label"></label>

          <!-- Button -->
          <div class="controls">
            <button class="btn btn-default" id="ride_confirm_variable">确定</button>&nbsp;&nbsp;&nbsp;<button class="btn btn-default" id="cancel_ridevaribale">取消</button>
          </div>
        </div>
    </fieldset>
  </div>