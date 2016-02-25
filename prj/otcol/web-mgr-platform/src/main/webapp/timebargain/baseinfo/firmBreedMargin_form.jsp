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
		    <script language="javascript" src="<%=request.getContextPath()%>/delivery/public/jstools/jquery.js"></script>		
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
    if(tradeBreedRuleForm.crud.value == "create")
    {
      tradeBreedRuleForm.firmID.focus();
    }
    else if(tradeBreedRuleForm.crud.value == "update")
    {

      setReadOnly(tradeBreedRuleForm.firmID);
      setReadOnly(tradeBreedRuleForm.breedID);
      tradeBreedRuleForm.marginAlgr.focus();
    }
    
    marginAlgr_change();
}
//function on_change(){
//	tradeBreedRuleForm.forceCloseFeeAlgr.value =  tradeBreedRuleForm.feeAlgr.value;
//}
//save
function save_onclick()
{
	if (confirm("您确定要提交吗？")) {
	
	    if (!tmp_settleinfo_up) {
	    	settleinfo_onclick();
	    }
	    if (!tmp_baseinfo_up) {
	    	baseinfo_onclick();
	    }
		if(tradeBreedRuleForm.type[1].checked ==true)
      {
	    if(trim(tradeBreedRuleForm.marginItem1_S.value) == "")
      {
	      alert("卖保证金系数不能为空！");
	      tradeBreedRuleForm.marginItem1_S.focus();
	      return false;
      }  
       if(trim(tradeBreedRuleForm.marginItemAssure1_S.value) == "")
      {
	      alert("卖担保金系数不能为空！");
	      tradeBreedRuleForm.marginItemAssure1_S.focus();
	      return false;
      }
      }
      
      if(tradeBreedRuleForm.type1[1].checked ==true)
	    {
		    if(trim(tradeBreedRuleForm.marginItem2_S.value) == "")
	      {
	      		tradeBreedRuleForm.marginItem2_S.value=0;
	      }  
	       if(trim(tradeBreedRuleForm.marginItemAssure2_S.value) == "")
	      {
		      tradeBreedRuleForm.marginItemAssure2_S.value=0;
	      }
      	}
      	if(tradeBreedRuleForm.type2[1].checked ==true)
	    {
		    if(trim(tradeBreedRuleForm.marginItem3_S.value) == "")
	      {
		      tradeBreedRuleForm.marginItem3_S.value=0;
	      }  
	       if(trim(tradeBreedRuleForm.marginItemAssure3_S.value) == "")
	      {
		      tradeBreedRuleForm.marginItemAssure3_S.value=0;
	      }
      	}
      	if(tradeBreedRuleForm.type3[1].checked ==true)
	    {
		    if(trim(tradeBreedRuleForm.marginItem4_S.value) == "")
	      {
		      tradeBreedRuleForm.marginItem4_S.value=0;
	      }  
	       if(trim(tradeBreedRuleForm.marginItemAssure4_S.value) == "")
	      {
		      tradeBreedRuleForm.marginItemAssure4_S.value=0;
	      }
      	}
      	if(tradeBreedRuleForm.type4[1].checked ==true)
	    {
		    if(trim(tradeBreedRuleForm.marginItem5_S.value) == "")
	      {
		      tradeBreedRuleForm.marginItem5_S.value=0;
	      }  
	       if(trim(tradeBreedRuleForm.marginItemAssure5_S.value) == "")
	      {
		      tradeBreedRuleForm.marginItemAssure5_S.value=0;
	      }
      	}

   	if (tradeBreedRuleForm.crud.value=="create") {
   	   
   	  if (tradeBreedRuleForm.firmID.value == "") {
    	alert("交易商代码不能为空！");
    	tradeBreedRuleForm.firmID.focus();
      	return false;
      }
    
      if(trim(tradeBreedRuleForm.breedID.value) == "")
      {
        alert("品种代码不能为空！");
        tradeBreedRuleForm.breedID.focus();
        return false;
      } 
    }
    
    if (tradeBreedRuleForm.marginAlgr.value == "") {
    	alert("保证金算法不能为空！");
    	tradeBreedRuleForm.marginAlgr.focus();
    	return false;
    }
    if (tradeBreedRuleForm.marginItem1.value == "") {
    	alert("买保证金不能为空！");
    	tradeBreedRuleForm.marginItem1.focus();
    	return false;
    }
    if (tradeBreedRuleForm.marginItemAssure1.value == "") {
    	alert("买担保金不能为空！");
    	tradeBreedRuleForm.marginItemAssure1.focus();
    	return false;
    }
    
    if (tradeBreedRuleForm.marginItem2.value == "") {
    	tradeBreedRuleForm.marginItem2.value=0;
    }
    if (tradeBreedRuleForm.marginItemAssure2.value == "") {
    	tradeBreedRuleForm.marginItemAssure2.value=0;
    }
    if (tradeBreedRuleForm.marginItem3.value == "") {
    	tradeBreedRuleForm.marginItem3.value=0;
    }
    if (tradeBreedRuleForm.marginItemAssure3.value == "") {
    	tradeBreedRuleForm.marginItemAssure3.value=0;
    }
    if (tradeBreedRuleForm.marginItem4.value == "") {
    	tradeBreedRuleForm.marginItem4.value=0;
    }
    if (tradeBreedRuleForm.marginItemAssure4.value == "") {
    	tradeBreedRuleForm.marginItemAssure4.value=0;
    }
    if (tradeBreedRuleForm.marginItem5.value == "") {
    	tradeBreedRuleForm.marginItem5.value=0;
    }
    if (tradeBreedRuleForm.marginItemAssure5.value == "") {
    	tradeBreedRuleForm.marginItemAssure5.value=0;
    }
    
    
    if (tradeBreedRuleForm.settleMarginRate_B.value == "") {
    	alert("买方标准不能为空！");
    	tradeBreedRuleForm.settleMarginRate_B.focus();
    	return false;
    }
    if (tradeBreedRuleForm.settleMarginRate_S.value == "") {
    	alert("卖方标准不能为空！");
    	tradeBreedRuleForm.settleMarginRate_S.focus();
    	return false;
    }
    
    if (tradeBreedRuleForm.settleMarginAlgr_B.value == "") {
    	alert("买方方式不能为空！");
    	tradeBreedRuleForm.settleMarginAlgr_B.focus();
    	return false;
    }
    if (tradeBreedRuleForm.settleMarginAlgr_S.value == "") {
    	alert("卖方方式不能为空！");
    	tradeBreedRuleForm.settleMarginAlgr_S.focus();
    	return false;
    }
    if (tradeBreedRuleForm.payoutRate.value == "") {
    	alert("交收货款系数不能为空！");
    	tradeBreedRuleForm.payoutRate.focus();
    	return false;
    }
    
    if (tradeBreedRuleForm.payoutAlgr.value == "") {
    	alert("交收货款算法不能为空！");
    	tradeBreedRuleForm.payoutAlgr.focus();
    	return false;
    }
    
    if (tradeBreedRuleForm.crud.value=="update") {
    	setReadWrite(tradeBreedRuleForm.firmID);
    	setReadWrite(tradeBreedRuleForm.breedID);
    	
    }else if (tradeBreedRuleForm.crud.value == "create") {
    	setReadWrite(tradeBreedRuleForm.firmID);
    	setEnabled(tradeBreedRuleForm.breedID);
    }
    if(confirm("是否级联变动该品种下商品的交易商特殊保证金？")){
    
    	tradeBreedRuleForm.action="<c:url value='/timebargain/baseinfo/tradeBreedRule.do?funcflg=saveFirmBreedMargin&logos=true'/>";
		tradeBreedRuleForm.submit();
	}else{
		tradeBreedRuleForm.submit();
	}
   	if (tradeBreedRuleForm.crud.value=="update") {
    	setReadOnly(tradeBreedRuleForm.firmID);
    	setReadOnly(tradeBreedRuleForm.breedID);
    }else if (tradeBreedRuleForm.crud.value == "create") {
    	setReadOnly(tradeBreedRuleForm.firmID);
    	setDisabled(tradeBreedRuleForm.breedID);
    }
    
    tradeBreedRuleForm.save.disabled = true;

	}
  
}
//cancel
function cancel_onclick()
{
   document.location.href = "<c:url value='/timebargain/baseinfo/tradeBreedRule.do?funcflg=searchFirmBreedMargin'/>";
}

