package fileIO.controller;
import fileIO.model.StudentService;
import fileIO.utils.Student;
import fileIO.view.Color;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class StudentController implements Color{
    Scanner sc = new Scanner(System.in);
    private static List<Student> students = new ArrayList<>();
    private boolean changesCommitted;
    private boolean delete;

    int randomNumber = generateRandomNumber(1000, 9999);
    String id = randomNumber + "CSTAD";

    // Method to generate a random number in a specified range
    private int generateRandomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }

    private String serializeProduct(Student student) {
        return String.format("%s,%s,%d,%d,%d,%s,%s,%s", student.getId(), student.getName(), student.getDay(), student.getMonth(),
                student.getYear(), student.getClassroom(), student.getSubject(), student.getCreateDate());
    }

    public void addNewStudent(){
        Scanner sc = new Scanner(System.in);

        System.out.println("🟢 INSERT STUDENT'S INFO");
        System.out.print("➡️ Insert student's name: ");
        String name = sc.nextLine();
        while (!isValidName(name)) {
            System.out.print(RED);
            System.out.println("⚠️ Name cannot contain numbers. Please enter a valid name.");
            System.out.print(RESET);
            System.out.print("➡️ Insert student's name: ");
            name = sc.nextLine();
        }

        System.out.println("🟢 STUDENT DATE OF BIRTH");

        Integer year = validaYear(sc, "➡️ Year (number): ");
        Integer month = validMonthDay(sc, "➡️ Month (number): ", 1, 12);
        Integer day = validMonthDay(sc, "➡️ Day (number): ", 1, 31);

        sc.nextLine(); // Consume newline

        System.out.println("🔵 YOU CAN INSERT MULTI CLASSES BY SPLITTING [,] SYMBOL (C1, C2).");
        System.out.print("➡️ Student's class: ");
        String classNamesInput = sc.nextLine();
        String[] classNames = classNamesInput.split(",");
        List<String> classes = new ArrayList<>();
        for (String className : classNames) {
            classes.add(className.trim());
        }

        System.out.println("🔵 YOU CAN INSERT MULTI SUBJECTS BY SPLITTING [,] SYMBOL (S1, S2).");
        System.out.print("➡️ Subject's studied: ");
        String subjectsInput = sc.nextLine();
        String[] subjects = subjectsInput.split(",");
        List<String> subjectsList = new ArrayList<>();
        for (String subject : subjects) {
            subjectsList.add(subject.trim());
        }

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
        String serializedProduct = serializeProduct(newStudent);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/transaction.dat", true))) {
            writer.write(serializedProduct + "\n");
            System.out.println("✅ STUDENT HAS BEEN ADD SUCCESSFULLY");
        } catch (IOException e) {
            System.err.println("⚠️ Error writing to transaction file: " + e.getMessage());
        }
        System.out.println("⚠️ TO STORE DATA PERMANENTLY, PLEASE COMMIT IT (START OPTIONS 3).");

    }

    private boolean isValidName(String name) {
        return name.matches("[a-zA-Z]+");
    }

    private Integer validaYear(Scanner sc, String message) {
        while (true) {
            System.out.print(message);
            if (sc.hasNextInt()) {
                return sc.nextInt();
            } else {

                System.out.println(RED +"⚠️ Invalid input! Please enter a valid number." + RESET);
                sc.next(); // Consume non-integer input
            }
        }
    }

    private Integer validMonthDay(Scanner sc, String message, int min, int max) {
        while (true) {
            Integer input = validaYear(sc, message);
            if (input >= min && input <= max) {
                return input;
            } else {

                System.out.println(RED +"⚠️ Invalid input! Input must be between " + min + " and " + max + "." + RESET);
            }
        }
    }

    public boolean hasUncommittedTransactions() {
        File file = new File("data/transaction.dat");
        return file.exists() && file.length() > 0;
    }

    public void commitChanges() {
        System.out.print("❔ Are you sure you want to commit? [Y/N]: ");
        String answer = sc.nextLine();
        if(answer.equalsIgnoreCase("y")){

            if (delete){
                try (BufferedReader reader = new BufferedReader(new FileReader("data/transaction.dat"));
                     BufferedWriter writer = new BufferedWriter(new FileWriter("data/student.dat"))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        writer.write(line);
                        writer.newLine();
                    }
                    System.out.println("✅ Changes committed successfully!");
                    this.changesCommitted = true;
                } catch (IOException e) {
                    System.err.println("Error committing changes: " + e.getMessage());
                }
                delete=false;
                return;
            }
            if (students.isEmpty()){
                System.out.println("⚠️ No data, can't commit...!");
            }
            else{
                if (!hasUncommittedTransactions()) {
                    System.out.println("No uncommitted transactions to commit.");
                    return;
                }
                try (BufferedReader reader = new BufferedReader(new FileReader("data/transaction.dat"));
                     BufferedWriter writer = new BufferedWriter(new FileWriter("data/student.dat"))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        writer.write(line);
                        writer.newLine();
                    }
                    System.out.println("✅ Changes committed successfully!");
                    this.changesCommitted = true;
                } catch (IOException e) {
                    System.err.println("Error committing changes: " + e.getMessage());
                }
            }
        }
        else if(answer.equalsIgnoreCase("n")){
            System.out.println("❗You didn't commit anything...!");
        }
        else System.out.println("Invalid input...!");
    }


}
