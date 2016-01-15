<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<title>交易商资金情况</title>
	</head>
	<body>
		<div class="div_cx">
			<table border="0" width="100%" align="center">
				<tr>
					<td>
						<div class="warning">
							<div class="content">
								温馨提示 :交易商${firmId}资金情况
							</div>
						</div>
					</td>
				</tr>
				<tr>
					<td>
						<table border="0" width="100%" align="center">
							<tr>
								<td>
									<div class="div_cxtj">
										<div class="div_cxtjL"></div>
										<div class="div_cxtjC">
											详情信息
										</div>
										<div class="div_cxtjR"></div>
									</div>
									<div style="clear: both;"></div>
									<div>
									 <%
									  try{
									    List list = (List)request.getAttribute("list");
									    Map map=null;
									    List listResult=null;
									   	if(list!=null&&list.size()==2)
									    {
									      	map=(Map)list.get(0);
									      	listResult=(List)list.get(1);
									    }
									    if(map!=null)
									    {
									   %>
										<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center" class="table2_style">
												<tr>
													<td align="right">
														期初余额：
													</td>
													<td>
														<fmt:formatNumber value="<%=(map.get(\"LASTBALANCE\")).toString()%>" pattern="#,##0.00"/>&nbsp;
													</td>
												</tr>
												<%
											     if(listResult!=null&&listResult.size()>0)
											     {
											        for(int i=0;i<listResult.size();i++)
											        {
											           Map m=(Map)listResult.get(i);
											           %>
												         <tr>
															<td align="right">
																<%=(m.get("NAME")).toString()%>：
															</td>
															<td>
																<fmt:formatNumber value="<%=(m.get(\"VALUE\")).toString()%>" pattern="#,##0.00"/>&nbsp;
															</td>
														</tr>
											           <%
											        }
											     }
											   %>
												<tr>
													<td align="right">
														期末余额：
													</td>
													<td >
														<fmt:formatNumber value="<%=(map.get(\"BALANCE\")).toString()%>" pattern="#,##0.00"/>&nbsp;
													</td>
												</tr>
											</table>
										<%
									     }
									     }catch(Exception e)
									     {
									       e.printStackTrace();
									     }
									   %>
									</div>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>
		<div>
			<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center">
				<tr>
					<td align="center">
						<button class="btn_sec" onClick="window.close();">关闭</button>
					</td>
				</tr>
			</table>
		</div>
	</body>
</html>