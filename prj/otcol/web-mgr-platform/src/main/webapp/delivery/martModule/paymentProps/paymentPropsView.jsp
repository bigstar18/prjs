<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../../public/session.jsp"%>
<html>
  <head>
	<title><%=TITLE%></title>
</head>
<body>
        <form id="frm" name="frm" method="POST" targetType="hidden">
			<table border="0" cellspacing="0" cellpadding="0" width="50%" align="center">
			    <tr height="35">
		            <td class="cd_hr"><div align="center">
		            	�����ո���Ϣ
					 </div></td>
		        </tr>
		    </table>
			<table border="1" cellspacing="0" cellpadding="0" width="50%" align="center">
			  <tr height="35">
                <td align="right" width="40%">����ģ�飺</td>
                <td align="left" width="60%">
                	${pay.moduleID}
                </td>
              </tr>
              <tr height="35">
            	<td align="right">Ʒ�����ƣ�</td>
                <td align="left">
                	${commodity.name}
                </td>
              </tr>
			   <tr height="35">
            	<td align="right">�ڼ������գ�</td>
                <td align="left">
                	${pay.settleDayNo}
                </td>
              </tr>
			  <tr height="35">
            	<td align="right">����һ��������</td>
                <td align="left">
                	<fmt:formatNumber value="${pay.buyPayoutPct}" pattern="#,##0.00"/>&nbsp;<font color="red">%</font>
                	
                </td>
              </tr>
			  <tr height="35">
            	<td align="right">�����һ��������</td> 
                <td align="left">
                	<fmt:formatNumber value="${pay.sellIncomePct}" pattern="#,##0.00"/>&nbsp;<font color="red">%</font>
                </td>
              </tr>
              <tr height="35">
                <td colspan="2"><div align="center">
      			  <button class="smlbtn" type="button" onClick="freturn()">����</button>
                </div></td>
              </tr>
          </table>
        </form>
</body>
</html>
<script>
	function freturn(){
		frm.action = "<%=basePath%>servlet/paymentPropsController.${POSTFIX}?funcflg=paymentPropsList";
		frm.submit();
	}
</script> 
