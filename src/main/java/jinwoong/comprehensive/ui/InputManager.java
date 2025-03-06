package jinwoong.comprehensive.ui;

import java.util.Arrays;
import java.util.Scanner;

public class InputManager {
    final Scanner scanner;

    public InputManager() {
        scanner = new Scanner(System.in);
    }

    int getInputByInt(String inputMessage) {
        System.out.print(inputMessage);

        int input = scanner.nextInt();
        scanner.nextLine();

        return input;
    }

    int getInputByInt(String message, String inputMessage) {
        System.out.println(message);
        return getInputByInt(inputMessage);
    }

    String getInputByString(String inputMessage) {
        System.out.print(inputMessage);
        return scanner.nextLine();
    }

    String getInputByString(String message, String inputMessage) {
        System.out.println(message);
        return getInputByString(inputMessage);
    }

    <T> String showElements(T[] candidates) {
        StringBuilder sb = new StringBuilder();
        Arrays.stream(candidates)
                .forEach(x -> sb.append(x.toString()).append(System.lineSeparator()));
        return sb.toString().trim();
    }
}
