package jpabasic.sample;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

//@Entity
//@Table(name = "ORDERS")
public class Order extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "ORDER_ID")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;
    /*
    객체 관점에서 양방향 관계보다 단방향 관계가 좋다
        - 가능하면 모두 단방향 관계가 되도록 설계를 잘하는 것이 중요하다
        - 실무에서 개발하거나 JPQL을 사용하면 양방향으로 탐색할 일이 많아지기도 한다
        - 단방향 관계로 설계하고 양방향은 필요할 때 추가한다
    연관 관계의 주인은 외래키를 갖고 있는 테이블을 참고한다
        - 주인이 아닌 쪽은 읽기만 가능하다
        - 주인만 등록하거나 수정하는 등 외래키를 관리할 수 있다
        - 순수 객체 상태를 고려하면 항상 양쪽에 값을 설정하는 것이 맞다
        - 까먹을 수 있기 때문에 연관 관계 편의 메서드를 만드는 것을 추천한다
        - persist 되기 전 참조되지 않는 점을 주의한다
        - 양방향 관계에서 무한 루프에 주의한다 / toString()
    실제 데이터베이스 테이블은 양방향 관계를 갖는 것이 아니라 방향이 없다고 보는 것이 정확하다
    객체의 양방향 관계 또한 사실은 서로 다른 단방향 관계 2개가 존재하는 것을 말한다
     */
    private LocalDateTime orderDate;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    @OneToOne
    @JoinColumn(name = "DELIVERY_ID")
    private Delivery delivery;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(final LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(final OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(final Member member) {
        this.member = member;
    }

}
