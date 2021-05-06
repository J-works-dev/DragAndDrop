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
import javafx.event.EventTarget;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.*;
import javafx.scene.paint.Color;
import javafx.scene.control.Alert;
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
import java.util.*;
import java.util.ArrayList;
import java.util.Iterator;

public class DragAndDrop extends Application {

    ArrayList<Text> toDoList = new ArrayList<Text>();
    ArrayList<Text> doneList = new ArrayList<Text>();
    static int count = 0;
    
    @Override
    public void start(Stage stage) {
        stage.setTitle("J-works To Do List");
        Text first = new Text(50, 100, "Code My Dream!");
        Text second = new Text(50, 100, "second");
        Text third = new Text(50, 100, "third!");
        toDoList.add(first);
        toDoList.add(second);
        toDoList.add(third);
        
        final SplitPane splitPane = new SplitPane();
        GridPane leftGrid = new GridPane();
        leftGrid.setAlignment(Pos.CENTER);
        leftGrid.setHgap(10);
        leftGrid.setVgap(10);
        leftGrid.setPadding(new Insets(10, 10, 10, 10));
        Label leftTitle = new Label("To Do List");
        leftTitle.setFont(Font.font("Tahoma", FontWeight.BOLD, 15));
        leftGrid.add(leftTitle, 0, 0, 2, 1);
               
        GridPane rightGrid = new GridPane();
        rightGrid.setAlignment(Pos.CENTER);
        rightGrid.setHgap(10);
        rightGrid.setVgap(10);
        rightGrid.setPadding(new Insets(10, 10, 10, 10));
        Label rightTitle = new Label("Done List");
        rightTitle.setFont(Font.font("Tahoma", FontWeight.BOLD, 15));
        rightGrid.add(rightTitle, 0, 0, 2, 1);

        splitPane.getItems().addAll(leftGrid, rightGrid);

        Scene scene = new Scene(splitPane, 800, 500);
        scene.setFill(Color.LIGHTGREEN);

        TextField getList = new TextField();
        Button buttonAdd = new Button("ADD");
        
        buttonAdd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (count > 10) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Alert");
                    alert.setHeaderText("To Do List is Full!");
                    alert.setContentText("Add after finish task");
                    alert.showAndWait();
                } else {
                    String list = getList.getText();
                    toDoList.add(new Text(list));
                    leftGrid.add(toDoList.get(count), 0, count + 2);
                    count++;
                }
            }
        });
        if (toDoList.size() > 0) {
            for (int i = 0; i < toDoList.size(); i++) {
//                leftControl.getChildren().add(toDolist.get(i));
                leftGrid.add(toDoList.get(i), 0, i + 2);
                count++;
            }
        }
        
        Text source = toDoList.get(0);
//        final Text target;

//        splitPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent mouseEvent) {
//              System.out.println(mouseEvent.getTarget());
//              System.out.println(mouseEvent.getTarget() instanceof Text);
//              System.out.println(mouseEvent.getTarget().getClass().getSimpleName());
//              if (mouseEvent.getTarget() instanceof Text) {
////                  Text temp = mouseEvent.getTarget().getText();
//                    EventTarget eventTarget = mouseEvent.getTarget();
//                    System.out.println(eventTarget);
//              }
//            }
//        });
        
        splitPane.setOnDragDetected(new EventHandler<MouseEvent>() {
         
            public void handle(MouseEvent event) {
                /* drag was detected, start drag-and-drop gesture*/
                System.out.println("onDragDetected");
                System.out.println(event.getTarget());
                System.out.println(event.getTarget() instanceof Text);
                System.out.println(event.getTarget().getClass().getSimpleName());
                if (event.getTarget() instanceof Text) {
                      EventTarget eventTarget = event.getTarget();
                      System.out.println(eventTarget);
                      System.out.println(toDoList.get(0));
                }
                
//                Text source = toDoList.get(0); // need to change to clicked text
                System.out.println("onDragDetected");
                /* allow MOVE transfer mode */
                Dragboard db = source.startDragAndDrop(TransferMode.MOVE);

                /* put a string on dragboard */
                ClipboardContent content = new ClipboardContent();
                content.putString(source.getText());
                db.setContent(content);

                event.consume();
            }
        });

        rightGrid.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                /* data is dragged over the target */
                System.out.println("onDragOver");

                /* accept it only if it is  not dragged from the same node 
                 * and if it has a string data */
                if (event.getGestureSource() != rightGrid
                        && event.getDragboard().hasString()) {
                    /* allow for moving */
                    event.acceptTransferModes(TransferMode.MOVE);
                }

                event.consume();
            }
        });

        rightGrid.setOnDragEntered(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                /* the drag-and-drop gesture entered the target */
                System.out.println("onDragEntered");
                /* show to the user that it is an actual gesture target */
                if (event.getGestureSource() != rightGrid
                        && event.getDragboard().hasString()) {
//                    target.setFill(Color.GREEN);
                }

                event.consume();
            }
        });

        rightGrid.setOnDragExited(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                /* mouse moved away, remove the graphical cues */
//                target.setFill(Color.BLACK);

                event.consume();
            }
        });

        rightGrid.setOnDragDropped(new EventHandler<DragEvent>() {
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
                        for (int i = 0; i < doneList.size(); i++) {
            //                leftControl.getChildren().add(toDolist.get(i));
                            leftGrid.add(doneList.get(i), 0, i + 2, 2, 1);
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

        leftGrid.setOnDragDone(new EventHandler<DragEvent>() {
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
                    System.out.println(toDoList);
                }

                event.consume();
            }
        });

        leftGrid.add(getList, 0, 15, 2, 1);
        leftGrid.add(buttonAdd, 2, 15);
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
