package gnnt.MEBS.integrated.mgr.action.usermanage;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import gnnt.MEBS.common.mgr.action.EcsideAction;
import gnnt.MEBS.common.mgr.common.Global;
import gnnt.MEBS.common.mgr.common.Page;
import gnnt.MEBS.common.mgr.common.PageRequest;
import gnnt.MEBS.common.mgr.common.QueryConditions;
import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.TradeModule;
import gnnt.MEBS.integrated.mgr.model.CertificateType;
import gnnt.MEBS.integrated.mgr.model.FirmCategory;
import gnnt.MEBS.integrated.mgr.model.Industry;
import gnnt.MEBS.integrated.mgr.model.SystemProps;
import gnnt.MEBS.integrated.mgr.model.Zone;
import gnnt.MEBS.integrated.mgr.model.usermanage.MFirmApply;

@Controller("mfirmApplyAction")
@Scope("request")
public class MFirmApplyAction extends EcsideAction {
	private static final long serialVersionUID = -640649317708880190L;
	@Autowired
	@Resource(name = "applyStatusMap")
	private Map<String, String> applyStatusMap;
	@Autowired
	@Resource(name = "mfirmTypeMap")
	private Map<String, String> mfirmTypeMap;
	@Resource(name = "certificateTypeMap")
	protected Map<Integer, String> certificateTypeMap;

	public MFirmApplyAction() {
		super.setEntityName(MFirmApply.class.getName());
	}

	public Map<String, String> getApplyStatusMap() {
		return this.applyStatusMap;
	}

	public Map<String, String> getMfirmTypeMap() {
		return this.mfirmTypeMap;
	}

	public Map<Integer, String> getCertificateTypeMap() {
		return this.certificateTypeMap;
	}

	public String updateAuditUnPass() throws Exception {
		String firmApplyId = this.request.getParameter("applyID");
		String auditNote = this.request.getParameter("auditNote");
		MFirmApply apply = new MFirmApply();
		apply.setApplyID(Long.valueOf(Long.parseLong(firmApplyId)));
		apply = (MFirmApply) getService().get(apply);
		if (apply.getStatus().intValue() != 0) {
			addReturnValue(1, 110702L, new Object[] { firmApplyId });
			return "success";
		}
		apply.setAuditNote(auditNote);
		apply.setStatus(Integer.valueOf(2));
		apply.setModifyTime(getService().getSysDate());
		getService().update(apply);

		addReturnValue(1, 110701L, new Object[] { firmApplyId });
		writeOperateLog(1021, "审核注册交易商：" + firmApplyId + "不通过", 1, "");
		return "success";
	}

