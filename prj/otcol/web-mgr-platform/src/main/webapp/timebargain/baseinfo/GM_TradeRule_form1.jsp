<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK"%>
<%@ page import="java.util.*"%>
<%@ page import="gnnt.MEBS.timebargain.manage.model.LabelValue"%>

		<%
             String type=(String)request.getAttribute("type");
        %>
<html>
	<head>
		<LINK REL="stylesheet" type="text/css"
			href="<c:url value="/timebargain/styles/common.css"/>" />
		<script language="JavaScript"
			src="<c:url value="/timebargain/scripts/global.js"/>"></script>
			<script type="text/javascript" src="<c:url value="/timebargain/widgets/dwr/interface/commodityManager.js"/>"></script>
			
		    <script type="text/javascript" src="<c:url value="/timebargain/widgets/dwr/engine.js"/>"></script>
		    <script type="text/javascript" src="<c:url value="/timebargain/widgets/dwr/util.js"/>"></script>	
		<title>
		</title>
		<style type="text/css">
<!--
.yin {
	visibility:hidden;
	position:absolute;
	
}
.xian{
	visibility:visible;
}
-->
</style>
		<script type="text/javascript"> 
function window_onload()
{
    highlightFormElements();
    if(tradeRuleForm.crud.value == "create")
    {
      tradeRuleForm.groupID.focus();
    }
    else if(tradeRuleForm.crud.value == "update")
    {

      setReadOnly(tradeRuleForm.groupID);
      setReadOnly(tradeRuleForm.commodityID);
      tradeRuleForm.marginAlgr.focus();
    }
}
function on_change(){
	tradeRuleForm.forceCloseFeeAlgr.value =  tradeRuleForm.feeAlgr.value;
}
//save
function save_onclick()
{
	if (confirm("您确定要提交吗？")) {
		if(tradeRuleForm.type[1].checked ==true)
      {
	    if(trim(tradeRuleForm.marginRate_S.value) == "")
      {
	      alert("<fmt:message key="commodityForm.MarginRate_S"/><fmt:message key="prompt.required"/>");
	      tradeRuleForm.marginRate_S.focus();
	      return false;
      }  
       if(trim(tradeRuleForm.marginAssure_S.value) == "")
      {
	      alert("<fmt:message key="commodityForm.MarginAssure_S"/><fmt:message key="prompt.required"/>");
	      tradeRuleForm.marginAssure_S.focus();
	      return false;
      }
       if(tradeRuleForm.historyCloseFeeRate_B.value==""){
    	alert("<fmt:message key="formatError"/>");
      tradeRuleForm.historyCloseFeeRate_B.focus();
      return false;
    } 
    if(tradeRuleForm.historyCloseFeeRate_S.value==""){
    	alert("<fmt:message key="formatError"/>");
      tradeRuleForm.historyCloseFeeRate_S.focus();
      return false;
    }     
      }

   	 if (tradeRuleForm.crud.value=="create") {
    	if(trim(tradeRuleForm.groupID.value) == "")
    {
      alert("<fmt:message key="customerForm.GroupID"/><fmt:message key="prompt.required"/>");
      tradeRuleForm.groupID.focus();
      return false;
    }  
    
    if(trim(tradeRuleForm.commodityID.value) == "")
    {
      alert("<fmt:message key="commodityForm.Name"/><fmt:message key="prompt.required"/>");
      tradeRuleForm.groupID.focus();
      return false;
    } 
    }
    
     
	if(tradeRuleForm.todayCloseFeeRate_B.value==""){
    	alert("<fmt:message key="formatError"/>");
      tradeRuleForm.todayCloseFeeRate_B.focus();
      return false;
    }  
    if(tradeRuleForm.todayCloseFeeRate_S.value==""){
    	alert("<fmt:message key="formatError"/>");
      tradeRuleForm.todayCloseFeeRate_S.focus();
      return false;
    }  
	 if(tradeRuleForm.historyCloseFeeRate_B.value==""){
    	alert("<fmt:message key="formatError"/>");
      tradeRuleForm.historyCloseFeeRate_B.focus();
      return false;
    } 
    if(tradeRuleForm.historyCloseFeeRate_S.value==""){
    	alert("<fmt:message key="formatError"/>");
      tradeRuleForm.historyCloseFeeRate_S.focus();
      return false;
    }     
    
    
    if (tradeRuleForm.settleFeeRate_B.value == "") {
    	alert("交收买不能为空！");
    	tradeRuleForm.settleFeeRate_B.focus();
    	return false;
    }
    if (tradeRuleForm.settleFeeRate_S.value == "") {
    	alert("交收卖不能为空！");
    	tradeRuleForm.settleFeeRate_S.focus();
    	return false;
    }
    if (tradeRuleForm.forceCloseFeeAlgr.value == "") {
    	alert("强平算法不能为空！");
    	return false;
    }
    if (tradeRuleForm.forceCloseFeeRate_B.value == "") {
    	alert("强平买不能为空！");
    	tradeRuleForm.forceCloseFeeRate_B.focus();
    	return false;
    }
    if (tradeRuleForm.forceCloseFeeRate_S.value == "") {
    	alert("强平卖不能为空！");
    	tradeRuleForm.forceCloseFeeRate_S.focus();
    	return false;
    }
    if (tradeRuleForm.feeAlgr.value == "") {
    	alert("手续费算法不能为空！");
    	tradeRuleForm.feeAlgr.focus();
    	return false;
    }
    
    
    if (tradeRuleForm.marginAlgr.value == "") {
    	alert("保证金算法不能为空！");
    	tradeRuleForm.marginAlgr.focus();
    	return false;
    }
    if (tradeRuleForm.marginRate_B.value == "") {
    	alert("买保证金不能为空！");
    	tradeRuleForm.marginRate_B.focus();
    	return false;
    }
    if (tradeRuleForm.marginAssure_B.value == "") {
    	alert("买担保金不能为空！");
    	tradeRuleForm.marginAssure_B.focus();
    	return false;
    }
    
    if (tradeRuleForm.crud.value=="update") {
    	setReadWrite(tradeRuleForm.groupID);
    	setReadWrite(tradeRuleForm.commodityID);
    	
    }else if (tradeRuleForm.crud.value == "create") {
    	setEnabled(tradeRuleForm.groupID);
    	setEnabled(tradeRuleForm.commodityID);
    }
    
    tradeRuleForm.submit();
   	if (tradeRuleForm.crud.value=="update") {
    	setReadOnly(tradeRuleForm.groupID);
    	setReadOnly(tradeRuleForm.commodityID);
    }else if (tradeRuleForm.crud.value == "create") {
    	setDisabled(tradeRuleForm.groupID);
    	setDisabled(tradeRuleForm.commodityID);
    }
    
    tradeRuleForm.save.disabled = true;

	}
  
}
//cancel
function cancel_onclick()
{
   document.location.href = "<c:url value="/timebargain/baseinfo/tradeRule.do?funcflg=searchGroup"/>";
}

