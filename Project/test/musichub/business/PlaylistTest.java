package musichub.business;

import static org.junit.Assert.assertEquals;
import java.util.UUID;

import org.junit.Test;
import org.junit.jupiter.api.Test;

public class PlaylistTest {
    @Test
    public void PlayListTest() {
        UUID uuid = UUID.randomUUID();
        PlayList playList = new PlayList("title",uuid.toString());
        assertEquals("title",PlayList.getTitle());
        assertEquals(uuid,PlayList.getUUID());

    }

}
