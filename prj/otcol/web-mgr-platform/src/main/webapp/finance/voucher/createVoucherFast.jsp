<%@ page contentType="text/html;charset=GBK" %>
<%@ page import='java.util.List'%>
<%@ page import='java.util.Map'%>
<%
	List list = (List)request.getAttribute("list");
 %>
<html>
  <head> 
    <%@ include file="../public/headInc.jsp" %> 
	<title>快捷录入</title>
	<script language="javascript" src="<%=basePath%>/public/jstools/common.js"></script>
	<script language="javascript" src="<%=basePath%>/public/jstools/tools.js"></script>
	<script language="javascript" src="<%=basePath%>/voucher/voucher.js"></script>
	<script>
		function createVoucher(){
		    if(formNew.type.value==-1)
		    {
		      alert("类型必须填写");
		      return;
		    }
		   var debitCode=formNew.debitCode.value;
		   var creditCode=formNew.creditCode.value;
		   var money=formNew.money.value;
		   if(debitCode.indexOf('-')>=0||debitCode.indexOf('*')>=0||creditCode.indexOf('-')>=0||creditCode.indexOf('*')>0)
		   {
		     alert("借方科目或贷方科目还有交易商代码或商品代码没有填写");
		     return;
		   }
		   if(money==''||isNaN(money))
		   {
		     alert("金额没有填写或者不是数字");
		     return;
		   }
			if(confirm("确定生成凭证"))
			{
				disableBtn();
				formNew.submit();
			}
		} 
		
		function disableBtn(){
			formNew.submitBtn.disabled = true;
		}
		function change(){
		         if(formNew.type.value==-1)
		         {
         		   formNew.reset();
         		   formNew.firmId.disabled=false;
				   formNew.contractNo.disabled=false;
				   formNew.commodityId.disabled=false;
		         }
                 <%
                 try{
                    if(list!=null&&list.size()>0)
				      {
				       for(int i=0;i<list.size();i++)
				       {
				        String code=(String)((Map)list.get(i)).get("code");
				        String debitCode=(String)((Map)list.get(i)).get("debitCode");
				        String creditCode=(String)((Map)list.get(i)).get("creditCode");
				        String needContractNo=(String)((Map)list.get(i)).get("needContractNo");
				        String summaryNo=(String)((Map)list.get(i)).get("summaryNo");
				        String summary=(String)((Map)list.get(i)).get("summary");
				        boolean firmIdSign=true;
				        if(debitCode.indexOf("-")>-1||creditCode.indexOf("-")>-1)
				        {
				             firmIdSign=false;
				        }
				        boolean commoditySign=true;
				        if(debitCode.indexOf("*")>-1||creditCode.indexOf("*")>-1)
				        {
				             commoditySign=false;
				        }
				        boolean contract=false;
				        if("N".equals(needContractNo))
				        { 
				             contract=true;
				        }
				        %>
				          if(formNew.type.value==<%=code%>)
				          {
				            formNew.firmId.disabled=false;
				            formNew.contractNo.disabled=false;
				            formNew.commodityId.disabled=false;
		         		    formNew.reset();
		         		    formNew.type.value=<%=code%>
				            formNew.debitCode.value="<%=debitCode%>";
				            formNew.creditCode.value="<%=creditCode%>";
				            formNew.debitCodeOld.value="<%=debitCode%>";
				            formNew.creditCodeOld.value="<%=creditCode%>";
				            formNew.summaryNo.value="<%=summaryNo%>";
				            formNew.summary.value="<%=summary%>";
				            
				            if(<%=firmIdSign%>)
				            {
				              formNew.firmId.value="无";
				              formNew.firmId.disabled=true;
				            }
				            if(<%=commoditySign%>)
				            {
				              formNew.commodityId.value="无";
				              formNew.commodityId.disabled=true;
				            }
				            if(<%=contract%>)
				            {
				              formNew.contractNo.value="无";
				              formNew.contractNo.disabled=true;
				            }
				            changeAccountCodeFast('<%=debitCode%>','debitCodeName');
				            changeAccountCodeFast('<%=creditCode%>','creditCodeName');
				          }
				          
				        <%
				       }
				      }
				      }catch(Exception e)
				      {
				        out.println(e);
				      }
                 %>
		}
		
		function changeAccountCodeFast( vCode,fieldName ){
	        if(!(vCode.indexOf('-')>=0||vCode.indexOf('*')>=0))
	        {
	    	if(vCode != null && vCode.length > 0){
		    	vAccountName = '';
		    	vNewCode = codeFillZero( vCode );
		    	url = basePath + '/accountController.spr?funcflg=getAccountName&code='+vNewCode;
		    	if (req) {
		            req.onreadystatechange = processReqChange;
		            req.open("GET", url, false);
		            req.send();
		            req.abort();
	        	}
	        	
	        	setAccountNameFast(vCode,vNewCode,vAccountName,fieldName);
        	}
        	}
	    }
	    
	    
	     function setAccountNameFast(vCode,vNewCode,vName,fieldName)
		{
		 document.getElementById(fieldName).value=vName;
	    }
		
		// 修改了change1和change1函数,解决借方/贷方科目代码显示的问题 2011-3-10 by feijl
		function change1(){
		   var firmId=formNew.firmId.value;
		   var debitCode=formNew.debitCodeOld.value;
		   var creditCode=formNew.creditCodeOld.value;
		   var debitCode1;
		   var creditCode1;
			if (debitCode.indexOf('-')>=0) {
				debitCode1=debitCode.replace('-',firmId);
				formNew.debitCode.value=debitCode1;
				changeAccountCodeFast(debitCode1,'debitCodeName');
				if (formNew.debitCodeName.value=='') {
						formNew.debitCode.value=debitCode;
				}
			}
			if (creditCode.indexOf('-')>=0) {
				creditCode1=creditCode.replace('-',firmId);
				formNew.creditCode.value=creditCode1;
				changeAccountCodeFast(creditCode1,'creditCodeName');
				if (formNew.creditCodeName.value=='') {
						formNew.creditCode.value=creditCode;
				}
			}
		}
		function change2(){
		   var commodityId=formNew.commodityId.value;
		   var debitCode=formNew.debitCodeOld.value;
		   var creditCode=formNew.creditCodeOld.value;
		   var debitCode1;
		   var creditCode1;
			if (debitCode.indexOf('*')>=0) {
				debitCode1=debitCode.replace('*',commodityId);
				formNew.debitCode.value=debitCode1;
				changeAccountCodeFast(debitCode1,'debitCodeName');
				if (formNew.debitCodeName.value=='') {
						formNew.debitCode.value=debitCode;
				}
			}
			if (creditCode.indexOf('*')>=0) {
				creditCode1=creditCode.replace('*',commodityId);
				formNew.creditCode.value=creditCode1;
				changeAccountCodeFast(creditCode1,'creditCodeName');
				if (formNew.creditCodeName.value=='') {
						formNew.creditCode.value=creditCode;
				}
			}
		}
	</script> 
