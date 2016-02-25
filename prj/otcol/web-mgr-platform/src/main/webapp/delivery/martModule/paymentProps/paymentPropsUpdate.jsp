<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../../public/session.jsp"%>
<html>
  <head>
	<title><%=TITLE%></title>
</head>
<body>
        <form id="formNew" name="frm" method="POST" targetType="hidden" action="<%=basePath%>servlet/paymentPropsController.${POSTFIX}?funcflg=paymentPropsUpdate">
		<fieldset width="50%" >
		<legend>�����ո�������Ϣ</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="50%" align="center">
			  <tr height="35">
                <td align="right" width="40%">����ģ�飺</td>
                <td align="left" width="60%">
                	<c:forEach items="${moduleNameMap}" var="moduleNameMap">
                		<c:choose>
                			<c:when test="${moduleNameMap.key == pay.moduleID}">
                				${moduleNameMap.value}
                			</c:when>
                		</c:choose>
                	</c:forEach>
                	<input type="hidden" id="moduleID" name="moduleID" value="${pay.moduleID}"/>
                </td>
              </tr>
              <tr height="35">
            	<td align="right">Ʒ�֣�</td>
                <td align="left">
						${commodity.name}
                		<input type="hidden" id="breedID" name="breedID" value="${pay.breedID}"/>
                </td>
              </tr>
			   <tr height="35">
            	<td align="right">�ڼ������գ�</td>
                <td align="left">
					${pay.settleDayNo}
					<input type="hidden" id="settleDayNo" name="settleDayNo" value="${pay.settleDayNo}" />
                </td>
              </tr>
			  <tr height="35">
            	<td align="right">����һ��������</td>
                <td align="left">
                	<input id="buyPayoutPct" name="buyPayoutPct" type="text" value="${pay.buyPayoutPct}" class="text" style="width: 120px;" maxlength="15">&nbsp;<font color="red">%&nbsp;*</font>
                </td>
              </tr>
			  <tr height="35">
            	<td align="right">�����һ��������</td> 
                <td align="left">
                	<input id="sellIncomePct" name="sellIncomePct" type="text" value="${pay.sellIncomePct}" class="text" style="width: 120px;" maxlength="15">&nbsp;<font color="red">%&nbsp;*</font>
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
			alert("��Ʒ���벻��Ϊ�գ�");
			return false;
		}
		if (frm.settleDayNo.value == "") {
			alert("�ڼ������ղ���Ϊ�գ�");
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
		frm.submit();
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
