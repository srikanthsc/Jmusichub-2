package musichub.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MusicHubTest {
	private static final String SAMPLE_DATE = "2021-04-06";
	private static final String SAMPLE_DATE2 = "2021-05-06";
	private static final String SAMPLE_DATE3 = "2021-06-06";

	private static final String EMPTY_ALBUMS = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><albums></albums>";
	private static final String EMPTY_ELEMENTS = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><elements></elements>";
	private static final String EMPTY_PLAYLISTS = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><playlists></playlists>";

	@Before
	public void before() {
		System.setProperty("source.dir", "testfiles\\musichub");
		after();
	}

	@After
	public void after() {
		try {
			createFile(MusicHub.ALBUMS_FILE_PATH, EMPTY_ALBUMS);
			createFile(MusicHub.ELEMENTS_FILE_PATH, EMPTY_ELEMENTS);
			createFile(MusicHub.PLAYLISTS_FILE_PATH, EMPTY_PLAYLISTS);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void createFile(String path, String content) throws FileNotFoundException {
		PrintWriter pw = new PrintWriter(new File(path));
		pw.println(content);
		pw.close();
	}

	@Test
	public void testAddElement() {
		MusicHub musichub = new MusicHub();
		Song song = new Song("testsong", "junit", 12345, "content", Genre.HIPHOP.getGenre());
		assertFalse(exists(musichub, song));
		musichub.addElement(song);
		assertTrue(exists(musichub, song));
	}

	private boolean exists(MusicHub musichub, Song song) {
		for (Iterator<AudioElement> itr = musichub.elements(); itr.hasNext();) {
			AudioElement element = itr.next();
			if (element.getUUID().equals(song.getUUID())) {
				return true;
			}
		}
		return false;
	}

	@Test
	public void testAddAlbum() {

		MusicHub musichub = new MusicHub();
		Album album = new Album("testalbum", "junit1", 1235, SAMPLE_DATE);
		assertFalse(exists(musichub, album));
		musichub.addAlbum(album);
		assertTrue(exists(musichub, album));

	}

	private boolean exists(MusicHub musichub, Album album) {
		for (Iterator<Album> itr = musichub.albums(); itr.hasNext();) {
			Album element = itr.next();
			if (element.getUUID().equals(album.getUUID())) {
				return true;
			}
		}
		return false;
	}

	@Test
	public void testAddDelPlaylist() throws Exception {

		UUID uuid = UUID.randomUUID();
		MusicHub musichub = new MusicHub();
		PlayList playlist = new PlayList("testplaylist", uuid.toString(), new ArrayList<>());
		assertFalse(exists(musichub, playlist));
		musichub.addPlaylist(playlist);
		assertTrue(exists(musichub, playlist));
		musichub.deletePlayList("testplaylist");
		assertFalse(exists(musichub, playlist));

	}

	private boolean exists(MusicHub musichub, PlayList playlist) {
		for (Iterator<PlayList> itr = musichub.playlists(); itr.hasNext();) {
			PlayList element = itr.next();
			if (element.getUUID().equals(playlist.getUUID())) {
				return true;
			}
		}
		return false;
	}

	@Test
	public void testAlbumsTitlesSortedByDate() {
		MusicHub musichub = new MusicHub();
		Album album1 = new Album("testalbum1", "junit1", 1235, SAMPLE_DATE3);
		Album album2 = new Album("testalbum2", "junit1", 1234, SAMPLE_DATE);
		Album album3 = new Album("testalbum3", "junit1", 1234, SAMPLE_DATE2);
		musichub.addAlbum(album1);
		musichub.addAlbum(album2);
		musichub.addAlbum(album3);
		String result = musichub.getAlbumsTitlesSortedByDate();
		assertEquals("testalbum2\ntestalbum3\ntestalbum1\n", result);

	}

	@Test
	public void testAudiobooksTitlesSortedByAuthor() {
		MusicHub musichub = new MusicHub();
		AudioBook audiobook1 = new AudioBook("testaudiobook1", "junit2", 1235, "content1",
				Language.GERMAN.getLanguage(), Category.SPEECH.getCategory());
		AudioBook audiobook2 = new AudioBook("testaudiobook2", "junit3", 1235, "content1",
				Language.ENGLISH.getLanguage(), Category.SPEECH.getCategory());
		AudioBook audiobook3 = new AudioBook("testaudiobook3", "junit1", 1235, "content1",
				Language.FRENCH.getLanguage(), Category.SPEECH.getCategory());

		musichub.addElement(audiobook1);
		musichub.addElement(audiobook2);
		musichub.addElement(audiobook3);
		String result = musichub.getAudiobooksTitlesSortedByAuthor();
		assertEquals("testaudiobook3\ntestaudiobook1\ntestaudiobook2\n", result);

	}

	@Test
	public void testGetAlbumSongs() throws Exception {
		MusicHub musichub = new MusicHub();
		Album album1 = new Album("testalbum1", "junit1", 1235, SAMPLE_DATE3);
		Album album2 = new Album("testalbum2", "junit1", 1234, SAMPLE_DATE);
		Album album3 = new Album("testalbum3", "junit1", 1234, SAMPLE_DATE2);
		musichub.addAlbum(album1);
		musichub.addAlbum(album2);
		musichub.addAlbum(album3);
		Song song1 = new Song("testsong1", "junit2", 1235, "content1", Genre.HIPHOP.getGenre());
		Song song2 = new Song("testsong2", "junit3", 1235, "content1", Genre.HIPHOP.getGenre());
		Song song3 = new Song("testsong3", "junit1", 1235, "content1", Genre.HIPHOP.getGenre());

		musichub.addElement(song1);
		musichub.addElement(song2);
		musichub.addElement(song3);
		album2.addSong(song1.getUUID());
		album2.addSong(song3.getUUID());
		List<AudioElement> albumSongs = musichub.getAlbumSongs("testalbum2");
		assertEquals(2, albumSongs.size());
		assertTrue(albumSongs.contains(song1));
		assertTrue(albumSongs.contains(song3));
	}

	@Test
	public void testGetAlbumSongsSortedByGenre() throws Exception {
		MusicHub musichub = new MusicHub();
		Album album1 = new Album("testalbum1", "junit1", 1235, SAMPLE_DATE3);
		Album album2 = new Album("testalbum2", "junit1", 1234, SAMPLE_DATE);
		Album album3 = new Album("testalbum3", "junit1", 1234, SAMPLE_DATE2);
		musichub.addAlbum(album1);
		musichub.addAlbum(album2);
		musichub.addAlbum(album3);
		Song song1 = new Song("testsong1", "junit2", 1235, "content1", Genre.HIPHOP.getGenre());
		Song song2 = new Song("testsong2", "junit3", 1235, "content1", Genre.ROCK.getGenre());
		Song song3 = new Song("testsong3", "junit1", 1235, "content1", Genre.JAZZ.getGenre());

		musichub.addElement(song1);
		musichub.addElement(song2);
		musichub.addElement(song3);
		album2.addSong(song1.getUUID());
		album2.addSong(song2.getUUID());
		album2.addSong(song3.getUUID());
		List<Song> albumSongs = musichub.getAlbumSongsSortedByGenre("testalbum2");
		assertEquals(3, albumSongs.size());
		assertEquals(song1.getUUID(), albumSongs.get(0).getUUID());
		assertEquals(song3.getUUID(), albumSongs.get(1).getUUID());
		assertEquals(song2.getUUID(), albumSongs.get(2).getUUID());
	}

	@Test
	public void testAddElementToAlbum() throws Exception {
		MusicHub musichub = new MusicHub();
		Album album1 = new Album("testalbum1", "junit1", 1235, SAMPLE_DATE3);
		Album album2 = new Album("testalbum2", "junit1", 1234, SAMPLE_DATE);
		Album album3 = new Album("testalbum3", "junit1", 1234, SAMPLE_DATE2);
		musichub.addAlbum(album1);
		musichub.addAlbum(album2);
		musichub.addAlbum(album3);
		Song song1 = new Song("testsong1", "junit2", 1235, "content1", Genre.HIPHOP.getGenre());
		Song song2 = new Song("testsong2", "junit3", 1235, "content1", Genre.ROCK.getGenre());
		Song song3 = new Song("testsong3", "junit1", 1235, "content1", Genre.JAZZ.getGenre());

		musichub.addElement(song1);
		musichub.addElement(song2);
		musichub.addElement(song3);
		musichub.addElementToAlbum(song2.getTitle(), album2.getTitle());
		assertEquals(1, album2.getSongs().size());
		assertEquals(song2.getUUID(), album2.getSongs().get(0));
	}

	@Test
	public void testAddElementToPlaylist() throws Exception {
		UUID uuid = UUID.randomUUID();
		MusicHub musichub = new MusicHub();
		PlayList playlist = new PlayList("testplaylist", uuid.toString(), new ArrayList<>());
		musichub.addPlaylist(playlist);
		Song song1 = new Song("testsong1", "junit2", 1235, "content1", Genre.HIPHOP.getGenre());
		Song song2 = new Song("testsong2", "junit3", 1235, "content1", Genre.ROCK.getGenre());
		Song song3 = new Song("testsong3", "junit1", 1235, "content1", Genre.JAZZ.getGenre());

		musichub.addElement(song1);
		musichub.addElement(song2);
		musichub.addElement(song3);
		musichub.addElementToPlayList("testsong3", "testplaylist");
		assertEquals(1, playlist.getElements().size());
		assertEquals(song3.getUUID(), playlist.getElements().get(0));

	}

	@Test
	public void testSaveAlbums() {
		MusicHub musichub = new MusicHub();
		Album album1 = new Album("testalbum1", "junit1", 1235, SAMPLE_DATE3);
		Album album2 = new Album("testalbum2", "junit1", 1234, SAMPLE_DATE);
		musichub.addAlbum(album1);
		musichub.addAlbum(album2);
		musichub.saveAlbums();
		MusicHub musichub2 = new MusicHub();
		int count = 0;
		for (Iterator<Album> itr = musichub2.albums(); itr.hasNext();) {
			Album album = itr.next();
			if (album.getUUID().equals(album1.getUUID())) {
				count++;
				assertEquals(album.getTitle(), album1.getTitle());
				assertEquals(album.getArtist(), album1.getArtist());
				assertEquals(album.getLengthInSeconds(), album1.getLengthInSeconds());
				assertEquals(album.getDate(), album1.getDate());
			} else if (album.getUUID().equals(album2.getUUID())) {
				count++;
				assertEquals(album.getTitle(), album2.getTitle());
				assertEquals(album.getArtist(), album2.getArtist());
				assertEquals(album.getLengthInSeconds(), album2.getLengthInSeconds());
				assertEquals(album.getDate(), album2.getDate());

			}
		}
		assertEquals(2, count);
	}

	@Test
	public void testSavePlaylists() {
		UUID uuid1 = UUID.randomUUID();
		UUID uuid2 = UUID.randomUUID();
		MusicHub musichub = new MusicHub();
		PlayList playlist1 = new PlayList("testplaylist1", uuid1.toString(), new ArrayList<>());
		musichub.addPlaylist(playlist1);
		PlayList playlist2 = new PlayList("testplaylist2", uuid2.toString(), new ArrayList<>());
		musichub.addPlaylist(playlist2);
		musichub.savePlayLists();
		MusicHub musichub2 = new MusicHub();
		int count = 0;
		for (Iterator<PlayList> itr = musichub2.playlists(); itr.hasNext();) {
			PlayList playlist = itr.next();
			if (playlist.getUUID().equals(playlist1.getUUID())) {
				count++;
				assertEquals(playlist.getTitle(), playlist1.getTitle());

			} else if (playlist.getUUID().equals(playlist2.getUUID())) {
				count++;
				assertEquals(playlist.getTitle(), playlist2.getTitle());

			}
		}
		assertEquals(2, count);

	}
	@Test
	public void testSaveElements() {
		MusicHub musichub = new MusicHub();
		AudioBook audiobook1 = new AudioBook("testaudiobook1", "junit2", 1235, "content1",
				Language.GERMAN.getLanguage(), Category.SPEECH.getCategory());
		musichub.addElement(audiobook1);
		Song song1 = new Song("testsong1", "junit2", 1235, "content1", Genre.HIPHOP.getGenre());
		musichub.addElement(song1);
		musichub.saveElements();
		MusicHub musichub2 = new MusicHub();
		int count = 0;
		for (Iterator<AudioElement> itr = musichub2.elements(); itr.hasNext();) {
			AudioElement element = itr.next();
			if (element.getUUID().equals(audiobook1.getUUID())) {
				count++;
				AudioBook audiobook = (AudioBook)element;
				assertEquals(audiobook.getTitle(), audiobook1.getTitle());
				assertEquals(audiobook.getArtist(), audiobook1.getArtist());
				assertEquals(audiobook.getCategory(), audiobook1.getCategory());
				assertEquals(audiobook.getContent(), audiobook1.getContent());
				assertEquals(audiobook.getLanguage(), audiobook1.getLanguage());
				assertEquals(audiobook.getLengthInSeconds(), audiobook1.getLengthInSeconds());

			} else if (element.getUUID().equals(song1.getUUID())) {
				count++;
				Song song = (Song)element;
				assertEquals(song.getTitle(), song1.getTitle());
				assertEquals(song.getArtist(), song1.getArtist());
				assertEquals(song.getGenre(), song1.getGenre());
				assertEquals(song.getContent(), song1.getContent());
				assertEquals(song.getLengthInSeconds(), song1.getLengthInSeconds());

			}
		}
		assertEquals(2, count);
	}
}
