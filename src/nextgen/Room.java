/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nextgen;

import java.util.ArrayList;

/**
 *
 * @author Mishko
 */
public class Room {

    private final char[][][] Cube;

    public Room(int x, int y, int z) {
        if (x > -2 && y > -2 && z > -2 && !(x == -2 && y == -2 && z == -2)) {
            Cube = new char[x + 2][y + 2][z + 2];
        } else {
            //System.err.println("Room: Nonexistent Room: " + x + ", " + y + ", " + z);
            Cube = new char[0][0][0];

        }
    }

    public Room(char[][][] inpt) {
        Cube = inpt;
    }

    public int getXDim() {
        return Cube.length;
    }

    public int getYDim() {
        return Cube[0].length;
    }

    public int getZDim() {
        return Cube[0][0].length;
    }

    public char[][][] getRoom() {
        return Cube;
    }

    public Coor getRoomSize() {
        return new Coor(getXDim() - 2, getYDim() - 2, getZDim() - 2);
    }

    public char getObjectAt(Coor coor) {
        if (contains(coor)) {
            return Cube[coor.getX()][coor.getY()][coor.getZ()];
        } else {
            //System.err.println("getObjectAt:  "+ coor.toString() + " does not exist.  @~");
            return '~';
        }
    }

    public Room getObjectsInRadius(Coor coor, int Rad) {
        if (contains(coor)) {
            Rad--;
            Coor target = new Coor(0, 0, 0);
            Coor obtar = new Coor(0, 0, 0);
            Room area = new Room(2 * Rad - 1, 2 * Rad - 1, 2 * Rad - 1);
            area.setEmpty();
            for (int ydim = -Rad; ydim <= Rad; ydim++) {
                for (int xdim = -Rad; xdim <= Rad; xdim++) {
                    for (int zdim = -Rad; zdim <= Rad; zdim++) {
                        if (Math.abs(xdim) + Math.abs(zdim) + Math.abs(ydim) <= Rad) {
                            target.setXYZ(xdim + Rad, ydim + Rad, zdim + Rad);
                            obtar.setXYZ(coor.getX() + xdim, coor.getY() + ydim, coor.getZ() + zdim);
                            if (contains(target) && contains(obtar)) {
                                area.setObject(target, getObjectAt(obtar));
                            }
                        }
                    }
                }
            }
            return area;
        } else {
            //System.err.println("getObjectsInRadius: "+coor.toString() + "does not exist");
        }
        return null;
    }

    public ArrayList<Coor> findObjects(char sym) {
        ArrayList<Coor> datapoints = new ArrayList<>();
        Coor obtar = new Coor(0, 0, 0);
        for (int y = 0; y < getYDim(); y++) {
            for (int x = 0; x < getXDim(); x++) {
                for (int z = 0; z < getZDim(); z++) {
                    obtar.setXYZ(x, y, z);
                    if (getObjectAt(obtar) == sym) {
                        datapoints.add(new Coor(x, y, z));
                    }
                }
            }
        }
        return datapoints;
    }

    public Room roundAbout(Room place) {
        Coor obtar = new Coor(0, 0, 0);
        for (int y = 0; y < getYDim(); y++) {
            for (int x = 0; x < getXDim(); x++) {
                for (int z = 0; z < getZDim(); z++) {
                    obtar.setXYZ(x, y, z);
                    if (getObjectAt(obtar) != 'X') {
                        setObject(obtar, place.getObjectAt(obtar));
                    }
                }
            }

        }
        return this;
    }

