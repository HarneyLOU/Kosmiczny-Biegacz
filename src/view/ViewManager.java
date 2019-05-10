package view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import model.InfoLabel;
import model.Ship;
import model.ShipPicker;
import model.SpaceRunnerButton;
import model.SpaceRunnerSubscene;
import model.Scores;


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
	
	private Ship chosenShip;
	
	private Media menuMusic;
	private AudioClip playSound;
	
	private ImageView ship;
	private ImageView meteor;
	private AnimationTimer menuTimer;
	
	private Label leaderboard;
	private Label leaderboardScores;
	
	public ViewManager() {
		menuButtons = new ArrayList<>();
		mainPane = new AnchorPane();
		mainScene = new Scene(mainPane, WIDTH, HEIGHT);
		mainStage = new Stage();
		mainStage.initStyle(StageStyle.UNDECORATED);
		mainStage.setScene(mainScene);
		createDecoration();
		createSubscenes();
		createButtons();
		createBackground();
		createLogo();
		createMenuLoop();
		addMusic();
			}
	
	private void addMusic() {
		String musicFile = "src/model/resources/menu.mp3";   
		menuMusic = new Media(new File(musicFile).toURI().toString());
		MediaPlayer player = new MediaPlayer(menuMusic);
		
		String musicPlay = "src/model/resources/switch-15.mp3";   
		playSound = new AudioClip(new File(musicPlay).toURI().toString());
		
		player.setVolume(0.5);
		player.setAutoPlay(true);
		player.setCycleCount(MediaPlayer.INDEFINITE);
		
	}
	
	private void showSubscene(SpaceRunnerSubscene subScene) {
		
		
		if(subScene != sceneToHide)
		{
			if(sceneToHide != null) {
				sceneToHide.moveSubscene(false);
			}
			
				subScene.moveSubscene(false);
				sceneToHide = subScene;
		}
		else
		{
			subScene.moveSubscene(false);
			sceneToHide = null;
		}	
		
	}
	
	private void createSubscenes() {
		creditsSubscene = new SpaceRunnerSubscene();
		mainPane.getChildren().add(creditsSubscene);
		
		helpSubscene = new SpaceRunnerSubscene();
		mainPane.getChildren().add(helpSubscene);
		
		
		createScoreSubscene();
		
		createShipChooserSubscene();
	}
	
	private void createScoreSubscene() {
		scoreSubscene = new SpaceRunnerSubscene();
		mainPane.getChildren().add(scoreSubscene);
		
		leaderboard = new Label();
		leaderboardScores = new Label();
		
		InfoLabel infoBoard = new InfoLabel("BEST TIMES");
		infoBoard.setLayoutX(115);
		infoBoard.setLayoutY(25);
		
		leaderboard.setLayoutX(50);
		leaderboard.setLayoutY(65);
		leaderboard.setLineSpacing(5);
		
		leaderboardScores.setLayoutX(400);
		leaderboardScores.setLayoutY(65);
		leaderboardScores.setLineSpacing(5);
		try {
			leaderboard.setFont(Font.loadFont(new FileInputStream(new File("src/model/resources/kenvector_future.ttf")), 20));
			leaderboardScores.setFont(Font.loadFont(new FileInputStream(new File("src/model/resources/kenvector_future.ttf")), 20));
		} catch (FileNotFoundException e) {
			leaderboard.setFont(Font.font("Verdana", 20));
			leaderboardScores.setFont(Font.font("Verdana", 20));
		}
		
		scoreSubscene.getPane().getChildren().addAll(leaderboard, leaderboardScores, infoBoard);
		
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
					playSound.play();
					shipToPick.setIsCircleChoosen(true);
					chosenShip = shipToPick.getShip();
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
		
		startButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				if(chosenShip != null) {
					playSound.play();
					GameViewManager gameManager = new GameViewManager();
					gameManager.createNewGame(mainStage, chosenShip);
	
				}
				
			}
			
		});
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
				playSound.play();
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
				playSound.play();
				Scores.fulfillScores(leaderboard, leaderboardScores);
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
				playSound.play();
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
				playSound.play();
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
				playSound.play();
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
	
	private void createDecoration() {
		ship = new ImageView("view/resources/shipMenu.png");
		ship.setLayoutX(300);
		ship.setLayoutY(300);
		mainPane.getChildren().add(ship);
		
		
	}
	
	private int directionX = 4;
	private int directionY = 4;
	
	private void moveDecoration() {
		
		ship.setRotate(ship.getRotate()+2);
		ship.setLayoutX(ship.getLayoutX()+directionX);
		ship.setLayoutY(ship.getLayoutY()+directionY);
	if(ship.getLayoutX() > 850 || ship.getLayoutX() < -20) directionX = -directionX;
	if(ship.getLayoutY() > 600 || ship.getLayoutY() < 0) directionY = -directionY;
			
	}
	
	private void createMenuLoop() {
		menuTimer = new AnimationTimer() {
				

			@Override
			public void handle(long now) {
				moveDecoration();
				
			}
		};
		menuTimer.start();
	}
	
	
}
