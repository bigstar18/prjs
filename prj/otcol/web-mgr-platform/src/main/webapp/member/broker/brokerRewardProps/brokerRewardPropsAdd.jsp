<%@ page contentType="text/html;charset=GBK" %>
<%@page import="gnnt.MEBS.member.broker.services.BrokerRewardPropsService"%>
<%@ include file="../../public/headInc.jsp" %>
<html>
  <head>
	<title></title>
	<script  type="text/javascript"  src="<%=request.getContextPath()%>/delivery/public/jstools/jquery.js" ></script>
	<script type="text/javascript">
	function checkBroker(){
		var brokerId = $("#brokerId").val();
		$.ajax({
				type:"post",
				url:"<%=brokerControllerPath%>getBrokerInfo",
				data:"brokerID=" + brokerId,
				success : function(data){
							if(data==1){
							  $("#brokerId").val("");
							  $("#hint").html("没有该加盟商");
							}else{
								$("#hint").html("");
							}
				}
			});
	}
	</script>
	<%
		List<Map<String,Object>> tbBreeds = (List)request.getAttribute("resultList1");
		List<Map<String,Object>> zcjsBreeds = (List)request.getAttribute("resultList2");
		if(tbBreeds == null) {
			tbBreeds = new ArrayList();
		}
		if(zcjsBreeds == null) {
			zcjsBreeds = new ArrayList();
		}
	%>
	<script>
	function doSubmit()
	{
		if (confirm("您确定要提交吗？")){
			if (frm.moduleId.value == "-1") {
				alert("模块不能为空！");
				return false;
			}
			if (frm.moduleId.value =='2'&&frm.breedId.value == "") {
				alert("品种不能为空！");
				return false;
			}
			if (frm.brokerId.value == "") {
				alert("<%=BROKERID%>不能为空！");
				return false;
			}
			
			if (frm.firstPayRate.value == "") {
				alert("提成首付比例不能为空！");
				return false;
			}
			if (frm.rewardRate.value == "") {
				alert("手续费佣金比例不能为空！");
				return false;
			}
			if (frm.rewardRate.value > 100) {
				alert("手续费佣金比例应小于百分之百！");
				return false;
			}
			if (frm.firstPayRate.value > 100) {
				alert("提成首付比例应小于百分之百！");
				return false;
			}
			if (frm.secondPayRate.value == "") {
				alert("提成尾款比例不能为空！");
				return false;
			}
			frm.submit();
		}
		
	}
	
	function selectModule(moduleId){
	    if(moduleId == '4')
	    {
	       	frm.breedId.disabled='true';
	    }
	    else if(moduleId == '2')
	    {
	    	frm.breedId.disabled='';
	    	var breedId=frm.breedId;
	    	removeOpts(breedId);
	    	var divTimebargin=timebargin.split(";;;;;;;;;");
		  	for(j=0;j<divTimebargin.length;j++){
		      if(divTimebargin[j]==null||divTimebargin[j]=="") continue;
	          var oNewItem = document.createElement("option");
			  breedId.options.add(oNewItem);
			  var divTimebarginOption=divTimebargin[j].split(",,,,,,");
			  oNewItem.innerText=divTimebarginOption[1];
			  oNewItem.value=divTimebarginOption[0];
			}
	    }
	    else if(moduleId == '3')
	    {
	    	frm.breedId.disabled='';
	    	var breedId=frm.breedId;
	    	removeOpts(breedId);
	    	var divZcjs=zcjs.split(";;;;;;;;;");
		 	for(j=0;j<divZcjs.length;j++){
		      if(divZcjs[j]==null||divZcjs[j]=="") continue;
	          var oNewItem = document.createElement("option");
			  breedId.options.add(oNewItem);
			  var divZcjsOption=divZcjs[j].split(",,,,,,");
			  oNewItem.innerText=divZcjsOption[1];
			  oNewItem.value=divZcjsOption[0];
			}
	    }
	    else
	    {
	       	frm.breedId.disabled='';
	    }
	}
	function removeOpts(obj){
     if(obj.length>1){
	     for(i=obj.length-1;i>0;i--){
		     obj.removeChild(obj[i]);
		 }
	 }
 }
	
	function cancle(){
		frm.action = "<%=brokerRewardControllerPath%>brokerRewardPropsList";
		frm.submit();
	}
	
	function change(){
		if (frm.firstPayRate.value != "") {
			frm.secondPayRate.value = 100 - frm.firstPayRate.value;
		}
	}
	
	function window_onload(){
		//isFormat(frm.rewardRate.value,2);
		//isFormat(frm.firstPayRate.value,2);
		//isFormat(frm.secondPayRate.value,2);
	}
	
	function suffixNamePress(){
	  if (event.keyCode<46 || event.keyCode>57 || event.keyCode == 47){
	    event.returnValue=false;
	  }else{
	    event.returnValue=true;
	  }
	}
	function piLiangSZ(){
		if(frm.moduleId.value == "-1"){
			alert("请先选择模块！");
		}else{
			window.location.href="<%=brokerRewardControllerPath%>brokerRewardPropsAllAddByModuleForward&moduleId="+frm.moduleId.value;
		}
	}