	public String forwordaddFirmDetail() {
		this.logger.debug("enter forwordaddFirmDetail");
		this.entity = getService().get(this.entity);
		MFirmApply app = (MFirmApply) this.entity;
		String maxid = "";
		String brokerId = app.getBrokerId();
		String appid = app.getApplyID() + "";
		if ((brokerId == null) || (brokerId.length() == 0)) {
			List<Map<Object, Object>> listBySqlResult = getService()
					.getListBySql("select max(t.brokerid||'#mine#'||t.brokerageid) maxid from br_firmapply t where t.applyid='" + appid + "'");
			if ((listBySqlResult != null) && (listBySqlResult.size() > 0)) {
				Map map = (Map) listBySqlResult.get(0);
				String mb = (String) map.get("MAXID");
				String[] mbSplit = mb.trim().split("#mine#");
				System.out.println("mb >>>>>>>>>>>" + mb);
				if (mbSplit.length == 1) {
					brokerId = mbSplit[0];
					while (brokerId.length() < 5) {
						brokerId = brokerId + "0";
					}
				} else if (mbSplit.length == 2) {
					brokerId = mbSplit[1];
				}
				List<Map<Object, Object>> MaxIdBySql = getService()
						.getListBySql("select max(t.traderid) maxid from m_trader t where t.traderid like '" + brokerId + "%'");
				if ((MaxIdBySql != null) && (MaxIdBySql.size() > 0)) {
					Map maxIdMap = (Map) MaxIdBySql.get(0);
					maxid = (String) maxIdMap.get("MAXID");
					System.out.println("no memid maxid>>>>>>>>>>>>>>>>>>" + maxid);
					if (maxid != null) {
						while (maxid.length() < 9) {
							maxid = maxid + "0";
						}
					} else {
						maxid = brokerId;
						while (maxid.length() < 9) {
							maxid = maxid + "0";
						}
					}
				} else if (MaxIdBySql.size() == 0) {
					maxid = brokerId + "000000";
				}
			}
		} else {
			while (brokerId.length() < 5) {
				List<Map<Object, Object>> listBySqlResult;
				Map<Object, Object> map;
				String mb;
				String[] mbSplit;
				List<Map<Object, Object>> MaxIdBySql;
				Map<Object, Object> maxIdMap;
				brokerId = brokerId + "0";
			}
			if (brokerId.length() > 5) {
				brokerId = brokerId.substring(0, 5);
			}
			System.out.println("brokerId>>>>>>>>>>>>>>>>>>>>" + brokerId);
			List<Map<Object, Object>> listBySql = getService()
					.getListBySql("select max(t.traderid) maxid from m_trader t where t.traderid like '" + brokerId + "%'");
			if ((listBySql != null) && (listBySql.size() > 0)) {
				Map<Object, Object> map = (Map) listBySql.get(0);
				maxid = (String) map.get("MAXID");
				System.out.println("listBySql>>>>>>>>>>>>>>>>" + listBySql.size());
				System.out.println("maxid>>>>>>>>>>>>>>>>>>" + maxid);
				if (maxid != null) {
					while (maxid.length() < 9) {
						maxid = maxid + "0";
					}
				} else {
					maxid = brokerId;
					while (maxid.length() < 9) {
						maxid = maxid + "0";
					}
				}
			} else if (listBySql.size() == 0) {
				maxid = brokerId + "000000";
			}
		}
		System.out.println("final maxid >>>>>>>>>>>>" + maxid);
		if (!"".equals(maxid)) {
			try {
				String head = maxid.substring(0, 5);
				String tail = maxid.substring(5, 9);
				System.out.println("tail >>>>>>>>>> " + tail);
				long tradeidtail = Long.parseLong(tail) + 1L;
				System.out.println("tradeidtail >>>>>>>>>>>>>> " + tradeidtail);
				String tradeidtailToStr = tradeidtail + "";
				while (tradeidtailToStr.length() < 4) {
					tradeidtailToStr = "0" + tradeidtailToStr;
				}
				String tradeid = head + tradeidtailToStr;
				System.out.println(tradeid);
				this.request.setAttribute("tradeid", tradeid);
			} catch (Exception e) {
				e.printStackTrace();
				String tradeid = "";
				this.request.setAttribute("tradeid", tradeid);
			}
		}
		SystemProps systemProps = new SystemProps();
		systemProps.setPropsKey("Offset");
		systemProps = (SystemProps) getService().get(systemProps);
		String lenStr = systemProps.getFirmIdLength();

		int len = Integer.parseInt(lenStr.trim());
		this.request.setAttribute("firmLen", Integer.valueOf(len));

		String note = this.request.getParameter("auditNote");
		this.request.setAttribute("auditNote", note);

		Map<Integer, TradeModule> tradeModuleMap = getTradeMap();
		this.request.setAttribute("tradeModuleMap", tradeModuleMap);
		PageRequest<String> pageRequest = new PageRequest(" and isvisibal = 'Y' order by sortNo");

		Page<StandardModel> zone = getService().getPage(pageRequest, new Zone());

		this.request.setAttribute("zoneList", zone.getResult());

		Page<StandardModel> industry = getService().getPage(pageRequest, new Industry());

		this.request.setAttribute("industryList", industry.getResult());

		Page<StandardModel> certificateType = getService().getPage(pageRequest, new CertificateType());

		this.request.setAttribute("certificateTypeList", certificateType.getResult());

		Page<StandardModel> firmCategory = getService().getPage(pageRequest, new FirmCategory());

		this.request.setAttribute("firmCategoryList", firmCategory.getResult());

		return "success";
	}

