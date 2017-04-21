/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bd.ac.seu;

import bd.ac.seu.model.Course;
import bd.ac.seu.model.ExamSlot;
import bd.ac.seu.model.Faculty;
import bd.ac.seu.model.Room;
import bd.ac.seu.model.Student;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;

/**
 *
 * @author rabi
 */
public class InputReaderSingleton {

    private static final InputReaderSingleton instance = new InputReaderSingleton();
    private static ArrayList<Student> students;
    private static ArrayList<Course> courses;
    private static ArrayList<Faculty> faculties;
    private static ArrayList<Room> rooms;
    private static ArrayList<ExamSlot> examSlots;

    private InputReaderSingleton() {
        students = new ArrayList<>();
        courses = new ArrayList<>();
        faculties = new ArrayList<>();
        rooms = new ArrayList<>();
        examSlots = new ArrayList<>();

        System.out.println("Reading input");
        readFaculties();
        readStudents();
        System.out.println("Reading courses");
        readCourses();
        System.out.println("Done reading courses");
        readRegistrations();
        readRooms();
        readExamSlots();
        readAvailableRooms();
        System.out.println("Reading alternate courses");
        readAlternateCourses();
        System.out.println("Done reading alternate courses");
    }

    public static ArrayList<Course> getCourses() {
        return courses;
    }

    public static ArrayList<ExamSlot> getExamSlots() {
        return examSlots;
    }

    public static ArrayList<Faculty> getFaculties() {
        return faculties;
    }

    public static ArrayList<Room> getRooms() {
        return rooms;
    }

    public static ArrayList<Student> getStudents() {
        return students;
    }

