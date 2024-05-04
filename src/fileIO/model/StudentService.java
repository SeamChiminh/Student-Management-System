package fileIO.model;

public interface StudentService {
    void generateDesignedObjectForWriting();
    void addNewStudent(Student student);
    List<Student> ListAllStudent();
    void commitDataToFile();
    void commitFromTransaction();
    void destroyData();
    Boolean checkIfDataAvailableInTransaction(String fileToCheck);
    List<Student> serchStudentById(String id);
    List<Student> searchStudentByName(String name);
    Student deleteStudentById(String id);
    Student deleteStudentByName(String name);


}
