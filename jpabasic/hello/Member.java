package jpabasic.hello;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import java.time.LocalDateTime;

// @Entity
public class Member {
    /*
    @Entity가 붙은 클래스는 JPA가 관리한다
    - JPA를 사용해서 테이블과 매핑할 클래스라면 필수적으로 붙여야 한다
    - 기본 생성자를 필수적으로 갖는다 / 단, parameter가 없는 public 또는 protected 생성자
    - final class, enum, interface, inner class 사용 불가
    - 저장할 field에 final 사용 불가
     */

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /*
    @GeneratedValue
    1. AUTO : 데이터베이스 방언에 맞춰서 자동으로 생성 / 나머지 3개의 전략 중에 하나를 자동으로 선택
    2. IDENTITY : 기본키 생성을 데이터베이스에 위임
        - 데이터베이스에 insert 값으로 null이 들어오면 그 때 key 값을 넣어준다
        - 그래서 데이터베이스에 데이터가 들어가야 key 값을 알 수 있다
        - 그런데 영속성 컨텍스트에서 관리되려면 무조건 PK 값이 있어야 한다
        - 그래서 예외적으로 manager.persist() 할 때 즉시 insert SQL을 실행한다
    3. TABLE : 키 생성 테이블을 만들어서 SQUENCE와 같이 사용
        - SQUENCE는 숫자를 가져오는 로직이 최적화되어 있지만 테이블은 그렇지 않기 때문에 성능이 다소 떨어진다
        - 모든 데이터베이스에서 사용 가능
    4. SEQUENCE : sequence object를 통해 값을 가져와서 설정
        + allocationSize 나중에 더 학습하기
     */
    /*
    primary key 값으로 Integer 대신 Long을 선택하는 이유 :
        - 더 많은 데이터를 저장하기 위해
        - 공간이 2배 정도 차이가 나겠지만 데이터가 증가하여 Integer에서 Long으로 변환하는 것이 훨씬 어렵다
        - Integer 범위 : 최대 2,147,483,647
        - Long 범위 : 최대 9,223,372,036,854,775,807
     */
    /*
    권장하는 식별자 전략 :
    시간이 지나도 변하면 안된다는 조건을 만족하는 자연키를 찾기 어렵기 때문에 대체키를 사용하는 것이 좋다
    - Long type + 대체키 + 키 생성 전략 사용
     */
    @Column(nullable = false) // NOT NULL 제약 조건
    private String name;
    private int age;
    @Enumerated(EnumType.STRING) // enum 이름을 데이터베이스에 저장 vs EnumType.ORDINAL : enum 순서를 데이터베이스에 저장 ORDINAL 사용을 권장하지 않는다
    private Role role;
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;
    @Lob
    private String description;

    public Member() {
        /*
        JPA는 기본적으로 내부적으로 reflection 같은 것들을 사용하기 때문에 동적으로 객체를 생성한다
        그래서 필수적으로 기본 생성자가 있어야 한다
         */
    }

}
