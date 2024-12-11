public class GameBoard {
    private static final int BOARD_SIZE = 9;
    private static final char EMPTY_CELL = '\u0000';
    private char[] board;

    public GameBoard() {
        board = new char[BOARD_SIZE]; // Initialize the board with default empty cells
    }


    public boolean makeMove(int index, char symbol) {
        if (isValidIndex(index) && board[index] == EMPTY_CELL) {
            board[index] = symbol;
            return true;
        }
        return false;
    }


    public boolean checkWinner(char currentPlayer) {
        int[][] winConditions = {
                {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // Rows
                {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // Columns
                {0, 4, 8}, {2, 4, 6}             // Diagonals
        };

        for (int[] condition : winConditions) {
            if (board[condition[0]] == currentPlayer &&
                    board[condition[1]] == currentPlayer &&
                    board[condition[2]] == currentPlayer) {
                return true;
            }
        }
        return false;
    }


    public boolean isBoardFull() {
        for (char cell : board) {
            if (cell == EMPTY_CELL) {
                return false;
            }
        }
        return true;
    }


    public void reset() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            board[i] = EMPTY_CELL;
        }
    }


    public char[] getBoard() {
        return board.clone(); // Return a copy to avoid direct modifications
    }


    private boolean isValidIndex(int index) {
        return index >= 0 && index < BOARD_SIZE;
    }
}
