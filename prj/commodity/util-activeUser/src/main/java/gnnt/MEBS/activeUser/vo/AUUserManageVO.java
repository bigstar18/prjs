
package gnnt.MEBS.activeUser.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <P>
 * ��˵������¼�û���Ϣ VO
 * <br/>
 * <br/>
 * </p>
 * �޸ļ�¼:
 * <br/>
 * <ul>
 * 
 * <li>������ |2014-4-21����01:11:45|����̩</li>
 * 
 * </ul>
 * 
 * @author liuzx
 */
public class AUUserManageVO extends AUBaseVO {
	private String lastLogonIp;
	private String lastLogonTime;

	public String getLastLogonIp() {
		return this.lastLogonIp;
	}

	public void setLastLogonIp(String lastLogonIp) {
		this.lastLogonIp = lastLogonIp;
	}

	public String getLastLogonTime() {
		return this.lastLogonTime;
	}

	public void setLastLogonTime(String lastLogonTime) {
		this.lastLogonTime = lastLogonTime;
	}

	/** ���б�� */
	private static final long serialVersionUID = 4463624248939431921L;

	/** AU SessionID */
	private long sessionID;

	/** �û����� */
	private String userID;

	/** ģ���ż��� */
	private List<Integer> moduleIDList = new ArrayList<Integer>();

	/**
	 * ��¼����<br/>
	 * web web�����¼<br/>
	 * pc ���Կͻ��˵�¼<br/>
	 * mobile �ֻ�ͻ��˵�¼<br/>
	 */
	private String logonType;

	/** ������ʱ�� */
	private long lastTime = 0;
	/**
	 * ��¼ʱ��
	 */
	private Date logonTime = null;
	/**
	 * ��¼IP��ַ
	 */
	private String logonIp;

	public AUUserManageVO() {
		lastTime = System.currentTimeMillis();
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
	 * �û�����
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
	 * �û�����
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
	 * ģ���ż���
	 * <br/>
	 * <br/>
	 * 
	 * @return
	 */
	public List<Integer> getModuleIDList() {
		return moduleIDList;
	}

	/**
	 * 
	 * ģ���ż���
	 * <br/>
	 * <br/>
	 * 
	 * @param moduleIDList
	 */
	public void setModuleIDList(List<Integer> moduleIDList) {
		this.moduleIDList = moduleIDList;
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
	 * 
	 * ������ʱ��
	 * <br/>
	 * <br/>
	 * 
	 * @return
	 */
	public long getLastTime() {
		return lastTime;
	}

	/**
	 * 
	 * ������ʱ��
	 * <br/>
	 * <br/>
	 * 
	 * @param lastTime
	 */
	public void setLastTime(long lastTime) {
		this.lastTime = lastTime;
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
	 * ��¼IP��ַ
	 */
	public void setLogonIp(String logonIp) {
		this.logonIp = logonIp;
	}

	/**
	 * ��¼IP��ַ
	 */
	public String getLogonIp() {
		return logonIp;
	}

}
