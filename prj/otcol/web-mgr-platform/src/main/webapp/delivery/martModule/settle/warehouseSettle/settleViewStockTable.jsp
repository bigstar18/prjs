<%@ page pageEncoding="GBK" %>
<table class="common" align="center" width="100%">
	    <tr>
		  <td align="right" width="50%" class="common">注册仓单号:</td>
		  <td align="left" width="50%">${result.regStockId }</td>
	    </tr>
	    <tr>
		  <td align="right" width="50%" class="common">仓单类型:</td>
		  <td align="left" width="50%">
		    <c:choose>
              <c:when test="${result.type==0 }">标准仓单</c:when>
		      <c:when test="${result.type==1 }">非标准仓单</c:when>
			  <c:when test="${result.type==2 }">临时仓单</c:when>
			</c:choose>
		  </td> 
	    </tr>
	    <tr>
		  <td align="right" width="50%" class="common">配对数量:</td>
		  <td align="left" width="50%">
		    <span id="weight">${weight }</span>
		  </td>
		</tr>
</table>