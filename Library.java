	/*
 * Mahir Tanzil Rahman
 * 501179031
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.io.RuntimeException;
/*
 * This class manages, stores, and plays audio content such as songs, podcasts and audiobooks. 
 */
public class Library
{
	private ArrayList<Song> 			songs; 
	private ArrayList<AudioBook> 	audiobooks;
	private ArrayList<Playlist> 	playlists; 
	
  //private ArrayList<Podcast> 	podcasts;
	
	// Public methods in this class set errorMesg string 
	// Error Messages can be retrieved from main in class MyAudioUI by calling  getErrorMessage()
	// In assignment 2 we will replace this with Java Exceptions
	String errorMsg = "";
	
	public String getErrorMessage()
	{
		return errorMsg;
	}

	public Library()
	{
		songs 			= new ArrayList<Song>(); 
		audiobooks 	= new ArrayList<AudioBook>(); 
		playlists   = new ArrayList<Playlist>();
	  //podcasts		= new ArrayList<Podcast>(); ;
	}
	/*
	 * Download audio content from the store. Since we have decided (design decision) to keep 3 separate lists in our library
	 * to store our songs, podcasts and audiobooks (we could have used one list) then we need to look at the type of
	 * audio content (hint: use the getType() method and compare to Song.TYPENAME or AudioBook.TYPENAME etc)
	 * to determine which list it belongs to above
	 * 
	 * Make sure you do not add song/podcast/audiobook to a list if it is already there. Hint: use the equals() method
	 * If it is already in a list, set the errorMsg string and return false. Otherwise add it to the list and return true
	 * See the video
	 */
	public void download(AudioContent content) throws AudioContentAlreadyDownloaded 
	{
		if(content.getType().equals(Song.TYPENAME)) //check if content is song
		{
			Song song = (Song) content;
			for(int i = 0; i<songs.size(); i++) //loop through songs to see if content already in songs
			{
				if(song.equals(songs.get(i)))
				{
					throw new AudioContentAlreadyDownloaded("Song " +content.getTitle() +" already downloaded");
				}
				
			}
			songs.add(song); //add song to list and return true
		}
		else if(content.getType().equals(AudioBook.TYPENAME))//check if content is audiobook
		{
			AudioBook audiobook = (AudioBook) content;
			for(int i = 0; i<audiobooks.size(); i++) //loop through songs to see if content already in audiobook
			{
				if(content.equals(audiobooks.get(i))) 
				{
					throw new AudioContentAlreadyDownloaded("AudioBook " + content.getTitle()+ " already downloaded");
				}
			}
			audiobooks.add(audiobook); //add audiobook to list and return true
			
		}
	}
	
	// Print Information (printInfo()) about all songs in the array list
	public void listAllSongs()
	{
		for (int i = 0; i < songs.size(); i++)
		{
			int index = i + 1;
			System.out.print("" + index + ". ");
			songs.get(i).printInfo();
			System.out.println();	
		}
	}
	
	// Print Information (printInfo()) about all audiobooks in the array list
	public void listAllAudioBooks()
	{
		for (int i = 0; i < audiobooks.size(); i++)
		{
			int index = i + 1;
			System.out.print("" + index + ". ");
			audiobooks.get(i).printInfo();
			System.out.println();	
		}
	}
	
  // Print Information (printInfo()) about all podcasts in the array list
	public void listAllPodcasts()
	{
		
	}
	
  // Print the name of all playlists in the playlists array list
	// First print the index number as in listAllSongs() above
	public void listAllPlaylists()
	{
		for (int i = 0; i < playlists.size(); i++)
		{
			int index = i + 1;
			System.out.print("" + index + ". ");
			System.out.println(playlists.get(i).getTitle());
		}
	}
	
