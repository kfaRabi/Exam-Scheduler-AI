/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bd.ac.seu;

import bd.ac.seu.model.Course;
import bd.ac.seu.model.ExamSlot;
import bd.ac.seu.model.Room;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author rabi
 */
public class SimulatedAnnealing {
    private ArrayList<Course> Courses;
    private ArrayList<ExamSlot> examSlots;
    private ArrayList<ExamSlot> RandomExamSlots;
    private ArrayList<ExamSlot> NeighborExamSlots;
    private ArrayList<ExamSlot> FinalExamSlots;
    private Map Slotted; 
    
    public SimulatedAnnealing(ArrayList<Course> Courses,ArrayList<ExamSlot> examSlot){
        this.Courses = new ArrayList<>();
        this.Courses.addAll(Courses);
        this.examSlots = new ArrayList<>();
        this.examSlots = InputReaderSingleton.getExamSlots();
        //System.out.println(examSlots.size());
        Slotted = new HashMap();
       /* RandomExamSlots = new ArrayList<>();
        RandomExamSlots = RandomGenarator();
        NeighborExamSlots = new ArrayList<>();
        NeighborExamSlots = NeighborGenarator(RandomExamSlots);*/
        FinalExamSlots = new ArrayList<>();
        FinalExamSlots = MainAlgo();
        //System.out.println(NotPlottedCourses());
    }
    
    private ArrayList<ExamSlot> MainAlgo(){
        long  E1;
        long  E2;
        ArrayList<ExamSlot> rn = new ArrayList<>();
       ArrayList<ExamSlot> ne = new ArrayList<>();
       
       rn =  RandomGenarator();
       ScoreCounter sc = new ScoreCounter(rn);
       E1 = sc.getTotal();
       int srl = 1;
       double temparature = 100000;
       double rate = 0.3;
       for(int i=0;i<100000;i++){
          
            ne =  NeighborGenarator(rn);
            ScoreCounter scc = new ScoreCounter(ne);
            E2 = scc.getTotal();
            //int st = StudentCount(ne);
            temparature *= 1-rate; 
            System.out.println(E1+"(best) "+E2 + "(now) " + srl + "  Calculating... ");
            double rand = Math.random();
            srl++;
            if(E1>E2){
                rn.clear();
                rn.addAll(ne);
                E1 = E2;
            }
            else if(probability(E1, E2, temparature) > rand){
                rn.clear();
                rn.addAll(ne);
                E1 = E2;
            }
       } 
       int totalexams = StudentCount(rn);
       System.out.println("Total Exams of all Students Σ(studentI * examsofStudentI ) : " + totalexams);
       System.out.println("FinalScore : \n " + E1);
       ScoreCounter scf = new ScoreCounter(rn);
       System.out.println(scf.getConflict());
       return rn;
    }
    
    public static double probability(long random, long neighbour, double temparature) {
        if (neighbour < random) {
            return 1;
        }

        //System.out.println("------------------EXPONENTIAL RESULT---------------------: " + Math.exp((random - neighbour) / temparature));
        return Math.exp((random - neighbour) / temparature);
    }
    
    private ArrayList<ExamSlot> RandomGenarator(){
        ArrayList<Course>cr = new ArrayList<>();
        cr.addAll(Courses);
        ArrayList<ExamSlot> ex = new ArrayList<>();
        //Collections.copy(examSlots,ex);
        ex = Deepclone();
        Collections.shuffle(cr);
        //Collections.shuffle(ex);
        int bound = ex.size();
        Slotted.clear();
        //System.out.println(ex.size());
        //System.out.println(examSlots.size());
        //System.out.println(cr.size());
        //int totcap = getTotalCap(ex);
        //int avgcap = totcap/ex.size()+1;
        for(Course c:cr){
           int rand = random(bound);
           ex.get(rand).addAssignedCourse(c);
        }
        return ex;
    }
    
    private int SlotCapacity(ExamSlot e){
        ArrayList<Room>rm = e.getAvailableRooms();
        int tot = 0;
        for(Room r: rm){
          tot = tot + r.getStudentCapacity();
        }
        return tot;
    }
    
    private int getTotalCap(ArrayList<ExamSlot>ex){
        int tot = 0;
        for(ExamSlot e:ex){
            tot = tot + SlotCapacity(e);
        }
        return tot;
    }
    
    private int NotPlottedCourses(){
        int tot = 0;
        for(Course c:Courses){
            if(Slotted.containsKey(c)==false){
                tot = tot + c.getRegisteredStudents().size();
            }
        }
        return tot;
    }
    
