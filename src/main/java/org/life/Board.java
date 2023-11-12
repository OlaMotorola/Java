package org.life;

public class Board {

    private int width;
    private int height;
    private Organism[][] organisms;

    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        this.organisms = new Organism[width][height];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void addOrganism(Organism organism, int x, int y) {
        if (organisms[x][y] == null) {
            organisms[x][y] = organism;
            organism.setPosition(new Position(x, y));
        } else {
            System.out.println("Position already occupied!");
        }
    }

    public void moveOrganism(Organism organism, int newX, int newY) {

        if (newX >= 0 && newX < width && newY >= 0 && newY < height) {

            if (organisms[newX][newY] == organism) {
                System.out.println("Organism cannot consume itself!");
                return;
            }

            if (organisms[newX][newY] == null){

                int currentX = organism.getPosition().getX();
                int currentY = organism.getPosition().getY();
                organisms[organism.getPosition().getX()][organism.getPosition().getY()] = null;
                organisms[newX][newY] = organism;
                organism.setPosition(new Position(newX, newY));

                System.out.println("Organism moved from (" + currentX + ", " + currentY + ") to (" + newX + ", " + newY + ")");
            }

            else{

                Organism otherOrganism = organisms[newX][newY];
                int organism_energy = organism.getEnergy();
                int other_organism_energy = otherOrganism.getEnergy();

                if (organism_energy >= other_organism_energy){
                    organism.setEnergy(organism_energy+other_organism_energy);
                    otherOrganism.setEnergy(0);
                    organisms[organism.getPosition().getX()][organism.getPosition().getY()] = null;
                    otherOrganism.setPosition(null);
                    organisms[newX][newY] = organism;
                    organism.setPosition(new Position(newX, newY));

                    System.out.println("Organism at (" + newX + ", " + newY + ") consumed organism. Energy: " + organism.getEnergy());

                }

                else {
                    otherOrganism.setEnergy(organism_energy+other_organism_energy);
                    organisms[organism.getPosition().getX()][organism.getPosition().getY()] = null;
                    organism.setPosition(null);

                    System.out.println("Organism at (" + newX + ", " + newY + ") consumed organism. Energy: " + otherOrganism.getEnergy());

                }


            }
        } else {
            System.out.println("Invalid move!");
        }
    }

    public Organism getOrganism(int x, int y) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            return organisms[x][y];
        }
        return null;
    }
}