package jpabook.jpashop.repository.order.query

import com.fasterxml.jackson.annotation.JsonIgnore

class OrderItemQueryDto(@JsonIgnore var orderId: Long?, var itemName: String, var orderPrice: Int, var count: Int) {
}