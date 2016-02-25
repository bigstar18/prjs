<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../../globalDef.jsp"%> 
<html xmlns:MEBS>
<%java.sql.Date sqlDate=new java.sql.Date(System.currentTimeMillis());%>

<!--����-->

<!--��������-->
<c:set var="sqlFilter" value=""/>
<!--��ҳ��������-->
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
	<title>ϵͳ���Կ���</title>
</head>
<body>
<form name=frm action="" method="post">
	<!--Ĭ��ѡ�а��-->
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
	//String loginID=session.getAttribute("LOGINID").toString();//�����û�
	try
	{
		//out.println(pid);		
		tsv = dao.getTradeStatus();
		if(request.getParameter("start") != null)//����ϵͳ
		{				
			int r = dao.startTrade();
			if(r == 2)
			{
				//д��־
 	            String remark="����ϵͳ";
 	            OptLog log=new OptLog(JNDI);
				%>
				<SCRIPT LANGUAGE="JavaScript">
				<!--
				alert("ϵͳ�����ɹ���");
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
				alert("ϵͳ�쳣��ϵͳ����ʧ�ܣ�");
				window.location = "manaTradeList.jsp?idx=<%=pid%>&partitionID=<%=pid%>";
				//-->
				</SCRIPT>
				<%
			}
		}
		else if(request.getParameter("manualstart") != null)//�ֶ�����
		{
			try
			{
				dao.manualStartTrade();
				//д��־
 	            String remark="�ֶ�����";
 	            OptLog log=new OptLog(JNDI);
                //log.log(MOVESYS,loginID,remark);
				%>
				<SCRIPT LANGUAGE="JavaScript">
				<!--
				alert("�ֶ����гɹ���");
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
				alert("ϵͳ�쳣���ֶ�����ʧ�ܣ�");
				window.location = "manaTradeList.jsp?idx=<%=pid%>&partitionID=<%=pid%>";
				//-->
				</SCRIPT>
				<%
			}		
		}
		else if(request.getParameter("pause") != null)//��ͣ����
		{
			try
			{
				dao.pauseTrade();
				//д��־
 	            String remark="��ͣ����";
 	            OptLog log=new OptLog(JNDI);
                //log.log(PAUSETRADE,loginID,remark);
				%>
				<SCRIPT LANGUAGE="JavaScript">
				<!--
				alert("��ͣ���׳ɹ���");
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
				alert("ϵͳ�쳣����ͣ����ʧ�ܣ�");
				window.location = "manaTradeList.jsp?idx=<%=pid%>&partitionID=<%=pid%>";
				//-->
				</SCRIPT>
				<%
			}
		}
		else if(request.getParameter("resume") != null)//�ָ�����
		{
			try
			{
				dao.continueTrade();
				//д��־
 	            String remark="�ָ�����";
 	            OptLog log=new OptLog(JNDI);
				%>
				<SCRIPT LANGUAGE="JavaScript">
				<!--
				alert("�ָ����׳ɹ���");
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
				alert("ϵͳ�쳣���ָ�����ʧ�ܣ�");
				//-->
				</SCRIPT>
				<%
			}
		}
		else if(request.getParameter("close") != null)//����
		{
			try
			{
				dao.closeTrade();
				//д��־
 	            String remark="����";
 	            OptLog log=new OptLog(JNDI);
				%>
				<SCRIPT LANGUAGE="JavaScript">
				<!--
				alert("���гɹ���");
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
				alert("ϵͳ�쳣������ʧ�ܣ�");
				//-->
				</SCRIPT>
				<%
			}
		}
		else if(request.getParameter("forcestart") != null)//ǿ�ƿ���
		{
			try
			{
				dao.forceStartTrade();
				//д��־
 	            String remark="ǿ�ƿ���";
 	            OptLog log=new OptLog(JNDI);
				%>
				<SCRIPT LANGUAGE="JavaScript">
				<!--
				alert("ǿ�ƿ��гɹ���");
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
				alert("ϵͳ�쳣��ǿ�ƿ���ʧ�ܣ�");
				//-->
				</SCRIPT>
				<%
			}
		}
		else if(request.getParameter("opt") != null)//���д���
		{
			try
			{
				dao.optTrade();
				//д��־
 	            String remark="���д���";
 	            OptLog log=new OptLog(JNDI);
                //log.log(BALANCEACC,loginID,remark);
				%>
				<SCRIPT LANGUAGE="JavaScript">
				<!--
				alert("���д���ɹ���");
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
				alert("ϵͳ�쳣�����д���ʧ�ܣ�");
				//-->
				</SCRIPT>
				<%
			}
		}
	}
	catch(Exception er)
	{
		er.printStackTrace();
		out.println("ϵͳ�쳣��");
		return;
	}	
	%>
	<table border="0" width="80%" align="center">
	<tr><td>
	<fieldset width="100%">
		<legend>���׹���&nbsp;&nbsp;
		<%
		if(tsv.getCurStatus() == 2)
		{
			%>
			��ǰ�ڣ�<%=tsv.getLastPartID()%>
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
				sysStatus = "������";break;
			case 3:
				sysStatus = "�ڼ���Ϣ";break;
			case 4:
				sysStatus = "��ͣ����";break;
			case 5:
				sysStatus = "����";break;
			case 9:
				sysStatus = "�ȴ�����";break;
			case 1:
				sysStatus = "ϵͳ����";break;
			default:
				sysStatus = "";break;
		}
		
		//out.println(tsv.curStatus);
		%>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<%=sysStatus%><%=tsv.getIsMaxTradeOrder()?"(��Ʒ��β)":""%>		
		</span>
	</fieldset>
	</td></tr>
	<tr><td align="center">
	<input name="start" type="submit" <%=tsv.getCurStatus()==5?"":"disabled"%> onclick="return subChk('ȷ������ϵͳ��')" class="btn" value="����ϵͳ">&nbsp;&nbsp;
	<input name="manualstart" type="submit" <%=tsv.getCurStatus()==9?"":"disabled"%> onclick="return subChk('ȷ�Ͽ�ʼ���ף�')" class="btn" value="��ʼ����">&nbsp;&nbsp;
	<input name="forcestart" type="submit" <%=tsv.getCurStatus()== 3 && !tsv.getIsMaxTradeOrder()?"":"disabled"%> onclick="return subChk('ȷ��ǿ�ƿ��У�')" class="btn" value="ǿ�ƿ���">&nbsp;&nbsp;
	<input name="pause" type="submit" <%=tsv.getCurStatus()==2?"":"disabled"%> onclick="return subChk('ȷ����ͣ���ף�')" class="btn" value="��ͣ����">&nbsp;&nbsp;
	<input name="resume" type="submit" onclick="return subChk('ȷ�ϻָ����ף�')" <%=tsv.getCurStatus()==4?"":"disabled"%> class="btn" value="�ָ�����">&nbsp;&nbsp;
	<input name="close" type="submit" onclick="return subChk('ȷ�ϱ��У�')" <%=tsv.getCurStatus()==4 || tsv.getCurStatus()==3 || tsv.getCurStatus()==1?"":"disabled"%> class="btn" value="����">
	<input name="opt" type="submit" onclick="return subChk('ȷ�ϱ��д���')" <%=tsv.getCurStatus()==5?"":"disabled"%> class="btn" value="���д���">
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
//�޸İ����Ϣ
function editUserInfo(id){
	window.open("sysPartitionMod.jsp?flag=query&id="+id,"_blank","width=500,height=350,scrollbars=yes;");
  //var a=openDialog("managerMod.jsp?flag=query&userId="+userCode,"_blank","500","600");
  //window.location="managerList.jsp";
}

