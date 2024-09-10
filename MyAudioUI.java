/*
 * Mahir Tanzil Rahman
 * 501179031
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.TreeMap;
import Song.Genre;
// Simulation of a Simple Text-based Music App (like Apple Music)

public class MyAudioUI
{
	public static void main(String[] args)
	{
		// Simulation of audio content in an online store
		// The songs, podcasts, audiobooks in the store can be downloaded to your mylibrary
		AudioContentStore store = new AudioContentStore();
		
		Song tempSong;
		AudioBook tempBook;
		// Create my music mylibrary
		Library mylibrary = new Library();

		Scanner scanner = new Scanner(System.in);
		System.out.print(">");

		// Process keyboard actions
		while (scanner.hasNextLine())
		{
			String action = scanner.nextLine();

			if (action == null || action.equals("")) 
			{
				System.out.print("\n>");
				continue;
			}
			else if (action.equalsIgnoreCase("Q") || action.equalsIgnoreCase("QUIT"))
				return;
			
			else if (action.equalsIgnoreCase("STORE"))	// List all songs
			{
				store.listAll(); 
			}
			else if (action.equalsIgnoreCase("SONGS"))	// List all songs
			{
				mylibrary.listAllSongs(); 
			}
			else if (action.equalsIgnoreCase("BOOKS"))	// List all songs
			{
				mylibrary.listAllAudioBooks(); 
			}
			else if (action.equalsIgnoreCase("PODCASTS"))	// List all songs
			{
				mylibrary.listAllPodcasts(); 
			}
			else if (action.equalsIgnoreCase("ARTISTS"))	// List all songs
			{
				mylibrary.listAllArtists(); 
			}
			else if (action.equalsIgnoreCase("PLAYLISTS"))	// List all play lists
			{
				mylibrary.listAllPlaylists(); 
			}
			// Download audiocontent (song/audiobook/podcast) from the store 
			// Specify the from index of the content to the last index of all songs
			// show error message for all songs that already have been downloaded 
			else if (action.equalsIgnoreCase("DOWNLOAD")) 
			{
				int index = 0;
				int index2 = 0;
				System.out.print("From Store Content #: ");
				if (scanner.hasNextInt())
				{
					index = scanner.nextInt();
					scanner.nextLine(); // "consume" nl character (necessary when mixing nextLine() and nextInt())
				}
				System.out.print("To Store Content #: ");
				if (scanner.hasNextInt())
				{
					index2 = scanner.nextInt();
					scanner.nextLine(); // "consume" nl character (necessary when mixing nextLine() and nextInt())
				}
				for(int i = index; i <= index2; i++){
					AudioContent content = store.getContent(i);
					if (content == null)
						System.out.println("Content Not Found in Store");
					try 
					{
						mylibrary.download(content);
						System.out.println(content.getType() + " " + content.getTitle()+" Added to Library");
					}
					catch(AudioContentAlreadyDownloaded exception)
					{
						System.out.println(exception.getMessage());
					}
				}				
			}
			//take artist name and download all content of artist
			//use the artist map
			else if (action.equalsIgnoreCase("DOWNLOADA"))
			{

				String artist = "";
				ArrayList<Integer> indices = new ArrayList<Integer>(); 
				System.out.print("Artist name: ");
				artist = scanner.nextLine();
				indices=(store.getArtistMap()).get(artist);
				for(int i =0;i<indices.size();i++)
				{
					AudioContent content = store.getContent(indices.get(i)+1);
					
					if (content == null)
						System.out.println("Content Not Found in Store");
					try 
					{
						mylibrary.download(content);
						//Output is different in video so I commented this out for downloada.
						//if(content.getType().equals("SONG"))
						
						//	System.out.println("SONG " + content.getTitle()+" Added to Library");
						//else
						//{
						//	System.out.println("AUDIOBOOK " + content.getTitle()+" Added to Library");
					//	}
					}
					catch(AudioContentAlreadyDownloaded exception)
					{
						System.out.println(exception.getMessage());
					}
				}
			}
			//take genre as a parameter and download all content of that genre
			//use genre map
			else if (action.equalsIgnoreCase("DOWNLOADG"))
			{
				Song.Genre genreS;
				ArrayList<Integer> indices = new ArrayList<Integer>();
				System.out.print("Genre [POP, ROCK, JAZZ, HIPHOP, RAP, CLASSICAL]: ");
				String genre = scanner.nextLine();
				genreS = Song.Genre.valueOf(genre);
				indices = store.getGenreMap().get(genreS);
				for(int i =0;i<indices.size();i++)
				{
					AudioContent content = store.getContent(indices.get(i)+1);
					
					if (content == null)
						System.out.println("Content Not Found in Store");
					try 
					{
						mylibrary.download(content);
					}
					catch(AudioContentAlreadyDownloaded exception)
					{
						System.out.println(exception.getMessage());
					}
				}

			}
			//search store for audiocontent with specific title, if title found 
			//print the index number of the content and the info(have to use a map)
			else if (action.equalsIgnoreCase("SEARCH"))
			{
				String title = "";
				TreeMap<String, Integer> tmap = new TreeMap<String, Integer>(); 
				System.out.print("Title: ");
				title = scanner.nextLine();
				tmap = store.getTitleMap();
				if(tmap.containsKey(title)){
					AudioContent content = store.getContent(tmap.get(title)+1);
					System.out.print(tmap.get(title)+1 + ". ");
					content.printInfo();
					System.out.println();
				}
				else System.out.println("No matches for " + title);
				
			}
			//searches with artist name
			else if (action.equalsIgnoreCase("SEARCHA"))
			{
				String artist = "";
				ArrayList<Integer> indices = new ArrayList<Integer>();
				TreeMap<String, ArrayList<Integer>> amap = new TreeMap<String, ArrayList<Integer>>(); 
				System.out.print("Artist: ");
				artist = scanner.nextLine();
				amap = store.getArtistMap();
				if(amap.containsKey(artist))
				{
					indices = amap.get(artist);
					for(int i = 0; i<indices.size(); i++)
					{
						AudioContent content = store.getContent(indices.get(i)+1);
						System.out.print(indices.get(i)+1 + ". ");
						content.printInfo();
						System.out.println();
					}
				}
				else System.out.println("No matches for " + artist); 
			}
			//searches according to genre (POP,...)
			else if (action.equalsIgnoreCase("SEARCHG"))
			{
				String genre = "";
				TreeMap<Song.Genre, ArrayList<Integer>> gMap= new TreeMap<Song.Genre, ArrayList<Integer>>();
				ArrayList<Integer> indices = new ArrayList<Integer>();
				System.out.print("Genre [POP, ROCK, JAZZ, HIPHOP, RAP, CLASSICAL]: ");
				genre = scanner.nextLine();
				gMap = store.getGenreMap();
				if(gMap.containsKey(Song.Genre.valueOf(genre)))
				{
					indices = gMap.get(Song.Genre.valueOf(genre));
					for(int i = 0; i<indices.size(); i++)
					{
						AudioContent content = store.getContent(indices.get(i)+1);
						System.out.print(indices.get(i)+1 + ". ");
						content.printInfo();
						System.out.println();
					}
				}
				else System.out.println("No matches for " + genre); 
			}
			// Get the *library* index (index of a song based on the songs list)
			// of a song from the keyboard and play the song 
			else if (action.equalsIgnoreCase("PLAYSONG")) 
			{
				int index = 0;
				// Print error message if the song doesn't exist in the library
				System.out.print("Song Number: ");
				if (scanner.hasNextInt())
				{
					index = scanner.nextInt();
					scanner.nextLine(); 
				}
				try {
					mylibrary.playSong(index);
				} catch (AudioContentNotFound e) {
					System.out.println(e.getMessage());
				} 
			}
			// Print the table of contents (TOC) of an audiobook that
			// has been downloaded to the library. Get the desired book index
			// from the keyboard - the index is based on the list of books in the library
			else if (action.equalsIgnoreCase("BOOKTOC")) 
			{
			// Print error message if the book doesn't exist in the library
				int index = 0;
				System.out.print("Audio Book Number: ");
				if (scanner.hasNextInt())
				{
					index = scanner.nextInt();
					scanner.nextLine(); 
				}
				try {
					mylibrary.printAudioBookTOC(index);
				} catch (AudioContentNotFound e) {
					System.out.println(e.getMessage());
				} 
			}
			// Similar to playsong above except for audio book
			// In addition to the book index, read the chapter 
			// number from the keyboard - see class Library
			else if (action.equalsIgnoreCase("PLAYBOOK")) 
			{
				int index = 0;
				int chapter = 0;
				System.out.print("Audio Book Number: ");
				if (scanner.hasNextInt())
				{
					index = scanner.nextInt();
					scanner.nextLine(); 
				}
				System.out.print("Chapter: ");
				if (scanner.hasNextInt())
				{
					chapter = scanner.nextInt();
					scanner.nextLine(); 
				}
				try {
					mylibrary.playAudioBook(index,chapter);
				} catch (AudioContentNotFound e) {
					System.out.println(e.getMessage());
				} 
			}
			// Print the episode titles for the given season of the given podcast
			// In addition to the podcast index from the list of podcasts, 
			// read the season number from the keyboard
			// see class Library for the method to call
			else if (action.equalsIgnoreCase("PODTOC")) 
			{
				
			}
			// Similar to playsong above except for podcast
			// In addition to the podcast index from the list of podcasts, 
			// read the season number and the episode number from the keyboard
			// see class Library for the method to call
			else if (action.equalsIgnoreCase("PLAYPOD")) 
			{
				
			}
			// Specify a playlist title (string) 
			// Play all the audio content (songs, audiobooks, podcasts) of the playlist 
			// see class Library for the method to call
			else if (action.equalsIgnoreCase("PLAYALLPL")) 
			{
				String plTitle = "";
				System.out.print("Playlist Title: ");
				if (scanner.hasNextLine())
				{
					plTitle = scanner.nextLine();
				}
				try {
					mylibrary.playPlaylist(plTitle);
				} catch (AudioContentNotFound e) {
					System.out.println(e.getMessage());
				} 
			}
			// Specify a playlist title (string) 
			// Read the index of a song/audiobook/podcast in the playist from the keyboard 
			// Play all the audio content 
			// see class Library for the method to call
			else if (action.equalsIgnoreCase("PLAYPL")) 
			{
				String plTitle = "";
				int index= 0;
				System.out.print("Playlist Title: ");
				if (scanner.hasNextLine())
				{
					plTitle = scanner.nextLine();
				}
				System.out.print("Content Number: ");
				if (scanner.hasNextInt())
				{
					index = scanner.nextInt();
					scanner.nextLine();
				}
				try {
					mylibrary.playPlaylist(plTitle,index);
				} catch (AudioContentNotFound e) {
					System.out.println(e.getMessage());
				} 
			}
			// Delete a song from the list of songs in mylibrary and any play lists it belongs to
			// Read a song index from the keyboard
			// see class Library for the method to call
			else if (action.equalsIgnoreCase("DELSONG")) 
			{
				System.out.print("Library Song #: ");
				int index = 0;
				if(scanner.hasNextInt())
				{
					index = scanner.nextInt();
					scanner.nextLine();
				}
				mylibrary.deleteSong(index);
				
			}
			// Read a title string from the keyboard and make a playlist
			// see class Library for the method to call
			else if (action.equalsIgnoreCase("MAKEPL")) 
			{

				System.out.print("Playlist title: ");
				String npltitle = "";
				if(scanner.hasNextLine())
				{
					npltitle = scanner.nextLine();
				}
				try {
					mylibrary.makePlaylist(npltitle);
				} catch (AudioContentAlreadyExists e) {
					System.out.println(e.getMessage());
				} 


			}
			// Print the content information (songs, audiobooks, podcasts) in the playlist
			// Read a playlist title string from the keyboard
		  // see class Library for the method to call
			else if (action.equalsIgnoreCase("PRINTPL"))	// print playlist content
			{
				String title = "";
				System.out.print("Playlist title: ");
				if(scanner.hasNextLine())
				{
					title = scanner.nextLine();
				}
				try {
					mylibrary.printPlaylist(title);
				} catch (AudioContentNotFound e) {
					System.out.println(e.getMessage());
				} 
			}
			// Add content (song, audiobook, podcast) from mylibrary (via index) to a playlist
			// Read the playlist title, the type of content ("song" "audiobook" "podcast")
			// and the index of the content (based on song list, audiobook list etc) from the keyboard
		  // see class Library for the method to call
			else if (action.equalsIgnoreCase("ADDTOPL")) 
			{
				String title = "";
				String type = "";
				int index = 0;
				System.out.print("Playlist title: ");
				if(scanner.hasNextLine())
				{
					title = scanner.nextLine();
				}
				System.out.print("Content Type [SONG, PODCAST, AUDIOBOOK]: ");
				if(scanner.hasNextLine())
				{
					type = scanner.nextLine().toUpperCase();
				}
				System.out.print("Library Content #: ");
				if(scanner.hasNextInt())
				{
					index = scanner.nextInt();
					scanner.nextLine();
				}
				try {
					mylibrary.addContentToPlaylist(type,index,title);
				} catch (AudioContentNotFound e) {
					System.out.println(e.getMessage());
				} 
			}
			// Delete content from play list based on index from the playlist
			// Read the playlist title string and the playlist index
		  // see class Library for the method to call
			else if (action.equalsIgnoreCase("DELFROMPL")) 
			{
				String title = "";
				int index = 0;
				System.out.print("Playlist Title: ");
				if(scanner.hasNextLine())
				{
					title = scanner.nextLine();
				}
				System.out.print("Playlist Content #: ");
				if(scanner.hasNextInt())
				{
					index = scanner.nextInt();
					scanner.nextLine();
				}
				try {
					mylibrary.delContentFromPlaylist(index, title);
				} catch (AudioContentNotFound e) {
					System.out.println(e.getMessage());
				} 
			}
			
			else if (action.equalsIgnoreCase("SORTBYYEAR")) // sort songs by year
			{
				mylibrary.sortSongsByYear();
			}
			else if (action.equalsIgnoreCase("SORTBYNAME")) // sort songs by name (alphabetic)
			{
				mylibrary.sortSongsByName();
			}
			else if (action.equalsIgnoreCase("SORTBYLENGTH")) // sort songs by length
			{
				mylibrary.sortSongsByLength();
			}

			System.out.print("\n>");
		}
	}
}
