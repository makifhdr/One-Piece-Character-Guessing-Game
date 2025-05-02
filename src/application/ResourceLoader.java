package application;

import javafx.scene.image.Image;
import javafx.scene.text.Font;

public class ResourceLoader {
	
	private Image upArrow;
    private Image downArrow;
    private Image backgroundImage;
    private Image menuBackgroundImage;
    private Image winBackgroundImage;
    private Image giveUpBackgroundImage;
    private Font bangersFont;
    private Font brookeShappelFont;
    
    public ResourceLoader() {
    	loadResources();
    }
    
    private void loadResources() {
        upArrow = new Image(getClass().getResource("/resources/up-arrow.png").toExternalForm());
        downArrow = new Image(getClass().getResource("/resources/down-arrow.png").toExternalForm());
        backgroundImage = new Image(getClass().getResource("/resources/one-piece-background2.jpg").toExternalForm());
        menuBackgroundImage = new Image(getClass().getResource("/resources/one-piece-menu-background.jpg").toExternalForm());
        winBackgroundImage = new Image(getClass().getResource("/resources/one-piece-win-background.jpg").toExternalForm());
        giveUpBackgroundImage = new Image(getClass().getResource("/resources/one-piece-give-up-background.jpg").toExternalForm());
        bangersFont = Font.loadFont(getClass().getResourceAsStream("/resources/Bangers-Regular.ttf"), 20);
        brookeShappelFont = Font.loadFont(getClass().getResourceAsStream("/resources/Brookeshappell8-eoKB.ttf"), 15);
    }

	public Image getUpArrow() {
		return upArrow;
	}

	public Image getDownArrow() {
		return downArrow;
	}

	public Image getBackgroundImage() {
		return backgroundImage;
	}

	public Image getMenuBackgroundImage() {
		return menuBackgroundImage;
	}
	
	public Image getWinBackgroundImage() {
		return winBackgroundImage;
	}
	
	public Image getgiveUpBackgroundImage() {
		return giveUpBackgroundImage;
	}

	public Font getBangersFont() {
		return bangersFont;
	}
	
	public Font getbrookeShappelFont() {
		return brookeShappelFont;
	}
	
	public String getStyleSheet(String fileName) {
        var url = ResourceLoader.class.getResource(fileName);
        if (url == null) {
            throw new IllegalArgumentException("CSS file not found: " + fileName);
        }
        return url.toExternalForm();
    }
}
