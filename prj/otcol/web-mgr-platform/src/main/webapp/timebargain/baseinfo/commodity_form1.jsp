<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page import="gnnt.MEBS.timebargain.manage.model.*"%>
<%@ page import="gnnt.MEBS.timebargain.manage.webapp.form.*"%>
<%@ page pageEncoding="GBK"%>

	<%
     	String type=(String)request.getAttribute("type");
     	
     	CommodityForm commodityForm = new CommodityForm();
		try {
			commodityForm = (CommodityForm)request.getAttribute("commodityForm");
		} catch (Exception e) {
			e.printStackTrace();
		}
	%>
<html>
	<head>
		<LINK REL="stylesheet" type="text/css"
			href="<c:url value="/timebargain/styles/common.css"/>" />
		<script language="JavaScript" src="<c:url value="/timebargain/scripts/global.js"/>"></script>
		<script type="text/javascript" src="../public/calendar/WdatePicker.js"></script>
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
    //highlightFormElements();
    change_date_settle();
    var tradeTimeCs = new Array();
    <c:forEach var="tradeTime" items="${commodityForm.tradeTimes}" varStatus="status">
      tradeTimeCs[<c:out value="${status.index}"/>] = "<c:out value="${tradeTime}"/>";
    </c:forEach>
    try{
    for(var k=0; k < commodityForm.tradeTimes.length; k++)
    {
      //�˴����жϻ���ʱ��box���
      if (contains(tradeTimeCs, commodityForm.tradeTimes[k].value))
      {
        commodityForm.tradeTimes[k].checked = true;
      }
      //�˴�������Ҫ�����Ĭ��ȫѡ
      //if (commodityForm.crud.value == "create") {
      //	commodityForm.tradeTimes[i].checked = true;
     // }
    }
    }catch(e){
    	alert("�޽��׽ڣ�");
    }
    
    
    
    if(commodityForm.crud.value == "create")
    {
    
      commodityForm.commodityID.focus();
     
    }
    else if(commodityForm.crud.value == "update")
    {
      
      setReadOnly(commodityForm.commodityID);
      commodityForm.name.focus();
      <c:if test="${market.commoditySetType eq 0}">
      		setReadOnly(commodityForm.name);
      		//setReadOnly(commodityForm.settleDate);
      		setDisabled(commodityForm.settleDateBtn);
      		setReadOnly(commodityForm.contractFactor); 
      		setReadOnly(commodityForm.lastPrice); 
      		setReadOnly(commodityForm.marketDate); 
      		commodityForm.marketDate.focus();     		
      </c:if>
      //setReadOnly(commodityForm.marketDate2);
      
    }  
    if (commodityForm.limitCmdtyQty.value == "-1") {
      	changeManner101(2);
      }else {
      	changeManner101(1);
      }
      if (commodityForm.firmCleanQty.value == "-1") {
      	changeManner102(2);
      }else {
      	changeManner102(1);
      }
      if (commodityForm.firmMaxHoldQty.value == "-1") {
      	changeManner103(2);
      }else {
      	changeManner103(1);
      }
      if (commodityForm.oneMaxHoldQty.value == "-1") {
      	changeManner104(2);
      }else {
      	changeManner104(1);
      }
      
      setReadOnly(commodityForm.settleDate7);
      setReadOnly(commodityForm.settleDate1);
      
      marginAlgr_change();
      setReadOnly(commodityForm.marginRate_B);
      setReadOnly(commodityForm.marginRate_S);
      //���ڽ�����������
      settleWay_settleDate();
      //Ĭ�����ڷ���ȡ��ʽ
      commodityForm.delayFeeWay.value = "1";
      commodityForm.aheadSettlePriceType.value=${aheadSettlePriceType}

      var maxHoldDay=document.getElementById("holdDays");
		<c:if test="${holdDaysLimit == '1'}">
			maxHoldDay.style.visibility='visible';
	    </c:if>
	    <c:if test="${holdDaysLimit == '0'}">
			maxHoldDay.style.visibility='hidden';
	    </c:if>
      
}
function on_change(){
	commodityForm.forceCloseFeeAlgr.value =  commodityForm.feeAlgr.value;
	
	if (commodityForm.feeAlgr.value == "1") {
		document.getElementById("feeRate_BPercent").className = "xian";
		document.getElementById("feeRate_SPercent").className = "xian";
		document.getElementById("historyCloseFeeRate_BPercent").className = "xian";
		document.getElementById("historyCloseFeeRate_SPercent").className = "xian";
		document.getElementById("todayCloseFeeRate_BPercent").className = "xian";
		document.getElementById("todayCloseFeeRate_SPercent").className = "xian";
		document.getElementById("forceCloseFeeRate_BPercent").className = "xian";
		document.getElementById("forceCloseFeeRate_SPercent").className = "xian";
	}else {
		document.getElementById("feeRate_BPercent").className = "yin";
		document.getElementById("feeRate_SPercent").className = "yin";
		document.getElementById("historyCloseFeeRate_BPercent").className = "yin";
		document.getElementById("historyCloseFeeRate_SPercent").className = "yin";
		document.getElementById("todayCloseFeeRate_BPercent").className = "yin";
		document.getElementById("todayCloseFeeRate_SPercent").className = "yin";
		document.getElementById("forceCloseFeeRate_BPercent").className = "yin";
		document.getElementById("forceCloseFeeRate_SPercent").className = "yin";
	}
}
//save
function save_onclick()
{

	for(var i =2;i<6;i++){
	var typeid = 'type'+(i-1);	
		var a = document.getElementsByName(typeid); 
		var marginItem = document.getElementById('marginItem'+i).value;
		var marginItem_S = document.getElementById('marginItem'+i+'_S').value;
		var marginItemAssure = document.getElementById('marginItemAssure'+i).value;
		var marginItemAssure_S  = document.getElementById('marginItemAssure'+i+'_S').value;
		if( a[0].checked==true){
			if((marginItem>0 || marginItemAssure>0)&&document.getElementById('settleDate'+i).value==""){
					alert('��'+i+'�׶α�֤�������ڻ�������');
					 return false;
			 }
			 if(document.getElementById('settleDate'+i).value!=""&&(marginItem<=0 && marginItemAssure<0)){
				 alert('��'+i+'�׶α�֤�������ڻ�������');
					 return false;
			 }
		}
		 if(a[1].checked==true){
		 	if((marginItem_S>0||marginItemAssure_S>0||marginItem>0 || marginItemAssure>0)&&document.getElementById('settleDate'+i).value==""){
				alert('��'+i+'�׶α�֤�������ڻ�������');
			 return false;
			 }
			 	if((marginItem_S<=0 &&marginItemAssure_S<=0 &&marginItem<=0  && marginItemAssure<0)&&document.getElementById('settleDate'+i).value!=""){
				alert('��'+i+'�׶α�֤�������ڻ�������');
				 return false;
			 }
		}
	
	}
	if (confirm("��ȷ��Ҫ�ύ��")) {
	
		
    if(!tmp_baseinfo_up)
    {
      baseinfo_onclick();
    }
    if (!tmp_baseinfo_up2) {
    	baseinfo2_onclick();
    }
    if (!tmp_baseinfo_up3) {
    	baseinfo3_onclick();
    }
    if (!tmp_baseinfo_up4) {
    	baseinfo4_onclick();
    }
    if (!tmp_baseinfo_up8) {
    	baseinfo8_onclick();
    }
    if(!tmp_settleinfo_up)
    {
      settleinfo_onclick();
    }
    if(!tmp_baseinfo_upy)
    {
      baseinfoy_onclick();
    }
    
    if(commodityForm.type[1].checked ==true)
      {
	    if(trim(commodityForm.marginItem1_S.value) == "")
      {
	      alert("����֤��1����Ϊ�գ�");
	      commodityForm.marginItem1_S.focus();
	      return false;
      }  
       if(trim(commodityForm.marginItemAssure1_S.value) == "")
      {
	      alert("��������1����Ϊ�գ�");
	      commodityForm.marginItemAssure1_S.focus();
	      return false;
      }  
      }
      
   
    if (commodityForm.marketDate.value == "") {
    	alert("�������ڲ���Ϊ�գ�");
      	commodityForm.marketDate.focus();
      	return false;
    }

    if (commodityForm.settleDate.value <= commodityForm.marketDate.value) {
    	alert("������ղ��������������ڣ�");
    	commodityForm.settleDate.focus();
    	return false;
    }
    
    if(parseFloat(trim(commodityForm.contractFactor.value),10) <= 0)
    {
	      alert("��λ��������Ӧ����0��");
	      commodityForm.contractFactor.focus();
	      return false;
    }
   
    if(commodityForm.todayCloseFeeRate_B.value==""){
    	alert("��ת�ý񶩻�����Ϊ�գ�");
      commodityForm.todayCloseFeeRate_B.focus();
      return false;
    }  
    if(commodityForm.todayCloseFeeRate_S.value==""){
    	alert("��ת�ý񶩻�����Ϊ�գ�");
      commodityForm.todayCloseFeeRate_S.focus();
      return false;
    } 
    if(commodityForm.historyCloseFeeRate_B.value==""){
    	alert("��ת����ʷ��������Ϊ�գ�");
      commodityForm.historyCloseFeeRate_B.focus();
      return false;
    } 
    if(commodityForm.historyCloseFeeRate_S.value==""){
    	alert("��ת����ʷ��������Ϊ�գ�");
      commodityForm.historyCloseFeeRate_S.focus();
      return false;
    }   
    
    if (commodityForm.sortID.value == "") {
    	alert("��Ʒ���಻��Ϊ�գ�");
    	commodityForm.sortID.focus();
    	return false;
    }
    
    if (commodityForm.feeAlgr.value == "") {
    	alert("�������㷨����Ϊ�գ�");
    	commodityForm.feeAlgr.focus();
    	return false;
    }
    if (commodityForm.feeRate_B.value == "") {
    	alert("��������Ϊ�գ�");
    	commodityForm.feeRate_B.focus();
    	return false;
    }
    if (commodityForm.feeRate_S.value == "") {
    	alert("����������Ϊ�գ�");
    	commodityForm.feeRate_S.focus();
    	return false;
    }
    if (commodityForm.settleFeeAlgr.value == "") {
    	alert("���������Ѳ���Ϊ�գ�");
    	commodityForm.settleFeeAlgr.focus();
    	return false;
    }
    if (commodityForm.settleFeeRate_B.value == "") {
    	alert("��������Ϊ�գ�");
    	commodityForm.settleFeeRate_B.focus();
    	return false;
    }
    if (commodityForm.settleFeeRate_S.value == "") {
    	alert("����������Ϊ�գ�");
    	commodityForm.settleFeeRate_S.focus();
    	return false;
    }
    
    //if (commodityForm.maxFeeRate.value == "") {
    //	alert("�����������ϵ������Ϊ�գ�");
    //	commodityForm.maxFeeRate.focus();
    //	return false;
    //}
    if (commodityForm.forceCloseFeeRate_B.value == "") {
    	alert("��ǿ��ת�ò���Ϊ�գ�");
    	commodityForm.forceCloseFeeRate_B.focus();
    	return false;
    }
    if (commodityForm.forceCloseFeeRate_S.value == "") {
    	alert("��ǿ��ת�ò���Ϊ�գ�");
    	commodityForm.forceCloseFeeRate_S.focus();
    	return false;
    }
    if (commodityForm.spreadAlgr.value == "") {
    	alert("ͣ���ǵ����㷨����Ϊ�գ�");
    	commodityForm.spreadAlgr.focus();
    	return false;
    }
    if (commodityForm.spreadUpLmt.value == "") {
    	alert("�Ƿ����޲���Ϊ�գ�");
    	commodityForm.spreadUpLmt.focus();
    	return false;
    }
    if (commodityForm.spreadDownLmt.value == "") {
    	alert("�Ƿ����޲���Ϊ�գ�");
    	commodityForm.spreadDownLmt.focus();
    	return false;
    }  
    if (commodityForm.marginAlgr.value == "") {
    	alert("��֤���㷨����Ϊ�գ�");
    	commodityForm.marginAlgr.focus();
    	return false;
    }
    if (commodityForm.marginItem1.value == "") {
    	alert("��֤����Ϊ�գ�");
    	commodityForm.marginItem1.focus();
    	return false;
    }
    if (commodityForm.marginItemAssure1.value == "") {
    	alert("�򵣱�����Ϊ�գ�");
    	commodityForm.marginItemAssure1.focus();
    	return false;
    }
    if (commodityForm.minPriceMove.value <= 0 ) {
    	alert("��С��λӦ����0��");
    	commodityForm.minPriceMove.focus();
    	return false;
    }
    if (commodityForm.minPriceMove.value == "") {
    	alert("��С��λ����Ϊ�գ�");
    	commodityForm.minPriceMove.focus();
    	return false;
    }
      if (commodityForm.minQuantityMove.value <= 0) {
    	alert("��С�䶯����Ӧ����0��");
    	commodityForm.minQuantityMove.focus();
    	return false;
    }
    if (commodityForm.minQuantityMove.value == "") {
    	alert("��С�䶯��������Ϊ�գ�");
    	commodityForm.minQuantityMove.focus();
    	return false;
    }
    if (commodityForm.minSettleMoveQty.value <= 0) {
    	alert("��С���λӦ����0��");
    	commodityForm.minSettleMoveQty.focus();
    	return false;
    }
    if (commodityForm.minSettleMoveQty.value == "") {
    	alert("��С���λ����Ϊ�գ�");
    	commodityForm.minSettleMoveQty.focus();
    	return false;
    }
    if (commodityForm.minSettleQty.value <= 0) {
    	alert("��С��������Ӧ����0��");
    	commodityForm.minSettleQty.focus();
    	return false;
    }
    if (commodityForm.minSettleQty.value == "") {
    	alert("��С������������Ϊ�գ�");
    	commodityForm.minSettleQty.focus();
    	return false;
    }
    if (commodityForm.forceCloseFeeAlgr.value == "") {
    	alert("ǿ��ת���㷨����Ϊ�գ�");
    	return false;
    }
    
    if (commodityForm.name.value == "") {
    	alert("��Ʒ���Ʋ���Ϊ�գ�");
    	commodityForm.name.focus();
    	return false;
    }
    if(commodityForm.commodityID.value == ""){
        alert("��Ʒ���벻��Ϊ�գ�");
     	commodityForm.commodityID.focus();
    	return false;
    }
    if (commodityForm.status.value == "") {
    	alert("״̬����Ϊ�գ�");
    	commodityForm.status.focus();
    	return false;
    }
	if(((commodityForm.lastPrice.value)*10000000)%((commodityForm.minPriceMove.value)*10000000)!=0){
		alert("����ָ���۱���Ϊ��С�䶯��λ����������");
    	commodityForm.lastPrice.focus();
    	return false;
	}
    if (commodityForm.lastPrice.value == "") {
    	alert("����ָ���۲���Ϊ�գ�");
    	commodityForm.lastPrice.focus();
    	return false;
    }else {
    	var lastPrice = parseFloat(commodityForm.lastPrice.value);
    	if (lastPrice <= 0) {
    		alert("����ָ����Ӧ�����㣡");
	    	commodityForm.lastPrice.focus();
	    	return false;
    	}
    }
    if (commodityForm.lastPrice.value < 0) {
    	alert("����ָ����Ӧ����0��");
    	commodityForm.lastPrice.focus();
    	return false;
    }
    
    if (commodityForm.settleDate.value == "") {
    	alert("������ղ���Ϊ�գ�");
    	commodityForm.settleDate.focus();
    	return false;
    }
    if (commodityForm.limitCmdtyQty.value == "") {
    	alert("��Ʒ������󶩻�������Ϊ�գ�");
    	commodityForm.limitCmdtyQty.focus();
    	return false;
    }
    if (commodityForm.marginAlgr.value == "1") {
    	if (commodityForm.marginPriceType.value == "") {
    		alert("��֤��������ݲ���Ϊ�գ�");
    		return false;
    	}
    }
    	
    if (commodityForm.addedTax.value == "") {
    	alert("��ֵ˰�ʲ���Ϊ�գ�");
    	return false;
    	}
    var holdDaysLimit=document.getElementById("holdDaysLimit").value;
    if(holdDaysLimit==1){
   		 if (commodityForm.maxHoldPositionDay.value == "") {
    		alert("��ֲ���������Ϊ�գ�");
    		commodityForm.maxHoldPositionDay.focus();
    		return false;
    	}
    }	
    if (commodityForm.lowestSettleFee.value == "") {
    	alert("������������ͽ���Ϊ�գ�");
    	commodityForm.lowestSettleFee.focus();
    	return false;
    }
    if (commodityForm.firmCleanQty.value == "") {
    	alert("�����̾�����������Ϊ�գ�");
    	commodityForm.firmCleanQty.focus();
    	return false;
    }
    
    if (commodityForm.oneMaxHoldQty.value == "") {
    	alert("�������ί��������Ϊ�գ�");
    	commodityForm.oneMaxHoldQty.focus();
    	return false;
    }
    
 	if (commodityForm.settleMarginRate_B.value == "") {
 		alert("�򷽱�׼����Ϊ�գ�");
 		commodityForm.settleMarginRate_B.focus();
    	return false;
 	}
 	if (commodityForm.settleMarginRate_S.value == "") {
 		alert("������׼����Ϊ�գ�");
 		commodityForm.settleMarginRate_S.focus();
    	return false;
 	}
 
 	if (document.getElementById("settleDate7").value == "") {
 		alert("���ձ�֤����Ч���ڲ���Ϊ�գ�");
    	return false;
 	}
 	
 	if (commodityForm.orderPrivilege_B.value == "") {
 		alert("��ί��Ȩ�޲���Ϊ�գ�");
 		commodityForm.orderPrivilege_B.focus();
    	return false;
 	}
 	
 	if (commodityForm.orderPrivilege_S.value == "") {
 		alert("����ί��Ȩ�޲���Ϊ�գ�");
 		commodityForm.orderPrivilege_S.focus();
    	return false;
 	}
 	if (commodityForm.settlePriceType.value == "") {
 		alert("��ǰ���ռ۷�ʽ����Ϊ�գ�");
 		commodityForm.settlePriceType.focus();
    	return false;
 	}
 	
 	if (commodityForm.aheadSettlePriceType.value == "") {
 		alert("���ռۼ��㷽ʽ����Ϊ�գ�");
 		commodityForm.aheadSettlePriceType.focus();
    	return false;
 	}
 	//add by yangpei 2011-12-2���ձ�֤����㷽ʽ
 	if (commodityForm.settleMarginType.value == "") {
 		alert("���ձ�֤����㷽ʽ����Ϊ�գ�");
 		commodityForm.settleMarginType.focus();
    	return false;
 	}
    
	if (commodityForm.type101[1].checked == true) {
		if (commodityForm.limitCmdtyQty.value < 0) {
			alert("��Ʒ������󶩻�������Ϊ����");
			return false;
		}
	}

	if (commodityForm.type102[1].checked == true) {
		if (commodityForm.firmCleanQty.value < 0) {
			alert("�����̾�����������Ϊ����");
			return false;
		}
	}

	if (commodityForm.type103[1].checked == true) {
		if (commodityForm.firmMaxHoldQtyAlgr.value == "") {
 			alert("�����̶����������㷨����Ϊ�գ�");
 			return false;
 		}
 		if (commodityForm.firmMaxHoldQtyAlgr.value == "2") {
 			if (commodityForm.firmMaxHoldQty.value == "") {
		 		alert("���������˫�߶���������Ϊ�գ�");
		 		commodityForm.firmMaxHoldQty.focus();
		    	return false;
		 	}
		 	if (commodityForm.firmMaxHoldQty.value < 0) {
				alert("���������˫�߶���������Ϊ����");
				return false;
			}
 		}else {
 			if (commodityForm.startPercentQty.value == "") {
 				alert("��Ʒ��������ֵ����Ϊ�գ�");
 				return false;
 			}
 			if (commodityForm.maxPercentLimit.value == "") {
 				alert("��������������Ϊ�գ�");
 				return false;
 			}
 			commodityForm.firmMaxHoldQty.value = parseInt((commodityForm.startPercentQty.value * (commodityForm.maxPercentLimit.value/100))+"");
 			if (commodityForm.firmMaxHoldQty.value < 1) {
 				alert("��ֵ��������ò���ȷ���˻���СΪ1��");
 				return false;
 			}
 		}
	}else {
		commodityForm.firmMaxHoldQtyAlgr.value = '2';
		commodityForm.startPercentQty.value = '0';
		commodityForm.maxPercentLimit.value = '0';
	}
	
	
	if (commodityForm.type104[1].checked == true) {
		if (commodityForm.oneMaxHoldQty.value < 0) {
			alert("�������ί��������Ϊ����");
			return false;
		}
	}
	
	if (commodityForm.settleDate2.value != "") {
		if ((commodityForm.marginItem2.value == "0.0") || (commodityForm.marginItem2.value == "0")) {
			if (confirm("��֤��Ϊ0���Ƿ������")) {
				
			}else {
				return false;
			}
		}
	}
	
	if (commodityForm.settleDate2.value != "") {
		if ((commodityForm.marginItem2.value == "0.0") || (commodityForm.marginItem2.value == "0")) {
			if (confirm("��֤��Ϊ0���Ƿ������")) {
				
			}else {
				return false;
			}
		}
	}
	
	
	if (commodityForm.settleDate2.value != "") {
		if ((commodityForm.marginItem2.value == "0.0") || (commodityForm.marginItem2.value == "0")) {
			if (confirm("��֤��Ϊ0���Ƿ������")) {
				
			}else {
				return false;
			}
		}
	}
	
	if (commodityForm.settleDate2.value != "") {
		if ((commodityForm.marginItem2.value == "0.0") || (commodityForm.marginItem2.value == "0")) {
			if (confirm("��֤��Ϊ0���Ƿ������")) {
				
			}else {
				return false;
			}
		}
	}
	
	if (commodityForm.payoutRate.value == "") {
		alert("���ջ����׼����Ϊ�գ�");
		return false;
	}
	
	if (commodityForm.payoutAlgr.value == "") {
		alert("���ջ����㷨����Ϊ�գ�");
		return false;
	}
	
	if (commodityForm.settleMarginAlgr_S.value == "") {
		alert("������ʽ����Ϊ�գ�");
		return false;
	}
	
	if (commodityForm.settleMarginAlgr_B.value == "") {
		alert("�򷽷�ʽ����Ϊ�գ�");
		return false;
	}
	
	
	if (commodityForm.delayRecoupRate.value == "") {
		alert("�����ڲ������ʲ���Ϊ�գ�");
		return false;
	}
	if (commodityForm.delayRecoupRate_S.value == "") {
		alert("�������ڲ������ʲ���Ϊ�գ�");
		return false;
	}
	if (commodityForm.storeRecoupRate.value == "") {
		alert("�ִ������Ѳ���Ϊ�գ�");
		return false;
	}
	if(commodityForm.storeRecoupRate.value >= 100){
		alert("�ִ������ѱ���С��100");
		return false;
	}
	if (commodityForm.settleWay.value == "") {
		alert("���շ�ʽ����Ϊ�գ�");
		return false;
	}
	if (commodityForm.delayFeeWay.value == "") {
		alert("���ڷ���ȡ��ʽ����Ϊ�գ�");
		return false;
	}

    commodityForm.addedTaxFactor.value = (parseFloat(commodityForm.addedTax.value)/(parseFloat(commodityForm.addedTax.value)+100));
    setEnabled(commodityForm.marginPriceType);
    if(confirm("�Ƿ������Ӹ���Ʒ����Ʒ���µĽ������������ã�")){
    	commodityForm.action="<c:url value='/timebargain/baseinfo/commodity.do?funcflg=save&logos=true'/>";
		commodityForm.submit();
	}else{
		commodityForm.submit();
	}
    commodityForm.save.disabled = true;
	}
}
//cancel
function cancel_onclick()
{
  parent.HeadFrame.query();
}