//commodity_onchange
function commodity_onchange()
{
  var commodity = tradeRuleForm.commodityID.value;
  if(commodity == "")
  {
    return false;
  }
  commodityManager.getCommodityById(commodity,function(commodity)
  {
    if(commodity != null)
    {
      /*if(!tmp_paraminfo_up)
      {
        paraminfo_onclick();
      }*/
      tradeRuleForm.marginAlgr.value = commodity.marginAlgr;
      tradeRuleForm.feeRate_B.value = commodity.feeRate_B;
      tradeRuleForm.marginRate_B.value = commodity.marginRate_B;
      tradeRuleForm.marginRate_S.value = commodity.marginRate_S;
      tradeRuleForm.marginAssure_B.value = commodity.marginAssure_B;
      tradeRuleForm.marginAssure_S.value = commodity.marginAssure_S;
      tradeRuleForm.feeAlgr.value = commodity.feeAlgr;
      tradeRuleForm.feeRate_S.value = commodity.feeRate_S;
      
      tradeRuleForm.settleFeeAlgr.value = commodity.settleFeeAlgr;
      tradeRuleForm.settleFeeRate_B.value = commodity.settleFeeRate_B;
      tradeRuleForm.settleFeeRate_S.value = commodity.settleFeeRate_S;
      tradeRuleForm.forceCloseFeeAlgr.value = commodity.forceCloseFeeAlgr;
      tradeRuleForm.forceCloseFeeRate_B.value = commodity.forceCloseFeeRate_B;
      tradeRuleForm.forceCloseFeeRate_S.value = commodity.forceCloseFeeRate_S;
      
      
      
      if(commodity.marginRate_B!=commodity.marginRate_S||commodity.marginAssure_B!=commodity.marginAssure_S)
      {
      //tradeRuleForm.type.value = 2;
      tradeRuleForm.type[1].checked ==true
      changeManner(2);
      }
      else
      {
      //tradeRuleForm.type.value = 1;
      tradeRuleForm.type[0].checked ==true
      changeManner(1);
      }
      tradeRuleForm.marginItem1.value = commodity.marginItem1;
      tradeRuleForm.marginItem2.value = commodity.marginItem2;
      tradeRuleForm.marginItem3.value = commodity.marginItem3;
      tradeRuleForm.marginItem4.value = commodity.marginItem4;
      tradeRuleForm.marginItemAssure1.value = commodity.marginItemAssure1;
      tradeRuleForm.marginItemAssure2.value = commodity.marginItemAssure2;
      tradeRuleForm.marginItemAssure3.value = commodity.marginItemAssure3;
      tradeRuleForm.marginItem1_S.value = commodity.marginItem1_S;
      tradeRuleForm.marginItem2_S.value = commodity.marginItem2_S;
      tradeRuleForm.marginItem3_S.value = commodity.marginItem3_S;
      tradeRuleForm.marginItem4_S.value = commodity.marginItem4_S;
      tradeRuleForm.marginItemAssure1_S.value = commodity.marginItemAssure1_S;
      tradeRuleForm.marginItemAssure2_S.value = commodity.marginItemAssure2_S;
      tradeRuleForm.marginItemAssure3_S.value = commodity.marginItemAssure3_S;
      tradeRuleForm.todayCloseFeeRate_B.value = commodity.todayCloseFeeRate_B;
      tradeRuleForm.todayCloseFeeRate_S.value = commodity.todayCloseFeeRate_S;
      tradeRuleForm.historyCloseFeeRate_B.value = commodity.historyCloseFeeRate_B;
      tradeRuleForm.historyCloseFeeRate_S.value = commodity.historyCloseFeeRate_S;
      
      tradeRuleForm.settleMarginAlgr_B.value = commodity.settleMarginAlgr_B;
      tradeRuleForm.settleMarginAlgr_S.value = commodity.settleMarginAlgr_S;
      tradeRuleForm.payoutAlgr.value = commodity.payoutAlgr;
      tradeRuleForm.payoutRate.value = commodity.payoutRate;
      if(commodity.marginItem1!=commodity.marginItem1_S||commodity.marginItemAssure1!=commodity.marginItemAssure1_S)
      {
      //tradeRuleForm.type1.value = 2;
      tradeRuleForm.type1[1].checked =true
      changeManner1(2);
      }
      else
      {
      //tradeRuleForm.type1.value = 1;
      tradeRuleForm.type1[0].checked =true
      changeManner1(1);
      }
      if(commodity.marginItem2!=commodity.marginItem2_S||commodity.marginItemAssure2!=commodity.marginItemAssure2_S)
      {
      //tradeRuleForm.type2.value = 2;
      tradeRuleForm.type2[1].checked =true
      changeManner2(2);
      }
      else
      {
      //tradeRuleForm.type2.value = 1;
      tradeRuleForm.type2[0].checked =true
      changeManner2(1);
      }
      if(commodity.marginItem3!=commodity.marginItem3_S||commodity.marginItemAssure3!=commodity.marginItemAssure3_S)
      {
      //tradeRuleForm.type3.value = 2;
      tradeRuleForm.type3[1].checked =true
      changeManner3(2);
      }
      else
      {
      //tradeRuleForm.type3.value = 1;
      tradeRuleForm.type3[0].checked =true
      changeManner3(1);
      }
      if(commodity.marginItem4!=commodity.marginItem4_S)
      {
      //tradeRuleForm.type4.value = 2;
      //tradeRuleForm.type4[1].checked =true
      //changeManner4(2);
      }
      else
      {
      //tradeRuleForm.type4.value = 1;
      //tradeRuleForm.type4[0].checked =true
      //changeManner4(1);
      }
      
    }
    else
    {
      alert("<fmt:message key="commodityForm.breedNotExist" />");
    }
  });   
}


