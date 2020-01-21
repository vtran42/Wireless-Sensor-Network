package Tran_Tzul_CSC_401_501_HW4;
/**
 * @author Alvaro Tzul, Vuong Tran
 */
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Create the random adjacent List network graph with given: length x and y
 * Number of Node tranmission Length
 *
 * @author Vuong Tran
 */
public class NetworkGraph {

    private LinkedList<Node>[] adjNetwork; // array of linked list to hold the adjacent list network
    private int numberOfNode;   // number node in network
    private double tranmissionLength;  // lengh of tranmission
    private LinkedList<Node> linkedNode;

    public NetworkGraph() {
    }

    ;
    /**
     * create the network graph adjacent
     * @param linkedNode:  contain all the node 
     * @param numberOfNode 
     * @param tranmissionLength 
     */
    public NetworkGraph(LinkedList<Node> linkedNode, int numberOfNode,
            double tranmissionLength) {
        this.linkedNode = linkedNode;
        this.numberOfNode = numberOfNode;
        this.tranmissionLength = tranmissionLength;
        adjNetwork = new LinkedList[numberOfNode];

        for (int i = 0; i < linkedNode.size(); i++) {
            adjNetwork[i] = new LinkedList<>();   //initialize all the adjacent list
        }
        for (int i = 0; i < linkedNode.size(); i++) {
            Node n = linkedNode.get(i);
            //adjNetwork[i].add(n);
            for (int j = i + 1; j < linkedNode.size(); j++) {
                if (n.distanceToAnotherNode(linkedNode.get(j)) <= (double) tranmissionLength) {
                    adjNetwork[i].add(linkedNode.get(j));
                    adjNetwork[j].add(linkedNode.get(i));
                }
            }
        }
    }

    public LinkedList<Node> neighbors(int idNode) {
        return adjNetwork[idNode];
    }

    public int getNumberOfNode() {
        return numberOfNode;
    }

    /**
     * BFS Step 0: initialize discovered[]. explored[], parent[] step 1: enqueue
     * the root node s in the queue Q, discovered[s]= T step 2: while the queue
     * is not empty: peek head if the Q, say it is u while (u has undiscovered
     * neighbor node, say it is v) insert v at the end of Q (enqueue) mark v as
     * discovered make u as v's parent mark u as explored move v from Q
     * (dequeue)
     *
     * @return true if all node is connected, false for not connected
     */
    public boolean isConnectedBFS() {
        boolean connect = true;
        LinkedList<Integer> g[] = this.getAdjacentByID();
        // mark the current node as visited and enqueue it 
        boolean discovered[] = new boolean[this.getNumberOfNode()];
        boolean explored[] = new boolean[this.getNumberOfNode()];
        int parents[] = new int[this.getNumberOfNode()];
        int s = 0;
        //Create a quueue for BFS
        LinkedList<Integer> queue = new LinkedList<>();
        // mark the current node as visited and enqueue it 
        discovered[s] = true;// start with node 0
        queue.add(s);
        while (!queue.isEmpty()) {
            s = queue.poll();// peek the first node in the list
            explored[s] = true; // set up node is explored
            Iterator<Integer> i = g[s].listIterator();
            // Get all adjacent vertices of the dequeued vertex s
            while (i.hasNext()) {
                int n = i.next();
                //System.out.println(n);
                // If a adjacent has not been visited, then mark it
                if (!discovered[n]) {
                    queue.add(n);// visited and enqueue it
                    discovered[n] = true;
                    parents[n] = s;
                }
            }
        }
        /*
        System.out.println("\n"+"prarents list");
        for(int i = 0; i < graph.getNumberOfNode(); i++)
            System.out.print(i + " ");
        System.out.println("");
        for(int i:parents)
            System.out.print (i + " ");
         System.out.println("");
         */
        for (int i = 0; i < this.getNumberOfNode(); i++) {
            //System.out.print( explored[i] + " ");
            connect &= explored[i];
        }
        return connect;
    }

