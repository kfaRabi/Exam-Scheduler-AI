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
public class Room {
    private String roomNumber;
    private int studentCapacity;

    public Room(String roomNumber, int studentCapacity) {
        this.roomNumber = roomNumber;
        this.studentCapacity = studentCapacity;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public int getStudentCapacity() {
        return studentCapacity;
    }

    public JSONObject toJSONObject() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("Room #", roomNumber);
            jsonObject.put("Capacity", studentCapacity);
        } catch (JSONException ex) {
            Logger.getLogger(Course.class.getName()).log(Level.SEVERE, null, ex);
        }
        return jsonObject;
    }    

    @Override
    public String toString() {
        return roomNumber;
    }
}
