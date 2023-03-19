# Java11

## 람다<br>
### 1-1. 람다란 무엇인가?<br>
람다 표현식은 메서드로 전달할 수 있는 익명 함수를 단순화 한 것이라고 할 수 있다.<br>

### 1-2. 람다의 특징<br>
익명: 보통의 메서드와 달리 이름이 없으므로 익명이라고 표현한다. (구현해야 할 코드에 대한 걱정거리가 줄어든다.)<br>
함수: 람다는 메서드처럼 특정 클래스에 종속되지 않으므로 함수라고 부른다. 하지만 메서드처럼 파라미터 리스트, 바디, 변환 형식, 가능한 예외 리스트를 포함한다.<br>
전달: 람다 표현식으로 메서드 인수로 전달하거나 변수로 저장할 수 있다.<br>
간결성: 익명 클래스처럼 많은 자질 구레한 코드를 구현할 필요가 없다.<br>

### 1-3. 람다의 표현식<br>
(Test t1, Test t2) -> t1.getLength().compareTo(t2.getLength());<br>
파라미터 리스트 : Test의 t1,t2객체 메서드 파라미터.<br>
화살표 : 화살표 (->)는 람다의 파라미터 리스트와 바디를 구분한다.<br>
람다 바디 : 두 객체의 길이를 비교한다. 람다의 반환값에 해당<br>

### 1-4. 람다는 어떻게 사용할까?<br>
함수형 인터페이스라는 문맥에서 람다 표현식을 사용할 수 있다.<br>

### 2-1. 함수형 인터페이스란?<br>
하나의 추상 메서드를 지정하는 인터페이스를 뜻한다.<br>
예시로 Java API의 함수형 인터페이스로 Comparator,Runnable 등이 있다.<br>
람다 표현식으로 함수형 인터페이스의 추상 메서드를 구현을 직접 전달할 수 있으므로 전체 표현식을 함수형 인터페이스의 인스턴스로 취급할 수 있습니다.<br>

### 2-2. @FunctionalInterface<br>
@FunctionalInterface 어노테이션을 통하여 해당 인터페이스가 함수형 인터페이스임을 가리킬 수 있으며 실제로 추상 메서드가 한개 이상일 경우 에러를 발생시킵니다.<br>

### 2-3. 함수 디스크립터란?
람다 표현식의 시그니처를 서술하는 메서드를 함수 디스크립터라고 칭합니다.
예를 들어 Runnable의 유일한 추상 메서드 run은 인수와 반환값이 없으므로 이를 시그니처로 생각 할 수 있습니다.

() -> void

### 2-4. Functions Framework
Java에서는 기본적으로 여러 시그니처 유형에 함수형 인터페이스를 제공해줍니다.

- Predicate
(T) -> Boolean

@FunctionalInterface
public interface Predicate<T> {
	boolean test(T t);
}

public <T> List<T> filter(List<T> list, Predicate<T> p) {
    List<T> results = new ArrayList<>();
    for (T t : list) {
        if (p.test(t))
          resuls.add(t);
    }
    return results;
}

Predicate<String> nonEmptyStringPredicate = (String s) -> !s.isEmpty();
List<String> nonEmpty = filter(listsOfStrings, nonEmptyStringPredicate);

- Consumer
(T) -> void
@FunctionalInterface
public interface Consumer<T> {
	void accept(T t);
}

public void <T> void forEach(List<T> list, Consumer<T> c) {
    for (T t : list) {
        c.accept(t);
    }
}

forEach(
  Arrays.asList(1,2,3,4,5),
  (Integer i) -> System.out.println(i); // <- Consumer의 accept 메서드를 구현하는 람다
);

- Function
(T,R) -> R
@FunctionalInterface
public interface Function<T, R> {
	R apply(T t);
}

public <T, R> List<R> map(List<T> list, Function<T, R> f) {
    List<R> result = new ArrayList<>();
    for (T t : list) {
        result.add(f.apply(t));
    }
    return result;
}

