/******************************************/
/*功能:open new windows                  */
/*参数:                                  */
/*返回:                                  */
/******************************************/
function openWindows(url)
{	
	window.top.opener=null;
	window.top.close();
	newWin=window.open(url,"","");
}
/******************************************/
/*功能:show  or   hidden                 */
/*参数:                                  */
/*返回:                                  */
/******************************************/

function workstatus(form){
if(form.sendFlag.options.value=="0"){
  inner.style.display='';
  outer.style.display='none';
}else{
 inner.style.display='none';
 outer.style.display='';
}
  
}
/******************************************/
/*功能:show calendar                     */
/*参数:                                  */
/*返回:                                  */
/******************************************/
function showcal()
{
	var CurrentDate=new Date();
	var CYear=CurrentDate.getFullYear();
	var CMonth=CurrentDate.getMonth()+1;
	var CDay=CurrentDate.getDay();
	var CDate=CurrentDate.getDate();
	var strWeek=new Array("日","一","二","三","四","五","六");
	var obj_today=document.getElementById("today");
	obj_today.innerText="今天是："+CMonth+"月"+CDate+"日 星期"+strWeek[CDay];
}
/******************************************/
/*功能: show times                       */
/*参数:                                  */
/*返回:                                  */
/******************************************/
var now,hours,minutes,seconds,timeValue;
function showtime(){
	now = new Date();
	hours = now.getHours();
	minutes = now.getMinutes();
	seconds = now.getSeconds();
	timeValue = (hours >= 12) ? "下午 " : "上午 ";
	timeValue += ((hours > 12) ? hours - 12 : hours) + " 点";
	timeValue += ((minutes < 10) ? " 0" : " ") + minutes + " 分";
	timeValue += ((seconds < 10) ? " 0" : " ") + seconds + " 秒";
	clock.innerHTML = timeValue;
	setTimeout("showtime()",1000);
}
/****************************************************/



/******************************************/
/*功能: 上周下周上月下月本周本月             */
/*参数:                                  */
/*返回:                                  */
/******************************************/
function subtoday()
	{
	document.form.date1.value=document.form.today.value;
	document.form.date2.value=document.form.today.value;
	}
	function subthisweek()
	{
	document.form.date1.value=document.form.thisweekfirstday.value;
	document.form.date2.value=document.form.today.value;
	}
	function subthismonth()
	{
	document.form.date1.value=document.form.thismonthfirstday.value;
	document.form.date2.value=document.form.today.value;
	}