//commodity_onchange
function commodity_onchange()
{
  var commodity = tradeBreedRuleForm.breedID.value;
  if(commodity == "")
  {
    return false;
  }
  commodityManager.getCommodityById(commodity,function(commodity)
  {
    if(commodity != null)
    {
      tradeBreedRuleForm.marginAlgr.value = commodity.marginAlgr;
      
      
      if(commodity.marginItem1!=commodity.marginItem1_S||commodity.marginItemAssure1!=commodity.marginItemAssure1_S)
      {
      //tradeBreedRuleForm.type.value = 2;
      //alert(commodity.marginItem1);
      tradeBreedRuleForm.type[1].checked =true
      changeManner(2);
      }
      else
      {
      //tradeBreedRuleForm.type.value = 1;
      tradeBreedRuleForm.type[0].checked =true
      changeManner(1);
      }
      
      
      if (commodity.marginAlgr == "1") {
      	
      	  tradeBreedRuleForm.marginItem1.value = (commodity.marginItem1 * 100);
      	  formatControl2Precision(tradeBreedRuleForm.marginItem1);
	      tradeBreedRuleForm.marginItem2.value = (commodity.marginItem2 * 100);
	      formatControl2Precision(tradeBreedRuleForm.marginItem2);
	      tradeBreedRuleForm.marginItem3.value = (commodity.marginItem3 * 100);
	      formatControl2Precision(tradeBreedRuleForm.marginItem3);
	      tradeBreedRuleForm.marginItem4.value = (commodity.marginItem4 * 100);
	      formatControl2Precision(tradeBreedRuleForm.marginItem4);
	      tradeBreedRuleForm.marginItem5.value = (commodity.marginItem5 * 100);
	      formatControl2Precision(tradeBreedRuleForm.marginItem5);
	      tradeBreedRuleForm.marginItemAssure1.value = (commodity.marginItemAssure1 * 100);
	      formatControl2Precision(tradeBreedRuleForm.marginItemAssure1);
	      tradeBreedRuleForm.marginItemAssure2.value = (commodity.marginItemAssure2 * 100);
	      formatControl2Precision(tradeBreedRuleForm.marginItemAssure2);
	      tradeBreedRuleForm.marginItemAssure3.value = (commodity.marginItemAssure3 * 100);
	      formatControl2Precision(tradeBreedRuleForm.marginItemAssure3);
	      tradeBreedRuleForm.marginItemAssure4.value = (commodity.marginItemAssure4 * 100);
	      formatControl2Precision(tradeBreedRuleForm.marginItemAssure4);
	      tradeBreedRuleForm.marginItemAssure5.value = (commodity.marginItemAssure5 * 100);
	      formatControl2Precision(tradeBreedRuleForm.marginItemAssure5);
	      tradeBreedRuleForm.marginItem1_S.value = (commodity.marginItem1_S * 100);
	      formatControl2Precision(tradeBreedRuleForm.marginItem1_S);
	      tradeBreedRuleForm.marginItem2_S.value = (commodity.marginItem2_S * 100);
	      formatControl2Precision(tradeBreedRuleForm.marginItem2_S);
	      tradeBreedRuleForm.marginItem3_S.value = (commodity.marginItem3_S * 100);
	      formatControl2Precision(tradeBreedRuleForm.marginItem3_S);
	      tradeBreedRuleForm.marginItem4_S.value = (commodity.marginItem4_S * 100);
	      formatControl2Precision(tradeBreedRuleForm.marginItem4_S);
	      tradeBreedRuleForm.marginItem5_S.value = (commodity.marginItem5_S * 100);
	      formatControl2Precision(tradeBreedRuleForm.marginItem5_S);
	      tradeBreedRuleForm.marginItemAssure1_S.value = (commodity.marginItemAssure1_S * 100);
	      formatControl2Precision(tradeBreedRuleForm.marginItemAssure1_S);
	      tradeBreedRuleForm.marginItemAssure2_S.value = (commodity.marginItemAssure2_S * 100);
	      formatControl2Precision(tradeBreedRuleForm.marginItemAssure2_S);
	      tradeBreedRuleForm.marginItemAssure3_S.value = (commodity.marginItemAssure3_S * 100);
	      formatControl2Precision(tradeBreedRuleForm.marginItemAssure3_S);
	      tradeBreedRuleForm.marginItemAssure4_S.value = (commodity.marginItemAssure4_S * 100);
	      formatControl2Precision(tradeBreedRuleForm.marginItemAssure4_S);
	      tradeBreedRuleForm.marginItemAssure5_S.value = (commodity.marginItemAssure5_S * 100);
	      formatControl2Precision(tradeBreedRuleForm.marginItemAssure5_S);
	      tradeBreedRuleForm.payoutRate.value = (commodity.payoutRate * 100);
	      formatControl2Precision(tradeBreedRuleForm.payoutRate);
	      tradeBreedRuleForm.settleMarginRate_B.value = (commodity.settleMarginRate_B * 100);
	      formatControl2Precision(tradeBreedRuleForm.settleMarginRate_B);
          tradeBreedRuleForm.settleMarginRate_S.value = (commodity.settleMarginRate_S * 100);
          formatControl2Precision(tradeBreedRuleForm.settleMarginRate_S);
	      
      }else {
      
      	  tradeBreedRuleForm.marginItem1.value = commodity.marginItem1;
	      tradeBreedRuleForm.marginItem2.value = commodity.marginItem2;
	      tradeBreedRuleForm.marginItem3.value = commodity.marginItem3;
	      tradeBreedRuleForm.marginItem4.value = commodity.marginItem4;
	      tradeBreedRuleForm.marginItem5.value = commodity.marginItem5;
	      tradeBreedRuleForm.marginItemAssure1.value = commodity.marginItemAssure1;
	      tradeBreedRuleForm.marginItemAssure2.value = commodity.marginItemAssure2;
	      tradeBreedRuleForm.marginItemAssure3.value = commodity.marginItemAssure3;
	      tradeBreedRuleForm.marginItemAssure4.value = commodity.marginItemAssure4;
	      tradeBreedRuleForm.marginItemAssure5.value = commodity.marginItemAssure5;
	      tradeBreedRuleForm.marginItem1_S.value = commodity.marginItem1_S;
	      tradeBreedRuleForm.marginItem2_S.value = commodity.marginItem2_S;
	      tradeBreedRuleForm.marginItem3_S.value = commodity.marginItem3_S;
	      tradeBreedRuleForm.marginItem4_S.value = commodity.marginItem4_S;
	      tradeBreedRuleForm.marginItem5_S.value = commodity.marginItem5_S;
	      tradeBreedRuleForm.marginItemAssure1_S.value = commodity.marginItemAssure1_S;
	      tradeBreedRuleForm.marginItemAssure2_S.value = commodity.marginItemAssure2_S;
	      tradeBreedRuleForm.marginItemAssure3_S.value = commodity.marginItemAssure3_S;
	      tradeBreedRuleForm.marginItemAssure4_S.value = commodity.marginItemAssure4_S;
	      tradeBreedRuleForm.marginItemAssure5_S.value = commodity.marginItemAssure5_S;
	      tradeBreedRuleForm.payoutRate.value = commodity.payoutRate;
	      tradeBreedRuleForm.settleMarginRate_B.value = commodity.settleMarginRate_B;
      	  tradeBreedRuleForm.settleMarginRate_S.value = commodity.settleMarginRate_S;
      }
      
      
      
      tradeBreedRuleForm.payoutAlgr.value = commodity.payoutAlgr;
      tradeBreedRuleForm.settleMarginAlgr_B.value = commodity.settleMarginAlgr_B;
      tradeBreedRuleForm.settleMarginAlgr_S.value = commodity.settleMarginAlgr_S;
      
      marginAlgr_change();
      settleMarginAlgr_B_change();
      settleMarginAlgr_S_change();
      payoutAlgr_change();
      
      if(commodity.marginItem1!=commodity.marginItem1_S||commodity.marginItemAssure1!=commodity.marginItemAssure1_S)
      {
      //tradeBreedRuleForm.type1.value = 2;
      tradeBreedRuleForm.type1[1].checked =true
      changeManner1(2);
      }
      else
      {
      //tradeBreedRuleForm.type1.value = 1;
      tradeBreedRuleForm.type1[0].checked =true
      changeManner1(1);
      }
      if(commodity.marginItem2!=commodity.marginItem2_S||commodity.marginItemAssure2!=commodity.marginItemAssure2_S)
      {
      //tradeBreedRuleForm.type2.value = 2;
      tradeBreedRuleForm.type2[1].checked =true
      changeManner2(2);
      }
      else
      {
      //tradeBreedRuleForm.type2.value = 1;
      tradeBreedRuleForm.type2[0].checked =true
      changeManner2(1);
      }
      if(commodity.marginItem3!=commodity.marginItem3_S||commodity.marginItemAssure3!=commodity.marginItemAssure3_S)
      {
      //tradeBreedRuleForm.type3.value = 2;
      tradeBreedRuleForm.type3[1].checked =true
      changeManner3(2);
      }
      else
      {
      //tradeBreedRuleForm.type3.value = 1;
      tradeBreedRuleForm.type3[0].checked =true
      changeManner3(1);
      }
      if(commodity.marginItem4!=commodity.marginItem4_S)
      {
      //tradeBreedRuleForm.type4.value = 2;
      //tradeBreedRuleForm.type4[1].checked =true
      //changeManner4(2);
      }
      else
      {
      //tradeBreedRuleForm.type4.value = 1;
      //tradeBreedRuleForm.type4[0].checked =true
      //changeManner4(1);
      }
      
    }
    else
    {
      alert("此商品不存在！");
    }
  });   
}



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