/*function changeManner(id){ 
  var td1 = window.td1;
  var td2 = window.td2;
  if(id==1)
  {
   td1.innerHTML='<fmt:message key="commodityForm.MarginRate"/>：';
   td2.innerHTML='<fmt:message key="commodityForm.MarginAssure"/>：';
   document.getElementById("abc1").style.display='none';
   document.getElementById("abc2").style.display='none';
   document.getElementById("abc3").style.display='none';
   document.getElementById("abc4").style.display='none';
  }
  else if(id==2)
  {
   td1.innerHTML='<fmt:message key="commodityForm.MarginRate_B"/>：';
   td2.innerHTML='<fmt:message key="commodityForm.MarginAssure_B"/>：';
   document.getElementById("abc1").style.display='inline';
   document.getElementById("abc2").style.display='inline';
   document.getElementById("abc3").style.display='inline';
   document.getElementById("abc4").style.display='inline';
  }
  
} 

function changeManner1(id){ 
  var td1 = window.td3;
  var td2 = window.td4;
  if(id==1)
  {
   td1.innerHTML='<fmt:message key="commodityForm.MarginItem11"/>：';
   td2.innerHTML='<fmt:message key="commodityForm.MarginItemAssure11"/>：';
   document.getElementById("abc11").style.display='none';
   document.getElementById("abc21").style.display='none';
   document.getElementById("abc31").style.display='none';
   document.getElementById("abc41").style.display='none';
  }
  else if(id==2)
  {
   td1.innerHTML='<fmt:message key="commodityForm.MarginItem1"/>：';
   td2.innerHTML='<fmt:message key="commodityForm.MarginItemAssure1"/>：';
   document.getElementById("abc11").style.display='inline';
   document.getElementById("abc21").style.display='inline';
   document.getElementById("abc31").style.display='inline';
   document.getElementById("abc41").style.display='inline';
  }
  
} 

function changeManner2(id){ 
  var td1 = window.td5;
  var td2 = window.td6;
  if(id==1)
  {
   td1.innerHTML='<fmt:message key="commodityForm.MarginItem21"/>：';
   td2.innerHTML='<fmt:message key="commodityForm.MarginItemAssure21"/>：';
   document.getElementById("abc12").style.display='none';
   document.getElementById("abc22").style.display='none';
   document.getElementById("abc32").style.display='none';
   document.getElementById("abc42").style.display='none';
  }
  else if(id==2)
  {
   td1.innerHTML='<fmt:message key="commodityForm.MarginItem2"/>：';
   td2.innerHTML='<fmt:message key="commodityForm.MarginItemAssure2"/>：';
   document.getElementById("abc12").style.display='inline';
   document.getElementById("abc22").style.display='inline';
   document.getElementById("abc32").style.display='inline';
   document.getElementById("abc42").style.display='inline';
  }
  
} 

function changeManner3(id){ 
  var td1 = window.td7;
  var td2 = window.td8;
  if(id==1)
  {
   td1.innerHTML='<fmt:message key="commodityForm.MarginItem31"/>：';
   td2.innerHTML='<fmt:message key="commodityForm.MarginItemAssure31"/>：';
   document.getElementById("abc13").style.display='none';
   document.getElementById("abc23").style.display='none';
   document.getElementById("abc33").style.display='none';
   document.getElementById("abc43").style.display='none';
  }
  else if(id==2)
  {
   td1.innerHTML='<fmt:message key="commodityForm.MarginItem3"/>：';
   td2.innerHTML='<fmt:message key="commodityForm.MarginItemAssure3"/>：';
   document.getElementById("abc13").style.display='inline';
   document.getElementById("abc23").style.display='inline';
   document.getElementById("abc33").style.display='inline';
   document.getElementById("abc43").style.display='inline';
  }
  
} 

function changeManner4(id){ 
  var td1 = window.td9;
  var td2 = window.td0;
  if(id==1)
  {
   td1.innerHTML='<fmt:message key="commodityForm.MarginItem41"/>：';
   td2.innerHTML='<fmt:message key="commodityForm.MarginItemAssure41"/>：';
   document.getElementById("abc14").style.display='none';
   document.getElementById("abc24").style.display='none';
   document.getElementById("abc34").style.display='none';
   document.getElementById("abc44").style.display='none';
  }
  else if(id==2)
  {
   td1.innerHTML='<fmt:message key="commodityForm.MarginItem4"/>：';
   td2.innerHTML='<fmt:message key="commodityForm.MarginItemAssure4"/>：';
   document.getElementById("abc14").style.display='inline';
   document.getElementById("abc24").style.display='inline';
   document.getElementById("abc34").style.display='inline';
   document.getElementById("abc44").style.display='inline';
  }
  
} */

