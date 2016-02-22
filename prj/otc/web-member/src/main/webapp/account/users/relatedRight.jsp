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
			&nbsp;&nbsp;&nbsp;<img src="<%=skinPath%>/cssimg/xr.gif" align="absmiddle" />
			&nbsp;用户【${user.name}】关联角色
	</div>
		<c:set var="length" value="0"></c:set>
	<div style="position:absolute;top:40;z-index:1;overflow:auto;height:380px;">
		<table border="0" width="100%" align="center">
			<tr>
				<td align="center">
					<form name="frm"
						action="${basePath}/user/relatedRight.action?userId=${user.id}"
						method="post">
						<table id="tb" border="0" cellspacing="0" cellpadding="0"
							width="100%" style="word-break:break-all;table-layout:fixed;border-right: 1px solid #d9d9d9;border-top: 1px solid #d9d9d9;border-bottom: 1px solid #d9d9d9;">
								<tr height="25" align=center>
									<td width="20%" class="panel_tHead_MB" align=center>
										<input class=checkbox_1 type="checkbox" id="checkAll"
											onclick="relatedSelect(tb,'ck','operateCk',true)">所属角色
									</td>
									<td width="20%" class="panel_tHead_MB" align=center>
										角色名称
									</td>
									<td width="40%" class="panel_tHead_MB" align=center id="titTd">
										角色描述
									</td>
									<td width="20%" class="panel_tHead_MB" align=center id="hiddenTd">
									<input class=checkbox_1 type="checkbox" id="checkAllRight"
											onclick="relatedSelect(tb,'operateCk','ck',false)">
										角色赋予权限
									</td>
								</tr>
								<c:forEach items="${roleList }" var="role">
									<c:set var="length" value="${length+1}"></c:set>
									<tr align=center>
										<td class="tab_td_border" align=center>
											<input name="ck" type="checkbox" id="ck_${role.id}" value="${role.id }" onclick="relatedSelectOne('${role.id }',true)"
											<c:set var="flag" value="false"></c:set>
											<c:forEach items="${user.roleSet}" var="roleSe">
												<c:if test="${roleSe.id==role.id}">
													<c:set var="flag" value="true"></c:set>
												</c:if>
											</c:forEach>
											<c:if test="${flag=='true'}">
												checked=true
											</c:if>>
										</td>
										<td class="tab_td_border" align=left style="font-size: 12px;">
											${role.name }&nbsp;
										</td>
										<td class="tab_td_border" align=left style="font-size: 12px;" id="showTd_${length}">
											${role.description }&nbsp;
										</td>
										<td class="tab_td_border" align=center id="hiddenTd_${length}">
											<input name="operateCk" type="checkbox" value="${role.id }" id ="ock_${role.id }" onclick="relatedSelectOne('${role.id }',false)"
												<c:set var="flag" value="false"></c:set>
												<c:forEach items="${user.operateRoleSet}" var="roleSe">
													<c:if test="${roleSe.id==role.id}">
														<c:set var="flag" value="true"></c:set>
													</c:if>
												</c:forEach>
												<c:if test="${flag=='true'}">
													checked=true
												</c:if>>										
										</td>
									</tr>
								</c:forEach>
								
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
				<td align="center" id="tdId">
					<button class="btn_sec" onClick="relatedRight()" id=related>
						保存
					</button>
					</td>
					<td align="center">
					<button class="btn_sec" onClick="window.close()">
						关闭
					</button>
				</td>
			</tr>
		</table>
	</div>
</body>
<SCRIPT LANGUAGE="JavaScript">
var tableTd=document.getElementById('hiddenTd');
tableTd.style.display="none";
var userId='${user.userId}';
for(var i=1;i<=${length};i++){
	var tableTds=document.getElementById('hiddenTd_'+i);
	tableTds.style.display="none";
}

for(var i=1;i<=${length};i++){
	var tableTds=document.getElementById('showTd_'+i);
	var titTd=document.getElementById('titTd');
	//tableTds.className='tab_td_borderightleft';
	tableTds.style.width='60%';
	titTd.style.width='60%';
}
var reg =/^\w*DEFAULT_+\w*$/; 
var id='${user.type}';
var regSuper=/^\w*DEFAULT_SUPER_+\w*$/;
if(id.match(regSuper)!=null){
	var checkAllRight=document.getElementById("checkAllRight");
	var checkAll=document.getElementById("checkAll");
	checkAllRight.checked=true;
	checkAll.checked=true;
	var cks=document.getElementsByName('ck');
	var operateCks=document.getElementsByName('operateCk');
	for(var i=0;i<operateCks.length;i++){
		operateCks[i].checked=true;
	}
	for(var i=0;i<cks.length;i++){
		cks[i].checked=true;
	}
}
if(id.match(reg)!=null ||userId==currenUserId){
	var tdId=document.getElementById("tdId");
	tdId.style.display="none";
	var checkAllRight=document.getElementById("checkAllRight");
	var checkAll=document.getElementById("checkAll");
	checkAllRight.disabled='disabled';
	checkAll.disabled='disabled';
	var cks=document.getElementsByName('ck');
	var operateCks=document.getElementsByName('operateCk');
	for(var i=0;i<operateCks.length;i++){
		operateCks[i].disabled='disabled';
	}
	for(var i=0;i<cks.length;i++){
		cks[i].disabled='disabled';
	}
}

