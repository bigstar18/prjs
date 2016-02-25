<%@ include file="../../../public/session.jsp"%>
<%@ page language="java" pageEncoding="GBK"%>
<base target="_self">
<html>
  <head>
    <title>�޸���Գֲ���Ϣ</title>
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
			<legend class="common"><b>�޸���Գֲ���Ϣ</b></legend>
			<span>
			<table class="common" align="center" width="100%">
				<tr class="common">
					<td align="right" width="50%">���������̴���:</td>
					<td align="left" width="50%">${settleMatch.firmID_S }</td>
				</tr>
				<tr class="common">
					<td align="right" width="50%">Ʒ�ִ���:</td>
					<td align="left" width="50%">${settleMatch.breedId }</td>
				</tr>
				<tr class="common">
					<td align="right" width="50%">�������ռ�:</td>
					<td align="left" width="50%"><fmt:formatNumber value="${settleMatch.sellPrice }" pattern="#,##0.00"/></td>
				</tr>
				<tr class="common">
				
					<td align="right" width="50%">ע��ֵ���:</td>
					<td align="left" width="50%">
					      <select name="regStockId" id="regStockId" onchange="regStockIdajaxChange(this.value)" >
							<OPTION value="">��ѡ��</OPTION>						
							<c:forEach items="${list}" var="list">
								<OPTION value="${list.regStockId }">${list.regStockId }</OPTION>
							</c:forEach>
						</select></td>
				</tr>
				<tr class="common">
					<td align="right" width="50%">
						Ʒ��:
					</td>
					<td align="left" width="50%">
						<span id="breedid"></span>
					</td>
				</tr>
				<tr class="common">
					<td align="right" width="50%">
						����:
					</td>
					<td align="left" width="50%">
						<span id="weight"></span>
					</td>
				</tr>
				<tr class="common">
					<td align="right" width="50%">
						��������:
					</td>
					<td align="left" width="50%">
						<span id="frozenweight"></span>
					</td>
				</tr>
						<tr class="common">
					<td align="center" width="100%" colspan="2">
						<input type="button" name="subbtn" class="button" value="�ύ" onclick="thisPay();">&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" class="button" value="�ر�" onclick="window.close();">
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
	    	alert("��ѡ��ֵ���!");
	    }else{
	    	if(confirm("ȷ���޸ĸ������Ϣ?"))
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
			  type : "POST",     //HTTP ���󷽷�,Ĭ��: "GET"   
			  url : "<%=basePath%>servlet/ajaxController.${POSTFIX}?funcflg=settleModifyAjax",   //��������ĵ�ַ   
			  data : "regStockId=" + regStockId, //���͵�������������   
			  dataType : "xml",         //Ԥ�ڷ��������ص���������   
			  error: function(xml) { alert('Error loading XML document:'); }, 
			  success : function(data){
			  				$("#weight").html($(data).find("weight").text());
			  				$("#frozenweight").html($(data).find("frozenweight").text());
			  				$("#breedid").html($(data).find("breedid").text());
			  				      				  				
			  			}        //����ɹ���ص�����   
			 });   
	  
	}
</script>
</html>