//上周下周上月下月本周本月
var currentmonth,currentday,currentweek;
currentmonth=0;
currentday=0;
currentweek=0;
function showToDay()
{
 var Nowdate=new Date();
 M=Number(Nowdate.getMonth())+1;
 D=Number(Nowdate.getDate());
 if(M<10) M="0"+M;
 if(D<10) D="0"+D;
 return Nowdate.getFullYear()+M+D;
}
//登录工作日记时，星期四为一周的第一天，星期三为一周的最后一天
function showWeekFirstDay(cases)
{
 switch (cases)
 {
  case "" :
   var Nowdate=new Date();
   var WeekFirstDay=new Date();
   if(Nowdate.getDay()<4){
   	//周一周二周三时
   		WeekFirstDay=new Date((Nowdate-(Nowdate.getDay()-1)*86400000)-(4*86400000));
   }else{
   		WeekFirstDay=new Date((Nowdate-(Nowdate.getDay()-1)*86400000)+(3*86400000));
   }
   return WeekFirstDay;
   break;
  case "n" :
   var MonthFirstDay=showWeekLastDay("");
   var WeekFirstDay=new Date((MonthFirstDay/1000+86400)*1000);
   return WeekFirstDay;
   break;
  case "p" :
   var WeekFirstDay=showWeekFirstDay("");
   WeekFirstDay=new Date(WeekFirstDay-86400000*7);
   return WeekFirstDay;
   break;
 }
}
//登录工作日记时，星期四为一周的第一天，星期三为一周的最后一天
function showWeekLastDay(cases)
{
 switch (cases)
 {
  case "" :
   var Nowdate=new Date();
   var WeekFirstDay;
   if(Nowdate.getDay()<4){
   		WeekFirstDay=new Date((Nowdate-(Nowdate.getDay()-1)*86400000)-(4*86400000));
   }else{
   		WeekFirstDay=new Date((Nowdate-(Nowdate.getDay()-1)*86400000)+(3*86400000));
   }
   var WeekLastDay=new Date((WeekFirstDay/1000+6*86400)*1000);
   return WeekLastDay;
   break;
  case "n" :
   var MonthFirstDay=showWeekLastDay("");
   var WeekLastDay=new Date((MonthFirstDay/1000+7*86400)*1000);
   return WeekLastDay;
   break;
  case "p" :
   var WeekFirstDay=showWeekFirstDay("");
   var WeekLastDay=new Date(WeekFirstDay-86400000);
   return WeekLastDay;
   break;
 }
}
function showMonthFirstDay(cases)
{
 var Nowdate=new Date();
 switch (cases)
 {
  case "" :
   var MonthFirstDay=new Date(Nowdate.getFullYear(),Nowdate.getMonth(),1);
   return MonthFirstDay;
   break;
  case "n" :
   var MonthFirstDay=new Date(Nowdate.getFullYear(),Nowdate.getMonth()+1+currentmonth,1);
   return MonthFirstDay;
   break;
  case "p" :
   var MonthFirstDay=new Date(Nowdate.getFullYear(),Nowdate.getMonth()-1+currentmonth,1);
   return MonthFirstDay;
   break;
 }
}
function showMonthLastDay(cases)
{
 var Nowdate=new Date();
 switch (cases)
 {
  case "" :
   currentmonth=0;
   currentday=0;
   var MonthNextFirstDay=new Date(Nowdate.getFullYear(),Nowdate.getMonth()+1+currentmonth,1);
   var MonthLastDay=new Date(MonthNextFirstDay-86400000);
   return MonthLastDay;
   break;
  case "n" :
   var MonthNextFirstDay=new Date(Nowdate.getFullYear(),Nowdate.getMonth()+2+currentmonth,1);
   var MonthLastDay=new Date(MonthNextFirstDay-86400000);
   return MonthLastDay;
   break;
  case "p" :
   var MonthNextFirstDay=new Date(Nowdate.getFullYear(),Nowdate.getMonth()+currentmonth,1);
   var MonthLastDay=new Date(MonthNextFirstDay-86400000);
   return MonthLastDay;
   break;
 }
}
Date.prototype.toString = function ()
{
 return this.getFullYear() + "-" + (this.getMonth()+1) + "-" + this.getDate();
};
function InputDate(s,StartTime,EndTime)
{
 switch (s)
 {
  case 0 : //当天
   document.getElementById(StartTime).value=formatDate(showToDay());
   document.getElementById(EndTime).value=formatDate(showToDay());
   break;
  case 1 : //上周
   document.getElementById(StartTime).value=formatDate(showWeekFirstDay("p"));
   document.getElementById(EndTime).value=formatDate(showWeekLastDay("p"));
   break;
  case 2 : //本周
   document.getElementById(StartTime).value=formatDate(showWeekFirstDay(""));
   document.getElementById(EndTime).value=formatDate(showWeekLastDay(""));
   break;
  case 3 : //下周
   document.getElementById(StartTime).value=formatDate(showWeekFirstDay("n"));
   document.getElementById(EndTime).value=formatDate(showWeekLastDay("n"));
   break;
  case 4 : //上月
   document.getElementById(StartTime).value=formatDate(showMonthFirstDay("p"));
   document.getElementById(EndTime).value=formatDate(showMonthLastDay("p"));
   break;
  case 5 : //本月
   document.getElementById(StartTime).value=formatDate(showMonthFirstDay(""));
   document.getElementById(EndTime).value=formatDate(showMonthLastDay(""));
   break;
  case 6 : //下月
   document.getElementById(StartTime).value=formatDate(showMonthFirstDay("n"));
   document.getElementById(EndTime).value=formatDate(showMonthLastDay("n"));
   break;
  case 99 : //清空
   document.getElementById(StartTime).value="";
   document.getElementById(EndTime).value="";
   break;
  default :
   alert("未知的参数");
 }
}

function formatDate(date){
	arr=String(date).split("-");
	var year=arr[0];
	var month=arr[1];
	var day=arr[2];
	if(month.length==1){
	
		month='0'+month;
	}
	if(day.length==1){
		day='0'+day;
	}
	return year+month+day;
}

