package fileIO.utils;

public class Student {
    private String id;
    private String name;
    private Integer day;
    private Integer month;
    private Integer year;
    private String classroom;
    private String subject;
    private String createDate;

    public Student(){}
    public Student(String id, String name, Integer day, Integer month, Integer year, String classroom, String subject, String createDate) {
        this.id = id;
        this.name = name;
        this.day = day;
        this.month = month;
        this.year = year;
        this.classroom = classroom;
        this.subject = subject;
        this.createDate = createDate;
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

    public Integer getDay() {
        return day;
    }
    public void setDay(int day) {
        this.day = day;

    }

    public Integer getMonth() {
        return month;
    }
    public void setMonth(int month) {
        this.month = month;
    }

    public Integer getYear() {
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

    public String getCreateDate() {
        return createDate;
    }
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", day=" + day +
                ", month=" + month +
                ", year=" + year +
                ", classroom='" + classroom + '\'' +
                ", subject='" + subject + '\'' +
                ", createDate='" + createDate + '\'' +
                '}';
    }
}
