<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="/public/session.jsp"%>
<head>
 <title>���ý�ɫȨ��</title>
</head>
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
<body style="overflow-y:hidden">
<form name="frm" method="post" targetType="hidden">	
<div class="st_title"><img src="<%=skinPath%>/cssimg/st_ico1.gif" align="absmiddle" />&nbsp;&nbsp;���ý�ɫȨ��</div>
		<div style="overflow-x:hidden;position:absolute;top:60;z-index:1;overflow:auto;height:480px;">
			<table border="0" width="100%" align="center">
				<tr>
					<td>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" class="st_bor">			  
				<tr height="35">
            		<td align="" width="20%" class="td_size">
            			��ɫ���룺${role.id }<input name="roleId" type="hidden" value="${role.id }">
            		</td>               
				</tr>
				<tr height="35">
				<td align="left" class="td_size">
				
				<!-- Ȩ�޸��ڵ� -->
				<c:if test="${role.type!='TRADE_ADMIN'}">
					<div id=div_ck class=opn>&nbsp;&nbsp;
					<c:set var="roleDispatchMark" value="false"/>
					<c:forEach items="${role.rightSet }" var="roleRight">
						<c:if test="${roleRight.id == treeRight.id }">
							<c:set var="roleDispatchMark" value="true"/>
						</c:if>
					</c:forEach>
					<c:choose>
						<c:when test="${roleDispatchMark }">
							<input type="checkbox"  name="ck" checked="checked" onclick="selectCks('${treeRight.id }');setParents('${treeRight.right.id }');" value="${treeRight.id }">
							<span style="cursor:hand;text-decoration: underline;" onclick="clkDiv('${treeRight.id }')">${treeRight.name }</span>
						</c:when>
						<c:otherwise>
							<input type="checkbox"  name="ck" onclick="selectCks('${treeRight.id }');setParents('${treeRight.right.id }');" value="${treeRight.id }">
							<span onclick="clkDiv('${treeRight.id }')">${treeRight.name }</span>
						</c:otherwise>
					</c:choose>
					<input type=hidden name="pid" value="${treeRight.right.id }">
					<input type="hidden" name="div_st" value="1"><br></div>
					
					<!-- ģ��Ȩ�� -->
					<c:forEach items="${treeRight.rightSet }" var="moduleRight">
						<c:if test="${role.type!='TRADE_ADMIN'&&moduleRight.id!=1000000}">
							<c:set var="roleDispatchModuleMark" value="false"/>
							<c:forEach items="${role.rightSet }" var="roleRight">
								<c:if test="${roleRight.id == moduleRight.id }">
									<c:set var="roleDispatchModuleMark" value="true"/>
								</c:if>
							</c:forEach>
							<div id=div_ck class=opn>&nbsp;&nbsp;
							&nbsp;&nbsp;&nbsp;&nbsp;
							<c:choose>
								<c:when test="${roleDispatchModuleMark }">
									<input type="checkbox"  name="ck" checked="checked" onclick="selectCks('${moduleRight.id }');setParents('${moduleRight.right.id }');" value="${moduleRight.id }">
									<span style="cursor:hand;text-decoration: underline;" onclick="clkDiv('${moduleRight.id }')">${moduleRight.name }</span>
								</c:when>
								<c:otherwise>
									<input type="checkbox"  name="ck" onclick="selectCks('${moduleRight.id }');setParents('${moduleRight.right.id }');" value="${moduleRight.id }">
							        <span onclick="clkDiv('${moduleRight.id }')">${moduleRight.name }</span>
								</c:otherwise>
							</c:choose>
							<input type=hidden name="pid" value="${moduleRight.right.id }">
							<input type="hidden" name="div_st" value="1"><br></div>
						
							<!-- һ���˵�Ȩ�� -->
							<c:forEach items="${moduleRight.rightSet }" var="menuRight">
								<c:set var="roleDispatchMenuMark" value="false"/>
								<c:forEach items="${role.rightSet }" var="roleRight">
									<c:if test="${roleRight.id == menuRight.id }">
										<c:set var="roleDispatchMenuMark" value="true"/>
									</c:if>
								</c:forEach>
								<div id=div_ck class=opn>&nbsp;&nbsp;
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<c:choose>
									<c:when test="${roleDispatchMenuMark }">
										<input type="checkbox"  name="ck" checked="checked" onclick="selectCks('${menuRight.id }');setParents('${menuRight.right.id }');" value="${menuRight.id }">
										<span style="cursor:hand;text-decoration: underline;" onclick="clkDiv('${menuRight.id }')">${menuRight.name }</span>
									</c:when>
									<c:otherwise>
										<input type="checkbox"  name="ck" onclick="selectCks('${menuRight.id }');setParents('${menuRight.right.id }');" value="${menuRight.id }">
								<span onclick="clkDiv('${menuRight.id }')">${menuRight.name }</span>
									</c:otherwise>
								</c:choose>
								<input type=hidden name="pid" value="${menuRight.right.id }">
								<input type="hidden" name="div_st" value="1"><br></div>
							
								<!-- �����˵�Ȩ�� -->
								<c:forEach items="${menuRight.rightSet }" var="childMenuRight">
									<c:if test="${childMenuRight.url!='/role/*'}">
									<c:set var="roleDispatchChildMenuMark" value="false"/>
									<c:forEach items="${role.rightSet }" var="roleRight">
										<c:if test="${roleRight.id == childMenuRight.id }">
											<c:set var="roleDispatchChildMenuMark" value="true"/>
										</c:if>
									</c:forEach>
									<div id=div_ck class=opn>&nbsp;&nbsp;
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<c:choose>
										<c:when test="${roleDispatchChildMenuMark }">
											<input type="checkbox"  name="ck" checked="checked" onclick="selectCks('${childMenuRight.id }');setParents('${childMenuRight.right.id }');" value="${childMenuRight.id }">
											<span style="cursor:hand;text-decoration: underline;" onclick="clkDiv('${childMenuRight.id }')">${childMenuRight.name }</span>
										</c:when>
										<c:otherwise>
											<input type="checkbox"  name="ck" onclick="selectCks('${childMenuRight.id }');setParents('${childMenuRight.right.id }');" value="${childMenuRight.id }">
											<span onclick="clkDiv('${childMenuRight.id }')">${childMenuRight.name }</span>
										</c:otherwise>
									</c:choose>
									<input type=hidden name="pid" value="${childMenuRight.right.id }">
									<input type="hidden" name="div_st" value="1"><br></div>
									
									<!-- ��ť��Ȩ�� -->
									<c:forEach items="${childMenuRight.rightSet }" var="buttonRight">
										<c:if test="${buttonRight.url!='/user/add*'&&buttonRight.url!='/user/update*'&&buttonRight.url!='/user/updatePassword*'&&buttonRight.url!='/user/delete*'&&buttonRight.url!='/user/related*'}">
										<c:set var="roleDispatchButtonMark" value="false"/>
										<c:forEach items="${role.rightSet }" var="roleRight">
											<c:if test="${roleRight.id == buttonRight.id }">
												<c:set var="roleDispatchButtonMark" value="true"/>
											</c:if>
										</c:forEach>
										<div id=div_ck class=opn>&nbsp;&nbsp;
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<c:choose>
											<c:when test="${roleDispatchButtonMark }">
												<input type="checkbox"  name="ck" checked="checked" onclick="selectCks('${buttonRight.id }');setParents('${buttonRight.right.id }');" value="${buttonRight.id }">
												<span style="cursor:hand;text-decoration: underline;" onclick="clkDiv('${buttonRight.id }')">${buttonRight.name }</span>
											</c:when>
											<c:otherwise>
												<input type="checkbox"  name="ck" onclick="selectCks('${buttonRight.id }');setParents('${buttonRight.right.id }');" value="${buttonRight.id }">
												<span onclick="clkDiv('${buttonRight.id }')">${buttonRight.name }</span>
											</c:otherwise>
										</c:choose>
										<input type=hidden name="pid" value="${buttonRight.right.id }">
										<input type="hidden" name="div_st" value="1"><br></div>
										</c:if>
									</c:forEach>
									</c:if>
								</c:forEach>
							</c:forEach>
						</c:if>
						<c:if test="${role.type=='TRADE_ADMIN'&&moduleRight.id==1000000}">
							<c:set var="roleDispatchModuleMark" value="false"/>
							<c:forEach items="${role.rightSet }" var="roleRight">
								<c:if test="${roleRight.id == moduleRight.id }">
									<c:set var="roleDispatchModuleMark" value="true"/>
								</c:if>
							</c:forEach>
							<div id=div_ck class=opn>&nbsp;&nbsp;
							&nbsp;&nbsp;&nbsp;&nbsp;
							<c:choose>
								<c:when test="${roleDispatchModuleMark }">
									<input type="checkbox"  name="ck" checked="checked" onclick="selectCks('${moduleRight.id }');setParents('${moduleRight.right.id }');" value="${moduleRight.id }" disabled="disabled">
									<span style="cursor:hand;text-decoration: underline;" onclick="clkDiv('${moduleRight.id }')">${moduleRight.name }</span>
								</c:when>
								<c:otherwise>
									<input type="checkbox"  name="ck" onclick="selectCks('${moduleRight.id }');setParents('${moduleRight.right.id }');" value="${moduleRight.id }" disabled="disabled">
							        <span onclick="clkDiv('${moduleRight.id }')">${moduleRight.name }</span>
								</c:otherwise>
							</c:choose>
							<input type=hidden name="ck" value="${moduleRight.id}">
							<input type=hidden name="pid" value="${moduleRight.right.id }">
							<input type="hidden" name="div_st" value="1"><br></div>
						</c:if>
					</c:forEach>
				</c:if>
				
				
				
				
				<!-- Ȩ�޸��ڵ� -->
				<c:if test="${role.type=='TRADE_ADMIN'}">
					<div id=div_ck class=opn>&nbsp;&nbsp;
					<c:set var="roleDispatchMark" value="false"/>
					<c:forEach items="${role.rightSet }" var="roleRight">
						<c:if test="${roleRight.id == treeRight.id }">
							<c:set var="roleDispatchMark" value="true"/>
						</c:if>
					</c:forEach>
					<c:choose>
						<c:when test="${roleDispatchMark }">
							<input type="checkbox"  name="ck" checked="checked" onclick="selectCks('${treeRight.id }');setParents('${treeRight.right.id }');" value="${treeRight.id }" disabled="disabled">
							<span style="cursor:hand;text-decoration: underline;">${treeRight.name }</span>
							<input type="hidden"  name="ck" value="${treeRight.id }">
						</c:when>
						<c:otherwise>
							<input type="checkbox"  name="ck" onclick="selectCks('${treeRight.id }');setParents('${treeRight.right.id }');" value="${treeRight.id }" disabled="disabled">
							<span>${treeRight.name }</span>
						</c:otherwise>
					</c:choose>
					<input type=hidden name="pid" value="${treeRight.right.id }">
					<input type="hidden" name="div_st" value="1"><br></div>
					
					<!-- ģ��Ȩ�� -->
					<c:forEach items="${treeRight.rightSet }" var="moduleRight">
						<c:if test="${role.type=='TRADE_ADMIN'&&moduleRight.id==1000000}">
							<c:set var="roleDispatchModuleMark" value="false"/>
							<c:forEach items="${role.rightSet }" var="roleRight">
								<c:if test="${roleRight.id == moduleRight.id }">
									<c:set var="roleDispatchModuleMark" value="true"/>
								</c:if>
							</c:forEach>
							<div id=div_ck class=opn>&nbsp;&nbsp;
							&nbsp;&nbsp;&nbsp;&nbsp;
							<c:choose>
								<c:when test="${roleDispatchModuleMark }">
									<input type="checkbox"  name="ck" checked="checked" onclick="selectCks('${moduleRight.id }');setParents('${moduleRight.right.id }');" value="${moduleRight.id }" disabled="disabled">
									<span style="cursor:hand;text-decoration: underline;">${moduleRight.name }</span>
									<input type=hidden name="ck" value="${moduleRight.id}">
								</c:when>
								<c:otherwise>
									<input type="checkbox"  name="ck" onclick="selectCks('${moduleRight.id }');setParents('${moduleRight.right.id }');" value="${moduleRight.id }" disabled="disabled">
							        <span>${moduleRight.name }</span>
								</c:otherwise>
							</c:choose>
							<input type=hidden name="pid" value="${moduleRight.right.id }">
							<input type="hidden" name="div_st" value="1"><br></div>
						</c:if>
					</c:forEach>
				</c:if>
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

		<td align="center" id="tdId">
		<rightButton:rightButton name="����" onclick="frmChk()" className="btn_sec" id="update"></rightButton:rightButton>
		<!-- 
		<button  class="btn_sec" onClick="frmChk()" id="update">����</button>
		 -->
		</td>
		<td align="center">
		<button  class="btn_sec" onClick="window.close()">�ر�</button>
		</td>
	  </tr>
	</table>  
