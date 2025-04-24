package lsp;

public class Sparrow implements Flyable, Animal {
	@Override
	public void fly() {
		System.out.println("참새가 날아요");
	}

	@Override
	public void eat() {
		System.out.println("참새 밥먹음");
	}
}
