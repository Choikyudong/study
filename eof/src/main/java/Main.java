import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Main {
	public static void main(String[] args) {
		String filePath = System.getProperties().get("user.dir") + File.separator + "eof";
		StringBuilder sb = new StringBuilder();
		try (FileInputStream fis = new FileInputStream(filePath + "/HelloWorld.txt")) {
			while (true) {
				int data = fis.read();
				if (data == -1) {
					break; // read()가 -1이면 EOF
				}
				sb.append((char) data);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(sb);
	}
}
