<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<%!
class barginCalendar{
   String date;
   String day;
   int status=2;//-1�ǽ����գ�-2δ���ǽ����� 1�ѽ��� 2������
   int week;
   boolean isToday=false;
}
String month;
String days[];
Map bcs;
String yAm;
Calendar thisMonth;
String year;
%>
<%
year=(String) request.getAttribute("year");
%>
<html> 
<head> 
<meta http-equiv="Content-Type" content="text/html; char=GBK"> 
<title>��</title> 
<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
<script Language="JavaScript"> 
<!-- 
function changeMonth()
{
var mm="${basePath}/timebargain/tradeparams/detailCalendar.action?month="+document.sm.elements[0].selectedIndex+"&year="+<%=year%>; 
window.open(mm,"_self"); 
} 
//-->
</script> 
</head> 

<% 

month=(String) request.getAttribute("month");
days = (String[]) request.getAttribute("days");
bcs = (Map) request.getAttribute("bcs");
yAm=(String) request.getAttribute("yAm");
thisMonth = (Calendar) request.getAttribute("thisMonth");
thisMonth.setFirstDayOfWeek(Calendar.SUNDAY); 
thisMonth.set(Calendar.DAY_OF_MONTH,1); 
int firstIndex=thisMonth.get(Calendar.DAY_OF_WEEK)-1; 
int maxIndex=thisMonth.getActualMaximum(Calendar.DAY_OF_MONTH); 
int w=firstIndex+1;
for(int i=0;i<maxIndex;i++) 
{ 
days[firstIndex+i]=String.valueOf(i+1);
barginCalendar bc=new barginCalendar();
bc.week=w;
w++;
if(w>7)
{
   w=1;
}
bcs.put(String.valueOf(i+1),bc); 
}

List list=(List)request.getAttribute("list");
if(list!=null&&list.size()>0)
{
   for(int i=0;i<list.size();i++)
   {
       String day=(String)(((Map)list.get(i)).get("D"));
       String d=Integer.parseInt(day)+"";
       barginCalendar bc=(barginCalendar)bcs.get(d);
       bc.status=1;
       bcs.put(d,bc);
   }
}


Calendar nowdate=Calendar.getInstance(); 
/*if(String.valueOf(nowdate.get(Calendar.YEAR)).equals(year)&&String.valueOf(nowdate.get(Calendar.MONTH)).equals(month))
{*/
  
  List list1=(List)request.getAttribute("list1");
  if(list1!=null&&list1.size()>0)
  {
     Map map=(Map)list1.get(0);
     String week=(String)map.get("WEEK");
     String day=(String)map.get("DAY");
     if(day!=null)
     {
     String[] days1=day.split(",");
     for(int i=0;i<days1.length;i++)
     {
        if(days1[i]!=null&&!"".equals(days1[i])&&days1[i].indexOf(yAm+"-")>=0)
        {
          String sd=days1[i].replaceAll(yAm+"-","");
          barginCalendar bc=(barginCalendar)bcs.get(Integer.parseInt(sd)+"");
          bc.status=-2;
          bcs.put(Integer.parseInt(sd)+"",bc);
        }
     }
     }
     if(week!=null)
     {
     String[] weeks=week.split(",");
     for(int i=0;i<weeks.length;i++)
     {
        if(weeks[i]!=null&&!"".equals(weeks[i]))
        {
           Set set=bcs.entrySet();
           Iterator e=set.iterator();
           while(e.hasNext())
           {
              Map.Entry me=(Map.Entry)e.next();
              barginCalendar bc=(barginCalendar)me.getValue();
              if(bc.week==Integer.parseInt(weeks[i])&&bc.status==2)
              {
                 bc.status=-2;
              }
           }
        }
     }
     }
  }
//}
String today=nowdate.get(Calendar.DAY_OF_MONTH)+"";
int sign=-1;
int sign1=-1;
if(String.valueOf(nowdate.get(Calendar.YEAR)).equals(year)&&String.valueOf(nowdate.get(Calendar.MONTH)).equals(month))
{
   sign=1;
   sign1=1;
}
else if(nowdate.after(thisMonth))
{
   sign=2;
}
    Set set=bcs.entrySet();
    Iterator e=set.iterator();
    while(e.hasNext())
    {
       Map.Entry me=(Map.Entry)e.next();
       barginCalendar bc=(barginCalendar)me.getValue();
       String key=(String)me.getKey();
       if((Integer.parseInt(key)<Integer.parseInt(today)&&bc.status!=1&&sign==1)||(sign==2&&bc.status!=1))
       {
         bc.status=-1;
       }
       else if(sign1==1&&Integer.parseInt(key)==Integer.parseInt(today))
       {
          bc.isToday=true;
       }
    }
