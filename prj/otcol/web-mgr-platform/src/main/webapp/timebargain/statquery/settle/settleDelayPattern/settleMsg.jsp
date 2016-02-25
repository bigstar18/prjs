<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="GBK"%>

<%@ page import="java.util.*" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="gnnt.MEBS.timebargain.manage.service.SettleManageDelay" %>
<html>
<head>
<%
	String matchID= (String)request.getParameter("MatchID");
	String settlePageInfo= (String)request.getParameter("settlePageInfo");
	String settlePageSize= (String)request.getParameter("settlePageSize");
	String settleTotalPage= (String)request.getParameter("settleTotalPage");
	String CommodityID = request.getParameter("CommodityID");
	String firmID = request.getParameter("firmID");
	
	if(matchID==null){
		matchID="ST";
	}
	
	String optValue = (String)request.getParameter("opt");
	if(optValue != null){		
		int finishValue = 0;
		if("handleOK".equals(optValue)){//�������
			finishValue= SettleManageDelay.finshSettle(matchID);
		}
		if("blankOut".equals(optValue)){//����
			finishValue= SettleManageDelay.cancelSettle(matchID);
		}
		if(finishValue==1){
		%>
		<script type="text/javascript">
			alert("�����ɹ���");
			//window.location.reload();
			//window.close();
		</script>
		<%
		}else if(finishValue==-1){
		%>
		<script type="text/javascript">
			alert("���ռ�¼״̬���Ϸ���");
			//window.close();
		</script>
		<%
		}else if(finishValue==-2){
		%>
		<script type="text/javascript">
			alert("���������쳣��");
			//window.close();
		</script>
		<%
	}else if(finishValue==-3){
		%>
		<script type="text/javascript">
			alert("Ӧ�������������ʵ���������");
			//window.close();
		</script>
		<%
	}else if(finishValue==-4){
		%>
		<script type="text/javascript">
			alert("ʵ���򷽻���㣡");
			//window.close();
		</script>
		<%
	}
	}
	Map matchMsg = SettleManageDelay.getSettle(matchID);
