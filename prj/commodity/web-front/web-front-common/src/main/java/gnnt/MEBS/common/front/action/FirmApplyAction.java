package gnnt.MEBS.common.front.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import gnnt.MEBS.common.front.common.Page;
import gnnt.MEBS.common.front.common.PageRequest;
import gnnt.MEBS.common.front.model.Client;
import gnnt.MEBS.common.front.model.StandardModel;
import gnnt.MEBS.common.front.model.integrated.CertificateType;
import gnnt.MEBS.common.front.model.integrated.Industry;
import gnnt.MEBS.common.front.model.integrated.MFirmApply;
import gnnt.MEBS.common.front.model.integrated.Zone;

@Controller("com_firmApplyAction")
@Scope("request")
public class FirmApplyAction extends StandardAction {
	private List<File> upPicture;
	private List<String> upPictureFileName;
	private List<String> upPictureContentType;
	private static final long serialVersionUID = 1521609715400531850L;
	@Autowired
	@Resource(name = "com_firmTypeMap")
	private Map<String, String> com_firmTypeMap;

	public List<File> getUpPicture() {
		return this.upPicture;
	}

	public void setUpPicture(List<File> upPicture) {
		this.upPicture = upPicture;
	}

	public List<String> getUpPictureFileName() {
		return this.upPictureFileName;
	}

	public void setUpPictureFileName(List<String> upPictureFileName) {
		this.upPictureFileName = upPictureFileName;
	}

	public List<String> getUpPictureContentType() {
		return this.upPictureContentType;
	}

	public void setUpPictureContentType(List<String> upPictureContentType) {
		this.upPictureContentType = upPictureContentType;
	}

	public FirmApplyAction() {
		super.setEntityName(MFirmApply.class.getName());
	}

	public Map<String, String> getCom_firmTypeMap() {
		return this.com_firmTypeMap;
	}

	public String firmApplyForwardTemp() {
		return "success";
	}

	public String firmApplyForward() {
		PageRequest<String> pageRequest = new PageRequest(" order by sortNo");

		Page<StandardModel> zone = getService().getPage(pageRequest, new Zone());

		this.request.setAttribute("zoneList", zone.getResult());

		Page<StandardModel> industry = getService().getPage(pageRequest, new Industry());

		this.request.setAttribute("industryList", industry.getResult());

		Page<StandardModel> certificateType = getService().getPage(pageRequest, new CertificateType());

		this.request.setAttribute("certificateTypeList", certificateType.getResult());
		try {
			List<Map<String, String>> brokerName = getService().getBrokerName();
			Set<Map<String, String>> areaidSet = new HashSet();
			String areaid;
			for (int i = 0; i < brokerName.size(); i++) {
				areaid = (String) ((Map) brokerName.get(i)).get("AREAID");
				Map<String, String> newMap = new HashMap();
				if (areaid == null) {
					areaid = "";
				}
				newMap.put("AREAID", (String) ((Map) brokerName.get(i)).get("AREAID"));
				newMap.put("AREA", (String) ((Map) brokerName.get(i)).get("AREA"));
				areaidSet.add(newMap);
			}
			for (Map<String, String> map : areaidSet) {
				String ss = "";
				for (int i = 0; i < brokerName.size(); i++) {
					if (((String) map.get("AREAID")).equals(((Map) brokerName.get(i)).get("AREAID"))) {
						ss = ss + (String) ((Map) brokerName.get(i)).get("BROKERID") + "#" + (String) ((Map) brokerName.get(i)).get("NAME") + ";";
					}
				}
				map.put("OP", ss);
			}
			this.request.setAttribute("areaidSet", areaidSet);
			this.request.setAttribute("brokers", brokerName);
		} catch (SQLException e) {
			e.printStackTrace();
			addReturnValue(-1, 9940001L);
		}
		return "success";
	}

