<%@ page contentType="text/html;charset=GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/front/public/includefiles/allIncludeFiles.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>��ǰ�ʽ���ϸ��ҳ��</title>
		<link href="${skinPath}/css/mgr/memberadmin/module.css"
			rel="stylesheet" type="text/css" />
		<link rel="stylesheet"
			href="${skinPath }/css/validationEngine.jquery.css" type="text/css" />
		<script type="text/javascript"
			src="${publicPath}/js/jquery-1.6.2.min.js"></script>
		<script type="text/javascript"
			src="${frontPath }/app/timebargain/js/jquery.validationEngine-zh_CN.js"></script>
		<script type="text/javascript"
			src="${publicPath }/js/jquery.validationEngine.js"></script>
		<script type="text/javascript"
			src="${publicPath}/js/calendar/WdatePicker.js"></script>
		<script type="text/javascript" src="${publicPath}/js/tool.js"></script>
		<script>
</script>
	</head>
	<body>
		<!-------------------------Body Begin------------------------->
		<div class="main">
			<jsp:include page="/front/frame/current.jsp"></jsp:include>
			<div class="warning">
				<div class="title font_orange_14b">
					��ܰ��ʾ :
				</div>
				<div class="content">
					�ڴ�չʾ���ĵ�ǰ�ʽ���ϸ��Ϣ��
				</div>
			</div>
			<div class="main_copy">
				<div class="form margin_10b">
					<table border="0" cellspacing="0" cellpadding="0" class="content">
						<c:if test="${fn:length(futureList)>0}">
							<c:set var="curProfix" value="0.00"/>
							<c:set var="firmFunds" value="0.00"/>
								<c:forEach items="${futureList}" var="future">
								<tr>
									<td class="main_tbw03" align="right" width="25%">
										�����̴���
									</td>
									<td align="right" class="main_tbw03" width="25%">
										<c:out value="${future.FirmID}"/>
									</td>
									<td class="main_tbw03" align="right" colspan="2">
										&nbsp;
									</td>
								</tr>
								<tr>
									<td class="main_tbw03" align="right" width="25%">
										<b>�ڳ����</b>
									</td>
									<td align="right" class="main_tbw03" width="25%">
										<fmt:formatNumber value="${future.LASTBALANCE}" pattern="#,##0.00#"/>
									</td>
									<td class="main_tbw03" align="right" colspan="2" >
										&nbsp;
									</td>
								</tr>
								<tr>
									<td class="main_tbw03" align="right" width="25%">
										(+)���ձ�֤��
									</td>
									<td align="right" class="main_tbw03" width="25%">
										<fmt:formatNumber value="${future.CLEARMARGIN}" pattern="#,##0.00#"/>
									</td>
									<td class="main_tbw03" align="right" width="25%">
										(-)���ձ�֤��
									</td>
									<td align="right" class="main_tbw03" width="25%">
										<fmt:formatNumber value="${future.RUNTIMEMARGIN}" pattern="#,##0.00#"/>
									</td>
								</tr>
								<tr>
									<td class="main_tbw03" align="right">
										(+)���ո���
									</td>
									<td align="right" class="main_tbw03" >
										<fmt:formatNumber value="${future.CLEARFL}" pattern="#,##0.00#"/>
									</td>
									<td class="main_tbw03" align="right">
										(-)���ո���
									</td>
									<td align="right" class="main_tbw03" >
										<fmt:formatNumber value="${future.RUNTIMEFL}" pattern="#,##0.00#"/>
									</td>
								</tr>
								<tr>
									<td class="main_tbw03" align="right">
										(+)���
									</td>
									<td align="right" class="main_tbw03" >
										<fmt:formatNumber value="${future.INAMOUNT}" pattern="#,##0.00#"/>
									</td>
									<td class="main_tbw03" align="right">
										(-)����
									</td>
									<td align="right" class="main_tbw03" >
										<fmt:formatNumber value="${future.OUTAMOUNT}" pattern="#,##0.00#"/>
									</td>
								</tr>
								<tr>
									<td class="main_tbw03" align="right">
										(+)����ת��ӯ��
									</td>
									<td align="right" class="main_tbw03" >
										<fmt:formatNumber value="${future.CLOSE_PL}" pattern="#,##0.00#"/>
									</td>
									<td class="main_tbw03" align="right">
										(-)ί�ж����ʽ�
									</td>
									<td align="right" class="main_tbw03" >
										<fmt:formatNumber value="${future.ORDERFROZEN}" pattern="#,##0.00#"/>
									</td>
								</tr>
								<tr>
									<td class="main_tbw03" align="right">
										(+)����������仯
									</td>
									<td align="right" class="main_tbw03" >
										<fmt:formatNumber value="${future.RUNTIMEASSURE-future.CLEARASSURE}" pattern="#,##0.00#"/>
									</td>
									<td class="main_tbw03" align="right">
										(-)����������
									</td>
									<td align="right" class="main_tbw03" >
									<fmt:formatNumber value="${future.TRADEFEE}" pattern="#,##0.00#"/>
									</td>
									<c:set var="firmFunds" value="${firmFunds + future.CLEARMARGIN - future.RUNTIMEMARGIN + future.CLEARFL - future.RUNTIMEFL + future.INAMOUNT - future.OUTAMOUNT + future.CLOSE_PL - future.ORDERFROZEN + future.RUNTIMEASSURE-future.CLEARASSURE - future.TRADEFEE}"/>
								</tr>
						<c:set var="timebargainFunds" value="0.00"/>		
						<c:if test="${fn:length(fundDetail)>0}">
							<c:forEach items="${fundDetail}" var="fund">
					 	 				<tr>
											<td class="main_tbw03" align="right">
					 	 						(+)��������
					 	 					</td>
					 	 					<td align="right" class="main_tbw03" >
					 	 					<fmt:formatNumber value="${fund.T_GOODMONEY}" pattern="#,##0.00#"/>
											</td>
											<td class="main_tbw03" align="right">
					 	 						(-)����ΥԼ��
					 	 					</td>
					 	 					<td align="right" class="main_tbw03" >
					 	 					<fmt:formatNumber value="${fund.PENAL}" pattern="#,##0.00#"/>
											</td>
					 	 				</tr>
					 	 				<tr>
					 	 					<td class="main_tbw03" align="right">
					 	 						(+)��������ӯ��
					 	 					</td>
					 	 					<td align="right" class="main_tbw03" >
					 	 					<fmt:formatNumber value="${fund.SETTLEPL}" pattern="#,##0.00#"/>
											</td>
											<td class="main_tbw03" align="right">
					 	 						(-)��������������
					 	 					</td>
					 	 					<td align="right" class="main_tbw03" >
					 	 					<fmt:formatNumber value="${fund.SETTLEFEE}" pattern="#,##0.00#"/>
											</td>
					 	 				</tr>
					 	 				<tr>
					 	 					<td class="main_tbw03" align="right">
					 	 						(+)�������ձ�֤��仯
					 	 					</td>
					 	 					<td align="right" class="main_tbw03" >
					 	 					<fmt:formatNumber value="${fund.SETTLEMARGAIN}" pattern="#,##0.00#"/>
											</td>
											
	                           <c:set var="timebargainFunds" value="${timebargainFunds + fund.T_GOODMONEY - fund.PENAL + fund.SETTLEPL - fund.SETTLEFEE + fund.SETTLEMARGAIN}"/>
															 
							</c:forEach>
						</c:if>
	
						<c:if test="${fn:length(fundDetail)<=0}">
							 			<tr>
											<td class="main_tbw03" align="right">
					 	 						(+)��������
					 	 					</td>
					 	 					<td align="right" class="main_tbw03" >
					 	 						<fmt:formatNumber value="0.00" pattern="#,##0.00#"/>
											</td>
											<td class="main_tbw03" align="right">
					 	 						(-)����ΥԼ��
					 	 					</td>
					 	 					<td align="right" class="main_tbw03" >
					 	 						<fmt:formatNumber value="0.00" pattern="#,##0.00#"/>
											</td>
					 	 				</tr>
					 	 				<tr>
					 	 					<td class="main_tbw03" align="right">
					 	 						(+)��������ӯ��
					 	 					</td>
					 	 					<td align="right" class="main_tbw03" >
					 	 						<fmt:formatNumber value="0.00" pattern="#,##0.00#"/>
											</td>
											<td class="main_tbw03" align="right">
					 	 						(-)��������������
					 	 					</td>
					 	 					<td align="right" class="main_tbw03" >
					 	 						<fmt:formatNumber value="0.00" pattern="#,##0.00#"/>
											</td>
					 	 				</tr>
					 	 				<tr>
					 	 					<td class="main_tbw03"  align="right">
					 	 						(+)�������ձ�֤��仯
					 	 					</td>
					 	 					<td align="right" class="main_tbw03" >
					 	 						<fmt:formatNumber value="0.00" pattern="#,##0.00#"/>
											</td>
											
		
							  </c:if>
						    <td class="main_tbw03" align="right" >
								               (-)�����걨�����ʽ�
							                </td>
							                <td class="main_tbw03" align="right">
								                <fmt:formatNumber value="${future.DELAYORDERFROZEN}" pattern="#,##0.00#"/>
							                </td>
						                 </tr>     
				
				      <c:set var="otherSysFunds" value="0.00"/>
						<tr>
    					    <td class="main_tbw03" align="right" colspan="2">&nbsp;</td>
						    <td class="main_tbw03" align="right" >
								               (-)����ϵͳ�����ʽ�
							                </td>
							                <td class="main_tbw03" align="right">
								                <fmt:formatNumber value="${future.OTHERFROZEN}" pattern="#,##0.00#"/>
							                </td>
 
						</tr>
						<tr>
						    <td class="main_tbw03" align="right" colspan="2">&nbsp;</td>
						    <td class="main_tbw03" align="right">
								(-)����ϵͳ�ʽ���
							</td>
							<td class="main_tbw03" align="right">
							   <c:set var="otherSysFunds" value="${otherSysFunds + future.LASTBALANCE + firmFunds - future.OTHERFROZEN - future.DELAYORDERFROZEN + timebargainFunds - future.USEFULFUND}"/>
							   <fmt:formatNumber value="${otherSysFunds}" pattern="#,##0.00#"/>
							</td>		
						</tr>
							  
							  <tr>
								 <td colspan="4">&nbsp;</td>
							  </tr>
								
								<tr>
									<td class="main_tbw03" align="right">
										<b>��ǰ�������</b>
									</td>
									<td align="right" class="main_tbw03">
										<fmt:formatNumber value="${future.USEFULFUND}" pattern="#,##0.00#"/>
									</td>
									<td class="main_tbw03" align="right" colspan="2">
										&nbsp;
									</td>
								</tr>
								<tr>
									<td class="main_tbw03" align="right">
										<b>����ӯ��</b>
									</td>
									<td align="right" class="main_tbw03">
										<fmt:formatNumber value="${future.PL}" pattern="#,##0.00#"/>
									</td>
									<td class="main_tbw03" align="right" colspan="2">
										&nbsp;
									</td>
								</tr>
								<tr>
									<td class="main_tbw03" align="right">
										<b>���յ�����</b>
									</td>
									<td align="right" class="main_tbw03">
										<fmt:formatNumber value="${future.RUNTIMEASSURE}" pattern="#,##0.00#"/>
									</td>
									<td class="main_tbw03" align="right" colspan="2">
										&nbsp;
									</td>
								</tr>
								<tr>
									<td class="main_tbw03" align="right">
										<b>���ս��ձ�֤��</b>
									</td>
									<td align="right" class="main_tbw03">
										<fmt:formatNumber value="${future.RUNTIMESETTLEMARGIN}" pattern="#,##0.00#"/>
									</td>
									<td class="main_tbw03" align="right" colspan="2">
										&nbsp;
									</td>
								</tr>
								<tr>
									<c:set var="curProfix" value="0.00"/>
									<c:set var="curProfix" value="${curProfix+future.BALANCE+future.CLEARMARGIN-future.CLEARASSURE+future.CLEARFL+future.PL+future.RUNTIMESETTLEMARGIN+future.CLOSE_PL-future.TRADEFEE}"/>
									<td class="main_tbw03" align="right">
										<b>��ǰȨ��</b>
									</td>
									<td align="right" class="main_tbw03">
									<fmt:formatNumber value="${curProfix}" pattern="#,##0.00#"/>
									</td>
									<td class="main_tbw03" align="right" colspan="2">
										&nbsp;
									</td>
								</tr>
								 <tr>
								 	<td colspan="4">
					     			 	<font face='����_GB2312' color='red' size='2' >
					     			 	��ע:
					     			 	����ϵͳ�ʽ���=�������Ͳ�����������ϵͳ�ķǶ�����ʽ�<br/>
					     			 	��ǰȨ��=�����ʽ�+ί�ж����ʽ�+�����걨�����ʽ�+����ϵͳ�����ʽ�+���ձ�֤��+���ո���-���յ�����+����ӯ��+���ս��ձ�֤��
					     			 	</font>
					    		 	</td>
					    		 </tr>
							</c:forEach>
						</c:if>				
					</table>
					
				</div>
			</div>
		</div>
		<!-------------------------Body End------------------------->
	</body>
</html>