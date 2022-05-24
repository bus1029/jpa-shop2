package jpabook.jpashop.api

import jpabook.jpashop.domain.Address
import jpabook.jpashop.domain.Order
import jpabook.jpashop.domain.OrderItem
import jpabook.jpashop.domain.OrderStatus
import jpabook.jpashop.repository.OrderRepository
import jpabook.jpashop.repository.OrderSearch
import jpabook.jpashop.repository.order.query.OrderQueryDto
import jpabook.jpashop.repository.order.query.OrderQueryRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
class OrderApiController(
  private val orderRepository: OrderRepository,
  private val orderQueryRepository: OrderQueryRepository
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

  @GetMapping("/api/v3.1/orders")
  fun ordersV3Page(@RequestParam(value = "offset", defaultValue = "0") offset: Int,
                    @RequestParam(value = "limit", defaultValue = "100") limit: Int): List<OrderDto> {
    val orders = orderRepository.findAllWithMemberDelivery(offset, limit)
    return orders
      .map {o -> OrderDto(o)}
      .toList()
  }

  @GetMapping("/api/v4/orders")
  fun ordersV4(): List<OrderQueryDto> {
    return orderQueryRepository.findOrderQueryDtos()
  }

  @GetMapping("/api/v5/orders")
  fun ordersV5(): List<OrderQueryDto> {
    return orderQueryRepository.findAllByDtoOptimization()
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