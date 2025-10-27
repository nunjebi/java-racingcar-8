package racingcar;

import camp.nextstep.edu.missionutils.test.NsTest;
import racingcar.Application.RacingConstants;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static camp.nextstep.edu.missionutils.test.Assertions.assertRandomNumberInRangeTest;
import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;

class ApplicationTest extends NsTest {
    private static final int MOVING_FORWARD = 4;
    private static final int STOP = 3;

    @Test
    void 기능_테스트() {
        assertRandomNumberInRangeTest(
                () -> {
                    run("pobi,woni", "1");
                    assertThat(output()).contains("pobi : -", "woni : ", "최종 우승자 : pobi");
                },
                MOVING_FORWARD, STOP);
    }

    @Test
    void 예외_테스트() {
        assertSimpleTest(() -> assertThatThrownBy(() -> runException("pobi,javaji", "1"))
                .isInstanceOf(IllegalArgumentException.class));
    }

    @ParameterizedTest
    @DisplayName("입력된 자동차 이름에 공백만 있는 경우")
    @ValueSource(strings = { " ", "a,", "a, " })
    void inputCarNames_공백만입력(String input) {
        assertSimpleTest(
                () -> assertThatThrownBy(
                        () -> Application.validateInputCarNames(input))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessageContaining(RacingConstants.CAR_NAME_EMPTY_ERROR));
    }

    @Test
    @DisplayName("입력된 자동차 이름에 연속된 쉼표가 있는 경우")
    void validateInputCarNames() {
        String input = "a,,";

        assertSimpleTest(
                () -> assertThatThrownBy(
                        () -> Application.validateInputCarNames(input))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessageContaining(RacingConstants.DUPLICATE_COMMA_ERROR));
    }

    @Test
    @DisplayName("입력된 자동차 이름에 중복이 있는 경우")
    void validateCarNames_중복이름입력() {
        List<String> input = List.of("aaa", "aaa");

        assertSimpleTest(
                () -> assertThatThrownBy(
                        () -> Application.validateCarNames(input))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessageContaining(RacingConstants.CAR_NAME_UNIQUE_ERROR));

    }

    @Test
    @DisplayName("자동차 이름이 5자를 초과한 경우")
    void inputCarNames_5자초과() {
        List<String> input = List.of("abcdef", "aaa,abcdef");

        assertSimpleTest(
                () -> assertThatThrownBy(
                        () -> Application.validateCarNames(input))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessageContaining(RacingConstants.CAR_NAME_LENGTH_ERROR));
    }

    @Test
    @DisplayName("입력된 시도 횟수가 음수인 경우")
    void validateInputTryCount_음수입력() {
        String input = "-1";

        assertSimpleTest(
                () -> assertThatThrownBy(
                        () -> Application.validateInputTryCount(input))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessageContaining(RacingConstants.TRY_COUNT_INVALID_ERROR));
    }
    
    @Test
    @DisplayName("입력된 시도 횟수가 0인 경우")
    void validateInputTryCount_0입력() {
        String input = "0";

        assertSimpleTest(
                () -> assertThatThrownBy(
                        () -> Application.validateInputTryCount(input))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessageContaining(RacingConstants.TRY_COUNT_INVALID_ERROR));
    }

    @ParameterizedTest
    @DisplayName("입력된 시도 횟수가 문자열인 경우")
    @ValueSource(strings = { " ", "a", "-1-", "-1-1", "-1" })
    void validateInputTryCount_문자열입력(String input) {
        assertSimpleTest(
                () -> assertThatThrownBy(
                        () -> Application.validateInputTryCount(input))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessageContaining(RacingConstants.TRY_COUNT_INVALID_ERROR));
    }

    @Test
    @DisplayName("입력된 시도 횟수가 30 초과인 경우")
    void validateInputTryCount_시도횟수초과() {
        String input = "31";
        assertSimpleTest(
                () -> assertThatThrownBy(
                        () -> Application.validateInputTryCount(input))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessageContaining(RacingConstants.TRY_COUNT_MAX_ERROR));
    }

    @Override
    public void runMain() {
        Application.main(new String[] {});
    }
}
