import java.util.List;
import java.util.function.Function;

public class Main {

	// 입력이 같으면 항상 같은 출력, 외부 상태 변경이 없다.
	public static int pureFunction(int a, int b) {
		return a + b;
	}

	// 데이터가 한 번 생성되면 변경하지 않으며 값을 바꾸는 대신 새로운 값을 만들어 사용한다.
	public static void immutability() {
		List<Integer> numbers = List.of(1, 2, 3, 4, 5);

		List<Integer> newNumbers = numbers.stream()
				.map(num -> num * 2)
				.toList();

		System.out.println(numbers);
		System.out.println(newNumbers);
	}

	// 변수에 함수를 저장하거나, 인자로 전달, 반환 가능
	public static void firstClassFunction() {
		Function<Integer, Integer> square = x -> x * x;
		int result = applyFunction(5, square);
		System.out.println(result);

		Function<Integer, Integer> applyFunction = addFunction(5);
		System.out.println(applyFunction.apply(5)); // 10
	}

	// 함수를 파라미터(인자)로 전달
	public static int applyFunction(int value, Function<Integer, Integer> function) {
		return function.apply(value);
	}

	// 함수를 반환
	public static Function<Integer, Integer> addFunction(int n) {
		return x -> x + n;
	}

	public static void higherOrderFunction() {
		Function<Integer, Integer> increment = x -> x + 1;
		int result = applyTwice(5, increment);
		System.out.println(result);
	}

	public static int applyTwice(int value, Function<Integer, Integer> function) {
		return function.apply(function.apply(value)); // 함수를 인자로 또 넣음
	}

	public static void main(String[] args) {
		System.out.println(pureFunction(1, 2));
		immutability();
		firstClassFunction();
		higherOrderFunction();
	}

}
