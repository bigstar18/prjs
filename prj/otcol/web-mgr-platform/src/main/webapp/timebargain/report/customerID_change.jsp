<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK"%>
<html>
	<head>
		<script language="JavaScript" src="<c:url value="/timebargain/scripts/global.js"/>"></script>
		<script language="JavaScript" src="<c:url value="/timebargain/scripts/open.js"/>"></script>
		<c:import url="/timebargain/common/dblCustomerID.jsp"/>
		<title>
		</title>
		<script type="text/javascript"> 
function customerID_dblclick()
{
  dblCustomerID();
}

function setCustomer(customerID,customerName)
{
  reportForm.customerID.value = customerID;
  reportForm.customerName.value = customerName;
  customerID_onchange();
}

function customerID_onchange()
{
  if(trim(reportForm.customerID.value) == "")
  {
    reportForm.customerName.value = "";
    return false;
  }
  customerManager.getCustomerName(trim(reportForm.customerID.value),function(data)
  {
    if(data != null)
    {
      reportForm.customerName.value = data;
    }
    else
    {
      alert("<fmt:message key="statQuery.customerNotExist"/>");
      reportForm.customerID.value = "";
      reportForm.customerName.value = "";
    }
  });  
}

</script>
	</head>
</html>
