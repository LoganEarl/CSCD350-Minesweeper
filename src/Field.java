//Author: Logan Earl

import java.util.*;

public class Field {
    private static final int MINE = -1;

    private Point dimensions;
    private int[][] board;

    private Field(Point dimensions, Set<Point> mines) {
        this.dimensions = dimensions;
        board = new int[dimensions.y][dimensions.x];
        for (Point p : mines) {
            board[p.y][p.x] = MINE;
            for (Point near : p.getNear(dimensions))
                if (board[near.y][near.x] != MINE)
                    board[near.y][near.x]++;
        }
    }

    @Override
    public String toString() {
        boolean first = true;
        StringBuilder out = new StringBuilder();
        for (int y = 0; y < dimensions.y; y++) {
            if (first)
                first = false;
            else
                out.append("\n");

            for (int x = 0; x < dimensions.x; x++) {
                if (board[y][x] == -1)
                    out.append('*');
                else
                    out.append(board[y][x]);
            }
        }
        return out.toString();
    }

    public String getRawField() {
        StringBuilder out = new StringBuilder();
        out.append(dimensions.y).append(" ").append(dimensions.x);
        for (int y = 0; y < dimensions.y; y++) {
            out.append("\n");
            for (int x = 0; x < dimensions.x; x++) {
                if (board[y][x] == -1)
                    out.append('*');
                else
                    out.append('.');
            }
        }
        return out.toString();
    }

    private static class Point {
        private int x;
        private int y;

        private Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        private Point[] getNear(Point dimensions) {
            List<Point> points = new ArrayList<>();
            for (int selectY = y - 1; selectY <= y + 1; selectY++)
                for (int selectX = x - 1; selectX <= x + 1; selectX++)
                    if (selectX >= 0 && selectY >= 0 && selectX < dimensions.x && selectY < dimensions.y && (selectX != x || selectY != y))
                        points.add(new Point(selectX, selectY));
            return points.toArray(new Point[0]);
        }
    }

    public static class Builder {
        public Collection<Field> fromInput(String input) {
            Scanner reader = new Scanner(input);
            Collection<Field> fields = new ArrayList<>();
            while (reader.hasNextLine()) {
                Point dimensions = getDimensions(reader.nextLine());
                if(dimensions.x == 0 && dimensions.y == 0)
                    continue;
                Set<Point> allMines = new HashSet<>();
                for (int i = 0; i < dimensions.y && reader.hasNextLine(); i++) {
                    allMines.addAll(getMines(reader.nextLine(), i));
                }
                fields.add(new Field(dimensions, allMines));
            }
            return fields;
        }

        public Field fromRandom(int width, int height, double minePercentage){
            Point dimensions = new Point(width,height);
            Set<Point> points = new HashSet<>();
            Random rnd = new Random();
            for(int y = 0; y < height; y++)
                for(int x = 0; x < width; x++)
                    if(rnd.nextDouble() <= minePercentage)
                        points.add(new Point(x,y));

            return new Field(dimensions,points);
        }

        private Point getDimensions(String dimensionLine) {
            String[] params = dimensionLine.split(" ");
            try {
                return new Point(
                        Integer.parseInt(params[1]),
                        Integer.parseInt(params[0])
                );
            } catch (NumberFormatException ignored) {
            }
            return new Point(0, 0);
        }

        private Set<Point> getMines(String singleLine, int lineY) {
            Set<Point> points = new HashSet<>();
            for (int x = 0; x < singleLine.length(); x++) {
                String item = singleLine.substring(x, x + 1);
                if (item.equals("*"))
                    points.add(new Point(x, lineY));
            }
            return points;
        }
    }
}
