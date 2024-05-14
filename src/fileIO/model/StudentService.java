package fileIO.model;

import fileIO.utils.Student;

import java.util.List;

public interface StudentService {
    void addNewStudent();
    List<Student> getStudents();
    void deleteStudentById();
    void deleteAllStudents();
    void loadStudentsFromFile();
    Student searchStudentById(String id);
    List<Student> searchStudentsByName(String name);
    double getTimeToReadDataFromFileInSeconds();
    int getNumberOfRecordsInFile();
    void commitDataToFile();
    void generateDataToFile(int numRecords);
    void updateStudentInfoById();
    int generateRandomNumber(int min, int max);
}
