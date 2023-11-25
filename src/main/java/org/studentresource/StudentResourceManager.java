package org.studentresource;

import java.util.ArrayList;
import java.util.List;

public class StudentResourceManager<T extends StudentResource> {
    private final List<T> resources;

    public StudentResourceManager() {
        this.resources = new ArrayList<>();
    }

    public void addResource(T resource) {
        resources.add(resource);
    }

    public void removeResource(T resource) {
        resources.remove(resource);
    }

    public T getResource(String id) {
        return searchResourceByID(id);
    }

    public T searchResourceByID(String id) {
        for (T resource : resources) {
            if (resource.getId().equalsIgnoreCase(id)) {
                return resource;
            }
        }
        return null;
    }

    public T searchResourceByName(String name) {
        for (T resource : resources) {
            if (resource.getName().equalsIgnoreCase(name)) {
                return resource;
            }
        }
        return null;
    }


}
