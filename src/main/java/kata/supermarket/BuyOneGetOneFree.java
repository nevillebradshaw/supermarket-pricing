package kata.supermarket;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BuyOneGetOneFree implements ItemGroup {
    private static final BigDecimal TWO = BigDecimal.valueOf(2d);

    private List<Item> items;
    private BigDecimal groupPrice;
    private BigDecimal pricePerUnit;

    BuyOneGetOneFree(Product... products) {
        items = Arrays.stream(products)
                .map(p -> new ItemByUnit(p))
                .collect(Collectors.toList());

        pricePerUnit = products.length > 0  ? products[0].pricePerUnit() : BigDecimal.ZERO;

        groupPrice = items.stream().map(Item::price)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    @Override
    public BigDecimal price() {
        return groupPrice.subtract(discount());
    }

    @Override
    public BigDecimal discount() {
        BigDecimal theDiscount;

        if (items.size() % 2 == 0) {
            theDiscount = groupPrice.divide(TWO);
        }
        else {
            theDiscount = groupPrice.subtract(pricePerUnit).divide(TWO);
        }

        return theDiscount;
    }
}