    public Room Vision(Coor Home) {
        if (contains(Home)) {
            Room area = new Room(getXDim() - 2, getYDim() - 2, getZDim() - 2);
            area.setEmpty();
            Coor target = new Coor(0, 0, 0);
            ArrayList<Coor> walls = findObjects(']');
            ArrayList<Coor> distance = new ArrayList<>();
            for (Coor wall : walls) {
                distance.add(new Coor(wall.getX() - Home.getX(), wall.getY() - Home.getY(), wall.getZ() - Home.getZ()));
            }
            for (int i = 0; i < distance.size(); i++) {
                int xVal = distance.get(i).getX();
                int yVal = distance.get(i).getY();
                int zVal = distance.get(i).getZ();

                int wallX = walls.get(i).getX();
                int wallY = walls.get(i).getY();
                int wallZ = walls.get(i).getZ();

                int xmov;
                if (xVal != 0) {
                    xmov = (xVal / Math.abs(xVal));
                } else {
                    xmov = 0;
                }
                int ymov;
                if (yVal != 0) {
                    ymov = (yVal / Math.abs(yVal));
                } else {
                    ymov = 0;
                }
                int zmov;
                if (zVal != 0) {
                    zmov = (zVal / Math.abs(zVal));
                } else {
                    zmov = 0;
                }

                int xLoc = wallX, yLoc = wallY, zLoc = wallZ;
                target.setXYZ(wallX + xmov, wallY + ymov, wallZ + zmov);
                while (area.contains(target)) {
                    this.contains(Home);
                    xVal = distance.get(i).getX();
                    yVal = distance.get(i).getY();
                    zVal = distance.get(i).getZ();
                    while (xVal != 0 || yVal != 0 || zVal != 0) {
                        if (xVal != 0) {
                            xLoc += xmov;
                            xVal -= xmov;
                        }
                        if (yVal != 0) {
                            yLoc += ymov;
                            yVal -= ymov;
                        }
                        if (zVal != 0) {
                            zLoc += zmov;
                            zVal -= zmov;
                        }

                        target.setXYZ(xLoc, yLoc, zLoc);
                        if (area.contains(target)) {
                            area.setObject(target, 'X');
                        }
                    }
                }
            }
            return area.roundAbout(this);
        } else {
            //System.err.println("Vision: " + Home.toString() + "does not exist");
            return null;
        }
    }

    public boolean contains(Coor coor) {
        return coor.getX() >= 0 && coor.getY() >= 0 && coor.getZ() >= 0 && coor.getX() < getXDim() && coor.getY() < getYDim() && coor.getZ() < getZDim();
    }
    /*
     //    public Room Vision(Room area) {
     //        int Radius = (area.getXDim() + 1) / 2;
     //        int side = area.getXDim();
     //        boolean yesWall = false;
     //
     //        //Checking back
     //        for (int y = 0; y < side; y++) {
     //            for (int x = 0; x < side; x++) {
     //                for (int z = Radius; z < side; z++) {
     //                    if (yesWall) {
     //                        area.setObject(x, y, z, 'X');
     //                    } else {
     //                        if (area.getObjectAt(x, y, z) == ']') {
     //                            yesWall = true;
     //                        }
     //                    }
     //                }
     //                yesWall = false;
     //            }
     //        }
     //
     //        //Checking front
     //        for (int y = 0; y < side; y++) {
     //            for (int x = 0; x < side; x++) {
     //                for (int z = Radius - 1; z >= 0; z--) {
     //                    if (yesWall) {
     //                        area.setObject(x, y, z, 'X');
     //                    } else {
     //                        if (area.getObjectAt(x, y, z) == ']') {
     //                            yesWall = true;
     //                        }
     //                    }
     //                }
     //                yesWall = false;
     //            }
     //        }
     //
     //        //Checking right
     //        for (int y = 0; y < side; y++) {
     //            for (int z = 0; z < side; z++) {
     //                for (int x = Radius; x < side; x++) {
     //                    if (yesWall) {
     //                        area.setObject(x, y, z, 'X');
     //                    } else {
     //                        if (area.getObjectAt(x, y, z) == ']') {
     //                            yesWall = true;
     //                        }
     //                    }
     //                }
     //                yesWall = false;
     //            }
     //        }
     //
     //        //Checking left
     //        for (int y = 0; y < side; y++) {
     //            for (int z = 0; z < side; z++) {
     //                for (int x = Radius; x >= 0; x--) {
     //                    if (yesWall) {
     //                        area.setObject(x, y, z, 'X');
     //                    } else {
     //                        if (area.getObjectAt(x, y, z) == ']') {
     //                            yesWall = true;
     //                        }
     //                    }
     //                }
     //                yesWall = false;
     //            }
     //        }
     //
     ////        
     ////        //Checking ceiling
     ////        for (int z = 0; z < side; z++) {
     ////            for (int x = 0; x < side; x++) {
     ////                for (int y = Radius; y < side; y++) {
     ////                    if (yesWall) {
     ////                        area.setObject(x, y, z, 'X');
     ////                    } else {
     ////                        if (area.getObjectAt(x, y, z) == ']') {
     ////                            yesWall = true;
     ////                        }
     ////                    }
     ////                }
     ////                yesWall = false;
     ////            }
     ////        }
     ////        
     ////        //Checking floor
     ////        for (int z = 0; z < side; z++) {
     ////            for (int x = 0; x < side; x++) {
     ////                for (int y = Radius; y >= 0; y--) {
     ////                    if (yesWall) {
     ////                        area.setObject(x, y, z, 'X');
     ////                    } else {
     ////                        if (area.getObjectAt(x, y, z) == ']') {
     ////                            yesWall = true;
     ////                        }
     ////                    }
     ////                }
     ////                yesWall = false;
     ////            }
     ////        }
     ////        
     //        return area;
     //    }
     */

