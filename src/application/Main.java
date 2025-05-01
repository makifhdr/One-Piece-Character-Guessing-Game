package application;
	
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;


public class Main extends Application {
	
	private GridPane grid;
	
	Scene mainScene = new Scene(new StackPane());
	
	boolean restarted = false;
	
	DataController dataController = new DataController();
	
	List<Character> list = dataController.loadCharacters();
	List<String> siralanmisArcList = dataController.getSiraliArcList();
	private Character targetCharacter;
	Map<String, Character> characterMap = dataController.getCharacterMap(list);
	
	ObservableList<String> suggestions = FXCollections.observableArrayList();
    ListView<String> suggestionListView = new ListView<>();
	
	ResourceLoader resourceLoader = new ResourceLoader();
	Image backgroundImage = resourceLoader.getBackgroundImage();
	Image menuBackGroundImage = resourceLoader.getMenuBackgroundImage();
	Font bangersFont = resourceLoader.getBangersFont();
    
    CreateCell createCell = new CreateCell(siralanmisArcList);
	
	@Override
	public void start(Stage primaryStage) {
	    
	    targetCharacter = dataController.getRandomCharacter(list);
		
		suggestions.addAll(characterMap.values().stream()
                .map(Character::getName)
                .sorted()
                .collect(Collectors.toList()));
		suggestionListView.setId("suggestionListView");
		
		VBox gameRoot = new VBox(10);
		gameRoot.setPadding(new Insets(10));

        TextField inputField = new TextField();
        inputField.setScaleX(3);
        inputField.setScaleY(3);
        inputField.setMinWidth(270);
        inputField.setTranslateY(40);
        inputField.setPromptText("Enter character name...");
        inputField.setId("inputField");
        
        suggestionListView.setItems(FXCollections.observableArrayList());
        suggestionListView.setStyle(STYLESHEET_CASPIAN);
        suggestionListView.setScaleX(3);
        suggestionListView.setScaleY(3);
        suggestionListView.setTranslateY(-275);
        suggestionListView.setMaxHeight(200);
        suggestionListView.setMinWidth(270);
        suggestionListView.setVisible(false);
        
        inputField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                suggestionListView.setVisible(false);
            } else {
                List<String> filtered = suggestions.stream()
                        .filter(name -> name.toLowerCase().contains(newValue.toLowerCase()))
                        .collect(Collectors.toList());
                suggestionListView.setItems(FXCollections.observableArrayList(filtered));
                suggestionListView.setVisible(!filtered.isEmpty());
            }
        });

        suggestionListView.setOnMouseClicked(event -> {
        	String userInput = suggestionListView.getSelectionModel().getSelectedItem();
            if (userInput != null) {
                Character guessedCharacter = characterMap.get(userInput);
                handleGuess(guessedCharacter, primaryStage);                
                suggestions.remove(userInput);
                List<String> filtered = suggestions.stream()
                        .filter(name -> name.toLowerCase().contains(inputField.getText().toLowerCase()))
                        .collect(Collectors.toList());
                suggestionListView.setItems(FXCollections.observableArrayList(filtered));
                suggestionListView.setVisible(false);
                inputField.clear();
            }
        });
        
        VBox vbox = new VBox(5, inputField, suggestionListView);
        vbox.setPrefHeight(200);
        vbox.setMinHeight(Region.USE_PREF_SIZE);
        vbox.setStyle("-fx-padding: 20;");

        grid = new GridPane();
        grid.setHgap(25);
        grid.setVgap(10);
        
        String[] headers = {
        	    "Character", "Gender", "Affiliation", "Devil Fruit Type",
        	    "Haki", "Bounty", "Height", "Origin", "First Arc", "Status"
        	};

        	for (int i = 0; i < headers.length; i++) {
        	    Label label = new Label(createCell.textFormatter(headers[i]));
        	    label.setTextAlignment(TextAlignment.CENTER);
        	    label.setStyle("-fx-font-size: 20px; -fx-text-fill: white; -fx-effect: dropshadow(one-pass-box, black, 20, 0.0, 0, 0); -fx-font-weight: bold;");
        	    grid.add(label, i, 0);
        	}
        
        grid.setAlignment(Pos.TOP_CENTER);
        
        BackgroundImage background1 = new BackgroundImage(
            backgroundImage,
            BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.CENTER,
            new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)
        );
        
        ScrollPane scrollPane = new ScrollPane(grid);
        scrollPane.setFitToWidth(true);
        
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        grid.setStyle("-fx-background-color: transparent;");
        
        //mainScene.setRoot(gameRoot);
        mainScene.getStylesheets().add(resourceLoader.getStyleSheet("/resources/style.css"));
        
        Button menuButton = new Button(createCell.textFormatter("Go back to main menu"));
        menuButton.setOnAction(e ->{
    		showMenuPage(primaryStage, mainScene, gameRoot);
        	});
        menuButton.setStyle("-fx-font-size: 20px; -fx-background-color: lightcoral; -fx-background-radius: 10;");
        menuButton.setScaleX(2);
        menuButton.setScaleY(2);
        menuButton.setTranslateX(250);
        menuButton.setTranslateY(50);
        
        HBox hbox = new HBox(9, vbox, menuButton);
        hbox.setAlignment(Pos.TOP_CENTER);
        
        VBox.setVgrow(scrollPane, Priority.ALWAYS);
        gameRoot.setBackground(new Background(background1));
        gameRoot.getChildren().addAll(scrollPane, hbox, grid);
        
		primaryStage.setMaximized(true);		
		showMenuPage(primaryStage, mainScene, gameRoot);
	}
	
	private void handleGuess(Character guessed, Stage primaryStage) {
		
        int newRow = grid.getRowCount();
        
        String characterName = guessed.getName();
        
        characterName = createCell.textFormatter(characterName);
        
        StackPane cell = new StackPane();
        Rectangle background = new Rectangle(120, 75);
        background.setArcWidth(30);
        background.setArcHeight(30);
        background.setFill(Color.ANTIQUEWHITE);
        
        Label nameLabel = new Label(characterName);
        nameLabel.setStyle("-fx-font-size: 15px; -fx-text-fill: white; -fx-effect: dropshadow(one-pass-box, black, 10, 0.0, 0, 0); -fx-font-weight: bold;");
        nameLabel.setTextAlignment(TextAlignment.CENTER);
        cell.getChildren().addAll(background, nameLabel);

        grid.add(cell, 0, newRow);
        grid.add(createCell.createPropertyCell(guessed.getGender().toString(), targetCharacter.getGender().toString()), 1, newRow);
        grid.add(createCell.createPropertyCell(guessed.getAffiliation(), targetCharacter.getAffiliation()), 2, newRow);
        grid.add(createCell.createPropertyCell(guessed.getDevilFruit().getType().toString(), targetCharacter.getDevilFruit().getType().toString()), 3, newRow);
        grid.add(createCell.createPropertyCellHaki(guessed.getHakiTypes(), targetCharacter.getHakiTypes()), 4, newRow);
        grid.add(createCell.createPropertyCellBounty(guessed.getBounty(), targetCharacter.getBounty()), 5, newRow);
        grid.add(createCell.createPropertyCellHeight(guessed.getHeight(), targetCharacter.getHeight()), 6, newRow);
        grid.add(createCell.createPropertyCell(guessed.getOrigin().toString(), targetCharacter.getOrigin().toString()), 7, newRow);
        grid.add(createCell.createPropertyCellFirstArc(guessed.getFirstArc(), targetCharacter.getFirstArc()), 8, newRow);
        grid.add(createCell.createPropertyCellStatus(guessed.getStatus().toString(), targetCharacter.getStatus().toString()), 9, newRow);
        
        if(guessed.equals(targetCharacter)) {
        	showWinPage(primaryStage, targetCharacter);
        }
    }
	
	private void showMenuPage(Stage primaryStage, Scene mainScene, VBox gameRoot) {
		StackPane menuRoot = new StackPane();
		
	    Label menuLabel = new Label("    WELCOME TO ONE PIECE\nCHARACTER GUESSING GAME");
	    menuLabel.setStyle("-fx-font-size: 40px; -fx-text-fill: white; -fx-effect: dropshadow(one-pass-box, black, 10, 0.0, 0, 0); -fx-font-weight: bold;");
	    	    
	    BackgroundImage menuBackground = new BackgroundImage(
	    		menuBackGroundImage,
	            BackgroundRepeat.NO_REPEAT,
	            BackgroundRepeat.NO_REPEAT,
	            BackgroundPosition.CENTER,
	            new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)
	        );
	    VBox layout = new VBox(20);
	    
	    Button startButton = new Button("▶ Play");
	    Button menuExitButton = new Button("❌ Exit");

	    startButton.setStyle("-fx-font-size: 20px; -fx-background-color: lightgreen; -fx-background-radius: 10;");
	    menuExitButton.setStyle("-fx-font-size: 20px; -fx-background-color: lightcoral; -fx-background-radius: 10;");

	    startButton.setOnAction(e -> {
	    	mainScene.setRoot(gameRoot);
	    	});
	    menuExitButton.setOnAction(e -> {
	    	primaryStage.close();
	    	});
	    
	    layout.setBackground(new Background(menuBackground));
	    layout.getChildren().addAll(menuLabel, startButton, menuExitButton);
	    layout.setAlignment(Pos.CENTER);	    

	    menuRoot.getChildren().add(layout);
	    
	    mainScene.setRoot(menuRoot);
	    primaryStage.setScene(mainScene);
	    primaryStage.setMaximized(true);
	    primaryStage.show();
	}
	
	private void showWinPage(Stage primaryStage, Character character) {
		Stage winStage = new Stage();
		
	    Label winLabel = new Label("🎉 YOU WIN! 🎉\nThe character was:\n   " + targetCharacter.getName());
	    winLabel.setTextAlignment(TextAlignment.CENTER);
	    winLabel.setStyle("-fx-font-size: 40px; -fx-text-fill: gold; -fx-font-weight: bold;");

	    Button restartButton = new Button("🔁 Restart");
	    Button exitButton = new Button("❌ Exit");

	    restartButton.setStyle("-fx-font-size: 20px; -fx-background-color: lightgreen;");
	    exitButton.setStyle("-fx-font-size: 20px; -fx-background-color: lightcoral;");

	    restartButton.setOnAction(e -> {
	    	restartGame(primaryStage);
	    	winStage.close();});
	    exitButton.setOnAction(e -> {
	    	primaryStage.close();
	    	winStage.close();});

	    VBox layout = new VBox(20, winLabel, restartButton, exitButton);
	    layout.setAlignment(Pos.CENTER);
	    layout.setStyle("-fx-background-color: navy; -fx-padding: 30px;");

	    Scene winScene = new Scene(layout, 800, 600);
	    winStage.setScene(winScene);
	    winStage.setResizable(false);
	    winStage.show();
	}

	private void restartGame(Stage primaryStage) {
		restarted = true;
		grid.getChildren().clear();
		suggestions.clear();
	    suggestionListView.getItems().clear();
	    primaryStage.close();;
	    start(primaryStage);
	}
	
	private static final Random random = new Random();

    public static Character getRandomCharacter(List<Character> characters) {
        if (characters == null || characters.isEmpty()) {
            return null;
        }
        int index = random.nextInt(characters.size());
        return characters.get(index);
    }
	
	public static void main(String[] args) {
		launch(args);
	}
}
