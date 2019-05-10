package view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.InfoLabel;
import model.Scores;
import model.Ship;
import model.SmallInfoLabel;
import model.SpaceRunnerButton;
import model.SpaceRunnerSubscene;

public class GameViewManager {

	private AnchorPane gamePane;
	private Scene gameScene;
	private Stage gameStage;
	
	private static final int GAME_WIDTH = 600;
	private static final int GAME_HEIGHT =	800;
	
	private Stage menuStage;
	private ImageView ship;
	
	private boolean isLeftKeyPressed;
	private boolean isRightKeyPressed;
	private boolean isUpKeyPressed;
	private boolean isDownKeyPressed;
	private int angle;
	private AnimationTimer gameTimer;
	
	private final static String METEOR_BROWN = "view/resources/meteorBrown_med3.png";
	private final static String METEOR_BROWN_BIG = "view/resources/meteorBrown_big4.png";
	private final static String METEOR_GREY = "view/resources/meteorGrey_med1.png";
	private final static String METEOR_GREY_BIG = "view/resources/meteorGrey_big1.png";
	
	private ImageView[] brownMeteors;
	private ImageView[] bigBrownMeteors;
	private ImageView[] greyMeteors;
	Random randomPositionGenerator;
	
	private ImageView star;
	private SmallInfoLabel pointsLabel;
	private ImageView[] playerLifes;
	private ImageView engineLeft;
	private ImageView engineRight;
	
	private int playerLife;
	private int points;
	private final static String GOLD_STAR_IMAGE = "view/resources/star_gold.png";
	
	private GridPane gridPane1;
	private GridPane gridPane2;
	private final static String BACKGROUND_IMAGE = "view/resources/purple.png";
	
	private final static int STAR_RADIUS = 15;
	private final static int SHIP_RADIUS = 27;
	private final static int METEOR_RADIUS = 20;
	private final static int BIG_METEOR_RADIUS = 49;
	
	private int pseudoTime;
	private long lastTime;
	
	private SmallInfoLabel timeLabel;
	private int difficulty;
	
	private AudioClip movingSound;
	
	public GameViewManager() {
		initializeStage();
		createKeyListener();
		randomPositionGenerator = new Random();
	}


