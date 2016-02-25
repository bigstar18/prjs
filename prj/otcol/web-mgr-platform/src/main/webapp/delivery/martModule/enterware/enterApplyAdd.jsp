<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../../public/session.jsp"%>

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
<form id="frm" method="POST" targetType="hidden" action="<%=basePath%>servlet/enterApplyController.${POSTFIX}?funcflg=enterApplyAdd">
	<input type="hidden" name="operate" id="operate" value="${value}">
	<fieldset width="100%">
	<legend>��Ʒ������뵥</legend>
		<br>
		<table width="96%" border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#333333" style="border-collapse:collapse;">
		
			<tr height="18px">
				<td  align="right" class="cd_bt" rowspan=2>${FIRMID}��</td>
		        <td  colspan="3" class="cd_list1"  rowspan=2><div align="left">	&nbsp;<input type="text" name="firmId" id="dealerId" style="width:120"  onblur="dealAjaxChange();" maxlength="32" onkeyup="value=value.replace(/[\W]/g,'') " >
					<font color="red">*<span id="hint"></span></font></div>
				</td>
				<td align="right" class="cd_bt">��ϵ�ˣ�</td>
				<td class="cd_list11"><div align="left" id="linkman">&nbsp;</div></td>
			</tr>
			<tr height="18px">
				<td align="right" class="cd_bt">��ϵ�绰��</td>
				<td class="cd_list11"><div align="left" id="tel">&nbsp;</div></td>
			</tr>
			<tr height="36px">
				<td align="right" class="cd_bt">���ղֿ⣺</td>
				<td  class="cd_list1" colspan="3">
					<div align="left">&nbsp;<select name="warehouseId" id="warehouseId" style="width:120" class="form_k"  onpropertychange="warehouseAjaxChange();">
						  <option value="">��ѡ��</option>
					<c:forEach items="${warehouseList}" var="result">
		                <option value="${result.id}">${result.name}</option>
		                </c:forEach>
					</select>&nbsp;<font color="red">*</font></div>					
				</td>
				<td align="right" class="cd_bt">Ʒ�����ƣ�</td>
				<td align="left" class="cd_list1">
					&nbsp;<select name="commodityId" id="commodityId" style="width:120" class="form_k" onpropertychange="commdityAjaxChange();">
						<option value="">��ѡ��</option>
					</select>&nbsp;<font color="red">*</font>					
				</td>
			</tr>
			<tr height="36px">
			    <td align="right" class="cd_bt">�����ʱ�䣺</td>
				<td  class="cd_list1"  colspan="3"  >
					&nbsp;<MEBS:calendar eltID="enterDate" eltName="enterDate" eltCSS="date" eltStyle="width:120px" eltImgPath="<%=skinPath%>/images/" eltValue="<fmt:formatDate value="<%=new java.util.Date()%>" pattern="yyyy-MM-dd" />"/>&nbsp;<font color="red">*</font>					
				</td>
				<td width="15%" align="right" class="cd_bt" >
					���ࣺ
				</td>
				<td width="18%" class="cd_list1" id="bre">
				    &nbsp;<select name="sort" id="sort" class="form_kr"style="width:120">
						<option value="">��ѡ��</option>
				  </select>&nbsp;<font color="red">*</font>					
				</td>
			</tr>
			<tr height="36px">
				<td width="15%" align="right" class="cd_bt" id="c1">���������</td>
				<td width="35%"  colspan="3" class="cd_list1">
					&nbsp;<input id="weight" name="weight" style="width:120" type="text" value="" class="form_kr" onblur="weight_blur()" maxlength="10">&nbsp<font color="red">*</font>					
				</td>
				<td width="15%" align="right" class="cd_bt" >
					�ȼ���
				</td>
				<td width="35%" class="cd_list1">
					&nbsp;<select name="grade" id="grade" class="form_kr"style="width:120">
						<option value="">��ѡ��</option>
				  </select>&nbsp;<font color="red">*</font>				
				</td>
			</tr>
			<tr height="36px">
				<td align="right" class="cd_bt" id="c2">ÿ��������</td>
				<td class="cd_list1" colspan="3">
					&nbsp;<input id="unitWeight" name="unitWeight" type="text" value="" class="form_kr" style="width:120" onblur="unitWeight_blur()" maxlength="10">&nbsp;<font color="red">*</font>					
				</td>
				<td align="right" class="cd_bt">��������</td>
				<td  class="cd_list1" colspan="3">
					<div align="left">&nbsp;<select name="origin" id="origin" style="width:120" class="form_k">
						  <option value="">��ѡ��</option>
					</select>&nbsp;<font color="red">*</font></div>	
				</td>
			</tr>
			<tr height="36px">
			    <td align="right" class="cd_bt">������</td>
			    <td class="cd_list1" colspan="3">
				  	&nbsp;<input id="quantity" name="quantity" type="text"  class="form_kr" style="width:120"  readonly="true" onfocus="this.blur()">&nbsp;<font color="red">*</font>
			    </td>
				<td align="right" class="cd_bt">�������£�</td>
				<td colspan="3" class="cd_list1">
				&nbsp;<select name="madeYear" id="madeYear"  class="form_k" style="width:65">
						<option value="">��ѡ��</option>
						<%
							Calendar ca = Calendar.getInstance();//����
							int Year2 = ca.get(Calendar.YEAR)-8;//���յ���
							for(int i = Year2-10;i<Year2+10;i++){
						%>
						<option value="<%=i%>"><%=i%></option>
						<%
							}
						%>
					</select> ��
					<select name="madeYear_Month" id="madeYear_Month" class="form_k" style="width:50">
						<%for(int j=1;j<=12;j++){%>
							<option value="<%=j%>" ><%=j%></option>
						<%}%>
					</select>
					��
					<font color="red">*</font>
				</td>
			</tr>
			<tr height="36px">
				<td width="15%" align="right" class="cd_bt">��װ��ʽ��</td>
				<td width="35%"   class="cd_list1" colspan="3" >
					&nbsp;<input name="packaging" type="text" value="" class="form_kr" style="width:120">&nbsp;<font color="red">*</font>					
				</td>
				<td align="right" class="cd_bt">���ţ�</td>
				<td  class="cd_list1"  colspan="3" >
					&nbsp;<input name="lot" type="text" value="" class="form_kr" style="width:120">&nbsp<font color="red">*</font>					
				</td>
			</tr>
			<input type="hidden" name="productionDate" id="productionDate">
			
		</table>
		
		<br>
		<br>
		
	<div align="center">
        <button class="smlbtn" type="button" onClick="doSubmit();" >ȷ��</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<button class="smlbtn" type="button" onClick="freturn();">����</button>
	</div>
	</fieldset>
	<div align="right" >
		<font color="red" size="2">˵�����������ɹ�ʽ ����=�������/ÿ������ �ó��ģ�������С��������������</font> 
       </div>
    
