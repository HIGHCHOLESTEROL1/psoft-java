package hw8;
import hw7.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.Group;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.control.ScrollPane;

public class RPICampusPathsMain extends Application{
    // GUI to display RPI campus map, does not represent a ADT
    // allowing users to interact with the campus map GUI
    private Image mapImage;
    private ImageView mapView;
    private double currentScale = 1.0;
    private String building1 = "none";
    private String building2 = "none";
    private Text b1;
    private Text b2;
    private Text path;
    private double scalex = 2057 / 1024;
    private double scaley = 1921 / 768;
    // Graph that stores the graph of the RPI campus map
    private CampusPaths g;

    @Override
    /**
     * construct campus map GUI
     * @param primaryStage stage of the GUI
     * @requires none
     * @modifies mapImage, mapView
     * @effects setups mapImage and mapView
     * @returns none
     * throws IllegalStateException if file does not exist
     */
    public void start(Stage primaryStage) throws Exception{
        // create graph from campusPaths
        g = new CampusPaths();
        g.createNewGraph("data/RPI_map_data_Nodes.csv", "data/RPI_map_data_Edges.csv");
        // name GUI
        primaryStage.setTitle("RPI Route Helper");
        // set RPI map image
        mapImage = new Image("file:data/RPI_campus_map_2010_extra_nodes_edges.png");
        mapView = new ImageView();
        mapView.setFitHeight(1000);
        mapView.setFitWidth(1000);
        mapView.setImage(mapImage);

        // add image to layout
        ScrollPane s1 = new ScrollPane();
        s1.setContent(mapView);
        s1.setPannable(true);
        mapView.setOnMouseClicked(event -> handleMouseClicked(event));

        // add buttons
        Button zoomI = new Button("Zoom in");
        zoomI.setOnAction(event -> zoomIn());
        Button zoomO = new Button("Zoom out");
        zoomO.setOnAction(event -> zoomOut());
        // Reset button
        Button resetButton = new Button("Reset");
        resetButton.setOnAction(event -> reset());
        VBox buts = new VBox(50);
        buts.getChildren().addAll(zoomI, zoomO, resetButton);

        // intialize buildings

        // view for the text display of buildings picked and setup horizontal view
        HBox texts = new HBox(200);
        // instructions for clients
        Text instructions = new Text("PLEASE SELECT 2 BUILDINGS AND CLICK FIND PATH, CLICK RESET TO RESET BUILDINGS");
        // setup building texts that can be updated
        b1 = new Text();
        b1.setText("Selected Building One: " + building1);
        b2 = new Text();
        b2.setText("Selected Building Two: " + building2);
        // button to findpath
        Button findPath = new Button("Find Path");
        // set path up should be no path at start
        path = new Text();
        path.setText("no path found");
        findPath.setOnAction(event -> displayPath());
        VBox buildings = new VBox();
        buildings.getChildren().addAll(instructions, b1, b2, findPath, path);
        texts.getChildren().addAll(buts, buildings);

        // setups vertical view 
        VBox vBox = new VBox(8);
        vBox.getChildren().addAll(s1,texts); // Add resetButton to the VBox

        Scene scene = new Scene(vBox, 1024, 768); // Set the scene with vBox, not s1
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    /**
     * handle mouseClick that ooccur on the map. Allows user to click on buildings and once
     * 2 building are selected, finds shortes path between them and displays
     * @param event the action event of user clicked on the map.
     * @requires none
     * @modifes none
     * @effects none
     * @returns none
     * @throws none
     */
    private void handleMouseClicked(MouseEvent event) {
        if (event != null) { // Check if the event object is not null
            // Get coordinates of mouse click, consider the scale factor and offset
            double x = event.getX() * scalex + 80;
            double y = event.getY() * scaley;
            // Perform actions based on click coordinates (e.g., select building)
            String clicked = g.findBuilding(x, y);
            // get the building corresponding to the clicked coordinate
            // make sure the clicked coordinate is a building, if yes intialize to building
            if (building1.equals("none")){
                building1 = clicked;
                b1.setText("Selected Building One: " + building1);
            }
            else if(building2.equals("none")){
                building2 = clicked;
                b2.setText("Selected Building Two: " + building2);
            }
        }
    }
    
    /**
     * Find path button, finds path between 2 buildings clicked by user
     * @param none
     * @requires none
     * @modifes path
     * @effects intializes a path between 2 buildings clicked by users
     * @returns none
     * @throws none
     */
    private void displayPath(){
        // update path displayed
        path.setText(g.findPath(building1, building2));
    }

    /**
     * zooms in on map
     * @param none
     * @requires none
     * @modifes currentScale, mapView
     * @effects changes the scalability of the map
     * @returns none
     * @throws none
     */
    private void zoomIn() {
        currentScale *= 1.1;
        mapView.setScaleX(currentScale);
        mapView.setScaleY(currentScale);
    }

    /**
     * zooms out on map
     * @param none
     * @requires none
     * @modifes currentScale, mapView
     * @effects changes the scalability of the map
     * @returns none
     * @throws none
     */
    private void zoomOut() {
        currentScale /= 1.1;
        mapView.setScaleX(currentScale);
        mapView.setScaleY(currentScale);
    }

    /**
     * resets the map scale and resets the buildings selected
     * @param none
     * @requires none
     * @modifes currentScale, mapView, building1, building2
     * @effects resets scalability of map and resets the buildings selected
     * @returns none
     * @throws none
     */
    private void reset() {
        // reset buildings
        building1 = "none";
        building2 = "none";
        b1.setText("Selected Building One: " + building1);
        b2.setText("Selected Building Two: " + building1);
        // reset path
        path.setText("no path found");
        // Reset zoom level
        currentScale = 1.0;
        mapView.setScaleX(currentScale);
        mapView.setScaleY(currentScale);
    }
    
    /**
     * main Function, creates graph for campusGraph then launches the GUI
     * @param args
     * @requires none
     * @modifes graph
     * @effects creates graph using nodes and edges file
     * @returns none
     * @throws none
     */
    public static void main(String[] args){
        // calls GUI to launch
        launch(args);
    }
}