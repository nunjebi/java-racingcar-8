package racingcar;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import camp.nextstep.edu.missionutils.Console;
import camp.nextstep.edu.missionutils.Randoms;

public class Application {
    public static void main(String[] args) {
        System.out.println("경주할 자동차 이름을 입력하세요.(이름은 쉼표(,) 기준으로 구분)");
        String inputCarsName = Console.readLine();

        System.out.println("시도할 횟수는 몇 회인가요?");
        int tryCount = Integer.parseInt(Console.readLine());

        List<String> names = Arrays.stream(inputCarsName.split(","))
                .map(String::trim)
                .toList();

        String[] dashScores = new String[names.size()];
        Arrays.fill(dashScores, "");

        IntStream.range(0, tryCount)
                .forEach(stage -> {
                    IntStream.range(0, names.size())
                            .forEach(personIdx -> {
                                int randomValue = Randoms.pickNumberInRange(0, 9);
                                if (randomValue >= 4) {
                                    dashScores[personIdx] += "-";
                                }
                                System.out.println(names.get(personIdx) + " : " + dashScores[personIdx]);
                            });

                });
    }
}
