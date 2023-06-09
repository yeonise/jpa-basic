package jpabasic.sample;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Set;

public class ValueMain {

    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("hello");
        EntityManager manager = factory.createEntityManager();

        EntityTransaction transaction = manager.getTransaction();
        transaction.begin();

        try {
            Member member = new Member();
            member.setName("member1");
            member.setHomeAddress(new Address("homeCity", "street1", "zipcode1"));

            member.getFavoriteFood().add("chicken");
            member.getFavoriteFood().add("pizza");
            member.getFavoriteFood().add("coke");

            member.getAddressHistory().add(new Address("old1", "street1", "zipcode1"));
            member.getAddressHistory().add(new Address("old2", "street1", "zipcode1"));

            manager.persist(member);
            /*
            값 타입 Collection은 별도의 테이블로 분류하긴 하지만 결국 값 타입이기 때문에 생명 주기는 Entity에 의존한다
            즉, 영속성 전이와 고아 객체 제거 기능을 필수로 갖는다고 볼 수 있다
            값 타입 컬렉션도 지연 로딩 전략을 사용한다
            */

            manager.flush();
            manager.clear();

            System.out.println("========== Start ==========");

            final Member findMember = manager.find(Member.class, member.getId());
            List<Address> addressHistory = findMember.getAddressHistory();
            for (Address address : addressHistory) {
                System.out.println("address = " + address.getCity());
            }

            final Set<String> favoriteFood = findMember.getFavoriteFood();
            for (String food : favoriteFood) {
                System.out.println("food = " + food);
            }

            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            manager.close();
        }
        factory.close();
    }

}
