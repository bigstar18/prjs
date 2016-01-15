<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<title>财务结算</title>
		<SCRIPT type="text/javascript">
		<!-- 		
			function ecReloadF(){
				
				if (document.readyState == "complete") // 当页面加载状态为完全结束时进入
				{
					 ECSideUtil.reload('ec');
				}
				setTimeout("ecReloadF()",1000);
			}
			
			ecReloadF();		
		//-->
		</SCRIPT>
	</head>
	<body>
		<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%" height="100%">
			<tr valign="top">
				<td>
					<ec:table items="pageInfo.result" var="clearStatus"
						action="${basePath}/finance/clearStatus/clearStatusList.action?sortColumns=order+by+actionId"											
						autoIncludeParameters="${empty param.autoInc}"
						xlsFileName="demo.xls" csvFileName="demo.csv"
						showPrint="true" listWidth="100%"
						minHeight="180"  style="table-layout:fixed"
						retrieveRowsCallback="limit" sortRowsCallback="limit"
						filterRowsCallback="limit" toolbarContent="refresh|extend">
						<ec:row>
							<ec:column property="actionId" width="10%" title="序号" style="text-align:center;" sortable="false" />												
							<ec:column property="actionNote" title="步骤名称说明" width="10%" style="text-align:center;" sortable="false" />
							<ec:column property="status" title="状态" width="10%" style="text-align:center;" sortable="false" >${clearStatus_statusMap[clearStatus.status]}</ec:column>
							<ec:column property="finishTime" title="完成时间" width="10%" style="text-align:center;" sortable="false" ><fmt:formatDate value="${clearStatus.finishTime }" pattern="yyyy-MM-dd HH:mm:ss"/></ec:column>
						</ec:row>
					</ec:table>
				</td>
			</tr>
		</table>
		<!-- 编辑和过滤所使用的 通用的文本框模板 -->
		<textarea id="ecs_t_input" rows="" cols="" style="display: none">
			<input type="text" class="inputtext" value="" onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;" name="" />
		</textarea>
	</body>
</html>