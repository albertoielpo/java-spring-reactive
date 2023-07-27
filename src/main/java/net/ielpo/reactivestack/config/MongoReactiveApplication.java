package net.ielpo.reactivestack.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.util.Assert;

import net.ielpo.reactivestack.util.PortChecker;

/**
 * @author Alberto Ielpo
 */
@EnableReactiveMongoRepositories(basePackages = "net.ielpo.reactivestack.repository")
@Configuration
public class MongoReactiveApplication {

    @Value("${mongodb.connection.required}")
    private boolean mongodbConnectionRequired;

    @Value("${mongodb.connection.host}")
    private String mongodbConnectionHost;

    @Value("${mongodb.connection.port}")
    private Integer mongodbConnectionPort;

    @Bean
    public void mongoDBChecker() {
        if (!mongodbConnectionRequired) {
            /** mongodb connection is not strictly required for the startup */
            return;
        }
        /* a mongodb connection is requried.. check if the DB is up and running */
        Assert.isTrue(PortChecker.isConnectionPossible(mongodbConnectionHost, mongodbConnectionPort),
                "Mongo DB must be up and running");
    }

}
