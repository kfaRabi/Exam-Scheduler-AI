/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bd.ac.seu.model;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;

/**
 *
 * @author kmhasan
 */
public class Course {
    private String courseCode;
    private Course alternateCourse;
    private String courseTitle;
    private int sectionNumber;
    private Faculty courseTeacher;
    private ArrayList<Student> registeredStudents;
    private ExamSlot examSlot;

    public Course(String courseCode, String courseTitle, int sectionNumber) {
        this.courseCode = courseCode;
        this.courseTitle = courseTitle;
        this.sectionNumber = sectionNumber;
        courseTeacher = null;
        registeredStudents = new ArrayList<>();
        examSlot = null;
        alternateCourse = null;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public int getSectionNumber() {
        return sectionNumber;
    }

    public Faculty getCourseTeacher() {
        return courseTeacher;
    }

    public ArrayList<Student> getRegisteredStudents() {
        return registeredStudents;
    }

    public ExamSlot getExamSlot() {
        return examSlot;
    }


    @Override
    public String toString() {
        return courseCode + "." + sectionNumber + " " + registeredStudents.size();
    }
        
    public JSONObject toJSONObject() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("Course Code", courseCode);
            jsonObject.put("Alternate Course Code", alternateCourse);
            jsonObject.put("Title", courseTitle);
            jsonObject.put("Section", sectionNumber);
            jsonObject.put("Faculty", courseTeacher);
            jsonObject.put("Exam Slot", examSlot);
            jsonObject.put("Registered Students", registeredStudents);
        } catch (JSONException ex) {
            Logger.getLogger(Course.class.getName()).log(Level.SEVERE, null, ex);
        }
        return jsonObject;
    }

    public void addStudent(Student student) {
        registeredStudents.add(student);
    }
    
    public void setCourseTeacher(Faculty courseTeacher) {
        this.courseTeacher = courseTeacher;
    }

    public void setAlternateCourse(Course alternateCourse) {
        this.alternateCourse = alternateCourse;
    }

    public Course getAlternateCourse() {
        return alternateCourse;
    }
    

}
