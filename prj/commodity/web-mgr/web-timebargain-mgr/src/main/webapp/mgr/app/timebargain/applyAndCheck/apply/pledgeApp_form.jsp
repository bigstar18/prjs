<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
		<title>
		</title>
		<script type="text/javascript"> 
function window_onload()
{
    document.all.typeOperate[0].checked = true;
    check_readOnly();
    //alert(document.all.typeOperate[0].value);
}
//save (/^[1-2]+\.\d{2}$/)������λС����������ʽ 
function save_onclick()
{
	if (confirm("��ȷ��Ҫ�ύ��")) {
	
		
		if (document.all.typeOperate[0].checked) {
			if (document.all.billID.value == "") {
			alert("�ֵ��Ų���Ϊ�գ�");
			return false;
		}/*
			if (document.all.quantity.value >(document.all.weight.value-document.all.frozenWeight.value)) {
				alert("��Ѻ�������ܴ��ڲֵ�����������");
				return false;
			}*/
		if (document.all.firmId.value == "") {
			alert("�����̴��벻��Ϊ�գ�");
			return false;
		}
		if (document.all.commodityName.value == "") {
			alert("Ʒ�����Ʋ���Ϊ�գ�");
			return false;
		}
		if (document.all.quantity.value == "") {
			alert("��Ѻ��������Ϊ�գ�");
			return false;
		}
		if (document.all.quantity.value <= 0) {
			alert("��Ѻ����Ӧ����0��");
			return false;
		}
			document.all.type.value = document.all.typeOperate[0].value;
		}else if (document.all.typeOperate[1].checked) {
			if (document.all.billID.value == "") {
			alert("�ֵ��Ų���Ϊ�գ�");
			return false;
		}if (document.all.quantity.value == "") {
			alert("û�ж�Ӧ�ֵ���");
			return false;
		}
			document.all.type.value = document.all.typeOperate[1].value;
		}
		
    	frm.submit();
    	document.all.add.disabled = true;
	}
  
}

function cancle_onclick(){
	document.location.href = "${basePath}/timebargain/apply/pledgeAppList.action";
}

 function suffixNamePress()
{
	
  if (event.keyCode<46 || event.keyCode>57 || event.keyCode == 47)  //|| (event.keyCode>=65 && event.keyCode<=90) || (event.keyCode>=97 && event.keyCode<=122)
  {
    event.returnValue=false;
  }
  else
  {
    event.returnValue=true;
  }
}

function check_readOnly(){
	/*
	if (document.all.typeOperate[0].checked) {
		document.all.quantity.value="";
		document.all.billFund.value="";
		setReadOnly(document.all.commodityName);
		setReadWrite(document.all.quantity);
		setReadOnly(document.all.firmId);
		setReadWrite(document.all.billFund);
	}else if (document.all.typeOperate[1].checked) {
		setReadOnly(document.all.commodityName);
		setReadOnly(document.all.quantity);
		setReadOnly(document.all.firmId);
		setReadOnly(document.all.billFund);
	}*/
}
//�ж������С��λ��
function mytrim(s){
	return s.replace(/(^\s*)|(\s*$)/g, "");
}
/**
 * �ж��Ƿ�������
 */
function integer(s){
	if(isEmpty(s)){
		return true;
	}
	var patrn=/^([1-9]{1}[0-9]*|0)$/;
	if (patrn.exec(s)) {
		return true ;
	}
	return false ;
}

/**
 * �ж��Ƿ�Ϊ���ַ���
 */
function isEmpty(s){
	if(mytrim(s+'').length<=0){
		return true;
	}
	return false;
}

