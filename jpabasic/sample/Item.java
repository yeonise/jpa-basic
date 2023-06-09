package jpabasic.sample;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

//@Entity
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
/*
1. Joined strategy
    - 테이블의 정규화
    - 외래키 참조 무결성 제약 조건을 활용할 수 있다
    - 저장 공간을 효율적으로 사용할 수 있다
    - select query가 상대적으로 복잡하다
    - 일반적으로 기본 전략으로 사용한다
2. Single Table strategy
    - join이 필요 없으므로 일반적으로 조회 성능이 빠르다
    - select query가 상대적으로 단순하다
    - 무조건 생성되는 DType으로 구분한다
    - 자식 Entity의 column은 모두 null을 허용한다
3. Table per Class strategy
    - 거의 사용하지 않는다
    - union을 사용하여 모든 테이블을 확인하기 때문에 성능이 느리다
*/
//@DiscriminatorColumn // 일반적으로 사용하는 것이 좋다
public abstract class Item extends BaseEntity { // @Entity 클래스는 @Entity나 @MappedSuperclass로 지정한 클래스만 상속 가능

    @Id @GeneratedValue
    @Column(name = "ITEM_ID")
    private Long id;
    private String name;
    private int price;
    private int stockQuantity;

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

    public int getPrice() {
        return price;
    }

    public void setPrice(final int price) {
        this.price = price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(final int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

}
