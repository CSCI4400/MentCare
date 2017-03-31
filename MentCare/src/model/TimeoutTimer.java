package model;

import controller.ViewMenuController;
import javafx.animation.PauseTransition;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Point2D;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TimeoutTimer {
	
	Pane gp;
	Stage window;
	Point2D prevMousePos = new Point2D(0,0);
	int timeOutDelay = 120;
	PauseTransition checkMouse;
	
	public TimeoutTimer(Pane g, Stage w) {
		gp = g;
		window = w;
	}
	public TimeoutTimer(Pane g, Stage w, int TOD) {
		gp = g;
		window = w;
		timeOutDelay = TOD;
	}
	
	public void start() {
		ObjectProperty<Point2D> mouseLocation = new SimpleObjectProperty<>(new Point2D(0, 0)); //Create a container for the mouse position
		gp.setOnMouseMoved(e -> mouseLocation.set(new Point2D(e.getX(), e.getY()))); //Fill the container with the mouse position
		
		checkMouse = new PauseTransition(Duration.seconds(timeOutDelay)); //Begin PauseTransition declaration
		checkMouse.setOnFinished( event -> {
			if (prevMousePos.getX() == mouseLocation.get().getX() && prevMousePos.getY() == mouseLocation.get().getY()) { //If the previous mouse position is equal to the current, then log out.
				logOut();
			} else { //Otherwise update the mouse location and restart the PauseTransition to check if the position is the same in x many seconds, where x is declared by the class member timeOutDelay
				prevMousePos = mouseLocation.get();
				checkMouse.play();
			}
		});
		checkMouse.play();
	}
	public void stop() {
		checkMouse.stop();
	}
	public void logOut() { //To be changed when we have a more robust login/logout system
		ViewMenuController logOut = new ViewMenuController();
		try {
			logOut.start(window);
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}


}
