package application;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Set;

public class Character {
	private String name;
	private Gender gender;
	private String affiliation;
	private DevilFruit devilFruit;
	private Set<HakiType> hakiTypes;
	private long bounty;
	private int height;
	private Origin origin;
	private String firstArc;
	private int firstAnimeEpisode;
	private int firstMangaChapter;
	private Status status;
	
	public Character() {
		this.name = null;
		this.gender = null;
		this.affiliation = null;
		this.devilFruit = null;
		this.hakiTypes = null;
		this.bounty = 0;
		this.height	= 0;
		this.origin = null;
		this.firstArc = null;
		this.status = null;
}
	
	public Character(String name, Gender gender, String affiliation, DevilFruit devilFruit, Set<HakiType> hakiTypes, 
					 long bounty, int height, Origin origin, String firstArc, int firstAnimeEpisode, int firstMangaChapter, Status status) {
		this.name = name;
		this.gender = gender;
		this.affiliation = affiliation;
		this.devilFruit = devilFruit;
		this.hakiTypes = hakiTypes;
		this.bounty = bounty;
		this.height	= height;
		this.origin = origin;
		this.firstArc = firstArc;
		this.firstAnimeEpisode = firstAnimeEpisode;
		this.firstMangaChapter = firstMangaChapter;
		this.status = status;
	}

	public String getName() {return this.name;}

	public Gender getGender() {return this.gender;}

	public String getAffiliation() {return this.affiliation;}

	public DevilFruit getDevilFruit() {return this.devilFruit;}

	public Set<HakiType> getHakiTypes() {return this.hakiTypes;}
	
	public long getBounty() {return this.bounty;}

	public String getBountyString() {
		NumberFormat formatter = NumberFormat.getInstance(Locale.of("tr", "TR"));
		return formatter.format(this.bounty);
    }

	public int getHeight() {return this.height;}

	public Origin getOrigin() {return this.origin;}

	public String getFirstArc() {return this.firstArc;}
	
	public int getFirstAnimeEpisode() {return this.firstAnimeEpisode;}
	
	public int getFirstMangaChapter() {return this.firstMangaChapter;}
	
	public Status getStatus() {return this.status;}
	
}
