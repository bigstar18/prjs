<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<%@ page import="gnnt.MEBS.config.constant.ActionConstant"%>
<c:set var="GNNT_" value="<%=ActionConstant.GNNT_%>"/>

<base target="_self">
<head>
	<title>关联角色</title>
</head>
<body style="overflow-y: hidden">
    <div class="st_title">
			&nbsp;&nbsp;&nbsp;
	</div>
	<div style="position:absolute;top:40;z-index:1;overflow:auto;height:380px;">
		<table border="0" width="100%" align="center">
			<tr>
				<td align="center">
					<form name="frm"
						action="${basePath}/user/relatedRight.action?userId=${user.id}"
						method="post">
						<table id="tb" border="0" cellspacing="0" cellpadding="0"
							width="100%" height="360" style="table-layout:fixed">
							<tHead>
								<tr height="25" align=center>
									<td width="10%" class="panel_tHead_MB" align=center>
										
									</td>
									<td width="90%" class="panel_tHead_MB" align="center">
										特别会员名称
									</td>
								</tr>
							</tHead>
							<tBody>
								<c:forEach items="${specialMemberList }" var="specialMember">
									<tr align="center" height="25">
										<td class="underLine" align=center>
											<input name="ck" type="checkbox" id="${specialMember.id}" value="${specialMember.id }"
										</td>
										<td class="underLine" align="center" style="font-size: 12px;">
											${specialMember.name }&nbsp;
										</td>
									</tr>
								</c:forEach>
							</tBody>
							<tFoot>
								<tr>
									<td class="panel_tBody_LB">
										&nbsp;
									</td>
									<td colspan="2">
										&nbsp;
									</td>
									<td class="panel_tBody_RB">
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
					<button class="btn_sec" onClick="relatedRight()">
						确定
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
	//用户关联权限
	function relatedRight(){
		var cks=document.getElementsByName('cks');
		var opearteCks=document.getElementsByName('operateCk');
		for(var i=0;i<opearteCks.length;i++){
			if(opearteCks[i].checked){
				var ck=document.getElementById(opearteCks[i].value);
				if(!ck.checked){
					alert('数据选择不正确，请重新选择！');
					return false;
				}
			}
		}
		frm.submit();
	}
	
</SCRIPT>
<%@ include file="/public/footInc.jsp"%>