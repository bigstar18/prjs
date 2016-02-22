<%@ page contentType="text/html;charset=GBK" import="java.util.Date"%>
<%@ include file="/public/session.jsp" %>

<html>
	<head>
		<script language="JavaScript" src="<%=basePath%>/public/global.js"></script>
		<script language="JavaScript" src="<%=basePath%>/public/open.js"></script>
		<%@ include file="/public/ecsideLoad.jsp"%>
		<title></title>
		<style type="text/css">
<!--
.yin {
	visibility: hidden;
	position: absolute;
}

.xian {
	visibility: visible;
}
-->
</style>
<script language="JavaScript">
function window_onload() {
	var relWeek = "${notTradeDayVO.week}";
    var relWeeks = relWeek.split(",");
	var weeks = document.forms(0).weeks;
	for (j = 0; j < relWeeks.length; j++) {
		for (i = 0; i < weeks.length; i++) {
			if (relWeeks[j] == weeks[i].value) {
				weeks[i].checked = true;
			}
		}
	}
}

function save_onclick() {
	var flag = false;
	var isChanged = false;
	//非交易星期，数据库中值
	var relWeek = "${notTradeDayVO.week}";
    var relWeeks = relWeek.split(",");
    //当前交易日与下一个交易日期之间的非交易日对应星期
    var nextDateWeeksArr = '${nextDateWeeks}';
    var nextDateWeeks2 = nextDateWeeksArr.split(",");
    //当前交易日与下一个交易日期之间的非交易日
    var nextDateForYearArr = '${nextDateForYear}';
    var nextDateForYear2 = nextDateForYearArr.split(',');
    
    var num = 0;
    //所有星期，包括选择和未选择的
	var weeks = document.forms(0).weeks;
    //将所有选择的星期存放在该数组中
	var arrayObj = new Array();
	//当前交易日对应的星期
	var dayForWeeks = '${dayForWeek}';
	if(dayForWeeks < 7){
		dayForWeeks = ${dayForWeek + 1};
	} else {
		dayForWeeks = 1;
	}
	for (i = 0; i < weeks.length; i++) {//循环遍历所有星期，包括选择和未选择的
		var week = weeks[i];
		for (b = 0; b < nextDateWeeks2.length;b++){//循环遍历当前交易日与下一个交易日期之间的非交易日对应星期
			//当前交易日与下一个交易日期之间的非交易日不能设置为交易日
    		//日期和星期结合起来判断，只要有一个改为交易日就弹出提示
    		if(!week.checked && week.value == nextDateWeeks2[b]){
    			var relDay0 = document.getElementById("day").value;
    			var relDays0 = relDay0.replace(/\r/ig,"").replace(/\n/ig,"");
   				if(relDays0.indexOf(nextDateForYear2[b]) < 0) {
    				alert("收取完延期费的日期不能修改设置！");
					return false;
    			}
    		}
		}
		//下一个交易日对应的星期和日期都不能改成非交易日
		if(week.checked && week.value == '${nextTradeDayWeek}'){
			alert("收取完延期费的日期不能修改设置！");
			return false;
		} else {
			var relDay0 = document.getElementById("day").value;
    		var relDays0 = relDay0.replace(/\r/ig,"").replace(/\n/ig,"");
			if('${nextTradeDay }' != '' && relDays0.indexOf('${nextTradeDay}') >= 0) {
   				alert("收取完延期费的日期不能修改设置！");
				return false;
   			}
		}
		
		if(week.checked == true && week.value == dayForWeeks){
			flag = true;
		} 
		if(week.checked){
			arrayObj[num] = week.value;
			num++;
		}
	}
	if(relWeek != arrayObj){
		isChanged = true;
	}
	if(document.getElementById("day").value != '${notTradeDayVO.day }'){
		isChanged = true;
	}
	if(flag && ${statusForNotTrade != 2} && ${statusForNotTrade != 3}){
		alert("当前交易日不能设置为非交易日，请重新设置！");
		return false;
	}
	var is = date();
	if (document.getElementById("day").value != "") {
		if (!is) {
		return false;
		}
	}
	if(!isChanged){
		alert("没有修改内容");
		return false;
		
	}
	if (affirm("您确定要提交吗？")) {
	    document.forms(0).submit();
	    document.forms(0).updateTradeDay.disabled = true;
   }
}
  function date(){
  	  if (document.getElementById("day").value != "") {
  		var relDay0 = document.getElementById("day").value;
  		
  		var relDays0 = relDay0.replace(/\r/ig,"").replace(/\n/ig,"");
  		var days = relDays0.split(",");
  		for (i = 0; i < days.length; i++) {
  			if(days[i] == '${dateForYear}'){
  				alert("当前交易日不能设置为非交易日，请重新设置！");
				return false;
  			}
  			ymd1 = days[i].split("-");
  			month1=ymd1[1]-1   
    		var Date1 = new Date(ymd1[0],month1,ymd1[2]);     
  			if (Date1.getMonth()+1!=ymd1[1] || Date1.getDate()!=ymd1[2] || Date1.getFullYear()!=ymd1[0] || ymd1[0].length!=4 || ymd1[1].length!=2 || ymd1[2].length!=2){   
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
  		for (j = 0; j < days.length; j++) {
			var relDays = days[j];
			var aEnd = '${dbDate}';
			var aaEnd = aEnd.split('-');
		
			
			var aStart = relDays.split('-'); //转成成数组，分别为年，月，日，下同
		  	var startDate = aStart[0]+"/" + aStart[1]+ "/" + aStart[2];
		    var endDate = aaEnd[0] + "/" + aaEnd[1] + "/" + aaEnd[2];
			var dt1=new Date(Date.parse(startDate));
      		var dt2=new Date(Date.parse(endDate));
		    if(dt1 < dt2) {
				alert("日期不能小于当前日期！");
				document.getElementById("day").focus();
				return false;
			}
  		}
  		document.getElementById("day").value = relDays0;  
  		return   true;
  	}else {
  		return   true;
  	}
  }   

function suffixNamePress() {
  if (marketForm.addedTax.value < 1 && (event.keyCode>=46 && event.keyCode<=57) )
  {
    event.returnValue=true;
  }
  else
  {
    event.returnValue=false;
  }
}

function type_onclick(){
	var url = "${basePath}/tradeManage/tradingParameter/forwardUpdateDaySection.action?notTradeDayVO.week=${notTradeDayVO.week}";
	var value=dialog(url, window, 800, 500);
	if(value==1111)
		window.location.reload();
}
</script>
	</head>
	<body onLoad="return window_onload()" onkeypress="keyEnter(event.keyCode);" class="body_bgcolor">
		<div id="main_body">
			<table width="60%" border="0" cellspacing="0"
				cellpadding="0" align="center" style="padding-top:60px;">
				<tr>
					<td>
						<div class="div_cxtjd">
							<img src="<%=skinPath%>/cssimg/13.gif" />
							&nbsp;&nbsp;交易日设置
						</div>
						<div class="div_tj">
							<form action="${basePath }/tradeManage/tradingParameter/update.action" method="POST" class="form" >
							<input type="hidden" name="notTradeDayVO.id" value="${notTradeDayVO.id}"/>
								<table border="0" cellpadding="0" cellspacing="0"
									class="table2_style" width="90%">
									<tr>
										<td>
											<div class="div2_top">
												<table width="600" border="0" cellspacing="0"
													cellpadding="0" style="table-layout:fixed;">
													<tr>
														<td height="10" width="20%" align="right">&nbsp;</td>
														<td height="10" align="left">&nbsp;</td>
													</tr>
													<tr height="60">
														<td align="right">
															非交易星期：&nbsp;
														</td>
														<td height="10">
														星期一<input type="checkbox" name="weeks" value="2" class="NormalInput">
														星期二<input type="checkbox" name="weeks" value="3" class="NormalInput">
														星期三<input type="checkbox" name="weeks" value="4" class="NormalInput">
														星期四<input type="checkbox" name="weeks" value="5" class="NormalInput">
														星期五<input type="checkbox" name="weeks" value="6" class="NormalInput">
														星期六<input type="checkbox" name="weeks" value="7" class="NormalInput">
														星期日<input type="checkbox" name="weeks" value="1" class="NormalInput">
														</td>
													</tr>
													<tr>
														<td valign="top" align="right">
															非交易日期：&nbsp;
														</td>
														<td>
															<textarea name="notTradeDayVO.day" id="day" rows="5" cols="55" style="width:440px;" class="text" >${notTradeDayVO.day }</textarea>
														</td>
													</tr>
													<tr height="60">
														<td height="10">&nbsp;</td>
														<td>
															逗号分隔的日期，如2008-06-01,2008-10-02,2008-10-03
														</td>
													</tr>
													<tr>
														<td align="center" colspan="2">
															<button class="btn_sec" name="updateTradeDay" id="updateTradeDay" onclick="save_onclick()">提交</button>&nbsp;&nbsp;
															<button class="btn_secMax" name="timeType" onclick="type_onclick()" id="updateDaySection">设置交易节</button>
														</td>
													</tr>
													<tr><td height="3"></td></tr>
												</table>
											</div>
										</td>
									</tr>
								</table>
							</form>
						</div>
					</td>
				</tr>
			</table>
			</div>
	</body>
</html>

<%@ include file="/public/footInc.jsp"%>
