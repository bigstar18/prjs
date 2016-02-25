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
  statQueryForm.customerID.value = customerID;
  statQueryForm.customerName.value = customerName;
  customerID_onchange();
}

function customerID_onchange()
{
  if(trim(statQueryForm.customerID.value) == "")
  {
    statQueryForm.customerName.value = "";
    return false;
  }
  customerManager.getCustomerName(trim(statQueryForm.customerID.value),function(data)
  {
    if(data != null)
    {
      statQueryForm.customerName.value = data;
    }
    else
    {
      alert("交易商不存在！");
      statQueryForm.customerID.value = "";
      statQueryForm.customerName.value = "";
    }
  });  
}

</script>
	</head>
</html>
