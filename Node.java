package Tran_Tzul_CSC_401_501_HW4;

/**
 * @author Alvaro Tzul, Vuong Tran
 */

import java.text.DecimalFormat;
import java.util.Random;

/**
 * @author Vuong Tran
 * Node class contain the x and y coordinate of one network
 */
public class Node {
    private double x;  // x coordinate of the node
    private double y; // y coordinate of the node
    private int nodeId; // index of the node
    private boolean DGs;// default is false meaning that is storage node
    //constructor
    public Node(){};// default constructor
    /**
     * @param nodeId
     * @param height of the network
     * @param width of the network
     */
    public Node(int nodeId, double width,double height){
        Random r = new Random();    // random number to generate the node
        this.nodeId = nodeId;
        this.x = r.nextDouble() * width;
        this.y = r.nextDouble() * height;
    }
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    public void setX(double x) {
        this.x = x;
    }
    public void setY(double y) {
        this.y = y;
    }
    public int getNodeId() {
        return nodeId;
    }
    public void setNodeId(int nodeId) {
        this.nodeId = nodeId;
    } 

    public boolean isDGs() {
        return DGs;
    }

    public void setDGs(boolean DGs) {
        this.DGs = DGs;
    }
    
    /**
     * 
     * @param n
     * @return true if equal and false if not equal
     */
    public boolean isEqual(Node n){
        return( x == n.getX() && y == n.getY());
    } 
    // distance from this node to another node
    /**
     * @param n another node
     * @return the distance between this node to another node 
     */
    public double distanceToAnotherNode(Node n){
        return(Math.sqrt(Math.pow(x - n.getX(),2) + Math.pow(y - n.getY(),2)));
    }
    public double engergy(Node n){
        double tranmission = Math.pow(10,5) + Math.pow(10,2) * Math.pow(distanceToAnotherNode(n),2);
        double receiving = Math.pow(10, 5);
        
        return tranmission + receiving;
    }
    @Override
    public String toString() {
        DecimalFormat d = new DecimalFormat("#.##");
        return "Id:"+nodeId +" "+'('+ d.format(x) + ", " + d.format(y) + ')';
    } 
}
