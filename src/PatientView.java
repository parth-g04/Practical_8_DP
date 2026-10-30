import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class PatientView extends JFrame {
    // UI components specified in UML
    private JTextField idField;
    private JTextField nameField;
    private JTextField ageField;
    private JComboBox<String> genderBox;
    private JTextField phoneField;
    private JTextField diseaseField;
    private DefaultTableModel tableModel;
    private JTable patientTable;

    // Action buttons
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton clearButton;

    public PatientView() {
        super("Smart Hospital Management System - Patient Management");
        initUI();
    }

    private void initUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(950, 650);
        setMinimumSize(new Dimension(850, 550));
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // 1. Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(33, 58, 87));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel titleLabel = new JLabel("Smart Hospital Management System");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(Color.WHITE);

        JLabel subtitleLabel = new JLabel("Patient Record Management (MVC Architecture)");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitleLabel.setForeground(new Color(200, 215, 230));

        headerPanel.add(titleLabel, BorderLayout.NORTH);
        headerPanel.add(subtitleLabel, BorderLayout.SOUTH);
        add(headerPanel, BorderLayout.NORTH);

        // 2. Center Panel (Left Form + Right Table)
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        // Left Form Panel
        JPanel formContainer = new JPanel(new BorderLayout(5, 10));
        formContainer.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(180, 195, 210), 1),
                " Patient Information ",
                0,
                0,
                new Font("Segoe UI", Font.BOLD, 14),
                new Color(33, 58, 87)
            ),
            BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));

        JPanel formGrid = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        idField = new JTextField(15);
        idField.setEditable(false);
        idField.setBackground(new Color(245, 245, 245));
        idField.setToolTipText("Auto-generated Patient ID");

        nameField = new JTextField(15);
        ageField = new JTextField(15);
        genderBox = new JComboBox<>(new String[]{"Select Gender", "Male", "Female", "Other"});
        phoneField = new JTextField(15);
        diseaseField = new JTextField(15);

        Font labelFont = new Font("Segoe UI", Font.PLAIN, 13);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 13);

        idField.setFont(fieldFont);
        nameField.setFont(fieldFont);
        ageField.setFont(fieldFont);
        genderBox.setFont(fieldFont);
        phoneField.setFont(fieldFont);
        diseaseField.setFont(fieldFont);

        int row = 0;
        addFormRow(formGrid, gbc, row++, "Patient ID:", idField, labelFont);
        addFormRow(formGrid, gbc, row++, "Full Name:", nameField, labelFont);
        addFormRow(formGrid, gbc, row++, "Age:", ageField, labelFont);
        addFormRow(formGrid, gbc, row++, "Gender:", genderBox, labelFont);
        addFormRow(formGrid, gbc, row++, "Phone:", phoneField, labelFont);
        addFormRow(formGrid, gbc, row++, "Disease / Diagnosis:", diseaseField, labelFont);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 8));
        addButton = new JButton("Add Patient");
        updateButton = new JButton("Update Patient");
        deleteButton = new JButton("Delete Patient");
        clearButton = new JButton("Clear Form");

        styleButton(addButton, new Color(34, 139, 34), Color.WHITE);
        styleButton(updateButton, new Color(30, 144, 255), Color.WHITE);
        styleButton(deleteButton, new Color(220, 53, 69), Color.WHITE);
        styleButton(clearButton, new Color(108, 117, 125), Color.WHITE);

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);

        formContainer.add(formGrid, BorderLayout.CENTER);
        formContainer.add(buttonPanel, BorderLayout.SOUTH);

        centerPanel.add(formContainer, BorderLayout.WEST);

        // Right Table Panel
        JPanel tableContainer = new JPanel(new BorderLayout());
        tableContainer.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(180, 195, 210), 1),
                " Patient Records ",
                0,
                0,
                new Font("Segoe UI", Font.BOLD, 14),
                new Color(33, 58, 87)
            ),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        String[] columns = {"ID", "Name", "Age", "Gender", "Phone", "Disease"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // read-only table view
            }
        };

        patientTable = new JTable(tableModel);
        patientTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        patientTable.setRowHeight(24);
        patientTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        patientTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        patientTable.getTableHeader().setBackground(new Color(235, 240, 248));
        patientTable.getTableHeader().setForeground(new Color(33, 58, 87));

        // Center align ID, Age, Gender
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        patientTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        patientTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        patientTable.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        patientTable.getColumnModel().getColumn(2).setPreferredWidth(50);
        patientTable.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        patientTable.getColumnModel().getColumn(3).setPreferredWidth(75);

        // Table selection listener to auto-populate form
        patientTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = patientTable.getSelectedRow();
                if (selectedRow != -1) {
                    idField.setText(tableModel.getValueAt(selectedRow, 0).toString());
                    nameField.setText(tableModel.getValueAt(selectedRow, 1).toString());
                    ageField.setText(tableModel.getValueAt(selectedRow, 2).toString());
                    genderBox.setSelectedItem(tableModel.getValueAt(selectedRow, 3).toString());
                    phoneField.setText(tableModel.getValueAt(selectedRow, 4) != null ? tableModel.getValueAt(selectedRow, 4).toString() : "");
                    diseaseField.setText(tableModel.getValueAt(selectedRow, 5) != null ? tableModel.getValueAt(selectedRow, 5).toString() : "");
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(patientTable);
        tableContainer.add(scrollPane, BorderLayout.CENTER);

        centerPanel.add(tableContainer, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);
    }

    private void addFormRow(JPanel panel, GridBagConstraints gbc, int row, String labelText, java.awt.Component comp, Font font) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.3;
        JLabel label = new JLabel(labelText);
        label.setFont(font);
        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.gridy = row;
        gbc.weightx = 0.7;
        panel.add(comp, gbc);
    }

    private void styleButton(JButton btn, Color bg, Color fg) {
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(7, 14, 7, 14));
        btn.setOpaque(true);
    }

    // UML Methods
    public Patient getPatientInput() {
        int id = 0;
        if (!idField.getText().trim().isEmpty()) {
            try {
                id = Integer.parseInt(idField.getText().trim());
            } catch (NumberFormatException ignored) {}
        }

        String name = nameField.getText().trim();

        int age = -1;
        if (!ageField.getText().trim().isEmpty()) {
            try {
                age = Integer.parseInt(ageField.getText().trim());
            } catch (NumberFormatException e) {
                age = -1;
            }
        }

        String gender = (String) genderBox.getSelectedItem();
        if ("Select Gender".equals(gender)) {
            gender = "";
        }

        String phone = phoneField.getText().trim();
        String disease = diseaseField.getText().trim();

        return new Patient(id, name, age, gender, phone, disease);
    }

    public int getSelectedPatientId() {
        int selectedRow = patientTable.getSelectedRow();
        if (selectedRow != -1) {
            return Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
        }
        if (!idField.getText().trim().isEmpty()) {
            try {
                return Integer.parseInt(idField.getText().trim());
            } catch (NumberFormatException ignored) {}
        }
        return -1;
    }

    public void displayPatients(List<Patient> patients) {
        tableModel.setRowCount(0);
        if (patients != null) {
            for (Patient p : patients) {
                tableModel.addRow(new Object[]{
                    p.getPatientId(),
                    p.getName(),
                    p.getAge(),
                    p.getGender(),
                    p.getPhone(),
                    p.getDisease()
                });
            }
        }
    }

    public void clearFields() {
        idField.setText("");
        nameField.setText("");
        ageField.setText("");
        genderBox.setSelectedIndex(0);
        phoneField.setText("");
        diseaseField.setText("");
        patientTable.clearSelection();
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public boolean confirmDelete() {
        int choice = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to delete this patient record?",
            "Confirm Deletion",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );
        return choice == JOptionPane.YES_OPTION;
    }

    // Button getters for Controller
    public JButton getAddButton() {
        return addButton;
    }

    public JButton getUpdateButton() {
        return updateButton;
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }

    public JButton getClearButton() {
        return clearButton;
    }
}
