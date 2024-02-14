package org.example.kiosk.spring.domain.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProductTypeTest {

    @DisplayName("상품 타입이 재고 관련 타입인지를 체크한다.")
    @Test
    void containsStockType() {
        // given
        ProductType givenType = ProductType.HANDMADE;

        // when
        boolean result = ProductType.containsStockType(givenType);

        // then
        assertThat(result).isFalse();
    }

    @DisplayName("상품 타입이 재고 관련 타입인지를 체크한다.")
    @Test
    void containsStockType2() {
        // given
        ProductType givenType = ProductType.BAKERY;

        // when
        boolean result = ProductType.containsStockType(givenType);

        // then
        assertThat(result).isTrue();
    }

    /**
     * 좋지 못한 작성 예시.
     * - 한 문단에 한 주제로 테스트 케이스를 작성하는 것이 좋다.
     *   분기문과 반복문이 들어간 코드는 한 문단에 한 주제가 아니라는 반증이기도 하다.
     *   또한 테스트 코드를 읽는 개발자가 한 번 더 논리 구조를 파악해야하므로 좋지 못한 예시라고 볼 수 있다.
     */
    @DisplayName("상품 타입이 재고 관련 타입인지를 체크한다.")
    @Test
    void badCase() {
        // given
        ProductType[] productTypes = ProductType.values();

        for (ProductType productType : productTypes) {
            if (productType == productType.HANDMADE) {
                // when
                boolean result = ProductType.containsStockType(productType);

                // then
                assertThat(result).isFalse();
            }

            if (productType == productType.BAKERY || productType == productType.BOTTLE) {
                // when
                boolean result = ProductType.containsStockType(productType);

                // then
                assertThat(result).isTrue();
            }
        }
    }

}