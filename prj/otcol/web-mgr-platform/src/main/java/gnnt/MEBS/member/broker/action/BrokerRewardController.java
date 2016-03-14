package gnnt.MEBS.member.broker.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import gnnt.MEBS.base.copy.ParamUtil;
import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.base.query.QueryHelper;
import gnnt.MEBS.base.util.Arith;
import gnnt.MEBS.member.broker.model.BrokerReward;
import gnnt.MEBS.member.broker.model.BrokerRewardProps;
import gnnt.MEBS.member.broker.services.BrokerRewardPropsService;
import gnnt.MEBS.member.broker.services.BrokerRewardService;
import gnnt.MEBS.member.broker.util.SysData;

public class BrokerRewardController extends BaseController {
	private final transient Log logger = LogFactory.getLog(BrokerRewardController.class);

	public ModelAndView brokerRewardList(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse) throws Exception {
		this.logger.debug("Entering 'brokerRewardList' method");
		QueryConditions localQueryConditions = QueryHelper.getQueryConditionsFromRequest(paramHttpServletRequest);
		PageInfo localPageInfo = QueryHelper.getPageInfoFromRequest(paramHttpServletRequest);
		if (localPageInfo == null) {
			localPageInfo = new PageInfo(1, 15, "brokerID", false);
			localPageInfo.addOrderField("occurDate", false);
		}
		BrokerRewardService localBrokerRewardService = (BrokerRewardService) SysData.getBean("m_brokerRewardService");
		String str1 = paramHttpServletRequest.getParameter("exportExcel");
		String str2 = paramHttpServletRequest.getParameter("exportAll");
		ModelAndView localModelAndView = null;
		if ((str1 == null) || ("".equals(str1))) {
			List localObject = localBrokerRewardService.getBrokerRewardList(localQueryConditions, localPageInfo);
			localModelAndView = new ModelAndView("member/broker/brokerReward/brokerReward", "resultList", localObject);
		} else if ((str2 == null) || ("".equals(str2))) {
			List localObject = localBrokerRewardService.getBrokerRewardList(localQueryConditions, localPageInfo);
			localModelAndView = new ModelAndView("member/broker/brokerReward/brokerRewardExcel", "resultList", localObject);
		} else {
			List localObject = localBrokerRewardService.getBrokerRewardList(localQueryConditions, null);
			localModelAndView = new ModelAndView("member/broker/brokerReward/brokerRewardExcel", "resultList", localObject);
		}
		Object localObject = WebUtils.getParametersStartingWith(paramHttpServletRequest, "_");
		localModelAndView.addObject("pageInfo", localPageInfo);
		localModelAndView.addObject("oldParams", localObject);
		return localModelAndView;
	}

