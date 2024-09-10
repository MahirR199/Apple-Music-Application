/*
 * Mahir Tanzil Rahman
 * 501179031
 */
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.util.Scanner;
import java.util.TreeMap;

import Song.Genre;

import java.util.NoSuchElementException;
// Simulation of audio content in an online store
// The songs, podcasts, audiobooks listed here can be "downloaded" to your library

public class AudioContentStore
{
		private ArrayList<AudioContent> contents; 
		private TreeMap<String, Integer> titleMap;
		private TreeMap<String, ArrayList<Integer>> artistMap;
		private TreeMap<Song.Genre, ArrayList<Integer>> genreMap;
		public AudioContentStore()
		{
			String audioType = "";
			String id = "";
			String title = "";
			int year = 0;
			int length = 0;
			String artist = "";
			String composer = "";
			Integer numOfLyrics = 0;
			String lyrics = "";
			String author = "";
			String narrator = "";
			String content = "";
			Integer numOfChapters = 0;
			ArrayList<String> chapterTitles = new ArrayList<>();
			this.artistMap = new TreeMap<String, ArrayList<Integer>>();
			this.titleMap = new TreeMap<String, Integer>();
			this.genreMap = new TreeMap<Song.Genre, ArrayList<Integer>>();
			ArrayList<String> chapters = new ArrayList<>();
			Song song = new Song(title, year, id, audioType, "SONG", length, artist, composer, null, lyrics);
			AudioBook audioBook = new AudioBook(title, 0, id, audioType, lyrics, length, author, narrator, chapterTitles, chapters);
			contents = new ArrayList<AudioContent>();
			String filename = "store.txt";
			try
			{
				Scanner in = new Scanner(new File(filename));
				while(in.hasNextLine()){
					audioType = in.nextLine();
					
					if(audioType.equals("SONG"))
					{
						id = in.nextLine();
						title = in.nextLine();
						year = Integer.parseInt(in.nextLine());
						length = Integer.parseInt(in.nextLine());
						artist = in.nextLine();
						composer = in.nextLine();
						Song.Genre genre = Song.Genre.valueOf(in.nextLine());
						numOfLyrics = Integer.parseInt(in.nextLine());
						for(int i = 0; i <numOfLyrics; i++)
						{
							lyrics += in.nextLine();
						}
						song = new Song(title, year, id, audioType, filename, length, artist, composer, genre, lyrics);
						contents.add(song);
						System.out.println("Loading SONG");
						
					}
					else if(audioType.equals("AUDIOBOOK"))
					{
						id = in.nextLine();
						title = in.nextLine();
						year = Integer.parseInt(in.nextLine());
						length = Integer.parseInt(in.nextLine());
						author = in.nextLine();
						narrator = in.nextLine();
						numOfChapters = Integer.parseInt(in.nextLine());
						for(int i = 0; i <numOfChapters; i++)
						{
							chapterTitles.add(in.nextLine());
						}
						for(int i = 0; i <numOfChapters; i++){
							numOfLyrics = Integer.parseInt(in.nextLine());
							for(int j = 0; j <numOfLyrics; j++)
							{
								content+=in.nextLine();
							}
							chapters.add(content);
							content = "";
						}
						audioBook = new AudioBook(title, year, id, audioType, "", length, author, narrator, chapterTitles, chapters);
						contents.add(audioBook);
						System.out.println("Loading AUDIOBOOK");
						
					}
				}
			}
			catch(FileNotFoundException exception)
			{
				System.out.println("File not found: " + filename);
			}
			
			for(int i = 0; i <contents.size(); i++)
			{
				//fill title map with title and index
				titleMap.put(contents.get(i).getTitle(), i);
				//fill artist map check if song then use .getArtitst() else use .getAuthor()
				if(contents.get(i).getType().equals("SONG")){
					//if the key already exists add to the arraylist of indices
					if(artistMap.containsKey(((Song)contents.get(i)).getArtist()))
					{
						artistMap.get(((Song)contents.get(i)).getArtist()).add(i);
					}
					//else create the key and make arraylist including the index of the first occurrence of the author/artist
					else{
						artistMap.put(((Song)contents.get(i)).getArtist(), new ArrayList<Integer>());
						artistMap.get(((Song)contents.get(i)).getArtist()).add(i);
					}
					//Genre if else blocks
					//if genremap does not contain the genre yet add it to the map and map the index to the genre
					if(genreMap.containsKey(((Song)contents.get(i)).getGenre()))
					{
						genreMap.get(((Song)contents.get(i)).getGenre()).add(i);
					}
					//else create the key and make arraylist including the index of the first occurrence of the genre
					else{
						genreMap.put(((Song)contents.get(i)).getGenre(), new ArrayList<Integer>());
						genreMap.get(((Song)contents.get(i)).getGenre()).add(i);
					}
				}
				else{
					if(artistMap.containsKey(((AudioBook)contents.get(i)).getAuthor()))
					{
						artistMap.get(((AudioBook)contents.get(i)).getAuthor()).add(i);
					}
					else{
						artistMap.put(((AudioBook)contents.get(i)).getAuthor(), new ArrayList<Integer>());
						artistMap.get(((AudioBook)contents.get(i)).getAuthor()).add(i);
					}
				}

			}
		}
		//return titleMap
		public TreeMap<String, Integer> getTitleMap()
		{
			return titleMap;
		}
		//return ArtistMap
		public TreeMap<String, ArrayList<Integer>> getArtistMap()
		{
			return artistMap;
		}
		//return genremap
		public TreeMap<Song.Genre, ArrayList<Integer>> getGenreMap()
		{
			return genreMap;
		}
		public AudioContent getContent(int index)
		{
			if (index < 1 || index > contents.size())
			{
				return null;
			}
			return contents.get(index-1);
		}
		
