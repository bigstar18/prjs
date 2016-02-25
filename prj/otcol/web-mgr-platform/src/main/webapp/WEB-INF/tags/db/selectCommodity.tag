<%@ tag body-content="scriptless" %>
<%@ attribute name="id"   required="true"  %>
<%@ attribute name="col"   required="true"  %>
<%@ include file="../init.tagf" %>

<sql_db:query dataSource="${tags_db_dataSource}" var="v_result">
    SELECT commodityid, name, createtime, modifytime, status, intomarketdate, settledate, type, handlefeerate, forceclosefeerate, settlefeerate, pricemovelimit, pricescopelimit, vataxrate, maxorderqty, unitconversion, minpricemove, minquantitymove, maxprice, minprice, openprice FROM v_COMMODITY where commodityid='${id}'
</sql_db:query>

<c_db:forEach var="v_row" items="${v_result.rows}">
    <c_db:if test="${col=='name'}">${v_row.name}</c_db:if>
</c_db:forEach>
