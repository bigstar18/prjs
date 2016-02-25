<%@ tag body-content="empty" dynamic-attributes="columnAttr" %>
<%@ include file="../init.tagf" %>
<%//电汇凭证信息%>
<%@ attribute name="id" required="true" %>
<%@ attribute name="contractid" required="true" %>
<%@ attribute name="name" required="true" %>
<%@ attribute name="account" required="true" %>
<%@ attribute name="province" required="true" %>
<%@ attribute name="city" required="true" %>
<%@ attribute name="bank" required="true" %>
<%@ attribute name="money" required="true" %>
<%@ attribute name="remark" required="true" %>
<%@ attribute name="status" required="true" %>
<%@ attribute name="opertime" required="true" %>
<sql_db:transaction dataSource="${tags_db_dataSource}">
    <sql_db:update>
        update v_acceptpayment set status=0 where contractid=${contractid}
    </sql_db:update>
    <sql_db:update>
        insert into v_acceptpayment(id,contractid,name,account,province,city,bank,money,remark,status,opertime)
        values(${id},${contractid},'${name}','${account}','${province}','${city}','${bank}','${money}',
	'${remark}',${status},to_date('${opertime}','yyyy-mm-dd hh24:mi:ss'))
    </sql_db:update>
</sql_db:transaction>
