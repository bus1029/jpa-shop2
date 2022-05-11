package jpabook.jpashop.domain

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "orders")
class Order protected constructor() {
  @Id
  @GeneratedValue
  @Column(name = "order_id")
  var id: Long? = null

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  var member: Member? = null
    set(value) {
      field = value
      field?.orders?.add(this)
    }

  @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL])
  private val orderItems = mutableListOf<OrderItem>()

  @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
  @JoinColumn(name = "delivery_id")
  var delivery: Delivery? = null
    set(delivery) {
      field = delivery
      field?.order = this
    }

  var orderDate: LocalDateTime? = null

  @Enumerated(EnumType.STRING)
  var status: OrderStatus? = null

  fun getOrderItems(): MutableList<OrderItem> {
    return this.orderItems
  }

  fun addOrderItem(orderItem: OrderItem) {
    orderItems.add(orderItem)
    orderItem.order = this
  }

  companion object {
    fun createOrder(member: Member, delivery: Delivery, vararg orderItems: OrderItem): Order {
      val order = Order()
      order.member = member
      order.delivery = delivery
      for (orderItem in orderItems) {
        order.addOrderItem(orderItem)
      }
      order.status = OrderStatus.ORDER
      order.orderDate = LocalDateTime.now()
      return order
    }
  }

  fun cancel() {
    if (delivery?.status == DeliveryStatus.COMP) {
      throw IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.")
    }

    this.status = OrderStatus.CANCEL
    for (orderItem in orderItems) {
      orderItem.cancel()
    }
  }

  fun getTotalPrice(): Int {
    var totalPrice = 0
    for (orderItem in orderItems) {
      totalPrice += orderItem.getTotalPrice()
    }
    return totalPrice
  }
}
