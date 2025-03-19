package org.ademun.timetableapi.controller;

import org.ademun.timetableapi.mapper.OrderMapper;
import org.ademun.timetableapi.model.Order;
import org.ademun.timetableapi.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/timetable/orders")
public class OrderController {
    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @Autowired
    public OrderController(OrderService orderService, OrderMapper orderMapper) {
        this.orderService = orderService;
        this.orderMapper = orderMapper;
    }

    @GetMapping(value = "/")
    public CollectionModel<EntityModel<Order>> all() {
        List<EntityModel<Order>> orderModelList = orderService.all().stream().map(orderMapper::toModel).toList();
        return CollectionModel.of(orderModelList, linkTo(methodOn(OrderController.class).all()).withSelfRel());
    }

    @GetMapping(value = "/{id}")
    public EntityModel<Order> one(@PathVariable Long id) {
        return orderMapper.toModel(orderService.one(id));
    }

    @PostMapping(value = "/")
    public EntityModel<Order> create(@RequestBody Order order) {
        return orderMapper.toModel(orderService.create(order));
    }

    @PutMapping(value = "/{id}")
    public EntityModel<Order> update(@PathVariable Long id, @RequestBody Order order) {
        return orderMapper.toModel(orderService.update(id, order));
    }

    @DeleteMapping(value = "/{id}")
    public EntityModel<Order> delete(@PathVariable("id") Long id) {
        return orderMapper.toModel(orderService.delete(id));
    }
}
