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
		if("handleOK".equals(optValue)){//处理完成
			finishValue= SettleManageDelay.finshSettle(matchID);
		}
		if("blankOut".equals(optValue)){//作废
			finishValue= SettleManageDelay.cancelSettle(matchID);
		}
		if(finishValue==1){
		%>
		<script type="text/javascript">
			alert("操作成功！");
			//window.location.reload();
			//window.close();
		</script>
		<%
		}else if(finishValue==-1){
		%>
		<script type="text/javascript">
			alert("交收记录状态不合法！");
			//window.close();
		</script>
		<%
		}else if(finishValue==-2){
		%>
		<script type="text/javascript">
			alert("操作产生异常！");
			//window.close();
		</script>
		<%
	}else if(finishValue==-3){
		%>
		<script type="text/javascript">
			alert("应付卖方货款不等于实付卖方货款！");
			//window.close();
		</script>
		<%
	}else if(finishValue==-4){
		%>
		<script type="text/javascript">
			alert("实收买方货款不足！");
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
			//根据金额的正负为是否显示按钮做标记
			int Status =((BigDecimal)matchMsg.get("Status")).intValue();//状态，作废的不显示处理完成
			int matchMsgResult =((BigDecimal)matchMsg.get("Result")).intValue();//交收履约情况
			
			boolean sign = (Status>1);//处理完成、作废
			
			String resultMsg = "";
			if(matchMsgResult == 1){
				resultMsg = "履约";
			}else if(matchMsgResult == 2){
				resultMsg = "买方违约";
			}else if(matchMsgResult == 3){
				resultMsg = "卖方违约";
			}else if(matchMsgResult == 4){
				resultMsg = "双方违约";
			}
			boolean TakePenalty_BSign = ((BigDecimal)matchMsg.get("TakePenalty_B")).doubleValue()>0;//收买方违约金
			boolean TakePenalty_SSign = ((BigDecimal)matchMsg.get("TakePenalty_S")).doubleValue()>0;//收卖方违约金
			boolean PayPenalty_BSign = ((BigDecimal)matchMsg.get("PayPenalty_B")).doubleValue()>0;//付买方违约金
			boolean PayPenalty_SSign = ((BigDecimal)matchMsg.get("PayPenalty_S")).doubleValue()>0;//付卖方违约金
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
			<legend class="common"><b>交收配对信息</b></legend>
			<span>
			<table class="common" align="center" width="100%" height="100%" border="0">
			<tr class="common">
				<td align="center" colspan="5" width="100%">
			<fieldset>
			<legend class="common"><b>商品信息</b></legend>
			<span>
				<table class="common" align="center" width="90%">
				<tr class="common">
					<td align="right" width="15%">商品代码：</td><td align="left" width="15%"><%=matchMsg.get("CommodityID") %></td>
					<td align="right" width="15%">交收数量：</td><td align="left" width="15%"><%=matchMsg.get("Quantity") %></td>
					<td align="right" width="15%">履约情况：</td><td align="left" width="15%"><%=resultMsg %></td>
				</tr>
				<tr class="common">
					<td align="right" width="15%">配对编号：</td><td align="left" width="15%"><%=matchID %></td>
					<td align="right" width="15%">合约因子：</td><td align="left" width="15%"><%=matchMsg.get("ContractFactor") %></td>
					<td align="right" width="15%">交收状态：</td>
					<td align="left" width="15%">
						<%
						if(Status == 0){
							out.print("未处理");
						}else if(Status == 1){
							out.print("处理中");
						}else if(Status == 2){
							out.print("处理完成");
						}else if(Status == 3){
							out.print("作废");
						}
						%>
					</td>
				</tr>
				</table>
			</span>
		</fieldset>
				</td></tr>
				<tr class="common">
				<!-- 横向显示 第1列 -->
				<td width="32%">
			<fieldset>
			<legend class="common"><b>买方信息</b></legend>
			<span>
				<table class="common" valign="top" border="0">
				<tr class="common">
					<td align="right">买方交易商代码：</td><td align="left"><%=matchMsg.get("FirmID_B") %></td>
				</tr>
				<tr class="common">
					<td align="right">买方交收价：</td><td align="left"><fmt:formatNumber value="<%=matchMsg.get("BuyPrice")%>" pattern="#,##0.00"/></td>
				</tr>
				<tr class="common">
					<td align="right">买方基准货款：</td><td align="left"><fmt:formatNumber value="<%=matchMsg.get("BuyPayout_Ref")%>" pattern="#,##0.00"/></td>
				</tr>
				<tr class="common">
					<td align="right"><b>买方升贴水后货款:</b></td><td align="left"><fmt:formatNumber value="<%=((BigDecimal)matchMsg.get("BuyPayout_Ref")).add(((BigDecimal)matchMsg.get("HL_Amount")))%>" pattern="#,##0.00"/></td>
				</tr>
				<tr class="common">
					<td align="right"><b>已収买方货款：</b></td><td align="left"><fmt:formatNumber value="<%=matchMsg.get("BuyPayout") %>" pattern="#,##0.00"/></td>
				</tr>
				<tr class="common">
					<td align="right">买方已收保证金：</td><td align="left"><fmt:formatNumber value="<%=matchMsg.get("BuyMargin") %>" pattern="#,##0.00"/></td>
				</tr>
				<%
				if(matchMsgResult!=1){
				%>
				<tr class="common">
					<td align="right">收买方违约金：</td><td align="left"><fmt:formatNumber value="<%=matchMsg.get("TakePenalty_B") %>" pattern="#,##0.00"/></td>
				</tr>
				<tr class="common">
					<td align="right">付买方违约金：</td><td align="left"><fmt:formatNumber value="<%=matchMsg.get("PayPenalty_B") %>" pattern="#,##0.00"/></td>
				</tr>
				<tr class="common">
					<td align="right">买方交收盈亏：</td><td align="left"><fmt:formatNumber value="<%=matchMsg.get("SettlePL_B") %>" pattern="#,##0.00"/></td>
				</tr>
				<%
				}
				%>
				</table>
			</span>
		</fieldset>
				</td>
				<!-- 横向显示 第2列 -->
				<td width="2%">
				<table class="common" valign="top" border="0">
				<tr class="common"><td rowspan="9">&nbsp;</td></tr>
				</table>
				</td>
				<!-- 横向显示 第3列 -->
				<td width="32%">
			<fieldset>
			<legend class="common"><b>卖方信息</b></legend>
			<span>
				<table class="common" valign="top" border="0">	
				<tr class="common">
					<td align="right">卖方交易商代码：</td><td align="left"><%=matchMsg.get("FirmID_S") %></td>
				</tr>
				<tr class="common">
					<td align="right">卖方交收价：</td><td align="left"><fmt:formatNumber value="<%=matchMsg.get("SellPrice") %>" pattern="#,##0.00"/></td>
				</tr>
				<tr class="common">
					<td align="right">卖方基准货款：</td><td align="left"><fmt:formatNumber value="<%=matchMsg.get("SellIncome_Ref") %>" pattern="#,##0.00"/></td>
				</tr>
				<tr class="common">
					<td align="right"><b>卖方升贴水后货款:</b></td><td align="left"><fmt:formatNumber value="<%=((BigDecimal)matchMsg.get("SellIncome_Ref")).add(((BigDecimal)matchMsg.get("HL_Amount"))) %>" pattern="#,##0.00"/></td>
				</tr>
				<tr class="common">
					<td align="right"><b>已付卖方货款：</b></td><td align="left"><fmt:formatNumber value="<%=matchMsg.get("SellIncome") %>" pattern="#,##0.00"/></td>
				</tr>
				<tr class="common">
					<td align="right">卖方已收保证金：</td><td align="left"><fmt:formatNumber value="<%=matchMsg.get("SellMargin") %>" pattern="#,##0.00"/></td>
				</tr>
				<%
				if(matchMsgResult!=1){
				%>
				<tr class="common">
					<td align="right">收卖方违约金：</td><td align="left"><fmt:formatNumber value="<%=matchMsg.get("TakePenalty_S") %>" pattern="#,##0.00"/></td>
				</tr>
				<tr class="common">
					<td align="right">付卖方违约金：</td><td align="left"><fmt:formatNumber value="<%=matchMsg.get("PayPenalty_S") %>" pattern="#,##0.00"/></td>
				</tr>
				<tr class="common">
					<td align="right">卖方交收盈亏：</td><td align="left"><fmt:formatNumber value="<%=matchMsg.get("SettlePL_S") %>" pattern="#,##0.00"/></td>
				</tr>
				<%
				}
				%>
				</table>
			</span>
		</fieldset>
				</td>
				<!-- 横向显示 第4列 -->
				<td width="2%">
				<table class="common" valign="top" border="0">
				<tr class="common"><td rowspan="9">&nbsp;</td></tr>
				</table>
				</td>
				<!-- 横向显示 第5列 -->
				<td width="32%">
			<fieldset>
			<legend class="common"><b>其他信息</b></legend>
			<span>
				<table class="common" valign="top" border="0">
				<tr class="common">
					<td align="right">市场货款：</td><td align="left"><fmt:formatNumber value="<%=((BigDecimal)matchMsg.get("SellIncome_Ref")).subtract(((BigDecimal)matchMsg.get("BuyPayout_Ref"))) %>" pattern="#,##0.00"/></td>
				</tr>	
				<tr class="common">
					<td align="right">升贴水：</td><td align="left"><fmt:formatNumber value="<%=matchMsg.get("HL_Amount") %>" pattern="#,##0.00"/></td>
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
			<legend class="common"><b>创建修改时间</b></legend>
			<span>
					<table class="common" align="left">
						<tr class="common">
								<td align="right" width="25%">创建时间:</td><td align="left" width="25%"><fmt:formatDate value="<%=(Date)matchMsg.get("CreateTime")%>" pattern="yyyy-MM-dd HH:mm:ss"/></td>
								<td align="right" width="25%">修改时间:</td><td align="left" width="25%"><fmt:formatDate value="<%=(Date)matchMsg.get("ModifyTime")%>" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						</tr>
					</table>					
				</span>
		</fieldset>
				</td></tr>
				
				<tr class="common"><td colspan="5" width="100%">&nbsp;</td></tr>
				
				<tr class="common"><td colspan="5" align="center" width="100%">
				<table class="common" align="center" border="0" width="95%">
				<!-- 根据执行结果展示不同的功能按钮 -->
			<%
				if(!sign)//未处理完成 、未作废
				{
				switch(matchMsgResult)
				{
					case 1://履约: 收货款  付货款 升贴水  处理完成  作废 返回
				%>
				<tr class="common">
					<td colspan="7" align="center">
					<input name="btn1" type="button" class="button" value="保证金转货款" onclick="event(13);">&nbsp;&nbsp;
					<input name="btn2" type="button" class="button" value="收货款" onclick="event(1);">&nbsp;&nbsp;
					<input name="btn2" type="button" class="button" value="付货款" onclick="event(2);">&nbsp;&nbsp;
					<input name="btn10" type="button" class="button" value="升贴水" onclick="event(10);">&nbsp;&nbsp;
					<input name="btn7" type="button" class="button" value="处理完成" onclick="event(7);">&nbsp;&nbsp;
					<input name="btn8" type="button" class="button" value="作废" onclick="event(8);" <%if(matchID.indexOf("ATS")>=0){out.print("disabled='disabled'");} %>>&nbsp;&nbsp;
					<input name="btn9" type="button" class="button" value="返回" onclick="event(9);">					
					</td>
				</tr>
				<%
						break;
					case 2://买方违约：收违约金  付违约金  处理完成 作废 买卖方交收盈亏 返回加注释
				%>
				<tr class="common">
					<td colspan="7" align="center">										
					<input name="btn3" type="button" class="button" value="收买方违约金" onclick="event(3);" <%if(TakePenalty_BSign&&false){out.print("disabled='disabled'");} %>>&nbsp;&nbsp;
					<input name="btn4" type="button" class="button" value="付卖方违约金" onclick="event(4);" <%if(PayPenalty_SSign&&false){out.print("disabled='disabled'");} %>>&nbsp;&nbsp;
					<input name="btn11" type="button" class="button" value="买方交收盈亏" onclick="event(11);">&nbsp;&nbsp;
					<input name="btn12" type="button" class="button" value="卖方交收盈亏" onclick="event(12);">&nbsp;&nbsp;
					<input name="btn7" type="button" class="button" value="处理完成" onclick="event(7);">&nbsp;&nbsp;
					<input name="btn8" type="button" class="button" value="作废" onclick="event(8);" <%if(matchID.indexOf("ATS")>=0){out.print("disabled='disabled'");} %>>&nbsp;&nbsp;
					<input name="btn9" type="button" class="button" value="返回" onclick="event(9);">					
					</td>
				</tr>
				<%		
						break;
					case 3://卖方违约：收违约金  付违约金  处理完成 作废 买卖方交收盈亏 返回加注释
				%>
				<tr class="common">
					<td colspan="7" align="center">										
					<input name="btn5" type="button" class="button" value="收卖方违约金" onclick="event(5);" <%if(TakePenalty_SSign&&false){out.print("disabled='disabled'");} %>>&nbsp;&nbsp;
					<input name="btn6" type="button" class="button" value="付买方违约金" onclick="event(6);" <%if(PayPenalty_BSign&&false){out.print("disabled='disabled'");} %>>&nbsp;&nbsp;
					<input name="btn11" type="button" class="button" value="买方交收盈亏" onclick="event(11);">&nbsp;&nbsp;
					<input name="btn12" type="button" class="button" value="卖方交收盈亏" onclick="event(12);">&nbsp;&nbsp;
					<input name="btn7" type="button" class="button" value="处理完成" onclick="event(7);">&nbsp;&nbsp;
					<input name="btn8" type="button" class="button" value="作废" onclick="event(8);" <%if(matchID.indexOf("ATS")>=0){out.print("disabled='disabled'");} %>>&nbsp;&nbsp;
					<input name="btn9" type="button" class="button" value="返回" onclick="event(9);">					
					</td>
				</tr>
				<%		
						break;
					case 4://双方违约：收买卖方违约金    处理完成 作废 买卖方交收盈亏 返回
				%>
				<tr class="common">
					<td colspan="7" align="center">										
					<input name="btn3" type="button" class="button" value="收买方违约金" onclick="event(3);" <%if(TakePenalty_BSign&&false){out.print("disabled='disabled'");} %>>&nbsp;&nbsp;
					<input name="btn5" type="button" class="button" value="收卖方违约金" onclick="event(5);" <%if(TakePenalty_SSign&&false){out.print("disabled='disabled'");} %>>&nbsp;&nbsp;
					<input name="btn11" type="button" class="button" value="买方交收盈亏" onclick="event(11);">&nbsp;&nbsp;
					<input name="btn12" type="button" class="button" value="卖方交收盈亏" onclick="event(12);">&nbsp;&nbsp;
					<input name="btn7" type="button" class="button" value="处理完成" onclick="event(7);">&nbsp;&nbsp;
					<input name="btn8" type="button" class="button" value="作废" onclick="event(8);" <%if(matchID.indexOf("ATS")>=0){out.print("disabled='disabled'");} %>>&nbsp;&nbsp;
					<input name="btn9" type="button" class="button" value="返回" onclick="event(9);">					
					</td>
				</tr>
				<%		
						break;
				}				
				}
				else//处理完成 、 作废
				{
				%>
				<tr class="common">
					<td colspan="7" align="center">
					<input name="btn7" type="button" class="button" value="处理完成" onclick="event(7);" disabled="disabled">&nbsp;&nbsp;
					<input name="btn8" type="button" class="button" value="作废" onclick="event(8);" disabled="disabled">&nbsp;&nbsp;
					<input name="btn9" type="button" class="button" value="返回" onclick="event(9);">										
					</td>
				</tr>	
				<%
				}
				%>
				<tr class="common">
					<td align="left" width="100%" colspan="7">
						<font color="red">
					注：金额精度为分(0.01元)，超过精度部分将被四舍五入。<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;收取违约金时会自动返还交收保证金。
					</font>
					<%
					if(Status==3){
					%>
					<font color="green">
					<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;该条交收消息已被作废，所有操作均已撤销。
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
		var resultMsg = frm.resultMsg.value;//违约信息
		var FirmID_B = frm.FirmID_B.value;//买方
		var FirmID_S = frm.FirmID_S.value;//卖方
		var CommodityID = frm.CommodityID.value;//商品代码
		var Quantity = frm.Quantity.value;//交收数量
		var HL_Amount = frm.HL_Amount.value;//升贴水
		var TakePenalty_B = frm.TakePenalty_B.value;//收买方违约金
		var PayPenalty_S = frm.PayPenalty_S.value;//付卖方违约金
		var SettlePL_B = frm.SettlePL_B.value;//买方交收盈亏
		var SettlePL_S = frm.SettlePL_S.value;//卖方交收盈亏
		var TakePenalty_S = frm.TakePenalty_S.value;//收卖方违约金
		var PayPenalty_B = frm.PayPenalty_B.value;//付买方违约金
		
		var showMsg="";
		switch (v)
		{
			case '1'://履约
				showMsg="买方代码："+FirmID_B+",卖方代码："+FirmID_S+
					"\n商品代码："+CommodityID+",升贴水："+HL_Amount;
				break;
			case '2'://买方违约
				showMsg="买方代码："+FirmID_B+",卖方代码："+FirmID_S+
					"\n商品代码："+CommodityID+",交收数量："+Quantity+
					"\n收买方违约金："+TakePenalty_B+",付卖方违约金："+PayPenalty_S+
					"\n买方交收盈亏："+SettlePL_B+",卖方交收盈亏："+SettlePL_S;
				break;
			case '3'://卖方违约
				showMsg="买方代码："+FirmID_B+",卖方代码："+FirmID_S+
					"\n商品代码："+CommodityID+",交收数量："+Quantity+
					"\n收卖方违约金："+TakePenalty_S+",付买方违约金："+PayPenalty_B+
					"\n买方交收盈亏："+SettlePL_B+",卖方交收盈亏："+SettlePL_S;
				break;
			case '4'://双方违约
				showMsg="买方代码："+FirmID_B+",卖方代码："+FirmID_S+
					"\n商品代码："+CommodityID+",交收数量："+Quantity+
					"\n收买方违约金："+TakePenalty_B+",收卖方违约金："+TakePenalty_S+
					"\n买方交收盈亏："+SettlePL_B+",卖方交收盈亏："+SettlePL_S;
				break;
		}
		handleOK(showMsg);
	}
	function handleOK(v)
	{
		var subbtn7 = false;
		if(confirm("交收信息为：\n"+v+"\n确定完成处理？"))
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
		if(confirm("确定作废此条交收数据？\n如果选择确定，交收数据将作废，金额变动自动回滚！"))
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