	private void createKeyListener() {
		
		gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				
				if(!movingSound.isPlaying()) movingSound.play();
				engineLeft.setVisible(true);
				engineRight.setVisible(true);
				
				if(event.getCode() == KeyCode.LEFT) {
					isLeftKeyPressed = true;
				} else if(event.getCode() == KeyCode.RIGHT) {
					isRightKeyPressed = true;
				} else if(event.getCode() == KeyCode.UP) {
					isUpKeyPressed = true;
				} else if(event.getCode() == KeyCode.DOWN) {
					isDownKeyPressed = true;
				}
				
			}
			
						
		});
		
		gameScene.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				
				
				if(event.getCode() == KeyCode.LEFT) {
					isLeftKeyPressed = false;
				} else if(event.getCode() == KeyCode.RIGHT) {
					isRightKeyPressed = false;
				} else if(event.getCode() == KeyCode.UP) {
					isUpKeyPressed = false;
				} else if(event.getCode() == KeyCode.DOWN) {
					isDownKeyPressed = false;
				}
				
			}
			
						
		});
		
	}
	
	private void createGameElements(Ship chosenShip) {
		
		difficulty = 10;
		
		timeLabel = new SmallInfoLabel("TIME : 0");
		timeLabel.setLayoutX(10);
		timeLabel.setLayoutY(20);
		
		playerLife = 2;
		star = new ImageView(GOLD_STAR_IMAGE);
		
		setNewElementPosition(star);
		gamePane.getChildren().add(star);
		pointsLabel = new SmallInfoLabel("POINTS : 00");
		pointsLabel.setLayoutX(460);
		pointsLabel.setLayoutY(20);
		gamePane.getChildren().addAll(pointsLabel, timeLabel);
		playerLifes = new ImageView[3];
		
		for(int i = 0; i< playerLifes.length; i++) {
			playerLifes[i] = new ImageView("/view/resources/playerLife1_blue.png");
			playerLifes[i].setLayoutX(455 + (i*50));
			playerLifes[i].setLayoutY(80);
			gamePane.getChildren().add(playerLifes[i]);
		}
		
		brownMeteors = new ImageView[difficulty];
		for(int i =0; i< brownMeteors.length;i++) {
			brownMeteors[i] = new ImageView(METEOR_BROWN);
			setNewElementPosition(brownMeteors[i]);
			gamePane.getChildren().add(brownMeteors[i]);
		}
		
		bigBrownMeteors = new ImageView[difficulty/3];
		for(int i = 0; i < bigBrownMeteors.length;i++) {
			bigBrownMeteors[i] = new ImageView(METEOR_BROWN_BIG);
			setNewElementPosition(bigBrownMeteors[i]);
			gamePane.getChildren().add(bigBrownMeteors[i]);
		}
		
		greyMeteors = new ImageView[difficulty];
		for(int i =0; i< greyMeteors.length;i++) {
			greyMeteors[i] = new ImageView(METEOR_GREY);
			setNewElementPosition(greyMeteors[i]);
			gamePane.getChildren().add(greyMeteors[i]);
		}
	}

	private void moveGameElements() {
			star.setLayoutY(star.getLayoutY()+2);
			star.setRotate(star.getRotate()+3);
		
		
		
		for(int i = 0; i < brownMeteors.length;i++) {
			brownMeteors[i].setLayoutY(brownMeteors[i].getLayoutY()+5);
			brownMeteors[i].setRotate(brownMeteors[i].getRotate()+4);
		}
		for(int i = 0; i < greyMeteors.length;i++) {
			greyMeteors[i].setLayoutY(greyMeteors[i].getLayoutY()+7);
			greyMeteors[i].setRotate(greyMeteors[i].getRotate()+4);
		}
		for(int i = 0; i < bigBrownMeteors.length;i++) {
			bigBrownMeteors[i].setLayoutY(bigBrownMeteors[i].getLayoutY()+3);
			bigBrownMeteors[i].setRotate(bigBrownMeteors[i].getRotate()+4);
		}
	}
	
	private void setNewElementPosition(ImageView image) {
		image.setLayoutX(randomPositionGenerator.nextInt(570));
		image.setLayoutY(-(randomPositionGenerator.nextInt(3200)+600));
	}

	private void checkIfBelow() {
		if(star.getLayoutY() > 1200) {
			setNewElementPosition(star);
		}
		
		for(int i =0; i<brownMeteors.length; i++) {
			if(brownMeteors[i].getLayoutY() > 900) {
				setNewElementPosition(brownMeteors[i]);
			}
		}
		for(int i =0; i<greyMeteors.length; i++) {
			if(greyMeteors[i].getLayoutY() > 900) {
				setNewElementPosition(greyMeteors[i]);
			}
		}
			
		for(int i =0; i<bigBrownMeteors.length; i++) {
			if(bigBrownMeteors[i].getLayoutY() > 900) {
				setNewElementPosition(bigBrownMeteors[i]);
			}

		}
		
	}
	
	private void initializeStage() {
		gamePane = new AnchorPane();
		gameScene = new Scene(gamePane, GAME_WIDTH, GAME_HEIGHT);
		gameStage = new Stage();
		gameStage.setScene(gameScene);
		
	}
	
	public void createNewGame(Stage menuStage, Ship chosenShip) {
		this.menuStage = menuStage;
		this.menuStage.hide();
		createBackground();
		createShip(chosenShip);
		createGameElements(chosenShip);
		createGameLoop();
		
		String movingSoundPath = "src/model/resources/spaceship.wav";   
		movingSound = new AudioClip(new File(movingSoundPath).toURI().toString());
		movingSound.setVolume(0.3);
		
		gameStage.show();
	}
	
	private void createShip(Ship chosenShip) {
		
		engineLeft = new ImageView("view/resources/fire03.png");
		engineRight = new ImageView("view/resources/fire03.png");
		
		gamePane.getChildren().addAll(engineLeft, engineRight);
		
		ship = new ImageView(chosenShip.getUrl());
		ship.setLayoutX(GAME_WIDTH/2);
		ship.setLayoutY(GAME_HEIGHT - 90);
		gamePane.getChildren().add(ship);
		
		
	}
	
	
	private void createGameLoop() {
		gameTimer = new AnimationTimer() {
			
			

			@Override
			public void handle(long now) {
			
				timer(now);
				moveBackground();
				moveGameElements();
				checkIfBelow();
				checkCollision();
				moveShip();
				
			
			}
	};
	gameTimer.start();
	}
	
	private void timer(long now)
	{
		if (lastTime != 0) {
            if (now > lastTime + 1_000_000_000) {
                pseudoTime++;
                timeLabel.setText("TIME : " + pseudoTime);
                lastTime = now;
            }
        } else {
            lastTime = now;
        }
	}
	
	
	private void moveShip() {
		
		
		if(!isLeftKeyPressed && !isRightKeyPressed && !isUpKeyPressed && !isDownKeyPressed) 
		{
		movingSound.stop();
		engineLeft.setVisible(false);
		engineRight.setVisible(false);
		}
		
		engineLeft.setLayoutX(ship.getLayoutX()+30);
		engineLeft.setLayoutY(ship.getLayoutY()+70);
		engineRight.setLayoutX(ship.getLayoutX()+53);
		engineRight.setLayoutY(ship.getLayoutY()+70);
		
	
		
		
		if(!isLeftKeyPressed && !isRightKeyPressed) {
			if(angle < 0) {
				angle +=5;
			} else if(angle > 0) {
				angle -=5;
			}
			ship.setRotate(angle);
			engineLeft.setScaleY(1);
			engineRight.setScaleY(1);
			
		}
		if(isLeftKeyPressed && !isRightKeyPressed) {
			if(angle > -45) {
				angle -= 5;
			}
			ship.setRotate(angle);
			if(ship.getLayoutX() > -20) {
				ship.setLayoutX(ship.getLayoutX()-4);
			}
			engineLeft.setScaleY(1);
			engineRight.setScaleY(1.75);
		}
		if(!isLeftKeyPressed && isRightKeyPressed) {
			if(angle < 45) {
				angle += 5;
			}
			ship.setRotate(angle);
			if(ship.getLayoutX() < 522) {
				ship.setLayoutX(ship.getLayoutX()+4);
			}
			engineRight.setScaleY(1);
			engineLeft.setScaleY(1.75);
		}
		if(isLeftKeyPressed && isRightKeyPressed) {
			engineLeft.setScaleY(0.5);
			engineRight.setScaleY(0.5);
			
		}
		if(isUpKeyPressed) {
			if(ship.getLayoutY() > 0) {
				ship.setLayoutY(ship.getLayoutY()-4);
			}
			engineLeft.setScaleY(1.75);
			engineRight.setScaleY(1.75);
		}
		if(isDownKeyPressed) {
			if(ship.getLayoutY() < 725) {
				ship.setLayoutY(ship.getLayoutY()+4);
			}
			engineLeft.setScaleY(0.75);
			engineRight.setScaleY(0.75);
	}
		
			
	}
	
	private void createBackground() {
		gridPane1 = new GridPane();
		gridPane2 = new GridPane();
		
		for(int i = 0; i<12; i++) {
			ImageView backgroundImage1 = new ImageView(BACKGROUND_IMAGE);
			ImageView backgroundImage2 = new ImageView(BACKGROUND_IMAGE);
			GridPane.setConstraints(backgroundImage1, i%3, i/3);
			GridPane.setConstraints(backgroundImage2, i%3, i/3);
			gridPane1.getChildren().add(backgroundImage1);
			gridPane2.getChildren().add(backgroundImage2);
			
		}
		
		
		gridPane2.setLayoutY(-1024);
		gamePane.getChildren().addAll(gridPane1, gridPane2);
	}
	
	private void moveBackground() {
		gridPane1.setLayoutY(gridPane1.getLayoutY() + 0.5);
		gridPane2.setLayoutY(gridPane2.getLayoutY() + 0.5);
		
		if(gridPane1.getLayoutY() >= 1024) {
			gridPane1.setLayoutY(-1024);
		}
		if(gridPane2.getLayoutY() >= 1024) {
			gridPane2.setLayoutY(-1024);
		}
	}
	
	private double calculateDistance(double x1, double x2, double y1, double y2) {
		return Math.sqrt(Math.pow(x1-x2, 2) + Math.pow(y1-y2, 2));
	}
	
	private void removeLife() {
		gamePane.getChildren().remove(playerLifes[playerLife]);
		playerLife--;
		if(playerLife < 3) {
			gameTimer.stop();
			if(Scores.checkIfHigher(pseudoTime) != -1) {
				nickSubmit();
			}
			else {
				gameStage.close();
				menuStage.show();
			}
			
			
		}
	}
	
	private void checkCollision() {
		if((SHIP_RADIUS + STAR_RADIUS)> calculateDistance(ship.getLayoutX()+49, star.getLayoutX()+15, ship.getLayoutY()+37, star.getLayoutY()+15)) {
			setNewElementPosition(star);
			points++;
			String textToSet = "POINTS : ";
			if(points < 10) {
				textToSet = textToSet + "0";
			}
			pointsLabel.setText(textToSet + points);
		}
		
		for(int i = 0; i < brownMeteors.length; i++) {
			if((SHIP_RADIUS + METEOR_RADIUS)> calculateDistance(ship.getLayoutX()+49, brownMeteors[i].getLayoutX()+20, ship.getLayoutY()+37, brownMeteors[i].getLayoutY()+20)) {
				setNewElementPosition(brownMeteors[i]);
				removeLife();
			}
		}
		for(int i = 0; i < greyMeteors.length; i++) {
			if((SHIP_RADIUS + METEOR_RADIUS)> calculateDistance(ship.getLayoutX()+49, greyMeteors[i].getLayoutX()+20, ship.getLayoutY()+37, greyMeteors[i].getLayoutY()+20)) {
				setNewElementPosition(greyMeteors[i]);
				removeLife();
				}
				
				
		}
		for(int i = 0; i < bigBrownMeteors.length; i++) {
			if((SHIP_RADIUS + BIG_METEOR_RADIUS)> calculateDistance(ship.getLayoutX()+49, bigBrownMeteors[i].getLayoutX()+49, ship.getLayoutY()+48, bigBrownMeteors[i].getLayoutY()+20)) {
				setNewElementPosition(bigBrownMeteors[i]);
				removeLife();
				}
				
				
		}
			
		
	}
	
	
	
	
	
	public void nickSubmit() {
		SpaceRunnerSubscene nickSubscene = new SpaceRunnerSubscene();
		nickSubscene.moveSubscene(true);
		InfoLabel winInfo = new InfoLabel("CONGRATULATIONS");
		winInfo.setLayoutX(115);
		winInfo.setLayoutY(25);
		
		VBox panel = new VBox(30);
		panel.setAlignment(Pos.BASELINE_CENTER);
		panel.setLayoutX(180);
		panel.setLayoutY(150);
		panel.setPrefHeight(100);
		
		
		
		TextField nickField = new TextField();
		nickField.setPromptText("Enter your nick");
		nickField.setPrefColumnCount(10);
		try {
			nickField.setFont(Font.loadFont(new FileInputStream(new File("src/model/resources/kenvector_future.ttf")), 20));
		} catch (FileNotFoundException e) {
			nickField.setFont(Font.font("Verdana", 20));
		}
		
		SpaceRunnerButton submit = new SpaceRunnerButton("Submit");
		
		submit.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				
				movingSound.stop();
				
				if(nickField.getText().trim().isEmpty() || nickField.getText().length() > 20 || nickField.getText().contains(" ")) {
					nickField.setText("");
					nickField.setPromptText("Wrong nick");
				}
				else {
					String newName = nickField.getText();
					Scores.addScore(pseudoTime, Scores.checkIfHigher(pseudoTime), newName);
					gameStage.close();
					menuStage.show();
				}
				
			}
			
		});
		
		gamePane.getChildren().addAll(nickSubscene);
		nickSubscene.getPane().getChildren().addAll(panel, winInfo);
		panel.getChildren().addAll(nickField, submit);
		
		
		
	}
	
	
	
	
	
	
	
	
}
