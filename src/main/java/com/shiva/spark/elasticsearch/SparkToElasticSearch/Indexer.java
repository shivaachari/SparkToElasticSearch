package com.shiva.spark.elasticsearch.SparkToElasticSearch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

import io.searchbox.client.JestClient;
import io.searchbox.core.Bulk;
import io.searchbox.core.Bulk.Builder;
import io.searchbox.core.Index;
import scala.Serializable;


@Service
public class Indexer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6920827364076475657L;
	private Indexer indexer;
	transient private JestClient client;
	transient private  TweetService tweetService;
	private static boolean isIntitialized = false;
	transient 	Builder bulkBuilder;
	
	@Autowired 
	public Indexer(JestClient client, TweetService tweetService) {
		this.client = client;
		this.tweetService = tweetService;
		bulkBuilder = new Bulk.Builder();
		
		//initialize();
	}
	
	public void addToBulk(RowFieldsVO row) throws Exception {
		/*if (!isIntitialized) {
			initialize();
		}*/
			Index index = new Index.Builder(row).index("platformMetric").type("events").build();
			bulkBuilder.addAction(index);
	
	}
	
	public void indexRows() throws Exception {
		client.execute(bulkBuilder.build());
	}
	
	
	public  void indexSomeStuff() throws Exception {
		//initialize();
		
		Builder bulkBuilder = new Bulk.Builder();
		
		for (Tweet tweet : tweetService.getTweets()) {
			System.out.println("Indexing tweet: " + tweet.getMessage());
			Index index = new Index.Builder(tweet).index("twitter").type("tweet").build();
			bulkBuilder.addAction(index);
		}
		
		client.execute(bulkBuilder.build());
	}
	
	
	
	public  void initialize() {
		if (!isIntitialized) {
		
		 @SuppressWarnings("resource")
		ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
		  indexer = context.getBean(Indexer.class);
		  isIntitialized = true;
		}
	}
	
	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		 ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
		 Indexer indexer = context.getBean(Indexer.class);
		 indexer.indexSomeStuff();
	}
	
	
}
