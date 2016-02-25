<%@ page contentType="text/html;charset=GBK"%>
<html>
	<head>
		<%@ include file="../public/headInc.jsp"%>
		<title>交易商类型列表</title>
		<script language="javascript"
			src="<%=basePathF%>/public/jstools/tools.js"></script>
		<script language="javascript"
			src="<%=basePathF%>/public/jstools/common.js"></script>
		<script language="javascript">
		function init(){
			 changeOrder(sign);  
		}
		function createNew(){ 
			var returnValue = openDialog("<%=basePath%>/firmController.mem?funcflg=firmCategoryAddForward","_blank",700,650);
			if(returnValue)
			    frm_query.submit(); 
		}
		function updateNew(firmId){
			var returnValue = openDialog("<%=basePath%>/firmController.mem?funcflg=firmCategoryUpdateForward&firmId="+firmId,"_blank",700,650);
				if(returnValue){
				    frm_query.submit();
				} 
		}
		function doQuery(){
			frm_query.submit();
		}
		function resetForm(){
			frm_query.firmId.value = "";
			frm_query.name.value = "";
		}

		function deleteFirm(frm_delete,tableList,checkName)
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
				    frm_delete.action="<%=basePath%>/firmController.mem?funcflg=firmCategoryDelete";
					frm_delete.submit();
				}
			}
</script>
	</head>
	<body onload="init();">
		<form name="frm_query"
			action="<%=basePath%>/firmController.mem?funcflg=firmCategoryList"
			method="post">
			<input type="hidden" name="orderField" value="${orderFiled}">
			<input type="hidden" name="orderDesc" value="${orderType}">
			<input type="hidden" name="pageSize"
				value="<c:out value="${pageInfo.pageSize}"/>">
			<input type="hidden" id="pageNo" name="pageNo">

			<fieldset width="95%">
				<legend>
					类别查询
				</legend>
				<table border="0" cellspacing="0" cellpadding="0" width="90%"
					height="35">
					<tr height="35">
						<td align="right">
							类别编码：&nbsp;
						</td>
						<td align="left">
							<input id="firmId" name="_id[like]"
								value="<c:out value='${oldParams["id[like]"]}'/>" type=text
								class="text" style="width: 100px" maxlength="16">
						</td>
						<td align="right">
							类别名称：&nbsp;
						</td>
						<td align="left">
							<input id="name" name="_name[like]"
								value="<c:out value='${oldParams["name[like]"]}'/>" type=text
								class="text" style="width: 100px" maxlength="16">
						</td>
						<td align="left">
							<button type="button" class="smlbtn" onclick="doQuery();">
								查询
							</button>
							&nbsp;
							<button type="button" class="smlbtn" onclick="resetForm();">
								重置
							</button>
							&nbsp;
						</td>
					</tr>

				</table>
			</fieldset>
		</form>
			<form id="frm_delete" name="frm_delete"  method="post" targetType="hidden" callback="doQuery();">
		
		<table border="0" cellspacing="0" cellpadding="0" width="80%">
			<table id="tableList" border="0" cellspacing="0" cellpadding="0"
				width="100%" height="400">
				<tHead>
					<tr height="25">
						<c:if test="${excel!=1}">
							<td class="panel_tHead_LB">
								&nbsp;
							</td>
							<td class="panel_tHead_MB" style="padding-left: 12px; width: 10%">
								<input type="checkbox" id="checkAll"
									onclick="selectAll(tableList,'delCheck')">
							</td>
						</c:if>
						<td class="panel_tHead_MB" abbr="id" style="text-align: center;width: 20%">
							&nbsp;类别编码
						</td>
						<td class="panel_tHead_MB" abbr="name" style="text-align: center;width: 50%">
							&nbsp;类别名称
						</td>
						<td class="panel_tHead_MB" width="20%">
							&nbsp;&nbsp;&nbsp;&nbsp;修改
						</td>
						<td class="panel_tHead_RB">&nbsp;
						</td>

					</tr>
				</tHead>
				<tBody>
					<c:forEach items="${resultList}" var="result">
						<tr height="22">
							<c:if test="${excel!=1}">
								<td class="panel_tBody_LB">
									&nbsp;
								</td>
								<td class="underLine" style="text-align: left;">&nbsp;
									<c:if test="${result.id !='0'}">
										<input name="delCheck" type="checkbox"
										value="<c:out value="${result.id}"/>">
									</c:if>
								</td>
							</c:if>
							<td class="underLine" style="text-align: center;">
								<c:out value="${result.id}" />
							</td>
							<td class="underLine" style="text-align: center;">
								 <c:out
										value="${result.name}" />
							</td>
							<td class="underLine">&nbsp;
								<c:if test="${result.name !='未分类'}">
										<button class="mdlbtn" type="button" 
											onclick="updateNew('<c:out value="${result.id}"/>')">
											修改
										</button>
								</c:if>
								
							</td>
							<td class="panel_tBody_RB">
									&nbsp;
								</td>
						</tr>
					</c:forEach>
				</tBody>
				<tFoot>
					<tr height="100%">
						<td class="panel_tBody_LB">
							&nbsp;
						</td>
						<td colspan="4">
							&nbsp;
						</td>
						<td class="panel_tBody_RB">
							&nbsp;
						</td>
					</tr>
					<tr height="22">
						<td class="panel_tBody_LB">
							&nbsp;
						</td>
						<td class="pager" colspan="4">
							<c:if test="${excel!=1}">
								<%@ include file="../public/pagerInc.jsp"%>
							</c:if>


						</td>
						<td class="panel_tBody_RB">
							&nbsp;
						</td>
					</tr>
					<tr height="22">
						<td class="panel_tFoot_LB">
							&nbsp;
						</td>
						<td class="panel_tFoot_MB" colspan="4"></td>
						<td class="panel_tFoot_RB">
							&nbsp;
						</td>
					</tr>
				</tFoot>
			</table>
			</form>
			<tr height="35">

				<td>
					<div align="right">
						<button class="mdlbtn" type="button" onclick="createNew()">
							添加类别
						</button>
						&nbsp;&nbsp;
						 <button class="mdlbtn" type="button" onclick="deleteFirm(frm_delete,tableList,'delCheck')">
							删除类别
						</button>
						&nbsp;&nbsp;
					</div>
				</td>
			</tr>
		</table>
	</body>
</html>
<%@ include file="../public/footInc.jsp"%>