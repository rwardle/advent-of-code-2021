package com.richardwardle.aoc2021.day1;

import com.richardwardle.aoc2021.input.InputUtils;

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class Day1Part2 {

    private final AtomicInteger result = new AtomicInteger(0);
    private Integer compareLeft;
    private Integer compareRight;

    public static void main(String[] args) {
        try {
            new Day1Part2().execute();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void execute() throws FileNotFoundException {
        try (Scanner scanner = InputUtils.day1Data()) {
            for (int lineIndex = 1; scanner.hasNext(); lineIndex++) {
                Integer currentValue = Integer.valueOf(scanner.nextLine());

                // Update sliding window sums
                for (SlidingWindow slidingWindow : SlidingWindow.values()) {
                    slidingWindow.update(lineIndex, currentValue);
                }

                // Get sum for finished window store for comparison
                int windowSum = SlidingWindow.getSumAndReset(lineIndex);
                if (compareLeft == null) {
                    compareLeft = windowSum;
                } else {
                    compareRight = windowSum;
                }

                // Compare, update result and reset for next comparison
                if (compareRight != null) {
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

    private enum SlidingWindow {
        FIRST, SECOND, THIRD, FOURTH;

        private final AtomicInteger sum = new AtomicInteger(0);

        public static int getSumAndReset(int lineIndex) {
            int remainder = lineIndex % SlidingWindow.values().length;
            SlidingWindow slidingWindow = SlidingWindow.values()[remainder];
            return slidingWindow.sum.getAndSet(0);
        }

        void update(int lineIndex, Integer lineValue) {
            if (lineIndex > this.ordinal() && lineIndex % SlidingWindow.values().length != this.ordinal()) {
                sum.addAndGet(lineValue);
            }
        }
    }
}
