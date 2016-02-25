<%@ page contentType="text/html;charset=GBK" %>
<%@ page import='gnnt.MEBS.member.firm.services.*'%>
<%@ page import='gnnt.MEBS.member.firm.unit.Firm' %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page import="gnnt.MEBS.timebargain.manage.service.TariffManager"%>

<% 
	String firmId = request.getParameter("firmId");
    FirmService firmService=(FirmService)gnnt.MEBS.member.firm.util.SysData.getBean("m_firmService");
    //FirmService firmService= new FirmService();
	
	Firm firm = null; 
	List brokerList  = null;
	List bankList = null;
	String permissions="";
	if(firmId != null){
		try{
		firm = firmService.getFirmById( firmId );
		brokerList = firmService.getBrokerByFirmId(firmId);
		bankList = firmService.getBankList();
		}catch(Exception e){e.printStackTrace();}
		 
		//String[] p=firm.getPermissions();
		
		//if(p!=null&&p.length>0)
		//{
		//	for(int i=0;i<p.length;i++)
		//	{  
			//  permissions+=","+p[i]+",";
		//	}
	//}
		pageContext.setAttribute("brokerList", brokerList);
		pageContext.setAttribute("user", firm);
		pageContext.setAttribute("bankList", bankList);
	}
	//List list=SystemManager.getTradeModuleList(null,null);
   // pageContext.setAttribute("moduleList", list);
	/*List listZone=FirmManager.getZones(null,null);
    List listIndustry=FirmManager.getIndustrys(null,null);
    pageContext.setAttribute("zone", listZone);
    pageContext.setAttribute("industry", listIndustry);*/
    TariffManager mgr=(TariffManager)gnnt.MEBS.timebargain.manage.util.SysData.getBean("tariffManager");
	pageContext.setAttribute("tariffList",mgr.getTariffPage());
	
%> 
<html  xmlns:MEBS>  
  <head>
    <%@ include file="../../public/headInc.jsp"%>
    <IMPORT namespace="MEBS" implementation="<%=basePathF%>/public/jstools/calendar.htc">
	<title>��ϸ��Ϣ</title>
	<script language="javascript" src="<%=basePathF%>/public/jstools/common.js"></script>
	<script language="javascript" src="<%=basePathF%>/public/jstools/tools.js"></script>
	<script>
		function doSubmit()
		{	
			var text=document.getElementById("viewText").innerText;
			if(formNew.name.value==''){
				alert("���������Ʋ���Ϊ��");
				formNew.name.focus();
				return;
			} else if(formNew.type.value==''){
				alert("���Ͳ���Ϊ�գ�");
				formNew.type.focus();
				return;
			}else if(formNew.bankId.value == ""){
				alert("��ѡ�����У�");
				return;
			}else if(formNew.accountName.value == ""){
				alert("�˻�������Ϊ�գ�");
				return;
			}else if(formNew.bank.value==''){
				alert("�������в���Ϊ�գ�");
				formNew.bank.focus();
				return;
			}else if(formNew.bankAccount.value==''){
				alert("�����ʺŲ���Ϊ�գ�");
				formNew.bankAccount.focus();
				return;
			}else if((formNew.email.value!="")&&!checkEmail(formNew.email)){
			  	return;
			}
			if(formNew.organizationCode.value=="")
			{
				if(formNew.type[0].value==3)
				{
					alert("���֤�Ų���Ϊ�գ�");
					formNew.organizationCode.focus();
				}
				else
				{
					alert("��֯�������벻��Ϊ�գ�");
					formNew.organizationCode.focus();
				}
				return;
			}
			if(formNew.type.value==3 && formNew.organizationCode.value.length!=18){
			     alert("���֤�ű���Ϊ18λ��");
			     formNew.organizationCode.focus();
			     return;
			}
			var re=/^([A-Z|a-z0-9]|(-)){9,10}$/;
			if(formNew.type.value!=3 && formNew.organizationCode.value.search(re)==-1){
			     alert("��֯��������ֻ������ĸ���ֺ�-���,��12345678-9��123456789");
			     formNew.organizationCode.focus();
			     return;
			}

			if(checkChecked()==false){
				alert("��ѡ����Ҫ���еĲ�����");
			}else if(formNew.operation[2].checked){
				if(formNew.reasonContent.value==""||formNew.reasonContent.value==null){
					alert("�����벵��ԭ��!");
					return;
				}else{
					formNew.submit();
				}
			}else{
				formNew.submit();
			}
		}
		function showTariff(){
		var firmTID=document.getElementById("firmTID").value;
		openDialog("<%=request.getContextPath()%>/timebargain/baseinfo/tariff.do?funcflg=getCommodityTariffList&tariffID="+firmTID,"_blank",700,650);
		}
		
		function checkChecked(){
			//������ ��ѡ��ť���Ƿ���ѡ�е�
			for(var i=0;i<formNew.operation.length;i++){   
   				if(formNew.operation[i].checked){
    				 return true;
    			}
			}
			return false;
		}
		
		function viewChange(){
			if(formNew.type.value==3)
				document.getElementById("viewText").innerText="���֤�� ��";
			else
				document.getElementById("viewText").innerText="��֯�������� ��";
			
		}
		function show(){
			document.getElementById("backReason").style.display="block";
			document.getElementById("reasonContent").style.display="block";
		}
		function hide(){
			document.getElementById("backReason").style.display="none";
			document.getElementById("reasonContent").style.display="none";
		}
		/**
		 *�������п�������д
		 */
		function bankChangeFun(obj) {
			if(obj.value=="-1" || obj.value==""){
				formNew.bank.value = "";
				formNew.bank.readOnly = false;
			} else if(obj.value == "66") {
				formNew.bankAccount.readOnly = true;
				formNew.bankAccount.value = "999999999999999999";
			} else {
				formNew.bank.value = obj.options[obj.selectedIndex].text;
				formNew.bank.readOnly = true;
			}
		}
	</script> 
	<base target="_self"/>
	
