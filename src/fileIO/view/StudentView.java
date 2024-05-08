package fileIO.view;

import fileIO.utils.Student;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.Table;

import java.util.List;

public class StudentView {
    static Integer currentPage = 1;
    static Integer pageSize = 1;
    static Integer rowPerPage = 5;

    public static void listAllStudent(List<Student> students) {
        pageSize = (int) Math.ceil((double) students.size() / rowPerPage);
        int startIndex = (currentPage * rowPerPage) - rowPerPage; // 5
        int endIndex = Math.min(startIndex + rowPerPage, students.size());
        System.out.println("Total Page : " + pageSize);
        System.out.println("[*] STUDENT'S DATA");

        Table table = new Table(6, BorderStyle.UNICODE_BOX_DOUBLE_BORDER_WIDE);
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

            System.out.println(table.render());
            System.out.println("+" + "~".repeat(150) + "+");
            System.out.println("[*] Page number: " + currentPage + "\t\t" + " [*] Actual record: " + "\t\t" + " All Record: " + students.size());
            System.out.println("[+] Previous (P/p) \t\t [+] Next (N/n) \t\t\t [+] Back(B/b)");
            System.out.println("+" + "~".repeat(150) + "+");

        } catch (IndexOutOfBoundsException e) {
            System.out.println("No records to display.");
        }


    }

}
