package org.rapidpm.demo.jaxenter.blog0012.teil8.arangodb;

import org.arangodb.objectmapper.ArangoDb4JException;
import org.arangodb.objectmapper.Database;
import org.arangodb.objectmapper.http.ArangoDbHttpClient;

/**
 * Created by Sven Ruppert on 16.02.14.
 */
public class ArangoDBLocalhost {

    public static ArangoDbHttpClient client;
    public static Database database;

    static {
        try {
            client = new ArangoDbHttpClient.Builder().host("localhost")
                    .port(8529)
                    .username("root")
                    .password("")
                    .build();

            database = new Database(client, "_system");
        } catch (ArangoDb4JException e) {
            e.printStackTrace();
        }

    }

}
