import fileIO.controller.StudentController;
import fileIO.view.Menu;

import javax.swing.*;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean exit = false;
//        Menu.banner();
        Menu.menu();

        while (!exit) {
            System.out.print("🖲️Insert Option: ");
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    StudentController student = new StudentController();
                    System.out.println("=".repeat(50));
                    student.AddNewStudent();
                    break;
                case 2:
                    System.out.println("2. LIST ALL STUDENT");
                    break;
                case 3:
                    System.out.println("3. COMMIT DATA TO FILE");
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