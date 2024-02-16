package org.example.kiosk.spring.api.controller.product.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.kiosk.spring.api.service.product.request.ProductCreateServiceRequest;
import org.example.kiosk.spring.domain.product.ProductSellingStatus;
import org.example.kiosk.spring.domain.product.ProductType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@NoArgsConstructor
public class ProductCreateRequest {

    @NotNull(message = "상품 타입은 필수입니다.")
    private ProductType type;

    @NotNull(message = "상품 판매상태는 필수입니다.")
    private ProductSellingStatus sellingStatus;

    @NotBlank(message = "상품 이름은 필수입니다.")
    private String name;

    @Positive(message = "상품 가격은 양수여야 합니다.")
    private int price;

    /**
     * Q. 테스트에서만 필요한 메서드가 생겼는데 프로덕션 코드에서는 필요 없다면?
     *
     * - 아래 빌더는 테스트 코드에서만 사용하고 있는 코드이다.
     *   테스트에서만 사용하는 코드를 프로덕트 코드에 만들어도 되는 것인가?
     *   답은 만들어도 된다. 하지만 보수적으로 접근이 필요하다. (= 지양하는 것이 좋다.)
     */
    @Builder
    private ProductCreateRequest(ProductType type, ProductSellingStatus sellingStatus, String name, int price) {
        this.type = type;
        this.sellingStatus = sellingStatus;
        this.name = name;
        this.price = price;
    }

    public ProductCreateServiceRequest toServiceRequest() {
        return ProductCreateServiceRequest.builder()
                .type(type)
                .sellingStatus(sellingStatus)
                .name(name)
                .price(price)
                .build();
    }

}
