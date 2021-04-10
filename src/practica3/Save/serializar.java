/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica3.Save;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;
import javafx.util.Pair;

/**
 *
 * @author Derek
 */
public class serializar {
    
    public void saveLeaderboard(Players[] player){
        try {
            ObjectOutputStream jugadorSave = new ObjectOutputStream(new FileOutputStream("Saves\\leaderboard"));
            jugadorSave.writeObject(player);
            jugadorSave.close();
        } catch (FileNotFoundException ex) {
//            Logger.getLogger(Serializar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
//            Logger.getLogger(Serializar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void saveConfig(config state){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(state);
        try (FileWriter writer = new FileWriter("Saves\\config.json")) {
            gson.toJson(state, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
