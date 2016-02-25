<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK"%>
<%
	String weeks = (String)request.getAttribute("weeks");
%>
<html>
	<head>
		<LINK REL="stylesheet" type="text/css"
			href="<c:url value="/timebargain/styles/common.css"/>" />
		<script language="JavaScript"
			src="<c:url value="/timebargain/scripts/global.js"/>"></script>
		<script language="JavaScript" src="<c:url value="/timebargain/scripts/open.js"/>"></script>
		<script language="VBScript" src="<c:url value="/timebargain/scripts/vbfunction.vbs"/>"></script>
		<title>
		</title>
		<style type="text/css">
<!--
.yin {
	visibility:hidden;
	position:absolute;
	
}
.xian{
	visibility:visible;
}
-->
</style>
		<script language="JavaScript"> 
function window_onload()
{
    highlightFormElements();
   	//var relWeeks = new Array();
    
    var relWeek = "<%=request.getAttribute("weeks")%>";
    //alert(relWeek);
    var relWeeks = relWeek.split(",");
	var weeks = document.forms(0).week;
	for (j = 0; j < relWeeks.length; j++) {
	//alert(relWeeks.length);
	//alert(relWeeks[j]);
		for (i = 0; i < weeks.length; i++) {
			if (relWeeks[j] == weeks[i].value) {
			
				weeks[i].checked = true;
			}
		}
	}
	
}



//save111111
function save_onclick()
{
	if (confirm("您确定要提交吗？")) {
	var is = date();
	//alert(is);
	//alert(document.forms(0).day.value);
	if (document.forms(0).day.value != "") {
		if (!is) {
		return false;
		}
	}
	
	
    document.forms(0).submit();
    document.forms(0).save.disabled = true;
   }
    
}

    
  function date(){  
  	if (document.forms(0).day.value != "") {
  		var relDay0 = document.forms(0).day.value;
  		
  		var relDays0 = relDay0.replace(/\r/ig,"").replace(/\n/ig,"");
  		var days = relDays0.split(",");
  		for (i = 0; i < days.length; i++) {
  			ymd1 = days[i].split("-");
  			month1=ymd1[1]-1   
    		var Date1 = new Date(ymd1[0],month1,ymd1[2]);     
    		//alert(ymd1[0].length);
    		//alert(ymd1[1].length);
    		//alert(ymd1[2].length);
    		//alert(Date1.getMonth()+1);
  			if (Date1.getMonth()+1!=ymd1[1] || Date1.getDate()!=ymd1[2] || Date1.getFullYear()!=ymd1[0] || ymd1[0].length!=4 || ymd1[1].length!=2 || ymd1[2].length!=2){   
           		//alert(ymd1[0]);
           		//alert(ymd1[1]);
           		//alert(ymd1[2]);
           		alert("非法日期,请依【YYYY-MM-DD】格式输入");   
           		document.forms(0).day.focus();   
           		return false;                       
    		}
    		
  		}
  		
  		for (j = 0; j < days.length; j++) {
  			for (k = 0; k < days.length; k++) {
  				var relDays = days[j];
  				var relDays2 = days[k];
  				if (j != k) {
  					if (relDays == relDays2) {
  						alert("不准输入相同日期！");
  						document.forms(0).day.focus();
  						return false;
  					}
  				}
  			}
  		}
  		//alert(relDays0);
  		document.forms(0).day.value = relDays0;
  		alert("操作成功！");   
  		return   true;
	//ymd1= document.forms(0).day.value.split("-");   
  	}else {
  		alert("操作成功！");
  		return   true;
  	}
  }   



function suffixNamePress()
{
	
  if (marketForm.addedTax.value < 1 && (event.keyCode>=46 && event.keyCode<=57) )  //|| (event.keyCode>=65 && event.keyCode<=90) || (event.keyCode>=97 && event.keyCode<=122)
  {
    event.returnValue=true;
  }
  else
  {
    event.returnValue=false;
  }
}

function type_onclick(){
	pTop("<c:url value="/timebargain/baseinfo/daySection_middle.jsp?weeks="/>" + "<%=weeks%>" + "&timeStamp=" + new Date(), 900,600);
	//pTop("<c:url value="/timebargain/baseinfo/tradeTime.do?funcflg=editDaySection&weeks="/>" + "<%=weeks%>" + "&timeStamp=" + new Date(), 900,600);
}


//---------------------------start baseinfo-------------------------------

//-----------------------------end baseinfo-----------------------------
//---------------------------start paraminfo-------------------------------

//-----------------------------end paraminfo-----------------------------
</script>
	</head>

	<body onLoad="return window_onload()"
		onkeypress="keyEnter(event.keyCode);">
		<table border="0" height="400" width="600" align="center">
			<tr>
				<td>
					<html:form action="/timebargain/baseinfo/tradeTime.do?funcflg=saveNotTradeDay"
						method="POST" styleClass="form">
						<fieldset class="pickList">
							<legend class="common">
								<b class="req">非交易日设置
								</b>
							</legend>

<table width="100%" border="0" align="center"  class="common" cellpadding="0" cellspacing="2">
<!-- 基本信息 -->
        <tr class="common">
          <td colspan="4">
            
              
<span id="baseinfo">
<table cellSpacing="4" cellPadding="4" width="100%" border="0" align="center" class="common">
									
								
								<tr>
									<td align="right" width="118">
											非交易星期：
									</td>
									
									<td align="left" width="460">
									
										星期一<html:checkbox property="week" value="2" styleClass="NormalInput"/>
										星期二<html:checkbox property="week" value="3" styleClass="NormalInput"/>
										星期三<html:checkbox property="week" value="4" styleClass="NormalInput"/>
										星期四<html:checkbox property="week" value="5" styleClass="NormalInput"/>
										星期五<html:checkbox property="week" value="6" styleClass="NormalInput"/>
										星期六<html:checkbox property="week" value="7" styleClass="NormalInput"/>
										星期日<html:checkbox property="week" value="1" styleClass="NormalInput"/>
										
									</td>
								</tr>
								
								

													
</table >
</span>
            
          </td>
        </tr>					
        	
<!-- 参数信息 -->
        <tr class="common">
          <td colspan="4">
            
<span id="paraminfo">
<table cellSpacing="3" cellPadding="3" width="100%" border="0" align="center" class="common">    
						<tr>
						<td align="right" width="118">
							非交易日期：
						</td>
						<td>
							<html:textarea property="day" rows="5" cols="55" onblur="return date()" style="width:428" styleClass="text" />
						</td>
						</tr>
						<tr>
						<td></td>
						<td >逗号分隔的日期，<b class="req">(逗号必须为英文状态下半角)</b></td>
						</tr>
						<tr>
						<td></td>
						<td>例如 ：2008-10-01,2008-10-02,2008-10-03</td>
						</tr>
																							
</table >
</span>
            
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
										<c:if test="${tradeTimeForm.tradeTimeType == '1'}">
											<html:button property="timeType" styleClass="button"
													onclick="type_onclick();">
													设置交易节
												</html:button>
										</c:if>
									</td>
								</tr>
							</table>
						</fieldset>
						<html:hidden property="crud" />
						<html:hidden property="id" />
					</html:form>
				</td>
			</tr>
		</table>

		
		<script language="javascript"
			src="<html:rewrite page="/timebargain/common/validator.jsp"/>"></script>
		<%@ include file="/timebargain/common/messages.jsp"%>
	</body>
</html>