    private void readCourses() {
        try {
//            URL jsonURL = new URL("http://my.seu.ac.bd/~kmhasan/_WebServices_/list_courses.php");
//            BufferedReader input = new BufferedReader(new InputStreamReader(jsonURL.openStream()));
            // uncomment the following line if reading from a local file
             RandomAccessFile input = new RandomAccessFile("list_courses.json", "r");
            String json = "";

            while (true) {
                String line = input.readLine();
                if (line == null) {
                    break;
                }
                if (line.length() == 0) {
                    continue;
                }

                json += line;
            }
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("courses");
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                Course course = new Course(jsonObject.getString("Course Code"), jsonObject.getString("Title"), jsonObject.getInt("Section"));
                String facultyInitials = jsonObject.getString("Faculty Code");
                for (Faculty f : faculties) {
                    if (f.getInitials().equals(facultyInitials)) {
                        f.addCourse(course);
                        course.setCourseTeacher(f);
                        break;
                    }
                }
                courses.add(course);
            }
        } catch (JSONException ex) {
            Logger.getLogger(InputReaderSingleton.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(InputReaderSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void readStudents() {
        try {
//            URL jsonURL = new URL("http://my.seu.ac.bd/~kmhasan/_WebServices_/list_students.php");
//            BufferedReader input = new BufferedReader(new InputStreamReader(jsonURL.openStream()));
            // uncomment the following line if reading from a local file
            RandomAccessFile input = new RandomAccessFile("list_students.json", "r");
            String json = "";

            while (true) {
                String line = input.readLine();
                if (line == null) {
                    break;
                }
                if (line.length() == 0) {
                    continue;
                }

                json += line;
            }
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("students");
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                Student student = new Student(jsonObject.getString("ID"), jsonObject.getString("Name"));
                students.add(student);
            }
        } catch (JSONException ex) {
            Logger.getLogger(InputReaderSingleton.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(InputReaderSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void readRegistrations() {
        try {
//            URL jsonURL = new URL("http://my.seu.ac.bd/~kmhasan/_WebServices_/list_registrations.php");
//            BufferedReader input = new BufferedReader(new InputStreamReader(jsonURL.openStream()));
            // uncomment the following line if reading from a local file
             RandomAccessFile input = new RandomAccessFile("list_registrations.json", "r");
            String json = "";

            while (true) {
                String line = input.readLine();
                if (line == null) {
                    break;
                }
                if (line.length() == 0) {
                    continue;
                }

                json += line;
            }
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("registrations");

            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                String studentId = jsonObject.getString("Student ID");
                String courseCode = jsonObject.getString("Course Code");
                int sectionNumber = jsonObject.getInt("Section");

                // HORRIBLY INEFFICIENT. NEEDS TO BE REPLACED BY HASHMAPS.
                for (Course course : courses) {
                    if (course.getCourseCode().equals(courseCode) && sectionNumber == course.getSectionNumber()) {
                        for (Student student : students) {
                            if (student.getId().equals(studentId)) {
                                student.addCourse(course);
                                course.addStudent(student);
                                break;
                            }
                        }
                        break;
                    }
                }
            }
        } catch (JSONException ex) {
            Logger.getLogger(InputReaderSingleton.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(InputReaderSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void readFaculties() {
        try {
//            URL jsonURL = new URL("http://my.seu.ac.bd/~kmhasan/_WebServices_/list_faculties.php");
//            BufferedReader input = new BufferedReader(new InputStreamReader(jsonURL.openStream()));
            // uncomment the following line if reading from a local file
             RandomAccessFile input = new RandomAccessFile("list_faculties.json", "r");
            String json = "";

            while (true) {
                String line = input.readLine();
                if (line == null) {
                    break;
                }
                if (line.length() == 0) {
                    continue;
                }

                json += line;
            }
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("faculties");
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                Faculty faculty = new Faculty(jsonObject.getString("Faculty Code"), jsonObject.getString("Full Name"));
                faculties.add(faculty);
            }
        } catch (JSONException ex) {
            Logger.getLogger(InputReaderSingleton.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(InputReaderSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void readRooms() {
        try {
//            URL jsonURL = new URL("http://my.seu.ac.bd/~kmhasan/_WebServices_/list_rooms.php");
//            BufferedReader input = new BufferedReader(new InputStreamReader(jsonURL.openStream()));
            // uncomment the following line if reading from a local file
             RandomAccessFile input = new RandomAccessFile("list_rooms.json", "r");
            String json = "";

            while (true) {
                String line = input.readLine();
                if (line == null) {
                    break;
                }
                if (line.length() == 0) {
                    continue;
                }

                json += line;
            }
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("rooms");
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                Room room = new Room(jsonObject.getString("Room #"), jsonObject.getInt("Capacity"));
                rooms.add(room);
            }
        } catch (JSONException ex) {
            Logger.getLogger(InputReaderSingleton.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(InputReaderSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void readExamSlots() {
        try {
//            URL jsonURL = new URL("http://my.seu.ac.bd/~kmhasan/_WebServices_/list_examslots.php");
//            BufferedReader input = new BufferedReader(new InputStreamReader(jsonURL.openStream()));
            // uncomment the following line if reading from a local file
             RandomAccessFile input = new RandomAccessFile("list_examslots.json", "r");
            String json = "";

            while (true) {
                String line = input.readLine();
                if (line == null) {
                    break;
                }
                if (line.length() == 0) {
                    continue;
                }

                json += line;
            }
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("examslots");
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                ExamSlot examSlot = new ExamSlot(jsonObject.getString("Slot Label"),
                        jsonObject.getString("Start"),
                        jsonObject.getString("End")
                );
                examSlots.add(examSlot);
            }
        } catch (JSONException ex) {
            Logger.getLogger(InputReaderSingleton.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(InputReaderSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void readAvailableRooms() {
        try {
//            URL jsonURL = new URL("http://my.seu.ac.bd/~kmhasan/_WebServices_/list_roomavailabilities.php");
//            BufferedReader input = new BufferedReader(new InputStreamReader(jsonURL.openStream()));
            // uncomment the following line if reading from a local file
             RandomAccessFile input = new RandomAccessFile("list_roomavailabilities.json", "r");
            String json = "";

            while (true) {
                String line = input.readLine();
                if (line == null) {
                    break;
                }
                if (line.length() == 0) {
                    continue;
                }

                json += line;
            }
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("room availability");
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                String roomNumber = jsonObject.getString("Room #");
                String slotLabel = jsonObject.getString("Slot Label");

                ExamSlot examSlot = null;
                Room room = null;

                for (ExamSlot e : examSlots) {
                    if (e.getSlotLabel().equals(slotLabel)) {
                        examSlot = e;
                        break;
                    }
                }

                for (Room r : rooms) {
                    if (r.getRoomNumber().equals(roomNumber)) {
                        room = r;
                        break;
                    }
                }

                if (examSlot != null & room != null) {
                    examSlot.addAvailableRoom(room);
                }
            }
        } catch (JSONException ex) {
            Logger.getLogger(InputReaderSingleton.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(InputReaderSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void readAlternateCourses() {
        try {
//            URL jsonURL = new URL("http://my.seu.ac.bd/~kmhasan/_WebServices_/list_alternatecourses.php");
//            BufferedReader input = new BufferedReader(new InputStreamReader(jsonURL.openStream()));
            // uncomment the following line if reading from a local file
             RandomAccessFile input = new RandomAccessFile("list_alternatecourses.json", "r");
            String json = "";

            while (true) {
                String line = input.readLine();
                if (line == null) {
                    break;
                }
                if (line.length() == 0) {
                    continue;
                }

                json += line;
            }
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("alternate courses");
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                String courseCode = jsonObject.getString("Course");
                String alternateCourseCode = jsonObject.getString("Alternate Course");

                Course course = null;
                Course alternateCourse = null;

                for (Course c : courses) {
                    if (c.getCourseCode().equals(courseCode)) {
                        for (Course a : courses) {
                            if (c != a && a.getCourseCode().equals(alternateCourseCode)
                                    && c.getCourseTeacher().getInitials().equals(a.getCourseTeacher().getInitials())) {
                                course = c;
                                alternateCourse = a;
                            }
                        }
                    }
                }
                if (course != null && alternateCourse != null) {
                    course.setAlternateCourse(alternateCourse);
                }
            }
        } catch (JSONException ex) {
            Logger.getLogger(InputReaderSingleton.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(InputReaderSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