</form>
</body>
<SCRIPT LANGUAGE="JavaScript">

var cks = document.all("ck");//��¼����Ȩ��id
var pids = document.all("pid");//��¼��Ȩ��id
var div_cks = document.all("div_ck");
var div_sts = document.all("div_st");


var reg =/^\w*DEFAULT_+\w*$/; 
var id='${role.type}';
if(id.match(reg)!=null){
	var elements = document.getElementsByName("ck");
	elementsDisableded(elements);
	var tableTd=document.getElementById("tdId");
	tableTd.style.display="none";
	function elementsDisableded(elements){
		var length = elements.length;
		for(var i=0;i<length;i++){
			var element = elements[i];
			element.readonly=true;
			element.disabled='disabled';
		}
	 }
}


var reg1 =/^\w*TRADE_+\w*$/; 
if(id.match(reg1)!=null){
	var elements = document.getElementsByName("ck");
	var tableTd=document.getElementById("tdId");
	tableTd.style.display="none";
}

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
//isLastNode ���ڲ鿴�Ƿ�����ײ�Ȩ�ޣ��Ƿ���true���񷵻�false
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
	if(affirm("ȷ�ϱ���?"))
	{
			sign=true;
	}
	if(sign)
	{
		frm.action="<%=basePath %>/role/updateUserRight.action"
		frm.submit();
	}
}

//-->
</SCRIPT>
<%@ include file="/public/footInc.jsp"%>