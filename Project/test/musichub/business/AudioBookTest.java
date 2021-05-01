package musichub.business;

import static org.junit.Assert.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;

import org.junit.Test;

public class AudioBookTest {
    @Test
    public void AudioBookTest() {
        UUID uuid = UUID.randomUUID();
        AudioBook audiobook = new AudioBook("title","artiste",100,uuid.toString(),"content","english","theater");
        assertEquals("title",audiobook.getTitle());
        assertEquals("artiste",audiobook.getArtist());
        assertEquals(uuid,audiobook.getUUID());
        assertEquals("rock",audiobook.getCategory());
        assertEquals("content",audiobook.getContent());
        assertEquals("english",audiobook.getLanguage());
    }

}
