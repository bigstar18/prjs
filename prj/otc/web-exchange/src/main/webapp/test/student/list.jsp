<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<html>
	<head>
		<title>系统用户浏览</title>
		<%@ include file="/public/ecsideLoad.jsp"%>

	</head>
	<body>
		<br/>
		<form name="myForm" action="${basePath}/stu/list.action" method="post">
			<fieldset width="50%" height="60%">
				<legend>
					查询条件
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
							姓名:
							<input type="text" name="_name[like]"
								value="${oldParams['name[like]'] }" />
							&nbsp;&nbsp;
						</td>
						<td>
							<input type="submit" value="查询" />
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
						添加
					</button>
					&nbsp;&nbsp;
					<button id='deleteStuTest' style="color: red" onclick="deleteByCheckBox()">
						删除
					</button>
					&nbsp;&nbsp;&nbsp;
				</td>
			</tr>
		</table>
		<br />
		<br />
		<%@ include file="table.jsp"%>
		<script type="text/javascript" src="${publicPath}/ecsideInit.js"></script>
		<!-- 编辑和过滤所使用的 通用的文本框模板 -->
		<textarea id="ecs_t_input" rows="" cols="" style="display: none">
			<input type="text" class="inputtext" value=""
				onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="" />
		</textarea>

		<!-- 增删改action -->
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
