package nosql.mongodb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Admin on 2016/9/8.
 */
@Repository
public class OrderOperationsImpl implements OrderOperations {

    @Autowired
    MongoOperations mongo;

    @Override
    public List<Order> findOrdersByType(String t) {
        String type = t.equals("NET") ? "WEB" : t;
        Criteria where = Criteria.where("type").is(t);
        Query query = Query.query(where);
        return mongo.find(query, Order.class);
    }
}
