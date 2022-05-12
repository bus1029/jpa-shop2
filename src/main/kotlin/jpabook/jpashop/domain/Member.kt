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

  // 양방향 연관관계의 경우 한 쪽은 JsonIgnore 를 명시해줘야 함
  @JsonIgnore
  @OneToMany(mappedBy = "member")
  val orders = mutableListOf<Order>()
}
