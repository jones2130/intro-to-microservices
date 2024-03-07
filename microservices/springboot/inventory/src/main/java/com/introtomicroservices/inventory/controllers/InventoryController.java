package com.introtomicroservices.inventory.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.introtomicroservices.inventory.exceptions.OrderDataNotProvided;
import com.introtomicroservices.inventory.exceptions.OrderNotFound;
import com.introtomicroservices.inventory.forms.OrderForm;
import com.introtomicroservices.inventory.models.ApiMessage;
import com.introtomicroservices.inventory.models.InventoryItem;
import com.introtomicroservices.inventory.models.Order;
import com.introtomicroservices.inventory.models.OrderInventoryItem;
import com.introtomicroservices.inventory.services.IOrderService;

@RestController
@RequestMapping("/inventory/order")
public class InventoryController {
    @Autowired
    IOrderService orderService;

    @GetMapping("/list")
    public List<Order> getOrders(
        @RequestParam(defaultValue = "10") int limit,
        @RequestParam(defaultValue = "0") int offset) {
        return orderService.listOrders(offset, limit);
    }

    @GetMapping("/{orderId:[0-9]+}")
    public Order getOrderById(
        @PathVariable long orderId) throws OrderNotFound {
        return orderService.getById(orderId);
    }

    @GetMapping("/{orderId:[0-9]+}/items")
    public List<InventoryItem> getOrderItemsById(
        @PathVariable long orderId) throws OrderNotFound {
        List<InventoryItem> results = new ArrayList<>();
        Order order = orderService.getById(orderId);

        for(OrderInventoryItem items: order.getInventoryItems()) {
            results.add(items.getKey().getInventoryItem());
        }

        return results;
    }

    @PostMapping
    public Order createOrder(
        @RequestBody OrderForm form) throws OrderDataNotProvided {
        return orderService.create(form);
    }

    @DeleteMapping("/{orderId:[0-9]+}")
    public ApiMessage deleteOrderById(
        @PathVariable long orderId) throws OrderNotFound {
        orderService.remove(orderId);
        return new ApiMessage("This order has been deleted");
    }
}
