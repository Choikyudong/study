package ocp;

import java.io.BufferedReader;
import java.util.*;

public class Library implements BaseLibaray {

	private final Map<String, Book> books = new HashMap<>();
	private final BookAppConsole bookAppConsole;

	public Library(BufferedReader br) {
		this.bookAppConsole = new BookAppConsole(br);
	}

	@Override
	public void borrowBook() {
		System.out.println("책 빌리기 메뉴");
		while (true) {
			try {
				int result = bookAppConsole.inputCommand("빌리기");
				if (result != -1) {
					if (result == 0) {
						System.out.println("뒤로 이동합니다.");
						break;
					}
					
					String bookName = bookAppConsole.inputBookName();
					if (!books.containsKey(bookName)) {
						System.out.println("해당 책은 없습니다.");
						System.out.println("다른 책을 빌려주세요.");
						continue;
					}

					Book book = books.get(bookName);
					book.borrowBook();
					System.out.printf("%s 을 대여완료 했습니다.", bookName);
					books.put(bookName, new Book(bookName, book.getCount() - 1));
				}
			} catch (InputMismatchException i) {
				System.err.println("지정된 명령어를 입력해주세요." + i.getMessage());
			} catch (Exception e) {
				System.err.println("오류가 발생하였습니다. " + e.getMessage());
			}
		}
	}

	@Override
	public void returnBook() {
		System.out.println("책 반납 메뉴");
		while (true) {
			try {
				int result = bookAppConsole.inputCommand("반납하기");
				if (result != -1) {
					if (result == 0) {
						System.out.println("뒤로 이동합니다.");
						break;
					}

					String bookName = bookAppConsole.inputBookName();
					if (!books.containsKey(bookName)) {
						books.put(bookName, new Book(bookName, 1));
						System.out.println("반납 되었습니다.");
						break;
					}

					Book book = books.get(bookName);
					book.returnBook();
				}
			} catch (InputMismatchException i) {
				System.err.println("지정된 명령어를 입력해주세요." + i.getMessage());
			} catch (Exception e) {
				System.err.println("오류가 발생하였습니다." + e.getMessage());
			}
		}
	}

}
