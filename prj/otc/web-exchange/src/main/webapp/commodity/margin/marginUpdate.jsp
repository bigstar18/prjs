<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<html>
	<head>
		<title>Ĭ�ϱ�֤��</title>
	</head>
	<body leftmargin="0" topmargin="0" class="st_body">
		<form name="frm" action="${basePath}/commodity/margin/update.action" method="post" targetType="hidden">
		<div>
			<%@ include file="/commodity/margin/marginInfoTable.jsp"%>
			</div>
			<div class="tab_pad">
			<table cellspacing="0" cellpadding="0" border="0" width="100%" align="center" >
				<tr>
				<td  align="center">
					<button  class="btn_sec" onClick="updateMargin()" id="update">�޸�</button>
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
function updateMargin(){
		if(frm.tradeMargin.value==""){
			alert('����ռ�ò�����Ϊ��');
			frm.tradeMargin.focus();
			return false;
		}else if((isNaN(frm.tradeMargin.value))){
			alert("����ռ�ñ���λ����");
			frm.tradeMargin.value="";
			frm.tradeMargin.focus();
			return false;
		}
		if(frm.settleMargin.value==""){
			alert('����ά�ֲ�����Ϊ��');
			frm.settleMargin.focus();
			return false;
		}else if((isNaN(frm.settleMargin.value))){
			alert("����ά�ֱ���λ����");
			frm.settleMargin.value="";
			frm.settleMargin.focus();
			return false;
		}
		if(frm.holidayMargin.value==""){
			alert('����ά�ֲ�����Ϊ��');
			frm.holidayMargin.focus();
			return false;
		}else if((isNaN(frm.holidayMargin.value))){
			alert("����ά�ֱ���λ����");
			frm.holidayMargin.value="";
			frm.holidayMargin.focus();
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