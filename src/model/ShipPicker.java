package model;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class ShipPicker extends VBox{

	private ImageView circleImage;
	private ImageView shipImage;
	
	private String circleNotChosen = "view/resources/shipchooser/grey_circle.png";
	private String circleChosen = "view/resources/shipchooser/yellow_boxTick.png";
	
	private Ship ship;
	
	private boolean isCircleChosen;
	
	public ShipPicker(Ship ship) {
		circleImage = new ImageView(circleNotChosen);
		shipImage = new ImageView(ship.getUrl());
		this.ship = ship;
		isCircleChosen = false;
		this.setAlignment(Pos.CENTER);
		this.setSpacing(20);
		this.getChildren().add(circleImage);
		this.getChildren().add(shipImage);
		
	}
	
	public Ship getShip() {
		return ship;
	}
	
	public boolean getCircleChosen() {
		return isCircleChosen;
	}
	
	public void setIsCircleChoosen(boolean isCircleChosen) {
		this.isCircleChosen = isCircleChosen;
		String imageToSet = this.isCircleChosen ? circleChosen : circleNotChosen;
		circleImage.setImage(new Image(imageToSet));
	}
	
}
