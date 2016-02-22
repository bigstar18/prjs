<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/public/ecsideLoad.jsp"%>

<html>
	<head>
		<title>系统用户浏览</title>
	</head>
	<body>

		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0"
				cellpadding="0">
				<tr>
					<td>
						&nbsp;
						<!--
						<div class="div_cxtj">
							<img src="<%=skinPath%>/cssimg/13.gif" />
							&nbsp;&nbsp;查询条件
						</div>
						<div class="div_tj">
							<form name="frm"
								action="${basePath}/tradeManage/onLineTrade/list.action"
								method="post">
								<table border="0" cellpadding="0" cellspacing="0"
									class="table2_style">
									<tr>
										<td height="60">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0"
													cellpadding="0">
													<tr>
														<td class="table3_td" align="right">
															登录账号:&nbsp;
														</td>
														<td class="table3_td2">
															<label>
																<input type="text"
																	class="input_text" name="name1" value="" />
															</label>
														</td>
														<td class="table3_td" align="right">
															&nbsp;
														</td>
														<td class="table3_td2">
															<label>
																&nbsp;
															</label>
														</td>
														<td class="table3_td" align="right">
															<button class="btn_sec" onclick="select1()">
																查询
															</button>
														</td>
														<td class="table3_td2" align="left">
															<button class="btn_cz" onclick="resetNun()">
																重置
															</button>
														</td>
													</tr>
												</table>
											</div>
										</td>
									</tr>
								</table>
							</form>
							-->
						</div>
						<div class="div_gn">
							<button class="anniu_btnmax" onclick="outline()">
								强制下线
							</button>
						</div>

						<div class="div_list">
							<table id="tb" border="0" cellspacing="0" cellpadding="0"
								width="100%">
								<tr>
									<td>
										<ec:table items="resultList"
											autoIncludeParameters="${empty param.autoInc}" var="trader"
											action="${basePath}/tradeManage/onLineTrade/list.action"
											minHeight="345" listWidth="100%"
											 csvFileName="导出列表.csv"
											  style="table-layout:fixed">
											<ec:row recordKey="${trader.id}">
												<ec:column cell="checkbox" headerCell="checkbox"
													alias="itemlist" columnId="itemlist" value="${trader.id}"
													style="text-align:center;padding-left:9px;width:30;"
													viewsAllowed="html" />
												<ec:column property="" title="登录账号"
													style="text-align:center; " value="" />
												<ec:column property="" title="ip地址"
													style="text-align:center; " value="" />
												<ec:column property="" title="登录时间"
													style="text-align:center; " value="" />
											</ec:row>
										</ec:table>
									</td>
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
<SCRIPT type="text/javascript">
		function resetNun(){
			
			frm.submit();
		}
		function outline(){
			var url="${basePath}/tradeStatusManage/operate/delete.action";
			deleteEcside(ec.ids,url);
		}
		</SCRIPT>