    public Room getObjectsAt(Coor coor1, Coor coor2) {
        Coor target = new Coor(0, 0, 0);
        Coor obtar = new Coor(0, 0, 0);
//**        coor1.switchXY();
//**        coor2.switchXY();
        try {
            if (contains(coor1) && contains(coor2)) {
                Coor distance = coor1.distanceTo(coor2);
                Room area = new Room(distance.getX() - 1, distance.getY() - 1, distance.getZ() - 1);
                for (int x = coor1.getX(); x <= coor2.getX(); x++) {
                    for (int y = coor1.getY(); y <= coor2.getY(); y++) {
                        for (int z = coor1.getZ(); z <= coor2.getZ(); z++) {
                            target.setXYZ(x - coor1.getX(), y - coor1.getY(), z - coor1.getZ());
                            obtar.setXYZ(x, y, z);
                            //**target.switchXY();
                            area.setObject(target, getObjectAt(obtar));
                            //**area.setObject(target, getObjectAt(obtar.switchXY()));
                        }
                    }
                }
                return area;
            } else {
                return null;
            }
        } catch (Exception e) {
            //System.err.println("getObjectsAt: Negative array size " + coor1.toString() + " " + coor2.toString());
        }
        return null;
    }

    public void setEmptyWithWalls() {
        Coor target = new Coor(0, 0, 0);
        for (int x = 0; x < getXDim(); x++) {
            for (int y = 0; y < getYDim(); y++) {
                for (int z = 0; z < getZDim(); z++) {
                    if (x == 0 || x == getXDim() - 1 || y == 0 || y == getYDim() - 1 || z == 0 || z == getZDim() - 1) {
                        target.setXYZ(x, y, z);
                        setObject(target, ']');
                    } else {
                        target.setXYZ(x, y, z);
                        setObject(target, '.');
                    }
                }
            }
        }
    }

    public void setEmpty() {
        Coor target = new Coor(0, 0, 0);
        for (int x = 0; x < getXDim(); x++) {
            for (int y = 0; y < getYDim(); y++) {
                for (int z = 0; z < getZDim(); z++) {
                    target.setXYZ(x, y, z);
                    setObject(target, ' ');
                }
            }
        }
    }

    public void setObject(Coor coor, char sym) {
        /*
         Adds an Object to the Map.  
         List How far left, high and deep from origin.
         Followed by the Object to be added
         (x,y,z,object)
         */
        if (contains(coor)) {
            Cube[coor.getX()][coor.getY()][coor.getZ()] = sym;
        } else {
            //System.err.println("setObject: " + coor.toString() + " does not exist");
        }

    }

    public void addArea(Coor coor1, Coor coor2, char sym) {
        if (contains(coor1) && contains(coor2)) {
            Coor distance = coor1.distanceTo(coor2);
            if (distance.getX() >= 0 && distance.getY() >= 0 && distance.getZ() >= 0) {
                Coor target = new Coor(0, 0, 0);
                for (int x = coor1.getX(); x <= coor2.getX(); x++) {
                    for (int y = coor1.getY(); y <= coor2.getY(); y++) {
                        for (int z = coor1.getZ(); z <= coor2.getZ(); z++) {
                            target.setXYZ(x, y, z);
                            setObject(target, sym);
                        }
                    }
                }
            } else {
                //System.err.println("addAtea: NonPositive distance: " + distance.toString());
            }
        } else {
            //System.err.println("addArea: Either " + coor1.toString() + " or " + coor2.toString() + "does not exist");
        }
    }

    public void removeObject(Coor coor) {
        //**coor.switchXY();
        /*
         Adds an Object to the Map.  
         List How far left, high and deep from origin.
         Followed by the Object to be added
         (x,y,z,object)
         */
        if (contains(coor)) {
            Cube[coor.getX()][coor.getY()][coor.getZ()] = ' ';
        } else {
            //System.err.println("removeObject: " + coor.toString() + "does not exist");
        }

    }

