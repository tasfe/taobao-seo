package com.taobaoseo.domain;

public class ItemsFilter {

	public static final short STATUS_ONSALE = 0;
	public static final short STATUS_INVENTORY = 1;
	
	private short saleStatus = STATUS_ONSALE;
	private String keyWord;
	private String sellerCids;
	private String banner;

	public void setSaleStatus(short status) {
		this.saleStatus = status;
	}

	public short getSaleStatus() {
		return saleStatus;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public String getKeyWord() {
		return keyWord;
	}

	public void setSellerCids(String cids) {
		this.sellerCids = cids;
	}

	public String getSellerCids() {
		return sellerCids;
	}

	public String toString()
	{
		return saleStatus + " : " + sellerCids + " : " + keyWord;
	}

	public void setBanner(String banner) {
		this.banner = banner;
	}

	public String getBanner() {
		return banner;
	}
}
