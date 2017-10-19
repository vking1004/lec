package service;

import java.sql.SQLException;

import vo.Notice;

public interface MemberShipService {
	public void insertAndPointUpOfMember(Notice n, String uid) throws ClassNotFoundException, SQLException;

}
