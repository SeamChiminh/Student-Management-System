package fileIO.view;

import fileIO.controller.StudentController;
import fileIO.utils.Student;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;

import java.util.List;
import java.util.Scanner;

public class StudentView implements Color{

    static Integer currentPage = 1;
    static Integer pageSize = 1;
    static Integer rowPerPage = 4;

    public static void listAllStudent(List<Student> students) {
        pageSize = (int) Math.ceil((double) students.size() / rowPerPage);
        int startIndex = (currentPage * rowPerPage) - rowPerPage; // 5
        int endIndex = Math.min(startIndex + rowPerPage, students.size());

        if (students.isEmpty()) {
            System.out.println("+" + "~".repeat(117) + "+");
            System.out.println( RED + "⚠️ No student data available!" + RESET);
            System.out.println("+" + "~".repeat(117) + "+");
            return;
        }

        System.out.println("+" + "~".repeat(117) + "+");
        System.out.println("📃Total Page : " + pageSize);
        System.out.println("💾 STUDENT'S DATA");

        Table table = new Table(6, BorderStyle.UNICODE_BOX_DOUBLE_BORDER_WIDE, ShownBorders.ALL);
        CellStyle cellStyle = new CellStyle(CellStyle.HorizontalAlign.CENTER);
        table.setColumnWidth(0, 30, 30);
        table.setColumnWidth(1, 30, 30);
        table.setColumnWidth(2, 30, 30);
        table.setColumnWidth(3, 30, 30);
        table.setColumnWidth(4, 30, 30);
        table.setColumnWidth(5, 30, 30);
        table.addCell("ID", cellStyle);
        table.addCell("STUDENT'S NAME", cellStyle);
        table.addCell("STUDENT'S DATE OF BIRTH", cellStyle);
        table.addCell("STUDENT CLASS", cellStyle);
        table.addCell("STUDENT'S SUBJECT", cellStyle);
        table.addCell("CREATE/ UPDATE AT", cellStyle);

        try {
            for (int i = startIndex; i < endIndex; i++) {
                table.addCell(students.get(i).getId(), cellStyle);
                table.addCell(students.get(i).getName(), cellStyle);
                table.addCell(students.get(i).getYear().toString() + "-" +
                        students.get(i).getMonth().toString() + "-" +
                        students.get(i).getDay().toString(), cellStyle);
                table.addCell(students.get(i).getClassroom(), cellStyle);
                table.addCell(students.get(i).getSubject(), cellStyle);
                table.addCell(students.get(i).getCreateDate(), cellStyle);
            }

            StudentController stController = new StudentController();
            System.out.println(table.render());
            System.out.println("+" + "~".repeat(117) + "+");
            System.out.println("[*] Page number: " + currentPage + "\t\t" + " [*] Actual record: " + (endIndex - startIndex) + "\t\t" + " [+] All Record: " + stController.getNumberOfRecordsInFile());
            System.out.println("[+] Previous (P/p) \t\t [+] Next (N/n) \t\t\t [+] Back(B/b)");
            System.out.println("+" + "~".repeat(117) + "+");
            System.out.print("[+] Insert to Navigation [P/N]: ");

            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();

            while (true) {

                if (input.equalsIgnoreCase("P") || input.equalsIgnoreCase("previous")) {
                    if (currentPage > 1) {
                        currentPage--;
                        listAllStudent(students);
                        break;
                    } else {
                        System.out.println(YELLOW +"⚠️ First page." + RESET);
                    }
                } else if (input.equalsIgnoreCase("N") || input.equalsIgnoreCase("next")) {
                    if (currentPage < pageSize) {
                        currentPage++;
                        listAllStudent(students);
                        break;
                    } else {
                        System.out.println( YELLOW + "⚠️ Last page." + RESET);
                    }
                } else if (input.equalsIgnoreCase("B") || input.equalsIgnoreCase("back")) {
                    return;
                } else {
                    System.out.println( RED + "⚠️ Invalid input." + RESET);
                }

                System.out.println("+" + "~".repeat(117) + "+");
                System.out.println("[*] Page number: " + currentPage + "\t\t" + " [*] Actual record: " + (endIndex - startIndex) + "\t\t" + " All Record: " + stController.getNumberOfRecordsInFile());
                System.out.println("[+] Previous (P/p) \t\t [+] Next (N/n) \t\t\t [+] Back(B/b)");
                System.out.println("+" + "~".repeat(117) + "+");
                System.out.print("[+] Insert to Navigation [P/N]: ");
                input = scanner.nextLine();
            }

        } catch (IndexOutOfBoundsException e) {
            System.out.println(YELLOW + "No records to display." + RESET);
        }
    }

    public static void displayStudentByID(Student student) {

        System.out.println(".".repeat(103));
        System.out.println("[*] STUDENT'S INFO.");
        Table table = new Table(2, BorderStyle.CLASSIC_COMPATIBLE, ShownBorders.ALL);
        CellStyle cellStyle = new CellStyle(CellStyle.HorizontalAlign.CENTER);
        table.setColumnWidth(0, 50, 50);
        table.setColumnWidth(1, 50, 50);
        table.addCell("STUDENT'S INFORMATION", cellStyle);
        table.addCell("DATA", cellStyle);

        table.addCell("ID", cellStyle);
        table.addCell(student.getId(), cellStyle);
        table.addCell("NAME", cellStyle);
        table.addCell(student.getName(), cellStyle);

        table.addCell("BIRTH", cellStyle);
        table.addCell(student.getYear() + "-" + student.getMonth() + "-" + student.getDay(), cellStyle);

        table.addCell("CLASS", cellStyle);
        table.addCell(student.getClassroom(), cellStyle);

        table.addCell("SUBJECT", cellStyle);
        table.addCell(student.getSubject(), cellStyle);

        System.out.println(table.render());
    }

}
