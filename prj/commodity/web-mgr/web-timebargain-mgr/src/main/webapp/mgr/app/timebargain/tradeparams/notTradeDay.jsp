<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<%
	String weeks = (String)request.getAttribute("weeks");
%>
<html>
	<head>
		<title>�ǽ���������</title>
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
			//�޸İ�ťע�����¼�
			$("#update").click(function(){
				//��֤��Ϣ
				if(jQuery("#frm").validationEngine('validateform')){
					var flag = false;
					flag = save_onclick();
					if (flag) {
						var vaild = affirm("��ȷ��Ҫ������");
						if(vaild){
							//�����ϢURL
							var updateDemoUrl = $(this).attr("action");
							//ȫ URL ·��
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

//���ý��׽�
function setTrade(){
	//��ȡ����Ȩ�޵� URL
	var setUrl=document.getElementById('set').action;
	//��ȡ������תURL
	var url = "${basePath}"+setUrl;

	if(showDialog(url, "", 800, 600)){
		//�����ӳɹ�����ˢ���б�
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
      <!--   <a href="#" id="trade" class="common" onclick="trade_onclick()">�ǽ�����</a>-->
       <rightHyperlink:rightHyperlink href="#" id="trade" text="�ǽ�����" className="common" onclick="trade_onclick()" action="/timebargain/tradeparams/notTradeDayList.action" />
    </td>
    <td width="29" background="${skinPath }/image/app/timebargain/bgimage.gif"><div align="left"><img src="${skinPath }/image/app/timebargain/line.gif" width="3" height="31"></div></td>
    <td width="46" background="${skinPath }/image/app/timebargain/bgimage.gif" class="common" >
       <!--   <a href="#" id="cal" class="common" onclick="cal_onclick()">����</a>-->
        <rightHyperlink:rightHyperlink href="#" id="cal" text="����" className="common" onclick="cal_onclick()" action="/timebargain/tradeparams/detailCalendar.action" />
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
								<b class="required">�ǽ���������
								</b>
							</legend>

<table width="100%" border="0" align="center"  class="common" cellpadding="0" cellspacing="2">
<!-- ������Ϣ -->
        <tr class="common">
          <td colspan="4">
            
              
						<span id="baseinfo">
							<table cellSpacing="4" cellPadding="4" width="100%" border="0" align="center" class="common">		
								<tr>
									<td align="right" width="118">
											�ǽ������ڣ�
									</td>
									
									<td align="left" width="460">
									
										����һ<input type="checkbox" name="entity.weeks" value="2" class="NormalInput"/>
										���ڶ�<input type="checkbox" name="entity.weeks" value="3" class="NormalInput"/>
										������<input type="checkbox" name="entity.weeks" value="4" class="NormalInput"/>
										������<input type="checkbox" name="entity.weeks" value="5" class="NormalInput"/>
										������<input type="checkbox" name="entity.weeks" value="6" class="NormalInput"/>
										������<input type="checkbox" name="entity.weeks" value="7" class="NormalInput"/>
										������<input type="checkbox" name="entity.weeks" value="1" class="NormalInput"/>
										
									</td>
								</tr>												
							</table >
						</span>
            
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
								<textarea id="day" name="entity.day" rows="5" cols="55" onblur="return date()" style="width:428" class="text"><c:out value="${entity.day }" /></textarea>
							</td>
						</tr>
						<tr>
							<td></td>
							<td>���ŷָ������ڣ�<b class="required">(���ű���ΪӢ��״̬�°��)</b></td>
						</tr>
						<tr>
							<td></td>
							<td>���� ��2008-10-01,2008-10-02,2008-10-03</td>
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
			    <rightButton:rightButton name="�ύ" onclick="" className="anniu_btn" action="/timebargain/tradeparams/updateTradeDay.action" id="update"></rightButton:rightButton>
				&nbsp;&nbsp;
			 <c:if test="${tradeTimeType=='1'}">	  
				<rightButton:rightButton name="���ý��׽�" onclick="setTrade();" className="anniu_btn" action="/timebargain/tradeparams/setTradeDay.action" id="set"></rightButton:rightButton>
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
