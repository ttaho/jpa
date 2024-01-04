package jpabook.jpashop.repository;

import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    // 스프링 부트에서는 EntityManagerFactory에서 꺼낼 필요없이 바로 EntityManager를 사용하면된다.
//    @PersistenceContext // EntityManager를 빈으로 주입할 때 사용. EntityManager의 동시성 문제를 해결해준다.
//    private EntityManager em;

    // Spring Data JPA는 위의 @PersistenceContext로 주입해주는것을 @Autowired로 가능하게 해준다. 그래서 @RequiredArgsConstructor 사용가능
    private final EntityManager em;

    public Long save(Member member){
        em.persist(member);
        return member.getId();
    }

    public Member findOne(Long id){
        return em.find(Member.class, id);
    }

    public List<Member> findAll(){
        return em.createQuery("select m from Member m", Member.class)
            .getResultList();
    }

    public List<Member> findByName(String name){
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
            .setParameter("name", name)
            .getResultList();
    }

}
