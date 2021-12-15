package com.richardwardle.aoc2021;

import java.util.Set;

public record Point(int x, int y) {
    public Set<Point> adjacentPoints() {
        return Set.of(
                new Point(x - 1, y - 1),
                new Point(x, y - 1),
                new Point(x + 1, y - 1),
                new Point(x + 1, y),
                new Point(x + 1, y + 1),
                new Point(x, y + 1),
                new Point(x - 1, y + 1),
                new Point(x - 1, y)
        );
    }
}
