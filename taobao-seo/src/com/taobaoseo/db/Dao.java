package com.taobaoseo.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.logging.Logger;

import javax.naming.NamingException;

import com.taobaoseo.recommendation.domain.RecommendScope;


public class Dao {
	
	static Logger _logger = Logger.getLogger(Dao.class.getName());
	
	public static final Dao INSTANCE = new Dao();
	
	private Dao()
	{
		
	}
	
	public void updateUser(long uid, String nick, String session) throws NamingException, SQLException
	{
		Connection conn = null;
		PreparedStatement statement = null;
		try
		{
			conn = DBUtils.getConnection();
			String sql = "update user_t set session_c=?, last_login_c=? where user_id_c=?";
			statement = conn.prepareStatement(sql);
			statement.setString(1, session);
			statement.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
			statement.setLong(3, uid);
			int result = statement.executeUpdate();
			if (result == 0)
			{
				sql = "insert into user_t(user_id_c, nick_c, session_c, last_login_c) values(?,?,?,?)";
				statement = conn.prepareStatement(sql);
				statement.setLong(1, uid);
				statement.setString(2, nick);
				statement.setString(3, session);
				statement.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
			}
			statement.executeUpdate();
		}
		finally
		{
			DBUtils.close(conn, statement, null);
		}
	}
	
	public String getTopSession(String nick) throws NamingException, SQLException
	{
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet rs;
		try
		{
			conn = DBUtils.getConnection();
			String sql = "select session_c from user_t where nick_c=?";
			statement = conn.prepareStatement(sql);
			statement.setString(1, nick);
			rs = statement.executeQuery();
			if (rs.next())
			{
				return rs.getString("session_c");
			}
		}
		finally
		{
			DBUtils.close(conn, statement, null);
		}
		return null;
	}
	
	public RecommendScope getRecommendScope()
	{
		//TODO: read options from db
		return new RecommendScope();
	}
}