function changeManner(id){ 
  var td1 = window.td1;
  var td2 = window.td2;
  if(id==1)
  {
   td1.innerHTML='保证金1：';
   td2.innerHTML='担保金1：';
   document.getElementById("abc1").style.visibility = "hidden";
   document.getElementById("abc4").style.visibility = "hidden";
   document.getElementById("aaa").className = "yin";
  }
  else if(id==2)
  {
   td1.innerHTML='买保证金1：';
   td2.innerHTML='买担保金1：';
   document.getElementById("abc1").style.visibility = "visible";
   document.getElementById("abc4").style.visibility = "visible";
   document.getElementById("aaa").className = "xian";
  }
  
} 


function changeManner12(id){ 
  var td1 = window.td12;

  if(id==1)
  {
   td1.innerHTML='交收货款：';

   //document.getElementById("abc1").style.display='none';
   document.getElementById("abcd1").style.visibility = "hidden";
   //document.getElementById("abc3").style.display='none';
  // document.getElementById("abc4").style.visibility = "hidden";
   document.getElementById("bbb").className = "yin";
  }
  else if(id==2)
  {
   td1.innerHTML='    买交收货款：';

   //document.getElementById("abc1").style.display='inline';
   document.getElementById("abcd1").style.visibility = "visible";
   //document.getElementById("abc3").style.display='inline';
   //document.getElementById("abc4").style.visibility = "visible";
   document.getElementById("bbb").className = "xian";
  }
  
} 

function changeManner1(id){ 
  var td1 = window.td3;
  var td2 = window.td4;
  if(id==1)
  {
   td1.innerHTML='保证金2：';
   td2.innerHTML='担保金2：';
   document.getElementById("abc11").style.visibility='hidden';
   document.getElementById("abc41").style.visibility='hidden';
   document.getElementById("aaa1").className = "yin";
  }
  else if(id==2)
  {
   td1.innerHTML='买保证金2：';
   td2.innerHTML='买担保金2：';
   document.getElementById("abc11").style.visibility='visible';
   document.getElementById("abc41").style.visibility='visible';
   document.getElementById("aaa1").className = "xian";
  }
  
} 

function changeManner2(id){ 
  var td1 = window.td5;
  var td2 = window.td6;
  if(id==1)
  {
   td1.innerHTML='保证金3：';
   td2.innerHTML='担保金3：';
   document.getElementById("abc12").style.visibility='hidden';
   document.getElementById("abc42").style.visibility='hidden';
   document.getElementById("aaa2").className = "yin";
  }
  else if(id==2)
  {
   td1.innerHTML='买保证金3：';
   td2.innerHTML='买担保金3：';
   document.getElementById("abc12").style.visibility='visible';
   document.getElementById("abc42").style.visibility='visible';
   document.getElementById("aaa2").className = "xian";
  }
  
} 

function changeManner3(id){ 
  var td1 = window.td7;
  var td2 = window.td8;
  if(id==1)
  {
   td1.innerHTML='保证金4：';
   td2.innerHTML='担保金4：';
   document.getElementById("abc23").style.visibility='hidden';
   document.getElementById("abc43").style.visibility='hidden';
   document.getElementById("aaa3").className = "yin";
  }
  else if(id==2)
  {
   td1.innerHTML='买保证金4：';
   td2.innerHTML='买担保金4：';
   document.getElementById("abc23").style.visibility='visible';
   document.getElementById("abc43").style.visibility='visible';
   document.getElementById("aaa3").className = "xian";
  }
  
} 
/*
function changeManner4(id){ 
  var td1 = window.td9;
  var td2 = window.td0;
  if(id==1)
  {
   td1.innerHTML='<fmt:message key="commodityForm.MarginItem41"/>：';
   td2.innerHTML='<fmt:message key="commodityForm.MarginItemAssure41"/>：';
   //document.getElementById("abc14").style.visibility='hidden';
   document.getElementById("abc24").style.visibility='hidden';
   //document.getElementById("abc34").style.visibility='hidden';
   document.getElementById("abc44").style.visibility='hidden';
   document.getElementById("aaa4").className = "yin";
  }
  else if(id==2)
  {
   td1.innerHTML='<fmt:message key="commodityForm.MarginItem4"/>：';
   td2.innerHTML='<fmt:message key="commodityForm.MarginItemAssure4"/>：';
   //document.getElementById("abc14").style.visibility='visible';
   document.getElementById("abc24").style.visibility='visible';
  //document.getElementById("abc34").style.visibility='visible';
   document.getElementById("abc44").style.visibility='visible';
   document.getElementById("aaa4").className = "xian";
  }
  
} */

//---------------------------start baseinfo-------------------------------
var tmp_baseinfo;
var tmp_baseinfo_up = true;
function baseinfo_onclick()
{
  if (tmp_baseinfo_up)
  {
    tmp_baseinfo_up = false;
    tradeRuleForm.baseinfo_img.src = "<c:url value="/timebargain/images/ctl_detail_Down.gif"/>";
    tmp_baseinfo = baseinfo4.innerHTML;
    baseinfo4.innerHTML = "";
  }
  else
  {
    tmp_baseinfo_up = true;
    tradeRuleForm.baseinfo_img.src = "<c:url value="/timebargain/images/ctl_detail_Up.gif"/>";
    baseinfo4.innerHTML = tmp_baseinfo;
  }
}

var tmp_baseinfo2;
var tmp_baseinfo_up2 = true;
function baseinfo2_onclick()
{
  if (tmp_baseinfo_up2)
  {
    tmp_baseinfo_up2 = false;
    tradeRuleForm.baseinfo_img2.src = "<c:url value="/timebargain/images/ctl_detail_Down.gif"/>";
    tmp_baseinfo2 = baseinfo2.innerHTML;
    baseinfo2.innerHTML = "";
  }
  else
  {
    tmp_baseinfo_up2 = true;
    tradeRuleForm.baseinfo_img2.src = "<c:url value="/timebargain/images/ctl_detail_Up.gif"/>";
    baseinfo2.innerHTML = tmp_baseinfo2;
  }
}

