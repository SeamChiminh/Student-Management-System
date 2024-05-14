import fileIO.utils.Student;
import fileIO.utils.StudentServiceImp;
import fileIO.view.Menu;
public class Application {
    public static void main(String[] args) {
//        Menu.banner();
//        Menu.menu();
        StudentServiceImp studentServiceImp = new StudentServiceImp();
        studentServiceImp.addNewStudent();
    }
}
