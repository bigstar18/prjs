<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/common/public/common.jsp"%>
<%@ page import="gnnt.MEBS.config.constant.ActionConstant"%>
<c:set var="GNNT_" value="<%=ActionConstant.GNNT_%>"/>

<base target="_self">
<head>
	<title>添加关联管理员</title>
</head>
<script language="javascript"
	src="<%=serverPath%>/public/jslib/pageAndOrder.js">
</script>
<style>
checkbox_1 {
	border-width: 0px;
	border-left-style: none;
	border-right-style: none;
	border-top-style: none;
	border-bottom-style: none;
	font-family: SimSun;
	font-size: 9pt;
	color: #333333;
	background-color: transparent;
}
</style>
<body style="overflow-y: hidden">
<div class="st_title">
			&nbsp;&nbsp;<img src="<%=skinPath%>/cssimg/st_ico1.gif"
				align="absmiddle" />
			&nbsp;&nbsp;角色${role.name }[${role.id}]可关联的用户信息
		</div>
	<div style="overflow-x:hidden;position:absolute;top:50;z-index:1;overflow:auto;height:450px;">
		
		<table border="0" width="100%" align="center">
			<tr>
				<td>
					<form name="selectFrm"
						action="<%=basePath%>/role/forwardAddAssociation.action?id=${role.id }"
						method="post">
						<table border="0" cellspacing="0" cellpadding="0" width="100%"
							class="st_bor">
							<tr height="35">
								<td align="center" class="td_size">
									用户代码 ：
									<input type="text" name="${ GNNT_}primary.userId[like]" id="selectUserId" size="12"
										value="${oldParams['primary.userId[like]'] }">
								</td>
								<td align="center" class="td_size">
									用户名称 ：
									<input type="text" name="${ GNNT_}primary.name[like]" id="selectName" size="12"
										value="${oldParams['primary.name[like]'] }">
								</td>
							</tr>
							<tr height="35">
								<td align="right" colspan="2">
								  <button class="btn_sec" onclick="select1(${role.id})">
										查询
									</button>&nbsp;&nbsp;
									<button class="btn_cz" onclick="resetNun(${role.id})">
										重置
									</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								</td>
							</tr>
						</table>
					</form>
					<br>
					<form name="frm"
						action="<%=basePath%>/role/addCommonRoleSaveAssociation.action?id=${role.id }"
						method="post" targetType="hidden">
						<table id="tb" border="0" cellspacing="0" cellpadding="0"
							width="100%" height="300">
							<tHead>
								<tr height="25" align=center>
									<td class="panel_tHead_LB">
										&nbsp;
									</td>
									<td class="panel_tHead_MB" align=center>
										<input class=checkbox_1 type="checkbox" id="checkAll"
											onclick="selectAll(tb,'ck')">
									</td>
									<td class="panel_tHead_MB" align=center>
										用户代码
									</td>
									<td class="panel_tHead_MB" align=center>
										用户名称
									</td>
									<td class="panel_tHead_MB" align=center>
										用户描述
									</td>
									<td class="panel_tHead_RB">
										&nbsp;
									</td>
								</tr>
							</tHead>
							<tBody>
								<c:forEach items="${userList }" var="user">
									<tr onclick="selectTr();" align=center height="25">
										<td class="panel_tBody_LB">
											&nbsp;
										</td>
										<td class="underLine" align=center >
											<input name="ck" type="checkbox" value="${user.userId }">
										</td>
										<td class="underLine" align=left style="font-size: 12px;">
											${user.userId }&nbsp;
										</td>
										<td class="underLine" align=left style="font-size: 12px;">
											${user.name }&nbsp;
										</td>
										<td class="underLine" align=left style="font-size: 12px;">
											${user.description }&nbsp;
										</td>
										<td class="panel_tBody_RB">
											&nbsp;
										</td>
									</tr>
								</c:forEach>
							</tBody>
							<tFoot>
								<tr height="100%">
									<td class="panel_tBody_LB">
										&nbsp;
									</td>
									<td colspan="4">
										&nbsp;
									</td>
									<td class="panel_tBody_RB">
										&nbsp;
									</td>
								</tr>
								<tr height="22">
									<td class="panel_tBody_LB">
										&nbsp;
									</td>
									<td colspan="4" align="right" class="pagerext">
									</td>
									<td class="panel_tBody_RB">
										&nbsp;
									</td>
								</tr>
								<tr height="22">
									<td class="panel_tFoot_LB">
										&nbsp;
									</td>
									<td class="panel_tFoot_MB" colspan="4"></td>
									<td class="panel_tFoot_RB">
										&nbsp;
									</td>
								</tr>
							</tFoot>
						</table>
					</form>
				</td>
			</tr>
		</table>
	</div>
	<div class="tab_pad">
		<table border="0" cellspacing="0" cellpadding="0" width="100%"
			align="center">
			<tr height="35">
				<td align="center">
					<button class="btn_sec" onClick="addAssociation()" id="add">
						添加
					</button>
					&nbsp;&nbsp;
					<button class="btn_sec" onClick="window.close()">
						关闭
					</button>
				</td>
			</tr>
		</table>
	</div>
</body>
<SCRIPT LANGUAGE="JavaScript">
	//添加新用户
	function addAssociation()
	{
		var ckValues = document.getElementsByName("ck");
		var subMark = false;
		var recordNum = 0;
		for(var i=0;i<ckValues.length;i++)
		{
			if(ckValues[i].checked == true)
			{
				recordNum ++;
			}
		}
		if(recordNum>0)
		{
			subMark = true;
		}
		
		if(subMark)
		{
			if(affirm("确认提交?"))
			{
				frm.submit();
			}
		}
		else
		{
			alert("没有可操作数据!");
		}
	}
	function select1(id){
		selectFrm.action="<%=basePath%>/role/forwardAddAssociation.action?id="+id;
		selectFrm.submit();
	}
	function resetNun(id){
		document.getElementById("selectUserId").value="";
		document.getElementById("selectName").value="";
		selectFrm.action="<%=basePath%>/role/forwardAddAssociation.action?id="+id;
		selectFrm.submit();
	}
</SCRIPT>
<%@ include file="/public/footInc.jsp"%>