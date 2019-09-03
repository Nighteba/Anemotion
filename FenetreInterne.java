package betapackage;


import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

/**
 * @author zella.
 * https://stackoverflow.com/questions/17673292/internal-frames-in-javafx
 */
public class FenetreInterne extends Region {


    //current state
    private boolean RESIZE_BOTTOM;
    private boolean RESIZE_RIGHT;

    public void setRoot(Node node) {
        getChildren().add(node);
    }

    public void makeFocusable() {
        this.setOnMouseClicked(mouseEvent -> {
            toFront();
        });
    }

    //we can select nodes that react drag event
    public void makeDraggable(Node what) {
        final Delta dragDelta = new Delta();
        what.setOnMousePressed(mouseEvent -> {
            dragDelta.x = getLayoutX() - mouseEvent.getScreenX();
            dragDelta.y = getLayoutY() - mouseEvent.getScreenY();
            //also bring to front when moving
            toFront();
        });
        what.setOnMouseDragged(mouseEvent -> {
            setLayoutX(mouseEvent.getScreenX() + dragDelta.x);
            setLayoutY(mouseEvent.getScreenY() + dragDelta.y);
        });
    }


    public void makeResizable(double mouseBorderWidth) {
        this.setOnMouseMoved(mouseEvent -> {
            //local window's coordinates
            double mouseX = mouseEvent.getX();
            double mouseY = mouseEvent.getY();
            //window size
            double width = this.boundsInLocalProperty().get().getWidth();
            double height = this.boundsInLocalProperty().get().getHeight();
            //if we on the edge, change state and cursor
            if (Math.abs(mouseX - width) < mouseBorderWidth
              && Math.abs(mouseY - height) < mouseBorderWidth) {
                RESIZE_RIGHT = true;
                RESIZE_BOTTOM = true;
                this.setCursor(Cursor.NW_RESIZE);
            } else {
                RESIZE_BOTTOM = false;
                RESIZE_RIGHT = false;
                this.setCursor(Cursor.DEFAULT);
            }

        });
        this.setOnMouseDragged(mouseEvent -> {
            //resize root
            Region region = (Region) getChildren().get(0);
            //resize logic depends on state
            if (RESIZE_BOTTOM && RESIZE_RIGHT) {
                region.setPrefSize(mouseEvent.getX(), mouseEvent.getY());
            } else if (RESIZE_RIGHT) {
                region.setPrefWidth(mouseEvent.getX());
            } else if (RESIZE_BOTTOM) {
                region.setPrefHeight(mouseEvent.getY());
            }
        });
    }

    public void setCloseButton(Button btn) {
        btn.setOnAction(event -> ((Pane) getParent()).getChildren().remove(this));
    }

    //just for encapsulation
    private class Delta {
        double x, y;
    }
}