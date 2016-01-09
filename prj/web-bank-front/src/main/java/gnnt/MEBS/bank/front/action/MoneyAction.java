/*      */ package gnnt.MEBS.bank.front.action;

/*      */
/*      */ import gnnt.MEBS.bank.front.core.BankCoreCode;
/*      */ import gnnt.MEBS.bank.front.model.Bank;
/*      */ import gnnt.MEBS.bank.front.model.CapitalInfo;
/*      */ import gnnt.MEBS.bank.front.model.FirmIDAndAccount;
/*      */ import gnnt.MEBS.bank.front.model.FirmUser;
/*      */ import gnnt.MEBS.bank.front.statictools.Util;
/*      */ import gnnt.MEBS.common.front.action.StandardAction;
/*      */ import gnnt.MEBS.common.front.common.Page;
/*      */ import gnnt.MEBS.common.front.common.PageRequest;
/*      */ import gnnt.MEBS.common.front.common.QueryConditions;
/*      */ import gnnt.MEBS.common.front.model.integrated.MFirm;
/*      */ import gnnt.MEBS.common.front.model.integrated.User;
/*      */ import gnnt.MEBS.common.front.service.StandardService;
/*      */ import gnnt.MEBS.common.front.statictools.ActionUtil;
/*      */ import gnnt.MEBS.common.front.statictools.ApplicationContextInit;
/*      */ import gnnt.MEBS.common.front.statictools.Tools;
/*      */ import gnnt.trade.bank.processorrmi.CapitalProcessorRMI;
/*      */ import gnnt.trade.bank.util.ErrorCode;
/*      */ import gnnt.trade.bank.util.MD5orSHA;
/*      */ import gnnt.trade.bank.util.Tool;
/*      */ import gnnt.trade.bank.vo.CorrespondValue;
/*      */ import gnnt.trade.bank.vo.FirmBalanceValue;
/*      */ import gnnt.trade.bank.vo.ReturnValue;
/*      */ import java.io.IOException;
/*      */ import java.io.UnsupportedEncodingException;
/*      */ import java.rmi.RemoteException;
/*      */ import java.util.Date;
/*      */ import java.util.Hashtable;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Vector;
/*      */ import javax.annotation.Resource;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpSession;
/*      */ import org.springframework.beans.factory.annotation.Autowired;
/*      */ import org.springframework.beans.factory.annotation.Qualifier;
/*      */ import org.springframework.context.annotation.Scope;
/*      */ import org.springframework.stereotype.Controller;

