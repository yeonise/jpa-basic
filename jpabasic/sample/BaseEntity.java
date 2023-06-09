package jpabasic.sample;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

//@MappedSuperclass
/*
@MappedSuperclass : 공통 정보가 필요할 때 사용한다
    - 상속 관계와 관련 없다
    - Entity도 아니기 때문에 table로 생성되지 않는다
    - 상속을 받는 자식 클래스에 정보만 제공한다
    - 조회 불가 ex) manager.find(BaseEntity.class) 불가
    - 직접 생성하여 사용할 일이 없으므로 abstract class로 만드는 것을 권장한다
 */
public abstract class BaseEntity {

    private String createdBy;
    private LocalDateTime createdDate;
    private String lastModifiedBy;
    private LocalDateTime lastModifiedDate;

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(final String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(final LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(final String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(final LocalDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

}
