package tfip.nus.iss.day29mock;

import java.io.StringReader;
import java.util.Date;
import java.util.List;

import org.bson.Document;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import tfip.nus.iss.day29mock.model.LineItem;
import tfip.nus.iss.day29mock.model.Order;

public class Utils {

    public static JsonObject toJson(String str) {
        StringReader s = new StringReader(str);
        JsonReader j = Json.createReader(s);
        return j.readObject();
    }

    public static Document toDocument(LineItem lineItem) {
        Document doc = new Document();
        doc.put("item", lineItem.getItem());
        doc.put("quantity", lineItem.getQuantity());
        return doc;
    }

    public static Document toBson(Order o) {
        Document doc = new Document();
        doc.put("name", o.getName());
        doc.put("email", o.getEmail());
        doc.put("deliveryDate", o.getDeliveryDate());
        doc.put("orderId", o.getOrderId());
        List<Document> docs = o.getLineItems()
                .stream()
                .map(v -> toDocument(v))
                .toList();
        doc.put("lineItems", docs);
        return doc;
    }

    public static JsonObject toJson(Order o) {
        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        o.getLineItems().stream()
                .map(v -> toJson(v))
                .forEach(v -> {
                    arrBuilder.add(v);
                });
        JsonObject json = Json.createObjectBuilder()
                .add("orderId", o.getOrderId())
                .add("name", o.getName())
                .add("email", o.getEmail())
                .add("deliveryDate", o.getDeliveryDate().toString())
                .add("lineItems", arrBuilder.build())
                .build();
        return json;

    }

    public static JsonObject toJson(LineItem item) {
        return Json.createObjectBuilder()
                .add("item", item.getItem())
                .add("quantity", item.getQuantity())
                .build();
    }

    public static Order toOrder(JsonObject j) {
        Order o = new Order();
        o.setName(j.getString("name"));
        o.setEmail(j.getString("email"));
        o.setDeliveryDate(new Date());
        List<LineItem> list = j.getJsonArray("lineItems").stream()
                .map(v -> (JsonObject) v)
                .map(v -> toLineItem(v))
                .toList();
        o.setLineItems(list);
        return o;
    }

    public static LineItem toLineItem(JsonObject j) {
        LineItem item = new LineItem();
        item.setItem(j.getString("item"));
        item.setQuantity(j.getInt("quantity"));
        return item;
    }

}
