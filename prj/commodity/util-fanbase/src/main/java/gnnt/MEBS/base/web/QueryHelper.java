package gnnt.MEBS.base.web;

import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.util.WebUtils;

import gnnt.MEBS.base.exception.BaseException;
import gnnt.MEBS.base.util.PageInfo;
import gnnt.MEBS.base.util.QueryConditions;
import gnnt.MEBS.base.util.Utils;

public class QueryHelper {
	private static final transient Log logger = LogFactory.getLog(QueryHelper.class);

	public static QueryConditions getQueryConditionsFromRequest(HttpServletRequest request) {
		logger.debug("enter getQueryConditionsFromReq...");

		QueryConditions qc = null;
		Map keys = WebUtils.getParametersStartingWith(request, "_");
		if ((keys != null) && (keys.size() > 0)) {
			logger.debug("parameter size:" + keys.size());

			Pattern pOperatorDatatype = Pattern.compile("(.+?)\\[(.+)]\\[(.+)]");

			Pattern pOperator = Pattern.compile("(.+?)\\[(.+)]");
			for (Iterator it = keys.keySet().iterator(); it.hasNext();) {
				String key = (String) it.next();
				Object value = keys.get(key);
				logger.debug("parameter:" + key);
				Matcher m = pOperatorDatatype.matcher(key);
				String type;
				String param;
				String op;
				if (m.matches()) {
					param = m.group(1);
					op = m.group(2);
					type = m.group(3);
				} else {
					m = pOperator.matcher(key);
					if (m.matches()) {
						param = m.group(1);
						op = m.group(2);
						type = null;
					} else {
						param = null;
						op = null;
						type = null;
					}
				}
				if ((param != null) && (value != null) && (((String) value).length() > 0)) {
					if (qc == null) {
						qc = new QueryConditions();
					}
					if (type != null) {
						try {
							value = Utils.convert(value, type);
						} catch (BaseException be) {
							logger.error("Utils.convert() failed!");
						}
					}
					logger.debug("parameter:" + param);
					logger.debug("operator:" + op);
					logger.debug("datatype:" + type);
					logger.debug("value:" + value);
					qc.addCondition(param, op, value);
				}
			}
		}
		return qc;
	}

	public static QueryConditions createQueryConditionsFromRequest(HttpServletRequest request) {
		QueryConditions qc = getQueryConditionsFromRequest(request);
		if (qc == null) {
			qc = new QueryConditions();
		}
		return qc;
	}

	public static PageInfo getPageInfoFromRequest(HttpServletRequest request) {
		logger.debug("enter getPageInfoFromRequest...");

		PageInfo pageInfo = null;
		int pageSize = Utils.parseInt(request.getParameter("pageSize"));
		int pageNo = Utils.parseInt(request.getParameter("pageNo"), 1);
		if (pageSize > 0) {
			pageInfo = new PageInfo();

			pageInfo.setPageSize(pageSize);
			pageInfo.setPageNo(pageNo);

			String[] orderFields = request.getParameterValues("orderField");
			String[] orderTypes = request.getParameterValues("orderDesc");
			if ((orderFields != null) && (orderTypes != null)) {
				for (int i = 0; i < orderFields.length; i++) {
					logger.debug("orderField " + i + "=" + orderFields[i]);
					logger.debug("orderDesc " + i + "=" + orderTypes[i]);
					String orderField = orderFields[i];
					boolean orderDesc = Utils.parseBoolean(orderTypes[i]);
					pageInfo.addOrderField(orderField, orderDesc);
				}
			} else {
				String orderField = request.getParameter("orderField");
				String orderType = request.getParameter("orderDesc");
				logger.debug("orderField =" + orderField);
				logger.debug("orderDesc =" + orderType);
				if ((orderField != null) && (orderField.length() > 0)) {
					boolean orderDesc = Utils.parseBoolean(orderType);
					pageInfo.addOrderField(orderField, orderDesc);
				} else {
					return null;
				}
			}
			logger.debug("pageInfo: pageNo = " + pageInfo.getPageNo() + ", pageSize = " + pageInfo.getPageSize());
		}
		return pageInfo;
	}

	public static String getTargetView(HttpServletRequest request, String defaultTarget) {
		String target = request.getParameter("targetView");
		if ((target == null) || (target.length() == 0)) {
			target = defaultTarget;
		}
		return target;
	}
}
