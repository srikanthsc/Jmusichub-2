package musichub.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.UUID;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import musichub.util.XMLHandler;

public class PlaylistTest {


    @Test
    public void testPlaylistConstructor1() {
        UUID uuid = UUID.randomUUID();
        PlayList playList = new PlayList("title",uuid.toString(), new ArrayList<>());
        assertEquals("title", playList.getTitle());
        assertEquals(uuid, playList.getUUID());
        assertTrue(playList.getElements().isEmpty());
    }

    @Test
    public void testPlaylistConstructor2() {
        PlayList playList = new PlayList("title");
        assertEquals("title", playList.getTitle());
        assertTrue(playList.getElements().isEmpty());
    }



    @Test
    public void testCreateXMLElement()throws Exception {
        XMLHandler xmlHandler = new XMLHandler();
        Document document = xmlHandler.createXMLDocument();
        Element root = document.createElement("playlists");
        document.appendChild(root);
        UUID uuid = UUID.randomUUID();
        ArrayList<UUID> elements = new ArrayList<>();
        elements.add(UUID.randomUUID());
        PlayList playList= new PlayList("title",uuid.toString(), elements);
        playList.createXMLElement(document, root);
        PlayList playList2= readPlayListFromXML(root.getChildNodes());
        assertEquals(playList2.getTitle(),playList2.getTitle());
        assertEquals(playList2.getElements().size(),playList2.getElements().size());
        assertEquals(playList2.getElements().get(0),playList2.getElements().get(0));
    }


}
