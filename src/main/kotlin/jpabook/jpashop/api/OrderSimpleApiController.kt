package jpabook.jpashop.api

import jpabook.jpashop.domain.Address
import jpabook.jpashop.domain.Order
import jpabook.jpashop.domain.OrderStatus
import jpabook.jpashop.repository.OrderRepository
import jpabook.jpashop.repository.OrderSearch
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

/**
 * x To One (ManyToOne, OneToOne)
 * Order
 * Order -> Member
 * Order -> Delivery
 */
@RestController
class OrderSimpleApiController(
  private val orderRepository: OrderRepository,
) {

  @GetMapping("/api/v1/simple-orders")
  fun ordersV1(): List<Order> {
    val orders = orderRepository.findAllByString(OrderSearch())
    for (order in orders) {
      order.member?.name // Lazy 강제 초기화
      order.delivery?.address // Lazy 강제 초기화
    }
    return orders
  }

  @GetMapping("/api/v2/simple-orders")
  fun ordersV2(): List<SimpleOrderDto> {
    val orders = orderRepository.findAllByString(OrderSearch())
    return orders
      .map { o -> SimpleOrderDto(o) }
      .toList()
  }

  class SimpleOrderDto(){
    var orderId: Long? = null
    var name: String = ""
    var orderDate: LocalDateTime? = null
    var orderStatus: OrderStatus? = null
    var address: Address? = null

    constructor(order: Order) : this() {
      this.orderId = order.id
      this.name = order.member?.name ?: "" // LAZY 초기화
      this.orderDate = order.orderDate
      this.orderStatus = order.status
      this.address = order.delivery?.address // LAZY 초기화
    }
  }
}