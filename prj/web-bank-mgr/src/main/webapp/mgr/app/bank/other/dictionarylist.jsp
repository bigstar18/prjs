<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<title>�ֵ��б��ѯ</title>
		<script type="text/javascript">
			function select1(){<%//ִ�в�ѯ����%>
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
											<ec:column width="5%" property="_0" title="���" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" style="text-align:right;"/>
											<ec:column property="id" width="10%" title="�ֵ���" style="text-align:right;"/>
											<ec:column property="type" width="10%" title="����" style="text-align:center;">${dictionaryType[dictionary.type]}</ec:column>
											<ec:column property="bank.bankID" title="���д���" width="10%" style="text-align:center;"/>
											<ec:column property="name" title="����������" width="10%" style="text-align:center;"/>
											<ec:column property="value" title="����ֵ" width="10%" style="text-align:center;"/>
											<ec:column property="note" title="��ע" width="10%" style="text-align:center;"/>
										</ec:row>
									</ec:table>
								</td>
							</tr>
						</table>
					</div>
				</td>
			</tr>
		</table>
		<!-- �༭�͹�����ʹ�õ� ͨ�õ��ı���ģ�� -->
		<textarea id="ecs_t_input" rows="" cols="" style="display: none">
			<input type="text" class="inputtext" value="" onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;" name="" />
		</textarea>
	</body>
</html>