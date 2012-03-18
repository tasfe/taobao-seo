package com.taobaoseo.domain.recommendation;

import java.io.Serializable;

public class RecommendScope implements Serializable{

	public static final short TYPE_ALL = 0;
	public static final short TYPE_KEYWORD = 1;
	public static final short TYPE_CATEGORIES = 2;
	public static final short TYPE_SPECIFIED = 3;
	
	private int type = TYPE_ALL;
	
	private String keyword;
	private String sellerCids;
	private String numIids;

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getKeyword() {
		return keyword;
	}
	
	public void setItems(String numIids) {
		this.numIids = numIids;
	}

	public String getItems() {
		return this.numIids;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}

	public void setSellerCids(String sellerCids) {
		this.sellerCids = sellerCids;
	}

	public String getSellerCids() {
		return sellerCids;
	}

}
