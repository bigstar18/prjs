<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<title>�������</title>
		<SCRIPT type="text/javascript">
		<!-- 		
			function ecReloadF(){
				
				if (document.readyState == "complete") // ��ҳ�����״̬Ϊ��ȫ����ʱ����
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
							<ec:column property="actionId" width="10%" title="���" style="text-align:center;" sortable="false" />												
							<ec:column property="actionNote" title="��������˵��" width="10%" style="text-align:center;" sortable="false" />
							<ec:column property="status" title="״̬" width="10%" style="text-align:center;" sortable="false" >${clearStatus_statusMap[clearStatus.status]}</ec:column>
							<ec:column property="finishTime" title="���ʱ��" width="10%" style="text-align:center;" sortable="false" ><fmt:formatDate value="${clearStatus.finishTime }" pattern="yyyy-MM-dd HH:mm:ss"/></ec:column>
						</ec:row>
					</ec:table>
				</td>
			</tr>
		</table>
		<!-- �༭�͹�����ʹ�õ� ͨ�õ��ı���ģ�� -->
		<textarea id="ecs_t_input" rows="" cols="" style="display: none">
			<input type="text" class="inputtext" value="" onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;" name="" />
		</textarea>
	</body>
</html>