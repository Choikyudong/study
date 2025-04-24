package ocp;

public class GoldRankService implements DiscountService {
	@Override
	public int discountPrice(int price) {
		return price - 5000;
	}
}
