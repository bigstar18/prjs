<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/broker/public/includefiles/allincludefiles.jsp"%>
<%
Date sysdate = new Date();
String nowDate = Tools.fmtDate(sysdate);
%>
<html>
	<head>
	    <link rel="stylesheet" href="${skinPath }/css/validationengine/validationEngine.jquery.css" type="text/css" />
		<link rel="stylesheet" href="${skinPath }/css/validationengine/template.css" type="text/css" />
		<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
		<script src="${publicPath }/js/validationengine/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="GBK"></script>
		<script src="${publicPath }/js/validationengine/jquery.validationEngine.js" type="text/javascript" charset="GBK"></script>
<title>
default
</title>
		<script type="text/javascript">

		jQuery(document).ready(function() {
			jQuery("#frm").validationEngine('attach', {promptPosition : "bottomRight"});
		});
		
		function dolistquery() {
			if(jQuery("#frm").validationEngine('validateform')){
				frm.submit();
			}
		}
		</script>
</head>
<body leftmargin="2" topmargin="0">
<div id="main_body">
	<table class="table1_style" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td>
				<div class="div_cx">
					<form id="frm" name="frm" action="${basePath}/firm/tariff/tradeFeeFirmList.action" method="post">
						<table border="0" cellpadding="0" cellspacing="0" class="table2_style">
							<tr>
								<td class="table5_td_width">
									<div class="div2_top">
										<table class="table3_style" border="0" cellspacing="0" cellpadding="0" style="margin-top: 5px">
											<tr>
												<td align="right">
														开始交易商：
												</td>
												<td>
													<input type="text" id="firmID" name="${GNNT_}a.FirmID[>=]" style="width:111;ime-mode:disabled" maxlength="<fmt:message key='MFirm.firmId_q' bundle='${PropsFieldLength}' />" title=""
														class="validate[onlyLetterNumber] input_text" value="${oldParams['a.FirmID[>=]']}"/>
												</td>
						                        <td align="right">
														结束交易商：
												</td>
												<td>
													<input type="text" id="commodityID" name="${GNNT_}a.FirmID[<=]"  style="width:111;ime-mode:disabled" maxlength="<fmt:message key='MFirm.firmId_q' bundle='${PropsFieldLength}' />"
														class="validate[onlyLetterNumber] input_text" value="${oldParams['a.FirmID[<=]']}"/>
												</td>		
						                        <td align="right"></td>
			                                    <td>
			                                       	                            
			                                    </td>																			
											</tr>
											
											<tr>
												<td align="right">
													开始日期：
												</td>
												<td><!--  ondblclick="if(!this.readOnly){setRq(this);}" -->
												<input type="text" style="width: 80px" id="beginDate"
													class="validate[required] wdate" maxlength="10"
													name="beginDate" value="<fmt:formatDate pattern="yyyy-MM-dd" value="${beginDate}" />"
													onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
												</td>
												<td align="right">
													结束日期：
												</td>
												<td>
												<input type="text" style="width: 80px" id="endDate"
													class="validate[required] wdate" maxlength="10"
													name="endDate" value="<fmt:formatDate pattern="yyyy-MM-dd" value="${endDate}" />"
													onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
												</td>
												<td></td>

												<td class="table3_td_1" align="left">
													<button class="btn_sec" id="view" onclick=dolistquery();>查询</button>	
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
			
				<div class="div_list">
					<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
						<tr>
							<td>
								<ec:table items="pageInfo.result" var="trade"
									action="${basePath}/firm/tariff/tradeFeeFirmList.action"											
									autoIncludeParameters="${empty param.autoInc}"
									xlsFileName="导出列表.xls" csvFileName="导出列表.csv"
									showPrint="true" listWidth="100%"
									minHeight="345" resizeColWidth="true" >
									<ec:row>
									    <ec:column property="NAME" title="加盟商名称" width="15%" style="text-align:center;" ellipsis="true"/>
										<ec:column property="BROKERID" title="加盟商编号" width="8%"
											style="text-align:center;" />
										<ec:column property="FIRMNAME" title="交易商名称" width="15%"
											style="text-align:center;" ellipsis="true"/>
										<ec:column property="FIRMID" title="交易商代码" width="8%"
											style="text-align:center;" />
										<ec:column property="SUMTRADEFEE" title="手续费" format="#,##0.00" calcTitle="合计" cell="number"
											calc="total" width="8%" style="text-align:right;" />
										<ec:column property="SUMSELFGAIN" title="加盟商手续费"
											format="#,##0.00"  cell="number" calc="total" width="8%"
											style="text-align:right;" />
										<ec:column property="SUMMARHETGAIN" title="交易所手续费"
											format="#,##0.00"  cell="number" calc="total" width="8%"
											style="text-align:right;" />
										<ec:column property="SUMBROKEREACHDIVIDE" title="加盟商手续费分成"
											format="#,##0.00"  cell="number" calc="total" width="10%"
											style="text-align:right;" />
										<ec:column property="SUMFINALMARHETGAIN" title="交易所实得手续费"
											format="#,##0.00"  cell="number" calc="total" width="10%"
											style="text-align:right;" />
										<ec:column property="SUMREWARD" title="加盟商实得手续费"
											format="#,##0.00"  cell="number" calc="total" width="10%"
											style="text-align:right;" />
									</ec:row>
								</ec:table>
							</td>
						</tr>
					</table>
				</div>
			</td>
		</tr>
	</table>
</div>
	<!-- 编辑和过滤所使用的 通用的文本框模板 -->
	<textarea id="ecs_t_input" rows="" cols="" style="display:none">
		<input type="text" class="inputtext" value="" onblur="ECSideUtil.updateEditCell(this)" 
		 style="width:100%;" name="" />
	</textarea>	
	<!-- 编辑状态所用模板 -->
	<textarea id="ecs_t_status" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="status" >
			<ec:options items="BS_FLAG1" />
		</select>
	</textarea>	
	<!-- 编辑状态所用模板 -->
	<textarea id="ecs_t_DelayOrderType" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="DelayOrderType" >
			<ec:options items="DELAYORDERTYPE" />
		</select>
	</textarea>	
	<!-- 编辑状态所用模板 -->
	<textarea id="ecs_t_status2" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="status" >
			<ec:options items="TRADETYPE" />
		</select>
	</textarea>	
</body>
</html>
