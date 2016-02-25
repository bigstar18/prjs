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
							  $("#hint").html("û�иü�����");
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
		if (confirm("��ȷ��Ҫ�ύ��")){
			if (frm.moduleId.value == "-1") {
				alert("ģ�鲻��Ϊ�գ�");
				return false;
			}
			if (frm.moduleId.value =='2'&&frm.breedId.value == "") {
				alert("Ʒ�ֲ���Ϊ�գ�");
				return false;
			}
			if (frm.brokerId.value == "") {
				alert("<%=BROKERID%>����Ϊ�գ�");
				return false;
			}
			
			if (frm.firstPayRate.value == "") {
				alert("����׸���������Ϊ�գ�");
				return false;
			}
			if (frm.rewardRate.value == "") {
				alert("������Ӷ���������Ϊ�գ�");
				return false;
			}
			if (frm.rewardRate.value > 100) {
				alert("������Ӷ�����ӦС�ڰٷ�֮�٣�");
				return false;
			}
			if (frm.firstPayRate.value > 100) {
				alert("����׸�����ӦС�ڰٷ�֮�٣�");
				return false;
			}
			if (frm.secondPayRate.value == "") {
				alert("���β���������Ϊ�գ�");
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
			alert("����ѡ��ģ�飡");
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
		<legend  >���Ӷ��������Ϣ</legend>
			<table align="center" border="0" cellspacing="2" cellpadding="2" width="80%">
				<tr height="35">
                <td align="right" >ģ�飺</td>
                <td align="left" >
                	<select name="moduleId" onchange="selectModule(this.value)">
                			<option value="-1">��ѡ��</option>
							<option value="2">����</option>
							<option value="3">����</option>
							<option value="4">����</option>
					</select>
                	&nbsp;<font color="red">*</font>
                </td>
                
              </tr>
			  <tr height="35">
                <td align="right" ><%=BREEDID%>��</td>
                <td align="left" >
                	<select name="breedId" style="width: 100px;">
                		<option value="">��ѡ��</option>
                	</select>
                	&nbsp;<font color="red">*</font>
                </td>
                
              </tr>
			  <tr height="35">
                <td align="right" ><%=BROKERID%>��</td>
                <td align="left" style="width: 400px;">
                	<input class="text" name="brokerId" id="brokerId" style="width: 150px;"  onblur="checkBroker();"
onkeypress="onlyNumberAndCharInput()" maxlength="16">&nbsp;<font color="red">*<span id="hint"></span><a href="javascript:piLiangSZ()">���������ã�</a></font>
                </td>
                
              </tr>
              <tr height="35">
                <td align="right" >������Ӷ�������</td>
                <td align="left" >
                	<input class="text" maxlength="5" name="rewardRate" value="${brokerReward.rewardRate}" onkeypress="return suffixNamePress()" style="width: 150px;" >&nbsp;%&nbsp;<font color="red">*</font>
                </td>
                
              </tr>
			  <tr height="35">
                <td align="right" >����׸�������</td>
                <td align="left" >
                	<input class="text" maxlength="5" name="firstPayRate" value="${brokerReward.firstPayRate}" style="width: 150px;" onkeypress="return suffixNamePress()" onblur="change()">&nbsp;%&nbsp;<font color="red">*</font>
                </td>
                
              </tr>
			  <tr>
			  	<td align="right">���β�������</td>
                <td align="left">
                	<input name="secondPayRate" type="text" value="${brokerReward.secondPayRate}"  class="text" readonly="readonly" style="width: 150px;" onkeypress="return suffixNamePress()" >&nbsp;%&nbsp;<font color="red">*</font>
                </td>
			  </tr>

              <tr height="35">
                <td colspan="6"><div align="center">
                  <button class="smlbtn" type="button" onClick="doSubmit();">ȷ��</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                	<button class="smlbtn" type="button" onClick="cancle();">����</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                </div></td>
               
              </tr>
          </table>
         
		</fieldset>
		</td></tr>
		</table>
        </form>
</body>
</html>

