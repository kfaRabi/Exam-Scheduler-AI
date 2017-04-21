/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bd.ac.seu;

import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kmhasan
 */
public class Converter {

    public Converter() {
        try {
            convert("students.csv", "students.json");
        } catch (Exception ex) {
            Logger.getLogger(Converter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void convert(String inputCSV, String outputJSON) throws Exception {
        RandomAccessFile input = new RandomAccessFile(inputCSV, "r");
        RandomAccessFile output = new RandomAccessFile(outputJSON, "rw");
        
        String attributes[] = input.readLine().split("\\,");
        String type = inputCSV.substring(0, inputCSV.indexOf('.'));
        String line;
        
        System.out.println(type);
        System.out.println(Arrays.toString(attributes));
        output.setLength(0);
        String message = "";

        message += ("{\n\""+ type + "\":\n[\n");
        while ((line = input.readLine()) != null) {
            if (line.length() == 0)
                continue;
            String values[] = line.split("\\,");
            if (attributes.length != values.length)
                System.err.printf("Error processing line: [%s]\n", line);
            message += ("{");
            for (int i = 0; i < attributes.length; i++) {
                if (i != 0)
                    message += (", ");
                message += (attributes[i] + ":" + values[i]);
            }
            message += ("},\n");
        }
        output.writeBytes(message.substring(0, message.length() - 2) + "\n]\n}\n");
        output.close();
//"employees":[
//{"firstName":"John", "lastName":"Doe"},
//{"firstName":"Anna", "lastName":"Smith"},
//{"firstName":"Peter","lastName":"Jones"}
//]
    }
}
