package fileIO.utils;

public class Student {
    private String id;
    private String name;
    private int day;
    private int month;
    private int year;
    private String classroom;
    private String subject;

    public Student(){}
    public Student(String id, String name, int day, int month, int year, String classroom, String subject) {
        this.id = id;
        this.name = name;
        this.day = day;
        this.month = month;
        this.year = year;
        this.classroom = classroom;
        this.subject = subject;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getDay() {
        return day;
    }
    public void setDay(int day) {
        this.day = day;

    }

    public int getMonth() {
        return month;
    }
    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }

    public String getClassroom() {
        return classroom;
    }
    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public String getSubject() {
        return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", day=" + day +
                ", month=" + month +
                ", year=" + year +
                ", classroom='" + classroom + '\'' +
                ", subject='" + subject + '\'' +
                '}';
    }
}
