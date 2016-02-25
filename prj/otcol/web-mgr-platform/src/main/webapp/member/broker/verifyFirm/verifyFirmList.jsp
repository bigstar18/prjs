<%@ page contentType="text/html;charset=GBK" %>
<html>
  <head>
    <%@ include file="../../public/headInc.jsp"%>
    <title>交易商列表</title>
    <script language="javascript" src="<%=basePathF%>/public/jstools/tools.js"></script>
	<script language="javascript" src="<%=basePathF%>/public/jstools/common.js"></script>
	<script language="JavaScript">
	    function init(){
			  changeOrder(false);  
		}
		function createNew(){ 
			var returnValue = openDialog("<%=basePath%>/firm/createNewFirm.jsp","_blank",700,650);
			if(returnValue)
			    frm_query.submit(); 
			    //window.open("<%=basePath%>/firm/createNewFirm.jsp","_blank",700,650);
				//window.location.reload();
				
		}
		function doQuery(){
			frm_query.submit();
		}
		function resetForm(){
			frm_query.firmId.value = "";
			frm_query.name.value = "";
			frm_query.type.value = "";
			frm_query.contactMan.value = "";
			frm_query.phone.value = "";
		}
		function editInfo(vCode){
			var returnValue = openDialog("<%=basePath%>/broker/verifyFirm/editNewFirm.jsp?firmId="+vCode+"&date="+Date() ,"_blank",750,680);
			if(returnValue)
			    frm_query.submit();
				//window.location.reload();
			//window.open("<%=basePath%>/firm/editFirm.jsp?firmId="+vCode);
		}
		function setStatus(frm_delete,tableList,checkName)
		{
		   if(isSelNothing(tableList,checkName) == -1)
			{
				alert ( "没有可以操作的数据！" );
				return;
			}
			if(isSelNothing(tableList,checkName))
			{
				alert ( "请选择需要操作的数据！" );
				return;
			}
			
			if(confirm("您确实要处理选中数据吗？"))
			{
				
				frm_delete.submit();
			}
		}
		
		function refuseCheck(frm_delete,tableList,checkName)
		{
		   if(isSelNothing(tableList,checkName) == -1)
			{
				alert ( "没有可以操作的数据！" );
				return;
			}
			if(isSelNothing(tableList,checkName))
			{
				alert ( "请选择需要操作的数据！" );
				return;
			}
			
			if(confirm("您确实要处理选中数据吗？"))
			{
				var selectFirmIds = document.getElementsByName(checkName);
				var vCode = "";
				for(var i=0;i<selectFirmIds.length;i++)
				{
					if(selectFirmIds[i].checked)
					{
						vCode = vCode + selectFirmIds[i].value+",";
					}
				}
				vCode = vCode.substring(0,vCode.length-1);
				var returnValue = openDialog("<%=basePath%>/brokerController.mem?funcflg=brokerRefuseFirm&firmIds="+vCode ,"_blank",450,250);
				if(returnValue)
				{
				    frm_query.submit();
				}
			}
		}
	</script>
  </head>
  <body onload="init();">
  	<form id="frm_query" action="<%=basePath%>/brokerController.mem?funcflg=verifyFirmList" method="post">
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
					<td align="right">类型&nbsp;</td>
					<td align="left">
						<select id="type" name="_type[=]" class="normal" style="width: 60px">
							<OPTION value="">全部</OPTION>
							<option value="1">法人</option>
           		            <option value="2">代理</option>
           					<option value="3">个人</option>
						</select>
					</td>
					<script>
						frm_query.type.value = "<c:out value='${oldParams["type[=]"]}'/>"
					</script>
					<td align="left">
						<button type="button" class="smlbtn" onclick="doQuery();">查询</button>&nbsp;
						<button type="button" class="smlbtn" onclick="resetForm();">重置</button>&nbsp;
					</td>
				</tr>
				<tr height="35">
					<td align="right">联系人&nbsp;</td>
					<td align="left">
						<input id="contactMan" name="_contactMan[like]" value="<c:out value='${oldParams["contactMan[like]"]}'/>" type=text class="text" style="width: 100px">
					</td>
					<td align="right">联系电话&nbsp;</td>
					<td align="left">
						<input id="phone" name="_phone[like]" value="<c:out value='${oldParams["phone[like]"]}'/>" type=text  class="text" style="width: 100px">
					</td>
					<td align="right">&nbsp;</td>
					<td align="left">&nbsp;</td>
					<td align="left">&nbsp;</td>
				</tr>
			</table>
		</fieldset>
	</form>
	<form id="frm_delete" name="frm_delete" action="<%=basePath%>/brokerController.mem?funcflg=verifyFirm" method="post" targetType="hidden" callback="doQuery();">
	  <table id="tableList" border="0" cellspacing="0" cellpadding="0" width="100%" height="400">
  		<tHead>
  			<tr height="25">
  				<td class="panel_tHead_LB">&nbsp;</td>
				<td class="panel_tHead_MB"><input type="checkbox" id="checkAll" onclick="selectAll(tableList,'delCheck')"></td>
				<td class="panel_tHead_MB" abbr="firmId"><%=FIRMID%></td>
				<td class="panel_tHead_MB" abbr="name"><%=FIRMNAME%></td>
				<td class="panel_tHead_MB" abbr="fullname"><%=FULLNAME%></td>
				<td class="panel_tHead_MB" abbr="tariffname">手续费套餐</td>
				<td class="panel_tHead_MB" abbr="contactMan">联系人</td>
				<td class="panel_tHead_MB" abbr="createTime">开户时间</td>
				<!-- <td class="panel_tHead_MB" abbr="extractValue(xmlType(extenddata),'/root/corporate/text()')">法人</td>
				<td class="panel_tHead_MB" abbr="extractValue(xmlType(extenddata),'/root/code/text()')">企业编号</td> -->
				<td class="panel_tHead_MB" abbr="type">类型</td>
		
				<td class="panel_tHead_RB">&nbsp;</td>
			</tr>
		</tHead>
		<tBody>
	  		<c:forEach items="${resultList}" var="result">
	  		<tr height="22" onclick="selectTr();">
	  			<td class="panel_tBody_LB">&nbsp;</td>
	  			<td class="underLine">
	  				<input id="firmSelect" name="delCheck" type="checkbox" value="<c:out value="${result.firmId}"/>">
	  			</td>
	  			<td class="underLine">
	  				<span onclick="editInfo('<c:out value="${result.firmId}"/>')" style="cursor:hand;color:blue">
	  				<c:out value="${result.firmId}"/></span></td>
	  			<td class="underLine"><c:out value="${result.name}"/></td>
	  			<td class="underLine"><c:out value="${result.fullname}"/>&nbsp;</td>
	  			<td class="underLine"><c:out value="${result.tariffname}"/>&nbsp;</td>
	  			<td class="underLine"><c:out value="${result.contactMan}"/>&nbsp;</td>
	  			<td class="underLine"><fmt:formatDate value="${result.createTime}" pattern="yyyy-MM-dd"/></td>
	  			<!-- <td class="underLine"><c:out value="${result.corporate}"/>&nbsp;</td>
	  			<td class="underLine"><c:out value="${result.code}"/>&nbsp;</td> -->
	  			<td class="underLine">
	  			<c:if test="${result.type==1}">法人</c:if>
			    <c:if test="${result.type==3}">个人</c:if>
	  			</td>
		  		
	  			
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
					<%@ include file="../../public/pagerInc.jsp" %>
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
            
              <button class="mdlbtn" type="button" onclick="setStatus(frm_delete,tableList,'delCheck')">审核通过</button>&nbsp;&nbsp;
              <button class="mdlbtn" type="button" onclick="refuseCheck(frm_delete,tableList,'delCheck')">驳回</button>
  			 
            </div></td>
        </tr>
    </table>
  </body>
</html>
<%@ include file="../../public/footInc.jsp" %>