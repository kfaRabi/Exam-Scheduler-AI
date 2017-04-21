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
public class Faculty {
    private String initials;
    private String fullName;
    private ArrayList<Course> assignedCourses;

    public Faculty(String initials, String fullName) {
        this.initials = initials;
        this.fullName = fullName;
        assignedCourses = new ArrayList<>();
    }

    public String getInitials() {
        return initials;
    }

    public String getFullName() {
        return fullName;
    }

    public ArrayList<Course> getAssignedCourses() {
        return assignedCourses;
    }
    
    public void addCourse(Course course) {
        assignedCourses.add(course);
    }
    
    public JSONObject toJSONObject() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("Faculty Code", initials);
            jsonObject.put("Full Name", fullName);
            jsonObject.put("Assigned Courses", assignedCourses);
        } catch (JSONException ex) {
            Logger.getLogger(Course.class.getName()).log(Level.SEVERE, null, ex);
        }
        return jsonObject;
    }

    @Override
    public String toString() {
        return initials;
    }
    
    
}
