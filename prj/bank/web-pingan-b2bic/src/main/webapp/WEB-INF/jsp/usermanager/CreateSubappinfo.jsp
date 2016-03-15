<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include.inc.jsp"%>

<div class="pageContent">
	
	<form method="post" action="CreateSubappinfo?navTabId=${ navTabId }" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone)">
		<div class="pageFormContent nowrap" layoutH="56">
			<dl>
				<dt>所属应用</dt>
				<dd>
					<s:select name="subApp.comp_id.appid" list="apps" listKey="appid" listValue="appname" theme="simple" cssClass="textInput"></s:select>
				</dd>
			</dl>
			<dl>
				<dt>子应用ID</dt>
				<dd><input type="text" name="subApp.comp_id.subappid" value="${ subApp.comp_id.subappid }" class="required"/></dd>
			</dl>
			<dl>
				<dt>子应用名称</dt>
				<dd><input type="text" name="subApp.appname" value="${ subApp.appname }" class="required"/></dd>
			</dl>
			<dl>
				<dt>子应用说明</dt>
				<dd><textarea name="subApp.subappdesc" rows="10" cols="125"></textarea></dd>
			</dl>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">确定</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
	</form>
	
</div>
