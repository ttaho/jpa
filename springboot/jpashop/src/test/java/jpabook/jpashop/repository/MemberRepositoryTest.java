package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    MemberRepositoryOld memberRepository;


    @Test
    @Transactional // 이걸 무조건 걸어줘야함. 엔티티매니저는 트랜잭션내에서 작업이 수행되어야함
    @Rollback(value = false) // 이걸 해주면  test 끝나고 db 반영됨
    public void testMember() throws Exception{
        Member member = new Member();
        member.setName("memberA");
        Long savedId = memberRepository.save(member);

        Member findMember = memberRepository.findOne(savedId);

        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
        Assertions.assertThat(findMember.getName()).isEqualTo(member.getName());
        Assertions.assertThat(findMember).isEqualTo(member);

    }


}