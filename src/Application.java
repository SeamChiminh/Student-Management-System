import fileIO.controller.StudentController;
import fileIO.utils.Student;
import fileIO.view.Menu;
import fileIO.view.StudentView;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Student> students = new ArrayList<>();
        StudentController student = new StudentController();
        boolean exit = false;
//        Menu.banner();

        Menu.menu();
        while (!exit) {
            System.out.print("🖲️Insert Option: ");
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    System.out.println("=".repeat(50));
                    student.addNewStudent();
                    break;
                case 2:
                    System.out.println("=".repeat(50));
                    StudentView.listAllStudent(students);
                    break;
                case 3:
                    System.out.println("3. COMMIT DATA TO FILE");
                    student.commitChanges();
                    break;
                case 4:
                    System.out.println("4. SEARCH STUDENT");
                    break;
                case 5:
                    System.out.println("5. UPDATE STUDENT'S INFO BY ID");
                    break;
                case 6:
                    System.out.println("6 DELETE STUDENT'S INFO BY ID");
                    break;
                case 7:
                    System.out.println("7. GENERATE DATA TO FILE");
                    break;
                case 8:
                    System.out.println("8. CLEAR ALL DATA FROM DATA STORE");
                    break;
                case 0,9:
                    System.out.println("Exiting...");
                    exit = true;
                    break;
                default:
                    System.out.println("⚠️Invalid choice. Please enter a valid option.");


            }
        }



    }
}