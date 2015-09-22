package org.hxx.vote.dao;

import org.hxx.vote.model.TicketInfo;

public interface IVoteDao {
	public void vote(TicketInfo ticket) throws Exception;

	public int getTickets(String picOwnerId) throws Exception;

	public boolean check(TicketInfo ticket) throws Exception;
}
