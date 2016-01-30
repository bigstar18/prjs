<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
		<style type="text/css">
		a:link {text-decoration:none;} 
		a:active:{text-decoration:none;} 
		a:visited {text-decoration:none;}
		a:hover { text-decoration:none;}
		
		</style>
		<script type="text/javascript">
			$(function(){
				var settleDate = $("#settleDate");
				if(settleDate.val() == ""){
					settleDate.val('${settleDateDefault}');
				}
			});
		</script>
		</head>
		<body>
		<br/>
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
					    <div class="div_cx">
							<form name="frm" action="${basePath}/timebargain/bill/notPairTotal.action" method="post">
								<table border="0" cellpadding="0" cellspacing="0" class="table2_style" height="60px">
									<tr>
										<td class="table5_td_width">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0" cellpadding="0">
													<tr>
														<td class="table3_td_1" align="center">
															交收日期:
															<input type="text" class="wdate" id="settleDate"  style="width: 106px" 
																name="${GNNT_}settleDate" value="${oldParams['settleDate'] }"
																onfocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})" />
														</td>
														<td align="center">商品代码：&nbsp;
															<select id="commodityID" name="${GNNT_}commodityID" style = "width: 106px">
																<option></option>
																<c:forEach items = "${commodityIDList }" var = "commodityID">
																	<c:choose>
																		 <c:when test="${commodityID['COMMODITYID'] == oldParams['commodityID']}">
																		 	<option value="${commodityID['COMMODITYID'] }" selected="selected">${commodityID['COMMODITYID'] }</option>
																		 </c:when>
																		 <c:otherwise>
																		 	<option value="${commodityID['COMMODITYID'] }">${commodityID['COMMODITYID'] }</option>
																		 </c:otherwise>
																	</c:choose>
																		
																</c:forEach>
															</select>
														</td>
												    
												    	<td class="table3_td_1" align="center">
															<input type = "submit" class="btn_sec" id="query" value = "查询"/>
															&nbsp;&nbsp;
															<button class="btn_cz" onclick="myReset();">重置</button>
														</td> 
												    </tr>
												</table>
											</div>
										</td>
									</tr>
								</table>
							</form>
						</div>
						<br />
						<div align="center">
						
							<table valign="top" border="0" height="100" width="100%" cellpadding="0" cellspacing="0" class="table2_style" >
								<tr>
									<td height="100%" width="100%">
										<table valign="top" border="0"  cellpadding="0" cellspacing=0"
											class="common" width="100%">
											<tr height="35px"><td colspan="2" align="center">商品代码：${commodityID }</td></tr>
											<tr>
												
												<td valign="top">
													<table valign="top" border="0"  width="100%" cellpadding="0" cellspacing="0" class="common" align="left">
														<tr height="20"><td  colspan="2" align="center" height="45px">买方</td></tr>
														<tr height="20"><td bgcolor="FF9950" align="center">交易商代码</td><td bgcolor="FF9950" align="center">未配对数量</td></tr>
														<c:forEach items = "${buyerNotPairTotalList }" var = "buyerNotTotalMap">
															<tr height="20" align="center">
																<td>
																	${buyerNotTotalMap['FIRMID'] }
																</td>
																<td>
																	${buyerNotTotalMap['NOTPAIRTOTAL'] }
																</td>
															</tr>
														</c:forEach>	
													</table>
												</td>
												<td valign="top">
													<table valign="top" border="0"  width="100%" cellpadding="0" cellspacing="0" class="common" align="right">
														<tr height="20"><td colspan="2" align="center" height="45px">卖方</td></tr>
														<tr height="20"><td bgcolor="FF9950" align="center">交易商代码</td><td bgcolor="FF9950" align="center">未配对数量</td></tr>
														<c:forEach items = "${sellerNotPairTotalList }" var = "sellerNotTotalMap">
															<tr height="20" align="center">
																<td>
																	${sellerNotTotalMap['FIRMID'] }
																</td>
																<td>
																	${sellerNotTotalMap['NOTPAIRTOTAL'] }
																</td>
															</tr>
														</c:forEach>
													</table>
												</td>																	
											</tr>
										</table>
									</td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
			</table>
		</div>
		</body>
</html>