function changeManner4(id){
  var td1 = window.td50;
  var td2 = window.td60;
  if(id==1)
  {
   td1.innerHTML='保证金5：';
   td2.innerHTML='担保金5：';
   document.getElementById("abc24").style.visibility='hidden';
   document.getElementById("abc44").style.visibility='hidden';
   document.getElementById("aaa4").className = "yin";
  }
  else if(id==2)
  {
   td1.innerHTML='买保证金5：';
   td2.innerHTML='买担保金5：';
   document.getElementById("abc24").style.visibility='visible';
   document.getElementById("abc44").style.visibility='visible';
   document.getElementById("aaa4").className = "xian";
  }
  
} 

//---------------------------start baseinfo-------------------------------
var tmp_baseinfo;
var tmp_baseinfo_up = true;
function baseinfo_onclick()
{
  if (tmp_baseinfo_up)
  {
    tmp_baseinfo_up = false;
    tradeBreedRuleForm.baseinfo_img.src = "<c:url value="/timebargain/images/ctl_detail_Down.gif"/>";
    tmp_baseinfo = baseinfo4.innerHTML;
    baseinfo4.innerHTML = "";
  }
  else
  {
    tmp_baseinfo_up = true;
    tradeBreedRuleForm.baseinfo_img.src = "<c:url value="/timebargain/images/ctl_detail_Up.gif"/>";
    baseinfo4.innerHTML = tmp_baseinfo;
  }
}



