/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bd.ac.seu;

import bd.ac.seu.model.Course;
import bd.ac.seu.model.ExamSlot;
import bd.ac.seu.model.Room;
import bd.ac.seu.model.Student;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 *
 * @author rabi
 */
public class ScoreCounter {
    private ArrayList<ExamSlot>exams;
    private ArrayList<Map>Conflicts;
    private ArrayList<Map>DayConflicts;
    private int conflictsCounter[];
    private int conflictsDayCounter[];
    private long points[];
    private long total ;
    private long exceedlimits;
    
    public ScoreCounter(ArrayList<ExamSlot> exams) {
        this.exams = new ArrayList<>();
        this.exams.addAll(exams);
        Conflicts = new ArrayList<>();
        DayConflicts = new ArrayList<>();
        conflictsCounter = new int[20];
        conflictsDayCounter = new int[20];
        points = new long[50];
        total = 0;
        exceedlimits = 0;
        exceedlimits = ExceedLimit();
        Setpoint();
        GoAround();
        CountAll();
        
    }
    
    private void GoAround(){
        for(ExamSlot exam:exams){
            ArrayList<Course>course = exam.getAssignedCourses();
            for(Course c:course){
                ArrayList<Student>student = c.getRegisteredStudents();
                for(Student s:student){
                    See(exam,s);
                }
                
            }
        }
    }
    
    private void See(ExamSlot e,Student s){
        Date date = e.getStartTime();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int day = cal.get(Calendar.DAY_OF_YEAR);
        if(ConflictCourses(date,s)==false){
            ConflictDay(day,s);
        }
    }
    
    private boolean ConflictCourses(Date date,Student s){
        boolean status = false ;
        int siz = Conflicts.size();
        if(siz==0){
            Map m = new HashMap();
            m.put(s, date);
            Conflicts.add(m);
            return false;
        }
        int cnt = 0;
        int ind = 0;
        for(Map m:Conflicts){
            if(m.containsKey(s)==true){
                Date dt = (Date)m.get(s);
                if(dt.equals(date)) {
                    cnt++;
                    status = true;
                }
            }
            else break;
            ind++;
        }
        
        if(cnt>0){
            if(cnt>1)
            conflictsCounter[cnt-1]--;
            
            conflictsCounter[cnt]++;
            
        }
        if(ind==siz){
            Map m = new HashMap();
            m.put(s, date);
            Conflicts.add(m);
         
        }
        else{
            Conflicts.get(ind).put(s, date);
        }
        return status;
    }
    
    private void ConflictDay(int day,Student s){
        int siz = DayConflicts.size();
        if(siz==0){
            Map m = new HashMap();
            m.put(s, day);
            DayConflicts.add(m);
            return ;
        }
        int cnt = 0;
        int ind = 0;
        for(Map m:DayConflicts){
            if(m.containsKey(s)==true){
                Number dt = (int)m.get(s);
                if(dt.equals(day)) cnt++;
            }
            else break;
            ind++;
        }
        if(cnt>0){
            if(cnt>1)
            conflictsDayCounter[cnt-1]++;
            conflictsDayCounter[cnt]++;
        }
        if(ind==siz){
            Map m = new HashMap();
            m.put(s, day);
            DayConflicts.add(m);
            return ;
        }
        else{
            DayConflicts.get(ind).put(s, day);
        }
    }
    
    private void Setpoint(){
        long init = 1;
        for(int i=0;i<=14;i++){
            points[i] = init;
            init = init * 2;
        }
    }
    
    private void CountAll(){
        for(int i=1;i<9;i++){
            total = total + conflictsDayCounter[i]*points[i];
        }
        for(int i=1;i<=6;i++){
            total = total + conflictsCounter[i]*points[i+8];
        }
        
        total = total + exceedlimits * points[10]; 
    }
    
    private long RoomAvailable(ArrayList<Room>rm){
        long tot = 0;
        for(Room r:rm){
            tot = tot +r.getStudentCapacity();
        }
        return tot;    
    } 
    
    private long StudentsHaveExam(ArrayList<Course>cr){
        long tot = 0;
        for(Course c:cr){
            tot = tot + c.getRegisteredStudents().size();
        }
        return tot;
    }
    
    private long ExceedLimit(){
        long tot = 0,cap,nec,dif;
        for(ExamSlot e:exams){
            cap = RoomAvailable(e.getAvailableRooms());
            nec = StudentsHaveExam(e.getAssignedCourses());
            if(nec>cap){
                dif = nec - cap;
                tot = tot + dif;
            }
        }
        return tot;
    }
    
    public String getConflict(){
        return "SameSlot 7 Exams : " + conflictsCounter[6]  + "\nSameSlot 6 Exams : " + conflictsCounter[5] + "\nSameSlot 5 Exams : " + conflictsCounter[4] + "\nSameSlot 4 Exams : " + conflictsCounter[3] + "\nSameSlot 3 Exams : " + conflictsCounter[2]
                + "\nSameSlot 2 Exams : " + conflictsCounter[1] + "\nSameDay 9 Exams : " + conflictsDayCounter[8] + "\nSameDay 8 Exams : " + conflictsDayCounter[7] + "\nSameDay 7 Exams : " + conflictsDayCounter[6]+ "\nSameDay 6 Exams : " + conflictsDayCounter[5]
                + "\nSameDay 5 Exams : " + conflictsDayCounter[4] + "\nSameDay 4 Exams : " + conflictsDayCounter[3] + "\nSameDay 3 Exams : " + conflictsDayCounter[2]
                + "\nSameDay 2 Exams : " + conflictsDayCounter[1] + "\nOutofLimits "  + exceedlimits ;
        
    }

    public long getTotal() {
        return total;
    }
    
}
