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
					<td align="right" width="15%">配对编号：</td><td align="left" width="15%">${settleMatch.matchId}</td>
					<td align="right" width="15%">模块：</td>
					<td align="left" width="15%"><c:out value="${moduleNameMap[settleMatch.moduleId]}" /></td>
					<td align="right" width="15%">履约情况：</td>
					<td align="left" width="15%">
						<c:choose>
							<c:when test="${settleMatch.result==1}">履约</c:when>
							<c:when test="${settleMatch.result==2}">买方违约</c:when>
							<c:when test="${settleMatch.result==3}">卖方违约</c:when>
							<c:when test="${settleMatch.result==4}">双方违约</c:when>
						</c:choose>
					</td>
				</tr>
				<tr class="common">
					<td align="right" width="15%">品种代码：</td><td align="left" width="15%">${settleMatch.breedId}</td>
					<td align="right" width="15%">交收数量：</td><td align="left" width="15%">${settleMatch.weight}</td>
					<td align="right" width="15%">交收状态：</td>
					<td align="left" width="15%"><font color="red">
						<c:choose>
							<c:when test="${settleMatch.status==0}">未处理</c:when>
							<c:when test="${settleMatch.status==1}">处理中</c:when>
							<c:when test="${settleMatch.status==2}">处理完成</c:when>
							<c:when test="${settleMatch.status==3}">作废</c:when>
						</c:choose></font>
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
					<td align="right">买方交易商代码：</td><td align="left">${settleMatch.firmID_B}</td>
				</tr>
				<tr class="common">
					<td align="right"><b>可用资金：</b></td><td align="left"><fmt:formatNumber value="${buyBalance}" pattern="#,##0.00"/></td>
				</tr>
				<tr class="common">
					<td align="right">买方交收价：</td><td align="left">
					<c:choose>
						<c:when test="${fn:contains(xml,'ATS_')}">  <!-- 提前交收 -->
							<c:if test="${aheadSettlePriceType == 1}">  <!-- 按订立价 -->
								<fmt:formatNumber value="${settleMatch.buyPrice}" pattern="#,##0.00"/>
							</c:if>
							<c:if test="${aheadSettlePriceType == 0}">   <!-- 按自主价 -->
								<a href="javaScript: showSettlePrice(1)">查看</a>
							</c:if>
						</c:when>
						<c:otherwise><!-- 按期交收或者递延交收 -->
							<c:if test="${settlePriceType != 2}">
								<fmt:formatNumber value="${settleMatch.buyPrice}" pattern="#,##0.00"/>
							</c:if>
							<c:if test="${settlePriceType == 2}">
								<a href="javaScript: showSettlePrice(1)">查看</a>
							</c:if>
						</c:otherwise>
					</c:choose>
					
					</td>
				</tr>
				<tr class="common">
					<td align="right">买方基准货款：</td><td align="left"><fmt:formatNumber value="${settleMatch.buyPayout_Ref}" pattern="#,##0.00"/></td>
				</tr>
				<tr class="common">
					<td align="right"><b>买方升贴水后货款：</b></td><td align="left">
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
					<td align="right"><b>已收买方货款：</b></td><td align="left"><fmt:formatNumber value="${settleMatch.buyPayout}" pattern="#,##0.00"/></td>
				</tr>
				<tr class="common">
					<td align="right">已收买方保证金：</td><td align="left"><fmt:formatNumber value="${settleMatch.buyMargin}" pattern="#,##0.00"/></td>
				</tr>
				<c:if test="${settleMatch.result!=1}">
					<c:choose>
						<c:when test="${settleMatch.result==2||settleMatch.result==4}">
							<tr class="common">
								<td align="right">收买方违约金：</td><td align="left"><fmt:formatNumber value="${settleMatch.penalty_B}" pattern="#,##0.00"/></td>
							</tr>
						</c:when>
						<c:when test="${settleMatch.result==3}">
							<tr class="common">
								<td align="right">付买方违约金：</td><td align="left"><fmt:formatNumber value="${settleMatch.penalty_B}" pattern="#,##0.00"/></td>
							</tr>
						</c:when>
					</c:choose>
					<tr class="common">
						<td align="right">买方交收盈亏：</td><td align="left"><fmt:formatNumber value="${settleMatch.settlePL_B}" pattern="#,##0.00"/></td>
					</tr>
				</c:if>
				</table>
			</span>
		</fieldset>
				</td>
				<!-- 横向显示 第2列 -->
				<td width="2%">
				<table class="common" valign="top" border="0">
				<tr class="common"><td rowspan="10">&nbsp;</td></tr>
				</table>
				</td>
				<!-- 横向显示 第3列 -->
				<td width="32%">
			<fieldset>
			<legend class="common"><b>卖方信息</b></legend>
			<span>
				<table class="common" valign="top" border="0">	
				<tr class="common">
					<td align="right">卖方交易商代码：</td><td align="left">${settleMatch.firmID_S}</td>
				</tr>
				<tr class="common">
					<td align="right">卖方交收价：</td><td align="left">
						<c:choose>
						<c:when test="${fn:contains(xml,'ATS_')}">  <!-- 提前交收 -->
							<c:if test="${aheadSettlePriceType == 1}">  <!-- 按订立价 -->
								<fmt:formatNumber value="${settleMatch.sellPrice}" pattern="#,##0.00"/>
							</c:if>
							<c:if test="${aheadSettlePriceType == 0}">   <!-- 按自主价 -->
								<a href="javaScript: showSettlePrice(2)">查看</a>
							</c:if>
						</c:when>
						<c:otherwise><!-- 按期交收或者递延交收 -->
							<c:if test="${settlePriceType != 2}">
								<fmt:formatNumber value="${settleMatch.sellPrice}" pattern="#,##0.00"/>
							</c:if>
							<c:if test="${settlePriceType == 2}">
								<a href="javaScript: showSettlePrice(2)">查看</a>
							</c:if>
						</c:otherwise>
					</c:choose>
					</td>
				</tr>
				<tr class="common">
					<td align="right">卖方基准货款：</td><td align="left"><fmt:formatNumber value="${settleMatch.sellIncome_Ref}" pattern="#,##0.00"/></td>
				</tr>
				<tr class="common">
					<td align="right"><b>卖方升贴水后货款：</b></td><td align="left"><fmt:formatNumber value="${settleMatch.sellIncome_Ref+settleMatch.HL_Amount}" pattern="#,##0.00"/></td>
				</tr>
				<tr class="common">
					<td align="right"><b>已付卖方货款：</b></td><td align="left"><fmt:formatNumber value="${settleMatch.sellIncome}" pattern="#,##0.00"/></td>
				</tr>
				<tr class="common">
					<td align="right">已收卖方保证金：</td><td align="left"><fmt:formatNumber value="${settleMatch.sellMargin}" pattern="#,##0.00"/></td>
				</tr>
				<c:if test="${settleMatch.result!=1}">
					<c:choose>
						<c:when test="${settleMatch.result==3||settleMatch.result==4}">
							<tr class="common">
								<td align="right">收卖方违约金：</td><td align="left"><fmt:formatNumber value="${settleMatch.penalty_S}" pattern="#,##0.00"/></td>
							</tr>
						</c:when>
						<c:when test="${settleMatch.result==2}">
							<tr class="common">
								<td align="right">付卖方违约金：</td><td align="left"><fmt:formatNumber value="${settleMatch.penalty_S}" pattern="#,##0.00"/></td>
							</tr>
						</c:when>
					</c:choose>
					<tr class="common">
						<td align="right">卖方交收盈亏：</td><td align="left"><fmt:formatNumber value="${settleMatch.settlePL_S}" pattern="#,##0.00"/></td>
					</tr>
				</c:if>
				<tr class="common">
					<td align="right">&nbsp;</td><td align="left">&nbsp;</td>
				</tr>
				</table>
			</span>
		</fieldset>
				</td>
				<!-- 横向显示 第4列 -->
				<td width="2%">
				<table class="common" valign="top" border="0">
				<tr class="common"><td rowspan="10">&nbsp;</td></tr>
				</table>
				</td>
				<!-- 横向显示 第5列 -->
				<td width="32%">
			<fieldset>
			<legend class="common"><b>其他信息</b></legend>
			<span>
				<table class="common" valign="top" border="0">	
				<tr class="common">
					<td align="right">升贴水：</td><td align="left"><fmt:formatNumber value="${settleMatch.HL_Amount }" pattern="#,##0.00"/></td>
				</tr>
				<tr class="common">
					<td align="right">市场货款：</td><td align="left"><fmt:formatNumber value="${settleMatch.sellIncome_Ref-settleMatch.buyPayout_Ref }" pattern="#,##0.00"/></td>
				</tr>
				<tr>
					<td align="right">是否收到发票：</td>
					<td align="left">
						<c:choose>
							<c:when test="${settleMatch.recvInvoice==0 }">否</c:when>
							<c:when test="${settleMatch.recvInvoice==1 }">是</c:when>
						</c:choose>
					</td>
				</tr>
				<tr>
					<td align="right">最近操作员：</td>
					<td align="left">
						<c:choose>
							<c:when test="${operator==null }">暂无人员操作</c:when>
							<c:when test="${operator!=null }">${operator}</c:when>
						</c:choose>
					</td>
				</tr>
				<tr>
					<td ><span onclick="test('${settleMatch.matchId }');"  style="cursor:hand;color:blue">查看所有操作记录->></span></td>
				</tr>
				<tr class="common">
					<c:if test="${settleMatch.result == 1}">
					  <td ><span onclick="findStock('${settleMatch.matchId }');"  style="cursor:hand;color:blue">查看配对持仓信息->></span></td>
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
			<legend class="common"><b>创建修改时间</b></legend>
			<span>
					<table class="common" align="left">
						<tr class="common">
								<td align="right" width="25%">创建时间:</td><td align="left" width="25%"><fmt:formatDate value="${settleMatch.createTime }" pattern="yyyy-MM-dd"/></td>
								<td align="right" width="25%">修改时间:</td><td align="left" width="25%"><fmt:formatDate value="${settleMatch.modifyTime }" pattern="yyyy-MM-dd"/></td>
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
				<!-- 根据执行结果展示不同的功能按钮 -->
						<c:if test="${settleMatch.status==1 }">
						<input name="btn14" type="button" class="button" value="还原" onclick="event(14);">&nbsp;&nbsp;
				</c:if>	
			<c:choose>
		
			
				<c:when test="${settleMatch.status<2 }">
					<c:choose>
					<c:when test="${settleMatch.result==1 }">
						<input name="btn2" type="button" class="button" value="收货款" onclick="event(1);">&nbsp;&nbsp;
						<input name="btn2" type="button" class="button" value="付货款" onclick="event(2);">&nbsp;&nbsp;					
						<input name="btn1" type="button" class="button" value="保证金转货款" onclick="event(13);">&nbsp;&nbsp;
						<input name="btn1" type="button" class="button" value="货权转移" onclick="event(20);">&nbsp;&nbsp;					
						<input name="btn10" type="button" class="button" value="升贴水" onclick="event(10);">&nbsp;&nbsp;
						<input name="btn10" type="button" class="button" value="退卖方保证金" onclick="event(17);">&nbsp;&nbsp;
						<input name="btn10" type="button" class="button" value="退买方保证金" onclick="event(18);">&nbsp;&nbsp;
					</c:when>
					<c:when test="${settleMatch.result==2 }">
						<input name="btn3" type="button" class="button" value="收买方违约金" onclick="event(3);">&nbsp;&nbsp;
						<input name="btn4" type="button" class="button" value="付卖方违约金" onclick="event(4);">&nbsp;&nbsp;
						<c:choose>
							<c:when test="${mark==true }">
							<input name="btn11" type="button" class="button" value="买方交收盈亏" onclick="event(11);">&nbsp;&nbsp;
							<input name="btn12" type="button" class="button" value="卖方交收盈亏" onclick="event(12);">&nbsp;&nbsp;
							</c:when>
						</c:choose>
					</c:when>
					<c:when test="${settleMatch.result==3 }">
						<input name="btn5" type="button" class="button" value="收卖方违约金" onclick="event(5);">&nbsp;&nbsp;
						<input name="btn6" type="button" class="button" value="付买方违约金" onclick="event(6);">&nbsp;&nbsp;
						<c:choose>
							<c:when test="${mark==true }">
							<input name="btn11" type="button" class="button" value="买方交收盈亏" onclick="event(11);">&nbsp;&nbsp;
							<input name="btn12" type="button" class="button" value="卖方交收盈亏" onclick="event(12);">&nbsp;&nbsp;
							</c:when>
						</c:choose>
					</c:when>
					<c:when test="${settleMatch.result==4 }">
						<input name="btn3" type="button" class="button" value="收买方违约金" onclick="event(3);">&nbsp;&nbsp;
						<input name="btn5" type="button" class="button" value="收卖方违约金" onclick="event(5);">&nbsp;&nbsp;
						<c:choose>
							<c:when test="${mark==true }">
							<input name="btn11" type="button" class="button" value="买方交收盈亏" onclick="event(11);">&nbsp;&nbsp;
							<input name="btn12" type="button" class="button" value="卖方交收盈亏" onclick="event(12);">&nbsp;&nbsp;
							</c:when>
						</c:choose>
					</c:when>
					</c:choose>
						<input name="btn7" type="button" class="button" value="处理完成" onclick="event(7);">&nbsp;&nbsp;
						<input name="btn8" type="button" class="button" value="作废" onclick="event(8);">&nbsp;&nbsp;
				</c:when>
				<c:when test="${settleMatch.status>1 }">
						<input name="btn7" type="button" class="button" value="处理完成" onclick="event(7);" disabled="disabled">&nbsp;&nbsp;
						<input name="btn8" type="button" class="button" value="作废" onclick="event(8);" disabled="disabled">&nbsp;&nbsp;
				</c:when>
					
			</c:choose>	
					<c:if test="${settleMatch.status==0&&settleMatch.result==1 }">
						<input name="btn18" type="button" class="button" value="自主交收" onclick="event(19);">&nbsp;&nbsp;
				</c:if>
					<c:if test="${settleMatch.status==0 && settleMatch.result==1}">
				
						<input name="btn15" type="button" class="button" value="修改仓单" onclick="event(15);">&nbsp;&nbsp;
				</c:if>	
			<c:if test="${settleMatch.recvInvoice==0 && settleMatch.status!=3 && settleMatch.result==1 && settleMatch.status!=2}">
			<input name="makeSure" id="makeSure" type="button" class="button" value="签收" onclick="doSubmit();">&nbsp;&nbsp;
			</c:if>	
			
			<input name="btn9" type="button" class="button" value="返回" onclick="event(9);">
			</td>
			</tr>
			
				<tr class="common">
					<td align="left" width="100%" colspan="7">
						<font color="red">
					注：金额精度为分(0.01元)，超过精度部分将被四舍五入。<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;收取违约金时会自动返还交收保证金。
					</font>
					
					<c:if test="${settleMatch.status==3 }">
					<font color="green">
					<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;该条交收消息已被作废，所有操作均已撤销。
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
					alert("货权转移已完成，不可以修改仓单！");
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
		if(confirm("确认还原该配对信息?\n点击确定后，该配对信息的所有操作将被还原，状态还原到未处理状态。"))
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
	
	// 查看所有操作记录信息 
	function test(matchId)
	{
		v="<%=basePath %>servlet/settleMatchController.${POSTFIX}?funcflg=viewOperator&matchId="+matchId;
		
		var result = window.showModalDialog(v,'', 
					"dialogWidth=600px; dialogHeight=580px; status=no;scroll=yes;help=no;");
		
		if(result){
			window.location.href="<%=basePath %>servlet/settleMatchController.${POSTFIX}?funcflg=settleView&matchId="+matchId;
			
		}
		
	}
	
	// 查看配对持仓信息 
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
		var FirmID_B = '<c:out value="${settleMatch.firmID_B }"/>';//买方
		var FirmID_S = '<c:out value="${settleMatch.firmID_S }"/>';//买方
		var CommodityID = '<c:out value="${settleMatch.breedId }"/>';//品种代码
		var Quantity = '<c:out value="${settleMatch.weight }"/>';//交收数量
		var HL_Amount = '<c:out value="${settleMatch.HL_Amount }"/>';//升贴水
		var TakePenalty_B = '<c:out value="${settleMatch.penalty_B }"/>';//买方违约金
		var SettlePL_B = '<c:out value="${settleMatch.settlePL_B }"/>';//买方交收盈亏
		var SettlePL_S = '<c:out value="${settleMatch.settlePL_S }"/>';//卖方交收盈亏
		var TakePenalty_S = '<c:out value="${settleMatch.penalty_S }"/>';//卖方违约金
		
		var showMsg="";
		switch (v)
		{
			case '1'://履约
				showMsg="买方代码："+FirmID_B+",卖方代码："+FirmID_S+
					"\n品种代码："+CommodityID+",升贴水："+HL_Amount;
				break;
			case '2'://买方违约
				showMsg="买方代码："+FirmID_B+",卖方代码："+FirmID_S+
					"\n品种代码："+CommodityID+",交收数量："+Quantity+
					"\n买方违约金："+TakePenalty_B+",买方交收盈亏："+SettlePL_B+
					"\n卖方交收盈亏："+SettlePL_S;
				break;
			case '3'://卖方违约
				showMsg="买方代码："+FirmID_B+",卖方代码："+FirmID_S+
					"\n品种代码："+CommodityID+",交收数量："+Quantity+
					"\n卖方违约金："+TakePenalty_S+",买方交收盈亏："+SettlePL_B+
					"\n卖方交收盈亏："+SettlePL_S;
				break;
			case '4'://双方违约
				showMsg="买方代码："+FirmID_B+",卖方代码："+FirmID_S+
					"\n品种代码："+CommodityID+",交收数量："+Quantity+
					"\n收买方违约金："+TakePenalty_B+",收卖方违约金："+TakePenalty_S+
					"\n买方交收盈亏："+SettlePL_B+",卖方交收盈亏："+SettlePL_S;
				break;
		}
		handleOK(showMsg);
	}
	function handleOK(v)
	{
		var mid = frm.matchId.value;
		var aheadSettlePriceType=frm.matchId.value;
		var subbtn7 = false;
		if(confirm("交收信息为：\n"+v+"\n确定完成处理？"))
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
		if(confirm("确定自主交收此配对信息？"))
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
			alert("货权转移已完成，不可以作废！");
			subbtn8 = false;
		} else {
			if(confirm("确定作废此条交收数据？\n如果选择确定，交收数据将作废，金额变动自动回滚！")){	
				frm.btn8.disabled= true;
				subbtn8 = true;
			}
		}
		if(subbtn8){
			frm.action="<%=basePath%>servlet/settleMatchController.${POSTFIX}?funcflg=settleCancel&matchId="+mid+"&moduleId=${settleMatch.moduleId }";
			frm.submit();
		}
	}
	//进行货权转移
	function doSettleTransfer(){
		var mid = frm.matchId.value;
		if(confirm("您确认进行货权转移吗？")){
			frm.action="<%=basePath %>/servlet/settleMatchController.${POSTFIX}?funcflg=settleTransfer&matchId="+mid;
			frm.submit();
		}
	}
	//提交确认已经接到发票
	function doSubmit(){
		//var matchId=document.getElementById("matchId").value;
		if(window.confirm("您确认收到发票吗？"))
		frm.submit();
	}
	
</script>
</html>