package lsp;

public class Penguin implements Animal {
	public void swim() {
		System.out.println("펭귄 수영한다.");
	}

	@Override
	public void eat() {
		System.out.println("펭귄 밥먹음");
	}
}
