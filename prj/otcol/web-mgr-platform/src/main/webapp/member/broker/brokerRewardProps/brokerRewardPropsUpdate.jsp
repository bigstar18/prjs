<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../../public/headInc.jsp" %>
<html>
  <head>
	<title></title>
	<script>
	function doSubmit()
	{
		if (confirm("��ȷ��Ҫ�ύ��")){
			if (frm.breedId.value == "") {
				alert("<%=BREEDID%>����Ϊ�գ�");
				return false;
			}
			if (frm.moduleId.value == "") {
				alert("<%=MODULEID%>����Ϊ�գ�");
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
			if (frm.firstPayRate.value > 100) {
				alert("����׸�����ӦС�ڵ��ڰٷ�֮�٣�");
				return false;
			}
			if (frm.secondPayRate.value == "") {
				alert("���β���������Ϊ�գ�");
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
			
			if (frm.payPeriodDate.value != "") {
				var payDate = parseInt(frm.payPeriodDate.value);
				if (frm.payPeriod[0].checked == true) {
					if (payDate < 1 || payDate > 28) {
						alert("���������ս�ֹ��28�ţ�");
						return false;
					}
				}else {
					if (payDate < 1 || payDate > 7) {
						alert("���������ճ�����Χ��");
						return false;
					}
				}
			}else {
				alert("���������ղ���Ϊ�գ�");
				return false;
			}
			frm.submit();
		}
		
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
	
	function changeradio(){
		frm.payPeriodDate.value="";
		frm.payPeriodDate.select();
	}
</script> 
</head>
<body onload="window_onload()">
        <form id="frm" name="frm" method="POST" targetType="hidden" action="<%=brokerRewardControllerPath%>brokerRewardPropsUpdate">
		<input type="hidden" id="breedId" name="breedId" value="${brokerReward.breedId}"/>
		<input type="hidden" id="moduleId" name="moduleId" value="${brokerReward.moduleId}"/>
		<input type="hidden" id="brokerId" name="brokerId" value="${brokerReward.brokerId}"/>
		<table align="center" width="50%">
		<tr><td>
		<fieldset style="width:800px;" >
		<legend  >Ӷ��������Ϣ</legend>
			
			
			<table align="center" border="0" cellspacing="0" cellpadding="0" width="50%">
			 
              <tr height="35">
                <td align="right" >������Ӷ�������</td>
                <td align="left" >
                	<input class="text" maxlength="5" name="rewardRate" value="${brokerReward.rewardRate}" onkeypress="return suffixNamePress()" style="width: 150px;" >&nbsp;%&nbsp;<font color="red">*</font>
                </td>
              </tr>
               <tr height="35">
			  	<td align="right">�Ƿ��Զ����</td>
                <%
                	String type = (String)request.getAttribute("type");
                %>
                <td align="left">
                	<input name="autoPay" type="radio"  value="Y" <%if("Y".equals(type)){out.println("checked");} %> class="" style="border:0px;" >��
                	<input name="autoPay" type="radio"  value="N" <%if("N".equals(type)){out.println("checked");} %> class="" style="border:0px;" >��
                </td>
			  </tr>
			  
			  <tr height="35">
                <td align="right" >����׸�������</td>
                <td align="left" >
                	<input class="text" maxlength="5" name="firstPayRate" value="${brokerReward.firstPayRate}" onkeypress="return suffixNamePress()" style="width: 150px;" onblur="change()">&nbsp;%&nbsp;<font color="red">*</font>
                </td>
                
              </tr>
			  <tr height="35">
			  	<td align="right">���β�������</td>
                <td align="left">
                	<input name="secondPayRate" type="text" value="${brokerReward.secondPayRate}"  class="text" readonly="readonly" style="width: 150px;" onkeypress="return suffixNamePress()">&nbsp;%&nbsp;<font color="red">*</font>
                </td>
			  </tr>
			 
			  <tr height="35">
			  	<td align="right">�������ڣ�</td>
                <%
                	String payPeriod = (String)request.getAttribute("payPeriod");
                %>
                <td align="left">
                	<input name="payPeriod" type="radio" onclick="changeradio();" value="1" <%if("1".equals(payPeriod)){out.println("checked");} %> class="" style="border:0px;" >����
                	<input name="payPeriod" type="radio" onclick="changeradio();" value="2" <%if("2".equals(payPeriod)){out.println("checked");} %> class="" style="border:0px;" >������
                </td>
			  </tr>
			  
			  <tr height="35">
                <td align="right" >���������գ�</td>
                <td align="left" >
                	<input class="text" maxlength="5" name="payPeriodDate" value="${brokerReward.payPeriodDate}" onkeypress="return suffixNamePress()" style="width: 150px;" >&nbsp; <font color="red">*</font>
                </td>
              </tr>
              <tr height="35">
                <td colspan="6"><div align="center">
                  <button class="smlbtn" type="button" onClick="doSubmit();">ȷ��</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                </div></td>
              </tr>
          </table>
         
		</fieldset>
		</td></tr>
		<tr>
			<td>
				<font color="red"> *  ��������Ϊ����ʱ�����������ձ�ʾ��������(��15��ʾ15��)�����򸶿������ձ�ʾ��������(��1��ʾ������)</font>
			</td>
		</tr>
		<tr>
			<td>
				<font color="red"> *  �����ն�Ӧ��ϵ�������գ�1������һ��2�����ڶ���3����������4�������ģ�5�������壺6����������7</font>
			</td>
		</tr>
		<tr>
			<td>
				<font color="red"> *  �Ƿ��Զ�������׸�Ӷ���β��Ӷ��ͬʱ��Ч�����Զ���������Ϊ��ʱ�����׸���β�����Ҫ�ֶ�֧����</font>
			</td>
		</tr>
		</table>
        </form>
</body>
</html>

