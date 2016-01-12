<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<%
	String weeks = (String)request.getAttribute("weeks");
%>
<html>
	<head>
		<title>非交易日设置</title>
		<link rel="stylesheet" href="${skinPath }/css/validationengine/validationEngine.jquery.css" type="text/css" />
		<link rel="stylesheet" href="${skinPath }/css/validationengine/template.css" type="text/css" />
		<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
		<script src="${publicPath }/js/validationengine/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="GBK"></script>
		<script src="${publicPath }/js/validationengine/jquery.validationEngine.js" type="text/javascript" charset="GBK"></script>
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

		$(document).ready(function() {
	    	jQuery("#frm").validationEngine('attach');
			//修改按钮注册点击事件
			$("#update").click(function(){
				//验证信息
				if(jQuery("#frm").validationEngine('validateform')){
					var flag = false;
					flag = save_onclick();
					if (flag) {
						var vaild = affirm("您确定要操作吗？");
						if(vaild){
							//添加信息URL
							var updateDemoUrl = $(this).attr("action");
							//全 URL 路径
							var url = "${basePath}"+updateDemoUrl;
							$("#frm").attr("action",url);
							$("#frm").submit();
							$(this).attr("disabled",true);
						}
					}	
				}
			});
			changeColor();
			window_onload();
	    });

		function changeColor()
		{
		  var $name = $("#crud").val();
		  if($name == "trade")
		  {
		  	$("#trade").css("color", "red");
		  	$("#cal").css("color", "#26548B");
		  }
		  else if($name == "cal")
		  {
			  $("#trade").css("color", "#26548B");
			  $("#cal").css("color", "red");
		  } 
		}	

		function trade_onclick()
		{
		  document.location.href = "${basePath}/timebargain/tradeparams/notTradeDayList.action";
		}

		function cal_onclick()
		{
		  document.location.href = "${basePath}/timebargain/tradeparams/detailCalendar.action";
		}	
function window_onload()
{
    var relWeek = "${entity.week}";
    //alert(relWeek);
    if (relWeek) {
    	var relWeeks = relWeek.split(",");
    	var weeks = $(":checkbox");
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
}



//save111111
function save_onclick()
{
	
	var is = date();
	//alert(is);
	//alert(document.forms(0).day.value);
	if (document.getElementById("day").value != "") {
		if (!is) {
		return false;
		}
	}
	//document.forms(0).submit();
    //document.forms(0).save.disabled = true;
	return true;
}

    
  function date(){  
  	if (document.getElementById("day").value != "") {
  		var relDay0 = document.getElementById("day").value;
  		
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
           		document.getElementById("day").focus();   
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
  						document.getElementById("day").focus();
  						return false;
  					}
  				}
  			}
  		}
  		//alert(relDays0);
  		document.getElementById("day").value = relDays0;  
  		return   true;
	//ymd1= document.forms(0).day.value.split("-");   
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

//设置交易节
function setTrade(){
	//获取配置权限的 URL
	var setUrl=document.getElementById('set').action;
	//获取完整跳转URL
	var url = "${basePath}"+setUrl;

	if(showDialog(url, "", 800, 600)){
		//如果添加成功，则刷新列表
		 document.location.href = "${basePath}/timebargain/tradeparams/notTradeDayList.action";
	}
	
}


//---------------------------start baseinfo-------------------------------

//-----------------------------end baseinfo-----------------------------
//---------------------------start paraminfo-------------------------------

//-----------------------------end paraminfo-----------------------------
</script>
	</head>

	<body>
		
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="11" height="31" background="${skinPath }/image/app/timebargain/bgimage.gif"></td>
    <td width="17" background="${skinPath }/image/app/timebargain/bgimage.gif"><div align="left"><img src="${skinPath }/image/app/timebargain/line.gif" width="3" height="31"></div></td>   
    <td width="55" background="${skinPath }/image/app/timebargain/bgimage.gif">
      <!--   <a href="#" id="trade" class="common" onclick="trade_onclick()">非交易日</a>-->
       <rightHyperlink:rightHyperlink href="#" id="trade" text="非交易日" className="common" onclick="trade_onclick()" action="/timebargain/tradeparams/notTradeDayList.action" />
    </td>
    <td width="29" background="${skinPath }/image/app/timebargain/bgimage.gif"><div align="left"><img src="${skinPath }/image/app/timebargain/line.gif" width="3" height="31"></div></td>
    <td width="46" background="${skinPath }/image/app/timebargain/bgimage.gif" class="common" >
       <!--   <a href="#" id="cal" class="common" onclick="cal_onclick()">日历</a>-->
        <rightHyperlink:rightHyperlink href="#" id="cal" text="日历" className="common" onclick="cal_onclick()" action="/timebargain/tradeparams/detailCalendar.action" />
    </td>
    <td width="13" background="${skinPath }/image/app/timebargain/bgimage.gif" class="common" ><img src="${skinPath }/image/app/timebargain/line.gif" width="3" height="31"></td>
    <td width="333" background="${skinPath }/image/app/timebargain/bgimage.gif" class="common" >&nbsp;</td>
    <td width="267" background="${skinPath }/image/app/timebargain/bgimage.gif" class="common" >&nbsp; </td>
  </tr>
</table>
		
		<table border="0" height="400" width="600" align="center">
			<tr>
				<td>
					<form id="frm" method="post" enctype="multipart/form-data" action="" targetType="hidden">
						<fieldset class="pickList">
							<legend class="common">
								<b class="required">非交易日设置
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
									
										星期一<input type="checkbox" name="entity.weeks" value="2" class="NormalInput"/>
										星期二<input type="checkbox" name="entity.weeks" value="3" class="NormalInput"/>
										星期三<input type="checkbox" name="entity.weeks" value="4" class="NormalInput"/>
										星期四<input type="checkbox" name="entity.weeks" value="5" class="NormalInput"/>
										星期五<input type="checkbox" name="entity.weeks" value="6" class="NormalInput"/>
										星期六<input type="checkbox" name="entity.weeks" value="7" class="NormalInput"/>
										星期日<input type="checkbox" name="entity.weeks" value="1" class="NormalInput"/>
										
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
								<textarea id="day" name="entity.day" rows="5" cols="55" onblur="return date()" style="width:428" class="text"><c:out value="${entity.day }" /></textarea>
							</td>
						</tr>
						<tr>
							<td></td>
							<td>逗号分隔的日期，<b class="required">(逗号必须为英文状态下半角)</b></td>
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
			    <rightButton:rightButton name="提交" onclick="" className="anniu_btn" action="/timebargain/tradeparams/updateTradeDay.action" id="update"></rightButton:rightButton>
				&nbsp;&nbsp;
			 <c:if test="${tradeTimeType=='1'}">	  
				<rightButton:rightButton name="设置交易节" onclick="setTrade();" className="anniu_btn" action="/timebargain/tradeparams/setTradeDay.action" id="set"></rightButton:rightButton>
			 </c:if>	
			</td>
		</tr>
	</table>
</fieldset>
<input type="hidden" id="crud" name="crud" value="${request.crud }"/>
<input type="hidden" name="entity.id" value="${entity.id }" />
					
					</form>
				</td>
			</tr>
		</table>

	</body>
</html>
