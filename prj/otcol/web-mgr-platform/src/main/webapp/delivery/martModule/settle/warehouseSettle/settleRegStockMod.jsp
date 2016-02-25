<%@ include file="../../../public/session.jsp"%>
<%@ page language="java" pageEncoding="GBK"%>
<base target="_self">
<html>
  <head>
    <title>修改配对持仓信息</title>
<c:if test="${ not empty resultMsg }">
	<script type="text/javascript">
		window.returnValue="-1";
		window.close();
	</script>
</c:if>
<script type="text/javascript">
  function window_load(){
    var regStockId = document.getElementById("current_regStockId").value;
    document.getElementById("regStockId").value = regStockId;
    regStockIdajaxChange(regStockId);
  }
  
</script>
  </head>
  <body onload="window_load()">
	<form name="frm" method="post">
		<input type="hidden" name="matchId" value="${settleMatch.matchId }">
		<input type="hidden" name="current_regStockId" value="${settleMatch.regStockId }" />
		<fieldset>
			<br>
			<legend class="common"><b>修改配对持仓信息</b></legend>
			<span>
			<table class="common" align="center" width="100%">
				<tr class="common">
					<td align="right" width="50%">卖方交易商代码:</td>
					<td align="left" width="50%">${settleMatch.firmID_S }</td>
				</tr>
				<tr class="common">
					<td align="right" width="50%">品种代码:</td>
					<td align="left" width="50%">${settleMatch.breedId }</td>
				</tr>
				<tr class="common">
					<td align="right" width="50%">卖方交收价:</td>
					<td align="left" width="50%"><fmt:formatNumber value="${settleMatch.sellPrice }" pattern="#,##0.00"/></td>
				</tr>
				<tr class="common">
				
					<td align="right" width="50%">注册仓单号:</td>
					<td align="left" width="50%">
					      <select name="regStockId" id="regStockId" onchange="regStockIdajaxChange(this.value)" >
							<OPTION value="">请选择</OPTION>						
							<c:forEach items="${list}" var="list">
								<OPTION value="${list.regStockId }">${list.regStockId }</OPTION>
							</c:forEach>
						</select></td>
				</tr>
				<tr class="common">
					<td align="right" width="50%">
						品种:
					</td>
					<td align="left" width="50%">
						<span id="breedid"></span>
					</td>
				</tr>
				<tr class="common">
					<td align="right" width="50%">
						数量:
					</td>
					<td align="left" width="50%">
						<span id="weight"></span>
					</td>
				</tr>
				<tr class="common">
					<td align="right" width="50%">
						冻结数量:
					</td>
					<td align="left" width="50%">
						<span id="frozenweight"></span>
					</td>
				</tr>
						<tr class="common">
					<td align="center" width="100%" colspan="2">
						<input type="button" name="subbtn" class="button" value="提交" onclick="thisPay();">&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" class="button" value="关闭" onclick="window.close();">
					</td>
				</tr>
			
			</table>
				<br>
			</span>
		</fieldset>
		<input type="hidden" name="opt">
	</form>
</body>
<script type="text/javascript">
	function thisPay()
	{
		var regStockId = frm.regStockId.value;
		var mid = frm.matchId.value;
	    if(regStockId==""){
	    	alert("请选择仓单号!");
	    }else{
	    	if(confirm("确认修改该配对信息?"))
	    	{
	     		frm.action="<%=basePath%>servlet/settleMatchController.${POSTFIX}?funcflg=settleModifyRegStock";
	     		frm.submit();
	     	}	
	     }
	}
	
	 function regStockIdajaxChange(regStockId)
	{
		//var regStockId = $("#regStockId").val();
   		$.ajax({   
			  type : "POST",     //HTTP 请求方法,默认: "GET"   
			  url : "<%=basePath%>servlet/ajaxController.${POSTFIX}?funcflg=settleModifyAjax",   //发送请求的地址   
			  data : "regStockId=" + regStockId, //发送到服务器的数据   
			  dataType : "xml",         //预期服务器返回的数据类型   
			  error: function(xml) { alert('Error loading XML document:'); }, 
			  success : function(data){
			  				$("#weight").html($(data).find("weight").text());
			  				$("#frozenweight").html($(data).find("frozenweight").text());
			  				$("#breedid").html($(data).find("breedid").text());
			  				      				  				
			  			}        //请求成功后回调函数   
			 });   
	  
	}
</script>
</html>