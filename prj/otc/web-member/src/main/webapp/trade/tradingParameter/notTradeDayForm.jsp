<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<%@ include file="/public/ecsideLoad.jsp"%>
<html>
	<head>
		<script language="JavaScript" src="<%=basePath%>/public/global.js"></script>
		<script language="JavaScript" src="<%=basePath%>/public/open.js"></script>
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
	highlightFormElements();
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
	if (confirm("��ȷ��Ҫ�ύ��")) {
		var is = date();
		if (document.getElementById("day").value != "") {
			if (!is) {
			return false;
			}
		}
	    document.forms(0).submit();
	    document.forms(0).save.disabled = true;
   }
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
  		document.getElementById("day").value = relDays0;
  		alert("�����ɹ���");   
  		return   true;
  	}else {
  		alert("�����ɹ���");
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
	var url = "${basePath}/tradeManage/tradingParameter/editDaySection.action?notTradeDayVO.week=${notTradeDayVO.week}";
	var result=dialog(url,window,800,400);
	if(result==1111)
		window.location.reload();
}
</script>
	</head>
	<body onLoad="return window_onload()" onkeypress="keyEnter(event.keyCode);" class="body_bgcolor">
		<table border="0" height="400" width="600" align="center">
			<tr>
				<td>
					<form action="${basePath }/tradeManage/tradingParameter/update.action" method="POST" class="form" >
						<input type="hidden" name="notTradeDayVO.id" value="${notTradeDayVO.id}"/>
						<fieldset class="pickList">
							<legend class="common">
								<b>���������� </b>
							</legend>
							<table width="100%" border="0" align="center" class="common" cellpadding="0" cellspacing="2">
								<!-- ������Ϣ -->
								<tr class="common">
									<td colspan="4">
										<span id="baseinfo">
											<table cellSpacing="4" cellPadding="4" width="100%"
												border="0" align="center" class="common">
												<tr>
													<td align="right" width="118">
														�ǽ������ڣ�
													</td>
													<td align="left" width="460">
														����һ<input type="checkbox" name="weeks" value="2" class="NormalInput">
														���ڶ�<input type="checkbox" name="weeks" value="3" class="NormalInput">
														������<input type="checkbox" name="weeks" value="4" class="NormalInput">
														������<input type="checkbox" name="weeks" value="5" class="NormalInput">
														������<input type="checkbox" name="weeks" value="6" class="NormalInput">
														������<input type="checkbox" name="weeks" value="7" class="NormalInput">
														������<input type="checkbox" name="weeks" value="1" class="NormalInput">
													</td>
												</tr>
											</table> </span>
									</td>
								</tr>
								<!-- ������Ϣ -->
								<tr class="common">
									<td colspan="4">
										<span id="paraminfo">
											<table cellSpacing="3" cellPadding="3" width="100%" border="0" align="center" class="common">
												<tr>
													<td align="right" width="118">
														�ǽ������ڣ�
													</td>
													<td>
														<textarea name="notTradeDayVO.day" id="day" rows="5" cols="55" onblur="return date()" style="width:428" class="text" >${notTradeDayVO.day }</textarea>
													</td>
												</tr>
												<tr>
													<td></td>
													<td>
														���ŷָ������ڣ���2008-10-01,2008-10-02,2008-10-03
													</td>
												</tr>
											</table> </span>
									</td>
								</tr>
								<tr>
									<td colspan="4" height="3">
									</td>
								</tr>
								<tr>
									<td colspan="4" align="center">
										<button class="btn_sec" name="save" id="updateTradeDay" onclick="return save_onclick()">
								                              �ύ
							            </button>
										<c:if test="${notTradeDayVO.tradeTimeType == '1'}">
											<input type="button" name="timeType" onclick="return type_onclick()" value="���ý��׽�" />
										</c:if>
									</td>
								</tr>
							</table>
						</fieldset>
					</form>
				</td>
			</tr>
		</table>
	</body>
</html>

<%@ include file="/public/footInc.jsp"%>
