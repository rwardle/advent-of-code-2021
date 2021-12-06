package com.richardwardle.aoc2021.day4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Predicate;

public class Board {

    private final List<BingoListener> bingoListeners = new ArrayList<>();
    private final Map<String, BoardEntry> entriesByNumber = new HashMap<>();
    private final AtomicBoolean won = new AtomicBoolean(false);

    public void addBingoListener(BingoListener listener) {
        this.bingoListeners.add(listener);
    }

    public void addNumber(String number, int xPosition, int yPosition) {
        BoardEntry entry = new BoardEntry(number, xPosition, yPosition, false);
        entriesByNumber.put(entry.number, entry);
    }

    public void markNumber(String number) {
        BoardEntry entryToMark = entriesByNumber.get(number);
        if (entryToMark == null || entryToMark.marked()) {
            return;
        }

        entriesByNumber.put(entryToMark.number(),
                new BoardEntry(entryToMark.number(), entryToMark.xPosition(), entryToMark.yPosition(), true));

        Predicate<BoardEntry> rowPredicate = e -> e.xPosition() == entryToMark.xPosition();
        Predicate<BoardEntry> columnPredicate = e -> e.yPosition() == entryToMark.yPosition();

        if (isBingo(rowPredicate) || isBingo(columnPredicate)) {
            won.set(true);
            fireBingoEvent(number);
        }
    }

    public int sumUnmarkedNumbers() {
        return entriesByNumber.values().stream()
                .filter(e -> !e.marked)
                .mapToInt(e -> Integer.parseInt(e.number()))
                .sum();
    }

    public boolean hasWon() {
        return won.get();
    }

    private boolean isBingo(Predicate<BoardEntry> boardEntryPredicate) {
        return entriesByNumber.values().stream()
                .filter(boardEntryPredicate)
                .allMatch(BoardEntry::marked);
    }

    private void fireBingoEvent(String number) {
        this.bingoListeners.forEach(l -> l.onBingo(this, number));
    }

    private record BoardEntry(String number, int xPosition, int yPosition, boolean marked) {
    }
}
