<%@ include file="/timebargain/common/taglibs.jsp"%>

<%
	String firmID = (String)request.getAttribute("firmID");
	
%>
<%@ page pageEncoding="GBK"%>
<html>
<head>
<title>
TraderForm
</title>
<LINK REL="stylesheet" type="text/css" href="<c:url value="/timebargain/styles/common.css"/>"/>
<script language="JavaScript" src="<c:url value="/timebargain/scripts/global.js"/>"></script>

<script type="text/javascript">
<!--
//function window_onload()
//{
//    highlightFormElements();
//    roleForm.role_name.focus();
//}
//save
function save_onclick()
{
	if (confirm("您确定要提交吗？")) {
		if (traderForm.traderID != "") {
		 // if(!tmp_baseinfo_up)
   // {
   //   baseinfo_onclick();
   // }
    if(!tmp_paraminfo_up)
    {
      paraminfo_onclick();
    }
   // if(validateTraderForm(traderForm))
   // {alert("要取lbRight3");
    //	traderForm.save.disabled=true;
   // }
   // else
   // {alert("要取lbRight3");
    //	return false;
  //  }
   
    var permission = "";
    
    
    for(i=0; i < traderForm.lbRight.options.length; i++)
    {
    	
        permission += traderForm.lbRight.options[i].value + ",";
    }
    if(permission != "")
    {
      permission =  permission.substr(0, permission.length - 1) ;
    }
    traderForm.permission.value = permission;
    
    //setFunc_privilege();
    //alert(roleForm.func_privilege.value);
    traderForm.submit();
	}
	}
	
  
}

//cancel
function cancel_onclick()
{
	
    //document.location.href = "<c:url value="/baseinfo/trader.do?method=editCode"/>";
    //alert("返回");
    //window.history.back(-1);
    document.location.href = "<c:url value="/timebargain/baseinfo/trader.do?funcflg=search&firmID="/>" + traderForm.firmID.value;
}

//function funcList(id, module_name, func_no, func_name, selected)
//{
//  this.id = id;
//  this.module_name = module_name;
 // this.func_no = func_no;
 // this.func_name = func_name;
 // this.selected = selected;
 // this.toString = funcListToString;
 // this.funcCheckList = funcCheckList;
//}
//function funcListToString()
//{
//  return "id = " + this.id + "\n" +
//         "module_name = " + this.module_name + "\n" +
//         "func_no = " + this.func_no + "\n" +
//         "func_nmae = " + this.func_name + "\n" +
//         "selected = " + Boolean(this.selected);
//}

//the choice list of the function button
//function funcCheckList()
//{
//  if ( trim(this.func_name) == "")
//  {
//    return "";
 // }
//  else
//  {
//    return "<input type=\"checkbox\" name=\"func\" value=\"" + this.id + "\" " + (this.selected ? "checked" : "") + " class=\"NormalInput\" id=\"" + this.id + "\" onclick=\"selectFunc('" + this.id + "');\"/>" +
//           "<label for=\"" + this.id + "\" class=\"hand\">" + this.func_name + "</label><br>";
//  }
//}
//set function selected or no
//function selectFunc(id)
//{
 // for (var i = 0; i < funcArray.length; i++)
  //{
 //   if (funcArray[i].id == id)
 //   {
 //     funcArray[i].selected = !funcArray[i].selected;
 //     //alert(funcArray[i].selected);
 //   }
//  }
//}

//get the current user's function privilege to set permision  for role
//function getModuleFunc(Module)
//{
 // var str_debug = "";
 // var str = "";
 // for (var i = 0; i < funcArray.length; i++)
 // {
 //   if (funcArray[i].module_name == Module)
 //   {
 //     str_debug += funcArray[i].toString() + "\n\n";
 //     str += funcArray[i].funcCheckList();
  //  }
 // }
  //funcCheck.innerHTML = str;
  //alert(str_debug);
//}

//if role's module permission is remove 
//then the function privilege of the module remove too
//function clearModuleFunc(Module)
//{
//  for (var i = 0; i < funcArray.length; i++)
 // {
 //   if (funcArray[i].module_name == Module)
 //   {
 //     funcArray[i].selected = false;
 //   }
 // }	
// }
//function clearAllFunc()
//{
//  for (var i = 0; i < funcArray.length; i++)
//  {
//      funcArray[i].selected = false;
//  }	
//}

//function selected?
//function getFuncSelected(id)
//{
 // return (roleForm.func_privilege.value.substr(id-1,1) == "1") ? true : false;
//}

//set the role function selected when submit
//function setFunc_privilege()
//{
 // var privilege = roleForm.func_privilege.value;
 // for (var i = 0; i < funcArray.length; i++)
 // {
 //   privilege = replace(privilege, (funcArray[i].id - 1), funcArray[i].id, (funcArray[i].selected ? "1" : "0"));
 // }
 // roleForm.func_privilege.value = privilege;
//}
//function replace(src, i, j, dst)
//{
//  return src.substring(0, i) + dst + src.substr(j);
//}
//function test()
//{
//  setFunc_privilege();
 // alert(roleForm.func_privilege.value);
//}

