<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>

<html>
	<head>
		<title>����</title>
		<%@ include file="/public/ecsideLoad.jsp"%>


	</head>
	<body><br/>
	<form name="myForm" action="${basePath}/audit/applyList.action" method="post">
		<fieldset width="50%" height="60%">
	<legend>
			��ѯ����
	</legend>
	<table >
		<tr>
			<td>  id: <input type="text" name="${GNNT_}id[=][long]" value="${oldParams['id[=][long]'] }"/> &nbsp;&nbsp;&nbsp; </td>
			<td>  <input type="submit" value="��ѯ"/></td>
		</tr>
	</table>
		</fieldset>
		<table id="editer" border="0" cellspacing="0" cellpadding="0"
			width="100%" align="right">
			<tr>
				<td align="right">
					<!-- <input type="button" style="color: red" value="�������" onclick="addApply()" >
					</input>-->
					 <button style="color: red" onclick="addApply()">
						�������
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
			<ec:column property="applyType[Like]" title="��������" width="40%" style="text-align:center;" value="${obj.applyType}"/>
			<ec:column property="statusDiscribe[like]" title="����"   ellipsis="true" width="20%" style="text-align:center;" value="${obj.statusDiscribe}"/>
             <ec:column  property="detail" title="����" width="20%" style="text-align:center;" value="<a href='${basePath}/apply/details.action?id=${obj.id}&applyType=${applyType}'>����չʾ</a>">
            </ec:column>
		</ec:row>
		
		</ec:table>
	 </td></tr>
	 </table>
	 
		<!-- �༭�͹�����ʹ�õ� ͨ�õ��ı���ģ�� -->
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