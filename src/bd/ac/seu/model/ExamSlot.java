/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bd.ac.seu.model;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;

/**
 *
 * @author kmhasan
 */
public class ExamSlot {

    private String slotLabel;
    private Date startTime;
    private String start;
    private Date endTime;
    private String end;
    private ArrayList<Room> availableRooms;
    private ArrayList<Course> assignedCourses;
    private DateFormat formatter;
    
    public ExamSlot(String slotLabel, String startTime, String endTime) {

        formatter = new SimpleDateFormat("d/M/yyyy h:mm a");
        this.start = startTime;
        this.end   = endTime; 
        this.slotLabel = slotLabel;
        try {
            this.startTime = formatter.parse(startTime);
            this.endTime = formatter.parse(endTime);
        } catch (ParseException ex) {
            Logger.getLogger(ExamSlot.class.getName()).log(Level.SEVERE, null, ex);
        }
        availableRooms = new ArrayList<>();
        assignedCourses = new ArrayList<>();
    }

    public String getSlotLabel() {
        return slotLabel;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public ArrayList<Room> getAvailableRooms() {
        return availableRooms;
    }

    public ArrayList<Course> getAssignedCourses() {
        return assignedCourses;
    }

    public void addAvailableRoom(Room room) {
        availableRooms.add(room);
    }

    public void addAssignedCourse(Course course) {
        assignedCourses.add(course);
    }

    public JSONObject toJSONObject() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("Slot Label", slotLabel);
            jsonObject.put("Start", formatter.format(startTime));
            jsonObject.put("End", formatter.format(endTime));
            jsonObject.put("Assigned Courses", assignedCourses);
            jsonObject.put("Available Rooms", availableRooms);
        } catch (JSONException ex) {
            Logger.getLogger(Course.class.getName()).log(Level.SEVERE, null, ex);
        }
        return jsonObject;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    @Override
    public String toString() {
        return "ExamSlot{" + "slotLabel=" + slotLabel + " assignedCourses=" + assignedCourses + '}';
    }
    
}
