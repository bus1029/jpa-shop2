package jpabook.jpashop.domain

import jpabook.jpashop.domain.item.Item
import javax.persistence.*

@Entity
class Category {
  @Id
  @GeneratedValue
  var id: Long? = null
  var name: String = ""

  @ManyToMany
  @JoinTable(
    name = "category_item",
    joinColumns = [JoinColumn(name = "category_id")],
    inverseJoinColumns = [JoinColumn(name = "item_id")]
  )
  private val items = listOf<Item>()

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "parent_id")
  var parent: Category? = null

  @OneToMany
  private val child = mutableListOf<Category>()

  fun addChildCategory(child: Category) {
    this.child.add(child)
    child.parent = this
  }
}