if(${ISSUPERADMIN}){
	tableTd.style.display="";
	for(var i=1;i<=${length};i++){
	var tableTds=document.getElementById('hiddenTd_'+i);
	tableTds.style.display="";
	
	var tableTds=document.getElementById('showTd_'+i);
	var titTd=document.getElementById('titTd');
	tableTds.style.width='40%';
	titTd.style.width='40%';
	}
}
	//用户关联权限
	function relatedRight(){
		if(userConfirm()){
			sign = true;
		  }else{
			return false;
		  }
	//防止重复提交
	if(sign){
			frm.submit();
		}
	}
//对一个表格中的多选框执行全选/全反选的操作
//tblObj:表格对象
//childName:checkbox对象的ID
function relatedSelect( tblObj , childName,childName2,flag )
{
	
	var checkAll=document.getElementById("checkAll");
	var checkAllRight=document.getElementById("checkAllRight");
	if(flag)
	{		
		if(!checkAll.checked)
		checkAllRight.checked = false;
	}
	else
	{
		if(checkAllRight.checked)
		checkAll.checked = true;
	}
	
	var objCheck = event.srcElement ;
	var collCheck,collCheckTarget;
	  
		var collCheck = document.all.namedItem(childName);
		
		var collCheckTarget = document.all.namedItem(childName2);
		
	if(collCheck)
	{	  
	  //所属角色操作，如果取消，另一边也同时取消
		if(flag)
		{
			if(!collCheck.length)
			{
				collCheck.checked = (objCheck.checked==true)? true:false;
				if(!collCheck.checked)
				{
					collCheckTarget.checked=false;
				}
			}
			else
			{
				for(var i=0;i < collCheck.length;i++ )
				{
					collCheck[i].checked = (objCheck.checked==true)? true:false;
					if(!collCheck[i].checked)
					{
						collCheckTarget[i].checked=false;
					}
				}
			} 
		}		
		else
		{
			if(!collCheck.length)
			{
				collCheck.checked = (objCheck.checked==true)? true:false;
				if(collCheck.checked){
					collCheckTarget.checked=true;
				}
			}
			else
			{
				for(var i=0;i < collCheck.length;i++ )
				{
					collCheck[i].checked = (objCheck.checked==true)? true:false;
					if(collCheck[i].checked)
					{
						collCheckTarget[i].checked=true;
					}
				}
			}
		}	  
	}
	else
	{
		objCheck.disabled = true;
	}
}

//对一个表格中的多选框执行全选/全反选的操作
//tblObj:表格对象
//childName:checkbox对象的ID
function relatedSelectOne( cellId , flag )
{	
	var checkAll=document.getElementById("checkAll");
	var checkAllRight=document.getElementById("checkAllRight");
			
	var checkSrc=document.getElementById("ck_" + cellId);
	var checkDes=document.getElementById("ock_" + cellId);
	if(flag)
	{		
		if(!checkSrc.checked)
		{
			checkDes.checked = false;
			
			checkAllRight.checked = false;
			checkAll.checked = false;
		}
		else
		{
			CheckSelectAll("ck","checkAll");
		}
	}
	else
	{
		if(checkDes.checked)
		{
			checkSrc.checked = true;			
			CheckSelectAll("operateCk","checkAllRight");
			CheckSelectAll("ck","checkAll");
		}
		else
		{			
			checkAllRight.checked = false;			
		}
	}		
}

function CheckSelectAll(name,taget)
{	
	var header=document.getElementById(taget);
	var checkAll=document.getElementsByName(name);
	var flag = true;
	for(var i=0;i < checkAll.length;i++ )
	{
		if(!checkAll[i].checked)
		{
			flag = false;
			break;
		}
	}
	if(flag)
	{
		header.checked = true;
	}
}
 function userConfirm(){
   if(affirm("您确实要操作这些数据吗？"))
   { 
     return true;
   }else{
     return false;
   }
 }
</SCRIPT>
<%@ include file="/public/footInc.jsp"%>