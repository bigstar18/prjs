<%@ page pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
	</head>
	<body class="report_body">
		<br>
		<br>
		<br>
		<div class="report_w16_title">
			持仓单
		</div>
		<table width="900" border="1" align="center" cellpadding="0"
			cellspacing="0" bordercolor="#000000"
			style='border-collapse: collapse;' bgcolor="#ffc080">
			<tr class="report_w14">
				<td width="10%">
					会员编号
				</td>
				<td width="10%">
					商品
				</td>
				<td width="10%">
					特别会员
				</td>
				<td width="30%">
					<table width="100%" border="1" align="center" cellpadding="0"
						cellspacing="0" bordercolor="#000000"
						style='border-collapse: collapse;' bgcolor="#ffc080">
						<tr>
							<td align="center">
								会员
							</td>
						</tr>
						<tr>
							<td>
								<table width="100%" border="0" align="center" cellpadding="0"
									cellspacing="0">
									<tr>
										<td width="15%" align="center" class="tab_td">
											买量
										</td>
										<td width="23%" align="center" class="tab_td">
											买盈亏
										</td>
										<td width="15%" align="center" class="tab_td">
											卖量
										</td>
										<td width="23%" align="center" class="tab_td">
											卖盈亏
										</td>
										<td width="23%" align="center">
											净盈亏
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</td>
				<td width="30%">
					<table width="100%" border="1" align="center" cellpadding="0"
						cellspacing="0" bordercolor="#000000"
						style='border-collapse: collapse;' bgcolor="#ffc080">
						<tr>
							<td align="center">
								客户累计
							</td>
						</tr>
						<tr>
							<td>
								<table width="100%" border="0" align="center" cellpadding="0"
									cellspacing="0">
									<tr>
										<td align="center" class="tab_td">
											买量
										</td>
										<td align="center" class="tab_td">
											买盈亏
										</td>
										<td align="center" class="tab_td">
											卖量
										</td>
										<td align="center" class="tab_td">
											卖盈亏
										</td>
										<td align="center">
											净盈亏
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>

				</td>
			</tr>
		</table>


		<s:iterator value="tradeHoldList" var="dataMap1">
			<table width="900" border="1" align="center" cellpadding="0"
				cellspacing="0" bordercolor="#d3d1d2"
				style='border-collapse: collapse;'>
				<tr class="report_w12_org">
					<td width="10%">
						<s:property value="#dataMap1.memberid" />
					</td>
					<td width="10%">
						<s:property value="#dataMap1.commodityName" />
					</td>
					<td width="40%">
						<table width="100%" border="0" align="center" cellpadding="0"
							cellspacing="0">
							<tr class="report_w12">
								<s:set var="buyqty" value="0"></s:set>
								<s:set var="buyloss" value="0"></s:set>
								<s:set var="sellqty" value="0"></s:set>
								<s:set var="sellloss" value="0"></s:set>
								<s:set var="netloss" value="0"></s:set>
								<s:set var="qc" value="commonQueryConditions()"></s:set>
								<s:set
									value="#qc.addCondition('primary.commodityName','=',#dataMap1.commodityName)"></s:set>
								<s:set
									value="#qc.addCondition('primary.memberid','=',#dataMap1.memberid)"></s:set>
								<s:iterator value="tradeMemberHoldPositingList()">
									<td width="25%" align="center" class="report_td_th">
										<s:property value="s_memberid" />
									</td>
									<td width="12%" align="center" class="report_td_th">
										<s:property value="buyqty" />
										<s:set var="buyqty" value="#buyqty+buyqty"></s:set>
									</td>
									<td width="17%" align="center" class="report_td_th">
										<s:property value="format(buyloss)" />
										<s:set var="buyloss" value="#buyloss+buyloss"></s:set>
									</td>
									<td width="12%" align="center" class="report_td_th">
										<s:property value="sellqty" />
										<s:set var="sellqty" value="#sellqty+sellqty"></s:set>
									</td>
									<td width="17%" align="center" class="report_td_th">
										<s:property value="format(sellloss)" />
										<s:set var="sellloss" value="#sellloss+sellloss"></s:set>
									</td>
									<td width="17%" align="center" class="report_td_tr">
										<s:property value="format(netloss)" />
										<s:set var="netloss" value="#netloss+netloss"></s:set>
									</td>
								</s:iterator>
							</tr>
							<tr class="report_w12">
								<td align="center" class="report_td_end">
									汇总：
								</td>
								<td align="center" class="report_td_end">
									<s:property value="#buyqty"/>
								</td>
								<td align="center" class="report_td_end">
									<s:property value="#buyloss"/>
								</td>
								<td align="center" class="report_td_end">
									<s:property value="#sellqty"/>
								</td>
								<td align="center" class="report_td_end">
									<s:property value="#sellloss"/>
								</td>
								<td align="center">
									<s:property value="#netloss"/>
								</td>
							</tr>
						</table>
					</td>
					<td width="30%">
						<table width="100%" border="0" align="center" cellpadding="0"
							cellspacing="0">
							<tr>
								<td width="15%" align="center" class="report_td_right">
									<s:property value="#dataMap1.buyqty" />
								</td>
								<td width="23%" align="center" class="report_td_right">
									<s:property value="format(#dataMap1.buyloss)" />
								</td>
								<td width="15%" align="center" class="report_td_right">
									<s:property value="#dataMap1.sellqty" />
								</td>
								<td width="23%" align="center" class="report_td_right">
									<s:property value="format(#dataMap1.sellloss)" />
								</td>
								<td width="23%" align="center">
									<s:property value="format(#dataMap1.netloss)" />
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</s:iterator>
		<br>
		<br>
		<br>
	</body>
</html>

