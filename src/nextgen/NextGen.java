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
public class NextGen {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Room Empty = new Room(9, 7, 5);
        Empty.setEmptyWithWalls();
        
        Coor alpha = new Coor(4, 2, 2);
        Coor beta = new Coor(4,4,3);
        
        
        Empty.setObject(beta, ']');
        Empty.setObject(beta, 'B');
        Empty.setObject(alpha, 'A');
        //Empty.addArea(alpha, beta, 'C');
        Empty.removeArea(alpha, beta.offSet(0, 0, 0));
        System.out.println(Empty.toString());
//  
//        ArrayList<Coor> walls = new ArrayList<>();
////        walls.add(new Coor(1, 1, 1));
////        walls.add(new Coor(1, 2, 1));
////        walls.add(new Coor(1, 3, 1));
//        //walls.add(new Coor(5, 7, 6));
////        walls.add(new Coor(3, 1, 1));
////        walls.add(new Coor(1, 2, 9));
////        walls.add(new Coor(2, 2, 1));
//
//        for (Coor i : walls) {
//            Empty.setObject(i, ']');
//        }
//
//        Coor player = new Coor(9, 9, 9);
//        //Empty.setObject(player, 'A');
//
//        Coor alpha = new Coor(1, 2, 1);
////        Coor beta = new Coor(7, 6, 3);
////        Empty.addArea(alpha, beta, ']');
////
////        Empty.removeObject(alpha.switchXY());
////        Empty.removeObject(beta.switchXY());
//////FIX switchXY
////        Coor capa = new Coor(3, 2, 1);
//       Coor delta = new Coor(5, 2, 3);
//        Empty.setObject(delta, 'D');
////      
//////        Room Empty = new Room(3,3,3);
//////        Empty.setEmpty();
//////        Empty.setObject(1,2,3, 'F');
//        
       System.out.println(Empty.toString());
        //"Vision Delta - " + delta.toString() + "\n" + Empty.Vision(delta));
//        System.out.println("Get objects from alpha to player A \n" + Empty.getObjectsAt(alpha, player));
//        System.out.println("Get Radius from A \n" + Empty.getObjectsInRadius(player, 5));
//    }

//    public static void printArray(ArrayList<Integer[]> temp) {
//        //Prints out an Array with numbered items
//        for (int i = 0; i < temp.size(); i++) {
//            for (int j = 0; j < 2; j++) {
//                System.out.print(temp.get(i)[j] + ", ");
//            }
//            System.out.print(temp.get(i)[2]);
//            System.out.println();
//        }
    }
    public static void printArray(ArrayList<Coor> temp) {
        //Prints out an Array with numbered items
        for (Coor temp1 : temp) {
            System.out.println(temp1.toString());
        }
    }
}
