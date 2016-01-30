
package gnnt.MEBS.activeUser.vo;

import java.util.Date;

/**
 * <P>
 * ��˵�����û���¼ʱ������� VO ����
 * <br/>
 * <br/>
 * </p>
 * �޸ļ�¼:
 * <br/>
 * <ul>
 * 
 * <li>������ |2014-4-21����01:49:35|����̩</li>
 * 
 * </ul>
 * 
 * @author liuzx
 */
public class AULogonVO extends AUBaseVO {
	/** ���б�� */
	private static final long serialVersionUID = 7741736096020219428L;

	/** �û���� */
	private String userID;

	/** ģ���� */
	private int moduleID;

	/**
	 * ��¼����<br/>
	 * web web�����¼<br/>
	 * pc ���Կͻ��˵�¼<br/>
	 * mobile �ֻ�ͻ��˵�¼<br/>
	 */
	private String logonType;
	/**
	 * ��¼ʱ��
	 */
	private Date logonTime = null;
	/**
	 * ��¼Ip��ַ
	 */
	private String logonIp = null;
	private String lastLogonTime;
	private String lastLogonIp;

	public String getLastLogonTime() {
		return this.lastLogonTime;
	}

	public void setLastLogonTime(String lastLogonTime) {
		this.lastLogonTime = lastLogonTime;
	}

	public String getLastLogonIp() {
		return this.lastLogonIp;
	}

	public void setLastLogonIp(String lastLogonIp) {
		this.lastLogonIp = lastLogonIp;
	}

	public AULogonVO() {
		logonTime = new Date();
	}

	/**
	 * 
	 * �û����
	 * <br/>
	 * <br/>
	 * 
	 * @return
	 */
	public String getUserID() {
		return userID;
	}

	/**
	 * 
	 * �û����
	 * <br/>
	 * <br/>
	 * 
	 * @param userID
	 */
	public void setUserID(String userID) {
		this.userID = userID;
	}

	/**
	 * 
	 * ģ����
	 * <br/>
	 * <br/>
	 * 
	 * @return
	 */
	public int getModuleID() {
		return moduleID;
	}

	/**
	 * 
	 * ģ����
	 * <br/>
	 * <br/>
	 * 
	 * @param moduleID
	 */
	public void setModuleID(int moduleID) {
		this.moduleID = moduleID;
	}

	/**
	 * 
	 * ��¼����<br/>
	 * web web�����¼<br/>
	 * pc ���Կͻ��˵�¼<br/>
	 * mobile �ֻ�ͻ��˵�¼<br/>
	 * <br/>
	 * <br/>
	 * 
	 * @return
	 */
	public String getLogonType() {
		return logonType;
	}

	/**
	 * 
	 * ��¼����<br/>
	 * web web�����¼<br/>
	 * pc ���Կͻ��˵�¼<br/>
	 * mobile �ֻ�ͻ��˵�¼<br/>
	 * <br/>
	 * <br/>
	 * 
	 * @param logonType
	 */
	public void setLogonType(String logonType) {
		this.logonType = logonType;
	}

	/**
	 * ��¼ʱ��
	 */
	public Date getLogonTime() {
		return logonTime;
	}

	/**
	 * ��¼ʱ��
	 */
	public void setLogonTime(Date logonTime) {
		this.logonTime = logonTime;
	}

	/**
	 * ��¼Ip��ַ
	 */
	public String getLogonIp() {
		return logonIp;
	}

	/**
	 * ��¼Ip��ַ
	 */
	public void setLogonIp(String logonIp) {
		this.logonIp = logonIp;
	}

}
