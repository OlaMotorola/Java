package org.life;

import java.util.Random;

public class JumpingOrganism extends Organism {

    public JumpingOrganism(int energy) {
        super(energy);
    }

    @Override
    public void move(Board board) {
        Position currentPosition = getPosition();

        if (currentPosition != null) {
            int newX = currentPosition.getX();
            int newY = currentPosition.getY();
            Random random = new Random();


            newX += (random.nextInt(3) - 1) * 2;
            newY += (random.nextInt(3) - 1) * 2;
            board.moveOrganism(this, newX, newY);
        }
    }
}
