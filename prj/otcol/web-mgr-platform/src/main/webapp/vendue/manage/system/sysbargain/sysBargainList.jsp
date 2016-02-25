<%@ page contentType="text/html;charset=GBK" %>
<html xmlns:MEBS>
<%java.sql.Date sqlDate=new java.sql.Date(System.currentTimeMillis());%>
<%@ include file="../../globalDef.jsp"%> 

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
	<title>ϵͳ�޸ĺ�ͬ</title>
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
	   <c:set var="sqlFilter" value="${sqlFilter} and type like '${partitionID}' "/>
	 </c:when> 
	 <c:otherwise> 
	   <c:set var="partitionID" value="${param.partitionID}"/> 
	   <c:set var="sqlFilter" value="${sqlFilter} and type like '${partitionID}' "/>
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
		<br>
		<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%" height="300">
	  		<tHead>
	  			<tr height="25"  align=center>
	  				<td class="panel_tHead_LB">&nbsp;</td>
					<td class="panel_tHead_MB"><input type="checkbox" id="checkAll" onclick="selectAll(tb,'ck')"></td>
					<td class="panel_tHead_MB">���</td>
					<td class="panel_tHead_MB">�޸�</td>
					<td class="panel_tHead_RB">&nbsp;</td>
				</tr>
			</tHead>
			<tBody>
			    <db:selectPG var="row" table="v_contracttemplet" columns="id,name" pageIndex="${pageIndex}" pageSize="${pageSize}" where="1=1${sqlFilter}" >
				  <tr onclick="selectTr();" align=center height="25">
		  			<td class="panel_tBody_LB">&nbsp;</td>
		  			<td class="underLine"><input name="ck" type="checkbox" value='${row.id}'></td>
		  			<td class="underLine">${row.name}</td>
		  			 <td class="underLine"><a href="javascript:modifyControl('${row.id}')" class="normal">�޸�</a></td>
		  			<td class="panel_tBody_RB">&nbsp;</td>
		  		</tr>
				</db:selectPG>
		  	</tBody>
			<!--������ҳ��-->
			<db:select var="c" table="v_contracttemplet" columns="count(*) as n" where="1=1${sqlFilter}">
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
				<jsp:param name="colspan" value="3"/>
				<jsp:param name="pageIndex" value="${pageIndex}"/>
				<jsp:param name="totalCnt" value="${totalCnt}"/>
				<jsp:param name="pageCnt" value="${pageCnt}"/>
				<jsp:param name="pageSize" value="${pageSize}"/>
			</jsp:include>			
		</table>
</form>
</body>
</html>
<%@ include file="../../public/pageTurn2.jsp"%>
<SCRIPT LANGUAGE="JavaScript">
//����˵��еİ��������ѯʱ
function clickMenu(v){
	frm.partitionID.value=v;
	frm.idx.value=v;
	frm.action="sysBargainList.jsp";
  frm.submit();	
}

function modifyControl(id){
   var result=window.showModalDialog("sysBargainMod.jsp?id="+id,"", "dialogWidth=800; dialogHeight=800; status=yes;scroll=yes;help=no;"); 
   if(result){
   		frm.action="sysBargainList.jsp";
   		frm.submit();
   	}
}

</SCRIPT>