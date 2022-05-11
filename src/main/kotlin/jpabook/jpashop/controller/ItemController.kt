package jpabook.jpashop.controller

import jpabook.jpashop.domain.item.Book
import jpabook.jpashop.service.ItemService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping

@Controller
class ItemController(
  private val itemService: ItemService
) {
  @GetMapping("/items/new")
  fun createForm(model: Model): String {
    model.addAttribute("form", BookForm())
    return "items/createItemForm"
  }

  @PostMapping("/items/new")
  fun create(form: BookForm): String {
    val book = Book()
    book.apply {
      this.name = form.name
      this.price = form.price
      this.stockQuantity = form.stockQuantity
      this.author = form.author
      this.isbn = form.isbn
    }

    itemService.saveItem(book)
    return "redirect:/"
  }

  @GetMapping("/items")
  fun list(model: Model): String {
    val items = itemService.findItems()
    model.addAttribute("items", items);
    return "items/itemList"
  }

  @GetMapping("items/{itemId}/edit")
  fun updateItemForm(@PathVariable("itemId") itemId: Long, model: Model): String {
    val item = itemService.findOne(itemId) as Book

    val bookForm = BookForm()
    bookForm.apply {
      this.id = item.id
      this.name = item.name
      this.price = item.price
      this.stockQuantity = item.stockQuantity
      this.author = item.author
      this.isbn = item.isbn
    }

    model.addAttribute("form", bookForm)
    return "items/updateItemForm"
  }

  @PostMapping("items/{itemId}/edit")
  fun updateItem(@PathVariable("itemId") itemId: Long, @ModelAttribute("form") form: BookForm): String {
    itemService.updateItem(itemId, form.name, form.price, form.stockQuantity)
    return "redirect:/items"
  }
}