<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include.inc.jsp"%>

<div class="pageContent">
	
	<form method="post" action="ModifyApplication?navTabId=${ navTabId }" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone)">
		<div class="pageFormContent" layoutH="58">
			<p>
				<label>应用ID</label>
				<input type="text" name="app.appid" readonly="readonly" value="${ app.appid }"/>
			</p>
			<p>
				<label>应用名称</label>
				<input type="text" name="app.appname" size="30" minlength="1" maxlength="6" class="required" value="${ app.appname }"/>
			</p>
			<p>
				<label>投产日期</label>
				<input type="text" name="app.depdate" size="30" class="date" dateFmt="yyyyMMdd" value="${ app.depdate }"/>
				<a class="inputDateButton" href="#">选择</a>
			</p>
			<p>
				<label>应用地址</label>
				<input type="text" name="app.appadress" size="30" value="${ app.appadress }"/>
			</p>
			<p>
				<label>版本号</label>
				<input type="text" name="app.version" size="30" value="${ app.version }"/>
			</p>
			<p>
				<label>启动时间</label>
				<input type="text" name="app.starttime" size="30" class="date" dateFmt="yyyyMMdd" value="${ app.starttime }"/>
				<a class="inputDateButton" href="#">选择</a>
			</p>
			<p>
				<label>停止时间</label>
				<input type="text" name="app.stoptime" size="30" class="date" dateFmt="yyyyMMdd" value="${ app.stoptime }"/>
				<a class="inputDateButton" href="#">选择</a>
			</p>
			<p>
				<label>最后升级时间</label>
				<input type="text" name="app.finalupdate" size="30" class="date" dateFmt="yyyyMMdd" value="${ app.finalupdate }"/>
				<a class="inputDateButton" href="#">选择</a>
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

