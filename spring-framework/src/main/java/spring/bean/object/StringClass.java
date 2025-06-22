package spring.bean.object;

public class StringClass {

	public String returnStr(int num) {
		if (num == 1) {
			return "Hello World";
		} else {
			System.err.println("num : " + num);
			return "Wrong";
		}
	}

}
