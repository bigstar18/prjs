<%@ page contentType="text/html;charset=GBK" %>
<%@ page import='gnnt.MEBS.member.firm.unit.*' %>
<%@ page import="gnnt.MEBS.common.security.util.Configuration"%>
<%
    String marketId="";
	  try{
	     marketId=new Configuration().getSection("MEBS.key").getProperty("marketId");
	  }
	  catch(Exception e)
	  {
	     
	  }
	Trader trader = null;
	String permissions="";
	trader = (Trader)request.getAttribute("trader");

	List listFm = trader.getTraderModule();
	
	if(listFm!=null&&listFm.size()>0)
	{
		for(int i=0;i<listFm.size();i++)
		{  
		  permissions+=","+((TraderModule)listFm.get(i)).getModuleId()+",";
		}
	}
	pageContext.setAttribute("user", trader);
	
	Firm firm = (Firm)request.getAttribute("firm");
	
	
	String permissions1="";
	List listFm1 = firm.getFirmModule();
	
	if(listFm1!=null&&listFm1.size()>0)
	{
		for(int i=0;i<listFm1.size();i++)
		{  
		  permissions1+=","+((FirmModule)listFm1.get(i)).getModuleId()+",";
		}
	}
	
	pageContext.setAttribute("p", permissions1);
	List list=(List)request.getAttribute("moduleList");
    pageContext.setAttribute("moduleList", list);
%> 
<html  xmlns:MEBS>  
  <head>
    <%@ include file="../public/headInc.jsp" %> 
    <IMPORT namespace="MEBS" implementation="<%=basePathF%>/public/jstools/calendar.htc">
	<title>修改交易员</title>
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
		function addKey()
		 {
		     var str1 = "";
	         var ifInstalled = true;
	         var traderId=formNew.traderId.value;
	         var errorCode = 0;
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
        <form id="formNew" action="<%=basePath%>/firmController.mem?funcflg=traderMod" method="POST" targetType="hidden" callback="closeDialog(1);">
		<fieldset width="100%">
		<legend>交易员基本信息</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
			  <tr height="35">
                <td align="right"> <%=TRADERID%> ：</td>
                <td align="left">
                    <input type="hidden" name="p" value="${p} }">
                	<input class="readonly" id="traderId" name="traderId" value="<c:out value='${user.traderId}'/>" style="width: 100px;" reqfv="required;交易员ID" readonly>&nbsp;<font color="#ff0000">*</font>
                </td>
                <td align="right"> 归属<%=FIRMID%> ：</td>
                <td align="left">
                	<input class="readonly" name="firmId" id="firmId" type="text" style="width: 100px;" reqfv="required;交易商ID" value="<c:out value='${user.firmId}'/>" readonly>&nbsp;<font color="#ff0000">*</font>
                </td>
              </tr>
              <tr height="35">
            	<td align="right"> 交易员名称 ：</td>
                <td align="left">
                	<input name="name" type="text" value="<c:out value='${user.name}'/>" class="text" style="width: 100px;" reqfv="required;交易员名称" onkeypress="onlyNumberAndCharInput()" maxlength="16">&nbsp;<font color="#ff0000">*</font>
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
              <script>
				formNew.type.value = "<c:out value='${user.type}'/>"
			  </script>
              <tr height="35">
            	<td align="right"> 是否启用key ：</td>
                <td align="left">
                	<input type="radio" name="enableKey" value="Y" onClick="onSelect(this.value)" <c:if test="${user.enableKey=='Y'}">checked</c:if>>启用<input type="radio" name="enableKey" value="N"  onClick="onSelect(this.value)" <c:if test="${user.enableKey=='N'}">checked</c:if>>不启用
                </td>
            	<td align="right"> key ：</td>
                <td align="left">
                    <input type="text" name="keyCode" style="width: 100px;" <c:if test="${user.enableKey=='N'}">disabled="true" class="readonly"</c:if><c:if test="${user.enableKey=='Y'}"> class="text"</c:if>  value="<c:out value='${user.keyCode}'/>"><button class="smlbtn" name="key" type="button" onclick="addKey()" <c:if test="${user.enableKey=='N'}"> disabled="true"</c:if>>绑定key</button>
                </td>
              </tr>
              <tr height="35">
                <td align="right"> 权限 ：</td>
                <td align="left" colspan="3">
                    <c:set var="count" value="0"/>
                    <c:set var="add" value="1"/>
                	<c:forEach items="${moduleList}" var="result">
        			<input type="checkbox" name="permissions" value="${result.moduleid}" abbr="${result.name}">${result.name}&nbsp;&nbsp;
        			<c:set var="count" value="${count+add}"/>
		        	</c:forEach>
		        	<script>
                    var allCheckbox=formNew.permissions; 
                    if(allCheckbox.length) {
						for (var i=0;i<allCheckbox.length;i++){ 
							if (allCheckbox[i].type=="checkbox") 
					    		var a=','+allCheckbox[i].value+',';
							if('<%=permissions%>'.indexOf(a)>=0) {
								allCheckbox[i].checked="true"; 
							}
						}
					} else {
					  	var a=','+allCheckbox.value+',';
						if('<%=permissions%>'.indexOf(a)>=0) {
							allCheckbox.checked="true"; 
						}
					} 
					</script>
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
        <!-- <OBJECT classid=clsid:0023145A-18C6-40C7-9C99-1DB6C3288C3A id="ePass" 
         STYLE="LEFT: 0px; TOP: 0px" width=0 height=0
         CODEBASE="GnntKey.cab#Version=1,0,0,5">
         </OBJECT> -->
</body>
</html>
<%@ include file="../public/footInc.jsp" %>