package org.life;

import java.util.Random;

public class VisionOrganism extends Organism {

    private int visionRadius;

    public VisionOrganism(int energy, int visionRadius) {
        super(energy);
        this.visionRadius = visionRadius;
    }

    @Override
    public void move(Board board) {
        int currentX = getPosition().getX();
        int currentY = getPosition().getY();
        Random random = new Random();


        int newX = currentX;
        int newY = currentY;

        for (int i = 0; i < 3; i++) {
            int x = currentX + random.nextInt(3) - 1;
            int y = currentY + random.nextInt(3) - 1;
            if (isWithinVisionRange(currentX, currentY, x, y)) {
                if (board.getOrganism(x, y) == null) {
                    newX = x;
                    newY = y;
                    break;
                }
            }
        }

        board.moveOrganism(this, newX, newY);
    }

    private boolean isWithinVisionRange(int currentX, int currentY, int targetX, int targetY) {
        int dx = targetX - currentX;
        int dy = targetY - currentY;
        double distance = Math.sqrt(dx * dx + dy * dy);
        return distance <= visionRadius;
    }
}
