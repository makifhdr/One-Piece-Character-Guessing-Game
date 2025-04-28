package application;
	
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Set;
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
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
	List<Character> list = CharacterLoader.loadCharactersFromCSV("/data/characters.csv");;
	List<String> siralanmisArcList = new ArrayList<>();
	private ObservableList<String> suggestions = FXCollections.observableArrayList();
    private ListView<String> suggestionListView = new ListView<>();
	private Character targetCharacter;
	Image upArrow = new Image(getClass().getResource("/resources/up-arrow.png").toExternalForm());
	Image downArrow = new Image(getClass().getResource("/resources/down-arrow.png").toExternalForm());
	Image backgroundImage = new Image(getClass().getResource("/resources/one-piece-background2.jpg").toExternalForm());
	Image menuBackGroundImage = new Image(getClass().getResource("/resources/one-piece-menu-background.jpg").toExternalForm());
	Font bangersFont = Font.loadFont(getClass().getResourceAsStream("/resources/Bangers-Regular.ttf"), 25);
	boolean restarted = false;
    
    DropShadow dropShadow = new DropShadow();
	
	@Override
	public void start(Stage primaryStage) {

	    dropShadow.setOffsetX(2);
	    dropShadow.setOffsetY(2);
	    dropShadow.setColor(Color.BLACK);
		
		Map<String, Character> characterMap = new HashMap<>();

		for (Character character : list) {
		    characterMap.put(character.getName(), character);
		}
		
		siralanmisArcList.add("Romance Dawn");siralanmisArcList.add("Orange Town");siralanmisArcList.add("Syrup Village");siralanmisArcList.add("Baratie");
		siralanmisArcList.add("Arlong Park");siralanmisArcList.add("Loguetown");siralanmisArcList.add("Reverse Mountain");siralanmisArcList.add("Whisky Peak");
		siralanmisArcList.add("Little Garden");siralanmisArcList.add("Drum Island");siralanmisArcList.add("Arabasta");siralanmisArcList.add("Jaya");
		siralanmisArcList.add("Skypiea");siralanmisArcList.add("Long Ring Long Land");siralanmisArcList.add("Water 7");siralanmisArcList.add("Enies Lobby");
		siralanmisArcList.add("Post-Enies Lobby");siralanmisArcList.add("Thriller Bark");siralanmisArcList.add("Sabaody Archipelago");siralanmisArcList.add("Amazon Lily");
		siralanmisArcList.add("Impel Down");siralanmisArcList.add("Marineford");siralanmisArcList.add("Post-War");siralanmisArcList.add("Return to Sabaody");
		siralanmisArcList.add("Fish-Man Island");siralanmisArcList.add("Punk Hazard");siralanmisArcList.add("Dressrosa");siralanmisArcList.add("Zou");
		siralanmisArcList.add("Whole Cake Island");siralanmisArcList.add("Levely");siralanmisArcList.add("Wano Country");siralanmisArcList.add("Egghead");
		
        targetCharacter = getRandomCharacter(list);
		
		suggestions.addAll(characterMap.values().stream()
                .map(Character::getName)
                .sorted()
                .collect(Collectors.toList()));
		
		VBox root = new VBox(10);
        root.setPadding(new Insets(10));

        TextField inputField = new TextField();
        inputField.setScaleX(3);
        inputField.setScaleY(3);
        inputField.setMaxWidth(200);
        inputField.setTranslateY(40);
        inputField.setPromptText("Enter character name...");
        
        suggestionListView.setItems(FXCollections.observableArrayList());
        suggestionListView.setScaleX(3);
        suggestionListView.setScaleY(3);
        suggestionListView.setTranslateY(-270);
        suggestionListView.setMaxHeight(200);
        suggestionListView.setMaxWidth(200);
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
        	    Label label = new Label(headers[i]);
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
        
        Button exitButton = new Button("❌ Exit");
        exitButton.setOnAction(e -> primaryStage.close());
        exitButton.setStyle("-fx-font-size: 20px; -fx-background-color: lightcoral;");
        exitButton.setScaleX(2);
        exitButton.setScaleY(2);
        exitButton.setTranslateX(250);
        exitButton.setTranslateY(50);
        
        HBox hbox = new HBox(9, vbox, exitButton);
        hbox.setAlignment(Pos.TOP_CENTER);
        
        VBox.setVgrow(scrollPane, Priority.ALWAYS);
        root.setBackground(new Background(background1));
        //root.getChildren().addAll(hbox, grid, scrollPane);
        root.getChildren().add(scrollPane);
        root.getChildren().add(hbox);
        root.getChildren().add(grid);
                
		Scene scene = new Scene(root,1500,1000);
		primaryStage.setScene(scene);
		primaryStage.setMaximized(true);
		
		if(!restarted) {
			Stage menuStage = new Stage();
			
		    Label menuLabel = new Label("    WELCOME TO ONE PIECE\nCHARACTER GUESSING GAME");
		    menuLabel.setStyle("-fx-font-size: 40px; -fx-text-fill: white; -fx-effect: dropshadow(one-pass-box, black, 10, 0.0, 0, 0); -fx-font-weight: bold;");

		    Button startButton = new Button("▶ Play");
		    Button menuExitButton = new Button("❌ Exit");

		    startButton.setStyle("-fx-font-size: 20px; -fx-background-color: lightgreen;");
		    menuExitButton.setStyle("-fx-font-size: 20px; -fx-background-color: lightcoral;");

		    startButton.setOnAction(e -> {
		    	primaryStage.show();
		    	menuStage.close();});
		    menuExitButton.setOnAction(e -> {
		    	primaryStage.close();
		    	menuStage.close();});
		    
		    BackgroundImage menuBackground = new BackgroundImage(
		    		menuBackGroundImage,
		            BackgroundRepeat.NO_REPEAT,
		            BackgroundRepeat.NO_REPEAT,
		            BackgroundPosition.CENTER,
		            new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)
		        );	    	    

		    VBox layout = new VBox(20);
		    layout.setBackground(new Background(menuBackground));
		    layout.getChildren().addAll(menuLabel, startButton, menuExitButton);
		    layout.setAlignment(Pos.CENTER);
		    //layout.setStyle("-fx-background-color: navy; -fx-padding: 30px;");
		    

		    Scene menuScene = new Scene(layout, 1000, 600);
		    menuStage.setScene(menuScene);
		    menuStage.setResizable(false);
		    menuStage.show();
		}
		else {
			primaryStage.show();
		}
		HBox.setHgrow(hbox, Priority.NEVER);
	}
	
	private void handleGuess(Character guessed, Stage primaryStage) {
		
        int newRow = grid.getRowCount();
        
        String characterName = guessed.getName();
        
        while(characterName.contains(String.valueOf(' '))) {
        	characterName = characterName.substring(0, characterName.indexOf(' ')) + "\n" + characterName.substring(characterName.indexOf(' ') + 1);
        }
        
        Label nameLabel = new Label(characterName);
        nameLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: white; -fx-effect: dropshadow(one-pass-box, black, 10, 0.0, 0, 0); -fx-font-weight: bold;");

        grid.add(nameLabel, 0, newRow);
        grid.add(createPropertyCell(guessed.getGender().toString(), targetCharacter.getGender().toString()), 1, newRow);
        grid.add(createPropertyCell(guessed.getAffiliation(), targetCharacter.getAffiliation()), 2, newRow);
        grid.add(createPropertyCell(guessed.getDevilFruit().getType().toString(), targetCharacter.getDevilFruit().getType().toString()), 3, newRow);
        grid.add(createPropertyCellHaki(guessed.getHakiTypes(), targetCharacter.getHakiTypes()), 4, newRow);
        grid.add(createPropertyCellBounty(guessed.getBounty(), targetCharacter.getBounty()), 5, newRow);
        grid.add(createPropertyCellHeight(guessed.getHeight(), targetCharacter.getHeight()), 6, newRow);
        grid.add(createPropertyCell(guessed.getOrigin().toString(), targetCharacter.getOrigin().toString()), 7, newRow);
        grid.add(createPropertyCellFirstArc(guessed.getFirstArc(), targetCharacter.getFirstArc()), 8, newRow);
        grid.add(createPropertyCellStatus(guessed.getStatus().toString(), targetCharacter.getStatus().toString()), 9, newRow);
        
        if(guessed.equals(targetCharacter)) {
        	showWinPage(primaryStage, targetCharacter);
        }
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
		siralanmisArcList.clear();
		suggestions.clear();
	    suggestionListView.getItems().clear();
	    primaryStage.hide();
	    start(primaryStage);
	}
	
	private String textCleaner(String text) {
        
		text = text.replace("_", " ");
		text = text.replace("[", "");
		text = text.replace("]", "");
		text = text.replaceAll(",", "");
        String[] words = text.split(" ");

        int mid = (int) Math.ceil(words.length / 2.0);

        String firstLine = String.join(" ", Arrays.copyOfRange(words, 0, mid));
        String secondLine = String.join(" ", Arrays.copyOfRange(words, mid, words.length));

        return firstLine + "\n" + secondLine;
	}
	
	private StackPane createPropertyCell(String guess, String correct) {
        StackPane cell = new StackPane();
        Rectangle background = new Rectangle(160, 100);
        
        if (guess.equals(correct)) {
            background.setFill(Color.LIGHTGREEN);
        }else {
            background.setFill(Color.INDIANRED);
        }
        
        guess = textCleaner(guess);

        background.setArcWidth(30);
        background.setArcHeight(30);
        Label text = new Label(guess);
        
        text.setEffect(dropShadow);
        text.setFont(bangersFont);
        text.setTextAlignment(TextAlignment.CENTER);
        cell.getChildren().addAll(background, text);
        return cell;
    }
	
	private StackPane createPropertyCellStatus(String guess, String correct) {
        StackPane cell = new StackPane();
        Rectangle background = new Rectangle(160, 100);
                
	    if (guess.equals(correct)) {
	        background.setFill(Color.LIGHTGREEN);
	    } else if (guess.equals(Status.UNKNOWN.toString())) {
	        background.setFill(Color.GOLD);
	    } else {
	        background.setFill(Color.INDIANRED);
	    }
	    
	    guess = textCleaner(guess);

        background.setArcWidth(30);
        background.setArcHeight(30);
        Label text = new Label(guess);
        
        text.setEffect(dropShadow);
        text.setFont(bangersFont);
        text.setTextAlignment(TextAlignment.CENTER);
        cell.getChildren().addAll(background, text);
        return cell;
    }
	
	private StackPane createPropertyCellHaki(Set<HakiType> guess, Set<HakiType> correct) {
		StackPane cell = new StackPane();
        Rectangle background = new Rectangle(160, 100);
        Set<HakiType> copy = new HashSet<>(guess);
        copy.retainAll(correct);
        
	        if (guess.equals(correct)) {
	            background.setFill(Color.LIGHTGREEN);
	        }else if(!copy.isEmpty()) {
	        	background.setFill(Color.GOLD);
	        }else {
	            background.setFill(Color.INDIANRED);
	        }
	        
	    String guessString = guess.toString();
	    
	    guessString = guessString.replace("[", "");
	    guessString = guessString.replace("]", "");
	    guessString = guessString.replaceAll(",", "");
	    guessString = guessString.replaceAll(" ", "\n");	    
	    
        background.setArcWidth(30);
        background.setArcHeight(30);
        Label text = new Label(guessString);
        
        text.setEffect(dropShadow);
        text.setFont(bangersFont);
        text.setTextAlignment(TextAlignment.CENTER);
        cell.getChildren().addAll(background, text);
        return cell;
    }
	
	private StackPane createPropertyCellFirstArc(String guessArc, String correctArc) {
        StackPane cell = new StackPane();
        Rectangle background = new Rectangle(160, 100);
                
        int guess = 0;
        int correct = 0;
        
        guess = siralanmisArcList.indexOf(guessArc);
        correct = siralanmisArcList.indexOf(correctArc);
        
    	ImageView upArrowImageView = new ImageView();
    	upArrowImageView.setFitWidth(80);
    	upArrowImageView.setFitHeight(80);
    	upArrowImageView.setOpacity(0.3);
    	
    	ImageView downArrowImageView = new ImageView();
    	downArrowImageView.setFitWidth(80);
    	downArrowImageView.setFitHeight(80);
    	downArrowImageView.setOpacity(0.3);


    	if (guess == correct) {
            background.setFill(Color.LIGHTGREEN);
        } else if (guess > correct) {
            background.setFill(Color.INDIANRED);
            downArrowImageView.setImage(downArrow);
        } else {
            background.setFill(Color.INDIANRED);
            upArrowImageView.setImage(upArrow);
        }
            
        guessArc = textCleaner(guessArc);
        
        background.setArcWidth(30);
        background.setArcHeight(30);
        Label text = new Label(guessArc);
        
        text.setEffect(dropShadow);    
        text.setFont(bangersFont);
        text.setTextAlignment(TextAlignment.CENTER);
        cell.getChildren().addAll(background, upArrowImageView, downArrowImageView, text);
        return cell;
    }
	
	private StackPane createPropertyCellHeight(long guess, long correct) {
        StackPane cell = new StackPane();
        Rectangle background = new Rectangle(160, 100);
           
        
        ImageView upArrowImageView = new ImageView();
    	upArrowImageView.setFitWidth(80);
    	upArrowImageView.setFitHeight(80);
    	upArrowImageView.setOpacity(0.3);
    	
    	ImageView downArrowImageView = new ImageView();
    	downArrowImageView.setFitWidth(80);
    	downArrowImageView.setFitHeight(80);
    	downArrowImageView.setOpacity(0.3);
        
        if (guess == correct) {
            background.setFill(Color.LIGHTGREEN);
        } else if (guess > correct) {
        	background.setFill(Color.INDIANRED); 
            downArrowImageView.setImage(downArrow);          
        } else {
            background.setFill(Color.INDIANRED); 
            upArrowImageView.setImage(upArrow);
        }

        background.setArcWidth(30);
        background.setArcHeight(30);
        String guessString = guess/100 + " meters " + "\n" + guess%100 + " cm";
        Label text = new Label(guessString);
        
        text.setEffect(dropShadow);
        text.setFont(bangersFont);
        text.setTextAlignment(TextAlignment.CENTER);
        cell.getChildren().addAll(background, upArrowImageView, downArrowImageView, text);
        return cell;
    }
	
	private StackPane createPropertyCellBounty(long guess, long correct) {
        StackPane cell = new StackPane();
        Rectangle background = new Rectangle(160, 100);
        
        ImageView upArrowImageView = new ImageView();
    	upArrowImageView.setFitWidth(80);
    	upArrowImageView.setFitHeight(80);
    	upArrowImageView.setOpacity(0.3);
    	
    	ImageView downArrowImageView = new ImageView();
    	downArrowImageView.setFitWidth(80);
    	downArrowImageView.setFitHeight(80);
    	downArrowImageView.setOpacity(0.3);

        if (guess == correct) {
            background.setFill(Color.LIGHTGREEN);
        } else if (guess > correct) {
            background.setFill(Color.INDIANRED); 
            downArrowImageView.setImage(downArrow);              
        } else {
            background.setFill(Color.INDIANRED); 
            upArrowImageView.setImage(upArrow);
        }

        background.setArcWidth(30);
        background.setArcHeight(30);
        NumberFormat formatter = NumberFormat.getInstance(Locale.of("tr", "TR"));
		String bountyString = formatter.format(guess);
        Label text = new Label(bountyString);
        
        text.setEffect(dropShadow);
        text.setFont(bangersFont);
        text.setTextAlignment(TextAlignment.CENTER);
        cell.getChildren().addAll(background, upArrowImageView, downArrowImageView, text);
        return cell;
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
