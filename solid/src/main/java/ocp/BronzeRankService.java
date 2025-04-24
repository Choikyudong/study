package ocp;

public class BronzeRankService implements DiscountService {
	@Override
	public int discountPrice(int price) {
		return price - 1000;
	}
}
