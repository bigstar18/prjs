<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../../public/headInc.jsp" %>
<base target="_self">
<html>
  <head>
	<title></title>
	<script>
	function doSubmit()
	{
		
			
			if (frm.firstPayRate.value == "") {
				alert("����׸���������Ϊ�գ�");
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
			if (frm.rewardRate.value == "") {
				alert("������Ӷ���������Ϊ�գ�");
				return false;
			}
			if (frm.rewardRate.value > 100) {
				alert("������Ӷ�����ӦС�ڰٷ�֮�٣�");
				return false;
			}
		if (confirm("�Ƿ��޸ĸ�Ʒ������������Ʒ���������ã�")){
			frm.tf.value="true";
			frm.submit();
		}else{
			frm.tf.value="false";
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
	
	function cancle(){
		window.close();
	}
	
	<c:if test='${not empty resultMsg}'>
		window.close();
	</c:if>
		
</script> 
</head>
<body onload="window_onload()">
        <form id="frm" name="frm" method="POST" callback="closeDialog(1);" targetType="hidden" action="<%=brokerRewardControllerPath%>brokerRewardPropsBreedUpdateAction">
		<input type="hidden" id="breedId" name="breedId" value="${brokerRewardProps.breedId}"/>
		<input type="hidden"  name="tf" value="false"/>
		<table align="center" width="500" height="80" border="0">
			<tr><td>&nbsp;</td></tr>
		</table>
		<table align="center" width="500">
		<tr><td>
		
		<fieldset style="width:490px;" >
		<legend  >Ӷ��������Ϣ</legend>
			
			
			<table align="center" border="0" cellspacing="0" cellpadding="0" width="490">
			  <tr height="35">
                <td align="right" >Ʒ�֣�</td>
                <td align="left" >
                	${brokerRewardProps.BreedName}
                </td>
              </tr>
              <tr height="35">
                <td align="right" >������Ӷ�������</td>
                <td align="left" >
                	<input class="text" maxlength="5" name="rewardRate" value="${brokerRewardProps.rewardRate}" onkeypress="return suffixNamePress()" style="width: 150px;" >&nbsp;%&nbsp;<font color="red">*</font>
                </td>
              </tr>
			  <tr height="35">
                <td align="right" >����׸�������</td>
                <td align="left" >
                	<input class="text" maxlength="5" name="firstPayRate" value="${brokerRewardProps.firstPayRate}" onkeypress="return suffixNamePress()" style="width: 150px;" onblur="change()">&nbsp;%&nbsp;<font color="red">*</font>
                </td>
                
              </tr>
			  <tr height="35">
			  	<td align="right">���β�������</td>
                <td align="left">
                	<input name="secondPayRate" type="text" value="${brokerRewardProps.secondPayRate}"  class="text" readonly="readonly" style="width: 150px;">&nbsp;%&nbsp;<font color="red">*</font>
                </td>
			  </tr>
			  
              <tr height="35">
                <td colspan="6"><div align="center">
                  <button class="smlbtn" type="button" onClick="doSubmit();">ȷ��</button>&nbsp;&nbsp;&nbsp;&nbsp;
                  <button class="smlbtn" type="button" onClick="cancle();">�ر�</button>
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
