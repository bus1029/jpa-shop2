package jpabook.jpashop.service

import jpabook.jpashop.domain.Member
import jpabook.jpashop.repository.MemberRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class MemberService(
  private val memberRepository: MemberRepository
) {
  @Transactional
  fun join(member: Member): Long {
    validateDuplicateMember(member)
    memberRepository.save(member)
    return member.id!!
  }

  private fun validateDuplicateMember(member: Member) {
    val findMembers = memberRepository.findByName(member.name)
    if (findMembers.isNotEmpty()) {
      throw IllegalStateException("이미 존재하는 회원입니다.")
    }
  }

  fun findMembers(): List<Member> {
    return memberRepository.findAll()
  }

  fun findOne(memberId: Long): Member? {
    return memberRepository.findOne(memberId)
  }

  @Transactional
  fun update(id: Long, name: String) {
    val member = memberRepository.findOne(id)
    member?.name = name
  }
}
