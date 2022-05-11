package jpabook.jpashop.domain

import javax.persistence.Embeddable

@Embeddable
class Address protected constructor() {
  var city = ""
  var street = ""
  var zipcode = ""

  constructor(city: String, street: String, zipcode: String): this() {
    this.city = city
    this.street = street
    this.zipcode = zipcode
  }
}