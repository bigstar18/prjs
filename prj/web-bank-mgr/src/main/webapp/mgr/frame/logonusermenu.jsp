<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<head>
 <title>�����û���ݲ˵�</title>
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
<div class="st_title">&nbsp;&nbsp;<img src="<%=skinPath%>/image/detail_title.gif" align="absmiddle" />&nbsp;���ÿ�ݲ˵�</div>
		<div class="mymenu_div"><div class="mymenu_div1">
		<div class="mymenu_div2">
			<table border="0" width="100%" align="center">
				<tr>
					<td>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" class="st_bor">			  
				<tr height="35">
				<td align="left" style="font-size: 12px">
				<!-- ģ��Ȩ�� -->
				<c:forEach items="${HaveRightMenu.childMenuSet }" var="moduleRight">
					<div id=div_ck class=opn>&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="hidden" disabled="disabled"  name="ck" onclick="selectCks('${moduleRight.id }');setParents('${moduleRight.parentMenu.id }');" value="${moduleRight.id }">
			        <c:set var="parentMenuMark" value="false"/>
			        <c:forEach items="${myMenuList }" var="list">
			        	<c:if test="${list.right.parentRight.parentRight.id==moduleRight.id}">
			        		<c:set var="parentMenuMark" value="true"/>
			        	</c:if>
			        </c:forEach>
			        <c:choose>
						<c:when test="${parentMenuMark }">
							<span style="font-weight: bold;font-size: 14;cursor:hand;text-decoration: underline;" onclick="clkDiv('${moduleRight.id }')">${moduleRight.name }</span>
						</c:when>
						<c:otherwise>
							<span style="font-weight: bold;font-size: 14;" onclick="clkDiv('${moduleRight.id }')">${moduleRight.name }</span>
						</c:otherwise>
					</c:choose>
					<input type=hidden name="pid" value="${moduleRight.parentMenu.id }">
					<input type="hidden" name="div_st" value="1"><br></div>
				
					<!-- һ���˵�Ȩ�� -->
					<c:forEach items="${moduleRight.childMenuSet }" var="menuRight">
						<div id=div_ck class=opn>&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="hidden" disabled="disabled" name="ck" onclick="selectCks('${menuRight.id }');setParents('${menuRight.parentMenu.id }');" value="${menuRight.id }">
						 <c:set var="parentMenuMark1" value="false"/>
				        <c:forEach items="${myMenuList }" var="list">
				        	<c:if test="${list.right.parentRight.id==menuRight.id}">
				        		<c:set var="parentMenuMark1" value="true"/>
				        	</c:if>
				        </c:forEach>
				        <c:choose>
							<c:when test="${parentMenuMark1 }">
								<span style="font-weight: bold;cursor:hand;text-decoration: underline;" onclick="clkDiv('${menuRight.id }')">${menuRight.name }</span>
							</c:when>
							<c:otherwise>
								<span style="font-weight: bold" onclick="clkDiv('${menuRight.id }')">${menuRight.name }</span>
							</c:otherwise>
						</c:choose>
						<input type=hidden name="pid" value="${menuRight.parentMenu.id }">
						<input type="hidden" name="div_st" value="1"><br></div>
					
						<!-- �����˵�Ȩ�� -->
						<c:forEach items="${menuRight.childMenuSet }" var="childMenuRight">
							<c:set var="roleDispatchChildMenuMark" value="false"/>
							<c:forEach items="${myMenuList }" var="list">
								<c:if test="${ childMenuRight.id==list.right.id }">
									<c:set var="roleDispatchChildMenuMark" value="true"/>
								</c:if>
							</c:forEach>
							<div id=div_ck class=opn>&nbsp;&nbsp;
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<c:choose>
								<c:when test="${roleDispatchChildMenuMark }">
									<input type="checkbox"  name="ck" checked="checked" onclick="selectCks('${childMenuRight.id }');setParents('${childMenuRight.parentMenu.id }');" value="${childMenuRight.id }">
									<span style="cursor:hand;text-decoration: underline;" onclick="clkDiv('${childMenuRight.id }')">${childMenuRight.name }</span>
								</c:when>
								<c:otherwise>
									<input type="checkbox"  name="ck" onclick="selectCks('${childMenuRight.id }');setParents('${childMenuRight.parentMenu.id }');" value="${childMenuRight.id }">
									<span onclick="clkDiv('${childMenuRight.id }')">${childMenuRight.name }</span>
								</c:otherwise>
							</c:choose>
							
							<input type=hidden name="pid" value="${childMenuRight.parentMenu.id }">
							<input type="hidden" name="div_st" value="1"><br></div>
						</c:forEach>
					</c:forEach>
				</c:forEach>
			</td>
		</tr>
	</table>
	</td>
	</tr>
	</table>
	</div></div></div>
	<div class="tab_pad">
	<table border="0" cellspacing="0" cellpadding="0" width="90%" align="center"> 
	  <tr height="35">

		<td align="center" id="tdId">
		<rightButton:rightButton name="����" onclick="frmChk()" className="btn_sec" action="/myMenu/updateMyMenu.action" id="update"></rightButton:rightButton>
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
var div_cks = document.all("div_ck");//��ʾ��Ȩ�޵� ��
var div_sts = document.all("div_st");

/*�����ѡ�򴥷��¼�*/
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
/*�������  ������ر����������ڵĲ� */
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
/*�򿪲� */
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
	var num=0;
	if(cks==null){
		alert("�޲������ݣ�");
		return false;
	}
	for(var i =0;i < cks.length;i++)
	{
		if(cks[i].checked==true&&cks[i].disabled==false){
			num++;
		} 
	}
	if(num>5){
		alert("���ֻ��ѡȡ5����");
		return false;
	}else{
		if(confirm("ȷ�ϱ���?"))
		{
			var a=document.getElementById('update').action;
			frm.action="<%=basePath %>"+a;
			frm.submit();
		}
		
	}
}

//-->
</SCRIPT>
<%@ include file="/mgr/public/jsp/footinc.jsp"%>