		public void listAll()
		{
			for (int i = 0; i < contents.size(); i++)
			{
				int index = i + 1;
				System.out.print("" + index + ". ");
				contents.get(i).printInfo();
				System.out.println();
			}
		}
		
		
		
		// Podcast Seasons
		/* 
		private ArrayList<Season> makeSeasons()
		{
			ArrayList<Season> seasons = new ArrayList<Season>();
		  Season s1 = new Season();
		  s1.episodeTitles.add("Bay Blanket");
		  s1.episodeTitles.add("You Don't Want to Sleep Here");
		  s1.episodeTitles.add("The Gold Rush");
		  s1.episodeFiles.add("The Bay Blanket. These warm blankets are as iconic as Mariah Carey's \r\n"
		  		+ "lip-syncing, but some people believe they were used to spread\r\n"
		  		+ " smallpox and decimate entire Indigenous communities. \r\n"
		  		+ "We dive into the history of The Hudson's Bay Company and unpack the\r\n"
		  		+ " very complicated story of the iconic striped blanket.");
		  s1.episodeFiles.add("There is no doubt that the Klondike Gold Rush was an iconic event. \r\n"
		  		+ "But what did the mining industry cost the original people of the territory? \r\n"
		  		+ "And what was left when all the gold was gone? And what is a sour toe cocktail?");
		  s1.episodeFiles.add("here is no doubt that the Klondike Gold Rush was an iconic event. \r\n"
		  		+ "But what did the mining industry cost the original people of the territory? \r\n"
		  		+ "And what was left when all the gold was gone? And what is a sour toe cocktail?");
		  s1.episodeLengths.add(31);
		  s1.episodeLengths.add(32);
		  s1.episodeLengths.add(45);
		  seasons.add(s1);
		  Season s2 = new Season();
		  s2.episodeTitles.add("Toronto vs Everyone");
		  s2.episodeTitles.add("Water");
		  s2.episodeFiles.add("There is no doubt that the Klondike Gold Rush was an iconic event. \r\n"
		  		+ "But what did the mining industry cost the original people of the territory? \r\n"
		  		+ "And what was left when all the gold was gone? And what is a sour toe cocktail?");
		  s2.episodeFiles.add("Can the foundation of Canada be traced back to Indigenous trade routes?\r\n"
		  		+ " In this episode Falen and Leah take a trip across the Great Lakes, they talk corn\r\n"
		  		+ " and vampires, and discuss some big concerns currently facing Canada's water."); 
		  s2.episodeLengths.add(45);
		  s2.episodeLengths.add(50);
		 
		  seasons.add(s2);
		  return seasons;
		}
		*/
}
