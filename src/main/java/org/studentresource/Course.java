package org.studentresource;

public class Course implements StudentResource {
    private final String id;
    private final String name;

    public Course(String id, String name) {
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