<%@ page contentType="text/html;charset=GBK" %>
<html xmlns:MEBS>
<%java.sql.Date sqlDate=new java.sql.Date(System.currentTimeMillis());%>
<%@ include file="../../globalDef.jsp"%> 
<%
String orderField = request.getParameter("orderField");
String orderType= request.getParameter("orderDesc");
if (orderField == null || orderField.equals("")) {
	orderField = "unitid";
}
if (orderType == null || orderType.equals("")) {
	orderType = "false";
}
String order = "";
if (orderType.equals("false")) {
	order = "asc";
}
if (orderType.equals("true")){
	order = "desc";
}
String a = orderField+" "+order;
request.setAttribute("a", a);
%>
<!--����-->
<c:if test='${not empty param.opt}'>
 <c:set var="modifyTime" value="<%=sqlDate%>"/>
 <!--�洢���̿��Ʊ��id-->
 <c:set var="ids" value=""/>
 <!--��¼���̵�Ԫ��������̵�Ԫ����-->
 <c:set var="unitIdValue" value=""/>
 <c:set var="unitTypeValue" value=""/>
 <!--Ϊ�˲�ִ������Ĳ������ñ��λ��¼ѭ���˵ڼ���-->
 <c:set var="circleFlag" value="0"/>
 <!--Ϊ��д��־,��¼ɾ�����̱��-->
 <c:set var="logUnitID" value=""/>
  <c:if test="${param.opt=='delete'}">
  	<!--�������Ĳ���Ϊ�����ֶ�ֵ��Ϊ�˵õ�ֵ������ѭ�����в��-->
  	<c:forEach var="row" items="${paramValues.ck}">
	    <c:forEach begin="0" end="0" step="1" var="rowChar" items="${row}">
	    	 <c:set var="unitIdValue" value="${rowChar}"/>
	    	 <c:choose>
	    	   <c:when test="${not empty logUnitID}">
	    	     <c:set var="logUnitID" value="${logUnitID},${rowChar}"/>
	    	   </c:when>
	    	   <c:otherwise>
	    	     <c:set var="logUnitID" value="${rowChar}"/>
	    	   </c:otherwise>
	    	   </c:choose>
	    </c:forEach>
	    <c:forEach begin="1" end="1" step="1" var="rowChar" items="${row}">
	    	 <c:set var="unitTypeValue" value="${rowChar}"/>
	    </c:forEach>
	    <db:delete table="v_flowcontrol" where="unitid=${unitIdValue} and unittype=${unitTypeValue} and tradepartition = ${param.partitionID}"/>
	    <c:set var="unitIdValue" value=""/>
	    <c:set var="unitTypeValue" value=""/>
 	  </c:forEach>
 	  <c:set var="ids" value="${ids}''"/>
<!-Ϊ��д��־,���������������-->
<%

%>
<script language="javascript">
 	<!--
 	  	alert("��������ɾ���ɹ�!");
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
<head>
	<title>�����������</title>
</head>
<body onload="init();">
<form name=frm action="" method="post">
		<input type="hidden" id="orderField" name="orderField" value="<%=orderField %>">
		<input type="hidden" id="orderDesc" name="orderDesc" value="<%=orderType %>">
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
<!--���̵�Ԫ����-->
<c:choose> 
 <c:when test="${empty param.unitType}"> 
   <c:set var="unitType" value=""/> 
 </c:when> 
 <c:otherwise> 
   <c:set var="unitType" value="${param.unitType}"/> 
   <c:set var="sqlFilter" value="${sqlFilter} and unittype = ${unitType} "/>
 </c:otherwise> 
</c:choose>
<!--����ѯ����-->
<c:choose> 
 <c:when test="${empty param.partitionID}"> 
   <c:set var="partitionID" value="${defaultSec}"/>
   <c:set var="sqlFilter" value="${sqlFilter} and tradepartition = '${partitionID}' "/>
 </c:when> 
 <c:otherwise> 
   <c:set var="partitionID" value="${param.partitionID}"/> 
   <c:set var="sqlFilter" value="${sqlFilter} and tradepartition = '${partitionID}' "/>
 </c:otherwise> 
