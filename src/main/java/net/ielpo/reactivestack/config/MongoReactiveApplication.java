package net.ielpo.reactivestack.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.util.Assert;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;

import net.ielpo.reactivestack.util.PortChecker;

/**
 * @author Alberto Ielpo
 */
@EnableReactiveMongoRepositories(basePackages = "net.ielpo.reactivestack.repository")
@Configuration
public class MongoReactiveApplication extends AbstractReactiveMongoConfiguration {

    @Value("${mongodb.host}")
    private String mongodbHost;

    @Value("${mongodb.port}")
    private Integer mongodbPort;

    @Value("${mongodb.name}")
    private String mongodbName;

    @Bean
    public MongoClient mongoClient() {
        ConnectionString connectionString = new ConnectionString(
                String.format("mongodb://%s:%s/:%s", mongodbHost, mongodbPort, mongodbName));
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();

        return MongoClients.create(mongoClientSettings);
    }

    @Override
    protected String getDatabaseName() {
        return mongodbName;
    }

    @Bean
    public void mongoDBChecker() {
        Assert.isTrue(PortChecker.isConnectionPossible(mongodbPort), "Mongo DB must be up and running");
    }

}
