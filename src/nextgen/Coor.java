/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nextgen;

/**
 *
 * @author Mishko
 */
public class Coor {

    private Integer[] Coor = new Integer[3];

    public Coor(int x, int y, int z) {
        Coor[0] = x;
        Coor[1] = y;
        Coor[2] = z;
    }

    public int getX() {
        return Coor[0];
    }

    public int getY() {
        return Coor[1];
    }

    public int getZ() {
        return Coor[2];
    }

    public void setX(int x) {
        Coor[0] = x;
    }

    public void setY(int y) {
        Coor[1] = y;
    }

    public void setZ(int x, int y, int z) {
        Coor[2] = z;
    }

    public Coor setXYZ(int x, int y, int z) {
        Coor[0] = x;
        Coor[1] = y;
        Coor[2] = z;
        return this;
    }
//    public Coor switchXY() {
//        int temp = getX();
//        Coor[0] = getY();
//        Coor[1] = temp;
//        Coor[2] = getZ();
//        return this;
//    }
    public Coor offSet(int x, int y, int z) {
        return new Coor(getX()+x,getY()+y,getZ()+z);
    }
    public Coor distanceTo(Coor target){
        return new Coor(target.getX()-getX(),target.getY()-getY(),target.getZ()-getZ());
    }

    @Override
    public String toString() {
        return ("("+getX() + ", " + getY() + ", " + getZ()+")");
    }
}
