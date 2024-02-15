package org.example.kiosk.spring.api.service.product;

import lombok.RequiredArgsConstructor;
import org.example.kiosk.spring.api.service.product.request.ProductCreateServiceRequest;
import org.springframework.stereotype.Service;
import org.example.kiosk.spring.api.service.product.response.ProductResponse;
import org.example.kiosk.spring.domain.product.Product;
import org.example.kiosk.spring.domain.product.ProductRepository;
import org.example.kiosk.spring.domain.product.ProductSellingStatus;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductNumberFactory productNumberFactory;

    @Transactional
    public ProductResponse createProduct(ProductCreateServiceRequest request) {
        String nextProductNumber = productNumberFactory.createNextProductNumber();

        Product product = request.toEntity(nextProductNumber);
        Product savedProduct = productRepository.save(product);

        return ProductResponse.of(savedProduct);
    }

    public List<ProductResponse> getSellingProducts() {
        List<Product> products = productRepository.findAllBySellingStatusIn(ProductSellingStatus.forDisplay());

        return products.stream()
                .map(ProductResponse::of)
                .collect(Collectors.toList());
    }

    /**
        - private 메서드의 테스트
          : private 메서드는 테스트가 불필요하다.
            그 이유는 클라이언트 기준에서는 공개된 public 메서드만 알고 있기 때문에 private 메서드까지의 검증은 불필요하다.
            그럼에도 불구하고 테스트가 필요한 경우는 객체를 분리시킬 시점이라는 반증이기도 하다.
     */
//    private String createNextProductNumber() {
//        String latestProductNumber = productRepository.findLatestProductNumber();
//        if (latestProductNumber == null) {
//            return "001";
//        }
//
//        int latestProductNumberInt = Integer.parseInt(latestProductNumber);
//        int nextProductNumberInt = latestProductNumberInt + 1;
//
//        return String.format("%03d", nextProductNumberInt);
//    }

}
