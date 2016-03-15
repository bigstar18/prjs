<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include.inc.jsp"%>

<div class="pageContent">
	
	<form id="" method="post" action="CreateSubdict_real?navTabId=${ navTabId }" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone)">
		<div class="pageFormContent nowrap" layoutH="56">
			<p>
				<label>所属类别</label>
				<s:select list="mainItems" listKey="id" listValue="value"
					name="item.comp_id.item"
					cssClass="required"
					disabled="true"></s:select>
			</p>
			<p>
				<label>代码</label>
				<input type="text" name="item.comp_id.code" value="${ item.comp_id.code }" class="required" />
			</p>
			<p>
				<label>代码名称</label>
				<input type="text" name="item.codename" value="${ item.codename }" class="required" size="40"/>
			</p>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">确定</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
		
		<input type="hidden" name="item.comp_id.item" value="${ item.comp_id.item }">
	</form>
	
</div>
