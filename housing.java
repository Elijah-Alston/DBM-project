import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Housing {
    private static Connection con;
    private static Statement stmt;
    private static ResultSet res;
    private static PreparedStatement prep_stmt;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int option = 11;

        try {
            // Create a connection
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/Housing", "root", "");

            while (option != 10) {
                System.out.println("\n1. Add a resident");
                System.out.println("2. Update a resident's information");
                System.out.println("3. Find a resident by their id");
                System.out.println("4. Delete a resident");
                System.out.println("5. Show all houses");
                System.out.println("6. Show rooms with open availability");
                System.out.println("7. Show the number of houses on a campus");
                System.out.println("8. Find residents by classification & house");
                System.out.println("9. Show where all residents live");
                System.out.println("10. Exit\n");

                System.out.print("Choice : ");
                option = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (option) {
                    case 1:
                        addresident();
                        break;
                    case 2:
                        updateresident();
                        break;
                    case 3:
                        findbyid();
                        break;
                    case 4:
                        deleteresident();
                        break;
                    case 5:
                        findall();
                        break;
                    case 6:
                        openspots();
                        break;
                    case 7:
                        displaycampushouses();
                        break;
                    case 8:
                        residentsbyclass();
                        break;
                    case 9:
                        residentandhouse();
                        break;
                }
            }
        } catch (SQLException e) {
            System.out.println("# ERR: SQLException in " + e.getMessage());
            System.out.println(" (MySQL error code: " + e.getErrorCode());
            System.out.println(", SQLState: " + e.getSQLState() + " )");
        } finally {
            try {
                if (res != null) res.close();
                if (stmt != null) stmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        System.out.println();
    }

    public static void addresident() {
        Scanner scanner = new Scanner(System.in);
        try {
            String Name, Class, Email, House_name, Phone, GPA_str;
            int Balance, Resident_id;
            double GPA;

            System.out.print("Name : ");
            Name = scanner.nextLine();

            System.out.print("Class : ");
            Class = scanner.nextLine();

            System.out.print("GPA  : ");
            GPA_str = scanner.nextLine();
            GPA = Double.parseDouble(GPA_str);

            System.out.print("House_name : ");
            House_name = scanner.nextLine();

            System.out.print("Email : ");
            Email = scanner.nextLine();

            System.out.print("Phone : ");
            Phone = scanner.nextLine();

            System.out.print("Balance : ");
            Balance = scanner.nextInt();

            // Create a connection
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/Housing", "root", "");

            prep_stmt = con.prepareStatement("INSERT INTO Residents (Name, Class, GPA, House_name, Email, Phone, Balance) " +
                    "VALUES(?,?,?,?,?,?,?) ", Statement.RETURN_GENERATED_KEYS);

            prep_stmt.setString(1, Name);
            prep_stmt.setString(2, Class);
            prep_stmt.setDouble(3, GPA);
            prep_stmt.setString(4, House_name);
            prep_stmt.setString(5, Email);
            prep_stmt.setString(6, Phone);
            prep_stmt.setInt(7, Balance);

            prep_stmt.executeUpdate();

            res = prep_stmt.getGeneratedKeys();
            if (res.next()) {
                Resident_id = res.getInt(1);
                System.out.println("Resident added successfully.");
                System.out.println("Resident ID: " + Resident_id + " | ");
                System.out.println("Name: " + Name + " | ");
                System.out.println("Class: " + Class + " | ");
                System.out.println("GPA: " + GPA + " | ");
                System.out.println("House Name: " + House_name + " | ");
                System.out.println("Email: " + Email + " | ");
                System.out.println("Phone: " + Phone + " | ");
                System.out.println("Balance: " + Balance);
            }

            res.close();
            prep_stmt.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("# ERR: SQLException in " + e.getMessage());
            System.out.println(" (MySQL error code: " + e.getErrorCode());
            System.out.println(", SQLState: " + e.getSQLState() + " )");
        }
    }

    public static void updateresident() {
        Scanner scanner = new Scanner(System.in);
        try {
            int Resident_id;
            String fieldToUpdate;
            String newValue;
            String confirmation;

            System.out.print("Enter Resident ID: ");
            Resident_id = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            // Create a connection
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/Housing", "root", "");

            prep_stmt = con.prepareStatement("SELECT * FROM Residents WHERE Resident_id = ?");
            prep_stmt.setInt(1, Resident_id);
            res = prep_stmt.executeQuery();

            if (res.next()) {
                System.out.println("Resident found:\n");
                System.out.println("Name: " + res.getString("Name"));
                System.out.println("Class: " + res.getString("Class"));
                System.out.println("GPA: " + res.getDouble("GPA"));
                System.out.println("House Name: " + res.getString("House_name"));
                System.out.println("Email: " + res.getString("Email"));
                System.out.println("Phone: " + res.getString("Phone"));
                System.out.println("Balance: " + res.getInt("Balance"));

                System.out.println("What do you want to update?\n");
                System.out.println("1. Class\n");
                System.out.println("2. GPA\n");
                System.out.println("3. House Name\n");
                System.out.println("4. Email\n");
                System.out.println("5. Phone\n");
                System.out.println("6. Balance\n");
                System.out.println("7. Cancel\n");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        fieldToUpdate = "Class";
                        break;
                    case 2:
                        fieldToUpdate = "GPA";
                        break;
                    case 3:
                        fieldToUpdate = "House_name";
                        break;
                    case 4:
                        fieldToUpdate = "Email";
                        break;
                    case 5:
                        fieldToUpdate = "Phone";
                        break;
                    case 6:
                        fieldToUpdate = "Balance";
                        break;
                    case 7:
                        System.out.println("Operation canceled.\n");
                        return;
                    default:
                        System.out.println("Invalid choice. Operation canceled.\n");
                        return;
                }

                System.out.print("Enter new value for " + fieldToUpdate + ": ");
                newValue = scanner.nextLine();

                System.out.print("Are you sure you want to update this information? (Y/N): ");
                confirmation = scanner.nextLine();

                if (confirmation.equalsIgnoreCase("Y")) {
                    prep_stmt = con.prepareStatement("UPDATE Residents SET " + fieldToUpdate + " = ? WHERE Resident_id = ?");
                    prep_stmt.setString(1, newValue);
                    prep_stmt.setInt(2, Resident_id);
                    prep_stmt.executeUpdate();
                    System.out.println("Information updated successfully.\n");
                } else {
                    System.out.println("Operation canceled.\n");
                }
            } else {
                System.out.println("Resident with ID " + Resident_id + " not found.\n");
            }

            res.close();
            prep_stmt.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("# ERR: SQLException in " + e.getMessage());
            System.out.println(" (MySQL error code: " + e.getErrorCode());
            System.out.println(", SQLState: " + e.getSQLState() + " )");
        }
    }

    public static void deleteresident() {
        Scanner scanner = new Scanner(System.in);
        try {
            int Resident_id;
            String confirmation;

            System.out.print("Enter Resident ID: ");
            Resident_id = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            // Create a connection
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/Housing", "root", "");

            prep_stmt = con.prepareStatement("SELECT * FROM Residents WHERE Resident_id = ?");
            prep_stmt.setInt(1, Resident_id);
            res = prep_stmt.executeQuery();

            if (res.next()) {
                System.out.println("Resident found:\n");
                System.out.println("Name: " + res.getString("Name") + " | ");
                System.out.println("Class: " + res.getString("Class") + " | ");
                System.out.println("GPA: " + res.getDouble("GPA") + " | ");
                System.out.println("House Name: " + res.getString("House_name") + " | ");
                System.out.println("Email: " + res.getString("Email") + " | ");
                System.out.println("Phone: " + res.getString("Phone") + " | ");
                System.out.println("Balance: " + res.getInt("Balance"));

                System.out.print("Are you sure you want to remove this resident? (Y/N): ");
                confirmation = scanner.nextLine();

                if (confirmation.equalsIgnoreCase("Y")) {
                    prep_stmt = con.prepareStatement("DELETE FROM Residents WHERE Resident_id = ?");
                    prep_stmt.setInt(1, Resident_id);
                    prep_stmt.executeUpdate();
                    System.out.println("Resident removed successfully.\n");
                } else {
                    System.out.println("Operation canceled.\n");
                }
            } else {
                System.out.println("Resident with ID " + Resident_id + " not found.\n");
            }

            res.close();
            prep_stmt.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("# ERR: SQLException in " + e.getMessage());
            System.out.println(" (MySQL error code: " + e.getErrorCode());
            System.out.println(", SQLState: " + e.getSQLState() + " )");
        }
    }

    public static void findall() {
        try {
            stmt = con.createStatement();
            res = stmt.executeQuery("SELECT * from Houses");

            while (res.next()) {
                System.out.println(res.getString("House_name") + " | ");
                System.out.println("Campus id:" + res.getInt("Campus_id") + " | ");
                System.out.println(res.getString("Location") + " | ");
                System.out.println("Num Residents: " + res.getInt("Num_residents") + " | ");
                System.out.println("Num Rooms:" + res.getInt("Num_rooms"));
            }
        } catch (SQLException e) {
            System.out.println("# ERR: SQLException in " + e.getMessage());
            System.out.println(" (MySQL error code: " + e.getErrorCode());
            System.out.println(", SQLState: " + e.getSQLState() + " )");
        }
    }

    public static void findbyid() {
        Scanner scanner = new Scanner(System.in);
        try {
            int Resident_id;

            System.out.print("Enter the resident ID : ");
            Resident_id = scanner.nextInt();

            prep_stmt = con.prepareStatement("SELECT * FROM Residents WHERE Resident_id = ?");
            prep_stmt.setInt(1, Resident_id);
            res = prep_stmt.executeQuery();

            while (res.next()) {
                System.out.println(res.getInt("Resident_id") + " | ");
                System.out.println(res.getString("Name") + " | ");
                System.out.println(res.getString("Class") + " | ");
                System.out.println(res.getInt("GPA") + " | ");
                System.out.println(res.getString("House_name") + " | ");
                System.out.println(res.getString("Email") + " | ");
                System.out.println(res.getString("Phone") + " | ");
                System.out.println(res.getInt("Balance"));
            }
        } catch (SQLException e) {
            System.out.println("# ERR: SQLException in " + e.getMessage());
            System.out.println(" (MySQL error code: " + e.getErrorCode());
            System.out.println(", SQLState: " + e.getSQLState() + " )");
        }
    }

    public static void openspots() {
        try {
            // Create a connection
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/Housing", "root", "");

            // Query to select room information with open spots
            String query = "SELECT House_name, Room_id, Room_type, Current_residents FROM Rooms WHERE (Room_type = 'Single' AND Current_residents < 1) OR (Room_type = 'Double' AND Current_residents < 2)";

            stmt = con.createStatement();
            res = stmt.executeQuery(query);

            System.out.println("Rooms with open spots:");

            while (res.next()) {
                String roomType = res.getString("Room_type");
                String roomId = res.getString("Room_id");
                int currentResidents = res.getInt("Current_residents");
                int openSpots = (roomType.equals("Double")) ? 2 - currentResidents : 1 - currentResidents;
                System.out.println(res.getString("House_name") + " |Room ID: " + roomId + " | " + roomType + " | OPEN SPOTS: " + openSpots);
            }

            res.close();
            stmt.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("# ERR: SQLException in " + e.getMessage());
            System.out.println(" (MySQL error code: " + e.getErrorCode());
            System.out.println(", SQLState: " + e.getSQLState() + " )");
        }
    }

    public static void displaycampushouses() {
        Scanner scanner = new Scanner(System.in);
        try {
            int campusId;
            System.out.print("Enter the campus ID: ");
            campusId = scanner.nextInt();

            // Create a connection
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/Housing", "root", "");

            // Query to select campus information
            String query = "SELECT Campus_id, Location FROM Campus WHERE Campus_id = " + campusId;

            stmt = con.createStatement();
            res = stmt.executeQuery(query);

            if (res.next()) {
                System.out.println("\nCampus ID: " + res.getInt("Campus_id") + " | " + res.getString("Location"));
                System.out.println("Houses:");
            } else {
                System.out.println("No campus found with ID: " + campusId);
                return;
            }

            // Query to select house names on the specified campus
            query = "SELECT House_name FROM Houses WHERE Campus_id = " + campusId;
            res = stmt.executeQuery(query);

            int numHouses = 0;
            while (res.next()) {
                System.out.println(res.getString("House_name"));
                numHouses++;
            }

            System.out.println("\nNumber of Houses: " + numHouses);

            res.close();
            stmt.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("# ERR: SQLException in " + e.getMessage());
            System.out.println(" (MySQL error code: " + e.getErrorCode());
            System.out.println(", SQLState: " + e.getSQLState() + " )");
        }
    }

    public static void residentsbyclass() {
        Scanner scanner = new Scanner(System.in);
        try {
            String Class, House_name;
            char option;

            System.out.print("Enter the class: ");
            Class = scanner.nextLine();

            System.out.print("Do you want to specify a house (Y/N)? ");
            option = scanner.nextLine().toUpperCase().charAt(0);

            if (option == 'Y') {
                System.out.print("Enter the house name: ");
                House_name = scanner.nextLine();
            }

            // Create a connection
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/Housing", "root", "");

            // Query to select residents based on class and house (if specified)
            StringBuilder queryBuilder = new StringBuilder("SELECT Name, House_name, Class FROM Residents WHERE Class = '");
            queryBuilder.append(Class).append("'");
            if (option == 'Y') {
                queryBuilder.append(" AND House_name = '").append(House_name).append("'");
            }
            String query = queryBuilder.toString();

            stmt = con.createStatement();
            res = stmt.executeQuery(query);

            System.out.println("Residents with class " + Class + ":");

            while (res.next()) {
                System.out.println("Name: " + res.getString("Name") + " | House Name: " + res.getString("House_name") + " | Class: " + res.getString("Class"));
            }

            res.close();
            stmt.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("# ERR: SQLException in " + e.getMessage());
            System.out.println(" (MySQL error code: " + e.getErrorCode());
            System.out.println(", SQLState: " + e.getSQLState() + " )");
        }
    }

    public static void residentandhouse() {
        try {
            // Create a connection
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/Housing", "root", "");

            // Query to join Residents and Houses tables and retrieve information
            String query = "SELECT r.Name AS ResidentName, r.Class, h.House_name AS HouseName FROM Residents r INNER JOIN Houses h ON r.House_name = h.House_name";

            stmt = con.createStatement();
            res = stmt.executeQuery(query);

            System.out.println("Residents and their Houses:");

            while (res.next()) {
                System.out.println(res.getString("ResidentName") + " | " + res.getString("Class") + " | " + res.getString("HouseName"));
            }

            res.close();
            stmt.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("# ERR: SQLException in " + e.getMessage());
            System.out.println(" (MySQL error code: " + e.getErrorCode());
            System.out.println(", SQLState: " + e.getSQLState() + " )");
        }
    }
}
```

