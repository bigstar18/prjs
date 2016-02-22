<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<html>
	<head>
		<title>客户详细信息</title>
	</head>
	<body leftmargin="0" topmargin="0">
		<form name="frm" action="${basePath}/account/customer/update.action"
			method="post" targetType="hidden">
			<input type="hidden" name="obj.memberNo" id="memberNoAdd">
			<div>
				<table border="0" width="100%" align="center">
					<tr>
						<td>
							<div class="st_title">
							&nbsp;&nbsp;&nbsp;<img src="<%=skinPath%>/cssimg/st_ico1.gif" align="absmiddle" />&nbsp;客户详细信息
							</div>
							<span><%@include file="commonTable.jsp"%></span>
							<input type="hidden" id="organization">
							<input type="hidden" id="brokerage"> 
						</td>
					</tr>
				</table>
			</div>
			<div class="tab_pad">
				<table cellspacing="0" cellpadding="0" border="0" width="100%"
					align="center">
					<tr>
						<td align="center">
							<button class="btn_sec" onClick="updateCustomer()" id="update">
								保存
							</button>
						</td>
						<td align="center">
							<button class="btn_sec" onClick="window.close()">
								关闭
							</button>
						</td>
					</tr>
				</table>
			</div>
		</form>
	</body>
</html>
<script type="text/javascript">
var customerId = document.getElementById("customerId").value;
var memberNo = customerId.substr(0, 3);
var select=document.getElementById("aaaa");
var shortId = customerId.substr(memberNo.length, customerId.length - 1);
document.getElementById("memberNo").disabled = "disabled";
document.getElementById("memberNos").value = memberNo;
document.getElementById("shortId").readOnly = true;
document.getElementById("shortId").value = shortId;
document.getElementById("memberNoAdd").value = '${obj.memberNo}';

/**
 * 
 * 对于证件类型的操作
 * 
 * */
if('${obj.papersType}'=='3'){
	var item1 = document.createElement("OPTION");
	var paper=document.getElementById("papersType");
    paper.options.add(item1);
    item1.value = '3';
    item1.innerText = "护照";
    item1.selected='selected';
}

if('${obj.status}'!='C'){
	document.getElementById("customerName").readOnly=true;
	var papersType=document.getElementById("papersType").value;
	document.getElementById("papersType").disabled = "disabled";
	document.getElementById("papersName").readOnly=true;
	var collFrm = document.all.tags("form");
    if ( collFrm )
    {
        for( var i = 0 ; i < collFrm.length ; i++ )
        {
            var e= document.createElement("input");
	        e.setAttribute("type","hidden");
	        e.setAttribute("id","papersType");
	        e.setAttribute("name",'obj.papersType');
	        e.setAttribute("value",papersType);
	        collFrm[i].appendChild(e);
        }
        
    }
}
if(${not empty obj.memberNo}) {
		document.getElementById("memberNo").value = "${obj.memberNo}";
}
	onloadOrganization('${obj.memberNo}');
	
	if('${obj.organizationNo}'==""){
		onloadBrokerage('${obj.memberNo}');
	}
	function onloadOrganization(id) {
		customerAdd.getOrganizationList(id,function(organizationList){
			if(organizationList==""){
			    return;
			}
			if (typeof window['DWRUtil'] == 'undefined'){
					window.DWRUtil = dwr.util;
			}
			var organization=document.getElementById("organization");
			DWRUtil.removeAllOptions(organization);
			organization.style.width='200px';
			var item1 = document.createElement("OPTION");
			organization.options.add(item1);
			item1.value ="";
			item1.innerText = "请选择";
			for(var i = 0; i < organizationList.length; i++){
			 	 	//DWRUtil.addOptions(brokerage,brokerageList[i].name);
			 	var item = document.createElement("OPTION");
			    organization.options.add(item);
			    item.value = organizationList[i].organizationNO;
			    item.innerText = organizationList[i].name;
			    if(organizationList[i].organizationNO=='${obj.organizationNo}'){
			    	item.selected='selected';
			    }
			}			
		});
		document.getElementById("organization").value='${obj.organizationNo}';
		//changeOrganization(${obj.organizationNo});
		customerAdd.getBrokerageAndManagerList('${obj.organizationNo}',id,function(map) {
		if (!map) {
			return;
		}
		var brokerageList = map['brokerageList'];

		//var managerList = map['managerList'];

		var brokerage = document.getElementById("brokerage");
		DWRUtil.removeAllOptions(brokerage);
		brokerage.style.width = '200px';
		var item = document.createElement("OPTION");
		brokerage.options.add(item);
		item.value = "";
		item.innerText = "请选择";
		for ( var i = 0; i < brokerageList.length; i++) {
			//DWRUtil.addOptions(brokerage,brokerageList[i].name);
			var item = document.createElement("OPTION");
			brokerage.options.add(item);
			item.value = brokerageList[i].brokerageNo;
			item.innerText = brokerageList[i].name;
			if (brokerageList[i].brokerageNo == '${obj.brokerageNo}') {
				item.selected = 'selected';
			}
		}

		/**
		 * var manager = document.getElementById("manager");
		DWRUtil.removeAllOptions(manager);
		manager.style.width = '200px';
		var item = document.createElement("OPTION");
		manager.options.add(item);
		item.value = "";
		item.innerText = "请选择";
		for ( var i = 0; i < managerList.length; i++) {
			//DWRUtil.addOptions(manager,managerList[i].name);
			var item = document.createElement("OPTION");
			manager.options.add(item);
			item.value = managerList[i].managerNo;
			item.innerText = managerList[i].name;
			if (managerList[i].managerNo == '${obj.managerNo}') {
				item.selected = 'selected';
			}
		}
	*/
	});
}
function onloadBrokerage(id) {
		customerAdd.getBrokerageListByMember(id,function(brokerageList){
			if(brokerageList==""){
			    return;
			}
			if (typeof window['DWRUtil'] == 'undefined'){
					window.DWRUtil = dwr.util;
			}
			var brokerage=document.getElementById("brokerage");
			DWRUtil.removeAllOptions(brokerage);
			brokerage.style.width='200px';
			var item1 = document.createElement("OPTION");
			brokerage.options.add(item1);
			item1.value ="";
			item1.innerText = "请选择";
			for(var i = 0; i < brokerageList.length; i++){
			 	 	//DWRUtil.addOptions(brokerage,brokerageList[i].name);
			 	var item = document.createElement("OPTION");
			    brokerage.options.add(item);
			    item.value = brokerageList[i].brokerageNo;
			    item.innerText = brokerageList[i].name;
			    if(brokerageList[i].brokerageNo=='${obj.brokerageNo}'){
			    	item.selected='selected';
			    }
				}			
		});
		document.getElementById("brokerage").value='${obj.brokerageNo}';
}
	function updateCustomer(){
		if(!myblur("all")){return false;}
		var papersType=document.getElementById("papersType").value;
		var id=document.getElementById("papersName").value;
		var customerNo=document.getElementById('customerId').value;
		var vaild = affirm("您确定要操作吗？");
		if(vaild==true){
		    frm.submit();
	   	}else{
	         return false;
	    }
	}
	
function checkMemberNo(userID){
	return true;
}
function password(userID){
	return true;
}
function passwordcompare(userID1,userID2){
	return true;
}
function checkMemberNoLaw(){
	return true;
}
function checkPapersTypeLaw(){
	return true;
}
</script>
<%@ include file="/public/footInc.jsp"%>
