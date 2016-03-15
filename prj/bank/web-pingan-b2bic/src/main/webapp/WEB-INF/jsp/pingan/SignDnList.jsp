<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include.inc.jsp"%>

<div class="pageContent">

	<div class="pageFormContent" layoutH="55">
		<table id="dnList_table" class="list" width="100%">
			<s:iterator value="#request.dnList">
				<tr>
					<td>
						<input type="checkbox" value="<s:property />">
					</td>
					<td>
						<s:property />
					</td>
				</tr>
			</s:iterator>
		</table>
		
	</div>
	
	<div class="formBar">
		<ul>
			<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="dnList_select_btn">选择</button></div></div></li>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close" id="dnList_close_btn">取消</button></div></div></li>
		</ul>
	</div>
	
</div>

<script type="text/javascript">
$(function() {
	$("#dnList_table :checkbox").click(function() {
		$("#dnList_table :checkbox").not(this).attr("checked", false);
	});
	
	$("#dnList_select_btn").click(function() {
		$selectedDn = $("#dnList_table :checkbox:checked");
		if($selectedDn.length < 1) {
			alertMsg.error("请选择信息");
			return;
		}
		
		$("#signConfigSaveBtn").attr("disabled", false);		//激活保存按钮
		$("#signConf_certDN").val($.trim($selectedDn.val()));
		$("#dnList_close_btn").click();
	});
});
</script>