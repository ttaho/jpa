package study.datajpa.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void testMember(){
        Member member = new Member("회원");

        Member save = memberRepository.save(member);
        Member finded = memberRepository.findById(save.getId()).get();

        assertThat(finded.getId()).isEqualTo(member.getId());
        assertThat(finded.getUsername()).isEqualTo(member.getUsername());
        assertThat(finded).isEqualTo(member);
    }
}