//breedID_onchange

//market_onchange

//spreadAlgr_onchange()
function spreadAlgr_onchange()
{
	if (commodityForm.spreadAlgr.value == "1") {
		document.getElementById("spreadUpLmtPercent").className = "xian";
		document.getElementById("spreadDownLmtPercent").className = "xian";
	}else {
		document.getElementById("spreadUpLmtPercent").className = "yin";
		document.getElementById("spreadDownLmtPercent").className = "yin";
	}

    if(commodityForm.spreadAlgr.value == "3")
    {
        commodityForm.spreadUpLmt.value = 0;
        commodityForm.spreadDownLmt.value = 0;
        setReadOnly(commodityForm.spreadUpLmt);
        setReadOnly(commodityForm.spreadDownLmt);
    } 
    else
    {
        setReadWrite(commodityForm.spreadUpLmt);
        setReadWrite(commodityForm.spreadDownLmt);
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
    commodityForm.baseinfo_img.src = "<c:url value="/timebargain/images/ctl_detail_Down.gif"/>";
    tmp_baseinfo = baseinfo.innerHTML;
    baseinfo.innerHTML = "";
  }
  else
  {
    tmp_baseinfo_up = true;
    commodityForm.baseinfo_img.src = "<c:url value="/timebargain/images/ctl_detail_Up.gif"/>";
    baseinfo.innerHTML = tmp_baseinfo;
  }
}