List<Integer> l = map(
    Arrays.asList("lambdas", "in", "action"),
    (String s) -> s.length(); // <- Function의 apply 메서드를 구현하는 람다
);

### 3-1 형식 검사
어떤 컨텍스트에서 기대되는 람다 표현식의 형식을 "대상형식" 이라고 부른다.<br>

List<Apple> heavierThan150gram = 
    filter(inventory, (Apple apple) -> apple.getWeitht() > 150);

두번째 파라미터로 Predicate 형식을 기대하게 됨. ("대상형식")<br>
Predicate은 test라는 한 개의 추상 메서드를 정의하는 함수형 인터페이스<br>
test 메서드는 Apple을 받아 boolean을 반환하는 함수 디스크립터 묘사<br>
filter 메서드로 전달된 인수는 이와 같은 요구사항을 만족해야 한다.<br>

### 3-2 형식 추론
람다 표현식의 파라미터 형식에 접근할 수 있으므로 람다 문법에서 이를 생략할 수 있다.<br>
가독성 향상을 기대함.<br>

Comparator<Apple> c =
        (Apple a1, Apple a2) -> a1.getWeight().compareTo(a2.getWeight());
Comparator<Apple> c =
        (a1, a2) -> a1.getWeight().compareTo(a2.getWeight());
        
### 3-3 지역변수 제약
람다 표현식에서는 익명 함수가 하는 것처럼 지역 변수를 활용할 수 있다.<br>
지역 변수에는 제약이 있는데, final로 선언 또는 final로 선언된 변수와 똑같이 사용되어야 한다.<br>

### 3-4 왜 final으로 변수를 선언해야되는가?
인스턴스 변수는 heap영역, 지역 변수는 stack영역에 위치한다.<br>
람다가 스레드에서 실행된 후, 변수를 할당한 스레드가 사라져 stack 메모리에서 해제되었으나, 람다를 실행하는 스레드는 살아 있어 해당 변수에 접근을 요청할 케이스가 생긴다.<br>
실제 변수에 접근을 허용하는 것이 아닌 해당 변수의 복사본을 만들어 그 곳에 접근을 허용하는데 이를 람다 캡처링 이라고 한다.<br>
복사한 값과 원본의 값이 달라지면 안되기 때문에, 람다에서 접근하는 지역 변수는 final로 선언되어야 한다.<br>

### 3-5 메소드 참조
메서드 참조로 가독성을 높일수 있습니다.<br>

### 3-6 메소드 참조를 만드는 방법
정적 메서드 참조<br>
(args) -> ClassName.staticMethod(args)<br>
ClassName::staticMethod<br>

다양한 형식의 인스턴스 메서드 참조<br>
(arg0, rest) -> arg0.instanceMethod(rest)<br>
ClassName::instanceMethod<br>

기존 객체의 인스턴스 메서드 참조<br>
Transaction 객체를 할당받은 expensiveTranaction 지역 변수가 있고, Tranaction 객체에는 getValue 메서드가 있다면, 이를 expensiveTransaction::getValue 라고 표현할 수 있다.<br>
(args) -> expr.instanceMethod(args)<br>
expr::instanceMethod<br>

### 3장 마무리
람자 표현식은 익명 함수의 일종이며 간결한 코드 구현 가능<br>
함수형 인터페이스는 하나의 추상 메서드만을 정의하는 인터페이스로 기대하는 곳에서만 람다 표현식 사용 가능<br>
람다 표현식을 이용해 함수형 인터페이스의 추상 메서드를 즉석으로 제공할 수 있으며, 람다 표현식 전체가 함수형 인터페이스의인터페이스로 취급됨<br>
실행 어라운드 패턴을 람다와 활용하면 유연성과 재사용성을 추가로 얻을 수 있음<br>
람다 표현식의 기대 형식을 대상 형식 이라고 함<br>
메서드 참조를 이용하여 기존 메서드 구현 재사용 및 직접 전달 가능<br>
Comparator, Predicate, Function 같은 함수형 인터페이스는 람다 표현식을 조합할 수 있는 다양한 디폴트 메서드 제공<br>
