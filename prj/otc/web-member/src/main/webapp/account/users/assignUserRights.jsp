<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="/common/public/common.jsp"%>
<script language="javascript">
		var rightMap=${sessionScope.rightMap};
</script>
<script type="text/javascript" src="<%=basePath%>/public/limit.js"
			defer="defer"></script>
<base target="_self">
<c:if test="${not empty modSuccess }">
	<script>
		window.returnValue=1;
		window.close();
	</script>
</c:if>
<style>
.cls
{
	display:none;
}
.opn
{
	display:;
}
</style>
<body style="overflow-y: hidden">
<form name="frm" method="post">
<div class="st_title"><img src="<%=skinPath%>/cssimg/st_ico1.gif" align="absmiddle" />&nbsp;&nbsp;设置用户权限</div>
		<div style="overflow:auto;height:500px;position:absolute;top:55;z-index:1;overflow-x: hidden">
			<table border="0" width="90%" align="center">
				<tr>
					<td>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" class="st_bor">			  
				<tr height="35">
            		<td align="" width="20%" class="td_size">
            			用户代码：${user.userId }<input name="userId" type="hidden" value="${user.userId }">
            		</td>               
				</tr>
				<tr height="35">
				<td align="left" class="td_size">
				
				<!-- 权限根节点 -->
				<div id=div_ck class=opn>&nbsp;&nbsp;
				<c:set var="userDispatchMark" value="false"/>
				<c:forEach items="${user.rightSet }" var="userRight">
					<c:if test="${userRight.id == treeRight.id }">
						<c:set var="userDispatchMark" value="true"/>
					</c:if>
				</c:forEach>
				<c:set var="roleDispatchMark" value="false"/>
				<c:forEach items="${user.roleSet }" var="role">
					<c:forEach items="${role.rightSet }" var="roleRight">
						<c:if test="${roleRight.id == treeRight.id }">
							<c:set var="roleDispatchMark" value="true"/>
						</c:if>
					</c:forEach>
				</c:forEach>
				<c:choose>
					<c:when test="${userDispatchMark&&!roleDispatchMark }">
						<input type="checkbox"  name="ck" checked="checked" onclick="selectCks('${treeRight.id }');setParents('${treeRight.right.id }');" value="${treeRight.id }">
						<span style="cursor:hand;text-decoration: underline;" onclick="clkDiv('${treeRight.id }')">${treeRight.name }</span>
					</c:when>
					<c:when test="${roleDispatchMark }">
						<input type="checkbox"  name="ck" checked="checked" onclick="selectCks('${treeRight.id }');setParents('${treeRight.right.id }');" value="${treeRight.id }" disabled="disabled">
						<span style="cursor:hand;text-decoration: underline;" onclick="clkDiv('${treeRight.id }')">${treeRight.name }</span>
					</c:when>
					<c:otherwise>
						<input type="checkbox"  name="ck" onclick="selectCks('${treeRight.id }');setParents('${treeRight.right.id }');" value="${treeRight.id }">
						<span onclick="clkDiv('${treeRight.id }')">${treeRight.name }</span>
					</c:otherwise>
				</c:choose>
				<input type=hidden name="pid" value="${treeRight.right.id }">
				<input type="hidden" name="div_st" value="1"><br></div>
				
				<!-- 模块权限 -->
				<c:forEach items="${treeRight.rightSet }" var="moduleRight">
					<c:set var="userDispatchModuleMark" value="false"/>
					<c:forEach items="${user.rightSet }" var="userRight">
						<c:if test="${userRight.id == moduleRight.id }">
							<c:set var="userDispatchModuleMark" value="true"/>
						</c:if>
					</c:forEach>
					<c:set var="roleDispatchModuleMark" value="false"/>
					<c:forEach items="${user.roleSet }" var="role">
						<c:forEach items="${role.rightSet }" var="roleRight">
							<c:if test="${roleRight.id == moduleRight.id }">
								<c:set var="roleDispatchModuleMark" value="true"/>
							</c:if>
						</c:forEach>
					</c:forEach>
					<div id=div_ck class=opn>&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;
					<c:choose>
						<c:when test="${userDispatchModuleMark&&!roleDispatchModuleMark }">
							<input type="checkbox"  name="ck" checked="checked" onclick="selectCks('${moduleRight.id }');setParents('${moduleRight.right.id }');" value="${moduleRight.id }">
							<span style="cursor:hand;text-decoration: underline;" onclick="clkDiv('${moduleRight.id }')">${moduleRight.name }</span>
						</c:when>
						<c:when test="${roleDispatchModuleMark }">
							<input type="checkbox"  name="ck" checked="checked" onclick="selectCks('${moduleRight.id }');setParents('${moduleRight.right.id }');" value="${moduleRight.id }" disabled="disabled">
							<span style="cursor:hand;text-decoration: underline;" onclick="clkDiv('${moduleRight.id }')">${moduleRight.name }</span>
						</c:when>
						<c:otherwise>
							<input type="checkbox"  name="ck" onclick="selectCks('${moduleRight.id }');setParents('${moduleRight.right.id }');" value="${moduleRight.id }">
					<span onclick="clkDiv('${moduleRight.id }')">${moduleRight.name }</span>
						</c:otherwise>
					</c:choose>
					<input type=hidden name="pid" value="${moduleRight.right.id }">
					<input type="hidden" name="div_st" value="1"><br></div>
				
					<!-- 一级菜单权限 -->
					<c:forEach items="${moduleRight.rightSet }" var="menuRight">
						<c:set var="userDispatchMenuMark" value="false"/>
						<c:forEach items="${user.rightSet }" var="userRight">
							<c:if test="${userRight.id == menuRight.id }">
								<c:set var="userDispatchMenuMark" value="true"/>
							</c:if>
						</c:forEach>
						<c:set var="roleDispatchMenuMark" value="false"/>
						<c:forEach items="${user.roleSet }" var="role">
							<c:forEach items="${role.rightSet }" var="roleRight">
								<c:if test="${roleRight.id == menuRight.id }">
									<c:set var="roleDispatchMenuMark" value="true"/>
								</c:if>
							</c:forEach>
						</c:forEach>
						<div id=div_ck class=opn>&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<c:choose>
							<c:when test="${userDispatchMenuMark&&!roleDispatchMenuMark }">
								<input type="checkbox"  name="ck" checked="checked" onclick="selectCks('${menuRight.id }');setParents('${menuRight.right.id }');" value="${menuRight.id }">
								<span style="cursor:hand;text-decoration: underline;" onclick="clkDiv('${menuRight.id }')">${menuRight.name }</span>
							</c:when>
							<c:when test="${roleDispatchMenuMark }">
								<input type="checkbox"  name="ck" checked="checked" onclick="selectCks('${menuRight.id }');setParents('${menuRight.right.id }');" value="${menuRight.id }" disabled="disabled">
								<span style="cursor:hand;text-decoration: underline;" onclick="clkDiv('${menuRight.id }')">${menuRight.name }</span>
							</c:when>
							<c:otherwise>
								<input type="checkbox"  name="ck" onclick="selectCks('${menuRight.id }');setParents('${menuRight.right.id }');" value="${menuRight.id }">
						<span onclick="clkDiv('${menuRight.id }')">${menuRight.name }</span>
							</c:otherwise>
						</c:choose>
						<input type=hidden name="pid" value="${menuRight.right.id }">
						<input type="hidden" name="div_st" value="1"><br></div>
					
						<!-- 二级菜单权限 -->
						<c:forEach items="${menuRight.rightSet }" var="childMenuRight">
							<c:set var="userDispatchChildMenuMark" value="false"/>
							<c:forEach items="${user.rightSet }" var="userRight">
								<c:if test="${userRight.id == childMenuRight.id }">
									<c:set var="userDispatchChildMenuMark" value="true"/>
								</c:if>
							</c:forEach>
							<c:set var="roleDispatchChildMenuMark" value="false"/>
							<c:forEach items="${user.roleSet }" var="role">
								<c:forEach items="${role.rightSet }" var="roleRight">
									<c:if test="${roleRight.id == childMenuRight.id }">
										<c:set var="roleDispatchChildMenuMark" value="true"/>
									</c:if>
								</c:forEach>
							</c:forEach>
							<div id=div_ck class=opn>&nbsp;&nbsp;
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<c:choose>
								<c:when test="${userDispatchChildMenuMark&&!roleDispatchChildMenuMark }">
									<input type="checkbox"  name="ck" checked="checked" onclick="selectCks('${childMenuRight.id }');setParents('${childMenuRight.right.id }');" value="${childMenuRight.id }">
									<span style="cursor:hand;text-decoration: underline;" onclick="clkDiv('${childMenuRight.id }')">${childMenuRight.name }</span>
								</c:when>
								<c:when test="${roleDispatchChildMenuMark }">
									<input type="checkbox"  name="ck" checked="checked" onclick="selectCks('${childMenuRight.id }');setParents('${childMenuRight.right.id }');" value="${childMenuRight.id }" disabled="disabled">
									<span style="cursor:hand;text-decoration: underline;" onclick="clkDiv('${childMenuRight.id }')">${childMenuRight.name }</span>
								</c:when>
								<c:otherwise>
									<input type="checkbox"  name="ck" onclick="selectCks('${childMenuRight.id }');setParents('${childMenuRight.right.id }');" value="${childMenuRight.id }">
									<span onclick="clkDiv('${childMenuRight.id }')">${childMenuRight.name }</span>
								</c:otherwise>
							</c:choose>
							<input type=hidden name="pid" value="${childMenuRight.right.id }">
							<input type="hidden" name="div_st" value="1"><br></div>
							
							<!-- 按钮级权限 -->
							<c:forEach items="${childMenuRight.rightSet }" var="buttonRight">
								<c:set var="userDispatchButtonMark" value="false"/>
								<c:forEach items="${user.rightSet }" var="userRight">
									<c:if test="${userRight.id == buttonRight.id }">
										<c:set var="userDispatchButtonMark" value="true"/>
									</c:if>
								</c:forEach>
								<c:set var="roleDispatchButtonMark" value="false"/>
								<c:forEach items="${user.roleSet }" var="role">
									<c:forEach items="${role.rightSet }" var="roleRight">
										<c:if test="${roleRight.id == buttonRight.id }">
											<c:set var="roleDispatchButtonMark" value="true"/>
										</c:if>
									</c:forEach>
								</c:forEach>
								<div id=div_ck class=opn>&nbsp;&nbsp;
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<c:choose>
									<c:when test="${userDispatchButtonMark&&!roleDispatchButtonMark }">
										<input type="checkbox"  name="ck" checked="checked" onclick="selectCks('${buttonRight.id }');setParents('${buttonRight.right.id }');" value="${buttonRight.id }">
										<span style="cursor:hand;text-decoration: underline;" onclick="clkDiv('${buttonRight.id }')">${buttonRight.name }</span>
									</c:when>
									<c:when test="${roleDispatchButtonMark }">
										<input type="checkbox"  name="ck" checked="checked" onclick="selectCks('${buttonRight.id }');setParents('${buttonRight.right.id }');" value="${buttonRight.id }" disabled="disabled">
										<span style="cursor:hand;text-decoration: underline;" onclick="clkDiv('${buttonRight.id }')">${buttonRight.name }</span>
									</c:when>
									<c:otherwise>
										<input type="checkbox"  name="ck" onclick="selectCks('${buttonRight.id }');setParents('${buttonRight.right.id }');" value="${buttonRight.id }">
										<span onclick="clkDiv('${buttonRight.id }')">${buttonRight.name }</span>
									</c:otherwise>
								</c:choose>
								<input type=hidden name="pid" value="${buttonRight.right.id }">
								<input type="hidden" name="div_st" value="1"><br></div>
							</c:forEach>
						</c:forEach>
					</c:forEach>
				</c:forEach>
			</td>
		</tr>
	</table>
	</td>
	</tr>
	</table>
	</div>
	<div class="tab_pad">
	<table border="0" cellspacing="0" cellpadding="0" width="90%" align="center">
	  <tr height="35">
		<td width="40%"><div align="center">
		<button  class="btn_sec" onClick="frmChk()" id="">保存</button>
			&nbsp;&nbsp; 
		<button  class="btn_sec" onClick="window.close()">关闭</button></td>
	  </tr>
	</table> 
	</div>
