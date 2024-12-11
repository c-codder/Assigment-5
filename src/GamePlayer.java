public class GamePlayer {
    private final String name;   // Marked as final to enforce immutability
    private final char symbol;   // Marked as final to ensure the symbol cannot be changed after initialization

    /**
     * Constructor for the Player class.
     *
     * @param name   The name of the player (non-null, non-empty).
     * @param symbol The symbol of the player ('X' or 'O').
     * @throws IllegalArgumentException if name is null/empty or symbol is invalid.
     */
    public GamePlayer(String name, char symbol) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Player name cannot be null or empty.");
        }
        if (symbol != 'X' && symbol != 'O') {
            throw new IllegalArgumentException("Symbol must be either 'X' or 'O'.");
        }
        this.name = name.trim();
        this.symbol = symbol;
    }


    public String getName() {
        return name;
    }


    public char getSymbol() {
        return symbol;
    }

    @Override
    public String toString() {
        return "Player{name='" + name + "', symbol=" + symbol + "}";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        GamePlayer player = (GamePlayer) obj;
        return symbol == player.symbol && name.equals(player.name);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + (int) symbol;
        return result;
    }
}
