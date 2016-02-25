<%@ include file="../../common/taglibs.jsp"%>
<%@ page pageEncoding="GBK"%>
<script language="javascript"src="<c:url value="/timebargain/public/jstools/jquery.js"/>"></script>
<html>
	<head>
		<title>
		</title>
		<script type="text/javascript"> 
function window_onload()
{
    highlightFormElements();
    pledgeForm.typeOperate[0].checked = true;
    check_readOnly();
    //alert(pledgeForm.typeOperate[0].value);
}
//save (/^[1-2]+\.\d{2}$/)保留两位小数的正则表达式 
function save_onclick()
{
	if (confirm("您确定要提交吗？")) {
	
		
		if (pledgeForm.typeOperate[0].checked) {
			if (pledgeForm.billID.value == "") {
			alert("仓单号不能为空！");
			return false;
		}if (pledgeForm.quantity.value >(pledgeForm.weight.value-pledgeForm.frozenWeight.value)) {
				alert("质押数量不能大于仓单可用数量！");
				return false;
			}
		if (pledgeForm.firmId.value == "") {
			alert("交易商代码不能为空！");
			return false;
		}
		if (pledgeForm.commodityName.value == "") {
			alert("品种名称不能为空！");
			return false;
		}
		if (pledgeForm.quantity.value == "") {
			alert("质押数量不能为空！");
			return false;
		}
		if (pledgeForm.quantity.value <= 0) {
			alert("质押数量应大于0！");
			return false;
		}
			pledgeForm.type.value = pledgeForm.typeOperate[0].value;
		}else if (pledgeForm.typeOperate[1].checked) {
			if (pledgeForm.billID.value == "") {
			alert("仓单号不能为空！");
			return false;
		}if (pledgeForm.quantity.value == "") {
			alert("没有对应仓单！");
			return false;
		}
			pledgeForm.type.value = pledgeForm.typeOperate[1].value;
		}
		
    	pledgeForm.submit();
    	pledgeForm.save.disabled = true;
	}
  
}