</head>
<body>
     <form id="formNew" action="<%=basePath%>/voucherController.spr?funcflg=fastVoucher" method="POST">
		<fieldset width="100%">
		<legend>快捷录入</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" height="350">
			  <tr height="35">
			  	<td align="right">类型：</td>
                <td align="left">
               <select name="type" onchange="change()">
                 <option value="-1">请选择</option>
            	 <c:forEach items="${list}" var="result">
                 <option value="${result.code}">${result.name}</option>
                 </c:forEach>
            	 </select>
                </td>
              </tr>
               <tr height="25">
			  	<td align="right"><%=SUMMARYNO%>：</td>
                <td align="left">
                <input type="text" name="summaryNo" class="readonly" style="width: 150px;" id="summaryNo" value="" readonly="true">
                </td>
              </tr>
              <tr height="25">
			  	<td align="right"><%=DUMMARYNAME%>：</td>
                <td align="left">
                <input type="text" name="summary" class="readonly" style="width: 150px;" id="summary" value="" readonly="true">
                </td>
              </tr>
              <tr height="25">
			  	<td align="right">借方<%=BITCODE%>：</td>
                <td align="left">
                <input type="text" name="debitCode" class="readonly" style="width: 150px;" id="debitCode" value="" readonly="true" >
                <input type="hidden" name="debitCodeOld" id="debitCodeOld" value="" >
                </td>
              </tr>
               <tr height="25">
			  	<td align="right">借方<%=BITCODENAME%>：</td>
                <td align="left">
                <input type="text" name="debitCodeName" class="readonly" style="width: 150px;" id="debitCodeName" value="" readonly="true">
                </td>
              </tr>
              <tr height="25">
			  	<td align="right">贷方<%=BITCODE%>：</td>
                <td align="left"><input type="text" name="creditCode" class="readonly" style="width: 150px;" id="creditCode" value="" readonly="true">
                <input type="hidden" name="creditCodeOld" id="creditCodeOld" value="" >
                </td>
              </tr>
              <tr height="25">
			  	<td align="right">贷方<%=BITCODENAME%>：</td>
                <td align="left"><input type="text" name="creditCodeName" class="readonly" style="width: 150px;" id="creditCodeName" value="" readonly="true">
                </td>
              </tr>
              <tr height="25">
			  	<td align="right"><%=FIRMID%>：</td>
                <td align="left"><input type="text" name="firmId" id="firmId" value=""  style="width: 150px;" onchange="change1()" onkeypress="onlyNumberAndCharInput()" maxlength="16">
                </td>
              </tr>
              <tr height="25">
			  	<td align="right"><%=COMMODITYID%>：</td>
                <td align="left"><input type="text" name="commodityId" id="commodityId" value="" onchange="change2()" style="width: 150px;" onkeypress="onlyNumberAndCharInput()" maxlength="16">
                </td>
              </tr>
              <tr height="25">
			  	<td align="right"><%=CONTRACTNO%>：</td>
                <td align="left"><input type="text" name="contractNo" id="contractNo" value="" style="width: 150px;" onkeypress="onlyNumberAndCharInput()" maxlength="32">
                </td>
              </tr>
              <tr height="25">
			  	<td align="right">金额：</td>
                <td align="left"><input type="text" name="money" value="" style="width: 150px;" onkeypress="onlyNumberInput()" maxlength="16">
                </td>
              </tr>
               <tr height="30">
                    <td align="right">&nbsp;</td>
                 <td align="left">
                  &nbsp;
                </td>
              </tr>
              <tr height="30">
                    <td align="right">&nbsp;</td>
                 <td align="left">
                  <button id="submitBtn" class="lgrbtn" type="button" onclick="createVoucher();">生成凭证</button>&nbsp;
                </td>
              </tr>
          </table>
		</fieldset>
    </form>
</body>
</html>
<%@ include file="../public/footInc.jsp" %>