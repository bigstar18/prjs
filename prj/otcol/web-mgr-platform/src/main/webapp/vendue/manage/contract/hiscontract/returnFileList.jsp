<%@ page contentType="text/html;charset=GBK" %>
<html xmlns:MEBS>
<%java.sql.Date sqlDate=new java.sql.Date(System.currentTimeMillis());%>
<%@ include file="/vendue/manage/globalDef.jsp"%> 
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
<%  //xieying
try
{

%>
<!--����-->
<c:if test='${not empty param.opt}'>
<c:if test="${param.opt=='print'}"><!--��ͬ��ݴ�ӡ-->
  <%
	  try{
          String contractIds=request.getParameter("contractIds");
		  if(ManaUtil.checkStr(contractIds)){
		      boolean printFlag=false;
			  String contractID=null;
		      contractIds=contractIds+"''";
	%>
	   <script language="javascript">
	       basePath="<%=request.getContextPath()%>/vendue";
	       contractIds="<%=contractIds%>";
		   sUrl =basePath+"/manage/public/printHisContract.jsp?contractIds="+contractIds+"";
           //openDialog(sUrl,window,300,300);
		   window.open(sUrl,"d","toolbar=no,location=no,resizable=no,scrollbars=yes,menubar=no,width=300,height=300,top=50,left=100");
	   </script>  
 <%
		  }
	  }catch(Exception e){
	      e.printStackTrace();
	  }
      finally{
      }
 %>
  </c:if>
</c:if>
<!--��������-->
<c:set var="sqlFilter" value=""/>
<!--��ҳ��������-->
<%
  int pageIndex=0;
  int pageSize=0;
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
  	 <%pageSize=PAGESIZE;%>
  </c:when>
  <c:otherwise>
  	 <c:set var="pageSize" value="${param.pageSize}"/>
  	 <%pageSize=Integer.parseInt(request.getParameter("pageSize"));%>
  </c:otherwise>
</c:choose>
  <head>
	<title>�鵵��ͬ</title>
</head>
<body onload="init();">
<%
  //�����������ݶ���
  Connection conn = null;
  PreparedStatement ps = null;
  ResultSet rs = null;
  int cnt = 0;
  int totalCnt=0;//�ܼ�¼��
  int pageCnt=0;//��ҳ��
  try{
    Context initContext = new InitialContext();
    Context envContext  = (Context)initContext.lookup("java:/comp/env");
    DataSource ds = (DataSource)envContext.lookup(JNDI);
    conn = ds.getConnection();
    StringBuffer sql=new StringBuffer();
    String tempPartiId=null;
%>
<form name=frm action="" method="post">
		<input type="hidden" id="orderField" name="orderField" value="<%=orderField %>">
		<input type="hidden" id="orderDesc" name="orderDesc" value="<%=orderType %>">
	<!--Ĭ��ѡ�а��-->
	<c:set var="defaultSec" value=""/>
	<c:set var="allPartition" value=""/>
<%
	  
	  String tempfilter=null;
	  String allpartition="";
	  //String userpar=session.getAttribute("USERPAR").toString();
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
      //if(userpar.equals(allpartition.trim())){
	      tempfilter=" and validflag=1 and rownum=1";
	  //}else{
	      //tempfilter=" and validflag=1 and partitionid in ("+userpar+"'') and rownum=1";
	  //}
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
	  <jsp:include page="/vendue/manage/public/menu.jsp">
	  	<jsp:param name="partitionID" value="${defaultSec}"/>
	  	<jsp:param name="idx" value="${defaultSec}"/>
		<jsp:param name="allPartition" value="${allPartition}"/>
	  </jsp:include>
<%
	  }else{		  
%>
	  <jsp:include page="/vendue/manage/public/menu.jsp">
	      <jsp:param name="allPartition" value="${allPartition}"/>
	  </jsp:include>
<%
      }	
%>
<!--��ѯ����-->
<%
  String filter="";
%>
<!--��Ʒ��-->
<c:choose> 
 <c:when test="${empty param.code}"> 
 <c:set var="code" value=""/> 
 </c:when> 
 <c:otherwise>
 <%filter=filter+"and code like '"+request.getParameter("code")+"'";%>
 </c:otherwise> 
</c:choose>
<!--��ͬ��-->
<c:choose> 
 <c:when test="${empty param.contractID}"> 
 </c:when> 
 <c:otherwise> 
 <%filter=filter+" and contractid like '"+request.getParameter("contractID")+"'";%>
 </c:otherwise> 
</c:choose>
<!--�������-->
<c:choose> 
 <c:when test="${empty param.blUserId}"> 
 </c:when> 
 <c:otherwise>
   <%filter=filter+" and bluserid like  '"+request.getParameter("blUserId")+"'";%>
 </c:otherwise> 
</c:choose>
<!--�򷽱��-->
<c:choose> 
<c:when test="${empty param.userId}">
</c:when> 
<c:otherwise>
<%filter=filter+" and userid like '"+request.getParameter("userId")+"'";%>
</c:otherwise> 
</c:choose>
<!--�ɽ�����-->
<c:choose> 
 <c:when test="${empty param.tradeDate}"><!--�ж��Ƿ��ǵ�һ�ε�����ҳ��,����ǵ�һ�ε�����ҳ����Ĭ��Ϊ�����ʱ��-->
   <c:choose>
   <c:when test="${not empty param.funcflg}">
   <%filter=filter+" and tradedate>=to_date('"+sqlDate.toString()+"  00:00:00','yyyy-mm-dd hh24:mi:ss') and  tradedate<=to_date('"+sqlDate.toString()+" 23:59:59','yyyy-mm-dd  hh24:mi:ss') ";%>
   </c:when> 
   <c:otherwise>
   <c:set var="tradeDate" value=""/>
   </c:otherwise> 
   </c:choose>
 </c:when> 
 <c:otherwise>
 <%filter=filter+" and tradedate>=to_date('"+request.getParameter("tradeDate")+"  00:00:00','yyyy-mm-dd hh24:mi:ss') and  tradedate<=to_date('"+request.getParameter("tradeDate")+" 23:59:59','yyyy-mm-dd hh24:mi:ss') ";%>
 </c:otherwise> 
</c:choose>
<!--����ѯ����-->
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
	  <br>
		<fieldset width="100%">
		<legend>�鵵��ͬ��ѯ</legend>
		<span>
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
			  <tr height="35">
            	<td align="right" class="tdstyle"> ��ĺţ�</td>
                <td align="left">
                	<input name="code" type="text" class="text" style="width: 100px;" value="${param.code}"onkeypress="notSpace()">
                </td>
                <td align="right" class="tdstyle">��ͬ�ţ�</td>
                <td align="left">
                <input name="contractID" type="text" class="text" style="width: 100px;" value="${param.contractID}"onkeypress="notSpace()">
                </td>
                <td align="right" class="tdstyle"> �򷽱�ţ�</td>
                <td align="left" class="tdstyle">
                <input name="userId" type="text" class="text" style="width: 100px;" value="${param.userId}"onkeypress="notSpace()">
                </td>
              </tr>
              <tr height="35">
              	<td align="right" class="tdstyle"> ������ţ�</td>
                <td align="left">
                <input name="blUserId" type="text" class="text" style="width: 100px;" value="${param.blUserId}"onkeypress="notSpace()">
                </td>
                <td align="right" class="tdstyle"> �ɽ����ڣ�</td>
                <td align="left">
				<!--�����ѯʱ¼��ʱ��Ϊ��,��Ĭ��Ϊ�����ʱ��-->
				<c:set var="tradeDateAgr" value=""/>
				<c:choose>
				<c:when test="${not empty param.funcflg}">
				<c:set var="tradeDateAgr" value="<%=sqlDate.toString()%>"/>
				</c:when>
				<c:otherwise>
				<c:set var="tradeDateAgr" value="${param.tradeDate}"/>
				</c:otherwise>
				</c:choose>
                <MEBS:calendar eltID="tradeDate" eltName="tradeDate" eltCSS="date" eltStyle="width:80px" eltImgPath="<%=skinPath%>/images/" eltValue="${tradeDateAgr}"/>
                </td>
                <td align="right">
                <input type="button" onclick="queryBtn('returnFileList.jsp');" class="btn" value="��ѯ">
                        <!-- add by lizhenli 2011-12-08 �������ù��� -->
            	<input type="button" onclick="resetForm();" class="btn" value="����">&nbsp;
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
				<td class="panel_tHead_MB" align=left>
				<input type="checkbox" id="checkAll" onclick="selectAll(tb,'ck')">
				</td>
				<td class="panel_tHead_MB" abbr="contractid">��ͬ��</td>
			    <td class="panel_tHead_MB" abbr="code">��ĺ�</td>
				<td class="panel_tHead_MB" abbr="userid">�򷽱��</td>
				<td class="panel_tHead_MB" abbr="bluserid">�������</td>
				<td class="panel_tHead_MB" abbr="str4">Ʒ��</td>
			    <td class="panel_tHead_MB" abbr="price">����</td>
				<td class="panel_tHead_MB" abbr="amount">����</td>
			    <td class="panel_tHead_MB" abbr="aa">�ɽ����</td>
			    <td class="panel_tHead_MB" abbr="tradedate">�ɽ�����</td>
			    <td class="panel_tHead_MB" abbr="status">״̬</td>
			    <td class="panel_tHead_MB">��ϸ</td>
			    <td class="panel_tHead_MB">�����¼</td>
				<td style="display:none;"></td>
				<td class="panel_tHead_RB">&nbsp;</td>
				</tr>
			</tHead>
			<tBody>
				 
		<%
		   String startRow=String.valueOf((pageIndex-1)*pageSize+1);
		   String endRow=String.valueOf(pageIndex*pageSize);
		   sql=new StringBuffer();
           sql.append("select (price*amount*tradeunit) aa,tradepartition,b_lastBail ,s_lastBail ,code,contractid,price,amount,userid,tradedate,");
		   sql.append("section,b_bail,b_poundage,s_bail,s_poundage,status,lastamount,id,str4,str2,str6,str7,str8,tradeunit,trademode,bluserid,");
		   sql.append("rown from (select tradepartition,b_lastBail ,s_lastBail,code,contractid,price,amount,userid,tradedate");
		   sql.append(",section,b_bail,b_poundage,s_bail,s_poundage,status,lastamount,id,str4,str2,str6,str7,str8,tradeunit,trademode,bluserid,");
		   sql.append("rownum rown from (select tradepartition,b_lastBail ,s_lastBail ,code,contractid,price,amount,userid,");
		   sql.append("tradedate,section,b_bail,b_poundage,s_bail,s_poundage,status,lastamount,id,str4,str2,str6,str7,str8,tradeunit,trademode");
		   sql.append(",bluserid,str19 from( select tradepartition,b_lastBail,s_lastBail,code,contractid,price,amount,tradedate,");
		   sql.append("section,b_bail,b_poundage,s_bail,s_poundage,status,lastAmount,id,str4,str2,str6,str7,str8,tradeunit,case when trademode=0");
		   sql.append(" then userid else bluserid end userid,case when trademode=0 then bluserid else userid");
		   sql.append(" end bluserid,trademode,str19 from ( select u1.tradepartition,u1.b_lastBail,u1.s_lastBail,u1.code,");
		   sql.append("u1.contractid,u1.price,u1.amount,u1.userid,u1.tradedate,u1.section,u1.b_bail,");
		   sql.append("u1.b_poundage,u1.s_bail,u1.s_poundage,u1.status,u1.lastAmount,u3.id,u4.str4,u4.str2,u4.str6,u4.str7,u4.str8,u3.tradeunit,");
		   sql.append("u3.trademode,u3.userid bluserid,u4.str19 from v_hisbargain u1,v_commodity u3,v_commext u4 where u1.commodityid=");
		   sql.append("u3.id and u3.id=u4.commid  and  u1.status=2 )) where 1=1 "+filter+" )) where rown");
		   sql.append(" between "+startRow+" and "+endRow+" ");
		   sql.append(" order by "+a);
           ps = conn.prepareStatement(sql.toString());
	       rs=ps.executeQuery();
	       while(rs.next()){
	         String code=rs.getString("code");
	         String tradeDate=rs.getString("tradedate");
	         String contractID=rs.getString("contractid");
	         String commodityID=rs.getString("id");
          %>
		  <input type="hidden" name="tradeunit" id="tradeunit<%=commodityID%>" value="<%=rs.getDouble("tradeunit")%>">
				    <tr onclick="selectTr();" align=center height="25">
		  		    <td class="panel_tBody_LB">&nbsp;</td>
		  			<td class="underLine" align=left><input name="ck" type="checkbox" value='<%=commodityID%>'></td>
		  			<td class="underLineExt"><a href="javascript:viewContract('<%=contractID%>')" class="normal"><%=contractID%><!--</a>--></td>
		  			<td class="underLineExt"><%=code%></td>
		  			<td class="underLineExt"><%=rs.getString("userid")%></td>
					<td class="underLineExt"><%=rs.getString("bluserid")%></td>
					<td class="underLineExt"><%=replaceNull(rs.getString("str4"))%></td>
					<td class="underLineExt" style="text-align:right;">
					<%=disDouble(rs.getDouble("price"))%>
					</td>
					<td class="underLineExt" style="text-align:right;">
					<%=disInt(rs.getDouble("amount"))%>
					</td>
					<td class="underLineExt" style="text-align:right;">
					<%=disDouble(rs.getDouble("amount")*rs.getDouble("price")*rs.getDouble("tradeunit"))%>
					</td>
					<td class="underLineExt"><%=tradeDate.substring(0,10)%></td>
					<td class="underLineExt">
					<a href="javascript:controlStatus('<%=commodityID%>','${marketId}','<%=rs.getString("str6")%>')" class="normal">
					<%
			        int status=rs.getInt("status");
		            if(status==2){
					%>
					    �鵵
					<%
					}
					%>
					</a>
					</td>
				    <td class="underLine"><a href="javascript:viewDetail('<%=contractID%>','<%=rs.getString("str6")%>')" class="normal">�鿴</a></td>
					<td class="underLine"><a href="javascript:liftGoods('<%=commodityID%>','${marketId}','<%=rs.getString("str6")%>')" class="normal">�鿴</a></td>
					<td class="underLine" style="display:none" name="contractContent" id="contractContent">
			        </td>
				    <td class="underLine" style="display:none">
				    <input type="hidden" value="<%=contractID%>">
				    </td>
		  		    <td class="panel_tBody_RB">&nbsp;</td>
		  		    </tr>
	     <%
			    }
			    //������ҳ��
                sql=new StringBuffer();
				sql.append("select count(contractid) n from (select tradepartition,b_lastBail ,s_lastBail,code,contractid,");
				sql.append("price,amount,userid,tradedate,section,b_bail,b_poundage,s_bail,s_poundage,status,lastamount,id,str4,");
				sql.append("str2,tradeunit,trademode,bluserid,str19 from ( select tradepartition,b_lastBail,s_lastBail,code,");
				sql.append("contractid,price,amount,tradedate,section,b_bail,b_poundage,s_bail,s_poundage,status,lastAmount,id,str4");
				sql.append(",str2,tradeunit,case when trademode=0 then userid else bluserid end userid,case when ");
				sql.append("trademode=0 then bluserid else userid end bluserid,trademode,str19 from ( select ");
				sql.append("u1.tradepartition,u1.b_lastBail,u1.s_lastBail,u1.code,u1.contractid,u1.price,u1.amount,u1.userid,");
				sql.append("u1.tradedate,u1.section,u1.b_bail,u1.b_poundage,u1.s_bail,u1.s_poundage,u1.status,u1.lastAmount,u3.id");
				sql.append(",u4.str4,u4.str2,u3.tradeunit,u3.trademode,u3.userid bluserid,u4.str19 from v_hisbargain u1,v_commodity");
				sql.append(" u3,v_commext u4 where u1.commodityid=u3.id and u3.id=u4.commid and u1.status=2 ))");
				sql.append(" where 1=1 "+filter+" )");
				sql.append(" order by "+a);
				ps=conn.prepareStatement(sql.toString());
				rs=ps.executeQuery();
				if(rs.next()){
				    totalCnt=rs.getInt("n");
				}
				rs.close();
				ps.close();
				pageSize=Integer.parseInt(pageContext.getAttribute("pageSize").toString());
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
		  	</tBody>
			<c:set var="pageIndex" value="<%=new Integer(pageIndex)%>"/>
            <c:set var="totalCnt" value="<%=new Integer(totalCnt)%>"/>
			<c:set var="pageCnt" value="<%=new Integer(pageCnt)%>"/>
			<c:set var="pageSize" value="<%=new Integer(pageSize)%>"/>
			<jsp:include page="/vendue/manage/public/pageTurn1.jsp">
				<jsp:param name="colspan" value="13"/>
				<jsp:param name="pageIndex" value="${pageIndex}"/>
				<jsp:param name="totalCnt" value="${totalCnt}"/>
				<jsp:param name="pageCnt" value="${pageCnt}"/>
				<jsp:param name="pageSize" value="${pageSize}"/>
			</jsp:include>			
		</table>
        <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr height="35"  align="right">
        <td width="40%"  align="right"><div align="right">
        <input type="button" onclick="printMulti(tb,'ck');" class="btn" value="��ӡ">
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        </div>
        <input type="hidden" name="opt">
        <input type="hidden" name="lpFlag">
	    <input type="hidden" name="marketId" value="${marketId}">
		<input type="hidden" name="contractIds">
        </td>
        </tr>
     </table>
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
	     }%>
