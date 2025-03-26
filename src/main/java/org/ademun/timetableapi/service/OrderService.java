package org.ademun.timetableapi.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.ademun.timetableapi.model.Order;
import org.ademun.timetableapi.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
public class OrderService {
  private final OrderRepository orderRepository;

  @Autowired
  public OrderService(OrderRepository orderRepository) {
    this.orderRepository = orderRepository;
  }

  @Transactional
  public List<Order> all() {
    log.info("Retrieving all Orders");
    return orderRepository.findAll().stream().sorted(Comparator.comparing(Order::getId)).toList();
  }

  @Transactional
  public Order one(Integer id) {
    log.info("Retrieving Order with id {}", id);
    return orderRepository.findById(id).orElseThrow();
  }

  @Transactional
  public Order create(Order order) {
    log.info("Creating Order: {}", order);
    return orderRepository.save(order);
  }

  @Transactional
  public Order update(Integer id, Order order) {
    log.info("Updating Order with id: {} with Order: {}", id, order);
    Order old = orderRepository.findById(id).orElseThrow();
    old.setDayOrder(order.getDayOrder());
    old.setTimeStart(order.getTimeStart());
    old.setTimeEnd(order.getTimeEnd());
    return orderRepository.save(old);
  }

  @Transactional
  public Order delete(Integer id) {
    log.info("Deleting Order: {}", id);
    Order order = orderRepository.findById(id).orElseThrow();
    orderRepository.deleteById(id);
    return order;
  }
}
