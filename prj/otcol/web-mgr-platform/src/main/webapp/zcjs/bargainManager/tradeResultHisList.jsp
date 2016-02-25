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
					��ͬ���ٹ����ѯ
				</legend>
					<table border="0" cellspacing="0" cellpadding="0" width="100%">
						<tr height="35">
							<td align="left" class="tdstyle">
								��ͬ��
							</td>
							<td align="left">
								<input name="_tradeNo[like]" id="tradeNo" type="text" class="text"
									style="width: 100px;" value='${oldParams["tradeNo[like]"]}' onkeypress="notSpace()">
							</td>
							<td align="left" class="tdstyle">
								�򷽽�����
							</td>
							<td align="left">
								<input name="_firmId_B[like]" type="text" class="text" id="firmId_B"
									style="width: 100px;" value='${oldParams["firmId_B[like]"] }' onkeypress="notSpace()">
							</td>
							<td align="left" class="tdstyle">
								����������
							</td>
							<td align="left">
								<input name="_firmId_S[like]" type="text" class="text" id="firmId_S"
									style="width: 100px;" value='${oldParams["firmId_S[like]"] }' onkeypress="notSpace()">
							</td>
							<td align="left">
								��ͬ״̬
							</td>
							<td align="left">
								<select name="_status[=]" id="status">
									<option value="">
										ȫ��
									</option>
									<option value="1" <c:if test="${oldParams['status[=]']==1 }">selected</c:if>>
										δ����
									</option>
									<option value="2" <c:if test="${oldParams['status[=]']==2 }">selected</c:if>>
										������
									</option>
									<option value="3" <c:if test="${oldParams['status[=]']==3 }">selected</c:if>>
										�Ѵ���
									</option>
								</select>
							</td>
							<td align="left">
								����ʣ������
							</td>
							<td align="left">
								<input type="text" name="_spareDate[=]" id="date" value="${oldParams['spareDate[=]']}" size="8" onkeypress="notSpace()"/>
							</td>
							
						</tr>
						<tr>
								<td align="right" colspan="10">
								<input type="button" onclick="submitQuery()" class="btn" value="��ѯ">&nbsp;&nbsp;
								<input type="button" onclick="resetForm()" class="btn" value="����">
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
		var r=/^[0-9]{1,20}$/;��
		if(!checkNumber(tradeNo)){
			document.getElementById('tradeNo').value="";
			document.getElementById('tradeNo').focus();
			alert('��ͬ�ű���Ϊ����');
			return false;
		}
		if(date!=null&&date!=""){
			if(!checkRate(date)){
			document.getElementById('date').value="";
			document.getElementById('date').focus();
			alert('ʣ��ʱ�����Ϊ����');
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
	//�ж�input�Ƿ�Ϊ����
function checkNumber(input){
		if(input==""){
			return true;
		}else{
			return checkRate(input);
		}
	}
function checkRate(input)    
	{    
  		var re = /^[0-9]+.?[0-9]*$/;   //�ж��ַ����Ƿ�Ϊ����
  		var res=/^\-[1-9][0-9]*$/;
  		
  		if (!re.test(input)&&!res.test(input)){
  			return false;
  		}else{
  			return true;
  		}
  	}
</script>
	