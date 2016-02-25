<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK"%>
<html>
	<head>
		<LINK REL="stylesheet" type="text/css" href="<c:url value="/timebargain/styles/common.css"/>" />
		<c:import url="/timebargain/statquery/customerID_change.jsp"/>
		<c:import url="/timebargain/common/date.jsp"/>
		<script language="VBScript" src="<c:url value="/timebargain/scripts/vbfunction.vbs"/>"></script>
		<title>
		</title>
		<script type="text/javascript"> 
		var day = new Date();
		   var Year = 0;
		   var Month = 0;
		   var Day = 0;
		 var CurrentDate = "";
		   Year       = day.getFullYear();
		   Month      = day.getMonth()+1;
		   Day        = day.getDate();   
		   CurrentDate += Year + "-";
		   if (Month >= 10 )
		   {
		    CurrentDate += Month + "-";
		   }
		   else
		   {
		    CurrentDate += "0" + Month + "-";
		   }
		   if (Day >= 10 )
		   {
		    CurrentDate += Day ;
		   }
		   else
		   {
		    CurrentDate += "0" + Day ;
		   }
		
function window_onload()
{
    highlightFormElements();
    //conditionOrderForm.isOne.value='true';
    
    query_onclick();
    //conditionOrderForm.isOne.value='false';
}
//query_onclick
function query_onclick()
{
if(conditionOrderForm.isQryHis.checked)
{
  if(conditionOrderForm.beginDate.value=="")
  {
    alert("��ʼ���ڲ���Ϊ�գ�");
    conditionOrderForm.beginDate.focus();
    return false;
  }
  if(conditionOrderForm.endDate.value=="")
  {
    alert("�������ڲ���Ϊ�գ�");
    conditionOrderForm.endDate.focus();
    return false;
  }
  if(!isDateFormat(conditionOrderForm.beginDate.value))
  {
    alert("��ʼ���ڸ�ʽ����ȷ��");
    conditionOrderForm.beginDate.focus();
    return false;
  }
  if(!isDateFormat(conditionOrderForm.endDate.value))
  {
    alert("�������ڸ�ʽ����ȷ��");
    conditionOrderForm.endDate.focus();
    return false;
  }
	 
	   var begin = conditionOrderForm.beginDate.value;
	   var end = conditionOrderForm.endDate.value;
	   var b = begin.replace("","-");
	   var e = end.replace("","-");
	   var c = CurrentDate.replace("","-");
		if(c < b){
			alert("��ʼ���ڴ��ڵ�ǰ���ڣ�");
			return false;
		}
		if(c < e){
			alert("�������ڴ��ڵ�ǰ���ڣ�");
			return false;
		}
		if(e < b){
			alert("��������С�ڿ�ʼ���ڣ�");
			return false;
		}
}
  wait.style.visibility = "visible";
  conditionOrderForm.submit();
  conditionOrderForm.query.disabled = true;  
}
//requery_onclick
function requery_onclick()
{
  top.mainFrame.location.href = "<c:url value="/timebargain/statquery/conditionOrder.jsp"/>";
}

function isQryHis_onclick()
{
  if(conditionOrderForm.isQryHis.checked)
  {
	  conditionOrderForm.beginDate.value = CurrentDate;
	  conditionOrderForm.endDate.value = CurrentDate;
		
    setReadWrite(conditionOrderForm.beginDate);
    setReadWrite(conditionOrderForm.endDate);
    conditionOrderForm.isQryHis.value = true;
  }
  else
  {
	// �����������
	conditionOrderForm.beginDate.value = "";
	conditionOrderForm.endDate.value = "";
	
    setReadOnly(conditionOrderForm.beginDate);
    setReadOnly(conditionOrderForm.endDate);
    conditionOrderForm.isQryHis.value = false;
  }
  conditionOrderForm.isQryHisHidd.value = conditionOrderForm.isQryHis.value;
}

