<%@ page contentType="text/html; charset=GBK" language="java" import="java.util.*" %>
<%@ page import="gnnt.MEBS.base.dao.DaoHelper"%>
<%@ page import="gnnt.MEBS.base.util.SysData"%>
<%!
class barginCalendar{
   String date;
   String day;
   int status=2;//-1�ǽ����գ�-2δ���ǽ����� 1�ѽ��� 2������
   int week;
   boolean isToday=false;
}
public List check(String sql){
     List list=null;
     DaoHelper dao = (DaoHelper)SysData.getBean("daoHelper");
     list=dao.queryBySQL(sql);
     return list;
}
%>
<%! 
String year; 
String month; 
%> 
<% 
month=request.getParameter("month"); 
year =request.getParameter("year"); 
%> 
<html> 
<head> 
<meta http-equiv="Content-Type" content="text/html; char=GBK"> 
<title>��</title> 
<script Language="JavaScript"> 
<!-- 
function changeMonth()
{
var mm="calendar.jsp?month="+document.sm.elements[0].selectedIndex+"&year=" +<%=year%>; 
window.open(mm,"_self"); 
} 
//-->
</script> 
</head> 
<%! 
String days[];
Map bcs;
%> 
<% 
days=new String[42];
bcs=new HashMap(); 
for(int i=0;i<42;i++) 
{ 
  days[i]=""; 
} 
%> 
<% 
Calendar thisMonth=Calendar.getInstance();
thisMonth.set(Calendar.DATE, 1 );  
if(month!=null&&(!month.equals("null"))) 
thisMonth.set(Calendar.MONTH, Integer.parseInt(month) ); 
if(year!=null&&(!year.equals("null"))) 
thisMonth.set(Calendar.YEAR, Integer.parseInt(year) ); 
year=String.valueOf(thisMonth.get(Calendar.YEAR)); 
int m=thisMonth.get(Calendar.MONTH)+1;
month=String.valueOf(thisMonth.get(Calendar.MONTH)); 
String month1="";
if(m<10)
{
  month1=String.valueOf("0"+m); 
}
else
{
  month1=String.valueOf(m); 
}
String yAm=year+"-"+month1;
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

String sql="select * from (select distinct(to_char(t.cleardate,'dd')) d from t_h_market t where to_char(t.cleardate,'yyyy-MM')='"+yAm+"' ) a order by d";
List list=check(sql);
if(list!=null&&list.size()>0)
{
   for(int i=0;i<list.size();i++)
   {
       String day=(String)(((Map)list.get(i)).get("d"));
       String d=Integer.parseInt(day)+"";
       barginCalendar bc=(barginCalendar)bcs.get(d);
       bc.status=1;
       bcs.put(d,bc);
   }
}


Calendar nowdate=Calendar.getInstance(); 
/*if(String.valueOf(nowdate.get(Calendar.YEAR)).equals(year)&&String.valueOf(nowdate.get(Calendar.MONTH)).equals(month))
{*/
  
  sql="select week,day from T_A_NotTradeDay";
  List list1=check(sql);
  if(list1!=null&&list1.size()>0)
  {
     Map map=(Map)list1.get(0);
     String week=(String)map.get("week");
     String day=(String)map.get("day");
     if(day!=null)
     {
     String[] days=day.split(",");
     for(int i=0;i<days.length;i++)
     {
        if(days[i]!=null&&!"".equals(days[i])&&days[i].indexOf(yAm+"-")>=0)
        {
          String sd=days[i].replaceAll(yAm+"-","");
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
</script>
<body bgcolor="#def2fb"> 
<FORM id="sm" name="sm" method="post" action=""> 
<table border="0" width="168" height="20"   align="center"> 
<tr> 
<td width=28%><input id="yaar" type=text name="year" value=<%=year%> size=4 maxlength=4 onkeyup="this.value=this.value.replace(/\D/g,'')"></td> 
<td>��</td> 
<td width=30%><select name="month" size="1" _disibledevent=>
<option value="0">1��</option> 
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

</FORM> 
<script Language="JavaScript">
<!-- 
document.sm.month.options.selectedIndex=<%=month%>; 
//--> 
</script> 
</body> 
</html> 