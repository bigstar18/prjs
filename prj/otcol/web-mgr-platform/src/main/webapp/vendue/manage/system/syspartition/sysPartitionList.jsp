<%@ page contentType="text/html;charset=GBK" %>
<html xmlns:MEBS>

<%@ include file="../../globalDef.jsp"%> 

<%
    java.sql.Date sqlDate=new java.sql.Date(System.currentTimeMillis());
%>
<!--操作-->
<c:if test='${not empty param.opt}'>
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
 <c:when test="${empty param.description}"> 
   <c:set var="description" value=""/>
 </c:when> 
 <c:otherwise> 
   <c:set var="description" value="${param.description}"/> 
   <c:set var="sqlFilter" value="${sqlFilter} and description like '${description}'"/>
 </c:otherwise> 
</c:choose>
<head>
	<title>商品</title>
</head>
<body>
<form name=frm action="" method="post">
		<fieldset width="100%">
		<legend>系统板块配置查询</legend>
		<span>
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
			  <tr height="35">
                <td align="right" width="15%"> 板块名称：</td>
                <td align="left" width="50%"><input name="description" type="text" class="text" style="width: 150px;" value="${param.description}"onkeypress="onlyNumberAndCharInput()" maxlength="16"></td>
				<td align="left">
                <input type="button" onclick="initIdx();" class="btn" value="查询">&nbsp;
               	<!-- add by yangpei 2011-11-22 增加重置功能 -->
            	<input type="button" onclick="resetForm();" class="btn" value="重置">&nbsp;
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
			    <td class="panel_tHead_MB">板块名称</td>
				<td class="panel_tHead_MB">交易模式</td>
			    <td class="panel_tHead_MB">加载配置数据</td>
			    <td class="panel_tHead_MB">修改</td>
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
				  <c:if test="${row.trademode==0}">竞买</c:if>	
				  <c:if test="${row.trademode==1}">竞卖</c:if>	
				  <c:if test="${row.trademode==2}">招标</c:if>
				  </td>
				  <td class="underLine">
				  <c:if test="${row.validFlag==0}">否</c:if>	
				  <c:if test="${row.validFlag==1}">是</c:if>	
				  </td>
				  <td class="underLine">
		  		  <a href="javascript:editUserInfo(${row.partitionid});" class="normal">修改</a></td>
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
//修改板块信息
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