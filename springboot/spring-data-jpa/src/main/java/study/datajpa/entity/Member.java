package study.datajpa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Member {

    @Id
    @GeneratedValue
    private Long id;
    private String username;

    protected Member(){} // 프록시 기술을 쓸때 Hibernate에서 생성자를 필요로 하는데 막아놓으면 안된다.

    public Member(String name){
        this.username = name;
    }
}