function flote(s,n){
	if(!integer(n)){
		return false;
	}else if(isEmpty(n)){
	}else if(n<0){
		return false;
	}else if(n==0){
		return integer(s);
	}
	// var matchs='\^\\+\?([1-9]{1}[0-9]\*|0)(\\.[0-9]{1,'+n+'})\?\$';
	var matchs='\^\\+\?([0-9]\*|0)(\\.[0-9]{1,'+n+'})\?\$';
	var patrn = new RegExp(matchs,"ig");
	if (patrn.exec(s)) {
		return true ;
	}
	return false;
}
function A(){
	
	var a=document.all.quantity.value;
	if(!flote(a,2)){
		alert("С���������2λ");
		document.all.quantity.onfocus();
		return false;
		}
}
function B(){
	
	var b=document.all.billFund.value;
	if(!flote(b,2)){
		alert("С���������2λ");
		document.all.billFund.onfocus();
		return false;
		}
}
</script>
<script type="text/javascript">
var isBill = false;
$(function(){
	
	 $("#billID").bind("blur",function(){
		 // typeOperate �������ж� �û�ѡ�����ɾ��������Ӱ�ť�ġ�1Ϊ���2Ϊɾ����
		 var typeOperate = $(":radio:checked").val();
		 //ҳ������Ĳֵ��� 
    	 var billID = $("#billID").val();
    	$.ajax({   
      				  type : "POST",     //HTTP ���󷽷�,Ĭ��: "GET" 
      				  url : "../../ajaxcheck/demo/ajaxRequest.action",   //��������ĵ�ַ   
      				  data : "billID=" + billID+"&typeOperate="+typeOperate, //���͵�������������    
      				  dataType : "xml",  
      				  error: function(xml) { alert('Error loading XML document:' +xml); }, 
      				  success : function (data) {
          			//���ܷ�����������������   
      				  var billResult = $(data).find("billResult").text();
      				  var firmId = $(data).find("firmId").text();
      				  var commodityName = $(data).find("commodityName").text();
      				  var quantity =$(data).find("quantity").text();
      				  var billFund =$(data).find("billFund").text();
      				  var weight =$(data).find("weight").text();
      				  var frozenWeight =$(data).find("frozenWeight").text();
      				  //��������������������䵽ҳ��
      				 	if(billResult == 0){
      				 		$("#commodityName").val("");
							$("#firmId").val("");
							$("#quantity").val("");
							$("#billFund").val("");
							$("#res").html("û�ж�Ӧ�ֵ�!") ;
							isBill = false; 
						}	if(weight == 0){
							$("#res").html("û�ж�Ӧ�ֵ�!") ;
							isBill = false; 
						}
						else{
							//���
							$("#res").html("*") ;
							$("#commodityName").val(commodityName);
							$("#firmId").val(firmId);
							//ɾ��
							if (document.all.typeOperate[1].checked){
							$("#quantity").val(quantity);
							$("#billFund").val(billFund);}
							$("#weight").val(weight);
							$("#frozenWeight").val(frozenWeight);
							isBill = true;
							
						}
		 			}
    		})
	})
})
</script>
	</head>

	<body>
		<table border="0" height="80%" width="55%" align="center">
			<tr>
				<td>
					<form id="frm" name="frm" action="${basePath}/timebargain/apply/addPledgeApp.action"
						method="POST" class="form">
						<input name="weight" id="weight" value="" type="hidden"/>
						<input name="frozenWeight" id="frozenWeight" value="" type="hidden"/>
						<fieldset class="pickList" >
							<legend class="common">
								<b>
								  ������Ѻ�ʽ�
								</b>
							</legend>
							<table border="0" align="center" cellpadding="5" cellspacing="5"
								class="common">
								<tr>
									<td align="right">
											�������ͣ�
									</td>
									<td>
										<input type="radio" name="typeOperate" id="typeOperate" value="1" onclick="check_readOnly()" checked="checked" style="border:0px;"/>���
										<input type="radio" name="typeOperate" id="typeOperate" value="2" onclick="check_readOnly()" style="border:0px;"/>ɾ��
										<span class="required">*</span>
									</td>
									<td></td>
								</tr>
								<tr>
									<td align="right">
											�ֵ��ţ�
									</td>
									<td>
										<input type="text" name="billID" id="billID" maxlength="10" class="input_text" style="ime-mode:disabled" />
										<span class="required" id="res">*</span>
									</td>
									<td></td>
								</tr>
								
								<tr>
									<td align="right">
											Ʒ�����ƣ�
									</td>
									<td>
										<input type="text" name="commodityName" id="commodityName" class="input_text"/>
										<span class="required">*</span>
									</td>
									<td></td>
								</tr>
								<tr>
									<td align="right">
											��Ѻ������
									</td>
									<td>
										<input type="text" name="quantity" id="quantity" onkeypress="return suffixNamePress()" class="input_text" style="ime-mode:disabled" maxlength="10" onkeyup="this.value=this.value.replace(/\D/g,'')" />
										<span class="required">*</span>
									</td>
									<td></td>
								</tr>
								<tr>
									<td align="right">
											�����̴��룺
									</td>
									<td>
										<input type="text" id="firmId" name="firmId" class="input_text" style="ime-mode:disabled"/>
										<span class="required">*</span>
									</td>
									<td></td>
								</tr>	
								<tr>
									<td align="right">
											��Ѻ��
									</td>
									<td>
										<input type="text" name="billFund" id="billFund" style="ime-mode:disabled" class="input_text" maxlength="15"/>
										<span class="required">*</span>
									</td>
									<td></td>
								</tr>
															
								
								
																																														
								<tr>
									<td colspan="3" align="center">
									    <rightButton:rightButton name="�ύ" onclick="save_onclick();" className="anniu_btn" action="/timebargain/apply/addPledgeApp.action" id="add"></rightButton:rightButton>
									    &nbsp;&nbsp;
									    <rightButton:rightButton name="����" onclick="cancle_onclick();" className="anniu_btn" action="/timebargain/apply/pledgeAppList.action" id="back"></rightButton:rightButton>
										
									</td>
								</tr>
							</table>
						</fieldset>
						<input type="hidden" name="type"/>
					</form>
				</td>
			</tr>
		</table>

	</body>
</html>
