package jpabasic.query;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("hello");
        EntityManager manager = factory.createEntityManager();

        EntityTransaction transaction = manager.getTransaction();
        transaction.begin(); // 단순 조회를 제외한 JPA의 모든 변경은 transaction 안에서 실행

        try {
            for (int i = 0; i < 100; i++) {
                Member member = new Member();
                member.setName("member" + i);
                member.setAge(10 + i);
                manager.persist(member);
            }

            manager.flush();
            manager.clear();

            final List<Member> members = manager.createQuery("SELECT m FROM Member m ORDER BY m.age DESC", Member.class)
                    .setFirstResult(1)
                    .setMaxResults(10)
                    .getResultList();

            for (Member member : members) {
                System.out.println("member = " + member.getName() + " " + member.getAge());
            }
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback(); // 문제가 발생했을 경우에 rollback
        } finally {
            manager.close(); // entity manager가 내부적으로 하나의 connection을 갖고 동작하기 때문에 반드시 닫아주기
        }
        factory.close();
    }
    /*
    < JPQL 문법 >
    - Entity와 속성은 대소문자를 구분한다
    - JPQL 키워드는 대소문자를 구분하지 않는다
    - 별칭은 필수로 사용한다
    TypeQuery : 반환 타입이 명확할 때 사용
    Query : 반환 타입이 명확하지 않을 때 사용

    < 결과 조회 API >
    query.getResultList() : 결과가 하나 이상일 때 List 반환 / 결과가 없으면 빈 List 반환
    query.getSingleResult() : 결과가 정확히 하나일 때 / 단일 객체 반환
        - 결과가 없는 경우 : javax.persistence.NoResultException 발생
        - 둘 이상인 경우 : javax.persistence.NonUniqueResultException 발생
        - 결과가 없는 경우 Exception이 발생하는 것에 대한 논쟁이 있다
            - Spring Data JPA를 사용하면 결과가 없는 경우 null이나 Optional을 반환한다
                - Spring이 try-catch를 내부적으로 처리해준다

    < Parameter Binding >
    1. 이름 기준을 사용할 것을 권장한다
        ex) WHERE m.name=:username
        query.setParameter("username", nameParam);
    2. 위치 기준은 권장하지 않는다
        ex) WHERE m.name=?1
        query.setParameter(1, nameParam);

    < Projection >
    Select 절에 조회할 대상을 지정하는 것을 말한다
    대상 : Entity, Embedded, Scala Type
    - Entity Projection : SELECT m FROM Member m / 영속성 컨텍스트에 의해 관리된다
    - Entity Projection : SELECT t FROM Member m JOIN m.team t / Join은 명시하는 것이 좋다
    - Embedded Projection : SELECT m.address FROM Member m
    - Scala Projection : SELECT m.name, m.age FROM Member m

    < 여러 값 조회 >
    1. Query 타입으로 조회
    2. Object[] 타입으로 조회
    3. new 명령어로 조회
        - 단순 값을 DTO로 바로 조회
            ex) SELECT new jpa.jpql.UserDTO(m.name, m.age) FROM Member m
        - 패키지를 포함한 전체 클래스 이름을 입력해야 한다
        - 순서와 타입이 일치하는 생성자가 필요하다

    < Paging API >
    JPA는 Paging을 다음 2개의 API로 추상화
        - setFirstResult(int startPosition) : 조회 시작 위치
        - setMaxResults(int maxResult) : 조회할 데이터의 수
    */

    /*
    < Join >
    1. Inner Join
        조건에 맞는 값이 없는 경우 아무것도 나오지 않는다
        ex) SELECT m FROM Member m [INNER] JOIN m.team t
    2. Outer Join
        조건에 맞는 값이 없는 경우 null로 반환한다
        ex) SELECT m FROM Member m LEFT [OUTER] JOIN m.team t
    3. Theta Join
        연관 관계가 전혀 없는 경우
        ex) SELECT count(m) FROM Member m, Team t WHERE m.name = t.name

    - ON
    1. Join 대상을 Filtering
        - JPQL : SELECT m, t FROM Member m LEFT JOIN m.team t ON t.name = 'A'
        - SQL : SELECT m.*, t.* FROM Member m LEFT JOIN Team t ON m.team_id=t.id and t.name='A'
    2. 연관 관계가 없는 Entity Outer Join
        - JPQL : SELECT m, t FROM Member m LEFT JOIN Team t ON m.name=t.name
        - SQL : SELECT m.*, t.* FROM Member m LEFT JOIN Team t ON m.name=t.name

    < SubQuery > * 공부하기 *
    JPA는 WHERE, HAVING 절에서만 subquery 사용이 가능하다
    FROM 절의 subquery는 현재 JPQL에서는 사용이 불가하다
    SELECT 절은 Hibernate에서 지원하기 때문에 사용이 가능하다
        - Join으로 풀 수 있으면 풀어서 해결한다

    < JPQL의 Type 표현 >
    - 문자 : 'Hello', 'She''s'
    - 숫자 : 10L, 10D, 10F
    - Boolean : TRUE, FALSE
    - Enum : jpa.MemberType.Admin / package 포함
    - Entity : TYPE(m) = Member / 상속 관계에서 사용
    - 표준 SQL 모두 지원한다

    조건식 - CASE
    - 기본 CASE
    - 단순 CASE
    */
}
