package main.java;

import java.io.IOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.*;

public class Main {
    private static Scanner scanner;
    private static  FtpClient ftpClient;
    private static StudentList studentList;

    public static void main(String[] args) throws IOException {
        scanner = new Scanner(System.in);

        studentList = new StudentList();
        connect();

        loop1: do {
            switch (menu()) {
                case 1:
                    studentList.setList(ftpClient.getData());
                    System.out.println(studentList.print());
                    break;
                case 2:
                    System.out.print("Введите id студента: ");
                    int id2 = readDigit();
                    Student std2 = studentList.getStudent(id2);
                    if (std2 == null) {
                        System.out.println("Студента с id " + id2 + " не найдено");
                    }
                    else{
                        System.out.println(std2);
                    }
                    break;
                case 3:
                    System.out.print("Введите имя студента: ");
                    scanner.nextLine();
                    String name = scanner.nextLine();
                    if(studentList.findStudent(name)){
                        System.out.print("Студент с таким именем существует.\nВсе равно добавить?\n\t1.\tДа\n\t2.\tНет\n");
                        if (readDigit()==1){
                            System.out.println("Добавлен студент с id = "+
                                    studentList.addStudent(name).getId());
                            ftpClient.saveData(studentList.toJson());
                        }
                    }else {
                        System.out.println("Добавлен студент с id = "+
                                studentList.addStudent(name).getId());
                        ftpClient.saveData(studentList.toJson());
                    }
                    break;
                case 4:
                    System.out.print("Введите id студента: ");

                    int id4 = readDigit();
                    if (studentList.deleteStudent(id4)) {
                        System.out.println("Студент с id " + id4 + " удален");
                    }
                    else{
                        System.out.println("Студента с id " + id4 + " не найдено");
                    }
                    ftpClient.saveData(studentList.toJson());
                    break;
                case 5:
                    System.out.println("Завершениe работы");
                    break loop1;
            }
            System.out.print("\n------\nНажмите Enter для выхода в меню\n");
            scanner.nextLine();
            scanner.nextLine();
        }while (true);
    }

    private static int menu() {
        int menu = 0;
        System.out.print("Выберите пункт меню.\n" +
                "\t1.\tПолучение списка студентов по имени\n" +
                "\t2.\tПолучение информации о студенте по id \n" +
                "\t3.\tДобавление студента ( id генерируется автоматически)\n" +
                "\t4.\tУдаление студента по id\n" +
                "\t5.\tЗавершение работы\n");
        do {
            System.out.print("Введите номер пункта меню: ");
            menu = readDigit();
            if (menu<1 || menu>5)
                System.out.print("\nВыбран несуществующий пункт меню.\n" +
                        "Попробуйте еще раз.\n");
            else break;
        }
        while (true);
        return menu;
    }

    private static int readDigit(){
        int digit;
        do {
            try {
                digit = scanner.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.print("\nВведен неправильный символ.\nВведите число:");
                scanner.nextLine();
            }
        }while (true);
       return digit;
    }

    private static void connect() throws IOException {
        do {
            System.out.print("Введите логин: ");
            String login = scanner.nextLine();  // 123
            System.out.print("Введите пароль: ");
            String pass = scanner.nextLine();   // 123
            System.out.print("Введите IP-адрес FTP-сервера: ");
            String ip = scanner.nextLine();     // 127.0.0.1/students.json

            ftpClient = new FtpClient(login, pass, ip);
            try {
                if(ip.isEmpty() || login.isEmpty() ||pass.isEmpty()) throw new UnknownHostException();
                studentList.setList(ftpClient.getData());
                break;
            } catch (ConnectException e) {
                System.out.println("Нет связи с сервером\n");
            } catch (IOException e) {
                System.out.println("Некорректный логин/пароль/ip\n");
            }
        }
        while (true);
    }
}