package org.hxx.event.dao;

import org.hxx.event.model.UserEvent;

public interface IUserEventDao {

	public UserEvent queryByUserAndEvent(String phone, String eventId) throws Exception;

	public void addUserEvent(UserEvent userEvent) throws Exception;

}
