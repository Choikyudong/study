package srp;

public class BankAccountService {

	private final BankUserService bankUserService;

	public BankAccountService(BankUserService bankUserService) {
		this.bankUserService = bankUserService;
	}

	/* SRP를 위반함
	public void signUpBankUser() {
		// 사용자 회원가입
	}
	*/

	public void BankAccountCreate() {
		boolean isNewUser = true;
		if (isNewUser) { // 신규 유저일 경우
			bankUserService.signUpBankUser();
		}
	}

}
