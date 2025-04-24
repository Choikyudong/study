package srp;

public class BankCardService {

	private final BankUserService bankUserService;

	public BankCardService(BankUserService bankUserService) {
		this.bankUserService = bankUserService;
	}

	public void BankCardCreate() {
		boolean isNewUser = true;
		if (isNewUser) { // 신규 유저일 경우
			bankUserService.signUpBankUser();
		}
	}

}