var tmp_baseinfo2;
var tmp_baseinfo_up2 = true;
function baseinfo2_onclick()
{
  if (tmp_baseinfo_up2)
  {
    tmp_baseinfo_up2 = false;
    commodityForm.baseinfo_img2.src = "<c:url value="/timebargain/images/ctl_detail_Down.gif"/>";
    tmp_baseinfo2 = baseinfo2.innerHTML;
    baseinfo2.innerHTML = "";
  }
  else
  {
    tmp_baseinfo_up2 = true;
    commodityForm.baseinfo_img2.src = "<c:url value="/timebargain/images/ctl_detail_Up.gif"/>";
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
    commodityForm.baseinfo_img3.src = "<c:url value="/timebargain/images/ctl_detail_Down.gif"/>";
    tmp_baseinfo3 = baseinfo3.innerHTML;
    baseinfo3.innerHTML = "";
  }
  else
  {
    tmp_baseinfo_up3 = true;
    commodityForm.baseinfo_img3.src = "<c:url value="/timebargain/images/ctl_detail_Up.gif"/>";
    baseinfo3.innerHTML = tmp_baseinfo3;
  }
}

var tmp_baseinfo4;
var tmp_baseinfo_up4 = true;
function baseinfo4_onclick()
{
  if (tmp_baseinfo_up4)
  {
    tmp_baseinfo_up4 = false;
    commodityForm.baseinfo_img4.src = "<c:url value="/timebargain/images/ctl_detail_Down.gif"/>";
    tmp_baseinfo4 = baseinfo4.innerHTML;
    baseinfo4.innerHTML = "";
  }
  else
  {
    tmp_baseinfo_up4 = true;
    commodityForm.baseinfo_img4.src = "<c:url value="/timebargain/images/ctl_detail_Up.gif"/>";
    baseinfo4.innerHTML = tmp_baseinfo4;
  }
}

var tmp_baseinfo8;
var tmp_baseinfo_up8 = true;
function baseinfo8_onclick()
{
  if (tmp_baseinfo_up8)
  {
    tmp_baseinfo_up8 = false;
    commodityForm.baseinfo_img8.src = "<c:url value="/timebargain/images/ctl_detail_Down.gif"/>";
    tmp_baseinfo8 = baseinfo8.innerHTML;
    baseinfo8.innerHTML = "";
  }
  else
  {
    tmp_baseinfo_up8 = true;
    commodityForm.baseinfo_img8.src = "<c:url value="/timebargain/images/ctl_detail_Up.gif"/>";
    baseinfo8.innerHTML = tmp_baseinfo8;
  }
}

var tmp_baseinfoy;
var tmp_baseinfo_upy = true;
function baseinfoy_onclick()
{
  if (tmp_baseinfo_upy)
  {
    tmp_baseinfo_upy = false;
    commodityForm.baseinfo_imgy.src = "<c:url value="/timebargain/images/ctl_detail_Down.gif"/>";
    tmp_baseinfoy = baseinfoy.innerHTML;
    baseinfoy.innerHTML = "";
  }
  else
  {
    tmp_baseinfo_upy = true;
    commodityForm.baseinfo_imgy.src = "<c:url value="/timebargain/images/ctl_detail_Up.gif"/>";
    baseinfoy.innerHTML = tmp_baseinfoy;
  }
}
//-----------------------------end baseinfo-----------------------------


//---------------------------start settleinfo-------------------------------
var tmp_settleinfo;
var tmp_settleinfo_up = true;
function settleinfo_onclick()
{
  if (tmp_settleinfo_up)
  {
    tmp_settleinfo_up = false;
    commodityForm.settleinfo_img.src = "<c:url value="/timebargain/images/ctl_detail_Down.gif"/>";
    tmp_settleinfo = settleinfo.innerHTML;
    settleinfo.innerHTML = "";
  }
  else
  {
    tmp_settleinfo_up = true;
    commodityForm.settleinfo_img.src = "<c:url value="/timebargain/images/ctl_detail_Up.gif"/>";
    settleinfo.innerHTML = tmp_settleinfo;
  }
}
//-----------------------------end settleinfo-----------------------------
function changeManner(id){ 
  var td1 = window.td1;
  var td2 = window.td2;
  if(id==1)
  {
   td1.innerHTML='��֤��1��';
   td2.innerHTML='������1��';
   document.getElementById("abc1").style.visibility = "hidden";
   document.getElementById("abc4").style.visibility = "hidden";
   document.getElementById("aaa").className = "yin";
  }
  else if(id==2)
  {
   td1.innerHTML='��֤��1��';
   td2.innerHTML='�򵣱���1��';
   document.getElementById("abc1").style.visibility = "visible";
   document.getElementById("abc4").style.visibility = "visible";
   document.getElementById("aaa").className = "xian";
  }
  
} 

function changeManner12(id){ 
  var td1 = window.td12;

  if(id==1)
  {
   td1.innerHTML='���ջ��';

   document.getElementById("abcd1").style.visibility = "hidden";
   document.getElementById("bbb").className = "yin";
  }
  else if(id==2)
  {
   td1.innerHTML='���ջ��';

   document.getElementById("abcd1").style.visibility = "visible";
   document.getElementById("bbb").className = "xian";
  }
  
} 

