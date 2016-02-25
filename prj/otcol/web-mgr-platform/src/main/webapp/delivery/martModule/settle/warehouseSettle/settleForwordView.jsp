<%@ include file="../../../public/session.jsp"%>
<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
</head>
<body>
	<form name="frm" method="post" action="<%=basePath %>servlet/settleMatchController.${POSTFIX}?funcflg=settleInvoice">
		<input type="hidden" id="matchId" name="matchId" value="<c:out value="${settleMatch.matchId}"/>">
		<input type="hidden" id="moduleId" name="moduleId" value="${settleMatch.moduleId }">
		<input type="hidden" id="isChangeOwner" name="isChangeOwner" value="${settleMatch.isChangeOwner }">
		<div id="cid" style="display: none;">${settleMatch.xml}</div>
		<input type="hidden" id="xml" name="xml" value="${xml }">
		<fieldset>
			<legend class="common"><b>���������Ϣ</b></legend>
			<span>
			<table class="common" align="center" width="100%" height="100%" border="0">
			<tr class="common">
				<td align="center" colspan="5" width="100%">
			<fieldset>
			<legend class="common"><b>��Ʒ��Ϣ</b></legend>
			<span>
				<table class="common" align="center" width="90%">
				<tr class="common">
					<td align="right" width="15%">��Ա�ţ�</td><td align="left" width="15%">${settleMatch.matchId}</td>
					<td align="right" width="15%">ģ�飺</td>
					<td align="left" width="15%"><c:out value="${moduleNameMap[settleMatch.moduleId]}" /></td>
					<td align="right" width="15%">��Լ�����</td>
					<td align="left" width="15%">
						<c:choose>
							<c:when test="${settleMatch.result==1}">��Լ</c:when>
							<c:when test="${settleMatch.result==2}">��ΥԼ</c:when>
							<c:when test="${settleMatch.result==3}">����ΥԼ</c:when>
							<c:when test="${settleMatch.result==4}">˫��ΥԼ</c:when>
						</c:choose>
					</td>
				</tr>
				<tr class="common">
					<td align="right" width="15%">Ʒ�ִ��룺</td><td align="left" width="15%">${settleMatch.breedId}</td>
					<td align="right" width="15%">����������</td><td align="left" width="15%">${settleMatch.weight}</td>
					<td align="right" width="15%">����״̬��</td>
					<td align="left" width="15%"><font color="red">
						<c:choose>
							<c:when test="${settleMatch.status==0}">δ����</c:when>
							<c:when test="${settleMatch.status==1}">������</c:when>
							<c:when test="${settleMatch.status==2}">�������</c:when>
							<c:when test="${settleMatch.status==3}">����</c:when>
						</c:choose></font>
					</td>
				</tr>
				</table>
			</span>
		</fieldset>
				</td></tr>
				<tr class="common">
				<!-- ������ʾ ��1�� -->
				<td width="32%">
			<fieldset>
			<legend class="common"><b>����Ϣ</b></legend>
			<span>
				<table class="common" valign="top" border="0">
				<tr class="common">
					<td align="right">�򷽽����̴��룺</td><td align="left">${settleMatch.firmID_B}</td>
				</tr>
				<tr class="common">
					<td align="right"><b>�����ʽ�</b></td><td align="left"><fmt:formatNumber value="${buyBalance}" pattern="#,##0.00"/></td>
				</tr>
				<tr class="common">
					<td align="right">�򷽽��ռۣ�</td><td align="left">
					<c:choose>
						<c:when test="${fn:contains(xml,'ATS_')}">  <!-- ��ǰ���� -->
							<c:if test="${aheadSettlePriceType == 1}">  <!-- �������� -->
								<fmt:formatNumber value="${settleMatch.buyPrice}" pattern="#,##0.00"/>
							</c:if>
							<c:if test="${aheadSettlePriceType == 0}">   <!-- �������� -->
								<a href="javaScript: showSettlePrice(1)">�鿴</a>
							</c:if>
						</c:when>
						<c:otherwise><!-- ���ڽ��ջ��ߵ��ӽ��� -->
							<c:if test="${settlePriceType != 2}">
								<fmt:formatNumber value="${settleMatch.buyPrice}" pattern="#,##0.00"/>
							</c:if>
							<c:if test="${settlePriceType == 2}">
								<a href="javaScript: showSettlePrice(1)">�鿴</a>
							</c:if>
						</c:otherwise>
					</c:choose>
					
					</td>
				</tr>
				<tr class="common">
					<td align="right">�򷽻�׼���</td><td align="left"><fmt:formatNumber value="${settleMatch.buyPayout_Ref}" pattern="#,##0.00"/></td>
				</tr>
				<tr class="common">
					<td align="right"><b>������ˮ����</b></td><td align="left">
					<c:choose>
						<c:when test="${(settleMatch.buyPayout_Ref+settleMatch.HL_Amount)>buyBalance}">
							<font color="red"><fmt:formatNumber value="${settleMatch.buyPayout_Ref+settleMatch.HL_Amount}" pattern="#,##0.00"/></font>
						</c:when>
						<c:otherwise>
							<fmt:formatNumber value="${settleMatch.buyPayout_Ref+settleMatch.HL_Amount}" pattern="#,##0.00"/>
						</c:otherwise>
					</c:choose>
					</td>
				</tr>
				<tr class="common">
					<td align="right"><b>�����򷽻��</b></td><td align="left"><fmt:formatNumber value="${settleMatch.buyPayout}" pattern="#,##0.00"/></td>
				</tr>
				<tr class="common">
					<td align="right">�����򷽱�֤��</td><td align="left"><fmt:formatNumber value="${settleMatch.buyMargin}" pattern="#,##0.00"/></td>
				</tr>
				<c:if test="${settleMatch.result!=1}">
					<c:choose>
						<c:when test="${settleMatch.result==2||settleMatch.result==4}">
							<tr class="common">
								<td align="right">����ΥԼ��</td><td align="left"><fmt:formatNumber value="${settleMatch.penalty_B}" pattern="#,##0.00"/></td>
							</tr>
						</c:when>
						<c:when test="${settleMatch.result==3}">
							<tr class="common">
								<td align="right">����ΥԼ��</td><td align="left"><fmt:formatNumber value="${settleMatch.penalty_B}" pattern="#,##0.00"/></td>
							</tr>
						</c:when>
					</c:choose>
					<tr class="common">
						<td align="right">�򷽽���ӯ����</td><td align="left"><fmt:formatNumber value="${settleMatch.settlePL_B}" pattern="#,##0.00"/></td>
					</tr>
				</c:if>
				</table>
			</span>
		</fieldset>
				</td>
				<!-- ������ʾ ��2�� -->
				<td width="2%">
				<table class="common" valign="top" border="0">
				<tr class="common"><td rowspan="10">&nbsp;</td></tr>
				</table>
				</td>
				<!-- ������ʾ ��3�� -->
				<td width="32%">
			<fieldset>
			<legend class="common"><b>������Ϣ</b></legend>
			<span>
				<table class="common" valign="top" border="0">	
				<tr class="common">
					<td align="right">���������̴��룺</td><td align="left">${settleMatch.firmID_S}</td>
				</tr>
				<tr class="common">
					<td align="right">�������ռۣ�</td><td align="left">
						<c:choose>
						<c:when test="${fn:contains(xml,'ATS_')}">  <!-- ��ǰ���� -->
							<c:if test="${aheadSettlePriceType == 1}">  <!-- �������� -->
								<fmt:formatNumber value="${settleMatch.sellPrice}" pattern="#,##0.00"/>
							</c:if>
							<c:if test="${aheadSettlePriceType == 0}">   <!-- �������� -->
								<a href="javaScript: showSettlePrice(2)">�鿴</a>
							</c:if>
						</c:when>
						<c:otherwise><!-- ���ڽ��ջ��ߵ��ӽ��� -->
							<c:if test="${settlePriceType != 2}">
								<fmt:formatNumber value="${settleMatch.sellPrice}" pattern="#,##0.00"/>
							</c:if>
							<c:if test="${settlePriceType == 2}">
								<a href="javaScript: showSettlePrice(2)">�鿴</a>
							</c:if>
						</c:otherwise>
					</c:choose>
					</td>
				</tr>
				<tr class="common">
					<td align="right">������׼���</td><td align="left"><fmt:formatNumber value="${settleMatch.sellIncome_Ref}" pattern="#,##0.00"/></td>
				</tr>
				<tr class="common">
					<td align="right"><b>��������ˮ����</b></td><td align="left"><fmt:formatNumber value="${settleMatch.sellIncome_Ref+settleMatch.HL_Amount}" pattern="#,##0.00"/></td>
				</tr>
				<tr class="common">
					<td align="right"><b>�Ѹ��������</b></td><td align="left"><fmt:formatNumber value="${settleMatch.sellIncome}" pattern="#,##0.00"/></td>
				</tr>
				<tr class="common">
					<td align="right">����������֤��</td><td align="left"><fmt:formatNumber value="${settleMatch.sellMargin}" pattern="#,##0.00"/></td>
				</tr>
				<c:if test="${settleMatch.result!=1}">
					<c:choose>
						<c:when test="${settleMatch.result==3||settleMatch.result==4}">
							<tr class="common">
								<td align="right">������ΥԼ��</td><td align="left"><fmt:formatNumber value="${settleMatch.penalty_S}" pattern="#,##0.00"/></td>
							</tr>
						</c:when>
						<c:when test="${settleMatch.result==2}">
							<tr class="common">
								<td align="right">������ΥԼ��</td><td align="left"><fmt:formatNumber value="${settleMatch.penalty_S}" pattern="#,##0.00"/></td>
							</tr>
						</c:when>
					</c:choose>
					<tr class="common">
						<td align="right">��������ӯ����</td><td align="left"><fmt:formatNumber value="${settleMatch.settlePL_S}" pattern="#,##0.00"/></td>
					</tr>
				</c:if>
				<tr class="common">
					<td align="right">&nbsp;</td><td align="left">&nbsp;</td>
				</tr>
				</table>
			</span>
		</fieldset>
				</td>
				<!-- ������ʾ ��4�� -->
				<td width="2%">
				<table class="common" valign="top" border="0">
				<tr class="common"><td rowspan="10">&nbsp;</td></tr>
				</table>
				</td>
				<!-- ������ʾ ��5�� -->
				<td width="32%">
			<fieldset>
			<legend class="common"><b>������Ϣ</b></legend>
			<span>
				<table class="common" valign="top" border="0">	
				<tr class="common">
					<td align="right">����ˮ��</td><td align="left"><fmt:formatNumber value="${settleMatch.HL_Amount }" pattern="#,##0.00"/></td>
				</tr>
				<tr class="common">
					<td align="right">�г����</td><td align="left"><fmt:formatNumber value="${settleMatch.sellIncome_Ref-settleMatch.buyPayout_Ref }" pattern="#,##0.00"/></td>
				</tr>
				<tr>
					<td align="right">�Ƿ��յ���Ʊ��</td>
					<td align="left">
						<c:choose>
							<c:when test="${settleMatch.recvInvoice==0 }">��</c:when>
							<c:when test="${settleMatch.recvInvoice==1 }">��</c:when>
						</c:choose>
					</td>
				</tr>
				<tr>
					<td align="right">�������Ա��</td>
					<td align="left">
						<c:choose>
							<c:when test="${operator==null }">������Ա����</c:when>
							<c:when test="${operator!=null }">${operator}</c:when>
						</c:choose>
					</td>
				</tr>
				<tr>
					<td ><span onclick="test('${settleMatch.matchId }');"  style="cursor:hand;color:blue">�鿴���в�����¼->></span></td>
				</tr>
				<tr class="common">
					<c:if test="${settleMatch.result == 1}">
					  <td ><span onclick="findStock('${settleMatch.matchId }');"  style="cursor:hand;color:blue">�鿴��Գֲ���Ϣ->></span></td>
					</c:if>
					<c:if test="${settleMatch.result != 1}">
					  <td align="right">&nbsp;</td><td align="left">&nbsp;</td>
					</c:if>
				</tr>
				<tr class="common">
					<td align="right">&nbsp;</td><td align="left">&nbsp;</td>
				</tr>
				<c:if test="${settleMatch.result!=1}">
					<tr class="common">
						<td align="right">&nbsp;</td><td align="left">&nbsp;</td>
					</tr>
					<tr class="common">
						<td align="right">&nbsp;</td><td align="left">&nbsp;</td>
					</tr>
				</c:if>
				<tr class="common">
					<td align="right">&nbsp;</td><td align="left">&nbsp;</td>
				</tr>
				</table>
				</span>
		</fieldset>
				</td>
				</tr>
								
				<tr class="common"><td colspan="5" width="100%">
					
			<fieldset>
			<legend class="common"><b>�����޸�ʱ��</b></legend>
			<span>
					<table class="common" align="left">
						<tr class="common">
								<td align="right" width="25%">����ʱ��:</td><td align="left" width="25%"><fmt:formatDate value="${settleMatch.createTime }" pattern="yyyy-MM-dd"/></td>
								<td align="right" width="25%">�޸�ʱ��:</td><td align="left" width="25%"><fmt:formatDate value="${settleMatch.modifyTime }" pattern="yyyy-MM-dd"/></td>
						</tr>
					</table>					
				</span>
		</fieldset>
				</td></tr>
				
				<tr class="common"><td colspan="5" width="100%">&nbsp;</td></tr>
				
				<tr class="common"><td colspan="5" align="center" width="100%">
				<table class="common" align="center" border="0" width="95%">
				<tr class="common">
					<td colspan="7" align="center">
				<!-- ����ִ�н��չʾ��ͬ�Ĺ��ܰ�ť -->
						<c:if test="${settleMatch.status==1 }">
						<input name="btn14" type="button" class="button" value="��ԭ" onclick="event(14);">&nbsp;&nbsp;
				</c:if>	
			<c:choose>
		
			
				<c:when test="${settleMatch.status<2 }">
					<c:choose>
					<c:when test="${settleMatch.result==1 }">
						<input name="btn2" type="button" class="button" value="�ջ���" onclick="event(1);">&nbsp;&nbsp;
						<input name="btn2" type="button" class="button" value="������" onclick="event(2);">&nbsp;&nbsp;					
						<input name="btn1" type="button" class="button" value="��֤��ת����" onclick="event(13);">&nbsp;&nbsp;
						<input name="btn1" type="button" class="button" value="��Ȩת��" onclick="event(20);">&nbsp;&nbsp;					
						<input name="btn10" type="button" class="button" value="����ˮ" onclick="event(10);">&nbsp;&nbsp;
						<input name="btn10" type="button" class="button" value="��������֤��" onclick="event(17);">&nbsp;&nbsp;
						<input name="btn10" type="button" class="button" value="���򷽱�֤��" onclick="event(18);">&nbsp;&nbsp;
					</c:when>
					<c:when test="${settleMatch.result==2 }">
						<input name="btn3" type="button" class="button" value="����ΥԼ��" onclick="event(3);">&nbsp;&nbsp;
						<input name="btn4" type="button" class="button" value="������ΥԼ��" onclick="event(4);">&nbsp;&nbsp;
						<c:choose>
							<c:when test="${mark==true }">
							<input name="btn11" type="button" class="button" value="�򷽽���ӯ��" onclick="event(11);">&nbsp;&nbsp;
							<input name="btn12" type="button" class="button" value="��������ӯ��" onclick="event(12);">&nbsp;&nbsp;
							</c:when>
						</c:choose>
					</c:when>
					<c:when test="${settleMatch.result==3 }">
						<input name="btn5" type="button" class="button" value="������ΥԼ��" onclick="event(5);">&nbsp;&nbsp;
						<input name="btn6" type="button" class="button" value="����ΥԼ��" onclick="event(6);">&nbsp;&nbsp;
						<c:choose>
							<c:when test="${mark==true }">
							<input name="btn11" type="button" class="button" value="�򷽽���ӯ��" onclick="event(11);">&nbsp;&nbsp;
							<input name="btn12" type="button" class="button" value="��������ӯ��" onclick="event(12);">&nbsp;&nbsp;
							</c:when>
						</c:choose>
					</c:when>
					<c:when test="${settleMatch.result==4 }">
						<input name="btn3" type="button" class="button" value="����ΥԼ��" onclick="event(3);">&nbsp;&nbsp;
						<input name="btn5" type="button" class="button" value="������ΥԼ��" onclick="event(5);">&nbsp;&nbsp;
						<c:choose>
							<c:when test="${mark==true }">
							<input name="btn11" type="button" class="button" value="�򷽽���ӯ��" onclick="event(11);">&nbsp;&nbsp;
							<input name="btn12" type="button" class="button" value="��������ӯ��" onclick="event(12);">&nbsp;&nbsp;
							</c:when>
						</c:choose>
					</c:when>
					</c:choose>
						<input name="btn7" type="button" class="button" value="�������" onclick="event(7);">&nbsp;&nbsp;
						<input name="btn8" type="button" class="button" value="����" onclick="event(8);">&nbsp;&nbsp;
				</c:when>
				<c:when test="${settleMatch.status>1 }">
						<input name="btn7" type="button" class="button" value="�������" onclick="event(7);" disabled="disabled">&nbsp;&nbsp;
						<input name="btn8" type="button" class="button" value="����" onclick="event(8);" disabled="disabled">&nbsp;&nbsp;
				</c:when>
					
			</c:choose>	
					<c:if test="${settleMatch.status==0&&settleMatch.result==1 }">
						<input name="btn18" type="button" class="button" value="��������" onclick="event(19);">&nbsp;&nbsp;
				</c:if>
					<c:if test="${settleMatch.status==0 && settleMatch.result==1}">
				
						<input name="btn15" type="button" class="button" value="�޸Ĳֵ�" onclick="event(15);">&nbsp;&nbsp;
				</c:if>	
			<c:if test="${settleMatch.recvInvoice==0 && settleMatch.status!=3 && settleMatch.result==1 && settleMatch.status!=2}">
			<input name="makeSure" id="makeSure" type="button" class="button" value="ǩ��" onclick="doSubmit();">&nbsp;&nbsp;
			</c:if>	
			
			<input name="btn9" type="button" class="button" value="����" onclick="event(9);">
			</td>
			</tr>
			
				<tr class="common">
					<td align="left" width="100%" colspan="7">
						<font color="red">
					ע������Ϊ��(0.01Ԫ)���������Ȳ��ֽ����������롣<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��ȡΥԼ��ʱ���Զ��������ձ�֤��
					</font>
					
					<c:if test="${settleMatch.status==3 }">
					<font color="green">
					<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;����������Ϣ�ѱ����ϣ����в������ѳ�����
					</font>
					</c:if>	
					</td>
				</tr>
			</table>
			</td>
			</tr>			
			</table>
			</span>
		</fieldset>
	</form>
