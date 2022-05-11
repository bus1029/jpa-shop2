package jpabook.jpashop

import jpabook.jpashop.domain.*
import jpabook.jpashop.domain.item.Book
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import javax.annotation.PostConstruct
import javax.persistence.EntityManager

@Component
class InitDb(
  private val initService: InitService
) {
  @PostConstruct
  fun init() {
    initService.dbInit1()
    initService.dbInit2()
  }

  @Component
  @Transactional
  class InitService(
    private val em: EntityManager
  ) {
    fun dbInit1() {
      println("Init1 ${this.javaClass}")
      val member = createMember("userA", "서울", "1", "1111")
      em.persist(member)

      val book1 = createBook("JPA1 BOOK", 10000, 100)
      val book2 = createBook("JPA2 BOOK", 20000, 100)
      em.persist(book1)
      em.persist(book2)

      val orderItem1 = OrderItem.createOrderItem(book1, 10000, 1)
      val orderItem2 = OrderItem.createOrderItem(book2, 20000, 2)

      val delivery = createDelivery(member)
      val order = Order.createOrder(member, delivery, orderItem1, orderItem2)
      em.persist(order)
    }

    fun dbInit2() {
      println("Init2 ${this.javaClass}")
      val member = createMember("userB", "부산", "2", "2222")
      em.persist(member)

      val book1 = createBook("SPRING1 BOOK", 20000, 200)
      val book2 = createBook("SPRING2 BOOK", 40000, 300)
      em.persist(book1)
      em.persist(book2)

      val orderItem1 = OrderItem.createOrderItem(book1, 20000, 3)
      val orderItem2 = OrderItem.createOrderItem(book2, 40000, 4)

      val delivery = createDelivery(member)
      val order = Order.createOrder(member, delivery, orderItem1, orderItem2)
      em.persist(order)
    }

    private fun createBook(name: String, price: Int, stockQuantity: Int): Book {
      val book = Book()
      book.name = name
      book.price = price
      book.stockQuantity = stockQuantity
      return book
    }

    private fun createMember(name: String, city: String, street: String, zipcode: String): Member {
      val member = Member()
      member.name = name
      member.address = Address(city, street, zipcode)
      return member
    }

    private fun createDelivery(member: Member): Delivery {
      val delivery = Delivery()
      delivery.address = member.address
      return delivery
    }
  }
}