/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bd.ac.seu;

import bd.ac.seu.model.Course;
import bd.ac.seu.model.Faculty;
import bd.ac.seu.model.Student;
import java.util.ArrayList;
/**
 *
 * @author rabi
 */


public class CourseConvert {
    private static ArrayList<Course> Courses;
    private ArrayList<Course> NowCourses;
    private String coverted[] = new String[1000];
    private int index;
    
    public CourseConvert(ArrayList<Course> Courses) {
        this.NowCourses = new ArrayList<>();
        
        this.Courses = Courses;
        //NowCourses = new ArrayList<Course>;
        index = 0;
        tasks();
        
    }
    
    private void check(Course c){
        //boolean status = false;
        //int sec = 0;
        
        /*if(NowCourses.size()==0) {
            NowCourses.add(c);
            return;
        }*/
        
            for(Course n:NowCourses){
                String codeN = n.getCourseCode();
                String codeC = c.getCourseCode();
                Faculty fn  = n.getCourseTeacher();
                String facN = fn.getInitials();
                Faculty fc = c.getCourseTeacher();
                String facC = fc.getInitials();
                if(facN.compareTo(facC)==0){
                    
                    if(codeN.compareTo(codeC)==0){
                        //status = true;
                        ArrayList<Student>st = c.getRegisteredStudents();
                        for(Student s:st){
                            n.addStudent(s);
                        }
                        return;
                    }
                    String CodeAlt = null;
                    Course alter = n.getAlternateCourse();
                    if(alter!=null)
                    CodeAlt = alter.getCourseCode();
                    if(CodeAlt!=null)
                        if(CodeAlt.compareTo(codeC)==0){
                             ArrayList<Student>st = c.getRegisteredStudents();
                            for(Student s:st){
                                n.addStudent(s);
                            }
                            return;
                        }
                    
                }
                /*if(codeN.compareTo(codeC)==0){
                    sec++;
                }*/
            }
        
       
           
                NowCourses.add(c);
            
            
}
    
    
    private void tasks(){
       int start = 0;
        for(Course c: Courses){
            //String s = c.getCourseCode();
            if(start==1)
                check(c);
            else 
                NowCourses.add(c);
            start = 1;
        } 
    }
     
    

    public ArrayList<Course> getNowCourses() {
        return NowCourses;
    }

    public ArrayList<Course> getCourses() {
        return Courses;
    }
}
