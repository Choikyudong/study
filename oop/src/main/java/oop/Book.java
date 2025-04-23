package oop;

public class Book {

	private final String name;

	private int count;

	public Book(String name, int count) {
		this.name = name;
		this.count = count;
	}

	public String getName() {
		return name;
	}

	public int getCount() {
		return count;
	}

	public void borrowBook() {
		if (this.count == 0) {
			System.out.println("해당 책의 갯수는 0입니다.");
			System.out.println("다른 책을 빌려주세요.");
			return;
		}
		this.count--;
	}

	public void returnBook() {
		System.out.println("반납 되었습니다.");
		this.count++;
	}

}
