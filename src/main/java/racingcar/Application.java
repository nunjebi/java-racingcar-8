package racingcar;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import camp.nextstep.edu.missionutils.Console;
import camp.nextstep.edu.missionutils.Randoms;

public class Application {
    public class RacingConstants {
        private RacingConstants() {
        }

        static final int MOVING_FORWARD = 4;
        static final int MAX_NAME_LENGTH = 5;
        static final int MIN_TRY_COUNT = 1;
        static final int MAX_TRY_COUNT = 30;

        static final String CAR_NAME_EMPTY_ERROR = "공백을 제외한 이름을 입력해야 합니다.";
        static final String DUPLICATE_COMMA_ERROR = "이름 사이에는 연속된 쉼표가 있을 수 없습니다.";
        static final String CAR_NAME_LENGTH_ERROR = "이름은 5자 이하만 가능합니다.";
        static final String CAR_NAME_UNIQUE_ERROR = "중복된 이름이 존재합니다.";
        static final String TRY_COUNT_INVALID_ERROR = "시도할 횟수는 1 이상의 정수로 입력해야 합니다.";
        static final String TRY_COUNT_MAX_ERROR = "시도할 횟수는 최대 30입니다.";
    }

    private static class Racingcar {
        private List<String> carNames;
        private int tryCount;
        private String[] dashScores;

        Racingcar(List<String> carNames, int tryCount) {
            this.carNames = carNames;
            this.tryCount = tryCount;

            dashScores = new String[carNames.size()];
            Arrays.setAll(dashScores, index -> "");
        };

        private void start() {
            System.out.println("\n실행 결과");

            IntStream.range(0, tryCount)
                    .forEach(temp -> {
                        race();
                        printDashScores();
                    });
        }

        private void race() {
            IntStream.range(0, carNames.size())
                    .forEach(carIndex -> {
                        moveCar(carIndex);
                    });
        }

        private void moveCar(int carIndex) {
            int randomValue = Randoms.pickNumberInRange(0, 9);
            if (randomValue >= RacingConstants.MOVING_FORWARD) {
                dashScores[carIndex] += "-";
            }
        }

        private void printDashScores() {
            IntStream.range(0, carNames.size())
                    .forEach(carIndex -> {
                        System.out.println(carNames.get(carIndex) + " : " + dashScores[carIndex]);
                    });
            System.out.println();
        }

        private int getWinnerScore() {
            return Arrays.stream(dashScores)
                    .mapToInt(String::length)
                    .max()
                    .orElse(0);
        }

        private String getWinners(int winnerScore) {
            return IntStream.range(0, dashScores.length)
                    .filter(carIndex -> dashScores[carIndex].length() == winnerScore)
                    .mapToObj(carIndex -> carNames.get(carIndex))
                    .collect(Collectors.joining(", "));
        }

        private void printWinners(String winners) {
            System.out.println("최종 우승자 : " + winners);
        }
    }

    public static void main(String[] args) {
        String inputString = inputCarNames();
        List<String> carNames = parseNames(inputString);

        int tryCount = inputTryCount();

        Racingcar game = new Racingcar(carNames, tryCount);

        game.start();

        int winnerScore = game.getWinnerScore();
        String winners = game.getWinners(winnerScore);

        game.printWinners(winners);
    }

    private static String inputCarNames() {
        System.out.println("경주할 자동차 이름을 입력하세요.(이름은 쉼표(,) 기준으로 구분)");
        String inputString = Console.readLine();

        validateInputCarNames(inputString);

        return inputString;
    }

    static void validateInputCarNames(String inputString) {
        if (inputString.contains(",,")) {
            throw new IllegalArgumentException(RacingConstants.DUPLICATE_COMMA_ERROR);
        }
        if (inputString.isBlank() || inputString.endsWith(",") || inputString.endsWith(" ")) {
            throw new IllegalArgumentException(RacingConstants.CAR_NAME_EMPTY_ERROR);
        }
    }

    private static int inputTryCount() {
        System.out.println("시도할 횟수는 몇 회인가요?");
        String tryCountString = Console.readLine();

        validateInputTryCount(tryCountString);

        return Integer.parseInt(tryCountString);
    }

    static void validateInputTryCount(String tryCountString) {
        try {
            int tryCount = Integer.parseInt(tryCountString);
            if (tryCount < RacingConstants.MIN_TRY_COUNT) {
                throw new IllegalArgumentException(RacingConstants.TRY_COUNT_INVALID_ERROR);
            } else if (tryCount > RacingConstants.MAX_TRY_COUNT) {
                throw new IllegalArgumentException(RacingConstants.TRY_COUNT_MAX_ERROR);
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(RacingConstants.TRY_COUNT_INVALID_ERROR);
        }
    }

    private static List<String> parseNames(String inputString) {
        List<String> names = Arrays.stream(inputString.split(","))
                .map(String::trim)
                .toList();

        validateCarNames(names);

        return names;
    }

    static void validateCarNames(List<String> names) {
        Set<String> uniqueNames = new HashSet<>(names);
        if (uniqueNames.size() != names.size()) {
            throw new IllegalArgumentException(RacingConstants.CAR_NAME_UNIQUE_ERROR);
        }

        int maxLength = names.stream()
                .mapToInt(String::length)
                .max()
                .orElse(0);
        if (maxLength > RacingConstants.MAX_NAME_LENGTH) {
            throw new IllegalArgumentException(RacingConstants.CAR_NAME_LENGTH_ERROR);
        }

    }
}