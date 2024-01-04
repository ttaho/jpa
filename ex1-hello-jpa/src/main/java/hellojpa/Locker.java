package hellojpa;

import javax.persistence.*;

@Entity
public class Locker {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

//    // 양방향인데 연관관계 주인이 아니므로 읽기전용!
//    @OneToOne(mappedBy = "locker")
//    private Member member;
}