</form>
</body>
<SCRIPT LANGUAGE="JavaScript">
<!--
var cks = document.all("ck");
var pids = document.all("pid");
var div_cks = document.all("div_ck");
var div_sts = document.all("div_st");

function selectCks(id)
{
	if(isLastNode(id))
	{
		return;
	}
	for(var i =0;i < cks.length;i++)
	{
		if(pids[i].value == id)
		{
			cks[i].checked = event.srcElement.checked;
			selectCks(cks[i].value);
		}
	}
}

function clkDiv(id)
{
	for(var i =0;i < cks.length;i++)
	{
		if(cks[i].value == id)
		{
			if(div_sts[i].value == 0)
			{
				div_sts[i].value = 1;
				
				if(isRootNode(id))
				{
					openDiv1(id);
				}
				else
				{
					openDiv(id);
				}		
				
				break;
			}
			else
			{
				div_sts[i].value = 0;
				clsDiv(id);
				break;
			}			
		}
	}
}

function openDiv(id)
{
	if(isLastNode(id))
	{
		return;
	}
	for(var i =0;i < cks.length;i++)
	{
		if(pids[i].value == id)
		{
			div_cks[i].className = "opn";
			div_sts[i].value = 1;
			openDiv(cks[i].value);
		}
	}
}

function openDiv1(id)
{		
	for(var i =0;i < cks.length;i++)
	{
		if(pids[i].value == id)
		{
			div_cks[i].className = "opn";
			div_sts[i].value = 0;
		}
	}
}

function clsDiv(id)
{
	if(isLastNode(id))
	{
		return;
	}
	for(var i =0;i < cks.length;i++)
	{
		if(pids[i].value == id)
		{
			div_cks[i].className = "cls";
			div_sts[i].value = 0;
			clsDiv(cks[i].value);
		}
	}
}

function setParents(parentid)
{
	
	
	for(var i =0;i < cks.length;i++)
	{
		if(cks[i].value == parentid)
		{
			if(event.srcElement.checked) cks[i].checked = event.srcElement.checked;
			setParents(pids[i].value);
		}
	}
	if(isRootNode(parentid))
	{
		return;
	}
}

function isRootNode(id)
{
	for(var i =0;i < cks.length;i++)
	{
		if(cks[i].value == id && pids[i].value == -1)
		{			
			return true;
		}
	}
	return false;
}

function isLastNode(id)
{
	for(var i =0;i < cks.length;i++)
	{
		if(pids[i].value == id)
		{
			return false;
		}
	}
	return true;
}


function frmChk()
{
	var sign=false;
	if(confirm("确认保存?"))
	{
			sign=true;
	}
	if(sign)
	{
		frm.action="<%=basePath %>/user/updateUserRights.action"
		frm.submit();
	}
}

//-->
</SCRIPT>