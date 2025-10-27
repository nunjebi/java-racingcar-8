package racingcar;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import camp.nextstep.edu.missionutils.Console;
import camp.nextstep.edu.missionutils.Randoms;

public class Application {
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
            if (randomValue >= 4) {
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
        if (inputString.isBlank() || inputString.endsWith(",") || inputString.endsWith(" ")) {
            throw new IllegalArgumentException("공백을 제외한 이름을 입력해야 합니다.");
        }

        return inputString;
    }

    private static int inputTryCount() {
        System.out.println("시도할 횟수는 몇 회인가요?");
        try {
            int tryCount = Integer.parseInt(Console.readLine());
            if (tryCount <= 0) {
                throw new IllegalArgumentException("시도할 횟수는 1 이상의 정수로 입력해야 합니다.");
            }

            return tryCount;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("시도할 횟수는 1 이상의 정수로 입력해야 합니다.");
        }
    }

    private static List<String> parseNames(String inputString) {
        List<String> names = Arrays.stream(inputString.split(","))
                .map(String::trim)
                .toList();

        validateCarNames(names);

        return names;
    }

    private static void validateCarNames(List<String> names) {
        int maxLength = names.stream()
                .mapToInt(String::length)
                .max()
                .orElse(0);

        if (maxLength > 5) {
            throw new IllegalArgumentException("이름은 5자 이하만 가능합니다.");
        }
    }
}
