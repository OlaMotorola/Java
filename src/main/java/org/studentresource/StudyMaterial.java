package org.studentresource;

public class StudyMaterial implements StudentResource {
    private final String id;
    private final String name;

    public StudyMaterial(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

}