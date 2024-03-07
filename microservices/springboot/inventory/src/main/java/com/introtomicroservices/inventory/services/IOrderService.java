package com.introtomicroservices.inventory.services;

import java.util.List;

import com.introtomicroservices.inventory.exceptions.OrderDataNotProvided;
import com.introtomicroservices.inventory.exceptions.OrderNotFound;
import com.introtomicroservices.inventory.forms.OrderForm;
import com.introtomicroservices.shared.models.postgres.Order;

/**
 * @author jjones
 */
public interface IOrderService {
    public List<Order> listOrders(int offset, int limit);
    public Order getById(long id) throws OrderNotFound;
    public Order create(OrderForm form) throws OrderDataNotProvided;
    public void remove(long id) throws OrderNotFound;
}
