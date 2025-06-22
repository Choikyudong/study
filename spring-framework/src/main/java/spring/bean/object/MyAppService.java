package spring.bean.object;

public class MyAppService {

	private final StringClass stringClass;

	public MyAppService(StringClass stringClass) {
		this.stringClass = stringClass;
	}

	public void printStrByNum(int num) {
		System.out.println(stringClass.returnStr(num));
	}

}
