package com.shiva.spark.elasticsearch.SparkToElasticSearch;

import org.joda.time.DateTime;

public class Tweet {

	private Integer id;
	private String user;
	private String message;
	private DateTime timestamp;
	
	public Tweet(Integer id, String user, String message) {
		this.id = id;
		this.user = user;
		this.message = message;
		timestamp = new DateTime(System.currentTimeMillis());
	}
	
	public DateTime getTimestamp() {
		return timestamp;
	}

	public Integer getId() {
		return id;
	}
	public String getUser() {
		return user;
	}
	public String getMessage() {
		return message;
	}

}
