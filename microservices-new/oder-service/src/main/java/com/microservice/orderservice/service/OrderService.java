package com.microservice.orderservice.service;

import com.microservice.orderservice.dto.OrderLineItemsDto;
import com.microservice.orderservice.dto.OrderRequest;
import com.microservice.orderservice.model.Order;
import com.microservice.orderservice.model.OrderLineItems;
import com.microservice.orderservice.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;
import java.util.List;
@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
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
        orderRepository.save(order);
    }


}