//---------------------------start baseinfo-------------------------------

//-----------------------------end baseinfo-----------------------------
//---------------------------start paraminfo-------------------------------
var tmp_paraminfo;
var tmp_paraminfo_up = true;
function paraminfo_onclick()
{
  if (tmp_paraminfo_up)
  {
    tmp_paraminfo_up = false;
    traderForm.paraminfo_img.src = "<c:url value="/timebargain/images/ctl_detail_Down.gif"/>";
    tmp_paraminfo = paraminfo.innerHTML;
    paraminfo.innerHTML = "";
  }
  else
  {
    tmp_paraminfo_up = true;
    traderForm.paraminfo_img.src = "<c:url value="/timebargain/images/ctl_detail_Up.gif"/>";
    paraminfo.innerHTML = tmp_paraminfo;
  }
}
//-----------------------------end paraminfo-----------------------------
// -->
</script>
</head>


	<body leftmargin="0" topmargin="0" 
		onkeypress="keyEnter(event.keyCode);">
		<table border="0" height="400" width="770" align="center">
			<tr>
				<td>
					<html:form action="/timebargain/baseinfo/trader.do?funcflg=saveCode" method="post" styleClass="form">
						<fieldset class="pickList">
							<legend class="common">
								<b>可操作交易代码设置
								</b>
							</legend>

<table width="100%" border="0" align="center"  class="common" cellpadding="0" cellspacing="2">
<!-- 基本信息 -->
       				
        	
<!-- 菜单权限 -->
        <tr class="common">
          <td colspan="4">
            <fieldset class="pickList">
              <legend>
                <table cellspacing="0" cellpadding="0" border="0" width="100%" class="common">
                  <col width="55"></col><col></col><col width="6"></col>
                  <tr>
                    <td><b>设置信息</b></td>
                    <td><hr width="99%" class="pickList"/></td>
                    <td ><img id="paraminfo_img" src="<c:url value="/timebargain/images/ctl_detail_Up.gif"/>" style="cursor:hand" onclick="javascript:paraminfo_onclick()"/></td>
                  </tr>
                </table>
              </legend>
<span id="paraminfo">
<table cellSpacing="0" cellPadding="0" width="100%" border="0" align="center" valign="top" class="common">        												
		<tr>
		  <td class="common"  width="30%" >
		    <table border="0" class="common"  width="200">
		    <tr ><td align="center">未用代码</td></tr>
		    <tr><td >
		      <select  name="lbLeft" size="16" onDblClick="moveSelected(lbLeft,lbRight);" style="width : 100%" multiple>
		        <%=(String)request.getAttribute("codeNotChoose")%>
		      </select>
		    </td></tr>
		    </table>
		  </td>
		  <td align="center" valign="center"  width="15%">
		    <table border="0" class="common"  height="140" width="100" >
		      <tr><td align="center" valign="bottom" >
		        <input type="button" name="lbAdd"  value="  >   "  onclick="moveSelected(lbLeft,lbRight);" class="button" >
		        </td></tr>
		        <tr><td align="center" valign="top">
		          <input type="button" name="lbAddAll" value="  >>  "  onclick="return moveSelectedAll(lbLeft,lbRight);" class="button" >
		        </td></tr>
		        <tr><td align="center" valign="bottom" >
		          <input type="button" name="lbDel" value="  <   "  onclick="moveSelected(lbRight,lbLeft);" class="button" >
		        </td></tr>
		        <tr><td align="center" valign="top">
		          <input type="button" name="lbDelAll" value="  <<  "  onclick="moveSelectedAll(lbRight,lbLeft);" class="button" >
		        </td></tr>
		    </table>
		  </td>
		  <td class="common" width="30%">
		    <table border="0" class="common" width="200">
		    <tr><td class="common" align="center" >已用代码</td></tr>
		    <tr><td >
		      <select  name="lbRight" size="16" onDblClick="moveSelected(lbRight,lbLeft);"   style="width : 100%" multiple>
		        <%=(String)request.getAttribute("operateCode")%>
		      </select>
		    </td></tr>
		    </table>
		  </td>
		  
		</tr>																		
</table >
</span>
            </fieldset>
          </td>
        </tr>        							
								<tr>
									<td colspan="4" height="3">	
								</td>
								</tr>																																										
								<tr>
									<td colspan="4" align="center">
										<html:button property="save" styleClass="button"
											onclick="javascript:return save_onclick();">
											提交
										</html:button>
										<html:button property="cancel" styleClass="button"
											onclick="javascript:return cancel_onclick();">
											返回
										</html:button>
									</td>
								</tr>
							</table>
						</fieldset>
						<html:hidden property="traderID"/>
						<html:hidden property="permission"/>
						<html:hidden property="firmID" value="<%=firmID%>"/>
					</html:form>
				</td>
			</tr>
		</table>

<html:javascript formName="traderForm" cdata="false" dynamicJavascript="true" staticJavascript="false"/>
<script type="text/javascript" src="<html:rewrite page="/timebargain/common/validator.jsp"/>"></script>

<%@ include file="/timebargain/common/messages.jsp"%>
</body>
</html>

