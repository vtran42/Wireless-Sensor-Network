package Tran_Tzul_CSC_401_501_HW4;
/**
 * @author Alvaro Tzul, Vuong Tran
 */
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;

import javafx.application.Application;
import static javafx.application.Application.launch;

import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import javafx.scene.Cursor;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

public class TestNetworkGraph extends Application {

    public static Scanner sc = new Scanner(System.in);
    public static Random r = new Random();
    public static double xCoordinate;
    public static double yCoordinate;
    public static int numNode;
    public static int trLength;
    public static int p;
    public static int q;
    public static int sNode;
    public static int m;
    public static LinkedList<Node> list;
    public static NetworkGraph graph;
    public static int s;
    public static int e;
    public static Graph g;
    public static double energy;
    public static String shortestPath;
    public static String DGsList = "";  // list of all DGs 
    public static String storageList="";  // list of all storage node
    // GUI start here
    public void generateChart(LineChart<Number, Number> lc) {

        for (int i = 0; i < list.size(); i++) {
            XYChart.Series series = new XYChart.Series();

            if (graph.getAdjNetwork()[i].isEmpty()) {    // adjacent list
                int index = i;
                double x1 = list.get(index).getX();
                double y1 = list.get(index).getY();
                final XYChart.Data<Number, Number> data = new XYChart.Data<>(x1, y1);
                data.setNode(new HoveredThresholdNodea(list.get(i).getNodeId()));
                series.getData().add(data);
            }

            for (Node item : graph.getAdjNetwork()[i]) {
                int index = i;
                double x2 = list.get(index).getX();
                double y2 = list.get(index).getY();

                final XYChart.Data<Number, Number> data = new XYChart.Data<>(x2, y2);
                data.setNode(new HoveredThresholdNodea(list.get(i).getNodeId()));
                series.getData().add(data);
            }
            lc.getData().add(series);
        }
    }

    @Override
    public void start(Stage stage) {
        NumberAxis xAxis = new NumberAxis(0, xCoordinate, 1);
        xAxis.setLabel("X-axis\n" + "\nNodes: " + list.size() + "\tT. Range: "
                + trLength + "\tConnected: " + graph.isConnectedBFS()
                + "\t\tDGs: " + DGsList + "Storage: " + storageList
                + "\nStart: " + graph.getNode(s) + "\t End: " + graph.getNode(e)
                + "\n Shortest Path: " + shortestPath
                + "\n Energy: " + energy + " pico Joule");

        NumberAxis yAxis = new NumberAxis(0, yCoordinate, 1);
        yAxis.setLabel("Y-axis");

        stage.setTitle("Alvaro Tzul, Vuong Tran");
        final ScatterChart<Number, Number> sc = new ScatterChart<>(xAxis, yAxis);
        final LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Wireless Sensor Network - Shortest Path");
        XYChart.Series<Number, Number> series[];
        series = new XYChart.Series[list.size()];

        XYChart.Series<Number, Number> seriesSC[];
        seriesSC = new XYChart.Series[list.size()];

        for (int i = 0; i < series.length; i++) {

            series[i] = new XYChart.Series<>();
            seriesSC[i] = new XYChart.Series<>();
        }
        for (int i = 0; i < list.size(); i++) {

            double x = list.get(i).getX();
            double y = list.get(i).getY();

            if (graph.getAdjNetwork()[i].isEmpty()) {
                int index = i;
                double x1 = list.get(index).getX();
                double y1 = list.get(index).getY();
                series[i].getData().addAll(new XYChart.Data<>(x, y),
                        new XYChart.Data<>(x1, y1));
            }
            for (int j = 0; j < graph.getAdjNetwork()[i].size(); j++) {

                int index = graph.getAdjNetwork()[i].get(j).getNodeId();
                double x2 = list.get(index).getX();
                double y2 = list.get(index).getY();
                series[i].getData().addAll(new XYChart.Data<>(x, y),
                        new XYChart.Data<>(x2, y2));
            }
        }
        generateChart(lineChart);
        lineChart.setAxisSortingPolicy(LineChart.SortingPolicy.NONE);

        lineChart.setLegendVisible(false);
        Scene scene = new Scene(lineChart, 1050, 825);

        
        ///
        scene.getStylesheets().add(TestNetworkGraph.class.getResource("style.css").toExternalForm());
        
        
        
        lineChart.getData().addAll(Arrays.asList(series));
        stage.setScene(scene);
        
        stage.show();
    }

    class HoveredThresholdNodea extends StackPane {

        public HoveredThresholdNodea(Object object) {
            setPrefSize(14, 14);

            final Label label = createDataThresholdLabel(object);

            setOnMouseEntered((MouseEvent mouseEvent) -> {
                getChildren().setAll(label);
                setCursor(Cursor.NONE);
                toFront();
            });
            setOnMouseExited((MouseEvent mouseEvent) -> {
                //getChildren().clear();
            });
        }

        private Label createDataThresholdLabel(Object object) {
            final Label label = new Label(object + "");
             label.getStyleClass().addAll("default-color0", "chart-line-symbol", "chart-series-line");  // modified
             label.setStyle("-fx-font-size: 9; -fx-font-weight: bold;");  // modified 
            label.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
            return label;
        }
    }

    // GUI end here
    public static LinkedList<Node> createListNode(int numNode, double width,
            double length) {
        LinkedList<Node> list = new LinkedList<>();
        // create the linked list for node
        for (int i = 0; i < numNode; i++) {
            list.add(new Node(i, width, length));
        }
        return list;
    }