</body>
</html>
<%@ include file="/vendue/manage/public/pageTurn2.jsp"%>
<SCRIPT LANGUAGE="JavaScript">
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
		result=PopWindow("curCommodityMod.jsp?flag=query&code="+id,400,300);
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

//����˵��еİ��������ѯʱ
function clickMenu(v){
	frm.partitionID.value=v;
	frm.idx.value=v;
	frm.action="returnFileList.jsp";
  frm.submit();	
}

//ָ�����׽�
function addSection(frm_delete,tableList,checkName){
	if(isSelNothing(tableList,checkName) == -1)
	{
		alert ( "û�п��Բ��������ݣ�" );
		return false;
	}
	else if(isSelNothing(tableList,checkName))
	{
		alert ( "��ѡ����Ҫ���������ݣ�" );
		return false;
	}else{
		var ids=getValues(tableList,checkName);
		var a=openDialog("addSection.jsp?ids="+ids+"","_blank","300","200");
    if(1==a)
      window.location.reload();
	}
}

//�鿴��ͬ����
function viewContract(contractID){
    result=PopWindow("retFPrintConfirm.jsp?contractID="+contractID+"",650,600);
}

//�鿴��ϸ

function viewDetail(contractID,str6){
	 url="detail.jsp?contractID="+contractID+"&str6="+str6;
	 PopWindow(url,900,600);
}

