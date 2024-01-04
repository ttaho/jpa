package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        // EntityManagerFactory는 데이터베이스당 1개!
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        // 트랜잭션 단위인 작업에는 entityManager를 만들어줘야한다!!
        // entityManager는 쓰레드간에 공유하면 안된다. 쓰고 버려야함.

        EntityManager entityManager = emf.createEntityManager(); // 엔티티매니저 만들기

        // JPA의 모든 데이터 변경은 트랜잭션 안에서 실행해야함.

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        // JPQL은 객체지향 쿼리언어이다. JPQL은 테이블 대상이 아닌 엔티티 객체를 대상
        try{

            Member member = new Member();
            member.setUsername("hello");
            member.setHomeAddress(new Address("city", "street", "10000"));
            member.setWorkPeriod(new Period());
            entityManager.persist(member);

            transaction.commit();
        } catch (Exception e){
            transaction.rollback();
        } finally {
            // close
            entityManager.close();

        }
        emf.close();

    }
}
