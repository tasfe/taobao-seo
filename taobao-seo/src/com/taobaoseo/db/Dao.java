package com.taobaoseo.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.naming.NamingException;

import com.taobao.api.domain.Item;
import com.taobaoseo.domain.listing.PlannedItem;
import com.taobaoseo.domain.recommendation.RecommendScope;


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
		ResultSet rs = null;
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
			DBUtils.close(conn, statement, rs);
		}
		return null;
	}
	
	public void logListing(PlannedItem item, long userId) throws NamingException, SQLException
	{
		Connection conn = null;
		PreparedStatement statement = null;
		try
		{
			conn = DBUtils.getConnection();
			String sql = "insert into list_log_t(execute_time_c, title_c, pic_url_c, old_list_time_c, user_id_c) values(?,?,?,?,?)";
			statement = conn.prepareStatement(sql);
			statement.setTimestamp(1, new Timestamp(item.getPlannedListTime().getTime()));
			statement.setString(2, item.getItem().getTitle());
			statement.setString(3, item.getItem().getPicUrl());
			statement.setTimestamp(4, new Timestamp(item.getItem().getListTime().getTime()));
			statement.setLong(5, userId);
			statement.executeUpdate();
		}
		finally
		{
			DBUtils.close(conn, statement, null);
		}
	}
	
	public void deleteOldestListingLog(long userId) throws NamingException, SQLException
	{
		Connection conn = null;
		PreparedStatement statement = null;
		try
		{
			conn = DBUtils.getConnection();
			String sql = "delete from list_log_t where execute_time_c=" +
							"(select MIN(execute_time_c) from " +
								"(select execute_time_c from list_log_t) t " +
								"where user_id_c=?) " +
						"and user_id_c=?";
			statement = conn.prepareStatement(sql);
			statement.setLong(1, userId);
			statement.setLong(2, userId);
			statement.executeUpdate();
		}
		finally
		{
			DBUtils.close(conn, statement, null);
		}
	}
	
	public List<PlannedItem> getListingLog(long userId) throws NamingException, SQLException
	{
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		try
		{
			conn = DBUtils.getConnection();
			String sql = "select * from list_log_t where user_id_c=? order by execute_time_c desc";
			statement = conn.prepareStatement(sql);
			statement.setLong(1, userId);
			rs = statement.executeQuery();
			List<PlannedItem> items = new ArrayList<PlannedItem>();
			while (rs.next())
			{
				PlannedItem item = new PlannedItem();
				Item it = new Item();
				it.setNum(rs.getLong("num_iid_c"));
				it.setTitle(rs.getString("title_c"));
				it.setPicUrl(rs.getString("pic_url_c"));
				it.setListTime(rs.getTimestamp("old_list_time_c"));
				item.setItem(it);
				item.setPlannedListTime(rs.getTimestamp("execute_time_c"));
				items.add(item);
			}
			return items;
		}
		finally
		{
			DBUtils.close(conn, statement, rs);
		}
	}
	
	public RecommendScope getRecommendScope()
	{
		//TODO: read options from db
		return new RecommendScope();
	}
}
