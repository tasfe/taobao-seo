package com.taobaoseo.action.listing;

import java.sql.SQLException;
import java.util.List;

import javax.naming.NamingException;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.taobaoseo.action.ActionBase;
import com.taobaoseo.db.Dao;
import com.taobaoseo.domain.listing.PlannedItem;

@Results({
	  @Result(location="../listing-logs.jsp")
})
public class LogsAction extends ActionBase{

	private List<PlannedItem> items;
	
	public String execute()
	{
		long userId = getUserId();
		try {
			items = Dao.INSTANCE.getListingLog(userId);
			if (items.size() > 20)
			{
				Dao.INSTANCE.deleteOldestListingLog(userId);
			}
		} catch (NamingException e) {
			error(e);
		} catch (SQLException e) {
			error(e);
		}
		return SUCCESS;
	}

	public void setItems(List<PlannedItem> items) {
		this.items = items;
	}

	public List<PlannedItem> getItems() {
		return items;
	}
}
