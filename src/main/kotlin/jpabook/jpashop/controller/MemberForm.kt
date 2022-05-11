package jpabook.jpashop.controller

import javax.validation.constraints.NotEmpty

class MemberForm {
  @NotEmpty(message = "회원 이름은 필수입니다.")
  var name = ""

  var city = ""
  var street = ""
  var zipcode = ""
}
