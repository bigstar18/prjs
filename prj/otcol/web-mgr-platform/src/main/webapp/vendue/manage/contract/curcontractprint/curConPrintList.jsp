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
<!--操作-->
<c:if test='${not empty param.opt}'>
	<c:if test="${param.opt=='print'}">
<%

	    String contractIds=request.getParameter("contractIds");
	    if(ManaUtil.checkStr(contractIds)){
			boolean printFlag=false;
			String contractID=null;
		    contractIds=contractIds+"''";
%>
     <script language="javascript">
	      basePath="<%=request.getContextPath()%>/vendue";
	      contractIds="<%=contractIds%>";
		  sUrl =basePath+"../../public/printCurContract.jsp?contractIds="+contractIds+"";
          //openDialog(sUrl,window,300,300);
		  window.open(sUrl,"d","toolbar=no,location=no,resizable=no,scrollbars=yes,menubar=no,width=300,height=300,top=50,left=100");
	 </script>
<%
		}		 
%>
	</c:if>
</c:if>

<!--变量定义-->
<c:set var="sqlFilter" value=""/>
<!--非所属市场查询-->
<c:set var="sqlFilterExt" value=""/>
<!--分页参数定义-->
<%
  int pageIndex=1;
  int pageSize=1;
%>
<c:choose> 
 <c:when test="${empty param.pageIndex}"> 
   <c:set var="pageIndex" value="1"/> 
   	<%pageIndex=1;%>
 </c:when> 
 <c:otherwise> 
   <c:set var="pageIndex" value="${param.pageIndex}"/>
   	<%pageIndex=Integer.parseInt(request.getParameter("pageIndex"));%>
 </c:otherwise> 
</c:choose>
<c:choose>
  <c:when test="${empty param.pageSize}">
  	 <c:set var="pageSize" value="${PAGESIZE}"/>
	 <%
		pageSize = PAGESIZE;
	 %>
  </c:when>
  <c:otherwise>
  	 <c:set var="pageSize" value="${param.pageSize}"/>
  	 <%pageSize=Integer.parseInt(request.getParameter("pageSize"));%>
  </c:otherwise>
</c:choose>
  <head>
	<title>当前合同打印</title>
