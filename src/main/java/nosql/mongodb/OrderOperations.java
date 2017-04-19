package nosql.mongodb;

import com.mongodb.Mongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Admin on 2016/9/8.
 * 中间接口，给标准接口做扩展
 */
@Repository
public interface OrderOperations {

    public List<Order> findOrdersByType(String t);

}
