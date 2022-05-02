package jpabook.jpashop.service

import jpabook.jpashop.domain.item.Item
import jpabook.jpashop.repository.ItemRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ItemService(
  private val itemRepository: ItemRepository
) {
  @Transactional
  fun saveItem(item: Item) {
    itemRepository.save(item)
  }

  @Transactional
  fun updateItem(itemId: Long, name: String, price: Int, stockQuantity: Int) {
    val item = itemRepository.findOne(itemId)
    item!!.apply {
      item.name = name
      item.price = price
      item.stockQuantity = stockQuantity
    }
  }

  fun findItems(): List<Item> {
    return itemRepository.findAll()
  }

  fun findOne(itemId: Long): Item? {
    return itemRepository.findOne(itemId)
  }
}