var tmp_baseinfo3;
var tmp_baseinfo_up3 = true;
function baseinfo3_onclick()
{
  if (tmp_baseinfo_up3)
  {
    tmp_baseinfo_up3 = false;
    tradeRuleForm.baseinfo_img3.src = "<c:url value="/timebargain/images/ctl_detail_Down.gif"/>";
    tmp_baseinfo3 = baseinfo3.innerHTML;
    baseinfo3.innerHTML = "";
  }
  else
  {
    tmp_baseinfo_up3 = true;
    tradeRuleForm.baseinfo_img3.src = "<c:url value="/timebargain/images/ctl_detail_Up.gif"/>";
    baseinfo3.innerHTML = tmp_baseinfo3;
  }
}


//-----------------------------end baseinfo-----------------------------
//---------------------------start settleinfo-------------------------------
var tmp_settleinfo2;
var tmp_settleinfo_up2 = true;
function settleinfo2_onclick()
{
  if (tmp_settleinfo_up2)
  {
    tmp_settleinfo_up2 = false;
    tradeRuleForm.settleinfo_img2.src = "<c:url value="/timebargain/images/ctl_detail_Down.gif"/>";
    tmp_settleinfo2 = settleinfo2.innerHTML;
    settleinfo2.innerHTML = "";
  }
  else
  {
    tmp_settleinfo_up2 = true;
    tradeRuleForm.settleinfo_img2.src = "<c:url value="/timebargain/images/ctl_detail_Up.gif"/>";
    settleinfo2.innerHTML = tmp_settleinfo2;
  }
}


var tmp_settleinfo;
var tmp_settleinfo_up = true;
function settleinfo_onclick()
{
  if (tmp_settleinfo_up)
  {
    tmp_settleinfo_up = false;
    tradeRuleForm.settleinfo_img.src = "<c:url value="/timebargain/images/ctl_detail_Down.gif"/>";
    tmp_settleinfo = settleinfo.innerHTML;
    settleinfo.innerHTML = "";
  }
  else
  {
    tmp_settleinfo_up = true;
    tradeRuleForm.settleinfo_img.src = "<c:url value="/timebargain/images/ctl_detail_Up.gif"/>";
    settleinfo.innerHTML = tmp_settleinfo;
  }
}
function changeb(){
    if(tradeRuleForm.todayCloseFeeRate_B.value==""){
        tradeRuleForm.todayCloseFeeRate_B.value = tradeRuleForm.feeRate_B.value;
        tradeRuleForm.historyCloseFeeRate_B.value = tradeRuleForm.feeRate_B.value;
    }
 }
 function changes(){
    if(tradeRuleForm.todayCloseFeeRate_S.value==""){
        tradeRuleForm.todayCloseFeeRate_S.value = tradeRuleForm.feeRate_S.value;
        tradeRuleForm.historyCloseFeeRate_S.value = tradeRuleForm.feeRate_S.value;
    }   
 }
