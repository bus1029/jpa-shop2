package jpabook.jpashop.repository

import jpabook.jpashop.domain.Order
import org.springframework.stereotype.Repository
import org.springframework.util.StringUtils
import javax.persistence.EntityManager

@Repository
class OrderRepository(
  private val em: EntityManager
) {
  fun save(order: Order) {
    em.persist(order)
  }

  fun findOne(id: Long): Order? {
    return em.find(Order::class.java, id)
  }

  fun findAll(): List<Order> {
    return em.createQuery("select o from Order o", Order::class.java)
      .resultList
  }

  fun findAllByString(orderSearch: OrderSearch): List<Order> {
    var jpql = "select o from Order o join o.member m"
    var isFirstCondition = true

    if (orderSearch.orderStatus != null) {
      if (isFirstCondition) {
        jpql += " where"
        isFirstCondition = false
      } else {
        jpql += " and"
      }
      jpql += " o.status = :status"
    }

    if (StringUtils.hasText(orderSearch.memberName)) {
      if (isFirstCondition) {
        jpql += " where"
        isFirstCondition = false
      } else {
        jpql += " and"
      }
      jpql += " m.name like :name"
    }

    var query = em.createQuery(jpql, Order::class.java)
      .setMaxResults(1000)

    if (orderSearch.orderStatus != null) {
      query = query.setParameter("status", orderSearch.orderStatus)
    }
    if (StringUtils.hasText(orderSearch.memberName)) {
      query = query.setParameter("name", orderSearch.memberName)
    }

    return query.resultList
  }

  fun findAllWithMemberDelivery(): List<Order> {
    return em.createQuery(
      "select o " +
              "from Order o " +
              "join fetch o.member m " +
              "join fetch o.delivery d", Order::class.java
    ).resultList
  }

  fun findAllWithItem(): MutableList<Order> {
    return em.createQuery(
      "select distinct o " +
              "from Order o " +
              "join fetch o.member m " +
              "join fetch o.delivery d " +
              "join fetch o.orderItems oi " +
              "join fetch oi.item i", Order::class.java
    ).resultList
  }

  fun findAllWithMemberDelivery(offset: Int, limit: Int): MutableList<Order> {
    return em.createQuery(
      "select o " +
              "from Order o " +
              "join fetch o.member m " +
              "join fetch o.delivery d", Order::class.java
    ).setFirstResult(offset)
      .setMaxResults(limit)
      .resultList
  }
}