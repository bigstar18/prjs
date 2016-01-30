
package gnnt.MEBS.activeUser.vo;

import java.util.Date;

/**
 * <P>
 * ��˵�����û���¼��֤������ VO ����
 * <br/>
 * <br/>
 * </p>
 * �޸ļ�¼:
 * <br/>
 * <ul>
 * 
 * <li>������ |2014-4-21����03:41:20|����̩</li>
 * 
 * </ul>
 * 
 * @author liuzx
 */
public class AUCheckUserVO extends AUBaseVO {
	/** ���б�� */
	private static final long serialVersionUID = 6049725213980514919L;

	/** AU SessionID */
	private long sessionID;

	/** �û��� */
	private String userID;

	/** ��ģ���� */
	private int toModuleID;

	/**
	 * ��¼����<br/>
	 * web web�����¼<br/>
	 * pc ���Կͻ��˵�¼<br/>
	 * mobile �ֻ�ͻ��˵�¼<br/>
	 */
	private String logonType;

	/**
	 * ��¼IP
	 */
	private String logonIp;
	/**
	 * ��¼ʱ��
	 */
	private Date logonTime = null;
	private String lastLogonTime;
	private String lastLogonIp;

	public String getLastLogonTime() {
		return lastLogonTime;
	}

	public void setLastLogonTime(String lastLogonTime) {
		this.lastLogonTime = lastLogonTime;
	}

	public String getLastLogonIp() {
		return lastLogonIp;
	}

	public void setLastLogonIp(String lastLogonIp) {
		this.lastLogonIp = lastLogonIp;
	}

	public AUCheckUserVO() {
		logonTime = new Date();
	}

	/**
	 * 
	 * AU SessionID
	 * <br/>
	 * <br/>
	 * 
	 * @return
	 */
	public long getSessionID() {
		return sessionID;
	}

	/**
	 * 
	 * AU SessionID
	 * <br/>
	 * <br/>
	 * 
	 * @param sessionID
	 */
	public void setSessionID(long sessionID) {
		this.sessionID = sessionID;
	}

	/**
	 * 
	 * �û���
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
	 * �û���
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
	 * ��ģ����
	 * <br/>
	 * <br/>
	 * 
	 * @return
	 */
	public int getToModuleID() {
		return toModuleID;
	}

	/**
	 * 
	 * ��ģ����
	 * <br/>
	 * <br/>
	 * 
	 * @param toModuleID
	 */
	public void setToModuleID(int toModuleID) {
		this.toModuleID = toModuleID;
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
	 * �����û���¼Ip��ַ
	 * 
	 * @param ip
	 */
	public void setLogonIp(String logonIp) {
		this.logonIp = logonIp;
	}

	/**
	 * ��ȡ�û���¼ip��ַ
	 * 
	 * @return
	 */

	public String getLogonIp() {
		return logonIp;
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

}