//-----------------------------end settleinfo-----------------------------
</script>
	</head>

	<body leftmargin="0" topmargin="0" onLoad="return window_onload()"
		onkeypress="keyEnter(event.keyCode);">
		<table border="0" height="400" width="790" align="center">
			<tr>
				<td>
					<html:form action="/timebargain/baseinfo/tradeRule.do?funcflg=saveGroup"
						method="POST" styleClass="form">
						<tr class="common">
					          <td colspan="4">
					            <fieldset>
					              <legend>
					                
					                  
					                    <b>设置特殊客户信息</b>
					                    
					              </legend>
					<span id="baseinfo">
							<table width="100" border="0" align="center"  class="common" cellpadding="0" cellspacing="2">
					<!-- 基本信息 -->
					        <tr class="common">
					          <td colspan="4">
					            <fieldset>
					              <legend>
					                <table cellspacing="0" cellpadding="0" border="0" width="700" class="common">
					                  <col width="65"></col><col></col><col width="6"></col>
					                  <tr>
					                    <td><b>基本信息</b></td>
					                    <td><hr width="629" class="pickList"/></td>
					                    <td ><img id="baseinfo_img" src="<c:url value="/timebargain/images/ctl_detail_Up.gif"/>" style="cursor:hand" onclick="javascript:baseinfo_onclick()"/></td>
					                  </tr>
					                </table>
					              </legend>
					<span id="baseinfo4">
					<table cellSpacing="0" cellPadding="0" width="700" border="0" align="center" class="common">
								<tr>
								<td>&nbsp;&nbsp;&nbsp;</td>
								<td>&nbsp;&nbsp;&nbsp;</td>
								<td>&nbsp;&nbsp;&nbsp;</td>
								<td>&nbsp;&nbsp;&nbsp;</td>
								<td></td>
								
									<td align="right">
											组ID：
									</td>
									
									<c:if test="${param['crud'] == 'update'}">
									<td width="120">
									<%
										String groupName = (String)request.getAttribute("groupName");
										String groupID = (String)request.getAttribute("groupID");
									%>
									<%=groupName%>
									<html:hidden property="groupID" value="<%=groupID%>"/>
									</td>
									</c:if>
									
									<c:if test="${param['crud'] == 'create'}">
									<td>
										<html:select property="groupID"  style="width:80">
                                          <html:options collection="customerGroupSelect" property="value" labelProperty="label"/>
                                        </html:select>
										<span class="req">*</span>
									</td>
									</c:if>
									
									
									<td align="right">
											商品名称：
									</td>
									<c:if test="${param['crud'] == 'create'}">
										<td>
										<html:select property="commodityID"  style="width:80" onchange="javascript:commodity_onchange()">
                                          <html:options collection="commoditySelect" property="value" labelProperty="label"/>
                                        </html:select>
										<span class="req">*</span>
									</td>
									</c:if>
									<c:if test="${param['crud'] == 'update'}">
									<td>
									<%
										String commodityID = (String)request.getAttribute("commodityID");
										String name = (String)request.getAttribute("name");
										System.out.println("================================="+commodityID);
									%>
										<%=name%>
										<html:hidden property="commodityID" value="<%=commodityID%>"/>
										</td>
									</c:if>
									
									<td align="right">
											&nbsp;
									</td>
									<td>
										&nbsp;
									</td>
									
									<td>&nbsp;&nbsp;</td>
									<td>&nbsp;&nbsp;</td>
									<td>&nbsp;&nbsp;</td>
									<td>&nbsp;&nbsp;</td>
									<td>&nbsp;&nbsp;</td><td>&nbsp;&nbsp;</td><td>&nbsp;&nbsp;</td>
									<td>&nbsp;&nbsp;</td><td>&nbsp;&nbsp;</td>
									<td>&nbsp;&nbsp;</td>
									<td>&nbsp;&nbsp;</td>
									<td>&nbsp;&nbsp;</td>
									<td>&nbsp;&nbsp;</td>
									<td>&nbsp;&nbsp;&nbsp;</td>
									<td>&nbsp;&nbsp;</td>
									<td>&nbsp;</td>
								</tr>
							</table >
						</span>
						            </fieldset>
						          </td>
						        </tr>	
		
		
						        
			
		<tr class="common">
					          <td colspan="4">
					            <fieldset>
					              <legend>
					                <table cellspacing="0" cellpadding="0" border="0" width="700" class="common">
					                  <col width="55"></col><col></col><col width="6"></col>
					                  <tr>
					                    <td><b>手续费</b></td>
					                    <td><hr width="639" class="pickList"/></td>
					                    <td ><img id="baseinfo_img2" src="<c:url value="/timebargain/images/ctl_detail_Up.gif"/>" style="cursor:hand" onclick="javascript:baseinfo2_onclick()"/></td>
					                  </tr>
					                </table>
					              </legend>
					<span id="baseinfo2">
					<table cellSpacing="0" cellPadding="0" width="700" border="0" align="center" class="common" >

         	<tr> 		            
		            	
		            <td   rowspan="4" width="250" height="10" valign="top"><div align="right"><span >
					
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;手续费算法：<html:select property="feeAlgr" style="width:80" onchange="on_change()">
					<html:option value=""></html:option>
				    <html:option value="1">按百分比</html:option>
					<html:option value="2">按绝对值</html:option>
			   </html:select> 
			   <span class="req">*</span>  
			</span></div></td>	            		            
		            <td  rowspan="1" width="450" height="5"><div align="left"><span >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;开仓买：<html:text size="10" property="feeRate_B" maxlength="11" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" onchange="changeb()"/>
			  <span class="req">*</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;开仓卖：<html:text size="10" property="feeRate_S" maxlength="11" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" onchange="changes()"/>
			  <span class="req">*</span>  </span></div></td>
		            
		          </tr>


				  <tr> 		            
		           <td width="450"  height="5"><div align="left"><span >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;平历史仓买：<html:text size="10" property="historyCloseFeeRate_B" maxlength="600" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" />&nbsp;<span class="req">*</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;平历史仓卖：<html:text size="10" property="historyCloseFeeRate_S" maxlength="11" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" />&nbsp;<span class="req">*</span></span></div></td>
		          </tr>

					 <tr> 		            
		           <td width="450"  height="5"><div align="left"><span >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;平今仓买：<html:text size="10" property="todayCloseFeeRate_B" maxlength="600" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" />&nbsp;<span class="req">*</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;平今仓卖：<html:text size="10" property="todayCloseFeeRate_S" maxlength="11" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" />&nbsp;<span class="req">*</span></span></div></td>
		            </tr>
		            
		            <tr>
		            <td align="left" ><div><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;强平买：<html:text size="10" property="forceCloseFeeRate_B" maxlength="11" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" onchange="changeb()"/>
			  <span class="req">*</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;强平卖：<html:text size="10" property="forceCloseFeeRate_S" maxlength="11" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" onchange="changes()"/>
			  <span class="req">*</span> </span></div>
            </td>
		            </tr>
	
<tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>

								 

        <tr>
	 <td align="right" >交收手续费算法：
            
				<html:select property="settleFeeAlgr" style="width:80">
					<html:option value=""></html:option>
				    <html:option value="1"><fmt:message key="commodityForm.FeeAlgr.option.percent"/></html:option>
					<html:option value="2">按绝对值</html:option>
			   </html:select> 
			   <span class="req">*</span>            
            </td>
     <td align="left" ><div><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;交收买：<html:text size="10" property="settleFeeRate_B" maxlength="11" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" onchange="changeb()"/>
			  <span class="req">*</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;交收卖：<html:text size="10" property="settleFeeRate_S" maxlength="11" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" onchange="changes()"/><span class="req">*</span> </span></div>
            </td>
            
		</tr>
               
  
           
            </table>
      </span>
		 </fieldset>
			 </td>
				 </tr>
               
       
       
       
         
									
						        	
								<tr class="common">
          <td colspan="4">
            <fieldset>
              <legend>
                <table cellspacing="0" cellpadding="0" border="0" width="700" class="common">
                  <col width="65"></col><col></col><col width="6"></col>
                  <tr>
                    <td><b>保证金</b></td>
                    <td><hr width="629" class="pickList"/></td>
                    <td ><img id="settleinfo_img" src="<c:url value="/timebargain/images/ctl_detail_Up.gif"/>" style="cursor:hand" onclick="javascript:settleinfo_onclick()"/></td>
                  </tr>
                </table>
              </legend>
