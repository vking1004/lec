package dao;

import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import vo.Notice;

public class NLNoticeDao extends SimpleJdbcDaoSupport implements NoticeDao{
	//private PlatformTransactionManager transactionManager;
	//private TransactionTemplate transactionTemplate;
	
	/*public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
		this.transactionTemplate = transactionTemplate;
	}*/
	/*private JdbcTemplate template;
		
	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}*/

	/*public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}*/

	public int getCount(String field, String query) throws ClassNotFoundException, SQLException
	{
		String sql = "SELECT COUNT(*) CNT FROM NOTICES WHERE "+field+" LIKE ?";
		
		return getSimpleJdbcTemplate().queryForInt(sql, "%"+query+"%");
		//return template.queryForInt(sql, "%"+query+"%");
		
		/*String sql = "SELECT COUNT(*) CNT FROM NOTICES WHERE "+field+" LIKE ?";
		
		Class.forName("oracle.jdbc.driver.OracleDriver");
		// 1. 접속
		Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl",
				"euijin", "dmlwls");
		// 2. 실행
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, "%"+query+"%");
		
		// 3. 결과
		ResultSet rs = st.executeQuery();
		rs.next();
		
		int cnt = rs.getInt("cnt");
		
		rs.close();
		st.close();
		con.close();
		
		return cnt;*/
	}
	
	

	public List<Notice> getNotices(int page, String field, String query) throws ClassNotFoundException, SQLException
	{					
		int srow = 1 + (page-1)*15; // 1, 16, 31, 46, 61, ... an = a1 + (n-1)*d
		int erow = 15 + (page-1)*15; //15, 30, 45, 60, 75, ...
		
		String sql = "SELECT * FROM";
		sql += "(SELECT ROWNUM NUM, N.* FROM (SELECT * FROM NOTICES WHERE "+field+" LIKE ? ORDER BY REGDATE DESC) N)";
		sql += "WHERE NUM BETWEEN ? AND ?";
		
		return getSimpleJdbcTemplate().query(sql, new BeanPropertyRowMapper<Notice>(Notice.class), "%"+query+"%",  srow, erow);
		
		/*int srow = 1 + (page-1)*15; // 1, 16, 31, 46, 61, ... an = a1 + (n-1)*d
		int erow = 15 + (page-1)*15; //15, 30, 45, 60, 75, ...
		
		String sql = "SELECT * FROM";
		sql += "(SELECT ROWNUM NUM, N.* FROM (SELECT * FROM NOTICES WHERE "+field+" LIKE ? ORDER BY REGDATE DESC) N)";
		sql += "WHERE NUM BETWEEN ? AND ?";
		// 0. 드라이버 로드
		Class.forName("oracle.jdbc.driver.OracleDriver");
		// 1. 접속
		Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe",
				"euijin", "dmlwls");
		// 2. 실행
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, "%"+query+"%");
		st.setInt(2, srow);
		st.setInt(3, erow);
		// 3. 결과
		ResultSet rs = st.executeQuery();
		
		List<Notice> list = new ArrayList<Notice>();
		
		while(rs.next()){
			Notice n = new Notice();
			n.setSeq(rs.getString("seq"));
			n.setTitle(rs.getString("title"));
			n.setWriter(rs.getString("writer"));
			n.setRegdate(rs.getDate("regdate"));
			n.setHit(rs.getInt("hit"));
			n.setContent(rs.getString("content"));
			n.setFileSrc(rs.getString("fileSrc"));
			
			list.add(n);
		}
		
		rs.close();
		st.close();
		con.close();
		
		return list;*/
	}
	
	public int delete(String seq) throws ClassNotFoundException, SQLException
	{
		String sql = "DELETE NOTICES WHERE SEQ=?";
		
		return getSimpleJdbcTemplate().update(sql, seq);
		/*// 2. 데이터 베이스 연동을 위한 쿼리와 실행 코드 작성
		String sql = "DELETE NOTICES WHERE SEQ=?";
		// 0. 드라이버 로드
		Class.forName("oracle.jdbc.driver.OracleDriver");
		// 1. 접속
		Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe",
				"euijin", "dmlwls");
		// 2. 실행
		PreparedStatement st = con.prepareStatement(sql);	
		st.setString(1, seq);
		
		int af = st.executeUpdate();
		
		return af;*/
	}
	
	public int update(Notice notice) throws ClassNotFoundException, SQLException{
		String sql = "UPDATE NOTICES SET TITLE=?, CONTENT=?, FILESRC=? WHERE SEQ=?";
		
		return getSimpleJdbcTemplate().update(sql, 
				notice.getTitle(),
				notice.getContent(), 
				notice.getFileSrc(),
				notice.getSeq());
		/*// 2. 데이터 베이스를 연동하기 위한 쿼리와 데이터베이스 연동을 위한 코드를 작성
		String sql = "UPDATE NOTICES SET TITLE=?, CONTENT=?, FILESRC=? WHERE SEQ=?";
		// 0. 드라이버 로드
		Class.forName("oracle.jdbc.driver.OracleDriver");
		// 1. 접속
		Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe",
				"euijin", "dmlwls");
		// 2. 실행
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, notice.getTitle());
		st.setString(2, notice.getContent());
		st.setString(3, notice.getFileSrc());
		st.setString(4, notice.getSeq());		
		
		int af = st.executeUpdate();
		
		return af;*/
	}
	
