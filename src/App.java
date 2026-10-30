import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class App {
    public static void main(String[] args) {
        // Initialize SQLite database and tables
        DBConnection.createTables();

        // Launch GUI in the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
            }

            PatientView view = new PatientView();
            PatientDAO model = new PatientDAO();
            new PatientController(model, view);

            view.setVisible(true);
        });
    }
}
