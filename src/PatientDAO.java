import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PatientDAO {
    private Connection connection;

    public PatientDAO() {
        this.connection = DBConnection.getConnection();
    }

    public PatientDAO(Connection connection) {
        this.connection = connection;
    }

    private Connection getValidConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DBConnection.getConnection();
        }
        return connection;
    }

    public boolean addPatient(Patient patient) {
        String sql = "INSERT INTO patients (name, age, gender, phone, disease) VALUES (?, ?, ?, ?, ?)";
        try {
            Connection conn = getValidConnection();
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, patient.getName());
                pstmt.setInt(2, patient.getAge());
                pstmt.setString(3, patient.getGender());
                pstmt.setString(4, patient.getPhone());
                pstmt.setString(5, patient.getDisease());
                return pstmt.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error adding patient: " + e.getMessage());
            return false;
        }
    }

    public List<Patient> getAllPatients() {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT * FROM patients";
        try {
            Connection conn = getValidConnection();
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    Patient patient = new Patient(
                        rs.getInt("patientId"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("gender"),
                        rs.getString("phone"),
                        rs.getString("disease")
                    );
                    patients.add(patient);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving patients: " + e.getMessage());
        }
        return patients;
    }

    public boolean updatePatient(Patient patient) {
        String sql = "UPDATE patients SET name = ?, age = ?, gender = ?, phone = ?, disease = ? WHERE patientId = ?";
        try {
            Connection conn = getValidConnection();
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, patient.getName());
                pstmt.setInt(2, patient.getAge());
                pstmt.setString(3, patient.getGender());
                pstmt.setString(4, patient.getPhone());
                pstmt.setString(5, patient.getDisease());
                pstmt.setInt(6, patient.getPatientId());
                return pstmt.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error updating patient: " + e.getMessage());
            return false;
        }
    }

    public boolean deletePatient(int patientId) {
        String sql = "DELETE FROM patients WHERE patientId = ?";
        try {
            Connection conn = getValidConnection();
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, patientId);
                return pstmt.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error deleting patient: " + e.getMessage());
            return false;
        }
    }
}
