package gnnt.MEBS.timebargain.manage.webapp.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.dao.DataIntegrityViolationException;

import gnnt.MEBS.timebargain.manage.model.TradeTime;
import gnnt.MEBS.timebargain.manage.service.LookupManager;
import gnnt.MEBS.timebargain.manage.service.MarketManager;
import gnnt.MEBS.timebargain.manage.service.StatQueryManager;
import gnnt.MEBS.timebargain.manage.service.TraderManager;
import gnnt.MEBS.timebargain.manage.webapp.form.TradeTimeForm;
import gnnt.MEBS.timebargain.server.model.SystemStatus;

public class TradeTimeAction extends BaseAction {
	public ActionForward search(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse) throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'search' method");
		}
		TraderManager localTraderManager = (TraderManager) getBean("traderManager");
		try {
			List localList = localTraderManager.getTradeTimes(null);
			paramHttpServletRequest.setAttribute("tradeTimeList", localList);
			paramHttpServletRequest.setAttribute("GATHERBID_STATUS", CommonDictionary.GATHERBID_STATUS);
			saveToken(paramHttpServletRequest);
		} catch (Exception localException) {
			this.log.error("查询TradeTime表出错：" + localException.getMessage());
			paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
		}
		return paramActionMapping.findForward("list");
	}

	public ActionForward edit(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse) throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'edit' method");
		}
		TraderManager localTraderManager = (TraderManager) getBean("traderManager");
		String str1 = paramHttpServletRequest.getParameter("crud");
		String str2 = paramHttpServletRequest.getParameter("sectionID");
		TradeTime localTradeTime = null;
		try {
			if (str1.equals("update")) {
				if ((str2 != null) && (!"".equals(str2))) {
					localTradeTime = localTraderManager.getTradeTime(str2);
					List localObject1 = localTraderManager.getTradeTimeBreed(str2);
					paramHttpServletRequest.setAttribute("list", localObject1);
					String str3 = localTradeTime.getModifyTime();
					String[] localObject2 = str3.split(" ");
					localTradeTime.setModifyTime(localObject2[0]);
					this.log.debug("edit TradeTime.sectionID:" + str2);
				}
			} else {
				localTradeTime = new TradeTime();
			}
			Object localObject1 = new TradeTimeForm();
			if (str1.equals("update")) {
				((TradeTimeForm) localObject1).setSectionID(localTradeTime.getSectionID().toString());
				((TradeTimeForm) localObject1).setStatus(localTradeTime.getStatus().toString());
				if ((localTradeTime.getBreedID() != null) && (!"".equals(localTradeTime.getBreedID()))) {
					((TradeTimeForm) localObject1).setBreedID(localTradeTime.getBreedID().toString());
				}
				if ((localTradeTime.getBreedName() != null) && (!"".equals(localTradeTime.getBreedName()))) {
					((TradeTimeForm) localObject1).setBreedName(localTradeTime.getBreedName());
				}
			} else {
				((TradeTimeForm) localObject1).setSectionID("");
				((TradeTimeForm) localObject1).setStatus("");
				((TradeTimeForm) localObject1).setBreedID("");
				((TradeTimeForm) localObject1).setBreedName("");
			}
			((TradeTimeForm) localObject1).setName(localTradeTime.getName());
			((TradeTimeForm) localObject1).setStartTime(localTradeTime.getStartTime());
			((TradeTimeForm) localObject1).setEndTime(localTradeTime.getEndTime());
			if (localTradeTime.getGatherBid() != null) {
				((TradeTimeForm) localObject1).setGatherBid(localTradeTime.getGatherBid().toString());
			}
			((TradeTimeForm) localObject1).setBidStartTime(localTradeTime.getBidStartTime());
			((TradeTimeForm) localObject1).setBidEndTime(localTradeTime.getBidEndTime());
			((TradeTimeForm) localObject1).setModifyTime(localTradeTime.getModifyTime());
			String str3 = "";
			if (((TradeTimeForm) localObject1).getGatherBid() != null) {
				if ("0".equals(((TradeTimeForm) localObject1).getGatherBid())) {
					str3 = "1";
				} else if ("1".equals(((TradeTimeForm) localObject1).getGatherBid())) {
					str3 = "2";
				}
			}
			paramHttpServletRequest.setAttribute("type", str3);
			paramHttpServletRequest.setAttribute("modifyTime", ((TradeTimeForm) localObject1).getModifyTime());
			((TradeTimeForm) localObject1).setCrud(str1);
			Object localObject2 = getTradeTimeType(paramHttpServletRequest);
			((TradeTimeForm) localObject1).setTradeTimeType((String) localObject2);
			updateFormBean(paramActionMapping, paramHttpServletRequest, (ActionForm) localObject1);
			saveToken(paramHttpServletRequest);
		} catch (Exception localException) {
			this.log.error("查询TradeTime表出错：" + localException.getMessage());
			localException.printStackTrace();
			paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
		}
		return paramActionMapping.findForward("edit");
	}

	public ActionForward save(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse) throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'save' method");
		}
		String str1 = paramHttpServletRequest.getParameter("sectionID");
		String str2 = paramHttpServletRequest.getParameter("name");
		String str3 = paramHttpServletRequest.getParameter("startTime");
		String str4 = paramHttpServletRequest.getParameter("endTime");
		String str5 = paramHttpServletRequest.getParameter("status");
		String str6 = paramHttpServletRequest.getParameter("modifyTime");
		String str7 = paramHttpServletRequest.getParameter("crud");
		String str8 = paramHttpServletRequest.getParameter("bidStartTime");
		String str9 = paramHttpServletRequest.getParameter("bidEndTime");
		String str10 = paramHttpServletRequest.getParameter("gatherBid");
		TraderManager localTraderManager = (TraderManager) getBean("traderManager");
		TradeTime localTradeTime = new TradeTime();
		if ((str1 != null) && (!"".equals(str1))) {
			localTradeTime.setSectionID(Integer.valueOf(Integer.parseInt(str1)));
		}
		if ((str2 != null) && (!"".equals(str2))) {
			localTradeTime.setName(str2);
		}
		if ((str3 != null) && (!"".equals(str3))) {
			localTradeTime.setStartTime(str3);
		}
		if ((str4 != null) && (!"".equals(str4))) {
			localTradeTime.setEndTime(str4);
		}
		if ((str5 != null) && (!"".equals(str5))) {
			localTradeTime.setStatus(Short.valueOf(Short.parseShort(str5)));
		}
		if ((str6 != null) && (!"".equals(str6))) {
			localTradeTime.setModifyTime(str6);
		}
		if ((str7 != null) && (!"".equals(str7))) {
			localTradeTime.setCrud(str7);
		}
		if ((str8 != null) && (!"".equals(str8))) {
			localTradeTime.setBidStartTime(str8);
		}
		if ((str9 != null) && (!"".equals(str9))) {
			localTradeTime.setBidEndTime(str9);
		}
		if ((str10 != null) && (!"".equals(str10))) {
			localTradeTime.setGatherBid(Short.valueOf(Short.parseShort(str10)));
		}
		try {
			String str11 = "";
			MarketManager localMarketManager = (MarketManager) getBean("marketManager");
			List localList = localMarketManager.getMarketById(null);
			Object localObject1;
			if ((localList != null) && (localList.size() > 0)) {
				localObject1 = (Map) localList.get(0);
				if (((Map) localObject1).get("TradeTimeType") != null) {
					str11 = ((Map) localObject1).get("TradeTimeType") + "";
				}
			}
			Object localObject2;
			Object localObject3;
			if ("0".equals(str11)) {
				localObject1 = null;
				localObject2 = localTraderManager.getTradeTimeId(str1);
				if ((localObject2 != null) && (((List) localObject2).size() > 0)) {
					localObject3 = (Map) ((List) localObject2).get(0);
					String str12 = ((Map) localObject3).get("id") + "";
					System.out.println(")))))))))))))id: " + str12);
					if ((!"null".equals(str12)) && (!"".equals(str12))) {
						localObject1 = localTraderManager.getTradeTime(str12);
						int j = str3.compareTo(((TradeTime) localObject1).getEndTime());
						if (j < 0) {
							throw new Exception("交易开始时间应该晚于上个交易节的结束时间！");
						}
					}
				}
			}
			if (isTokenValid(paramHttpServletRequest)) {
				if (str7.trim().equals("create")) {
					localTraderManager.insertTradeTime(localTradeTime);
					addSysLog(paramHttpServletRequest, "保存交易节信息[" + str1 + "]");
					paramHttpServletRequest.setAttribute("prompt", "操作成功！");
					paramHttpServletRequest.setAttribute("ifSave", "save");
				} else if (str7.trim().equals("update")) {
					localObject1 = localTraderManager.getSysdate();
					localObject2 = (StatQueryManager) getBean("statQueryManager");
					localObject3 = ((StatQueryManager) localObject2).getSystemStatusObject();
					int i = ((SystemStatus) localObject3).getStatus();
					localTraderManager.updateTradeTime(localTradeTime);
					addSysLog(paramHttpServletRequest, "修改交易节信息[" + str1 + "]");
					paramHttpServletRequest.setAttribute("prompt", "操作成功！");
					paramHttpServletRequest.setAttribute("ifSave", "save");
				}
			}
			resetToken(paramHttpServletRequest);
		} catch (Exception localException) {
			this.log.error("===>save err：" + localException);
			localException.printStackTrace();
			paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
			return search(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
		}
		return search(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
	}

	public ActionForward delete(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse) throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'delete' method");
		}
		TraderManager localTraderManager = (TraderManager) getBean("traderManager");
		String[] arrayOfString = paramHttpServletRequest.getParameterValues("itemlist");
		int i = 0;
		if (arrayOfString != null) {
			this.log.debug("==ids.length:" + arrayOfString.length);
			String str2 = "";
			for (int j = 0; j < arrayOfString.length; j++) {
				String str1 = arrayOfString[j];
				try {
					List localList1 = localTraderManager.getTradeTimeBreed(str1);
					List localList2 = localTraderManager.getTradeTimeRelBreed(str1);
					if (localList1.size() > 0) {
						str2 = str2 + "交易节[" + str1 + "]与商品数据关联，删除失败！";
						break;
					}
					if (localList2.size() > 0) {
						str2 = str2 + "交易节[" + str1 + "]与品种数据关联，删除失败！";
						break;
					}
					localTraderManager.deleteTradeTimeById(str1);
					paramHttpServletRequest.setAttribute("ifSave", "save");
					addSysLog(paramHttpServletRequest, "删除交易节[" + str1 + "]");
					i++;
				} catch (DataIntegrityViolationException localDataIntegrityViolationException) {
					str2 = str2 + "[" + str1 + "]与其他数据关联，删除失败！";
					break;
				}
			}
			if (str2.equals("")) {
				str2 = str2 + "删除交易节成功，共删除" + i + "条纪录！";
			}
			paramHttpServletRequest.setAttribute("prompt", str2);
			paramHttpServletRequest.setAttribute("zhushi", "");
		}
		return search(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
	}

	public ActionForward updateStatus(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse) throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'updateStatus' method");
		}
		TraderManager localTraderManager = (TraderManager) getBean("traderManager");
		String[] arrayOfString = paramHttpServletRequest.getParameterValues("itemlist");
		String str1 = paramHttpServletRequest.getParameter("crud");
		int i = 0;
		if (arrayOfString != null) {
			this.log.debug("==ids.length:" + arrayOfString.length);
			TradeTime localTradeTime = new TradeTime();
			String str3 = "";
			for (int j = 0; j < arrayOfString.length; j++) {
				String str2 = arrayOfString[j];
				try {
					localTradeTime.setSectionID(Integer.valueOf(Integer.parseInt(str2)));
					if ("correct".equals(str1)) {
						localTradeTime.setStatus(Short.valueOf(Short.parseShort("1")));
					} else if ("incorrect".equals(str1)) {
						localTradeTime.setStatus(Short.valueOf(Short.parseShort("0")));
					}
					localTraderManager.updateStatusT(localTradeTime);
					paramHttpServletRequest.setAttribute("ifSave", "save");
					addSysLog(paramHttpServletRequest, "修改状态[" + str2 + "]");
					i++;
				} catch (DataIntegrityViolationException localDataIntegrityViolationException) {
					str3 = str3 + str2 + ",";
					paramHttpServletRequest.setAttribute("prompt", "[" + str2 + "]修改失败！");
				}
			}
			if (!str3.equals("")) {
				str3 = str3.substring(0, str3.length() - 1);
				str3 = str3 + "修改失败！";
			}
			str3 = str3 + "成功修改" + i + "条纪录！";
			paramHttpServletRequest.setAttribute("prompt", str3);
		}
		return search(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
	}

	public ActionForward onLine(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse) throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'onLine' method");
		}
		try {
			AgencyRMIBean localAgencyRMIBean = new AgencyRMIBean(paramHttpServletRequest);
			localAgencyRMIBean.refreshTradeTime();
		} catch (Exception localException) {
			localException.printStackTrace();
			paramHttpServletRequest.setAttribute("prompt", "生效失败！");
		}
		return paramActionMapping.findForward("operate");
	}

	public ActionForward editNotTradeDay(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse) {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'editNotTradeDay' method");
		}
		TradeTimeForm localTradeTimeForm = (TradeTimeForm) paramActionForm;
		String str1 = "";
		TraderManager localTraderManager = (TraderManager) getBean("traderManager");
		TradeTime localTradeTime = null;
		try {
			localTradeTime = localTraderManager.getNotTradeDay();
			if (localTradeTime != null) {
				str1 = "update";
			} else {
				str1 = "create";
				localTradeTime = new TradeTime();
			}
			localTradeTimeForm = (TradeTimeForm) convert(localTradeTime);
			localTradeTimeForm.setCrud(str1);
			StringBuffer localStringBuffer = new StringBuffer();
			if (localTradeTimeForm.getWeek() != null) {
				for (int i = 0; i < localTradeTimeForm.getWeek().length; i++) {
					if (i != localTradeTimeForm.getWeek().length - 1) {
						localStringBuffer.append(localTradeTimeForm.getWeek()[i]).append(",");
					} else {
						localStringBuffer.append(localTradeTimeForm.getWeek()[i]);
					}
				}
			}
			String str2 = localStringBuffer.toString();
			paramHttpServletRequest.setAttribute("weeks", str2);
			String str3 = getTradeTimeType(paramHttpServletRequest);
			localTradeTimeForm.setTradeTimeType(str3);
			updateFormBean(paramActionMapping, paramHttpServletRequest, localTradeTimeForm);
		} catch (Exception localException) {
			localException.printStackTrace();
		}
		return paramActionMapping.findForward("editNotTradeDay");
	}

	public ActionForward saveNotTradeDay(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse) {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'saveNotTradeDay' method");
		}
		TradeTimeForm localTradeTimeForm = (TradeTimeForm) paramActionForm;
		TraderManager localTraderManager = (TraderManager) getBean("traderManager");
		String[] arrayOfString = paramHttpServletRequest.getParameterValues("week");
		TradeTime localTradeTime = new TradeTime();
		localTradeTime.setWeek(arrayOfString);
		localTradeTime.setDay(localTradeTimeForm.getDay());
		String str = localTradeTimeForm.getCrud();
		if ("create".equals(str)) {
			localTraderManager.insertNotTradeDay(localTradeTime);
			addSysLog(paramHttpServletRequest, "增加非交易日[1]");
		} else if ("update".equals(str)) {
			localTraderManager.updateNotTradeDay(localTradeTime);
			addSysLog(paramHttpServletRequest, "修改非交易日[1]");
		}
		return editNotTradeDay(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
	}

	public ActionForward editDaySection(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse) {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'editDaySection' method");
		}
		TradeTimeForm localTradeTimeForm = (TradeTimeForm) paramActionForm;
		TraderManager localTraderManager = (TraderManager) getBean("traderManager");
		try {
			String str = paramHttpServletRequest.getParameter("weeks");
			Map localMap = localTraderManager.getDaySectionInfo();
			paramHttpServletRequest.setAttribute("weeks", str);
			paramHttpServletRequest.setAttribute("mapWeek", localMap);
			updateFormBean(paramActionMapping, paramHttpServletRequest, localTradeTimeForm);
		} catch (Exception localException) {
			localException.printStackTrace();
		}
		return paramActionMapping.findForward("editDaySection");
	}

	public ActionForward saveDaySection(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse) {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'saveDaySection' method");
		}
		TradeTimeForm localTradeTimeForm = (TradeTimeForm) paramActionForm;
		TraderManager localTraderManager = (TraderManager) getBean("traderManager");
		String[] arrayOfString1 = paramHttpServletRequest.getParameterValues("week1");
		String[] arrayOfString2 = paramHttpServletRequest.getParameterValues("week2");
		String[] arrayOfString3 = paramHttpServletRequest.getParameterValues("week3");
		String[] arrayOfString4 = paramHttpServletRequest.getParameterValues("week4");
		String[] arrayOfString5 = paramHttpServletRequest.getParameterValues("week5");
		String[] arrayOfString6 = paramHttpServletRequest.getParameterValues("week6");
		String[] arrayOfString7 = paramHttpServletRequest.getParameterValues("week7");
		HashMap localHashMap = new HashMap();
		for (int i = 1; i < 8; i++) {
			String str = i + "";
			if ("1".equals(str)) {
				localHashMap.put(str, arrayOfString1);
			}
			if ("2".equals(str)) {
				localHashMap.put(str, arrayOfString2);
			}
			if ("3".equals(str)) {
				localHashMap.put(str, arrayOfString3);
			}
			if ("4".equals(str)) {
				localHashMap.put(str, arrayOfString4);
			}
			if ("5".equals(str)) {
				localHashMap.put(str, arrayOfString5);
			}
			if ("6".equals(str)) {
				localHashMap.put(str, arrayOfString6);
			}
			if ("7".equals(str)) {
				localHashMap.put(str, arrayOfString7);
			}
		}
		try {
			localTraderManager.updateDaySection(localHashMap);
			addSysLog(paramHttpServletRequest, "修改每日交易节");
			paramHttpServletRequest.setAttribute("prompt", "操作成功！");
		} catch (Exception localException) {
			localException.printStackTrace();
			paramHttpServletRequest.setAttribute("prompt", "操作失败！");
		}
		return editNotTradeDay(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
	}

	private void getIDSelectAttribute(HttpServletRequest paramHttpServletRequest) throws Exception {
		LookupManager localLookupManager = (LookupManager) getBean("lookupManager");
		paramHttpServletRequest.setAttribute("IDSelect", localLookupManager.getSelectLabelValueByTable("TradeTime", "ID", "ID", " order by ID "));
	}

	private String getTradeTimeType(HttpServletRequest paramHttpServletRequest) throws Exception {
		String str = "";
		MarketManager localMarketManager = (MarketManager) getBean("marketManager");
		List localList = localMarketManager.getMarketById(null);
		if ((localList != null) && (localList.size() > 0)) {
			Map localMap = (Map) localList.get(0);
			if (localMap.get("TradeTimeType") != null) {
				str = localMap.get("TradeTimeType").toString();
			}
		}
		return str;
	}
}
