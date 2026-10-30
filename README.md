# Smart Hospital Management System (MVC Architecture)

A desktop-based Hospital Management application built in **Java** following the **Model-View-Controller (MVC) Architectural Pattern**, using **Java Swing** for the GUI and **SQLite** with **JDBC** for persistent data storage.

---

## 🏛️ System Architecture

The application strictly implements the **MVC (Model-View-Controller)** pattern:

```
+-----------------------------------------------------------------------------------------+
|                                      PatientView                                        |
|                                    (Java Swing GUI)                                     |
|  - Text fields (ID, Name, Age, Phone, Disease), Gender dropdown, Buttons, JTable       |
+-----------------------------------------------------------------------------------------+
                    ▲                                                 │
   6. Update View   │                                1. User Action   │
    (Show results)  │                                   (Click, etc.) │
                    │                                                 ▼
+-----------------------------------------------------------------------------------------+
|                                   PatientController                                     |
|  - Handles user events, input validation, calls DAO, and updates the view               |
+-----------------------------------------------------------------------------------------+
                    ▲                                                 │
    5. Return Data  │                                2. Request Data  │
     (List / Bool)  │                                (CRUD operation) │
                    │                                                 ▼
+-----------------------------------------------------------------------------------------+
|                                      PatientDAO                                         |
|  - Executes CRUD queries via JDBC, maps database records to Patient POJO                |
+-----------------------------------------------------------------------------------------+
                    ▲                                                 │
   4. Return Result │                                3. Execute Query │
       (ResultSet)  │                                     (SQL/JDBC)  │
                    ▼                                                 ▼
+-----------------------------------------------------------------------------------------+
|                              SQLite Database (hospital.db)                              |
+-----------------------------------------------------------------------------------------+
```

---

## 📐 UML Class Specifications

### 1. Model
* **`Patient.java`** (`<<Model>>`):
  * **Attributes:**
    * `- patientId : int`
    * `- name : String`
    * `- age : int`
    * `- gender : String`
    * `- phone : String`
    * `- disease : String`
  * **Operations:**
    * `+ getters() : void` (Getters for all fields)
    * `+ setters() : void` (Setters for all fields)
    * Default and parameterized constructors

* **`PatientDAO.java`** (`<<Model>>`):
  * **Attributes:**
    * `- connection : Connection`
  * **Operations:**
    * `+ addPatient(Patient) : boolean`
    * `+ getAllPatients() : List<Patient>`
    * `+ updatePatient(Patient) : boolean`
    * `+ deletePatient(int) : boolean`

### 2. View
* **`PatientView.java`** (`<<View>>`):
  * **Attributes:**
    * `- idField : JTextField`
    * `- nameField : JTextField`
    * `- ageField : JTextField`
    * `- genderBox : JComboBox<String>`
    * `- phoneField : JTextField`
    * `- diseaseField : JTextField`
    * `- tableModel : DefaultTableModel`
    * `- patientTable : JTable`
    * Button controls: Add, Update, Delete, Clear
  * **Operations:**
    * `+ getPatientInput() : Patient`
    * `+ getSelectedPatientId() : int`
    * `+ displayPatients(List<Patient>) : void`
    * `+ clearFields() : void`
    * `+ showMessage(String) : void`
    * `+ confirmDelete() : boolean`

### 3. Controller
* **`PatientController.java`** (`<<Controller>>`):
  * **Attributes:**
    * `- model : PatientDAO`
    * `- view : PatientView`
  * **Operations:**
    * `+ addPatient() : void`
    * `+ updatePatient() : void`
    * `+ deletePatient() : void`
    * `+ loadPatients() : void`

### 4. Database & Utility
* **`DBConnection.java`** (`<<Utility>>`):
  * **Attributes:**
    * `- URL : String` (`jdbc:sqlite:hospital.db`)
  * **Operations:**
    * `+ getConnection() : Connection`
    * `+ createTables() : void`
* **`hospital.db`**: Local SQLite database file with auto-created `patients` table.

---

## 📁 Project Structure

```
Practical_8_DP/
│
├── .vscode/
│   └── settings.json           # VS Code Java configuration
│
├── lib/
│   └── sqlite-jdbc.jar         # SQLite JDBC Driver
│
├── src/
│   ├── App.java                # Application entry point (Main)
│   ├── DBConnection.java       # Database connection & table setup utility
│   ├── Patient.java            # POJO Entity (Model)
│   ├── PatientDAO.java         # Data Access Object (Model)
│   ├── PatientView.java        # Swing GUI Interface (View)
│   └── PatientController.java  # Business logic & event handler (Controller)
│
├── hospital.db                 # SQLite database file (created automatically)
└── README.md                   # Project documentation
```

---

## 🚀 How to Run

### Option 1: Visual Studio Code
1. Open this workspace in Visual Studio Code.
2. Ensure the **Extension Pack for Java** is installed.
3. Open `src/App.java`.
4. Click **Run** or press `F5`.

### Option 2: Command Line (PowerShell / Terminal)

1. **Compile all sources:**
   ```powershell
   javac -cp "lib/*;." -d bin src/*.java
   ```

2. **Run the Application:**
   ```powershell
   java -cp "bin;lib/*" App
   ```

---

## ✨ Features & Usage

1. **Add Patient:**
   - Enter Full Name, Age, Gender, Phone, and Disease/Diagnosis.
   - Click **Add Patient**.
   - Input is validated by the Controller before being saved in SQLite.

2. **View Patients:**
   - All patient records are displayed dynamically in the table on the right.

3. **Update Patient:**
   - Click on any patient record in the table. The form fields will automatically populate with the record's details.
   - Modify the fields and click **Update Patient**.

4. **Delete Patient:**
   - Select a patient in the table.
   - Click **Delete Patient** and confirm the prompt dialog to delete the record.

5. **Clear Form:**
   - Click **Clear Form** to reset all fields and clear table selection.
