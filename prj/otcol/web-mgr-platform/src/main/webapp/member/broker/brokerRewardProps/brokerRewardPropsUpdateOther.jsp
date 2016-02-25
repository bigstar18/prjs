<%@ page contentType="text/html;charset=GBK" %>
<%@page import="gnnt.MEBS.member.broker.model.BrokerRewardProps"%>
<%@ include file="../../public/headInc.jsp" %>
<base target="_self">
<html>
  <head>
	<title>修改佣金设置</title>
	<script>
	function doSubmit()
	{
		if (confirm("您确定要提交吗？")){
			if (frm.breedId.value == "") {
				alert("<%=BREEDID%>不能为空！");
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
			if (frm.firstPayRate.value > 100) {
				alert("提成首付比例应小于百分之百！");
				return false;
			}
			if (frm.secondPayRate.value == "") {
				alert("提成尾款比例不能为空！");
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
			frm.submit();
		}
		
	}
	
	function Subtr(arg1,arg2){
     var r1,r2,m,n;
     try{r1=arg1.toString().split(".")[1].length}catch(e){r1=0}
     try{r2=arg2.toString().split(".")[1].length}catch(e){r2=0}
     m=Math.pow(10,Math.max(r1,r2));
     //last modify by deeka
     //动态控制精度长度
     n=(r1>=r2)?r1:r2;
     return ((arg1*m-arg2*m)/m).toFixed(n);
	}
	
	function change(){
		if (frm.firstPayRate.value != "") {
			frm.secondPayRate.value = Subtr(100 , frm.firstPayRate.value);
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
	
	function cancle(){
		window.close();
	}
	
	<c:if test='${not empty resultMsg}'>
		window.close();
	</c:if>
		
</script> 
</head>
<body onload="window_onload()">
        <form id="frm" name="frm" method="POST" callback="closeDialog(1);" targetType="hidden" action="<%=brokerRewardControllerPath%>brokerRewardPropsUpdateOtherAction">
		<input type="hidden" id="breedId" name="breedId" value="${brokerRewardProps.breedId}"/>
		<input type="hidden" id="brokerId" name="brokerId" value="${brokerRewardProps.brokerId}"/>
		<input type="hidden" id="moduleId" name="moduleId" value="${brokerRewardProps.moduleId}"/>
		<table align="center" width="500" height="80" border="0">
			<tr><td>&nbsp;</td></tr>
		</table>
		<table align="center" width="500">
		<tr><td>
		<fieldset style="width:490px;" >
		<legend  >佣金设置信息</legend>
			
			
			<table align="center" border="0" cellspacing="0" cellpadding="0" width="490">
			<tr>
			 <td align="right" >模块：</td>
                <td align="left" >
                	<select name="moduleIdForShow" onchange="selectModule(this.value)">
                			<option value="-1">请选择</option>
							<option value="2">订单</option>
							<option value="3">挂牌</option>
							<option value="4">竞价</option>
					</select>
                	&nbsp;<font color="red">*</font>
                </td>
                <script>
							frm.moduleIdForShow.value = "${brokerRewardProps.moduleId}";
							frm.moduleIdForShow.disabled=true;
				</script>
              </tr>
			  <tr height="35">
                <td align="right" ><%=BREEDID%>：</td>
                <td align="left" >
                	<select name="breedIdForShow" style="width: 100px;">
                		<option value="">请选择</option>
						<%
							BrokerRewardProps b = new BrokerRewardProps();
							if (request.getParameter("moduleId").equals("2")){
								List list = (List)request.getAttribute("resultList1");
								if (list != null && list.size() > 0) {
									for (int i = 0; i < list.size(); i++) {
										Map map = (Map)list.get(i);
										String breedId = map.get("breedId")+"";
										String name = map.get("breedName")+"";
						%>
							<option value="<%=breedId%>"><%=name%></option>
						<%
									}
								}
							}
							if (request.getParameter("moduleId").equals("3")){
								List list = (List)request.getAttribute("resultList2");
								if (list != null && list.size() > 0) {
									for (int i = 0; i < list.size(); i++) {
										Map map = (Map)list.get(i);
										String breedId = map.get("breedId")+"";
										String name = map.get("breedName")+"";
						%>
							<option value="<%=breedId%>"><%=name%></option>
						<%
									}
								}
							}
						%>
						
                	</select>
                	&nbsp;<font color="red">*</font>
                </td>
                <script>
							frm.breedIdForShow.value = "${brokerRewardProps.breedId}";
							frm.breedIdForShow.disabled=true;
				</script>
              </tr>
			  <tr height="35">
                <td align="right" ><%=BROKERID%>：</td>
                <td align="left" >
                	<input class="text" name="brokerId"  style="width: 150px;" value="${brokerRewardProps.brokerId}" disabled="true">&nbsp;<font color="red">*</font>
                </td>
                
              </tr>
              <tr height="35">
                <td align="right" >手续费佣金比例：</td>
                <td align="left" >
                	<input class="text" maxlength="5" name="rewardRate" value="${brokerRewardProps.rewardRate}" onkeypress="return suffixNamePress()" style="width: 150px;" >&nbsp;%&nbsp;<font color="red">*</font>
                </td>
              </tr>
			  <tr height="35">
                <td align="right" >提成首付比例：</td>
                <td align="left" >
                	<input class="text" maxlength="5" name="firstPayRate" value="${brokerRewardProps.firstPayRate}" onkeypress="return suffixNamePress()" style="width: 150px;" onblur="change()">&nbsp;%&nbsp;<font color="red">*</font>
                </td>
                
              </tr>
			  <tr height="35">
			  	<td align="right">提成尾款比例：</td>
                <td align="left">
                	<input name="secondPayRate" type="text" value="${brokerRewardProps.secondPayRate}"  class="text" readonly="readonly" style="width: 150px;" onkeypress="return suffixNamePress()" >&nbsp;%&nbsp;<font color="red">*</font>
                </td>
			  </tr>
			  
              <tr height="35">
                <td colspan="6"><div align="center">
                  <button class="smlbtn" type="button" onClick="doSubmit();">确定</button>&nbsp;&nbsp;&nbsp;&nbsp;
                  <button class="smlbtn" type="button" onClick="cancle();">关闭</button>
                </div></td>
              </tr>
          </table>
         
		</fieldset>
		</td></tr>
		</table>
        </form>
</body>
</html>
<%@ include file="../../public/footInc.jsp" %>
