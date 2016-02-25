<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../../../public/session.jsp"%>
<html xmlns:MEBS>
  <head>
	<IMPORT namespace="MEBS" implementation="<%=basePath%>public/jstools/calendar.htc">
	<title><%=TITLE%></title>
	
    <style type="text/css">
	<!--
	.a1 {
		float: left;
		width: 100px;
	}
	.a2 {
		float: left;
		width: 100px;
	}
	.cd_bt {
		font-size: 12px;
		line-height: 16px;
		font-weight: bold;
		text-decoration: none;
		text-align: right;
		padding-right: 5px;
	}
	-->
    </style>
</head>
<body>
        <form action="<%=basePath%>servlet/regStockApplyController.${POSTFIX}?funcflg=addnonStandardregStockApply" name="frm" method="POST" targetType="hidden">
		<input type="hidden" name="type" value=${type}>
		<input type="hidden" name="operate" id="operate" value="${value }">
		
		<fieldset width="100%">
		<legend>	
		<c:choose>
    	   		<c:when test="${type==1}">
      				非标准注册仓单申请	
		        </c:when>
		        <c:when test="${type==2}">
      				临时注册仓单申请	
		        </c:when>
    	   </c:choose></legend>
			<table width="100%" border="0" align="center" cellpadding="0"
					cellspacing="0" bordercolor="#333333"
					style="border-collapse:collapse;">
					<tr height="40px">
						<td colspan="5" class="cd_hr">
							<br>
							&nbsp;<font color=red></font>
						</td>
					</tr>
				</table>
				<table width="100%" border="0" align="center" cellpadding="0px"
					cellspacing="0" bordercolor="#333333"
					style="border-collapse:collapse;">
					<tr height="30px">
						<td  width="40%" align="left" class="cd_bt">
							
						</td>
						<td colspan="3" width="60%">
							&nbsp;
						</td>
					</tr>
				</table>
				<table id="tableList" width="100%" border="1" align="center"
					cellpadding="0px" cellspacing="0" bordercolor="#333333"
					style="border-collapse:collapse;">

					<tr height="25px">
						<td width="15%" class="cd_bt">
							${FIRMID}：
						</td>
						<td width="30%" class="cd_list1">
								<input type="text" name="firmId" id="dealerId"   onblur="dealajaxChange();" class="form_kr" maxlength="32" onkeyup="value=value.replace(/[\W]/g,'') "><font color="red"><span id="hint"></span></font>
						</td>
						<td class="cd_bt">
							${FIRMNAME}：
						</td>
						<td  class="cd_list1" colspan=3>
								<span id="_firmname"></span>
						</td>
					</tr>
					<tr height="25px">
						<td width="15%" class="cd_bt">
							品种名称：
						</td>
						<td width="30%" class="cd_list1">
								<select name="breedId" id="breedId" style="width: 150px;" class="form_k">
								<option value="">请选择</option> 
							<c:forEach items="${commodityList}" var="commodity">
			               		<option value="${commodity.id}">${commodity.name}</option>
			              	</c:forEach>	
						</select>&nbsp;<font color="red">*</font>	
						</td>
						<td class="cd_bt" >
							重量：
						</td>
						<td  class="cd_list1" >
								<input type="text" id="weight" name="weight" style="width: 150px;" value="" class="form_kr" maxlength="10">&nbsp;<font color="red">*</font>
						</td>
						
					</tr>
					<tr height="25px">
						<td width="15%" class="cd_bt">
							每件重量：
						</td>
						<td width="30%" class="cd_list1" colspan="3" >
							<input type="text" id="unitWeight" name="unitWeight" value="" class="form_kr" style="width: 150px;" maxlength="10">&nbsp;<font color="red">*</font>
						</td>
						<%-- 
					    <td class="cd_bt">拟出库时间</td>
						<td class="cd_list1" >
								
								<MEBS:calendar eltID="planOutDate" eltName="planOutDate"
									eltCSS="date" eltStyle="width:80px"
									eltImgPath="<%=skinPath%>/images/"
									eltValue="<fmt:formatDate value="<%=new java.util.Date()%>" pattern="yyyy-MM-dd" />"
								/>
						</td>
						--%>
					  </tr>
					</table>
			<br>
			<br>
			<div align="center">
				<input class="lgrbtn" type="button" onClick="doSubmit()" value="提交申请">
			</div>
		</fieldset>
        </form>
</body>
</html>
<script>

	function weight_blur(){
		if (frm.weight.value != "" && frm.unitWeight.value != "") {
			frm.weight.value = Math.floor(frm.weight.value/frm.unitWeight.value);
		}
	}
	function unitWeight_blur(){
		if (frm.weight.value != "" && frm.unitWeight.value != "") {
			frm.weight.value = Math.floor(frm.weight.value/frm.unitWeight.value);
		}
	}
	
	function doSubmit()
	{
		if(frm.firmId.value==""){
			frm.firmId.focus();
			alert("交易商不能为空");
			return false;
		}
		if(frm.weight.value==""){
			frm.weight.focus();
			alert("重量不能为空");
			return false;
		}
		if(!IsIntOrFloat(frm.weight.value) || frm.weight.value==0){
			frm.weight.value="";
			frm.weight.focus();
			alert("重量必须为大于0的数字！");
			return false;		
		}
		if(frm.unitWeight.value==""){
			frm.unitWeight.focus();
			alert("每件重量不能为空");
			return false;
		}
		if(!IsIntOrFloat(frm.unitWeight.value) || frm.unitWeight.value==0){		
			frm.unitWeight.value="";
			frm.unitWeight.focus();
			alert("每件重量必须为大于0的数字！");
			return false;
		}
		if(frm.breedId.value==""){
			frm.breedId.focus();
			alert("请选择商品名称");
			return false;
		}
		if(confirm("确定执行此操作？")){
			frm.submit();
		}
	}
	 		function dealajaxChange()
			{
				var dealerId = $("#dealerId").val();
	    		$.ajax({   
      				  type : "POST",     //HTTP 请求方法,默认: "GET"   
      				  url : "<%=basePath%>servlet/ajaxController.${POSTFIX}?funcflg=dealerAjax",   //发送请求的地址   
      				  data : "dealerId=" + dealerId, //发送到服务器的数据   
      				  dataType : "xml",         //预期服务器返回的数据类型   
      				  error: function(xml) { alert('Error loading XML document:' +'<%=basePath%>servlet/ajaxController.${POSTFIX}?funcflg=dealerAjax'); }, 
      				  success : function(data){
      				  					var result = $(data).find("result").text();
						      		    if(result==-1)
										{
										  $("#dealerId").val("");
										  $("#_firmname").html("");
										  $("#hint").html("没有该交易商!")
										}else{
											var name = $(data).find("name").text();
											  $("#hint").html("")
											$("#_firmname").html(name);
										
										}
      				  			}        //请求成功后回调函数   
   					 });   
			  
			}
</script> 
