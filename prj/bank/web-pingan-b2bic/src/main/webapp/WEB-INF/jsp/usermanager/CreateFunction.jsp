<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include.inc.jsp"%>

<div class="pageContent">
	
	<form id="query_addfunc" method="post" action="CreateFunction?navTabId=${ navTabId }" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone)">
		<div class="pageFormContent nowrap" layoutH="56">
			<dl>
				<dt>功能名称</dt>
				<dd>
					<input name="func.funcName" value="${func.funcName}" class="required"/>
				</dd>
			</dl>
			<dl>
				<dt>所属应用</dt>
				<dd>
					<s:select id="select_app" name="func.appid" value="func.appid" list="appidList" listKey="id" listValue="value" 
						cssClass="required textInput commontypeSelect"
						refSelect="select_subapp"
						refCt="'subappidList'"
						refForm="query_addfunc"
						refUrl="QuerySelectList_ListFunction"></s:select>
				</dd>
			</dl>
			<dl>
				<dt>所属子应用</dt>
				<dd><s:select id="select_subapp" name="func.subappid" value="func.subappid" list="subappidList" listKey="id" listValue="value" headerKey="" headerValue="请选择应用" cssClass="textInput"></s:select></dd>
			</dl>
			<dl>
				<dt>功能地址</dt>
				<dd><input name="func.funcAddress" value="${func.funcAddress}" class="required"/></dd>
			</dl>
			<dl>
				<dt>是否显示</dt>
				<dd>
					是	<input type="radio" name="func.runflg" value="1" ${func.runflg == '1' ? 'checked' : empty func.runflg ? 'checked' : ''} style="width:30px;" />
					否	<input type="radio" name="func.runflg" value="0" ${func.runflg == '0' ? 'checked' : ''} style="width:30px;" />
				</dd>
			</dl>
			<dl>
				<dt>功能说明</dt>
				<dd><textarea name="func.funcDesc" rows="10" cols="125">${func.funcDesc}</textarea></dd>
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