	private Map<Integer, TradeModule> getTradeMap() {
		Map<Integer, TradeModule> tradeModuleMap = new LinkedHashMap();
		if ((Global.modelContextMap != null) && (Global.modelContextMap.size() > 0)) {
			Set<Integer> keys = Global.modelContextMap.keySet();
			for (Integer key : keys) {
				Map<Object, Object> map = (Map) Global.modelContextMap.get(key);
				if ("Y".equalsIgnoreCase((String) map.get("isFirmSet"))) {
					TradeModule value = new TradeModule();
					value.setAddFirmFn((String) map.get("addFirmFn"));
					value.setCnName((String) map.get("cnName"));
					value.setDelFirmFn((String) map.get("delFirmFn"));
					value.setEnName((String) map.get("enName"));
					value.setIsFirmSet((String) map.get("isFirmSet"));
					value.setModuleId(key);
					value.setShortName((String) map.get("shortName"));
					value.setUpdateFirmStatusFn((String) map.get("updateFirmStatusFn"));
					tradeModuleMap.put(key, value);
				}
			}
		}
		return tradeModuleMap;
	}

	public String mfirmApplyList() throws Exception {
		PageRequest<QueryConditions> pageRequest = getPageRequest(this.request);
		pageRequest.setSortColumns("order by createTime desc");
		listByLimit(pageRequest);
		return "success";
	}

	public String forwordGetPictures() {
		this.logger.debug("enter getPictures");
		String applyID = this.request.getParameter("applyID");
		String pic = this.request.getParameter("picture");
		MFirmApply apply = new MFirmApply();
		apply.setApplyID(Long.valueOf(Long.parseLong(applyID)));
		apply = (MFirmApply) getService().get(apply);
		try {
			ServletOutputStream imgout = this.response.getOutputStream();
			if ("picture".equals(pic)) {
				this.logger.debug("enter getPicture~~~~~~~~~~~");
				if (apply.getPicture() != null) {
					imgout.write(apply.getPicture());
				}
			} else if ("picturecs".equals(pic)) {
				this.logger.debug("enter getPicturecs~~~~~~~~~~");
				if (apply.getPicturecs() != null) {
					imgout.write(apply.getPicturecs());
				}
			} else if ("pictureos".equals(pic)) {
				this.logger.debug("enter getPictureos~~~~~~~~~");
				if (apply.getPictureos() != null) {
					imgout.write(apply.getPictureos());
				}
			} else if ("yingYePic".equals(pic)) {
				this.logger.debug("enter getYingYePic~~~~~~~~~");
				if (apply.getYingYePic() != null) {
					imgout.write(apply.getYingYePic());
				}
			} else if ("shuiWuPic".equals(pic)) {
				this.logger.debug("enter getShuiWuPic~~~~~~~~~");
				if (apply.getShuiWuPic() != null) {
					imgout.write(apply.getShuiWuPic());
				}
			} else if ("zuZhiPic".equals(pic)) {
				this.logger.debug("enter getZuZhiPic~~~~~~~~~");
				if (apply.getZuZhiPic() != null) {
					imgout.write(apply.getZuZhiPic());
				}
			} else if ("kaiHuPic".equals(pic)) {
				this.logger.debug("enter getKaiHuPic~~~~~~~~~");
				if (apply.getKaiHuPic() != null) {
					imgout.write(apply.getKaiHuPic());
				}
			} else {
				addReturnValue(-1, 140001L);
			}
			imgout.close();
		} catch (Exception e) {
			addReturnValue(-1, 140001L);
			e.printStackTrace();
		}
		this.logger.debug("enter getPictures end>>>>>>>>>>>>>>>");
		return null;
	}

