package model;

import javafx.animation.TranslateTransition;
import javafx.scene.SubScene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

public class SpaceRunnerSubscene extends SubScene {

	private final String FONT_PATH = "model/resources/kenvector_future.ttf";
	private final String BACKGROUND_IMAGE = "model/resources/yellow_panel.png";
	
	private boolean isHidden;
	
	public SpaceRunnerSubscene() {
		super(new AnchorPane(), 600, 400);
		prefWidth(600);
		prefHeight(400);
		
		BackgroundImage image = new BackgroundImage(new Image(BACKGROUND_IMAGE, 600, 400, false, true),
				BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
		
		AnchorPane root2 = (AnchorPane) this.getRoot();
		
		root2.setBackground(new Background(image));
		
		isHidden = true;
		
		setLayoutX(1024);
		setLayoutY(180);
		
		
	}
	
	public void moveSubscene(boolean gameMode) {
		TranslateTransition transition = new TranslateTransition();
		transition.setDuration(Duration.seconds(0.3));
		transition.setNode(this);
		
		if(isHidden) {
		if(gameMode == false) transition.setToX(-676);
		else transition.setToX(-1024);
		isHidden = false;
		} else {
			transition.setToX(0);
			isHidden = true;
		}
		
		transition.play();
	}
	
	public AnchorPane getPane() {
		return (AnchorPane) this.getRoot();
	}
	
	

}
