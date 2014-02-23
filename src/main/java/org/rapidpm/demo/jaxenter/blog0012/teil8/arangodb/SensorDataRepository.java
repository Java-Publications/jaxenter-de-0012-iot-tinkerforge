package org.rapidpm.demo.jaxenter.blog0012.teil8.arangodb;

import data.SensorDataElement;
import org.arangodb.objectmapper.ArangoDbRepository;
import org.arangodb.objectmapper.Database;

/**
 * Created by Sven Ruppert on 16.02.14.
 */
public class SensorDataRepository extends ArangoDbRepository<SensorDataElement> {
    /**
     * Constructor
     *
     * @param database  the ArangoDB database
     */
    public SensorDataRepository(Database database) {
        super(database, SensorDataElement.class);
    }
}
