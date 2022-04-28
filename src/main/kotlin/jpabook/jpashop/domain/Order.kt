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

  @ManyToOne
  @JoinColumn(name = "member_id")
  private var member: Member? = null

  @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL])
  private val orderItems = mutableListOf<OrderItem>()

  @OneToOne
  @JoinColumn(name = "delivery_id")
  private var delivery: Delivery? = null

  private var orderDate: LocalDateTime? = null

  @Enumerated(EnumType.STRING)
  var status: OrderStatus? = null

  fun setMember(member: Member) {
    this.member = member
    member.orders.add(this)
  }

  fun addOrderItem(orderItem: OrderItem) {
    orderItems.add(orderItem)
    orderItem.order = this
  }

  fun setDelivery(delivery: Delivery) {
    this.delivery = delivery
    delivery.order = this
  }

  companion object {
    fun createOrder(member: Member, delivery: Delivery, vararg orderItems: OrderItem): Order {
      val order = Order()
      order.setMember(member)
      order.setDelivery(delivery)
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
