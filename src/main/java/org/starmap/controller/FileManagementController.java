package org.starmap.controller;

import org.json.JSONArray;
import org.json.JSONObject;
import org.starmap.model.Constellation;
import org.starmap.model.Star;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileManagementController {

    public static List<Star> loadStars(String filePath) {
        List<Star> stars = new ArrayList<>();
        try {
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            JSONObject jsonObject = new JSONObject(content);
            JSONArray starsJson = jsonObject.getJSONArray("stars");

            for (int i = 0; i < starsJson.length(); i++) {
                JSONObject starJson = starsJson.getJSONObject(i);
                Star star = new Star(
                        starJson.getString("name"),
                        starJson.getDouble("xPosition"),
                        starJson.getDouble("yPosition"),
                        starJson.getDouble("brightness")
                );
                stars.add(star);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stars;
    }

    public static List<Constellation> loadConstellations(String filePath, List<Star> stars) {
        List<Constellation> constellations = new ArrayList<>();
        try {
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            JSONObject jsonObject = new JSONObject(content);
            JSONArray constellationsJson = jsonObject.getJSONArray("constellations");

            for (int i = 0; i < constellationsJson.length(); i++) {
                JSONObject constellationJson = constellationsJson.getJSONObject(i);
                List<Star> constellationStars = new ArrayList<>();
                JSONArray starNames = constellationJson.getJSONArray("stars");

                for (int j = 0; j < starNames.length(); j++) {
                    String starName = starNames.getString(j);
                    stars.stream()
                            .filter(star -> star.getName().equals(starName))
                            .findFirst()
                            .ifPresent(constellationStars::add);
                }

                Constellation constellation = new Constellation(
                        constellationJson.getString("name"),
                        constellationStars
                );
                constellations.add(constellation);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return constellations;
    }

    public static void writeStars(String filePath, List<Star> stars) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            JSONObject jsonObject = new JSONObject();
            JSONArray starsJson = new JSONArray();

            for (Star star : stars) {
                JSONObject starJson = new JSONObject();
                starJson.put("name", star.getName());
                starJson.put("xPosition", star.getXPosition());
                starJson.put("yPosition", star.getYPosition());
                starJson.put("brightness", star.getBrightness());
                starsJson.put(starJson);
            }
            jsonObject.put("stars", starsJson);

            writer.write(jsonObject.toString(2));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeConstellations(String filePath, List<Constellation> constellations) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            JSONObject jsonObject = new JSONObject();
            JSONArray constellationsJson = new JSONArray();

            for (Constellation constellation : constellations) {
                JSONObject constellationJson = new JSONObject();
                constellationJson.put("name", constellation.getName());

                JSONArray starNames = new JSONArray();
                for (Star star : constellation.getStars()) {
                    starNames.put(star.getName());
                }
                constellationJson.put("stars", starNames);
                constellationsJson.put(constellationJson);
            }
            jsonObject.put("constellations", constellationsJson);

            writer.write(jsonObject.toString(2));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
