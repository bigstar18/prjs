<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../../globalDef.jsp"%> 
<html xmlns:MEBS>
<%java.sql.Date sqlDate=new java.sql.Date(System.currentTimeMillis());%>

<!--操作-->

<!--变量定义-->
<c:set var="sqlFilter" value=""/>
<!--分页参数定义-->
<c:choose> 
 <c:when test="${empty param.pageIndex}"> 
   <c:set var="pageIndex" value="1"/> 
 </c:when> 
 <c:otherwise> 
   <c:set var="pageIndex" value="${param.pageIndex}"/> 
 </c:otherwise> 
</c:choose>
<c:choose>
  <c:when test="${empty param.pageSize}">
  	 <c:set var="pageSize" value="${PAGESIZE}"/>
  </c:when>
  <c:otherwise>
  	 <c:set var="pageSize" value="${param.pageSize}"/>
  </c:otherwise>
</c:choose>
<head>
	<title>系统属性控制</title>
</head>
<body>
<form name=frm action="" method="post">
	<!--默认选中板块-->
	<c:set var="defaultSec" value=""/>
	<c:choose>
	<c:when test="${empty param.partitionID}">
    <c:set var="defaultSec" value=""/>
    <db:select var="row" table="v_syspartition" columns="partitionid" orderBy="partitionid desc">
      <c:set var="defaultSec" value="${row.partitionid}"/>
    </db:select>
    <jsp:include page="../../public/menu1.jsp">
	  	<jsp:param name="partitionID" value="${defaultSec}"/>
	  	<jsp:param name="idx" value="${defaultSec}"/>
    </jsp:include>
	  </c:when>
	  <c:otherwise>
	  	<jsp:include page="../../public/menu1.jsp"/>
	  </c:otherwise>
	</c:choose>
	<br><br>
	
	<%
	int pid = -1;
	TradeStatusValue tsv = null;
	try
	{
		String pidStr = "";
		if(request.getParameter("partitionID") == null)
		{
			pidStr = pageContext.getAttribute("defaultSec")+"";
		}
		else
		{
			pidStr = request.getParameter("partitionID");
		}
		pid = Integer.parseInt(pidStr);
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	KernelEngineRMI dao = (KernelEngineRMI) Naming.lookup("rmi://" + host +":"+port+ "/"+REMOTECLASS+""+pid);
	tsv=dao.getTradeStatus();
	//String loginID=session.getAttribute("LOGINID").toString();//操作用户
	try
	{
		//out.println(pid);		
		tsv = dao.getTradeStatus();
		if(request.getParameter("start") != null)//启动系统
		{				
			int r = dao.startTrade();
			if(r == 2)
			{
				//写日志
 	            String remark="启动系统";
 	            OptLog log=new OptLog(JNDI);
				%>
				<SCRIPT LANGUAGE="JavaScript">
				<!--
				alert("系统启动成功！");
				window.location = "manaTradeList.jsp?idx=<%=pid%>&partitionID=<%=pid%>";
				//-->
				</SCRIPT>
				<%
			}
			else
			{
				%>
				<SCRIPT LANGUAGE="JavaScript">
				<!--
				alert("系统异常，系统启动失败！");
				window.location = "manaTradeList.jsp?idx=<%=pid%>&partitionID=<%=pid%>";
				//-->
				</SCRIPT>
				<%
			}
		}
		else if(request.getParameter("manualstart") != null)//手动开市
		{
			try
			{
				dao.manualStartTrade();
				//写日志
 	            String remark="手动开市";
 	            OptLog log=new OptLog(JNDI);
                //log.log(MOVESYS,loginID,remark);
				%>
				<SCRIPT LANGUAGE="JavaScript">
				<!--
				alert("手动开市成功！");
				window.location = "manaTradeList.jsp?idx=<%=pid%>&partitionID=<%=pid%>";
				//-->
				</SCRIPT>
				<%
			}
			catch(Exception e)
			{
				e.printStackTrace();
				%>
				<SCRIPT LANGUAGE="JavaScript">
				<!--
				alert("系统异常，手动开市失败！");
				window.location = "manaTradeList.jsp?idx=<%=pid%>&partitionID=<%=pid%>";
				//-->
				</SCRIPT>
				<%
			}		
		}
		else if(request.getParameter("pause") != null)//暂停交易
		{
			try
			{
				dao.pauseTrade();
				//写日志
 	            String remark="暂停交易";
 	            OptLog log=new OptLog(JNDI);
                //log.log(PAUSETRADE,loginID,remark);
				%>
				<SCRIPT LANGUAGE="JavaScript">
				<!--
				alert("暂停交易成功！");
				window.location = "manaTradeList.jsp?idx=<%=pid%>&partitionID=<%=pid%>";
				//-->
				</SCRIPT>
				<%
			}
			catch(Exception e)
			{
				e.printStackTrace();
				%>
				<SCRIPT LANGUAGE="JavaScript">
				<!--
				alert("系统异常，暂停交易失败！");
				window.location = "manaTradeList.jsp?idx=<%=pid%>&partitionID=<%=pid%>";
				//-->
				</SCRIPT>
				<%
			}
		}
		else if(request.getParameter("resume") != null)//恢复交易
		{
			try
			{
				dao.continueTrade();
				//写日志
 	            String remark="恢复交易";
 	            OptLog log=new OptLog(JNDI);
				%>
				<SCRIPT LANGUAGE="JavaScript">
				<!--
				alert("恢复交易成功！");
				window.location = "manaTradeList.jsp?idx=<%=pid%>&partitionID=<%=pid%>";
				//-->
				</SCRIPT>
				<%
			}
			catch(Exception e)
			{
				e.printStackTrace();
				%>
				<SCRIPT LANGUAGE="JavaScript">
				<!--
				alert("系统异常，恢复交易失败！");
				//-->
				</SCRIPT>
				<%
			}
		}
		else if(request.getParameter("close") != null)//闭市
		{
			try
			{
				dao.closeTrade();
				//写日志
 	            String remark="闭市";
 	            OptLog log=new OptLog(JNDI);
				%>
				<SCRIPT LANGUAGE="JavaScript">
				<!--
				alert("闭市成功！");
				window.location = "manaTradeList.jsp?idx=<%=pid%>&partitionID=<%=pid%>";
				//-->
				</SCRIPT>
				<%
			}
			catch(Exception e)
			{
				e.printStackTrace();
				%>
				<SCRIPT LANGUAGE="JavaScript">
				<!--
				alert("系统异常，闭市失败！");
				//-->
				</SCRIPT>
				<%
			}
		}
		else if(request.getParameter("forcestart") != null)//强制开市
		{
			try
			{
				dao.forceStartTrade();
				//写日志
 	            String remark="强制开市";
 	            OptLog log=new OptLog(JNDI);
				%>
				<SCRIPT LANGUAGE="JavaScript">
				<!--
				alert("强制开市成功！");
				window.location = "manaTradeList.jsp?idx=<%=pid%>&partitionID=<%=pid%>";
				//-->
				</SCRIPT>
				<%
			}
			catch(Exception e)
			{
				e.printStackTrace();
				%>
				<SCRIPT LANGUAGE="JavaScript">
				<!--
				alert("系统异常，强制开市失败！");
				//-->
				</SCRIPT>
				<%
			}
		}
		else if(request.getParameter("opt") != null)//闭市处理
		{
			try
			{
				dao.optTrade();
				//写日志
 	            String remark="闭市处理";
 	            OptLog log=new OptLog(JNDI);
                //log.log(BALANCEACC,loginID,remark);
				%>
				<SCRIPT LANGUAGE="JavaScript">
				<!--
				alert("闭市处理成功！");
				window.location = "manaTradeList.jsp?idx=<%=pid%>&partitionID=<%=pid%>";
				//-->
				</SCRIPT>
				<%
			}
			catch(Exception e)
			{
				e.printStackTrace();
				%>
				<SCRIPT LANGUAGE="JavaScript">
				<!--
				alert("系统异常，闭市处理失败！");
				//-->
				</SCRIPT>
				<%
			}
		}
	}
	catch(Exception er)
	{
		er.printStackTrace();
		out.println("系统异常！");
		return;
	}	
	%>
	<table border="0" width="80%" align="center">
	<tr><td>
	<fieldset width="100%">
		<legend>交易管理&nbsp;&nbsp;
		<%
		if(tsv.getCurStatus() == 2)
		{
			%>
			当前节：<%=tsv.getLastPartID()%>
			<%
		}
		%>
		</legend>
		<BR>
		<span >
		<%
		String sysStatus = "";
		switch(tsv.getCurStatus())
		{
			case 2:
				sysStatus = "交易中";break;
			case 3:
				sysStatus = "节间休息";break;
			case 4:
				sysStatus = "暂停交易";break;
			case 5:
				sysStatus = "闭市";break;
			case 9:
				sysStatus = "等待交易";break;
			case 1:
				sysStatus = "系统启动";break;
			default:
				sysStatus = "";break;
		}
		
		//out.println(tsv.curStatus);
		%>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<%=sysStatus%><%=tsv.getIsMaxTradeOrder()?"(商品结尾)":""%>		
		</span>
	</fieldset>
	</td></tr>
	<tr><td align="center">
	<input name="start" type="submit" <%=tsv.getCurStatus()==5?"":"disabled"%> onclick="return subChk('确认启动系统？')" class="btn" value="启动系统">&nbsp;&nbsp;
	<input name="manualstart" type="submit" <%=tsv.getCurStatus()==9?"":"disabled"%> onclick="return subChk('确认开始交易？')" class="btn" value="开始交易">&nbsp;&nbsp;
	<input name="forcestart" type="submit" <%=tsv.getCurStatus()== 3 && !tsv.getIsMaxTradeOrder()?"":"disabled"%> onclick="return subChk('确认强制开市？')" class="btn" value="强制开市">&nbsp;&nbsp;
	<input name="pause" type="submit" <%=tsv.getCurStatus()==2?"":"disabled"%> onclick="return subChk('确认暂停交易？')" class="btn" value="暂停交易">&nbsp;&nbsp;
	<input name="resume" type="submit" onclick="return subChk('确认恢复交易？')" <%=tsv.getCurStatus()==4?"":"disabled"%> class="btn" value="恢复交易">&nbsp;&nbsp;
	<input name="close" type="submit" onclick="return subChk('确认闭市？')" <%=tsv.getCurStatus()==4 || tsv.getCurStatus()==3 || tsv.getCurStatus()==1?"":"disabled"%> class="btn" value="闭市">
	<input name="opt" type="submit" onclick="return subChk('确认闭市处理？')" <%=tsv.getCurStatus()==5?"":"disabled"%> class="btn" value="闭市处理">
    </td></tr>
	</table>
</form>
</body>
</html>
<SCRIPT LANGUAGE="JavaScript">
<!--
function subChk(msg)
{
	if(confirm(msg))
	{
		return true;
	}
	else
	{
		return false;
	}
}

function winOpen(url)
{
	window.showModalDialog(url,"", "dialogWidth=420px; dialogHeight=280px; status=yes;scroll=yes;help=no;"); 
}
function winOpen1(url)
{
	window.showModalDialog(url,"", "dialogWidth=420px; dialogHeight=400px; status=yes;scroll=yes;help=no;"); 
}
//-->
//修改板块信息
function editUserInfo(id){
	window.open("sysPartitionMod.jsp?flag=query&id="+id,"_blank","width=500,height=350,scrollbars=yes;");
  //var a=openDialog("managerMod.jsp?flag=query&userId="+userCode,"_blank","500","600");
  //window.location="managerList.jsp";
}

//跳转页面
         function pageTo(){
         	   if(event.keyCode==13){
         	   agr=document.frm.pageJumpIdx.value;
             document.frm.pageIndex.value=agr;
             alert(document.frm.pageIndex.value);
             document.frm.submit();	
            }
        }
 
//添加商品

function addRec(frm_delete,tableList,checkName)
{
	if(isSelNothing(tableList,checkName) == -1)
	{
		alert ( "没有可以操作的数据！" );
		return false;
	}
	if(isSelNothing(tableList,checkName))
	{
		alert ( "请选择需要操作的数据！" );
		return false;
	}
	if(confirm("是否以流拍加入到当前交易商品列表？"))
	{ 
		if(confirm("确实要操作这些数据?")){
		  frm.opt.value="添加到当前交易商品";
		  frm.lpFlag.value="1";
	    frm.submit();
	  }else{
	    return false;	
	  }
	}
	else
	{ if(confirm("确实要操作这些数据?")){
		  frm.opt.value="添加到当前交易商品";
		  frm.lpFlag.value="0";
		  frm.submit();
	  }
	  else{
	    return false;	
	  }
	}
}

//点击菜单中的板块栏做查询时
function clickMenu(v){
	frm.partitionID.value=v;
	frm.idx.value=v;
	frm.action="manaTradeList.jsp";
  frm.submit();	
}

function modifyControl(partitionId){
  var a=window.open("sysControlMod.jsp?partitionID="+partitionId,"_blank","width=410,height=350,scrollbars=yes");
  //window.location.reload();
}

</SCRIPT>