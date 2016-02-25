<%@ page contentType="text/html;charset=GBK" %>
<html xmlns:MEBS>
<%java.sql.Date sqlDate=new java.sql.Date(System.currentTimeMillis());%>
<%@ include file="../../globalDef.jsp"%> 

<fmt:requestEncoding value="GBK" />
<!--操作-->
<c:if test='${not empty param.opt}'>
 <c:set var="modifyTime" value="<%=sqlDate%>"/>
 <c:set var="ids" value=""/>
  <c:if test="${param.opt=='删除'}">
  	<c:forEach var="row" items="${paramValues.ck}">
	  <c:set var="ids" value="${ids}'${row}',"/>		
 	  </c:forEach>
 	  <c:set var="ids" value="${ids}''"/>
 	  <db:delete table="v_syspartition" where="partitionid in (${ids})"/>
 	    	 <script language="javascript">
 	    	 	  <!--
 	  		 	  alert("模块删除成功!");
 	  		 	  //-->
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
	<title>系统板块浏览</title>
</head>
<body>
<form name=frm action="" method="post">
		<fieldset width="100%">
		<legend>系统板块配置查询</legend>
		<span>
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
			  <tr height="35">
            	<td align="right"> 板块编号 ：</td>
                <td align="left">
                	<input name="partitionID" type="text" class="text" style="width: 150px;" value="${param.partitionID}">
                </td>
                <td align="right"> 板块描述 ：</td>
                <td align="left"><input name="description" type="text" class="text" style="width: 150px;" value="${param.description}"></td>
				        <td align="left">
                <input type="button" onclick="initIdx();" class="btn" value="查询">&nbsp;
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
			    <td class="panel_tHead_MB">板块编号 </td>
			    <td class="panel_tHead_MB">交易引擎对象的java实现类的名称</td>
			    <td class="panel_tHead_MB">行情对象的java实现类的名称 </td>
			    <td class="panel_tHead_MB">委托响应的java实现类的名称</td>
			    <td class="panel_tHead_MB">加载配置数据</td>
			    <td class="panel_tHead_MB">板块描述</td>
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
					  <c:if test="${row.validFlag==0}">否</c:if>	
					  <c:if test="${row.validFlag==1}">是</c:if>	
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
			<!--计算总页数-->
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
			  <input type="button" onclick="gotoUrl('sysPartitionAdd.jsp')" class="btn" value="添加">&nbsp;&nbsp;
  		  <input type="button" onclick="return deleteRec(frm,tb,'ck');" class="btn" value="删除">&nbsp;&nbsp;
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
//修改板块信息
function editUserInfo(id){
	window.open("sysPartitionMod.jsp?flag=query&id="+id,"_blank","width=500,height=350,scrollbars=yes;");
  //var a=openDialog("managerMod.jsp?flag=query&userId="+userCode,"_blank","500","600");
  //window.location="managerList.jsp";
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
 
//添加商品

function addRec(frm_delete,tableList,checkName)
{
	if(isSelNothing(tableList,checkName) == -1)
	{
		alert ( "没有可以操作的数据！" );
		return false;
	}
	if(isSelNothing(tableList,checkName))
	{
		alert ( "请选择需要操作的数据！" );
		return false;
	}
	if(confirm("是否以流拍加入到当前交易商品列表？"))
	{ 
		if(confirm("确实要操作这些数据?")){
		  frm.opt.value="添加到当前交易商品";
		  frm.lpFlag.value="1";
	    frm.submit();
	  }else{
	    return false;	
	  }
	}
	else
	{ if(confirm("确实要操作这些数据?")){
		  frm.opt.value="添加到当前交易商品";
		  frm.lpFlag.value="0";
		  frm.submit();
	  }
	  else{
	    return false;	
	  }
	}
}
</SCRIPT>