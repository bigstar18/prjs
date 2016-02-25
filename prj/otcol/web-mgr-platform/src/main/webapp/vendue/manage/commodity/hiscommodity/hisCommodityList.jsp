<%@ page contentType="text/html;charset=GBK" %>
<html xmlns:MEBS>
<%java.sql.Date sqlDate=new java.sql.Date(System.currentTimeMillis());%>
<%@ include file="../../globalDef.jsp"%> 
<%
String orderField = request.getParameter("orderField");
String orderType= request.getParameter("orderDesc");
if (orderField == null || orderField.equals("")) {
	orderField = "code";
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
	<title>������Ʒ��ʷ��¼</title>
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
<!--��ѯ����-->
<c:choose> 
 <c:when test="${empty param.code}"> 
   <c:set var="code" value=""/> 
 </c:when> 
 <c:otherwise> 
   <c:set var="code" value="${param.code}"/> 
   <c:set var="sqlFilter" value="${sqlFilter} and code like '${code}' "/>
 </c:otherwise> 
</c:choose>
<c:choose> 
 <c:when test="${empty param.bargainFlag}"> 
   <c:set var="bargainFlag" value=""/> 
 </c:when> 
 <c:otherwise> 
   <c:set var="bargainFlag" value="${param.bargainFlag}"/> 
   <c:set var="sqlFilter" value="${sqlFilter} and bargainflag =${bargainFlag} "/>
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
<!--���׽ڷ�Χ-->
<c:choose> 
 <c:when test="${empty param.startSection}"> 
   <c:set var="startSection" value=""/> 
 </c:when> 
 <c:otherwise> 
   <c:set var="startSection" value="${param.startSection}"/> 
   <c:set var="sqlFilter" value="${sqlFilter} and section >= '${startSection}' "/>
 </c:otherwise> 
</c:choose>
<c:choose> 
 <c:when test="${empty param.endSection}"> 
   <c:set var="endSection" value=""/> 
 </c:when> 
 <c:otherwise> 
   <c:set var="endSection" value="${param.endSection}"/> 
   <c:set var="sqlFilter" value="${sqlFilter} and section <= '${endSection}' "/>
 </c:otherwise> 
</c:choose>
<!--����ʱ�䷶Χ-->
<c:choose> 
 <c:when test="${empty param.startTradeDate}">
    <!--��һ�β�ѯʱĬ��Ϊ�����ʱ��-->
    <c:choose>
	  <c:when test="${not empty param.funcflg}">
        <c:set var="startTradeDate" value="<%=sqlDate.toString()%>"/> 
        <c:set var="sqlFilter" value="${sqlFilter} and tradedate >= to_date('${startTradeDate} 00:00:00','yyyy-mm-dd hh24:mi:ss') "/>
	  </c:when>
	 <c:otherwise>
       <c:set var="startTradeDate" value=""/> 
	 </c:otherwise> 
   </c:choose>
 </c:when> 
 <c:otherwise> 
   <c:set var="startTradeDate" value="${param.startTradeDate}"/> 
   <c:set var="sqlFilter" value="${sqlFilter} and tradedate >= to_date('${startTradeDate} 00:00:00','yyyy-mm-dd hh24:mi:ss') "/>
 </c:otherwise> 
</c:choose>
<c:choose> 
 <c:when test="${empty param.endTradeDate}">
 <!--��һ�β�ѯʱĬ��Ϊ�����ʱ��-->
   <c:choose>
	  <c:when test="${not empty param.funcflg}">
         <c:set var="endTradeDate" value="<%=sqlDate.toString()%>"/> 
         <c:set var="sqlFilter" value="${sqlFilter} and tradedate <= to_date('${endTradeDate} 23:59:59','yyyy-mm-dd hh24:mi:ss') "/>
      </c:when>
	  <c:otherwise>
         <c:set var="endTradeDate" value=""/>
	  </c:otherwise> 
   </c:choose>
 </c:when> 
 <c:otherwise> 
   <c:set var="endTradeDate" value="${param.endTradeDate}"/> 
   <c:set var="sqlFilter" value="${sqlFilter} and tradedate <= to_date('${endTradeDate} 23:59:59','yyyy-mm-dd hh24:mi:ss') "/>
 </c:otherwise> 
</c:choose>
	  <br>
		<fieldset width="100%">
		<legend>������Ʒ��ʷ��¼��ѯ</legend>
		<span>
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
			  <tr height="35">
            	<td align="right">��ĺţ�</td>
                <td align="left">
                	<input name="code" type="text" class="text" style="width: 100px;" value="${param.code}"onkeypress="notSpace()">
                </td>
                <td align="left">���׽ڷ�Χ��</td>
                <td align="left">
                	<input name="startSection" type="text" class="text" style="width: 100px;" value="${param.startSection}"onkeypress="notSpace()">��
                	<input name="endSection" type="text" class="text" style="width: 100px;" value="${param.endSection}"onkeypress="notSpace()">
                </td>
                <td algin="left">����ʱ�䣺</td>
                <td>
				<!--����͵�һ�β�ѯʱ,��Ĭ��Ϊ�����ʱ��-->
				<c:set var="startTradeDateAgr" value=""/>
				<c:set var="endTradeDateAgr" value=""/>
				<c:choose>
					<c:when test="${not empty param.funcflg}">
					<c:set var="startTradeDateAgr" value="<%=sqlDate.toString()%>"/>
					<c:set var="endTradeDateAgr" value="<%=sqlDate.toString()%>"/>
					</c:when>
					<c:otherwise>
					<c:set var="startTradeDateAgr" value="${param.startTradeDate}"/>
					<c:set var="endTradeDateAgr" value="${param.endTradeDate}"/>
					</c:otherwise>
				</c:choose>
                <MEBS:calendar eltID="startTradeDate" eltName="startTradeDate" eltCSS="date" eltStyle="width:80px" eltImgPath="<%=skinPath%>/images/" eltValue="${startTradeDateAgr}"/>
                &nbsp;��&nbsp;<MEBS:calendar eltID="endTradeDate" eltName="endTradeDate" eltCSS="date" eltStyle="width:80px" eltImgPath="<%=skinPath%>/images/" eltValue="${endTradeDateAgr}"/>
                </td>
               	<td align="right">״̬��</td>
                <td align="left">
                	<select name="bargainFlag">
                		 <option value="" <c:if test="${empty param.bargainFlag}"><c:out value="selected"/></c:if>>ȫ��</option>
                		 <option value="1" <c:if test="${param.bargainFlag==1}"><c:out value="selected"/></c:if>>�ɽ�</option>
                		 	<option value="0" <c:if test="${param.bargainFlag=='0'}"><c:out value="selected"/></c:if>>δ�ɽ�</option>
                  </select>	
                </td>
                <td align="right" colspan="4">
                <input type="button" onclick="queryBtnNN();" class="btn" value="��ѯ">
                <!-- add by yangpei 2011-11-22 �������ù��� -->
            	<input type="button" onclick="resetForm();" class="btn" value="����">&nbsp;
            	<script>
            		function resetForm(){
            			frm.code.value="";
            			frm.startSection.value="";
            			frm.endSection.value="";
            			frm.startTradeDate.value="";
            			frm.endTradeDate.value="";
            			frm.bargainFlag.value="";
            		}
            		//���׽�������֤
            		function queryBtnNN(){
            		//�������Ľ��׽���Ϣ��������
            			if(!isNumber(frm.startSection.value)||!isNumber(frm.endSection.value)){
            				alert("���׽ڷ�Χ���������ֲ�ѯ");
            				return false;
            			}
            			queryBtn('hisCommodityList.jsp');//�����ύ����
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
	  			<td class="panel_tHead_MB">&nbsp;</td>
					<td class="panel_tHead_MB" align=left><input type="checkbox" id="checkAll" onclick="selectAll(tb,'ck')"></td>
			    <td class="panel_tHead_MB" abbr="code">��ĺ� </td>
			    <td class="panel_tHead_MB" abbr="section">�������׽�</td>
			    <td class="panel_tHead_MB" abbr="bargainflag">�ɽ�״�� </td>
			    <td class="panel_tHead_MB" abbr="tradedate">����ʱ�� </td>
			    <td class="panel_tHead_MB" abbr="modifytime">����޸�ʱ��</td>
					<td class="panel_tHead_RB">&nbsp;</td>
				</tr>
			</tHead>
			<tBody>
			    <db:selectPG var="row" table="v_hiscommodity" columns="<%=COLS_HISCOMMODITY%>" orderBy="${a}" pageIndex="${pageIndex}" pageSize="${pageSize}" where="1=1${sqlFilter}">
				  <tr onclick="selectTr();" align=center height="25">
		  			<td class="panel_tBody_LB">&nbsp;</td>
		  			<td class="underLine">
		  			<c:choose>
		  				<c:when test="${row.section==-1}">
		  			    <font color="#ff0000">��</font>
		  			  </c:when>
		  			  <c:otherwise>
		  			  	   <c:choose>
		  			  	   	 <c:when test="${row.bargainflag==1}">
		  			  	   	 	  <font color="#00ff00">��</font>
		  			  		   </c:when>
                     <c:otherwise>
		  			  		      <font color="#FFBB0D">��</font>
		  			  		   </c:otherwise>
		  			  		</c:choose>
		  			  </c:otherwise>
		  			</c:choose>
		  			</td>
		  			<td class="underLine" align=left><input name="ck" type="checkbox" value='${row.code}'></td>
		  			<td class="underLine">${row.code}</td>
		  			<td class="underLine">
		  			<c:choose>
		  			<c:when test="${not empty row.section}">
		  				<c:choose>
		  					<c:when test="${row.section!=-1}">
		  			      ${row.section}
		  			    </c:when>
		  			    <c:otherwise>
		  			    	&nbsp;
		  			    </c:otherwise>
		  			  </c:choose>
		  			</c:when>
		  			<c:otherwise>
		  				&nbsp;
		  		  </c:otherwise>
		  		  </c:choose>
		  			</td>
					  <td class="underLine">
					  <c:if test="${row.bargainflag==1}">�ɽ�</c:if>
					  <c:if test="${row.bargainflag==0}">δ�ɽ�</c:if>
					  </td>
					  <td class="underLine"><fmt:formatDate value="${row.tradedate}" pattern="<%=DATEPATTERN%>"/></td>
					  <td class="underLine"><fmt:formatDate value="${row.modifytime}" pattern="<%=DATEPATTERN%>"/></td>
		  			<td class="panel_tBody_RB">&nbsp;</td>
		  		</tr>
				</db:selectPG>
		  	</tBody>
			<!--������ҳ��-->
			<db:select var="c" table="v_hiscommodity" columns="count(*) as n" where="1=1${sqlFilter}">
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
	frm.action="hisCommodityList.jsp";
  frm.submit();	
}
</SCRIPT>