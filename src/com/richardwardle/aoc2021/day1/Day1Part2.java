package com.richardwardle.aoc2021.day1;

import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class Day1Part2 {

    private static final String INPUT_FILE = "day1-input.txt";
    //    private static final String INPUT_FILE = "day1-example.txt";

    private final AtomicInteger result = new AtomicInteger(0);
    private final AtomicInteger windowOne = new AtomicInteger(0);
    private final AtomicInteger windowTwo = new AtomicInteger(0);
    private final AtomicInteger windowThree = new AtomicInteger(0);
    private final AtomicInteger windowFour = new AtomicInteger(0);

    private Integer compareLeft;
    private Integer compareRight;

    private void execute() throws FileNotFoundException {
        try (Scanner scanner = new Scanner(
                Objects.requireNonNull(this.getClass().getResourceAsStream(INPUT_FILE)))) {
            for (int i = 1; scanner.hasNext(); i++) {
                Integer currentValue = Integer.valueOf(scanner.nextLine());

                updateWindow(i, 0, currentValue, windowOne);
                updateWindow(i, 1, currentValue, windowTwo);
                updateWindow(i, 2, currentValue, windowThree);
                updateWindow(i, 3, currentValue, windowFour);

                switch (i % 4) {
                    case 0 -> storeAndResetWindow(windowOne);
                    case 1 -> storeAndResetWindow(windowTwo);
                    case 2 -> storeAndResetWindow(windowThree);
                    case 3 -> storeAndResetWindow(windowFour);
                }

                if (compareLeft != null && compareRight != null) {
                    if (compareLeft.compareTo(compareRight) < 0) {
                        result.incrementAndGet();
                    }
                    compareLeft = compareRight;
                    compareRight = null;
                }
            }
        }

        System.out.println("Total: " + result);
    }

    private void updateWindow(int index, int remainder, Integer currentValue, AtomicInteger window) {
        if (index > remainder && index % 4 != remainder) {
            window.addAndGet(currentValue);
        }
    }

    private void storeAndResetWindow(AtomicInteger window) {
        if (compareLeft == null) {
            compareLeft = window.intValue();
        } else {
            compareRight = window.intValue();
        }
        window.set(0);
    }

    public static void main(String[] args) {
        try {
            new Day1Part2().execute();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
