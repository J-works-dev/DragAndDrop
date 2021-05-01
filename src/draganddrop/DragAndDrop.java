/**
 * Portfolio Question 5
 * SangJoon Lee
 * 30024165
 * 30/04/2021
 */
package draganddrop;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.*;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Iterator;

public class DragAndDrop extends Application {

    ArrayList<Text> toDoList = new ArrayList<Text>();
    ArrayList<Text> doneList = new ArrayList<Text>();
    
    
    @Override
    public void start(Stage stage) {
        stage.setTitle("J-works To Do List");
        Text first = new Text(50, 100, "Code My Dream!");
        toDoList.add(first);
        
        SplitPane splitPane = new SplitPane();
        GridPane leftGrid = new GridPane();
        leftGrid.setAlignment(Pos.CENTER);
        leftGrid.setHgap(10);
        leftGrid.setVgap(10);
        leftGrid.setPadding(new Insets(10, 10, 10, 10));
        
//        VBox leftControl  = new VBox();
        Label leftTitle = new Label("To Do List");
        leftTitle.setFont(Font.font("Tahoma", FontWeight.BOLD, 15));
        leftGrid.add(leftTitle, 0, 0, 2, 1);
//        leftTitle.setScaleX(2.0);
//        leftTitle.setScaleY(2.0);
//        leftControl.getChildren().add(leftTitle);
//        leftControl.setAlignment(Pos.TOP_CENTER);
        
        final VBox rightControl = new VBox();
        Label rightTitle = new Label("Done List");
        rightTitle.setScaleX(2.0);
        rightTitle.setScaleY(2.0);
        rightControl.getChildren().add(rightTitle);
        rightControl.setAlignment(Pos.TOP_CENTER);
        splitPane.getItems().addAll(leftGrid, rightControl);

        Scene scene = new Scene(splitPane, 800, 500);
//        Group root = new Group();
//        Scene scene = new Scene(root, 600, 500);
//        scene.setFill(Color.LIGHTGREEN);

        TextField getList = new TextField();
        Button buttonAdd = new Button("ADD");
        
        buttonAdd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String list = getList.getText();
                System.out.println(list);
                toDoList.add(new Text(list));
            }
        });
        if (toDoList.size() > 0) {
            for (int i = 2; i < toDoList.size() + 2; i++) {
//                leftControl.getChildren().add(toDolist.get(i));
                leftGrid.add(toDoList.get(i), 0, i);
            }
        }
        final Text source = toDoList.get(0);
//        source = toDoList.get(0);
        Text target = new Text();

        source.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                /* drag was detected, start drag-and-drop gesture*/
                System.out.println("onDragDetected");
                
//                source = toDoList.get(0); // need to change to clicked text
                /* allow MOVE transfer mode */
                Dragboard db = source.startDragAndDrop(TransferMode.MOVE);

                /* put a string on dragboard */
                ClipboardContent content = new ClipboardContent();
                content.putString(source.getText());
                db.setContent(content);

                event.consume();
            }
        });

        rightControl.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                /* data is dragged over the target */
                System.out.println("onDragOver");

                /* accept it only if it is  not dragged from the same node 
                 * and if it has a string data */
                if (event.getGestureSource() != rightControl
                        && event.getDragboard().hasString()) {
                    /* allow for moving */
                    event.acceptTransferModes(TransferMode.MOVE);
                }

                event.consume();
            }
        });

        rightControl.setOnDragEntered(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                /* the drag-and-drop gesture entered the target */
                System.out.println("onDragEntered");
                /* show to the user that it is an actual gesture target */
                if (event.getGestureSource() != rightControl
                        && event.getDragboard().hasString()) {
//                    target.setFill(Color.GREEN);
                }

                event.consume();
            }
        });

        rightControl.setOnDragExited(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                /* mouse moved away, remove the graphical cues */
//                target.setFill(Color.BLACK);

                event.consume();
            }
        });

        rightControl.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                /* data dropped */
                System.out.println("onDragDropped");
                /* if there is a string data on dragboard, read it and use it */
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasString()) {
//                    target.setText(db.getString());
                    doneList.add(new Text(db.getString()));
                    if (doneList.size() > 0) {
                        for (int i = 2; i < doneList.size() + 2; i++) {
            //                leftControl.getChildren().add(toDolist.get(i));
                            leftGrid.add(doneList.get(i), 0, i, 2, 1);
                        }
                    }
                    success = true;
                }
                /* let the source know whether the string was successfully 
                 * transferred and used */
                event.setDropCompleted(success);

                event.consume();
            }
        });

        source.setOnDragDone(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                /* the drag-and-drop gesture ended */
                System.out.println("onDragDone");
                Dragboard db = event.getDragboard();
                /* if the data was successfully moved, clear it */
                if (event.getTransferMode() == TransferMode.MOVE) {
                    Iterator itr = toDoList.iterator();
                    while (itr.hasNext()) {
                        String del = (String)itr.next();
                        if (del == db.getString()) {
                            itr.remove();
                        }
                    }
                }

                event.consume();
            }
        });

//        leftGrid.add(source, 0, 2, 2, 1);
//        leftControl.getChildren().add(source);
        leftGrid.add(getList, 0, 12, 2, 1);
        leftGrid.add(buttonAdd, 1, 13);
//        leftControl.getChildren().add(getList);
//        leftControl.getChildren().add(buttonAdd);
//        rightControl.getChildren().add(target);
        stage.setScene(scene);
        stage.show();
    }
    
    public static void main(String[] args) {
        Application.launch(args);
    }
    
}
