package ocp;

public class SilverRankService implements DiscountService {
	@Override
	public int discountPrice(int price) {
		return price - 3000;
	}
}
