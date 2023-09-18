public abstract class Direction {

    // calculates score if motion stars in specified direction
    public abstract int calcScore(int[] position,Jewel currentJewel,Grid gameGrid);

    // deletes the triple starting from specified position
    public abstract void delete(int[] position,Grid gameGrid);



}