  // Print the name of all artists. 
	public void listAllArtists()
	{
		// First create a new (empty) array list of string 
		// Go through the songs array list and add the artist name to the new arraylist only if it is
		// not already there. Once the artist arrayl ist is complete, print the artists names
		ArrayList<String> emptyArrL = new ArrayList<>();
		for(int i = 0; i<songs.size(); i++)
		{
			if(!emptyArrL.contains(songs.get(i).getArtist()))
			{
				emptyArrL.add(songs.get(i).getArtist());
			}
		}
		for(int i = 0; i<emptyArrL.size(); i++)
		{
			System.out.println(i+1 + ". " + emptyArrL.get(i));
		}
	}

	// Delete a song from the library (i.e. the songs list) - 
	// also go through all playlists and remove it from any playlist as well if it is part of the playlist
	public void deleteSong(int index) 
	{
		if(index>songs.size()) throw new AudioContentNotFound("SONG at position" + index + "not found");
		for(int i = 0; i<playlists.size(); i++)
		{
			for(int j = 0; j<playlists.get(i).getContent().size(); j++)
			{
				if(playlists.get(i).getContent().get(j).getType().equals(Song.TYPENAME)){
					if(playlists.get(i).getContent().get(j).equals(songs.get(index-1)))
					{
						playlists.get(i).getContent().remove(songs.get(index-1));
					}
				}
			}
			
		}
		songs.remove(index-1);
	}
	
  //Sort songs in library by year
	public void sortSongsByYear()
	{
		// Use Collections.sort() 
		Collections.sort(songs, new SongYearComparator());
		
	}
  // Write a class SongYearComparator that implements
	// the Comparator interface and compare two songs based on year
	private class SongYearComparator implements Comparator<Song>
	{
		public int compare(Song other1, Song other2){
			return other1.getYear()-other2.getYear();
		}
	}

	// Sort songs by length
	public void sortSongsByLength()
	{
	 // Use Collections.sort() 
	 Collections.sort(songs, new SongLengthComparator());
	}
  // Write a class SongLengthComparator that implements
	// the Comparator interface and compare two songs based on length
	private class SongLengthComparator implements Comparator<Song>
	{
		public int compare(Song other1, Song other2){
			return other1.getLength()-other2.getLength();
		}
	}

	// Sort songs by title 
	public void sortSongsByName() 
	{
	  // Use Collections.sort()
		// class Song should implement the Comparable interface
		// see class Song code
		Collections.sort(songs);
	}

	
	
	/*
	 * Play Content
	 */
	
	// Play song from songs list
	public void playSong(int index) throws AudioContentNotFound
	{
		if (index < 1 || index > songs.size())
		{
			throw new AudioContentNotFound("Song not found");
		}
		songs.get(index-1).play();
	}
	
	// Play podcast from list (specify season and episode)
	// Bonus
	public boolean playPodcast(int index, int season, int episode)
	{
		return false;
	}
	
	// Print the episode titles of a specified season
	// Bonus 
	public boolean printPodcastEpisodes(int index, int season)
	{
		return false;
	}
	
	// Play a chapter of an audio book from list of audiobooks
	public void playAudioBook(int index, int chapter) throws AudioContentNotFound
	{
		if(index<1 || index>audiobooks.size())
		{
			throw new AudioContentNotFound("AudioBook not found");
		}
		else if(chapter<1 || chapter>audiobooks.get(index-1).getNumberOfChapters())
		{
			throw new AudioContentNotFound("Chapter not found");
		}
		audiobooks.get(index-1).selectChapter(chapter);
		audiobooks.get(index-1).play();

	}
	
	// Print the chapter titles (Table Of Contents) of an audiobook
	// see class AudioBook
	public void printAudioBookTOC(int index) throws AudioContentNotFound
	{
		if(index<1 || index>audiobooks.size())
		{
			throw new AudioContentNotFound("AudioBook not found");
		}
		audiobooks.get(index-1).printTOC();
	}
	
  /*
   * Playlist Related Methods
   */
	