    public LinkedList<Integer> printNodeConnected(int root) {
        LinkedList<Integer> g[] = this.getAdjacentByID();
        LinkedList<Integer> setConection = new LinkedList<>();
        // mark the current node as visited and enqueue it 
        boolean discovered[] = new boolean[this.getNumberOfNode()];
        boolean explored[] = new boolean[this.getNumberOfNode()];
        //int parents[] = new int[graph.getNumberOfNode()];
        int s = root;
        //Create a quueue for BFS
        LinkedList<Integer> queue = new LinkedList<>();
        // mark the current node as visited and enqueue it 
        discovered[s] = true;// start with node 0
        queue.add(s);
        while (!queue.isEmpty()) {
            s = queue.poll();// peek the first node in the list
            //explored[s] = true; // set up node is explored     
            Iterator<Integer> i = g[s].listIterator();
            // Get all adjacent vertices of the dequeued vertex s
            while (i.hasNext()) {
                int n = i.next();
                //System.out.println(n);
                // If a adjacent has not been visited, then mark it
                if (!discovered[n]) {
                    queue.add(n);// visited and enqueue it
                    discovered[n] = true;
                    //parents[n] = s;  
                }
            }
        }
        //System.out.print("\n" + root + " connect to ");
        for (int i = 0; i < this.getNumberOfNode(); i++) {
            if (discovered[i] == true) {
                setConection.add(i);
            }
            //System.out.print(i + " ");
        }
        return setConection;
    }

    public double getTranmissionLength() {
        return tranmissionLength;
    }

    public Node getNode(int nodeId) {
        //Node n = new Node();
        //n = linkedNode.get(nodeId);
        /* for (LinkedList<Node> adjNetwork1 : adjNetwork) {
            for (int j = 0; j < adjNetwork1.size(); j++) {
                if (adjNetwork1.get(j).getNodeId() == nodeId) {
                    n = adjNetwork1.get(j);
                }
            }
        }
         */
        return linkedNode.get(nodeId);
    }

    public int getNodeID(int nodeId) {
        //Node n = new Node();
        //n = linkedNode.get(nodeId);
        /* for (LinkedList<Node> adjNetwork1 : adjNetwork) {
            for (int j = 0; j < adjNetwork1.size(); j++) {
                if (adjNetwork1.get(j).getNodeId() == nodeId) {
                    n = adjNetwork1.get(j);
                }
            }
        }
         */
        return linkedNode.get(nodeId).getNodeId();
    }

    public LinkedList<Node>[] getAdjNetwork() {
        return adjNetwork;
    }

    /**
     *
     * @return
     */
    public LinkedList<Integer>[] getAdjacentByID() {
        LinkedList<Integer>[] graph = new LinkedList[numberOfNode];
        for (int i = 0; i < numberOfNode; i++) {
            graph[i] = new LinkedList<>();
        }
        for (int i = 0; i < numberOfNode; i++) {
            for (int j = 0; j < adjNetwork[i].size(); j++) {
                graph[i].add(adjNetwork[i].get(j).getNodeId());
            }
        }
        return graph;
    }

    public void printAdjacentList() {
        for (int i = 0; i < numberOfNode; i++) {
            System.out.print(i + ": ");
            for (int j = 0; j < adjNetwork[i].size(); j++) {
                System.out.print(" ==> " + adjNetwork[i].get(j).getNodeId());
                //System.out.print(adjNetwork[i].get(j).toString()+" ==> ");
            }
            System.out.println();
        }
    }

    public void printConnectedGraph() {
        int setCount = 0;
        boolean nodeInSet[] = new boolean[numberOfNode];
        for (int i = 0; i < numberOfNode; i++) {
            if (this.getAdjNetwork()[i].isEmpty()) {
                System.out.println("[" + this.getNodeID(i) + "] only one component");
                setCount++;
            }
            if (this.getAdjacentByID()[i].size() > 0 && !nodeInSet[i]) {
                //System.out.println(printNodeConnected(graph, i));
                nodeInSet[i] = true;
                Iterator<Integer> x = this.printNodeConnected(i).listIterator();
                while (x.hasNext()) {
                    int n = x.next();
                    nodeInSet[n] = true;
                }
                System.out.println(this.printNodeConnected(i)
                        + " Number node connected: " + this.printNodeConnected(i).size());
                setCount++;
            }
        }
        System.out.println("Set of connected components: " + setCount);
    }

}