var tmp_baseinfo3;
var tmp_baseinfo_up3 = true;
function baseinfo3_onclick()
{
  if (tmp_baseinfo_up3)
  {
    tmp_baseinfo_up3 = false;
    tradeBreedRuleForm.baseinfo_img3.src = "<c:url value="/timebargain/images/ctl_detail_Down.gif"/>";
    tmp_baseinfo3 = baseinfo3.innerHTML;
    baseinfo3.innerHTML = "";
  }
  else
  {
    tmp_baseinfo_up3 = true;
    tradeBreedRuleForm.baseinfo_img3.src = "<c:url value="/timebargain/images/ctl_detail_Up.gif"/>";
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
    tradeBreedRuleForm.settleinfo_img2.src = "<c:url value="/timebargain/images/ctl_detail_Down.gif"/>";
    tmp_settleinfo2 = settleinfo2.innerHTML;
    settleinfo2.innerHTML = "";
  }
  else
  {
    tmp_settleinfo_up2 = true;
    tradeBreedRuleForm.settleinfo_img2.src = "<c:url value="/timebargain/images/ctl_detail_Up.gif"/>";
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
    tradeBreedRuleForm.settleinfo_img.src = "<c:url value="/timebargain/images/ctl_detail_Down.gif"/>";
    tmp_settleinfo = settleinfo.innerHTML;
    settleinfo.innerHTML = "";
  }
  else
  {
    tmp_settleinfo_up = true;
    tradeBreedRuleForm.settleinfo_img.src = "<c:url value="/timebargain/images/ctl_detail_Up.gif"/>";
    settleinfo.innerHTML = tmp_settleinfo;
  }
}


