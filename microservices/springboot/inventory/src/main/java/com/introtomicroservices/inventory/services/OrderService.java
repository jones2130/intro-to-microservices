package com.introtomicroservices.inventory.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.introtomicroservices.inventory.exceptions.OrderDataNotProvided;
import com.introtomicroservices.inventory.exceptions.OrderNotFound;
import com.introtomicroservices.inventory.forms.OrderForm;
import com.introtomicroservices.inventory.models.InventoryItem;
import com.introtomicroservices.inventory.models.OffsetBasedPageRequest;
import com.introtomicroservices.inventory.models.Order;
import com.introtomicroservices.inventory.models.OrderInventoryItem;
import com.introtomicroservices.inventory.models.keys.OrderInventoryItemKey;
import com.introtomicroservices.inventory.repositories.InventoryItemRepository;
import com.introtomicroservices.inventory.repositories.OrderInventoryItemRepository;
import com.introtomicroservices.inventory.repositories.OrderRepository;

@Service
public class OrderService implements IOrderService {
    Logger logger = LoggerFactory.getLogger(OrderService.class);

    private static final Sort ORDER_SORT = Sort
        .by(Sort.Direction.DESC, "timestamp");

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    InventoryItemRepository itemRepository;

    @Autowired
    OrderInventoryItemRepository orderItemRepository;

    @Override
    public List<Order> listOrders(int offset, int limit) {
        if(limit == 0) {
            return orderRepository.findAll();
        }

        Page<Order> orders = orderRepository
            .findAll(new OffsetBasedPageRequest(
                offset, limit, ORDER_SORT));

        return orders.toList();
    }

    @Override
    public Order getById(long id) throws OrderNotFound {
        return orderRepository.findById(id)
            .orElseThrow(() -> new OrderNotFound());
    }

    @Override
    public Order create(OrderForm form) throws OrderDataNotProvided {
        if(form == null) {
            throw new OrderDataNotProvided();
        }

        Order order = new Order();
        order.setNotes(form.getNotes());
        order.setTimestamp(new Date());

        List<InventoryItem> items = createInventoryItems(form.getItems());

        order = orderRepository.save(order);

        order.setInventoryItems(
            createOrderInventoryItems(items, order));

        orderItemRepository.saveAll(order.getInventoryItems());

        return order;
    }

    @Override
    public void remove(long id) throws OrderNotFound {
        Order order = orderRepository.findById(id).orElseThrow(() -> new OrderNotFound());
        cleanupOrderInventoryItems(id);
        orderRepository.delete(order);
    };

    private List<InventoryItem> createInventoryItems(
        List<OrderForm.OrderItem> formItems) {
        List<InventoryItem> items = new ArrayList<>();

        for(OrderForm.OrderItem orderItem: formItems) {
            InventoryItem item = new InventoryItem();
            item.setBookId(orderItem.getBookId());
            item.setQuantity(orderItem.getQuantity());
            items.add(item);
        }

        return itemRepository.saveAllAndFlush(items);
    }

    private List<OrderInventoryItem> createOrderInventoryItems(
        List<InventoryItem> items, Order order) {
        List<OrderInventoryItem> orderItems = new ArrayList<>();

        for(InventoryItem item: items) {
            OrderInventoryItemKey key = new OrderInventoryItemKey();
            key.setInventoryItem(item);
            key.setOrder(order);
            OrderInventoryItem newItem = new OrderInventoryItem();
            newItem.setKey(key);
            orderItems.add(newItem);
        }

        return orderItems;
    }

    private void cleanupOrderInventoryItems(long orderId) {
        List<OrderInventoryItem> orderItems = orderItemRepository.findByOrderId(orderId);
        orderItemRepository.deleteAll(orderItems);
        cleanupInventoryItems(orderItems);
    }

    private void cleanupInventoryItems(List<OrderInventoryItem> orderItems) {
        List<InventoryItem> items = new ArrayList<>();
        for(OrderInventoryItem orderItem: orderItems) {
            items.add(orderItem.getKey().getInventoryItem());
        }

        itemRepository.deleteAll(items);
    }
}
