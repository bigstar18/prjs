<%@ page contentType="text/html;charset=GBK" %>
<html>
  <head>
    <%@ include file="../public/headInc.jsp" %>
    <title>交易商列表</title>
    <script language="javascript" src="<%=basePath%>/public/jstools/tools.js"></script>
	<script language="javascript" src="<%=basePath%>/public/jstools/common.js"></script>
	<script language="JavaScript">
	    function init(){
			  changeOrder(sign);  
		}
		function createNew(){ 
			var returnValue = openDialog("<%=basePath%>/tradeuser/createTradeuser.jsp","_blank",500,650);
			if(returnValue)
				window.location.reload();
		}
		function doQuery(){
			frm_query.submit();
		}
		function resetForm(){
			frm_query.firmId.value = "";
			frm_query.userName.value = "";
			frm_query.enabled.value = "";
		}
		function editInfo(vCode){
			var returnValue = openDialog("<%=basePath%>/tradeuser/editTradeuser.jsp?firmId="+vCode ,"_blank",500,650);
			if(returnValue)
				window.location.reload();
		}
		function repairInfo(vCode){
			var returnValue = openDialog("<%=basePath%>/tradeuser/repairTradeuser.jsp?firmId="+vCode ,"_blank",500,350);
			if(returnValue)
				window.location.reload();
		}
	</script>
  </head>
  <body onload="init();">
  	<form id="frm_query" action="<%=basePath%>/tradeuserList.spr" method="post">
		<input type="hidden" name="orderField" value="${orderFiled}">
		<input type="hidden" name="orderDesc" value="${orderType}">
		<input type="hidden" name="pageSize" value="<c:out value="${pageInfo.pageSize}"/>">
		<input type="hidden" id="pageNo" name="pageNo">
		<fieldset width="95%">
			<legend>交易商查询</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="90%" height="35">
				<tr height="35">
					<td align="right"><%=FIRMID%>&nbsp;</td>
					<td align="left">
						<input id="firmId" name="_firmId[like]" value="<c:out value='${oldParams["firmId[like]"]}'/>" type=text class="text" style="width: 100px">
					</td>
					<td align="right"><%=FIRMNAME%>&nbsp;</td>
					<td align="left">
						<input id="name" name="_name[like]" value="<c:out value='${oldParams["name[like]"]}'/>" type=text  class="text" style="width: 100px">
					</td>
					
					<td align="left">
						<button type="button" class="smlbtn" onclick="doQuery();">查询</button>&nbsp;
						<button type="button" class="smlbtn" onclick="resetForm();">重置</button>&nbsp;
					</td>
				</tr>
			</table>
		</fieldset>
	</form>
	<form id="frm_delete" name="frm_delete" action="<%=basePath%>/tradeuserDelete.spr" method="post" targetType="hidden" callback="doQuery();">
	  <table id="tableList" border="0" cellspacing="0" cellpadding="0" width="100%" height="400">
  		<tHead>
  			<tr height="25">
  				<td class="panel_tHead_LB">&nbsp;</td>
				<td class="panel_tHead_MB"><input type="checkbox" id="checkAll" onclick="selectAll(tableList,'delCheck')"></td>
				<td class="panel_tHead_MB" abbr="firmId"><%=FIRMID %></td>
				<td class="panel_tHead_MB" abbr="name"><%=FIRMNAME%></td>
				<td class="panel_tHead_MB" abbr="fullname"><%=FULLNAME%></td>
				<td class="panel_tHead_MB" abbr="contactMan">联系人</td>
				<td class="panel_tHead_MB">修改密码</td>
				<td class="panel_tHead_RB">&nbsp;</td>
			</tr>
		</tHead>
		<tBody>
	  		<c:forEach items="${resultList}" var="result">
	  		<tr height="22" onclick="selectTr();">
	  			<td class="panel_tBody_LB">&nbsp;</td>
	  			<td class="underLine">
	  				<input name="delCheck" type="checkbox" value="<c:out value="${result.firmId}"/>">
	  			</td>
	  			<td class="underLine">
	  				<span onclick="editInfo('<c:out value="${result.firmId}"/>')" style="cursor:hand;color:blue">
	  				<c:out value="${result.firmId}"/></span></td>
	  			<td class="underLine"><c:out value="${result.name}"/></td>
	  			<td class="underLine"><c:out value="${result.fullname}"/>&nbsp;</td>
	  			<td class="underLine"><c:out value="${result.contactMan}"/>&nbsp;</td>
	  			<td class="underLine"><fmt:formatDate value="${result.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	  			<td class="underLine">
	  				<span onclick="repairInfo('<c:out value="${result.firmId}"/>')" style="cursor:hand;color:blue">
	  				<img src="<%=skinPath%>/ico/053753300.gif" width="15" height="15" /></span></td>
	  			<td class="panel_tBody_RB">&nbsp;</td>
	  		</tr>
	  		</c:forEach>
	  	</tBody>
	  	<tFoot>
			<tr height="100%">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td colspan="8">&nbsp;</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td class="pager" colspan="8">
					<%@ include file="../public/pagerInc.jsp" %>
				</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tFoot_LB">&nbsp;</td>
				<td class="panel_tFoot_MB" colspan="8"></td>
				<td class="panel_tFoot_RB">&nbsp;</td>
			</tr>
		</tFoot>
	</table>
	</from>
   	<table border="0" cellspacing="0" cellpadding="0" width="80%">
	    <tr height="35">
            <td><div align="right">
              <button class="smlbtn" type="button" onclick="createNew()">创建</button>&nbsp;&nbsp;
  			  <!-- <button class="smlbtn" type="button" onclick="deleteRec(frm_delete,tableList,'delCheck')">删除用户</button> -->
            </div></td>
        </tr>
    </table>
  </body>
</html>
<%@ include file="../public/footInc.jsp" %>