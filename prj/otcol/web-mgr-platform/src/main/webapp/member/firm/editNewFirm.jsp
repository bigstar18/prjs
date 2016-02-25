<%@ page contentType="text/html;charset=GBK" %>
<%@ page import='gnnt.MEBS.member.firm.unit.Firm' %>
<%@ page import='gnnt.MEBS.member.firm.unit.FirmModule' %>
<%@page import="gnnt.MEBS.timebargain.manage.service.TariffManager"%>
<%@page import="gnnt.MEBS.member.firm.services.FirmService"%>
<% 
	Firm firm = null; 
	String permissions="";
	firm = (Firm)request.getAttribute("firm");
	 
	List listFm = firm.getFirmModule();
	if(listFm!=null&&listFm.size()>0)
	{
		for(int i=0;i<listFm.size();i++)
		{  
		  permissions+=","+((FirmModule)listFm.get(i)).getModuleId()+",";
		}
	}
	pageContext.setAttribute("user", firm);
	List list= (List)request.getAttribute("moduleList");
    pageContext.setAttribute("moduleList", list);
    TariffManager mgr=(TariffManager)gnnt.MEBS.timebargain.manage.util.SysData.getBean("tariffManager");
    pageContext.setAttribute("tariffList",mgr.getTariffPage());
	FirmService fs=(FirmService)gnnt.MEBS.member.firm.util.SysData.getBean("m_firmService");
	pageContext.setAttribute("tariffStatus",fs.checkTariffStatus(firm));
	   
