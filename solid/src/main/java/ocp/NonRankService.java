package ocp;

public class NonRankService implements DiscountService {

	@Override
	public int discountPrice(int price) {
		return price;
	}

}