function changeManner1(id){ 
  var td1 = window.td3;
  var td2 = window.td4;
  if(id==1)
  {
   td1.innerHTML='��֤��2��';
   td2.innerHTML='������2��';
   document.getElementById("abc11").style.visibility='hidden';
   document.getElementById("abc41").style.visibility='hidden';
   document.getElementById("aaa1").className = "yin";
  }
  else if(id==2)
  {
   td1.innerHTML='��֤��2��';
   td2.innerHTML='�򵣱���2��';
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
   td1.innerHTML='��֤��3��';
   td2.innerHTML='������3��';
   document.getElementById("abc12").style.visibility='hidden';
   document.getElementById("abc42").style.visibility='hidden';
   document.getElementById("aaa2").className = "yin";
  }
  else if(id==2)
  {
   td1.innerHTML='��֤��3��';
   td2.innerHTML='�򵣱���3��';
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
   td1.innerHTML='��֤��4��';
   td2.innerHTML='������4��';
   document.getElementById("abc23").style.visibility='hidden';
   document.getElementById("abc43").style.visibility='hidden';
   document.getElementById("aaa3").className = "yin";
  }
  else if(id==2)
  {
   td1.innerHTML='��֤��4��';
   td2.innerHTML='�򵣱���4��';
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
   td1.innerHTML='��֤��5��';
   td2.innerHTML='������5��';
   document.getElementById("abc24").style.visibility='hidden';
   document.getElementById("abc44").style.visibility='hidden';
   document.getElementById("aaa4").className = "yin";
  }
  else if(id==2)
  {
   td1.innerHTML='��֤��5��';
   td2.innerHTML='�򵣱���5��';
   document.getElementById("abc24").style.visibility='visible';
   document.getElementById("abc44").style.visibility='visible';
   document.getElementById("aaa4").className = "xian";
  }
  
} 
function changeb(){
    if(commodityForm.todayCloseFeeRate_B.value==""){
        commodityForm.todayCloseFeeRate_B.value = commodityForm.feeRate_B.value;
        commodityForm.historyCloseFeeRate_B.value = commodityForm.feeRate_B.value;
    }
 }
 function changes(){
    if(commodityForm.todayCloseFeeRate_S.value==""){
        commodityForm.todayCloseFeeRate_S.value = commodityForm.feeRate_S.value;
        commodityForm.historyCloseFeeRate_S.value = commodityForm.feeRate_S.value;
    } 
 }  
 
  function marginPriceType_change(){
 	var marginAlgr = commodityForm.marginAlgr.value;
 	var tdName = document.getElementById("td1s");
 	if (marginAlgr == 1) {
 		tdName.innerHTML = '��֤��������ݣ�';
 		document.getElementById("td1s").className = "xian";
 		document.getElementById("td2s").className = "xian";
 		commodityForm.marginPriceType.value = "";
 	}else {
 		tdName.innerHTML = '';
 		document.getElementById("td1s").className = "yin";
 		document.getElementById("td2s").className = "yin";
 		commodityForm.marginPriceType.value = "0";
 	}
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
 function suffixNamePress2()
 {
 	
   if (event.keyCode<45 || event.keyCode>57 || event.keyCode == 47) 
   {
     event.returnValue=false;
   }
   else
   {
     event.returnValue=true;
   }
 }

function change_date(){
	commodityForm.settleDate1.value = commodityForm.marketDate.value;
}

function change_date_settle(){
	document.getElementById("settleDate7").value = commodityForm.settleDate.value;
}

 function changeManner101(id){
 	if (id == "1") {
 		setReadWrite(commodityForm.limitCmdtyQty);
 	}
 	if (id == "2") {
 		commodityForm.limitCmdtyQty.value = "-1";
 		setReadOnly(commodityForm.limitCmdtyQty);
 	}
 }
 
  function changeManner102(id){
 	if (id == "1") {
 		setReadWrite(commodityForm.firmCleanQty);
 	}
 	if (id == "2") {
 		commodityForm.firmCleanQty.value = "-1";
 		setReadOnly(commodityForm.firmCleanQty);
 	}
 }
 
  function changeManner104(id){
 	if (id == "1") {
 		setReadWrite(commodityForm.oneMaxHoldQty);
 	}
 	if (id == "2") {
 		commodityForm.oneMaxHoldQty.value = "-1";
 		setReadOnly(commodityForm.oneMaxHoldQty);
 	}
 }
 
 function changeManner103(id){
 	if (id == "1") {
 		setReadWrite(commodityForm.firmMaxHoldQty);
 		//���ƽ����̶����������㷨����
 		document.getElementById("tdFirmMaxHoldQtyAlgr").style.visibility = 'visible';
 		changeFirmMaxHoldQtyAlgr();
 	}
 	if (id == "2") {
 		document.getElementById("tdFirmMaxHoldQtyAlgr").style.visibility = 'hidden';
 		document.getElementById("tdStartPercentQty").style.visibility = 'hidden';
 		document.getElementById("tdMaxPercentLimit").style.visibility = 'hidden';
 		document.getElementById("tdFirmMaxHoldQty").style.visibility = 'visible';
 		commodityForm.firmMaxHoldQty.value = "-1";
 		setReadOnly(commodityForm.firmMaxHoldQty);
 	}
 }
 
 	function changeFirmMaxHoldQtyAlgr(){
 		if (commodityForm.firmMaxHoldQtyAlgr.value == "1") {
 			document.getElementById("tdStartPercentQty").style.visibility = 'visible';
 			document.getElementById("tdMaxPercentLimit").style.visibility = 'visible';
 			document.getElementById("tdFirmMaxHoldQty").style.visibility = 'hidden';
 		}else {
 			document.getElementById("tdStartPercentQty").style.visibility = 'hidden';
 			document.getElementById("tdMaxPercentLimit").style.visibility = 'hidden';
 			document.getElementById("tdFirmMaxHoldQty").style.visibility = 'visible';
 		}
 	}
 
function change(){
	var settlePriceType = document.forms(0).settlePriceType.value;
	
	if (settlePriceType == "3") {//ָ�����ռ�
		document.getElementById("bbb0").className = "yin";
	 	document.getElementById("aaa0").className = "yin";
		document.getElementById("ccc0").className = "xian";
	 	document.getElementById("ddd0").className = "xian";
	}else if (settlePriceType == "1"){//���㽻�ռ۵���ǰ��
		document.getElementById("bbb0").className = "xian";
	 	document.getElementById("aaa0").className = "xian";
	 	document.getElementById("ccc0").className = "yin";
	 	document.getElementById("ddd0").className = "yin";
		
	}else {
		document.getElementById("bbb0").className = "yin";
	 	document.getElementById("aaa0").className = "yin";
	 	document.getElementById("ccc0").className = "yin";
	 	document.getElementById("ddd0").className = "yin";
	}
}
function change2(){
	var settleMarginType = document.forms(0).settleMarginType.value;
	
	if (settleMarginType == "1"){//���㽻�ռ۵���ǰ��
		document.getElementById("bbb02").className = "xian";
	 	document.getElementById("aaa02").className = "xian";
	}else {
		document.getElementById("bbb02").className = "yin";
	 	document.getElementById("aaa02").className = "yin";
	}
}

function settleFeeAlgr_change(){
	if (commodityForm.settleFeeAlgr.value == "1") {
		document.getElementById("settleFeeRate_BPercent").className = "xian";
		document.getElementById("settleFeeRate_SPercent").className = "xian";
	}else {
		document.getElementById("settleFeeRate_BPercent").className = "yin";
		document.getElementById("settleFeeRate_SPercent").className = "yin";
	}
}

function settleMarginAlgr_B_change(){
	if (commodityForm.settleMarginAlgr_B.value == "1") {
		document.getElementById("settleMarginRate_BPercent").className = "xian";
	}else {
		document.getElementById("settleMarginRate_BPercent").className = "yin";
	}
}

function settleMarginAlgr_S_change(){
	if (commodityForm.settleMarginAlgr_S.value == "1") {
		document.getElementById("settleMarginRate_SPercent").className = "xian";
	}else {
		document.getElementById("settleMarginRate_SPercent").className = "yin";
	}
}

function payoutAlgr_change(){
	if (commodityForm.payoutAlgr.value == "1") {
		document.getElementById("payoutRatePercent").className = "xian";
	}else {
		document.getElementById("payoutRatePercent").className = "yin";
	}
}

function marginAlgr_change(){
	if (commodityForm.marginAlgr.value == "1") {
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
		
		document.getElementById("marginRate_BPercent").innerHTML = '%';
		document.getElementById("marginRate_SPercent").innerHTML = '%';
		
		//commodityForm.marginRate_B.value = commodityForm.marginRate_B.value*100;
    	//commodityForm.marginRate_S.value = commodityForm.marginRate_S.value*100;
		
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
		
		document.getElementById("marginRate_BPercent").innerHTML = '';
		document.getElementById("marginRate_SPercent").innerHTML = '';
		
		//commodityForm.marginRate_B.value = commodityForm.marginRate_B.value/100;
		//commodityForm.marginRate_S.value = commodityForm.marginRate_S.value/100;
	}
	
	
}
 
 		function settleWay_settleDate(){
 			var myDate = new Date();
			var time = myDate.toLocaleString().substring(0,4);
			var relTime = parseInt(time);
			var day = 0;
			for (i = relTime + 100; i > relTime; i--) {
				var y = i % 4;
				if (y == 0) {
					day = day + 366;
				}else {
					day = day + 365;
				}
			}
			myDate.setDate(myDate.getDate()+day);
			var date = myDate.toLocaleString().split(" ")[0];
			var year = date.indexOf("��");
			var month = date.indexOf("��");
			var day = date.indexOf("��");	
			var relDate = date.substring(0,year) + "-" + date.substring(year+1,month) + "-" + date.substring(month+1,day);
			
			if (commodityForm.settleWay.value == "1") {
				document.getElementById("settleDate7").value = relDate;
				commodityForm.settleDate.value = relDate;
				setReadOnly(commodityForm.settleDate);
			}else {
				setReadWrite(commodityForm.settleDate);
			}
 		}
 		function changeHoldDays(){
 			var holdDaysLimit=document.getElementById("holdDaysLimit").value;
 			var maxHoldDay=document.getElementById("holdDays");
 			if(holdDaysLimit==1){
 				maxHoldDay.style.visibility='visible';
 			}else{
 				maxHoldDay.style.visibility='hidden';
 			}
 		}
</script>
	</head>

	<body leftmargin="0" topmargin="0" onLoad="return window_onload()"
		onkeypress="keyEnter(event.keyCode);">
		<table border="0" height="400" width="790" align="center">
			<tr>
				<td>
					<html:form action="/timebargain/baseinfo/commodity.do?funcflg=save"
						method="POST" styleClass="form" target="HiddFrame">
						<fieldset>
							<legend class="common">
								<b>������Ʒ��Ϣ
								</b>
							</legend>
<table width="700" border="0" align="center"  class="common" cellpadding="0" cellspacing="0">
<!-- ������Ϣ -->
        <tr class="common">
          <td colspan="4">
            <fieldset>
              <legend class="common">
                <table cellspacing="0" cellpadding="0" border="0" width="700" class="common" >
                  <col width="65"></col><col></col><col width="6"></col>
                  <tr>
                    <td><b>������Ϣ</b></td>
                    <td><hr width="629" class="pickList"/></td>
                    <td ><img id="baseinfo_img" src="<c:url value="/timebargain/images/ctl_detail_Up.gif"/>" style="cursor:hand" onclick="javascript:baseinfo_onclick()"/></td>
                  </tr>
                </table>
              </legend>
<span id="baseinfo">
<table cellSpacing="0" cellPadding="0"  width="100%" border="0" align="center" class="common">
        
        <tr >
        	<td align="right" width="138">��ƷƷ�֣�</td>
            <td >
            
            <%
               LabelValue lv1=(LabelValue)request.getAttribute("LabelValue1");
               if(lv1!=null)
               {
             %>
             <%=lv1.getLabel()%>
            <%
            }
             %>
            <html:hidden property="breedID"/>
            <span class="req">&nbsp;</span>
            </td> 
            <td align="right" width="140">Ʒ�ַ��ࣺ</td>
            <td>
				
                <%
               LabelValue lv2=(LabelValue)request.getAttribute("LabelValue2");
               if(lv2!=null)
               {
             %>
             <%=lv2.getLabel()%>
            <%
            }
             %>
                <html:hidden property="sortID"/>

			</td>    
			
        </tr>
        
        <tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
        
        <tr>
             
            <td align="right">��Ʒ���ƣ�</td>
            <td ><html:text property="name" onkeypress="chackValue2()" onkeyup="value=value.replace(/[\n\s\t]/g,'') " maxlength="16" styleClass="text" size="10"/>
            <span class="req">*</span></td> 
            
            <td align="right">��Ʒ���룺</td>
            <td ><html:text property="commodityID" onkeypress="chackValue()" onkeydown="chackValue3()" onkeyup="value=value.replace(/[\n\s\t]/g,'') " style="ime-mode:disabled"  maxlength="11" styleClass="text" size="10"/>
            <!-- ����Ʒ���������ơ�������Ʒ���벻������   >,<, �� /,�»���(_) -->
            <script type="text/javascript">
            	function chackValue(){
            		if (event.keyCode==47  || event.keyCode==60 || event.keyCode==62 || event.keyCode==32 )
  					{
   						 event.returnValue=false;
  					}
  	         	}
  	         	function chackValue2(){
            		if (event.keyCode==32)
  					{
   						 event.returnValue=false;
  					}
  	         	}
  	         	function chackValue3(){
  	         	   
  	         	   if ((event.shiftKey)&&(event.keyCode==189)){ //���� shift+_(�»���) 
                         event.returnValue=false; 
                   } 
  	         	}
            </script>
            <span class="req">*</span></td>
            
            <c:if test="${param['crud'] == 'create'}">
            <td align="right" width="80">��ǰ״̬��</td>
            	<td width="110">
				<html:select property="status" style="width:80">
					<html:option value=""></html:option>
				    <html:option value="0">��Ч</html:option>
					<html:option value="2">��ͣ����</html:option>					
			   </html:select> 
			   <span class="req">*</span>            
            </td>  
            </c:if>
            <c:if test="${param['crud'] == 'update'}">
            
            	<%
            		String status = (String)request.getAttribute("status");
            		String name = (String)request.getAttribute("name");
            		if ("1".equals(status)) {
            			%>
            			<td align="right" width="80">��ǰ״̬��</td>
            			<td width="110">
            				<%=name%>
            				<html:hidden property="status" value="<%=status%>"/>
            			</td>
            			
            			<%
            		}else {
            		%>
            		<td align="right" width="80">��ǰ״̬��</td>
            		<td width="110">
            		<html:select property="status" style="width:80">
					<html:option value=""></html:option>
				    <html:option value="0">��Ч</html:option>
					<html:option value="2">��ͣ����</html:option>					
			   </html:select> 
			   <span class="req">*</span>
			   </td>
            	<%
            		}
            	%>
            	
            	
            	
            </c:if>
              
                               
        </tr>       
        
        <tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
        <tr>
        	  <td align="right">����ָ���ۣ�</td>
            <td>
			  <html:text property="lastPrice" maxlength="11" style="ime-mode:disabled" onkeypress="return suffixNamePress()" styleClass="text" size="10"/>  
              <span class="req">*</span>       
            </td>  
             <td align="right">�������ڣ�</td>
			<td>
				<input type="text" style="width: 100px" id="marketDate"
															class="wdate" maxlength="10"
															name="marketDate"
															value='<%=commodityForm.getMarketDate() == null ? "" : commodityForm.getMarketDate()%>'
															onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})"
															onchange="change_date()">
				<%--<MEBS:calendar eltID="marketDate" eltName="marketDate" eltCSS="date" eltStyle="width:80px" eltImgPath="<%=skinPath%>/images/" eltValue="<%=commodityForm.getMarketDate() == null ? "" : commodityForm.getMarketDate()%>" eltF="settleDate1" />--%>
				<span class="req">*</span>
			</td>
            <td align="right" >������գ�</td>
			<td>
				<input type="text" style="width: 100px" id="settleDate"
															class="wdate" maxlength="10"
															name="settleDate"
															value='<%=commodityForm.getSettleDate() == null ? "" : commodityForm.getSettleDate()%>'
															onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})"
															onchange="change_date_settle()">
				<%--<MEBS:calendar eltID="settleDate" eltName="settleDate" eltCSS="date" eltStyle="width:80px" eltImgPath="<%=skinPath%>/images/" eltValue="<%=commodityForm.getSettleDate() == null ? "" : commodityForm.getSettleDate()%>" eltF="settleDate7" />--%>
				<span class="req">*</span>
			</td>     
        </tr>
                 
</table >
</span>
            </fieldset>
          </td>
        </tr>					
        	
