package jpql;

import javax.persistence.*;
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
            member.setUsername("member1");
            member.setAge(10);
            entityManager.persist(member);

            // return 타입 정보가 명확한 경우 TypedQuery 사용
            TypedQuery<Member> query1 = entityManager.createQuery("select m from Member m", Member.class);
            System.out.println(query1);
            // return 타입 정보가 명확하지 않은 경우(String 컬럼과 int 컬럼 동시 리턴인 경우) Query 사용
//            Query query2 = entityManager.createQuery("select m.username, m.age From Member m");

            // 쿼리 수행 값이 1개 이상인 경우 getResultList. -> 결과가 없으면 빈리스트 반환
            List<Member> resultList = query1.getResultList();
            // 쿼리 수행 값이 정확히 1개인 경우 getSingleResult.
            // 결과가 없으면 NoResultException 발생
            // 결과가 2개 이상이면 NonUniqueResultException 발생
            Member singleResult = query1.getSingleResult();

            TypedQuery<Member> query = entityManager.createQuery("select m from Member m where m.username = :username", Member.class);
            query.setParameter("username", "member1"); // 이름 기준 -> 쿼리문에 username 자리에 member1을 넣는것.            Member singleResult1 = query.getSingleResult(); // 여기서 쿼리가 실행된다. 위의 작업은 세팅작업
            System.out.println("singleResult = " + singleResult.getUsername());


            transaction.commit();
        } catch (Exception e){
            transaction.rollback();
        } finally {
            // close
            entityManager.close();
            emf.close();
        }

    }
}