import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

/**
 * A custom JPanel that creates a form-like layout for collecting user information.
 * Fields include Last Name, First Name, Address, City, State, and Zip.
 * It uses GridBagLayout to arrange JLabels and JTextFields in a two-column format.
 */
public class ComplexDialogPanel extends JPanel {
    
    // Labels for form fields
    public static final String[] LABEL_TEXTS = { "Last Name", "First Name", "Address", "City", "State", "Zip" };
    
    // Number of columns for each JTextField
    public static final int COLS = 8;
    
    // Maps label text to its corresponding JTextField
    private Map<String, JTextField> labelFieldMap = new HashMap<>();

    /**
     * Constructor sets up the form UI using GridBagLayout
     */
    public ComplexDialogPanel() {
        setLayout(new GridBagLayout());
        
        // Create a label and a text field for each entry in LABEL_TEXTS
        for (int i = 0; i < LABEL_TEXTS.length; i++) {
            String labelTxt = LABEL_TEXTS[i];
            
            // Add label to panel
            add(new JLabel(labelTxt), createGbc(0, i));
            
            // Create and add corresponding text field
            JTextField textField = new JTextField(COLS);
            labelFieldMap.put(labelTxt, textField);
            add(textField, createGbc(1, i));
        }

        // Add a titled border to the panel
        setBorder(BorderFactory.createTitledBorder("Enter User Information"));
    }

    /**
     * Gets the text entered in the text field associated with a specific label.
     * @param labelText the label text to retrieve the field from
     * @return the user-entered text
     * @throws IllegalArgumentException if the label does not exist
     */
    public String getText(String labelText) {
        JTextField textField = labelFieldMap.get(labelText);
        if (textField != null) {
            return textField.getText();
        } else {
            throw new IllegalArgumentException(labelText);
        }
    }

    /**
     * Helper method to create GridBagConstraints for a component.
     * @param x column index (0 for label, 1 for field)
     * @param y row index
     * @return configured GridBagConstraints object
     */
    public static GridBagConstraints createGbc(int x, int y) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.weightx = 1.0;
        gbc.weighty = gbc.weightx;

        if (x == 0) {
            gbc.anchor = GridBagConstraints.LINE_START;
            gbc.fill = GridBagConstraints.BOTH;
            gbc.insets = new Insets(3, 3, 3, 8); // spacing around label
        } else {
            gbc.anchor = GridBagConstraints.LINE_END;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets = new Insets(3, 3, 3, 3); // spacing around field
        }

        return gbc;
    }

    /**
     * Creates and displays a dialog using this panel to collect user input.
     */
    private static void createAndShowGui() {
        ComplexDialogPanel mainPanel = new ComplexDialogPanel();

        // Set dialog options
        int optionType = JOptionPane.DEFAULT_OPTION;
        int messageType = JOptionPane.PLAIN_MESSAGE;
        Icon icon = null;
        String[] options = { "Submit", "Cancel" };
        Object initialValue = options[0];

        // Show dialog
        int reply = JOptionPane.showOptionDialog(null, mainPanel,
                "Get User Information", optionType, messageType, icon, options, initialValue);

        // If user clicks "Submit", print the entered values
        if (reply == 0) {
            System.out.println("Selections:");
            for (String labelText : LABEL_TEXTS) {
                System.out.printf("%12s: %s%n", labelText, mainPanel.getText(labelText));
            }
        }
    }

    /**
     * Main method to run the application.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGui();
            }
        });
    }
}