%> 
<script type="text/javascript">
	function TestValue(){
		if(document.getElementById("year").value==""){
			alert("��������ݣ�");
			return false;
		}
		document.getElementById("sm").submit();
	}
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
</script>
<body onload="changeColor();"> 

<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="11" height="31" background="${skinPath }/image/app/timebargain/bgimage.gif"></td>
    <td width="17" background="${skinPath }/image/app/timebargain/bgimage.gif"><div align="left"><img src="${skinPath }/image/app/timebargain/line.gif" width="3" height="31"></div></td>   
    <td width="55" background="${skinPath }/image/app/timebargain/bgimage.gif"><a href="#" id="trade" class="common" onclick="trade_onclick()">�ǽ�����</a></td>
    <td width="29" background="${skinPath }/image/app/timebargain/bgimage.gif"><div align="left"><img src="${skinPath }/image/app/timebargain/line.gif" width="3" height="31"></div></td>
    <td width="46" background="${skinPath }/image/app/timebargain/bgimage.gif" class="common" ><a href="#" id="cal" class="common" onclick="cal_onclick()">����</a></td>
    <td width="13" background="${skinPath }/image/app/timebargain/bgimage.gif" class="common" ><img src="${skinPath }/image/app/timebargain/line.gif" width="3" height="31"></td>
    <td width="333" background="${skinPath }/image/app/timebargain/bgimage.gif" class="common" >&nbsp;</td>
    <td width="267" background="${skinPath }/image/app/timebargain/bgimage.gif" class="common" >&nbsp; </td>
  </tr>
</table>


<FORM id="sm" name="sm" method="post" action=""> 
<table border="0" width="168" height="20"   align="center"> 
<tr> 
<td width=28%><input id="yaar" type=text name="year" value=<%=year%> size=4 maxlength=4 onkeyup="this.value=this.value.replace(/\D/g,'')"></td> 
<td>��</td> 
<td width=30%><select name="month" size="1" _disibledevent=>
<option value="0"> 1��</option> 
<option value="1"> 2��</option> 
<option value="2"> 3��</option> 
<option value="3"> 4��</option> 
<option value="4"> 5��</option> 
<option value="5"> 6��</option> 
<option value="6"> 7��</option> 
<option value="7"> 8��</option> 
<option value="8"> 9��</option> 
<option value="9">10��</option> 
<option value="10">11��</option> 
<option value="11">12��</option> 
</select></td> 

<td width=28%><input type="button" value="��ѯ" onclick="TestValue()"></td> 
</tr> 
</table> 
<!-- <%=year%>�� <%=Integer.parseInt(month)+1%>��  -->
<table border="1" width="500" height="350" align=center	> 
<tr> 
<td width="14%" height="50" align="center"><font size='2' color='black'><b>����</b></font></td> 
<td width="14%" height="50" align="center"><font size='2' color='black'><b>��һ</b></font></td> 
<td width="14%" height="50" align="center"><font size='2' color='black'><b>�ܶ�</b></font></td> 
<td width="14%" height="50" align="center"><font size='2' color='black'><b>����</b></font></td> 
<td width="14%" height="50" align="center"><font size='2' color='black'><b>����</b></font></td> 
<td width="14%" height="50" align="center"><font size='2' color='black'><b>����</b></font></td> 
<td width="14%" height="50" align="center"><font size='2' color='black'><b>����</b></font></td> 
</tr> 
<% for(int j=0;j<6;j++) { %> 
<tr> 
<% for(int i=j*7;i<(j+1)*7;i++) { %> 
<td width="14%" height="50" valign="middle" align="center" >
<%
  if(!"".equals(days[i]))
  {
    barginCalendar bc=(barginCalendar)bcs.get(days[i]); 
    if(bc.isToday)
    {
    %>
    <div style="background-color:yellow">
    <%
    }
    if(bc.status==1)
    {
     out.println(days[i]+"<br><font size='2' color='gray'><b>�ѽ���</b></font>");
    }
    else if(bc.status==2)
    {
     out.println(days[i]+"<br><font size='2' color='green'>������</font>");
    }
    else if(bc.status==-2)
    {
     out.println(days[i]+"<br><font size='2' color='red'>�ǽ�����</font>");
    }
    else if(bc.status==-1)
    {
     out.println(days[i]+"<br><font size='2' color='brown'>δ����</font>");
    }
    if(bc.isToday)
    {
    %>
    </div>
    <%
    }
  }
  else
  {
    out.println("&nbsp;");
  }
%>
</td> 
<% } %> 
</tr> 
<% } %> 

</table> 
<input type="hidden" id="crud" name="crud" value="${opr }" />
</FORM> 
<script Language="JavaScript">
<!-- 
document.sm.month.options.selectedIndex=<%=month%>; 
//--> 
</script> 
</body> 
</html> 