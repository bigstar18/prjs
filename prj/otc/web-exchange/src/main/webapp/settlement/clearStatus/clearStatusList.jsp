<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<%@page import="java.util.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
		<%@ include file="/public/ecsideLoad.jsp"%>
		<style type="text/css">
</style>
	</head>
	<body>
			<table id="tb" border="0" cellspacing="0" cellpadding="0"
				width="100%">
				<tr>
					<td>
						<ec:table items="resultList" 
							autoIncludeParameters="${empty param.autoInc}" var="clearStatus"
							action="${basePath}/settlement/clearStatus/list.action" title=""
							minHeight="345" listWidth="100%" style="table-layout:fixed"
							retrieveRowsCallback="limit" sortRowsCallback="limit"
							filterRowsCallback="limit" toolbarContent="refresh|extend">
							<ec:row recordKey="${clearStatus.actionId}">
								<ec:column width="8%" property="_0" title="序号" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" />
								<ec:column property="primary.actionNote[like]" title="步骤名称说明"
									width="30%" style="text-align:center;"
									editTemplate="ecs_note" filterable="false" sortable="false">
									 ${clearStatus.actionNote }
								</ec:column>
								<ec:column property="primary.status[=][String]"
									title="状态" width="30%" style="text-align:center;"
									  editTemplate="ecs_status" filterable="false" sortable="false">
									 ${clearStatusMap[clearStatus.status] }	
								</ec:column>
								<ec:column property="primary.finishTime[=][Date]"
									title="完成时间" width="30%" style="text-align:center;"
									  editTemplate="ecs_note" filterable="false" sortable="false">
									 ${datefn:formatdate(clearStatus.finishTime) }	
								</ec:column>
							</ec:row>
						</ec:table>
					</td>
				</tr>
			</table>
		<!-- 编辑和过滤所使用的 通用的文本框模板 -->
		<textarea id="ecs_t_input" rows="" cols="" style="display: none">
			<input type="text" class="inputtext" value=""
				onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="" />
		</textarea>
		<!-- 编辑和过滤状态的文本框模板 -->
		<textarea id="ecs_status" rows="" cols=""
			style="display: none">
		<select onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="primary.status[=][String]">
			<ec:options items="clearStatusMap" />
		</select>
	    </textarea>
		<SCRIPT type="text/javascript">
			/**setInterval("ECSideUtil.reload('ec')",1000);*/
			function ecReloadF(){
				if (document.readyState == "complete") // 当页面加载状态为完全结束时进入
				{
					 ECSideUtil.reload('ec');
				}else
				{
				}
					setTimeout("ecReloadF()",1000);
			}
		ecReloadF();
		</SCRIPT>
	</body>
</html>
