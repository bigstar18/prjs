<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../../public/session.jsp"%>
<html>
  <head>
	<title><%=TITLE%></title>
</head>
<body>
        <form id="formNew" name="frm" method="POST" targetType="hidden" action="<%=basePath%>servlet/paymentPropsController.${POSTFIX}?funcflg=paymentPropsAdd">
		<fieldset width="50%" >
		<legend>�����ո�������Ϣ</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="50%" align="center">
			  <tr height="35">
                <td align="right" width="40%">����ģ�飺</td>
                <td align="left" width="60%">
                	<select id="moduleID" name="moduleID" class="normal" style="width: 120px">
						<OPTION value="">��ѡ��</OPTION>
						<c:forEach items="${moduleNameMap}" var="moduleNameMap">
                			<OPTION value="${moduleNameMap.key}">${moduleNameMap.value}</OPTION>
                		</c:forEach>
					</select>
                </td>
              </tr>
              <tr height="35">
            	<td align="right">Ʒ�֣�</td>
                <td align="left">
                	<select id="breedID" name="breedID" class="normal" style="width: 120px">
						<OPTION value="">��ѡ��</OPTION>
						<c:forEach items="${payCommoditysList}" var="payCommoditysList">
                			<OPTION value="${payCommoditysList.id}">${payCommoditysList.name}</OPTION>
                		</c:forEach>
					</select>
                </td>
              </tr>
			   <tr height="35">
            	<td align="right">�ڼ������գ�</td>
                <td align="left">
                	<input id="settleDayNo" name="settleDayNo" type="text" value="" class="text" style="width: 120px;" maxlength="5" onkeyup="this.value=this.value.replace(/\D/g,'')">&nbsp;<font color="red">*</font>
                </td>
              </tr>
			  <tr height="35">
            	<td align="right">����һ��������</td>
                <td align="left">
                	<input id="buyPayoutPct" name="buyPayoutPct" type="text" value="" class="text" style="width: 120px;" maxlength="15" >&nbsp;<font color="red">%&nbsp;*</font>
                </td>
              </tr>
			  <tr height="35">
            	<td align="right">�����һ��������</td> 
                <td align="left">
                	<input id="sellIncomePct" name="sellIncomePct" type="text" value="" class="text" style="width: 120px;" maxlength="15">&nbsp;<font color="red">%&nbsp;*</font>
                </td>
              </tr>
              <tr height="35">
                <td colspan="2"><div align="center">
                  <button class="smlbtn" type="button" onClick="doSubmit();">ȷ��</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      			  <button class="smlbtn" type="button" onClick="freturn()">����</button>
                </div></td>
              </tr>
          </table>
          
		</fieldset>
		<table border="0" cellspacing="0" cellpadding="0" width="50%" align="center">
			    <tr height="35">
		            <td ><div align="right">
		            	<font color="red">˵�������������СΪ0.01%</font>
					 </div></td>
		        </tr>
		   </table>
        </form>
</body>
</html>
<script>
	function doSubmit()
	{
		if (frm.moduleID.value == "") {
			alert("����ģ�鲻��Ϊ�գ�");
			return false;
		}
		if (frm.breedID.value == "") {
			alert("Ʒ�ֲ���Ϊ�գ�");
			return false;
		}
		if (frm.settleDayNo.value == "") {
			alert("�ڼ������ղ���Ϊ�գ�");
			return false;
		}
		if (isNaN(frm.settleDayNo.value)){
			alert("���������ֽ�����");
			return false;
		}
		if (frm.buyPayoutPct.value == "") {
			alert("����һ����������Ϊ�գ�");
			return false;
		}
		if(!IsIntOrFloat(frm.buyPayoutPct.value)) {
			alert("����һ���������벻��ȷ!");
			frm.buyPayoutPct.value = "";
			frm.buyPayoutPct.select();
			return false;
		}
		if (frm.sellIncomePct.value == "") {
			alert("�����һ����������Ϊ�գ�");
			return false;
		}
		if(!IsIntOrFloat(frm.sellIncomePct.value)) {
			alert("�����һ���������벻��ȷ!");
			frm.sellIncomePct.value = "";
			frm.sellIncomePct.select();
			return false;
		}
		if(confirm("ȷ��ִ�д˲�����")){
			frm.submit();
		}
	}
	function freturn(){
		frm.action = "<%=basePath%>servlet/paymentPropsController.${POSTFIX}?funcflg=paymentPropsList";
		frm.submit();
	}
	function IsIntOrFloat(num){
  		var reNum=/(^\d)\d*(\.?)\d*$/;
  		return (reNum.test(num));
	}
</script> 
