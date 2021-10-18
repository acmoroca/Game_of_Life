package uk.ac.cam.acm239.prejava.ex2;
public class TinyLife {
        public static void main(String[] args) throws java.io.IOException {
                play(Long.decode(args[0]));
        }
        public static boolean getCell(long world, int col, int row) {
                if (col > 7 || col < 0 || row > 7 || row < 0) {
                        return(false); //Maybe should print out standardised error message and break?
                }
                int position = col + 8*row;
                boolean result = PackedLong.get(world, position);
                return(result);
        }
        public static long setCell(long world, int col, int row, boolean newval) {
                if (col > 7 || col < 0 || row > 7 || row < 0) {
                        return(world);//Maybe should print out standardised error message and break?
                }
                int position = col + 8*row;
                long result = PackedLong.set(world, position, newval);
                return(result);
        }
        public static void print(long world) {
                System.out.println("-");
                for (int row = 0; row < 8; row++) {
                        for (int col = 0; col < 8; col++) {
                                System.out.print(getCell(world, col, row) ? "#" : "_");
                        }
                        System.out.println();
                }
        }
        public static int countNeighbours(long world, int col, int row) {
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
        public static boolean computeCell(long world, int col, int row) {
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
        public static long nextGeneration(long world) {
                long copy = world;
                boolean nextCell;
                for (int row = 0; row < 8; row++) {
                        for (int col = 0; col < 8; col++) {
                                nextCell = computeCell(world, col, row);
                                copy = setCell(copy, col, row, nextCell);
                        }
                }
                return(copy);
        }
        public static void play(long world) throws java.io.IOException {
                int userResponse = 0;
                while (userResponse != 'q') {
                        print(world);
                        userResponse = System.in.read();
                        world = nextGeneration(world);
                }
        }

}

