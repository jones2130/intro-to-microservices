package com.introtomicroservices.inventory.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.introtomicroservices.inventory.exceptions.OrderDataNotProvided;
import com.introtomicroservices.inventory.exceptions.OrderNotFound;
import com.introtomicroservices.inventory.forms.OrderForm;
import com.introtomicroservices.inventory.repositories.InventoryItemRepository;
import com.introtomicroservices.inventory.repositories.OrderInventoryItemRepository;
import com.introtomicroservices.inventory.repositories.OrderRepository;
import com.introtomicroservices.shared.models.OffsetBasedPageRequest;
import com.introtomicroservices.shared.models.postgres.InventoryItem;
import com.introtomicroservices.shared.models.postgres.Order;
import com.introtomicroservices.shared.models.postgres.OrderInventoryItem;
import com.introtomicroservices.shared.models.postgres.keys.OrderInventoryItemKey;

/**
 * @author jjones
 */
@Service
public class OrderService implements IOrderService {
    private static final Sort ORDER_SORT = Sort
        .by(Sort.Direction.DESC, "timestamp");

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    InventoryItemRepository itemRepository;

    @Autowired
    OrderInventoryItemRepository orderItemRepository;

    /**
     * Return a list of orders
     * @param offset the offset for the results list
     * @param limit the maximum size for the results list
     * @return the order list
     */
    @SuppressWarnings("null")
    @Override
    public List<Order> listOrders(int offset, int limit) {
        // If the limit value is 0 then return all results
        if(limit == 0) {
            return orderRepository.findAll(ORDER_SORT);
        }

        // Create an order data page based on the limit and offset
        Page<Order> orders = orderRepository
            .findAll(new OffsetBasedPageRequest(
                offset, limit, ORDER_SORT));

        // Return the order page data as a list
        return orders.toList();
    }

    /**
     * Get the order information from the database
     * @param id the order ID
     * @return the order data
     * @throws OrderNotFound
     */
    @Override
    public Order getById(long id) throws OrderNotFound {
        return orderRepository.findById(id)
            .orElseThrow(() -> new OrderNotFound());
    }

    /**
     * Create a new order from form data
     * @param form the order form data
     * @return the new order
     * @throws OrderDataNotProvided
     */
    @SuppressWarnings("null")
    @Override
    public Order create(OrderForm form) throws OrderDataNotProvided {
        // If form data is not provided then throw an exception
        if(form == null) {
            throw new OrderDataNotProvided();
        }

        // Create the new order, set the notes from the form and 
        // the current date as the timestamp
        Order order = new Order();
        order.setNotes(form.getNotes());
        order.setTimestamp(new Date());

        // Create the inventory items from the form data
        List<InventoryItem> items = createInventoryItems(form.getItems());

        // Save and get the new order
        order = orderRepository.save(order);

        // Set the inventory item information based on the order inventory
        // data.
        order.setInventoryItems(
            createOrderInventoryItems(items, order));

        // Save all of the order / inventory items
        orderItemRepository.saveAll(order.getInventoryItems());

        // Return the new order
        return order;
    }

    /**
     * Delete an order, by ID
     * @param id the order ID
     * @throws OrderNotFound
     */
    @SuppressWarnings("null")
    @Override
    public void remove(long id) throws OrderNotFound {
        // Get the order, if it is not found then throw an exception
        Order order = orderRepository.findById(id)
            .orElseThrow(() -> new OrderNotFound());

        // Remove inventory item information associated with this order
        cleanupOrderInventoryItems(id);

        // Delete the order
        orderRepository.delete(order);
    };

    /**
     * Create inventory item records based on the information from the
     * form
     * @param formItems the order form information for inventory
     * @return the new inventory items
     */
    private List<InventoryItem> createInventoryItems(
        List<OrderForm.OrderItem> formItems) {
        // Create a new array for the results
        List<InventoryItem> items = new ArrayList<>();

        // For each order item in the form, create an ew item and insert
        // the form data
        for(OrderForm.OrderItem orderItem: formItems) {
            InventoryItem item = new InventoryItem();
            item.setBookId(orderItem.getBookId());
            item.setQuantity(orderItem.getQuantity());

            // Add the new inventory item to the results array
            items.add(item);
        }

        // insert all items in the repository, and return the array
        // of inventory items
        return itemRepository.saveAllAndFlush(items);
    }

    /**
     * Create associations betweent the order and inventory items
     * @param items the inventory items associated with the order
     * @param order the order data
     * @return a list of order / inventory item links
     */
    private List<OrderInventoryItem> createOrderInventoryItems(
        List<InventoryItem> items, Order order) {
        // Create a new array to hold the order / inventory item
        // association records
        List<OrderInventoryItem> orderItems = new ArrayList<>();

        // For each inventory item, create a new order / inventory item
        // assocation, set the item and inventory item as part of the 
        // primary key, and add the association to the results list
        for(InventoryItem item: items) {
            OrderInventoryItemKey key = new OrderInventoryItemKey();
            key.setInventoryItem(item);
            key.setOrder(order);
            OrderInventoryItem newItem = new OrderInventoryItem();
            newItem.setKey(key);
            orderItems.add(newItem);
        }

        // Return the order item information
        return orderItems;
    }

    /**
     * Remove all order / inventory item links for deleted orders
     * @param orderId the order ID
     */
    @SuppressWarnings("null")
    private void cleanupOrderInventoryItems(long orderId) {
        // Get all order / inventory item links by order ID
        List<OrderInventoryItem> orderItems = orderItemRepository.findByOrderId(orderId);

        // Delete all order items in the list
        orderItemRepository.deleteAll(orderItems);

        // Cleanup any left over inventory items
        cleanupInventoryItems(orderItems);
    }

    /**
     * Remove all inventory items associated with deleted orders
     * @param orderItems a list of inventory items to be deleted
     */
    private void cleanupInventoryItems(List<OrderInventoryItem> orderItems) {
        // Create a list of inventory items to be removed
        List<InventoryItem> items = new ArrayList<>();
        for(OrderInventoryItem orderItem: orderItems) {
            items.add(orderItem.getKey().getInventoryItem());
        }

        // Remove all list of items
        itemRepository.deleteAll(items);
    }
}