	public Notice getNotice(String seq) throws ClassNotFoundException, SQLException
	{
		String sql = "SELECT * FROM NOTICES WHERE SEQ=?";
		
		return getSimpleJdbcTemplate().queryForObject(sql
				, new BeanPropertyRowMapper<Notice>(Notice.class),seq);
		/*return getSimpleJdbcTemplate().queryForObject(sql,
				new RowMapper<Notice>() {

					@Override
					public Notice mapRow(ResultSet rs, int rowNum) throws SQLException {
						
						Notice n = new Notice();
						n.setSeq(rs.getString("seq"));
						n.setTitle(rs.getString("title"));
						n.setWriter(rs.getString("writer"));
						n.setRegdate(rs.getDate("regdate"));
						n.setHit(rs.getInt("hit"));
						n.setContent(rs.getString("content"));
						n.setFileSrc(rs.getString("fileSrc"));
						
						return n;
					}
				
		}, seq);*/
		/*DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
		dataSource.setUrl("jdbc:oracle:thin:@localhost:1521:xe");
		dataSource.setUsername("euijin");
		dataSource.setPassword("dmlwls");*/
		
		/*JdbcTemplate template = new JdbcTemplate();
		template.setDataSource(dataSource);*/
		
		
		
		/*// 0. 드라이버 로드
		Class.forName("oracle.jdbc.driver.OracleDriver");
		// 1. 접속
		Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe",
				"euijin", "dmlwls");
		// 2. 실행
		Statement st = con.createStatement();
		// 3. 결과
		ResultSet rs = st.executeQuery(sql);
		rs.next();
		
		Notice n = new Notice();
		n.setSeq(rs.getString("seq"));
		n.setTitle(rs.getString("title"));
		n.setWriter(rs.getString("writer"));
		n.setRegdate(rs.getDate("regdate"));
		n.setHit(rs.getInt("hit"));
		n.setContent(rs.getString("content"));
		n.setFileSrc(rs.getString("fileSrc"));
		
		rs.close();
		st.close();
		con.close();
		
		return n;*/
	}
	
	@Transactional(propagation = Propagation.NESTED)
	public int insert(Notice n) throws ClassNotFoundException, SQLException {
		String sql = "INSERT INTO NOTICES(SEQ, TITLE, CONTENT, WRITER, REGDATE, HIT, FILESRC) VALUES( "
				+ "(SELECT MAX(TO_NUMBER(SEQ))+1 FROM NOTICES), :title, :content, 'newlec', SYSDATE, 0, :fileSrc)";
		
		
		int result= getSimpleJdbcTemplate().update(sql, new BeanPropertySqlParameterSource(n));
		
		String sqlPoint = "update \"MEMBER\" set point = point + 1 where \"UID\"=?";
		getSimpleJdbcTemplate().update(sqlPoint, "newlec");
		return result;
		
		/*Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("title", n.getTitle());
		parameters.put("content", n.getContent());
		parameters.put("fileSrc", n.getFileSrc());
		
		return getSimpleJdbcTemplate().update(sql, new MapSqlParameterSource(parameters));*/
		
		
		/*return getsimpleJdbcTemplate().update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				String sql = "INSERT INTO NOTICES(SEQ, TITLE, CONTENT, WRITER, REGDATE, HIT, FILESRC) VALUES( (SELECT MAX(TO_NUMBER(SEQ))+1 FROM NOTICES), ?, ?, 'newlec', SYSDATE, 0, ?)";
				PreparedStatement st = con.prepareStatement(sql);
				st.setString(1, n.getTitle());
				st.setString(2, n.getContent());
				st.setString(3, n.getFileSrc());
				
				return st;
			}
		});*/
		
		
		/*String sql = "INSERT INTO NOTICES(SEQ, TITLE, CONTENT, WRITER, REGDATE, HIT, FILESRC) VALUES( (SELECT MAX(TO_NUMBER(SEQ))+1 FROM NOTICES), ?, ?, 'newlec', SYSDATE, 0, ?)";
		// 0. 드라이버 로드
		Class.forName("oracle.jdbc.driver.OracleDriver");
		// 1. 접속
		Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe",
				"euijin", "dmlwls");
		// 2. 실행
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, n.getTitle());
		st.setString(2, n.getContent());
		st.setString(3, n.getFileSrc());
		
		int af = st.executeUpdate();
		
		st.close();
		con.close();
		
		return af;*/
	}



	@Override
	public void hitUp(String seq) {
		String sql = "update notices set hit= hit+1 where seq=?";
		
		String threadName = Thread.currentThread().getName();
		System.out.println(threadName + "->조회수 증가 전");
		
		getSimpleJdbcTemplate().update(sql, seq);
		
		System.out.println(threadName +"->조회수 증가 후");
		
		sleep(threadName, 1000);
		
		System.out.println(threadName + "->조회수 롤백");
		throw new RuntimeException("조회수 증가 예외 발생");
	}



	private void sleep(String threadName, long duration) {
		
		System.out.println(threadName + "->Sleep 시작");
		try {
			Thread.sleep(duration);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(threadName + "->Sleep 끝");
	}



	@Override
	public int getHit(String seq) {
		String sql = "select hit from notices where seq=?";
		
		int hit = getSimpleJdbcTemplate().queryForInt(sql, seq);
		System.out.println(hit);
		return hit;
		
	}

	
}
