<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<link rel="stylesheet" href="${skinPath }/css/validationengine/validationEngine.jquery.css" type="text/css" />
		<link rel="stylesheet" href="${skinPath }/css/validationengine/template.css" type="text/css" />
		<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
		<script src="${mgrPath }/app/timebargain/js/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="GBK"></script>
		<script src="${mgrPath }/app/timebargain/js/jquery.validationEngine.js" type="text/javascript" charset="GBK"></script>	
		<title>Ʒ��</title>
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


		function setReadOnly(obj)
		{
		  obj.readOnly = true;
		  obj.style.backgroundColor = "#C0C0C0";
		}
		function setReadWrite(obj)
		{
		  obj.readOnly = false;
		  obj.style.backgroundColor = "white";
		}
		function setDisabled(obj)
		{
		  obj.disabled = true;
		  obj.style.backgroundColor = "#C0C0C0";
		}
		function setEnabled(obj)
		{
		  obj.disabled = false;
		  obj.style.backgroundColor = "white";
		}
		function keyEnter(iKeyCode)
		{
		  var srcElement = window.event.srcElement;
		  /*
		  if(srcElement.name == "save" || iKeyCode==-1)
		  {
		    document.forms[0].submit();
		    return true;
		  }
		  */
		  if(iKeyCode != 13)
		  {
		    return false;
		  }
		  if ((srcElement.tagName == "INPUT" && srcElement.type != "button" &&
		       srcElement.type != "submit" && srcElement.type != "reset") ||
		       srcElement.tagName == "SELECT")
		  {
		    var i = 0;
		    while (srcElement != srcElement.form.elements[i])
		    {
		      i++;
		    } 
		    if(i == srcElement.form.elements.length-1)
		    {
		      return false;
		    }    
		    while (srcElement.form.elements[i+1].tagName == "FIELDSET")
		    {
		      i++;
		    } 
		    if(i == srcElement.form.elements.length-1)
		    {
		      return false;
		    } 
		    while (srcElement.form.elements[i+1].disabled || srcElement.form.elements[i+1].readOnly || srcElement.form.elements[i+1].type == "hidden")
		    {
		      i++;
		    } 

		    if(i == srcElement.form.elements.length-1)
		    {
		      return false;
		    }
		    
		    while (srcElement.form.elements[i+1].tagName == "FIELDSET")
		    {
		      i++;
		    } 
		    if(i == srcElement.form.elements.length-1)
		    {
		      return false;
		    }  
		        
		    if(srcElement.form.elements[i+1] != null)
		    {
		      srcElement.form.elements[i+1].focus();
		    }
		  }
		  return false;
		}

			$(document).ready(function() {
		    	//ajax��֤
				jQuery("#frm").validationEngine( {
					ajaxFormValidation : true,
					ajaxFormValidationURL : "../../ajaxcheck/demo/formCheckBreedById.action",
					onAjaxFormComplete : ajaxValidationCallback,
					onBeforeAjaxFormValidation : beforeCall
				});

				jQuery("#frm").validationEngine('attach');

				//�ύǰ�¼�
				function beforeCall(form, options) {
					return true;
				}

				//�ύ���¼�
				function ajaxValidationCallback(status, form, json, options) {
					//������سɹ�
					if (status === true) {
					    var flag = false;
				    	flag = save_onclick();
				    	if(flag){
					    	var vaild = affirm("��ȷ��Ҫ������");
							if(vaild){
								//�����ϢURL
								var updateDemoUrl = $("#add").attr("action");
								//ȫ URL ·��
								var url = "${basePath}"+updateDemoUrl;
								$("#frm").attr("action",url);
								frm.submit();
								$("#add").attr("disabled",true);
							}	
				    	}
					} else {
						alert("��Ʒ�ִ����Ѵ��ڣ����޸�");
						$("#breedID").focus();
					}
				}
		    	
				//�޸İ�ťע�����¼�
				$("#add").click(function(){
					jQuery("#frm").modifyAjax( {
						ajaxFormValidation : true
					});
					//��֤��Ϣ
					if(jQuery("#frm").validationEngine('validateform')){
					}
				});
				
				$("#update").click(function(){
					jQuery("#frm").modifyAjax( {
						ajaxFormValidation : false
					});
					//��֤��Ϣ
					if(jQuery("#frm").validationEngine('validateform')){
						var flag = false;
					    flag = save_onclick();
					    if(flag){
					    	var vaild = affirm("��ȷ��Ҫ������");
							if(vaild){
								//�����ϢURL
								var updateDemoUrl = $(this).attr("action");
								//ȫ URL ·��
								var url = "${basePath}"+updateDemoUrl;
								$("#frm").attr("action",url);
								$("#frm").submit();
								$(this).attr("disabled",true);
							}
						}	
					}
				});
				$("#back").click(function(){
					
					//�����ϢURL
					var updateDemoUrl = $(this).attr("action");
					//ȫ URL ·��
					var url = "${basePath}"+updateDemoUrl+"?sortColumns=order+by+breedID+asc";

					document.location.href = url;
					
				});
				
				try {
					var crud = $("#crud").val();
					if (crud == "update") {
						$("#breedID").attr("readonly", true).css("background-color", "#c0c0c0");

						if ($("#limitBreedQty").val() == "-1") {
	      					changeManner101(2);
						} else {
							changeManner101(1);
						}
						if ($("#limitCmdtyQty").val() == "-1") {
							changeManner102(2);
						} else {
							changeManner102(1);
						}
						if ($("#firmCleanQty").val() == "-1") {
							changeManner103(2);
						} else {
							changeManner103(1);
						}
						if ($("#firmMaxHoldQty").val() == "-1") {
							changeManner104(2);
						} else {
							changeManner104(1);
						}
	      				if ($("#oneMaxHoldQty").val() == "-1") {
	      					changeManner105(2);
	      				} else {
	      					changeManner105(1);
	      				}
				    } else {
						var breedsList = eval("(${breedsList})");
						var $breedID = $("#breedName");
						for (var i=0; i<breedsList.length; i++) {
							$breedID.append("<option value='"+breedsList[i].breedName+"'>"+breedsList[i].breedName+"</option>");
						}
						//$list_week.append("<option value=''></option>");
						$breedID.data("breedsList", breedsList);
					}
					var trades = "${entity.tradeTime}".split(",");
					$("input[name='entity.tradeTime']").each(function() {
						if (crud == "create") {
							$(this).attr("checked", true);
						} else {
							for (var k=0 ; k<trades.length; k++) {
								if ($(this).val() == trades[k]) {
									$(this).attr("checked", true);
								}
							}
						}
					});

					marginAlgr_change();//��֤��ٷֱ�
				} catch (e) {
					alert("�޽��׽ڣ�")
				}
				
				

				
		 	});
		 	function change() {
		 		var settlePriceType = $("#settlePriceType").val();
				
				if (settlePriceType == "3") {//ָ�����ռ�
					$("#bbb0").hide();
				 	$("#aaa0").hide();
					$("#ccc0").show();
				 	$("#ddd0").show();
				}else if (settlePriceType == "1"){//���㽻�ռ۵���ǰ��
					$("#bbb0").show();
				 	$("#aaa0").show();
				 	$("#ccc0").hide();
				 	$("#ddd0").hide();	
				}else {
					$("#bbb0").hide();
				 	$("#aaa0").hide();
				 	$("#ccc0").hide();
				 	$("#ddd0").hide();
				}
			}
			function window_onload() {
    			 
    			
    			if(breedForm.crud.value == "create") {
      				breedForm.spreadAlgr.value = ""
					breedForm.limitCmdtyQty.focus();
					breedForm.type101[0].checked = true;
					breedForm.type102[0].checked = true;
					breedForm.type103[0].checked = true;
					breedForm.type104[0].checked = true;
					breedForm.type105[0].checked = true;
					breedForm.limitBreedQty.value = "-1";
					breedForm.limitCmdtyQty.value = "-1";
					breedForm.firmCleanQty.value = "-1";
					breedForm.firmMaxHoldQty.value = "-1";
					breedForm.oneMaxHoldQty.value = "-1";
					if (breedForm.firmMaxHoldQty.value == "-1") {
      					changeManner104(2);
      				}
					setReadOnly(breedForm.limitBreedQty);
					setReadOnly(breedForm.limitCmdtyQty);
					setReadOnly(breedForm.firmCleanQty);
					setReadOnly(breedForm.firmMaxHoldQty);
					setReadOnly(breedForm.oneMaxHoldQty);
					breedForm.minPriceMove.value="1.0";
					breedForm.contractFactor.value="1";
					breedForm.minQuantityMove.value="1";
					breedForm.minSettleMoveQty.value="1";
					breedForm.minSettleQty.value="1";
					breedForm.marginItemAssure1.value = "0";
					breedForm.marginItemAssure2.value = "0";
					breedForm.marginItemAssure3.value = "0";
					breedForm.marginItemAssure4.value = "0";
					breedForm.marginItemAssure5.value = "0";
    			} else if(breedForm.crud.value == "update") {
      				breedForm.limitCmdtyQty.focus();
      				if (breedForm.limitBreedQty.value == "-1") {
      					changeManner101(2);
					} else {
						changeManner101(1);
					}
					if (breedForm.limitCmdtyQty.value == "-1") {
						changeManner102(2);
					} else {
						changeManner102(1);
					}
					if (breedForm.firmCleanQty.value == "-1") {
						changeManner103(2);
					} else {
						changeManner103(1);
					}
					if (breedForm.firmMaxHoldQty.value == "-1") {
						changeManner104(2);
					} else {
						changeManner104(1);
					}
      				if (breedForm.oneMaxHoldQty.value == "-1") {
      					changeManner105(2);
      				} else {
      					changeManner105(1);
      				}
    			} 
    			//marginAlgr_change();//��֤��ٷֱ�
    			//Ĭ�����ڷ���ȡ��ʽ
    			//breedForm.delayFeeWay.value = "1";
    			//<c:if test="${breedForm.isSettle == 'Y'}">
    				//breed();
    			//</c:if>
			}


function chackValue(){
    if (event.keyCode==32)
    {
		 event.returnValue=false;
    }
}