    public static Graph DijkstraAlgorithm(NetworkGraph net) {
        Graph g = new Graph();

        for (int i = 0; i < net.getNumberOfNode(); i++) {
            LinkedList neighbor = new LinkedList();
            List<Vertex> listV = new LinkedList<>();
            // store the arraylist associate with the node i
            neighbor = net.neighbors(i);// find the neighbor of the first list
            Node node = net.getNode(i);
            for (int j = 0; j < neighbor.size(); j++) {
                Node n = (Node) neighbor.get(j);
                Vertex v = new Vertex(n.getNodeId(), n.distanceToAnotherNode(node));
                listV.add(v);
            }
            g.addVertex(node.getNodeId(), listV);
        }

        return g;
    }

    public static void getInforFromUser() {

        System.out.println("Please enter the width: ");
        xCoordinate = sc.nextDouble();
        System.out.println("Please enter the length: ");
        yCoordinate = sc.nextDouble();
        System.out.println("Please enter the number of node in network: ");
        numNode = sc.nextInt();
        System.out.println("Please enter tranmission Length: ");
        trLength = sc.nextInt();
        System.out.println("Please enter number of Data Generators: ");
        p = sc.nextInt();
        System.out.println("Please enter number of data packets in each DGs");
        q = sc.nextInt();
        sNode = numNode - p;
        System.out.println("Enter the storage for each Storage Node");
        m = sc.nextInt();
    }

    public static boolean enoughStorageSpace() {
        return p * q <= sNode * m;
    }

    // set the node to DGs node randomly and print out the Id of DGsNode
    public static void printDGsNode() {
        LinkedList<Integer> a = new LinkedList<>();
        LinkedList<Integer> DGs = new LinkedList<>();
        int id;
        for (int i = 0; i < numNode; i++) {
            a.add(i);
        }
        for (int i = 0; i < p; i++) {
            DGs.add(a.remove(r.nextInt(a.size())));
        }
        while (!DGs.isEmpty()) {
            id = DGs.poll();
            graph.getNode(id).setDGs(true);
        }

        System.out.println("List of DGs node");
        for (int i = 0; i < numNode; i++) {
            if (graph.getNode(i).isDGs()) {
                DGsList += graph.getNode(i).getNodeId() + "\t";
                System.out.print(graph.getNode(i).getNodeId() + "   ");
            }
        }
        System.out.println("\nList of Storage node");
        for (int i = 0; i < numNode; i++) {
            if (!graph.getNode(i).isDGs()) {
                storageList += graph.getNode(i).getNodeId() + "\t";
                System.out.print(graph.getNode(i).getNodeId() + "   ");
            }
            
        }
    }

    public static void main(String[] args) {
        getInforFromUser();
        list = createListNode(numNode, xCoordinate, yCoordinate);
        graph = new NetworkGraph(list, numNode, trLength);

        if (!graph.isConnectedBFS()) {
            System.out.println("Graph is NOT connected");
        }
        if (!enoughStorageSpace()) {
            System.out.println("There is not enough storage in the network");
        }

        // check for graph is connected and enough storage
        while (!graph.isConnectedBFS() || !enoughStorageSpace()) {
            getInforFromUser();
            if (!graph.isConnectedBFS()) {
                System.out.println("Graph is NOT connected");
            }
            if (!enoughStorageSpace()) {
                System.out.println("There is not enough storage in the network");
            }
            list = createListNode(numNode, xCoordinate, yCoordinate);
            graph = new NetworkGraph(list, numNode, trLength);
        }
        printDGsNode();

        // print the node
        /* 
        System.out.println("********* ID and Coordinate of each node ********");
        for (int i = 0; i < numNode; i++){
            System.out.println(list.get(i).toString());
        }
         */
        // print the adjacent list    
        System.out.println("\n************** Adjacent List *************************");
        graph.printAdjacentList();

        System.out.println("\nPlease choose starting DGs node");
        int start = sc.nextInt();
        s = start;
        System.out.println("Please choose ending Storage node");
        int end = sc.nextInt();
        e = end;
        /*
        // check whether the graph is connected
        System.out.println("*********** Connected Status *****************");
        System.out.println("Connected: " + graph.isConnectedBFS());
        
        System.out.println("****** Set of connected node ******");
        graph.printConnectedGraph();       
         */
        g = DijkstraAlgorithm(graph);
        System.out.println(g.getShortestPath(start, end));
        List arr = g.getShortestPath(start, end);
        Stack<Integer> st = new Stack<>();
        // print out the ascending shortest path
        while (!arr.isEmpty()) {
            st.push((Integer) arr.remove(0));
        }

        System.out.print("Reverse: " + s + " ");
        // calculate the shortest length
        double shortestLength = 0;
        energy = 0;
        Node n = graph.getNode(start);
        shortestPath = n.getNodeId() + "\t";
        while (!st.empty()) {
            shortestLength += n.distanceToAnotherNode(graph.getNode(st.peek()));
            energy += n.engergy(graph.getNode(st.peek()));
            n = graph.getNode(st.peek());
            shortestPath += n.getNodeId() + "\t";
            System.out.print(st.pop() + " ");
        }
        
        System.out.println("\nshortest path " + shortestLength);
        System.out.println("Total Energy: " + energy);
        launch(args);
    }
}
