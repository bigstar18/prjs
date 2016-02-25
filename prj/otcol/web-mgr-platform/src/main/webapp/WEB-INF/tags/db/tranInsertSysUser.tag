<%@ tag body-content="empty" dynamic-attributes="columnAttr" %>
<%@ include file="../init.tagf" %>
<%//添加系统用户信息%>
<%@ attribute name="sysuser" required="true" %>
<%@ attribute name="remark" required="true" %>
<%@ attribute name="password" required="true" %>
<%@ attribute name="accessnum" required="true" %>
<%@ attribute name="createtime" required="true" %>
<sql_db:transaction dataSource="${tags_db_dataSource}">
    <sql_db:update>
        insert into v_sysuser(sysuser,remark,password,accessnum,createtime) values('${sysuser}',
	'${remark}','${password}',${accessnum},to_date('${createtime}','yyyy-mm-dd'))
    </sql_db:update>
    <sql_db:update>
        insert into v_sysuserandpartition(sysuser,tradepartition) values('${sysuser}',-1)
    </sql_db:update>
</sql_db:transaction>
