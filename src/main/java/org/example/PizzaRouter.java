package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class PizzaRouter extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("file:{{file.input.directory}}?noop=true")
                .routeId("pizzaOrderFileRoute")
                .log("Reading file: ${header.CamelFileName}")
                .log("Reading body of file: ${body}")
                .split(body().tokenize("\n")).streaming()
                .log("Processing order: ${body}")
                .to("kafka:{{pizza.order.kafka.topic}}?brokers=localhost:9092")
                .process(exchange -> {
                    // Get the JSON body as a String
                    String body = exchange.getIn().getBody(String.class);

                    // Parse JSON into a Map
                    ObjectMapper objectMapper = new ObjectMapper();
                    Map<String, String> order = objectMapper.readValue(body, new TypeReference<Map<String, String>>() {});

                    // Prepare the SQL INSERT statement
                    String sql = String.format(
                            "INSERT INTO orders (customer_name, pizza_type, pizza_size) VALUES ('%s', '%s', '%s')",
                            order.get("customerName"),
                            order.get("pizzaType"),
                            order.get("size")
                    );

                    // Set the SQL query as the message body
                    exchange.getIn().setBody(sql);
                })
                .to("jdbc:myDataSource") // Persist to MySQL
                .end()
                .log("Finished processing file: ${header.CamelFileName}");
    }
}
