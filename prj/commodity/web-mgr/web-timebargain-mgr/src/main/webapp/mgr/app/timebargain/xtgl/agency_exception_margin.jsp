<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
  <head>
    <base target="_self"/>
    <link rel="stylesheet" href="${mgrPath}/skinstyle/default/css/app/common.css" type="text/css"/>
    <script src="${publicPath}/js/jquery-1.6.min.js" type="text/javascript"></script>
	<script type="text/javascript" src="${publicPath}/js/firmjson.js"></script>
	<script src="${publicPath }/js/validationengine/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="GBK"></script>
	<script src="${publicPath }/js/validationengine/jquery.validationEngine.js" type="text/javascript" charset="GBK"></script>
	<script type="text/javascript" src="${publicPath}/js/autocomplete/jquery.autocomplete.js"></script>
    <title>在线设置保证金</title>
    
	<script type="text/javascript"> 

//save
function save_onclick()
{
	if (confirm("您确定要提交吗？")) {
		if (document.forms(0).commodityID.value == "") {
			alert("商品代码不能为空！");
			return false;
		}
		if (document.forms(0).marginRate_B.value == "") {
			alert("买保证金不能为空！");
			return false;
		}
		if (document.forms(0).marginRate_S.value == "") {
			alert("卖保证金不能为空！");
			return false;
		}
		if (document.forms(0).marginAssure_B.value == "") {
			alert("买担保金不能为空！");
			return false;
		}
		if (document.forms(0).marginAssure_S.value == "") {
			alert("卖担保金不能为空！");
			return false;
		}
    	var updateDataUrl = $("#ok14").attr("action");
		$("#frm").attr("action",updateDataUrl);
		frm.submit();
	}
  
}


//commodity_onchange
function commodity_onchange(){
	var commodityId=document.forms(0).commodityID.value;
	if ( commodityId== "") {
		alert("商品代码不能为空！");
		document.getElementById("marginRate_B").value="";
		document.getElementById("marginRate_S").value="";
		document.getElementById("marginAssure_B").value="";
		document.getElementById("marginAssure_S").value="";
		document.getElementById("marginAlgr").value="";
		return false;
	}
	isCommodity();
    isPencent();
}
function isCommodity(){
	var commodityId=document.forms(0).commodityID.value;
	var oldAjaxAsync = $.ajaxSettings.async;
	var url = "${basePath}/timebargain/xtwh/ycclCommodity.action?id=" + commodityId;
	$.ajaxSettings.async = false;
	$.getJSON(url,null,function call(result){
		var value=obj2str(result);
    	var arrStr=value.split(",");
    	document.getElementById("marginRate_B").value=arrStr[0];
    	document.getElementById("marginRate_S").value=arrStr[1];
		document.getElementById("marginAssure_B").value=arrStr[2];
		document.getElementById("marginAssure_S").value=arrStr[3];
		document.getElementById("marginAlgr").value=arrStr[4];
	});
	$.ajaxSettings.async = oldAjaxAsync;
}