function cancle_onclick(){
	document.location.href = "<%=basePath%>/timebargain/pledge/app/pledgeApp.jsp";
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
	if (pledgeForm.typeOperate[0].checked) {
		pledgeForm.quantity.value="";
		pledgeForm.billFund.value="";
		setReadOnly(pledgeForm.commodityName);
		setReadWrite(pledgeForm.quantity);
		setReadOnly(pledgeForm.firmId);
		setReadWrite(pledgeForm.billFund);
	}else if (pledgeForm.typeOperate[1].checked) {
		setReadOnly(pledgeForm.commodityName);
		setReadOnly(pledgeForm.quantity);
		setReadOnly(pledgeForm.firmId);
		setReadOnly(pledgeForm.billFund);
	}
}
//判断输入的小数位数
function mytrim(s){
	return s.replace(/(^\s*)|(\s*$)/g, "");
}
/**
 * 判断是否是整数
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
 * 判断是否为空字符串
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
	
	var a=pledgeForm.quantity.value;
	if(!flote(a,2)){
		alert("小数后面最多2位");
		pledgeForm.quantity.onfocus();
		return false;
		}
}
function B(){
	
	var b=pledgeForm.billFund.value;
	if(!flote(b,2)){
		alert("小数后面最多2位");
		pledgeForm.billFund.onfocus();
		return false;
		}
}
</script>
<script type="text/javascript">
var isBill = false;
$(function(){
	 $("#billID").bind("blur",function(){
		 // typeOperate 是用来判断 用户选择的是删除或者添加按钮的【1为添加2为删除】
		 var typeOperate = $(":radio:checked").val();
		 //页面输入的仓单号 
    	 var billID = $("#billID").val();
    	$.ajax({   
      				  type : "POST",     //HTTP 请求方法,默认: "GET"   
      				  url : "<%=basePath%>/timebargain/pledge/app/valiAjaxByPledge.jsp",   //发送请求的地址   
      				  data : "billID=" + billID+"&typeOperate="+typeOperate, //发送到服务器的数据   
      				  dataType : "xml",         //预期服务器返回的数据类型   
      				  error: function(xml) { alert('Error loading XML document:' +xml); }, 
      				  success : function (data) {
          			//接受服务器传过来的数据   
      				  var billResult = $(data).find("billResult").text();
      				  var firmId = $(data).find("firmId").text();
      				  var commodityName = $(data).find("commodityName").text();
      				  var quantity =$(data).find("quantity").text();
      				  var billFund =$(data).find("billFund").text();
      				  var weight =$(data).find("weight").text();
      				  var frozenWeight =$(data).find("frozenWeight").text();
      				  //将服务器发来的数据填充到页面
      				 	if(billResult == 0){
      				 		$("#commodityName").val("");
							$("#firmId").val("");
							$("#quantity").val("");
							$("#billFund").val("");
							$("#res").html("没有对应仓单!") ;
							isBill = false; 
						}	if(weight == 0){
							$("#res").html("没有对应仓单!") ;
							isBill = false; 
						}
						else{
							//添加
							$("#res").html("*") ;
							$("#commodityName").val(commodityName);
							$("#firmId").val(firmId);
							//删除
							if (pledgeForm.typeOperate[1].checked){
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

	<body leftmargin="0" topmargin="0" onLoad="return window_onload()"
		onkeypress="keyEnter(event.keyCode);">
		<table border="0" height="80%" width="55%" align="center">
			<tr>
				<td>
					<form id="pledgeForm" action="<%=basePath%>/timebargain/pledgeAppSave.spr"
						method="POST" class="form" target="mainFrame">
						<input name="weight" id="weight" value="" type="hidden"/>
						<input name="frozenWeight" id="frozenWeight" value="" type="hidden"/>
						<fieldset class="pickList" >
							<legend class="common">
								<b>
								  设置质押资金
								</b>
							</legend>
							<table border="0" align="center" cellpadding="5" cellspacing="5"
								class="common">
								<tr>
									<td align="right">
											操作类型：
									</td>
									<td>
										<input type="radio" name="typeOperate" id="typeOperate" value="1" onclick="check_readOnly()" style="border:0px;"/>添加
										<input type="radio" name="typeOperate" id="typeOperate" value="2" onclick="check_readOnly()" style="border:0px;"/>删除
										<span class="req">*</span>
									</td>
									<td></td>
								</tr>
								<tr>
									<td align="right">
											仓单号：
									</td>
									<td>
										<input type="text" name="billID" id="billID" maxlength="10"  style="ime-mode:disabled" />
										<span class="req" id="res">*</span>
									</td>
									<td></td>
								</tr>
								
								<tr>
									<td align="right">
											品种名称：
									</td>
									<td>
										<input type="text" name="commodityName" id="commodityName"/>
										<span class="req">*</span>
									</td>
									<td></td>
								</tr>
								<tr>
									<td align="right">
											质押数量：
									</td>
									<td>
										<input type="text" name="quantity" id="quantity" onkeypress="return suffixNamePress()" style="ime-mode:disabled" maxlength="10" onkeyup="this.value=this.value.replace(/\D/g,'')" />
										<span class="req">*</span>
									</td>
									<td></td>
								</tr>
								<tr>
									<td align="right">
											交易商代码：
									</td>
									<td>
										<input type="text" id="firmId" name="firmId"  style="ime-mode:disabled"/>
										<span class="req">*</span>
									</td>
									<td></td>
								</tr>	
								<tr>
									<td align="right">
											质押金额：
									</td>
									<td>
										<input type="text" name="billFund" id="billFund" onkeypress="return numberPass()" style="ime-mode:disabled" maxlength="15"/>
										<span class="req">*</span>
									</td>
									<td></td>
								</tr>
															
								
								
																																														
								<tr>
									<td colspan="3" align="center">
										<input type="button" name="save" value="提交" class="button"
											onclick="javascript:return save_onclick();"/>
											
										<input type="button" name="save" value="返回" class="button"
											onclick="javascript:return cancle_onclick();"/>
											
									</td>
								</tr>
							</table>
						</fieldset>
						<input type="hidden" name="type"/>
					</form>
				</td>
			</tr>
		</table>

		<%@ include file="../../common/messages.jsp"%>
	</body>
</html>
