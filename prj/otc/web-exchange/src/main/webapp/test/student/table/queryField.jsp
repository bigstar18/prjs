<%@ page pageEncoding="GBK"%>
<%@ taglib uri="/tld/forEcSide" prefix="forEcside"%>

<!-- 查询字段 -->
<div class="headZone" id="ec_headZone">
	<table border="0" cellspacing="0" cellpadding="0" class="tableRegion"
		style="table-layout: fixed;" width="100%">
		<thead id="ec_table_head">
			<tr>
				<forEcside:td_checkBox />
				<forEcside:td columnName="id[=][long]" title="学号"></forEcside:td>
				<forEcside:td columnName="name[Like]" title="姓名" ></forEcside:td>
				<forEcside:td columnName="age[=][int]" title="年龄" ></forEcside:td>
				<forEcside:td columnName="grade[=]" title="年级"></forEcside:td>
				<forEcside:td columnName="speciality[Like]" title="专业"></forEcside:td>
			</tr>
		</thead>
	</table>
</div>
<!-- 查询字段 -->