</head>
<%
     //创建连接数据对象
     Connection conn = null;
     PreparedStatement ps = null;
     ResultSet rs = null;
	 StringBuffer sql=new StringBuffer();
     String tempPartiId=null;
	 int totalCnt=0;
	 int pageCnt=0;
	 try{
		 Context initContext = new InitialContext();
         Context envContext  = (Context)initContext.lookup("java:/comp/env");
         DataSource ds = (DataSource)envContext.lookup(JNDI);
         conn = ds.getConnection();
%>
<body onload="init();">
<form name=frm action="" method="post">
		<input type="hidden" id="orderField" name="orderField" value="<%=orderField %>">
		<input type="hidden" id="orderDesc" name="orderDesc" value="<%=orderType %>">
	<!--默认选中板块-->
	<c:set var="defaultSec" value=""/>
	<c:set var="allPartition" value=""/>
<%
	  
	  String tempfilter=null;
	  String allpartition="";
	  String t_partitionID=delNull(request.getParameter("partitionID"));
	  sql=new StringBuffer();
	  sql.append("select partitionid from v_syspartition where validflag=1 order by partitionid asc");
	  ps=conn.prepareStatement(sql.toString());
	  rs=ps.executeQuery();
	  while(rs.next()){
	      allpartition+=rs.getString("partitionid")+",";
	  }
%>
<c:set var="allPartition" value="<%=allpartition%>"/>
<%
	      tempfilter=" and validflag=1 and rownum=1";
	  if("".equals(t_partitionID)){
	      sql=new StringBuffer();
	      sql.append("select partitionid from v_syspartition  where 1=1 "+tempfilter+" order by partitionid asc");
          ps = conn.prepareStatement(sql.toString());
	      rs=ps.executeQuery();
	      if(rs.next()){
	          tempPartiId=rs.getString("partitionid");
%>
	      <c:set var="defaultSec" value="<%=tempPartiId%>"/>
<%
	      }
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
<!--查询条件-->
<%
  String filter="";
  String filterExt="";
%>
<!--商品号-->
<c:choose> 
 <c:when test="${empty param.code}"> 
 </c:when> 
 <c:otherwise> 
   <%filter=filter+"and code like '"+request.getParameter("code")+"'";%>
 </c:otherwise> 
</c:choose>
<!--合同号-->
<c:choose> 
 <c:when test="${empty param.contractID}"> 
 </c:when> 
 <c:otherwise> 
   <%filter=filter+" and contractid like '"+request.getParameter("contractID")+"'";%>
 </c:otherwise> 
</c:choose>
<!--板块查询条件-->
<c:choose> 
 <c:when test="${empty param.partitionID}"> 
   <c:set var="partitionID" value="${defaultSec}"/> 
	<%filter=filter+" and tradepartition = "+pageContext.getAttribute("partitionID").toString()+"";%>
 </c:when> 
 <c:otherwise> 
   <c:set var="partitionID" value="${param.partitionID}"/>
   <c:choose>
   <c:when test="${partitionID!=allPartition and partitionID!=0}">
	 <%filter=filter+" and tradepartition = "+pageContext.getAttribute("partitionID").toString()+"";%>
   </c:when>
   <c:otherwise>
	 <%filter=filter+" and tradepartition in  (select partitionid from v_syspartition where validflag=1)";%>
   </c:otherwise>
   </c:choose>
 </c:otherwise> 
</c:choose>

<!--卖方编号-->
<c:choose> 
 <c:when test="${empty param.blUserId}"> 
 </c:when> 
 <c:otherwise> 
   <%filter=filter+" and bluserid like  '"+request.getParameter("blUserId")+"'";%>
 </c:otherwise> 
</c:choose>
<!--买方编号-->
<c:choose> 
 <c:when test="${empty param.userId}"> 
 </c:when> 
 <c:otherwise> 
   <%filter=filter+" and userid like '"+request.getParameter("userId")+"'";%>
 </c:otherwise> 
</c:choose>

	  <br>
		<fieldset width="100%">
		<legend>当前合同打印查询</legend>
		<span>
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
			  <tr height="35">
            	<td align="right"> 标的号：</td>
                <td align="left">
                	<input name="code" type="text" class="text" style="width: 100px;" value="${param.code}"onkeypress="notSpace()">
                </td>
                <td align="right">合同号：</td>
                <td align="left">
                	<input name="contractID" type="text" class="text" style="width: 100px;" value="${param.contractID}"onkeypress="notSpace()">
                </td>
                <td align="right"> 买方编号：</td>
                <td align="left">
                	<input name="userId" type="text" class="text" style="width: 150px;" value="${param.userId}"onkeypress="notSpace()">
                </td>
                	<td align="right"> 卖方编号：</td>
                <td align="left">
                	<input name="blUserId" type="text" class="text" style="width: 100px;" value="${param.blUserId}"onkeypress="notSpace()">
                </td>
                <td colspan="4" align="left">
                <input type="button" onclick="initIdxExt(1);" class="btn" value="查询">
                <!-- add by yangpei 2011-11-22 增加重置功能 -->
            	<input type="button" onclick="resetForm();" class="btn" value="重置">&nbsp;
            	<script>
            		function resetForm(){
            			frm.code.value="";
            			frm.contractID.value="";
            			frm.userId.value="";
            			frm.blUserId.value="";
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
				<td class="panel_tHead_MB" align=left><input type="checkbox" id="checkAll" onclick="selectAll(tb,'ck')"></td> 
			    <td class="panel_tHead_MB" abbr="contractid">合同号</td>
			    <td class="panel_tHead_MB" abbr="code">标的号</td>
				<td class="panel_tHead_MB" abbr="userid">买方编号</td>
				<td class="panel_tHead_MB" abbr="bluserid">卖方编号</td>
				<td class="panel_tHead_MB" abbr="str4">品种</td>
			    <td class="panel_tHead_MB" abbr="price">单价（元）</td>
				<td class="panel_tHead_MB" abbr="amount">数量</td>
			    <td class="panel_tHead_MB" abbr="aa">成交金额（元）</td>
			    <td class="panel_tHead_MB" abbr="tradedate">成交日期</td>
			    <td class="panel_tHead_MB" style="display:none">&nbsp;</td>
				<td class="panel_tHead_RB">&nbsp;</td>
				</tr>
			</tHead>
			<tBody>
	<%
		   String startRow=String.valueOf((pageIndex-1)*pageSize+1);
		   String endRow=String.valueOf(pageIndex*pageSize);
		   sql=new StringBuffer();
		   sql.append("select (price*amount*tradeunit) aa,tradepartition,submitid,code,contractid,price,amount,userid,");
		   sql.append("tradedate,section,b_bail,b_poundage,s_bail,s_poundage,status,str4,str2,tradeunit,trademode");
		   sql.append(",bluserid,rown from ( select tradepartition,submitid,code,contractid,");
		   sql.append("price,amount,userid,tradedate,section,b_bail,b_poundage,s_bail,s_poundage,status,str4,str2");
		   sql.append(",tradeunit,trademode,bluserid,rownum rown from (select tradepartition,");
		   sql.append("submitid,code,contractid,price,amount,userid,tradedate,section,b_bail");
		   sql.append(",b_poundage,s_bail,s_poundage,status,str4,str2,tradeunit,trademode,bluserid from (select ");
		   sql.append("tradepartition,submitid,code,contractid,price,amount,tradedate,section");
		   sql.append(",b_bail,b_poundage,s_bail,s_poundage,status,str4,str2,tradeunit,trademode,case when trademode=0");
		   sql.append("then userid else bluserid end userid,case when trademode=0 then bluserid else userid");
		   sql.append(" end bluserid from (select u1.tradepartition,u1.submitid,");
		   sql.append("u1.code,u1.contractid,u1.price,u1.amount,u1.userid,u1.tradedate,u1.section");
		   sql.append(",u1.b_bail,u1.b_poundage,u1.s_bail,u1.s_poundage,u1.status,u2.trademode,u2.userid bluserid,u3.str4");
		   sql.append(",u3.str2,u2.tradeunit from v_bargain u1,v_commodity u2,v_commext u3 where  ");
		   sql.append("u1.commodityid=u2.id and u2.id=u3.commid )) where 1=1 "+filter+" ) ) ");
		   sql.append("where rown between "+startRow+" and "+endRow+"");
		   sql.append(" order by "+a);
           ps = conn.prepareStatement(sql.toString());
	       rs=ps.executeQuery();
	       while(rs.next()){
    %>
				    <tr onclick="selectTr();" align=center height="25">
		  			<td class="panel_tBody_LB">&nbsp;</td>
		  			<td class="underLine" align=left><input name="ck" type="checkbox" value='<%=rs.getString("contractid")%>'></td> 
		  			<td class="underLine"><a href="javascript:viewContract('<%=rs.getString("contractid")%>')" class="normal"><%=rs.getString("contractid")%></a></td>
		  			<td class="underLine"><%=rs.getString("code")%></td>
					<td class="underLine"><%=rs.getString("userid")%></td>
					<td class="underLine"><%=rs.getString("bluserid")%></td>
					
					<td class="underLine"><%=replaceNull(rs.getString("str4"))%></td>
					<td class="underLine">
					<%=disDouble(rs.getDouble("price"))%>
					</td>
					<td class="underLine">
					<fmt:formatNumber value="<%=disInt(rs.getDouble(7))%>" pattern="<%=AMOUNTPATTERN%>"/>
					</td>
					<td class="underLine">
					<%=disDouble(rs.getDouble("aa")) %>
					</td>
					<td class="underLine"><%=rs.getString("tradedate").substring(0,10)%></td>
					<td class="underLine" style="display:none" name="contractContent" id="contractContent">
			        </td>
		  	        <td class="panel_tBody_RB">&nbsp;</td>
		  	        </tr>
		  	<%
		       }
	        %>
		  	</tBody>
			<%
		        sql=new StringBuffer();
			    sql.append("select count(contractid) as n from (select tradepartition,submitid,");
				sql.append("code,contractid,price,amount,userid,tradedate,section,b_bail,b_poundage,s_bail,s_poundage,");
				sql.append("status,str4,str2,tradeunit,trademode,bluserid from (select tradepartition");
				sql.append(",submitid,code,contractid,price,amount,tradedate,section,b_bail,");
				sql.append("b_poundage,s_bail,s_poundage,status,str4,str2,tradeunit,trademode,case when trademode=0");
				sql.append(" then userid else bluserid end userid,case when trademode=0 then");
				sql.append(" bluserid else userid end bluserid from (select u1.tradepartition");
				sql.append(",u1.submitid,u1.code,u1.contractid,u1.price,u1.amount,u1.userid,");
				sql.append("u1.tradedate,u1.section,u1.b_bail,u1.b_poundage,u1.s_bail,u1.s_poundage,u1.status,u2.trademode");
				sql.append(",u2.userid bluserid,u3.str4,u3.str2,u2.tradeunit from v_bargain u1,");
				sql.append("v_commodity u2,v_commext u3 where  u1.commodityid=u2.id and u2.id=u3.commid ");
				sql.append(" ) where 1=1 "+filter+" ))");
                ps = conn.prepareStatement(sql.toString());
	            rs=ps.executeQuery();
				if(rs.next()){
				    totalCnt=rs.getInt("n");
				}
				rs.close();
				ps.close();
				if(totalCnt%pageSize==0){
				    pageCnt=totalCnt/pageSize;
				}else{
				    if((totalCnt%pageSize)*10>=5*pageSize){
					    pageCnt=totalCnt/pageSize;
					}else{
					    pageCnt=totalCnt/pageSize+1;
					}
				}
	        %>
			<c:set var="pageIndex" value="<%=new Integer(pageIndex)%>"/>
            <c:set var="totalCnt" value="<%=new Integer(totalCnt)%>"/>
			<c:set var="pageCnt" value="<%=new Integer(pageCnt)%>"/>
			<c:set var="pageSize" value="<%=new Integer(pageSize)%>"/>
			<jsp:include page="../../public/pageTurn1.jsp">
				<jsp:param name="colspan" value="10"/>
				<jsp:param name="pageIndex" value="${pageIndex}"/>
				<jsp:param name="totalCnt" value="${totalCnt}"/>
				<jsp:param name="pageCnt" value="${pageCnt}"/>
				<jsp:param name="pageSize" value="${pageSize}"/>
			</jsp:include>			
		</table>
     <table border="0" cellspacing="0" cellpadding="0" width="100%">
          <tr height="35">
          	<td width="40%"><div align="center">
            <!--<input type="button" onclick="printMulti(tb,'ck');" class="btn" value="打印">-->&nbsp;&nbsp;
            <c:if test="${CONVIEWNOT==1}">
            </c:if>
            </div>
        <input type="hidden" name="opt">
		<input type="hidden" name="queryOpt">
        <input type="hidden" name="lpFlag"> 
		<input type="hidden" name="contractIds">
            </td>
          </tr>
     </table>
	 <div style="display:none"><textarea name="printContent" id="printContent"></textarea></div>
</form>
<%
   rs.close();
   ps.close();
   }catch(Exception e){
       e.printStackTrace();
   }finally{
	   if(rs!=null){try{rs.close();}catch(Exception ex){}rs=null;}
       if(ps!=null){try{ps.close();}catch(Exception ex){}ps=null;}
	   try{
           conn.close();
       }catch (Exception e){}
           conn = null;
   }
%>
</body>
</html>
<%@ include file="../../public/pageTurn2.jsp"%>

<SCRIPT LANGUAGE="JavaScript">

function winOpen(url)
{
	window.showModalDialog(url,"", "dialogWidth=420px; dialogHeight=280px; status=yes;scroll=yes;help=no;"); 
}
function winOpen1(url)
{
	window.showModalDialog(url,"", "dialogWidth=420px; dialogHeight=400px; status=yes;scroll=yes;help=no;"); 
}
//修改会员用户信息
function editUserInfo(id,bargainflag){
	if(parseInt(bargainflag)==1){
	  alert("商品已成交,不能修改!");
	}else{
		var a=window.open("curCommodityMod.jsp?flag=query&code="+id,"_blank","width=400,height=300,scrollbars=yes;");
  }
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
 

//点击菜单中的板块栏做查询时
function clickMenu(v){
	frm.partitionID.value=v;
	frm.idx.value=v;
	frm.action="curConPrintList.jsp";
    frm.submit();	
}


//查看合同内容
function viewContract(contractID){
	  //window.open("printContract.jsp?contractID="+contractID,"_blank","width=650,height=600,scrollbars=yes");
	  //var result=PopWindow("printConfirm.jsp?contractID="+contractID+"",650,600);
	  window.showModalDialog("printConfirm.jsp?contractID="+contractID,"", "dialogWidth=650px; dialogHeight=600px; status=yes;scroll=yes;help=no;");
}

//打印多份合同
function printMulti(tableList,checkName){
  if(isSelNothing(tableList,checkName) == -1)
	{
		alert ( "没有可以操作的数据！" );
	}
	else if(isSelNothing(tableList,checkName))
	{
		alert ( "请选择需要操作的数据！" );
	}else{
  var tab=document.all.tb;
  var len=tab.rows.length;
  var basePath="<%=request.getContextPath()%>/vendue";
  var maxCheck=0;
  var mark=0;
  var contractIds="";
  for(var i=1;i<len-3;i++){//算出选了多少条记录
  	if(tab.rows[i].cells(1).children(0).checked){
      maxCheck++;
    }
  }
  if(maxCheck>10){
      alert("一次打印合同条数不能超过10条!");
      return false;
  }
  for(var i=1;i<len-3;i++){
  	if(tab.rows[i].cells(1).children(0).checked){
	   contractIds=contractIds+tab.rows[i].cells(1).children(0).value+",";
       mark++;
    }
  }
    frm.opt.value="print";
	  frm.contractIds.value=contractIds;
	  frm.submit();
  }
}

</SCRIPT>
