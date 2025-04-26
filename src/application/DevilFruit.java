package application;

public class DevilFruit {
	private DevilFruitType type;
	private String name;
	private String translatedName;
	
	public DevilFruit() {
		this.type = null;
		this.name = "None";
		this.translatedName = "None";
	}
	
	public DevilFruit(String name, String translatedName, DevilFruitType type) {
		this.type = type;
		this.name = name;
		this.translatedName = translatedName;
	}
	
	public DevilFruitType getType() {
		return this.type;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getTranslatedName() {
		return this.translatedName;
	}
}
