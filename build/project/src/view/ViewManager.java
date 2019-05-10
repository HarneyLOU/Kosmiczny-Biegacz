package view;

import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.InfoLabel;
import model.Ship;
import model.ShipPicker;
import model.SpaceRunnerButton;
import model.SpaceRunnerSubscene;



public class ViewManager {
	
	private static final int WIDTH = 1024;
	private static final int HEIGHT = 768;
	
	private AnchorPane mainPane;
	private Scene mainScene;
	private Stage mainStage;
	
	private final static int MENU_BUTTONS_START_X = 100;
	private final static int MENU_BUTTONS_START_Y = 150;
	
	private SpaceRunnerSubscene creditsSubscene;
	private SpaceRunnerSubscene helpSubscene;
	private SpaceRunnerSubscene scoreSubscene;
	private SpaceRunnerSubscene shipChooserSubscene;
	
	private SpaceRunnerSubscene sceneToHide;
	
	List<SpaceRunnerButton> menuButtons;
	List<ShipPicker> shipsList;
	
	public ViewManager() {
		menuButtons = new ArrayList<>();
		mainPane = new AnchorPane();
		mainScene = new Scene(mainPane, WIDTH, HEIGHT);
		mainStage = new Stage();
		mainStage.initStyle(StageStyle.UNDECORATED);
		mainStage.setScene(mainScene);
		createSubscenes();
		createButtons();
		createBackground();
		createLogo();
		
			}
	
	private void showSubscene(SpaceRunnerSubscene subScene) {
		
		
		if(subScene != sceneToHide)
		{
			if(sceneToHide != null) {
				sceneToHide.moveSubscene();
			}
			
				subScene.moveSubscene();
				sceneToHide = subScene;
		}
		else
		{
			subScene.moveSubscene();
			sceneToHide = null;
		}	
		
	}
	
	private void createSubscenes() {
		creditsSubscene = new SpaceRunnerSubscene();
		mainPane.getChildren().add(creditsSubscene);
		
		helpSubscene = new SpaceRunnerSubscene();
		mainPane.getChildren().add(helpSubscene);
		
		scoreSubscene = new SpaceRunnerSubscene();
		mainPane.getChildren().add(scoreSubscene);
		
				
		createShipChooserSubscene();
	}
	
	private void createShipChooserSubscene() {
		shipChooserSubscene = new SpaceRunnerSubscene();
		mainPane.getChildren().add(shipChooserSubscene);
		
		InfoLabel chooseShipLabel = new InfoLabel("CHOOSE YOUR CHAMPION");
		chooseShipLabel.setLayoutX(110);
		chooseShipLabel.setLayoutY(25);
		shipChooserSubscene.getPane().getChildren().add(chooseShipLabel);
		shipChooserSubscene.getPane().getChildren().add(createShipsToChoose());
		shipChooserSubscene.getPane().getChildren().add(createButtonToStart());
	}
	
	
	private HBox createShipsToChoose() {
		HBox box = new HBox();
		box.setSpacing(20);
		shipsList = new ArrayList<>();
		for(Ship ship : Ship.values()) {
			ShipPicker shipToPick = new ShipPicker(ship);
			shipsList.add(shipToPick);
			box.getChildren().add(shipToPick);
			shipToPick.setOnMouseClicked(new EventHandler<MouseEvent>() {
			
			@Override
			public void handle(MouseEvent event) {
				for (ShipPicker ship : shipsList) {
					ship.setIsCircleChoosen(false);
				}
					shipToPick.setIsCircleChoosen(true);
					shipToPick.getShip();
				}
			});
		}
		box.setLayoutX(300 - (118*2));
		box.setLayoutY(100);
		return box;
	}
	
	private SpaceRunnerButton createButtonToStart() {
		SpaceRunnerButton startButton = new SpaceRunnerButton("START");
		startButton.setLayoutX(350);
		startButton.setLayoutY(300);
		return startButton;
		
	}
	public Stage getMainStage() {
		return mainStage;
	}
	
	private void addMenuButton(SpaceRunnerButton button) {
		button.setLayoutX(MENU_BUTTONS_START_X);
		button.setLayoutY(MENU_BUTTONS_START_Y + menuButtons.size() * 100);
		menuButtons.add(button);
		mainPane.getChildren().add(button);
	}
	
	private void createButtons() {
		
		createStartButton();
		createScoreButton();
		createHelpButton();
		createCreditsButton();
		createExitButton();
		
	}
	
	private void createStartButton() {
		SpaceRunnerButton startButton = new SpaceRunnerButton("PLAY");
		addMenuButton(startButton);
		
		startButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				showSubscene(shipChooserSubscene);
			}
		});
	}
	
	private void createScoreButton() {
		SpaceRunnerButton scoreButton = new SpaceRunnerButton("SCORES");
		addMenuButton(scoreButton);
		
		scoreButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				showSubscene(scoreSubscene);
			}
		});
	}
	
	private void createHelpButton() {
		SpaceRunnerButton helpButton = new SpaceRunnerButton("HELP");
		addMenuButton(helpButton);
		
		helpButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				showSubscene(helpSubscene);
			}
		});
	}
	
	private void createCreditsButton() {
		SpaceRunnerButton creditsButton = new SpaceRunnerButton("CREDITS");
		addMenuButton(creditsButton);
		
		creditsButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				showSubscene(creditsSubscene);
			}
		});
	}
	
	private void createExitButton() {
		SpaceRunnerButton exitButton = new SpaceRunnerButton("EXIT");
		addMenuButton(exitButton);
		
		exitButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				mainStage.close();
				
			}
			
		});
	}
	
	private void createBackground() {
		Image backgroundImage = new Image("view/resources/purple.png", 256, 256, false,  true);
		BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
		mainPane.setBackground(new Background(background));
		
	}
	
	private void createLogo() {
		ImageView logo = new ImageView("view/resources/logo.png");
		logo.setLayoutX(256);
		logo.setLayoutY(25);
		logo.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				logo.setEffect(new DropShadow());
			}
		});
		logo.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				logo.setEffect(null);
			}
		});
		
		mainPane.getChildren().add(logo);
		
		
	}
	
	
}
