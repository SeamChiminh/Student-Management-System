package fileIO.controller;
import fileIO.model.StudentService;
import fileIO.utils.Student;
import fileIO.view.Color;
import fileIO.view.StudentView;

import java.io.*;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class StudentController implements Color, StudentService {
    private static List<Student> students = new ArrayList<>();
    private boolean changesCommitted;
    private boolean delete;

    public List<Student> getStudents() {
        return students;
    }

    public int generateRandomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }

    String serializeStudent(Student student) {
        return String.format("%s,%s,%d,%d,%d,%s,%s,%s", student.getId(), student.getName(), student.getDay(), student.getMonth(),
                student.getYear(), student.getClassroom(), student.getSubject(), student.getCreateDate());
    }

    public void addNewStudent(){
        Scanner sc = new Scanner(System.in);

        System.out.println("🧑🏻‍🚀 INSERT STUDENT'S INFO");
        System.out.print("[+] Insert student's name: ");
        String name = sc.nextLine();
        while (!isValidName(name)) {
            System.out.println(RED + "⚠️ Name cannot contain numbers. Please enter a valid name." + RESET);
            System.out.print("[+] Insert student's name: ");
            name = sc.nextLine();
        }

        System.out.println("[+] STUDENT DATE OF BIRTH");

        Integer year = validaYear(sc, "> Year (number): ");
        Integer month = validateMonth(sc, "> Month (number): ");
        Integer day = validateDay(sc, "> Day (number): ");

        sc.nextLine();

        System.out.println( YELLOW + "🔔 YOU CAN INSERT MULTI CLASSES BY SPLITTING [,] SYMBOL (C1, C2)." + RESET);
        System.out.print("[+] Student's class: ");
        String classNamesInput = sc.nextLine();
        String[] classNames = classNamesInput.split(",");
        List<String> classes = new ArrayList<>();
        for (String className : classNames) {
            classes.add(className.trim());
        }

        System.out.println( YELLOW + "🔔 YOU CAN INSERT MULTI SUBJECTS BY SPLITTING [,] SYMBOL (S1, S2)." + RESET);
        System.out.print("[+] Subject's studied: ");
        String subjectsInput = sc.nextLine();
        String[] subjects = subjectsInput.split(",");
        List<String> subjectsList = new ArrayList<>();
        for (String subject : subjects) {
            subjectsList.add(subject.trim());
        }

        int randomNumber;
        String id;
        do{
            randomNumber = generateRandomNumber(1000,9999);
            id = randomNumber + "CSTAD";
        }while (isDuplicateID(id));

        LocalDate date = LocalDate.now();
        String createDate = date.toString();

        Student newStudent = new Student();
        newStudent.setId(id);
        newStudent.setName(name);
        newStudent.setYear(year);
        newStudent.setMonth(month);
        newStudent.setDay(day);
        newStudent.setSubject(subjectsInput);
        newStudent.setClassroom(classNamesInput);
        newStudent.setCreateDate(createDate);

        students.add(newStudent);
        String serializedProduct = serializeStudent(newStudent);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/student.dat", true))) {
            writer.write(serializedProduct + "\n");
            System.out.println("-".repeat(100));
            System.out.println(GREEN +"💾 STUDENT HAS BEEN ADD SUCCESSFULLY" + RESET);
        } catch (IOException e) {
            System.err.println( YELLOW + "⚠️ Error writing to transaction file: " + e.getMessage() + RESET);
        }
        System.out.println( YELLOW+ "⚠️ TO STORE DATA PERMANENTLY, PLEASE COMMIT IT (START OPTIONS 3)." + RESET);
        System.out.println("-".repeat(100));
    }

    private boolean isDuplicateID(String id) {
        for(Student student : students)
        {
            if(student.getId().equals(id))
            {
                return true;
            }
        }
        return false;
    }

    private boolean isValidName(String name) {
        return name.matches("[a-zA-Z\\s]+");
    }

   private static Integer validaYear(Scanner sc, String message) {
       while (true) {
           System.out.print(message);
           if (sc.hasNextInt()) {
               int year = sc.nextInt();
               if (year >= 1900 && year <= 2024) {
                   return year;
               } else {
                   System.out.println(RED + "⚠️ Invalid input! Please enter a year between 1900 and 2024." + RESET);
               }
           } else {
               System.out.println(RED + "⚠️ Invalid input! Please enter a valid number." + RESET);
               sc.next();
           }
       }
   }

    private static Integer validateDay(Scanner sc, String message) {
        while (true) {
            System.out.print(message);
            if (sc.hasNextInt()) {
                int day = sc.nextInt();
                if (day >= 1 && day <= 31) {
                    return day;
                } else {
                    System.out.println(RED + "⚠️ Invalid input! Please enter a day between 1 and 31." + RESET);
                }
            } else {
                System.out.println(RED + "⚠️ Invalid input! Please enter a valid number." + RESET);
                sc.next();
            }
        }
    }

    private static Integer validateMonth(Scanner sc, String message) {
        while (true) {
            System.out.print(message);
            if (sc.hasNextInt()) {
                int month = sc.nextInt();
                if (month >= 1 && month <= 12) {
                    return month;
                } else {
                    System.out.println(RED + "⚠️ Invalid input! Please enter a month between 1 and 12." + RESET);
                }
            } else {
                System.out.println(RED + "⚠️ Invalid input! Please enter a valid number." + RESET);
                sc.next();
            }
        }
    }

    public void loadStudentsFromFile() {
        students.clear(); // Clear existing data to prevent duplicates
        try (BufferedReader reader = new BufferedReader(new FileReader("data/student.dat"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 8) {
                    try {
                        Student student = new Student(parts[0], parts[1], Integer.parseInt(parts[2]), Integer.parseInt(parts[3]),
                                Integer.parseInt(parts[4]), parts[5], parts[6], parts[7]);
                        if (!isDuplicateID(student.getId())) {
                            students.add(student);
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("Error parsing data from file: " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading student data from file: " + e.getMessage());
        }
    }

    public List<Student> searchStudentsByName(String name) {
        List<Student> foundStudents = new ArrayList<>();
        for (Student student : students) {
            if (student.getName().toLowerCase().contains(name.toLowerCase())) {
                foundStudents.add(student);
            }
        }
        return foundStudents;
    }

    public Student searchStudentById(String id) {
        for (Student student : students) {
            if (student.getId().equalsIgnoreCase(id)) {
                return student;
            }
        }
        return null;
    }

    public void updateStudentInfoById() {
        Scanner sc = new Scanner(System.in);
        boolean found = false;

        while (!found) {
            System.out.println("[*] UPDATE STUDENT'S INFO BY ID");
            System.out.print("> Insert student's ID: ");
            String id = sc.nextLine();

            Student studentToUpdate = searchStudentById(id);

            if (studentToUpdate != null) {
                found = true;
                StudentView.displayStudentByID(studentToUpdate);

                System.out.println("+" + "~".repeat(117) + "+");
                System.out.println("[+] UPDATE STUDENT'S INFORMATION:");
                System.out.println("+" + "~".repeat(117) + "+");

                System.out.println("1. UPDATE STUDENT'S NAME");
                System.out.println("2. UPDATE STUDENT'S DATE OF BIRTH");
                System.out.println("3. UPDATE STUDENT'S CLASS");
                System.out.println("4. UPDATE STUDENT'S SUBJECT");
                System.out.println("0. Cancel");
                System.out.println("+" + "~".repeat(117) + "+");
                System.out.print("> Insert option: ");
                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {
                    case 1:
                        System.out.print("[+] Insert New student's name: ");
                        studentToUpdate.setName(sc.nextLine());
                        break;
                    case 2:
                        System.out.println("[+] Insert New student's date of birth:");
                        System.out.print("> Year (number): ");
                        int year = sc.nextInt();
                        System.out.print("> Month (number): ");
                        int month = sc.nextInt();
                        System.out.print("> Day (number): ");
                        int day = sc.nextInt();
                        studentToUpdate.setYear(year);
                        studentToUpdate.setMonth(month);
                        studentToUpdate.setDay(day);
                        break;
                    case 3:
                        System.out.print("[+] Insert New student's class: ");
                        studentToUpdate.setClassroom(sc.nextLine());
                        break;
                    case 4:
                        System.out.print("[+] Insert New student's subject: ");
                        studentToUpdate.setSubject(sc.nextLine());
                        break;
                    case 0:
                        System.out.println("❌ Update cancelled.");
                        System.out.println("+" + "~".repeat(117) + "+");
                        return;
                    default:
                        System.out.println("Invalid choice.");
                        return;
                }

                LocalDate currentDate = LocalDate.now();
                studentToUpdate.setCreateDate(currentDate.toString());

                StudentView.displayStudentByID(studentToUpdate);
                System.out.print("💾 UPDATE SUCCESSFULLY, PRESS TO CONTINUE...");
                sc.nextLine();


                try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("data/student.dat")))) {
                    for (Student student : students) {
                        writer.println(serializeStudent(student));
                    }
                } catch (IOException e) {
                    System.err.println("⚠️ Error updating data: " + e.getMessage());
                }

            } else {
                System.out.println("+" + "~".repeat(117) + "+");
                System.out.println("⚠️ Student not found. Please try again.");
                System.out.println("+" + "~".repeat(117) + "+");
            }
        }
    }

    public double getTimeToReadDataFromFileInSeconds() {
        long startTime = System.currentTimeMillis(); // Record start time
        try (BufferedReader reader = new BufferedReader(new FileReader("data/student.dat"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 8) {
                    try {
                        Student student = new Student(parts[0], parts[1], Integer.parseInt(parts[2]), Integer.parseInt(parts[3]),
                                Integer.parseInt(parts[4]), parts[5], parts[6], parts[7]);
                        students.add(student);
                    } catch (NumberFormatException e) {
                        System.err.println("Error parsing data: " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading student data from file: " + e.getMessage());
        }
        long endTime = System.currentTimeMillis(); // Record end time
        long elapsedTimeMillis = endTime - startTime;
        return elapsedTimeMillis / 1000.0; // Return elapsed time in seconds
    }

    public int getNumberOfRecordsInFile() {
        int count = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader("data/student.dat"))) {
            while (reader.readLine() != null) {
                count++;
            }
        } catch (IOException e) {
            System.err.println("Error counting records in file: " + e.getMessage());
        }
        return count;
    }

    public void commitDataToFile() {
        try {
            List<Student> studentsToCommit = new ArrayList<>();
            boolean dataToCommit = false;

            try (BufferedReader reader = new BufferedReader(new FileReader("transaction/transaction-addNew.dat"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length == 8) {
                        try {
                            Student student = new Student(parts[0], parts[1], Integer.parseInt(parts[2]), Integer.parseInt(parts[3]),
                                    Integer.parseInt(parts[4]), parts[5], parts[6], parts[7]);
                            studentsToCommit.add(student);
                            dataToCommit = true;
                        } catch (NumberFormatException e) {
                            System.err.println("Error parsing data from transaction file: " + e.getMessage());
                        }
                    }
                }
            } catch (IOException e) {
                System.err.println("Error reading transaction data from file: " + e.getMessage());
            }

            if (!dataToCommit) {
                System.out.println(RESET + "⚠️ No data to commit" + RESET);
                System.out.println("~".repeat(100));
                return;
            }

            Scanner scanner = new Scanner(System.in);
            System.out.print(YELLOW + "⚠️ Are you sure you want to commit data? (Yes/No): " + RESET);
            String confirm = scanner.nextLine().trim();

            if (confirm.equalsIgnoreCase("Yes") || confirm.equalsIgnoreCase("Y")) {

                try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/student.dat", true))) {
                    for (Student student : studentsToCommit) {
                        String serializedStudent = serializeStudent(student);
                        writer.write(serializedStudent + "\n");
                    }
                    System.out.println( GREEN + "✅ Data committed successfully." + RESET);
                    System.out.println(".".repeat(100));
                } catch (IOException e) {
                    System.err.println( RED+ "⚠️ Error writing data to file: " + e.getMessage() + RESET);
                }

                // Clear the transaction file after committing data
                try (PrintWriter writer = new PrintWriter("transaction/transaction-addNew.dat")) {
                    writer.print("");

                } catch (FileNotFoundException e) {
                    System.err.println( RED + "⚠️ Error clearing transaction data: " + e.getMessage() + RESET);
                }
            } else if (confirm.equalsIgnoreCase("No") || confirm.equalsIgnoreCase("N")) {
                System.out.println(YELLOW + "⚠️ Commit operation cancelled." + RESET);
            } else {
                System.out.println(".".repeat(119));
                System.out.println(RED +"⚠️ Invalid input. Please enter 'Yes' or 'No'." + RESET);
                System.out.println(".".repeat(119));
            }
        } catch (Exception e) {
            System.err.println( RED + "⚠️ Error: " + e.getMessage() + RESET);
        }
    }

    public StudentController() {
        loadStudentsFromFile();
    }

    public void saveChanges() {
        if (changesCommitted || delete) {
            commitDataToFile();
        }
    }

    public void generateDataToFile(int numRecords) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/student.dat",true))) {
            Instant start = Instant.now();
            Random random = new Random();
            for (int i = 0; i < numRecords; i++) {
                int randomNumber = 1000 + random.nextInt(9000);
                String id = randomNumber + "CSTAD";
                String name = "Student" + (i + 1);
                int year = 1990 + random.nextInt(20);
                int month = 1 + random.nextInt(12);
                int day = 1 + random.nextInt(28);
                String classroom = "Class" + (random.nextInt(5) + 1);
                String subject = "Subject" + (random.nextInt(5) + 1);
                LocalDate date = LocalDate.now();
                String createDate = date.toString();

                String serializedStudent = String.format("%s,%s,%d,%d,%d,%s,%s,%s", id, name, day, month, year, classroom, subject, createDate);
                writer.write(serializedStudent + "\n");
            }
            Instant end = Instant.now();
            Duration timeElapsed = Duration.between(start, end);
            double seconds = timeElapsed.toMillis() / 1000.0;
            System.out.println("~".repeat(119));
            System.out.println("💾 Data generated successfully.");
            System.out.println("⏰ Time spent for generating data: " + seconds + " S");
            System.out.println("✅ WROTE DATA " + numRecords + " RECORD SUCCESSFULLY.");
            System.out.println("~".repeat(119));
        } catch (IOException e) {
            System.err.println("⚠️ Error generating data to student_generated.dat file: " + e.getMessage());
        }
    }

    public void deleteStudentById() {
        Scanner sc = new Scanner(System.in);
        boolean found = false;

        while (!found) {
            System.out.println("[*] DELETE STUDENT'S INFO BY ID");
            System.out.print("> Insert student's ID: ");
            String id = sc.nextLine();

            Student studentToDelete = searchStudentById(id);

            if (studentToDelete != null) {
                found = true;
                StudentView.displayStudentByID(studentToDelete);
                System.out.print("[+] Are you sure you want to delete this student? (Yes/No): ");
                String confirm = sc.nextLine().trim();

                if (confirm.equalsIgnoreCase("Yes") || confirm.equalsIgnoreCase("Y")) {
                    students.remove(studentToDelete);
                    System.out.println("+" + "~".repeat(117) + "+");
                    System.out.println("✅ User data has been deleted successfully.");
                    System.out.println("+" + "~".repeat(117) + "+");

                    try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("data/student.dat")))) {
                        for (Student student : students) {
                            writer.println(serializeStudent(student));
                        }

                    } catch (IOException e) {
                        System.err.println("⚠️ Error updating data in file: " + e.getMessage());
                    }
                } else if (confirm.equalsIgnoreCase("No") || confirm.equalsIgnoreCase("N")) {
                    System.out.println(".".repeat(103));
                    System.out.println("❌ Deletion cancelled.");
                    System.out.println(".".repeat(103));
                } else {
                    System.out.println("+" + "~".repeat(117) + "+");
                    System.out.println("⚠️ Invalid input. Please enter 'Yes' or 'No'.");
                    System.out.println("+" + "~".repeat(117) + "+");
                }
            } else {
                System.out.println("+" + "~".repeat(117) + "+");
                System.out.println("⚠️ Student not found. Please try again.");
                System.out.println("+" + "~".repeat(117) + "+");
            }
        }
    }

    public void deleteAllStudents() {
        if (students.isEmpty()) {
            System.out.println( RED + "⚠️ There are no students to delete." + RESET);
            System.out.println("~".repeat(119));
            return;
        }

        Scanner sc = new Scanner(System.in);
        System.out.print(YELLOW + "⚠️ Are you sure you want to delete all student data? (Yes/No): " + RESET);
        String confirm = sc.nextLine().trim();

        if (confirm.equalsIgnoreCase("Yes") || confirm.equalsIgnoreCase("Y")) {
            students.clear();
            System.out.println("~".repeat(119));
            System.out.println("✅ All student data has been deleted successfully.");
            System.out.println("~".repeat(119));

            try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("data/student.dat")))) {
            } catch (IOException e) {
                System.err.println("⚠️ Error updating data in file: " + e.getMessage());
            }
        } else if (confirm.equalsIgnoreCase("No") || confirm.equalsIgnoreCase("N")) {
            System.out.println("~".repeat(119));
            System.out.println("❌ Deletion cancelled.");
            System.out.println("~".repeat(119));
        } else {
            System.out.println(RED + "⚠️ Invalid input. Please enter 'Yes' or 'No'." + RESET);
        }
    }
}

