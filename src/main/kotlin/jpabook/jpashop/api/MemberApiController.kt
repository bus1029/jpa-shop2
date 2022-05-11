package jpabook.jpashop.api

import jpabook.jpashop.domain.Member
import jpabook.jpashop.service.MemberService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid
import javax.validation.constraints.NotEmpty

@RestController
class MemberApiController(
  private val memberService: MemberService
) {
  @PostMapping("/api/v1/members")
  fun saveMemberV1(@RequestBody @Valid member: Member): CreateMemberResponse {
    val id = memberService.join(member)
    return CreateMemberResponse(id)
  }

  @PostMapping("api/v2/members")
  fun saveMemberV2(@RequestBody @Valid request: CreateMemberRequest): CreateMemberResponse {
    val member = Member()
    member.name = request.name
    val id = memberService.join(member)
    return CreateMemberResponse(id)
  }

  class CreateMemberRequest(@NotEmpty val name: String) {

  }

  class CreateMemberResponse(val id: Long) {
  }
}