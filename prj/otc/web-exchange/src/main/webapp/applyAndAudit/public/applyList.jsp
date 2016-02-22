<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>

<html>
	<head>
		<title>申请</title>
		<%@ include file="/public/ecsideLoad.jsp"%>


	</head>
	<body><br/>
	<form name="myForm" action="${basePath}/audit/applyList.action" method="post">
		<fieldset width="50%" height="60%">
	<legend>
			查询条件
	</legend>
	<table >
		<tr>
			<td>  id: <input type="text" name="${GNNT_}id[=][long]" value="${oldParams['id[=][long]'] }"/> &nbsp;&nbsp;&nbsp; </td>
			<td>  <input type="submit" value="查询"/></td>
		</tr>
	</table>
		</fieldset>
		<table id="editer" border="0" cellspacing="0" cellpadding="0"
			width="100%" align="right">
			<tr>
				<td align="right">
					<!-- <input type="button" style="color: red" value="添加申请" onclick="addApply()" >
					</input>-->
					 <button style="color: red" onclick="addApply()">
						添加申请
					</button>
					
				</td>
			</tr>
		</table>
	</form>
	<br/><br/>
		<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
	 <tr><td>

	    <ec:table items="list" 
	    	      sortable="true" 
		  	      autoIncludeParameters="${empty param.autoInc}"
		  	      var="obj" 
		  	      action="${basePath}/audit/applyList.action"	
		  	      classic="true"
		  	      title=""
		  	      useAjax="false"
		  	      doPreload="false"
		  	      editable="false"
		  	      style="table-layout:fixed"
			      minHeight="300"
			      listWidth="100%"
			      height="500px" 
			      retrieveRowsCallback="limit"    
	              sortRowsCallback="limit"    
	              filterRowsCallback="limit"
	              
	              filterable="true"
		  >
		<ec:row>
			<ec:column property="id[=][long]" title="id" width="20%" style="text-align:center; " value="${obj.id}"/>
			<ec:column property="applyType[Like]" title="审请类型" width="40%" style="text-align:center;" value="${obj.applyType}"/>
			<ec:column property="statusDiscribe[like]" title="描述"   ellipsis="true" width="20%" style="text-align:center;" value="${obj.statusDiscribe}"/>
             <ec:column  property="detail" title="详情" width="20%" style="text-align:center;" value="<a href='${basePath}/apply/details.action?id=${obj.id}&applyType=${applyType}'>详情展示</a>">
            </ec:column>
		</ec:row>
		
		</ec:table>
	 </td></tr>
	 </table>
	 
		<!-- 编辑和过滤所使用的 通用的文本框模板 -->
		<textarea id="ecs_t_input" rows="" cols="" style="display: none">
			<input type="text" class="inputtext" value=""
					onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
					name="" />
		</textarea>
	</body>
</html>
<script language="javascript">
  	function addApply(){
		myForm.action="${basePath}/apply/auditDetails.action";
		myForm.submit();
	}
</script>