</form>
</body>
</html>
<script>

	function weight_blur(){
		if(frm.weight.value != "" && !IsIntOrFloat(frm.weight.value)){
			alert("�������ӦΪ����0����");
			frm.weight.value = "";
			return false;
		}
		if (frm.weight.value != "" && frm.unitWeight.value != "") {
			frm.quantity.value = Math.floor(frm.weight.value/frm.unitWeight.value);
		}
	}
	function unitWeight_blur(){
		if(frm.unitWeight.value != "" && !IsIntOrFloat(frm.unitWeight.value)||parseFloat(frm.unitWeight.value) == 0){
			alert("ÿ������ӦΪ����0����");
			frm.unitWeight.value = "";
			return false;
		}
		if (frm.weight.value != "" && frm.unitWeight.value != ""&& frm.unitWeight.value != 0) {
			frm.quantity.value = Math.floor(frm.weight.value/frm.unitWeight.value);
		}
	}
	
	function doSubmit()
	{
		if(!checkValue("frm"))
			return;
		if(frm.firmId.value==""){
			frm.firmId.focus();
			alert("�����̲���Ϊ��");
			return false;
		}
		if(frm.warehouseId.value==""){
			frm.warehouseId.focus();
			alert("��ѡ��Ҫ���Ĳֿ�");
			return false;
		}
		if(frm.commodityId.value==""){
			frm.commodityId.focus();
			alert("��ѡ��Ʒ������");
				return false;
		}
		if(frm.unitWeight.value==""){
			frm.unitWeight.focus();
			alert("ÿ����������Ϊ��");
			return false;
		}
		if(frm.weight.value==""){
			frm.weight.focus();
			alert("�����������Ϊ��");
			return false;
		}
		if(frm.enterDate.value==""){
			alert("�����ʱ�䲻��Ϊ��");
			return;
		}
		if(frm.lot.value==""){
			frm.lot.focus();
			alert("���Ų���Ϊ��");
			return false;
		}
		if(frm.grade.value==""){
			frm.grade.focus();
			alert("��ѡ���Ʒ�ĵȼ�");
			return false;
		}
		if(frm.origin.value==""){
			frm.origin.focus();
			alert("�������Ҳ���Ϊ��");
			return false;
		}
		if(frm.madeYear.value==""){
			frm.madeYear.focus();
			alert("��ѡ����������");
			return false;
		}
		if(frm.packaging.value==""){
			frm.packaging.focus();
			alert("��װ��ʽ����Ϊ��");
			return false;
		}
		if(frm.sort.value==""){
			frm.sort.focus();
			alert("���಻��Ϊ��");
			return false;
		}
		
		$("#productionDate").val($("#madeYear").val()+"��"+$("#madeYear_Month").val()+"��");
		
		if(confirm("ȷ��ִ�д˲�����")){
			frm.submit();
		}
		
	}

	function dealAjaxChange()
	{
		
		var dealerId = $("#dealerId").val();
   		$.ajax({   
    				  type : "POST",     //HTTP ���󷽷�,Ĭ��: "GET"   
    				  url : "<%=basePath%>servlet/ajaxController.${POSTFIX}?funcflg=getFirm",   //��������ĵ�ַ   
    				  data : "firmId=" + dealerId, //���͵�������������   
    				  dataType : "xml",         //Ԥ�ڷ��������ص���������   
    				  error: function(xml) { alert('Error loading XML document:' +xml); }, 
    				  success : function(data){
    				  			var result = $(data).find("result").text();
				      		    if(result==-1)
								{
								  $("#dealerId").val("");
								  $("#hint").html("û�иý�����!")
								}else{
									var linkman = $(data).find("linkman").text();
									var tel = $(data).find("tel").text();
									  $("#hint").html("")
									  if(linkman !='null'){
									  $("#linkman").html(linkman);
									  }else{
								      $("#linkman").html("");
									  }
									if(tel!='null'){
										$("#tel").html(tel);
									}else{
										$("#tel").html("");
									}
								}
    				  			}        //����ɹ���ص�����   
 					 });   
	  
	}
	
	function warehouseAjaxChange()
	{
		
		
		var warehouseId = $("#warehouseId").val();
		if(warehouseId=="" || warehouseId==null)
			return;
   		$.ajax({   
    				  type : "POST",     //HTTP ���󷽷�,Ĭ��: "GET"   
    				  url : "<%=basePath%>servlet/ajaxController.${POSTFIX}?funcflg=getWarehouse",   //��������ĵ�ַ   
    				  data : "warehouseId=" + warehouseId, //���͵�������������   
    				  dataType : "xml",         //Ԥ�ڷ��������ص���������   
    				  error: function(xml) { alert('Error loading XML document:' +xml); }, 
    				  success : function(data){
    				  				$("#commodityId").empty();
    				  				if($(data).find("result").text()=="1"){
    				  					$("<option value=''>��ѡ��</option>").appendTo($("#commodityId"));
  									$(data).find("warehousecommodityid").each(function(i){
  				 				 	$("<option value='"+$(data).find("warehousecommodityid").eq(i).text()+"'>"+$(data).find("warehousecommodityname").eq(i).text()+"</option>").appendTo($("#commodityId"));
  								 });
  								}else{
								$("<option value=''>��ѡ��</option>").appendTo($("#commodityId"));	   			
  								}
    				  			}        //����ɹ���ص�����   
 					 });   
	  
	}
	
	function commdityAjaxChange()
	{
		
			
		var commodityId = $("#commodityId").val();
		
   		$.ajax({   
    				  type : "POST",     //HTTP ���󷽷�,Ĭ��: "GET"   
    				  url : "<%=basePath%>servlet/ajaxController.${POSTFIX}?funcflg=getCommodity",   //��������ĵ�ַ   
    				  data : "commodityId=" + commodityId, //���͵�������������   
    				  dataType : "xml",         //Ԥ�ڷ��������ص���������   
    				  error: function(xml) { alert('Error loading XML document:' +xml); }, 
    				  success : function(data){
    				  				$("#origin").empty();
    				  				$("#sort").empty();
    				  				$("#grade").empty();      				  				
    				  				if($(data).find("result").text()=="1"){
    				  					$("<option value=''>��ѡ��</option>").appendTo($("#origin"));
					   			        $(data).find("origin").each(function(i){
				   				 	       $("<option value='"+$(data).find("origin").eq(i).text()+"'>"+$(data).find("origin").eq(i).text()+"</option>").appendTo($("#origin"));
				   				         });  			
					   			         $("<option value=''>��ѡ��</option>").appendTo($("#grade"));	 
				   				        $(data).find("grade").each(function(i){
                                        $("<option value='"+$(data).find("grade").eq(i).text()+"'>"+$(data).find("grade").eq(i).text()+"</option>").appendTo($("#grade"));
				   				         });
				   				         $("<option value=''>��ѡ��</option>").appendTo($("#sort"));
				   				 	    $(data).find("sort").each(function(i){
				   				 	        $("<option value='"+$(data).find("sort").eq(i).text()+"'>"+$(data).find("sort").eq(i).text()+"</option>").appendTo($("#sort"));
				   				         });
				   					
				   				 
				   				 
				   				 var c1 = "�������";
				   				 var c2 = "ÿ������";
				  				 var c3 = "�Ƿ��ʼ�";
				   				 var unit = $(data).find("unit").text();
				 			
				 				if('null' != unit){
						   			 $("#c1").html( c1 +"("+ unit+")");
									 $("#c2").html( c2 +"("+ unit+")");
									 $("#c3").html( c3 +"("+ unit+")");
								}else{
									$("#c1").html( c1);
									 $("#c2").html( c2);
									 $("#c3").html( c3);
								}
							}
    				  			}        //����ɹ���ص�����   
 					 });   
	  
	}  
	function freturn()
	{
		frm.action = "<%=basePath%>servlet/enterApplyController.wha?funcflg=enterApplyReturn";
		frm.submit();
	}
</script> 
