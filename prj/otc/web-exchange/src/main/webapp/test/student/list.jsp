<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<html>
	<head>
		<title>ϵͳ�û����</title>
		<%@ include file="/public/ecsideLoad.jsp"%>

	</head>
	<body>
		<br/>
		<form name="myForm" action="${basePath}/stu/list.action" method="post">
			<fieldset width="50%" height="60%">
				<legend>
					��ѯ����
				</legend>
				<table>
					<tr>
						<td>
							id:
							<input type="text" name="_id[=][long]"
								value="${oldParams['id[=][long]'] }" />
							&nbsp;&nbsp;&nbsp;
						</td>
						<td>
							����:
							<input type="text" name="_name[like]"
								value="${oldParams['name[like]'] }" />
							&nbsp;&nbsp;
						</td>
						<td>
							<input type="submit" value="��ѯ" />
						</td>
					</tr>
				</table>
			</fieldset>
		</form>
		<table id="editer" border="0" cellspacing="0" cellpadding="0"
			width="100%" align="right">
			<tr>
				<td align="right">
					<button id='addStuTest' style="color: red" onclick="add()">
						���
					</button>
					&nbsp;&nbsp;
					<button id='deleteStuTest' style="color: red" onclick="deleteByCheckBox()">
						ɾ��
					</button>
					&nbsp;&nbsp;&nbsp;
				</td>
			</tr>
		</table>
		<br />
		<br />
		<%@ include file="table.jsp"%>
		<script type="text/javascript" src="${publicPath}/ecsideInit.js"></script>
		<!-- �༭�͹�����ʹ�õ� ͨ�õ��ı���ģ�� -->
		<textarea id="ecs_t_input" rows="" cols="" style="display: none">
			<input type="text" class="inputtext" value=""
				onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="" />
		</textarea>

		<!-- ��ɾ��action -->
		<SCRIPT type="text/javascript">
		function add(){
			var url="${basePath}/student/forwardAdd.action";
			ecsideDialog(url);
		}
		function update(id){
			var url="${basePath}/student/forwardUpdate.action?obj.id="+id;
			ecsideDialog(url);
		}
			
		function deleteByCheckBox(){
			var url="${basePath}/student/delete.action";
			deleteEcside(ec.ids,url);
		}
		</SCRIPT>

		
	</body>
</html>