	public String firmApply() {
		System.out.println("进入提交方法****************");
		MFirmApply apply = (MFirmApply) this.entity;
		System.out.println(apply);
		apply.setCreateTime(getService().getSysDate());

		String randomicitynum = (String) this.request.getSession().getAttribute("RANDOMICITYNUM");

		String imgcode = this.request.getParameter("imgcode");
		if ((randomicitynum == null) || (randomicitynum.trim().length() < 0)) {
			this.logger.debug("系统生成的验证码为空值");
			addReturnValue(-1, 9930103L);
			return "error";
		}
		if ((imgcode == null) || (imgcode.trim().length() < 0)) {
			this.logger.debug("用户传入验证码为空值");
			addReturnValue(-1, 9930103L);
			return "error";
		}
		if (!imgcode.equalsIgnoreCase(randomicitynum)) {
			this.logger.debug("用户输入验证码[" + imgcode + "]与系统生成的验证码[" + randomicitynum + "]不一致");

			addReturnValue(-1, 9930103L);
			return "error";
		}
		System.out.println("========");
		System.out.println("file length>>>>>>>>>>>>>>" + this.upPicture.size());
		try {
			System.out.println("enter set pic");
			if (this.upPicture.get(0) != null) {
				InputStream is = new FileInputStream((File) this.upPicture.get(0));
				byte[] bs = new byte[is.available()];
				is.read(bs);
				apply.setPicturecs(bs);
				is.close();
			}
			if (this.upPicture.size() == 3) {
				System.out.println("enter geren if >>>>>>>>>>>>>>");
				if (this.upPicture.get(1) != null) {
					InputStream is = new FileInputStream((File) this.upPicture.get(1));
					byte[] bs = new byte[is.available()];
					is.read(bs);
					apply.setPictureos(bs);
					is.close();
				}
				if (this.upPicture.get(2) != null) {
					InputStream is = new FileInputStream((File) this.upPicture.get(2));
					byte[] bs = new byte[is.available()];
					is.read(bs);
					apply.setPicture(bs);
					is.close();
				}
			} else if (this.upPicture.size() == 5) {
				System.out.println("enter faren if >>>>>>>>>>>");
				if (this.upPicture.get(1) != null) {
					System.out.println("pic 1 >>>>>>>>" + ((File) this.upPicture.get(1)).length());
					InputStream is = new FileInputStream((File) this.upPicture.get(1));
					byte[] bs = new byte[is.available()];
					is.read(bs);
					apply.setYingYePic(bs);
					is.close();
				}
				if (this.upPicture.get(2) != null) {
					System.out.println("pic 2 >>>>>>>>" + ((File) this.upPicture.get(2)).length());
					InputStream is = new FileInputStream((File) this.upPicture.get(2));
					byte[] bs = new byte[is.available()];
					is.read(bs);
					apply.setShuiWuPic(bs);
					is.close();
				}
				if (this.upPicture.get(3) != null) {
					System.out.println("pic 3 >>>>>>>>" + ((File) this.upPicture.get(3)).length());
					InputStream is = new FileInputStream((File) this.upPicture.get(3));
					byte[] bs = new byte[is.available()];
					is.read(bs);
					apply.setZuZhiPic(bs);
					is.close();
				}
				if (this.upPicture.get(4) != null) {
					System.out.println("pic 4 >>>>>>>>" + ((File) this.upPicture.get(4)).length());
					InputStream is = new FileInputStream((File) this.upPicture.get(4));
					byte[] bs = new byte[is.available()];
					is.read(bs);
					apply.setKaiHuPic(bs);
					is.close();
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return "error";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		getService().add(this.entity);
		Client client = null;
		try {
			client = new Client("SDK-JWA-010-00001", "1234567");
			client.mt(apply.getMobile() + "", apply.getUserID() + "用户" + "您已经成功注册，请等待审核，手机号码：" + apply.getMobile() + "密码：" + apply.getPassword()
					+ "。为了您的交易安全，请您妥善保管您的密码，严防泄露。【长三角商品交易所】", "", "", "654");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		addReturnValue(1, 100001L);
		return "success";
	}
}