%> 
<html  xmlns:MEBS>  
  <head>
    <%@ include file="../public/headInc.jsp" %> 
    <IMPORT namespace="MEBS" implementation="<%=basePathF%>/public/jstools/calendar.htc">
	<title>修改交易商</title>
	<script language="javascript" src="<%=basePathF%>/public/jstools/common.js"></script>
	<script language="javascript" src="<%=basePathF%>/public/jstools/tools.js"></script>
	<script>
		function doSubmit()
		{
			if(!checkValue("formNew"))
				return;
			if((formNew.email.value!="")&&!checkEmail(formNew.email))
			{
			  return;
			}

			if(formNew.organizationCode.value=="")
			{
				if(formNew.type.value==3)
				{
					alert("身份证号不能为空！");
					formNew.organizationCode.focus();
				}
				else
				{
					alert("组织机构代码不能为空！");
					formNew.organizationCode.focus();
				}
				return;
			}
			if(formNew.type.value==3 && formNew.organizationCode.value.length!=18){
			     alert("身份证号必须为18位！");
			     formNew.organizationCode.focus();
			     return;
			}
			var re=/^([A-Z0-9]|(-)){9,12}$/;
			if(formNew.type.value!=3 && formNew.organizationCode.value.search(re)==-1){
			     alert("组织机构代码只能是字母数字和-组成,如12345678-9或123456789");
			     formNew.organizationCode.focus();
			     return;
			}
			if(formNew.bankId.value == ""){
				alert("请选择银行！");
				return;
			}
			if(formNew.accountName.value == ""){
				alert("账户名不能为空！");
				return;
			}
			if(formNew.bank.value=="")
			{
				alert("开户银行不能为空！");
				formNew.bank.focus();
				return;
			}
			if(formNew.bankAccount.value=="")
			{
				alert("银行帐号不能为空！");
				formNew.bankAccount.focus();
				return;
			}
			
			formNew.submit();
		}
		function showTariff(){
			var firmTID=document.getElementById("firmTID").value;
			if(firmTID!=null&&firmTID!=''){
				openDialog("<%=request.getContextPath()%>/timebargain/baseinfo/tariff.do?funcflg=getCommodityTariffList&tariffID="+firmTID,"_blank",700,650);
			}else{
				alert("没有可查看的套餐！");
			}
		}
		function showOldTariff(){		
			openDialog("<%=request.getContextPath()%>/timebargain/baseinfo/tariff.do?funcflg=getCommodityTariffList&tariffID=${user.tariffID}","_blank",700,500);
		}

		function viewChange(){
			if(formNew.type.value==3)
				document.getElementById("viewText").innerText="身份证号 ：";
			else
				document.getElementById("viewText").innerText="组织机构代码 ：";
		}
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
			/**
		 *根据银行控输入框可写
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
</head>
<body>
        <form id="formNew" action="<%=basePath%>/firmController.mem?funcflg=firmMod" method="POST" targetType="hidden" callback="closeDialog(1);">
		<fieldset width="100%">
		<legend>交易商基本信息</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
			  <tr height="35">
                <td align="right"> <%=FIRMID%> ：</td>
                <td align="left">
                	<input class="readonly" id="firmId" name="firmId" value="<c:out value='${user.firmId}'/>" style="width: 200px;" reqfv="required;用户ID" readonly onkeypress="onlyNumberAndCharInput()" maxlength="16">&nbsp;<font color="#ff0000">*</font>
                </td>
                <td align="right"> 加盟商编号 ：</td>
                <td align="left">
                	<input class="readonly"  value="<c:out value='${brokerId}'/>" style="width: 200px;"  readonly  maxlength="16">&nbsp;
                </td>
            	 
                </tr>
              <tr height="35">
               <td align="right"> <%=FIRMNAME%> ：</td>
                <td align="left">
                	<input name="name" type="text" value="<c:out value='${user.name}'/>" class="text" style="width: 150px;" reqfv="required;用户名称" onkeypress="onlyNumberAndCharInput()" maxlength="16">&nbsp;<font color="#ff0000">*</font>
                </td>
            	<td align="right"> <%=FULLNAME%> ：</td>
                <td align="left">
                	<input name="fullname" type="text" class="text" style="width: 150px;" value="<c:out value='${user.fullname}'/>" onkeypress="onlyNumberAndCharInput()" maxlength="16">
                </td>
                
              </tr>
              <tr height="35">
              <td align="right"> EMail ：</td>
                <td align="left">
                	<input name="email" value="<c:out value='${user.email}'/>"  type="text" class="text" style="width: 150px;">
                </td>
                <td align="right"> 类型 ：</td>
                <td align="left">
               	  <select id="type" name="type" class="normal" style="width: 150px" reqfv="required;类别" onchange="viewChange();">
				    <OPTION value=""></OPTION>
				    <option value="1">法人</option>
        		    <option value="2">代理</option>
           		    <option value="3">个人</option>
	    		  </select>
	    		  <font color="#ff0000">*</font>
	    		  <script>
				    formNew.type.value = "<c:out value='${user.type}'/>"
			       </script>
                 </td>
                
              </tr>
               <!--       
              <tr height="35">
            	<td align="right"> 开户银行 ：</td>
                <td align="left">
                	<input name="bank" type="text" class="text" style="width: 150px;" value="<c:out value='${user.bank}'/>">&nbsp;<font color="#ff0000">*</font>
                </td>
            	<td align="right"> 银行帐号 ：</td>
                <td align="left">
                	<input name="bankAccount" type="text" class="text" style="width: 150px;" value="<c:out value='${user.bankAccount}'/>">&nbsp;<font color="#ff0000">*</font>
                </td>
              </tr>
                -->  
              <tr height="35">
              <td align="right"> 
                  <div id="viewText" name="viewText">
                    <c:if test="${user.type == 3}">
                                                        身份证号 ：
                    </c:if>
                    <c:if test="${user.type != 3}">
                                                         组织机构代码 ：
                    </c:if>                                  
                  </div>
                </td>
                <td align="left">
                	<input name="organizationCode" value="<c:out value='${user.extendMap.organizationCode}'/>"  type="text" class="text" style="width: 150px;" >&nbsp;<font color="#ff0000">*</font>
                </td>
            	<td align="right"> 营业执照号 ：</td>
                <td align="left">
                	<input name="businessLicenseNo" value="<c:out value='${user.extendMap.businessLicenseNo}'/>"  type="text" class="text" style="width: 150px;" >
                </td>
            	
              </tr>
              <tr height="35">
              <td align="right"> 注册资金 ：</td>
                <td align="left">
                	<input name="registeredCapital" value="<c:out value='${user.extendMap.registeredCapital}'/>"  type="text" class="text" style="width: 150px;" >
                </td>
            	<td align="right"> 税务登记号 ：</td>
                <td align="left">
                	<input name="taxRegistrationNo" value="<c:out value='${user.extendMap.taxRegistrationNo}'/>"  type="text" class="text" style="width: 150px;" >
                </td>
            	
              </tr>             
             <tr height="35">
            	 <td align="right"> 邮政编码 ：</td>
                <td align="left">
                	<input name="postCode" type="text" value="<c:out value='${user.postCode}'/>" class="text" style="width: 150px;" >
                </td>
            	<td align="right"> 法定代表人 ：</td>
                <td align="left">
                	<input name="legalRepresentative" value="<c:out value='${user.extendMap.legalRepresentative}'/>"  type="text" class="text" style="width: 150px;" >
                </td>
            	
              </tr>
              <tr height="35">
              	<td align="right"> 代表人手机号码 ：</td>
                <td align="left">
                	<input name="LRphoneNo" value="<c:out value='${user.extendMap.LRphoneNo}'/>"  type="text" class="text" style="width: 150px;" >
                </td>
            	<td align="right"> 联&nbsp;系&nbsp;人 ：</td>
                <td align="left">
                	<input name="contactMan" value="<c:out value='${user.contactMan}'/>"  type="text" class="text" style="width: 150px;" >
                </td>
            	
              </tr>
              <tr height="35">
              <td align="right"> 联系人手机号码 ：</td>
                <td align="left">
                	<input name="ContacterPhoneNo" value="<c:out value='${user.extendMap.ContacterPhoneNo}'/>"  type="text" class="text" style="width: 150px;" >
                </td>
            	<td align="right"> 联系电话 ：</td>
                <td align="left">
                	<input name="phone" type="text" value="<c:out value='${user.phone}'/>" class="text" style="width: 150px;" >
                </td>
            	
              </tr>
              <tr height="35">
              	<td align="right"> 传&nbsp;&nbsp;&nbsp;&nbsp;真 ：</td>
                <td align="left">
                	<input name="fax" type="text" value="<c:out value='${user.fax}'/>" class="text" style="width: 150px;" >
                </td>
            	<td align="right"> 业务联系人 ：</td>
                <td align="left">
                	<input name="businessContacter" value="<c:out value='${user.extendMap.businessContacter}'/>"  type="text" class="text" style="width: 150px;" >
                </td>
            	
              </tr>
                                    
			 <tr height="30">
				 <td align="right"> 企业性质 ：</td>
                <td align="left">
                	<input name="enterpriseKind" value="<c:out value='${user.extendMap.enterpriseKind}'/>"  type="text" class="text" style="width: 150px;" >
                </td>
		         <td align="right"> 订单手续费套餐 ：</td>
		         <td align="left" width="220">
		                
		         <select width="80" id="firmTID" name="tariffID">
		         
		         <c:forEach items="${tariffList}" var="result">       
		         <option value="${result.tariffID}" <c:if test="${result.tariffID == user.tariffID }">
		         <c:out value="selected"/></c:if>>${result.tariffName }</option>
		         </c:forEach>
		         </select>&nbsp;&nbsp;<span id="tariffview" name="tariffview" onclick='showTariff()'  style="color:#00008B;
		    text-decoration:underline;">查看套餐详细信息</span> 
		         </td>
         	</tr>
         	<tr>
         	 <td align="right"> 类别 ：</td>
                <td align="left">
	                <select id="firmCategoryId" name="firmCategoryId" class="normal" style="width: 150px" reqfv="required;类别" >
						<c:forEach items="${resultList}" var="result">
							<option value="${result.id}" <c:if test="${result.id == user.firmCategoryId }">
			         	<c:out value="selected"/></c:if>>${result.name}</option>
						</c:forEach>
		    			<font color="#ff0000">*</font>
		    		</select>
             	</td>
             	 <td align="right"> </td>
                <td align="left">
             	</td>
         	</tr>
         	<tr>
              	<td colspan="4">
              	  <fieldset width="100%" style="border-style:ridge;border-color:F3FDFC">
              	   <legend>录入银行信息</legend>
              	   <table border="0" cellspacing="0" cellpadding="0" width="100%" align="center">
		              <tr height="35">
		            	<td align="right"> 银&nbsp;&nbsp;&nbsp;&nbsp;行 ：</td>
		                <td align="left">
		              
		                	<select id="bankId" name="bankId"  onchange="bankChangeFun(this);">
		                	  <option value="">请选择</option>
		                	  <c:forEach items="${bankList}" var="bankMap">
		                		<option value='${bankMap["bankID"] }'>${bankMap["bankName"] }</option>
		                	  </c:forEach>
		                	  <option value="-1">其他</option>
		                	</select>&nbsp;<font color="#ff0000">*</font>
		                	<script type="text/javascript">
		                	formNew.bankId.value=${(user.extendMap.bankId == null || user.extendMap.bankId == "") ? "-1" : user.extendMap.bankId}
		                	</script>
		                </td>
		            	<td align="right"> 账&nbsp;户&nbsp;名 ：</td>
		                <td align="left">
		                	<input name="accountName" type="text"  class="text" style="width: 150px;" maxlength="64" value="${user.extendMap.accountName }"/>&nbsp;<font color="#ff0000">*</font>
		                </td>
		              </tr>
		              <tr height="35">
		            	<td align="right"> 开户银行 ：</td>
		                <td align="left">
		                	<input name="bank" type="text" value="${user.bank }"  class="text" style="width: 150px;" maxlength="32">&nbsp;<font color="#ff0000">*</font>
		                </td>
		            	<td align="right"> 银行帐号 ：</td>
		                <td align="left">
		                	<input name="bankAccount" type="text" value="${user.bankAccount }" class="text" style="width: 150px;" maxlength="64" onkeyup="this.value=this.value.replace(/\D/g,'')">&nbsp;<font color="#ff0000">*</font>
		                </td>
		              </tr>
		              <tr height="35">
		            	<td align="right"> 开户行名称 ：</td>
		                <td align="left">
		                	<input name="bankName" type="text"  class="text" style="width: 150px;" maxlength="64" value="${user.extendMap.bankName }"/>
		                </td>
		            	<td align="right"> 开户行省份 ：</td>
		                <td align="left">
		                	<input name="bankProvince" type="text"  class="text" style="width: 150px;" maxlength="64" value="${user.extendMap.bankProvince }"/>
		                </td>
		              </tr>
		              <tr height="35">
		            	<td align="right"> 开户行所在市 ：</td>
		                <td align="left">
		                	<input name="bankCity" type="text"  class="text" style="width: 150px;" maxlength="64" value="${user.extendMap.bankCity }"/>
		                </td>
		              </tr>
		           </table>
		           </fieldset>
                </td>
              </tr>
         	
              <tr height="35">
                <td align="right"> 详细地址 ：</td>
                <td align="left" colspan="3">
                	<textarea name="address" cols="64" rows="3"><c:out value='${user.address}'/></textarea>
                </td>
              </tr>
              <tr height="35">
            	<td align="right">经营业务范围 ：</td>
                <td align="left" colspan="3">
                	<textarea name="businessScope" cols="64" rows="3"><c:out value='${user.extendMap.businessScope}'/></textarea>
                </td>
              </tr>
              <tr height="35">
              <td align="right"> 备注 ：</td>
              <td align="left" colspan="3">
                <textarea name="note" cols="64" rows="3" ><c:out value='${user.note}'/></textarea>
              </td>
              <tr height="35">
                <td align="right"> 权限 ：</td>
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
              <tr height="35">
                <td colspan="4"><div align="center">
                  <button class="smlbtn" type="button" onclick="doSubmit();" readonly>提交</button>&nbsp;
      			  <button class="smlbtn" type="button" onclick="window.close()">关闭窗口</button>
                </div></td>
              </tr>
          </table>
		</fieldset>
        </form>
</body>
</html>
<%@ include file="../public/footInc.jsp" %>