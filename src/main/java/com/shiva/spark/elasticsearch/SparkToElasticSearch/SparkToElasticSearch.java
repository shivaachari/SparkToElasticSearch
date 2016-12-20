package com.shiva.spark.elasticsearch.SparkToElasticSearch;

import java.util.HashMap;
import java.util.Map;

import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.VoidFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.elasticsearch.spark.rdd.api.java.JavaEsSpark;
import org.spark_project.guava.collect.ImmutableList;
import org.spark_project.guava.collect.ImmutableMap;

public class SparkToElasticSearch {
	

	public static void main(String[] args) {
		//String mode = args[0];
		//final String collection = args[1];
		SparkConf sparkConf = new SparkConf();
		sparkConf.set("es.nodes", "localhost");
		sparkConf.setAppName("Spark to ElasticSearch");
		sparkConf.setMaster("local[*]");
		
		SQLContext sqlContext;
		
		HashMap<String, String> options = new HashMap<String, String>();
	    options.put("header", "true");
	    options.put("path", "/Users/shivaa/git/SparkProcessorKinesis_git/src/main/resources/datamodel/streaming/inputs/randomData.csv");
		
		 //sqlContext = new SQLContext(new SparkContext("local[2]", "O"));
		 //Dataset<Row> df = sqlContext.load("com.databricks.spark.csv", options);
		 
		 /*
		 JavaRDD<Row> rdd = df.toJavaRDD();
		
		 Map<String,String> conf = new HashMap<String,String>();
			conf.put("mapred.output.format.class", "org.elasticsearch.hadoop.mr.EsOutputFormat");
			conf.put(ConfigurationOptions.ES_RESOURCE_WRITE, "test/hadoopDatasetSaveToEs");
			conf.put("es.nodes", "localhost");
			JavaEsSpark.saveToEs(rdd, "test/" + "sparkes");
		 */
			
			//SparkConf conf = ...
					JavaSparkContext jsc = new JavaSparkContext(sparkConf);                              

					Map<String, ?> numbers = ImmutableMap.of("one", 1, "two", 2);                   
					Map<String, ?> airports = ImmutableMap.of("OTP", "Otopeni", "SFO", "San Fran");

					JavaRDD<Map<String, ?>> javaRDD = jsc.parallelize(ImmutableList.of(numbers, airports));
					
					javaRDD.foreach(new VoidFunction<Map<String,?>>() {
						
						public void call(Map<String, ?> arg0) throws Exception {
							Indexer.indexSomeStuff();
						}
					});
				//	JavaEsSpark.saveToEs(javaRDD, "spark/docs");  
		 
	}

}
