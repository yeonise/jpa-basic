package jpabasic.sample;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//@Entity
public class Member {

    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;
    private String name;

//    @Embedded
//    private Period workPeriod;

    @Embedded // @Embeddable 둘 중 하나만 사용해도 되지만 2가지 모두 작성하는 것을 권장
    private Address homeAddress;

    @ElementCollection
    @CollectionTable(name = "FAVORITE_FOOD", joinColumns = @JoinColumn(name = "MEMBER_ID"))
    @Column(name = "FOOD_NAME")
    /*
    값 타입 Collection : 값 타입을 하나 이상 저장할 때 사용
    - 데이터베이스는 Collection을 같은 테이블에 저장할 수 없기 때문에 Collection을 저장하기 위한 별도의 테이블이 필요하다
     */
    private Set<String> favoriteFood = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "ADDRESS", joinColumns = @JoinColumn(name = "MEMBER_ID"))
    private List<Address> addressHistory = new ArrayList<>();
    /*
    값 타입 Collection에 변경 사항이 발생하면 주인 Entity와 연관된 모든 데이터를 삭제하고 값 타입 Collection에 있는 현재 값을 모두 다시 저장한다
    값 타입 Collection을 Mapping 하는 테이블은 모든 column을 묶어서 기본 키를 구성할 수 밖에 없기 때문에 null이 입력되지 않으며 중복 저장되지 않는다
    - 결론적으로 사용하지 않는 것을 권장한다
    - 실무에서는 상황에 따라 값 타입 Collection 대신 일대다 관계를 고려한다
    - 영속선 전이와 고아 객체 제거를 사용해서 값 타입 Collection처럼 사용한다
    - Entity와 혼동해서 Entity를 값 타입으로 만들지 않도록 주의한다
    - 식별자가 필요하고 지속적으로 값을 추적 및 변경해야 한다면 값 타입이 아닌 Entity이다
     */

//    @Embedded
//    @AttributeOverrides({ // 한 Entity에서 같은 값을 사용할 때
//            @AttributeOverride(name = "city", column = @Column(name = "WORK_CITY")),
//            @AttributeOverride(name = "street", column = @Column(name = "WORK_STREET")),
//            @AttributeOverride(name = "zipcode", column = @Column(name = "WORK_ZIPCODE"))})
//    private Address workAddress;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Address getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(final Address homeAddress) {
        this.homeAddress = homeAddress;
    }

    public Set<String> getFavoriteFood() {
        return favoriteFood;
    }

    public List<Address> getAddressHistory() {
        return addressHistory;
    }

}
