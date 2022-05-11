package jpabook.jpashop.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import jpabook.jpashop.domain.item.Item
import javax.persistence.*

@Entity
class OrderItem protected constructor() {
  @Id
  @GeneratedValue
  @Column(name = "order_item_id")
  var id: Long? = null

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "item_id")
  var item: Item? = null

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id")
  var order: Order? = null

  var orderPrice = 0
  var count = 0

  companion object {
    fun createOrderItem(item: Item, orderPrice: Int, count: Int): OrderItem {
      val orderItem = OrderItem()
      orderItem.item = item
      orderItem.orderPrice = orderPrice
      orderItem.count = count

      item.removeStock(count)
      return orderItem
    }
  }

  fun cancel() {
    item?.addStock(count)
  }

  fun getTotalPrice(): Int {
    return orderPrice * count
  }
}
