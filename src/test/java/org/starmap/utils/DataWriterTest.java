package org.starmap.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.starmap.model.Constellation;
import org.starmap.model.Star;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DataWriterTest {

    @TempDir
    Path tempDir;

    @Test
    void testWriteStars() throws IOException {
        Path testFilePath = tempDir.resolve("stars.json");

        List<Star> stars = new ArrayList<>();
        stars.add(new Star("Sirius", 100, 200, 1.46));
        stars.add(new Star("Canopus", 150, 250, 0.72));

        DataWriter.writeStars(testFilePath.toString(), stars);

        List<String> lines = Files.readAllLines(testFilePath);
        assertEquals(3, lines.size()); // including an empty line at the end

        // Add assertions here based on the expected content of the file
        // You can read the file and parse the content to verify it's correctly written
    }

    @Test
    void testWriteConstellations() throws IOException {
        Path testFilePath = tempDir.resolve("constellations.json");

        List<Star> stars = new ArrayList<>();
        stars.add(new Star("Sirius", 100, 200, 1.46));
        stars.add(new Star("Canopus", 150, 250, 0.72));

        List<Constellation> constellations = new ArrayList<>();
        List<Star> taurusStars = new ArrayList<>();
        taurusStars.add(stars.get(0)); // Adding Sirius to Taurus
        constellations.add(new Constellation("Taurus", taurusStars));

        DataWriter.writeConstellations(testFilePath.toString(), constellations);

        List<String> lines = Files.readAllLines(testFilePath);
        assertEquals(3, lines.size()); // including an empty line at the end

        // Add assertions here based on the expected content of the file
        // You can read the file and parse the content to verify it's correctly written
    }
}
