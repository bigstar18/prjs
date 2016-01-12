<%@ page pageEncoding="GBK" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
	
	
<html>
<head>
<title>
强制转让信息
</title>
<script type="text/javascript">

		
// -->
</script>    
</head>

<body leftmargin="2" topmargin="0" onLoad="">
    <table class="common" border="0" align="center" cellpadding="0" cellspacing="0" width="100%">
        <caption><b>交易商基本信息</b></caption>
        <table class="common" border="0" align="center" cellpadding="0" cellspacing="0" width="100%">
        <tr>
            <td align="right" class="right" >交易商代码：</td>
            <td class="left" id="firmID">${mapInfo.firmID}</td>
            <td align="right" class="right">交易商名称：</td>
            <td  class="left">${mapInfo.firmName}</td>
        </tr>
      
        <tr>
            <td align="right" class="right">联系方式：</td>
            <td class="left">${mapInfo.phone}</td>
            <td align="right" class="right" >资金安全率：</td>
            <td class="left"><fmt:formatNumber value="${mapInfo.fundsSafeRate}" pattern="#,##0.00"/>&nbsp;</td>
        </tr> 
        <tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
        <tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
        <tr>
        	<td valign="top" colspan="4">
        								
										<table valign="top" border="1"  width="100%" cellpadding="0" cellspacing="0" class="common" align="left">
											<caption><b>交易商持仓信息</b></caption>
											<tr height="20"><td bgcolor="C0C0C0" align="center">商品代码</td><td bgcolor="C0C0C0" align="center">持仓数量</td><td bgcolor="C0C0C0" align="center">订货均价</td><td bgcolor="C0C0C0" align="center">买卖</td></tr>

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
														<c:when test="${result.BS_Flag=='1'}">买</c:when>
														<c:when test="${result.BS_Flag=='2'}">卖</c:when>
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