function on_change(){
	document.getElementById("forceCloseFeeAlgr").value = document.getElementById("feeAlgr").value;
	
	if (document.getElementById("feeAlgr").value == "1") {
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
	if(!tmp_baseinfo_up)
    {
      baseinfo_onclick();
    }
    if(!tmp_paraminfo_up)
    {
      paraminfo_onclick();
    }
    
    if(!tmp_paraminfo_up6)
    {
      paraminfo6_onclick();
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
	if(!tmp_baseinfo_upy)
    {
      baseinfoy_onclick();
    }

	if (!$("input[name='entity.tradeTime']:checked").val()) {
		alert("û��ѡ�����׽�");
		return false;
	}
	if ($("input[name='type']:checked").val() == "1") {
		$("#marginItem1_S").val($("#marginItem1").val());
		$("#marginItemAssure1_S").val($("#marginItemAssure1").val());
	}
    for (var i=1; i<5; i++) {
    	if ($("input[name='type"+i+"']:checked").val() == "1") {
    		$("#marginItem"+(i+1)+"_S").val($("#marginItem"+(i+1)).val());
    		$("#marginItemAssure"+(i+1)+"_S").val($("#marginItemAssure"+(i+1)).val());
    	}
    }
    for (var i=1; i<5; i++) {
    	if ($("#marginItem"+(i+1)).val()=="" && $("#marginItem"+(i+1)+"_S").val()=="" && $("#marginItemAssure"+(i+1)).val()=="" && $("#marginItemAssure"+(i+1)+"_S").val()=="") {	
    	} else if ($("#marginItem"+(i+1)).val()!="" && $("#marginItem"+(i+1)+"_S").val()!="" && $("#marginItemAssure"+(i+1)).val()!="" && $("#marginItemAssure"+(i+1)+"_S").val()!="") {}else {alert("��"+(i+1)+"�׶α�֤����������");return false;}
    }

    if (document.getElementById("minQuantityMove").value <= 0) {
    	alert("��С�䶯����Ӧ����0��");
    	document.getElementById("minQuantityMove").focus();
    	return false;
    }
  
    if (document.getElementById("minSettleMoveQty").value <= 0) {
    	alert("��С���λӦ����0��");
    	document.getElementById("minSettleMoveQty").focus();
    	return false;
    }
    
    if (document.getElementById("minSettleQty").value <= 0) {
    	alert("��С��������Ӧ����0��");
    	document.getElementById("minSettleQty").focus();
    	return false;
    }

    //if (breedForm.maxFeeRate.value == "") {
    	//alert("�����������ϵ������Ϊ�գ�");
    	//breedForm.maxFeeRate.focus();
    	//return false;
   // }
    
    
    if (document.getElementById("contractFactor").value <=0) {
    	alert("��λ�������������0��");
    	document.getElementById("contractFactor").focus();
    	return false;
    }
    if (document.getElementById("minPriceMove").value <=0) {
    	alert("��С��λ�����0��");
    	document.getElementById("minPriceMove").focus();
    	return false;
    }
    
	if (document.all.type101[1].checked == true) {
		if (document.getElementById("limitBreedQty").value < 0) {
			alert("Ʒ�ֵ�����󶩻�������Ϊ����");
			return false;
		}
	}
	if (document.all.type102[1].checked == true) {
		if (document.getElementById("limitCmdtyQty").value < 0) {
			alert("��Ʒ������󶩻�������Ϊ����");
			return false;
		}
	}
	
	if (document.all.type103[1].checked == true) {
		if (document.getElementById("firmCleanQty").value < 0) {
			alert("��������󾻶���������Ϊ����");
			return false;
		}
	}
 	if (document.all.type104[1].checked == true) {
 		if (document.getElementById("firmMaxHoldQtyAlgr").value == "") {
 			alert("�����̶����������㷨����Ϊ�գ�");
 			return false;
 		}
 		if (document.getElementById("firmMaxHoldQtyAlgr").value == "2") {
 			if (document.getElementById("firmMaxHoldQty").value == "") {
		 		alert("���������˫�߶���������Ϊ�գ�");
		 		document.getElementById("firmMaxHoldQty").focus();
		    	return false;
		 	}
		 	if (document.getElementById("firmMaxHoldQty").value < 0) {
				alert("���������˫�߶���������Ϊ����");
				return false;
			}
 		}else {
 			if (document.getElementById("startPercentQty").value == "") {
 				alert("��Ʒ��������ֵ����Ϊ�գ�");
 				return false;
 			}
 			if (document.getElementById("maxPercentLimit").value == "") {
 				alert("��������������Ϊ�գ�");
 				return false;
 			}
 			document.getElementById("firmMaxHoldQty").value = parseInt((document.getElementById("startPercentQty").value * (document.getElementById("maxPercentLimit").value/100))+"");
 			if (document.getElementById("firmMaxHoldQty").value < 1) {
 				alert("��ֵ��������ò���ȷ���˻���СΪ1��");
 				return false;
 			}
 		}
	}else {
		document.getElementById("firmMaxHoldQtyAlgr").value = '2';
		document.getElementById("startPercentQty").value = '0';
		document.getElementById("maxPercentLimit").value = '0';
	}
	
	if (document.all.type105[1].checked == true) {
		if (document.getElementById("oneMaxHoldQty").value < 0) {
			alert("�������ί��������Ϊ����");
			return false;
		}
	}
	
	if(document.getElementById("storeRecoupRate").value >= 100){
		alert("�ִ������ѱ���С��100");
		return false;
	}
	var addedTax = Number($("#addedTax").val());
    $("#addedTaxFactor").val(addedTax/(addedTax+100));    setEnabled(document.all.marginPriceType);
    return true;
}
//���������ֺ�.
function onlyNumberInput()
{
  if (event.keyCode<46 || event.keyCode>57 || event.keyCode == 47)
  {
    event.returnValue=false;
  }
}
function suffixNamePress()
{
	
  if ((event.keyCode>=46 && event.keyCode<=57) )  //|| (event.keyCode>=65 && event.keyCode<=90) || (event.keyCode>=97 && event.keyCode<=122)
  {
  	
    event.returnValue=true;
  }
  else
  {
    event.returnValue=false;
  }
}

//spreadAlgr_onchange()
function spreadAlgr_onchange(value)
{
	if (value == "1") {
		$("#spreadUpLmtPercent").show();
		$("#spreadDownLmtPercent").show();
	}else {
		$("#spreadUpLmtPercent").hide();
		$("#spreadDownLmtPercent").hide();
	}
}

//---------------------------start baseinfo-------------------------------

var tmp_baseinfo_up = true;
function baseinfo_onclick()
{
	if (tmp_baseinfo_up)
  {
    tmp_baseinfo_up = false;
    var src = "${skinPath}/image/app/timebargain/ctl_detail_Down.gif";
    $("#baseinfo_img").attr("src", src);
    $("#baseinfo").hide();
  }
  else
  {
    tmp_baseinfo_up = true;
    var src = "${skinPath}/image/app/timebargain/ctl_detail_Up.gif";
    $("#baseinfo_img").attr("src", src);
    $("#baseinfo").show();
  }
  
}
var tmp_baseinfo_up2 = true;
function baseinfo2_onclick()
{
  if (tmp_baseinfo_up2)
  {
    tmp_baseinfo_up2 = false;
    var src = "${skinPath}/image/app/timebargain/ctl_detail_Down.gif";
    $("#baseinfo_img2").attr("src", src);
    $("#baseinfo2").hide();
  }
  else
  {
    tmp_baseinfo_up2 = true;
    var src = "${skinPath}/image/app/timebargain/ctl_detail_Up.gif";
    $("#baseinfo_img2").attr("src", src);
    $("#baseinfo2").show();
  }
}

var tmp_baseinfo_up3 = true;
function baseinfo3_onclick()
{
  if (tmp_baseinfo_up3)
  {
    tmp_baseinfo_up3 = false;
    var src = "${skinPath}/image/app/timebargain/ctl_detail_Down.gif";
    $("#baseinfo_img3").attr("src", src);
    $("#baseinfo3").hide();
  }
  else
  {
    tmp_baseinfo_up3 = true;
    var src = "${skinPath}/image/app/timebargain/ctl_detail_Up.gif";
    $("#baseinfo_img3").attr("src", src);
    $("#baseinfo3").show();
  }
}

var tmp_baseinfo_up4 = true;
function baseinfo4_onclick()
{
  if (tmp_baseinfo_up4)
  {
    tmp_baseinfo_up4 = false;
    var src = "${skinPath}/image/app/timebargain/ctl_detail_Down.gif";
    $("#baseinfo_img4").attr("src", src);
    $("#baseinfo4").hide();
  }
  else
  {
    tmp_baseinfo_up4 = true;
    var src = "${skinPath}/image/app/timebargain/ctl_detail_Up.gif";
    $("#baseinfo_img4").attr("src", src);
    $("#baseinfo4").show();
  }
}

var tmp_baseinfo_upy = true;
function baseinfoy_onclick()
{
  if (tmp_baseinfo_upy)
  {
    tmp_baseinfo_upy = false;
    var src = "${skinPath}/image/app/timebargain/ctl_detail_Down.gif";
    $("#baseinfo_imgy").attr("src", src);
    $("#baseinfoy").hide();
  }
  else
  {
    tmp_baseinfo_upy = true;
    var src = "${skinPath}/image/app/timebargain/ctl_detail_Up.gif";
    $("#baseinfo_imgy").attr("src", src);
    $("#baseinfoy").show();
  }
}
//-----------------------------end baseinfo-----------------------------
//---------------------------start paraminfo-------------------------------
var tmp_paraminfo_up = true;
function paraminfo_onclick()
{
  if (tmp_paraminfo_up)
  {
    tmp_paraminfo_up = false;
    var src = "${skinPath}/image/app/timebargain/ctl_detail_Down.gif";
    $("#paraminfo_img").attr("src", src);
    $("#paraminfo").hide();
  }
  else
  {
    tmp_paraminfo_up = true;
    var src = "${skinPath}/image/app/timebargain/ctl_detail_Up.gif";
    $("#paraminfo_img").attr("src", src);
    $("#paraminfo").show();
  }
}

var tmp_paraminfo_up6 = true;
function paraminfo6_onclick()
{
  if (tmp_paraminfo_up6)
  {
    tmp_paraminfo_up6 = false;
    var src = "${skinPath}/image/app/timebargain/ctl_detail_Down.gif";
    $("#paraminfo_img6").attr("src", src);
    $("#paraminfo6").hide();
  }
  else
  {
    tmp_paraminfo_up6 = true;
    var src = "${skinPath}/image/app/timebargain/ctl_detail_Up.gif";
    $("#paraminfo_img6").attr("src", src);
    $("#paraminfo6").show();
  }
}

function changeManner(id, td1, td2, td3, step){ 
  var td1 = $("#"+td1);
  var td2 = $("#"+td2);
  if(id==1)
  {
   td1.html('��֤��'+step+'��');
   td2.html('������'+step+'��');
   $("#"+td3).hide();
  }
  else if(id==2)
  {
	td1.html('��֤��'+step+'��');
	td2.html('�򵣱���'+step+'��');
	$("#"+td3).show();
  }
} 

 function changeb(value){
	 var todayCloseFeeRate_B = document.getElementById("todayCloseFeeRate_B");
	 var feeRate_B = document.getElementById("feeRate_B");
	 var historyCloseFeeRate_B = document.getElementById("historyCloseFeeRate_B");
    if(todayCloseFeeRate_B.value==""){
        todayCloseFeeRate_B.value = feeRate_B.value;
        historyCloseFeeRate_B.value = feeRate_B.value;
    }
 }
 function changes(value){
	 var todayCloseFeeRate_S = document.getElementById("todayCloseFeeRate_S");
	 var feeRate_S = document.getElementById("feeRate_B");
	 var historyCloseFeeRate_S = document.getElementById("historyCloseFeeRate_S");
    if(todayCloseFeeRate_S.value==""){
        todayCloseFeeRate_S.value = feeRate_S.value;
        historyCloseFeeRate_S.value = feeRate_S.value;
    }   
 }


 function marginPriceType_change(){
 	var marginAlgr = document.getElementById("marginAlgr").value;
 	var tdName = document.getElementById("td1s");
 	if (marginAlgr == 1) {
 		tdName.innerHTML = '��֤��������ݣ�';
 		$("#td1s").show();
 		$("#td2s").show();
 		document.getElementById("marginPriceType").value = "";
 	}else {
 		tdName.innerHTML = '';
 		$("#td1s").hide();
 		$("#td2s").hide();
 		document.getElementById("marginPriceType").value = "0";
 		
 	}
 }

 function marginAlgr_change(){
 	if (document.getElementById("marginAlgr").value == "1") {
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


 
 function changeManner101(id){
 	if (id == "1") {
 		setReadWrite(document.getElementById("limitBreedQty"));
 	}
 	if (id == "2") {
 		document.getElementById("limitBreedQty").value = "-1";
 		setReadOnly(document.getElementById("limitBreedQty"));
 	}
 }
 
  function changeManner102(id){
 	if (id == "1") {
 		setReadWrite(document.getElementById("limitCmdtyQty"));
 	}
 	if (id == "2") {
 		document.getElementById("limitCmdtyQty").value = "-1";
 		setReadOnly(document.getElementById("limitCmdtyQty"));
 	}
 }
 
 function changeManner103(id){
 	if (id == "1") {
 		setReadWrite(document.getElementById("firmCleanQty"));
 	}
 	if (id == "2") {
 		document.getElementById("firmCleanQty").value = "-1";
 		setReadOnly(document.getElementById("firmCleanQty"));
 	}
 }
 
  function changeManner105(id){
 	if (id == "1") {
 		setReadWrite(document.getElementById("oneMaxHoldQty"));
 	}
 	if (id == "2") {
 		document.getElementById("oneMaxHoldQty").value = "-1";
 		setReadOnly(document.getElementById("oneMaxHoldQty"));
 	}
 }

 function changeManner104(id){
 	if (id == "1") {
 		setReadWrite(document.getElementById("firmMaxHoldQty"));
 		//���ƽ����̶����������㷨����
 		$("#tdFirmMaxHoldQtyAlgr").show();
 		changeFirmMaxHoldQtyAlgr();
 		$("#firmMaxHoldQtyAlgr").addClass("validate[required]");
 	}
 	if (id == "2") {
 		$("#tdFirmMaxHoldQtyAlgr").hide();
 		$("#tdFirmMaxHoldQty").show();
 		$("#firmMaxHoldQtyAlgr").removeClass("validate[required]");	
 		
 		document.getElementById("firmMaxHoldQty").value = "-1";
 		setReadOnly(document.getElementById("firmMaxHoldQty"));
 	}
 }
 
 	function changeFirmMaxHoldQtyAlgr(){
 		if (document.getElementById("firmMaxHoldQtyAlgr").value == "1") {
 			$("#tdStartPercentQty").show();
 			$("#tdMaxPercentLimit").show();
 			$("#tdFirmMaxHoldQty").hide();
 		}else {
 			$("#tdStartPercentQty").hide();
 			$("#tdMaxPercentLimit").hide();
 			$("#tdFirmMaxHoldQty").show();

 			// �����̶����������㷨Ϊ����ֵʱ
 			frm.startPercentQty.value = "0";  // ��Ʒ�ٷֱȷ�ֵ�� ��Ĭ��ֵΪ0
 			frm.maxPercentLimit.value = "0";  // �����̶��������ٷֱȣ�Ĭ��ֵΪ0
 		}
 	}

//add by yangpei 2011-12-2���ձ�֤����㷽ʽ
function change2(){
	var settleMarginType = document.forms(0).settleMarginType.value;
	if (settleMarginType == "1"){//���㽻�ռ۵���ǰ��
		$("#bbb02").show();
		$("#aaa02").show();
		
	}else {
		$("#bbb02").hide();
		$("#aaa02").hide();
	}
}

//�������óֲ��������Ƶ���ֲ�����
function change3(){
	var holdDaysLimit = document.forms(0).holdDaysLimit.value;
	
	if (holdDaysLimit == "1"){
		$("#bbb01").show();
		$("#aaa01").show();
	}else {
		$("#bbb01").hide();
		$("#aaa01").hide();
	}
}

//�ж����ֲ������Ƿ�Ϊ��
function changeMaxHoldPositionDay(){
	if(frm.holdDaysLimit.value == 1){
		$("#maxHoldPositionDay").removeClass("validate[maxSize[10] input_text");
		$("#maxHoldPositionDay").addClass("validate[required,maxSize[10] input_text");
	}
}

function settleFeeAlgr_change(){
	if ($("#settleFeeAlgr").val() == "1") {
		document.getElementById("settleFeeRate_BPercent").className = "xian";
		document.getElementById("settleFeeRate_SPercent").className = "xian";
	}else {
		document.getElementById("settleFeeRate_BPercent").className = "yin";
		document.getElementById("settleFeeRate_SPercent").className = "yin";
	}
}

function settleMarginAlgr_B_change(){
	if ($("#settleMarginAlgr_B").val() == "1") {
		$("#settleMarginRate_BPercent").show();
	}else {
		$("#settleMarginRate_BPercent").hide();
	}
}

function settleMarginAlgr_S_change(){
	if ($("#settleMarginAlgr_S").val() == "1") {
		$("#settleMarginRate_SPercent").show();
	}else {
		$("#settleMarginRate_SPercent").hide();
	}
}

function payoutAlgr_change(){
	if ($("#payoutAlgr").val() == "1") {
		$("#payoutRatePercent").show();
	}else {
		$("#payoutRatePercent").hide();
	}
}



	function breed(){
		document.getElementById("breedName").value = document.all.breedID.options[document.all.breedID.selectedIndex].text;
	}

function changeUnits(){ 
    var value=document.getElementById("contractFactorName").value;
    if(value=="")
    alert("�������뽻�׵�λ����");
    else 
   document.getElementById("span_contractFactor").innerHTML ="("+value+ "/��)";
    }
function changeUnits1(){ 
    var value=document.getElementById("contractFactorName").value;
    if(value=="")
   document.getElementById("span_contractFactor").innerHTML ="��(��/��)"
   else 
   document.getElementById("span_contractFactor").innerHTML ="("+value+ "/��)";
     }
 
//-----------------------------end paraminfo-----------------------------

function setSortName(value) {
	//$("#sortName").val(obj.options[obj.selectedIndex].text);

	var breedsList = $("#breedName").data("breedsList");
	for (var i=0; i<breedsList.length; i++) {
		if (value == breedsList[i].breedName) {
			$("#breedID").val(breedsList[i].breedID);
			$("#sortID").val(breedsList[i].categoryID);
			$("#contractFactorName").val(breedsList[i].unit);
			$("#span_contractFactor").html("("+breedsList[i].unit+ "/��)");
			break;
		}
	}
}
</script>
</head>
<body leftmargin="14" topmargin="0">
	<table border="0"  height="100%" width="100%"  align="center" >
		<tr>
			<td>
				<form id="frm" name="frm" action="" method="POST">
				<fieldset>
				<legend class="common"><b>����Ʒ����Ϣ</b></legend>
				<span id="baseinfo9">
				<table width="850" border="0" align="center"  class="common" cellpadding="0" cellspacing="2">
					<!-- ������Ϣ -->
        				<tr class="common">
							<td colspan="4">
					      		<fieldset>
					          	<legend>
					           	<table cellspacing="0" cellpadding="0" border="0" width="800" class="common">
					            	<col width="55"></col><col></col><col width="6"></col>
					               	<tr>
					                 	<td><b>������Ϣ</b></td>
					                    <td><hr width="735" class="pickList"/></td>
					                    <td ><img id="baseinfo_img" src="<c:url value="${skinPath }/image/app/timebargain/ctl_detail_Up.gif"/>" style="cursor:hand" onclick="javascript:baseinfo_onclick()"/></td>
					            	</tr>
					         	</table>
					         	</legend>
								<span id="baseinfo">
								<table cellSpacing="0" cellPadding="0" width="800" border="0" align="left" class="common">
        							<tr>
        			 					<td align="right" width="98"><span class="required">Ʒ������</span>��</td>
	      								<td style="white-space:nowrap;">
	      								    <c:if test="${crud == 'create'}">
		      									<select id="breedName" name="entity.breedName" style="width:100" class="validate[required]" onchange="setSortName(this.value)">
								            		<option value="">��ѡ��</option>
								            	</select>
								            </c:if>
								            <c:if test="${crud == 'update'}">
								            	<input id="breedName" name="entity.breedName" value="${entity.breedName }" style="width: 80;background-color: #C0C0C0" class="input_text" size="10" readonly="readonly"/>
								            </c:if>
							            	<input id="sortID" type="hidden" name="entity.sortID" value="${entity.sortID }" />
							            	<input id="breedID" type="hidden" name="entity.breedID" value="${entity.breedID }" />
											<!--<input id="breedID" name="entity.breedID" onkeypress="chackValue()" value="${entity.breedID }"
												onkeydown="chackValue3()" onkeyup="value=value.replace(/[^\d]/g,'')" style="ime-mode:disabled; width: 80" class="validate[required] input_text datepicker" size="10"/>
		                  					 ����Ʒ���������ơ�������Ʒ���벻������   >,<, �� /,�»���(_) 
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
								            </script>	-->
		          							<span class="required">*</span>
		          						</td>
		          						<!-- 
		          						<td align="right" width="98"><span class="required">Ʒ������</span>��</td>
	      								<td style="white-space:nowrap;">
											<input id="breedName" name="entity.breedName" value="${entity.breedName }"
												class="input_text datepicker" style="width:80; background-color: C0C0C0" readOnly="readOnly"/>
		                  						
		          							<span class="required">*</span>
		          						</td> -->
		            					<!-- 
            							<td align="right" width="90"><span class="required">Ʒ�ַ���</span>��</td>   
            							  
            							<td style="white-space:nowrap;">
											<select id="sortID" name="entity.sortID" class="validate[required]" style="width:80" onchange="setSortName(this)">
                   								<option value=""></option>
                   								<c:forEach items="${allSortsList}" var="sort">
                   									<option value="${sort.sortID }" <c:if test="${entity.sortID==sort.sortID }">selected</c:if>>${sort.sortName }</option>
                   								</c:forEach>
                							</select>
											<span class="required">*</span>
										</td> -->
										<%
											String useBreedTradeMode = (String) request.getAttribute("useBreedTradeMode");
											if (useBreedTradeMode.equals("Y")) {
										%>
										<td align="right" width="90"><span class="required">Ʒ�ֽ���ģʽ</span>��</td>     
            							<td style="white-space:nowrap;" >  
											<select id="breedTradeMode" name="entity.breedTradeMode" class="validate[required]">
							            		<option value="0" <c:if test="${entity.breedTradeMode==0 }">selected</c:if>>��׼ģʽ</option>
							            		<c:if test="${typicalTradeMode == 'Y'}">
							            		<option value="1" <c:if test="${entity.breedTradeMode==1 }">selected</c:if>>��ר��</option>
							            		<option value="2" <c:if test="${entity.breedTradeMode==2 }">selected</c:if>>��ר��</option>
							            		<option value="3" <c:if test="${entity.breedTradeMode==3 }">selected</c:if>>��ר������ת��</option>
							            		<option value="4" <c:if test="${entity.breedTradeMode==4 }">selected</c:if>>��ר������ת��</option>
							            	    </c:if>
							            	</select>
											<span class="required">*</span>
										</td>
										<%
											} else if (useBreedTradeMode.equals("N")) {
										%>
											<input type="hidden" name="entity.breedTradeMode" value="0"/>
										<%
											}
										%>	
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
					        	<table cellspacing="0" cellpadding="0" border="0" width="800" class="common">
					           		<col width="55"></col><col></col><col width="6"></col>
					         		<tr>
					              		<td><b>��������</b></td>
					                    <td><hr width="770" class="pickList"/></td>
					                    <td ><img id="baseinfo_img2" src="<c:url value="${skinPath }/image/app/timebargain/ctl_detail_Up.gif"/>" style="cursor:hand" onclick="javascript:baseinfo2_onclick()"/></td>
					             	</tr>
					         	</table>
					       		</legend>
								<span id="baseinfo2">
								<table cellSpacing="0" cellPadding="0" width="790" border="0" align="left" class="common">   
									<tr>
        								<input type="hidden" id="cmdtyPrefix"/>   
        	  							<td align="right">&nbsp;&nbsp;���۵�λ��</td>
            							<td>Ԫ/<input id="contractFactorName" name="entity.contractFactorName" value="${entity.contractFactorName }"
            								maxlength="10" class="input_text datepicker" size="7" style="width: 60; background-color: C0C0C0" readOnly="readOnly"/>          
			  								<span class="required">&nbsp;</span>  
            							</td>    
        								<td align="right" >���׵�λ��</td> 
            							<td> 
			  								<input id="contractFactor" name="entity.contractFactor" maxlength="10" value="${entity.contractFactor }"
			  									style="ime-mode:disabled; width: 60" class="validate[required] input_text datepicker" size="5" onkeypress="onlyNumberInput()"/>
			  								<span id="span_contractFactor"  class="required">
			  									<c:if test="${entity.contractFactorName!=null}">(${entity.contractFactorName}/��)</c:if>
			  									<c:if test="${entity.contractFactorName==null}">��(��/��)</c:if>
			  								</span>          
            							</td>
            							<td align="right">��ֵ˰�ʣ�</td>
										<td>
											<input id="addedTax" size="10" name="entity.addedTax" maxlength="10" value="${entity.addedTax }"
												class="validate[required] input_text datepicker" style="ime-mode:disabled; width: 70"  onkeypress="suffixNamePress()" />
											<span >%</span><span class="required">*</span>
										</td>
        							</tr>
        							<tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
							       
							        <tr>
							            <td align="right" >&nbsp;�ǵ�ͣ���㷨��</td>
							            <td >
											<select id="spreadAlgr" name="entity.spreadAlgr" style="width:80" onchange="spreadAlgr_onchange(this.value)">
												<option value=""></option>
											    <option value="1" <c:if test="${entity.spreadAlgr==1 }">selected</c:if>>���ٷֱ�</option>
												<option value="2" <c:if test="${entity.spreadAlgr==2 }">selected</c:if>>������ֵ</option>
										   </select> <span class="required">*</span>            
							            </td>        
							            <td align="right">�Ƿ����ޣ�</td>
							            <td>
			  								<input id="spreadUpLmt" name="entity.spreadUpLmt" maxlength="10" value="${entity.spreadUpLmt }"
			  									style="ime-mode:disabled; width: 60" onkeypress="return onlyNumberInput()" class="validate[required] input_text datepicker" size="10"/>
			  								<span id="spreadUpLmtPercent">%</span><span class="required">*</span>           
            							</td>
            							<td align="right">�������ޣ�</td>
            							<td>
											<input id="spreadDownLmt" name="entity.spreadDownLmt" maxlength="10" value="${entity.spreadDownLmt }"
												style="ime-mode:disabled; width: 70" onkeypress="return onlyNumberInput()" class="validate[required] input_text datepicker" size="10"/>
											<span id="spreadDownLmtPercent">%</span><span class="required">*</span>           
								      	</td>
								      	<td>&nbsp;</td>
								      	<script type="text/javascript">
							            	if ("${entity.spreadAlgr}" == "1"){$("#spreadUpLmtPercent").show();$("#spreadDownLmtPercent").show();} else {$("#spreadUpLmtPercent").hide();$("#spreadDownLmtPercent").hide();}
							            </script>
        							</tr> 
									<tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>			
		
									<tr>
										<td align="right">&nbsp;��С�䶯��λ(Ԫ)��</td>
            							<td>
			  								<input type="text" id="minPriceMove" name="entity.minPriceMove" maxlength="10" value="${entity.minPriceMove }"
			  									style="ime-mode:disabled; width: 80" onkeypress="return onlyNumberInput()" class="validate[required] input_text datepicker" style="width: 80"/>          
			  								<span class="required">*</span>  
            							</td>
            							<td align="right">&nbsp;��С�䶯������</td>
            							<td>
			  								<input type="text" id="minQuantityMove" name="entity.minQuantityMove" maxlength="3" value="${entity.minQuantityMove }" 
			  									class="validate[required] input_text datepicker"  style="ime-mode:disabled; width: 80"  onkeyup="value=value.replace(/[^\d]/g,'')" style="width: 80"/>          
			  								<span class="required">*</span>  
            							</td>
            							<td align="right">���ռۼ��㷽ʽ��</td>
										<td>
											<select id="settlePriceType" name="entity.settlePriceType" class="validate[required]" style="width:90" onchange="change()">
												<option value=""></option>
											    <option value="0" <c:if test="${entity.settlePriceType==0 }">selected</c:if>>���н����</option>
												<option value="1" <c:if test="${entity.settlePriceType==1 }">selected</c:if>>����ռ�Ȩƽ����</option>
												<option value="2" <c:if test="${entity.settlePriceType==2 }">selected</c:if>>������</option>
												<option value="3" <c:if test="${entity.settlePriceType==3 }">selected</c:if>>ָ�����ռ�</option>
										    </select> <span class="required">*</span>
										</td>
         							</tr>
									<tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
	 								<tr id="trSettlePriceType">
	 									<td align="right" id="aaa0">���㽻�ռ���ǰ�գ�</td>
										<td id="bbb0">
											<input size="10" id="beforeDays" name="entity.beforeDays" maxlength="10" value="${entity.beforeDays }"
												class="validate[required] input_text datepicker" style="width: 80" onkeypress="return onlyNumberInput()"/>(������)
											<span class="required">&nbsp;</span>
										</td>
										<td align="right" id="ccc0">ָ�����ռۣ�</td>
										<td id="ddd0">
											<input size="10" id="specSettlePrice" name="entity.specSettlePrice" maxlength="10" value="${entity.specSettlePrice }"
												class="validate[required] input_text datepicker" style="width: 80" onkeypress="return onlyNumberInput()"/>
											<span class="required">&nbsp;</span>
										</td>
										<script type="text/javascript">
										var settlePriceType = $("#settlePriceType").val();
										
										if (settlePriceType == "3") {//ָ�����ռ�
											$("#bbb0").hide();
										 	$("#aaa0").hide();
											$("#ccc0").show();
										 	$("#ddd0").show();
										}else if (settlePriceType == "1"){//���㽻�ռ۵���ǰ��
											$("#bbb0").show();
										 	$("#aaa0").show();
										 	$("#ccc0").hide();
										 	$("#ddd0").hide();	
										}else {
											$("#bbb0").hide();
										 	$("#aaa0").hide();
										 	$("#ccc0").hide();
										 	$("#ddd0").hide();
										}
							            </script>
	 								</tr>
	 								<!-- ��ǰ���ս��ռۼ��㷽ʽ  add by zhangjian 2011��12��15��15:24:02 -->
	 								<tr>
	 	 								<td align="right">��ǰ���ռۼ��㷽ʽ��</td>
										<td>
											<select id="aheadSettlePriceType" name="entity.aheadSettlePriceType" style="width:100" class="validate[required]">
												<option value=""></option>
											    <option value="0" <c:if test="${entity.aheadSettlePriceType==0 }">selected</c:if>>�������۽���</option>
												<option value="1" <c:if test="${entity.aheadSettlePriceType==1 }">selected</c:if>>�������۽���</option> 
										    </select> <span class="required">*</span>
										</td>
										<!-- add by yangpei 2011-12-2���ձ�֤����㷽ʽ   -->
						
	 									<td align="right">���ձ�֤����㷽ʽ��</td>
										<td>
											<select id="settleMarginType" name="entity.settleMarginType" style="width:125" onchange="change2()">
												<option value=""></option>
											    <option value="0" <c:if test="${entity.settleMarginType==0 }">selected</c:if>>���н����</option>
												<option value="1" <c:if test="${entity.settleMarginType==1 }">selected</c:if>>����ռ�Ȩƽ����</option>
												<option value="2" <c:if test="${entity.settleMarginType==2 }">selected</c:if>>������</option>
										    </select> <span class="required">*</span>
										</td>
										<td align="right" id="aaa02">���㱣֤����ǰ�գ�</td>
										<td id="bbb02">
											<input id="beforeDays_M" size="10" name="entity.beforeDays_M" maxlength="10" value="${entity.beforeDays_M }"
												class="input_text datepicker" onkeypress="return onlyNumberInput()"/>(������)
											<span class="required">&nbsp;</span>
										</td>
										<script type="text/javascript">
							            	if ("${entity.settleMarginType}" == "1"){$("#aaa02").show();$("#bbb02").show();} else {$("#aaa02").hide();$("#bbb02").hide();}
							            </script>
	 								</tr>
	 								
	 								<tr>
										<td align="right" >
												�Ƿ����óֲ��������ƣ�
										</td>
										<td>
											<select id="holdDaysLimit" name="entity.holdDaysLimit" style="width:85" onchange="change3(),changeMaxHoldPositionDay()">
											    <option value="0" <c:if test="${entity.holdDaysLimit==0 }">selected</c:if>>������</option>
												<option value="1" <c:if test="${entity.holdDaysLimit==1 }">selected</c:if>>����</option>					
										    </select> <span class="required">*</span>
										</td>
										
										<td align="right" id="aaa01">
											��ֲ�������
										</td>
										<td id="bbb01">
										<input type="text" id="maxHoldPositionDay" name="entity.maxHoldPositionDay" value="${entity.maxHoldPositionDay }"
										  	style="width:80" class="validate[maxSize[10] onlyNumberSp input_texts" onkeyup="value=value.replace(/[^\d]/g,'')" />(������)
										<span class="required">*</span>
										</td>
										<script type="text/javascript">
							            	if ("${entity.holdDaysLimit}" == "1"){$("#aaa01").show();$("#bbb01").show();} else {$("#aaa01").hide();$("#bbb01").hide();}
							            </script>						
										<td></td><td></td>
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
                				<table cellspacing="0" cellpadding="0" border="0" width="800" class="common">
                  					<col width="70"></col><col></col><col width="6"></col>
                  					<tr>
                    					<td><b>����������</b></td>
                    					<td><hr width="725" class="pickList"/></td>
                    					<td ><img id="paraminfo_img6" src="<c:url value="${skinPath }/image/app/timebargain/ctl_detail_Up.gif"/>" style="cursor:hand" onclick="javascript:paraminfo6_onclick()"/></td>
                  					</tr>
                				</table>
              					</legend>
								<span id="paraminfo6">
								<table cellSpacing="0" cellPadding="0" width="720" border="0" align="left" class="common">                        
         							<tr>
									<%
										String type101 = (String)request.getAttribute("type101");
										String type102 = (String)request.getAttribute("type102");
										String type103 = (String)request.getAttribute("type103");
										String type104 = (String)request.getAttribute("type104");
										String type105 = (String)request.getAttribute("type105");
									%>
										<td align="right">
											<span class="required">Ʒ�ֵ�����󶩻���(��)</span>��
										</td>
										<td>
											<input type="radio" name="type101" value="2" onclick="changeManner101(2);" <%if("2".equals(type101)){out.println("checked");} %> style="border:0px;"><span class="required">������</span>
											<input type="radio" name="type101" value="1" onclick="changeManner101(1);" <%if("1".equals(type101)){out.println("checked");} %> style="border:0px;"><span class="required">����</span>
											<input id="limitBreedQty" name="entity.limitBreedQty" value="${entity.limitBreedQty }" 
												style="ime-mode:disabled; width: 80" class="validate[required] input_text datepicker" size="10" onkeypress="return onlyNumberInput()"/>
            								<span class="required">*</span>
										</td>
										<td align="right" >��Ʒ������󶩻���(��)��</td>
            							<td >
											<input type="radio" name="type102" value="2" onclick="changeManner102(2);" <%if("2".equals(type102)){out.println("checked");} %> style="border:0px;">������
            								<input type="radio" name="type102" value="1" onclick="changeManner102(1);" <%if("1".equals(type102)){out.println("checked");} %> style="border:0px;">����
            								<input id="limitCmdtyQty" name="entity.limitCmdtyQty" value="${entity.limitCmdtyQty }"
            									style="ime-mode:disabled; width: 80" onkeypress="return onlyNumberInput()" class="validate[required] input_text datepicker" size="10"/>
            								<span class="required">*</span>
            							</td>
									</tr>
									<tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
									<tr>			
										<td align="right" >��������󾻶�������</td>
           								<td>
											<input type="radio" name="type103" value="2" onclick="changeManner103(2);" <%if("2".equals(type103)){out.println("checked");} %> style="border:0px;">������
           									<input type="radio" name="type103" value="1" onclick="changeManner103(1);" <%if("1".equals(type103)){out.println("checked");} %> style="border:0px;">����
           									<input id="firmCleanQty" name="entity.firmCleanQty" style="ime-mode:disabled; width: 80" value="${entity.firmCleanQty }"
           										onkeypress="return onlyNumberInput()" class="validate[required] input_text datepicker" size="10"/>
            								<span class="required">*</span>
           								</td>
           								<td align="right">�������ί������</td>
										<td>
											<input type="radio" name="type105" value="2" onclick="changeManner105(2);" <%if("2".equals(type105)){out.println("checked");} %> style="border:0px;">������
											<input type="radio" name="type105" value="1" onclick="changeManner105(1);" <%if("1".equals(type105)){out.println("checked");} %> style="border:0px;">����
											<input id="oneMaxHoldQty" name="entity.oneMaxHoldQty" value="${entity.oneMaxHoldQty }"
												style="ime-mode:disabled; width: 80" class="validate[required] input_text datepicker" size="10" onkeypress="return onlyNumberInput()"/>
            								<span class="required">*</span>
										</td>
									</tr>
             						<tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
									<tr>
										<td align="right" >���������˫�߶�������</td>
           								<td>
											<input type="radio" name="type104" value="2" onclick="changeManner104(2);" <%if("2".equals(type104)){out.println("checked");} %> style="border:0px;">������
							           		<input type="radio" name="type104" value="1" onclick="changeManner104(1);" <%if("1".equals(type104)){out.println("checked");} %> style="border:0px;">����
							           		<span id="tdFirmMaxHoldQty">
							           			<input id="firmMaxHoldQty" name="entity.firmMaxHoldQty" value="${entity.firmMaxHoldQty }" 
							           				onkeypress="return onlyNumberInput()" style="ime-mode:disabled; width: 80" class="validate[required] input_text datepicker" size="10"/>
							           			<span class="required">*</span>
							           		</span>
           								</td>
									</tr>
									<tr >	
           								<td id="tdFirmMaxHoldQtyAlgr"  colspan="4">&nbsp;&nbsp;&nbsp;&nbsp;�����̶����������㷨��
							           		<select id="firmMaxHoldQtyAlgr" name="entity.firmMaxHoldQtyAlgr" style="width:80" onchange="changeFirmMaxHoldQtyAlgr()">
												<option value=""></option>
											    <option value="1" <c:if test="${entity.firmMaxHoldQtyAlgr==1 }">selected</c:if>>���ٷֱ�</option>
												<option value="2" <c:if test="${entity.firmMaxHoldQtyAlgr==2 }">selected</c:if>>������ֵ</option>
										   	</select> 
							               	<span class="required">*</span>&nbsp;&nbsp;&nbsp;&nbsp;
           									<span id="tdStartPercentQty">��Ʒ��������ֵ��
           												<input id="startPercentQty" name="entity.startPercentQty" onkeypress="return onlyNumberInput()" value="${entity.startPercentQty }"
           																style="ime-mode:disabled; width: 80" class="validate[required] input_text datepicker" size="10"/>
        										<span class="required">*</span>
           									</span>&nbsp;&nbsp;&nbsp;&nbsp;
           									<span id="tdMaxPercentLimit">������������
           										<input id="maxPercentLimit" name="entity.maxPercentLimit" onkeypress="return onlyNumberInput()" value="${entity.maxPercentLimit }"
           											style="ime-mode:disabled; width: 80" class="validate[required] input_text datepicker" size="10"/>
        										<span class="required">%*</span>
           									</span>
       									</td>
									</tr>
									<script type="text/javascript">
						            	if ("${entity.firmMaxHoldQtyAlgr}" == "1"){$("#tdFirmMaxHoldQty").show();$("#tdStartPercentQty").show();$("#tdMaxPercentLimit").show();} else {$("#tdFirmMaxHoldQty").hide();$("#tdStartPercentQty").hide();$("#tdMaxPercentLimit").hide();}
						            </script>
								</table >
								</span>
            					</fieldset>
          					</td>
        				</tr>  
        				
        				 
						<tr class="common">
          					<td colspan="4">
            					<fieldset>
              					<legend>
                				<table cellspacing="0" cellpadding="0" border="0" width="800" class="common">
                  					<col width="45"></col><col></col><col width="6"></col>
                  					<tr>
                    					<td><b>������</b></td>
                    					<td><hr width="750" class="pickList"/></td>
                    					<td ><img id="paraminfo_img" src="<c:url value="${skinPath }/image/app/timebargain/ctl_detail_Up.gif"/>" style="cursor:hand" onclick="javascript:paraminfo_onclick()"/></td>
                  					</tr>
                				</table>
              					</legend>
								<span id="paraminfo">
								<table cellSpacing="0" cellPadding="0" width="720" border="0" align="left" class="common">
					         	<%
					         		String typeFeeAlgr = (String)request.getAttribute("typeFeeAlgr");
					         	%>
         							<tr > 		            
		            					<td rowspan="4" width="220" height="10" valign="top">
		            						<div align="right">
		            							<span >�����������㷨��
		            								<select id="feeAlgr" name="entity.feeAlgr" style="width:80" onchange="on_change()">
														<option value=""></option>
					    								<option value="1" <c:if test="${entity.feeAlgr==1 }">selected</c:if>>���ٷֱ�</option>
														<option value="2" <c:if test="${entity.feeAlgr==2 }">selected</c:if>>������ֵ</option>
			   										</select> 
			   										<span class="required">*</span>  
												</span>
											</div>
										</td>	            		            
		            					<td align="right" rowspan="1" width="225" height="5">������<input id="feeRate_B" name="entity.feeRate_B" maxlength="6" value="${entity.feeRate_B }"
		            										style="ime-mode:disabled; width: 80" onkeypress="return onlyNumberInput()" class="validate[required] input_text datepicker" onchange="changeb(this.value)" size="10"/>
			  								<span id="feeRate_BPercent" class="<%if("1".equals(typeFeeAlgr)){%>xian<%}else{%>yin<%}%>">%</span><span class="required">*</span>
			  							</td>
			  							<td align="right" >��������<input id="feeRate_S" name="entity.feeRate_S"  maxlength="6" value="${entity.feeRate_S }"
			  											style="ime-mode:disabled; width: 80" onkeypress="return onlyNumberInput()" class="validate[required] input_text datepicker" onchange="changes(this.value)" size="10"/>
			  								<span id="feeRate_SPercent" class="<%if("1".equals(typeFeeAlgr)){%>xian<%}else{%>yin<%}%>">%</span><span class="required">*</span>  </td>
			  							<td width="25"></td>
		    						</tr>
									<tr> 		            
		            					<td align="right" width="225" height="5">��ת����ʷ������<input id="historyCloseFeeRate_B" name="entity.historyCloseFeeRate_B" maxlength="11" value="${entity.historyCloseFeeRate_B }"
		            										style="ime-mode:disabled; width: 80" onkeypress="return onlyNumberInput()" class="validate[required] input_text datepicker" size="10"/>
											<span id="historyCloseFeeRate_BPercent" class="<%if("1".equals(typeFeeAlgr)){%>xian<%}else{%>yin<%}%>">%</span><span class="required">*</span>
										</td>
			  							<td align="right">��ת����ʷ������<input id="historyCloseFeeRate_S" name="entity.historyCloseFeeRate_S" maxlength="11" value="${entity.historyCloseFeeRate_S }" 
			  										style="ime-mode:disabled; width: 80" onkeypress="return onlyNumberInput()" class="validate[required] input_text datepicker" size="10"/>
											<span id="historyCloseFeeRate_SPercent" class="<%if("1".equals(typeFeeAlgr)){%>xian<%}else{%>yin<%}%>">%</span><span class="required">*</span>
										</td>
		           					</tr>
									<tr> 		            
		            					<td align="right" width="225"  height="5">��ת�ý񶩻���<input id="todayCloseFeeRate_B" name="entity.todayCloseFeeRate_B" maxlength="11" value="${entity.todayCloseFeeRate_B }" 
		            								style="ime-mode:disabled; width: 80" onkeypress="return onlyNumberInput()" class="validate[required] input_text datepicker" size="10"/>
		            						<span id="todayCloseFeeRate_BPercent" class="<%if("1".equals(typeFeeAlgr)){%>xian<%}else{%>yin<%}%>">%</span><span class="required">*</span>
		            					</td>
								  		<td align="right">��ת�ý񶩻���<input id="todayCloseFeeRate_S" name="entity.todayCloseFeeRate_S" maxlength="11" value="${entity.todayCloseFeeRate_S }" 
								  							style="ime-mode:disabled; width: 80" onkeypress="return onlyNumberInput()" class="validate[required] input_text datepicker" size="10"/>
							            	<span id="todayCloseFeeRate_SPercent" class="<%if("1".equals(typeFeeAlgr)){%>xian<%}else{%>yin<%}%>">%</span><span class="required">*</span>
							          	</td>
		        					</tr>
		            				<tr>
		              					<td align="right" width="225">��ǿ��ת�ã�<input id="forceCloseFeeRate_B" name="entity.forceCloseFeeRate_B" maxlength="11" value="${entity.forceCloseFeeRate_B }" 
		              								style="ime-mode:disabled; width: 80" onkeypress="return onlyNumberInput()" class="validate[required] input_text datepicker" onchange="changeb(this.value)" size="10"/>
			  								<span id="forceCloseFeeRate_BPercent" class="<%if("1".equals(typeFeeAlgr)){%>xian<%}else{%>yin<%}%>">%</span><span class="required">*</span>
			  	 						</td>
							  			<td align="right">��ǿ��ת�ã�<input id="forceCloseFeeRate_S" name="entity.forceCloseFeeRate_S" maxlength="11" value="${entity.forceCloseFeeRate_S }" 
							  						style="ime-mode:disabled; width: 80" onkeypress="return onlyNumberInput()" class="validate[required] input_text datepicker" onchange="changes(this.value)" size="10"/>
							  				<span id="forceCloseFeeRate_SPercent" class="<%if("1".equals(typeFeeAlgr)){%>xian<%}else{%>yin<%}%>">%</span><span class="required">*</span>         
				            			</td>
						           	</tr>
    								<tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>     
							        <%
							        	String typeSettleFeeAlgr = (String)request.getAttribute("typeSettleFeeAlgr");
							        %>
        							<tr>
							        	<td align="right" >�����������㷨��<select id="settleFeeAlgr" name="entity.settleFeeAlgr" class="validate[required]" style="width:80" onchange="settleFeeAlgr_change()">
												<option value=""></option>
											    <option value="1" <c:if test="${entity.settleFeeAlgr==1 }">selected</c:if>>���ٷֱ�</option>
												<option value="2" <c:if test="${entity.settleFeeAlgr==2 }">selected</c:if>>������ֵ</option>
										   </select> 
										   <span class="required">*</span>&nbsp;           
							            </td>
            							<td align="right" >������<input id="settleFeeRate_B" name="entity.settleFeeRate_B" maxlength="11" value="${entity.settleFeeRate_B }" 
            										style="ime-mode:disabled; width: 80" onkeypress="return onlyNumberInput()" class="validate[required] input_text datepicker" onchange="changeb(this.value)" size="10"/>
			  								<span id="settleFeeRate_BPercent" class="<%if("1".equals(typeSettleFeeAlgr)){%>xian<%}else{%>yin<%}%>">%</span><span class="required">*</span>
			 							</td>
			 							<td align="right">��������<input id="settleFeeRate_S" name="entity.settleFeeRate_S" maxlength="11" value="${entity.settleFeeRate_S }" 
			 										style="ime-mode:disabled; width: 80" onkeypress="return onlyNumberInput()" class="validate[required] input_text datepicker" onchange="changes(this.value)" size="10"/>
			  								<span id="settleFeeRate_SPercent" class="<%if("1".equals(typeSettleFeeAlgr)){%>xian<%}else{%>yin<%}%>">%</span><span class="required">*</span>
            							</td>
        							</tr>
        							<tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
       								<tr>
            							<td align="right">��ͽ��������ѽ�<input id="lowestSettleFee" name="entity.lowestSettleFee" maxlength="11" value="${entity.lowestSettleFee }" 
            										style="ime-mode:disabled; width: 80" onkeypress="return onlyNumberInput()" class="validate[required] input_text datepicker" size="10"/>
			   								<span class="required">*</span>&nbsp;          
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
                				<table cellspacing="0" cellpadding="0" border="0" width="800" class="common">
                  					<col width="45"></col><col></col><col width="6"></col>
                  					<tr>
                    					<td><b>��֤��</b></td>
                    					<td><hr width="745" class="pickList"/></td>
                    					<td ><img id="baseinfo_img4" src="<c:url value="${skinPath }/image/app/timebargain/ctl_detail_Up.gif"/>" style="cursor:hand" onclick="javascript:baseinfo4_onclick()"/></td>
                  					</tr>
               	 				</table>
              					</legend>
								<span id="baseinfo4">
								<table cellSpacing="0" cellPadding="0" width="720" border="0" align="center" class="common"> 
           							<tr>
            							<td align="right" width="130">&nbsp;&nbsp;&nbsp;&nbsp;��֤���㷨��</td>
            							<td width="100">
            								<select id="marginAlgr" name="entity.marginAlgr" style="width:80" class="validate[required]" onchange="marginPriceType_change();marginAlgr_change()">
												<option value=""></option>
				    							<option value="1" <c:if test="${entity.marginAlgr==1 }">selected</c:if>>���ٷֱ�</option>
												<option value="2" <c:if test="${entity.marginAlgr==2 }">selected</c:if>>������ֵ</option>
			   								</select> 
			   								<span class="required">*</span>            
            							</td>
            		
            			            <td align="right" width="110" id="td1s">
											��֤��������ݣ�
									</td>
									<td width="368" id="td2s" colspan="3">
										<select id="marginPriceType" name="entity.marginPriceType" class="validate[required]" style="width:180" >
										   <option value="">��ѡ��</option>
				                           <option value="0" <c:if test="${entity.marginPriceType==0 }">selected</c:if>>������</option>
					                       <option value="1" <c:if test="${entity.marginPriceType==1 }">selected</c:if>>������</option> 
					                       <option value="2" <c:if test="${entity.marginPriceType==2 }">selected</c:if>>���а��������̺󰴽����</option>
			                  	        </select>
			                  	        <span class="required">*</span>
						           </td>
            					    <script type="text/javascript">
						            	if ("${entity.marginAlgr}" == "1"){$("#td1s").show();$("#td2s").show();} else {$("#td1s").hide();$("#td2s").hide();}
						            </script>
              
              
            </tr>
            
           <tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
                 
        <tr>
            
			
			<td align="right" >
           			 ��һ�׶�������֤��
            </td>
            <td colspan="3">
             <input type="radio" name="type" value="1" onclick="changeManner(1,'td1','td2','aaa',1);" <c:if test="${entity.type == 1 or entity.type == null}">checked</c:if> style="border:0px;">��ͬ
             
             <input type="radio" name="type" value="2" onclick="changeManner(2,'td1','td2','aaa',1);" <c:if test="${entity.type == 2}">checked</c:if> style="border:0px;">��ͬ
            </td >
        </tr>
        <tr> 
             <td align="right" id="td1">
                <c:choose>
                	<c:when test="${entity.type == 2}">��֤��1��</c:when>
                	<c:otherwise>��֤��1��</c:otherwise>
                </c:choose>
             </td>
            <td>
			  <input type="text" size="10" id="marginItem1" name="entity.marginItem1" maxlength="11" value="${entity.marginItem1 }"
			  	class="validate[required,custom[onlyDoubleSp]] input_text datepicker" style="ime-mode:disabled; width: 80" onkeypress="return onlyNumberInput()" />
			  <span id="marginItem1Percent"></span><span class="required">*</span>           
            </td>
            <td align="right" width="112" id="td2">
            	<c:choose>
                	<c:when test="${entity.type == 2}">�򵣱���1��</c:when>
                	<c:otherwise>������1��</c:otherwise>
                </c:choose>
            </td>
            <td>
			  <input type="text" size="10" id="marginItemAssure1" name="entity.marginItemAssure1" maxlength="11" value="${entity.marginItemAssure1 }"
			  	class="validate[required,custom[onlyDoubleSp]] input_text datepicker" style="ime-mode:disabled; width: 80" onkeypress="return onlyNumberInput()" />
			  <span id="marginItemAssure1Percent"></span><span class="required">*</span>           
            </td>     
            </tr>
            
	         <tr id="aaa">
	             <td align="right" id="abc2">����֤��1��</td>
	             <td id="abc1">
				  <input size="10" id="marginItem1_S" name="entity.marginItem1_S" maxlength="11" onkeypress="return onlyNumberInput()" value="${entity.marginItem1_S }"
				  	class="validate[required,custom[onlyDoubleSp]] input_text datepicker" style="ime-mode:disabled; width: 80" />
				  <span id="marginItem1_SPercent"></span><span class="required">*</span>           
	            </td >
	            <td align="right"  id="abc3">
	          		  ��������1��</td>
	            <td id="abc4">
				  <input size="10" id="marginItemAssure1_S" name="entity.marginItemAssure1_S" maxlength="11" onkeypress="return onlyNumberInput()" value="${entity.marginItemAssure1_S }"
				  	class="validate[required,custom[onlyDoubleSp]] input_text datepicker" style="ime-mode:disabled; width: 80" />
				  <span id="marginItemAssure1_SPercent"></span><span class="required">*</span>           
	            </td>   
	        </tr>
        	<script type="text/javascript">
            	if ("${entity.type}" == "2"){$("#aaa").show();} else {$("#aaa").hide();}
            </script>
        <tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
        
        <tr>
			<td align="right">
            	�ڶ��׶�������֤��
            </td>
			<td colspan="3">
             <input type="radio" name="type1" value="1" onclick="changeManner(1,'td3','td4','aaa1',2);" <c:if test="${entity.type1 == 1 or entity.type1 == null}">checked</c:if> style="border:0px;">��ͬ
             
             <input type="radio" name="type1" value="2" onclick="changeManner(2,'td3','td4','aaa1',2);" <c:if test="${entity.type1 == 2}">checked</c:if> style="border:0px;">��ͬ
            
            </td >
			
			
            </tr>
            <tr>
        	<td align="right" id="td3">
        	    <c:choose>
                	<c:when test="${entity.type1 == 2}">��֤��2��</c:when>
                	<c:otherwise>��֤��2��</c:otherwise>
                </c:choose>
            </td>
            <td>
			  <input size="10" id="marginItem2" name="entity.marginItem2" maxlength="16" value="${entity.marginItem2 }"
			  	style="ime-mode:disabled; width: 80" onkeypress="return onlyNumberInput()" class="validate[custom[onlyDoubleSp]] input_text datepicker" />         
            <span id="marginItem2Percent"></span>
            </td> 
            <td align="right" id="td4">
            	<c:choose>
                	<c:when test="${entity.type1 == 2}">�򵣱���2��</c:when>
                	<c:otherwise>������2��</c:otherwise>
                </c:choose>
            </td>
            <td>
			  <input size="10" id="marginItemAssure2" name="entity.marginItemAssure2" maxlength="16" value="${entity.marginItemAssure2 }"
			  	style="ime-mode:disabled; width: 80" onkeypress="return onlyNumberInput()" class="validate[custom[onlyDoubleSp]] input_text datepicker" />         
            <span id="marginItemAssure2Percent"></span>
            </td>  
        
       
        </tr>
        <tr id="aaa1">
            <td align="right"  id="abc21">&nbsp;&nbsp;����֤��2��</td>
            <td id="abc11">
			  <input size="10" id="marginItem2_S" name="entity.marginItem2_S" maxlength="16" value="${entity.marginItem2_S }"
			  	style="ime-mode:disabled; width: 80" onkeypress="return onlyNumberInput()" class="validate[custom[onlyDoubleSp]] input_text datepicker" />         
            <span id="marginItem2_SPercent"></span>
            </td> 
            <td align="right" id="abc31">&nbsp;&nbsp;��������2��</td>
            <td id="abc41">
			  <input size="10" id="marginItemAssure2_S" name="entity.marginItemAssure2_S" maxlength="16" value="${entity.marginItemAssure2_S }"
			  	style="ime-mode:disabled; width: 80" onkeypress="return onlyNumberInput()" class="validate[custom[onlyDoubleSp]] input_text datepicker" />         
            <span id="marginItemAssure2_SPercent"></span>
            </td>     
        </tr>
        <script type="text/javascript">
           	if ("${entity.type1}" == "2"){$("#aaa1").show();} else {$("#aaa1").hide();}
        </script>   
      <tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr> 
         
        <tr>
			<td align="right">
           		 �����׶�������֤��
            </td>
			<td colspan="3">
             <input type="radio" name="type2" value="1" onclick="changeManner(1,'td5','td6','aaa2',3);" <c:if test="${entity.type2 == 1 or entity.type2 == null}">checked</c:if> style="border:0px;">��ͬ
             <input type="radio" name="type2" value="2" onclick="changeManner(2,'td5','td6','aaa2',3);" <c:if test="${entity.type2 == 2}">checked</c:if> style="border:0px;">��ͬ
            
            </td >
		</tr>
		<tr >
            <td align="right" id="td5">
            	<c:choose>
                	<c:when test="${entity.type2 == 2}">��֤��3��</c:when>
                	<c:otherwise>��֤��3��</c:otherwise>
                </c:choose>
            </td>
            <td>
			  <input size="10" id="marginItem3" name="entity.marginItem3" maxlength="16" value="${entity.marginItem3 }"
			  	style="ime-mode:disabled; width: 80" onkeypress="return onlyNumberInput()" class="validate[custom[onlyDoubleSp]] input_text datepicker" />        
            <span id="marginItem3Percent"></span>
            </td>
            <td align="right" id="td6">
            	<c:choose>
                	<c:when test="${entity.type2 == 2}">�򵣱���3��</c:when>
                	<c:otherwise>������3��</c:otherwise>
                </c:choose>
            </td>
            <td>
			  <input size="10" id="marginItemAssure3" name="entity.marginItemAssure3" maxlength="16" value="${entity.marginItemAssure3 }"
			  	style="ime-mode:disabled; width: 80" onkeypress="return onlyNumberInput()" class="validate[custom[onlyDoubleSp]] input_text datepicker" />        
            <span id="marginItemAssure3Percent"></span>
            </td>
			
            </tr>
            
            <tr id="aaa2"> 
			<td align="right" id="abc22">����֤��3��</td>
            <td id="abc12">
			  <input size="10" id="marginItem3_S" name="entity.marginItem3_S" maxlength="16" value="${entity.marginItem3_S }"
			  	style="ime-mode:disabled; width: 80" onkeypress="return onlyNumberInput()" class="validate[custom[onlyDoubleSp]] input_text datepicker" />        
            <span id="marginItem3_SPercent"></span>
            </td>
            <td align="right"  id="abc32">��������3��</td>
            <td id="abc42">
			  <input size="10" id="marginItemAssure3_S" name="entity.marginItemAssure3_S" maxlength="16" value="${entity.marginItemAssure3_S }"
			  	style="ime-mode:disabled; width: 80" onkeypress="return onlyNumberInput()" class="validate[custom[onlyDoubleSp]] input_text datepicker" />        
            <span id="marginItemAssure3_SPercent"></span>
            </td>
            </tr>
			<script type="text/javascript">
            	if ("${entity.type2}" == "2"){$("#aaa2").show();} else {$("#aaa2").hide();}
            </script>
			<tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
			
          
        <tr>
			<td align="right">
             	 ���Ľ׶�������֤��
            </td>
			<td colspan="3">
            
             <input type="radio" name="type3" value="1" onclick="changeManner(1,'td7','td8','aaa3',4);" <c:if test="${entity.type3 == 1 or entity.type3 == null}">checked</c:if> style="border:0px;">��ͬ
             <input type="radio" name="type3" value="2" onclick="changeManner(2,'td7','td8','aaa3',4);" <c:if test="${entity.type3 == 2}">checked</c:if> style="border:0px;">��ͬ
            </td >
            </tr>
            <tr>
			<td align="right" id="td7">
				<c:choose>
                	<c:when test="${entity.type3 == 2}">��֤��4��</c:when>
                	<c:otherwise>��֤��4��</c:otherwise>
                </c:choose>
			</td>
            <td>
			  <input size="10" id="marginItem4" name="entity.marginItem4" maxlength="16" value="${entity.marginItem4 }"
			  	style="ime-mode:disabled; width: 80" onkeypress="return onlyNumberInput()" class="validate[custom[onlyDoubleSp]] input_text datepicker" />           
            <span id="marginItem4Percent"></span>
            </td>
            <td align="right" id="td8">
            	<c:choose>
                	<c:when test="${entity.type3 == 2}">�򵣱���4��</c:when>
                	<c:otherwise>������4��</c:otherwise>
                </c:choose>
            </td>
            <td>
			  <input size="10" id="marginItemAssure4" name="entity.marginItemAssure4" maxlength="16" value="${entity.marginItemAssure4 }"
			  	style="ime-mode:disabled; width: 80" onkeypress="return onlyNumberInput()" class="validate[custom[onlyDoubleSp]] input_text datepicker" />           
              <span id="marginItemAssure4Percent"></span>
            </td>
         </tr>
         
         <tr id="aaa3">
             <td align="right" id="abc13">����֤��4��</td>
            <td id="abc23">
			  <input size="10" id="marginItem4_S" name="entity.marginItem4_S" maxlength="16" value="${entity.marginItem4_S }"
			  	style="ime-mode:disabled; width: 80" onkeypress="return onlyNumberInput()" class="validate[custom[onlyDoubleSp]] input_text datepicker" />           
            <span id="marginItem4_SPercent"></span>
            </td>
            <td align="right" id="abc33">��������4��</td>
            <td id="abc43">
			  <input size="10" id="marginItemAssure4_S" name="entity.marginItemAssure4_S" maxlength="16" value="${entity.marginItemAssure4_S }"
			  	style="ime-mode:disabled; width: 80" onkeypress="return onlyNumberInput()" class="validate[custom[onlyDoubleSp]] input_text datepicker" />           
              <span id="marginItemAssure4_SPercent"></span>
            </td>
        </tr>
        <script type="text/javascript">
            	if ("${entity.type3}" == "2"){$("#aaa3").show();} else {$("#aaa3").hide();}
        </script>   
       <tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
           
        
        
        <tr>
			<td align="right">
             	 ����׶�������֤��
            </td>
			<td colspan="3">
            <input type="radio" name="type4" value="1" onclick="changeManner(1,'td50','td30','aaa4',5);" <c:if test="${entity.type4 == 1 or entity.type4 == null}">checked</c:if> style="border:0px;">��ͬ
             <input type="radio" name="type4" value="2" onclick="changeManner(2,'td50','td60','aaa4',5);" <c:if test="${entity.type4 == 2}">checked</c:if> style="border:0px;">��ͬ
             
            </td >
            </tr>
            <tr>
			 <td align="right" id="td50">
			 	<c:choose>
                	<c:when test="${entity.type4 == 2}">��֤��5��</c:when>
                	<c:otherwise>��֤��5��</c:otherwise>
                </c:choose>
			 </td>
            <td>
			  <input size="10" id="marginItem5" name="entity.marginItem5" maxlength="16" value="${entity.marginItem5 }"
			  	style="ime-mode:disabled; width: 80" onkeypress="return onlyNumberInput()" class="validate[custom[onlyDoubleSp]] input_text datepicker" />           
            <span id="marginItem5Percent"></span>
            </td>
            <td align="right" id="td60">
            	<c:choose>
                	<c:when test="${entity.type4 == 2}">�򵣱���5��</c:when>
                	<c:otherwise>������5��</c:otherwise>
                </c:choose>
            </td>
            <td>
			  <input size="10" id="marginItemAssure5" name="entity.marginItemAssure5" maxlength="16" value="${entity.marginItemAssure5 }"
			  	style="ime-mode:disabled; width: 80" onkeypress="return onlyNumberInput()" class="validate[custom[onlyDoubleSp]] input_text datepicker" />           
            <span id="marginItemAssure5Percent"></span>
            </td>   
        </tr>
        
         <tr id="aaa4">
            <td align="right" id="abc13">����֤��5��</td>
            <td id="abc24">
			  <input size="10" id="marginItem5_S" name="entity.marginItem5_S" maxlength="16" value="${entity.marginItem5_S }"
			  	style="ime-mode:disabled; width: 80" onkeypress="return onlyNumberInput()" class="validate[custom[onlyDoubleSp]] input_text datepicker" />           
            <span id="marginItem5_SPercent"></span>
            </td>
            <td align="right" id="abc33">��������5��</td>
            <td id="abc44">
			  <input size="10" id="marginItemAssure5_S" name="entity.marginItemAssure5_S" maxlength="16" value="${entity.marginItemAssure5_S }"
			  	style="ime-mode:disabled; width: 80" onkeypress="return onlyNumberInput()" class="validate[custom[onlyDoubleSp]] input_text datepicker" />           
            <span id="marginItemAssure5_SPercent"></span>
            </td>  
        </tr>
        <script type="text/javascript">
            	if ("${entity.type4}" == "2"){$("#aaa4").show();} else {$("#aaa4").hide();}
        </script>      
       <tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
       
        <tr>
        	<td align="left" >
        		&nbsp;&nbsp;���ձ�֤��
        	</td>
        </tr>
        <tr>
            <td align="right" id="td9">�򷽷�ʽ��</td>
            <td>
               <select id="settleMarginAlgr_B" name="entity.settleMarginAlgr_B" style="width:80" onchange="settleMarginAlgr_B_change()" class="validate[required]">
					<option value=""></option>
				    <option value="1" <c:if test="${entity.settleMarginAlgr_B == '1' }">selected</c:if>>���ٷֱ�</option>
					<option value="2" <c:if test="${entity.settleMarginAlgr_B == '2' }">selected</c:if>>������ֵ</option>
			   </select> 
            <span class="required">*</span>
            </td>
            <td align="right" id="td0">�򷽱�׼��</td>
            <td>
			  <input size="10" id="settleMarginRate_B" name="entity.settleMarginRate_B" maxlength="16" value="${entity.settleMarginRate_B }"
			  	style="ime-mode:disabled; width: 80" onkeypress="return onlyNumberInput()" class="validate[required] input_text datepicker" />         
              <span id="settleMarginRate_BPercent">%</span><span class="required">*</span>
            </td>
            <script type="text/javascript">
            	if ("${entity.settleMarginAlgr_B}" == "1"){$("#settleMarginRate_BPercent").show();} else {$("#settleMarginRate_BPercent").hide();}
            </script>   
        </tr>
        <tr id="aaa4">
            <td align="right"  id="abc14">������ʽ��</td>
            <td  >
            <select id="settleMarginAlgr_S" name="entity.settleMarginAlgr_S" style="width:80" class="validate[required]" onchange="settleMarginAlgr_S_change()">
					<option value=""></option>
				    <option value="1" <c:if test="${entity.settleMarginAlgr_S == '1' }">selected</c:if>>���ٷֱ�</option>
					<option value="2" <c:if test="${entity.settleMarginAlgr_S == '2' }">selected</c:if>>������ֵ</option>
			   </select>
            <span class="required">*</span>
            </td>
            <td align="right"  id="abc34">������׼��</td>
            <td>
			  <input size="10" id="settleMarginRate_S" name="entity.settleMarginRate_S" maxlength="16" value="${entity.settleMarginRate_S }"
			  	style="ime-mode:disabled; width: 80" onkeypress="return onlyNumberInput()" class="validate[required] input_text datepicker" />         
              <span id="settleMarginRate_SPercent">%</span><span class="required">*</span>
            </td>
            <script type="text/javascript">
            	if ("${entity.settleMarginAlgr_S}" == "1"){$("#settleMarginRate_SPercent").show();} else {$("#settleMarginRate_SPercent").hide();}
            </script> 
        </tr>
        <tr>
        	<td align="right"  id="abc14">���ջ����㷨��</td>
            <td  >
            <select id="payoutAlgr" name="entity.payoutAlgr" style="width:80" class="validate[required]" onchange="payoutAlgr_change()">
					<option value=""></option>
				    <option value="1" <c:if test="${entity.payoutAlgr == '1' }">selected</c:if>>���ٷֱ�</option>
					<option value="2" <c:if test="${entity.payoutAlgr == '2' }">selected</c:if>>������ֵ</option>
			</select>
            <span class="required">*</span>
            </td>
            <td align="right"  id="abc34">���ջ����׼��</td>
            <td>
			  <input size="10" id="payoutRate" name="entity.payoutRate" maxlength="16" value="${entity.payoutRate}"
			  	style="ime-mode:disabled; width: 80" onkeypress="return onlyNumberInput()" class="validate[required] input_text datepicker" />         
              <span id="payoutRatePercent">%</span><span class="required">*</span>
            </td>
            <script type="text/javascript">
            	if ("${entity.payoutAlgr}" == "1"){$("#payoutRatePercent").show();} else {$("#payoutRatePercent").hide();}
            </script> 
        </tr>
	</table>
</fieldset>
</td>
</tr>
		
						<!-- ���ڽ�����Ϣ -->
						<%
							String useDelay = (String)request.getAttribute("useDelay");;
							if (useDelay.equals("Y")) {
						%>
        				<tr class="common">
							<td colspan="4">
					       		<fieldset>
					           	<legend>
					         	<table cellspacing="0" cellpadding="0" border="0" width="800" class="common">
					          		<col width="80"></col><col></col><col width="6"></col>
					             	<tr>
					               		<td><b>���ڽ�����Ϣ</b></td>
					                    <td><hr width="715" class="pickList"/></td>
					                    <td ><img id="baseinfo_imgy" src="<c:url value="${skinPath }/image/app/timebargain/ctl_detail_Up.gif"/>" style="cursor:hand" onclick="javascript:baseinfoy_onclick()"/></td>
					             	</tr>
					         	</table>
					           	</legend>
								<span id="baseinfoy">
								<table cellSpacing="0" cellPadding="0" width="800" border="0" align="left" class="common">
        							<tr>
            							<td align="right" width="120">�����ڲ������ʣ�</td>
            							<td >
            								<input type="text" id="delayRecoupRate" name="entity.delayRecoupRate" maxlength="13" value="${entity.delayRecoupRate }"
            									class="validate[required] input_text datepicker" size="10" onkeypress="suffixNamePress()"/>
            								<span >%</span><span class="required">*</span>
            							</td>
            							<td align="right" >�ִ������ѣ�</td>
            							<td >
            								<input type="text" id="storeRecoupRate" name="entity.storeRecoupRate" maxlength="13" value="${entity.storeRecoupRate }"
            									class="validate[required] input_text datepicker" size="10" onkeypress="suffixNamePress()"/>
            								<span class="required">*</span>
            							</td>
        							</tr>
        							<tr>  
         								<td align="right" width="120">�������ڲ������ʣ�</td>
            							<td >
            								<input type="text" id="delayRecoupRate_S" name="entity.delayRecoupRate_S" maxlength="13" value="${entity.delayRecoupRate_S }"
            									class="validate[required] input_text datepicker" size="10" onkeypress="suffixNamePress()"/>
            								<span >%</span><span class="required">*</span>
            							</td>
            							<td align="right" >���շ�ʽ��</td>     
            							<td>  
											<select id="settleWay" name="entity.settleWay" class="validate[required]">
												<option value=""></option>
							                   	<option value="0" <c:if test="${entity.settleWay == '0' }">selected</c:if>>���ڽ���</option>
							                   	<c:if test="${useDelay == 'Y'}">
							                   	<option value="1" <c:if test="${entity.settleWay == '1' }">selected</c:if>>���ڽ���</option>
                							    </c:if>
                							</select>
											<span class="required">*</span>
										</td>
        							</tr>
        							<tr>
            							<td align="right">��С���λ��</td>
            							<td>
			  								<input type="text" id="minSettleMoveQty" name="entity.minSettleMoveQty" maxlength="10" value="${entity.minSettleMoveQty }"    
			  									style="ime-mode:disabled"  onkeyup="value=value.replace(/[^\d]/g,'')" class="validate[required] input_text datepicker" size="10"/>          
			  								<span class="required">*</span>  
            							</td>
            							<td align="right">��С����������</td>
            							<td>
			  								<input type="text" id="minSettleQty" name="entity.minSettleQty" maxlength="10" value="${entity.minSettleQty }" 
			  								 	style="ime-mode:disabled"  onkeyup="value=value.replace(/[^\d]/g,'')" class="validate[required] input_text datepicker" size="10"/>          
			  								<span class="required">*</span>  
            							</td>
        							</tr> 
        							<tr>
        								<td align="right">���ڷ���ȡ��ʽ��</td>     
            							<td>  
											<select id="delayFeeWay" name="entity.delayFeeWay"  class="validate[required]">
												<option value=""></option>
                   								<option value="1" <c:if test="${entity.delayFeeWay == '1' }">selected</c:if>>����Ȼ��������ȡ</option>
                							</select>
											<span class="required">*</span>
										</td>
										<td align="right">�����걨���ռ����ͣ�</td>    
										<td>  
											<select id="delaySettlePriceType" name="entity.delaySettlePriceType"  class="validate[required]">
												<option value=""></option>
                   								<option value="0" <c:if test="${entity.delaySettlePriceType == '0' }">selected</c:if>>������۽���</option>
                   								<option value="1" <c:if test="${entity.delaySettlePriceType == '1' }">selected</c:if>>�������۽���</option>
                							</select>
											<span class="required">*</span>
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
			<input type="hidden" id="delaySettlePriceType" name="entity.delaySettlePriceType" value="0"/>
			<input type="hidden" id="delayRecoupRate" name="entity.delayRecoupRate" value="0"/>
			<input type="hidden" id="delayRecoupRate_S" name="entity.delayRecoupRate_S" value="0"/>
			<input type="hidden" id="storeRecoupRate" name="entity.storeRecoupRate" value="0"/>
			<input type="hidden" id="settleWay" name="entity.settleWay" value="0"/>
			<input type="hidden" id="delayFeeWay" name="entity.delayFeeWay" value="1"/>
			<input type="hidden" id="minSettleMoveQty" name="entity.minSettleMoveQty" value="1"/>
			<input type="hidden" id="minSettleQty" name="entity.minSettleQty" value="1"/>
		<%
			}
		%>	
						        
                         <tr class="common">
					         <td colspan="4">
					            <fieldset>
					              <legend>
					                <table cellspacing="0" cellpadding="0" border="0" width="800" class="common">
					                  <col width="70"></col><col></col><col width="6"></col>
					                  <tr>
					                    <td><b>�������׽�</b></td>
					                    <td><hr width="715" class="pickList"/></td>
					                    <td >
					                    	<img id="baseinfo_img3" src="<c:url value="${skinPath }/image/app/timebargain/ctl_detail_Up.gif"/>" 
												style="cursor:hand" onclick="javascript:baseinfo3_onclick()"/>
										</td>
					                  </tr>
					                </table>
					              </legend>
									<span id="baseinfo3">
										<table cellSpacing="0" cellPadding="0" width="800" border="0" align="left" class="common">
							         		<tr>
											<td>&nbsp;&nbsp;</td>
							                <td align="left">
							                    <c:forEach var="tradeTime" items="${listSection}" varStatus="status">
							                      <c:if test="${status.count%5==0}"><br /></c:if>
							                      <input type="checkbox" name="entity.tradeTime" class="NormalInput" value="${tradeTime.sectionID }"/>
							                        
							                      <label class="hand">
							                      <c:out value="${tradeTime.name}"/>
							                      </label>
							                    </c:forEach>
							                 </td>
							                </tr>	
									    </table>
									</span>
						       </fieldset>
						    </td>
						</tr>	
       		
       					<input type="hidden" id="crud" name="crud" value="${request.crud }"/>
						<input type="hidden" id="forceCloseFeeAlgr" name="entity.forceCloseFeeAlgr" value="${entity.forceCloseFeeAlgr }" />
						<input type="hidden" id="addedTaxFactor" name="entity.addedTaxFactor"/>
						<input type="hidden" id="maxFeeRate" name="entity.maxFeeRate" value="0" />
						<input type="hidden" name="entity.marginRate_B" value="0" />
						<input type="hidden" name="entity.marginRate_S" value="0" />
						<input type="hidden" name="entity.marginAssure_B" value="0" />
						<input type="hidden" name="entity.marginAssure_S" value="0" />
						<input id="orderPrivilege" type="hidden" name="entity.orderPrivilege" value="${entity.orderPrivilege }" />
						<script>
							if(document.getElementById("orderPrivilege").value=="")document.getElementById("orderPrivilege").value="101";
						</script> 
       		
						<tr>
							<td colspan="4" height="3">	
						</td>
						</tr>                   
						<tr>
							<td colspan="4" align="center">
								<div class="div_gn">
								    <c:if test="${request.crud == 'create'}">
								    	<rightButton:rightButton name="���" onclick="" className="anniu_btn" action="/timebargain/tradeparams/addBreed.action" id="add"></rightButton:rightButton>
								    </c:if>
								    <c:if test="${request.crud == 'update'}">
								    	<rightButton:rightButton name="�ύ" onclick="" className="anniu_btn" action="/timebargain/tradeparams/updateBreed.action" id="update"></rightButton:rightButton>
								    </c:if>
									&nbsp;&nbsp;
									<rightButton:rightButton name="����" onclick="" className="anniu_btn" action="/timebargain/tradeparams/breedsList.action" id="back"></rightButton:rightButton>
								</div>
							</td>
						</tr>
								
					</table>
				</span>
			</fieldset>

			</form>
		</td>
	</tr>
</table>
		 <table cellSpacing="0" cellPadding="0" width="800" border="0" align="center" class="common">
			<tr>
				<td colspan="4" align="left">
			 		<span class="required">��ʾ����ɫ�����ʾ������Ʒ����</span>
			    </td>
			</tr>
		 </table>
</body>
</html>
