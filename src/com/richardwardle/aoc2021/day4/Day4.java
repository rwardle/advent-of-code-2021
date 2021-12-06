package com.richardwardle.aoc2021.day4;

import com.richardwardle.aoc2021.input.InputUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Day4 implements BingoListener {

    public static void main(String[] args) {
        new Day4().execute();
    }

    private void execute() {
        List<String> sections = readSections();
        List<String> numbers = parseNumbers(sections.remove(0));
        List<Board> boards = parseBoards(sections.toArray(new String[0]));
        markBoards(numbers, boards);
    }

    private List<String> readSections() {
        List<String> sections = new ArrayList<>();
        try (Scanner scanner = InputUtils.day4Data()) {
            scanner.useDelimiter("\n\n");
            while (scanner.hasNext()) {
                String data = scanner.next();
                if (!data.isEmpty()) {
                    sections.add(data);
                }
            }
        }

        return sections;
    }

    private List<String> parseNumbers(String numbersSection) {
        return Arrays.asList(numbersSection.split(","));
    }

    private List<Board> parseBoards(String[] boardSections) {
        List<Board> boards = new ArrayList<>();

        for (String boardSection : boardSections) {
            Board board = new Board();
            board.addBingoListener(this);
            boards.add(board);

            String[] lines = boardSection.lines().map(String::strip).toArray(String[]::new);
            for (int y = 0; y < lines.length; y++) {
                String[] numbers = lines[y].split("\s+");
                for (int x = 0; x < numbers.length; x++) {
                    board.addNumber(numbers[x], x, y);
                }
            }
        }

        return boards;
    }

    private void markBoards(List<String> numbers, List<Board> boards) {
        for (String number : numbers) {
            for (Board board : boards) {
                if (!board.hasWon()) {
                    board.markNumber(number);
                }
            }
        }
    }

    @Override
    public void onBingo(Board board, String number) {
        int sumUnmarkedNumbers = board.sumUnmarkedNumbers();
        int result = sumUnmarkedNumbers * Integer.parseInt(number);
        System.out.println("Result: " + result);
    }
}
