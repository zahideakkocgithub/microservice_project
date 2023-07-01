package com.microservice.orderservice.service;

import com.microservice.orderservice.dto.OrderLineItemsDto;
import com.microservice.orderservice.dto.OrderRequest;
import com.microservice.orderservice.model.Order;
import com.microservice.orderservice.model.OrderLineItems;
import com.microservice.orderservice.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.UUID;
import java.util.List;
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient.Builder webClient;

    public OrderService(OrderRepository orderRepository, WebClient.Builder webClient) {
        this.orderRepository = orderRepository;
        this.webClient = webClient;
    }

    public void placeOrder(OrderRequest orderRequest)
    {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderLineItemsDto> itemsDto = orderRequest.getOrderLineItemsDto();

        List<OrderLineItems>  items = new ArrayList<>();
        for(int i=0;i<itemsDto.size();i++)
        {
            OrderLineItems orderLineItems = new OrderLineItems();
            orderLineItems.setQuantity(itemsDto.get(i).getQuantity());
            orderLineItems.setPrice(itemsDto.get(i).getPrice());
            orderLineItems.setSkuCode(itemsDto.get(i).getSkuCode());
            items.add(orderLineItems);
        }

        order.setOrderLineItemsList(items);

        List<String> skuCodes= items.stream().map(OrderLineItems::getSkuCode).toList();

       boolean result=  webClient.build().get().uri("http://inventory-service/api/inventory"
                       ,uriBuilder -> uriBuilder.queryParam("skuCode",skuCodes).build()).retrieve().
                bodyToMono(Boolean.class).block();

       if(result==true) {
           orderRepository.save(order);
       }else{
           throw  new IllegalArgumentException("Product is not in stock");
       }
    }


}
