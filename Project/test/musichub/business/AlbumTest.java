package musichub.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
//import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.UUID;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import musichub.util.XMLHandler;

public class AlbumTest {

	private static final String SAMPLE_DATE = "2021-04-06";

	@Test
	public void testAlbumConstructor1() {
		UUID uuid = UUID.randomUUID();
		Album album = new Album("title", "artist", 2160, uuid.toString(), SAMPLE_DATE, new ArrayList<>());
		assertEquals("title", album.getTitle());
		assertEquals(SAMPLE_DATE, new SimpleDateFormat("yyyy-MM-dd").format(album.getDate()));
		assertEquals("artist", album.getArtist());
		assertEquals(2160, album.getLengthInSeconds());
		assertEquals(uuid, album.getUUID());
		assertTrue(album.getSongs().isEmpty());
	}

	@Test
	public void testAlbumConstructor2() {
		Album album = new Album("title", "artist", 2160, SAMPLE_DATE);
		assertEquals("title", album.getTitle());
		assertEquals(SAMPLE_DATE, new SimpleDateFormat("yyyy-MM-dd").format(album.getDate()));
		assertEquals("artist", album.getArtist());
		assertEquals(2160, album.getLengthInSeconds());
		assertTrue(album.getSongs().isEmpty());
	}

	@Test
	public void testAlbumConstructorXml() throws Exception {
		XMLHandler xmlHandler = new XMLHandler();
		NodeList albumNodes = xmlHandler.parseXMLFile("testfiles/album/album.xml");
		assertNotNull(albumNodes);

		Album album = readAlbumFromXML(albumNodes);
		assertEquals("Best of Beatles", album.getTitle());
		assertEquals("The Beatles", album.getArtist());
		assertEquals(2160, album.getLengthInSeconds());
		assertEquals(5, album.getSongs().size());
		assertEquals(new UUID(0, 0), album.getUUID());
		assertEquals(SAMPLE_DATE, new SimpleDateFormat("yyyy-MM-dd").format(album.getDate()));
	}

	@Test
	public void testAlbumRandomSong() throws Exception {
		XMLHandler xmlHandler = new XMLHandler();
		NodeList albumNodes = xmlHandler.parseXMLFile("testfiles/album/album.xml");
		assertNotNull(albumNodes);

		Album album = readAlbumFromXML(albumNodes);
		assertTrue(album.getSongs().containsAll(album.getSongsRandomly()));
	}

	@Test
	public void testCreateXMLElement()throws Exception {
		XMLHandler xmlHandler = new XMLHandler();
		Document document = xmlHandler.createXMLDocument();
		Element root = document.createElement("albums");
		document.appendChild(root);
		UUID uuid = UUID.randomUUID();
		ArrayList<UUID> songs = new ArrayList<>();
		songs.add(UUID.randomUUID());
		Album album = new Album("title", "artist", 2160, uuid.toString(), SAMPLE_DATE, songs);
		album.createXMLElement(document, root);
		Album album2 = readAlbumFromXML(root.getChildNodes());
		assertEquals(album.getTitle(),album2.getTitle());
		assertEquals(album.getArtist(),album2.getArtist());
		assertEquals(album.getLengthInSeconds(),album2.getLengthInSeconds());
		assertEquals(album.getDate(),album2.getDate());
		assertEquals(album.getSongs().size(),album2.getSongs().size());
		assertEquals(album.getSongs().get(0),album2.getSongs().get(0));
	}

	public static Album readAlbumFromXML(NodeList albumNodes) throws Exception {
		for (int i = 0; i < albumNodes.getLength(); i++) {
			if (albumNodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
				Element albumElement = (Element) albumNodes.item(i);
				if (albumElement.getNodeName().equals("album")) {
					return new Album(albumElement);
				}
			}
		}
		throw new Exception("album not found!!");

	}
}