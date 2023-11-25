package org.studentresource.decorator;

import org.studentresource.StudentResource;

public class RateableResource extends ResourceDecorator {

    private double rate;

    public RateableResource(StudentResource decoratedResource){
        super(decoratedResource);
        this.rate = 0;
    }
    public void setRating(double newRate){
        System.out.println("Adding rate: " + newRate + " to resource: " + decoratedResource.getName());
        rate = newRate;
    }

    public double getRating(){
        System.out.println("Getting rate: " + rate + " from resource: " + decoratedResource.getName());
        return rate;
    }
}
