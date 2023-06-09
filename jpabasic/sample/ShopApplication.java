package jpabasic.sample;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class ShopApplication {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("hello");
        EntityManager manager = factory.createEntityManager();

        EntityTransaction transaction = manager.getTransaction();
        transaction.begin();

        try {
            Book book = new Book();
            book.setName("JPA");
            book.setAuthor("김영한");

            manager.persist(book);

            manager.getReference(Book.class, book.getId());
            /*
            manager.find() : 데이터베이스를 통해서 실제 Entity 객체 조회
            manager.getReference() : 데이터베이스 조회를 미루는 Proxy Entity 객체 조회
            Proxy 특징
                - 실제 클래스를 상속받아서 만들어진다
                - 실제 클래스와 겉모양이 같다
                - Proxy 객체는 실제 객체의 참조인 Target을 보관한다
                - Proxy 객체를 호출하면 Proxy 객체는 실제 객체의 메서드를 호출한다
                - Proxy 객체는 실제 Entity를 상속받기 때문에 Type Check 시 주의해야 한다 ( == 비교대신 instance of를 사용해야 한다 )
                - 영속성 컨텍스트에 찾는 Entity가 이미 있다면 manager.getReference()를 호출해도 실제 Entity를 반환한다
                    - JPA의 하나의 transaction에서 동일성을 보장하는 특징 때문에
                    - 그 반대도 마찬가지로 영속성 컨텍스트에 Proxy가 이미 있다면 manager.find()를 호출해도 Proxy를 반환한다
            Proxy 초기화
                1. 요청이 들어왔는데 Target에 값이 없는 경우 영속성 컨텍스트에 초기화를 요청한다
                2. 영속성 컨텍스트가 데이터베이스를 조회해서 실제 Entity 객체를 생성한다
                3. Proxy의 Target과 실제 Entity 객체를 연결한다
             */

            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            manager.close();
        }
        factory.close();
    }
    /*
    테이블
        - 외래키 하나로 양쪽에서 사용할 수 있다
        - 사실 방향이라는 개념이 없다
    객체
        - 참조 필드가 있는 쪽으로만 참조할 수 있다
        - 한쪽만 참조하면 단방향
        - 양쪽이 서로 참조하면 양방향
        - 연관 관계의 주인 : 외래키를 관리하는 곳
        - 주인의 반대편 : 단순 조회만 가능하다
    1. 다대일 관계 @ManyToOne
        - 가장 많이 사용한다
    2. 일대다 관계 @OneToMany
        - 테이블의 일대다 관계는 항상 다 쪽에 외래키가 있다
        - 객체와 테이블의 차이 때문에 반대편 테이블의 외래키를 관리하는 특이한 구조
        - @JoinColumn을 사용하지 않으면 중간에 테이블을 하나 추가하기 때문에 꼭 사용하자
        - 일대다 단방향보다 다대일 양방향을 사용하는 것을 권장
    3. 일대일 관계 @OneToOne
        - 주로 많이 사용하는 테이블이나 대상 테이블 중에서 외래키 위치를 선택
        - 외래키가 있는 곳이 연관 관계의 주인
        - 반대편은 mappedBy 적용
        - JPA에서 단방향 관계는 지원하지 않으며 양방향 관계는 지원한다
    4. 다대다 관계 @ManyToMany
        - 관계형 데이터베이스는 정규화된 테이블 2개로 다대다 관계를 표현할 수 없다
        - 연결 테이블을 추가해서 일대다 또는 다대일 관계로 풀어내야 한다
        - 실무에서 사용하지 않는다
     */

}
