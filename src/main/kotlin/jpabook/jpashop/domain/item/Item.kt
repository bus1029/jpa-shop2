package jpabook.jpashop.domain.item

import jpabook.jpashop.domain.Category
import jpabook.jpashop.exception.NotEnoghStockException
import javax.persistence.Column
import javax.persistence.DiscriminatorColumn
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Inheritance
import javax.persistence.InheritanceType
import javax.persistence.ManyToMany

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
abstract class Item {
  @Id
  @GeneratedValue
  @Column(name = "item_id")
  var id: Long? = null

  var name: String = ""
  var price: Int = 0
  var stockQuantity: Int = 0

  @ManyToMany(mappedBy = "items")
  private val categories = mutableListOf<Category>()

  fun addStock(quantity: Int) {
    this.stockQuantity += quantity
  }

  fun removeStock(quantity: Int) {
    val restStock = this.stockQuantity - quantity
    if (restStock < 0) {
      throw NotEnoghStockException("Need more stock")
    }
    this.stockQuantity = restStock
  }
}