package musichub.business;

import static org.junit.Assert.assertEquals;

import java.util.UUID;

import org.junit.Test;

public class SongTest {
	@Test
	public void SongTest() {
		UUID uuid = UUID.randomUUID();
		Song song = new Song("title","artiste",100,uuid.toString(),"content","ROCK");
		assertEquals("title",song.getTitle());
		assertEquals("artiste",song.getArtist());
		assertEquals(uuid,song.getUUID());
		assertEquals("rock",song.getGenre());
		assertEquals("content",song.getContent());
	}

}