<!-- ������Ϣ -->
        
           
           
   <tr class="common">
          <td colspan="4">
            <fieldset>
              <legend>
                <table cellspacing="0" cellpadding="0" border="0" width="700" class="common">
                  <col width="65"></col><col></col><col width="6"></col>
                  <tr>
                    <td><b>��������</b></td>
                    <td><hr width="629" class="pickList"/></td>
                    <td ><img id="baseinfo_img3" src="<c:url value="/timebargain/images/ctl_detail_Up.gif"/>" style="cursor:hand" onclick="javascript:baseinfo3_onclick()"/></td>
                  </tr>
                </table>
              </legend>
<span id="baseinfo3">
<table cellSpacing="0" cellPadding="0" width="720" border="0" align="center" class="common">                     
         <tr>
         	<td align="right" width="140">���׵�λ��
         	</td>
         	<td width="150">
         	<html:text property="contractFactor"  maxlength="11" style="ime-mode:disabled"  styleClass="text" size="10" onkeypress="chackValue2()"/>
			  <span class="req">
			  <% 
			   if(commodityForm.getContractFactorName()!=null){
			   %>
			   (<%=commodityForm.getContractFactorName()%>/��)
			   <% 
			   }
			   %></span><span class="req">*</span>            
            </td>
            
            <td align="right" width="115">��С�䶯��λ(Ԫ)��</td><td><html:text property="minPriceMove"  maxlength="11" style="ime-mode:disabled" onkeypress="return suffixNamePress()" styleClass="text" size="10"/>  
			  <span class="req">*</span>          
            </td>
            
             <td align="right" width="100">
				��С�䶯������
			 </td>
			 <td width="115">
				<html:text size="10" property="minQuantityMove" onkeyup="value=value.replace(/[^\d]/g,'')" maxlength="10" styleClass="text" />
				<span class="req">*</span>
			</td>
		</tr>
		<tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
         <tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
          
          <%
        	String typeSpreadAlgr = (String)request.getAttribute("typeSpreadAlgr");
          %>
          <tr>
            <td align="right" >�ǵ�ͣ���㷨��
            </td>
            <td>
            <html:select property="spreadAlgr" style="width:80" onchange="spreadAlgr_onchange()">
					<html:option value=""></html:option>
				    <html:option value="1">���ٷֱ�</html:option>
					<html:option value="2">������ֵ</html:option>
			   </html:select> <span class="req">*</span>
            </td>
            <td align="right" >�Ƿ����ޣ�</td>
			<td><html:text size="10" property="spreadUpLmt" maxlength="10" style="ime-mode:disabled" onkeypress="return suffixNamePress()" styleClass="text" />
			  <span id="spreadUpLmtPercent" class="<%if("1".equals(typeSpreadAlgr)){%>xian<%}else{%>yin<%}%>">%</span><span class="req">*</span>           
            </td>
            <td align="right">�������ޣ�</td>
            <td>
			  <html:text size="10" property="spreadDownLmt" maxlength="10" style="ime-mode:disabled" onkeypress="return suffixNamePress()" styleClass="text" />
			  <span id="spreadDownLmtPercent" class="<%if("1".equals(typeSpreadAlgr)){%>xian<%}else{%>yin<%}%>">%</span><span class="req">*</span>   
			  &nbsp;     
            </td>
        </tr>   
     <tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
      		<tr>
			<td align="right" >&nbsp;��ί��Ȩ�ޣ�</td>
            <td >
				<html:select property="orderPrivilege_B" style="width:80">
					<html:option value=""></html:option>
				    <html:option value="101">ȫȨ</html:option>
					<html:option value="102">ֻ�ɶ���</html:option>
					<html:option value="103">ֻ��ת��</html:option>
					<html:option value="104">��Ȩ</html:option>
			   </html:select> <span class="req">*</span>            
            </td>  
            
            <td align="right" >&nbsp;����ί��Ȩ�ޣ�</td>
            <td >
				<html:select property="orderPrivilege_S" style="width:80">
					<html:option value=""></html:option>
				    <html:option value="101">ȫȨ</html:option>
					<html:option value="102">ֻ�ɶ���</html:option>
					<html:option value="103">ֻ��ת��</html:option>
					<html:option value="104">��Ȩ</html:option>
			   </html:select> <span class="req">*</span>            
            </td> 
            <td align="right" width="80">
				��ֵ˰�ʣ�
			</td>
			<td width="115">
				<html:text size="10" property="addedTax" maxlength="10" styleClass="text" onkeypress="return suffixNamePress()"/>
				<span >%</span><span class="req">*</span>
			</td>
			</tr>
		<tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
	<td align="right" >
					��ǰ���ռۼ��㷽ʽ��
			</td>
			<td>
				<html:select property="aheadSettlePriceType" style="width:120"  >
					<html:option value=""></html:option>
				    <html:option value="0">�������۽���</html:option>
					<html:option value="1">�������۽���</html:option> 
			    </html:select> <span class="req">*</span>
			</td><%
			String typeSettlePriceType = (String)request.getAttribute("typeSettlePriceType");
		%>
	<tr>
		<td align="right" >
					���ռۼ��㷽ʽ��
			</td>
			<td width="140">
				<html:select property="settlePriceType" style="width:120" onchange="change()">
					<html:option value=""></html:option>
				    <html:option value="0">���н����</html:option>
					<html:option value="1">����ռ�Ȩƽ����</html:option>
					<html:option value="2">������</html:option>
					<html:option value="3">ָ�����ռ�</html:option>
			    </html:select> <span class="req">*</span>
			</td>
			
			<td align="right"  class="<%if("1".equals(typeSettlePriceType)){%>xian<%}else{%>yin<%}%>" id="aaa0">
				���㽻�ռ���ǰ�գ�
			</td>
			<td width="140" class="<%if("1".equals(typeSettlePriceType)){%>xian<%}else{%>yin<%}%>" id="bbb0">
			<html:text size="10" property="beforeDays" maxlength="10" styleClass="text" onkeypress="return suffixNamePress()"/>(������)
			<span class="req">&nbsp;</span>
			</td>
									
			<td align="right" class="<%if("3".equals(typeSettlePriceType)){%>xian<%}else{%>yin<%}%>" id="ccc0">
				ָ�����ռۣ�
			</td>
			<td  class="<%if("3".equals(typeSettlePriceType)){%>xian<%}else{%>yin<%}%>" id="ddd0">
			<html:text size="10" property="specSettlePrice" maxlength="10" styleClass="text" onkeypress="return suffixNamePress()"/>
			<span width="100" class="req">&nbsp;</span>
			</td>
	</tr>
		<tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>

	 	
		<!-- add by yangpei 2011-11-02 -->
	<%
		String typeSettleMarginType = (String)request.getAttribute("typeSettleMarginType");
	 %>
	<tr>
			<td align="right" >
					���ձ�֤����㷽ʽ��
			</td>
			<td>
				<html:select property="settleMarginType" style="width:120" onchange="change2()">
				    <html:option value="0">���н����</html:option>
					<html:option value="1">����ռ�Ȩƽ����</html:option>
					<html:option value="2">������</html:option>
			    </html:select> <span class="req">*</span>
			</td>
			
			<td align="right"  class="<%if("1".equals(typeSettleMarginType)){%>xian<%}else{%>yin<%}%>" id="aaa02">
				���㽻�ձ�֤����ǰ�գ�
			</td>
			<td class="<%if("1".equals(typeSettleMarginType)){%>xian<%}else{%>yin<%}%>" id="bbb02">
			<html:text size="10" property="beforeDays_M" maxlength="10" styleClass="text" onkeypress="return suffixNamePress()"/>(������)
			<span class="req">&nbsp;</span>
			</td>
									
			<td></td><td></td>
		</tr>
		<tr>
										<td align="right" style="white-space:nowrap;">
							          	 �Ƿ����óֲ��������ƣ�
							          	 </td>
							          	 <td>
							          	 	<html:select property="holdDaysLimit" style="width:120" onchange="changeHoldDays()" >
											    <html:option value="0">������</html:option>
												<html:option value="1">����</html:option>
										    </html:select> <span class="req">*</span>
							          	 </td>
										   		<td colspan="3"  align="left"
										   		<c:choose><c:when test="${holdDaysLimit=='1'}">style="visible"</c:when><c:otherwise>style="hidden"</c:otherwise></c:choose>
										   		 id="holdDays" style="white-space:nowrap;">
										   		��ֲ�����:
										   		<html:text property="maxHoldPositionDay" maxlength="11" style="ime-mode:disabled" onkeypress="return suffixNamePress()" styleClass="text" size="11"/><span>������</span><span class="req">*</span> 
										   		</td>
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
                  <col width="95"></col><col></col><col width="6"></col>
                  <tr>
                    <td><b>����������</b></td>
                    <td><hr width="609" class="pickList"/></td>
                    <td ><img id="baseinfo_img8" src="<c:url value="/timebargain/images/ctl_detail_Up.gif"/>" style="cursor:hand" onclick="javascript:baseinfo8_onclick()"/></td>
                  </tr>
                </table>
              </legend>
<span id="baseinfo8">
<table cellSpacing="0" cellPadding="0" width="720" border="0" align="center" class="common">                           
       		<tr>
       		
       			<%
							String type101 = (String)request.getAttribute("type101");
							String type102 = (String)request.getAttribute("type102");
							String type103 = (String)request.getAttribute("type103");
							String type104 = (String)request.getAttribute("type104");
							String type105 = (String)request.getAttribute("type105");
							String firmMaxHoldQtyAlgr = (String)request.getAttribute("firmMaxHoldQtyAlgr");
				%>
       		
         	<td align="right">&nbsp;&nbsp;��Ʒ������󶩻���(��)��</td>
            <td>
            
			<input type="radio" name="type101" value="2" onclick="changeManner101(2);" <%if("2".equals(type101)){out.println("checked");} %> style="border:0px;">������
            <input type="radio" name="type101" value="1" onclick="changeManner101(1);" <%if("1".equals(type101)){out.println("checked");} %> style="border:0px;">����
            <html:text property="limitCmdtyQty"  style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" size="10"/>  
			  <span class="req">*</span>          
            </td>
			
						<td align="right" width="120">
							�����̾���������
						</td>
						<td>
							
							<input type="radio" name="type102" value="2" onclick="changeManner102(2);" <%if("2".equals(type102)){out.println("checked");} %> style="border:0px;">������
							<input type="radio" name="type102" value="1" onclick="changeManner102(1);" <%if("1".equals(type102)){out.println("checked");} %> style="border:0px;">����
							<html:text property="firmCleanQty"  styleClass="text" onkeypress="return onlyNumberInput()" size="10"/>
            				<span class="req">*</span>
						</td>
						
						
					</tr>
					<tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
					<tr>
						<td align="right" >
           					���������˫�߶�������
           				</td>
			           	<td>
			           	
						<input type="radio" name="type103" value="2" onclick="changeManner103(2);" <%if("2".equals(type103)){out.println("checked");} %> style="border:0px;">������
			           	<input type="radio" name="type103" value="1" onclick="changeManner103(1);" <%if("1".equals(type103)){out.println("checked");} %> style="border:0px;">����	
			            <span id="tdFirmMaxHoldQty" style="visibility:<%if("1".equals(firmMaxHoldQtyAlgr)){%>visible<%}else{%>hidden<%}%>;">
	           				<html:text property="firmMaxHoldQty"  onkeypress="return onlyNumberInput()" styleClass="text" size="10"/>
	           			<span class="req">*</span>
			           	</td>
			           	
			<td align="right">&nbsp;&nbsp;�������ί������</td>
            <td>
            
			<input type="radio" name="type104" value="2" onclick="changeManner104(2);" <%if("2".equals(type104)){out.println("checked");} %> style="border:0px;">������
            <input type="radio" name="type104" value="1" onclick="changeManner104(1);" <%if("1".equals(type104)){out.println("checked");} %> style="border:0px;">����
            <html:text property="oneMaxHoldQty"  style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" size="10"/>  
			  <span class="req">*</span>          
            </td>
					</tr>
					
       				<tr>	
       			
           	<td id="tdFirmMaxHoldQtyAlgr" class="" colspan="4">
           		&nbsp;&nbsp;&nbsp;&nbsp;�����̶����������㷨��
           		<html:select property="firmMaxHoldQtyAlgr" style="width:80" onchange="changeFirmMaxHoldQtyAlgr()">
					<html:option value=""></html:option>
				    <html:option value="1">���ٷֱ�</html:option>
					<html:option value="2">������ֵ</html:option>
			   </html:select> 
               <span class="req">*</span>
               
               &nbsp;&nbsp;&nbsp;&nbsp;
               <span id="tdStartPercentQty" style="visibility:<%if("1".equals(firmMaxHoldQtyAlgr)){%>visible<%}else{%>hidden<%}%>;">
               		��Ʒ��������ֵ��<html:text property="startPercentQty" onkeypress="return onlyNumberInput()" styleClass="text" size="10"/>
            		<span class="req">*</span>
               </span>
               
               &nbsp;&nbsp;&nbsp;&nbsp;
               <span id="tdMaxPercentLimit" style="visibility:<%if("1".equals(firmMaxHoldQtyAlgr)){%>visible<%}else{%>hidden<%}%>;">
               		������������<html:text property="maxPercentLimit" onkeypress="return onlyNumberInput()" styleClass="text" size="10"/>
            		<span>%</span><span class="req">*</span>
               </span>
           	</td>
			</tr>
            <tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
       				
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
                    <td><b>������</b></td>
                    <td><hr width="639" class="pickList"/></td>
                    <td ><img id="baseinfo_img2" src="<c:url value="/timebargain/images/ctl_detail_Up.gif"/>" style="cursor:hand" onclick="javascript:baseinfo2_onclick()"/></td>
                  </tr>
                </table>
              </legend>