function settleMarginAlgr_B_change(){
	if (tradeBreedRuleForm.settleMarginAlgr_B.value == "1") {
		document.getElementById("settleMarginRate_BPercent").className = "xian";
	}else {
		document.getElementById("settleMarginRate_BPercent").className = "yin";
	}
}


function settleMarginAlgr_S_change(){
	if (tradeBreedRuleForm.settleMarginAlgr_S.value == "1") {
		document.getElementById("settleMarginRate_SPercent").className = "xian";
	}else {
		document.getElementById("settleMarginRate_SPercent").className = "yin";
	}
}

function payoutAlgr_change(){
	if (tradeBreedRuleForm.payoutAlgr.value == "1") {
		document.getElementById("payoutRatePercent").className = "xian";
	}else {
		document.getElementById("payoutRatePercent").className = "yin";
	}
}

function marginAlgr_change(){
	if (tradeBreedRuleForm.marginAlgr.value == "1") {
		document.getElementById("marginItem1Percent").innerHTML = '%';
		document.getElementById("marginItemAssure1Percent").innerHTML = '%';
		document.getElementById("marginItem1_SPercent").innerHTML = '%';
		document.getElementById("marginItemAssure1_SPercent").innerHTML = '%';
		
		document.getElementById("marginItem2Percent").innerHTML = '%';
		document.getElementById("marginItemAssure2Percent").innerHTML = '%';
		document.getElementById("marginItem2_SPercent").innerHTML = '%';
		document.getElementById("marginItemAssure2_SPercent").innerHTML = '%';
		
		document.getElementById("marginItem3Percent").innerHTML = '%';
		document.getElementById("marginItemAssure3Percent").innerHTML = '%';
		document.getElementById("marginItem3_SPercent").innerHTML = '%';
		document.getElementById("marginItemAssure3_SPercent").innerHTML = '%';
		
		document.getElementById("marginItem4Percent").innerHTML = '%';
		document.getElementById("marginItemAssure4Percent").innerHTML = '%';
		document.getElementById("marginItem4_SPercent").innerHTML = '%';
		document.getElementById("marginItemAssure4_SPercent").innerHTML = '%';
		
		document.getElementById("marginItem5Percent").innerHTML = '%';
		document.getElementById("marginItemAssure5Percent").innerHTML = '%';
		document.getElementById("marginItem5_SPercent").innerHTML = '%';
		document.getElementById("marginItemAssure5_SPercent").innerHTML = '%';
		
	}else {
		document.getElementById("marginItem1Percent").innerHTML = '';
		document.getElementById("marginItemAssure1Percent").innerHTML = '';
		document.getElementById("marginItem1_SPercent").innerHTML = '';
		document.getElementById("marginItemAssure1_SPercent").innerHTML = '';
		
		document.getElementById("marginItem2Percent").innerHTML = '';
		document.getElementById("marginItemAssure2Percent").innerHTML = '';
		document.getElementById("marginItem2_SPercent").innerHTML = '';
		document.getElementById("marginItemAssure2_SPercent").innerHTML = '';
		
		document.getElementById("marginItem3Percent").innerHTML = '';
		document.getElementById("marginItemAssure3Percent").innerHTML = '';
		document.getElementById("marginItem3_SPercent").innerHTML = '';
		document.getElementById("marginItemAssure3_SPercent").innerHTML = '';
		
		document.getElementById("marginItem4Percent").innerHTML = '';
		document.getElementById("marginItemAssure4Percent").innerHTML = '';
		document.getElementById("marginItem4_SPercent").innerHTML = '';
		document.getElementById("marginItemAssure4_SPercent").innerHTML = '';
		
		document.getElementById("marginItem5Percent").innerHTML = '';
		document.getElementById("marginItemAssure5Percent").innerHTML = '';
		document.getElementById("marginItem5_SPercent").innerHTML = '';
		document.getElementById("marginItemAssure5_SPercent").innerHTML = '';
	}
}
function dealTest(){
	var dealerId = tradeBreedRuleForm.firmID.value;
	$.ajax({
		type:"post",
		url:"<c:url value="/timebargain/baseinfo/tradeRule.do?funcflg=getFirmInfo"/>",
		data:"firmId=" + dealerId,
		success : function(data){
					if(data==1){
						tradeBreedRuleForm.firmID.value = "";
					  $("#hint").html("没有该交易商");
					}else{
					  $("#hint").html("");
					}
		}
	});
}
//-----------------------------end settleinfo-----------------------------
</script>
	</head>

	<body leftmargin="0" topmargin="0" onLoad="return window_onload()"
		onkeypress="keyEnter(event.keyCode);">
		<table border="0" height="400" width="790" align="center">
			<tr>
				<td>
					<html:form action="/timebargain/baseinfo/tradeBreedRule.do?funcflg=saveFirmBreedMargin"
						method="POST" styleClass="form" target="ListFrame1">
						<tr class="common">
					          <td colspan="4">
					            <fieldset>
					              <legend>
					                
					                  
					                    <b>设置特殊保证金</b>
					                    
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
							
								
									<td align="right" width="120">
											交易商代码：
									</td>
									
									<td width="120">
										<html:text size="10" property="firmID" maxlength="16" style="ime-mode:disabled" styleClass="text" onchange="dealTest();"/>         
										<span class="req">*</span>
									</td>
									
									<td width="80">
										<span id="hint" style="color: red"></span>
									</td>
									<td align="right" width="90">
											品种代码：
									</td>
									<c:if test="${param['crud'] == 'create'}">
										<td width="300">
										<!-- onchange="javascript:commodity_onchange()" -->
										<html:select property="breedID" >
                                          <html:options collection="breedSelect" property="value" labelProperty="label"/>
                                        </html:select>
										<span class="req">*</span>
									</td>
									</c:if>
									<c:if test="${param['crud'] == 'update'}">
									<td width="340">
									<%
										String breedID = (String)request.getAttribute("breedID");
										String name = (String)request.getAttribute("name");
										System.out.println("================================="+breedID);
									%>
										<%=breedID%>
										<html:hidden property="breedID" value="<%=breedID%>"/>
										</td>
									</c:if>
									
									
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
                  <col width="53"></col><col></col><col width="8"></col>
                  <tr>
                    <td><b>保证金</b></td>
                    <td><hr width="639" class="pickList"/></td>
                    <td ><img id="settleinfo_img" src="<c:url value="/timebargain/images/ctl_detail_Up.gif"/>" style="cursor:hand" onclick="javascript:settleinfo_onclick()"/></td>
                  </tr>
                </table>
              </legend>
