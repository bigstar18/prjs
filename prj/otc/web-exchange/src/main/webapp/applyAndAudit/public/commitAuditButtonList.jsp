<%@ page pageEncoding="GBK"%>
<input type="hidden" name="buttonClick" value="destroy">
<div class="tab_pad">
<table border="0" cellspacing="0" cellpadding="0" width="90%" align="center">
<tr>
	<td align="center">
			<button class="btn_secmid" id="update" onClick="destroy();">
					��&nbsp;&nbsp;&nbsp;&nbsp;��
				</button>
	</td>
	<td align="center">
			<button class="btn_secmid" onClick="window.close()">
					��&nbsp;&nbsp;&nbsp;&nbsp;��
				</button>
	</td>
</tr>
</table>
</div>
<script type="text/javascript">
	function destroy(){
		 var collFrm = document.all.tags("FORM");
		 
		collFrm[0].action="${basePath}/audit/commitAudit/update_${apply.applyType}.action";
		var vaild = window.confirm("��ȷ��Ҫ������");
		if(vaild==true){
			collFrm[0].submit();
	    }else{
           return false;
	    }
	}

</script>