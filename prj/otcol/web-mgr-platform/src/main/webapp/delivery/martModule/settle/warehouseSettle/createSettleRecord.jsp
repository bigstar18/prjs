<%@ page language="java" pageEncoding="GBK"%>
<%@ include file="../../../public/session.jsp"%>

<html>
<head>
</head>
<body>
	<form name="frm" method="post" action="">
	<br>
	<fieldset width="100%">
		<legend class="common"><b>¼��</b></legend>
		<BR>
		<span>
			<table class="common" border="0" cellspacing="0" cellpadding="0" width="100%" align="center">
				<input type="hidden" id="_quantity" name="_quantity">
				<input type="hidden" id="_buyMargin" name="_buyMargin">
				<input type="hidden" id="_buyPayout" name="_buyPayout">
				<input type="hidden" id="_sellMargin" name="_sellMargin">
				<input type="hidden" id="_availablenum" name="_availablenum">
				<input type="hidden" id="_availableweight" name="_availableweight" />
				<input type="hidden" id="commodityId" name="commodityId" />
				<input type="hidden" id="status" name="status" />
				
				<tr class="common">
					<td align="right" width="50%" height="30">ԭ��Ա�ţ�</td>
					<td align="left" width="50%" height="30"><input type="text" id="matchId" name="matchId" onblur="matchIdajaxChange();"><span  id="availablenum"></span></td>
				</tr>
				<!-- contractId -->
				<tr class="common">
					<td align="right" width="50%" height="30">��ͬ��ţ�</td>
					<td align="left" width="50%" height="30"><input type="text" name="contractId" id="contractId">&nbsp;&nbsp;</td>
				</tr>
				<!-- money -->
				<tr class="common">
					<td align="right" width="50%" height="30">�򷽽��ռۣ�</td>
					<td align="left" width="50%" height="30"><input type="text" name="buyPrice" id="buyPrice">&nbsp;&nbsp;</td>
				</tr>
				<tr class="common">
					<td align="right" width="50%" height="30">�򷽱�֤��</td>
					<td align="left" width="50%" height="30"><input type="text" name="buyMargin" id="buyMargin">&nbsp;&nbsp;</td>
				</tr>
				<tr class="common">
					<td align="right" width="50%" height="30">�����򷽻��</td>
					<td align="left" width="50%" height="30"><input type="text" name="buyPayout" id="buyPayout">&nbsp;&nbsp;</td>
				</tr>
				<tr class="common">
					<td align="right" width="50%" height="30">�������ռۣ�</td>
					<td align="left" width="50%" height="30"><input type="text" name="sellPrice" id="sellPrice">&nbsp;&nbsp;</td>
				</tr>
				<tr class="common">
					<td align="right" width="50%" height="30">������֤��</td>
					<td align="left" width="50%" height="30"><input type="text" name="sellMargin" id="sellMargin">&nbsp;&nbsp;</td>
				</tr>
				<!-- contractId -->
				<tr class="common">
					<td align="right" width="50%" height="30">ģ���ţ�</td>
					<td align="left" width="50%" height="30">
						<select name="moduleId" id="moduleId">
							<option value="">��ѡ��</option>
							<c:forEach items="${moduleNameMap}" var="map">
								<OPTION value="${map.key }">${map.value}</OPTION>
							</c:forEach>
						</select>
					</td>
				</tr>
				<!-- breedid -->
				<tr class="common">
					<td align="right" width="50%" height="30">��ƷƷ�ִ��룺</td>
					<td align="left" width="50%" height="30">
						<select name="breedId" id="breedId">
							<OPTION value="">��ѡ��</OPTION>
							<c:forEach items="${commodityList}" var="list">
								<OPTION value="${list.id }">${list.name }</OPTION>
							</c:forEach>
						</select>
					</td>
				</tr>
				<!-- commodity -->
				
				<!-- buyer -->
				<tr class="common">
					<td align="right" width="50%" height="30">�򷽴��룺</td>
					<td align="left" width="50%" height="30"><input type="text" name="FirmID_B" id="FirmID_B">&nbsp;&nbsp;</td>
				</tr>
				<!-- seller -->
				<tr class="common">
					<td align="right" width="50%" height="30">�������룺</td>
					<td align="left" width="50%" height="30"><input type="text" name="FirmID_S" id="FirmID_S">&nbsp;&nbsp;</td>
				</tr>
				<!-- ִ�н�� -->
				<tr class="common">
					<td align="right" width="50%" height="30">���ս����</td>
					<td align="left" width="50%">
						<select name="handleResult"  id="handleResult" onmouseover="">
							<option value="">��ѡ��</option>
							<option value="1">��Լ</option>
							<option value="2">��ΥԼ</option>
							<option value="3">����ΥԼ</option>
							<option value="4">˫��ΥԼ</option>
						</select>
					</td>
				</tr>
				<!-- amount -->
				<tr class="common">
					<td align="right" width="50%" height="30">����������</td>
					<td align="left" width="50%" height="30"><input type="text" id="quantity" name="quantity" >&nbsp;&nbsp;</td>
				</tr>
				<tr class="common">
					<td align="right" width="50%" height="30">�ֵ��ţ�</td>
					<td align="left" width="50%" height="30"><input type="text" id="regStockId" name="regStockId" onchange="regStockIdajaxChange();">&nbsp;&nbsp;</td>
				</tr>
				<tr><td colspan="2">&nbsp;</td></tr>
				<!-- submit -->
				<tr class="common">
					<td colspan="2" align="center" height="30">
						<input type="button" name="subbut" class="button" onclick="submitMSG();" value="�ύ" class="mdlbtn">
						&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" class="button" onclick="cancle();" value="����" class="mdlbtn">
						<input type="hidden" name="opt">
				</tr>
			</table><br>
		</span>
		</fieldset>
	</form>
