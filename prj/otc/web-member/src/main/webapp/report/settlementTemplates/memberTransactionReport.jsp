<%@ page pageEncoding="GBK"%>
<%@ include file="/public/reportCss.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
	</head>
	<body class="report_body">
		<br>
		<div class="report_w16_titlemin">
		      �����������
	</div>
		<div class="report_w16_title">
			��Ա����ͳ�Ʊ�
		</div>	
   <table width="60%" border="0" align="center" cellpadding="0" cellspacing="0" bordercolor="#000000" style='border-collapse: collapse;' height="26">
	  <tr>
	    <td align="left">��ʼ���ڣ�${oldParams["trunc(primary.cleardate)[>=][date]"]}</td>
	    <td align="left">�������ڣ�${oldParams["trunc(primary.cleardate)[<=][date]"]}</td>
	    <td align="left">�����ˣ�${CURRENUSERID }</td>
	  </tr>
	</table>
		<table width="150%" border="1" align="center" cellpadding="0"
			cellspacing="0" bordercolor="#000000"
			style='table-layout:fixed;border-collapse: collapse;' bgcolor="#ffbfc9" height="26">
			<tr class="report_w14">
				<td width="3%" rowspan="2">
					���				</td>
				<td width="3%" rowspan="2">
					��Ʒ				</td>
				<td width="4%"  rowspan="2"> ��Ա��� </td>
				<td width="5%"  rowspan="2"> ��Ա���� </td>
				<td width="8%" colspan="3">
					�ɽ���				</td>
				<td width="13%" colspan="3">
					�ɽ���				</td>
				<td width="13%" colspan="3">
					ƽ��ӯ��				</td>
				<td width="13%" colspan="3">
					�ֲ�ӯ��				</td>
				<td width="13%" colspan="3">
					������				</td>
				<td width="13%" colspan="3">
					���ڷ�				</td>
				
				<td width="5%"  rowspan="2">
					��������				</td>
			</tr>
			<s:set var="num" value="0"></s:set>
			<s:set var="customerfundsum" value="0"></s:set>
			<s:set var="memberfundsum" value="0"></s:set>
			<s:set var="fundsum" value="0"></s:set>
			<s:set var="customercloseplsum" value="0"></s:set>
			<s:set var="memberclosepl" value="0"></s:set>
			<s:set var="closeplsum" value="0"></s:set>
			<s:set var="customerholdpl" value="0"></s:set>
			<s:set var="memberholdpl" value="0"></s:set>
			<s:set var="holdplsum" value="0"></s:set>
			<s:set var="mktfee" value="0"></s:set>
			<s:set var="memberfee" value="0"></s:set>
			<s:set var="customerfee" value="0"></s:set>
			<s:set var="asmemberdelayfee" value="0"></s:set>
			<s:set var="customerdelayfee" value="0"></s:set>
			<s:set var="membernetdelayfee" value="0"></s:set>
			<s:set var="customerqtysum" value="0"></s:set>
			<s:set var="memberqtysum" value="0"></s:set>
			<s:set var="qtysum" value="0"></s:set>
				<tr>
					<td width="4%">
						�ͻ�					</td>
					<td width="4%">
						��Ա					</td>
					<td width="4%">
						�ܼ�					</td>
					<td width="4%">
						�ͻ�					</td>
					<td width="4%">
						��Ա					</td>
					<td width="4%">
						�ܼ�					</td>
					<td width="4%">
						�ͻ�					</td>
					<td width="4%">
						��Ա					</td>
					<td width="4%">
						�ܼ�					</td>
					<td width="4%">
						�ͻ�					</td>
					<td width="4%">
						��Ա					</td>
					<td width="4%">
						�ܼ�					</td>
					<td width="4%">
						����������					</td>
					<td width="4%">
						��Ա����					</td>
					<td width="4%">
						�տͻ�					</td>
					<td width="4%">
						���ر��Ա					</td>
					<td width="4%">
						�տͻ�					</td>
					<td width="4%">
						��Ա����					</td>
				</tr>
				<s:iterator value="dataList()" var="dataMap">
				<tr>
					<td>
						<s:set var="num" value="#num+1"></s:set>
						<s:property value="#num" />					</td>
					<td>
						<s:property value="#dataMap.commodityName" />					</td>
					<td><s:property value="#dataMap.memberNo" />
                    </td>
					<td><s:property value="#dataMap.memberName" />
                    </td>
					<td align="right">
						<s:set var="a" value="#dataMap.customerqtysum" />
						<fmt:formatNumber value="${a }" pattern="#,##0"/>
						<s:if test="#dataMap.customerqtysum!=null">
							<s:set var="customerqtysum"
								value="#dataMap.customerqtysum+#customerqtysum"></s:set>
						</s:if>					</td>
					<td align="right">
						<s:set var="a" value="#dataMap.memberqtysum" />
						<fmt:formatNumber value="${a }" pattern="#,##0"/>
						<s:if test="#dataMap.memberqtysum!=null">
							<s:set var="memberqtysum"
								value="#dataMap.memberqtysum+#memberqtysum"></s:set>
						</s:if>					</td>
					<td align="right">
						<s:set var="a" value="#dataMap.qtysum" />
						<fmt:formatNumber value="${a }" pattern="#,##0"/>
						<s:if test="#dataMap.qtysum!=null">
							<s:set var="qtysum" value="#dataMap.qtysum+#qtysum"></s:set>
						</s:if>					</td>
					<td align="right">
						<s:set var="a" value="format(#dataMap.customerfundsum)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.customerfundsum!=null">
							<s:set var="customerfundsum"
								value="#dataMap.customerfundsum+#customerfundsum"></s:set>
						</s:if>					</td>
					<td align="right">
						<s:set var="a" value="format(#dataMap.memberfundsum)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.memberfundsum!=null">
							<s:set var="memberfundsum"
								value="#dataMap.memberfundsum+#memberfundsum"></s:set>
						</s:if>					</td>
					<td align="right">
						<s:set var="a" value="format(#dataMap.fundsum)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.fundsum!=null">
							<s:set var="fundsum" value="#dataMap.fundsum+#fundsum"></s:set>
						</s:if>					</td>
					<td align="right">
						<s:set var="a" value="format(#dataMap.customercloseplsum)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.customercloseplsum!=null">
							<s:set var="customercloseplsum"
								value="#dataMap.customercloseplsum+#customercloseplsum"></s:set>
						</s:if>					</td>
					<td align="right">
						<s:set var="a" value="format(#dataMap.memberclosepl)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.memberclosepl!=null">
							<s:set var="memberclosepl"
								value="#dataMap.memberclosepl+#memberclosepl"></s:set>
						</s:if>					</td>
					<td align="right">
						<s:set var="a" value="format(#dataMap.closeplsum)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.closeplsum">
							<s:set var="closeplsum" value="#dataMap.closeplsum+#closeplsum"></s:set>
						</s:if>					</td>
					<td align="right">
						<s:set var="a" value="format(#dataMap.customerholdpl)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.customerholdpl">
							<s:set var="customerholdpl"
								value="#dataMap.customerholdpl+#customerholdpl"></s:set>
						</s:if>					</td>
					<td align="right">
						<s:set var="a" value="format(#dataMap.memberholdpl)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.memberholdpl!=null">
							<s:set var="memberholdpl"
								value="#dataMap.memberholdpl+#memberholdpl"></s:set>
						</s:if>					</td>
					<td align="right">
						<s:set var="a" value="format(#dataMap.holdplsum)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.holdplsum!=null">
							<s:set var="holdplsum" value="#dataMap.holdplsum+#holdplsum"></s:set>
						</s:if>					</td>
					<td align="right">
						<s:set var="a" value="format(#dataMap.mktfee)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.mktfee!=null">
							<s:set var="mktfee" value="#dataMap.mktfee+#mktfee"></s:set>
						</s:if>					</td>

					<td align="right">
						<s:set var="a" value="format(#dataMap.memberfee)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.memberfee!=null">
							<s:set var="memberfee" value="#dataMap.memberfee+#memberfee"></s:set>
						</s:if>					</td>
					<td align="right">
						<s:set var="a" value="format(#dataMap.customerfee)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.customerfee!=null">
							<s:set var="customerfee"
								value="#dataMap.customerfee+#customerfee"></s:set>
						</s:if>					</td>
					<td align="right">
						<s:set var="a" value="format(#dataMap.asmemberdelayfee)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.asmemberdelayfee!=null">
							<s:set var="asmemberdelayfee"
								value="#dataMap.asmemberdelayfee+#asmemberdelayfee"></s:set>
						</s:if>					</td>
					<td align="right">
						<s:set var="a" value="format(#dataMap.customerdelayfee)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.customerdelayfee!=null">
							<s:set var="customerdelayfee"
								value="#dataMap.customerdelayfee+#customerdelayfee"></s:set>
						</s:if>					</td>
					<td align="right">
						<s:set var="a" value="format(#dataMap.membernetdelayfee)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.membernetdelayfee!=null">
							<s:set var="membernetdelayfee"
								value="#dataMap.membernetdelayfee+#membernetdelayfee"></s:set>
						</s:if>					</td>
					
					<td>
						<s:date name="#dataMap.clearDate" format="yyyy-MM-dd" />					</td>
				  </tr>
			</s:iterator>
			<tr>
				<td>
					�ϼ�:				</td>
				<td>&nbsp;				</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td align="right">
					<s:set var="a" value="#customerqtysum" /><fmt:formatNumber value="${a }" pattern="#,##0"/></td>
				<td align="right">
					<s:set var="a" value="#memberqtysum" /><fmt:formatNumber value="${a }" pattern="#,##0"/></td>
				<td align="right">
					<s:set var="a" value="#qtysum" /><fmt:formatNumber value="${a }" pattern="#,##0"/></td>
				<td align="right">
					<s:set var="a" value="format(#customerfundsum)" />	<fmt:formatNumber value="${a }" pattern="#,##0.00"/></td>
				<td align="right">
					<s:set var="a" value="format(#memberfundsum)" /><fmt:formatNumber value="${a }" pattern="#,##0.00"/></td>
				<td align="right">
					<s:set var="a" value="format(#fundsum)" />	<fmt:formatNumber value="${a }" pattern="#,##0.00"/></td>
				<td align="right">
					<s:set var="a" value="format(#customercloseplsum)" /><fmt:formatNumber value="${a }" pattern="#,##0.00"/></td>
				<td align="right">
					<s:set var="a" value="format(#memberclosepl)" />	<fmt:formatNumber value="${a }" pattern="#,##0.00"/></td>
				<td align="right">
					<s:set var="a" value="format(#closeplsum)" />	<fmt:formatNumber value="${a }" pattern="#,##0.00"/></td>
				<td align="right">
					<s:set var="a" value="format(#customerholdpl)" />	<fmt:formatNumber value="${a }" pattern="#,##0.00"/></td>
				<td align="right">
					<s:set var="a" value="format(#memberholdpl)" /><fmt:formatNumber value="${a }" pattern="#,##0.00"/></td>
				<td align="right">
					<s:set var="a" value="format(#holdplsum)" /><fmt:formatNumber value="${a }" pattern="#,##0.00"/></td>
				<td align="right">
					<s:set var="a" value="format(#mktfee)" />	<fmt:formatNumber value="${a }" pattern="#,##0.00"/></td>

				<td align="right">
					<s:set var="a" value="format(#memberfee)" /><fmt:formatNumber value="${a }" pattern="#,##0.00"/></td>
				<td align="right">
					<s:set var="a" value="format(#customerfee)" />	<fmt:formatNumber value="${a }" pattern="#,##0.00"/></td>
				<td align="right">
					<s:set var="a" value="format(#asmemberdelayfee)" /><fmt:formatNumber value="${a }" pattern="#,##0.00"/></td>
				<td align="right">
					<s:set var="a" value="format(#customerdelayfee)" /><fmt:formatNumber value="${a }" pattern="#,##0.00"/></td>
				<td align="right">
					<s:set var="a" value="format(#membernetdelayfee)" /><fmt:formatNumber value="${a }" pattern="#,##0.00"/></td>
				<td>&nbsp;</td>
			</tr>
		</table>
		<br>
		<br>
		<br>
	</body>
</html>