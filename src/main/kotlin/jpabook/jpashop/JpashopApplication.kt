package jpabook.jpashop

import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class JpashopApplication {
  @Bean
  fun hibernate5Module(): Hibernate5Module {
    val hibernate5Module = Hibernate5Module()
//    hibernate5Module.configure(Hibernate5Module.Feature.FORCE_LAZY_LOADING, true)
    return hibernate5Module
  }
}

fun main(args: Array<String>) {
  runApplication<JpashopApplication>(*args)
}