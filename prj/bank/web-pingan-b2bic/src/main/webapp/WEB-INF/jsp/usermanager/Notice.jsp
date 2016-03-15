<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include.inc.jsp"%>

<div class="pageContent" style="overflow:hidden;">
	<center>
		<form method="post" action="ModifyNotice?navTabId=main" class="pageForm required-validate" onsubmit="return iframeCallback(this, refreshMainTab);">
			<div class="pageFormContent" layoutH="53">
				<div class="unit">
					<textarea class="editor" name="notice.content" rows="20" cols="120" tools="simple">${ notice.content }</textarea>
				</div>
			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive"><div class="buttonContent"><button type="submit">提交</button></div></div></li>
					<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
				</ul>
			</div>
			
			<input type="hidden" name="notice.id" value="${ notice.id }">
		</form>
		
	</center>
</div>

<script type="text/javascript">
function refreshMainTab(json) {
	$("#notice_frame").attr("src", "NoticeDisplay?notice.id=1&rand=" + Math.random());
	navTabAjaxDone(json);
}
</script>