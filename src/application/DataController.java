package application;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class DataController {
	public List<Character> loadCharacters(){
		return CharacterLoader.loadCharactersFromCSV("/data/characters.csv");
	}
	
	public Map<String, Character> getCharacterMap(List<Character> list){
		Map<String, Character> characterMap = new HashMap<>();
		
		for (Character character : list) {
		    characterMap.put(character.getName(), character);
		}
		
		return characterMap;
	}
	
	public List<String> getSiraliArcList(){
		return Arrays.asList(
	            "Romance Dawn", "Orange Town", "Syrup Village", 
	            "Baratie", "Arlong Park", "Loguetown", "Reverse Mountain",
	            "Whisky Peak","Little Garden","Drum Island","Arabasta","Jaya",
	            "Skypiea","Long Ring Long Land","Water 7","Enies Lobby",
	            "Post-Enies Lobby","Thriller Bark","Sabaody Archipelago",
	            "Amazon Lily","Impel Down","Marineford","Post-War","Return to Sabaody",
	            "Fish-Man Island","Punk Hazard","Dressrosa","Zou","Whole Cake Island",
	            "Levely","Wano Country","Egghead"
	        );
	}
	
	private static Random random = new Random();

    public Character getRandomCharacter(List<Character> characters) {
        if (characters == null || characters.isEmpty()) {
            return null;
        }
        int index = random.nextInt(characters.size());
        return characters.get(index);
    }
}
