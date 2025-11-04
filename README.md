### Repository의 문제점

- 현재 Repository는 **두가지 역할**을 가지고 있다.
    1. 사용자 데이터를 저장 및 조회
    2. 사용자 로직 처리 및 ID 생성
    
    → 하나의 클래스가 **두가지 이상의 역할을 수행하고 있으므로** SRP원칙에 어긋난다.
    
- Service 계층에서 구현된 **Repository를 직접 의존하고 있어서** OCP에도 위반됨.

### 인터페이스 & ID생성 책임 분리

> **인터페이스**
> 

userRepository를 인터페이스로 분리하고 InMemoryUserRepository로 구현체를 형성하였다. 

- **인터페이스를 사용하는 이유**
    - **책임 분리(SRP 적용)**
        - 구현체는 인터페이스에서 가이드한 메서드를 구현하기만 하면됨.
    - **구현체의 확장성(OCP 적용 )**
        - 인터페이스를 기반으로 구현체를 자유롭게 교체 가능
        - **기존 코드를 수정하지 않고** 새로운 클래스만 추가 → 확장에 열려있다.
    
    서비스계층에서는 구현체를 **직접** 상속하면안된다.
    

- 서비스 계층(UserService)은 인터페이스 레포지토리(UserRepository)에만 의존하도록 구성하여 **구현체를 변경하더라도 코드 변경없이 확장이 가능하도록 개선하였다.** →OCP
- 스프링이 실행될 때,
    - `@Repository`가 붙은 구현체 중 **UserRepository를 구현한 클래스**를 찾아서
    - 생성자 파라미터로 **자동 주입(DI, Dependency Injection)** 해줌

> **ID 생성 책임 분리**
> 
- 기존에는 `UserRepository` 내부에서 `AtomicLong`을 직접 관리했음
    
    → **ID 생성 책임을 Repository로부터 분리**
    
- 별도의 `UserIdGenerator` 클래스로 ID 생성 책임을 이전함
- **싱글톤(Singleton)** 패턴을 적용하여 애플리케이션 전역에서 하나의 인스턴스가 ID를 관리하도록 함
    - Id는 post, like, user, comment에서 각자 유일한 id(식별자)로 사용되어야 되기 때문에 각각의 IdGenerator를 싱글톤으로 구현하였다.
- **중복 코드가 나올 것을 염려하고도 싱글톤으로 Id생성자를 구현한 이유**
    - Id값은 유일해야한다. → 싱글톤
    - 싱글톤은 인스턴스의 객체가 하나로 보장되기 때문에, 마지막으로 생성되었던 Index의 값을 유지하기때문에 싱글톤을 채택하였다.
    

### 리팩토링 전 코드

```jsx
public class UserRepository {
		private AtomicLong index = new AtomicLong(0);
		private Map<Long, User> Users = new ConcurrentHashMap<>();
		
		public User addUser(UserSignupFormDto userSignupFormDto) {
		    User user = new User();
		    user.setUserId(index.getAndIncrement());
		    user.setPassword(userSignupFormDto.getPassword());
		    user.setUsername(userSignupFormDto.getUsername());
		    user.setEmail(userSignupFormDto.getEmail());
		    Users.put(user.getUserId(), user);
		    return user;
		}

```

### 리팩토링 후 코드

interface UserRepository

```jsx
public interface UserRepository {
    User addUser(UserSignupFormDto userSignupFormDto);
    User updateUser(User user, UserSignupFormDto userSignupFormDto);
    Optional<User> getUser(long id);
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
    Optional<User> deleteUser(long id);
}
```

**Singleton UserIdGenerator** 

```jsx

public class UserIdGenerator {
    private static final UserIdGenerator INSTANCE = new UserIdGenerator();
    private final AtomicLong index = new AtomicLong(0);

    private UserIdGenerator() {}
    
    public static UserIdGenerator getInstance() {
        return INSTANCE;
    }

    public long nextId() {
        return index.getAndIncrement();
    }

}
```

**InMemoryUserRepository 클래스 내부** - Id 생성 책임 분리

```jsx

    public User addUser(UserSignupFormDto userSignupFormDto) {
        User user = new User();

        **user.setUserId(UserIdGenerator.getInstance().nextId());**
        user.setPassword(userSignupFormDto.getPassword());
        user.setUsername(userSignupFormDto.getUsername());
        user.setEmail(userSignupFormDto.getEmail());
        Users.put(user.getUserId(), user);
        return user;
    }
```