<span id="settleinfo">
<table cellSpacing="0" cellPadding="0" width="700" border="0" align="center" class="common">  
            <tr>
            <td align="right" width="150">保证金算法：</td>
            <td width="110">
				<html:select property="marginAlgr" style="width:80" onchange="marginAlgr_change()">
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
           
            
            
             <td align="right" width="155" id="td1"><%if("1".equals(type)){%>保证金1<%}else{%>买保证金1<%}%>：</td>
            <td width="105">
			  <html:text size="10" property="marginItem1" maxlength="11" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" />
			  <span id="marginItem1Percent"></span><span class="req">*</span>           
            </td>
            <td align="right" id="td2"><%if("1".equals(type)){%>担保金1<%}else{%>买担保金1<%}%>：</td>
            <td>
			  <html:text size="10" property="marginItemAssure1" maxlength="11" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" />
			  <span id="marginItemAssure1Percent"></span><span class="req">*</span>           
            </td>     
            </tr>
             <tr class="<%if("1".equals(type)){%>yin<%}else{%>xian<%}%>" id="aaa">
             
            <td align="right"  id="abc2">卖保证金1：</td>
            <td  id="abc1" style="visibility:<%if("1".equals(type)){%>hidden<%}else{%>visible<%}%>;">
			  <html:text size="10" property="marginItem1_S" maxlength="11"  onkeypress="return onlyNumberInput()" styleClass="text" />
			  <span id="marginItem1_SPercent"></span><span class="req">*</span>           
            </td >
            <td align="right"  id="abc3">
            卖担保金1：</td>
            <td  id="abc4" style="visibility:<%if("1".equals(type)){%>hidden<%}else{%>visible<%}%>;">
			  <html:text size="10" property="marginItemAssure1_S" maxlength="11"  onkeypress="return onlyNumberInput()" styleClass="text" />
			  <span id="marginItemAssure1_SPercent"></span><span class="req">*</span>           
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
			  <html:text size="10" property="marginItem2" maxlength="16" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" />         
            <span id="marginItem2Percent"></span>
            </td> 
            <td align="right"  id="td4">&nbsp;&nbsp;<%if("1".equals(type1)){%>担保金2<%}else{%>买担保金2<%}%>：</td>
            <td width="325" align="left">
			  <html:text size="10" property="marginItemAssure2" maxlength="16" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" />         
            <span id="marginItemAssure2Percent"></span>
            </td>  
        </tr>
        <tr class="<%if("1".equals(type1)){%>yin<%}else{%>xian<%}%>" id="aaa1">
			<td align="right"  id="abc21">&nbsp;&nbsp;卖保证金2：</td>
            <td style="visibility:<%if("1".equals(type1)){%>hidden<%}else{%>visible<%}%>;" id="abc11">
			  <html:text size="10" property="marginItem2_S" maxlength="16" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" />         
            <span id="marginItem2_SPercent"></span>
            </td> 
            <td align="right" id="abc31">&nbsp;&nbsp;卖担保金2：</td>
            <td style="visibility:<%if("1".equals(type1)){%>hidden<%}else{%>visible<%}%>;" id="abc41">
			  <html:text size="10" property="marginItemAssure2_S" maxlength="16" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" />         
            <span id="marginItemAssure2_SPercent"></span>
            </td>           
        </tr>
          <%
             String type2=(String)request.getAttribute("type2");
             %>
             
             <tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
             
        <tr>
 			<td align="right">
            第三阶段买卖保证金：
            </td>
			<td colspan="3">
            
             <input type="radio" name="type2" value="1" onclick="changeManner2(1);" <%if("1".equals(type2)){out.println("checked");} %> style="border:0px;">相同
             
             <input type="radio" name="type2" value="2" onclick="changeManner2(2);" <%if("2".equals(type2)){out.println("checked");} %> style="border:0px;">不同
            </td >
            </tr>
            <tr >
			<td align="right" id="td5"><%if("1".equals(type2)){%>保证金3<%}else{%>买保证金3<%}%>：</td>
            <td>
			  <html:text size="10" property="marginItem3" maxlength="16" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" />        
            <span id="marginItem3Percent"></span>
            </td>
            <td align="right" id="td6"><%if("1".equals(type2)){%>担保金3<%}else{%>买担保金3<%}%>：</td>
            <td>
			  <html:text size="10" property="marginItemAssure3" maxlength="16" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" />        
            <span id="marginItemAssure3Percent"></span>
            </td>
            </tr>   
             <tr class="<%if("1".equals(type2)){%>yin<%}else{%>xian<%}%>" id="aaa2">
			<td align="right" id="abc22">卖保证金3：</td>
            <td style="visibility:<%if("1".equals(type2)){%>hidden<%}else{%>visible<%}%>;" id="abc12">
			  <html:text size="10" property="marginItem3_S" maxlength="16" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" />        
             <span id="marginItem3_SPercent"></span>
            </td>
            <td align="right"  id="abc32">卖担保金3：</td>
            <td style="visibility:<%if("1".equals(type2)){%>hidden<%}else{%>visible<%}%>;" id="abc42">
			  <html:text size="10" property="marginItemAssure3_S" maxlength="16" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" />        
            <span id="marginItemAssure3_SPercent"></span>
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
             <input type="radio" name="type3" value="1" onclick="changeManner3(1);" <%if("1".equals(type3)){out.println("checked");} %> style="border:0px;">相同
             
             <input type="radio" name="type3" value="2" onclick="changeManner3(2);" <%if("2".equals(type3)){out.println("checked");} %> style="border:0px;">不同
            </td >
			</tr>
			<tr>
            <td align="right" id="td7"><%if("1".equals(type3)){%>保证金4<%}else{%>买保证金4<%}%>：</td>
            <td >
			  <html:text size="10" property="marginItem4" maxlength="16" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" />           
            <span id="marginItem4Percent"></span>
            </td>
            <td align="right" id="td8"><%if("1".equals(type3)){%>担保金4<%}else{%>买担保金4<%}%>：</td>
            <td >
			  <html:text size="10" property="marginItemAssure4" maxlength="16" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" />           
            <span id="marginItemAssure4Percent"></span>
            </td>
        </tr>
         <tr class="<%if("1".equals(type3)){%>yin<%}else{%>xian<%}%>" id="aaa3">
            <td align="right" id="abc13">卖保证金4：</td>
            <td  id="abc23" style="visibility:<%if("1".equals(type3)){%>hidden<%}else{%>visible<%}%>;">
			  <html:text size="10" property="marginItem4_S" maxlength="16" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" />           
            <span id="marginItem4_SPercent"></span>
            </td>
            <td align="right" id="abc33">卖担保金4：</td>
            <td  id="abc43" style="visibility:<%if("1".equals(type3)){%>hidden<%}else{%>visible<%}%>;">
			  <html:text size="10" property="marginItemAssure4_S" maxlength="16" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" />           
            <span id="marginItemAssure4_SPercent"></span>
            </td>
        </tr>
          <%
             String type4=(String)request.getAttribute("type4");
             %>
             
             <tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
             
             <tr>
			<td align="right">
            第五阶段买卖保证金：
            </td>
			<td colspan="3" >
             <input type="radio" name="type4" value="1" onclick="changeManner4(1);" <%if("1".equals(type4)){out.println("checked");} %> style="border:0px;">相同
             
             <input type="radio" name="type4" value="2" onclick="changeManner4(2);" <%if("2".equals(type4)){out.println("checked");} %> style="border:0px;">不同
            </td >
			</tr>
			<tr>
            <td align="right" id="td50"><%if("1".equals(type4)){%>保证金5<%}else{%>买保证金5<%}%>：</td>
            <td >
			  <html:text size="10" property="marginItem5" maxlength="16" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" />           
            <span id="marginItem5Percent"></span>
            </td>
            <td align="right" id="td60"><%if("1".equals(type4)){%>担保金5<%}else{%>买担保金5<%}%>：</td>
            <td >
			  <html:text size="10" property="marginItemAssure5" maxlength="16" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" />           
           <span id="marginItemAssure5Percent"></span>
            </td>
        </tr>
         <tr class="<%if("1".equals(type4)){%>yin<%}else{%>xian<%}%>" id="aaa4">
            <td align="right" id="abc14">卖保证金5：</td>
            <td  id="abc24" style="visibility:<%if("1".equals(type4)){%>hidden<%}else{%>visible<%}%>;">
			  <html:text size="10" property="marginItem5_S" maxlength="16" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" />           
            <span id="marginItem5_SPercent"></span>
            </td>
            <td align="right" id="abc34">卖担保金5：</td>
            <td  id="abc44" style="visibility:<%if("1".equals(type4)){%>hidden<%}else{%>visible<%}%>;">
			  <html:text size="10" property="marginItemAssure5_S" maxlength="16" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" />           
            <span id="marginItemAssure5_SPercent"></span>
            </td>
        </tr>
     <tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
        <tr>
			<td align="right">
            交收保证金：
            </td>
            <td>&nbsp;</td>
        </tr>   
        
        <%
        	String typeSettleMarginAlgr_B = (String)request.getAttribute("typeSettleMarginAlgr_B");
        %>
          <tr>
            <td align="right" id="td9">买方方式：</td>
            <td>
            <html:select property="settleMarginAlgr_B" style="width:80" onchange="settleMarginAlgr_B_change()">
					<html:option value=""></html:option>
				    <html:option value="1">按百分比</html:option>
					<html:option value="2">按绝对值</html:option>
			   </html:select>
            <span class="req">*</span>
            </td>
            <td align="right" id="td0">买方标准：</td>
            <td>
			  <html:text size="10" property="settleMarginRate_B" maxlength="16" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" />         
            <span id="settleMarginRate_BPercent" class="<%if("1".equals(typeSettleMarginAlgr_B)){%>xian<%}else{%>yin<%}%>">%</span>
            <span class="req">*</span>
            </td>
        </tr>
             
        <%
        	String typeSettleMarginAlgr_S = (String)request.getAttribute("typeSettleMarginAlgr_S");
        %>
        <tr id="aaa4">
            <td align="right"  id="abc14">卖方方式：</td>
            <td  >
            <html:select property="settleMarginAlgr_S" style="width:80" onchange="settleMarginAlgr_S_change()">
					<html:option value=""></html:option>
				    <html:option value="1">按百分比</html:option>
					<html:option value="2">按绝对值</html:option>
			   </html:select>
            <span class="req">*</span>
            </td>
            <td align="right"  id="abc34">卖方标准：</td>
            <td>
			  <html:text size="10" property="settleMarginRate_S" maxlength="16" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" />         
            <span id="settleMarginRate_SPercent" class="<%if("1".equals(typeSettleMarginAlgr_S)){%>xian<%}else{%>yin<%}%>">%</span><span class="req">*</span>
            </td>
        </tr>
        
        <%
        	String typePayoutAlgr = (String)request.getAttribute("typePayoutAlgr");
        %>
        <tr>
            <td align="right">交收货款算法：</td>
            <td  >
            <html:select property="payoutAlgr" style="width:80" onchange="payoutAlgr_change()">
					<html:option value=""></html:option>
				    <html:option value="1">按百分比</html:option>
					<html:option value="2">按绝对值</html:option>
			   </html:select>
            <span class="req">*</span>
            </td>
            <td align="right">交收货款标准：</td>
            <td>
			  <html:text size="10" property="payoutRate" maxlength="16" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" />         
            <span id="payoutRatePercent" class="<%if("1".equals(typePayoutAlgr)){%>xian<%}else{%>yin<%}%>">%</span><span class="req">*</span>
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
					</html:form>
				</td>
			</tr>
		</table>
		 
		<script type="text/javascript"
			src="<html:rewrite page="/timebargain/common/validator.jsp"/>"></script>
		<%@ include file="/timebargain/common/messages.jsp"%>
	</body>
</html>
