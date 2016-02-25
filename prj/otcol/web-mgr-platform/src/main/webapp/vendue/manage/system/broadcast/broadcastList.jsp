<%@ page contentType="text/html;charset=GBK" %>
<html xmlns:MEBS>
<%@ include file="../../globalDef.jsp"%> 

<!--操作-->
<%
    java.sql.Date sqlDate=new java.sql.Date(System.currentTimeMillis());
%>
<c:if test='${not empty param.opt}'>
 <c:set var="modifyTime" value="<%=sqlDate%>"/>
 <c:set var="ids" value=""/>
   <c:if test="${param.opt=='delete'}">
   	  <c:forEach var="row" items="${paramValues.ck}">
	    <c:set var="ids" value="${ids}'${row}',"/>		
    </c:forEach>
   <c:set var="ids" value="${ids}''"/>
   <db:delete table="v_broadcast" where="id in (${ids})"/>

   <script language="javascript">
 	  alert("广播信息删除成功!");
   </script>
</c:if>
</c:if>

<!--变量定义-->
<c:set var="sqlFilter" value=""/>
<!--分页参数定义-->
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
<!--查询条件-->
<!--标题-->
<c:choose> 
 <c:when test="${empty param.title}"> 
   <c:set var="title" value=""/> 
 </c:when> 
 <c:otherwise> 
   <c:set var="title" value="${param.title}"/> 
   <c:set var="sqlFilter" value="${sqlFilter} and title like '${title}' "/>
 </c:otherwise> 
</c:choose>
<!--发送时间-->
<c:choose> 
 <c:when test="${empty param.sendtimeStart}"> 
   <c:set var="sendtimeStart" value=""/> 
 </c:when> 
 <c:otherwise> 
   <c:set var="sendtimeStart" value="${param.sendtimeStart}"/> 
   <c:set var="sqlFilter" value="${sqlFilter} and sendtime >=to_date('${sendtimeStart}','yyyy-mm-dd') "/>
 </c:otherwise> 
</c:choose>
<c:choose> 
 <c:when test="${empty param.sendtimeEnd}"> 
   <c:set var="sendtimeEnd" value=""/> 
 </c:when> 
 <c:otherwise> 
   <c:set var="sendtimeEnd" value="${param.sendtimeEnd}"/> 
   <c:set var="sqlFilter" value="${sqlFilter} and sendtime <=to_date('${sendtimeEnd}','yyyy-mm-dd') "/>
 </c:otherwise> 
</c:choose>
  <head>
	<title>广播消息浏览</title>
