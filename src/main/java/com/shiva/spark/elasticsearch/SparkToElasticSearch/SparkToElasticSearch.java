package com.shiva.spark.elasticsearch.SparkToElasticSearch;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RuntimeConfig;
import org.apache.spark.sql.SparkSession;
import org.elasticsearch.spark.rdd.api.java.JavaEsSpark;
import org.springframework.stereotype.Service;

@Service
public class SparkToElasticSearch {
	
	final static String HOST = "localhost";
    final static String PORT = "9200";
    final static String INDEX = "spark_platform";
    final static String INDEX_TYPE = INDEX + "/doc";
	

	public static void main(String[] args) {
		try {
			
			String file = "/Users/shivaa/git/SparkProcessorKinesis_git/src/main/resources/datamodel/streaming/inputs/randomData.csv";
			
			
			
			  SparkSession spark = SparkSession.builder()
					  .master("local[*]")
					  .appName("Spark to ElasticSearch")
					  
					  .getOrCreate();
			  
			   RuntimeConfig sparkConf = spark.conf();
				sparkConf.set("es.resource", INDEX_TYPE);
		        sparkConf.set("es.query", "{\"query\":{\"match_all\":{}}}");
		        sparkConf.set("es.nodes", HOST);
		        sparkConf.set("es.port", PORT);
			  
			  Dataset<RowFieldsVO> ds = spark.read()
					  .option("header", "false")
					  .csv(file)
					  .map(new MapFunction<Row, RowFieldsVO>() {
				            private static final long serialVersionUID = 6349961442643649083L;

							@Override
				            public RowFieldsVO call(Row value) throws Exception {
								//System.out.println("value:"+ value.mkString(","));
				                return new RowFieldsVO(Long.parseLong(value.getString(0)) * 1000, 
				                		value.getString(1), 
				                		value.getString(2)
				                		, Integer.parseInt(value.getString(3)), 
				                		value.getString(4), 
				                		Integer.parseInt(value.getString(5))
				                		);
				            }
					  }, Encoders.bean(RowFieldsVO.class));
			   
				JavaRDD<RowFieldsVO> rdd = ds.toJavaRDD();
				
				JavaEsSpark.saveToEs(rdd, INDEX_TYPE);
				
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		 
	}

}
