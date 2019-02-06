package com.example.demo.config;


import org.springframework.cloud.Cloud;
import org.springframework.cloud.CloudFactory;
import org.springframework.cloud.service.common.MongoServiceInfo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;


@Configuration
@Profile("cloud")
@EnableMongoRepositories(basePackages="com.example.demo.*")
public class MongoDataSourceConfiguration extends AbstractMongoConfiguration {

	private Cloud getCloud() {
	    CloudFactory cloudFactory = new CloudFactory();
	    return cloudFactory.getCloud();
	}
	@Override
	@Bean
	public MongoDbFactory mongoDbFactory() {
	    Cloud cloud = getCloud();
	    MongoServiceInfo serviceInfo = (MongoServiceInfo) cloud.getServiceInfo(cloud.getCloudProperties().getProperty("cloud.services.mongo.id"));
	    String serviceID = serviceInfo.getId();
	    return cloud.getServiceConnector(serviceID, MongoDbFactory.class, null);
	}

	@Override
	protected String getDatabaseName() {
	    Cloud cloud = getCloud();
	    return cloud.getCloudProperties().getProperty("cloud.services.mongo.id");
	}

	@Override
	public Mongo mongo() throws Exception {
	    Cloud cloud = getCloud();
	    return new MongoClient(cloud.getCloudProperties().getProperty("cloud.services.mongo.connection.host"));
	}
	@Override
	@Bean
	public MongoTemplate mongoTemplate() {
	    return new MongoTemplate(mongoDbFactory());
	}

	
}
