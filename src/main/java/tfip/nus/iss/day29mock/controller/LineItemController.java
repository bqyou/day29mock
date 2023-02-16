package tfip.nus.iss.day29mock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import tfip.nus.iss.day29mock.Utils;
import tfip.nus.iss.day29mock.model.Order;
import tfip.nus.iss.day29mock.service.OrderService;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class LineItemController {

    @Autowired
    private OrderService orderSvc;

    @PostMapping(path = "/api/order", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> submitOrder(@RequestBody String s, Model model) {
        Order o = Utils.toOrder(Utils.toJson(s));
        orderSvc.insertOrder(o);
        JsonObject resp = Json.createObjectBuilder()
                .add("orderId", o.getOrderId())
                .add("message", "Your order with the order id %s will be delivered on %s.".formatted(o.getOrderId(),
                        o.getDeliveryDate()))
                .build();
        return ResponseEntity.status(HttpStatus.OK)
                .body(resp.toString());
    }

}
