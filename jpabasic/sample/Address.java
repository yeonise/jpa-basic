package jpabasic.sample;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

//@Embeddable
public class Address {
    /*
    JPA는 데이터 타입을 최상위 레벨로 보면 2가지로 분류한다
    1. Entity 타입
        - @Entity로 정의하는 객체
        - 내부 데이터가 변해도 식별자로 지속적으로 추적이 가능하다
    2. 값 타입
        - int, Integer처럼 단순히 값으로 사용하는 Java 기본 타입이나 객체
        - 식별자가 없고 값만 있으므로 변경 시 추적이 불가하다
        - 값 타입은 복잡한 객체 세상을 조금이라도 단순화하려고 만든 개념으로 단순하고 안전하게 다룰 수 있어야 한다
            - 값 타입의 실제 인스턴스를 공유하는 대신 값을 복사해서 사용한다
        - 값 타입은 인스턴스가 달라도 그 안에 값이 같으면 같은 것으로 봐야 한다
            - 동일성 Identity 비교 : 인스턴스의 참조 값을 비교 / == 사용
            - 동등성 Equivalence 비교 : 인스턴스의 값을 비교 / equals() 사용 / 재정의 필요
            1. 기본 값 타입
                - Java 기본 타입 Primitive Type : int, double
                - Wrapper Class : Integer, Long
                - String
                - 생명 주기를 Entity에 의존한다
                - 절대로 공유하면 안된다
                - 참고 : Java의 기본 타입은 항상 값을 복사하기 때문에 절대 공유되지 않는다
            2. 임베디드 타입 Embedded Type
                - 새로운 값 타입을 직접 정의할 수 있다
                - 주로 기본 값 타입을 모아서 만들기 때문에 복합 값 타입이라고도 한다
                - 임베디드 타입 또한 값 타입을 소유한 Entity에 생명 주기를 의존한다
                - 임베디드 타입을 사용하기 전과 후의 테이블은 같다
                - 객체와 테이블을 아주 세밀하게 Mapping 하는 것이 가능하다
                - 임베디드 타입과 같은 값 타입을 여러 Entity에서 공유하면 위험하다
                    - 한계 : 객체 타입에서 참조 값을 직접 대입하는 것을 막을 수 있는 방법이 없다
                    - 예방 : 객체 타입을 수정할 수 없도록 불변 객체로 생성하여 부작용을 원천 차단한다
                        - 생성자로만 값을 설정할 수 있으며 setter와 같은 수정자를 만들지 않는다
                        - 객체를 새로 만들어서 통째로 바꾼다
            3. 컬렉션 값 타입 Collection Value Type
     */

    @Column(length = 10)
    private String city;
    @Column(length = 20)
    private String street;
    @Column(length = 5)
    private String zipcode;

    public Address() {
    }

    public Address(final String city, final String street, final String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }

    public String fullAddress() {
        return getCity() + " " + getStreet() + " " + getZipcode();
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public String getZipcode() {
        return zipcode;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Address address = (Address) o;
        return Objects.equals(getCity(), address.getCity()) && Objects.equals(getStreet(), address.getStreet()) && Objects.equals(getZipcode(), address.getZipcode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCity(), getStreet(), getZipcode());
    }
}