    private ArrayList<ExamSlot> NeighborGenarator(ArrayList<ExamSlot>ex){
        int Listsize = ex.size();
        int n1 = random(Listsize);
        int n2 = random(Listsize);
        while(n1==n2){
            n2   = random(Listsize);
        }
        int p1 = ex.get(n1).getAssignedCourses().size();
        int p2 = ex.get(n2).getAssignedCourses().size();
        //If p1 && p2 both greater than it
        while(p1==0&&p2==0){
            n2   = random(Listsize);
            p2 = ex.get(n2).getAssignedCourses().size();
        }
        ArrayList<ExamSlot> exe = new ArrayList<>();
        int ind1 = 0 ;
        int ind2 = 0 ;
        ArrayList<Course> new1  = new ArrayList<>();;
        ArrayList<Course> new2 =  new ArrayList<>(); ;
        if(p1>0&&p2>0){
            ind1 =random(p1);
            ind2 =random(p2);
            new1 = CourseLister(ind1,ex.get(n2).getAssignedCourses().get(ind2),ex.get(n1).getAssignedCourses());
            new2 = CourseLister(ind2,ex.get(n1).getAssignedCourses().get(ind1),ex.get(n2).getAssignedCourses());;
            
            /*int i= 0;
            for(ExamSlot e:ex){
                if(i==n1){
                       ExamSlot en =  examObjectCreater(e,new1);
                       exe.add(en);
                }else if(i==n2){
                       ExamSlot en =  examObjectCreater(e,new2);
                       exe.add(en);
                }
                else
                    exe.add(e);
                i++;
            }*/
        }
        else if(p1>0&&p2==0){
            ind1 =random(p1);
            new1 = CourseListerSpecial(ind1,ex.get(n1).getAssignedCourses());
            
            new2.add(ex.get(n1).getAssignedCourses().get(ind1));
        }
        else if(p1==0&&p2>0){
            ind2 =random(p2);
            new2 = CourseListerSpecial(ind2,ex.get(n2).getAssignedCourses());
            new1.add(ex.get(n2).getAssignedCourses().get(ind2));
        }
        
        int i= 0;
            for(ExamSlot e:ex){
                if(i==n1){
                       ExamSlot en =  examObjectCreater(e,new1);
                       exe.add(en);
                }else if(i==n2){
                       ExamSlot en =  examObjectCreater(e,new2);
                       exe.add(en);
                }
                else
                    exe.add(e);
                i++;
            }
        //System.out.println(exe.get(n1));
        //System.out.println(exe.get(n2));
        //System.out.println(ex.get(n1));
        //System.out.println(ex.get(n2));
        //System.out.println(n1 + " " + n2 + " " + ind1 + " " + ind2 );
        return exe;
    }
    
    private int random(int n){
        if(n==0) return 0;
        ArrayList<Number> index = new ArrayList<>();
        index.clear();
        for(int i=0;i<n;i++){
            index.add(i);
        }
        Collections.shuffle(index);
        //System.out.println(index.size()+ " "+ n);
        return index.get(0).intValue();
    }
    
    private ArrayList<Course> CourseLister(int in,Course c,ArrayList<Course>cr){
        ArrayList<Course> new1 = new ArrayList<>();
        int i=0;
        for(Course cs:cr){
            if(i==in){
                new1.add(c);
            }
            else
                new1.add(cs);
            i++;
        }
        return new1;
    }
    
    private ArrayList<Course> CourseListerSpecial(int in,ArrayList<Course>cr){
        ArrayList<Course> new1 = new ArrayList<>();
        int i=0;
        for(Course cs:cr){
            if(i==in){
                ;
            }
            else
                new1.add(cs);
            i++;
        }
        return new1;
    }
    
    private ExamSlot examObjectCreater(ExamSlot e,ArrayList<Course>c){
        String Start = e.getStart();
        String End   = e.getEnd();
        //System.out.println(Start + "  " + End);
        ExamSlot en = new ExamSlot(e.getSlotLabel(),Start,End);
        ArrayList<Room> rm = e.getAvailableRooms();
        for(Room r:rm){
            en.addAvailableRoom(r);
        }
        for(Course cr:c){
            en.addAssignedCourse(cr);
        }
        return en;
    }
    
    private int StudentCount(ArrayList<ExamSlot> now){
        int tot = 0;
        for(ExamSlot e:now){
            //tot++;
            ArrayList<Course>cow = e.getAssignedCourses();
            for(Course c: cow){
                tot = tot + c.getRegisteredStudents().size();
                //tot++;
            }
        }
        return tot; 
    }
    
    private ArrayList<ExamSlot> Deepclone(){
       ArrayList<ExamSlot> exam;
       exam = new ArrayList<>();
       for(ExamSlot e:examSlots){
           ExamSlot en = examObjectCreater( e,e.getAssignedCourses());
           exam.add(en);
       }
       return exam;
    }
    
    
    
    
    public ArrayList<ExamSlot> getRandomExamSlots() {
        return RandomExamSlots;
    }

    public ArrayList<ExamSlot> getNeighborExamSlots() {
        return NeighborExamSlots;
    }

    public ArrayList<ExamSlot> getFinalExamSlots() {
        return FinalExamSlots;
    }
    
    
}
