<%@ page contentType="text/html;charset=GBK" %>
<html xmlns:MEBS>
<%java.sql.Date sqlDate=new java.sql.Date(System.currentTimeMillis());%>
<%@ include file="../../globalDef.jsp"%> 
<%
String orderField = request.getParameter("orderField");
String orderType= request.getParameter("orderDesc");
if (orderField == null || orderField.equals("")) {
	orderField = "contractid";
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
<%
try
{
%>
<!--����-->
<c:if test="${param.opt=='concelBargain'}">
</c:if>

<!--��������-->
<c:set var="sqlFilter" value=""/>
<c:set var="sqlFilterExt" value=""/>
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
	<title>��ǰ�ɽ���ѯ</title>
</head>
<body onload="init();">
<form name=frm action="" method="post">
		<input type="hidden" id="orderField" name="orderField" value="<%=orderField %>">
		<input type="hidden" id="orderDesc" name="orderDesc" value="<%=orderType %>">
	<!--Ĭ��ѡ�а��-->
	<c:set var="defaultSec" value=""/>
	<c:set var="allPartition" value=""/>
<%
  DBBean bean=null;
  ResultSet rs=null;
  StringBuffer sql=null;
  String tempPartiId=null;
  String t_partitionID=delNull(request.getParameter("partitionID"));
  try{
	  bean=new DBBean(JNDI);
	  String tempfilter=null;
	  String allpartition="";
	  //String userpar=session.getAttribute("USERPAR").toString();
	  sql=new StringBuffer();
	  sql.append("select partitionid from v_syspartition where validflag=1 order by partitionid asc");
	  rs=bean.executeQuery(sql.toString());
	  while(rs.next()){
	      allpartition+=rs.getString("partitionid")+",";
	  }
%>
<c:set var="allPartition" value="<%=allpartition%>"/>
<%
      //if(userpar.equals(allpartition.trim())){
	      tempfilter=" and validflag=1 and rownum=1";
	  //}else{
	      //tempfilter=" and validflag=1 and partitionid in ("+userpar+"'') and rownum=1";
	  //}
	  if("".equals(t_partitionID)){
	      sql=new StringBuffer();
	      sql.append("select partitionid from v_syspartition  where 1=1 "+tempfilter+" order by partitionid asc");
          rs=bean.executeQuery(sql.toString());
	      if(rs.next()){
	          tempPartiId=rs.getString("partitionid");
%>
	      <c:set var="defaultSec" value="<%=tempPartiId%>"/>
<%
	      }
	  }	  
%>
	  
<%
  }catch(Exception e){
       e.printStackTrace();
  }finally{
       if(rs!=null)rs.close();
	   if(bean!=null)bean.close();
  }

  if("".equals(t_partitionID)){
%>
  <jsp:include page="../../public/menu.jsp">
	  <jsp:param name="partitionID" value="${defaultSec}"/>
	  <jsp:param name="idx" value="${defaultSec}"/>
	  <jsp:param name="allPartition" value="${allPartition}"/>
  </jsp:include>
<%
  }else{
%>
  <jsp:include page="../../public/menu.jsp">
      <jsp:param name="allPartition" value="${allPartition}"/>
  </jsp:include>
<%
  }	
%>
<!--��ѯ����-->
<!--��ĺ�-->
<c:choose> 
 <c:when test="${empty param.code}"> 
   <c:set var="code" value=""/> 
 </c:when> 
 <c:otherwise>
   <c:set var="code" value="${param.code}"/> 
     <c:set var="sqlFilter" value="${sqlFilter} and u3.code like '${code}' "/>
   </c:otherwise> 
</c:choose>
<!--ί�к�-->
<c:choose> 
 <c:when test="${empty param.submitID}"> 
   <c:set var="submitID" value=""/> 
 </c:when> 
 <c:otherwise>
     <c:set var="submitID" value="${param.submitID}"/> 
     <c:set var="sqlFilter" value="${sqlFilter} and u2.submitid like '${submitID}' "/>
 </c:otherwise> 
</c:choose>
<!--��ͬ��-->
<c:choose> 
 <c:when test="${empty param.contractID}"> 
   <c:set var="contractID" value=""/> 
 </c:when> 
 <c:otherwise>
     <c:set var="contractID" value="${param.contractID}"/> 
     <c:set var="sqlFilter" value="${sqlFilter} and u2.contractid like '${contractID}' "/>
 </c:otherwise> 
</c:choose>
<!--�����û�ID-->
<c:choose> 
 <c:when test="${empty param.userID}"> 
   <c:set var="userID" value=""/> 
 </c:when> 
 <c:otherwise>
     <c:set var="userID" value="${param.userID}"/> 
     <c:set var="sqlFilter" value="${sqlFilter} and u2.userid like '${userID}' "/>
 </c:otherwise> 
</c:choose>
<!--����ѯ����-->
<c:choose> 
 <c:when test="${empty param.partitionID}"> 
   <c:set var="partitionID" value="${defaultSec}"/> 
   <c:set var="sqlFilter" value="${sqlFilter} and u2.tradepartition = '${partitionID}' "/>
 </c:when> 
 <c:otherwise> 
   <c:set var="partitionID" value="${param.partitionID}"/>
   <c:choose>
   <c:when test="${partitionID!=allPartition and partitionID!=0}">
     <c:set var="sqlFilter" value="${sqlFilter} and u2.tradepartition = '${partitionID}' "/>
   </c:when>
   <c:otherwise>
     <c:set var="sqlFilter" value="${sqlFilter} and u2.tradepartition in (select partitionid from v_syspartition where validflag=1) "/>
   </c:otherwise>
   </c:choose>
 </c:otherwise> 
</c:choose>
<!--��ҵȫ��-->
<c:choose> 
 <c:when test="${empty param.name}"> 
   <c:set var="name" value=""/>
 </c:when> 
 <c:otherwise>
     <c:set var="name" value="${param.name}"/> 
     <c:set var="sqlFilter" value="${sqlFilter} and u1.name like '${name}' "/>
 </c:otherwise> 
</c:choose>
	  <br>
		<fieldset width="100%">
		<legend>��ǰ�ɽ���Ʒ��ѯ</legend>
		<span>
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
			  <tr height="35">
            	<td align="right"> ��ĺţ�</td>
                <td align="left">
                	<input name="code" type="text" class="text" style="width: 100px;" value="${param.code}"onkeypress="notSpace()">
                </td>
                <td align="right"> ί�кţ�</td>
                <td align="left">
                	<input name="submitID" type="text" class="text" style="width: 100px;" value="${param.submitID}"onkeypress="notSpace()">
                </td>
                <td align="right"> ��ͬ�ţ�</td>
                <td align="left">
                	<input name="contractID" type="text" class="text" style="width: 100px;" value="${param.contractID}"onkeypress="notSpace()">
                </td>
              </tr>
              <tr>
              <td align="right"> ��ҵȫ�ƣ�</td>
              <td align="left">
              <input name="name" type="text" class="text" style="width: 100px;" value="${param.name}"onkeypress="notSpace()">
              </td>
              <td align="right"> �����û����룺</td>
                <td align="left">
                	<input name="userID" type="text" class="text" style="width: 100px;" value="${param.userID}"onkeypress="notSpace()">
              </td>
              <td></td>
			  <td align="left" colspan="2">
              <!-- add by yangpei 2011-11-22 �������ù��� -->
              <input type="button" onclick="initIdxExt(1);" class="btn" value="��ѯ">&nbsp;
            	<input type="button" onclick="resetForm();" class="btn" value="����">&nbsp;
            	<script>
            		function resetForm(){
            			frm.code.value="";
            			frm.submitID.value="";
            			frm.contractID.value="";
            			frm.name.value="";
            			frm.userID.value="";
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
			    <td class="panel_tHead_MB" abbr="contractid">��ͬ��</td>
			    <td class="panel_tHead_MB" abbr="submitid">ί�к� </td>
			    <td class="panel_tHead_MB" abbr="code">��ĺ�</td>
			    <td class="panel_tHead_MB" abbr="name">��ҵ����</td>
			    <td class="panel_tHead_MB" abbr="id">�����û�����</td>
			    <td class="panel_tHead_MB" abbr="price">�ɽ��۸� </td>
				<td class="panel_tHead_MB" abbr="amount">�ɽ�����</td>
			    <td class="panel_tHead_MB" abbr="tradedate">��������</td>
			    <td class="panel_tHead_MB" abbr="section">���׽ڱ��</td>
					<td class="panel_tHead_RB">&nbsp;</td>
				</tr>
			</tHead>
			<tBody>
			    <db:selectPG_Bargain var="row" orderBy="${a}" pageIndex="${pageIndex}" pageSize="${pageSize}" where="u1.firmid=u2.userid and u2.commodityid=u3.id and u3.id=u4.commid${sqlFilter}">
				  <tr onclick="selectTr();" align=center height="25">
		  			<td class="panel_tBody_LB">&nbsp;</td>
		  			<td class="underLine">${row.contractid}</td>
		  			<td class="underLine">${row.submitid}</td>
		  			<td class="underLine">${row.code}</td>
		  			<td class="underLine">${row.name}</td>
					  <td class="underLine">${row.id}</td>
					  <td class="underLine">${row.price}</td>
					  <td class="underLine">${row.amount}</td>
					  <td class="underLine"><fmt:formatDate value="${row.tradedate}" pattern="<%=DATEPATTERN%>"/></td>
					  <td class="underLine">${row.section}</td>
		  			<td class="panel_tBody_RB">&nbsp;</td>
		  		</tr>
				</db:selectPG_Bargain>
		  	</tBody>
			<!--������ҳ��-->
			<db:cnt_Bargain var="c" where="u1.firmid=u2.userid and u2.commodityid=u3.id and u3.id=u4.commid${sqlFilter}">
			 <c:set var="totalCnt" value="${c.n}"/>
			</db:cnt_Bargain>
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
				<jsp:param name="colspan" value="9"/>
				<jsp:param name="pageIndex" value="${pageIndex}"/>
				<jsp:param name="totalCnt" value="${totalCnt}"/>
				<jsp:param name="pageCnt" value="${pageCnt}"/>
				<jsp:param name="pageSize" value="${pageSize}"/>
			</jsp:include>			
		</table>
     <table border="0" cellspacing="0" cellpadding="0" width="100%">
          <tr height="35">
          	<td width="40%"><div align="center">
		        <input type="hidden" name="opt">
						<input type="hidden" name="queryOpt">
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

//�޸Ļ�Ա�û���Ϣ
function editUserInfo(id,bargainflag){
	if(parseInt(bargainflag)==1){
	  alert("��Ʒ�ѳɽ�,�����޸�!");
	}else{
		var a=window.open("curCommodityMod.jsp?flag=query&code="+id,"_blank","width=400,height=300,scrollbars=yes;");
    //opener.location.href=opener.location.href;
    //var a=openDialog("curCommodityMod.jsp?flag=query&id="+id,"_blank","500","600");
    //window.location="managerList.jsp";
  }
}

//��תҳ��
function pageTo(){
  if(event.keyCode==13){
    agr=document.frm.pageJumpIdx.value;
    document.frm.pageIndex.value=agr;
    alert(document.frm.pageIndex.value);
    ocument.frm.submit();	
   }
}
 
//�����ɽ�
function concelBargain(frm_delete,tableList,checkName)
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
	var queryType='${param.queryOpt}';
	if(queryType=='2'){
	    alert("��ѡ�ɽ�������û�������ͬһ�����г�,���ܳ����ɽ�!");
		return false;
	}
	if(confirm("ȷʵҪ������Щ����?")){
	  frm.opt.value="concelBargain";
	  frm.submit();
	}else{
	  return false;	
	}
}

//����˵��еİ��������ѯʱ
function clickMenu(v){
	frm.partitionID.value=v;
	frm.idx.value=v;
	frm.action="bargainList.jsp";
  frm.submit();	
}
//��������(��Ӳ�ѯ����)
 function expData(){
   var allPartition='${ALLPARTITION}';
   var submitID=encodeURI(frm.submitID.value);
   var code=encodeURI(frm.code.value);
   var userID=encodeURI(frm.userID.value);
   var name=encodeURI(frm.name.value);
   var contractID=encodeURI(frm.contractID.value);
   var marketType=frm.marketType.value;
   var partitionID="";
   if(parseInt(frm.partitionID.value)!=parseInt(allPartition)){
       partitionID=frm.partitionID.value;
   }else{
       partitionID="";
   }
   var url="expXls.jsp?type=9&submitID="+submitID+"&code="+code+"&userID="+userID+"&name="+name+"&partitionID="+partitionID+"&contractID="+contractID+"&marketType="+marketType+"";
   parent.window.location=url;
 }

//����Excel
function excel(){
  	     // frm.add.value="export";
  	      //frm.submit();
  	      var tableList=document.getElementById("tb");
	       //alert(tableList.rows[1].cells.length);
  	      if(tableList)
  	      {
  	        exportToExcel(tableList,0);
  	      }
  }
 //������غ�ͬ
 function expForeignBar(){
   var allPartition='${ALLPARTITION}';
   var partitionID="";
   if(parseInt(frm.partitionID.value)!=parseInt(allPartition)){
       partitionID=frm.partitionID.value;
   }else{
       partitionID="";
   }
   var url="expForeignBar.jsp?type=23&partitionID="+partitionID+"";
   parent.window.location=url;
 }

 //��ѯ�����������г�
 function query2(){
   if(Trim(frm.notBelongCode.value) == ""&&Trim(frm.notBelongComm.value) == "")
	{
		alert("������������г���ͬ�Ż��߱�ĺţ�");
		frm.notBelongCode.focus();
		return false;
	}else if(Trim(frm.notBelongMarket.value) == "")
	{
		alert("�������г�����Ϊ�գ�");
		frm.tradepartition.focus();
		return false;
	}else if(frm.notBelongCode.value.indexOf('%')>-1)
	{
		alert("��ѯ�������г���Ϣ����ģ����ѯ,ֻ�ܲ�ѯ������");
		frm.notBelongCode.focus();
		return false;
	}else if(frm.notBelongComm.value.indexOf('%')>-1)
	{
		alert("��ѯ�������г���Ϣ����ģ����ѯ,ֻ�ܲ�ѯ������");
		frm.notBelongComm.focus();
		return false;
	}else{
       initIdxExt(2);
	}
 }
//-->
</SCRIPT>
<%
}
catch(Exception e)
{
	System.out.println(e.toString());
}
%>