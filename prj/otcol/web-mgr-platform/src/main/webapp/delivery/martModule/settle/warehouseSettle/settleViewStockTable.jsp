<%@ page pageEncoding="GBK" %>
<table class="common" align="center" width="100%">
	    <tr>
		  <td align="right" width="50%" class="common">ע��ֵ���:</td>
		  <td align="left" width="50%">${result.regStockId }</td>
	    </tr>
	    <tr>
		  <td align="right" width="50%" class="common">�ֵ�����:</td>
		  <td align="left" width="50%">
		    <c:choose>
              <c:when test="${result.type==0 }">��׼�ֵ�</c:when>
		      <c:when test="${result.type==1 }">�Ǳ�׼�ֵ�</c:when>
			  <c:when test="${result.type==2 }">��ʱ�ֵ�</c:when>
			</c:choose>
		  </td> 
	    </tr>
	    <tr>
		  <td align="right" width="50%" class="common">�������:</td>
		  <td align="left" width="50%">
		    <span id="weight">${weight }</span>
		  </td>
		</tr>
</table>