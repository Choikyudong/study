package isp;

public class HumanWorker implements Workable, Eatable {
	@Override
	public void work() {
		System.out.println("일한다");
	}

	@Override
	public void eat() {
		System.out.println("밥 먹는다.");
	}
}
