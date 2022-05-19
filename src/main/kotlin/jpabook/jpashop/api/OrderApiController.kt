package jpabook.jpashop.api

import jpabook.jpashop.domain.Address
import jpabook.jpashop.domain.Order
import jpabook.jpashop.domain.OrderItem
import jpabook.jpashop.domain.OrderStatus
import jpabook.jpashop.repository.OrderRepository
import jpabook.jpashop.repository.OrderSearch
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
class OrderApiController(
  private val orderRepository: OrderRepository
) {
  @GetMapping("/api/v1/orders")
  fun ordersV1(): List<Order> {
    val all = orderRepository.findAllByString(OrderSearch())
    for (order in all) {
      // 강제 지연로딩 초기화
      order.member?.name
      order.delivery?.address
      val orderItems = order.getOrderItems()
      orderItems.forEach { orderItem -> orderItem.item?.name }
    }
    return all
  }

  @GetMapping("/api/v2/orders")
  fun ordersV2(): List<OrderDto> {
    val orders = orderRepository.findAllByString(OrderSearch())
    return orders
      .map {o -> OrderDto(o)}
      .toList()
  }

  @GetMapping("/api/v3/orders")
  fun ordersV3(): List<OrderDto> {
    val orders = orderRepository.findAllWithItem()
    return orders
      .map {o -> OrderDto(o)}
      .toList()
  }

  class OrderDto() {
    var orderId: Long? = null
    var name: String = ""
    var orderDate: LocalDateTime? = null
    var orderStatus: OrderStatus? = null
    var address: Address? = null
    var orderItems: List<OrderItemDto>? = null

    constructor(order: Order) : this() {
      orderId = order.id
      name = order.member?.name ?: ""
      orderDate = order.orderDate
      orderStatus = order.status
      address = order.delivery?.address
      orderItems = order.getOrderItems()
        .map { o -> OrderItemDto(o) }
        .toList()
    }
  }

  class OrderItemDto() {
    var itemName: String = ""
    var orderPrice = 0
    var count = 0

    constructor(orderItem: OrderItem) : this() {
      itemName = orderItem.item?.name ?: ""
      orderPrice = orderItem.orderPrice
      count = orderItem.count
    }
  }
}