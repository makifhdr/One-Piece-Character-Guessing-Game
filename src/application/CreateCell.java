package application;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class CreateCell {
	
	DropShadow dropShadow = new DropShadow();{
		dropShadow.setOffsetX(2);
	    dropShadow.setOffsetY(2);
	    dropShadow.setColor(Color.BLACK);
	}
	
	ResourceLoader resourceLoader = new ResourceLoader();
	Image upArrow = resourceLoader.getUpArrow();
	Image downArrow = resourceLoader.getDownArrow();
	Font bangersFont = resourceLoader.getBangersFont();
	
	List<String> siralanmisArcList;
	
	public CreateCell(List<String> siralanmisArcList) {
		this.siralanmisArcList = siralanmisArcList;
	}
	
	public String textFormatter(String text) {
        
		text = text.replace("_", " ");
		text = text.replace("[", "");
		text = text.replace("]", "");
		text = text.replaceAll(",", "");
        String[] words = text.split(" ");
        
        if(text.length() > 10 || words.length > 2) {

        int mid = (int) Math.ceil(words.length / 2.0);

        String firstLine = String.join(" ", Arrays.copyOfRange(words, 0, mid));
        String secondLine = String.join(" ", Arrays.copyOfRange(words, mid, words.length));
        
        if(firstLine.length() > 15 && firstLine.contains(" ")) {
        	firstLine = textFormatter(firstLine);
        }
        
        return firstLine + "\n" + secondLine;
        }
        else {
        	return text;
        }
	}
	
	public StackPane createPropertyCell(String guess, String correct) {
        StackPane cell = new StackPane();
        Rectangle background = new Rectangle(120, 75);
        
        if (guess.equals(correct)) {
            background.setFill(Color.LIGHTGREEN);
        }else {
            background.setFill(Color.INDIANRED);
        }
        
        guess = textFormatter(guess);

        background.setArcWidth(30);
        background.setArcHeight(30);
        Label text = new Label(guess);
        
        text.setEffect(dropShadow);
        text.setFont(bangersFont);
        text.setTextAlignment(TextAlignment.CENTER);
        cell.getChildren().addAll(background, text);
        return cell;
    }
	
	public StackPane createPropertyCellStatus(String guess, String correct) {
        StackPane cell = new StackPane();
        Rectangle background = new Rectangle(120, 75);
                
	    if (guess.equals(correct)) {
	        background.setFill(Color.LIGHTGREEN);
	    } else if (guess.equals(Status.UNKNOWN.toString())) {
	        background.setFill(Color.GOLD);
	    } else {
	        background.setFill(Color.INDIANRED);
	    }
	    
	    guess = textFormatter(guess);

        background.setArcWidth(30);
        background.setArcHeight(30);
        Label text = new Label(guess);
        
        text.setEffect(dropShadow);
        text.setFont(bangersFont);
        text.setTextAlignment(TextAlignment.CENTER);
        cell.getChildren().addAll(background, text);
        return cell;
    }
	
	public StackPane createPropertyCellHaki(Set<HakiType> guess, Set<HakiType> correct) {
		StackPane cell = new StackPane();
        Rectangle background = new Rectangle(120, 75);
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
	
	public StackPane createPropertyCellFirstArc(String guessArc, String correctArc) {
        StackPane cell = new StackPane();
        Rectangle background = new Rectangle(120, 75);
                
        int guess = 0;
        int correct = 0;
        
        guess = siralanmisArcList.indexOf(guessArc);
        correct = siralanmisArcList.indexOf(correctArc);
        
    	ImageView upArrowImageView = new ImageView();
    	upArrowImageView.setFitWidth(60);
    	upArrowImageView.setFitHeight(60);
    	upArrowImageView.setOpacity(0.3);
    	
    	ImageView downArrowImageView = new ImageView();
    	downArrowImageView.setFitWidth(60);
    	downArrowImageView.setFitHeight(60);
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
            
        guessArc = textFormatter(guessArc);
        
        background.setArcWidth(30);
        background.setArcHeight(30);
        Label text = new Label(guessArc);
        
        text.setEffect(dropShadow);    
        text.setFont(bangersFont);
        text.setTextAlignment(TextAlignment.CENTER);
        cell.getChildren().addAll(background, upArrowImageView, downArrowImageView, text);
        return cell;
    }
	
	public StackPane createPropertyCellHeight(long guess, long correct) {
        StackPane cell = new StackPane();
        Rectangle background = new Rectangle(120, 75);
           
        
        ImageView upArrowImageView = new ImageView();
    	upArrowImageView.setFitWidth(60);
    	upArrowImageView.setFitHeight(60);
    	upArrowImageView.setOpacity(0.3);
    	
    	ImageView downArrowImageView = new ImageView();
    	downArrowImageView.setFitWidth(60);
    	downArrowImageView.setFitHeight(60);
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
	
	public StackPane createPropertyCellBounty(long guess, long correct) {
        StackPane cell = new StackPane();
        Rectangle background = new Rectangle(120, 75);
        
        ImageView upArrowImageView = new ImageView();
    	upArrowImageView.setFitWidth(60);
    	upArrowImageView.setFitHeight(60);
    	upArrowImageView.setOpacity(0.3);
    	
    	ImageView downArrowImageView = new ImageView();
    	downArrowImageView.setFitWidth(60);
    	downArrowImageView.setFitHeight(60);
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
}
