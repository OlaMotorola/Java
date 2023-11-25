package org.studentresource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class StudentResourceManagerTest {
    private StudentResourceManager<Course> courseManager;
    private StudentResourceManager<StudyMaterial> materialManager;

    @BeforeEach
    void setUp() {
        courseManager = new StudentResourceManager<>();
        materialManager = new StudentResourceManager<>();
    }

    @Test
    void addAndRetrieveStudyMaterialTest() {
        StudyMaterial studyMaterial = new StudyMaterial("ITA01", "Introduction to Algorithms");
        materialManager.addResource(studyMaterial);

        StudyMaterial retrieved = materialManager.getResource("ITA01");
        assertNotNull(retrieved, "Retrieved study material should not be null.");
        assertEquals("Introduction to Algorithms", retrieved.getName(), "Study material name should match.");
    }

    @Test
    void addAndRetrieveCourseTest() {
        Course course = new Course("CS101", "Introduction to Computer Science");
        courseManager.addResource(course);

        Course retrieved = courseManager.getResource("CS101");
        assertNotNull(retrieved, "Retrieved course should not be null.");
        assertEquals("Introduction to Computer Science", retrieved.getName(), "Course name should match.");
    }

    @Test
    void removeStudyMaterialTest() {
        StudyMaterial studyMaterial = new StudyMaterial("ITA01", "Introduction to Algorithms");
        materialManager.addResource(studyMaterial);

        materialManager.removeResource(studyMaterial);

        StudyMaterial removed = materialManager.searchResourceByName("Introduction to Algorithms");
        assertNull(removed, "Removed study material should be null.");
    }

    @Test
    void removeCourseTest() {
        Course course = new Course("CS101", "Introduction to Computer Science");
        courseManager.addResource(course);

        courseManager.removeResource(course);

        Course removed = courseManager.searchResourceByName("Introduction to Computer Science");
        assertNull(removed, "Removed course should be null.");
    }

    @Test
    void searchStudyMaterialByNameTest() {
        StudyMaterial studyMaterial = new StudyMaterial("ITA01", "Introduction to Algorithms");
        materialManager.addResource(studyMaterial);

        StudyMaterial found = materialManager.searchResourceByName("Introduction to Algorithms");

        assertNotNull(found, "Found study material should not be null.");
        assertEquals("Introduction to Algorithms", found.getName(), "Study material name should match.");
    }

    @Test
    void searchCourseByNameTest() {
        Course course = new Course("CS101", "Introduction to Computer Science");
        courseManager.addResource(course);

        Course found = courseManager.searchResourceByName("Introduction to Computer Science");

        assertNotNull(found, "Found course should not be null.");
        assertEquals("Introduction to Computer Science", found.getName(), "Course name should match.");
    }
}
