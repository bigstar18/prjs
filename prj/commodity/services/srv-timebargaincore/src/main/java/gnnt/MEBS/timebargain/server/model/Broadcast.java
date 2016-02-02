package gnnt.MEBS.timebargain.server.model;

import java.io.*;
import java.util.Date;

import org.apache.commons.lang.builder.*;

/**
 * 广播消息对象
 * @author cxc
 */
public class Broadcast implements Serializable {
    private static final long serialVersionUID = 3690197650654049817L;
    private Long id;
    private String title;
    private String content;
    private Short status;
    private String firmID;
    private Date createTime;
    private String author;
    private Date sendTime;

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getFirmID() {
		return firmID;
	}

	public void setFirmID(String firmID) {
		this.firmID = firmID;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Broadcast)) return false;

        final Broadcast m = (Broadcast) o;

        return !(id != null ? !id.equals(m.id) : m.id != null);

    }

    public int hashCode() {
        return (id != null ? id.hashCode() : 0);
    }

    public String toString() {
    	return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}
