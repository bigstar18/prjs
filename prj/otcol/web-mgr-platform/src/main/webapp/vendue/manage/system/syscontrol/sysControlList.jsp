<%@ page contentType="text/html;charset=GBK" %>
<html xmlns:MEBS>
<%java.sql.Date sqlDate=new java.sql.Date(System.currentTimeMillis());%>
<%@ include file="../../globalDef.jsp"%> 

<!--����-->
<c:if test='${not empty param.opt}'>
<c:if test="${param.opt=='refreshEngine'}">
<%
    DBBean bean=null;
	ResultSet rs=null;
	StringBuffer sql=null;
	long durativeTime=-1l;
	long spacetime=-1l;
    int countdownStart=-1;
		int countdownTime=-1;
    try{
		bean=new DBBean(JNDI);
	  String partitionID=request.getParameter("partitionID");
    sql=new StringBuffer();
        
		KernelEngineRMI dao = (KernelEngineRMI) Naming.lookup("rmi://" + host+":"+port+ "/"+REMOTECLASS+""+partitionID);
		sql.append("select * from v_sysproperty where tradepartition="+partitionID+"");
		rs=bean.executeQuery(sql.toString());
        while(rs.next()){
		    durativeTime=rs.getLong("durativetime");
            spacetime=rs.getLong("spacetime");
            countdownStart=rs.getInt("countdownStart");
            countdownTime=rs.getInt("countdownTime");
            dao.updateSysProperty(durativeTime,spacetime,countdownStart,countdownTime);
		}
	alert("ˢ���ڴ�ɹ�!",out);
	}catch(Exception e){
	    e.printStackTrace();
		alert("��������,�������Ա��ϵ!",out);
	}
	finally{
	    try{
		    if(rs!=null) rs.close();
			if(bean!=null) bean.close();
		}catch(Exception ee){}
	}
%>
</c:if>
</c:if>

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
	<title>ϵͳ���Կ������</title>
</head>
<body>
<form name=frm action="" method="post">
    <!--Ĭ��ѡ�а��-->
	<c:set var="defaultSec" value=""/>
	<c:choose>
	<c:when test="${empty param.partitionID}">
    <c:set var="defaultSec" value=""/>
    <db:select var="row" table="v_syspartition" columns="partitionid" where="1=1 " orderBy="partitionid desc">
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
<!--��ѯ����-->
<c:choose> 
 <c:when test="${empty param.partitionID}"> 
   <c:set var="partitionID" value="${defaultSec}"/>
   <c:set var="sqlFilter" value="${sqlFilter} and tradepartition like '${partitionID}' "/>
 </c:when> 
 <c:otherwise> 
   <c:set var="partitionID" value="${param.partitionID}"/> 
   <c:set var="sqlFilter" value="${sqlFilter} and tradepartition like '${partitionID}' "/>
 </c:otherwise> 
</c:choose>
<c:choose> 
 <c:when test="${empty param.description}"> 
   <c:set var="description" value=""/>
 </c:when> 
 <c:otherwise> 
   <c:set var="description" value="${param.description}"/> 
   <c:set var="sqlFilter" value="${sqlFilter} and description like '%${description}%'"/>
 </c:otherwise> 
</c:choose>
	  <br>
		<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%" height="300">
	  		<tHead>
	  			<tr height="25"  align=center>
	  				<td class="panel_tHead_LB">&nbsp;</td>
					<td class="panel_tHead_MB" align=left><input type="checkbox" id="checkAll" onclick="selectAll(tb,'ck')"></td>
			    <td class="panel_tHead_MB">���׽�Ĭ�ϳ���ʱ��</td>
			    <td class="panel_tHead_MB">�ڼ���Ϣʱ��</td>
			    <td class="panel_tHead_MB">����ʱ��ʼʱ�� </td>
			    <td class="panel_tHead_MB">����ʱ˳��ʱ��</td>
			    <td class="panel_tHead_MB">�޸�</td>
					<td class="panel_tHead_RB">&nbsp;</td>
				</tr>
			</tHead>
			<tBody>
			    <db:selectPG var="row" table="v_sysproperty" columns="<%=COLS_SYSPROPERTY%>" pageIndex="${pageIndex}" pageSize="${pageSize}" where="1=1${sqlFilter}">
				  <tr onclick="selectTr();" align=center height="25">
		  			<td class="panel_tBody_LB">&nbsp;</td>
		  			<td class="underLine"><input name="ck" type="checkbox" value='${row.tradepartition}'></td>
		  			<td class="underLine">${row.durativetime}</td>
		  			<td class="underLine">${row.spacetime}</td>
		  			<td class="underLine">${row.countdownstart}</td>
					  <td class="underLine">${row.countdowntime}</td>
					  <td class="underLine"><a href="javascript:modifyControl('${row.tradepartition}')" class="normal">�޸�</a></td>
		  			<td class="panel_tBody_RB">&nbsp;</td>
		  		</tr>
				</db:selectPG>
		  	</tBody>
			<!--������ҳ��-->
			<db:select var="c" table="v_sysproperty" columns="count(*) as n" where="1=1${sqlFilter}">
			 <c:set var="totalCnt" value="${c.n}"/>
			</db:select>
			<c:choose> 
			 <c:when test="${totalCnt%pageSize==0}"> 
			   <c:set var="pageCnt" value="${totalCnt/pageSize}"/> 
			 </c:when> 
			 <c:otherwise>

			   <c:choose> 
				 <c:when test="${(totalCnt%pageSize)*10>=5*pageSize}"> 
				   <c:set var="pageCnt" value="${totalCnt/pageSize}"/> 
				 </c:when> 
				 <c:otherwise>
				   <c:set var="pageCnt" value="${totalCnt/pageSize+1}"/>
				 </c:otherwise> 
				</c:choose>
			 </c:otherwise> 
			</c:choose>
			<jsp:include page="../../public/pageTurn1.jsp">
				<jsp:param name="colspan" value="6"/>
				<jsp:param name="pageIndex" value="${pageIndex}"/>
				<jsp:param name="totalCnt" value="${totalCnt}"/>
				<jsp:param name="pageCnt" value="${pageCnt}"/>
				<jsp:param name="pageSize" value="${pageSize}"/>
			</jsp:include>			
		</table>
     <table border="0" cellspacing="0" cellpadding="0" width="100%">
          <tr height="35">
          <td width="40%"><div align="center">
          <input type="button" onclick="refreshEngine();" class="btn" value="ˢ���ڴ�">
          </div>
          <input type="hidden" name="opt">
          <input type="hidden" name="lpFlag"> 
          </td>
          </tr>
     </table>
</form>
</body>
</html>
<%@ include file="../../public/pageTurn2.jsp"%>
<SCRIPT LANGUAGE="JavaScript">

//ˢ���ڴ�

function refreshEngine(){
  frm.opt.value="refreshEngine";
  frm.submit();
}

<!--
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
  result=PopWindow("sysPartitionMod.jsp?flag=query&id="+id,500,350);
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
	frm.action="sysControlList.jsp";
  frm.submit();	
}

function modifyControl(partitionId){
  var result=window.showModalDialog("sysControlMod.jsp?partitionID="+partitionId,"", "dialogWidth=420px; dialogHeight=350px; status=yes;scroll=yes;help=no;");
  if(result){
		frm.action="sysControlList.jsp";
  	frm.submit();	
	}  	
}
</SCRIPT>