<span id="baseinfo2">
<table cellSpacing="0" cellPadding="0" width="720" border="0" align="center" class="common">                           
       
       		<%
         		String typeFeeAlgr = (String)request.getAttribute("typeFeeAlgr");
         	%>
         	<tr> 		            
		    <td align="right" rowspan="4" width="245" height="10" valign="top">
						�����������㷨��<html:select property="feeAlgr" style="width:80" onchange="on_change()">
					<html:option value=""></html:option>
				    <html:option value="1">���ٷֱ�</html:option>
					<html:option value="2">������ֵ</html:option>
			   </html:select> 
			   <span class="req">*</span>  
			</td>	            		            
		            
		           <td align="right" colspan="1" width="210" height="5">
		            	������<html:text property="feeRate_B" maxlength="6"  style="ime-mode:disabled" onkeypress="return suffixNamePress()" styleClass="text" onchange="changeb()" size="10"/>
			  			<span id="feeRate_BPercent" class="<%if("1".equals(typeFeeAlgr)){%>xian<%}else{%>yin<%}%>">%</span><span class="req">*</span>
			  	   </td>
			  	   <td align="right" >
			  		��������<html:text property="feeRate_S"  maxlength="6" style="ime-mode:disabled" onkeypress="return suffixNamePress()" styleClass="text" onchange="changes()" size="10"/>
			  		<span id="feeRate_SPercent" class="<%if("1".equals(typeFeeAlgr)){%>xian<%}else{%>yin<%}%>">%</span><span class="req">*</span>
			  	   </td>
			  	   <td width="20"></td>
		          </tr>

				   <tr> 		            
		           <td width="210" align="right" height="5">
		           	��ת����ʷ������<html:text property="historyCloseFeeRate_B" maxlength="11" style="ime-mode:disabled" onkeypress="return suffixNamePress()" styleClass="text" size="10"/>
		           	<span id="historyCloseFeeRate_BPercent" class="<%if("1".equals(typeFeeAlgr)){%>xian<%}else{%>yin<%}%>">%</span><span class="req">*</span>
		           </td>
			  	   <td align="right" >
		           	��ת����ʷ������<html:text property="historyCloseFeeRate_S" maxlength="11" style="ime-mode:disabled" onkeypress="return suffixNamePress()" styleClass="text" size="10"/>
		           	<span id="historyCloseFeeRate_SPercent" class="<%if("1".equals(typeFeeAlgr)){%>xian<%}else{%>yin<%}%>">%</span><span class="req">*</span>
		          </td>
		              </tr>

				<tr> 		            
		            <td align="right" width="210" height="5">
		            	��ת�ý񶩻���<html:text property="todayCloseFeeRate_B" maxlength="11" style="ime-mode:disabled" onkeypress="return suffixNamePress()" styleClass="text" size="10"/>
		            	<span id="todayCloseFeeRate_BPercent" class="<%if("1".equals(typeFeeAlgr)){%>xian<%}else{%>yin<%}%>">%</span><span class="req">*</span>
		            </td>
			  	   	<td align="right" >	
		            	��ת�ý񶩻���<html:text property="todayCloseFeeRate_S" maxlength="11" style="ime-mode:disabled" onkeypress="return suffixNamePress()" styleClass="text" size="10"/>
		            	<span id="todayCloseFeeRate_SPercent" class="<%if("1".equals(typeFeeAlgr)){%>xian<%}else{%>yin<%}%>">%</span><span class="req">*</span>
		            </td>
		        </tr>
		              
		              <tr>
		              	<td align="right" width="210">
		              	��ǿ��ת�ã�<html:text size="10" property="forceCloseFeeRate_B" maxlength="11" style="ime-mode:disabled" onkeypress="return suffixNamePress()" styleClass="text" onchange="changeb()"/>
			  			<span id="forceCloseFeeRate_BPercent" class="<%if("1".equals(typeFeeAlgr)){%>xian<%}else{%>yin<%}%>">%</span><span class="req">*</span>
			  			</td>
			  	   		<td align="right" >
			  			��ǿ��ת�ã�<html:text size="10" property="forceCloseFeeRate_S" maxlength="11" style="ime-mode:disabled" onkeypress="return suffixNamePress()" styleClass="text" onchange="changes()"/>
			  			<span id="forceCloseFeeRate_SPercent" class="<%if("1".equals(typeFeeAlgr)){%>xian<%}else{%>yin<%}%>">%</span><span class="req">*</span>        
            			</td>
		              </tr>
	
	 
	 
	 <tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>    
         
       
        <%
        	String typeSettleFeeAlgr = (String)request.getAttribute("typeSettleFeeAlgr");
        %>
         <tr>
        	<td align="right" >�����������㷨��<html:select property="settleFeeAlgr" style="width:80" onchange="settleFeeAlgr_change()">
					<html:option value=""></html:option>
				    <html:option value="1">���ٷֱ�</html:option>
					<html:option value="2">������ֵ</html:option>
			   </html:select> 
			   <span class="req">*</span>            
            </td>
            <td align="right" width="210">
            	������<html:text size="10" property="settleFeeRate_B" maxlength="11" style="ime-mode:disabled" onkeypress="return suffixNamePress()" styleClass="text" onchange="changeb()"/>
			  <span id="settleFeeRate_BPercent" class="<%if("1".equals(typeSettleFeeAlgr)){%>xian<%}else{%>yin<%}%>">%</span><span class="req">*</span>
			  </td>
			  <td align="right" >
			  ��������<html:text size="10" property="settleFeeRate_S" maxlength="11" style="ime-mode:disabled" onkeypress="return suffixNamePress()" styleClass="text" onchange="changes()"/>
			  <span id="settleFeeRate_SPercent" class="<%if("1".equals(typeSettleFeeAlgr)){%>xian<%}else{%>yin<%}%>">%</span><span class="req">*</span>         
           </td>
        </tr> 
         <tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
    <tr>
    	
            <td align="right">
				��ͽ��������ѽ�<html:text property="lowestSettleFee" maxlength="11" style="ime-mode:disabled" onkeypress="return suffixNamePress()" styleClass="text" size="10"/>
			   <span class="req">*</span>            
            </td>	
            <td align="right">&nbsp;
            <!-- 
				�����������ϵ����<html:text property="maxFeeRate" maxlength="15" style="ime-mode:disabled" onkeypress="return suffixNamePress()" styleClass="text" size="10"/>
			   <span>%</span><span class="req">*</span>            
             -->
            </td>		
    </tr>
        
        </table >
</span>
            </fieldset>
          </td>
        </tr>	
	
				
        	
<!-- ��ؽ�����Ϣ -->
        <tr class="common">
          <td colspan="4">
            <fieldset>
              <legend>
                <table cellspacing="0" cellpadding="0" border="0" width="700" class="common">
                  <col width="55"></col><col></col><col width="6"></col>
                  <tr>
                    <td><b>��֤��</b></td>
                    <td><hr width="639" class="pickList"/></td>
                    <td ><img id="settleinfo_img" src="<c:url value="/timebargain/images/ctl_detail_Up.gif"/>" style="cursor:hand" onclick="javascript:settleinfo_onclick()"/></td>
                  </tr>
                </table>
              </legend>
