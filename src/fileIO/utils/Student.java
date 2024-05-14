package fileIO.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Date;

public class Student {
    private String UUID;
    private String name;
    private String dob;
    private String classroom;
    private String subject;

    public Student(){}
    public Student(String UUID, String name, String dob, String classroom, String subject) {
        this.UUID = UUID;
        this.name = name;
        this.dob = dob;
        this.classroom = classroom;
        this.subject = subject;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
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
}