%>
</head>
<body>
	<form name="frm" method="post">
		<input type="hidden" name="MatchID" value="<%=matchID %>">
		<%
		if(matchMsg!=null)
		{
			//���ݽ�������Ϊ�Ƿ���ʾ��ť�����
			int Status =((BigDecimal)matchMsg.get("Status")).intValue();//״̬�����ϵĲ���ʾ�������
			int matchMsgResult =((BigDecimal)matchMsg.get("Result")).intValue();//������Լ���
			
			boolean sign = (Status>1);//������ɡ�����
			
			String resultMsg = "";
			if(matchMsgResult == 1){
				resultMsg = "��Լ";
			}else if(matchMsgResult == 2){
				resultMsg = "��ΥԼ";
			}else if(matchMsgResult == 3){
				resultMsg = "����ΥԼ";
			}else if(matchMsgResult == 4){
				resultMsg = "˫��ΥԼ";
			}
			boolean TakePenalty_BSign = ((BigDecimal)matchMsg.get("TakePenalty_B")).doubleValue()>0;//����ΥԼ��
			boolean TakePenalty_SSign = ((BigDecimal)matchMsg.get("TakePenalty_S")).doubleValue()>0;//������ΥԼ��
			boolean PayPenalty_BSign = ((BigDecimal)matchMsg.get("PayPenalty_B")).doubleValue()>0;//����ΥԼ��
			boolean PayPenalty_SSign = ((BigDecimal)matchMsg.get("PayPenalty_S")).doubleValue()>0;//������ΥԼ��
		%>
		<input type="hidden" name="Status" value="<%=Status %>">
		<input type="hidden" name="result" value="<%=matchMsgResult %>">
		<input type="hidden" name="resultMsg" value="<%=resultMsg %>">
		<input type="hidden" name="FirmID_B" value="<%=matchMsg.get("FirmID_B") %>">
		<input type="hidden" name="FirmID_S" value="<%=matchMsg.get("FirmID_S") %>">
		<input type="hidden" name="CommodityID" value="<%=matchMsg.get("CommodityID") %>">
		<input type="hidden" name="Quantity" value="<%=matchMsg.get("Quantity") %>">
		<input type="hidden" name="HL_Amount" value="<%=matchMsg.get("HL_Amount") %>">
		<input type="hidden" name="TakePenalty_B" value="<%=matchMsg.get("TakePenalty_B") %>">
		<input type="hidden" name="PayPenalty_S" value="<%=matchMsg.get("PayPenalty_S") %>">
		<input type="hidden" name="SettlePL_B" value="<%=matchMsg.get("SettlePL_B") %>">
		<input type="hidden" name="SettlePL_S" value="<%=matchMsg.get("SettlePL_S") %>">
		<input type="hidden" name="TakePenalty_S" value="<%=matchMsg.get("TakePenalty_S") %>">
		<input type="hidden" name="PayPenalty_B" value="<%=matchMsg.get("PayPenalty_B") %>">		
		<input type="hidden" name="opt">
		
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
					<td align="right" width="15%">��Ʒ���룺</td><td align="left" width="15%"><%=matchMsg.get("CommodityID") %></td>
					<td align="right" width="15%">����������</td><td align="left" width="15%"><%=matchMsg.get("Quantity") %></td>
					<td align="right" width="15%">��Լ�����</td><td align="left" width="15%"><%=resultMsg %></td>
				</tr>
				<tr class="common">
					<td align="right" width="15%">��Ա�ţ�</td><td align="left" width="15%"><%=matchID %></td>
					<td align="right" width="15%">��Լ���ӣ�</td><td align="left" width="15%"><%=matchMsg.get("ContractFactor") %></td>
					<td align="right" width="15%">����״̬��</td>
					<td align="left" width="15%">
						<%
						if(Status == 0){
							out.print("δ����");
						}else if(Status == 1){
							out.print("������");
						}else if(Status == 2){
							out.print("�������");
						}else if(Status == 3){
							out.print("����");
						}
						%>
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
					<td align="right">�򷽽����̴��룺</td><td align="left"><%=matchMsg.get("FirmID_B") %></td>
				</tr>
				<tr class="common">
					<td align="right">�򷽽��ռۣ�</td><td align="left"><fmt:formatNumber value="<%=matchMsg.get("BuyPrice")%>" pattern="#,##0.00"/></td>
				</tr>
				<tr class="common">
					<td align="right">�򷽻�׼���</td><td align="left"><fmt:formatNumber value="<%=matchMsg.get("BuyPayout_Ref")%>" pattern="#,##0.00"/></td>
				</tr>
				<tr class="common">
					<td align="right"><b>������ˮ�����:</b></td><td align="left"><fmt:formatNumber value="<%=((BigDecimal)matchMsg.get("BuyPayout_Ref")).add(((BigDecimal)matchMsg.get("HL_Amount")))%>" pattern="#,##0.00"/></td>
				</tr>
				<tr class="common">
					<td align="right"><b>�х��򷽻��</b></td><td align="left"><fmt:formatNumber value="<%=matchMsg.get("BuyPayout") %>" pattern="#,##0.00"/></td>
				</tr>
				<tr class="common">
					<td align="right">�����ձ�֤��</td><td align="left"><fmt:formatNumber value="<%=matchMsg.get("BuyMargin") %>" pattern="#,##0.00"/></td>
				</tr>
				<%
				if(matchMsgResult!=1){
				%>
				<tr class="common">
					<td align="right">����ΥԼ��</td><td align="left"><fmt:formatNumber value="<%=matchMsg.get("TakePenalty_B") %>" pattern="#,##0.00"/></td>
				</tr>
				<tr class="common">
					<td align="right">����ΥԼ��</td><td align="left"><fmt:formatNumber value="<%=matchMsg.get("PayPenalty_B") %>" pattern="#,##0.00"/></td>
				</tr>
				<tr class="common">
					<td align="right">�򷽽���ӯ����</td><td align="left"><fmt:formatNumber value="<%=matchMsg.get("SettlePL_B") %>" pattern="#,##0.00"/></td>
				</tr>
				<%
				}
				%>
				</table>
			</span>
		</fieldset>
				</td>
				<!-- ������ʾ ��2�� -->
				<td width="2%">
				<table class="common" valign="top" border="0">
				<tr class="common"><td rowspan="9">&nbsp;</td></tr>
				</table>
				</td>
				<!-- ������ʾ ��3�� -->
				<td width="32%">
			<fieldset>
			<legend class="common"><b>������Ϣ</b></legend>
			<span>
				<table class="common" valign="top" border="0">	
				<tr class="common">
					<td align="right">���������̴��룺</td><td align="left"><%=matchMsg.get("FirmID_S") %></td>
				</tr>
				<tr class="common">
					<td align="right">�������ռۣ�</td><td align="left"><fmt:formatNumber value="<%=matchMsg.get("SellPrice") %>" pattern="#,##0.00"/></td>
				</tr>
				<tr class="common">
					<td align="right">������׼���</td><td align="left"><fmt:formatNumber value="<%=matchMsg.get("SellIncome_Ref") %>" pattern="#,##0.00"/></td>
				</tr>
				<tr class="common">
					<td align="right"><b>��������ˮ�����:</b></td><td align="left"><fmt:formatNumber value="<%=((BigDecimal)matchMsg.get("SellIncome_Ref")).add(((BigDecimal)matchMsg.get("HL_Amount"))) %>" pattern="#,##0.00"/></td>
				</tr>
				<tr class="common">
					<td align="right"><b>�Ѹ��������</b></td><td align="left"><fmt:formatNumber value="<%=matchMsg.get("SellIncome") %>" pattern="#,##0.00"/></td>
				</tr>
				<tr class="common">
					<td align="right">�������ձ�֤��</td><td align="left"><fmt:formatNumber value="<%=matchMsg.get("SellMargin") %>" pattern="#,##0.00"/></td>
				</tr>
				<%
				if(matchMsgResult!=1){
				%>
				<tr class="common">
					<td align="right">������ΥԼ��</td><td align="left"><fmt:formatNumber value="<%=matchMsg.get("TakePenalty_S") %>" pattern="#,##0.00"/></td>
				</tr>
				<tr class="common">
					<td align="right">������ΥԼ��</td><td align="left"><fmt:formatNumber value="<%=matchMsg.get("PayPenalty_S") %>" pattern="#,##0.00"/></td>
				</tr>
				<tr class="common">
					<td align="right">��������ӯ����</td><td align="left"><fmt:formatNumber value="<%=matchMsg.get("SettlePL_S") %>" pattern="#,##0.00"/></td>
				</tr>
				<%
				}
				%>
				</table>
			</span>
		</fieldset>
				</td>
				<!-- ������ʾ ��4�� -->
				<td width="2%">
				<table class="common" valign="top" border="0">
				<tr class="common"><td rowspan="9">&nbsp;</td></tr>
				</table>
				</td>
				<!-- ������ʾ ��5�� -->
				<td width="32%">
			<fieldset>
			<legend class="common"><b>������Ϣ</b></legend>
			<span>
				<table class="common" valign="top" border="0">
				<tr class="common">
					<td align="right">�г����</td><td align="left"><fmt:formatNumber value="<%=((BigDecimal)matchMsg.get("SellIncome_Ref")).subtract(((BigDecimal)matchMsg.get("BuyPayout_Ref"))) %>" pattern="#,##0.00"/></td>
				</tr>	
				<tr class="common">
					<td align="right">����ˮ��</td><td align="left"><fmt:formatNumber value="<%=matchMsg.get("HL_Amount") %>" pattern="#,##0.00"/></td>
				</tr>
				<tr class="common">
					<td align="right">&nbsp;</td><td align="left">&nbsp;</td>
				</tr>
				<tr>
					<td align="right">&nbsp;</td><td align="left">&nbsp;</td>
				</tr>
				<tr class="common">
					<td align="right">&nbsp;</td><td align="left">&nbsp;</td>
				</tr>
				<tr class="common">
					<td align="right">&nbsp;</td><td align="left">&nbsp;</td>
				</tr>
				<%
				if(matchMsgResult!=1){
				%>
				<tr class="common">
					<td align="right">&nbsp;</td><td align="left">&nbsp;</td>
				</tr>
				<tr class="common">
					<td align="right">&nbsp;</td><td align="left">&nbsp;</td>
				</tr>
				<tr class="common">
					<td align="right">&nbsp;</td><td align="left">&nbsp;</td>
				</tr>
				<%
				}
				%>
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
								<td align="right" width="25%">����ʱ��:</td><td align="left" width="25%"><fmt:formatDate value="<%=(Date)matchMsg.get("CreateTime")%>" pattern="yyyy-MM-dd HH:mm:ss"/></td>
								<td align="right" width="25%">�޸�ʱ��:</td><td align="left" width="25%"><fmt:formatDate value="<%=(Date)matchMsg.get("ModifyTime")%>" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						</tr>
					</table>					
				</span>
		</fieldset>
				</td></tr>
				
				<tr class="common"><td colspan="5" width="100%">&nbsp;</td></tr>
				
				<tr class="common"><td colspan="5" align="center" width="100%">
				<table class="common" align="center" border="0" width="95%">
				<!-- ����ִ�н��չʾ��ͬ�Ĺ��ܰ�ť -->
			<%
				if(!sign)//δ������� ��δ����
				{
				switch(matchMsgResult)
				{
					case 1://��Լ: �ջ���  ������ ����ˮ  �������  ���� ����
				%>
				<tr class="common">
					<td colspan="7" align="center">
					<input name="btn1" type="button" class="button" value="��֤��ת����" onclick="event(13);">&nbsp;&nbsp;
					<input name="btn2" type="button" class="button" value="�ջ���" onclick="event(1);">&nbsp;&nbsp;
					<input name="btn2" type="button" class="button" value="������" onclick="event(2);">&nbsp;&nbsp;
					<input name="btn10" type="button" class="button" value="����ˮ" onclick="event(10);">&nbsp;&nbsp;
					<input name="btn7" type="button" class="button" value="�������" onclick="event(7);">&nbsp;&nbsp;
					<input name="btn8" type="button" class="button" value="����" onclick="event(8);" <%if(matchID.indexOf("ATS")>=0){out.print("disabled='disabled'");} %>>&nbsp;&nbsp;
					<input name="btn9" type="button" class="button" value="����" onclick="event(9);">					
					</td>
				</tr>
				<%
						break;
					case 2://��ΥԼ����ΥԼ��  ��ΥԼ��  ������� ���� ����������ӯ�� ���ؼ�ע��
				%>
				<tr class="common">
					<td colspan="7" align="center">										
					<input name="btn3" type="button" class="button" value="����ΥԼ��" onclick="event(3);" <%if(TakePenalty_BSign&&false){out.print("disabled='disabled'");} %>>&nbsp;&nbsp;
					<input name="btn4" type="button" class="button" value="������ΥԼ��" onclick="event(4);" <%if(PayPenalty_SSign&&false){out.print("disabled='disabled'");} %>>&nbsp;&nbsp;
					<input name="btn11" type="button" class="button" value="�򷽽���ӯ��" onclick="event(11);">&nbsp;&nbsp;
					<input name="btn12" type="button" class="button" value="��������ӯ��" onclick="event(12);">&nbsp;&nbsp;
					<input name="btn7" type="button" class="button" value="�������" onclick="event(7);">&nbsp;&nbsp;
					<input name="btn8" type="button" class="button" value="����" onclick="event(8);" <%if(matchID.indexOf("ATS")>=0){out.print("disabled='disabled'");} %>>&nbsp;&nbsp;
					<input name="btn9" type="button" class="button" value="����" onclick="event(9);">					
					</td>
				</tr>
				<%		
						break;
					case 3://����ΥԼ����ΥԼ��  ��ΥԼ��  ������� ���� ����������ӯ�� ���ؼ�ע��
				%>
				<tr class="common">
					<td colspan="7" align="center">										
					<input name="btn5" type="button" class="button" value="������ΥԼ��" onclick="event(5);" <%if(TakePenalty_SSign&&false){out.print("disabled='disabled'");} %>>&nbsp;&nbsp;
					<input name="btn6" type="button" class="button" value="����ΥԼ��" onclick="event(6);" <%if(PayPenalty_BSign&&false){out.print("disabled='disabled'");} %>>&nbsp;&nbsp;
					<input name="btn11" type="button" class="button" value="�򷽽���ӯ��" onclick="event(11);">&nbsp;&nbsp;
					<input name="btn12" type="button" class="button" value="��������ӯ��" onclick="event(12);">&nbsp;&nbsp;
					<input name="btn7" type="button" class="button" value="�������" onclick="event(7);">&nbsp;&nbsp;
					<input name="btn8" type="button" class="button" value="����" onclick="event(8);" <%if(matchID.indexOf("ATS")>=0){out.print("disabled='disabled'");} %>>&nbsp;&nbsp;
					<input name="btn9" type="button" class="button" value="����" onclick="event(9);">					
					</td>
				</tr>
				<%		
						break;
					case 4://˫��ΥԼ����������ΥԼ��    ������� ���� ����������ӯ�� ����
				%>
				<tr class="common">
					<td colspan="7" align="center">										
					<input name="btn3" type="button" class="button" value="����ΥԼ��" onclick="event(3);" <%if(TakePenalty_BSign&&false){out.print("disabled='disabled'");} %>>&nbsp;&nbsp;
					<input name="btn5" type="button" class="button" value="������ΥԼ��" onclick="event(5);" <%if(TakePenalty_SSign&&false){out.print("disabled='disabled'");} %>>&nbsp;&nbsp;
					<input name="btn11" type="button" class="button" value="�򷽽���ӯ��" onclick="event(11);">&nbsp;&nbsp;
					<input name="btn12" type="button" class="button" value="��������ӯ��" onclick="event(12);">&nbsp;&nbsp;
					<input name="btn7" type="button" class="button" value="�������" onclick="event(7);">&nbsp;&nbsp;
					<input name="btn8" type="button" class="button" value="����" onclick="event(8);" <%if(matchID.indexOf("ATS")>=0){out.print("disabled='disabled'");} %>>&nbsp;&nbsp;
					<input name="btn9" type="button" class="button" value="����" onclick="event(9);">					
					</td>
				</tr>
				<%		
						break;
				}				
				}
				else//������� �� ����
				{
				%>
				<tr class="common">
					<td colspan="7" align="center">
					<input name="btn7" type="button" class="button" value="�������" onclick="event(7);" disabled="disabled">&nbsp;&nbsp;
					<input name="btn8" type="button" class="button" value="����" onclick="event(8);" disabled="disabled">&nbsp;&nbsp;
					<input name="btn9" type="button" class="button" value="����" onclick="event(9);">										
					</td>
				</tr>	
				<%
				}
				%>
				<tr class="common">
					<td align="left" width="100%" colspan="7">
						<font color="red">
					ע������Ϊ��(0.01Ԫ)���������Ȳ��ֽ����������롣<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��ȡΥԼ��ʱ���Զ��������ձ�֤��
					</font>
					<%
					if(Status==3){
					%>
					<font color="green">
					<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;����������Ϣ�ѱ����ϣ����в������ѳ�����
					</font>
					<%
					}
					%>
					</td>
				</tr>
			</table>
			</td>
			</tr>			
			</table>
			</span>
		</fieldset>
		<%
		}
		%>
	</form>
