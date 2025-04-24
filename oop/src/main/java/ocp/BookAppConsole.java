package ocp;

import java.io.BufferedReader;
import java.io.IOException;

public class BookAppConsole {

	private final BufferedReader br;

	public BookAppConsole(BufferedReader br) {
		this.br = br;
	}

	private int inputAction(String action) throws IOException {
		System.out.println("커맨드를 입력해주세요.");
		System.out.printf("1 : %s, 0 : 뒤로가기\n", action);
		return Integer.parseInt(this.br.readLine());
	}

	public int inputCommand(String action) {
		try {
			return inputAction(action);
		} catch (IOException i) {
			System.err.println("지정된 명령어를 입력해주세요." + i.getMessage());
			return -1;
		}
	}

	public String inputBookName() throws IOException {
		System.out.println("책 이름을 입력해주세요.");
		return br.readLine();
	}

}
