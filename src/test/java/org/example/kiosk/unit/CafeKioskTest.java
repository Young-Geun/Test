package org.example.kiosk.unit;

import org.example.kiosk.unit.beverage.Americano;
import org.example.kiosk.unit.beverage.Latte;
import org.example.kiosk.unit.order.Order;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CafeKioskTest {

    @Test
    void add_manual_test() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        cafeKiosk.add(new Americano());

        System.out.println(">>> 담긴 음료 수 : " + cafeKiosk.getBeverages().size());
        System.out.println(">>> 담긴 음료 : " + cafeKiosk.getBeverages().get(0).getName());
    }

    @Test
    // @DisplayName("음료 1개 추가")
    @DisplayName("음료 1개를 추가하면 주문 목록에 담긴다.")
    // 명사의 나열보다 문장으로 작성하면 의도가 명확해진다.
    void add() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        cafeKiosk.add(new Americano());

        assertThat(cafeKiosk.getBeverages()).hasSize(1);
        assertThat(cafeKiosk.getBeverages().get(0).getName()).isEqualTo("아메리카노");
    }

    @Test
    void addSeveral() { // 해피 케이스
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();
        cafeKiosk.add(americano, 2);

        assertThat(cafeKiosk.getBeverages()).hasSize(2);
        assertThat(cafeKiosk.getBeverages().get(0)).isEqualTo(americano);
        assertThat(cafeKiosk.getBeverages().get(1)).isEqualTo(americano);
    }

    @Test
    void addZero() { // 예외 케이스
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();

        assertThatThrownBy(() -> cafeKiosk.add(americano, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("음료는 1잔 이상 주문하실 수 있습니다.");
    }

    @Test
    void addOne() { // 경계값 테스트 [ = 범위(이상, 이하, 초과, 미만), 구간, 날짜 등 ]
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();
        cafeKiosk.add(americano, 1);

        assertThat(cafeKiosk.getBeverages()).hasSize(1);
        assertThat(cafeKiosk.getBeverages().get(0)).isEqualTo(americano);
    }

    @Test
    void remove() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();

        cafeKiosk.add(americano);
        assertThat(cafeKiosk.getBeverages()).hasSize(1);

        cafeKiosk.remove(americano);
        assertThat(cafeKiosk.getBeverages()).isEmpty();
    }

    @Test
    void clear() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();
        Latte latte = new Latte();

        cafeKiosk.add(americano);
        cafeKiosk.add(latte);
        assertThat(cafeKiosk.getBeverages()).hasSize(2);

        cafeKiosk.clear();
        assertThat(cafeKiosk.getBeverages()).isEmpty();
    }

    /**
     * TDD
     * - 테스트코드를 우선시 하는 개발 방법론.
     * - Red(실패) -> Green(성공) -> Refactoring -> Red -> Green .. 순환 형태
     * - 최초에는 테스트 코드가 없으니 Red 상태이고,
     * 이후 calculateTotalPrice()를 어떻게든 성공 상태로 만들기 위해 구현부를 임의로라도 작성한다.
     * Ex) calculateTotalPrice()의 return 값을 8500원으로 하여 무조건 테스트케이스가 성공하록 작성.
     * 그 후에는 calculateTotalPrice()를 정상적인 로직으로 리팩토링 한다.
     * <p>
     * - 장점
     * 1. 복잡도가 낮은, 테스트 가능한 코드를 구현할 수 있게 해준다.
     * 2. 쉽게 발견하기 어려운 엣지케이스를 놓치지 않게 해준다.
     * 3. 구현에 대한 빠른 피드백을 받을 수 있다.
     * 4. 과감한 리팩토링이 가능해진다.
     */
    @Test
    void calculateTotalPrice() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();
        Latte latte = new Latte();

        cafeKiosk.add(americano);
        cafeKiosk.add(latte);

        int totalPrice = cafeKiosk.calculateTotalPrice();
        assertThat(totalPrice).isEqualTo(8500);
    }

    @Test
    @DisplayName("주문 목록에 담긴 상품들의 총 금액을 계산할 수 있다.")
    void calculateTotalPriceWithBDD() {
        // Given : 시나리오 진행에 필요한 모든 준비 과정
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();
        Latte latte = new Latte();

        cafeKiosk.add(americano);
        cafeKiosk.add(latte);

        // When : 시나리오 행동 진행
        int totalPrice = cafeKiosk.calculateTotalPrice();

        // Then : 시나리오 진행에 대한 결과 명시, 검증
        assertThat(totalPrice).isEqualTo(8500);
    }

    /**
     * 주문시간인 경우에만 성공하는 케이스
     */
    @Test
    @Disabled
    void createOrder() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        cafeKiosk.add(new Americano());

        Order order = cafeKiosk.createOrder();
        assertThat(order.getBeverages()).hasSize(1);
        assertThat(order.getBeverages().get(0).getName()).isEqualTo("아메리카노");
    }

    /**
     * 주문시간인 경우에만 성공하는 케이스를 보완하기 위해 createOrder()에 시간을 넣도록 변경하고 그에 대한 검증 케이스
     */
    @Test
    void createOrderWithCurrentTime() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        cafeKiosk.add(new Americano());

        Order order = cafeKiosk.createOrder(LocalDateTime.of(2024, 2, 11, 10, 0));
        assertThat(order.getBeverages()).hasSize(1);
        assertThat(order.getBeverages().get(0).getName()).isEqualTo("아메리카노");
    }

    @Test
    // @DisplayName("특정 시간 이전에 주문을 생성하면 실패한다.")
    @DisplayName("영업 시작 시간 이전에는 주문을 생성할 수 없다.")
    /*
        1. 도메인 용어를 사용하여 한층 추상화된 내용을 담는다.
           Ex) '특정 시간'이라는 단어 대신 '영업 시작 시간'이라는 팀에서 사용하는 비즈니스 용어를 사용하도록 변경했다.
        2. 테스트의 현상을 중점으로 기술하지 않는다.
           Ex) '실패한다'라는 테스트 결과 관점의 용어 대신 '주문을 생성할 수 없다.'와 같이 기능 관점 용어를 사용한다.
     */
    void createOrderOutsideOpenTime() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        cafeKiosk.add(new Americano());

        assertThatThrownBy(() -> cafeKiosk.createOrder(LocalDateTime.of(2024, 2, 11, 9, 0)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주문 시간이 아닙니다. 관리자에게 문의하세요.");
    }

}