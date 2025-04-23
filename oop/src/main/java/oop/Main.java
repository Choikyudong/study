package oop;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {

	public static void main(String[] args) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		Library library = new Library(br);
		while (true) {
			try {
				System.out.println("커맨드를 입력해주세요.");
				System.out.println("1 : 책 빌리기, 2 : 책 반납, 0 : 종료");
				int command = Integer.parseInt(br.readLine());
				switch (command) {
					case 1 -> library.borrowBook();
					case 2 -> library.returnBook();
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

}