</script>
  </head>
  <body leftmargin="6" topmargin="0" onLoad="return window_onload()" onkeypress="keyEnter(event.keyCode);">
    <c:import url="/timebargain/common/waitbar.jsp" charEncoding="GBK"/>
	<table border="0" height="100%" width="100%" cellpadding="0" cellspacing="0" class="common" align="center">
	  <tr>
	    <td>
		  <html:form action="/timebargain/statquery/conditionOrder.do?funcflg=listConditionOrder" method="POST" styleClass="form" target="ListFrame">
			<fieldset class="pickList" >
			  <legend class="common">
			    <b>��ѯ����</b>
			  </legend>
			  <table border="0" align="center" cellpadding="0" cellspacing="0" class="common" width="100%">
			    <tr>
			      <td align="right">&nbsp;&nbsp;�������ţ�</td>
			      <td>
			        <input type="text" id="orderNo" name="_A_OrderNo[=]" style="width:111" maxlength="16" title="" styleClass="text" onkeypress="onlyNumberAndCharInput()" />
			      </td>
			      <td align="right">&nbsp;&nbsp;�����̴��룺</td>
				  <td>
					<input type="text" id="firmID" name="_FirmID[=]" style="width:111" maxlength="16" title="" styleClass="text" onkeypress="onlyNumberAndCharInput()" />
			      </td>      
			      <td align="right">&nbsp;&nbsp;ί����Ʒ���룺</td>
				  <td>
					<input type="text" id="commodityID" name="_CommodityID[=]" style="width:111" maxlength="16" title="" styleClass="text" onkeypress="onlyNumberAndCharInput()" />
			      </td>
			      <td align="right">&nbsp;&nbsp;������Ʒ���룺</td>
			      <td>
			        <input type="text" id="conditionCommodityID" name="_ConditionCommodityID[=]" style="width:111" maxlength="16" title="" styleClass="text" onkeypress="onlyNumberAndCharInput()" />
			      </td>
			    </tr>
				<tr>
				  <td align="right">
			        <label>������</label>
			      </td>
			      <td>
				    <select id="BS_Flag" name="_BS_Flag[=]" style="width:111">
				      <option value="">ȫ��</option>
				      <option value="1">��</option>
					  <option value="2">��</option>
			        </select>
			      </td>		
			     
				  <td align="right">
			        <label>&nbsp;&nbsp;����/ת�ã�</label>
			      </td>
			      <td>
					<select id="orderType" name="_OrderType[=]" style="width:111">
				      <option value="">ȫ��</option>
					  <option value="1">����</option>
					  <option value="2">ת��</option>
					</select>	
			      </td> 			
                  <td align="right">�������ͣ�</td>
                  <td>
                    <select id="conditionType" name="_ConditionType[=]" style="width:111">
                      <option value="">ȫ��</option>
                      <option value="1">�ּ�</option>
					  <option value="2">��1</option>										
					  <option value="3">��1</option>	
                    </select>
                  </td>							
									
                  <td align="right">ί��״̬��</td>
                  <td>
                    <select id="sendStatus" name="_SendStatus[=]" style="width:111">
				      <option value="">ȫ��</option>
					  <option value="01">δί��</option>
					  <option value="02">�ѹ���</option>
					  <option value="11">ί�гɹ�</option>
					  <option value="12">ί��ʧ��</option>											
					  <option value="2">�ѳ���</option>
			        </select>			                            
                  </td>	                                            							
				</tr>
				<tr>
				  <td align="right"></td>
				  <td align="right"></td>
				  <td align="right"></td>
				  <td align="right">
					<input type="checkbox" name="isQryHis" id="isQryHis" onclick="isQryHis_onclick()" value="false" class="NormalInput"/><label for="isQryHis" class="hand">����ʷ</label>
					<input type="hidden" id="isQryHisHidd" name="_isQryHis[=]" value="false">
				  </td>		
				  <td align="right">��ʼ���ڣ�</td>
				  <td>
					<input type="text" id="beginDate" name="_validDate[>=][date]" ondblclick="if(!this.readOnly){setRq(this);}"  title="˫��ѡ������" readonly="true" styleId="beginDate" maxlength="10" style="ime-mode:disabled;width:111" onkeypress="return numberPass()" class="ReadOnlyString"/>
				  </td>
				  <td align="right">�������ڣ�</td>
				  <td>
					<input type="text" id="endDate" name="_validDate[<=][date]" ondblclick="if(!this.readOnly){setRq(this);}"  title="˫��ѡ������" readonly="true" styleId="endDate" maxlength="10" style="ime-mode:disabled;width:111" onkeypress="return numberPass()" class="ReadOnlyString"/>
			      </td>	
				  <td align="left">
					<html:button property="query" style="width:60" styleClass="button" onclick="javascript:return query_onclick();">
					       ��ѯ
					</html:button>
					<html:reset property="reset" style="width:60" styleClass="button">
					       ����
				    </html:reset>
				  </td>				
				  <td></td>																		
			    </tr>
			  </table>
		    </fieldset>
		  </html:form>
	    </td>
	  </tr>
    </table>
		<%@ include file="/timebargain/common/messages.jsp"%>
  </body>
</html>
