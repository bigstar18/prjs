<%@ page contentType="text/html;charset=GBK" %>
<%@ page import='gnnt.MEBS.common.security.util.Configuration' %>
<%@ page import='gnnt.MEBS.member.firm.unit.*' %>
<%@ page import='java.util.Properties' %>
<html>
  <head>
    <%@ include file="../public/headInc.jsp" %>
    <%
      String marketId="";
	  try{
	     marketId=new Configuration().getSection("MEBS.key").getProperty("marketId");
	  }
	  catch(Exception e)
	  {
	     
	  }
      String firmId=request.getParameter("firmId");
      if(firmId!=null&&!"".equals(firmId.trim()))
      {
      pageContext.setAttribute("firmId", firmId);
      Firm firm = (Firm)request.getAttribute("firm");
      String permissions="";
		List listFm = firm.getFirmModule();
		
		if(listFm!=null&&listFm.size()>0)
		{
			for(int i=0;i<listFm.size();i++)
			{  
			  permissions+=","+((FirmModule)listFm.get(i)).getModuleId()+",";
			}
		}
	  pageContext.setAttribute("p", permissions);  
      }
    %>
    <%
    //Properties params=new Configuration().getSection("MEBS.Database");
    //String keyType=params.getProperty("");
    %>
    <IMPORT namespace="MEBS" implementation="<%=basePathF%>/public/jstools/calendar.htc">
	<title>创建交易员</title>
	<script language="javascript" src="<%=basePathF%>/public/jstools/common.js"></script>
	<script language="javascript" src="<%=basePathF%>/public/jstools/tools.js"></script>
 
	<script> 
		function doSubmit()
		{ 
			if(!checkValue("formNew"))
				return;
			if(formNew.enableKey.value=='Y'&&formNew.keyCode.value=='')
			{
			  alert("key未填写");
			  return;
			}
			if(formNew.password.value!=formNew.passwords.value)
			{
			   alert("密码和确认密码不一致");
			   return;
			}
			var p=formNew.p.value;
			var allCheckbox=formNew.permissions; 
            if(allCheckbox.length)
            {
				for (var i=0;i<allCheckbox.length;i++){ 
				    var a=','+allCheckbox[i].value+',';
					if(allCheckbox[i].checked==true&&p.indexOf(a)<0)
					{
					   alert("所属交易商不存在权限:"+allCheckbox[i].abbr);
					   return;
					   break;
					}
				}
		    }
			else
			{
			  var a=','+allCheckbox.value+',';
				if(allCheckbox.checked==true&&p.indexOf(a)<0)
				{
				   alert("所属交易商不存在权限:"+allCheckbox.abbr);
				   return;
				}
			} 
			formNew.submit(); 
		}
		function onSelect(value)
		{
		   if(value=='Y')
		   {
		     formNew.keyCode.disabled=false;
		     formNew.keyCode.value='';
		     formNew.keyCode.className="text";
		     formNew.key.disabled=false;
		   }
		   else
		   {
		     formNew.keyCode.disabled=true;
		     formNew.keyCode.value='';
		     formNew.keyCode.className="readonly";
		     formNew.key.disabled=true;
		   }
		}
		
		function setSign(value,permissions)
		{
		  if(value=='true')
		  {
		     formNew.t_firmId.value=formNew.firmId.value;
		     formNew.smallCount.disabled=false;
		     formNew.smallCount.value='';
		     formNew.smallCount.className="text";
		     formNew.smallCount.focus();
		     formNew.p.value=permissions;
		  }
		  else if(value=='false')
		  {
		    alert("不存在<%=FIRMID%>！");
		    formNew.firmId.value='';
		    formNew.smallCount.disabled=true;
		    formNew.smallCount.value='';
		    formNew.smallCount.className="readonly";
		  }
		  else
		  {
		    formNew.firmId.value='';
		    formNew.smallCount.disabled=true;
		    formNew.smallCount.value='';
		    formNew.smallCount.className="readonly";
		  }
	    }
	    
	    var req = new ActiveXObject("Microsoft.XMLHTTP");
	    var sign = '';
	    var permissions='';
	    function checkFirm( ){
	        var vCode =formNew.firmId.value;
	    	if(vCode != null && vCode.length > 0){
		    	sign = '';
		    	vNewCode = codeFillZero( vCode );
		    	url = basePath + '/firmController.mem?funcflg=getFirmAjax&firmId='+vNewCode;
		    	if (req) {
		            req.onreadystatechange = processReqChange;
		            req.open("POST", url, false);
		            req.send();
		            req.abort();
	        	}
	        	setSign(sign,permissions);
        	}
	    }
	    
	    
	     function codeFillZero( vCode) {
	    	return trim(vCode);
	    }
	    
	    
	    
	    function processReqChange() {
		    // only if req shows "loaded"
		    if (req.readyState == 4) {
		        // only if "OK"
		        if (req.status == 200) {
		            sign = trim(req.responseText);
		            var arr = sign.split( "|||" );
		            	sign = arr[0];
		            	permissions = arr[1];
		        } else {
		            alert("不存在<%=FIRMID%>！");
		            formNew.firmId.value='';
		            formNew.smallCount.disabled=true;
        		    formNew.smallCount.value='';
		            formNew.smallCount.className="readonly";
		        }
		    }
		}
		
		function processReqChange1() {
		    // only if req shows "loaded"
		    if (req.readyState == 4) {
		        // only if "OK"
		        if (req.status == 200) {
		            sign = trim(req.responseText);
		        } else {
		            alert("已存在小号！");
        		    formNew.smallCount.disabled=false;
		            formNew.smallCount.value='';
		            formNew.smallCount.className="text";
		            formNew.smallCount.focus();
		        }
		    }
		}
		
		function checkTrader(vCode)
		{
		  if(vCode != null && vCode.length > 0){
		    	sign = '';
		    	vNewCode = codeFillZero( vCode );
		    	var firmId=formNew.firmId.value;
		    	vNewCode=firmId+vNewCode;
		    	url = basePath + '/firmController.mem?funcflg=getTraderAjax&traderId='+vNewCode;
		    	if (req) {
		            req.onreadystatechange = processReqChange1;
		            req.open("POST", url, false);
		            req.send();
		            req.abort();
	        	}
	        	setTraderSign(sign);
        	}
		}
		
		function setTraderSign(value)
		{
		  if(value=='true')
		  {
		  }
		  else if(value=='false')
		  {
		     alert("已存在小号！");
		     formNew.smallCount.disabled=false;
		     formNew.smallCount.value='';
		     formNew.smallCount.className="text";
		     formNew.smallCount.focus();
		  }
		  else
		  {
		     formNew.smallCount.disabled=false;
		     formNew.smallCount.value='';
		     formNew.smallCount.className="text";
		     formNew.smallCount.focus();
		  }
		 }
		 function addKey()
		 {
		     var str1 = "";
	         var ifInstalled = true;
	         var errorCode = 0;
	         var traderId=formNew.firmId.value+formNew.smallCount.value;
			try
			{
			
				str1 = ePass.VerifyUser(<%=marketId%>,traderId);	
			}
			catch(err)
			{
				ifInstalled = false;														
			}	
			  if(isNaN(str1))
			  {
			  }
			  else
			  {
			    ifInstalled = true;
			    errorCode = parseInt(str1);
			    if(errorCode==-10)
			    {
			      alert("USB身份验证盘驱动程序错误！");	
			    }else if (errorCode==-1)
			    {
			      alert("请插入USB身份验证盘！");	
			    }else if (errorCode==-2)
			    {
			      alert("非法USB身份验证盘！");	
			    }else if (errorCode==-3)
			    {
			      alert("USB身份验证盘不正确");	
			    }else if (errorCode==-4)
			    {
			      alert("USB身份验证盘已经损坏，请联系发放者！");	
			    }
			  }
			if(!ifInstalled)
			{
				alert("请安装交易控件以正常绑定！");	
				return false;	
			}
			else
			{	   
			      if(errorCode==0)
			       {
					    formNew.keyCode.value = str1;	
					}else
					{
					    return false;	
					}			
			}
		 }
	</script> 