<span id="settleinfo">
<table cellSpacing="0" cellPadding="0" width="720" border="0" align="center" class="common">  
           <tr>
            <td align="right" width="150">&nbsp;&nbsp;&nbsp;&nbsp;��֤���㷨��
            </td>
            
            <td width="98">
            <html:select property="marginAlgr" style="width:80" onchange="marginPriceType_change();marginAlgr_change()">
					<html:option value=""></html:option>
				    <html:option value="1">���ٷֱ�</html:option>
					<html:option value="2">������ֵ</html:option>
			   </html:select> 
			   <span class="req">*</span>            
            </td>
            
            			
            			<%
            				String typeBZJ = (String)request.getAttribute("typeBZJ");
            			%>
            			<td align="right" width="100" id="td1s" class="<%if("1".equals(typeBZJ)){%>xian<%}else{%>yin<%}%>">
											��֤��������ݣ�
									</td>
									<td width="260" id="td2s" colspan="3" class="<%if("1".equals(typeBZJ)){%>xian<%}else{%>yin<%}%>">
										<html:select property="marginPriceType" style="width:180" >
										   <html:option value=""></html:option>
										   <c:if test="${floatingType<3}">
				                           <html:option value="0">������</html:option>
				                           </c:if>
				                           <c:if test="${floatingType>=3}">
					                       <html:option value="1">������</html:option> 
					                       <html:option value="2">���а��������̺󰴽����</html:option>
					                       </c:if>
			                  	 </html:select> <span class="req">*</span>
						</td>
            
            
              
              
            </tr>
            
            
           <tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
            
           
           
            <%
             String type1=(String)request.getAttribute("type1");
             %>            
        <tr>
            <td align="right" width="150">��һ�׶α�֤�� ��Ч���ڣ�</td>
			<td >
			  	<html:text size="10" property="settleDate1" styleId="settleDate1" maxlength="19" style="ime-mode:disabled;width:80;" onkeypress="return numberPass()" styleClass="text"/>
				<span class="req">*</span>
			</td>
			
			<td align="right">
           			 ������֤��
            </td>
            <td colspan="3">
            
             <input type="radio" name="type" value="1" onclick="changeManner(1);" <%if("1".equals(type)){out.println("checked");} %> style="border:0px;">��ͬ
             
             <input type="radio" name="type" value="2" onclick="changeManner(2);" <%if("2".equals(type)){out.println("checked");} %> style="border:0px;">��ͬ
            </td >
			
			
        </tr>
        <tr>
        	<td>&nbsp;</td>
            <td width="100">&nbsp;</td>
            
             <td align="right" id="td1"><%if("1".equals(type)){%>��֤��1<%}else{%>��֤��1<%}%>��</td>
            <td>
			  <html:text size="10" property="marginItem1" maxlength="11" style="ime-mode:disabled" onkeypress="return suffixNamePress2()" styleClass="text" />
			  <span id="marginItem1Percent"></span><span class="req">*</span>           
            </td>
            <td align="right" width="130" id="td2"><%if("1".equals(type)){%>������1<%}else{%>�򵣱���1<%}%>��</td>
            <td width="115">
			  <html:text size="10" property="marginItemAssure1" maxlength="11" style="ime-mode:disabled" onkeypress="return suffixNamePress()" styleClass="text" />
			  <span id="marginItemAssure1Percent"></span><span class="req">*</span>           
            </td>     
            </tr>
           <tr class="<%if("1".equals(type)){%>yin<%}else{%>xian<%}%>" id="aaa">
             <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td align="right"  id="abc2">����֤��1��</td>
            <td  id="abc1" style="visibility:<%if("1".equals(type)){%>hidden<%}else{%>visible<%}%>;">
			  <html:text size="10" property="marginItem1_S" maxlength="11"  onkeypress="return suffixNamePress2()" styleClass="text" />
			  <span id="marginItem1_SPercent"></span><span class="req">*</span>           
            </td >
            <td align="right"  id="abc3">
            ��������1��</td>
            <td  id="abc4" style="visibility:<%if("1".equals(type)){%>hidden<%}else{%>visible<%}%>;">
			  <html:text size="10" property="marginItemAssure1_S" maxlength="11"  onkeypress="return suffixNamePress()" styleClass="text" />
			  <span id="marginItemAssure1_SPercent"></span><span class="req">*</span>           
            </td>
        </tr>
        
        <tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
        
          <%
             String type2=(String)request.getAttribute("type2");
             %>
        <tr>
            <td align="right" >�ڶ��׶α�֤�� ��Ч���ڣ�</td>
			<td>
				<input type="text" style="width: 100px" id="settleDate2"
															class="wdate" maxlength="10"
															name="settleDate2"
															value='<%=commodityForm.getSettleDate2() == null ? "" : commodityForm.getSettleDate2()%>'
															onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
				<%--<MEBS:calendar eltID="settleDate2" eltName="settleDate2" eltCSS="date" eltStyle="width:80px" eltImgPath="<%=skinPath%>/images/" eltValue="<%=commodityForm.getSettleDate2() == null ? "" : commodityForm.getSettleDate2()%>" />--%>
			</td>
			<td align="right">
            ������֤��
            </td>
			<td colspan="3">
            
             <input type="radio" name="type1" value="1" onclick="changeManner1(1);" <%if("1".equals(type1)){out.println("checked");} %> style="border:0px;">��ͬ
             
             <input type="radio" name="type1" value="2" onclick="changeManner1(2);" <%if("2".equals(type1)){out.println("checked");} %> style="border:0px;">��ͬ
            </td >
			
			
            </tr>
            <tr>
        <td >&nbsp;</td>
        <td>&nbsp;</td>
        <td align="right" id="td3">&nbsp;&nbsp;<%if("1".equals(type1)){%>��֤��2<%}else{%>��֤��2<%}%>��</td>
            <td>
			  <html:text size="10" property="marginItem2" maxlength="16" style="ime-mode:disabled" onkeypress="return suffixNamePress2()" styleClass="text" />         
            <span id="marginItem2Percent"></span>
            </td> 
            <td align="right" id="td4">&nbsp;&nbsp;<%if("1".equals(type1)){%>������2<%}else{%>�򵣱���2<%}%>��</td>
            <td>
			  <html:text size="10" property="marginItemAssure2" maxlength="16" style="ime-mode:disabled" onkeypress="return suffixNamePress()" styleClass="text" />         
            <span id="marginItemAssure2Percent"></span>
            </td>  
        </tr>
        <tr class="<%if("1".equals(type1)){%>yin<%}else{%>xian<%}%>" id="aaa1">
            <td>&nbsp;</td>
            <td >
            &nbsp;
            </td >
			<td align="right"  id="abc21">&nbsp;&nbsp;����֤��2��</td>
            <td style="visibility:<%if("1".equals(type1)){%>hidden<%}else{%>visible<%}%>;" id="abc11">
			  <html:text size="10" property="marginItem2_S" maxlength="16" style="ime-mode:disabled" onkeypress="return suffixNamePress2()" styleClass="text" />         
            <span id="marginItem2_SPercent"></span>
            </td> 
            <td align="right" id="abc31">&nbsp;&nbsp;��������2��</td>
            <td style="visibility:<%if("1".equals(type1)){%>hidden<%}else{%>visible<%}%>;" id="abc41">
			  <html:text size="10" property="marginItemAssure2_S" maxlength="16" style="ime-mode:disabled" onkeypress="return suffixNamePress()" styleClass="text" />         
            <span id="marginItemAssure2_SPercent"></span>
            </td>           
        </tr>
            
          <tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>  
               
         <%
             String type3=(String)request.getAttribute("type3");
             %>
        <tr>
            <td align="right">�����׶α�֤�� ��Ч���ڣ�</td>
			<td>
				<%--<MEBS:calendar eltID="settleDate3" eltName="settleDate3" eltCSS="date" eltStyle="width:80px" eltImgPath="<%=skinPath%>/images/" eltValue="<%=commodityForm.getSettleDate3() == null ? "" : commodityForm.getSettleDate3()%>" />--%>
				<input type="text" style="width: 100px" id="settleDate3"
															class="wdate" maxlength="10"
															name="settleDate3"
															value='<%=commodityForm.getSettleDate3() == null ? "" : commodityForm.getSettleDate3()%>'
															onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
			</td>
			<td align="right">
            ������֤��
            </td>
			<td colspan="3">
          
             <input type="radio" name="type2" value="1" onclick="changeManner2(1);" <%if("1".equals(type2)){out.println("checked");} %> style="border:0px;">��ͬ
             <input type="radio" name="type2" value="2" onclick="changeManner2(2);" <%if("2".equals(type2)){out.println("checked");} %> style="border:0px;">��ͬ
            </td >
			
			
			</tr>
			<tr >
            <td>&nbsp;</td>
            <td>&nbsp;</td>
			<td align="right" id="td5"><%if("1".equals(type2)){%>��֤��3<%}else{%>��֤��3<%}%>��</td>
            <td>
			  <html:text size="10" property="marginItem3" maxlength="16" style="ime-mode:disabled" onkeypress="return suffixNamePress2()" styleClass="text" />        
            <span id="marginItem3Percent"></span>
            </td>
            <td align="right" id="td6"><%if("1".equals(type2)){%>������3<%}else{%>�򵣱���3<%}%>��</td>
            <td>
			  <html:text size="10" property="marginItemAssure3" maxlength="16" style="ime-mode:disabled" onkeypress="return suffixNamePress()" styleClass="text" />        
            <span id="marginItemAssure3Percent"></span>
            </td>
            </tr>   
             <tr class="<%if("1".equals(type2)){%>yin<%}else{%>xian<%}%>" id="aaa2">
            <td>&nbsp;</td>
            <td>&nbsp;</td>
			<td align="right" id="abc22">����֤��3��</td>
            <td style="visibility:<%if("1".equals(type2)){%>hidden<%}else{%>visible<%}%>;" id="abc12">
			  <html:text size="10" property="marginItem3_S" maxlength="16" style="ime-mode:disabled" onkeypress="return suffixNamePress2()" styleClass="text" />        
            <span id="marginItem3_SPercent"></span>
            </td>
            <td align="right"  id="abc32">��������3��</td>
            <td style="visibility:<%if("1".equals(type2)){%>hidden<%}else{%>visible<%}%>;" id="abc42">
			  <html:text size="10" property="marginItemAssure3_S" maxlength="16" style="ime-mode:disabled" onkeypress="return suffixNamePress()" styleClass="text" />        
            <span id="marginItemAssure3_SPercent"></span>
            </td>
            </tr>
			
			<tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
			
          <%
             String type4=(String)request.getAttribute("type4");
             %>
        <tr>
            <td align="right" >���Ľ׶α�֤�� ��Ч���ڣ�</td>
			<td>
				<input type="text" style="width: 100px" id="settleDate4"
															class="wdate" maxlength="10"
															name="settleDate4"
															value='<%=commodityForm.getSettleDate4() == null ? "" : commodityForm.getSettleDate4()%>'
															onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
				<%--<MEBS:calendar eltID="settleDate4" eltName="settleDate4" eltCSS="date" eltStyle="width:80px" eltImgPath="<%=skinPath%>/images/" eltValue="<%=commodityForm.getSettleDate4() == null ? "" : commodityForm.getSettleDate4()%>" />--%>
			</td>
			<td align="right">
            ������֤��
            </td>
			<td colspan="3">
            
             <input type="radio" name="type3" value="1" onclick="changeManner3(1);" <%if("1".equals(type3)){out.println("checked");} %> style="border:0px;">��ͬ
             <input type="radio" name="type3" value="2" onclick="changeManner3(2);" <%if("2".equals(type3)){out.println("checked");} %> style="border:0px;">��ͬ
            </td >
			
			
            </tr>
            <tr>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
            <td align="right" id="td7"><%if("1".equals(type3)){%>��֤��4<%}else{%>��֤��4<%}%>��</td>
            <td>
			  <html:text size="10" property="marginItem4" maxlength="16" style="ime-mode:disabled" onkeypress="return suffixNamePress2()" styleClass="text" />           
            <span id="marginItem4Percent"></span>
            </td>
            <td align="right" id="td8"><%if("1".equals(type3)){%>������4<%}else{%>�򵣱���4<%}%>��</td>
            <td>
			  <html:text size="10" property="marginItemAssure4" maxlength="16" style="ime-mode:disabled" onkeypress="return suffixNamePress()" styleClass="text" />           
            <span id="marginItemAssure4Percent"></span>
            </td>
        </tr>
         <tr class="<%if("1".equals(type3)){%>yin<%}else{%>xian<%}%>" id="aaa3">
             <td>&nbsp;</td>
             <td>&nbsp;</td>
            <td align="right" id="abc13">����֤��4��</td>
            <td  id="abc23" style="visibility:<%if("1".equals(type3)){%>hidden<%}else{%>visible<%}%>;">
			  <html:text size="10" property="marginItem4_S" maxlength="16" style="ime-mode:disabled" onkeypress="return suffixNamePress2()" styleClass="text" />           
            <span id="marginItem4_SPercent"></span>
            </td>
            <td align="right" id="abc33">��������4��</td>
            <td  id="abc43" style="visibility:<%if("1".equals(type3)){%>hidden<%}else{%>visible<%}%>;">
			  <html:text size="10" property="marginItemAssure4_S" maxlength="16" style="ime-mode:disabled" onkeypress="return suffixNamePress()" styleClass="text" />           
            <span id="marginItemAssure4_SPercent"></span>
            </td>
        </tr>
            
            <tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
           
       <tr>
            <td align="right" >����׶α�֤�� ��Ч���ڣ�</td>
			<td>
				<input type="text" style="width: 100px" id="settleDate5"
															class="wdate" maxlength="10"
															name="settleDate5"
															value='<%=commodityForm.getSettleDate5() == null ? "" : commodityForm.getSettleDate5()%>'
															onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
				
				<%--<MEBS:calendar eltID="settleDate5" eltName="settleDate5" eltCSS="date" eltStyle="width:80px" eltImgPath="<%=skinPath%>/images/" eltValue="<%=commodityForm.getSettleDate5() == null ? "" : commodityForm.getSettleDate5()%>" />--%>
			</td>
			<td align="right">
            ������֤��
            </td>
			<td colspan="3">
            
             <input type="radio" name="type4" value="1" onclick="changeManner4(1);" <%if("1".equals(type4)){out.println("checked");} %> style="border:0px;">��ͬ
             <input type="radio" name="type4" value="2" onclick="changeManner4(2);" <%if("2".equals(type4)){out.println("checked");} %> style="border:0px;">��ͬ
            </td >
			
			
            </tr>
            <tr>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
            <td align="right" id="td50"><%if("1".equals(type4)){%>��֤��5<%}else{%>��֤��5<%}%>��</td>
            <td>
			  <html:text size="10" property="marginItem5" maxlength="16" style="ime-mode:disabled" onkeypress="return suffixNamePress2()" styleClass="text" />           
           <span id="marginItem5Percent"></span>
            </td>
            <td align="right" id="td60"><%if("1".equals(type4)){%>������5<%}else{%>�򵣱���5<%}%>��</td>
            <td>
			  <html:text size="10" property="marginItemAssure5" maxlength="16" style="ime-mode:disabled" onkeypress="return suffixNamePress()" styleClass="text" />           
            <span id="marginItemAssure5Percent"></span>
            </td>
        </tr>
         <tr class="<%if("1".equals(type4)){%>yin<%}else{%>xian<%}%>" id="aaa4">
             <td>&nbsp;</td>
             <td>&nbsp;</td>
            <td align="right" id="abc14">����֤��5��</td>
            <td  id="abc24" style="visibility:<%if("1".equals(type4)){%>hidden<%}else{%>visible<%}%>;">
			  <html:text size="10" property="marginItem5_S" maxlength="16" style="ime-mode:disabled" onkeypress="return suffixNamePress2()" styleClass="text" />           
            <span id="marginItem5_SPercent"></span>
            </td>
            <td align="right" id="abc34">��������5��</td>
            <td  id="abc44" style="visibility:<%if("1".equals(type4)){%>hidden<%}else{%>visible<%}%>;">
			  <html:text size="10" property="marginItemAssure5_S" maxlength="16" style="ime-mode:disabled" onkeypress="return suffixNamePress()" styleClass="text" />           
            <span id="marginItemAssure5_SPercent"></span>
            </td>
        </tr>
            
            <tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
           <tr>
        	<td align="right">
        		<font color="blue">��ǰ��Ч���ױ�֤��</font>
        	</td>
        	
        </tr>
        <tr>
        	<td align="right">
        		<font color="blue">�򷽱�֤��</font>
        	</td>
        	<td>
			  <html:text size="10" property="marginRate_B" maxlength="16" style="ime-mode:disabled" onkeypress="return suffixNamePress()" styleClass="text" />         
              <span id="marginRate_BPercent"></span>
            </td>
            <td align="right">
        		<font color="blue">������֤��</font>
        	</td>
        	<td>
			  <html:text size="10" property="marginRate_S" maxlength="16" style="ime-mode:disabled" onkeypress="return suffixNamePress()" styleClass="text" />         
              <span id="marginRate_SPercent"></span>
            </td>
        </tr>
           
        <tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>    
        
        <tr>
        	<td align="right">
        		���ձ�֤�� ��Ч���ڣ�
        	</td>
        	<td >
				<input type="text" id="settleDate7"  style="ime-mode:disabled;width:80;" onkeypress="return numberPass()"/>
				<span class="req">*</span>
			</td> 
        
        <%
        	String typeSettleMarginAlgr_B = (String)request.getAttribute("typeSettleMarginAlgr_B");
        %>
            <td align="right" id="td9">�򷽷�ʽ��</td>
            <td>
            <html:select property="settleMarginAlgr_B" style="width:80" onchange="settleMarginAlgr_B_change()">
					<html:option value=""></html:option>
				    <html:option value="1">���ٷֱ�</html:option>
					<html:option value="2">������ֵ</html:option>
			   </html:select>
            <span class="req">*</span>
            </td>
            <td align="right" id="td0">�򷽱�׼��</td>
            <td>
			  <html:text size="10" property="settleMarginRate_B" maxlength="16" style="ime-mode:disabled" onkeypress="return suffixNamePress()" styleClass="text" />         
            <span id="settleMarginRate_BPercent" class="<%if("1".equals(typeSettleMarginAlgr_B)){%>xian<%}else{%>yin<%}%>">%</span><span class="req">*</span>
            </td>
        </tr>
        
       
        <%
        	String typeSettleMarginAlgr_S = (String)request.getAttribute("typeSettleMarginAlgr_S");
        %>     
        <tr id="aaa4">
             <td>&nbsp;</td>
             <td>&nbsp;</td>
            <td align="right"  id="abc14">������ʽ��</td>
            <td  >
            <html:select property="settleMarginAlgr_S" style="width:80" onchange="settleMarginAlgr_S_change()">
					<html:option value=""></html:option>
				    <html:option value="1">���ٷֱ�</html:option>
					<html:option value="2">������ֵ</html:option>
			   </html:select>
            <span class="req">*</span>
            </td>
            <td align="right"  id="abc34">������׼��</td>
            <td>
			  <html:text size="10" property="settleMarginRate_S" maxlength="16" style="ime-mode:disabled" onkeypress="return suffixNamePress()" styleClass="text" />         
            <span id="settleMarginRate_SPercent" class="<%if("1".equals(typeSettleMarginAlgr_S)){%>xian<%}else{%>yin<%}%>">%</span><span class="req">*</span>
            </td>
        </tr>
        
        <%
        	String typePayoutAlgr = (String)request.getAttribute("typePayoutAlgr");
        %>
        <tr>
        	<td align="right"  id="abc14">���ջ����㷨��</td>
            <td  >
            <html:select property="payoutAlgr" style="width:80" onchange="payoutAlgr_change()">
					<html:option value=""></html:option>
				    <html:option value="1">���ٷֱ�</html:option>
					<html:option value="2">������ֵ</html:option>
			   </html:select>
            <span class="req">*</span>
            </td>
            <td align="right"  id="abc34">���ջ����׼��</td>
            <td>
			  <html:text size="10" property="payoutRate" maxlength="16" style="ime-mode:disabled" onkeypress="return suffixNamePress()" styleClass="text" />         
            <span id="payoutRatePercent" class="<%if("1".equals(typePayoutAlgr)){%>xian<%}else{%>yin<%}%>">%</span><span class="req">*</span>
            </td>
        </tr>
        
        
          
								<tr>
									<td colspan="4" height="3"></td>
								</tr>
								
								        
								
							</table>
						</fieldset>
						</td>
						</tr>
						
		<!-- ���ڽ�����Ϣ -->
		<%
			String useDelay = (String) request.getAttribute("useDelay");
			if (useDelay.equals("Y")) {
		%>
        <tr class="common">
					          <td colspan="4">
					            <fieldset>
					              <legend>
					                <table cellspacing="0" cellpadding="0" border="0" width="700" class="common">
					                  <col width="95"></col><col></col><col width="6"></col>
					                  <tr>
					                    <td><b>���ڽ�����Ϣ</b></td>
					                    <td><hr width="599" class="pickList"/></td>
					                    <td ><img id="baseinfo_imgy" src="<c:url value="/timebargain/images/ctl_detail_Up.gif"/>" style="cursor:hand" onclick="javascript:baseinfoy_onclick()"/></td>
					                  </tr>
					                </table>
					              </legend>
					<span id="baseinfoy">
					<table cellSpacing="0" cellPadding="0" width="700" border="0" align="left" class="common">
        
        <tr>
        	
            <td align="right" width="100">�����ڲ����ʣ�</td>
            <td ><html:text property="delayRecoupRate" maxlength="13" styleClass="text" size="10" onkeypress="suffixNamePress()"/>
            <span >%</span><span class="req">*</span></td>
            
            <td align="right">�ִ������ѣ�</td>
            <td ><html:text property="storeRecoupRate" maxlength="13" styleClass="text" size="10" onkeypress="suffixNamePress()"/>
            <span class="req">*</span></td>
        </tr>
        <tr> 
            <td align="right" width="100">�������ڲ����ʣ�</td>
            <td >
				 <html:text property="delayRecoupRate_S" maxlength="13" styleClass="text" size="10" onkeypress="suffixNamePress()"/>
            <span >%</span><span class="req">*</span></td>
            
            <td align="right">���շ�ʽ��</td>     
            <td>  
				<html:select property="settleWay"  onchange="settleWay_settleDate()">
					<html:option value=""></html:option>
                   <html:option value="0">���ڽ���</html:option>
                   <html:option value="1">���ڽ���</html:option>
                </html:select>
				<span class="req">*</span>
			</td>
			
			
        </tr>
        
        <tr>
			<td align="right" width="100">��С���λ��
			 </td>
			 <td width="115">
				<html:text size="10" property="minSettleMoveQty" onkeyup="value=value.replace(/[^\d]/g,'')" maxlength="10" styleClass="text" />
				<span class="req">*</span>
			</td>
			<td align="right">��С����������
			 </td>
			 <td width="115">
				<html:text size="10" property="minSettleQty" onkeyup="value=value.replace(/[^\d]/g,'')" maxlength="10" styleClass="text" />
				<span class="req">*</span>
			</td>
         </tr>  
         <tr>
        <td align="right" width="100">���ڷ���ȡ��ʽ��</td>     
            <td>  
				<html:select property="delayFeeWay"  >
					<html:option value=""></html:option>
                   <html:option value="1">����Ȼ��������ȡ</html:option>
                </html:select>
				<span class="req">*</span>
			</td>
        <td align="right">�����걨���ռ����ͣ�</td>     
            <td>  
				<html:select property="delaySettlePriceType"  >
					<html:option value=""></html:option>
                   <html:option value="0">������۽���</html:option>
                   <html:option value="1">�������۽���</html:option>
                </html:select>
				<span class="req">*</span>
			</td>
        </tr>
</table >
						</span>
						            </fieldset>
						          </td>
						        </tr>			
		<%
			} else if (useDelay.equals("N")) {
		%>
			<html:hidden property="delayRecoupRate" />
			<html:hidden property="delayRecoupRate_S" />
			<html:hidden property="storeRecoupRate" />
			<html:hidden property="settleWay" />
			<html:hidden property="delayFeeWay" />
			<html:hidden property="minSettleMoveQty" />
			<html:hidden property="minSettleQty" />
		<%
			}
		%>
						
						<tr class="common">
					          <td colspan="4">
					            <fieldset>
					              <legend>
					                <table cellspacing="0" cellpadding="0" border="0" width="700" class="common">
					                  <col width="85"></col><col></col><col width="6"></col>
					                  <tr>
					                    <td><b>�������׽�</b></td>
					                    <td><hr width="609" class="pickList"/></td>
					                    <td ><img id="baseinfo_img4" src="<c:url value="/timebargain/images/ctl_detail_Up.gif"/>" style="cursor:hand" onclick="javascript:baseinfo4_onclick()"/></td>
					                  </tr>
					                </table>
					              </legend>
					<span id="baseinfo4">
					<table cellSpacing="0" cellPadding="0" width="700" border="0" align="center" class="common">
								
         
        <tr>
        		  <td></td><td></td><td></td>
                  <td>
                    <c:forEach var="commodity" items="${listSection}">
                      <html-el:multibox property="tradeTimes" styleId="${commodity.label}" styleClass="NormalInput">
                        <c:out value="${commodity.value}"/>
                      </html-el:multibox>
                      <label for="<c:out value="${commodity.label}"/>" class="hand">
                      <c:out value="${commodity.label}"/>
                      </label>
                    </c:forEach>
                  </td>
                </tr>	
                
                 
</table >
</span>
            </fieldset>
          </td>
        </tr>
        
        	
        		<c:if test="${param['oper'] == 'cur'}"> 
								<tr>
									<td colspan="4" align="center">
										<html:button property="save" styleClass="button"
											onclick="javascript:return save_onclick();">
											�ύ
										</html:button>
										<html:reset property="reset" styleClass="button">
											����
										</html:reset>
										<html:button property="cancel" styleClass="button"
											onclick="javascript:return cancel_onclick();">
											����
										</html:button>
									</td>
								</tr>
								</c:if>
        	
						<html:hidden property="crud" />
						<html:hidden property="oper" />
						<html:hidden property="forceCloseFeeAlgr"/>
						<html:hidden property="addedTaxFactor"/>
					</html:form>
			
			
			
		</table>
		     
			

		<html:javascript formName="commodityForm" cdata="false"
			dynamicJavascript="true" staticJavascript="false" />
		<script type="text/javascript"
			src="<html:rewrite page="/timebargain/common/validator.jsp"/>"></script>
		<%@ include file="/timebargain/common/messages.jsp"%>
	</body>
</html>
