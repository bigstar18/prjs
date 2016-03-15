<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include.inc.jsp"%>

<div class="pageContent">
	
	<form id="" method="post" action="CreateMaindict?navTabId=${ navTabId }" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone)">
		<div class="pageFormContent nowrap" layoutH="56">
			<p>
				<label>英文名称</label>
				<input type="text" name="item.itemename" value="${ item.itemename }" class="required" />
			</p>
			<p>
				<label>中文名称</label>
				<input type="text" name="item.itemcname" value="${ item.itemcname }" class="required" />
			</p>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">确定</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
	</form>
	
</div>