//�����¼
function liftGoods(commodityID,marketId,str6){
	 result=PopWindow("retFileLiftGoods.jsp?commodityID="+commodityID+"&marketId="+marketId+"&str6="+str6,650,450);
}

//��ݴ�ӡ
function printMulti(tableList,checkName){
  if(isSelNothing(tableList,checkName) == -1)
	{
		alert ( "û�п��Բ��������ݣ�" );
	}
	else if(isSelNothing(tableList,checkName))
	{
		alert ( "��ѡ����Ҫ���������ݣ�" );
	}else{
  var tab=document.all.tb;
  var len=tab.rows.length;
  var basePath="<%=request.getContextPath()%>/vendue";
  var maxCheck=0;
  var mark=0;
  var contractIds="";
  for(var i=1;i<len-3;i++){
  	if(tab.rows[i].cells(1).children(0).checked){
      maxCheck++;
    }
  }
  if(maxCheck>10){
      alert("һ�δ�ӡ��ͬ�������ܳ���10��!");
      return false;
  }
  for(var i=1;i<len-3;i++){
  	if(tab.rows[i].cells(1).children(0).checked){
		contractIds=contractIds+tab.rows[i].cells(15).children(0).value+",";
        mark++;
    }
  }	  
	  frm.opt.value="print";
	  frm.contractIds.value=contractIds;
	  frm.submit();
  }
}

//��ͬ״̬����
function controlStatus(commodityID,marketId,str6){
	var id = "tradeunit"+commodityID;
	var tradeunit = document.getElementById(id).value;
	//alert("retFileControlStatus.jsp?commodityID="+commodityID+"&marketId="+marketId+"&tradeunit="+tradeunit+"");
	 result=PopWindow("retFileControlStatus.jsp?commodityID="+commodityID+"&marketId="+marketId+"&tradeunit="+tradeunit+"&str6="+str6,650,600);
}
</SCRIPT>
<%
}
catch(Exception e)
{
	System.out.println(e.toString());
}
%>