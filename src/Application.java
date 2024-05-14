import fileIO.controller.StudentController;
import fileIO.utils.Student;
import fileIO.view.Color;
import fileIO.view.Menu;
import fileIO.view.StudentView;
import java.util.List;
import java.util.Scanner;

public 3#class Application implements Color {
    #

   # public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StudentController studentController = new StudentController();
        boolean exit = false;

        double timeTakenInSeconds = studentController.getTimeToReadDataFromFileInSeconds();
        System.out.println("⏰ SPENT TIME FOR READING DATA: " + timeTakenInSeconds + " S");

        studentController.loadStudentsFromFile();
        int numberOfRecords = studentController.getNumberOfRecordsInFile();
        System.out.println("📁 NUMBERS OF RECORD IN DATA SOURCE FILE: " + numberOfRecords);
        Menu.banner();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            studentController.saveChanges();
        }));

        while (!exit) {
            Menu.menu();
            System.out.print("🖲️Insert Option: ");
            String choiceStr = sc.nextLine();

            try {
                int choice = Integer.parseInt(choiceStr);
                switch (choice) {
                    case 1:
                        System.out.println("+" + "~".repeat(117) + "+");
                        studentController.addNewStudent();
                        break;
                    case 2:
                        StudentView.listAllStudent(studentController.getStudents());
                        break;
                    case 3:
                        System.out.println("+" + "~".repeat(117) + "+");
                        studentController.commitDataToFile();
                        break;
                    case 4:
                        boolean searchBack = false;
                        while (!searchBack) {
                            System.out.println("🔎 SEARCH STUDENT");
                            System.out.println("+" + "~".repeat(117) + "+");
                            System.out.println("1. SEARCH BY ID");
                            System.out.println("2. SEARCH BY NAME");
                            System.out.println("- (BACK/B) TO BACK");
                            System.out.println("+" + "~".repeat(117) + "+");
                            System.out.print(">> Insert option: ");
                            String searchOption = sc.nextLine();

                            switch (searchOption.toUpperCase()) {
                                case "1":
                                    System.out.println("+" + "~".repeat(117) + "+");
                                    System.out.println("🔎 SEARCH STUDENT BY ID");
                                    System.out.print(">>> Insert student's ID: ");
                                    String searchId = sc.nextLine();
                                    Student foundById = studentController.searchStudentById(searchId);
                                    if (foundById != null) {
                                        StudentView.displayStudentByID(foundById);
                                        System.out.println(">>> Press Enter to continue...");
                                        sc.nextLine();
                                    } else {
                                        System.out.println("+" + "~".repeat(117) + "+");
                                        System.out.println(RED + "⚠️ Student not found by ID! " + RESET);
                                        System.out.println("+" + "~".repeat(117) + "+");
                                    }
                                    break;
                                case "2":
                                    System.out.println("+" + "~".repeat(117) + "+");
                                    System.out.println("🔎 SEARCH STUDENT BY NAME");
                                    System.out.print(">>> Insert student's Name: ");
                                    String searchName = sc.nextLine();
                                    List<Student> foundByName = studentController.searchStudentsByName(searchName);
                                    if (!foundByName.isEmpty()) {
                                        StudentView.listAllStudent(foundByName);
                                    } else {
                                        System.out.println("+" + "~".repeat(117) + "+");
                                        System.out.println(RED + "⚠️ No students found by name! " + RESET);
                                        System.out.println("+" + "~".repeat(117) + "+");
                                    }
                                    break;
                                case "B":
                                    searchBack = true;
                                    break;
                                default:
                                    System.out.println(RED + "⚠️ Invalid option. Please select 1, 2, or B." + RESET);
                                    System.out.println("+" + "~".repeat(117) + "+");
                                    break;
                            }
                        }
                        break;
                    case 5:
                        System.out.println("+" + "~".repeat(117) + "+");
                        studentController.updateStudentInfoById();
                        break;
                    case 6:
                        System.out.println("+" + "~".repeat(117) + "+");
                        studentController.deleteStudentById();
                        break;
                    case 7:
                        System.out.println("+" + "~".repeat(117) + "+");
                        System.out.print("🖨️ Number of objects you want to generate (100M - 1_000_000_000): ");
                        int numRecords = sc.nextInt();
                        sc.nextLine();
                        studentController.generateDataToFile(numRecords);
                        break;
                    case 8:
                        System.out.println("+" + "~".repeat(117) + "+");
                        System.out.println("🗑️ DELETE ALL DATA STORE");
                        studentController.deleteAllStudents();
                        break;
                    case 0:
                    case 99:
                        System.out.println("+" + "~".repeat(117) + "+");
                        System.out.println("📤 Exiting the program...");
                        System.out.println("+" + "~".repeat(117) + "+");
                        exit = true;
                        break;
                    default:
                        System.out.println("+" + "~".repeat(117) + "+");
                        System.out.println(RED + "⚠️ Invalid choice. Please enter a valid option."+ RESET);
                        System.out.println("+" + "~".repeat(117) + "+");
                }
            } catch (NumberFormatException e) {
                System.out.println("+" + "~".repeat(117) + "+");
                System.out.println( RED+ "⚠️ Invalid choice. Please enter a valid number." + RESET);
                System.out.println("+" + "~".repeat(117) + "+");
            }
        }
    }
}