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
import java.util.ArrayList;

/**
 *
 * @author kmhasan
 */
public class ExamScheduler {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ArrayList<Student> students = InputReaderSingleton.getStudents();
        ArrayList<Course> courses = InputReaderSingleton.getCourses();
        ArrayList<Faculty> faculties = InputReaderSingleton.getFaculties();
        ArrayList<Room> rooms = InputReaderSingleton.getRooms();
        ArrayList<ExamSlot> examSlots = InputReaderSingleton.getExamSlots();
      /*  for (Student s: students)
            System.out.println(s.toJSONObject());*/
      /* System.out.println(courses.size());
        for (Course c: courses)
            System.out.println(c);*/
      /*  for (Faculty f: faculties)
            System.out.println(f.toJSONObject());
        for (Room r: rooms)
            System.out.println(r.toJSONObject());
        for (ExamSlot e: examSlots)
            System.out.println(e.toJSONObject());*/
        CourseConvert c = new CourseConvert(courses);
        ArrayList<Course> course = c.getNowCourses();
       /* System.out.println(course.size());
        for (Course cr: course){
            System.out.println(cr);
         }*/
       /* CourseConvert c = new CourseConvert(courses);
        ArrayList<Course> coursess = c.getNowCourses();
        ArrayList<Course> course = new ArrayList<>();
        //Collections.copy(coursess, course);
        course.addAll(coursess);
        System.out.println(coursess.size());
        System.out.println(course.size());
        for (Course cr: course){
            System.out.println(cr);
         }*/
        
        /*SimulatedAnnealing s = new SimulatedAnnealing(course,examSlots);
        int flag[][] = s.getFlag();
        for(int i=0;i<flag.length;i++){
            for(int j=0;j<flag.length;j++){
                System.out.print(flag[i][j]+" ");
            }
            System.out.print("\n");
        }*/
        
        // Final Simulated Annealing
        
        //System.out.print("Final \n");
        SimulatedAnnealing s = new SimulatedAnnealing(course,examSlots);
        ArrayList<ExamSlot>ex = s.getFinalExamSlots();
        System.out.print("Final Schedule : \n");
        for(ExamSlot e:ex){
            System.out.println(e);
        }
       /* System.out.print("Neighbor\n");
        ArrayList<ExamSlot>el = s.getNeighborExamSlots();
        for(ExamSlot e:el){
            System.out.println(e);
        }*/
        
        
        //Random Neighbor Testing 
      /*  int i = 0;
        int j = 0; 
        int cnt = 0;
        int siz1 = ex.size();
        for(int x=0;x<siz1;x++){
            ArrayList<Course>C1 = ex.get(x).getAssignedCourses();
            ArrayList<Course>C2 = el.get(x).getAssignedCourses();
            int siz2 = C1.size();
            for(int y=0;y<siz2;y++){
                String s1 = C1.get(y).getCourseCode();
                String s2 = C2.get(y).getCourseCode();
                if(s1.compareTo(s2)!=0) cnt++;
            }
        }
        System.out.println("Conflicts OR Exchange: " +cnt);
        
        ScoreCounter sc1 = new ScoreCounter(ex);
        long tot1 = sc1.getTotal();
        ScoreCounter sc2 = new ScoreCounter(el);
        long tot2 = sc2.getTotal();
        
        System.out.println(tot1+"  "+tot2);*/
        
    }
    
    
    
    
}
