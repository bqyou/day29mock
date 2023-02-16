package tfip.nus.iss.day29mock.repository;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import tfip.nus.iss.day29mock.Utils;
import tfip.nus.iss.day29mock.model.Order;

import static tfip.nus.iss.day29mock.Constants.*;

@Repository
public class OrderRepo {

    @Autowired
    private MongoTemplate template;

    public void insertOrder(Order order) {
        Document d = Utils.toBson(order);
        template.insert(d, COLLECTION_ORDERS);
    }

}
