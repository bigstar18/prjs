<%@ page pageEncoding="GBK"%>
<table border="0" cellspacing="0" cellpadding="4" width="100%"
	align="center">
	<tr>
		<td align="center">
			<button onclick="updateCustomerStatus()">
				����
			</button>
		</td>
		<td align="center">
			<button onclick="window.close()">
				�ر�
			</button>
		</td>
	</tr>
</table>
<script type="text/javascript">
function updateCustomerStatus() {
	frm.action = '${basePath}/account/customerStatus/updateClosingStatus.action';
	frm.submit();
}
</script>