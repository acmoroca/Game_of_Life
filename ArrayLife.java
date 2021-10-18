package uk.ac.cam.acm239.prejava.ex3;
// col and row to position -> col + 8*row
// col from position -> position % 8
// row from position -> position / 8
public class ArrayLife {
        public static boolean getFromPackedLong(long packed, int position) {
                return ((packed >>> position) & 1) == 1;
        }
        public static void main(String[] args) throws java.io.IOException {
                int size = Integer.parseInt(args[0]);
                long initial = Long.decode(args[1]);
                boolean[][] world = new boolean[size][size];
                //place the long representation of the game board in the centre of "world"
                for(int i = 0; i < 8; i++) {
                        for(int j = 0; j < 8; j++) {
                                world[i+size/2-4][j+size/2-4] = getFromPackedLong(initial, i*8+j);
                        }
                }
                play(world);
        }
        public static boolean getCell(boolean[][] world, int col, int row) {
                if (row < 0 || row > world.length - 1) return false;
                if (col < 0 || col > world[row].length - 1) return false;

                return world[row][col];
        }
        public static void setCell(boolean[][] world, int col, int row, boolean newval) {
                if (row < 0 || row > world.length - 1) return;
                if (col < 0 || col > world[row].length - 1) return;

                world[row][col] = newval;
        }
        public static void print(boolean[][] world) {
                System.out.println("-");
                for (int row = 0; row < world.length; row++) {
                        for (int col = 0; col < world[row].length; col++) {
                                System.out.print(getCell(world, col, row) ? "#" : "_");
                        }
                        System.out.println();
                }
        }
        public static int countNeighbours(boolean[][] world, int col, int row) {
                int count = 0;
                for (int r = -1; r < 2; r++) {
                        for (int c = -1; c < 2; c++) {
                                if (c == 0 && r == 0) {
                                        continue;
                                }
                                else if (getCell(world, col + c, row + r)) {
                                        count += 1;
                                }
                        }
                }
                return(count);
        }
        public static boolean computeCell(boolean[][] world, int col, int row) {
                // liveCell is true if the cell at position (col, row) in world is alive
                boolean liveCell = getCell(world, col, row);

                // neighbours is the number of live neighbours to cell (col. row)
                int neighbours = countNeighbours(world, col, row);
                // we will return this value at the end of the method to indicate whether
                // cell (col, row) should be alive in the next generation
                boolean nextCell = false;

                // A live cell with less than two neighbours dies (underpopulation)
                if (neighbours < 2) {
                        nextCell = false;
                }
                // A live cell with two or three neighbours lives (a balanced population)
                else if ((neighbours == 2 || neighbours == 3) && liveCell) {
                        nextCell = true;
                }
                // A live cell with more than three neighbours dies (overcrowding)
                else if (neighbours > 3) {
                        nextCell = false;
                }
                // A dead cell with exactly three neighbours comes alive
                else if (neighbours == 3 && !liveCell) { // Maybe split balanced into 2 and 3 since 2 is unique
                        nextCell = true;
                }
                return(nextCell);
        }
        public static boolean[][] nextGeneration(boolean[][] world) {
                boolean[][] copy = new boolean[world.length][world.length];
                for(int row = 0; row < world.length; row++) {
                        copy[row] = new boolean[world[row].length];
                        for(int col = 0; col < world[row].length; col++) {
                                copy[row][col] = world[row][col];
                        }
                }
                boolean nextCell;
                for (int row = 0; row < world.length; row++) {
                        for (int col = 0; col < world[row].length; col++) {
                                nextCell = computeCell(world, col, row);
                                setCell(copy, col, row, nextCell);
                        }
                }
                return(copy);
        }
        public static void play(boolean[][] world) throws java.io.IOException {
                boolean[][] copy = null;
                int userResponse = 0;
                while (userResponse != 'q') {
                        print(world);
                        userResponse = System.in.read();
                        copy = nextGeneration(world);
                        for(int row = 0; row < copy.length; row++) {
                                for(int col = 0; col < copy[row].length; col++) {
                                        world[row][col] = copy[row][col];
                                }
                        }
                }
        }

}

