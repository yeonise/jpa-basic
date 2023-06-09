package jpabasic.sample;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JPQL {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("hello");
        EntityManager manager = factory.createEntityManager();

        EntityTransaction transaction = manager.getTransaction();
        transaction.begin();

        try {
            final List<Member> members = manager.createQuery("SELECT m FROM Member m WHERE m.name LIKE '%kim%'", Member.class).getResultList();

            for (Member member : members) {
                System.out.println("member = " + member.getName());
            }

            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            manager.close();
        }
        factory.close();
    }
    /*
    < JPQL >
    - JPA를 사용하면 Entity 객체를 중심으로 개발
    - 검색할 때도 테이블이 아닌 Entity 객체를 대상으로 검색한다
    - 그러나 모든 데이터베이스를 객체로 변환해서 검색하는 것은 불가능하다
    - JPA는 SQL을 추상화한 JPQL이라는 객체 지향 Query 언어를 제공한다
    - 결국 JPQL을 작성하면 SQL로 번환되어 실행된다
    - JPQL : Entity 객체를 대상으로 한다
    - SQL : 데이터베이스 테이블을 대상으로 한다

    < QueryDSL >
    - 자바 코드로 JPQL을 작성할 수 있다
    - JPQL Builder 역할
    - 컴파일 시점에 문법적인 오류를 찾을 수 있다
    - 동적 Query 작성이 편리하다
    - Criteria 대신 실무에서 사용하는 것을 권장한다

    < Native SQL >
    - JPA가 제공하는 SQL을 직접 사용
    - JPQL로 해결할 수 없는 특정 데이터베이스에 의존적인 기능을 수행할 때
    - manager.createNativeQuery()
    - Native SQL 대신 SpringJdbcTemplate을 직접 사용하는 경우가 더 많다
        - JPA를 사용하면서 JDBC connection을 직접 사용하거나 SpringJdbcTemplate, Mybatis 등을 함께 사용할 수 있다
        - 단, 영속성 컨텍스트를 적절한 시점에 강제로 flush 해야 한다
    */

}
