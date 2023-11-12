package org.life;

public class LifeSimulator {

    public static void main(String[] args) {
        Board board = new Board(10, 10);

        Organism organism = new Organism(100){
        };

        board.addOrganism(organism, 5, 5);

        JumpingOrganism jumpingOrganism = new JumpingOrganism(100);
        board.addOrganism(jumpingOrganism, 3, 1);

        VisionOrganism visionOrganism = new VisionOrganism(100, 3);
        board.addOrganism(visionOrganism, 2, 8);

        int simulationCycles = 1000;
        for (int i = 0; i < simulationCycles; i++) {
            if (organism.getPosition() != null){
                System.out.println("Organism move:");
                organism.move(board);
                System.out.println("---------------------------------");
            }
            if (jumpingOrganism.getPosition() != null){
                System.out.println("JumpingOrganism move:");
                jumpingOrganism.move(board);
                System.out.println("---------------------------------");
            }

            if (visionOrganism.getPosition() != null){
                System.out.println("VisionOrganism move:");
                visionOrganism.move(board);
                System.out.println("---------------------------------");
            }

            if (countOrganisms(board) == 1) {
                System.out.println("Simulation completed, only one organism remains.");
                break;
            }
        }
    }

    private static int countOrganisms(Board board) {
        int count = 0;
        for (int i = 0; i < board.getWidth(); i++) {
            for (int j = 0; j < board.getHeight(); j++) {
                if (board.getOrganism(i, j) != null) {
                    count++;
                }
            }
        }
        return count;
    }
}

