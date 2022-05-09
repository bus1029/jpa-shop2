package jpabook.jpashop.controller

import jpabook.jpashop.domain.Address
import jpabook.jpashop.domain.Member
import jpabook.jpashop.service.MemberService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import javax.validation.Valid

@Controller
class MemberController(
  private val memberService: MemberService
) {
  @GetMapping("/members/new")
  fun createForm(model: Model): String {
    model.addAttribute("memberForm", MemberForm())
    return "members/createMemberForm"
  }

  @PostMapping("/members/new")
  // @Valid: MemberForm 에 작성된 @NotEmpty 를 검사하라는 애노테이션
  fun create(@Valid form: MemberForm, result: BindingResult): String {
    if (result.hasErrors()) {
      return "members/createMemberForm"
    }

    val address = Address(form.city, form.street, form.zipcode)
    val member = Member()
    member.name = form.name
    member.address = address

    memberService.join(member)
    return "redirect:/"
  }

  @GetMapping("/members")
  fun list(model: Model): String {
    val members = memberService.findMembers()
    model.addAttribute("members", members)
    return "members/memberList"
  }
}