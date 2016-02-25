<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../public/session.jsp"%>
<html>
  <head>
    <title></title>
  </head>
  <body onload="initBody('${returnRefresh}')">
  	<form name="frm" action="<%=basePath%>bargainManagerController.zcjs?funcflg=getHisTradelist" method="post">
		<input type="hidden" name="orderField" value="${orderFiled}">
		<input type="hidden" name="orderDesc" value="${orderType}">
		<input type="hidden" name="pageSize" value="<c:out value="${pageInfo.pageSize}"/>">
		<input type="hidden" id="pageNo" name="pageNo">
			<fieldset width="100%">
				<legend>
					合同跟踪管理查询
				</legend>
					<table border="0" cellspacing="0" cellpadding="0" width="100%">
						<tr height="35">
							<td align="left" class="tdstyle">
								合同号
							</td>
							<td align="left">
								<input name="_tradeNo[like]" id="tradeNo" type="text" class="text"
									style="width: 100px;" value='${oldParams["tradeNo[like]"]}' onkeypress="notSpace()">
							</td>
							<td align="left" class="tdstyle">
								买方交易商
							</td>
							<td align="left">
								<input name="_firmId_B[like]" type="text" class="text" id="firmId_B"
									style="width: 100px;" value='${oldParams["firmId_B[like]"] }' onkeypress="notSpace()">
							</td>
							<td align="left" class="tdstyle">
								卖方交易商
							</td>
							<td align="left">
								<input name="_firmId_S[like]" type="text" class="text" id="firmId_S"
									style="width: 100px;" value='${oldParams["firmId_S[like]"] }' onkeypress="notSpace()">
							</td>
							<td align="left">
								合同状态
							</td>
							<td align="left">
								<select name="_status[=]" id="status">
									<option value="">
										全部
									</option>
									<option value="1" <c:if test="${oldParams['status[=]']==1 }">selected</c:if>>
										未处理
									</option>
									<option value="2" <c:if test="${oldParams['status[=]']==2 }">selected</c:if>>
										处理中
									</option>
									<option value="3" <c:if test="${oldParams['status[=]']==3 }">selected</c:if>>
										已处理
									</option>
								</select>
							</td>
							<td align="left">
								交割剩余天数
							</td>
							<td align="left">
								<input type="text" name="_spareDate[=]" id="date" value="${oldParams['spareDate[=]']}" size="8" onkeypress="notSpace()"/>
							</td>
							
						</tr>
						<tr>
								<td align="right" colspan="10">
								<input type="button" onclick="submitQuery()" class="btn" value="查询">&nbsp;&nbsp;
								<input type="button" onclick="resetForm()" class="btn" value="重置">
								</TD>
						</tr>
					</table>
			</fieldset>
			
			<%@ include file="tradeResultHisTable.jsp"%>
		</form>
	</body>
</html>
<script type="text/javascript">

	function submitQuery(){
		var tradeNo=document.getElementById('tradeNo').value;
		var date=document.getElementById('date').value;
		var r=/^[0-9]{1,20}$/;　
		if(!checkNumber(tradeNo)){
			document.getElementById('tradeNo').value="";
			document.getElementById('tradeNo').focus();
			alert('合同号必须为数字');
			return false;
		}
		if(date!=null&&date!=""){
			if(!checkRate(date)){
			document.getElementById('date').value="";
			document.getElementById('date').focus();
			alert('剩余时间必须为数字');
			return false;
			}
		}
		frm.submit();
	}
	function resetForm(){
		frm.tradeNo.value = "";
		frm.firmId_B.value = "";
		frm.firmId_S.value = "";
		frm.status.value = "";
		frm.submit();
	}
	function fdetail(tradeid){
		frm.action = "<%=basePath%>bargainManagerController.zcjs?funcflg=getDeliveryList&tradeId="+tradeid;
		frm.submit();
   
	}
	//判断input是否为数字
function checkNumber(input){
		if(input==""){
			return true;
		}else{
			return checkRate(input);
		}
	}
function checkRate(input)    
	{    
  		var re = /^[0-9]+.?[0-9]*$/;   //判断字符串是否为数字
  		var res=/^\-[1-9][0-9]*$/;
  		
  		if (!re.test(input)&&!res.test(input)){
  			return false;
  		}else{
  			return true;
  		}
  	}
</script>
	