function isPencent(){
	if (document.forms(0).marginAlgr.value == "1") {
		document.getElementById("marginRate_BPercent").innerHTML = '%';
     	document.getElementById("marginRate_SPercent").innerHTML = '%';
     	document.getElementById("marginAssure_BPercent").innerHTML = '%';
     	document.getElementById("marginAssure_SPercent").innerHTML = '%';
	}else {
		document.getElementById("marginRate_BPercent").innerHTML = '';
     	document.getElementById("marginRate_SPercent").innerHTML = '';
     	document.getElementById("marginAssure_BPercent").innerHTML = '';
     	document.getElementById("marginAssure_SPercent").innerHTML = '';
	}
}
//Object转换成String
function obj2str(o){
   var r = [];
   if(typeof o == "string" || o == null) {
     return o;
   }
   if(typeof o == "object"){
     if(!o.sort){
       r[0]="{"
       for(var i in o){
         r[r.length]=i;
         r[r.length]=":";
         r[r.length]=obj2str(o[i]);
         r[r.length]=",";
       }
       r[r.length-1]="}"
     }else{
       r[0]=""
       for(var i =0;i<o.length;i++){
         r[r.length]=obj2str(o[i]);
         r[r.length]=",";
       }
       r[r.length-1]=""
     }
     return r.join("");
   }
   return o.toString();
}
function cancle_onclick(){
	window.close();
}
function dealcontent1(sel)
{
    document.forms[0].commodityID.value=sel.options[sel.selectedIndex].value;
}
function onlyNumberInput()
{
  if (event.keyCode<45 || event.keyCode>57 || event.keyCode == 47)
  {
    event.returnValue=false;
  }
}
</script>
	</head>

	<body>
		<table border="0" height="80%" width="55%" align="center">
			<tr>
				<td>
					<form id="frm" enctype="multipart/form-data" action="" targetType="hidden">
						<fieldset class="pickList" >
							<legend class="common">
								<b>
								  在线设置保证金
								</b>
							</legend>
							<table border="0" align="center" cellpadding="5" cellspacing="5"
								class="common">
								<tr>
									<td align="right">
											商品代码：
									</td>
									<td>
										<input type="hidden" id="commodityID"  name="commodityID" value=""/>
                                        <SELECT  name="select6" style="width:100px"  onchange="dealcontent1(this);commodity_onchange()">
								            <OPTION value="">请选择</OPTION>
								            <c:forEach items="${commodityList}" var="result">
									        	<option value="${result.commodityId}">${result.commodityId}</option>
										    </c:forEach>
								        </SELECT>   
										<span class="req">*</span>
									</td>
									<td></td>
								</tr>
								
								<tr>
									<td align="right">
										买保证金：
									</td>
									<td>
										<input type="text" id="marginRate_B" name="marginRate_B" maxlength="10" onkeypress="return onlyNumberInput()" style="ime-mode:disabled" styleClass="Number"/>
										<span id="marginRate_BPercent"></span><span class="req">*</span>
									</td>
									<td></td>
								</tr>
								<tr>
									<td align="right">
											卖保证金：
									</td>
									<td>
										<input type="text" id="marginRate_S" name="marginRate_S" maxlength="15" onkeypress="return onlyNumberInput()" style="ime-mode:disabled" styleClass="Number"/>
										<span id="marginRate_SPercent"></span><span class="req">*</span>
									</td>
									<td></td>
								</tr>								
								<tr>
									<td align="right">
											买担保金：
									</td>
									<td>
										<input type="text" id="marginAssure_B" name="marginAssure_B" maxlength="15" onkeypress="return onlyNumberInput()" style="ime-mode:disabled" styleClass="Number"/>
										<span id="marginAssure_BPercent"></span><span class="req">*</span>
									</td>
									<td></td>
								</tr>
								
								<tr>
									<td align="right">
											卖担保金：
									</td>
									<td>
										<input type="text" id="marginAssure_S" name="marginAssure_S" maxlength="15" onkeypress="return onlyNumberInput()" style="ime-mode:disabled" styleClass="Number"/>
										<span id="marginAssure_SPercent"></span><span class="req">*</span>
									</td>
									<td></td>
								</tr>																																							
								<tr>
									<td colspan="3" align="center">
										<rightButton:rightButton name="提交"   id="ok14" onclick="save_onclick();" className="button2" action="${basePath}/timebargain/xtwh/ycclEditOnlineMargin.action" ></rightButton:rightButton>
										<rightButton:rightButton name="关闭"   id="ok" onclick="cancle_onclick();" className="button2" action="${basePath}/timebargain/xtwh/yccl.action" ></rightButton:rightButton>
									</td>
								</tr>
							</table>
						</fieldset>
						<input type="hidden" id="marginAlgr" name="marginAlgr"/>
					</form>
				</td>
			</tr>
		</table>
	</body>
</html>