</script>
<script language="javascript">
	var timebargin = "";
	<%
		for(Map map:tbBreeds){
	%>
	timebargin += "<%=map.get("breedId")%>,,,,,,<%=map.get("breedName")%>;;;;;;;;;";
	<%
		}
	%>				
	var zcjs = "";
	<%
		for(Map map:zcjsBreeds){
	%>
	zcjs += "<%=map.get("breedId")%>,,,,,,<%=map.get("breedName")%>;;;;;;;;;";
	<%
		}
	%>
</script> 
</head>
<body onload="window_onload()">
        <form id="frm" name="frm" method="POST" targetType="hidden" action="<%=brokerRewardControllerPath%>brokerRewardPropsAdd">
		
		<table align="center" width="50%">
		<tr><td>
		<fieldset style="width:800px;" >
		<legend  >添加佣金设置信息</legend>
			<table align="center" border="0" cellspacing="2" cellpadding="2" width="80%">
				<tr height="35">
                <td align="right" >模块：</td>
                <td align="left" >
                	<select name="moduleId" onchange="selectModule(this.value)">
                			<option value="-1">请选择</option>
							<option value="2">订单</option>
							<option value="3">挂牌</option>
							<option value="4">竞价</option>
					</select>
                	&nbsp;<font color="red">*</font>
                </td>
                
              </tr>
			  <tr height="35">
                <td align="right" ><%=BREEDID%>：</td>
                <td align="left" >
                	<select name="breedId" style="width: 100px;">
                		<option value="">请选择</option>
                	</select>
                	&nbsp;<font color="red">*</font>
                </td>
                
              </tr>
			  <tr height="35">
                <td align="right" ><%=BROKERID%>：</td>
                <td align="left" style="width: 400px;">
                	<input class="text" name="brokerId" id="brokerId" style="width: 150px;"  onblur="checkBroker();"
onkeypress="onlyNumberAndCharInput()" maxlength="16">&nbsp;<font color="red">*<span id="hint"></span><a href="javascript:piLiangSZ()">（批量设置）</a></font>
                </td>
                
              </tr>
              <tr height="35">
                <td align="right" >手续费佣金比例：</td>
                <td align="left" >
                	<input class="text" maxlength="5" name="rewardRate" value="${brokerReward.rewardRate}" onkeypress="return suffixNamePress()" style="width: 150px;" >&nbsp;%&nbsp;<font color="red">*</font>
                </td>
                
              </tr>
			  <tr height="35">
                <td align="right" >提成首付比例：</td>
                <td align="left" >
                	<input class="text" maxlength="5" name="firstPayRate" value="${brokerReward.firstPayRate}" style="width: 150px;" onkeypress="return suffixNamePress()" onblur="change()">&nbsp;%&nbsp;<font color="red">*</font>
                </td>
                
              </tr>
			  <tr>
			  	<td align="right">提成尾款比例：</td>
                <td align="left">
                	<input name="secondPayRate" type="text" value="${brokerReward.secondPayRate}"  class="text" readonly="readonly" style="width: 150px;" onkeypress="return suffixNamePress()" >&nbsp;%&nbsp;<font color="red">*</font>
                </td>
			  </tr>

              <tr height="35">
                <td colspan="6"><div align="center">
                  <button class="smlbtn" type="button" onClick="doSubmit();">确定</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                	<button class="smlbtn" type="button" onClick="cancle();">返回</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                </div></td>
               
              </tr>
          </table>
         
		</fieldset>
		</td></tr>
		</table>
        </form>
</body>
</html>

