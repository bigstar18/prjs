<%@ page contentType="text/html;charset=GBK" %>
<?xml version='1.0' encoding='GBK'?>
<Context>
<%

	response.setContentType("text/xml");

	response.setHeader("Cache-Control","no-cache");
	response.setHeader("Cache-Control","no-store");
	response.setHeader("Pragma","no-cache");
	String mod = null;
	gnnt.MEBS.vendue.server.dao.TradeDAO to = gnnt.MEBS.vendue.server.dao.TradeDAOFactory.getDAO();
	int cpid=Integer.valueOf(request.getParameter("cpid"));
		//out.println("<typeid>"+ctvs[i].id+"</typeid>");  //商品种类id
		gnnt.MEBS.vendue.server.vo.QueryValue qv2 = new gnnt.MEBS.vendue.server.vo.QueryValue();
  		qv2.pageIndex = 1;
 		qv2.pageSize = 100;  //每页显示100条
 		qv2.filter += " and cpid = "+cpid+" order by id ";
		gnnt.MEBS.vendue.server.vo.CommodityPropertyVO[] cpvs = to.getCommodityProperty(qv2);  //取商品属性数组

		gnnt.MEBS.vendue.server.vo.QueryValue qv = new gnnt.MEBS.vendue.server.vo.QueryValue();
		qv.filter = "";
		qv.pageIndex = 1;
		qv.pageSize = 100;  //每页显示10条
		qv.filter += " and id= "+cpid;
		gnnt.MEBS.vendue.server.vo.CommodityTypeVO[] ctvs = to.getCommodityType(qv);
		out.println("<dw>"+ctvs[0].str1+"</dw>");    //商品数量单位
		for(int j=0;j<cpvs.length;j++)
		{
			out.println("<parameter>");
			out.println("<p>"+cpvs[j].id+"</p>");    //商品属性id
			out.println("<p>"+cpvs[j].name+"</p>");  //商品属性名称
			out.println("<p>"+cpvs[j].type+"</p>");  //商品属性种类
			out.println("<p>"+cpvs[j].isnull+"</p>");  //商品属性是否可以为空
			out.println("<p>"+cpvs[j].charvartext+"</p>");  //商品属性下拉框选项
			out.println("<p>"+cpvs[j].str1+"</p>");  //商品扩展属性1
			out.println("<p>"+cpvs[j].str2+"</p>");  //商品扩展属性2
			out.println("<p>"+cpvs[j].str3+"</p>");  //商品扩展属性3
			out.println("<p>"+cpvs[j].str4+"</p>");  //商品扩展属性4
			out.println("<p>"+cpvs[j].str5+"</p>");  //商品扩展属性5
			out.println("</parameter>");
		}
		
		
		


%></Context>
