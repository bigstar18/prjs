<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK"%>
<%@ page import="java.util.*" %>


<html>
	<head>
		<LINK REL="stylesheet" type="text/css" href="<c:url value="/timebargain/styles/common.css"/>" />
		<script language="JavaScript" src="<c:url value="/timebargain/scripts/global.js"/>"></script>
		<title>
		</title>
		<script type="text/javascript"> 
function window_onload()
{
	//window.setTimeout(highlightFormElements(),1000);
	//statQueryForm.oper.value = '<c:out value="${param['oper']}"/>';
	highlightFormElements();
}
//query_onclick


</script>
	</head>

	<body leftmargin="13" topmargin="0" onLoad="return window_onload()"
		onkeypress="keyEnter(event.keyCode);">
		<table valign="top" border="0" height="100" width="100%" cellpadding="0" cellspacing="0" class="common" >
			<tr>
				<td height="100%" width="100%">
					
						<fieldset class="pickList" >
							<legend class="common">
								<b>交收配对列表
								</b>
							</legend>
							<table valign="top" border="0"  cellpadding="0" cellspacing=0"
								class="common" width="100%">
								<%
									String commodityID = "";
									List listC = (List)request.getAttribute("settlePairsBList");
									System.out.println("listC: "+listC.size());
										if (listC != null && listC.size() > 0) {
											Map map = (Map)listC.get(0);
											if (map.get("CommodityID") != null) {
												commodityID = map.get("commodityID").toString();
											}
										}
								%>
								<tr><td colspan="2" align="center">商品代码：<%=commodityID%></td></tr>
								<tr>
									
									<td valign="top">
										<table valign="top" border="0"  width="100%" cellpadding="0" cellspacing="0" class="common" align="left">
											<tr height="20"><td  colspan="3" align="center">买方</td></tr>
											<tr height="20"><td bgcolor="C0C0C0" align="right">交易商代码</td><td bgcolor="C0C0C0" align="right">订货数量</td><td bgcolor="C0C0C0" align="right">抵顶数量</td></tr>
											<%
												List list = (List)request.getAttribute("settlePairsBList");
												System.out.println("listB: "+list.size());
												if (list != null && list.size() > 0) {
												 	for (int i = 0; i < list.size(); i++) {
												 		Map map = (Map)list.get(i);
												 		String firmID = "";
												 		String holdQty = "";
												 		String gageQty = "";
												 		if (map.get("FirmID") != null) {
												 			firmID = map.get("FirmID").toString();
												 		}
												 		if (map.get("HoldQty") != null) {
												 			holdQty = map.get("HoldQty").toString();
												 		}
												 		if (map.get("GageQty") != null) {
												 			gageQty = map.get("GageQty").toString();
												 		}
												 		if (map.get("HoldQty") != null && map.get("GageQty") != null) {
												 			holdQty = (Long.parseLong(holdQty) + Long.parseLong(gageQty))+"";
												 		}
												 		
											%>
												<tr valign="top" height="20">
													<td align="right">
														<%=firmID%>
													</td>
													<td align="right">
														<%=holdQty%>
													</td>
													<td align="right">
														<%=gageQty%>
													</td>
												</tr>
											<%
												 	}
													
												}
											%>
										</table>
									</td>
									<td valign="top">
										<table valign="top" border="0"  width="100%" cellpadding="0" cellspacing="0" class="common" align="right">
											<tr height="20"><td colspan="3" align="center">卖方</td></tr>
											<tr height="20"><td bgcolor="C0C0C0" align="right">交易商代码</td><td bgcolor="C0C0C0" align="right">订货数量</td><td bgcolor="C0C0C0" align="right">抵顶数量</td></tr>
											<%
												List list2 = (List)request.getAttribute("settlePairsSList");
												System.out.println("listS: "+list.size());
												if (list2 != null && list2.size() > 0) {
												 	for (int i = 0; i < list2.size(); i++) {
												 		Map map = (Map)list2.get(i);
												 		String firmIDS = "";
												 		String holdQtyS = "";
												 		String gageQtyS = "";
												 		if (map.get("FirmID") != null) {
												 			firmIDS = map.get("FirmID").toString();
												 		}
												 		if (map.get("HoldQty") != null) {
												 			holdQtyS = map.get("HoldQty").toString();
												 		}
												 		if (map.get("GageQty") != null) {
												 			gageQtyS = map.get("GageQty").toString();
												 		}
												 		
												 		if (map.get("HoldQty") != null && map.get("GageQty") != null) {
												 			holdQtyS = (Long.parseLong(holdQtyS) + Long.parseLong(gageQtyS))+"";
												 		}
											%>
												<tr valign="top" height="20">
													<td valign="top" align="right">
														<%=firmIDS%>
													</td>
													<td valign="top" align="right">
														<%=holdQtyS%>
													</td>
													<td valign="top" align="right">
														<%=gageQtyS%>
													</td>
												</tr>
											<%
												 	}
													
												}
											%>
										</table>
									</td>																	
								</tr>
							</table>
						</fieldset>
				</td>
			</tr>
		</table>

		<%@ include file="/timebargain/common/messages.jsp"%>
	</body>
</html>