</body>
<script type="text/javascript">
	function event(v)
	{
		document.getElementsByName("btn"+v).disabled="disabled";
		var mid = frm.MatchID.value;
		switch(v)
		{
			case 1:
				dispatchMethod("incomePayMent.jsp?matchID="+mid+"&d="+Date());
				break;
			case 2:
				dispatchMethod("payPayMent.jsp?matchID="+mid+"&d="+Date());
				break;
			case 3:
				dispatchMethod("incomeFromBuyer.jsp?matchID="+mid+"&d="+Date());
				break;
			case 4:
				dispatchMethod("payToSeller.jsp?matchID="+mid+"&d="+Date());
				break;
			case 5:
				dispatchMethod("incomeFromSeller.jsp?matchID="+mid+"&d="+Date());
				break;
			case 6:
				dispatchMethod("payToBuyer.jsp?matchID="+mid+"&d="+Date());
				break;
			case 7:
				var statusValue = frm.result.value;
				fufillHandle(statusValue);
				break;
			case 8:
				blankOut();
				break;
			case 9:
				window.location.href="settle.jsp?settlePageInfo=<%=settlePageInfo%>&settlePageSize=<%=settlePageSize%>&settleTotalPage=<%=settleTotalPage%>&CommodityID=<%=CommodityID%>&firmID=<%=firmID%>";
				break;
			case 10:
				dispatchMethod("HL_Amount.jsp?matchID="+mid+"&d="+Date());
				break;
			case 11:
				dispatchMethod("SettlePL_B.jsp?matchID="+mid+"&d="+Date());
				break;
			case 12:
				dispatchMethod("SettlePL_S.jsp?matchID="+mid+"&d="+Date());
				break;
			case 13:
				dispatchMethod("bailToPayment.jsp?matchID="+mid+"&d="+Date());
				break;
		}
	}
	function dispatchMethod(v)
	{
		var result = window.showModalDialog(v,"", 
					"dialogWidth=420px; dialogHeight=390px; status=no;scroll=yes;help=no;");
		if(result){
			window.location.href="settleMsg.jsp?MatchID=<%=matchID%>&settlePageInfo=<%=settlePageInfo%>&settlePageSize=<%=settlePageSize%>&settleTotalPage=<%=settleTotalPage%>&CommodityID=<%=CommodityID%>&firmID=<%=firmID%>";
		}
	}
	function fufillHandle(v)
	{
		var resultMsg = frm.resultMsg.value;//ΥԼ��Ϣ
		var FirmID_B = frm.FirmID_B.value;//��
		var FirmID_S = frm.FirmID_S.value;//����
		var CommodityID = frm.CommodityID.value;//��Ʒ����
		var Quantity = frm.Quantity.value;//��������
		var HL_Amount = frm.HL_Amount.value;//����ˮ
		var TakePenalty_B = frm.TakePenalty_B.value;//����ΥԼ��
		var PayPenalty_S = frm.PayPenalty_S.value;//������ΥԼ��
		var SettlePL_B = frm.SettlePL_B.value;//�򷽽���ӯ��
		var SettlePL_S = frm.SettlePL_S.value;//��������ӯ��
		var TakePenalty_S = frm.TakePenalty_S.value;//������ΥԼ��
		var PayPenalty_B = frm.PayPenalty_B.value;//����ΥԼ��
		
		var showMsg="";
		switch (v)
		{
			case '1'://��Լ
				showMsg="�򷽴��룺"+FirmID_B+",�������룺"+FirmID_S+
					"\n��Ʒ���룺"+CommodityID+",����ˮ��"+HL_Amount;
				break;
			case '2'://��ΥԼ
				showMsg="�򷽴��룺"+FirmID_B+",�������룺"+FirmID_S+
					"\n��Ʒ���룺"+CommodityID+",����������"+Quantity+
					"\n����ΥԼ��"+TakePenalty_B+",������ΥԼ��"+PayPenalty_S+
					"\n�򷽽���ӯ����"+SettlePL_B+",��������ӯ����"+SettlePL_S;
				break;
			case '3'://����ΥԼ
				showMsg="�򷽴��룺"+FirmID_B+",�������룺"+FirmID_S+
					"\n��Ʒ���룺"+CommodityID+",����������"+Quantity+
					"\n������ΥԼ��"+TakePenalty_S+",����ΥԼ��"+PayPenalty_B+
					"\n�򷽽���ӯ����"+SettlePL_B+",��������ӯ����"+SettlePL_S;
				break;
			case '4'://˫��ΥԼ
				showMsg="�򷽴��룺"+FirmID_B+",�������룺"+FirmID_S+
					"\n��Ʒ���룺"+CommodityID+",����������"+Quantity+
					"\n����ΥԼ��"+TakePenalty_B+",������ΥԼ��"+TakePenalty_S+
					"\n�򷽽���ӯ����"+SettlePL_B+",��������ӯ����"+SettlePL_S;
				break;
		}
		handleOK(showMsg);
	}
	function handleOK(v)
	{
		var subbtn7 = false;
		if(confirm("������ϢΪ��\n"+v+"\nȷ����ɴ���"))
		{	
			frm.btn7.disabled= true;
			frm.opt.value="handleOK";
			subbtn7 = true;
		}
		if(subbtn7){
			frm.submit();
		}
	}
	function blankOut()
	{
		var subbtn8 = false;
		if(confirm("ȷ�����ϴ����������ݣ�\n���ѡ��ȷ�����������ݽ����ϣ����䶯�Զ��ع���"))
		{	
			frm.btn8.disabled= true;
			frm.opt.value="blankOut";
			subbtn8 = true;
		}
		if(subbtn8){
			frm.submit();
		}
	}
</script>
</html>