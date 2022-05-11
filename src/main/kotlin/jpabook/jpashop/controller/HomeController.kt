package jpabook.jpashop.controller

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class HomeController {
  private val log: Logger = LoggerFactory.getLogger(this::class.java)

  @RequestMapping("/")
  fun home(): String {
    log.info("Home controller")
    return "home"
  }
}