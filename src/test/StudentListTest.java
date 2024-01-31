
import main.java.Student;
import main.java.StudentList;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StudentListTest {
    private StudentList studentList;
    private List<Student> stud;

    @BeforeClass
    public void beforeClass(){
        stud = new ArrayList<>();
        stud.add(new Student(0, "qaz"));
        stud.add(new Student(1, "wsx"));
        stud.add(new Student(2, "edc"));
        stud.add(new Student(3, "qwe"));
        stud.add(new Student(4, "asd"));
    }

    @BeforeMethod
    public void setUp(){
        studentList = new StudentList();
        studentList.setList(new ArrayList<>(stud));
    }

    @Test
    public void addStudent_uniqueID(){
        Student newStudent = studentList.addStudent("poi");
        HashMap<Integer, String> map = new HashMap<>();
        for(Student student:stud){
            map.put(student.getId(), student.getName());
        }
        map.put(newStudent.getId(), newStudent.getName());
        Assert.assertEquals(stud.size()+1, map.size());
    }

    @Test
    public void getStudent_fail(){
        int id = -1;
        Assert.assertNull(studentList.getStudent(id));
    }
    @Test
    public void getStudent_success(){
        int id = stud.size()-1;
        Assert.assertNotNull(studentList.getStudent(id));
    }

    @Test
    public void findStudent_success(){
        Assert.assertTrue(studentList.findStudent(stud.get(stud.size()-1).getName()));
    }

    @Test
    public void findStudent_fail(){
        Assert.assertFalse(studentList.findStudent("stud not found"));
    }
}
