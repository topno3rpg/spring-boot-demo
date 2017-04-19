package nosql.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

/**
 * Created by Admin on 2016/9/8.
 */
public interface OrderRepository extends MongoRepository<Order, String>, OrderOperations {

    @Query("{'customer':'chuck wagon','type':?0}")
    List<Order> findChucksOrders(String t);

}
