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
		//out.println("<typeid>"+ctvs[i].id+"</typeid>");  //��Ʒ����id
		gnnt.MEBS.vendue.server.vo.QueryValue qv2 = new gnnt.MEBS.vendue.server.vo.QueryValue();
  		qv2.pageIndex = 1;
 		qv2.pageSize = 100;  //ÿҳ��ʾ100��
 		qv2.filter += " and cpid = "+cpid+" order by id ";
		gnnt.MEBS.vendue.server.vo.CommodityPropertyVO[] cpvs = to.getCommodityProperty(qv2);  //ȡ��Ʒ��������

		gnnt.MEBS.vendue.server.vo.QueryValue qv = new gnnt.MEBS.vendue.server.vo.QueryValue();
		qv.filter = "";
		qv.pageIndex = 1;
		qv.pageSize = 100;  //ÿҳ��ʾ10��
		qv.filter += " and id= "+cpid;
		gnnt.MEBS.vendue.server.vo.CommodityTypeVO[] ctvs = to.getCommodityType(qv);
		out.println("<dw>"+ctvs[0].str1+"</dw>");    //��Ʒ������λ
		for(int j=0;j<cpvs.length;j++)
		{
			out.println("<parameter>");
			out.println("<p>"+cpvs[j].id+"</p>");    //��Ʒ����id
			out.println("<p>"+cpvs[j].name+"</p>");  //��Ʒ��������
			out.println("<p>"+cpvs[j].type+"</p>");  //��Ʒ��������
			out.println("<p>"+cpvs[j].isnull+"</p>");  //��Ʒ�����Ƿ����Ϊ��
			out.println("<p>"+cpvs[j].charvartext+"</p>");  //��Ʒ����������ѡ��
			out.println("<p>"+cpvs[j].str1+"</p>");  //��Ʒ��չ����1
			out.println("<p>"+cpvs[j].str2+"</p>");  //��Ʒ��չ����2
			out.println("<p>"+cpvs[j].str3+"</p>");  //��Ʒ��չ����3
			out.println("<p>"+cpvs[j].str4+"</p>");  //��Ʒ��չ����4
			out.println("<p>"+cpvs[j].str5+"</p>");  //��Ʒ��չ����5
			out.println("</parameter>");
		}
		
		
		


%></Context>
