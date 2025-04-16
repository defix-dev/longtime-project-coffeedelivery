package defix.coffeedelivery.basket.service;

import defix.coffeedelivery.basket.api.dto.response.BasketDTO;

import java.util.Comparator;

public class BasketIdComparator implements Comparator<BasketDTO> {
    @Override
    public int compare(BasketDTO a, BasketDTO b) {
        return Integer.compare(a.getId(), b.getId());
    }
}
