## JPA 설정하기

**Maven** 사용 시  

1. `pom.xml` 에 라이브러리를 추가

``` xml
	<dependencies>
		<!-- JPA Hibernate -->
		<dependency>
			<groupId>org.hibernate</groupId>
			<artifactId>hibernate-entitymanager</artifactId>
			<version>5.6.15.Final</version>
		</dependency>

		<!-- H2 Database -->
		<dependency>
			<groupId>com.h2database</groupId>
			<artifactId>h2</artifactId>
			<version>2.1.212</version>
		</dependency>
	</dependencies>
```

2. `/META-INF/persistence.xml` 에 JPA 관련 설정을 등록

``` xml
<?xml version="1.0" encoding="UTF-8"?>
<persistence version="2.2"
            xmlns="http://xmlns.jcp.org/xml/ns/persistence" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/persistence http://xmlns.jcp.org/xml/ns/persistence/persistence_2_2.xsd">
    <persistence-unit name="hello">
        <properties>
            <!-- 필수 속성 -->
            <property name="javax.persistence.jdbc.driver" value="org.h2.Driver"/>
            <property name="javax.persistence.jdbc.user" value="sa"/>
            <property name="javax.persistence.jdbc.password" value=""/>
            <property name="javax.persistence.jdbc.url" value="jdbc:h2:tcp://localhost/~/test"/>
            <property name="hibernate.dialect" value="org.hibernate.dialect.H2Dialect"/>

            <!-- 옵션 -->
            <property name="hibernate.show_sql" value="true"/>
            <property name="hibernate.format_sql" value="true"/>
            <property name="hibernate.use_sql_comments" value="true"/>
            <property name="hibernate.jdbc.batch_size" value="10"/>
            <property name="hibernate.hbm2ddl.auto" value="create"/>
        </properties>
    </persistence-unit>
</persistence>
```

- `javax.persistence` 로 시작하는 경우 : JPA 표준 속성

- `hibernate` 로 시작하는 경우 : Hibernate 전용 속성

- `hibernate.dialect` 속성에 데이터베이스 방언을 지정
    - H2 : org.hibernate.dialect.H2Dialect

    - Oracle : org.hibernate.dialect.Oracle10gDialect

    - MySQL : org.hibernate.dialect.MySQL5InnoDBDialect

<br/>

## JPA 작동 방식

``` java
@Entity
public class Member {
    /*
    @Entity가 붙은 클래스는 JPA가 관리한다
    - JPA를 사용해서 테이블과 매핑할 클래스라면 필수적으로 붙여야 한다
    - 기본 생성자를 필수적으로 갖는다 / 단, parameter가 없는 public 또는 protected 생성자
    - final class, enum, interface, inner class 사용 불가
    - 저장할 field에 final 사용 불가
    */

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false) // NOT NULL 제약 조건
    private String name;

    private int age;

    @Enumerated(EnumType.STRING) // enum 이름을 데이터베이스에 저장 vs EnumType.ORDINAL : enum 순서를 데이터베이스에 저장 STRING 사용을 권장한다
    private Role role;

    private LocalDateTime createdAt;

    private LocalDateTime lastModifiedAt;

    @Lob
    private String description;

    public Member() {
        /*
        JPA는 기본적으로 내부적으로 reflection 같은 것들을 사용하기 때문에 동적으로 객체를 생성한다
        그래서 필수적으로 기본 생성자가 있어야 한다
        */
    }

}
```

``` java
public class JpaApplication {

	public static void main(String[] args) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("hello");
		EntityManager manager = factory.createEntityManager();

		EntityTransaction transaction = manager.getTransaction();
		transaction.begin(); // 단순 조회를 제외한 JPA의 모든 변경은 transaction 안에서 실행

		try {
			/*
			1. JPA를 통해서 Entity를 가져오면 JPA가 관리하기 시작한다
			2. 변경된 부분이 있는지 transaction을 commit하는 시점에 전부 확인한다
			3. 변경된 부분이 있다면 Update Query를 만들어서 실행한다
			4. 그리고 transaction을 commit한다
			*/

			transaction.commit();
		} catch (Exception e) {
			transaction.rollback(); // 문제가 발생했을 경우에 rollback
		} finally {
			manager.close(); // entity manager가 내부적으로 하나의 connection을 갖고 동작하기 때문에 반드시 닫아주기
		}
		factory.close();
	}

}
```

- `EntityManagerFactory` 는 하나만 생성해서 애플리케이션 전체에서 공유한다

- `EntityManager` 는 Thread 간에 공유하지 않으며 사용하고 버린다

- **JPA의 모든 데이터 변경은 Transaction 안에서 실행한다**

<br/>

### JPQL

- JPA는 SQL을 추상화한 JPQL이라는 객체 지향 언어를 제공한다

- JPQL은 **Entity** 객체를 대상으로 한다 vs SQL은 데이터베이스 **Table**을 대상으로 한다

- 특정 데이터베이스 SQL에 의존하지 않는다
