package org.life;

import java.util.Random;

public abstract class Organism {

    private int energy;
    private Position position;
    private Random random = new Random();

    public Organism(int energy) {
        this.energy = energy;
    }

    public int getEnergy(){
        return energy;
    }

    public void setEnergy(int energy){
        this.energy = energy;
    }

    public void move(Board board) {
        int newX = position.getX();
        int newY = position.getY();


        boolean moveVertically = random.nextBoolean();

        if (moveVertically) {
            // Move up or down by 1
            newY += random.nextBoolean() ? 1 : -1;
        } else {
            // Move left or right by 1
            newX += random.nextBoolean() ? 1 : -1;
        }

        board.moveOrganism(this, newX, newY);
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }


}