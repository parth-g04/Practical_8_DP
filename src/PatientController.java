import java.util.List;

public class PatientController {
    private PatientDAO model;
    private PatientView view;

    public PatientController(PatientDAO model, PatientView view) {
        this.model = model;
        this.view = view;

        // Register ActionListeners to View components
        this.view.getAddButton().addActionListener(e -> addPatient());
        this.view.getUpdateButton().addActionListener(e -> updatePatient());
        this.view.getDeleteButton().addActionListener(e -> deletePatient());
        this.view.getClearButton().addActionListener(e -> this.view.clearFields());

        // Load initial patient data from database into view
        loadPatients();
    }

    public void addPatient() {
        Patient patient = view.getPatientInput();

        // Input validation
        if (patient.getName().isEmpty()) {
            view.showMessage("Please enter patient name.");
            return;
        }
        if (patient.getAge() <= 0) {
            view.showMessage("Please enter a valid age (positive integer).");
            return;
        }
        if (patient.getGender().isEmpty()) {
            view.showMessage("Please select a gender.");
            return;
        }
        if (patient.getPhone().isEmpty()) {
            view.showMessage("Please enter a phone number.");
            return;
        }
        if (patient.getDisease().isEmpty()) {
            view.showMessage("Please enter disease/diagnosis.");
            return;
        }

        boolean success = model.addPatient(patient);
        if (success) {
            view.showMessage("Patient added successfully!");
            view.clearFields();
            loadPatients();
        } else {
            view.showMessage("Failed to add patient to database.");
        }
    }

    public void updatePatient() {
        int id = view.getSelectedPatientId();
        if (id <= 0) {
            view.showMessage("Please select a patient from the table to update.");
            return;
        }

        Patient patient = view.getPatientInput();
        patient.setPatientId(id);

        // Input validation
        if (patient.getName().isEmpty()) {
            view.showMessage("Please enter patient name.");
            return;
        }
        if (patient.getAge() <= 0) {
            view.showMessage("Please enter a valid age (positive integer).");
            return;
        }
        if (patient.getGender().isEmpty()) {
            view.showMessage("Please select a gender.");
            return;
        }
        if (patient.getPhone().isEmpty()) {
            view.showMessage("Please enter a phone number.");
            return;
        }
        if (patient.getDisease().isEmpty()) {
            view.showMessage("Please enter disease/diagnosis.");
            return;
        }

        boolean success = model.updatePatient(patient);
        if (success) {
            view.showMessage("Patient updated successfully!");
            view.clearFields();
            loadPatients();
        } else {
            view.showMessage("Failed to update patient.");
        }
    }

    public void deletePatient() {
        int id = view.getSelectedPatientId();
        if (id <= 0) {
            view.showMessage("Please select a patient from the table to delete.");
            return;
        }

        if (view.confirmDelete()) {
            boolean success = model.deletePatient(id);
            if (success) {
                view.showMessage("Patient deleted successfully!");
                view.clearFields();
                loadPatients();
            } else {
                view.showMessage("Failed to delete patient.");
            }
        }
    }

    public void loadPatients() {
        List<Patient> patients = model.getAllPatients();
        view.displayPatients(patients);
    }
}
