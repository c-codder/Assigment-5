import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class GameDatabaseManager {
    private static final String DATABASE_URL = "jdbc:sqlite:tic_tac_toe.db";
    private static final String CREATE_TABLE_SQL = """
            CREATE TABLE IF NOT EXISTS games (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                player1 TEXT NOT NULL,
                player2 TEXT NOT NULL,
                currentPlayer TEXT NOT NULL,
                board TEXT NOT NULL
            );
            """;
    private static final String SAVE_GAME_SQL = """
            INSERT INTO games (player1, player2, currentPlayer, board) 
            VALUES (?, ?, ?, ?);
            """;
    private static final String LOAD_LATEST_GAME_SQL = """
            SELECT player1, player2, currentPlayer, board 
            FROM games 
            ORDER BY id DESC 
            LIMIT 1;
            """;

    public GameDatabaseManager() {
        createTable();
    }


    private void createTable() {
        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement stmt = conn.prepareStatement(CREATE_TABLE_SQL)) {
            stmt.execute();
        } catch (Exception e) {
            System.err.println("Error creating table: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Saves the game state to the database.
     *
     * @param player1       the first player.
     * @param player2       the second player.
     * @param currentPlayer the current player's turn.
     * @param board         the current state of the board.
     */
    public void saveGame(GamePlayer player1, GamePlayer player2, GamePlayer currentPlayer, char[] board) {
        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement stmt = conn.prepareStatement(SAVE_GAME_SQL)) {
            stmt.setString(1, player1.getName());
            stmt.setString(2, player2.getName());
            stmt.setString(3, currentPlayer.getName());
            stmt.setString(4, new String(board));
            stmt.executeUpdate();
        } catch (Exception e) {
            System.err.println("Error saving game: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public GameState loadLatestGame() {
        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement stmt = conn.prepareStatement(LOAD_LATEST_GAME_SQL);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                GamePlayer player1 = new GamePlayer(rs.getString("player1"), 'X');
                GamePlayer player2 = new GamePlayer(rs.getString("player2"), 'O');
                char[] board = rs.getString("board").toCharArray();
                String currentPlayerName = rs.getString("currentPlayer");
                GamePlayer currentPlayer = currentPlayerName.equals(player1.getName()) ? player1 : player2;
                return new GameState(player1, player2, currentPlayer, board);
            }
        } catch (Exception e) {
            System.err.println("Error loading latest game: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
