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
import com.introtomicroservices.inventory.services.IOrderService;
import com.introtomicroservices.shared.models.ApiMessage;
import com.introtomicroservices.shared.models.postgres.InventoryItem;
import com.introtomicroservices.shared.models.postgres.Order;
import com.introtomicroservices.shared.models.postgres.OrderInventoryItem;

/**
 * @author jjones
 */
@RestController
@RequestMapping("/inventory/order")
public class InventoryController {
    @Autowired
    IOrderService orderService;

    /**
     * Get a list of orders
     * @param limit the maximum number of items returned
     * @param offset the list offset
     * @return a list of orders
     */
    @GetMapping("/list")
    public List<Order> getOrders(
        @RequestParam(defaultValue = "10") int limit,
        @RequestParam(defaultValue = "0") int offset) {
        // Return a list of orders
        return orderService.listOrders(offset, limit);
    }

    /**
     * Get the order data, by ID
     * @param orderId the order data
     * @return the order data
     * @throws OrderNotFound
     */
    @GetMapping("/{orderId:[0-9]+}")
    public Order getOrderById(
        @PathVariable long orderId) throws OrderNotFound {
        // Get the order by ID, if it cannot be found then throw an exception.
        return orderService.getById(orderId);
    }

    /**
     * Get the order items in a list, by order ID
     * @param orderId the order ID
     * @return the list of order items
     * @throws OrderNotFound
     */
    @GetMapping("/{orderId:[0-9]+}/items")
    public List<InventoryItem> getOrderItemsById(
        @PathVariable long orderId) throws OrderNotFound {
        // Get the order by ID, if it cannot be found the return an exception
        Order order = orderService.getById(orderId);

        // Create the results list
        List<InventoryItem> results = new ArrayList<>();

        // For each order inventory item, add the item to the results and return
        // them.
        for(OrderInventoryItem items: order.getInventoryItems()) {
            results.add(items.getKey().getInventoryItem());
        }

        return results;
    }

    /**
     * Create a new order from order form data
     * @param form the order form data
     * @return the new order
     * @throws OrderDataNotProvided
     */
    @PostMapping
    public Order createOrder(
        @RequestBody OrderForm form) throws OrderDataNotProvided {
        return orderService.create(form);
    }

    /**
     * Delete the order information, by ID
     * @param orderId the order ID
     * @return an API message confirmation that the order has been
     *         deleted
     * @throws OrderNotFound
     */
    @DeleteMapping("/{orderId:[0-9]+}")
    public ApiMessage deleteOrderById(
        @PathVariable long orderId) throws OrderNotFound {
        orderService.remove(orderId);
        return new ApiMessage("This order has been deleted");
    }
}