</c:choose>
	  <br>
		<br>
		<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%" height="300">
	  		<tHead>
	  			<tr height="25"  align=center>
	  			<td class="panel_tHead_LB">&nbsp;</td>
	  			<td class="panel_tHead_MB">&nbsp;</td>
					<td class="panel_tHead_MB" align=left><input type="checkbox" id="checkAll" onclick="selectAll(tb,'ck')"></td>
			    <td class="panel_tHead_MB" abbr="unitid">���̵�Ԫ���</td>
			    <td class="panel_tHead_MB" abbr="unittype">���̵�Ԫ���� </td>
			    <td class="panel_tHead_MB" abbr="startMode">���׽ڿ�ʼģʽ</td>
			    <td class="panel_tHead_MB" abbr="starttime">���׽ڿ�ʼʱ��</td>
			    <td class="panel_tHead_MB" abbr="durativetime">���׵�Ԫ����ʱ��</td>
					<td class="panel_tHead_RB">&nbsp;</td>
				</tr>
			</tHead>
			<tBody>
			    <db:selectPG var="row" table="v_flowcontrol" columns="<%=COLS_FLOWCONTROL%>" orderBy="${a}" pageIndex="${pageIndex}" pageSize="${pageSize}" where="1=1${sqlFilter}">
				  <tr onclick="selectTr();" align=center height="25">
		  			<td class="panel_tBody_LB">&nbsp;</td>
		  			<td class="underLine">	
		  			<c:choose>
		  			<c:when test="${row.unittype==1}">
		  			  <font color="#0000ff">��</font>
		  			</c:when>
		  			<c:otherwise>
		  				��
		  		  </c:otherwise>
		  		  </c:choose>
		  			</td>
		  			<td class="underLine" align=left><input name="ck" type="checkbox" value='${row.unitid},${row.unittype}'></td>
		  			<td class="underLine"><a href="javascript:editUserInfo('${row.unitid}','${row.unittype}','${row.tradepartition}');" class="normal">${row.unitid}</a></td>
		  			  <td class="underLine">
		  			  <c:if test="${row.unittype==1}">���׽�</c:if>
					    <c:if test="${row.unittype==2}">��Ϣ��</c:if>
		  			</td>
					  <td class="underLine">
					  <c:if test="${row.startMode==1}">����ָ��ʱ���Զ���ʼ</c:if>
					  <c:if test="${row.startMode==2}">�ֹ���ʼ</c:if>
					  <c:if test="${row.startMode==3}">���տ��������Զ���ʼ</c:if>
					  <c:if test="${row.startMode==-1}">&nbsp;</c:if>
					  </td>
					  <td class="underLine">
					  <c:choose>
		  			<c:when test="${not empty row.starttime}">
		  			  ${row.starttime}
		  			</c:when>
		  			<c:otherwise>
		  				&nbsp;
		  		  </c:otherwise>
		  		  </c:choose>	
					  </td>
					  <td class="underLine">
					  <c:choose>
		  			<c:when test="${not empty row.durativetime}">
		  			  ${row.durativetime}
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
			<db:select var="c" table="v_flowcontrol" columns="count(*) as n" where="1=1${sqlFilter}">
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
				<jsp:param name="colspan" value="7"/>
				<jsp:param name="pageIndex" value="${pageIndex}"/>
				<jsp:param name="totalCnt" value="${totalCnt}"/>
				<jsp:param name="pageCnt" value="${pageCnt}"/>
				<jsp:param name="pageSize" value="${pageSize}"/>
			</jsp:include>			
		</table>
     <table border="0" cellspacing="0" cellpadding="0" width="100%">
          <tr height="35"  align="right">
          	<td width="40%"  align="right"><div align="right">
			  <input type="button" onclick="gotoUrl('traderFlowAdd.jsp?partitionID=${partitionID}')" class="btn" value="���">&nbsp;
  		  	  <input type="button" onclick="return deleteRec(frm,tb,'ck');" class="btn" value="ɾ��">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
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
//�޸����̿�����Ϣ
function editUserInfo(id,unitType,tradepartition){
	
	var result=window.showModalDialog("traderFlowMod.jsp?unitID="+id+"&unitType="+unitType+"&tradePartition="+tradepartition+"","", "dialogWidth=450px; dialogHeight=380px; status=yes;scroll=yes;help=no;");
  if(result){
  	window.location.href="traderFlowList.jsp?partitionID="+frm.partitionID.value;
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

//����˵��еİ��������ѯʱ
function clickMenu(v){
	frm.partitionID.value=v;
	frm.idx.value=v;
	frm.action="traderFlowList.jsp";
  frm.submit();	
}
</SCRIPT>