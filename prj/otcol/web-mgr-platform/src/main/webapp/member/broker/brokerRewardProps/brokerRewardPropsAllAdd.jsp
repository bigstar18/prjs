<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../../public/headInc.jsp" %>
<script language="JavaScript" src="<c:url value="/timebargain/scripts/global.js"/>"></script>
<html>
  <head>
	<title></title>
	<script>
	function doSubmit()
	{
	//var brokerid = frm.lbRight.options[frm.lbRight.selectedIndex].value;
	
	var moduleId =document.getElementById("moduleId").value;
	var breedId =-1;
			if (frm.lbRight.length == 0) {
				alert("添加佣金设置的加盟商不能为空！");
				return false;
			}else{
			 	var brokerids = "";
				for(var t=0;t<frm.lbRight.length;t++){
					brokerids+=frm.lbRight.options[t].value+",";
				}
				if(brokerids != "")
			    {
			      brokerids =  brokerids.substr(0, brokerids.length - 1) ;
			    }
		//	    alert(brokerids);
				frm.brokerids.value=brokerids;
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
			if(moduleId == 2 || moduleId== 3){
				breedId=document.getElementById("breedId").value;
			}
		if (breedId == ""){
			frm.tf.value="true";
			frm.submit();
		}else{
			frm.tf.value="false";
			frm.submit();
		}
		
	}
	
	function cancle(){
		frm.action = "<%=basePath%>/brokerRewardController.mem?funcflg=brokerRewardPropsList";
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
</script> 
</head>
<body leftmargin="20">
        <form id="frm" name="frm" method="POST" targetType="hidden" action="<%=basePath%>/brokerRewardController.mem?funcflg=brokerRewardPropsAllAddAction">
		
		<table align="center" width="50%">
		<tr><td>
		<fieldset style="width:800px;" >
		<legend  >添加佣金设置信息</legend>
			<table cellSpacing="2" cellPadding="2" width="100%"  border="0" align="center" >        												
		<tr align="center" valign="top">
		  <td align="center" class="common"  width="30%" >
		    <table border="0" class="common"  width="200">
		    <tr ><td align="center">未用</td></tr>
		    <tr><td >
		      <select  name="lbLeft" size="16" onDblClick="moveSelected(lbLeft,lbRight);" style="width : 100%" multiple>
		        <%
							List list = (List)request.getAttribute("brokerList");
							if (list != null && list.size() > 0) {
								for (int i = 0; i < list.size(); i++) {
									Map map = (Map)list.get(i);
									String BROKERID = map.get("BROKERID")+"";
									%>
										<option value="<%=BROKERID%>"><%=BROKERID%></option>
									<%
								}
							}
						%>
		      </select>
		    </td></tr>
		    </table>
		  </td>
		  <td align="center" valign="center"  width="15%">
		  <input type="hidden" value="${moduleId}" id="moduleId" name="moduleId"/>
		    <table border="0" class="common"  height="140" width="100" >
		      <tr><td align="center" valign="bottom" >
		        <input type="button" name="lbAdd"  value="  >   "  onclick="moveSelected(lbLeft,lbRight);" class="button" >
		        </td></tr>
		        <tr><td align="center" valign="top">
		          <input type="button" name="lbAddAll" value="  >>  "  onclick="return moveSelectedAll(lbLeft,lbRight);" class="button" >
		        </td></tr>
		        <tr><td align="center" valign="bottom" >
		          <input type="button" name="lbDel" value="  <   "  onclick="moveSelected(lbRight,lbLeft);" class="button" >
		        </td></tr>
		        <tr><td align="center" valign="top">
		          <input type="button" name="lbDelAll" value="  <<  "  onclick="moveSelectedAll(lbRight,lbLeft);" class="button" >
		        </td></tr>
		    </table>
		  </td>
		  <td align="center" class="common" width="30%">
		    <table border="0" class="common" width="200">
		    <tr><td class="common" align="center" >已用</td></tr>
		    <tr><td >
		      <select  name="lbRight" size="16" onDblClick="moveSelected(lbRight,lbLeft);"   style="width : 100%" multiple>
		       
		      </select>
		    </td></tr>
		    </table>
		  </td> 
		</tr>
		<c:if test="${requestScope.moduleId != 4 }">
			<tr height="35" align="center">
	                <td align="right" >模块：</td>
	                <c:if test="${moduleId == 2}">
	                <td align="left" >
	                	订单
	                	&nbsp;<font color="red"></font>
	                </td>
	                </c:if>
	                 <c:if test="${moduleId == 3}">
	                <td align="left" >
	                	挂牌
	                	&nbsp;<font color="red"></font>
	                </td>
	                </c:if>
	              </tr>
			<tr height="35" align="center">
	                <td align="right" ><%=BREEDID%>：</td>
	                <td align="left" >
	                	<select name="breedId" id="breedId" style="width: 150px;">
	                		<option value="">请选择</option>
	                		<c:forEach items="${breedList}" var="breed">
	     						<option value="${breed.breedId}">${breed.breedName}</option>
		 					</c:forEach>
	                	</select>
	                </td>
	                <td colspan="4" align="left"><font color="red" size="2">（品种不选为该模块下所有品种都按此标准添加）</font></td>
	        </tr>
        </c:if>
        <c:if test="${moduleId==4}">
        <tr height="35" >
	                <td align="right" >模块：</td>
	                <td align="left" >
	                	竞价
	                	&nbsp;<font color="red"></font>
	                </td>
	              </tr>
        </c:if>
		 <tr height="35" >
                <td align="right" >手续费佣金比例：</td>
                <td colspan=6 align="left" >
                	<input class="text" maxlength="5" name="rewardRate" value="${brokerReward.rewardRate}" onkeypress="return suffixNamePress()" style="width: 150px;" >&nbsp;%&nbsp;<font color="red">*</font>
                </td>
                
              </tr>
			  <tr height="35" >
                <td align="right" >提成首付比例：</td>
                <td colspan=6 align="left" >
                	<input class="text" maxlength="5" name="firstPayRate" value="${brokerReward.firstPayRate}" style="width: 150px;" onkeypress="return suffixNamePress()" onblur="change()">&nbsp;%&nbsp;<font color="red">*</font>
                </td>
                
              </tr>
             
			  <tr height="35" >
			  	<td align="right">提成尾款比例：</td>
                <td colspan=6 align="left">
                	<input name="secondPayRate" type="text" value="${brokerReward.secondPayRate}"  class="text" readonly="readonly" style="width: 150px;">&nbsp;%&nbsp;<font color="red">*</font>
                </td>
			  </tr>
			  
              <tr height="35" >
                <td colspan="6">
             		<div align="center">
                    	<input type="hidden" name="tf" value="false">
                    	<input type="hidden" name="brokerids" value="">
                  		<button class="smlbtn" type="button" onClick="doSubmit();" style="text-align: center">确定</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                		<button class="smlbtn" type="button" onClick="cancle();">返回</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                	</div>               
                 </td>
              </tr>	
</table >
         
		</fieldset>
		</td></tr>
		</table>
        </form>
</body>
</html>

