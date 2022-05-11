package jpabook.jpashop.api

import jpabook.jpashop.domain.Member
import jpabook.jpashop.service.MemberService
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
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

  @PatchMapping("api/v2/members/{id}")
  fun updateMemberV2(@PathVariable("id") id: Long,
                     @RequestBody @Valid request: UpdateMemberRequest): UpdateMemberResponse {
    // update 에서 수정한 멤버를 반환해도 되지만, 그럴 경우 커맨드와 쿼리가 같이 있게 되기 때문에(커맨드: Update, 쿼리: Select)
    // 이 둘을 분리하려고 update 에선 아무값도 반환하지 않거나, update 한 id 정도만 반환하게 만든다.
    memberService.update(id, request.name)
    val member = memberService.findOne(id)
    member?.let {
      return UpdateMemberResponse(id, member.name)
    }

    return UpdateMemberResponse(id, "")
  }

  class UpdateMemberRequest(val name: String) {
  }

  class UpdateMemberResponse(val id: Long, val name: String) {
  }

  class CreateMemberRequest(@NotEmpty val name: String) {
  }

  class CreateMemberResponse(val id: Long) {
  }
}