	// Make a new playlist and add to playlists array list
	// Make sure a playlist with the same title doesn't already exist
	public void makePlaylist(String title) throws AudioContentAlreadyExists
	{
		for(int i = 0; i<playlists.size(); i++)
		{
			if(playlists.get(i).getTitle().equals(title))
			{
				throw AudioContentAlreadyExists("Playlist already exists");
			}
			
		}
		Playlist newPl = new Playlist(title);
		playlists.add(newPl);
		
	}
	
	// Print list of content information (songs, audiobooks etc) in playlist named title from list of playlists
	public void printPlaylist(String title) throws AudioContentNotFound
	{
		for(int i = 0; i<playlists.size(); i++)
		{
			if(playlists.get(i).getTitle().equals(title))
			{
				playlists.get(i).printContents();
			}
			
		}
		throw AudioContentNotFound("Playlist not found");
	}
	
	// Play all content in a playlist
	public void playPlaylist(String playlistTitle) throws AudioContentNotFound
	{
		for(int i = 0; i<playlists.size(); i++)
		{
			if(playlists.get(i).getTitle().equals(playlistTitle))
			{
				playlists.get(i).playAll();
			}
			
		}
		throw AudioContentNotFound("Playlist not found");
	}
	
	// Play a specific song/audiobook in a playlist
	public void playPlaylist(String playlistTitle, int indexInPL) throws AudioContentNotFound
	{
		System.out.println(playlistTitle);
		for(int i = 0; i<playlists.size(); i++)
		{
			if(playlists.get(i).getTitle().equals(playlistTitle))
			{
				playlists.get(i).play(indexInPL);
			}
			
		}
		throw AudioContentNotFound("Playlist not found");
	}
	
	// Add a song/audiobook/podcast from library lists at top to a playlist
	// Use the type parameter and compare to Song.TYPENAME etc
	// to determine which array list it comes from then use the given index
	// for that list
	public void addContentToPlaylist(String type, int index, String playlistTitle) throws AudioContentNotFound
	{
		int index2 = 0;
		if(type.equals(Song.TYPENAME))
		{
			if(index<1 || index>songs.size())
			{
				throw AudioContentNotFound("Song not found");
			}
			else
			{
				for(int i = 0; i<playlists.size(); i++)
				{
					if(playlists.get(i).getTitle().equals(playlistTitle))
						index2 = i;
				}
				playlists.get(index2).addContent((AudioContent)songs.get(index-1));
			}
		}
		if(type.equals(AudioBook.TYPENAME))
		{
			if(index<1 || index>songs.size())
			{
				throw AudioContentNotFound("Audiobook not found");
			}
			else
			{
				for(int i = 0; i<playlists.size(); i++)
				{
					if(playlists.get(i).getTitle().equals(playlistTitle))
					index2 = i;
				}
				playlists.get(index2).addContent((AudioContent)audiobooks.get(index-1));
			}
		}
		throw AudioContentNotFound("Type not found");
		
	}

  // Delete a song/audiobook/podcast from a playlist with the given title
	// Make sure the given index of the song/audiobook/podcast in the playlist is valid 
	public void delContentFromPlaylist(int index, String title) throws AudioContentNotFound
	{
		for(int i = 0; i<playlists.size(); i++)
		{
			
			if(title.equals(playlists.get(i).getTitle()))
			{
				if(index<1 || index>playlists.get(i).getContent().size())
				{
					throw AudioContentNotFound("Content does not exist");
				}

				playlists.get(i).deleteContent(index);
			}
			
		}
		throw AudioContentNotFound("Content does not exist");
	}
	
}

class AudioContentAlreadyDownloaded extends RuntimeException
{
	public AudioContentAlreadyDownloaded(){}

	public AudioContentAlreadyDownloaded(String message)
	{
		super(message);
	}
}
class AudioContentNotFound extends RuntimeException
{
	public AudioContentNotFound(){}

	public AudioContentNotFound(String message)
	{
		super(message);
	}
}

class AudioContentAlreadyExists extends RuntimeException
{
	public AudioContentAlreadyExists(){}

	public AudioContentAlreadyExists(String message)
	{
		super(message);
	}
}