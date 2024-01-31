package main.java;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class StudentList {
    private List<Student> list;

    public void setList(List<Student> list) {
        this.list = list;
    }

    public StringBuilder print(){
        StringBuilder builder = new StringBuilder();
        LinkedList<Student> sortStud = new LinkedList<>(list);
        Collections.sort(sortStud);
        for (Student student : sortStud) {
            builder.append(student).append("\n");
        }
        return builder;
    }

    public Student getStudent(int id){
        for (Student student : list) {
            if(student.getId() == id) return student;
        }
        return null;
    }

    public boolean findStudent(String name){
        return list.contains(new Student(0, name));
    }

    public Student addStudent(String name){
        int lastID = getLastId();
        Student student = new Student(lastID+1, name);
        list.add(student);
        return student;
    }
    public int getLastId(){
        return list.get(list.size()-1).getId();
    }

    public boolean deleteStudent(int id){
        for (int i = 0; i<list.size();i++) {
            if(list.get(i).getId() == id) {
                list.remove(i);
                return true;
            }
        }
        return false;
    }

    String toJson(){
        StringBuilder json = new StringBuilder("{ \"students\": [");
        for (int i = 0; i<list.size()-1;i++) {
            json.append(list.get(i).toJson()).append(",");
        }
        json.append(list.get(list.size()-1).toJson()).append("]}");
        return json.toString();
    }
}
