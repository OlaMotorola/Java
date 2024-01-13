package org.starmap.utils;

import org.json.JSONArray;
import org.json.JSONObject;
import org.starmap.model.Constellation;
import org.starmap.model.Star;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class DataWriter {

    public static void writeStars(String filePath, List<Star> stars) {
        JSONArray starsJson = new JSONArray();
        for (Star star : stars) {
            JSONObject starJson = new JSONObject();
            starJson.put("name", star.getName());
            starJson.put("xPosition", star.getXPosition());
            starJson.put("yPosition", star.getYPosition());
            starJson.put("brightness", star.getBrightness());
            starsJson.put(starJson);
        }

        JSONObject starsObject = new JSONObject();
        starsObject.put("stars", starsJson);

        try (FileWriter file = new FileWriter(filePath)) {
            file.write(starsObject.toString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeConstellations(String filePath, List<Constellation> constellations) {
        JSONArray constellationsJson = new JSONArray();
        for (Constellation constellation : constellations) {
            JSONObject constellationJson = new JSONObject();
            constellationJson.put("name", constellation.getName());

            JSONArray starsArray = new JSONArray();
            for (Star star : constellation.getStars()) {
                starsArray.put(star.getName());
            }
            constellationJson.put("stars", starsArray);

            constellationsJson.put(constellationJson);
        }

        JSONObject constellationsObject = new JSONObject();
        constellationsObject.put("constellations", constellationsJson);

        try (FileWriter file = new FileWriter(filePath)) {
            file.write(constellationsObject.toString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
