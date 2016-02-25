<%@ page contentType="text/html;charset=GBK" %>
<html xmlns:MEBS>
<%java.sql.Date sqlDate=new java.sql.Date(System.currentTimeMillis());%>
<%@ include file="../../globalDef.jsp"%> 

<fmt:requestEncoding value="GBK" />
<!--����-->
<c:if test='${not empty param.opt}'>
 <c:set var="modifyTime" value="<%=sqlDate%>"/>
 <c:set var="ids" value=""/>
  <c:if test="${param.opt=='ɾ��'}">
  	<c:forEach var="row" items="${paramValues.ck}">
	  <c:set var="ids" value="${ids}'${row}',"/>		
 	  </c:forEach>
 	  <c:set var="ids" value="${ids}''"/>
 	  <db:delete table="v_syspartition" where="partitionid in (${ids})"/>
 	    	 <script language="javascript">
 	    	 	  <!--
 	  		 	  alert("ģ��ɾ���ɹ�!");
 	  		 	  //-->
 	  		 </script>
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
<!--��ѯ����-->
<c:choose> 
 <c:when test="${empty param.partitionID}"> 
   <c:set var="partitionID" value=""/> 
 </c:when> 
 <c:otherwise> 
   <c:set var="partitionID" value="${param.partitionID}"/> 
   <c:set var="sqlFilter" value="${sqlFilter} and partitionid like '%${partitionID}%' "/>
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
<head>
	<title>ϵͳ������</title>
</head>
<body>
<form name=frm action="" method="post">
		<fieldset width="100%">
		<legend>ϵͳ������ò�ѯ</legend>
		<span>
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
			  <tr height="35">
            	<td align="right"> ����� ��</td>
                <td align="left">
                	<input name="partitionID" type="text" class="text" style="width: 150px;" value="${param.partitionID}">
                </td>
                <td align="right"> ������� ��</td>
                <td align="left"><input name="description" type="text" class="text" style="width: 150px;" value="${param.description}"></td>
				        <td align="left">
                <input type="button" onclick="initIdx();" class="btn" value="��ѯ">&nbsp;
                </td>
              </tr>
        	</table>
        </span>  
		</fieldset>
		<br>
		<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%" height="300">
	  		<tHead>
	  			<tr height="25"  align=center>
	  				<td class="panel_tHead_LB">&nbsp;</td>
					<td class="panel_tHead_MB" align=left><input type="checkbox" id="checkAll" onclick="selectAll(tb,'ck')"></td>
			    <td class="panel_tHead_MB">����� </td>
			    <td class="panel_tHead_MB">������������javaʵ���������</td>
			    <td class="panel_tHead_MB">��������javaʵ��������� </td>
			    <td class="panel_tHead_MB">ί����Ӧ��javaʵ���������</td>
			    <td class="panel_tHead_MB">������������</td>
			    <td class="panel_tHead_MB">�������</td>
					<td class="panel_tHead_RB">&nbsp;</td>
				</tr>
			</tHead>
			<tBody>
			    <db:selectPG var="row" table="v_syspartition" columns="<%=COLS_SYSPARTITION%>" orderBy="partitionid desc" pageIndex="${pageIndex}" pageSize="${pageSize}" where="1=1${sqlFilter}">
				  <tr onclick="selectTr();" align=center height="25">
		  			<td class="panel_tBody_LB">&nbsp;</td>
		  			<td class="underLine" align=left><input name="ck" type="checkbox" value='${row.partitionid}'></td>
		  			<td class="underLine">
		  			<a href="javascript:editUserInfo(${row.partitionid});" class="normal">${row.partitionid}</a></td>
		  			<td class="underLine">
		  			<c:choose>
		  			<c:when test="${not empty row.engineclass}">
		  			  ${row.engineclass}
		  			</c:when>
		  			<c:otherwise>
		  				&nbsp;
		  		  </c:otherwise>
		  		  </c:choose>
		  			</td>
		  			<td class="underLine">${row.quotationclass}</td>
					  <td class="underLine">${row.submitactionclass}</td>
					  <td class="underLine">
					  <c:if test="${row.validFlag==0}">��</c:if>	
					  <c:if test="${row.validFlag==1}">��</c:if>	
					  </td>
					  <td class="underLine">
					  <c:choose>
		  			<c:when test="${not empty row.description}">
		  			  ${row.description}
		  			</c:when>
		  			<c:otherwise>
		  				&nbsp;
		  		  </c:otherwise>
		  		  </c:choose>
					  </td>
		  			<td class="panel_tBody_RB">&nbsp;</td>
		  		</tr>
				</db:selectPG>
		  	</tBody>
			<!--������ҳ��-->
			<db:select var="c" table="v_syspartition" columns="count(*) as n" where="1=1${sqlFilter}">
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
			<jsp:include page="/vendue/manage/public/pageTurn1.jsp">
				<jsp:param name="colspan" value="7"/>
				<jsp:param name="pageIndex" value="${pageIndex}"/>
				<jsp:param name="totalCnt" value="${totalCnt}"/>
				<jsp:param name="pageCnt" value="${pageCnt}"/>
				<jsp:param name="pageSize" value="${pageSize}"/>
			</jsp:include>			
		</table>
     <table border="0" cellspacing="0" cellpadding="0" width="100%">
          <tr height="35">
          	<td width="40%"><div align="center">
			  <input type="button" onclick="gotoUrl('sysPartitionAdd.jsp')" class="btn" value="���">&nbsp;&nbsp;
  		  <input type="button" onclick="return deleteRec(frm,tb,'ck');" class="btn" value="ɾ��">&nbsp;&nbsp;
            </div>
        <input type="hidden" name="opt">
        <input type="hidden" name="lpFlag"> 
            </td>
          </tr>
     </table>
</form>
</body>
</html>
<%@ include file="/vendue/manage/public/pageTurn2.jsp"%>
<SCRIPT LANGUAGE="JavaScript">
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
</SCRIPT>