/*      */
/*      */ @Controller("moneyAction")
/*      */ @Scope("request")
/*      */ public class MoneyAction extends StandardAction
/*      */ {
	/*      */ private static final long serialVersionUID = -8408589201557981299L;
	/*      */
	/*      */ @Resource(name = "capitalInfoType")
	/*      */ private Map<Integer, String> capitalInfoType;
	/*      */
	/*      */ @Resource(name = "capitalInfoStatus")
	/*      */ private Map<Integer, String> capitalInfoStatus;
	/*      */
	/*      */ @Autowired
	/*      */ @Qualifier("capitalProcessorRMI")
	/*      */ private CapitalProcessorRMI capitalProcessorRMI;

	/*      */
	/*      */ public Map<Integer, String> getCapitalInfoType()
	/*      */ {
		/* 89 */ return this.capitalInfoType;
		/*      */ }

	/*      */
	/*      */ public Map<Integer, String> getCapitalInfoStatus()
	/*      */ {
		/* 99 */ return this.capitalInfoStatus;
		/*      */ }

	/*      */
	/*      */ public String gotoChangePasswordPage()
	/*      */ {
		/* 109 */ User user = (User) this.request.getSession().getAttribute("CurrentUser");
		/* 110 */ long isFirstChangePassword = 0L;
		/*      */ try {
			/* 112 */ isFirstChangePassword = this.capitalProcessorRMI.isPassword(user.getBelongtoFirm().getFirmID(), "");
			/*      */ } catch (Exception e) {
			/* 114 */ this.logger.error("跳转到修改页码页面时，连接处理器异常", e);
			/*      */
			/* 116 */ addReturnValue(-1, 2830201L);
			/*      */ }
		/* 118 */ if (isFirstChangePassword == 1L) /* 119 */ this.request.setAttribute("isFirstChangePassword", Boolean.valueOf(true));
		/*      */ else {
			/* 121 */ this.request.setAttribute("isFirstChangePassword", Boolean.valueOf(false));
			/*      */ }
		/* 123 */ return "success";
		/*      */ }

	/*      */
	/*      */ public String changePassword()
	/*      */ {
		/* 133 */ User user = (User) this.request.getSession().getAttribute("CurrentUser");
		/* 134 */ String oldpwd = this.request.getParameter("oldpwd");
		/* 135 */ String newpwd = this.request.getParameter("newpwd");
		/* 136 */ long isFirstChangePassword = -1L;
		/*      */ try {
			/* 138 */ isFirstChangePassword = this.capitalProcessorRMI.isPassword(user.getBelongtoFirm().getFirmID(), oldpwd);
			/*      */ } catch (Exception e) {
			/* 140 */ this.logger.error("验证旧密码异常", e);
			/*      */
			/* 142 */ addReturnValue(-1, 2830204L);
			/* 143 */ return "success";
			/*      */ }
		/* 145 */ if (isFirstChangePassword < 0L) {
			/* 146 */ if (isFirstChangePassword == -1L)
			/*      */ {
				/* 148 */ addReturnValue(-1, 9930092L, new Object[] { "原密码错误" });
				/* 149 */ } else if (isFirstChangePassword == -2L)
			/*      */ {
				/* 151 */ addReturnValue(-1, 9930092L, new Object[] { "为查询到您的信息" });
				/*      */ }
				/*      */ else {
				/* 154 */ addReturnValue(-1, 9930092L, new Object[] { BankCoreCode.getCode(isFirstChangePassword) });
				/*      */ }
			/* 156 */ return "success";
			/*      */ }
		/*      */ try {
			/* 159 */ long flag2 = this.capitalProcessorRMI.modPwd(user.getBelongtoFirm().getFirmID(), oldpwd, newpwd);
			/* 160 */ if (flag2 < 0L)
			/*      */ {
				/* 162 */ addReturnValue(-1, 9930092L, new Object[] { BankCoreCode.getCode(flag2) });
				/*      */ }
				/*      */ else/* 165 */ addReturnValue(1, 2810201L);
			/*      */ }
			/*      */ catch (Exception e) {
			/* 168 */ this.logger.error("修改密码异常", e);
			/*      */
			/* 170 */ addReturnValue(-1, 2830203L);
			/*      */ }
		/* 172 */ return "success";
		/*      */ }

	/*      */
	/*      */ public String gotoMoneyPage()
	/*      */ {
		/* 182 */ User user = (User) this.request.getSession().getAttribute("CurrentUser");
		/* 183 */ QueryConditions qc = new QueryConditions();
		/* 184 */ qc.addCondition("firm.firmID", "=", user.getBelongtoFirm().getFirmID());
		/* 185 */ qc.addCondition("bank.validflag", "=", Integer.valueOf(0));
		/* 186 */ qc.addCondition("status", "=", Integer.valueOf(0));
		/* 187 */ qc.addCondition("isOpen", "=", Integer.valueOf(1));
		/* 188 */ PageRequest pageRequest = new PageRequest(1, 100, qc, " order by bank.bankID ");
		/* 189 */ Page page = getService().getPage(pageRequest, new FirmIDAndAccount());
		/* 190 */ this.request.setAttribute("pageInfo", page);
		/*      */
		/* 192 */ boolean isCheckMarketPassword = Tools.strToBoolean(ApplicationContextInit.getConfig("isCheckMarketPassword"));
		/* 193 */ this.request.setAttribute("isCheckMarketPassword", Boolean.valueOf(isCheckMarketPassword));
		/*      */
		/* 195 */ String inMoneyNeedPasswordBankID = ApplicationContextInit.getConfig("inMoneyNeedPasswordBankID");
		/* 196 */ this.request.setAttribute("inMoneyNeedPasswordBankID", inMoneyNeedPasswordBankID);
		/*      */
		/* 198 */ String outMoneyNeedPasswordBankID = ApplicationContextInit.getConfig("outMoneyNeedPasswordBankID");
		/* 199 */ this.request.setAttribute("outMoneyNeedPasswordBankID", outMoneyNeedPasswordBankID);
		/*      */
		/* 201 */ return "success";
		/*      */ }

	/*      */
	/*      */ public String moneyTransfer()
	/*      */ {
		/* 212 */ String bankID = this.request.getParameter("bankID");
		/* 213 */ String InOutStart = this.request.getParameter("InOutStart");
		/* 214 */ String PersonName = this.request.getParameter("PersonName");
		/* 215 */ String bankName = this.request.getParameter("BankName");
		/* 216 */ String AmoutDate = this.request.getParameter("AmoutDate");
		/* 217 */ String OutAccount = this.request.getParameter("OutAccount");
		/* 218 */ if ((bankID == null) || (bankID.trim().length() <= 0))
		/*      */ {
			/* 220 */ addReturnValue(-1, 2830101L);
			/* 221 */ return "success";
			/*      */ }
		/*      */
		/* 224 */ User user = (User) this.request.getSession().getAttribute("CurrentUser");
		/* 225 */ FirmIDAndAccount ffirmIDAndAccount = new FirmIDAndAccount();
		/* 226 */ Bank bank = new Bank();
		/* 227 */ bank.setBankID(bankID);
		/* 228 */ bank = (Bank) getService().get(bank);
		/* 229 */ ffirmIDAndAccount.setBank(bank);
		/* 230 */ ffirmIDAndAccount.setFirm(user.getBelongtoFirm());
		/* 231 */ FirmIDAndAccount firmIDAndAccount = (FirmIDAndAccount) getService().get(ffirmIDAndAccount);
		/* 232 */ firmIDAndAccount.setBank(bank);
		/* 233 */ if (firmIDAndAccount == null)
		/*      */ {
			/* 235 */ addReturnValue(-1, 2830104L, new Object[] { bankID });
			/* 236 */ return "success";
			/*      */ }
		/* 238 */ if (firmIDAndAccount.getIsOpen().intValue() != 1)
		/*      */ {
			/* 240 */ addReturnValue(-1, 2830105L, new Object[] { bankID });
			/* 241 */ return "success";
			/*      */ }
		/* 243 */ if (firmIDAndAccount.getStatus().intValue() != 0)
		/*      */ {
			/* 245 */ addReturnValue(-1, 2830106L, new Object[] { bankID });
			/* 246 */ return "success";
			/*      */ }
		/* 248 */ if (firmIDAndAccount.getBank().getValidflag().intValue() != 0)
		/*      */ {
			/* 250 */ addReturnValue(-1, 2830107L, new Object[] { bankID });
			/* 251 */ return "success";
			/*      */ }
		/*      */
		/* 254 */ double money = Tools.strToDouble(this.request.getParameter("money"), -1000.0D);
		/* 255 */ if (money <= 0.0D)
		/*      */ {
			/* 257 */ addReturnValue(-1, 2830102L);
			/* 258 */ return "success";
			/*      */ }
		/*      */
		/* 261 */ int express = Tools.strToInt(this.request.getParameter("express"), 0);
		/* 262 */ String password = this.request.getParameter("password");
		/*      */
		/* 265 */ String inoutMoney = this.request.getParameter("inoutMoney");
		/* 266 */ if ("0".equals(inoutMoney))
			/* 267 */ return inMoney(firmIDAndAccount, money, express, password, InOutStart, PersonName, AmoutDate, bankName, OutAccount);
		/* 268 */ if ("1".equals(inoutMoney)) {
			/* 269 */ return outMoney(firmIDAndAccount, money, express, password);
			/*      */ }
		/*      */
		/* 272 */ addReturnValue(-1, 2830103L);
		/* 273 */ return "success";
		/*      */ }

	/*      */
	/*      */ private String inMoney(FirmIDAndAccount firmIDAndAccount, double money, int express, String password, String InOutStart,
			String PersonName, String AmoutDate, String bankName, String OutAccount)
	/*      */ {
		/* 288 */ boolean isCheckMarketPassword = Tools.strToBoolean(ApplicationContextInit.getConfig("isCheckMarketPassword"));
		/* 289 */ String pb = ApplicationContextInit.getConfig("inMoneyNeedPasswordBankID");
		/* 290 */ long result = 0L;
		/* 291 */ if ((!getNeedPasswordBankID(pb, firmIDAndAccount.getBank().getBankID())) && (isCheckMarketPassword)) {
			/*      */ try {
				/* 293 */ result = this.capitalProcessorRMI.isPassword(firmIDAndAccount.getFirm().getFirmID(), password);
				/*      */ } catch (Exception e) {
				/* 295 */ this.logger.error("入金验证密码时异常", e);
				/*      */
				/* 297 */ addReturnValue(-1, 2830108L);
				/* 298 */ return "success";
				/*      */ }
			/*      */ }
		/* 301 */ if (result == 1L)
		/*      */ {
			/* 303 */ addReturnValue(-1, 2830109L);
			/* 304 */ return "success";
			/*      */ }
		/* 306 */ if (result != 0L) {
			/* 307 */ if (result == -1L)
			/*      */ {
				/* 309 */ addReturnValue(-1, 9930092L, new Object[] { "密码错误" });
				/* 310 */ } else if (result == -2L)
			/*      */ {
				/* 312 */ addReturnValue(-1, 9930092L, new Object[] { "为查询到您的信息" });
				/*      */ }
				/*      */ else {
				/* 315 */ addReturnValue(-1, 9930092L, new Object[] { BankCoreCode.getCode(result) });
				/*      */ }
			/* 317 */ return "success";
			/*      */ }
		/*      */ try {
			/* 320 */ this.logger.info("入金--银行：" + firmIDAndAccount.getBank().getBankID());
			/* 321 */ this.logger.info("入金--：" + InOutStart + "付款人：" + PersonName + "日期：" + AmoutDate + "付款银行：" + bankName + "帐号：" + OutAccount);
			/*      */
			/* 324 */ if (firmIDAndAccount.getBank().getBankID().equals("66"))
				/* 325 */ result = this.capitalProcessorRMI.inMoneyMarketGS(firmIDAndAccount.getBank().getBankID(),
						firmIDAndAccount.getFirm().getFirmID(), /* 326 */ firmIDAndAccount.getAccount(), password, money, "market_in");
			/* 327 */ if (firmIDAndAccount.getBank().getBankID().equals("17"))
			/*      */ {
				/* 329 */ result = this.capitalProcessorRMI.inMoneyMarket(firmIDAndAccount.getBank().getBankID(),
						firmIDAndAccount.getFirm().getFirmID(), /* 330 */ firmIDAndAccount.getAccount(), password, money, "market_in", InOutStart,
						PersonName, AmoutDate, bankName, OutAccount);
				/*      */ }
				/*      */ else/* 333 */ result = this.capitalProcessorRMI.inMoneyMarket(firmIDAndAccount.getBank().getBankID(),
						firmIDAndAccount.getFirm().getFirmID(), /* 334 */ firmIDAndAccount.getAccount(), password, money, "market_in");
			/*      */ }
			/*      */ catch (Exception e) {
			/* 337 */ this.logger.error("执行入金时异常", e);
			/*      */
			/* 339 */ addReturnValue(-1, 2830111L);
			/* 340 */ return "success";
			/*      */ }
		/* 342 */ if (result >= 0L)
		/*      */ {
			/* 344 */ addReturnValue(1, 2810101L);
			/* 345 */ return "success";
			/*      */ }
		/*      */
		/* 348 */ addReturnValue(-1, 9930092L, new Object[] { BankCoreCode.getCode(result) });
		/* 349 */ return "success";
		/*      */ }

	/*      */
	/*      */ private String outMoney(FirmIDAndAccount firmIDAndAccount, double money, int express, String password)
	/*      */ {
		/* 364 */ boolean isCheckMarketPassword = Tools.strToBoolean(ApplicationContextInit.getConfig("isCheckMarketPassword"));
		/* 365 */ String pb = ApplicationContextInit.getConfig("outMoneyNeedPasswordBankID");
		/* 366 */ long result = 0L;
		/* 367 */ if ((!getNeedPasswordBankID(pb, firmIDAndAccount.getBank().getBankID())) && (isCheckMarketPassword)) {
			/*      */ try {
				/* 369 */ result = this.capitalProcessorRMI.isPassword(firmIDAndAccount.getFirm().getFirmID(), password);
				/*      */ } catch (Exception e) {
				/* 371 */ this.logger.error("出金验证密码时异常", e);
				/*      */
				/* 373 */ addReturnValue(-1, 2830108L);
				/* 374 */ return "success";
				/*      */ }
			/*      */ }
		/* 377 */ if (result == 1L)
		/*      */ {
			/* 379 */ addReturnValue(-1, 2830109L);
			/* 380 */ return "success";
			/*      */ }
		/* 382 */ if (result != 0L) {
			/* 383 */ if (result == -1L)
			/*      */ {
				/* 385 */ addReturnValue(-1, 9930092L, new Object[] { "密码错误" });
				/* 386 */ } else if (result == -2L)
			/*      */ {
				/* 388 */ addReturnValue(-1, 9930092L, new Object[] { "为查询到您的信息" });
				/*      */ }
				/*      */ else {
				/* 391 */ addReturnValue(-1, 9930092L, new Object[] { BankCoreCode.getCode(result) });
				/*      */ }
			/* 393 */ return "success";
			/*      */ }
		/* 395 */ boolean isAudit = true;
		/* 396 */ Double maxAuditMoney = firmIDAndAccount.getBank().getMaxAuditMoney();
		/* 397 */ FirmUser firmUser = new FirmUser();
		/* 398 */ firmUser.setFirm(firmIDAndAccount.getFirm());
		/* 399 */ firmUser.setFirmID(firmIDAndAccount.getFirm().getFirmID());
		/* 400 */ firmUser = (FirmUser) getService().get(firmUser);
		/* 401 */ if ((firmUser.getMaxAuditMoney() != null) && (firmUser.getMaxAuditMoney().doubleValue() > 0.0D)) {
			/* 402 */ maxAuditMoney = firmUser.getMaxAuditMoney();
			/*      */ }
		/*      */
		/* 405 */ if ((maxAuditMoney != null) && (money <= maxAuditMoney.doubleValue())) /* 406 */ isAudit = false;
		/*      */ try
		/*      */ {
			/* 409 */ ReturnValue rv = this.capitalProcessorRMI.outMoney(firmIDAndAccount.getBank().getBankID(), /* 410 */ money,
					firmIDAndAccount.getFirm().getFirmID(), firmIDAndAccount.getAccount(), null, "market_out", express, 0);
			/* 411 */ if (rv.result < 0L) {
				/* 412 */ if (rv.result == -30006L)
				/*      */ {
					/* 414 */ addReturnValue(1, 2810103L);
					/* 415 */ } else if ((rv.remark != null) && (rv.remark.trim().length() > 0)) {
					/* 416 */ addReturnValue(-1, 9930092L, new Object[] { rv.remark });
					/*      */ }
					/*      */ else/* 419 */ addReturnValue(-1, 9930092L, new Object[] { BankCoreCode.getCode(rv.result) });
				/*      */ }
				/* 421 */ else if (!isAudit)
			/*      */ {
				/* 423 */ addReturnValue(1, 2810102L);
				/*      */ }
				/*      */ else/* 426 */ addReturnValue(1, 2810103L);
			/*      */ }
			/*      */ catch (Exception e) {
			/* 429 */ this.logger.error("执行入金时异常", e);
			/*      */
			/* 431 */ addReturnValue(-1, 2830113L);
			/*      */ }
		/* 433 */ return "success";
		/*      */ }

	/*      */
	/*      */ public String gotoQueryMoneyPage()
	/*      */ {
		/* 443 */ User user = (User) this.request.getSession().getAttribute("CurrentUser");
		/* 444 */ QueryConditions qc = new QueryConditions();
		/* 445 */ qc.addCondition("firm.firmID", "=", user.getBelongtoFirm().getFirmID());
		/* 446 */ qc.addCondition("bank.validflag", "=", Integer.valueOf(0));
		/* 447 */ qc.addCondition("status", "=", Integer.valueOf(0));
		/* 448 */ qc.addCondition("isOpen", "=", Integer.valueOf(1));
		/* 449 */ PageRequest pageRequest = new PageRequest(1, 100, qc, " order by bank.bankID ");
		/* 450 */ Page page = getService().getPage(pageRequest, new FirmIDAndAccount());
		/* 451 */ this.request.setAttribute("pageInfo", page);
		/*      */
		/* 453 */ boolean isCheckMarketPassword = Tools.strToBoolean(ApplicationContextInit.getConfig("isCheckMarketPassword"));
		/* 454 */ this.request.setAttribute("isCheckMarketPassword", Boolean.valueOf(isCheckMarketPassword));
		/*      */
		/* 456 */ String queryMoneyNeedPasswordBankID = ApplicationContextInit.getConfig("queryMoneyNeedPasswordBankID");
		/* 457 */ this.request.setAttribute("queryMoneyNeedPasswordBankID", queryMoneyNeedPasswordBankID);
		/*      */
		/* 459 */ return "success";
		/*      */ }

	/*      */
	/*      */ public String queryMoney()
	/*      */ {
		/* 470 */ String bankID = this.request.getParameter("bankID");
		/* 471 */ if ((bankID == null) || (bankID.trim().length() <= 0))
		/*      */ {
			/* 473 */ addReturnValue(-1, 2830101L);
			/* 474 */ return "success";
			/*      */ }
		/*      */
		/* 477 */ User user = (User) this.request.getSession().getAttribute("CurrentUser");
		/* 478 */ FirmIDAndAccount firmIDAndAccount = new FirmIDAndAccount();
		/* 479 */ Bank bank = new Bank();
		/* 480 */ bank.setBankID(bankID);
		/* 481 */ bank = (Bank) getService().get(bank);
		/* 482 */ firmIDAndAccount.setBank(bank);
		/* 483 */ firmIDAndAccount.setFirm(user.getBelongtoFirm());
		/* 484 */ firmIDAndAccount = (FirmIDAndAccount) getService().get(firmIDAndAccount);
		/* 485 */ if (firmIDAndAccount == null)
		/*      */ {
			/* 487 */ addReturnValue(-1, 2830104L, new Object[] { bankID });
			/* 488 */ return "success";
			/*      */ }
		/* 490 */ if (firmIDAndAccount.getIsOpen().intValue() != 1)
		/*      */ {
			/* 492 */ addReturnValue(-1, 2830105L, new Object[] { bankID });
			/* 493 */ return "success";
			/*      */ }
		/* 495 */ if (firmIDAndAccount.getStatus().intValue() != 0)
		/*      */ {
			/* 497 */ addReturnValue(-1, 2830106L, new Object[] { bankID });
			/* 498 */ return "success";
			/*      */ }
		/* 500 */ if (firmIDAndAccount.getBank().getValidflag().intValue() != 0)
		/*      */ {
			/* 502 */ addReturnValue(-1, 2830107L, new Object[] { bankID });
			/* 503 */ return "success";
			/*      */ }
		/*      */
		/* 506 */ boolean isCheckMarketPassword = Tools.strToBoolean(ApplicationContextInit.getConfig("isCheckMarketPassword"));
		/* 507 */ String pb = ApplicationContextInit.getConfig("queryMoneyNeedPasswordBankID");
		/* 508 */ String password = this.request.getParameter("password");
		/* 509 */ long result = 0L;
		/* 510 */ if ((!getNeedPasswordBankID(pb, firmIDAndAccount.getBank().getBankID())) && (isCheckMarketPassword)) {
			/*      */ try {
				/* 512 */ result = this.capitalProcessorRMI.isPassword(firmIDAndAccount.getFirm().getFirmID(), password);
				/*      */ } catch (Exception e) {
				/* 514 */ this.logger.error("查询 余额验证密码时异常", e);
				/*      */
				/* 516 */ addReturnValue(-1, 2830108L);
				/* 517 */ return "success";
				/*      */ }
			/*      */ }
		/* 520 */ if (result == 1L)
		/*      */ {
			/* 522 */ addReturnValue(-1, 2830109L);
			/* 523 */ return "success";
			/*      */ }
		/* 525 */ if (result != 0L) {
			/* 526 */ if (result == -1L)
			/*      */ {
				/* 528 */ addReturnValue(-1, 9930092L, new Object[] { "密码错误" });
				/* 529 */ } else if (result == -2L)
			/*      */ {
				/* 531 */ addReturnValue(-1, 9930092L, new Object[] { "为查询到您的信息" });
				/*      */ }
				/*      */ else {
				/* 534 */ addReturnValue(-1, 9930092L, new Object[] { BankCoreCode.getCode(result) });
				/*      */ }
			/* 536 */ return "success";
			/*      */ }
		/*      */ try {
			/* 539 */ FirmBalanceValue fv = this.capitalProcessorRMI.getFirmBalance(bankID, firmIDAndAccount.getFirm().getFirmID(), password);
			/* 540 */ this.request.setAttribute("bank", bank);
			/* 541 */ this.request.setAttribute("marketBalance", Double.valueOf(fv.marketBalance));
			/* 542 */ this.request.setAttribute("frozenBalance", Double.valueOf(fv.frozenBalance));
			/* 543 */ this.request.setAttribute("avilableBalance", Double.valueOf(fv.avilableBalance));
			/* 544 */ this.request.setAttribute("bankAccount", fv.bankAccount);
			/* 545 */ this.request.setAttribute("bankBalance", Double.valueOf(fv.bankBalance));
			/*      */ } catch (Exception e) {
			/* 547 */ this.logger.error("查询余额时异常", e);
			/* 548 */ addReturnValue(-1, 2830301L);
			/*      */ }
		/* 550 */ return "success";
		/*      */ }

	/*      */
	/*      */ private boolean getNeedPasswordBankID(String pb, String bankID)
	/*      */ {
		/* 562 */ if ((pb == null) || (pb.trim().length() <= 0)) {
			/* 563 */ return false;
			/*      */ }
		/* 565 */ if (pb.trim().equalsIgnoreCase(bankID)) {
			/* 566 */ return true;
			/*      */ }
		/* 568 */ if (pb.contains("," + bankID + ",")) {
			/* 569 */ return true;
			/*      */ }
		/* 571 */ if (pb.startsWith(bankID + ",")) {
			/* 572 */ return true;
			/*      */ }
		/* 574 */ if (pb.endsWith("," + bankID)) {
			/* 575 */ return true;
			/*      */ }
		/* 577 */ return false;
		/*      */ }

	/*      */
	/*      */ public String getCapitalInfoList()/*      */ throws Exception
	/*      */ {
		/* 588 */ User user = (User) this.request.getSession().getAttribute("CurrentUser");
		/* 589 */ QueryConditions qc = new QueryConditions();
		/* 590 */ qc.addCondition("firm.firmID", "=", user.getBelongtoFirm().getFirmID());
		/*      */
		/* 593 */ qc.addCondition("isOpen", "=", Integer.valueOf(1));
		/* 594 */ PageRequest pageRequest = new PageRequest(1, 100, qc, " order by bank.bankID ");
		/* 595 */ Page page = getService().getPage(pageRequest, new FirmIDAndAccount());
		/* 596 */ this.request.setAttribute("firmIDAndAccountList", page);
		/*      */
		/* 598 */ PageRequest pageRequestc = ActionUtil.getPageRequest(this.request);
		/* 599 */ QueryConditions qcc = (QueryConditions) pageRequestc.getFilters();
		/* 600 */ qcc.addCondition("firm.firmID", "=", user.getBelongtoFirm().getFirmID());
		/* 601 */ Page pagec = getService().getPage(pageRequestc, new CapitalInfo());
		/* 602 */ this.request.setAttribute("pageInfo", pagec);
		/*      */
		/* 604 */ this.request.setAttribute("oldParams", ActionUtil.getParametersStartingWith(this.request, "gnnt_"));
		/* 605 */ return "success";
		/*      */ }

	/*      */
	/*      */ public String gotoRgstDelFirmInfoPage()
	/*      */ {
		/* 616 */ User user = (User) this.request.getSession().getAttribute("CurrentUser");
		/* 617 */ QueryConditions qc = new QueryConditions();
		/* 618 */ qc.addCondition("firm.firmID", "=", user.getBelongtoFirm().getFirmID());
		/* 619 */ qc.addCondition("bank.validflag", "=", Integer.valueOf(0));
		/*      */
		/* 622 */ PageRequest pageRequest = new PageRequest(1, 100, qc, " order by bank.bankID ");
		/* 623 */ Page page = getService().getPage(pageRequest, new FirmIDAndAccount());
		/* 624 */ this.request.setAttribute("pageInfo", page);
		/*      */
		/* 626 */ boolean isCheckMarketPassword = Tools.strToBoolean(ApplicationContextInit.getConfig("isCheckMarketPassword"));
		/* 627 */ this.request.setAttribute("isCheckMarketPassword", Boolean.valueOf(isCheckMarketPassword));
		/*      */
		/* 629 */ String rgstAccountNeedPasswordBankID = ApplicationContextInit.getConfig("rgstAccountNeedPasswordBankID");
		/* 630 */ this.request.setAttribute("rgstAccountNeedPasswordBankID", rgstAccountNeedPasswordBankID);
		/*      */
		/* 632 */ String delAccountNeedPasswordBankID = ApplicationContextInit.getConfig("delAccountNeedPasswordBankID");
		/* 633 */ this.request.setAttribute("delAccountNeedPasswordBankID", delAccountNeedPasswordBankID);
		/*      */
		/* 635 */ return "success";
		/*      */ }

	/*      */
	/*      */ public String gotoHXCrossFirmInfoPage()
	/*      */ {
		/* 647 */ this.logger.info("华夏他行签约");
		/* 648 */ User user = (User) this.request.getSession().getAttribute("CurrentUser");
		/*      */
		/* 650 */ String bankID = this.request.getParameter("bankID");
		/* 651 */ QueryConditions qc = new QueryConditions();
		/* 652 */ qc.addCondition("firm.firmID", "=", user.getBelongtoFirm().getFirmID());
		/* 653 */ qc.addCondition("bank.validflag", "=", Integer.valueOf(0));
		/*      */
		/* 656 */ PageRequest pageRequest = new PageRequest(1, 100, qc, " order by bank.bankID ");
		/* 657 */ Page page = getService().getPage(pageRequest, new FirmIDAndAccount());
		/* 658 */ this.request.setAttribute("pageInfo", page);
		/*      */
		/* 660 */ List firm = getService().getListBySql(
				"select * from F_b_firmIDAndAccount where firmID ='" + user.getBelongtoFirm().getFirmID() + "'", new FirmIDAndAccount());
		/* 661 */ this.request.setAttribute("firmIDAndAccount", firm.get(0));
		/*      */
		/* 663 */ String filterForGetCitys = "where parentID='0'";
		/* 664 */ Vector citysValue = null;
		/*      */ try
		/*      */ {
			/* 667 */ citysValue = this.capitalProcessorRMI.getCitysValue(filterForGetCitys);
			/*      */ }
			/*      */ catch (RemoteException e)
		/*      */ {
			/* 671 */ e.printStackTrace();
			/*      */ }
		/* 673 */ this.request.setAttribute("cityValues", citysValue);
		/*      */
		/* 675 */ return "success";
		/*      */ }

	/*      */
	/*      */ public String synchroRegist()
	/*      */ {
		/* 685 */ String firmID = this.request.getParameter("firmID");
		/* 686 */ if ((firmID == null) || (firmID.trim().length() <= 0))
		/*      */ {
			/* 688 */ addReturnValue(-1, 133301L);
			/* 689 */ return "success";
			/*      */ }
		/* 691 */ String bankID = this.request.getParameter("bankID");
		/* 692 */ if ((bankID == null) || (bankID.trim().length() <= 0))
		/*      */ {
			/* 694 */ addReturnValue(-1, 133302L);
			/* 695 */ return "success";
			/*      */ }
		/*      */
		/* 699 */ User user = (User) this.request.getSession().getAttribute("CurrentUser");
		/* 700 */ gnnt.MEBS.bank.front.model.Log log = new gnnt.MEBS.bank.front.model.Log();
		/* 701 */ log.setLogDate(new Date());
		/* 702 */ log.setLogIP(user.getIpAddress());
		/* 703 */ log.setLogopr(user.getUserID());
		/*      */
		/* 705 */ String success = "";
		/* 706 */ String error = "";
		/*      */
		/* 708 */ FirmIDAndAccount firmIDAndAccount = (FirmIDAndAccount) getFirmIDAndAccount(firmID, bankID).clone();
		/* 709 */ CorrespondValue cv = firmIDAndAccount.getCorrespondValue();
		/* 710 */ cv.status = 0;
		/* 711 */ cv.isOpen = 0;
		/*      */ try
		/*      */ {
			/* 714 */ if (!Util.isContentsBank(ApplicationContextInit.getConfig("marketPerformSynchroAccount"), bankID)) {
				/* 715 */ log.setLogcontent("同步交易商 " + firmID + " 在银行 " + bankID + " 帐号，失败。本银行不允许交易所发起同步");
				/* 716 */ getService().add(log);
				/* 717 */ this.logger.debug("同步交易商 " + firmID + " 在银行 " + bankID + " 帐号，失败。本银行不允许交易所发起同步");
				/* 718 */ if (error.trim().length() > 0)
					error = error + ",";
				/* 719 */ error = error + bankID;
				/*      */ }
			/*      */
			/* 723 */ ReturnValue result = this.capitalProcessorRMI.synchroAccountMarket(cv);
			/* 724 */ if (result.result == 0L) {
				/* 725 */ log.setLogcontent("同步交易商 " + firmID + " 在银行 " + bankID + " 帐号，成功。");
				/* 726 */ getService().add(log);
				/* 727 */ if (success.trim().length() > 0)
					success = success + ",";
				/* 728 */ success = success + bankID;
				/*      */ } else {
				/* 730 */ log.setLogcontent("同步交易商 " + firmID + " 在银行 " + bankID + " 帐号，失败。" + BankCoreCode.getCode(result.result));
				/* 731 */ getService().add(log);
				/* 732 */ this.logger.debug("同步交易商 " + firmID + " 在银行 " + bankID + " 帐号，" + BankCoreCode.getCode(result.result));
				/* 733 */ if (error.trim().length() > 0)
					error = error + ",";
				/* 734 */ error = error + "银行" + bankID + "：" + BankCoreCode.getCode(result.result);
				/*      */ }
			/*      */ } catch (Exception e) {
			/* 737 */ log.setLogcontent("同步交易商 " + firmID + " 在银行 " + bankID + " 帐号，发生异常");
			/* 738 */ getService().add(log);
			/* 739 */ if (error.trim().length() > 0)
				error = error + ",";
			/* 740 */ error = error + "银行" + bankID + "：异常";
			/* 741 */ this.logger.error("同步交易商 " + firmID + " 在银行 " + bankID + " 帐号异常", e);
			/*      */ }
		/*      */
		/* 744 */ String msg = "同步交易商 " + firmID + " 银行帐号";
		/* 745 */ if (success.length() > 0) {
			/* 746 */ msg = msg + "," + success + " 成功";
			/*      */ }
		/* 748 */ if (error.length() > 0) {
			/* 749 */ msg = msg + "," + error;
			/*      */ }
		/*      */
		/* 752 */ addReturnValue(1, 130000L, new Object[] { msg });
		/*      */
		/* 754 */ return "success";
		/*      */ }

	/*      */
	/*      */ public String gotoRgstHX()
	/*      */ {
		/* 763 */ String firmID = this.request.getParameter("firmID");
		/* 764 */ if ((firmID == null) || (firmID.trim().length() <= 0))
		/*      */ {
			/* 766 */ addReturnValue(-1, 133301L);
			/* 767 */ return "success";
			/*      */ }
		/* 769 */ String bankID = this.request.getParameter("bankID");
		/* 770 */ if ((bankID == null) || (bankID.trim().length() <= 0))
		/*      */ {
			/* 772 */ addReturnValue(-1, 133302L);
			/* 773 */ return "success";
			/*      */ }
		/* 775 */ String accountName1 = this.request.getParameter("accountName1").trim();
		/* 776 */ String RelatingAcct = this.request.getParameter("RelatingAcct").trim();
		/* 777 */ String RelatingAcctName = this.request.getParameter("RelatingAcctName").trim();
		/* 778 */ String RelatingAcctBank = this.request.getParameter("RelatingAcctBank").trim();
		/* 779 */ String RelatingAcctBankAddr = this.request.getParameter("RelatingAcctBankAddr").trim();
		/* 780 */ String RelatingAcctBankCode = this.request.getParameter("RelatingAcctBankCode").trim();
		/* 781 */ String PersonName = this.request.getParameter("PersonName").trim();
		/* 782 */ String OfficeTel = this.request.getParameter("OfficeTel").trim();
		/* 783 */ String MobileTel = this.request.getParameter("MobileTel").trim();
		/* 784 */ String Addr = this.request.getParameter("Addr").trim();
		/* 785 */ String LawName = this.request.getParameter("LawName").trim();
		/*      */
		/* 787 */ String NoteFlag = this.request.getParameter("NoteFlag").trim();
		/* 788 */ String NotePhone = this.request.getParameter("NotePhone").trim();
		/* 789 */ String eMail = this.request.getParameter("eMail").trim();
		/* 790 */ String zipCode = this.request.getParameter("ZipCode").trim();
		/* 791 */ String checkFlag = this.request.getParameter("CheckFlag").trim();
		/*      */
		/* 793 */ Vector corrList = null;
		/*      */ try
		/*      */ {
			/* 796 */ corrList = this.capitalProcessorRMI.getCorrespondValue(" where firmID ='" + firmID + "'");
			/*      */ }
			/*      */ catch (RemoteException e)
		/*      */ {
			/* 800 */ e.printStackTrace();
			/*      */ }
		/* 802 */ CorrespondValue corr = new CorrespondValue();
		/* 803 */ corr.bankID = bankID;
		/* 804 */ corr.firmID = firmID;
		/* 805 */ corr.account = RelatingAcct;
		/* 806 */ corr.accountName = RelatingAcctName;
		/* 807 */ corr.accountName1 = accountName1;
		/* 808 */ corr.bankName = RelatingAcctBank;
		/* 809 */ corr.bankCity = RelatingAcctBankAddr;
		/* 810 */ corr.mobile = OfficeTel;
		/* 811 */ corr.status = 0;
		/* 812 */ corr.isOpen = 1;
		/* 813 */ corr.cardType = ((CorrespondValue) corrList.get(0)).cardType;
		/* 814 */ corr.card = ((CorrespondValue) corrList.get(0)).card;
		/* 815 */ corr.OpenBankCode = RelatingAcctBankCode;
		/* 816 */ corr.Phone = MobileTel;
		/* 817 */ corr.Linkman = PersonName;
		/* 818 */ corr.addr = Addr;
		/* 819 */ corr.LawName = LawName;
		/* 820 */ corr.NoteFlag = NoteFlag;
		/* 821 */ corr.NotePhone = NotePhone;
		/* 822 */ corr.email = eMail;
		/* 823 */ corr.zipCode = zipCode;
		/* 824 */ corr.checkFlag = checkFlag;
		/*      */
		/* 828 */ User user = (User) this.request.getSession().getAttribute("CurrentUser");
		/* 829 */ gnnt.MEBS.bank.front.model.Log log = new gnnt.MEBS.bank.front.model.Log();
		/* 830 */ log.setLogDate(new Date());
		/* 831 */ log.setLogIP(user.getIpAddress());
		/* 832 */ log.setLogopr(user.getUserID());
		/*      */
		/* 834 */ String success = "";
		/* 835 */ String error = "";
		/*      */ try
		/*      */ {
			/* 839 */ ReturnValue result = this.capitalProcessorRMI.openAccountMarket(corr);
			/* 840 */ if (result.result == 0L) {
				/* 841 */ log.setLogcontent("交易商 " + firmID + " 在银行 " + bankID +
						/* 842 */ " 帐号，他行签约成功。");
				/* 843 */ getService().add(log);
				/* 844 */ if (success.trim().length() > 0) /* 845 */ success = success + ",";
				/* 846 */ success = success + bankID;
				/*      */ } else {
				/* 848 */ log.setLogcontent("交易商 " + firmID + " 在银行 " + bankID +
						/* 849 */ " 帐号，他行签约失败。" + BankCoreCode.getCode(result.result));
				/* 850 */ getService().add(log);
				/* 851 */ this.logger.debug("交易商 " + firmID + " 在银行 " + bankID + " 帐号，" +
						/* 852 */ BankCoreCode.getCode(result.result));
				/* 853 */ if (error.trim().length() > 0) /* 854 */ error = error + ",";
				/* 855 */ error = error + "银行" + bankID + "：" + BankCoreCode.getCode(result.result);
				/*      */ }
			/*      */ } catch (Exception e) {
			/* 858 */ log.setLogcontent("交易商 " + firmID + " 在银行 " + bankID + " 帐号，发生异常");
			/* 859 */ getService().add(log);
			/* 860 */ if (error.trim().length() > 0) /* 861 */ error = error + ",";
			/* 862 */ error = error + "银行" + bankID + "：异常";
			/* 863 */ this.logger.error("交易商 " + firmID + " 在银行 " + bankID + " 帐号异常", e);
			/*      */ }
		/*      */
		/* 866 */ String msg = "交易商 " + firmID + " 银行帐号";
		/* 867 */ if (success.length() > 0) {
			/* 868 */ msg = msg + "," + success + " 成功";
			/*      */ }
		/* 870 */ if (error.length() > 0) {
			/* 871 */ msg = msg + "," + error;
			/*      */ }
		/*      */
		/* 874 */ addReturnValue(1, 130000L, new Object[] { msg });
		/*      */
		/* 876 */ return "success";
		/*      */ }

	/*      */
	/*      */ public String openRegist()
	/*      */ {
		/* 885 */ String firmID = this.request.getParameter("firmID");
		/* 886 */ if ((firmID == null) || (firmID.trim().length() <= 0))
		/*      */ {
			/* 888 */ addReturnValue(-1, 133301L);
			/* 889 */ return "success";
			/*      */ }
		/* 891 */ String bankID = this.request.getParameter("bankID");
		/* 892 */ if ((bankID == null) || (bankID.trim().length() <= 0))
		/*      */ {
			/* 894 */ addReturnValue(-1, 133302L);
			/* 895 */ return "success";
			/*      */ }
		/*      */
		/* 898 */ User user = (User) this.request.getSession().getAttribute("CurrentUser");
		/* 899 */ gnnt.MEBS.bank.front.model.Log log = new gnnt.MEBS.bank.front.model.Log();
		/* 900 */ log.setLogDate(new Date());
		/* 901 */ log.setLogIP(user.getIpAddress());
		/* 902 */ log.setLogopr(user.getUserID());
		/*      */
		/* 904 */ String pwd = this.request.getParameter("password");
		/* 905 */ String success = "";
		/* 906 */ String error = "";
		/*      */
		/* 909 */ FirmIDAndAccount firmIDAndAccount = (FirmIDAndAccount) getFirmIDAndAccount(firmID, bankID).clone();
		/* 910 */ CorrespondValue cv = firmIDAndAccount.getCorrespondValue();
		/* 911 */ cv.status = 0;
		/* 912 */ cv.isOpen = 0;
		/* 913 */ cv.bankCardPassword = pwd;
		/*      */ try {
			/* 915 */ if (!Util.isContentsBank(ApplicationContextInit.getConfig("marketPerformOpenAccount"), bankID)) {
				/* 916 */ log.setLogcontent("签约交易商 " + firmID + " 在银行 " + bankID + " 代码，失败。本银行不允许交易所发起签约");
				/* 917 */ getService().add(log);
				/* 918 */ this.logger.debug("签约交易商 " + firmID + " 在银行 " + bankID + " 代码，失败。本银行不允许交易所发起签约");
				/* 919 */ if (error.trim().length() > 0)
					error = error + ",";
				/* 920 */ error = error + bankID;
				/*      */ }
			/*      */
			/* 924 */ ReturnValue result = this.capitalProcessorRMI.openAccountMarket(cv);
			/* 925 */ if (result.result == 0L) {
				/* 926 */ log.setLogcontent("签约交易商 " + firmID + " 在银行 " + bankID + " 代码，成功");
				/* 927 */ getService().add(log);
				/* 928 */ if (success.trim().length() > 0)
					success = success + ",";
				/* 929 */ success = success + bankID;
				/*      */ } else {
				/* 931 */ log.setLogcontent("签约交易商 " + firmID + " 在银行 " + bankID + " 代码，失败。" + BankCoreCode.getCode(result.result));
				/* 932 */ getService().add(log);
				/* 933 */ this.logger.debug("签约交易商 " + firmID + " 在银行 " + bankID + " 代码，" + BankCoreCode.getCode(result.result));
				/* 934 */ if (error.trim().length() > 0)
					error = error + ",";
				/* 935 */ error = error + "银行" + bankID + "：" + BankCoreCode.getCode(result.result);
				/*      */ }
			/*      */ } catch (Exception e) {
			/* 938 */ log.setLogcontent("签约交易商 " + firmID + " 在银行 " + bankID + " 代码，发生异常");
			/* 939 */ getService().add(log);
			/* 940 */ if (error.trim().length() > 0)
				error = error + ",";
			/* 941 */ error = error + "银行" + bankID + "：异常";
			/* 942 */ this.logger.error("签约交易商 " + firmID + " 在银行 " + bankID + " 代码异常", e);
			/*      */ }
		/*      */
		/* 945 */ String msg = "签约交易商 " + firmID + " 银行代码";
		/* 946 */ if (success.length() > 0) {
			/* 947 */ msg = msg + "," + success + " 成功";
			/*      */ }
		/* 949 */ if (error.length() > 0) {
			/* 950 */ msg = msg + "," + error;
			/*      */ }
		/*      */
		/* 953 */ addReturnValue(1, 130000L, new Object[] { msg });
		/*      */
		/* 955 */ return "success";
		/*      */ }

	/*      */
	/*      */ public String delRegist()
	/*      */ {
		/* 965 */ String firmID = this.request.getParameter("firmID");
		/* 966 */ if ((firmID == null) || (firmID.trim().length() <= 0))
		/*      */ {
			/* 968 */ addReturnValue(-1, 133301L);
			/* 969 */ return "success";
			/*      */ }
		/* 971 */ String bankID = this.request.getParameter("bankID");
		/* 972 */ if ((bankID == null) || (bankID.trim().length() <= 0))
		/*      */ {
			/* 974 */ addReturnValue(-1, 133302L);
			/* 975 */ return "success";
			/*      */ }
		/*      */
		/* 978 */ User user = (User) this.request.getSession().getAttribute("CurrentUser");
		/* 979 */ gnnt.MEBS.bank.front.model.Log log = new gnnt.MEBS.bank.front.model.Log();
		/* 980 */ log.setLogDate(new Date());
		/* 981 */ log.setLogIP(user.getIpAddress());
		/* 982 */ log.setLogopr(user.getUserID());
		/*      */
		/* 985 */ String success = "";
		/* 986 */ String error = "";
		/* 987 */ String pwd = this.request.getParameter("password");
		/* 988 */ FirmIDAndAccount firmIDAndAccount = (FirmIDAndAccount) getFirmIDAndAccount(firmID, bankID).clone();
		/* 989 */ CorrespondValue cv = firmIDAndAccount.getCorrespondValue();
		/* 990 */ cv.bankCardPassword = pwd;
		/*      */ try {
			/* 992 */ if (!Util.isContentsBank(ApplicationContextInit.getConfig("marketPerformDelAccount"), bankID)) {
				/* 993 */ log.setLogcontent("注销交易商 " + firmID + " 在银行 " + bankID + " 帐号，银行不允许交易所端发起解约");
				/* 994 */ getService().add(log);
				/* 995 */ if (error.trim().length() > 0)
					error = error + ",";
				/* 996 */ error = error + bankID;
				/*      */ }
			/*      */
			/* 999 */ long result = 0L;
			/* 1000 */ if (Util.isContentsBank(ApplicationContextInit.getConfig("noAdapterBank"), bankID))
				/* 1001 */ result = this.capitalProcessorRMI.delAccountNoAdapter(cv);
			/*      */ else {
				/* 1003 */ result = this.capitalProcessorRMI.delAccountMaket(cv);
				/*      */ }
			/* 1005 */ if (result == 0L) {
				/* 1006 */ log.setLogcontent("注销交易商 " + firmID + " 在银行 " + bankID + " 帐号，成功");
				/* 1007 */ getService().add(log);
				/* 1008 */ if (success.trim().length() > 0)
					success = success + ",";
				/* 1009 */ success = success + bankID;
				/*      */ } else {
				/* 1011 */ log.setLogcontent("注销交易商 " + firmID + " 在银行 " + bankID + " 帐号，失败，" + BankCoreCode.getCode(result));
				/* 1012 */ getService().add(log);
				/* 1013 */ this.logger.debug("注销交易商 " + firmID + " 在银行 " + bankID + " 帐号，" + BankCoreCode.getCode(result));
				/* 1014 */ if (error.trim().length() > 0)
					error = error + ",";
				/* 1015 */ error = error + "银行" + bankID + "：" + BankCoreCode.getCode(result);
				/*      */ }
			/*      */ }
			/*      */ catch (Exception e) {
			/* 1019 */ log.setLogcontent("注销交易商 " + firmID + " 在银行 " + bankID + " 帐号，发生异常");
			/* 1020 */ getService().add(log);
			/* 1021 */ if (error.trim().length() > 0)
				error = error + ",";
			/* 1022 */ error = error + "银行" + bankID + "：异常";
			/* 1023 */ this.logger.error("注销交易商 " + firmID + " 在银行 " + bankID + " 帐号异常", e);
			/*      */ }
		/*      */
		/* 1027 */ String msg = "注销交易商 " + firmID + " 银行帐号";
		/* 1028 */ if (success.length() > 0) {
			/* 1029 */ msg = msg + "," + success + " 成功";
			/*      */ }
		/* 1031 */ if (error.length() > 0) {
			/* 1032 */ msg = msg + "," + error;
			/*      */ }
		/*      */
		/* 1035 */ addReturnValue(1, 130000L, new Object[] { msg });
		/*      */
		/* 1037 */ return "success";
		/*      */ }

	/*      */
	/*      */ private FirmIDAndAccount getFirmIDAndAccount(String firmID, String bankID)
	/*      */ {
		/* 1049 */ FirmIDAndAccount result = new FirmIDAndAccount();
		/* 1050 */ MFirm firm = new MFirm();
		/* 1051 */ firm.setFirmID(firmID);
		/* 1052 */ result.setFirm(firm);
		/* 1053 */ Bank bank = new Bank();
		/* 1054 */ bank.setBankID(bankID);
		/* 1055 */ result.setBank(bank);
		/* 1056 */ return (FirmIDAndAccount) getService().get(result);
		/*      */ }

	/*      */
	/*      */ public String gotoRgstDelFirmInfoPageGFB() {
		/* 1060 */ this.logger.info("国付宝签解约");
		/* 1061 */ String msg = "";
		/*      */ try
		/*      */ {
			/* 1064 */ String bankID = this.request.getParameter("bankID");
			/* 1065 */ String firmID = this.request.getParameter("firmID");
			/* 1066 */ String account = this.request.getParameter("account");
			/* 1067 */ String accountName = this.request.getParameter("accountName");
			/* 1068 */ int rgstdelType = Integer.parseInt(this.request.getParameter("rgstdelType"));
			/* 1069 */ this.logger.info("签解约交易商代码[" + firmID + "] 账号[" + account + "] 户名[" + accountName + "] 签解约类型[" + rgstdelType + "] ");
			/* 1070 */ FirmIDAndAccount firmIDAndAccount = (FirmIDAndAccount) getFirmIDAndAccount(firmID, bankID).clone();
			/* 1071 */ CorrespondValue cv = firmIDAndAccount.getCorrespondValue();
			/*      */
			/* 1073 */ ReturnValue returnValue = this.capitalProcessorRMI.addRgstCapitalValue(cv, rgstdelType);
			/* 1074 */ if (returnValue.result < 0L) {
				/* 1075 */ msg = "调用处理器验证签解约失败：" + returnValue.remark;
				/* 1076 */ addReturnValue(-1, 130000L, new Object[] { msg });
				/* 1077 */ return "error";
				/*      */ }
			/*      */
			/* 1080 */ String GFBTime = Tool.getGSYTTime();
			/* 1081 */ long actionID = this.capitalProcessorRMI.getMktActionID();
			/* 1082 */ String backgroundMerUrl = ApplicationContextInit.getConfig("BackgroundMerUrl_GFB");
			/* 1083 */ String VerficationCode = ApplicationContextInit.getConfig("VerficationCode_GFB");
			/* 1084 */ String frontMerUrl = ApplicationContextInit.getConfig("FrontMerUrl_GFB");
			/* 1085 */ String MarkCode = ApplicationContextInit.getConfig("MarkCode_GFB");
			/*      */
			/* 1087 */ String tranIP = this.request.getRemoteAddr();
			/* 1088 */ String version = "2.0";
			/* 1089 */ String signType = "1";
			/* 1090 */ String merOrderNum = MarkCode + "-" + actionID;
			/* 1091 */ String signMsg = null;
			/* 1092 */ String tranCode = "10000";
			/* 1093 */ if (rgstdelType == 0) {
				/* 1094 */ signMsg = "version=[" + version + "]signType=[" + signType + "]" +
						/* 1095 */ "tranCode=[" + tranCode + "]merchantID=[" + MarkCode + "]" +
						/* 1096 */ "merOrderNum=[" + merOrderNum + "]tranDateTime=[" + GFBTime + "]" +
						/* 1097 */ "merURL=[" + backgroundMerUrl + "]VerficationCode=[" + VerficationCode + "]";
				/*      */ } else {
				/* 1099 */ tranCode = "10001";
				/* 1100 */ signMsg = "version=[" + version + "]signType=[" + signType + "]" +
						/* 1101 */ "tranCode=[" + tranCode + "]merchantID=[" + MarkCode + "]" +
						/* 1102 */ "merOrderNum=[" + merOrderNum + "]tranDateTime=[" + GFBTime + "]" +
						/* 1103 */ "merURL=[" + backgroundMerUrl + "]contractNo=[" + account + "]" +
						/* 1104 */ "VerficationCode=[" + VerficationCode + "]";
				/*      */ }
			/*      */
			/* 1107 */ this.logger.info("签解约加密明文signMsg:" + signMsg);
			/* 1108 */ String signValue = MD5orSHA.md5(signMsg);
			/* 1109 */ this.logger.info("签约加密密文signMsg:" + signValue);
			/*      */
			/* 1111 */ this.request.setAttribute("tranCode", tranCode);
			/* 1112 */ this.request.setAttribute("firmID", firmID);
			/* 1113 */ this.request.setAttribute("account", account);
			/* 1114 */ this.request.setAttribute("accountName", accountName);
			/* 1115 */ this.request.setAttribute("GFBTime", GFBTime);
			/* 1116 */ this.request.setAttribute("signValue", signValue);
			/* 1117 */ this.request.setAttribute("tranIP", tranIP);
			/* 1118 */ this.request.setAttribute("backgroundMerUrl", backgroundMerUrl);
			/* 1119 */ this.request.setAttribute("frontMerUrl", frontMerUrl);
			/* 1120 */ this.request.setAttribute("MarkCode", MarkCode);
			/* 1121 */ this.request.setAttribute("merOrderNum", merOrderNum);
			/* 1122 */ msg = "签解约信息准备成功";
			/*      */ }
			/*      */ catch (RemoteException e)
		/*      */ {
			/* 1126 */ this.logger.error("执行取市场流水号时异常", e);
			/* 1127 */ msg = "获取流水号失败";
			/* 1128 */ addReturnValue(-1, 130000L, new Object[] { msg });
			/* 1129 */ return "error";
			/*      */ }
			/*      */ catch (UnsupportedEncodingException e) {
			/* 1132 */ this.logger.error("获取签解约信息签名异常", e);
			/* 1133 */ msg = "获取签解约信息签名失败";
			/* 1134 */ addReturnValue(-1, 130000L, new Object[] { msg });
			/* 1135 */ return "error";
			/*      */ }
			/*      */ catch (IOException e) {
			/* 1138 */ this.logger.error("取得国付宝时间戳异常", e);
			/* 1139 */ msg = "取得国付宝时间戳失败";
			/* 1140 */ addReturnValue(-1, 130000L, new Object[] { msg });
			/* 1141 */ return "error";
			/*      */ } catch (Exception e) {
			/* 1143 */ this.logger.error("国付宝签解约其他异常", e);
			/* 1144 */ msg = "国付宝签解约其他异常";
			/* 1145 */ addReturnValue(-1, 130000L, new Object[] { msg });
			/* 1146 */ return "error";
			/*      */ }
		/*      */
		/* 1149 */ return "success";
		/*      */ }

	/*      */
	/*      */ public String moneyTransferGFB() {
		/* 1153 */ this.logger.info("国付宝入金");
		/* 1154 */ String msg = "";
		/*      */ try
		/*      */ {
			/* 1157 */ String bankID = this.request.getParameter("bankID");
			/* 1158 */ if ((bankID == null) || (bankID.trim().length() <= 0))
			/*      */ {
				/* 1160 */ addReturnValue(-1, 2830101L);
				/* 1161 */ return "error";
				/*      */ }
			/* 1163 */ User user = (User) this.request.getSession().getAttribute("CurrentUser");
			/* 1164 */ FirmIDAndAccount ffirmIDAndAccount = new FirmIDAndAccount();
			/* 1165 */ Bank bank = new Bank();
			/* 1166 */ bank.setBankID(bankID);
			/* 1167 */ bank = (Bank) getService().get(bank);
			/* 1168 */ ffirmIDAndAccount.setBank(bank);
			/* 1169 */ ffirmIDAndAccount.setFirm(user.getBelongtoFirm());
			/* 1170 */ FirmIDAndAccount firmIDAndAccount = (FirmIDAndAccount) getService().get(ffirmIDAndAccount);
			/* 1171 */ firmIDAndAccount.setBank(bank);
			/* 1172 */ if (firmIDAndAccount == null)
			/*      */ {
				/* 1174 */ addReturnValue(-1, 2830104L, new Object[] { bankID });
				/* 1175 */ return "error";
				/*      */ }
			/* 1177 */ if (firmIDAndAccount.getIsOpen().intValue() != 1)
			/*      */ {
				/* 1179 */ addReturnValue(-1, 2830105L, new Object[] { bankID });
				/* 1180 */ return "error";
				/*      */ }
			/* 1182 */ if (firmIDAndAccount.getStatus().intValue() != 0)
			/*      */ {
				/* 1184 */ addReturnValue(-1, 2830106L, new Object[] { bankID });
				/* 1185 */ return "error";
				/*      */ }
			/* 1187 */ if (firmIDAndAccount.getBank().getValidflag().intValue() != 0)
			/*      */ {
				/* 1189 */ addReturnValue(-1, 2830107L, new Object[] { bankID });
				/* 1190 */ return "error";
				/*      */ }
			/*      */
			/* 1193 */ double money = Tools.strToDouble(this.request.getParameter("money"), -1000.0D);
			/* 1194 */ if (money <= 0.0D)
			/*      */ {
				/* 1196 */ addReturnValue(-1, 2830102L);
				/* 1197 */ return "error";
				/*      */ }
			/*      */
			/* 1200 */ String firmID = firmIDAndAccount.getFirm().getFirmID();
			/* 1201 */ String account = firmIDAndAccount.getAccount();
			/* 1202 */ String accountName = firmIDAndAccount.getAccountName();
			/*      */
			/* 1205 */ this.logger
					.info("国付宝交易商代码[" + firmIDAndAccount.getFirm().getFirmID() + "] 账号[" + account + "] 户名[" + accountName + "] 金额[" + money + "]");
			/*      */
			/* 1207 */ long result = this.capitalProcessorRMI.inMoneyMarketGS(bankID, firmID, account, "", money, "market_in");
			/* 1208 */ if (result < 0L) {
				/* 1209 */ msg = "调用处理器验证入金失败：" + (String) ErrorCode.error.get(Long.valueOf(result));
				/* 1210 */ addReturnValue(-1, 130000L, new Object[] { msg });
				/* 1211 */ return "error";
				/*      */ }
			/*      */
			/* 1214 */ String GFBTime = Tool.getGSYTTime();
			/* 1215 */ long actionID = result;
			/* 1216 */ String backgroundMerUrl = ApplicationContextInit.getConfig("BackgroundMerUrl_GFB");
			/* 1217 */ String VerficationCode = ApplicationContextInit.getConfig("VerficationCode_GFB");
			/* 1218 */ String frontMerUrl = ApplicationContextInit.getConfig("FrontMerUrl_GFB");
			/* 1219 */ String MarkCode = ApplicationContextInit.getConfig("MarkCode_GFB");
			/* 1220 */ String MarketGSAcount = ApplicationContextInit.getConfig("MarketGSAcount_GFB");
			/*      */
			/* 1222 */ String tranIP = this.request.getRemoteAddr();
			/* 1223 */ String version = "2.0";
			/* 1224 */ String signType = "1";
			/* 1225 */ String merOrderNum = MarkCode + "-" + actionID;
			/* 1226 */ String msgExt = firmID + "|" + account;
			/*      */
			/* 1228 */ String signMsg = "version=[" + version + "]tranCode=[8801]" +
					/* 1229 */ "merchantID=[" + MarkCode + "]contractNo=[" + account + "]" +
					/* 1230 */ "merOrderNum=[" + merOrderNum + "]virCardNoIn=[" + MarketGSAcount + "]" +
					/* 1231 */ "tranAmt=[" + Tool.fmtDouble2(money) + "]currencyType=[156]" +
					/* 1232 */ "tranDateTime=[" + GFBTime + "]frontMerUrl=[" + frontMerUrl + "]" +
					/* 1233 */ "backgroundMerUrl=[" + backgroundMerUrl + "]signType=[" + signType + "]" +
					/* 1234 */ "isRepeatSubmit=[1]tranIP=[" + tranIP + "]" +
					/* 1235 */ "VerficationCode=[" + VerficationCode + "]";
			/* 1236 */ this.logger.info("入金加密明文signMsg:" + signMsg);
			/* 1237 */ String signValue = MD5orSHA.md5(signMsg);
			/* 1238 */ this.logger.info("入金加密密文signMsg:" + signValue);
			/*      */
			/* 1241 */ this.request.setAttribute("firmID", firmID);
			/* 1242 */ this.request.setAttribute("account", account);
			/* 1243 */ this.request.setAttribute("accountName", accountName);
			/* 1244 */ this.request.setAttribute("GFBTime", GFBTime);
			/* 1245 */ this.request.setAttribute("signValue", signValue);
			/* 1246 */ this.request.setAttribute("tranIP", tranIP);
			/* 1247 */ this.request.setAttribute("backgroundMerUrl", backgroundMerUrl);
			/* 1248 */ this.request.setAttribute("frontMerUrl", frontMerUrl);
			/* 1249 */ this.request.setAttribute("MarkCode", MarkCode);
			/* 1250 */ this.request.setAttribute("merOrderNum", merOrderNum);
			/* 1251 */ this.request.setAttribute("money", Tool.fmtDouble2(money));
			/* 1252 */ this.request.setAttribute("MarketGSAcount", MarketGSAcount);
			/* 1253 */ this.request.setAttribute("msgExt", msgExt);
			/*      */
			/* 1255 */ msg = "入金信息准备成功";
			/*      */ }
			/*      */ catch (RemoteException e)
		/*      */ {
			/* 1259 */ this.logger.error("执行取市场流水号时异常", e);
			/* 1260 */ msg = "获取流水号失败";
			/* 1261 */ addReturnValue(-1, 130000L, new Object[] { msg });
			/* 1262 */ return "error";
			/*      */ }
			/*      */ catch (UnsupportedEncodingException e) {
			/* 1265 */ this.logger.error("获取入金信息签名异常", e);
			/* 1266 */ msg = "获取入金信息签名失败";
			/* 1267 */ addReturnValue(-1, 130000L, new Object[] { msg });
			/* 1268 */ return "error";
			/*      */ }
			/*      */ catch (IOException e) {
			/* 1271 */ this.logger.error("取得国付宝时间戳异常", e);
			/* 1272 */ msg = "取得国付宝时间戳失败";
			/* 1273 */ addReturnValue(-1, 130000L, new Object[] { msg });
			/* 1274 */ return "error";
			/*      */ } catch (Exception e) {
			/* 1276 */ this.logger.error("国付宝入金其他异常", e);
			/* 1277 */ msg = "国付宝入金其他异常";
			/* 1278 */ addReturnValue(-1, 130000L, new Object[] { msg });
			/* 1279 */ return "error";
			/*      */ }
		/*      */
		/* 1282 */ return "success";
		/*      */ }
	/*      */ }