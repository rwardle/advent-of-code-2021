package com.richardwardle.aoc2021.day10;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

enum Chunk {
    ANGLE('<', '>'), BRACE('{', '}'), ROUND('(', ')'), SQUARE('[', ']');

    private final static Map<Character, Chunk> opening = new HashMap<>();
    private final static Map<Character, Chunk> closing = new HashMap<>();

    static {
        Chunk[] values = Chunk.values();
        for (Chunk chunk : values) {
            opening.put(chunk.open, chunk);
            closing.put(chunk.close, chunk);
        }
    }

    private final char open;
    private final char close;

    Chunk(char open, char close) {
        this.open = open;
        this.close = close;
    }

    static Chunk lookup(char c) {
        return Objects.requireNonNullElseGet(opening.get(c), () -> closing.get(c));
    }

    static boolean isClosing(char c) {
        return closing.containsKey(c);
    }
}
