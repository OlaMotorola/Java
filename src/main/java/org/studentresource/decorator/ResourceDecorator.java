package org.studentresource.decorator;

import org.studentresource.StudentResource;

public abstract class ResourceDecorator implements StudentResource {
    protected StudentResource decoratedResource;

    public ResourceDecorator(StudentResource decoratedResource) {
        this.decoratedResource = decoratedResource;
    }

    public StudentResource getDecoratedResource() {
        return decoratedResource;
    }

    @Override
    public String getId() {
        return decoratedResource.getId();
    }

    @Override
    public String getName() {
        return decoratedResource.getName();
    }

}