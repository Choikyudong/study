package pp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

	static Map<String, Integer> books = new HashMap<>();
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	public static void main(String[] args) {
		while (true) {
			try {
				System.out.println("커맨드를 입력해주세요.");
				System.out.println("1 : 책 빌리기, 2 : 책 반납, 0 : 종료");
				int command = Integer.parseInt(br.readLine());
				switch (command) {
					case 1 -> borrowBook();
					case 2 -> returnBook();
					case 0 -> {
						System.out.println("종료합니다.");
						System.exit(0);
					}
					default -> System.out.println("지정된 커맨드를 입력해주세요.");
				}
			} catch (Exception e) {
				System.err.println("오류가 발생하였습니다 : " + e.getMessage());
			}
		}
	}

	static void borrowBook() {
		System.out.println("책 빌리기 메뉴");
		while (true) {
			try {
				int command = inputCommnad();
				if (command == 0) {
					System.out.println("뒤로 이동합니다.");
					break;
				}

				if (command != 1) {
					System.out.println("지정된 명령어를 입력해주세요.");
					continue;
				}

				System.out.println("빌리고 싶은 책 이름을 입력해주세요.");
				String bookName = br.readLine();
				if (!books.containsKey(bookName)) {
					System.out.println("해당 책은 없습니다.");
					System.out.println("다른 책을 빌려주세요.");
					continue;
				}

				int bookCount = books.get(bookName);
				if (bookCount == 0) {
					System.out.println("해당 책의 갯수는 0입니다.");
					System.out.println("다른 책을 빌려주세요.");
					continue;
				}
				System.out.printf("%s 을 대여완료 했습니다.", bookName);
				books.put(bookName, bookCount - 1);
			} catch (InputMismatchException i) {
				System.err.println("지정된 명령어를 입력해주세요." + i.getMessage());
			} catch (Exception e) {
				System.err.println("에러 발생으로 프로그램 강제 종료. " + e.getMessage());
			}
		}
	}

	static void returnBook() {
		System.out.println("책 반납 메뉴");
		while (true) {
			try {
				int command = inputCommnad();
				if (command == 0) {
					System.out.println("뒤로 이동합니다.");
					break;
				}

				if (command != 1) {
					System.out.println("지정된 명령어를 입력해주세요.");
					continue;
				}

				System.out.println("반납할 책 이름을 입력해주세요.");
				String bookName = br.readLine();
				if (!books.containsKey(bookName)) {
					books.put(bookName, 1);
					System.out.println("반납 되었습니다.");
					break;
				}
				books.put(bookName, books.get(bookName) + 1);
			} catch (InputMismatchException i) {
				System.err.println("지정된 명령어를 입력해주세요." + i.getMessage());
			} catch (Exception e) {
				System.err.println("에러 발생으로 프로그램 강제 종료. " + e.getMessage());
			}
		}
	}

	static int inputCommnad() throws IOException {
		System.out.println("커맨드를 입력해주세요.");
		System.out.println("1 : 반납하기, 0 : 뒤로가기");
		return Integer.parseInt(br.readLine());
	}

}
