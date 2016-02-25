<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../../globalDef.jsp"%>

<%
Calendar cld = Calendar.getInstance();
int h = cld.get(Calendar.HOUR_OF_DAY);
int m = cld.get(Calendar.MINUTE);
int s = cld.get(Calendar.SECOND);
%>

<span class="time_num" id=clock><%=h<10?"0"+h:""+h%>:<%=m<10?"0"+m:""+m%>:<%=s<10?"0"+s:""+s%></span>

<script language="Javascript">

var normalelapse = 1000;
var nextelapse = normalelapse;
var counter; 
var startTime;
var start = clock.innerText; 
var timer = null;

// 开始运行
function run() {
 timer = window.setInterval("onTimer()", nextelapse);   
}

// 停止运行
function stop() {
  window.clearTimeout(timer);
}

// 计时函数
function onTimer()
{
start = clock.innerText;//added 


var hms = new String(start).split(":");
var s = new Number(hms[2]);
var m = new Number(hms[1]);
var h = new Number(hms[0]);
  
s += 1;
if(s >= 60)
{
    s = 0;
	m += 1;
	if(m >= 60)
	{
	    m = 0;
		h += 1;
		if(h >= 24)
		{
		    h = 0;
			m = 0;
			s = 0;
		}
	}
}



var ss = s < 10 ? ("0" + s) : s;
var sm = m < 10 ? ("0" + m) : m;
var sh = h < 10 ? ("0" + h) : h;

start = sh + ":" + sm + ":" + ss;
clock.innerText = start;

 
}

run();

</script>
