<%@ page pageEncoding="GBK" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
	
	
<html>
<head>
<title>
ǿ��ת����Ϣ
</title>
<script type="text/javascript">

		
// -->
</script>    
</head>

<body leftmargin="2" topmargin="0" onLoad="">
    <table class="common" border="0" align="center" cellpadding="0" cellspacing="0" width="100%">
        <caption><b>�����̻�����Ϣ</b></caption>
        <table class="common" border="0" align="center" cellpadding="0" cellspacing="0" width="100%">
        <tr>
            <td align="right" class="right" >�����̴��룺</td>
            <td class="left" id="firmID">${mapInfo.firmID}</td>
            <td align="right" class="right">���������ƣ�</td>
            <td  class="left">${mapInfo.firmName}</td>
        </tr>
      
        <tr>
            <td align="right" class="right">��ϵ��ʽ��</td>
            <td class="left">${mapInfo.phone}</td>
            <td align="right" class="right" >�ʽ�ȫ�ʣ�</td>
            <td class="left"><fmt:formatNumber value="${mapInfo.fundsSafeRate}" pattern="#,##0.00"/>&nbsp;</td>
        </tr> 
        <tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
        <tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
        <tr>
        	<td valign="top" colspan="4">
        								
										<table valign="top" border="1"  width="100%" cellpadding="0" cellspacing="0" class="common" align="left">
											<caption><b>�����ֲ̳���Ϣ</b></caption>
											<tr height="20"><td bgcolor="C0C0C0" align="center">��Ʒ����</td><td bgcolor="C0C0C0" align="center">�ֲ�����</td><td bgcolor="C0C0C0" align="center">��������</td><td bgcolor="C0C0C0" align="center">����</td></tr>

											<c:forEach items="${listHold}" var="result">
												<tr valign="top" height="20">
													<td align="center">
														${result.commodityID}
													</td>
													<td align="right">
														${result.HoldQty}
													</td>
													<td align="right">
														${result.evenPrice}
													</td>
													<td align="center">
													<c:choose>
														<c:when test="${result.BS_Flag=='1'}">��</c:when>
														<c:when test="${result.BS_Flag=='2'}">��</c:when>
													</c:choose>
													</td>
												</tr>
											</c:forEach>	
										</table>
									</td>
        </tr>
       	
		
        </table>
        <table height="50">
        	<tr>
        		<td>
        			&nbsp;
        		</td>
        	</tr>
        </table>
        <table id="tab" class="common" border="0" align="center" cellpadding="1" cellspacing="1" width="100%">
        	
        	<tbody id="tbo" align="center">
        		
        
        	</tbody>
        </table>
    </table>
</body>
</html>

