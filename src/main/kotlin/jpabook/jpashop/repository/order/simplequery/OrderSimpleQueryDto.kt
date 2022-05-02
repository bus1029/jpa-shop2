package jpabook.jpashop.repository.order.simplequery

import jpabook.jpashop.domain.Address
import jpabook.jpashop.domain.OrderStatus
import java.time.LocalDateTime

class OrderSimpleQueryDto(
  var orderId: Long?,
  var name: String,
  var orderDate: LocalDateTime,
  var orderStatus: OrderStatus,
  var address: Address
) {
}