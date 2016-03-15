/******************************************/
/*����:open new windows                  */
/*����:                                  */
/*����:                                  */
/******************************************/
function openWindows(url)
{	
	window.top.opener=null;
	window.top.close();
	newWin=window.open(url,"","");
}
/******************************************/
/*����:show  or   hidden                 */
/*����:                                  */
/*����:                                  */
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
/*����:show calendar                     */
/*����:                                  */
/*����:                                  */
/******************************************/
function showcal()
{
	var CurrentDate=new Date();
	var CYear=CurrentDate.getFullYear();
	var CMonth=CurrentDate.getMonth()+1;
	var CDay=CurrentDate.getDay();
	var CDate=CurrentDate.getDate();
	var strWeek=new Array("��","һ","��","��","��","��","��");
	var obj_today=document.getElementById("today");
	obj_today.innerText="�����ǣ�"+CMonth+"��"+CDate+"�� ����"+strWeek[CDay];
}
/******************************************/
/*����: show times                       */
/*����:                                  */
/*����:                                  */
/******************************************/
var now,hours,minutes,seconds,timeValue;
function showtime(){
	now = new Date();
	hours = now.getHours();
	minutes = now.getMinutes();
	seconds = now.getSeconds();
	timeValue = (hours >= 12) ? "���� " : "���� ";
	timeValue += ((hours > 12) ? hours - 12 : hours) + " ��";
	timeValue += ((minutes < 10) ? " 0" : " ") + minutes + " ��";
	timeValue += ((seconds < 10) ? " 0" : " ") + seconds + " ��";
	clock.innerHTML = timeValue;
	setTimeout("showtime()",1000);
}
/****************************************************/



/******************************************/
/*����: ���������������±��ܱ���             */
/*����:                                  */
/*����:                                  */
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

//���������������±��ܱ���
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
//��¼�����ռ�ʱ��������Ϊһ�ܵĵ�һ�죬������Ϊһ�ܵ����һ��
function showWeekFirstDay(cases)
{
 switch (cases)
 {
  case "" :
   var Nowdate=new Date();
   var WeekFirstDay=new Date();
   if(Nowdate.getDay()<4){
   	//��һ�ܶ�����ʱ
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
//��¼�����ռ�ʱ��������Ϊһ�ܵĵ�һ�죬������Ϊһ�ܵ����һ��
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
  case 0 : //����
   document.getElementById(StartTime).value=formatDate(showToDay());
   document.getElementById(EndTime).value=formatDate(showToDay());
   break;
  case 1 : //����
   document.getElementById(StartTime).value=formatDate(showWeekFirstDay("p"));
   document.getElementById(EndTime).value=formatDate(showWeekLastDay("p"));
   break;
  case 2 : //����
   document.getElementById(StartTime).value=formatDate(showWeekFirstDay(""));
   document.getElementById(EndTime).value=formatDate(showWeekLastDay(""));
   break;
  case 3 : //����
   document.getElementById(StartTime).value=formatDate(showWeekFirstDay("n"));
   document.getElementById(EndTime).value=formatDate(showWeekLastDay("n"));
   break;
  case 4 : //����
   document.getElementById(StartTime).value=formatDate(showMonthFirstDay("p"));
   document.getElementById(EndTime).value=formatDate(showMonthLastDay("p"));
   break;
  case 5 : //����
   document.getElementById(StartTime).value=formatDate(showMonthFirstDay(""));
   document.getElementById(EndTime).value=formatDate(showMonthLastDay(""));
   break;
  case 6 : //����
   document.getElementById(StartTime).value=formatDate(showMonthFirstDay("n"));
   document.getElementById(EndTime).value=formatDate(showMonthLastDay("n"));
   break;
  case 99 : //���
   document.getElementById(StartTime).value="";
   document.getElementById(EndTime).value="";
   break;
  default :
   alert("δ֪�Ĳ���");
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

