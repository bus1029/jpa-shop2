package jpabook.jpashop.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.validation.constraints.NotEmpty

@Entity
class Member {
  @Id
  @GeneratedValue
  var id: Long? = null

  @NotEmpty
  var name = ""

  @Embedded
  var address: Address? = null

  @JsonIgnore
  @OneToMany(mappedBy = "member")
  val orders = mutableListOf<Order>()
}
