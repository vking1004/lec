package service;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import dao.NoticeDao;
import vo.Notice;

public class NLMemberShipService implements MemberShipService {

	private NoticeDao noticeDao;
	
	@Autowired
	public void setNoticeDao(NoticeDao noticeDao) {
		this.noticeDao = noticeDao;
	}

	@Override
	@Transactional
	public void insertAndPointUpOfMember(Notice n, String uid) throws ClassNotFoundException, SQLException {
		/*String sql = "INSERT INTO NOTICES(SEQ, TITLE, CONTENT, WRITER, REGDATE, HIT, FILESRC) VALUES( "
				+ "(SELECT MAX(TO_NUMBER(SEQ))+1 FROM NOTICES), :title, :content, 'newlec', SYSDATE, 0, :fileSrc)";
		getSimpleJdbcTemplate().update(sql, new BeanPropertySqlParameterSource(n));*/
		
		noticeDao.insert(n);
		n.setTitle(n.getTitle()+"1");
		noticeDao.insert(n);
		
		/*transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				getSimpleJdbcTemplate().update(sql, new BeanPropertySqlParameterSource(n));
				getSimpleJdbcTemplate().update(sqlPoint, uid);
				
			}
		});*/
		
		
		
		
	}

}
