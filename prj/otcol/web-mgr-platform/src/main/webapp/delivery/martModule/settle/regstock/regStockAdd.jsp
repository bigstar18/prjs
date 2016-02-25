<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../../../public/session.jsp"%>
<html xmlns:MEBS>
  <head>
	<IMPORT namespace="MEBS" implementation="<%=basePath%>public/jstools/calendar.htc">
	<title></title>
	<style type="text/css">
	<!--
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
        <form action="" name="frm" method="POST" targetType="hidden">
		<input type="hidden" name="firmId" id="firmId"/>
		<input type="hidden" name="warehouseId" id="warehouseId" />
		<input type="hidden" name="breedId" id="breedId"/>
		<input type="hidden" name="availablenum" id="availablenum"/>
		<input type="hidden" name="countType" id="countType"/>
		<input type="hidden" name="unitWeight" id="unitWeight"/>
		<input type="hidden" name="operate" id="operate" value="${value }">
		<input type="hidden" name="type" id="type" value=0>

		<fieldset width="100%">
		<legend>����ע��ֵ�</legend>
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
						<td width="21%" align="left" class="cd_bt">
							��ⵥ���ţ�<input type="text" name="stockId" id="enterWareId" size='16' onblur="enterWareIdajaxChange();" maxlength="10" onkeyup="this.value=this.value.replace(/\D/g,'')"/>
						</td>
						<td colspan="3" width="60%">
							<font color="red"><span id="enterWareIdflag"></span></font>
						</td>
					</tr>
				</table>
				<table id="tableList" width="100%" border="1" align="center"
					cellpadding="0px" cellspacing="0" bordercolor="#333333"
					style="border-collapse:collapse;">
					<tr height="25px">
						<td width="15%" class="cd_bt">
							${FIRMID}��
						</td>
						<td width="15%" class="cd_list1">
								<span id="_firmId"></span>
						</td>
						<td class="cd_bt">
							${FIRMNAME}��
						</td>
						<td  class="cd_list1" colspan=3>
								<span id="_firmname"></span>
						</td>
					</tr>
					<tr height="25px">
						<td width="15%" class="cd_bt">
							�ֿ���룺
						</td>
						<td width="15%" class="cd_list1">
								<span id="_warehouseId"></span>
						</td>
						<td class="cd_bt" >
							�ֿ����ƣ�
						</td>
						<td  class="cd_list1"  colspan=3>
								<span id="_warehouseName"></span>
						</td>
					</tr>
					<tr height="25px">
						<td width="15%" class="cd_bt">
							Ʒ�����ƣ�
						</td>
						<td width="15%" class="cd_list1" >
							<span id="_commodityName"></span>
						</td>
						
						<td class="cd_bt">
							תע��ֵ�������
						</td>
						<td class="cd_list1" >
							&nbsp;<input type="text" name="weight" id="quantity" maxlength="10" >
						</td>
					</tr>
					</table>
			<br>
			<div align="center">
				<input class="lgrbtn" type="button" onClick="outWareAdd()" value="��ⵥתע��ֵ�">
			</div>
		</fieldset>
	
        </form>
</body>
</html>
<script>
function enterWareIdajaxChange(){
	var enterWareId = $("#enterWareId").val();
	 		$.ajax({   
		  type : "POST",     //HTTP ���󷽷�,Ĭ��: "GET"   
		  url : "<%=basePath%>servlet/ajaxController.${POSTFIX}?funcflg=enterWareAjax",   //��������ĵ�ַ   
	  data : "enterWareId=" + enterWareId, //���͵�������������   
	  dataType : "xml",         //Ԥ�ڷ��������ص���������   
	  error: function(xml) { alert('Error loading XML document:' +'<%=basePath%>servlet/ajaxController.${POSTFIX}?funcflg=regStockApplyAjax'); }, 
	  success : function(data){
				if($(data).find("result").text()==-1){
					$("#enterWareIdflag").html("û�и���ⵥ");
					$("#enterWareId").val("");
				}else if($(data).find("result").text()==-2) {
					$("#enterWareIdflag").html("����ⵥ״̬����");
					$("#enterWareId").val("");
				}else{
					$("#enterWareIdflag").html("��������:"+$(data).find("availablenum").text()+$(data).find("countType").text());
					$("#availablenum").val($(data).find("availablenum").text());
					$("#_firmId").html($(data).find("dealerid").text());
					$("#firmId").val($(data).find("dealerid").text());
					$("#_firmname").html($(data).find("dealername").text());
					$("#_warehouseId").html($(data).find("warehouseid").text());
					$("#warehouseId").val($(data).find("warehouseid").text());
					$("#_warehouseName").html($(data).find("warehousename").text());
					$("#_commodityName").html($(data).find("commodityname").text());	
					$("#breedId").val($(data).find("commodityid").text());
					$("#unitWeight").val($(data).find("unitWeight").text());		
					$("#agency").val($(data).find("agency").text());		
					$("#responsibleman").val($(data).find("responsibleman").text());		
					$("#dealerAgency").val($(data).find("dealerAgency").text());			
				}
			}        //����ɹ���ص�����   
	 });   
}
	function outWareAdd(){
		if(frm.enterWareId.value==""){
			frm.enterWareId.focus();
			alert("��ⵥ���Ų���Ϊ��");
			return false;
		}
		if(frm.quantity.value==""){
			frm.quantity.focus();
			alert("תע��ֵ���������Ϊ��");
			return false;
		}
		if(parseFloat(frm.weight.value)>parseFloat(frm.availablenum.value)){
			frm.weight.focus();
			alert("תע��ֵ��������ܴ��ڿ�������");
			return false;
		}
		if(!IsIntOrFloat(frm.weight.value) || frm.weight.value==0){
			frm.weight.focus();
			frm.weight.value="";
			alert("תע��ֵ���������Ϊ�����������");
			return false;
		}
		frm.action = "<%=basePath%>servlet/regStockApplyController.${POSTFIX}?funcflg=addRegStockApply";
		if(confirm("ȷ��ִ�д˲�����")){
			frm.submit();
		}
	}
</script>