<span id="settleinfo">
<table cellSpacing="0" cellPadding="0" width="700" border="0" align="center" class="common">  
            <tr>
            <td align="right" width="150">保证金算法：</td>
            <td width="100">
				<html:select property="marginAlgr" style="width:80">
					<html:option value=""></html:option>
				    <html:option value="1">按百分比</html:option>
					<html:option value="2">按绝对值</html:option>
			   </html:select> 
			   <span class="req">*</span>            
            </td>       
              
              <td width="100">&nbsp;&nbsp;&nbsp;&nbsp;</td>
            </tr>
            
            <tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
            
            <tr>
            <td align="right" width="150">
            	第一阶段买卖保证金：
            </td>
            <td colspan="3">
            
             <input type="radio" name="type" value="1" onclick="changeManner(1);" <%if("1".equals(type)){out.println("checked");} %> style="border:0px;">相同
             
             <input type="radio" name="type" value="2" onclick="changeManner(2);" <%if("2".equals(type)){out.println("checked");} %> style="border:0px;">不同
            </td >
            </tr>
            
            <tr>
           
            
            
             <td align="right" width="150" id="td1"><%if("1".equals(type)){%>保证金1<%}else{%>买保证金1<%}%>：</td>
            <td width="100">
			  <html:text size="10" property="marginRate_B" maxlength="11" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" />
			  <span class="req">*</span>           
            </td>
            <td align="right" id="td2"><%if("1".equals(type)){%>担保金1<%}else{%>买担保金1<%}%>：</td>
            <td>
			  <html:text size="10" property="marginAssure_B" maxlength="11" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" />
			  <span class="req">*</span>           
            </td>     
            </tr>
             <tr class="<%if("1".equals(type)){%>yin<%}else{%>xian<%}%>" id="aaa">
             
            <td align="right"  id="abc2">卖保证金1：</td>
            <td  id="abc1" style="visibility:<%if("1".equals(type)){%>hidden<%}else{%>visible<%}%>;">
			  <html:text size="10" property="marginRate_S" maxlength="11"  onkeypress="return onlyNumberInput()" styleClass="text" />
			  <span class="req">*</span>           
            </td >
            <td align="right"  id="abc3">
            卖担保金1：</td>
            <td  id="abc4" style="visibility:<%if("1".equals(type)){%>hidden<%}else{%>visible<%}%>;">
			  <html:text size="10" property="marginAssure_S" maxlength="11"  onkeypress="return onlyNumberInput()" styleClass="text" />
			  <span class="req">*</span>           
            </td>
        </tr>
            
            <tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
            
            <%
             String type1=(String)request.getAttribute("type1");
             %>            
        <tr>
			<td align="right" width="150">
            第二阶段买卖保证金：
            </td>
			<td colspan="3" >
            
             <input type="radio" name="type1" value="1" onclick="changeManner1(1);" <%if("1".equals(type1)){out.println("checked");} %> style="border:0px;">相同
             
             <input type="radio" name="type1" value="2" onclick="changeManner1(2);" <%if("2".equals(type1)){out.println("checked");} %> style="border:0px;">不同
            </td >
			         
        </tr>
        <tr>
        <td align="right" width="150" id="td3"><%if("1".equals(type1)){%>保证金2<%}else{%>买保证金2<%}%>：</td>
            <td width="100" align="left">
			  <html:text size="10" property="marginItem1" maxlength="16" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" />         
            </td> 
            <td align="right"  id="td4">&nbsp;&nbsp;<%if("1".equals(type1)){%>担保金2<%}else{%>买担保金2<%}%>：</td>
            <td width="325" align="left">
			  <html:text size="10" property="marginItemAssure1" maxlength="16" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" />         
            </td>  
        </tr>
        <tr class="<%if("1".equals(type1)){%>yin<%}else{%>xian<%}%>" id="aaa1">
			<td align="right"  id="abc21">&nbsp;&nbsp;卖保证金2：</td>
            <td style="visibility:<%if("1".equals(type1)){%>hidden<%}else{%>visible<%}%>;" id="abc11">
			  <html:text size="10" property="marginItem1_S" maxlength="16" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" />         
            </td> 
            <td align="right" id="abc31">&nbsp;&nbsp;卖担保金2：</td>
            <td style="visibility:<%if("1".equals(type1)){%>hidden<%}else{%>visible<%}%>;" id="abc41">
			  <html:text size="10" property="marginItemAssure1_S" maxlength="16" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" />         
            </td>           
        </tr>
          <%
             String type2=(String)request.getAttribute("type2");
             %>
             
             <tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
             
        <tr>
 			<td align="right">
            第三阶段买卖保证金:
            </td>
			<td colspan="3">
            
             <input type="radio" name="type2" value="1" onclick="changeManner2(1);" <%if("1".equals(type2)){out.println("checked");} %> style="border:0px;">相同
             
             <input type="radio" name="type2" value="2" onclick="changeManner2(2);" <%if("2".equals(type2)){out.println("checked");} %> style="border:0px;">不同
            </td >
            </tr>
            <tr >
			<td align="right" id="td5"><%if("1".equals(type2)){%>保证金3<%}else{%>买保证金3<%}%>：</td>
            <td>
			  <html:text size="10" property="marginItem2" maxlength="16" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" />        
            </td>
            <td align="right" id="td6"><%if("1".equals(type2)){%>担保金3<%}else{%>买担保金3<%}%>：</td>
            <td>
			  <html:text size="10" property="marginItemAssure2" maxlength="16" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" />        
            </td>
            </tr>   
             <tr class="<%if("1".equals(type2)){%>yin<%}else{%>xian<%}%>" id="aaa2">
			<td align="right" id="abc22">卖保证金3：</td>
            <td style="visibility:<%if("1".equals(type2)){%>hidden<%}else{%>visible<%}%>;" id="abc12">
			  <html:text size="10" property="marginItem2_S" maxlength="16" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" />        
            </td>
            <td align="right"  id="abc32">卖担保金3：</td>
            <td style="visibility:<%if("1".equals(type2)){%>hidden<%}else{%>visible<%}%>;" id="abc42">
			  <html:text size="10" property="marginItemAssure2_S" maxlength="16" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" />        
            </td>
            </tr>   
         <%
             String type3=(String)request.getAttribute("type3");
             %>
             
             <tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
             
        <tr>
			<td align="right">
            第四阶段买卖保证金：
            </td>
			<td colspan="3" >
            <!-- <select name="type" onChange="changeManner(this.value);">
             <option value="1" <%if("1".equals(type1)){out.println("selected");} %>>相同</option>
             <option value="2" <%if("2".equals(type1)){out.println("selected");} %>><fmt:message key="commodityForm.type2"/></option>
             </select>-->
             <input type="radio" name="type3" value="1" onclick="changeManner3(1);" <%if("1".equals(type3)){out.println("checked");} %> style="border:0px;">相同
             
             <input type="radio" name="type3" value="2" onclick="changeManner3(2);" <%if("2".equals(type3)){out.println("checked");} %> style="border:0px;">不同
            </td >
			</tr>
			<tr>
            <td align="right" id="td7"><%if("1".equals(type3)){%>保证金4<%}else{%>买保证金4<%}%>：</td>
            <td >
			  <html:text size="10" property="marginItem3" maxlength="16" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" />           
            </td>
            <td align="right" id="td8"><%if("1".equals(type3)){%>担保金4<%}else{%>买担保金4<%}%>：</td>
            <td >
			  <html:text size="10" property="marginItemAssure3" maxlength="16" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" />           
            </td>
        </tr>
         <tr class="<%if("1".equals(type3)){%>yin<%}else{%>xian<%}%>" id="aaa3">
            <td align="right" id="abc13">卖保证金4：</td>
            <td  id="abc23" style="visibility:<%if("1".equals(type3)){%>hidden<%}else{%>visible<%}%>;">
			  <html:text size="10" property="marginItem3_S" maxlength="16" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" />           
            </td>
            <td align="right" id="abc33">卖担保金4：</td>
            <td  id="abc43" style="visibility:<%if("1".equals(type3)){%>hidden<%}else{%>visible<%}%>;">
			  <html:text size="10" property="marginItemAssure3_S" maxlength="16" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" />           
            </td>
        </tr>
          <%
             String type4=(String)request.getAttribute("type4");
             %>
             
             <tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
             
        <tr>
			<td align="right">
            交收保证金：
            </td>
            <td>&nbsp;</td>
        </tr>   
        
          <tr>
        	
            <td align="right" id="td9">买方方式：</td>
            <td>
            <html:select property="settleMarginAlgr_B" style="width:80">
					<html:option value=""></html:option>
				    <html:option value="1">按百分比</html:option>
					<html:option value="2">按绝对值</html:option>
			   </html:select>
            <span class="req">*</span>
            </td>
            <td align="right" id="td0">买方标准：</td>
            <td>
			  <html:text size="10" property="marginItem4" maxlength="16" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" />         
            
            <span class="req">*</span>
            </td>
        </tr>
             
        <tr id="aaa4">
             
            <td align="right"  id="abc14">卖方方式：</td>
            <td  >
            <html:select property="settleMarginAlgr_S" style="width:80">
					<html:option value=""></html:option>
				    <html:option value="1">按百分比</html:option>
					<html:option value="2">按绝对值</html:option>
			   </html:select>
            <span class="req">*</span>
            </td>
            <td align="right"  id="abc34">卖方标准：</td>
            <td>
			  <html:text size="10" property="marginItem4_S" maxlength="16" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" />         
            <span class="req">*</span>
            </td>
        </tr>
        <tr>
             
            <td align="right">交收货款算法：</td>
            <td  >
            <html:select property="payoutAlgr" style="width:80">
					<html:option value=""></html:option>
				    <html:option value="1">按百分比</html:option>
					<html:option value="2">按绝对值</html:option>
			   </html:select>
            <span class="req">*</span>
            </td>
            <td align="right">交收货款标准：</td>
            <td>
			  <html:text size="10" property="payoutRate" maxlength="16" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" />         
            <span class="req">*</span>
            </td>
        </tr>
               
</table >
</span>
            </fieldset>
          </td>
        </tr>          
								 
								<tr>
									<td colspan="2" align="center">
										<html:button property="save" styleClass="button"
											onclick="javascript:return save_onclick();">
											提交
										</html:button>
										<html:button property="cancel" styleClass="button"
											onclick="javascript:return cancel_onclick();">
											返回
										</html:button>
									</td>
								</tr>
								
							</table>
						</span>
						            </fieldset>
						          </td>
						        </tr>	
						<html:hidden property="crud" />
						<html:hidden property="forceCloseFeeAlgr"/>
					</html:form>
				</td>
			</tr>
		</table>
		 <table cellSpacing="0" cellPadding="0" width="700" border="0" align="center" class="common">
		     <tr>
			 <td colspan="4" align="left">
			 	<span class="req">说明：百分比用小数表示。例 0.2表示20%</span>
			 </td>
			</tr>
			</table>
		<html:javascript formName="tradeRuleForm" cdata="false"
			dynamicJavascript="true" staticJavascript="false" />
		<script type="text/javascript"
			src="<html:rewrite page="/timebargain/common/validator.jsp"/>"></script>
		<%@ include file="/timebargain/common/messages.jsp"%>
	</body>
</html>
