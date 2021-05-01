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
import javafx.scene.control.GridPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import java.util.ArrayList;

public class DragAndDrop extends Application {

    ArrayList<TextField> toDoList = new ArrayList<TextField>();
    ArrayList<TextField> doneList = new ArrayList<TextField>();
    
    @Override
    public void start(Stage stage) {
        stage.setTitle("J-works To Do List");

        SplitPane splitPane = new SplitPane();
        
        VBox leftControl  = new VBox();
        Label leftTitle = new Label("To Do List");
        leftTitle.setScaleX(2.0);
        leftTitle.setScaleY(2.0);
        leftControl.getChildren().add(leftTitle);
        leftControl.setAlignment(Pos.TOP_CENTER);
        
        VBox rightControl = new VBox();
        Label rightTitle = new Label("Done List");
        rightTitle.setScaleX(2.0);
        rightTitle.setScaleY(2.0);
        rightControl.getChildren().add(rightTitle);
        rightControl.setAlignment(Pos.TOP_CENTER);
        splitPane.getItems().addAll(leftControl, rightControl);

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
                toDoList.add(new TextField(list));
            }
        });
        if (toDoList.size() > 0) {
            for (int i = 0; i < toDoList.size(); i++) {
                leftControl.getChildren().add(toDolist.get(i));
            }
        }
        

        source.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                /* drag was detected, start drag-and-drop gesture*/
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

        target.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                /* data is dragged over the target */
                System.out.println("onDragOver");

                /* accept it only if it is  not dragged from the same node 
                 * and if it has a string data */
                if (event.getGestureSource() != target
                        && event.getDragboard().hasString()) {
                    /* allow for moving */
                    event.acceptTransferModes(TransferMode.MOVE);
                }

                event.consume();
            }
        });

        target.setOnDragEntered(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                /* the drag-and-drop gesture entered the target */
                System.out.println("onDragEntered");
                /* show to the user that it is an actual gesture target */
                if (event.getGestureSource() != target
                        && event.getDragboard().hasString()) {
                    target.setFill(Color.GREEN);
                }

                event.consume();
            }
        });

        target.setOnDragExited(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                /* mouse moved away, remove the graphical cues */
                target.setFill(Color.BLACK);

                event.consume();
            }
        });

        target.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                /* data dropped */
                System.out.println("onDragDropped");
                /* if there is a string data on dragboard, read it and use it */
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasString()) {
                    target.setText(db.getString());
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
                /* if the data was successfully moved, clear it */
                if (event.getTransferMode() == TransferMode.MOVE) {
                    source.setText("");
                }

                event.consume();
            }
        });

        leftControl.getChildren().add(source);
        leftControl.getChildren().add(getList);
        leftControl.getChildren().add(buttonAdd);
        rightControl.getChildren().add(target);
        stage.setScene(scene);
        stage.show();
    }
    
    public static void main(String[] args) {
        Application.launch(args);
    }
    
}
