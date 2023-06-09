package jpabasic.sample;

import javax.persistence.Entity;

//@Entity
public class Book extends Item {
    /*
    관계형 데이터베이스는 상속 관계라는 것이 없다
    하지만 super type / sub type 관계를 갖는 논리 모델링 기법이 객체의 상속과 유사하다
    이를 물리적으로 구현할 수 있는 세 가지 방법이 있다
        1. Joined strategy // 비즈니스적으로 중요하고 복잡한 경우 많이 사용
        2. Single Table strategy // 단순한 경우 많이 사용
        3. Table per Class strategy
    즉, 상속 관계 Mapping이란 객체의 상속 관계와 DB의 super/sub 관계를 Mapping한다고 생각하면 된다
     */

    private String author;
    private String isbn;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(final String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(final String isbn) {
        this.isbn = isbn;
    }

}
