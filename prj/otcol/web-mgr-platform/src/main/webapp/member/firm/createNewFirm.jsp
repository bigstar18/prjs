<%@ page contentType="text/html;charset=GBK" %>
<%@page import="gnnt.MEBS.timebargain.manage.service.TariffManager"%>
<%
  pageContext.setAttribute("date", "2019-01-01");
  List list= (List)request.getAttribute("moduleList");
  pageContext.setAttribute("moduleList", list);
  TariffManager mgr=(TariffManager)gnnt.MEBS.timebargain.manage.util.SysData.getBean("tariffManager");
  pageContext.setAttribute("tariffList",mgr.getTariffPage());
%>
<html  xmlns:MEBS>
  <head>
    <%@ include file="../public/headInc.jsp" %>
    <IMPORT namespace="MEBS" implementation="<%=basePathF%>/public/jstools/calendar.htc">
	<title>创建交易商</title>
	<script language="javascript" src="<%=basePathF%>/public/jstools/common.js"></script>
	<script language="javascript" src="<%=basePathF%>/public/jstools/tools.js"></script>
	<script language="javascript" src="<%=request.getContextPath() %>/common/public/jslib/jquery.js"></script>
	<script>
		function testFirmId(firmId){
			var reg =/^[a-zA-Z0-9]+$/g;
			if(!reg.test(firmId.value)){
				alert("交易商代码必须是数字和字母");
			}else{
				getBrokerId(firmId);
			}
				
		}
		/*
		 *检验加盟商代码的合法性
		 */
		function testBrokerId(brokerId){
			if(brokerId.value != null && brokerId.value != "") {
				var reg =/^[a-zA-Z0-9]+$/g;
				if(!reg.test(brokerId.value)){
					alert("加盟商编号必须是数字和字母");
					return;
				}
			}
			return true;
		}
		function doSubmit() 
		{ 
			var brokerId = formNew.brokerId;
			if(!checkValue("formNew"))
				return;
			if(formNew.firmId.value.length > 9){
				alert("交易商代码不得多于九位");
				return;
			}
			var reg =/^[a-zA-Z0-9]+$/g;
			if(!reg.test(formNew.firmId.value)){
				alert("交易商代码必须是数字和字母");
				return;
			}
			var reg2 =/^[a-zA-Z0-9]+$/g;
			if(brokerId.value != null && brokerId.value != "") {
				if(!reg2.test(brokerId.value)) {
					alert("加盟商代码必须是数字和字母");
					return;
				} else {
					if(!ajaxGetData(brokerId)) {
						return false;
					}
				}
			}
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
			if((formNew.type.value==3 && formNew.organizationCode.value.length!=18) && (formNew.type.value==3 && formNew.organizationCode.value.length!=15 )){
			     alert("身份证号必须为18位或者15位！");
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
		
		function viewChange(){
			if(formNew.type.value==3)
				document.getElementById("viewText").innerText="身份证号 ：";
			else
				document.getElementById("viewText").innerText="组织机构代码 ：";
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

		/**
		 *根据交易商代码截取会员账号
		 *
		 */
		function getBrokerId(obj) {
			var brokerId = formNew.brokerId.value;
			if(obj != null) {
				var objValue = obj.value; 
				var len = strLen(objValue);
				if(len >= 8) {
					if(len === 8) {
						brokerId = objValue.substr(0,4);
					} else if(len === 9) {
						brokerId = objValue.substr(0,5);
					}
				} else {
					brokerId = "";
				}
			}
			if(brokerId != undefined)
				formNew.brokerId.value = brokerId;
		}

		function strLen(str) { 
			var l = 0; 
			var c = str.split(""); 
			for (var i=0;i<c.length;i++) { 
				if (c[i].charCodeAt(0)<299) { 
					l++; 
				} else { 
					l+=2; 
				} 
			} 
			return l; 
		}

		/*
		 *检查加盟商信息
		 */
		function ajaxGetData(brokerID){
			var flag = false;
			$.ajax({
				url : "<%=brokerControllerPath%>getBrokerInfo",
				type : "post",
				async : false,
				data : {brokerID: brokerID.value},
				//dateType : "txt",
				error : function () { alert("error!")},
				success : function (data, status){
						if(data != null){
							var result = $.trim(data);
							//result 1、没有此会员； 0：有此会员
							if(result == 1) {
								alert("加盟商" +brokerID.value + "不存在！");
								flag = false;
							} else {
								flag = true;
							}
						} else {
							alert("查询加盟商失败，请稍后再试！");
							flag = false;
						}
					}
				});
			return flag;
	    }
		
	</script> 
</head>
<body>
        <form id="formNew" action="<%=basePath%>/firmController.mem?funcflg=firmAdd" method="POST" targetType="hidden" callback="closeDialog(1);">
		<fieldset width="100%">
		<legend>交易商基本信息</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
			  <tr height="35">
                <td align="right"> <%=FIRMID%> ：</td>
                <td align="left">
                	<input class="normal" name="firmId" id="firmId" type="text" style="width: 150px;" reqfv="required;交易商代码" maxlength="9"   onkeypress="onlyNumberAndCharInput()"  >&nbsp;<font color="#ff0000">*</font>
                </td>
                <td align="right"> 加盟商编号 ：</td>
                <td align="left">
                	<input name="brokerId" type="text" class="text" style="width: 150px;"  onblur="testBrokerId(this);" onkeyup="value=value.replace(/[\n\s\t]/g,'') "  maxlength="16" >&nbsp;
                </td>
            	
                </tr>
              <tr height="35">
              <td align="right"> <%=FIRMNAME%> ：</td>
                <td align="left">
                	<input name="name" type="text" class="text" style="width: 150px;" reqfv="required;交易商名称" onkeyup="value=value.replace(/[\n\s\t]/g,'') "  maxlength="16">&nbsp;<font color="#ff0000">*</font>
                </td>
            	<td align="right"> <%=FULLNAME%> ：</td>
                <td align="left">
                	<input name="fullname" type="text" class="text" style="width: 150px;"  maxlength="64" >
                </td>
                
                
              </tr>
              
              <tr height="35">    

            	<td align="right"> 类型 ：</td>
                <td align="left">
                  <select id="type" name="type" class="normal" style="width: 150px" reqfv="required;类别" onchange="viewChange();">
				    <OPTION value=""></OPTION>
				    <option value="1">法人</option>
        		    <option value="2">代理</option>
           		    <option value="3">个人</option>
	    		  </select>
	    		  <font color="#ff0000">*</font>
                </td>
            	<td align="right"> <div id="viewText" name="viewText">组织机构代码 ：</div></td>
                <td align="left">
                	<input name="organizationCode" type="text" class="text" style="width: 150px;" >&nbsp;<font color="#ff0000">*</font>
                </td>
  
              
              <tr height="35">
            	<td align="right"> 营业执照号 ：</td>
                <td align="left">
                	<input name="businessLicenseNo" type="text" class="text" style="width: 150px;" >
                </td>
            	<td align="right"> 注册资金 ：</td>
                <td align="left">
                	<input name="registeredCapital" type="text" class="text" style="width: 150px;" >
                </td>
              </tr>
              <tr height="35">
            	<td align="right"> 税务登记号 ：</td>
                <td align="left">
                	<input name="taxRegistrationNo" type="text" class="text" style="width: 150px;" >
                </td>
                <td align="right"> 邮政编码 ：</td>
                <td align="left">
                	<input name="postCode" type="text" class="text" style="width: 150px;" onkeyup="this.value=this.value.replace(/\D/g,'')" maxlength="6">
                </td>
            	
              </tr>             
             <tr height="35">
            	<td align="right"> 法定代表人 ：</td>
                <td align="left">
                	<input name="legalRepresentative" type="text" class="text" style="width: 150px;" >
                </td>
            	<td align="right"> 代表人手机号码 ：</td>
                <td align="left">
                	<input name="LRphoneNo" type="text" class="text" style="width: 150px;" >
                </td>
              </tr>
              <tr height="35">
            	<td align="right"> 联&nbsp;系&nbsp;人 ：</td>
                <td align="left">
                	<input name="contactMan" type="text" class="text" style="width: 150px;" maxlength="16">
                </td>
            	<td align="right"> 联系人手机号码 ：</td>
                <td align="left">
                	<input name="ContacterPhoneNo" type="text" class="text" style="width: 150px;" >
                </td>
              </tr>
              <tr height="35">
            	<td align="right"> 联系电话 ：</td>
                <td align="left">
                	<input name="phone" type="text" class="text" style="width: 150px;" onkeyup="this.value=this.value.replace(/\D/g,'')" maxlength="11">
                </td>
            	<td align="right"> 传&nbsp;&nbsp;&nbsp;&nbsp;真 ：</td>
                <td align="left">
                	<input name="fax" type="text" class="text" style="width: 150px;" onkeyup="this.value=this.value.replace(/\D/g,'')" maxlength="16">
                </td>
              </tr>
              <tr height="35">
            	<td align="right"> 业务联系人 ：</td>
                <td align="left">
                	<input name="businessContacter" type="text" class="text" style="width: 150px;" >
                </td>
            	<td align="right"> 企业性质 ：</td>
                <td align="left">
                	<input name="enterpriseKind" type="text" class="text" style="width: 150px;" >
                </td>
              </tr>
              <tr height="30">
                <td align="right"> EMail ：</td>
                <td align="left">
                	<input name="email" type="text" class="text" style="width: 150px;">
                </td> 
		         
		    <td align="right"> 类别 ：</td>
                <td align="left">
                  <select id="firmCategoryId" name="firmCategoryId" class="normal" style="width: 150px" reqfv="required;类别" >
                  <option value="0" >未分类</option>
				   <c:forEach items="${resultList}" var="result">
				   		<c:if test="${result.id !='0'}">
				   		<option value="${result.id}" >${result.name}</option>
				   		</c:if>
					</c:forEach>
	    		  <font color="#ff0000">*</font>
	    		  </select>
                </td>
		      
            	
              </tr>
               <tr height="35">
               <td align="right"> 订单手续费套餐 ：</td>
		         <td align="left" width="220">
		                
		         <select width="80" id="firmTID" name="tariffID">
		         <c:forEach items="${tariffList}" var="result">       
		         <option value="${result.tariffID}" <c:if test="${result.tariffID == user.tariffID }"><c:out value="selected"/></c:if>>${result.tariffName }</option>
		         </c:forEach>
		         </select>&nbsp;&nbsp;<span id="tariffview" name="tariffview" onclick='showTariff()'  style="color:#00008B;
		    text-decoration:underline;">查看套餐详细信息</span> </td>
		    <td colspan="2">&nbsp;</td>
               </tr>
               
                             <tr>
              	<td colspan="4">
              	  <fieldset width="100%" style="border-style:ridge;border-color:F3FDFC">
              	   <legend>录入银行信息</legend>
              	   <table border="0" cellspacing="0" cellpadding="0" width="100%" align="center">
		              <tr height="35">
		            	<td align="right"> 银&nbsp;&nbsp;&nbsp;&nbsp;行 ：</td>
		                <td align="left">
		                	<select name="bankId" onchange="bankChangeFun(this);">
		                	  <option value="">请选择</option>
		                	  <c:forEach items="${bankList}" var="bankMap">
		                		<option value='${bankMap["bankID"] }'>${bankMap["bankName"] }</option>
		                	  </c:forEach>
		                	  <option value="-1">其他</option>
		                	</select>
		                	<font color="#ff0000">*</font>
		                </td>
		            	<td align="right"> 账&nbsp;户&nbsp;名 ：</td>
		                <td align="left">
		                	<input name="accountName" type="text" class="text" style="width: 150px;" maxlength="64"/>&nbsp;<font color="#ff0000">*</font>
		                </td>
		              </tr>
		              <tr height="35">
		            	<td align="right"> 开户银行 ：</td>
		                <td align="left">
		                	<input name="bank" type="text" class="text" style="width: 150px;" maxlength="32">&nbsp;<font color="#ff0000">*</font>
		                </td>
		            	<td align="right"> 银行帐号 ：</td>
		                <td align="left">
		                	<input name="bankAccount" type="text" class="text" style="width: 150px;" maxlength="64" onkeyup="this.value=this.value.replace(/\D/g,'')">&nbsp;<font color="#ff0000">*</font>
		                </td>
		              </tr>
		              <tr height="35">
		            	<td align="right"> 开户行名称 ：</td>
		                <td align="left">
		                	<input name="bankName" type="text" class="text" style="width: 150px;" maxlength="64"/>
		                </td>
		            	<td align="right"> 开户行省份 ：</td>
		                <td align="left">
		                	<input name="bankProvince" type="text" class="text" style="width: 150px;" maxlength="64"/>
		                </td>
		              </tr>
		              <tr height="35">
		            	<td align="right"> 开户行所在市 ：</td>
		                <td align="left">
		                	<input name="bankCity" type="text" class="text" style="width: 150px;" maxlength="64"/>
		                </td>
		              </tr>
		           </table>
		           </fieldset>
                </td>
              </tr>
               
               
              <tr height="35">
                <td align="right"> 详细地址 ：</td>
                <td align="left" colspan="3">
                	<textarea name="address" cols="64" rows="3" ></textarea>
                </td>
              </tr>                  
              <tr height="35">
            	<td align="right">经营业务范围 ：</td>
                <td align="left" colspan="3">
                	<textarea name="businessScope" cols="64" rows="3" ></textarea>
                </td>
              </tr>              
              <tr height="35">
              <td align="right"> 备注 ：</td>
              <td align="left" colspan="3">
                <textarea name="note" cols="64" rows="3" ></textarea>
              </td>
              <tr height="35">
                <td align="right"> 权限 ：</td>
                <td align="left" colspan="3">
                    <c:set var="count" value="0"/>
                    <c:set var="add" value="1"/>
                	<c:forEach items="${moduleList}" var="result">
                	<c:if test="${count%5==0&&count>0}">
                	<br/>
                	</c:if>
        			<input type="checkbox" name="permissions" value="${result.moduleid}">${result.name}&nbsp;&nbsp;
        			<c:set var="count" value="${count+add}"/>
		        	</c:forEach>		        	
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
                <input type="hidden" name="zoneCode">
                <input type="hidden" name="industryCode">
        </form>
</body>
</html>
<%@ include file="../public/footInc.jsp" %>