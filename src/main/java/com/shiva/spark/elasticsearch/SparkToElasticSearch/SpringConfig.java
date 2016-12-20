package com.shiva.spark.elasticsearch.SparkToElasticSearch;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;

@Configuration
@ComponentScan("com.shiva.spark.elasticsearch.SparkToElasticSearch")
public class SpringConfig {

	@Bean
	public JestClient jestClient() {
		 HttpClientConfig clientConfig = new HttpClientConfig.Builder("http://localhost:9200").multiThreaded(true).build();
		 JestClientFactory factory = new JestClientFactory();
		 factory.setHttpClientConfig(clientConfig);
		 
		 return factory.getObject();
	}

}
