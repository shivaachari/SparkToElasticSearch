package com.shiva.spark.elasticsearch.SparkToElasticSearch;

public class RowFieldsVO  {
	

	long timestamp;
	String platform;
	String userid;
	int type;
	String key;
	public RowFieldsVO(long timestamp, String platform, String userid, int type, String key, int value) {
		super();
		this.timestamp = timestamp;
		this.platform = platform;
		this.userid = userid;
		this.type = type;
		this.key = key;
		this.value = value;
	}
	int value;
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Long timestamp) {
		this.timestamp = Long.valueOf(timestamp);
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public int getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = Integer.valueOf(type);
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public int getValue() {
		return value;
	}
	public void setValue(Integer value) {
		this.value = Integer.valueOf(value);
	}
	
	
	
}
