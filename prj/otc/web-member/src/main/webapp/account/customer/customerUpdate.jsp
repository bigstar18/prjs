<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<html>
	<head>
		<title>客户信息</title>
	</head>
	<body leftmargin="0" topmargin="0" style="overflow-y: hidden">
		<form name="frm" action="${basePath}/account/customer/update.action"
			method="post" targetType="hidden">
			<input type="hidden" name="obj.memberNo" id="memberNoAdd">
			<div class="div_scromidmin">
				<table border="0" width="100%" align="center">
					<tr>
						<td>
							<div class="st_title">
							&nbsp;&nbsp;&nbsp;<img src="<%=skinPath%>/cssimg/st_ico1.gif" align="absmiddle" />&nbsp;客户信息
							</div>
							<span><%@include file="commonTable.jsp"%></span>
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
	var memberNo = customerId.substr(0,3);
	var shortId=customerId.substr(memberNo.length,customerId.length-1);
	document.getElementById("memberNo").disabled="disabled";
	document.getElementById("memberNos").value=memberNo;
	document.getElementById("shortId").readOnly=true;
	document.getElementById("shortId").value=shortId;
	document.getElementById("memberNoAdd").value='${obj.memberNo}';
	
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
	var sessionOrgNo='${sessionScope.ORGANIZATIONID}';
	if(${not empty obj.memberNo}) {
		document.getElementById("memberNo").value = "${obj.memberNo}";
	}
	if(''=='${obj.organizationNo}'){
		onloadBrokerage();
	}else{
		changeOrganization('${obj.organizationNo}');
	}
	function onloadBrokerage(){
		customerAdd.getBrokerageListByMember('${obj.memberNo}',sessionOrgNo,function(brokerageList){
			if(brokerageList==""){
			    return;
			}
			var brokerage=document.getElementById("brokerage");
			DWRUtil.removeAllOptions(brokerage);
			brokerage.style.width='300px';
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
			    item.title=brokerageList[i].name;
			    if(brokerageList[i].brokerageNo=='${obj.brokerageNo}'){
			    	item.selected='selected';
			    }
				}
		});
	}
	function updateCustomer(){
		if( !myblur("all") ){
			return false;
		}
		var vaild = affirm("您确定要操作吗？");
		if(vaild==true){
	    	frm.submit();
   		}else{
          	return false;
    	}
	}
		function checkMemberNo(){
			return true;
		}
		function password(userID){
			return true;
		}
		
		function passwordcompare(p1,p2){
			return true;
		}
		function checkPapersTypeLaw(userID){
			return true;
		}
		function checkMemberNoLaw(){
			return true;
		}
	</script>
<%@ include file="/public/footInc.jsp"%>
