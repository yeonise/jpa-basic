## 영속성 컨텍스트

- *"Entity를 영구적으로 저장하는 환경"*

- Entity Manager를 통해 영속성 컨텍스트에 접근한다

<br/>

### Entity Lifecycle

- 비영속 (new/transient) :
영속성 컨텍스트와 전혀 관계가 없는 새로운 상태

``` java
// 객체를 생성한 상태 : 비영속
Member member = new Member();
member.setId(1L);
member.setName("new member");
```

<br/>

- 영속 (managed) :
영속성 컨텍스트에 의해 관리되는 상태

``` java
EntityManager manager = factory.createEntityManager();
manager.getTransaction().begin();

// 객체를 저장한 상태 : 영속
manager.persist(member);
```

<br/>

- 준영속 (detached) :
영속성 컨텍스트에 저장되었다가 분리된 상태

``` java
// 영속성 컨텍스트에서 분리 : 준영속
manager.detach(member);
```

<br/>

- 삭제 (removed) :
삭제된 상태

``` java
// 객체를 삭제한 상태 : 삭제
manager.remove(member);
```

<br/>

### 연습용 JpaApplication 생성

``` java
public class JpaApplication {

	public static void main(String[] args) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("hello");
		EntityManager manager = factory.createEntityManager();

		EntityTransaction transaction = manager.getTransaction();
		transaction.begin(); // 단순 조회를 제외한 JPA의 모든 변경은 transaction 안에서 실행

		try {
			/*
			JPA를 통해서 Entity를 가져오면 JPA가 관리하기 시작한다
			변경된 부분이 있는지 transaction을 commit하는 시점에 전부 확인한다
			변경된 부분이 있다면 Update Query를 만들어서 실행한다
			그리고 transaction을 commit한다
			*/

			transaction.commit();
		} catch (Exception e) {
			transaction.rollback(); // 문제가 발생했을 경우에 rollback
		} finally {
			manager.close(); // entity manager가 내부적으로 하나의 connection을 갖고 동작하기 때문에 반드시 닫아주기
		}
		factory.close();
	}
	/*
	JPQL : 객체 지향 SQL
	영속성 컨텍스트 : 엔티티를 영구적으로 저장하는 환경으로 J2SE 환경에서는 엔티티 매니저 : 영속성 컨텍스트 = 1 : 1
	동일성 identity 보장
	1차 캐시 : 하나의 트랜잭션 안에서 공유
	- 요청이 들어와서 비즈니스 로직이 끝나면 영속성 컨텍스트를 지운다 이때 1차 캐시도 함께 날라간다 따라서 성능에 큰 이점은 없다
	- persist : 1차 캐시에 저장
	- persist를 통해 1차 캐시에 저장하고 find하면 insert query를 실행하지 않고 1차 캐시에서 가져온다
	2차 캐시 : 애플리케이션 전체에서 공유하는 캐시
	쓰기 지연 SQL 저장소 transactional write-behind : transaction을 commit하는 순간 flush되면서 query를 날리고 실제 database에 commit한다
	변경 감지 Dirty Checking : 최초로 영속성 컨텍스트에 들어왔을 때의 상태를 snapshot으로 저장해서 갖고 있는다
	- commit 직전에 Entity와 snapshot을 비교한 후 변경된 부분을 반영하기 위한 Update query를 쓰기 지연 SQL 저장소에 만들어 넣는다
	Flush : 영속성 컨텍스트의 변경 내용을 database에 반영한다
	- 영속성 컨덱스트를 비우는 것이 아니라 변경 내용을 동기화하는 것
	- 영속성 컨텍스트를 flush하는 방법
		1. entityManager.flush() - 직접 호출
		2. transaction.commit() - flush 자동 호출
		3. JPQL Query 실행 - flush 자동 호출
	+ 질문 : 왜 비영속이 아닌 준영속 상태라고 부르는가?
	*/

}
```

<br/>

### 영속성 컨텍스트의 특징

- 1차 캐시

``` java
Member member = new Member();
member.setId(1L);
member.setName("new member");

manager.persist(member); // 1차 캐시에 저장

Member findMember = manager.find(Member.class, 1L); // 1차 캐시에서 조회
```

- 동일성 보장

``` java
Member memberA = manager.find(Member.class, 1L);
Member memberB = manager.find(Member.class, 1L);

System.out.println(memberA == memeberB); // true
```

- 트랜잭션을 지원하는 쓰기 지연 (Transactional Write-Behind) : transaction을 commit하는 순간 flush되면서 query를 날리고 실제 database에 commit한다

- 변경 감지 (Dirty Checking) :최초로 영속성 컨텍스트에 들어왔을 때의 상태를 snapshot으로 저장해서 갖고 있는다  
commit 직전에 Entity와 snapshot을 비교한 후 변경된 부분을 반영하기 위한 Update query를 쓰기 지연 SQL 저장소에 만들어 넣는다

- 지연 로딩 (Lazy Loading)
