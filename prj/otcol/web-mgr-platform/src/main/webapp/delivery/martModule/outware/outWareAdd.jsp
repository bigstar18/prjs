<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../../public/session.jsp"%>
<html xmlns:MEBS>
  <head>
	<IMPORT namespace="MEBS" implementation="<%=basePath%>public/jstools/calendar.htc">
	<title><%=TITLE%></title>
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
		<input type="hidden" name="commodityId" id="commodityId"/>
		<input type="hidden" name="availablenum" id="availablenum"/>
		<input type="hidden" name="operate" id="operate" value="${value}">
	

		<fieldset width="100%">
		<legend>ת���ⵥ</legend>
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
						<td  width="20%" align="left" class="cd_bt">
							��ⵥ���ţ�<input type="text" name="enterWareId" id="enterWareId" size=12 onblur="enterWareIdajaxChange();" maxlength="10" onkeyup="this.value=this.value.replace(/\D/g,'')"/>
							<font color="red"><span id="enterWareIdflag"></span></font>
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
							����������
						</td>
						<td class="cd_list1" >
							&nbsp;<input type="text" name="weight" id="weight" maxlength="10">
						</td>
								<td class="cd_bt">�����ʱ�䣺</td>
							<td class="cd_list1" >
								<MEBS:calendar eltID="planOutDate" eltName="planOutDate"
									eltCSS="date" eltStyle="width:80px"
									eltImgPath="<%=skinPath%>/images/"
									eltValue="<fmt:formatDate value="<%=new java.util.Date()%>" pattern="yyyy-MM-dd" />"
								/>
							</td>
					</tr>
					  <tr height="25px">
					 			<td class="cd_bt">��ϵ�绰��	</td>
							<td class="cd_list1" >
								&nbsp;<input type="text" name="tel" id="tel" maxlength="20">
							</td>
					 </tr>
					</table>
			<br>
					<table width="100%" border="0" align="center" cellpadding="0px"
						cellspacing="0" bordercolor="#333333"
						style="border-collapse:collapse;" >
						<tr height="25px">
							<td width="15%" class="cd_bt" align="right">
								�ֿ⾭���ˣ�
							</td>
							<td width="15%" class="cd_list1" align="left">
								<input name="agency" id="agency" type="text" size="15"
									value="${outWare.agency}" class="form_kr"
									reqfv="required;�ֿ⾭����" readOnly>
							</td>
							<td width="15%" class="cd_bt" align="right">
								�ֿ⸺���ˣ�
							</td>
							<td width="15%" class="cd_list1" align="left">
								<input name="responsibleman" id="responsibleman" type="text" size="15"
									value="${outWare.responsibleman}" class="form_kr"
									reqfv="required;�ֿ⸺����" readOnly>
							</td>
							<td width="15%" class="cd_bt" align="right">
								�����̾����ˣ�
							</td>
							<td width="15%" class="cd_list1" align="left">
								<input name="dealerAgency" id="dealerAgency" type="text" size="15"
									value="${outWare.dealerAgency}" class="form_kr"
									reqfv="required;�����̾�����" readOnly>
							</td>
						</tr>
					</table>
			<br>
			<div align="center">
				<input class="lgrbtn" type="button" onClick="outWareAdd()" value="��ⵥת���ⵥ">&nbsp;&nbsp;
				<input class="smlbtn" type="button" value="����" onclick="returnListPage();">
			</div>
		</fieldset>
        </form>
</body>
</html>
<script>
	function enterWareIdajaxChange() {
	  var enterWareId = $("#enterWareId").val();
   		$.ajax({   
    				  type : "POST",     //HTTP ���󷽷�,Ĭ��: "GET"   
    				  url : "<%=basePath%>servlet/ajaxController.${POSTFIX}?funcflg=enterWareAjax",   //��������ĵ�ַ   
    				  data : "enterWareId=" + enterWareId, //���͵�������������   
    				  dataType : "xml",         //Ԥ�ڷ��������ص���������   
    				  error: function(xml) { alert('Error loading XML document:' +xml); }, 
    				  success : function(data){
    				  				if($(data).find("result").text()==-1){
    				  					$("#enterWareIdflag").html("û�и���ⵥ");
    				  					$("#enterWareId").val("");
    				  					$("#enterWareId").focus();
    				  				}else if($(data).find("result").text()==-2) {
										$("#enterWareIdflag").html("����ⵥ״̬����");
										$("#enterWareId").val("");
										$("#enterWareId").focus();
									}else{
    				  					$("#enterWareIdflag").html("��������:"+$(data).find("availablenum").text());
    				  					$("#availablenum").val($(data).find("availablenum").text());
    				  					$("#_firmId").html($(data).find("dealerid").text());
    				  					$("#firmId").val($(data).find("dealerid").text());
    				  					$("#_firmname").html($(data).find("dealername").text());
    				  					$("#_warehouseId").html($(data).find("warehouseid").text());
    				  					$("#warehouseId").val($(data).find("warehouseid").text());
    				  					$("#_warehouseName").html($(data).find("warehousename").text());
    				  					$("#_commodityName").html($(data).find("commodityname").text());	
    				  					$("#commodityId").val($(data).find("commodityid").text());	
    				  					
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
			alert("��ⵥ���Ų���Ϊ�գ�");
			return false;
		}
		if(frm.tel.value==""){
			frm.tel.focus();
			alert("��ϵ�绰����Ϊ��");
			return false;
		}
		if(frm.weight.value==""){
			frm.weight.focus();
			alert("������������Ϊ��");
			return false;
		}
		if(!IsIntOrFloat(frm.weight.value)){
			alert("������������Ϊ����0������");
			frm.weight.value="";
			frm.weight.focus();
			return false;
		}
		if(parseFloat(frm.weight.value)>parseFloat(frm.availablenum.value)){
			frm.weight.focus();
			frm.weight.value="";
			alert("�����������ܴ��ڿ�������");
			return false;
		}
	
		frm.action = "<%=basePath%>servlet/outWareController.${POSTFIX}?funcflg=outWareAdd";
		if(confirm("ȷ��ִ�д˲�����")){
			frm.submit();
		}
	}
	
	function returnListPage(){
		frm.action = "<%=basePath%>servlet/outWareController.${POSTFIX}?funcflg=outWareReturn";
		frm.submit();
	}
</script>