</head>
<body>
<form name=frm action="" method="post">
		<fieldset width="100%">
		<legend>广播消息查询</legend>
		<span>
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
			  <tr height="35">
            	<td align="right"> 标题：</td>
                <td align="left">
                	<input name="title" type="text" class="text" style="width: 150px;" value="${param.title}" onkeypress="notSpace()">
                </td>
                <td align="right">发送开始时间：</td>
                <td align="left">
                	<MEBS:calendar eltID="sendtimeStart" eltName="sendtimeStart" eltCSS="date" eltStyle="width:80px" eltImgPath="${CONTEXT}/common/skinstyle/default/common/commoncss/images/" eltValue="${sendtimeStart}"/>&nbsp;至
                	<MEBS:calendar eltID="sendtimeEnd" eltName="sendtimeEnd" eltCSS="date" eltStyle="width:80px" eltImgPath="${CONTEXT}/common/skinstyle/default/common/commoncss/images/" eltValue="${sendtimeEnd}"/>
                </td>
                <td align="left" colspan="4">
                <input type="button" onclick="initIdx();" class="btn" value="查询">&nbsp;	
                <input type="button" onclick="freset();" class="btn" value="重置">&nbsp;	
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
			    <td class="panel_tHead_MB">标题 </td>
			    <td class="panel_tHead_MB">作者 </td>
			    <td class="panel_tHead_MB">类别</td>
			    <td class="panel_tHead_MB">发送开始时间 </td>
			    <td class="panel_tHead_MB">发送结束时间 </td>
			    <td class="panel_tHead_MB">创建时间</td>
			    <td class="panel_tHead_MB">信息修改时间</td>
					<td class="panel_tHead_RB">&nbsp;</td>
				</tr>
			</tHead>
			<tBody>
			    <db:selectPG var="row" table="v_broadcast" columns="<%=COLS_BROADCAST%>" orderBy="createtime desc" pageIndex="${pageIndex}" pageSize="${pageSize}" where="1=1${sqlFilter}">
				  <tr onclick="selectTr();" align=center height="25">
		  			<td class="panel_tBody_LB">&nbsp;</td>
		  			<td class="underLine" align=left><input name="ck" type="checkbox" value='${row.id}'></td>
		  			<td class="underLine">
		  			<a href="javascript:editUserInfo(${row.id});" class="normal">${row.title}</a>
		  			</td>
		  			<td class="underLine">
		  			<c:choose>
		  			<c:when test="${not empty row.author}">
		  				${row.author}
		  			</c:when>
		  			<c:otherwise>
		  				 &nbsp;
		  			</c:otherwise>
		  			</c:choose>
		  			</td>
		  			<td class="underLine">
					  <c:choose>
		  			<c:when test="${not empty row.type}">
		  			  ${row.type}
		  			</c:when>
		  			<c:otherwise>
		  				&nbsp;
		  		  </c:otherwise>
		  		  </c:choose>
					  </td>
					  <td class="underLine">
					    <fmt:formatDate value="${row.sendtime}" pattern="<%=DATEPATTERN%>"/>
					  </td>
					  <td class="underLine">
					  <c:choose>
		  			  <c:when test="${not empty row.endtime}">
					  <fmt:formatDate value="${row.endtime}" pattern="<%=DATEPATTERN%>"/>
					  </c:when>
					  <c:otherwise>
		  				&nbsp;
		  		      </c:otherwise>
		  		      </c:choose>
					  </td>
					  <td class="underLine">
					    <fmt:formatDate value="${row.createtime}" pattern="<%=DATEPATTERN%>"/>
		  		      </td>
					  <td class="underLine">
					  <c:choose>
		  			  <c:when test="${not empty row.updatetime}">
		  			      <fmt:formatDate value="${row.updatetime}" pattern="<%=DATEPATTERN%>"/></td>
		  			  </c:when>
					  <c:otherwise>
		  				&nbsp;
		  		      </c:otherwise>
		  		      </c:choose>
		  			  <td class="panel_tBody_RB">&nbsp;</td>
		  		</tr>
				</db:selectPG>
		  	</tBody>
			<!--计算总页数-->
			<db:select var="c" table="v_broadcast" columns="count(*) as n" where="1=1${sqlFilter}">
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
				<jsp:param name="colspan" value="8"/>
				<jsp:param name="pageIndex" value="${pageIndex}"/>
				<jsp:param name="totalCnt" value="${totalCnt}"/>
				<jsp:param name="pageCnt" value="${pageCnt}"/>
				<jsp:param name="pageSize" value="${pageSize}"/>
			</jsp:include>			
		</table>
     <table border="0" cellspacing="0" cellpadding="0" width="100%">
          <tr height="35">
          	<td width="40%"><div align="center">
            <input type="button" onclick="gotoUrl('broadcastAdd.jsp')" class="btn" value="添加">&nbsp;&nbsp;
            <input type="button" onclick="return deleteRec(frm,tb,'ck');" class="btn" value="删除">
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
function winOpen(url)
{
	window.showModalDialog(url,"", "dialogWidth=420px; dialogHeight=280px; status=yes;scroll=yes;help=no;"); 
}
function winOpen1(url)
{
	window.showModalDialog(url,"", "dialogWidth=420px; dialogHeight=400px; status=yes;scroll=yes;help=no;"); 
}
//修改会员用户信息
function editUserInfo(id){
	var result=window.showModalDialog("broadcastMod.jsp?id="+id,"", "dialogWidth=500px; dialogHeight=450px; status=yes;scroll=yes;help=no;");
	if(result){
			frm.action="broadcastList.jsp";
			frm.submit();
		}
}
function freset(){
	frm.title.value='';
	frm.sendtimeStart.value='';
	frm.sendtimeEnd.value='';
	frm.submit();
}
//控制不输入空格
function notSpace()
{
  if(event.keyCode == 32)
  {
    event.returnValue=false;
  }
}
</SCRIPT>