package application;

import java.io.*;
import java.util.*;

public class CharacterLoader {

    public static List<Character> loadCharactersFromCSV(String filePath) {
        List<Character> characters = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            reader.readLine(); // Skip header

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", -1); // Handle empty values
                
                if(parts.length == 1) continue;
                
                String name = parts[0];
                Gender gender = Gender.valueOf(parts[1].toUpperCase());
                String affiliation = parts[2];

                String fruitName = parts[3];
                String fruitNameTranslated = parts[4];
                String fruitTypeStr = parts[5];
                DevilFruit devilFruit = DevilFruitType.valueOf(fruitTypeStr.toUpperCase()) == DevilFruitType.NONE ?
                		new DevilFruit("No devil fruit","No devil fruit", DevilFruitType.NONE) :
                        new DevilFruit(fruitName,fruitNameTranslated, DevilFruitType.valueOf(fruitTypeStr));

                Set<HakiType> hakiTypes = parseHakiTypes(parts[6]);

                long bounty = Long.parseLong(parts[7]);
                int height = Integer.parseInt(parts[8]);
                Origin origin = Origin.valueOf(parts[9]);
                String firstArc = parts[10];
                int firstAnimeEpisode = Integer.parseInt(parts[11]);
                int firstMangaChapter = Integer.parseInt(parts[12]);
                Status status = Status.valueOf(parts[13]);

                Character character = new Character(name, gender, affiliation, devilFruit,
                        hakiTypes, bounty, height, origin, firstArc, firstAnimeEpisode, firstMangaChapter, status);

                characters.add(character);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return characters;
    }

    private static Set<HakiType> parseHakiTypes(String hakiString) {
        Set<HakiType> set = EnumSet.noneOf(HakiType.class);

        if (!hakiString.isEmpty()) {
            String[] tokens = hakiString.split("\\|");
            for (String token : tokens) {
                set.add(HakiType.valueOf(token.trim().toUpperCase()));
            }
        }

        return set;
    }
}
