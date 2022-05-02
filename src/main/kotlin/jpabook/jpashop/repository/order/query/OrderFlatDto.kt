package jpabook.jpashop.repository.order.query

import jpabook.jpashop.domain.Address
import jpabook.jpashop.domain.OrderStatus
import java.time.LocalDateTime

class OrderFlatDto() {
  var orderId: Long? = null
  var name = ""
  var orderDate: LocalDateTime? = null
  var address: Address? = null
  var orderStatus: OrderStatus? = null

  var itemName = ""
  var orderPrice = 0
  var count = 0

  constructor(orderId: Long, name: String, orderDate: LocalDateTime, orderStatus: OrderStatus, address: Address, itemName: String, orderPrice: Int, count: Int) : this() {
    this.orderId = orderId
    this.name = name
    this.orderDate = orderDate
    this.orderStatus = orderStatus
    this.address = address
    this.itemName = itemName
    this.orderPrice = orderPrice
    this.count = count
  }
}