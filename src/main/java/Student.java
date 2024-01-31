package main.java;

import java.util.Objects;

public class Student implements Comparable<Student>{
    private int id;
    private String name;

    public Student(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return  name + " ("+ id + ')';
    }

    String toJson(){
        return "{\"id\": "+id+", \"name\": \""+name+"\"}";
    }
    @Override
    public int compareTo(Student o) {
        return -o.name.compareTo(this.name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(name, student.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
