<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
	    <base target="_self" />
		<title>仓单明细</title>
		<script type="text/javascript">
		function cancel_onclick()
		{
		   window.close();
		}
		</script>
	</head>
<body>
		<div class="div_cx">
			<table border="0" width="100%" align="center">
		 
			<tr>
				<td>
					<div class="warning">
						<div class="content">
							温馨提示 :仓单明细
						</div>
					</div>
				</td>
			</tr>
			
			</table>
		</div>
			

		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0" cellpadding="0">
				<tr><td>
					<div class="div_list">
						<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
							<tr>
								<td class="" align="left" style = "width: 80%;">
									<c:if test = "${not empty (remark[0]['REMARK'])}">
										<span>备注：${remark[0]['REMARK'] }</span>
									</c:if>
								</td>
								<td class="table3_td_1" align="right" style = "width : 20%;">
									<button class="btn_sec" id="view" onclick="javascript:return cancel_onclick();">关闭</button>
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<ec:table items="pageInfo.result" var="bill"
										action="${basePath}/timebargain/bill/gageBillDetail.action?gageBillID=${gageBillID }"											
										autoIncludeParameters="${empty param.autoInc}"
										xlsFileName="export.xls" csvFileName="export.csv"
										showPrint="true" listWidth="100%"
										minHeight="345"  style="table-layout:fixed;">
										<ec:row>
											<ec:column property="_1" title=" " width="5%" style="text-align:center;">
									            <img title="点击查看详细信息" src="<c:url value="${skinPath}/image/app/timebargain/right.gif"/>"/>
									        </ec:column>
											<ec:column property="STOCKID" width="15%" title="仓单号" style="text-align:center;" />
											<ec:column property="WAREHOUSEID" title="仓库编号" width="16%" style="text-align:center;"/>
											<ec:column property="BREEDNAME" title="商品品种名称" width="16%" style="text-align:center;"/>
											<ec:column property="QUANTITYUNIT" title="商品数量" width="16%" style="text-align:center;"/>
											<ec:column property="BILLNUM" title="仓单数量" width="10%" style="text-align:center;"/>
											<ec:column property="LASTTIME" title="最后变更时间" width="22%" style="text-align:center;"/>
										</ec:row>
									</ec:table>
								</td>
							</tr>
						</table>
					</div>	
					
				</td></tr>
			</table>
		</div>
				<!-- 编辑和过滤所使用的 通用的文本框模板 -->
		<textarea id="ecs_t_input" rows="" cols="" style="display:none">
			<input type="text" class="inputtext" value="" onblur="ECSideUtil.updateEditCell(this)" 
			 style="width:100%;" name="" />
		</textarea>
	</body>
</html>