//��תҳ��
         function pageTo(){
         	   if(event.keyCode==13){
         	   agr=document.frm.pageJumpIdx.value;
             document.frm.pageIndex.value=agr;
             alert(document.frm.pageIndex.value);
             document.frm.submit();	
            }
        }
 
//�����Ʒ

function addRec(frm_delete,tableList,checkName)
{
	if(isSelNothing(tableList,checkName) == -1)
	{
		alert ( "û�п��Բ��������ݣ�" );
		return false;
	}
	if(isSelNothing(tableList,checkName))
	{
		alert ( "��ѡ����Ҫ���������ݣ�" );
		return false;
	}
	if(confirm("�Ƿ������ļ��뵽��ǰ������Ʒ�б�"))
	{ 
		if(confirm("ȷʵҪ������Щ����?")){
		  frm.opt.value="��ӵ���ǰ������Ʒ";
		  frm.lpFlag.value="1";
	    frm.submit();
	  }else{
	    return false;	
	  }
	}
	else
	{ if(confirm("ȷʵҪ������Щ����?")){
		  frm.opt.value="��ӵ���ǰ������Ʒ";
		  frm.lpFlag.value="0";
		  frm.submit();
	  }
	  else{
	    return false;	
	  }
	}
}

//����˵��еİ��������ѯʱ
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