</body>
</html>
<script type="text/javascript">

	function cancle(){
		frm.action = "<%=basePath%>servlet/settleMatchController.${POSTFIX}?funcflg=settleMatchReturn";
		frm.submit();
	}
	function submitMSG()
	{
		var breedId = frm.breedId.value;
		var handleResult = frm.handleResult.value;
		var contractId = frm.contractId.value;
		//var commodityId = frm.commodityId.value;
		var FirmID_B = frm.FirmID_B.value;
		var FirmID_S = frm.FirmID_S.value;
		var quantity = frm.quantity.value;
		var regStockID = frm.regStockId.value;
		var mark = true;
		var pattern= /^([0-9]*[1-9][0-9]*|0)((\.\d)|(\.\d\d))?$/;
		
		if(trim(breedId)=="")
		{
			alert("��ѡ����ƷƷ��!");
			frm.breedId.value="";
			frm.breedId.focus();
			mark = false;
		}
		else if(trim(handleResult)=="")
		{
			alert("��ѡ���ս��!");
			frm.handleResult.value="";
			frm.handleResult.focus();
			mark = false;
		}
		else if(trim(frm.moduleId.value)=="")
		{
			alert("ģ��Ų���Ϊ��!");
			frm.moduleId.value="";
			frm.moduleId.focus();
			mark = false;
		}
		else if(trim(frm.contractId.value) =="" )
        {
			alert("��ͬ�Ų���Ϊ��!");
			frm.contractId.focus();
			mark = false;
		}
//		else if(trim(frm.contractId.value)!="" && !pattern.exec(frm.contractId.value))
//		{
//			alert("��ͬ�ű���������!");
//			frm.contractId.value="";
//			frm.contractId.focus();
//			mark = false;
//		}
		else if(trim(frm.buyPrice.value)=="" || !pattern.exec(frm.buyPrice.value) || parseFloat(frm.buyPrice.value)<0)
		{
			alert("�򷽽��ռ۱���Ϊ����!");
			frm.buyPrice.value="";
			frm.buyPrice.focus();
			mark = false;
		}
		else if(trim(frm.buyMargin.value)=="" || !pattern.exec(frm.buyMargin.value)   || frm.buyMargin.value<0)
		{
			alert("�򷽱�֤�����Ϊ����!");
			frm.buyMargin.value="";
			frm.buyMargin.focus();
			mark = false;
		}		
		else if(trim(frm.sellPrice.value)=="" || !pattern.exec(frm.sellPrice.value)  || frm.sellPrice.value<0)
		{
			alert("�������ռ۱���Ϊ����!");
			frm.sellPrice.value="";
			frm.sellPrice.focus();
			mark = false;
		}
		else if(trim(frm.sellMargin.value)=="" || !pattern.exec(frm.sellMargin.value) || frm.sellMargin.value<0)
		{
			alert("������֤�����Ϊ����!");
			frm.sellMargin.value="";
			frm.sellMargin.focus();
			mark = false;
		}
		else if(trim(FirmID_B)=="")
		{
			alert("�򷽽����̴��벻��Ϊ��!");
			frm.FirmID_B.value="";
			frm.FirmID_B.focus();
			mark = false;
		}
		else if(trim(FirmID_S)=="")
		{
			alert("���������̴��벻��Ϊ��!");
			frm.FirmID_S.value="";
			frm.FirmID_S.focus();
			mark = false;
		}
		else if(trim(quantity)=="")
		{
			alert("������������Ϊ��!");
			frm.quantity.value="";
			frm.quantity.focus();
			mark = false;
		}
		else if(trim(frm.buyPayout.value)=="")
		{
			frm.buyPayout.value="0";
		}
		else if((trim(frm.buyPayout.value)!="")&&(!pattern.exec(frm.buyPayout.value)  || frm.buyPayout.value<0))
		{
			alert("�����򷽻������Ϊ����!");
			frm.buyPayout.value="";
			frm.buyPayout.focus();
			mark = false;
		}
		else if(trim(regStockID)==""){
		   alert("�ֵ��Ų���Ϊ�գ�");
		   frm.regStockID.focus();
		   mark = false;
		}
//		else if((trim(handleResult)=="1"&&trim(regStockID)=="")||(trim(handleResult)!="1"&&trim(regStockID)!=""))
//		{
//			alert("���ֵ����Ƿ���Ч!");
//			frm.regStockID.value="";
//			frm.regStockID.focus();
//			mark = false;
//		}
//		else if($("#quantity").val()>$("#_availableweight").val() || $("#quantity").val()>$("#availableweight").val()){
//			alert("������������������");
//			$("#quantity").val("");
//			frm.quantity.focus();
//			mark = false;
//		}	
		if(mark)
		{
			$("#breedId").attr("disabled","");
	 		$("#moduleId").attr("disabled","");
			if(confirm("ȷ���ύ��")){
				frm.action="<%=basePath%>servlet/settleMatchController.${POSTFIX}?funcflg=settleMatchAdd&dd="+Date();
				frm.submit();
			}
		}
	}
	//���ǰ��ո� 
	function trim ( str ) {
		regExp = /\S/
		
		if ( regExp.test(str) == false )
			return "";
		
		regExp = /(^\s*)(.*\S)(\s*$)/
		regExp.exec(str);
		
		return RegExp.$2;
	}
	
	
		function matchIdajaxChange()
			{
				var matchId = $("#matchId").val();
	    		$.ajax({   
      				  type : "POST",     //HTTP ���󷽷�,Ĭ��: "GET"   
      				  url : "<%=basePath%>servlet/ajaxController.${POSTFIX}?funcflg=settleAddMatchIdAjax",   //��������ĵ�ַ   
      				  data : "matchId=" + matchId, //���͵�������������   
      				  dataType : "xml",         //Ԥ�ڷ��������ص���������   
      				  error: function(xml) { alert('Error loading XML document:'); }, 
      				  success : function(data){
      				  				var resultInt = $(data).find("resultInt").text();
      				  				if(resultInt<0){
      				  					alert($(data).find("resultMsg").text());
      				  					tableClear();
      				  					return;
      				  				}
      				  				$("#breedId").val($(data).find("BREEDID").text());
      				  				$("#breedId").attr("disabled","disabled");
	 							 	$("#moduleId").val($(data).find("MODULEID").text());
	 							 	$("#moduleId").attr("disabled","disabled");
								  	$("#handleResult").val($(data).find("RESULT").text());
								  	$("#_quantity").val($(data).find("WEIGHT").text());
								  	$("#quantity").val($(data).find("WEIGHT").text());
								  	$("#_buyMargin").val($(data).find("BUYMARGIN").text());
								  	$("#buyMargin").val($(data).find("BUYMARGIN").text());
								  	$("#_buyPayout").val($(data).find("BUYPAYOUT").text());
								  	$("#buyPayout").val($(data).find("BUYPAYOUT").text());
								 	$("#_sellMargin").val($(data).find("SELLMARGIN").text());
								  	$("#sellMargin").val($(data).find("SELLMARGIN").text());
									
									 $("#sellPrice").val($(data).find("SELLPRICE").text());
									 $("#sellPrice").attr("readonly","readonly");
									 $("#buyPrice").val($(data).find("BUYPRICE").text());
									 $("#buyPrice").attr("readonly","readonly");
									 $("#contractId").val($(data).find("MATCHID").text());
									 $("#contractId").attr("readonly","readonly");
									 
									 $("#commodityId").val($(data).find("COMMODITYID").text());
									 $("#FirmID_B").val($(data).find("FIRMID_B").text());
									 $("#FirmID_S").val($(data).find("FIRMID_S").text());
									 $("#regStockID").val($(data).find("REGSTOCKID").text());
									 $("#availablenum").html("<font color='red'>(�������������"+$(data).find("availablenum").text()+")</font>");
		                        		        
		                             $("#contractId").attr("readonly","readonly");                     	                             
		                             $("#buyPrice").attr("readonly","readonly");
		                             $("#buyMargin").attr("readonly","readonly");
		                             $("#buyPayout").attr("readonly","readonly");
		                             $("#sellMargin").attr("readonly","readonly");
		                             $("#sellPrice").attr("readonly","readonly");
		                             $("#FirmID_B").attr("readonly","readonly");
		                             $("#FirmID_S").attr("readonly","readonly");
		                             $("#quantity").attr("readonly","readonly");
		                             
		                             $("#status").val($(data).find("STATUS").text());
									 
      				  				      				  				
      				  			}        //����ɹ���ص�����   
   					 });   
			  
			}
	  function tableClear(){
	  	$(":text").val("");
	  	$("#breedId").val("");
	  	$("#moduleId").val("");
	  	$("#handleResult").val("");
	  }
	  
	  function regStockIdajaxChange()
	  {     
	     var quantity = $("#quantity").val();
	        
		if(trim(quantity)==""){
           alert("��������ԭ��Ա�ţ�");
           frm.regStockId.value = "";
           frm.matchId.focus();
           
        }else{  
            var regStockId = $("#regStockId").val();
			var breedId = $("#breedId").val();   
    		$.ajax({   
     				  type : "POST",     //HTTP ���󷽷�,Ĭ��: "GET"   
     				  url : "<%=basePath%>servlet/ajaxController.${POSTFIX}?funcflg=settleAddregStockIdAjax",   //��������ĵ�ַ   
     				  data : "regStockId=" + regStockId+"&breedId="+breedId, //���͵�������������   
     				  dataType : "xml",         //Ԥ�ڷ��������ص���������   
     				  error: function(xml) { alert('Error loading XML document:'); }, 
     				  success : function(data){
     				  			
     				  				var resultInt = $(data).find("resultInt").text();
     				  				if(resultInt<0){
     				  					alert($(data).find("resultMsg").text());
     				  					$("#regStockId").val("");
     				  					return;
     				  				}
     				  				
     				  				$("#_availableweight").val($(data).find("availableweight").text()); 
     				  				//checkStock();
     				  				   				  				
     				  			}        //����ɹ���ص�����   
  					 });  
  			} 
		}
   // ���ֵ� 
   function checkStock(){
       
      alert($("#quantity").val() + "---" + $("#_availableweight").val());
      var weight = $("#_availableweight").val();
      var quantity = $("#quantity").val();
      alert(quantity + "---" + weight);
      if(weight < quantity){
          alert("�òֵ������������㣡");
	      frm.regStockId.value = "";
      }
   }
</script>