</head>
<body onload="viewChange()">
        <form id="formNew" name="formNew" action="<%=basePath%>/firmController.mem?funcflg=doSubmit" method="POST" targetType="hidden" callback="closeDialog(1);">
		<fieldset width="100%">
		<legend>�����̻�����Ϣ</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
			  <tr height="35">
                <td align="right"> <%=FIRMID%> ��</td>
                <td align="left">
                	<input class="readonly" id="firmId" name="firmId" value="<c:out value='${user.firmId}'/>" style="width: 200px;" reqfv="required;�û�ID" readonly>&nbsp;<font color="#ff0000">*</font>
                </td>
                <td align="right"> ���������� ��</td>
                <td align="left">
                	<input  class="readonly" name="belongBroker" value="<c:out value='${brokerList[0].name}'/>"  type="text" class="text" style="width: 150px;" >
                </td>
            	
                </tr>
              <tr height="35">
            	<td align="right"> <%=FULLNAME%> ��</td>
                <td align="left">
                	<input name="fullname" type="text" class="text" style="width: 150px;" value="<c:out value='${user.fullname}'/>">
                </td>
                <td align="right"> <%=FIRMNAME%> ��</td>
                <td align="left">
                	<input name="name" type="text" value="<c:out value='${user.name}'/>" class="text" style="width: 150px;" reqfv="required;�û�����">&nbsp;<font color="#ff0000">*</font>
                </td>
              </tr>
              <tr height="35">
            	<td align="right"> ���� ��</td>
                <td align="left">
               	<select id="type" name="type" class="normal" style="width: 150px" reqfv="required;���" onchange="viewChange();">
				<OPTION value=""></OPTION>
				<option value="1">����</option>
        		<option value="2">����</option>
           		<option value="3">����</option>
	    		</select>
	    		&nbsp;<font color="#ff0000">*</font>
	    		<script>
				formNew.type.value = "<c:out value='${user.type}'/>"
			  </script>
                <td align="right"><div id="viewText" name="viewText">��֯�������� ��</div></td>
                <td align="left">
                	<input name="organizationCode" value="<c:out value='${user.extendMap.organizationCode}'/>"  type="text" class="text" style="width: 150px;" >&nbsp;<font color="#ff0000">*</font>
                </td>
              </tr>
              <!--         
              <tr height="35">
            	<td align="right"> �������� ��</td>
                <td align="left">
                	<input name="bank" type="text" class="text" style="width: 150px;" value="<c:out value='${user.bank}'/>">&nbsp;<font color="#ff0000">*</font>
                </td>
            	<td align="right"> �����ʺ� ��</td>
                <td align="left">
                	<input name="bankAccount" type="text" class="text" style="width: 150px;" value="<c:out value='${user.bankAccount}'/>">&nbsp;<font color="#ff0000">*</font>
                </td>
              </tr>
              --> 
              <tr height="35">
            	<td align="right"> Ӫҵִ�պ� ��</td>
                <td align="left">
                	<input name="businessLicenseNo" value="<c:out value='${user.extendMap.businessLicenseNo}'/>"  type="text" class="text" style="width: 150px;" >
                </td>
            	<td align="right"> ע���ʽ� ��</td>
                <td align="left">
                	<input name="registeredCapital" value="<c:out value='${user.extendMap.registeredCapital}'/>"  type="text" class="text" style="width: 150px;" >
                </td>
              </tr>
              <tr height="35">
              	<td align="right"> ˰��ǼǺ� ��</td>
                <td align="left">
                	<input name="taxRegistrationNo" value="<c:out value='${user.extendMap.taxRegistrationNo}'/>"  type="text" class="text" style="width: 150px;" >
                </td>
                 </td>
            	<td align="right"> �������� ��</td>
                <td align="left">
                	<input name="postCode" type="text" value="<c:out value='${user.postCode}'/>" class="text" style="width: 150px;" >
                </td>
            	
              </tr>             
             <tr height="35">
             	<td align="right"> ���������� ��</td>
                <td align="left">
                	<input name="legalRepresentative" value="<c:out value='${user.extendMap.legalRepresentative}'/>"  type="text" class="text" style="width: 150px;" >
                </td>
             	
            	<td align="right"> �������ֻ����� ��</td>
                <td align="left">
                	<input name="LRphoneNo" value="<c:out value='${user.extendMap.LRphoneNo}'/>"  type="text" class="text" style="width: 150px;" >
                </td>
              </tr>
              <tr height="35">
              	<td align="right"> ��&nbsp;ϵ&nbsp;�� ��</td>
                <td align="left">
                	<input name="contactMan" value="<c:out value='${user.contactMan}'/>"  type="text" class="text" style="width: 150px;" >
                </td>
            	<td align="right"> ��ϵ���ֻ����� ��</td>
                <td align="left">
                	<input name="ContacterPhoneNo" value="<c:out value='${user.extendMap.ContacterPhoneNo}'/>"  type="text" class="text" style="width: 150px;" >
                </td>
              </tr>
              <tr height="35">
            	<td align="right"> ��ϵ�绰 ��</td>
                <td align="left">
                	<input name="phone" type="text" value="<c:out value='${user.phone}'/>" class="text" style="width: 150px;" >
                </td>
            	<td align="right"> ��&nbsp;&nbsp;&nbsp;&nbsp;�� ��</td>
                <td align="left">
                	<input name="fax" type="text" value="<c:out value='${user.fax}'/>" class="text" style="width: 150px;" >
                </td>
              </tr>
              <tr height="35">
            	<td align="right"> ҵ����ϵ�� ��</td>
                <td align="left">
                	<input name="businessContacter" value="<c:out value='${user.extendMap.businessContacter}'/>"  type="text" class="text" style="width: 150px;" >
                </td>
                <td align="right"> EMail ��</td>
                <td align="left">
                	<input name="email" value="<c:out value='${user.email}'/>"  type="text" class="text" style="width: 150px;">
                </td>
            	
              </tr>
                        
             <tr height="30">
             	<td align="right"> �������ײ� ��
		         <td align="left" width="220">
		                
		         <select width="80" id="firmTID" name="tariffID">
		         <c:forEach items="${tariffList}" var="result">       
		         <option value="${result.tariffID}" <c:if test="${result.tariffID == user.tariffID }"><c:out value="selected"/></c:if>>${result.tariffName }</option>
		         </c:forEach>
		         </select>&nbsp;&nbsp;<span id="tariffview" name="tariffview" onclick='showTariff()'  style="color:#00008B;
		    text-decoration:underline;">�鿴�ײ���ϸ��Ϣ</span> 
		         </td>
                <td align="right"> ��ҵ���� ��</td>
                <td align="left">
                	<input name="enterpriseKind" value="<c:out value='${user.extendMap.enterpriseKind}'/>"  type="text" class="text" style="width: 150px;" >
                </td>
		        
         	 </tr>
         	 
         	 <tr>
              	<td colspan="4">
              	  <fieldset width="100%" style="border-style:ridge;border-color:F3FDFC">
              	   <legend>¼��������Ϣ</legend>
              	   <table border="0" cellspacing="0" cellpadding="0" width="100%" align="center">
		              <tr height="35">
		            	<td align="right"> ��&nbsp;&nbsp;&nbsp;&nbsp;�� ��</td>
		                <td align="left">
		             
		                	<select id="bankId" name="bankId"  onchange="bankChangeFun(this);">
		                	  <option value="">��ѡ��</option>
		                	  <c:forEach items="${bankList}" var="bankMap">
		                		<option value='${bankMap["bankID"] }'>${bankMap["bankName"] }</option>
		                	  </c:forEach>
		                	  <option value="-1">����</option>
		                	</select>&nbsp;<font color="#ff0000">*</font>
		                	<script type="text/javascript">
		                	formNew.bankId.value=${(user.extendMap.bankId == null || user.extendMap.bankId == "") ? "-1" : user.extendMap.bankId}
		                	</script>
		                </td>
		            	<td align="right"> ��&nbsp;��&nbsp;�� ��</td>
		                <td align="left">
		                	<input name="accountName" type="text"  class="text" style="width: 150px;" maxlength="64" value="${user.extendMap.accountName }"/>&nbsp;<font color="#ff0000">*</font>
		                </td>
		              </tr>
		              <tr height="35">
		            	<td align="right"> �������� ��</td>
		                <td align="left">
		                	<input name="bank" type="text" value="${user.bank }"  class="text" style="width: 150px;" maxlength="32">&nbsp;<font color="#ff0000">*</font>
		           
		                </td>
		            	<td align="right"> �����ʺ� ��</td>
		                <td align="left">
		                	<input name="bankAccount" type="text"  value="${user.bankAccount }" class="text" style="width: 150px;" maxlength="64" onkeyup="this.value=this.value.replace(/\D/g,'')">&nbsp;<font color="#ff0000">*</font>
		                </td>
		              </tr>
		              <tr height="35">
		            	<td align="right"> ���������� ��</td>
		                <td align="left">
		                	<input name="bankName" type="text"  class="text" style="width: 150px;" maxlength="64" value="${user.extendMap.bankName }"/>
		                </td>
		            	<td align="right"> ������ʡ�� ��</td>
		                <td align="left">
		                	<input name="bankProvince" type="text"  class="text" style="width: 150px;" maxlength="64" value="${user.extendMap.bankProvince }"/>
		                </td>
		              </tr>
		              <tr height="35">
		            	<td align="right"> ������������ ��</td>
		                <td align="left">
		                	<input name="bankCity" type="text"  class="text" style="width: 150px;" maxlength="64" value="${user.extendMap.bankCity }"/>
		                </td>
		              </tr>
		           </table>
		           </fieldset>
                </td>
         	 
             <tr height="35">
                <td align="right"> ��ϸ��ַ ��</td>
                <td align="left" colspan="3">
                	<textarea name="address" cols="64" rows="3"><c:out value='${user.address}'/></textarea>
                </td>	
             </tr>   
              <tr height="35">
            	<td align="right">��Ӫҵ��Χ ��</td>
                <td align="left" colspan="3">
                	<textarea name="businessScope" cols="64" rows="3"><c:out value='${user.extendMap.businessScope}'/></textarea>
                </td>
              </tr>
              <tr height="35">
              <td align="right"> ��ע ��</td>
              <td align="left" colspan="3">
                <textarea name="note" cols="64" rows="3" ><c:out value='${user.note}'/></textarea>
              </td>
              <!-- 
              <tr height="35">
                <td align="right"> Ȩ�� ��</td>
                <td align="left" colspan="3">
                    <c:set var="count" value="0"/>
                    <c:set var="add" value="1"/>
                	<c:forEach items="${moduleList}" var="result">
        			<input type="checkbox" name="permissions" value="${result.moduleid}">${result.name}&nbsp;&nbsp;
        			<c:set var="count" value="${count+add}"/>
		        	</c:forEach>
		        	<script>
                    var allCheckbox=formNew.permissions; 
                    if(allCheckbox.length)
                    {
					for (var i=0;i<allCheckbox.length;i++){ 
					if (allCheckbox[i].type=="checkbox") 
					    var a=','+allCheckbox[i].value+',';
						if('<%=permissions%>'.indexOf(a)>=0)
						{
						allCheckbox[i].checked="true"; 
						}
					}
					}
					else
					{
					  var a=','+allCheckbox.value+',';
						if('<%=permissions%>'.indexOf(a)>=0)
						{
						allCheckbox.checked="true"; 
						}
					} 

					</script>
                </td>
              </tr>
              -->
              <tr height="35">
                <td align="right"> ���� ��</td>
                <td align="left" colspan="3">
                    <input type="radio" name="operation" value="1" onclick="hide()"/>�޸�
                    <input type="radio" name="operation" value="2" onclick="hide()"/>���ͨ��
                    <input type="radio" name="operation" value="3" onclick="show()"/>����
                    &nbsp;<font color="#ff0000">*</font>
                </td>
              </tr>
              	<tr>
                	<td align="right"><div id="backReason" style="display:none;"> ����ԭ�� ��</div></td>
                	<td align="left" colspan="3">
                    	<div id="reasonContent" style="display:none;"><textarea rows="3" cols="64" name="reasonContent"></textarea><font color="#ff0000">*</font></div>
                	</td>
              	</tr>
              <tr height="35">
                <td colspan="4">
                <div align="center"> 
      			  <input class="smlbtn" type="button" value="�ύ" onclick="doSubmit();">&nbsp;&nbsp;
      			  <button class="smlbtn" type="button" onclick="window.close()">�رմ���</button>
                </div></td>
              </tr>
          </table>
		</fieldset>
        </form>
</body>
</html>
<%@ include file="../../public/footInc.jsp" %>