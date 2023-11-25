package org.studentresource.decorator;

import org.studentresource.StudentResource;

public class CommentableResource extends ResourceDecorator {

    private String comment;

    public CommentableResource(StudentResource decoratedResource) {
        super(decoratedResource);
        this.comment = null;
    }

    public void addComment(String newComment) {
        System.out.println("Adding comment: " + newComment + " to resource: " + decoratedResource.getName());
        comment = newComment;
    }

    public String getComment() {
        System.out.println("Getting comment: " + comment + " from resource: " + decoratedResource.getName());
        return comment;
    }
}