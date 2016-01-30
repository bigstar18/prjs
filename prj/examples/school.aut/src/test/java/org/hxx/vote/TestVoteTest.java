package org.hxx.vote;

import org.junit.Test;

import org.hxx.vote.dao.IVoteDao;
import org.hxx.vote.dao.impl.VoteDao;
import org.hxx.vote.model.TicketInfo;

public class TestVoteTest {

	@Test
	public void testVote() throws Exception {
		IVoteDao dao = new VoteDao();
		TicketInfo ticket = new TicketInfo("123", "123456", "123", "56789", "1");
		dao.vote(ticket);
	}

	@Test
	public void testCheck() throws Exception {
		IVoteDao dao = new VoteDao();
		TicketInfo ticket = new TicketInfo("123", "3589045", "123", "56789", "1");
		if (dao.check(ticket)) {
			System.out.println("已经存在");
		}
	}

	@Test
	public void testGetTickets() throws Exception {
		IVoteDao dao = new VoteDao();
		int result = dao.getTickets("56789");
		System.out.println(result);
	}
}
