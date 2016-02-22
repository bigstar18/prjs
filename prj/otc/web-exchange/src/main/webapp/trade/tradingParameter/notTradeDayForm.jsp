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
	//�ǽ������ڣ����ݿ���ֵ
	var relWeek = "${notTradeDayVO.week}";
    var relWeeks = relWeek.split(",");
    //��ǰ����������һ����������֮��ķǽ����ն�Ӧ����
    var nextDateWeeksArr = '${nextDateWeeks}';
    var nextDateWeeks2 = nextDateWeeksArr.split(",");
    //��ǰ����������һ����������֮��ķǽ�����
    var nextDateForYearArr = '${nextDateForYear}';
    var nextDateForYear2 = nextDateForYearArr.split(',');
    
    var num = 0;
    //�������ڣ�����ѡ���δѡ���
	var weeks = document.forms(0).weeks;
    //������ѡ������ڴ���ڸ�������
	var arrayObj = new Array();
	//��ǰ�����ն�Ӧ������
	var dayForWeeks = '${dayForWeek}';
	if(dayForWeeks < 7){
		dayForWeeks = ${dayForWeek + 1};
	} else {
		dayForWeeks = 1;
	}
	for (i = 0; i < weeks.length; i++) {//ѭ�������������ڣ�����ѡ���δѡ���
		var week = weeks[i];
		for (b = 0; b < nextDateWeeks2.length;b++){//ѭ��������ǰ����������һ����������֮��ķǽ����ն�Ӧ����
			//��ǰ����������һ����������֮��ķǽ����ղ�������Ϊ������
    		//���ں����ڽ�������жϣ�ֻҪ��һ����Ϊ�����վ͵�����ʾ
    		if(!week.checked && week.value == nextDateWeeks2[b]){
    			var relDay0 = document.getElementById("day").value;
    			var relDays0 = relDay0.replace(/\r/ig,"").replace(/\n/ig,"");
   				if(relDays0.indexOf(nextDateForYear2[b]) < 0) {
    				alert("��ȡ�����ڷѵ����ڲ����޸����ã�");
					return false;
    			}
    		}
		}
		//��һ�������ն�Ӧ�����ں����ڶ����ܸĳɷǽ�����
		if(week.checked && week.value == '${nextTradeDayWeek}'){
			alert("��ȡ�����ڷѵ����ڲ����޸����ã�");
			return false;
		} else {
			var relDay0 = document.getElementById("day").value;
    		var relDays0 = relDay0.replace(/\r/ig,"").replace(/\n/ig,"");
			if('${nextTradeDay }' != '' && relDays0.indexOf('${nextTradeDay}') >= 0) {
   				alert("��ȡ�����ڷѵ����ڲ����޸����ã�");
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
		alert("��ǰ�����ղ�������Ϊ�ǽ����գ����������ã�");
		return false;
	}
	var is = date();
	if (document.getElementById("day").value != "") {
		if (!is) {
		return false;
		}
	}
	if(!isChanged){
		alert("û���޸�����");
		return false;
		
	}
	if (affirm("��ȷ��Ҫ�ύ��")) {
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
  				alert("��ǰ�����ղ�������Ϊ�ǽ����գ����������ã�");
				return false;
  			}
  			ymd1 = days[i].split("-");
  			month1=ymd1[1]-1   
    		var Date1 = new Date(ymd1[0],month1,ymd1[2]);     
  			if (Date1.getMonth()+1!=ymd1[1] || Date1.getDate()!=ymd1[2] || Date1.getFullYear()!=ymd1[0] || ymd1[0].length!=4 || ymd1[1].length!=2 || ymd1[2].length!=2){   
           		alert("�Ƿ�����,������YYYY-MM-DD����ʽ����");   
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
  						alert("��׼������ͬ���ڣ�");
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
		
			
			var aStart = relDays.split('-'); //ת�ɳ����飬�ֱ�Ϊ�꣬�£��գ���ͬ
		  	var startDate = aStart[0]+"/" + aStart[1]+ "/" + aStart[2];
		    var endDate = aaEnd[0] + "/" + aaEnd[1] + "/" + aaEnd[2];
			var dt1=new Date(Date.parse(startDate));
      		var dt2=new Date(Date.parse(endDate));
		    if(dt1 < dt2) {
				alert("���ڲ���С�ڵ�ǰ���ڣ�");
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
							&nbsp;&nbsp;����������
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
															�ǽ������ڣ�&nbsp;
														</td>
														<td height="10">
														����һ<input type="checkbox" name="weeks" value="2" class="NormalInput">
														���ڶ�<input type="checkbox" name="weeks" value="3" class="NormalInput">
														������<input type="checkbox" name="weeks" value="4" class="NormalInput">
														������<input type="checkbox" name="weeks" value="5" class="NormalInput">
														������<input type="checkbox" name="weeks" value="6" class="NormalInput">
														������<input type="checkbox" name="weeks" value="7" class="NormalInput">
														������<input type="checkbox" name="weeks" value="1" class="NormalInput">
														</td>
													</tr>
													<tr>
														<td valign="top" align="right">
															�ǽ������ڣ�&nbsp;
														</td>
														<td>
															<textarea name="notTradeDayVO.day" id="day" rows="5" cols="55" style="width:440px;" class="text" >${notTradeDayVO.day }</textarea>
														</td>
													</tr>
													<tr height="60">
														<td height="10">&nbsp;</td>
														<td>
															���ŷָ������ڣ���2008-06-01,2008-10-02,2008-10-03
														</td>
													</tr>
													<tr>
														<td align="center" colspan="2">
															<button class="btn_sec" name="updateTradeDay" id="updateTradeDay" onclick="save_onclick()">�ύ</button>&nbsp;&nbsp;
															<button class="btn_secMax" name="timeType" onclick="type_onclick()" id="updateDaySection">���ý��׽�</button>
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