	public ModelAndView brokerRewardPropsGoUpdate(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
			throws Exception {
		this.logger.debug("Entering 'brokerRewardGoUpdate' method");
		BrokerRewardPropsService localBrokerRewardPropsService = (BrokerRewardPropsService) SysData.getBean("m_brokerRewardPropsService");
		BrokerRewardProps localBrokerRewardProps = localBrokerRewardPropsService.getBrokerRewardProps();
		localBrokerRewardProps.setFirstPayRate(Arith.mul(localBrokerRewardProps.getFirstPayRate(), 100.0F));
		localBrokerRewardProps.setSecondPayRate(Arith.mul(localBrokerRewardProps.getSecondPayRate(), 100.0F));
		localBrokerRewardProps.setRewardRate(Arith.mul(localBrokerRewardProps.getRewardRate(), 100.0F));
		ModelAndView localModelAndView = new ModelAndView("member/broker/brokerRewardProps/brokerRewardPropsUpdate", "brokerReward",
				localBrokerRewardProps);
		localModelAndView.addObject("type", localBrokerRewardProps.getAutoPay());
		localModelAndView.addObject("payPeriod", localBrokerRewardProps.getPayPeriod() + "");
		return localModelAndView;
	}

	public ModelAndView brokerRewardPropsUpdate(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
			throws Exception {
		this.logger.debug("Entering 'brokerRewardUpdate' method");
		BrokerRewardProps localBrokerRewardProps = new BrokerRewardProps();
		ParamUtil.bindData(paramHttpServletRequest, localBrokerRewardProps);
		localBrokerRewardProps.setFirstPayRate(localBrokerRewardProps.getFirstPayRate() / 100.0D);
		localBrokerRewardProps.setSecondPayRate(localBrokerRewardProps.getSecondPayRate() / 100.0D);
		localBrokerRewardProps.setRewardRate(localBrokerRewardProps.getRewardRate() / 100.0D);
		BrokerRewardPropsService localBrokerRewardPropsService = (BrokerRewardPropsService) SysData.getBean("m_brokerRewardPropsService");
		localBrokerRewardPropsService.updateBrokerRewardPropsByPrimaryKey(localBrokerRewardProps);
		paramHttpServletRequest.setAttribute("resultMsg", "修改成功！");
		return brokerRewardPropsGoUpdate(paramHttpServletRequest, paramHttpServletResponse);
	}

	public ModelAndView brokerRewardPropsList(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
			throws Exception {
		this.logger.debug("Entering 'brokerRewardPropsList' method");
		QueryConditions localQueryConditions = QueryHelper.getQueryConditionsFromRequest(paramHttpServletRequest);
		PageInfo localPageInfo = QueryHelper.getPageInfoFromRequest(paramHttpServletRequest);
		if (localPageInfo == null) {
			localPageInfo = new PageInfo(1, 15, "brokerID", false);
		}
		if (localQueryConditions == null) {
			localQueryConditions = new QueryConditions();
		}
		localQueryConditions.addCondition("brokerId", "<>", "-1");
		BrokerRewardPropsService localBrokerRewardPropsService = (BrokerRewardPropsService) SysData.getBean("m_brokerRewardPropsService");
		List localList = localBrokerRewardPropsService.getBrokerRewardPropsList(localQueryConditions, localPageInfo);
		Map localMap = WebUtils.getParametersStartingWith(paramHttpServletRequest, "_");
		ModelAndView localModelAndView = new ModelAndView("member/broker/brokerRewardProps/brokerRewardPropsList", "resultList", localList);
		localModelAndView.addObject("pageInfo", localPageInfo);
		localModelAndView.addObject("oldParams", localMap);
		return localModelAndView;
	}

	public ModelAndView brokerRewardPropsAddForward(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
			throws Exception {
		this.logger.debug("Entering 'brokerRewardPropsAdd' method");
		BrokerRewardPropsService localBrokerRewardPropsService = (BrokerRewardPropsService) SysData.getBean("m_brokerRewardPropsService");
		BrokerRewardProps localBrokerRewardProps = localBrokerRewardPropsService.getBrokerRewardProps();
		localBrokerRewardProps.setFirstPayRate(Arith.mul(localBrokerRewardProps.getFirstPayRate(), 100.0F));
		localBrokerRewardProps.setSecondPayRate(Arith.mul(localBrokerRewardProps.getSecondPayRate(), 100.0F));
		localBrokerRewardProps.setRewardRate(Arith.mul(localBrokerRewardProps.getRewardRate(), 100.0F));
		List localList1 = localBrokerRewardPropsService.getTbBreeds();
		List localList2 = localBrokerRewardPropsService.getZcjsBreeds();
		ModelAndView localModelAndView = new ModelAndView("member/broker/brokerRewardProps/brokerRewardPropsAdd", "brokerReward",
				localBrokerRewardProps);
		localModelAndView.addObject("resultList1", localList1);
		localModelAndView.addObject("resultList2", localList2);
		return localModelAndView;
	}

	public ModelAndView brokerRewardPropsAdd(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
			throws Exception {
		this.logger.debug("Entering 'brokerRewardPropsAdd' method");
		BrokerRewardPropsService localBrokerRewardPropsService = (BrokerRewardPropsService) SysData.getBean("m_brokerRewardPropsService");
		BrokerRewardProps localBrokerRewardProps = localBrokerRewardPropsService.getBrokerRewardProps();
		ParamUtil.bindData(paramHttpServletRequest, localBrokerRewardProps);
		localBrokerRewardProps.setFirstPayRate(localBrokerRewardProps.getFirstPayRate() / 100.0D);
		localBrokerRewardProps.setSecondPayRate(localBrokerRewardProps.getSecondPayRate() / 100.0D);
		localBrokerRewardProps.setRewardRate(localBrokerRewardProps.getRewardRate() / 100.0D);
		int i = localBrokerRewardPropsService.BrokerRewardPropsAdd(localBrokerRewardProps);
		String str = "";
		if (i == 0) {
			str = "";
		} else if (i == 1) {
			str = "添加成功！";
		} else if (i == -1) {
			str = "已有此记录！";
		} else if (i == -2) {
			str = "已有此记录！";
		}
		ModelAndView localModelAndView = new ModelAndView("member/broker/brokerRewardProps/brokerRewardPropsList", "resultMsg", str);
		return localModelAndView;
	}

	public ModelAndView brokerRewardPropsDelete(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
			throws Exception {
		this.logger.debug("Entering 'brokerRewardPropsDelete' method");
		int i = 0;
		BrokerRewardPropsService localBrokerRewardPropsService = (BrokerRewardPropsService) SysData.getBean("m_brokerRewardPropsService");
		String[] arrayOfString1 = paramHttpServletRequest.getParameterValues("delCheck");
		if (arrayOfString1 != null) {
			for (int j = 0; j < arrayOfString1.length; j++) {
				String localObject = arrayOfString1[j];
				if ((localObject != null) && (!"".equals(localObject))) {
					BrokerRewardProps localBrokerRewardProps = new BrokerRewardProps();
					String[] arrayOfString2 = ((String) localObject).split(",");
					String str2 = arrayOfString2[0];
					String str3 = arrayOfString2[1];
					String str4 = arrayOfString2[2];
					this.logger.debug("breedID: " + str2);
					this.logger.debug("moduleId" + str3);
					this.logger.debug("brokerID: " + str4);
					localBrokerRewardProps.setBreedId(str2);
					localBrokerRewardProps.setModuleId(str3);
					localBrokerRewardProps.setBrokerId(str4);
					i = localBrokerRewardPropsService.BrokerRewardPropsDel(localBrokerRewardProps);
				}
			}
		}
		String str1 = "";
		if (i == 1) {
			str1 = "删除成功！";
		} else {
			str1 = "其他错误！";
		}
		ModelAndView localObject = new ModelAndView("member/broker/brokerRewardProps/brokerRewardPropsList");
		((ModelAndView) localObject).addObject("resultMsg", str1);
		return localObject;
	}

	public ModelAndView brokerRewardPropsUpdateOther(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
			throws Exception {
		this.logger.debug("Entering 'brokerRewardPropsUpdateOther' method");
		String str1 = paramHttpServletRequest.getParameter("breedId");
		String str2 = paramHttpServletRequest.getParameter("moduleId");
		String str3 = paramHttpServletRequest.getParameter("brokerId");
		this.logger.debug("breedID: " + str1);
		this.logger.debug("moduleId" + str2);
		this.logger.debug("brokerID: " + str3);
		BrokerRewardPropsService localBrokerRewardPropsService = (BrokerRewardPropsService) SysData.getBean("m_brokerRewardPropsService");
		BrokerRewardProps localBrokerRewardProps = localBrokerRewardPropsService.getBrokerRewardProps(str1, str3, str2);
		localBrokerRewardProps.setFirstPayRate(Arith.mul(localBrokerRewardProps.getFirstPayRate(), 100.0F));
		localBrokerRewardProps.setSecondPayRate(Arith.mul(localBrokerRewardProps.getSecondPayRate(), 100.0F));
		localBrokerRewardProps.setRewardRate(Arith.mul(localBrokerRewardProps.getRewardRate(), 100.0F));
		List localList1 = localBrokerRewardPropsService.getTbBreeds();
		List localList2 = localBrokerRewardPropsService.getZcjsBreeds();
		ModelAndView localModelAndView = new ModelAndView("member/broker/brokerRewardProps/brokerRewardPropsUpdateOther", "brokerRewardProps",
				localBrokerRewardProps);
		localModelAndView.addObject("resultList1", localList1);
		localModelAndView.addObject("resultList2", localList2);
		return localModelAndView;
	}

	public ModelAndView brokerRewardPropsUpdateOtherAction(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
			throws Exception {
		this.logger.debug("Entering 'brokerRewardPropsUpdateOtherAction' method");
		BrokerRewardProps localBrokerRewardProps = new BrokerRewardProps();
		ParamUtil.bindData(paramHttpServletRequest, localBrokerRewardProps);
		localBrokerRewardProps.setFirstPayRate(localBrokerRewardProps.getFirstPayRate() / 100.0D);
		localBrokerRewardProps.setSecondPayRate(localBrokerRewardProps.getSecondPayRate() / 100.0D);
		localBrokerRewardProps.setRewardRate(localBrokerRewardProps.getRewardRate() / 100.0D);
		BrokerRewardPropsService localBrokerRewardPropsService = (BrokerRewardPropsService) SysData.getBean("m_brokerRewardPropsService");
		localBrokerRewardPropsService.updateBrokerRewardProps(localBrokerRewardProps);
		paramHttpServletRequest.setAttribute("resultMsg", "修改成功！");
		return brokerRewardPropsList(paramHttpServletRequest, paramHttpServletResponse);
	}

	public ModelAndView brokerRewardPayMoney(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
			throws Exception {
		this.logger.debug("Entering 'brokerRewardPayMoney' method");
		double d = Double.parseDouble(paramHttpServletRequest.getParameter("money"));
		BrokerReward localBrokerReward = new BrokerReward();
		ParamUtil.bindData(paramHttpServletRequest, localBrokerReward);
		this.logger.debug("brokerId:" + localBrokerReward.getBrokerId());
		this.logger.debug("moduleId:" + localBrokerReward.getModuleId());
		this.logger.debug("date:" + localBrokerReward.getOccurDate());
		BrokerRewardService localBrokerRewardService = (BrokerRewardService) SysData.getBean("m_brokerRewardService");
		int i = -2;
		try {
			i = localBrokerRewardService.payCommission(localBrokerReward, d);
		} catch (Exception localException) {
			localException.printStackTrace();
		}
		this.logger.debug("result:" + i);
		String str = "支付成功";
		if (i == -1) {
			str = "可支付金额不足";
		}
		if (i == -2) {
			str = "操作异常";
		}
		return new ModelAndView("member/public/done", "resultMsg", str);
	}

	public ModelAndView changebrokerRewardForward(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
			throws Exception {
		BrokerReward localBrokerReward = new BrokerReward();
		ParamUtil.bindData(paramHttpServletRequest, localBrokerReward);
		BrokerRewardService localBrokerRewardService = (BrokerRewardService) SysData.getBean("m_brokerRewardService");
		localBrokerReward = localBrokerRewardService.getObject(localBrokerReward);
		ModelAndView localModelAndView = new ModelAndView("member/broker/brokerReward/changebrokerReward");
		localModelAndView.addObject("brokerReward", localBrokerReward);
		return localModelAndView;
	}

	public ModelAndView brokerRewardPropsAllAddByModuleForward(HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse) throws Exception {
		this.logger.debug("Entering 'brokerRewardPropsAllAdd' method");
		String str = paramHttpServletRequest.getParameter("moduleId");
		BrokerRewardPropsService localBrokerRewardPropsService = (BrokerRewardPropsService) SysData.getBean("m_brokerRewardPropsService");
		BrokerRewardProps localBrokerRewardProps = localBrokerRewardPropsService.getBrokerRewardProps();
		localBrokerRewardProps.setFirstPayRate(Arith.mul(localBrokerRewardProps.getFirstPayRate(), 100.0F));
		localBrokerRewardProps.setSecondPayRate(Arith.mul(localBrokerRewardProps.getSecondPayRate(), 100.0F));
		localBrokerRewardProps.setRewardRate(Arith.mul(localBrokerRewardProps.getRewardRate(), 100.0F));
		List localList1 = localBrokerRewardPropsService.getBrokers();
		List localList2 = null;
		if (str.equals("2")) {
			localList2 = localBrokerRewardPropsService.getTbBreeds();
		}
		if (str.equals("3")) {
			localList2 = localBrokerRewardPropsService.getZcjsBreeds();
		}
		ModelAndView localModelAndView = new ModelAndView("member/broker/brokerRewardProps/brokerRewardPropsAllAdd", "brokerReward",
				localBrokerRewardProps);
		localModelAndView.addObject("brokerList", localList1);
		localModelAndView.addObject("breedList", localList2);
		localModelAndView.addObject("moduleId", str);
		return localModelAndView;
	}

	public ModelAndView brokerRewardPropsAllAddAction(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
			throws Exception {
		this.logger.debug("Entering 'brokerRewardPropsAllAdd' method");
		String str1 = paramHttpServletRequest.getParameter("moduleId");
		String str2 = paramHttpServletRequest.getParameter("brokerids");
		String str3 = paramHttpServletRequest.getParameter("tf");
		String str4 = paramHttpServletRequest.getParameter("rewardRate");
		String str5 = paramHttpServletRequest.getParameter("firstPayRate");
		String str6 = paramHttpServletRequest.getParameter("secondPayRate");
		BrokerRewardPropsService localBrokerRewardPropsService = (BrokerRewardPropsService) SysData.getBean("m_brokerRewardPropsService");
		BrokerRewardProps localBrokerRewardProps1 = localBrokerRewardPropsService.getBrokerRewardProps();
		localBrokerRewardProps1.setModuleId(str1);
		if ((str5 != null) && (!"".equals(str5))) {
			localBrokerRewardProps1.setFirstPayRate(Double.parseDouble(str5) / 100.0D);
		}
		if ((str6 != null) && (!"".equals(str6))) {
			localBrokerRewardProps1.setSecondPayRate(Double.parseDouble(str6) / 100.0D);
		}
		if ((str4 != null) && (!"".equals(str4))) {
			localBrokerRewardProps1.setRewardRate(Double.parseDouble(str4) / 100.0D);
		}
		String str7 = "";
		try {
			if (str1.equals("4")) {
				localBrokerRewardProps1.setBreedId("-1");
				String[] localObject = str2.split(",");
				for (int i = 0; i < localObject.length; i++) {
					BrokerRewardProps localBrokerRewardProps2 = localBrokerRewardPropsService
							.getBrokerRewardProps(localBrokerRewardProps1.getBreedId(), localObject[i], str1);
					if (localBrokerRewardProps2 == null) {
						localBrokerRewardProps1.setBrokerId(localObject[i]);
						localBrokerRewardPropsService.insertBrokerRewardPropsAll(localBrokerRewardProps1);
					} else {
						localBrokerRewardProps2.setFirstPayRate(localBrokerRewardProps1.getFirstPayRate());
						localBrokerRewardProps2.setSecondPayRate(localBrokerRewardProps1.getSecondPayRate());
						localBrokerRewardProps2.setRewardRate(localBrokerRewardProps1.getRewardRate());
						localBrokerRewardPropsService.updateBrokerRewardProps(localBrokerRewardProps2);
					}
				}
			} else {
				String[] arrayOfString;
				int j;
				if (str3.equals("false")) {
					String localObject = paramHttpServletRequest.getParameter("breedId");
					localBrokerRewardProps1.setBreedId((String) localObject);
					arrayOfString = str2.split(",");
					for (j = 0; j < arrayOfString.length; j++) {
						BrokerRewardProps localBrokerRewardProps3 = localBrokerRewardPropsService.getBrokerRewardProps((String) localObject,
								arrayOfString[j], str1);
						if (localBrokerRewardProps3 == null) {
							localBrokerRewardProps1.setBrokerId(arrayOfString[j]);
							localBrokerRewardPropsService.insertBrokerRewardPropsAll(localBrokerRewardProps1);
						} else {
							localBrokerRewardProps3.setFirstPayRate(localBrokerRewardProps1.getFirstPayRate());
							localBrokerRewardProps3.setSecondPayRate(localBrokerRewardProps1.getSecondPayRate());
							localBrokerRewardProps3.setRewardRate(localBrokerRewardProps1.getRewardRate());
							localBrokerRewardPropsService.updateBrokerRewardProps(localBrokerRewardProps3);
						}
					}
				} else if (str3.equals("true")) {
					int k;
					String str8;
					BrokerRewardProps localBrokerRewardProps4;
					if (str1.equals("2")) {
						List localObject = localBrokerRewardPropsService.getTbBreeds();
						arrayOfString = str2.split(",");
						for (j = 0; j < arrayOfString.length; j++) {
							localBrokerRewardProps1.setBrokerId(arrayOfString[j]);
							if (localObject != null) {
								for (k = 0; k < ((List) localObject).size(); k++) {
									str8 = ((Map) ((List) localObject).get(k)).get("breedId").toString();
									localBrokerRewardProps4 = localBrokerRewardPropsService.getBrokerRewardProps(str8, arrayOfString[j], str1);
									if (localBrokerRewardProps4 == null) {
										localBrokerRewardProps1.setBreedId(str8);
										localBrokerRewardPropsService.insertBrokerRewardPropsAll(localBrokerRewardProps1);
									} else {
										localBrokerRewardProps4.setFirstPayRate(localBrokerRewardProps1.getFirstPayRate());
										localBrokerRewardProps4.setSecondPayRate(localBrokerRewardProps1.getSecondPayRate());
										localBrokerRewardProps4.setRewardRate(localBrokerRewardProps1.getRewardRate());
										localBrokerRewardPropsService.updateBrokerRewardProps(localBrokerRewardProps4);
									}
								}
							}
						}
					} else if (str1.equals("3")) {
						List localObject = localBrokerRewardPropsService.getZcjsBreeds();
						arrayOfString = str2.split(",");
						for (j = 0; j < arrayOfString.length; j++) {
							localBrokerRewardProps1.setBrokerId(arrayOfString[j]);
							if (localObject != null) {
								for (k = 0; k < ((List) localObject).size(); k++) {
									str8 = ((Map) ((List) localObject).get(k)).get("breedId").toString();
									localBrokerRewardProps4 = localBrokerRewardPropsService.getBrokerRewardProps(str8, arrayOfString[j], str1);
									if (localBrokerRewardProps4 == null) {
										localBrokerRewardProps1.setBreedId(str8);
										localBrokerRewardPropsService.insertBrokerRewardPropsAll(localBrokerRewardProps1);
									} else {
										localBrokerRewardProps4.setFirstPayRate(localBrokerRewardProps1.getFirstPayRate());
										localBrokerRewardProps4.setSecondPayRate(localBrokerRewardProps1.getSecondPayRate());
										localBrokerRewardProps4.setRewardRate(localBrokerRewardProps1.getRewardRate());
										localBrokerRewardPropsService.updateBrokerRewardProps(localBrokerRewardProps4);
									}
								}
							}
						}
					}
				}
			}
			str7 = "更新成功！";
		} catch (Exception localException) {
			localException.printStackTrace();
			str7 = "更新失败！" + localException.getMessage();
		}
		ModelAndView localModelAndView = new ModelAndView("member/broker/brokerRewardProps/brokerRewardPropsList", "resultMsg", str7);
		return localModelAndView;
	}

	public ModelAndView brokerRewardPropsBreedList(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
			throws Exception {
		this.logger.debug("Entering 'brokerRewardPropsBreedList' method");
		QueryConditions localQueryConditions = QueryHelper.getQueryConditionsFromRequest(paramHttpServletRequest);
		PageInfo localPageInfo = QueryHelper.getPageInfoFromRequest(paramHttpServletRequest);
		if (localPageInfo == null) {
			localPageInfo = new PageInfo(1, 15, "breedID", false);
		}
		BrokerRewardPropsService localBrokerRewardPropsService = (BrokerRewardPropsService) SysData.getBean("m_brokerRewardPropsService");
		List localList = localBrokerRewardPropsService.getBrokerRewardPropsBreedList(localQueryConditions, localPageInfo);
		Map localMap = WebUtils.getParametersStartingWith(paramHttpServletRequest, "_");
		ModelAndView localModelAndView = new ModelAndView("member/broker/brokerRewardProps/brokerRewardPropsBreedList", "resultList", localList);
		localModelAndView.addObject("pageInfo", localPageInfo);
		localModelAndView.addObject("oldParams", localMap);
		return localModelAndView;
	}

	public ModelAndView brokerRewardPropsBreedAdd(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
			throws Exception {
		this.logger.debug("Entering 'brokerRewardPropsBreedAdd' method");
		BrokerRewardPropsService localBrokerRewardPropsService = (BrokerRewardPropsService) SysData.getBean("m_brokerRewardPropsService");
		BrokerRewardProps localBrokerRewardProps = localBrokerRewardPropsService.getBrokerRewardProps();
		localBrokerRewardProps.setFirstPayRate(Arith.mul(localBrokerRewardProps.getFirstPayRate(), 100.0F));
		localBrokerRewardProps.setSecondPayRate(Arith.mul(localBrokerRewardProps.getSecondPayRate(), 100.0F));
		localBrokerRewardProps.setRewardRate(Arith.mul(localBrokerRewardProps.getRewardRate(), 100.0F));
		List localList = localBrokerRewardPropsService.getBreeds();
		ModelAndView localModelAndView = new ModelAndView("member/broker/brokerRewardProps/brokerRewardPropsBreedAdd", "brokerReward",
				localBrokerRewardProps);
		localModelAndView.addObject("resultList", localList);
		return localModelAndView;
	}

	public ModelAndView brokerRewardPropsBreedAddAction(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
			throws Exception {
		this.logger.debug("Entering 'brokerRewardPropsBreedAddAction' method");
		String str1 = paramHttpServletRequest.getParameter("breedId");
		String str2 = paramHttpServletRequest.getParameter("tf");
		String str3 = paramHttpServletRequest.getParameter("rewardRate");
		String str4 = paramHttpServletRequest.getParameter("firstPayRate");
		String str5 = paramHttpServletRequest.getParameter("secondPayRate");
		BrokerRewardPropsService localBrokerRewardPropsService = (BrokerRewardPropsService) SysData.getBean("m_brokerRewardPropsService");
		this.logger.debug("firstPayRate: " + str4);
		this.logger.debug("secondPayRate: " + str5);
		BrokerRewardProps localBrokerRewardProps = localBrokerRewardPropsService.getBrokerRewardProps();
		if ((str4 != null) && (!"".equals(str4))) {
			localBrokerRewardProps.setFirstPayRate(Double.parseDouble(str4) / 100.0D);
		}
		if ((str5 != null) && (!"".equals(str5))) {
			localBrokerRewardProps.setSecondPayRate(Double.parseDouble(str5) / 100.0D);
		}
		if ((str3 != null) && (!"".equals(str3))) {
			localBrokerRewardProps.setRewardRate(Double.parseDouble(str3) / 100.0D);
		}
		localBrokerRewardProps.setBreedId(str1);
		localBrokerRewardProps.setModuleId("2");
		String str6 = "";
		try {
			if (str2.equals("false")) {
				localBrokerRewardPropsService.insertBrokerRewardPropsBreed(localBrokerRewardProps);
			} else if (str2.equals("true")) {
				localBrokerRewardPropsService.BrokerRewardPropsAddByBreed(localBrokerRewardProps);
				localBrokerRewardPropsService.insertBrokerRewardPropsBreed(localBrokerRewardProps);
			}
			str6 = "添加成功！";
		} catch (Exception localException) {
			localException.printStackTrace();
			str6 = "添加失败！" + localException.getMessage();
		}
		ModelAndView localModelAndView = new ModelAndView("member/broker/brokerRewardProps/brokerRewardPropsBreedList", "resultMsg", str6);
		return localModelAndView;
	}

	public ModelAndView brokerRewardPropsBreedUpdate(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
			throws Exception {
		this.logger.debug("Entering 'brokerRewardPropsBreedUpdate' method");
		String str = paramHttpServletRequest.getParameter("breedId");
		BrokerRewardPropsService localBrokerRewardPropsService = (BrokerRewardPropsService) SysData.getBean("m_brokerRewardPropsService");
		List localList = localBrokerRewardPropsService.getBrokerRewardPropsBreedByID(Integer.parseInt(str));
		ModelAndView localModelAndView = new ModelAndView("member/broker/brokerRewardProps/brokerRewardPropsBreedUpdate", "brokerRewardProps",
				localList.get(0));
		return localModelAndView;
	}

	public ModelAndView brokerRewardPropsBreedUpdateAction(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
			throws Exception {
		this.logger.debug("Entering 'brokerRewardPropsBreedUpdateAction' method");
		String str1 = paramHttpServletRequest.getParameter("breedId");
		String str2 = paramHttpServletRequest.getParameter("tf");
		String str3 = paramHttpServletRequest.getParameter("rewardRate");
		String str4 = paramHttpServletRequest.getParameter("firstPayRate");
		String str5 = paramHttpServletRequest.getParameter("secondPayRate");
		this.logger.debug("firstPayRate: " + str4);
		this.logger.debug("secondPayRate: " + str5);
		BrokerRewardProps localBrokerRewardProps = new BrokerRewardProps();
		localBrokerRewardProps.setBreedId(str1);
		if ((str4 != null) && (!"".equals(str4))) {
			localBrokerRewardProps.setFirstPayRate(Double.parseDouble(str4) / 100.0D);
		}
		if ((str5 != null) && (!"".equals(str5))) {
			localBrokerRewardProps.setSecondPayRate(Double.parseDouble(str5) / 100.0D);
		}
		if ((str3 != null) && (!"".equals(str3))) {
			localBrokerRewardProps.setRewardRate(Double.parseDouble(str3) / 100.0D);
		}
		BrokerRewardPropsService localBrokerRewardPropsService = (BrokerRewardPropsService) SysData.getBean("m_brokerRewardPropsService");
		try {
			if ((str2 != null) && (str2.equals("true"))) {
				localBrokerRewardPropsService.updateBrokerRewardPropsByBreed(localBrokerRewardProps);
			}
			localBrokerRewardPropsService.updateBrokerRewardPropsBreed(localBrokerRewardProps);
		} catch (Exception localException) {
			localException.printStackTrace();
		}
		paramHttpServletRequest.setAttribute("resultMsg", "修改成功！");
		ModelAndView localModelAndView = new ModelAndView("member/broker/brokerRewardProps/brokerRewardPropsBreedList");
		return localModelAndView;
	}

	public ModelAndView brokerRewardPropsBreedDelete(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
			throws Exception {
		this.logger.debug("Entering 'brokerRewardPropsBreedDelete' method");
		BrokerRewardPropsService localBrokerRewardPropsService = (BrokerRewardPropsService) SysData.getBean("m_brokerRewardPropsService");
		String[] arrayOfString = paramHttpServletRequest.getParameterValues("delCheck");
		String str1 = paramHttpServletRequest.getParameter("tf");
		String str2 = "";
		try {
			if (arrayOfString != null) {
				for (int i = 0; i < arrayOfString.length; i++) {
					String str3 = arrayOfString[i];
					if ((str3 != null) && (!"".equals(str3))) {
						if ((str1 != null) && (str1.equals("true"))) {
							List localList = localBrokerRewardPropsService.getBrokerIDList();
							if (localList != null) {
								for (int j = 0; j < localList.size(); j++) {
									String str4 = ((Map) localList.get(j)).get("BrokerID").toString();
									localBrokerRewardPropsService.BrokerRewardPropsDel(str3, str4);
								}
							}
						}
						localBrokerRewardPropsService.deleteBrokerRewardPropsBreed(Integer.parseInt(str3));
					}
				}
			}
			str2 = "删除成功！";
		} catch (Exception localException) {
			localException.printStackTrace();
			str2 = "其他错误！" + localException.getMessage();
		}
		ModelAndView localModelAndView = new ModelAndView("member/broker/brokerRewardProps/brokerRewardPropsBreedList");
		localModelAndView.addObject("resultMsg", str2);
		return localModelAndView;
	}
}
