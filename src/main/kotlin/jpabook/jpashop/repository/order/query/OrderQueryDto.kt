package jpabook.jpashop.repository.order.query

import jpabook.jpashop.domain.Address
import jpabook.jpashop.domain.OrderStatus
import java.time.LocalDateTime

class OrderQueryDto(
  var orderId: Long?,
  var name: String,
  var orderDate: LocalDateTime?,
  var orderStatus: OrderStatus?,
  var address: Address?
) {
  var orderItems: List<OrderItemQueryDto>? = null

  constructor(orderId: Long?, name: String, orderDate: LocalDateTime?, orderStatus: OrderStatus?, address: Address?, orderItems: List<OrderItemQueryDto>?) : this(orderId, name, orderDate, orderStatus, address) {
    this.orderItems = orderItems
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as OrderQueryDto

    if (orderId != other.orderId) return false

    return true
  }

  override fun hashCode(): Int {
    return orderId?.hashCode() ?: 0
  }
}