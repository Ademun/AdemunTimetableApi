package org.ademun.timetableapi.mapper;

import org.ademun.timetableapi.controller.OrderController;
import org.ademun.timetableapi.model.Order;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OrderMapper implements RepresentationModelAssembler<Order, EntityModel<Order>> {
  @NonNull
  @Override
  public EntityModel<Order> toModel(@NonNull Order order) {
    return EntityModel.of(order,
        linkTo(methodOn(OrderController.class).one(order.getId())).withSelfRel(),
        linkTo(methodOn(OrderController.class).all()).withRel("orders"));
  }
}