	public String forwordGetPicturesXYS() {
		String applyID = this.request.getParameter("applyID");
		MFirmApply apply = new MFirmApply();
		apply.setApplyID(Long.valueOf(Long.parseLong(applyID)));
		apply = (MFirmApply) getService().get(apply);
		this.request.setAttribute("entity", apply);
		return "success";
	}

	public String downPicturesToDesk() {
		this.logger.debug("enter downPicturesToDesk");
		System.out.println("enter downPicturesToDesk");
		String applyID = this.request.getParameter("applyID");
		this.request.setAttribute("applyID", applyID);
		String pic = this.request.getParameter("picture");
		this.request.setAttribute("picture", pic);
		MFirmApply apply = new MFirmApply();
		apply.setApplyID(Long.valueOf(Long.parseLong(applyID)));
		apply = (MFirmApply) getService().get(apply);

		Random myrandom = new Random();
		int randomrs = myrandom.nextInt(9999);
		String randomstr = randomrs + "";
		while (randomstr.length() < 4) {
			randomstr = "0" + randomstr;
		}
		this.request.setAttribute("downFileName", randomstr + "Pic.jpg");
		String picName = randomstr + "Pic.jpg";
		System.out.println("downPic>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + picName);
		this.response.setContentType("text/html; charset=utf-8");
		this.response.setContentType("application/x-msdownload");
		this.response.setHeader("Content-Disposition", "attachment;fileName=" + picName);
		ServletOutputStream fos = null;
		try {
			fos = this.response.getOutputStream();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			if ("picture".equals(pic)) {
				this.logger.debug("enter getPicture~~~~~~~~~~~");
				if (apply.getPicture() != null) {
					fos.write(apply.getPicture());
				}
			} else if ("picturecs".equals(pic)) {
				this.logger.debug("enter getPicturecs~~~~~~~~~~");
				if (apply.getPicturecs() != null) {
					fos.write(apply.getPicturecs());
				}
			} else if ("pictureos".equals(pic)) {
				this.logger.debug("enter getPictureos~~~~~~~~~");
				if (apply.getPictureos() != null) {
					fos.write(apply.getPictureos());
				}
			} else if ("yingYePic".equals(pic)) {
				this.logger.debug("enter getYingYePic~~~~~~~~~");
				if (apply.getYingYePic() != null) {
					fos.write(apply.getYingYePic());
				}
			} else if ("shuiWuPic".equals(pic)) {
				this.logger.debug("enter getShuiWuPic~~~~~~~~~");
				if (apply.getShuiWuPic() != null) {
					fos.write(apply.getShuiWuPic());
				}
			} else if ("zuZhiPic".equals(pic)) {
				this.logger.debug("enter getZuZhiPic~~~~~~~~~");
				if (apply.getZuZhiPic() != null) {
					fos.write(apply.getZuZhiPic());
				}
			} else if ("kaiHuPic".equals(pic)) {
				this.logger.debug("enter getKaiHuPic~~~~~~~~~");
				if (apply.getKaiHuPic() != null) {
					fos.write(apply.getKaiHuPic());
				}
			} else {
				addReturnValue(-1, 140001L);
			}
			fos.flush();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		addReturnValue(1, 140002L);
		this.logger.debug("enter getPictures end>>>>>>>>>>>>>>>");
		return null;
	}

	public String forwordOpenBigPic() {
		System.out.println("enter open big picture!");
		String applyID = this.request.getParameter("applyID");
		String pic = this.request.getParameter("picture");
		this.request.setAttribute("applyID", applyID);
		this.request.setAttribute("picture", pic);
		return "success";
	}
}