</head>
<body>
        <form id="formNew" action="<%=basePath%>/firmController.mem?funcflg=traderAdd" method="POST" targetType="hidden" callback="closeDialog(1);">
		<fieldset width="100%">
		<legend>交易员基本信息</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
			  <tr height="35">
                <td align="right"> 归属<%=FIRMID%>：</td>
                <td align="left">
                	<input class="normal" name="firmId" id="firmId" type="text" style="width: 100px;" reqfv="required;归属交易商代码" onblur="checkFirm()" value="${firmId}" onkeypress="onlyNumberAndCharInput()" maxlength="16">&nbsp;<font color="#ff0000">*</font>
                	<input type="hidden" name="p" value="${p}">
                </td>
            	<td align="right"> <%=TRADERID%> ：</td>
                <td align="left">
                    <input name="t_firmId" type="text" class="readonly" style="width: 100px;" disabled="true" value="${firmId}">&nbsp;&nbsp;<input name="smallCount" type="text"  <c:if test="${not empty firmId}">class="text"</c:if><c:if test="${empty firmId}">class="readonly"</c:if> style="width: 20px;" reqfv="required;小号" maxlength="1" <c:if test="${not empty firmId}"></c:if><c:if test="${empty firmId}">disabled="true"</c:if> onblur="checkTrader(this.value)" onkeyup="value=value.replace(/[\W]/g,'') "onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))">
                    &nbsp;<font color="#ff0000">*</font>
                </td>
              </tr>
              <tr height="35">
                <td align="right"> 密码 ：</td>
                <td align="left">
                	<input class="normal" type="password" name="password"  id="password" style="width: 100px;" reqfv="required;密码" onkeypress="onlyNumberAndCharInput()" maxlength="16">&nbsp;<font color="#ff0000">*</font>
                </td>
                <td align="right"> 确认密码 ：</td>
                <td align="left">
                	<input class="normal" type="password" name="passwords" id="passwords" style="width: 100px;" reqfv="required;确认密码" onkeypress="onlyNumberAndCharInput()" maxlength="16">&nbsp;<font color="#ff0000">*</font>
                </td>
              </tr>
              <tr height="35">
            	<td align="right"> 交易员名称 ：</td>
                <td align="left">
                	<input name="name" type="text" class="text" style="width: 100px;" reqfv="required;交易员名称" onkeypress="onlyNumberAndCharInput()" maxlength="16">&nbsp;<font color="#ff0000">*</font>
                </td>
            	<td align="right"> 类别 ：</td>
                <td align="left">
                	<select id="type" name="type" class="normal" style="width: 100px" reqfv="required;类别">
							<OPTION value=""></OPTION>
							<option value="A">公司管理员</option>
              				<option value="N">普通交易员</option>
				    </select>
				    &nbsp;<font color="#ff0000">*</font>
                </td>
              </tr>
              <tr height="35">
            	<td align="right"> 是否启用key ：</td>
                <td align="left">
                	<input type="radio" name="enableKey" value="Y" onClick="onSelect(this.value)">启用<input type="radio" name="enableKey" value="N" checked="checked" onClick="onSelect(this.value)">不启用
                </td>
            	<td align="right"> key ：</td>
                <td align="left">
                    <input ytpe="text" name="keyCode" class="readonly" style="width: 100px;" disabled="true"><button class="smlbtn" name="key" type="button" onclick="addKey()" disabled="true">绑定key</button>
                </td>
              </tr>
              <tr height="35">
                <td align="right"> 权限 ：</td>
                <td align="left" colspan="3">
                    <c:set var="count" value="0"/>
                    <c:set var="add" value="1"/>
                	<c:forEach items="${moduleList}" var="result">
                	<c:if test="${count%5==0&&count>0}">
                	<br/>
                	</c:if>
        			<input type="checkbox" name="permissions" value="${result.moduleid}" abbr="${result.name}">${result.name}&nbsp;&nbsp;
        			<c:set var="count" value="${count+add}"/>
		        	</c:forEach>
		        	
                </td>
              </tr>
              <tr height="35">
                <td colspan="4"><div align="center">
                  <button class="smlbtn" type="button" onclick="doSubmit();">提交</button>&nbsp;
      			  <button class="smlbtn" type="button" onclick="window.close()">关闭窗口</button>
                </div></td>
              </tr>
          </table>
		</fieldset>
        </form>
        <OBJECT classid=clsid:0023145A-18C6-40C7-9C99-1DB6C3288C3A id="ePass" 
         STYLE="LEFT: 0px; TOP: 0px" width=0 height=0
         CODEBASE="GnntKey.cab#Version=1,0,0,5">
        </OBJECT>
</body>
</html>
<%@ include file="../public/footInc.jsp" %>