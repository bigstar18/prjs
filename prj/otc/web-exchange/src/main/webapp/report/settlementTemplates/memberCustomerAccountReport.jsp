<%@ page pageEncoding="GBK"%>
<%@ include file="/public/reportCss.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
	</head>
	<body class="report_body">
		<div class="report_w16_titlemin">
			贵金属交易所
		</div>
		<div class="report_w16_title">
			会员客户开户统计情况表
		</div>
	<table width="200%" border="0" align="center" cellpadding="0"
			cellspacing="0" bordercolor="#000000"
			style='border-collapse: collapse;'>
			<tr>
			<td>
		<table width="60%" border="0" align="left" cellpadding="0"
			cellspacing="0" bordercolor="#000000"
			style='border-collapse: collapse;padding-left:20px;' height="20">
			<tr>
				<td align="left">
					开始日期：${oldParams["trunc(primary.cleardate)[>=][date]"]}
				</td>
				<td align="left">
					结束日期：${oldParams["trunc(primary.cleardate)[<=][date]"]}
				</td>
				<td align="left">
					操作人：${CURRENUSERID }
				</td>
			</tr>
		</table>
		</td>
		</tr>
		<tr>
		<td>

		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="0" bordercolor="#000000"
			style='border-collapse: collapse;'>
			<s:set var="createcount" value="0"></s:set>
			<s:set var="totalcreatecount" value="0"></s:set>
			<s:set var="demisecount" value="0"></s:set>
			<s:set var="totaldemisecount" value="0"></s:set>
			<s:set var="dealcount" value="0"></s:set>
			<s:set var="totaldealcount" value="0"></s:set>
			<s:set var="signcount" value="0"></s:set>
			<s:set var="totalsigncount" value="0"></s:set>
			<s:set var="designcount" value="0"></s:set>
			<s:set var="totaldesigncount" value="0"></s:set>
			<s:set var="num" value="1"></s:set>
			<tr class="report_w14">
				<td width="3%" class="report_td_bt1" rowspan="2">
					序号
				</td>
				<td width="7%" class="report_td_bt" rowspan="2">
					结算日期
				</td>
				<td width="8%" class="report_td_bt" rowspan="2">
					会员编号
				</td>
				<td width="8%" class="report_td_bt" rowspan="2">
					会员名称
				</td>

				<td width="8%" class="report_td_bt" rowspan="2">
					会员交易账户
				</td>
				<td width="8%" class="report_td_bt" colspan="2">
					开户
				</td>
				<td width="8%" class="report_td_bt" colspan="2">
					销户
				</td>
				<td width="8%" class="report_td_bt" colspan="2">
					可交易
				</td>
				<td width="9%" class="report_td_th" rowspan="2">
					签约银行
				</td>
				<td width="8%" class="report_td_bt" colspan="2">
					签约
				</td>
				<td width="8%" class="report_td_bt" colspan="2">
					解约
				</td>
			</tr>
			<tr class="report_w14">
				<td class="report_td_th">
					变动
				</td>
				<td class="report_td_th">
					汇总
				</td>
				<td class="report_td_th">
					变动
				</td>
				<td class="report_td_th">
					汇总
				</td>
				<td class="report_td_th">
					变动
				</td>
				<td class="report_td_th">
					汇总
				</td>
				<td class="report_td_th">
					变动
				</td>
				<td class="report_td_th">
					汇总
				</td>
				<td class="report_td_th">
					变动
				</td>
				<td class="report_td_th">
					汇总
				</td>
			</tr>
			<s:iterator value="dataList()" var="dataMap">
				<tr class="report_w14_neirong">
					<td class="report_td_bt1">
						<s:property value="#num" />
						<s:set var="num" value="#num+1"></s:set>
					</td>
					<td class="report_td_bt">
						<s:date name="#dataMap.clearDate" format="yyyy-MM-dd"></s:date>
					</td>
					<td class="report_td_bt">
						<s:property value="#dataMap.memberno" />&nbsp;
					</td>
					<td class="report_td_bt">
						<s:property value="#dataMap.membername" />
					</td>
					<td class="report_td_bt">
						<s:property value="#dataMap.contact" />
						&nbsp;
					</td>
					<td class="report_td_bt" align="right">
						<s:set value="#dataMap.createcount" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0"/>
						<s:if test="#dataMap.createcount!=null">
							<s:set var="createcount"
								value="#createcount+#dataMap.createcount"></s:set>
						</s:if>
					</td>
					<td class="report_td_bt" align="right">
						<s:set value="#dataMap.totalcreatecount" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0"/>
						<s:if test="#dataMap.totalcreatecount!=null">
							<s:set var="totalcreatecount"
								value="#totalcreatecount+#dataMap.totalcreatecount"></s:set>
						</s:if>
					</td>
					<td class="report_td_bt" align="right">
						<s:set value="#dataMap.demisecount" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0"/>
						<s:if test="#dataMap.demisecount!=null">
							<s:set var="demisecount"
								value="#demisecount+#dataMap.demisecount"></s:set>
						</s:if>
					</td>
					<td class="report_td_bt" align="right">
						<s:set value="#dataMap.totaldemisecount" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0"/>
						<s:if test="#dataMap.totaldemisecount!=null">
							<s:set var="totaldemisecount"
								value="#totaldemisecount+#dataMap.totaldemisecount"></s:set>
						</s:if>
					</td>
					<td class="report_td_bt" align="right">
						<s:set value="#dataMap.dealcount" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0"/>
						<s:if test="#dataMap.dealcount!=null">
							<s:set var="dealcount" value="#dealcount+#dataMap.dealcount"></s:set>
						</s:if>
					</td>
					<td class="report_td_bt" align="right">
						<s:set value="#dataMap.totaldealcount" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0"/>
						<s:if test="#dataMap.totaldealcount!=null">
							<s:set var="totaldealcount"
								value="#totaldealcount+#dataMap.totaldealcount"></s:set>
						</s:if>
					</td>
					<td colspan="5" height="100%">
						<table width="100%" height="100%" border="0" align="center" cellpadding="0"
							cellspacing="0">
							<s:set var="qc"
								value="new gnnt.MEBS.base.query.jdbc.QueryConditions()"></s:set>
							<s:set
								value="#qc.addCondition('trunc(primary.cleardate)','=',#dataMap.clearDate,'date')"></s:set>
							<s:set
								value="#qc.addCondition('primary.memberno','=',#dataMap.memberno)"></s:set>
							<s:iterator value="memberCustomerAllList(#qc)" status="sta">
								<tr>
									<td width="36%" align="left" class="report_td_th" >
										<s:property value="bankname" />
										&nbsp;
									</td>
									<td align="right" class="report_td_th" width="16%">
										<s:set value="signcount" var="a"/>
										<fmt:formatNumber value="${a }" pattern="#,##0"/>
										<s:if test="signcount!=null">
											<s:set var="signcount" value="#signcount+signcount"></s:set>
										</s:if>
									</td>
									<td align="right" class="report_td_th" width="16%">
										<s:set value="totalsigncount" var="a"/>
										<fmt:formatNumber value="${a }" pattern="#,##0"/>
										<s:if test="totalsigncount!=null">
											<s:set var="totalsigncount" value="#totalsigncount+totalsigncount"></s:set>
										</s:if>
									</td>
									<td align="right" class="report_td_th" width="16%">
										<s:set value="designcount" var="a"/>
										<fmt:formatNumber value="${a }" pattern="#,##0"/>
										<s:if test="designcount!=null">
											<s:set var="designcount" value="#designcount+designcount"></s:set>
										</s:if>
									</td>
									<td  align="right" class="report_td_th" width="16%" >
										<s:set value="totaldesigncount" var="a"/>
										<fmt:formatNumber value="${a }" pattern="#,##0"/>
										<s:if test="totaldesigncount!=null">
											<s:set var="totaldesigncount" value="#totaldesigncount+totaldesigncount"></s:set>
										</s:if>
									</td>

								</tr>
							</s:iterator>
						</table>
					</td>
				</tr>
			</s:iterator>
			<tr class="report_w14">
				<td class="report_td_bt1">
					总计：
				</td>
				<td class="report_td_bt">
					&nbsp;
				</td>
				<td class="report_td_bt">
					&nbsp;
				</td>
				<td class="report_td_bt">
					&nbsp;
				</td>
				<td class="report_td_bt">
					&nbsp;
				</td>
				<td class="report_td_bt" align="right">
					<s:set value="#createcount" var="a"/>
					<fmt:formatNumber value="${a }" pattern="#,##0"/>
				</td>
				<td class="report_td_bt" align="right">
					<s:set value="#totalcreatecount" var="a"/>
					<fmt:formatNumber value="${a }" pattern="#,##0"/>
				</td>
				<td class="report_td_bt" align="right">
					<s:set value="#demisecount" var="a"/>
					<fmt:formatNumber value="${a }" pattern="#,##0"/>
				</td>
				<td class="report_td_bt" align="right">
					<s:set value="#totaldemisecount" var="a"/>
					<fmt:formatNumber value="${a }" pattern="#,##0"/>
				</td>
				<td class="report_td_bt" align="right">
					<s:set value="#dealcount" var="a"/>
					<fmt:formatNumber value="${a }" pattern="#,##0"/>
				</td>
				<td class="report_td_bt" align="right">
					<s:set value="#totaldealcount" var="a" />
					<fmt:formatNumber value="${a }" pattern="#,##0"/>
				</td>
				<td class="report_td_bt">
					&nbsp;
				</td>
				<td class="report_td_bt" align="right">
					<s:set value="#signcount" var="a"/>
					<fmt:formatNumber value="${a }" pattern="#,##0"/>
				</td>
				<td class="report_td_bt" align="right">
					<s:set value="#totalsigncount" var="a"/>
					<fmt:formatNumber value="${a }" pattern="#,##0"/>
				</td>
				<td class="report_td_bt" align="right">
					<s:set value="#designcount" var="a"/>
					<fmt:formatNumber value="${a }" pattern="#,##0"/>
				</td>
				<td class="report_td_bt" align="right">
					<s:set value="#totaldesigncount" var="a"/>
					<fmt:formatNumber value="${a }" pattern="#,##0"/>
				</td>
			</tr>
		</table>
		</td>
		</tr>
		</table>
	</body>
</html>



