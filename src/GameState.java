public class GameState {
    private final GamePlayer player1;       // Marked as final to enforce immutability
    private final GamePlayer player2;
    private GamePlayer currentPlayer;
    private final char[] board;        // Marked as final for immutability of reference

    /**
     * Constructor for the Game class.
     *
     * @param player1       Player 1 instance.
     * @param player2       Player 2 instance.
     * @param currentPlayer The player whose turn it currently is.
     * @param board         The current game board state.
     */
    public GameState(GamePlayer player1, GamePlayer player2, GamePlayer currentPlayer, char[] board) {
        if (player1 == null || player2 == null || currentPlayer == null || board == null) {
            throw new IllegalArgumentException("Game parameters cannot be null.");
        }
        if (board.length != 9) {
            throw new IllegalArgumentException("Board must contain exactly 9 cells.");
        }
        this.player1 = player1;
        this.player2 = player2;
        this.currentPlayer = currentPlayer;
        this.board = board.clone(); // Defensive copy to prevent external modification
    }


    public GamePlayer getPlayer1() {
        return player1;
    }


    public GamePlayer getPlayer2() {
        return player2;
    }


    public GamePlayer getCurrentPlayer() {
        return currentPlayer;
    }


    public void setCurrentPlayer(GamePlayer currentPlayer) {
        if (currentPlayer == null) {
            throw new IllegalArgumentException("Current player cannot be null.");
        }
        if (!currentPlayer.equals(player1) && !currentPlayer.equals(player2)) {
            throw new IllegalArgumentException("Current player must be either Player 1 or Player 2.");
        }
        this.currentPlayer = currentPlayer;
    }


    public char[] getBoard() {
        return board.clone(); // Return a defensive copy to prevent external modification
    }

    /**
     * Updates the game board at a specific position.
     *
     * @param index  The index to update (0-8).
     * @param symbol The symbol to place ('X' or 'O').
     */
    public void updateBoard(int index, char symbol) {
        if (index < 0 || index >= board.length) {
            throw new IllegalArgumentException("Invalid board index. Must be between 0 and 8.");
        }
        if (symbol != 'X' && symbol != 'O') {
            throw new IllegalArgumentException("Invalid symbol. Must be 'X' or 'O'.");
        }
        board[index] = symbol;
    }
}