    public void removeArea(Coor coor1, Coor coor2) {
        if (contains(coor1) && contains(coor2)) {
            Coor distance = coor1.distanceTo(coor2);
            if (distance.getX() >= 0 && distance.getY() >= 0 && distance.getZ() >= 0) {
                Coor obtar = new Coor(0, 0, 0);
                for (int x = coor1.getX(); x <= coor2.getX(); x++) {
                    for (int y = coor1.getY(); y <= coor2.getY(); y++) {
                        for (int z = coor1.getZ(); z <= coor2.getZ(); z++) {
                            obtar.setXYZ(x, y, z);
                            removeObject(obtar);
                        }
                    }
                }
            }
            //System.err.println("removeArea: NonPositive distance: " + distance.toString());

        } else {
            //System.err.println("removeArea: Either " + coor1.toString() + " or " + coor2.toString() + "does not exist");
        }
    }

    @Override
    public String toString() {
        Coor obtar = new Coor(0, 0, 0);
        String spaces = "";
        String astrixes = "";
        for (int i = 0; i < (int) (Math.ceil(getXDim() + 4 * getZDim())); i++) {
            spaces += " ";
            astrixes += "**";
        }
        astrixes += "\n";
        String map = spaces.substring(1) + "Ceiling\n      ";

        for (int y = getYDim() - 1; y >= 0; y--) {
            for (int z = 0; z < getZDim(); z++) {
                for (int x = 0; x < getXDim(); x++) {
                    obtar.setXYZ(x, y, z);
                    map += getObjectAt(obtar);
                }
                map += "  |  ";
            }
            if (y == Math.ceil((getYDim() - 1) / 2) + 1) {
                map = map.substring(0, map.length() - 4) + "\nFront ";
            } else {
                map = map.substring(0, map.length() - 4) + "\n      ";
            }
            if (y == Math.ceil((getYDim() - 1) / 2)) {
                map = map.substring(0, map.length() - 7) + "Back\n      ";
            }
        }
        map += spaces.substring(7) + "Floor\n";
        map += astrixes;
//        spaces = "";
//        astrixes = "";
//        for (int i = 0; i < (int) (Math.ceil((getYDim() / 2) * getXDim() + (getXDim() * 5) / 2)); i++) {
//            spaces += " ";
//            astrixes += "**";
//        }
//        }
//        astrixes += "\n";
//        map += spaces + "      Back\n      ";
//        for (int z = getZDim() - 1; z >= 0; z--) {
//            for (int x = 0; x < getXDim(); x++) {
//                for (int y = 0; y < getYDim(); y++) {
//                    obtar.setXYZ(x, y, z);
//                    map += getObjectAt(obtar);
//                }
//                map += "  |  ";
//            }
//            if (z == Math.ceil((getZDim() - 1) / 2) + 1) {
//                map = map.substring(0, map.length() - 4) + "\nFloor ";
//            } else {
//                map = map.substring(0, map.length() - 4) + "\n      ";
//            }
//            if (z == Math.ceil((getZDim() - 1) / 2)) {
//                map = map.substring(0, map.length() - 7) + "Ceiling\n      ";
//            }
//        }
//        map += spaces + "Front\n";
//        map += astrixes;
////              RIGHT
////        FRONT       BACK
////              LEFT
//        spaces = "";
//        astrixes = "";
//        for (int i = 0; i < (int) (Math.ceil(getZDim() / 2) * getYDim() + (getXDim() * 5) / 2); i++) {
//            spaces += " ";
//            astrixes += "**";
//        }
//        astrixes += "\n";
//        map += spaces + "      Ceiling\n      ";
//        for (int x = getXDim() - 1; x >= 0; x--) {
//            for (int y = 0; y < getYDim(); y++) {
//                for (int z = 0; z < getZDim(); z++) {
//                    obtar.setXYZ(x, y, z);
//                    map += getObjectAt(obtar);
//                }
//                map += "  |  ";
//            }
//            map = map.substring(0, map.length() - 3) + "\n";
//
//            if (x == Math.ceil((getXDim() - 1) / 2) + 1) {
//                map = map.substring(0, map.length() - 3) + "\nLeft  ";
//            } else {
//                map = map.substring(0, map.length() - 3) + "\n      ";
//            }
//            if (x == Math.ceil((getXDim() - 1) / 2)) {
//                map = map.substring(0, map.length() - 7) + " Right\n      ";
//            }
//        }
//        map += spaces + "Floor\n";
//        map += astrixes + astrixes;

        return map;

    }
}