</body>
<script type="text/javascript">
	function showSettlePrice(BS_Flag) {
		var xml = frm.xml.value;
		var v="<%=basePath %>servlet/settleMatchController.${POSTFIX}?funcflg=getMatchSettleholdRelevanceList&xml="+xml+"&BS_Flag="+BS_Flag+"&d="+Date();
		window.showModalDialog(v,'', "dialogWidth=600px; dialogHeight=400px; status=no;scroll=yes;help=no;");
	}
	function event(v)
	{
		document.getElementsByName("btn"+v).disabled="disabled";
		var mid = frm.matchId.value;
		switch(v)
		{
			case 1:
				dispatchMethod("<%=basePath %>servlet/settleMatchController.${POSTFIX}?funcflg=settleIncomePayMent&matchId="+mid+"&d="+Date());
				break;
			case 2:
				dispatchMethod("<%=basePath %>servlet/settleMatchController.${POSTFIX}?funcflg=settlePayPayMent&matchId="+mid+"&d="+Date());
				break;
			case 3:
				dispatchMethod("<%=basePath %>servlet/settleMatchController.${POSTFIX}?funcflg=settleIncomeFromBuyer&matchId="+mid+"&d="+Date());
				break;
			case 4:
				dispatchMethod("<%=basePath %>servlet/settleMatchController.${POSTFIX}?funcflg=settlePayToSeller&matchId="+mid+"&d="+Date());
				break;
			case 5:
				dispatchMethod("<%=basePath %>servlet/settleMatchController.${POSTFIX}?funcflg=settleIncomeFromSeller&matchId="+mid+"&d="+Date());
				break;
			case 6:
				dispatchMethod("<%=basePath %>servlet/settleMatchController.${POSTFIX}?funcflg=settlePayToBuyer&matchId="+mid+"&d="+Date());
				break;
			case 7:
				var statusValue = '<c:out value="${settleMatch.result }"/>';
				fufillHandle(statusValue);
				break;
			case 8:
				blankOut();
				break;
			case 9:
				window.location.href="<%=basePath%>servlet/settleMatchController.${POSTFIX}?funcflg=settleMatchReturn&moduleId=2";
				break;
			case 10:
				dispatchMethod("<%=basePath %>servlet/settleMatchController.${POSTFIX}?funcflg=settleHL_Amount&matchId="+mid+"&d="+Date());
				break;
			case 11:
				dispatchMethod("<%=basePath %>servlet/settleMatchController.${POSTFIX}?funcflg=settleSettlePL_B&matchId="+mid+"&d="+Date());
				break;
			case 12:
				dispatchMethod("<%=basePath %>servlet/settleMatchController.${POSTFIX}?funcflg=settleSettlePL_S&matchId="+mid+"&d="+Date());
				break;
			case 13:
				dispatchMethod("<%=basePath %>servlet/settleMatchController.${POSTFIX}?funcflg=settleBailToPayment&matchId="+mid+"&d="+Date());
				break;
			case 14:
				reback(frm.matchId.value);
				break;
			case 15:
				if (frm.isChangeOwner.value==1) {
					alert("��Ȩת������ɣ��������޸Ĳֵ���");
					break;
				} else {
					dispatchMethod("<%=basePath%>servlet/settleMatchController.${POSTFIX}?funcflg=settleModifyRegStockForward&matchId="+mid+"&d="+Date());
					break;
				}
			case 16:
				dispatchMethod("<%=basePath %>servlet/settleModifyRegStockForward.wha?matchId="+mid+"&d="+Date());
				break;
			case 17:
				dispatchMethod("<%=basePath %>servlet/settleMatchController.${POSTFIX}?funcflg=returnMoneyForSell&matchId="+mid+"&d="+Date());
				break;
			case 18:
				dispatchMethod("<%=basePath %>/servlet/settleMatchController.${POSTFIX}?funcflg=returnMoneyForBuy&matchId="+mid+"&d="+Date());
				break;
			case 19:
				auto();
				break;
			case 20:
				dispatchMethod("<%=basePath %>/servlet/settleMatchController.${POSTFIX}?funcflg=transforSettle&matchId="+mid+"&d="+Date());
				break;
		}
	}
	function reback(v)
	{
		if(confirm("ȷ�ϻ�ԭ�������Ϣ?\n���ȷ���󣬸������Ϣ�����в���������ԭ��״̬��ԭ��δ����״̬��"))
		{
			frm.action="<%=basePath%>servlet/settleMatchController.${POSTFIX}?funcflg=settleRestore&matchId="+v+"&d="+Date();
			frm.submit();
		}	
	}
	function dispatchMethod(v)
	{
		var matchId = frm.matchId.value;
		var result = window.showModalDialog(v,'', 
					"dialogWidth=420px; dialogHeight=390px; status=no;scroll=yes;help=no;");
		if(result){
			window.location.href="<%=basePath%>servlet/settleMatchController.${POSTFIX}?funcflg=settleView&matchId="+matchId+"&moduleId=${settleMatch.moduleId }";
		}
	}
	
	// �鿴���в�����¼��Ϣ 
	function test(matchId)
	{
		v="<%=basePath %>servlet/settleMatchController.${POSTFIX}?funcflg=viewOperator&matchId="+matchId;
		
		var result = window.showModalDialog(v,'', 
					"dialogWidth=600px; dialogHeight=580px; status=no;scroll=yes;help=no;");
		
		if(result){
			window.location.href="<%=basePath %>servlet/settleMatchController.${POSTFIX}?funcflg=settleView&matchId="+matchId;
			
		}
		
	}
	
	// �鿴��Գֲ���Ϣ 
	function findStock(matchId)
	{
	    var v="<%=basePath %>servlet/settleMatchController.${POSTFIX}?funcflg=viewStock&matchId="+matchId;
	    
		var result = window.showModalDialog(v,'', 
					"dialogWidth=320px; dialogHeight=190px; status=no;scroll=no;help=no;");
		if(result){
			window.location.href="<%=basePath %>servlet/settleMatchController.${POSTFIX}?funcflg=settleView&matchId="+matchId;
			
		}
	}
	
	function fufillHandle(v)
	{
		var mid = frm.matchId.value;
		var FirmID_B = '<c:out value="${settleMatch.firmID_B }"/>';//��
		var FirmID_S = '<c:out value="${settleMatch.firmID_S }"/>';//��
		var CommodityID = '<c:out value="${settleMatch.breedId }"/>';//Ʒ�ִ���
		var Quantity = '<c:out value="${settleMatch.weight }"/>';//��������
		var HL_Amount = '<c:out value="${settleMatch.HL_Amount }"/>';//����ˮ
		var TakePenalty_B = '<c:out value="${settleMatch.penalty_B }"/>';//��ΥԼ��
		var SettlePL_B = '<c:out value="${settleMatch.settlePL_B }"/>';//�򷽽���ӯ��
		var SettlePL_S = '<c:out value="${settleMatch.settlePL_S }"/>';//��������ӯ��
		var TakePenalty_S = '<c:out value="${settleMatch.penalty_S }"/>';//����ΥԼ��
		
		var showMsg="";
		switch (v)
		{
			case '1'://��Լ
				showMsg="�򷽴��룺"+FirmID_B+",�������룺"+FirmID_S+
					"\nƷ�ִ��룺"+CommodityID+",����ˮ��"+HL_Amount;
				break;
			case '2'://��ΥԼ
				showMsg="�򷽴��룺"+FirmID_B+",�������룺"+FirmID_S+
					"\nƷ�ִ��룺"+CommodityID+",����������"+Quantity+
					"\n��ΥԼ��"+TakePenalty_B+",�򷽽���ӯ����"+SettlePL_B+
					"\n��������ӯ����"+SettlePL_S;
				break;
			case '3'://����ΥԼ
				showMsg="�򷽴��룺"+FirmID_B+",�������룺"+FirmID_S+
					"\nƷ�ִ��룺"+CommodityID+",����������"+Quantity+
					"\n����ΥԼ��"+TakePenalty_S+",�򷽽���ӯ����"+SettlePL_B+
					"\n��������ӯ����"+SettlePL_S;
				break;
			case '4'://˫��ΥԼ
				showMsg="�򷽴��룺"+FirmID_B+",�������룺"+FirmID_S+
					"\nƷ�ִ��룺"+CommodityID+",����������"+Quantity+
					"\n����ΥԼ��"+TakePenalty_B+",������ΥԼ��"+TakePenalty_S+
					"\n�򷽽���ӯ����"+SettlePL_B+",��������ӯ����"+SettlePL_S;
				break;
		}
		handleOK(showMsg);
	}
	function handleOK(v)
	{
		var mid = frm.matchId.value;
		var aheadSettlePriceType=frm.matchId.value;
		var subbtn7 = false;
		if(confirm("������ϢΪ��\n"+v+"\nȷ����ɴ���"))
		{	
			frm.btn7.disabled= true;
			subbtn7 = true;
		}
		if(subbtn7){
			frm.action="<%=basePath%>servlet/settleMatchController.${POSTFIX}?funcflg=settleHandleOK&matchId="+mid+"&aheadSettlePriceType="+${aheadSettlePriceType}+"&dd="+Date();
			frm.submit();
		}
	}
	
	function auto()
	{
		var mid = frm.matchId.value;
		var cid = document.getElementById("cid").innerText;
		var subbtn18 = false;
		if(confirm("ȷ���������մ������Ϣ��"))
		{	
			frm.btn18.disabled= true;
			subbtn18 = true;
		}
		if(subbtn18){
			frm.action="<%=basePath%>servlet/settleMatchController.${POSTFIX}?funcflg=autoSettleMatch&matchId="+mid+"&contractId="+cid+"&dd="+Date();
			frm.submit();
		}
	}
	
	
	function blankOut()
	{
		var mid = frm.matchId.value;
		var subbtn8 = false;
		if (frm.isChangeOwner.value==1) {
			alert("��Ȩת������ɣ����������ϣ�");
			subbtn8 = false;
		} else {
			if(confirm("ȷ�����ϴ����������ݣ�\n���ѡ��ȷ�����������ݽ����ϣ����䶯�Զ��ع���")){	
				frm.btn8.disabled= true;
				subbtn8 = true;
			}
		}
		if(subbtn8){
			frm.action="<%=basePath%>servlet/settleMatchController.${POSTFIX}?funcflg=settleCancel&matchId="+mid+"&moduleId=${settleMatch.moduleId }";
			frm.submit();
		}
	}
	//���л�Ȩת��
	function doSettleTransfer(){
		var mid = frm.matchId.value;
		if(confirm("��ȷ�Ͻ��л�Ȩת����")){
			frm.action="<%=basePath %>/servlet/settleMatchController.${POSTFIX}?funcflg=settleTransfer&matchId="+mid;
			frm.submit();
		}
	}
	//�ύȷ���Ѿ��ӵ���Ʊ
	function doSubmit(){
		//var matchId=document.getElementById("matchId").value;
		if(window.confirm("��ȷ���յ���Ʊ��"))
		frm.submit();
	}
	
</script>
</html>