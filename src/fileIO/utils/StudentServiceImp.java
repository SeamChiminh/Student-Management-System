package fileIO.utils;
import javax.xml.crypto.Data;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;
import java.util.UUID;
import java.util.zip.DataFormatException;

interface StudentService {
    abstract void addNewStudent();
    abstract void listStudent();
}
public class StudentServiceImp implements StudentService {
    @Override
    public void addNewStudent() {
        try{
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("./output/student.dat",true));
            Student student = new Student();
            Scanner sc = new Scanner(System.in);
            student.setUUID(UUID.randomUUID().toString());

            System.out.print("[+] Insert name : ");
            String name = sc.next();
            student.setName(name);

            System.out.println("🎂[+] Day of birth ");
            System.out.print("- Day   : ");
            int day = sc.nextInt();
            System.out.print("- Month : ");
            int month = sc.nextInt();
            System.out.print("- Year  : ");
            int year = sc.nextInt();
            LocalDate dob = LocalDate.of(year, month, day);
            student.getDob();

            System.out.print("[+] Insert room : ");
            String classroom = sc.next();
            student.setClassroom(classroom);

            System.out.print("[+] Insert subject : ");
            String subject = sc.next();
            student.setSubject(subject);

            String data = student.getUUID()+","+
                    student.getName()+","+
                    student.getDob()+","+
                    student.getClassroom()+","+
                    student.getSubject()+"\n";
            bufferedWriter.write(data);
            bufferedWriter.close();
            System.out.println("successfully");
        }catch (Exception ignore){
        }
    }
    public void listStudent(){
        try{

        }catch (Exception ignore){

        }
    }
}


