<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<html>
	<head>
		<title>��Ա�ֲ�����</title>
	</head>
	<body leftmargin="0" topmargin="0" class="st_body">
		<form name="frm" action="${basePath}/commodity/holdQuantily/update.action" method="post" targetType="hidden">
			<div>
			<%@ include file="/commodity/holdQuantily/holdQuantilyUpdateTable.jsp"%>
			</div>
			<div class="tab_pad">
				<table cellspacing="0" cellpadding="0" border="0" width="100%" align="center">
					<tr>
						<td  align="center">
								<button  class="btn_sec" onClick="updateHoldQuantily()" id="update">�޸�</button>
							</td>
							<td align="center">
								<button  class="btn_sec" onClick="window.close()">�ر�</button>
							</td>
						</tr>
				</table>
				</div>
		</form>
	</body>
</html>
<script type="text/javascript">
function updateHoldQuantily(){
		if(frm.oneMaxOrderQty.value==""){
			alert('��������µ���������Ϊ��');
			frm.oneMaxOrderQty.focus();
			return false;
		}else if((isNaN(frm.oneMaxOrderQty.value))){
			alert("��������µ�������Ϊ����");
			frm.oneMaxOrderQty.value="";
			frm.oneMaxOrderQty.focus();
			return false;
		}
		if(frm.oneMinOrderQty.value==""){
			alert('������С�µ���������Ϊ��');
			frm.oneMinOrderQty.focus();
			return false;
		}else if((isNaN(frm.oneMinOrderQty.value))){
			alert("������С�µ�������λ����");
			frm.oneMinOrderQty.value="";
			frm.oneMinOrderQty.focus();
			return false;
		}
		if(frm.maxCleanQty.value==""){
			alert('��󾻳ֲ���������Ϊ��');
			frm.maxCleanQty.focus();
			return false;
		}else if((isNaN(frm.maxCleanQty.value))){
			alert("��󾻳ֲ�������λ����");
			frm.maxCleanQty.value="";
			frm.maxCleanQty.focus();
			return false;
		}
		if(frm.maxHoldQty.value==""){
			alert('���ֲ���������Ϊ��');
			frm.maxHoldQty.focus();
			return false;
		}else if((isNaN(frm.maxHoldQty.value))){
			alert("���ֲ�������λ����");
			frm.maxHoldQty.value="";
			frm.maxHoldQty.focus();
			return false;
		}
		var vaild = window.confirm("��ȷ��Ҫ������");
		if(vaild==true){
		    frm.submit();
	    }else{
           return false;
	    }
	}
</script>
<%@ include file="/public/footInc.jsp"%>