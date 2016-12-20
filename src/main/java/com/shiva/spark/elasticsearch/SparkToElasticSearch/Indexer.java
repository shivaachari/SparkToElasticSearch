package com.shiva.spark.elasticsearch.SparkToElasticSearch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

import io.searchbox.client.JestClient;
import io.searchbox.core.Bulk;
import io.searchbox.core.Bulk.Builder;
import io.searchbox.core.Index;


@Service
public class Indexer {

	private static Indexer indexer;
	private static JestClient client;
	private static TweetService tweetService;
	private static boolean isIntitialized = false;
	
	@Autowired 
	public Indexer(JestClient client, TweetService tweetService) {
		this.client = client;
		this.tweetService = tweetService;
	}
	
	public static void indexSomeStuff() throws Exception {
		initialize();
		
		Builder bulkBuilder = new Bulk.Builder();
		
		for (Tweet tweet : tweetService.getTweets()) {
			System.out.println("Indexing tweet: " + tweet.getMessage());
			Index index = new Index.Builder(tweet).index("twitter").type("tweet").build();
			bulkBuilder.addAction(index);
		}
		
		client.execute(bulkBuilder.build());
		

	}
	
	public static void initialize() {
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
