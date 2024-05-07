package fileIO.controller;
import fileIO.utils.Student;
import fileIO.view.Color;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StudentController implements Color {
    List<Student> students = new ArrayList<>();

    public void AddNewStudent() {
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
        String className = sc.nextLine();

        System.out.println("🔵 YOU CAN INSERT MULTI SUBJECTS BY SPLITTING [,] SYMBOL (S1, S2).");
        System.out.print("➡️ Subject's studied: ");
        String subject = sc.nextLine();

        LocalDate addDate = LocalDate.now();
        String add_date = addDate.toString();

        Student newStudent = new Student();
        newStudent.setName(name);
        newStudent.setYear(year);
        newStudent.setMonth(month);
        newStudent.setDay(day);
        newStudent.setSubject(subject);
        newStudent.setClassroom(className);
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
}
