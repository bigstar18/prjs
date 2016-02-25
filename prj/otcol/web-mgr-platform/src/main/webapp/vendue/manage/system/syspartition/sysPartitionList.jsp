<%@ page contentType="text/html;charset=GBK" %>
<html xmlns:MEBS>

<%@ include file="../../globalDef.jsp"%> 

<%
    java.sql.Date sqlDate=new java.sql.Date(System.currentTimeMillis());
%>
<!--����-->
<c:if test='${not empty param.opt}'>
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
 <c:when test="${empty param.description}"> 
   <c:set var="description" value=""/>
 </c:when> 
 <c:otherwise> 
   <c:set var="description" value="${param.description}"/> 
   <c:set var="sqlFilter" value="${sqlFilter} and description like '${description}'"/>
 </c:otherwise> 
</c:choose>
<head>
	<title>��Ʒ</title>
</head>
<body>
<form name=frm action="" method="post">
		<fieldset width="100%">
		<legend>ϵͳ������ò�ѯ</legend>
		<span>
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
			  <tr height="35">
                <td align="right" width="15%"> ������ƣ�</td>
                <td align="left" width="50%"><input name="description" type="text" class="text" style="width: 150px;" value="${param.description}"onkeypress="onlyNumberAndCharInput()" maxlength="16"></td>
				<td align="left">
                <input type="button" onclick="initIdx();" class="btn" value="��ѯ">&nbsp;
               	<!-- add by yangpei 2011-11-22 �������ù��� -->
            	<input type="button" onclick="resetForm();" class="btn" value="����">&nbsp;
            	<script>
            		function resetForm(){
            			frm.description.value="";
            		}
            	</script>
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
			    <td class="panel_tHead_MB">�������</td>
				<td class="panel_tHead_MB">����ģʽ</td>
			    <td class="panel_tHead_MB">������������</td>
			    <td class="panel_tHead_MB">�޸�</td>
					<td class="panel_tHead_RB">&nbsp;</td>
				</tr>
			</tHead>
			<tBody>
			    <db:selectPG var="row" table="v_syspartition" columns="<%=COLS_SYSPARTITION%>" orderBy="partitionid desc" pageIndex="${pageIndex}" pageSize="${pageSize}" where="1=1${sqlFilter}">
				  <tr onclick="selectTr();" align=center height="25">
		  		  <td class="panel_tBody_LB">&nbsp;</td>
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
				  <td class="underLine">
				  <c:if test="${row.trademode==0}">����</c:if>	
				  <c:if test="${row.trademode==1}">����</c:if>	
				  <c:if test="${row.trademode==2}">�б�</c:if>
				  </td>
				  <td class="underLine">
				  <c:if test="${row.validFlag==0}">��</c:if>	
				  <c:if test="${row.validFlag==1}">��</c:if>	
				  </td>
				  <td class="underLine">
		  		  <a href="javascript:editUserInfo(${row.partitionid});" class="normal">�޸�</a></td>
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
				<jsp:param name="colspan" value="4"/>
				<jsp:param name="pageIndex" value="${pageIndex}"/>
				<jsp:param name="totalCnt" value="${totalCnt}"/>
				<jsp:param name="pageCnt" value="${pageCnt}"/>
				<jsp:param name="pageSize" value="${pageSize}"/>
			</jsp:include>			
		</table>
     <table border="0" cellspacing="0" cellpadding="0" width="100%">
          <tr height="35">
          	<td width="40%"><div align="center">
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
  //window.open("sysPartitionMod.jsp?flag=query&id="+id,"_blank","width=500,height=230,scrollbars=yes;");
  //result=PopWindow("sysPartitionMod.jsp?flag=query&id="+id,500,230);
  //var a=openDialog("managerMod.jsp?flag=query&userId="+userCode,"_blank","500","600");
  //window.location="managerList.jsp";
  var result = window.showModalDialog("sysPartitionMod.jsp?flag=query&id="+id,"", "dialogWidth=420px; dialogHeight=280px; status=yes;scroll=yes;help=no;");
  if(result){
  	frm.action="sysPartitionList.jsp";
  	frm.submit();
  	}
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