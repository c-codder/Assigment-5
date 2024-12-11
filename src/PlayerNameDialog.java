import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class PlayerNameDialog extends JDialog {
    private final JTextField player1Field;
    private final JTextField player2Field;
    private String player1Name;
    private String player2Name;

    public PlayerNameDialog(Frame owner) {
        super(owner, "Enter Player Names", true);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Add spacing between components

        // Player 1 input
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        add(new JLabel("Player 1 (X):"), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        player1Field = new JTextField(15);
        add(player1Field, gbc);

        // Player 2 input
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        add(new JLabel("Player 2 (O):"), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        player2Field = new JTextField(15);
        add(player2Field, gbc);

        // OK button
        JButton okButton = new JButton("OK");
        okButton.addActionListener(this::onOkButtonPressed);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(okButton, gbc);

        // Dialog settings
        pack();
        setLocationRelativeTo(owner);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void onOkButtonPressed(ActionEvent e) {
        player1Name = player1Field.getText().trim();
        player2Name = player2Field.getText().trim();

        if (player1Name.isEmpty() || player2Name.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Both player names must be entered.",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        dispose();
    }

    public String getPlayer1Name() {
        return player1Name;
    }

    public String getPlayer2Name() {
        return player2Name;
    }
}
