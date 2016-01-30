<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<title>字典列表查询</title>
		<script type="text/javascript">
			function select1(){<%//执行查询功能%>
				frm.submit();
			}
		</script>
	</head>
	<body>
		<table class="table1_style" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td>
					<div class="div_cx">
						<form name="frm" action="${basePath}/bank/other/dictionaryList.action?sortColumns=order+by+id" method="post">
						</form>
					</div>
					<div class="div_list">
						<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
							<tr>
								<td>
									<ec:table items="pageInfo.result" var="dictionary"
										action="${basePath}/bank/other/dictionaryList.action"											
										autoIncludeParameters="${empty param.autoInc}"
										xlsFileName="firm.xls" csvFileName="firm.csv"
										showPrint="true" listWidth="100%"
										minHeight="345"  style="table-layout:fixed;">
										<ec:row>
											<ec:column width="5%" property="_0" title="序号" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" style="text-align:right;"/>
											<ec:column property="id" width="10%" title="字典编号" style="text-align:right;"/>
											<ec:column property="type" width="10%" title="分类" style="text-align:center;">${dictionaryType[dictionary.type]}</ec:column>
											<ec:column property="bank.bankID" title="银行代码" width="10%" style="text-align:center;"/>
											<ec:column property="name" title="数据项名称" width="10%" style="text-align:center;"/>
											<ec:column property="value" title="数据值" width="10%" style="text-align:center;"/>
											<ec:column property="note" title="备注" width="10%" style="text-align:center;"/>
										</ec:row>
									</ec:table>
								</td>
							</tr>
						</table>
					</div>
				</td>
			</tr>
		</table>
		<!-- 编辑和过滤所使用的 通用的文本框模板 -->
		<textarea id="ecs_t_input" rows="" cols="" style="display: none">
			<input type="text" class="inputtext" value="" onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;" name="" />
		</textarea>
	</body>
</html>