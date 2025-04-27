package application;
	
import java.text.NumberFormat;
import java.util.ArrayList;
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
import javafx.scene.text.FontWeight;


public class Main extends Application {
	
	private GridPane grid;
	List<Character> list = CharacterLoader.loadCharactersFromCSV("/data/characters.csv");;
	List<String> siralanmisArcList = new ArrayList<>();
	private ObservableList<String> suggestions = FXCollections.observableArrayList();
    private ListView<String> suggestionListView = new ListView<>();
	private Character targetCharacter; // Hedef karakter
	Image upArrow = new Image(getClass().getResource("/resources/up-arrow.png").toExternalForm());
	Image downArrow = new Image(getClass().getResource("/resources/down-arrow.png").toExternalForm());
	Image backgroundImage = new Image(getClass().getResource("/resources/one-piece-background2.jpg").toExternalForm());
	
	@Override
	public void start(Stage primaryStage) {
		
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
        inputField.setPromptText("Enter character name...");
        
        // Suggestion list setup
        suggestionListView.setItems(FXCollections.observableArrayList());
        suggestionListView.setMaxHeight(200);
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
                handleGuess(guessedCharacter,targetCharacter, primaryStage);                
                suggestions.remove(userInput);
                List<String> filtered = suggestions.stream()
                        .filter(name -> name.toLowerCase().contains(inputField.getText().toLowerCase()))
                        .collect(Collectors.toList());
                suggestionListView.setItems(FXCollections.observableArrayList(filtered));                
            }
        });
        
        VBox vbox = new VBox(5, inputField, suggestionListView);
        vbox.setPrefHeight(200);
        vbox.setMinHeight(Region.USE_PREF_SIZE);
        vbox.setStyle("-fx-padding: 20;");

        grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        
        String[] headers = {
        	    "Character", "Gender", "Affiliation", "Devil Fruit Type",
        	    "Haki", "Bounty", "Height", "Origin", "First Arc"
        	};

        	for (int i = 0; i < headers.length; i++) {
        	    Label label = new Label(headers[i]);
        	    label.setStyle("-fx-font-size: 20px; -fx-text-fill: white; -fx-effect: dropshadow(one-pass-box, black, 10, 0.0, 0, 0); -fx-font-weight: bold;");
        	    grid.add(label, i, 0);
        	}
        
        grid.setAlignment(Pos.TOP_CENTER);
        
     // Create BackgroundImage
        BackgroundImage background1 = new BackgroundImage(
            backgroundImage,
            BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.CENTER,
            new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)
        );
        
        ScrollPane scrollPane = new ScrollPane(grid);
        scrollPane.setFitToWidth(true);  // Optional: make it stretch horizontally
        VBox.setVgrow(scrollPane, Priority.ALWAYS);
        
     // 3. Make ScrollPane and GridPane transparent
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        grid.setStyle("-fx-background-color: transparent;");
        
        HBox hbox = new HBox(9, inputField, vbox);
        hbox.setAlignment(Pos.TOP_CENTER);
        
        root.setBackground(new Background(background1));
        root.getChildren().addAll(hbox, grid, scrollPane);
        
		Scene scene = new Scene(root,1500,1000);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	private void handleGuess(Character guessed, Character correct, Stage primaryStage) {
		
        int newRow = grid.getRowCount();
        
        String characterName = guessed.getName();
        
        while(characterName.contains(String.valueOf(' '))) {
        	characterName = characterName.substring(0, characterName.indexOf(' ')) + "\n" + characterName.substring(characterName.indexOf(' ') + 1);
        }

        grid.add(new Label(characterName), 0, newRow);
        grid.add(createPropertyCell(guessed.getGender().toString(), correct.getGender().toString()), 1, newRow);
        grid.add(createPropertyCell(guessed.getAffiliation(), correct.getAffiliation()), 2, newRow);
        grid.add(createPropertyCellDevilFruit(guessed.getDevilFruit().getType().toString(), correct.getDevilFruit().getType().toString()), 3, newRow);
        grid.add(createPropertyCellHaki(guessed.getHakiTypes(), correct.getHakiTypes()), 4, newRow);
        grid.add(createPropertyCellBounty(guessed.getBounty(), correct.getBounty()), 5, newRow);
        grid.add(createPropertyCellHeight(guessed.getHeight(), correct.getHeight()), 6, newRow);
        grid.add(createPropertyCell(guessed.getOrigin().toString(), correct.getOrigin().toString()), 7, newRow);
        grid.add(createPropertyCellFirstArc(guessed.getFirstArc(), correct.getFirstArc()), 8, newRow);        		
        
        if(guessed.equals(correct))
        	showWinPage(primaryStage);
    }
	
	private void showWinPage(Stage primaryStage) {
		Stage winStage = new Stage();
		
	    // Title
	    Label winLabel = new Label("🎉 YOU WIN! 🎉");
	    winLabel.setStyle("-fx-font-size: 40px; -fx-text-fill: gold; -fx-font-weight: bold;");

	    // Buttons
	    Button restartButton = new Button("🔁 Restart");
	    Button exitButton = new Button("❌ Exit");

	    restartButton.setStyle("-fx-font-size: 20px; -fx-background-color: lightgreen;");
	    exitButton.setStyle("-fx-font-size: 20px; -fx-background-color: lightcoral;");

	    // Button Actions
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
	    winStage.show();
	}

	private void restartGame(Stage primaryStage) {
		grid.getChildren().clear();
		siralanmisArcList.clear();
		suggestions.clear();
	    suggestionListView.getItems().clear();
	    start(primaryStage);
	}
	
	private StackPane createPropertyCellHaki(Set<HakiType> guess, Set<HakiType> correct) {
		StackPane cell = new StackPane();
        Rectangle background = new Rectangle(140, 100);
        Set<HakiType> copy = new HashSet<>(guess);
        copy.retainAll(correct);
        
	        if (guess.equals(correct)) {
	            background.setFill(Color.LIGHTGREEN);
	        }else if(!copy.isEmpty()) {
	        	background.setFill(Color.YELLOW);
	        }else {
	            background.setFill(Color.INDIANRED);
	        }
	        
	    String guessString = guess.toString();
        
        while(guessString.contains(String.valueOf(' '))) {
        	guessString = guessString.substring(0, guessString.indexOf(' ')) + "\n" + guessString.substring(guessString.indexOf(' ') + 1);
        }

        Label text = new Label(guessString);
        text.setFont(Font.font("Georgia", FontWeight.BOLD, 16));
        cell.getChildren().addAll(background, text);
        return cell;
    }
	
	private StackPane createPropertyCellDevilFruit(String guess, String correct) {
		StackPane cell = new StackPane();
        Rectangle background = new Rectangle(140, 100);
        
	        if (guess.equals(correct)) {
	            background.setFill(Color.LIGHTGREEN);
	        } else {
	            background.setFill(Color.INDIANRED);
	        }
        
        while(guess.contains(String.valueOf(' '))) {
        	guess = guess.substring(0, guess.indexOf(' ')) + "\n" + guess.substring(guess.indexOf(' ') + 1);
        }

        Label text = new Label(guess);
        text.setFont(Font.font("Georgia", FontWeight.BOLD, 16));
        cell.getChildren().addAll(background, text);
        return cell;
    }
	
	private StackPane createPropertyCellFirstArc(String guessArc, String correctArc) {
        StackPane cell = new StackPane();
        Rectangle background = new Rectangle(140, 100);
        
        
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
        
        while(guessArc.contains(String.valueOf(' '))) {
        	guessArc = guessArc.substring(0, guessArc.indexOf(' ')) + "\n" + guessArc.substring(guessArc.indexOf(' ') + 1);
        }
        Label text = new Label(guessArc);       
        text.setFont(Font.font("Georgia", FontWeight.BOLD, 16));
        cell.getChildren().addAll(background, upArrowImageView, downArrowImageView, text);
        return cell;
    }
	
	private StackPane createPropertyCellHeight(long guess, long correct) {
        StackPane cell = new StackPane();
        Rectangle background = new Rectangle(140, 100);
           
        
        ImageView upArrowImageView = new ImageView();
    	upArrowImageView.setFitWidth(80);
    	upArrowImageView.setFitHeight(80);
    	upArrowImageView.setOpacity(0.3); // Make the arrow semi-transparent
    	
    	ImageView downArrowImageView = new ImageView();
    	downArrowImageView.setFitWidth(80);
    	downArrowImageView.setFitHeight(80);
    	downArrowImageView.setOpacity(0.3); // Make the arrow semi-transparent
        
        if (guess == correct) {
            background.setFill(Color.LIGHTGREEN);
        } else if (guess > correct) {
        	background.setFill(Color.INDIANRED); 
            downArrowImageView.setImage(downArrow);          
        } else {
            background.setFill(Color.INDIANRED); 
            upArrowImageView.setImage(upArrow);
        }

        
        String guessString = guess/100 + " meters " + "\n" + guess%100 + " cm";
        Label text = new Label(guessString);
        text.setFont(Font.font("Georgia", FontWeight.BOLD, 16));
        cell.getChildren().addAll(background, upArrowImageView, downArrowImageView, text);
        return cell;
    }
	
	private StackPane createPropertyCellBounty(long guess, long correct) {
        StackPane cell = new StackPane();
        Rectangle background = new Rectangle(140, 100);
        
        ImageView upArrowImageView = new ImageView();
    	upArrowImageView.setFitWidth(80);
    	upArrowImageView.setFitHeight(80);
    	upArrowImageView.setOpacity(0.3); // Make the arrow semi-transparent
    	
    	ImageView downArrowImageView = new ImageView();
    	downArrowImageView.setFitWidth(80);
    	downArrowImageView.setFitHeight(80);
    	downArrowImageView.setOpacity(0.3); // Make the arrow semi-transparent

        if (guess == correct) {
            background.setFill(Color.LIGHTGREEN);
        } else if (guess > correct) {
            background.setFill(Color.INDIANRED); 
            downArrowImageView.setImage(downArrow);              
        } else {
            background.setFill(Color.INDIANRED); 
            upArrowImageView.setImage(upArrow);
        }

        
        NumberFormat formatter = NumberFormat.getInstance(Locale.of("tr", "TR"));
		String bountyString = formatter.format(guess);
        Label text = new Label(bountyString);
        text.setFont(Font.font("Georgia", FontWeight.BOLD, 16));
        cell.getChildren().addAll(background, upArrowImageView, downArrowImageView, text);
        return cell;
    }
	
	private StackPane createPropertyCell(String guess, String correct) {
        StackPane cell = new StackPane();
        Rectangle background = new Rectangle(140, 100);
        
        if(guess.equals("MALE") || guess.equals("FEMALE")) {
        	if (guess.equals(correct)) {
                background.setFill(Color.LIGHTGREEN); // Doğruysa yeşil
            }else {
                background.setFill(Color.INDIANRED); // Yanlışsa kırmızı
            }
        }
        else {
	        if (guess.equals(correct)) {
	            background.setFill(Color.LIGHTGREEN); // Doğruysa yeşil
	        } else if (guess.contains(correct) || correct.contains(guess)) {
	            background.setFill(Color.LIGHTYELLOW); // Kısmen doğruysa sarı
	        } else {
	            background.setFill(Color.INDIANRED); // Yanlışsa kırmızı
	        }
        }
        
        while(guess.contains(String.valueOf(' '))) {
        	guess = guess.substring(0, guess.indexOf(' ')) + "\n" + guess.substring(guess.indexOf(' ') + 1);
        }

        Label text = new Label(guess);
        text.setFont(Font.font("Georgia", FontWeight.BOLD, 16));
        cell.getChildren().addAll(background, text);
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
