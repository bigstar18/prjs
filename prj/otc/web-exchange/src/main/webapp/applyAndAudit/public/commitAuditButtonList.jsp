<%@ page pageEncoding="GBK"%>
<input type="hidden" name="buttonClick" value="destroy">
<div class="tab_pad">
<table border="0" cellspacing="0" cellpadding="0" width="90%" align="center">
<tr>
	<td align="center">
			<button class="btn_secmid" id="update" onClick="destroy();">
					撤&nbsp;&nbsp;&nbsp;&nbsp;销
				</button>
	</td>
	<td align="center">
			<button class="btn_secmid" onClick="window.close()">
					关&nbsp;&nbsp;&nbsp;&nbsp;闭
				</button>
	</td>
</tr>
</table>
</div>
<script type="text/javascript">
	function destroy(){
		 var collFrm = document.all.tags("FORM");
		 
		collFrm[0].action="${basePath}/audit/commitAudit/update_${apply.applyType}.action";
		var vaild = window.confirm("您确定要操作吗？");
		if(vaild==true){
			collFrm[0].submit();
	    }else{
           return false;
	    }
	}

</script>