package pathfinder;

import org.junit.Test;
import pathfinder.parser.CampusBuilding;
import pathfinder.parser.CampusPathsParser;

import java.io.FileNotFoundException;
import java.util.List;

import static org.junit.Assert.*;

public class CampusMapTest {

    CampusMap campusMap = new CampusMap();

    @Test
    public void shortNameExists() {
        assertTrue(campusMap.shortNameExists("BAG"));
        assertTrue(campusMap.shortNameExists("CSE"));
        assertTrue(campusMap.shortNameExists("KNE"));
        assertTrue(campusMap.shortNameExists("MGH (E)"));
        assertFalse(campusMap.shortNameExists("DNE"));
        assertFalse(campusMap.shortNameExists("CSE "));
        assertFalse(campusMap.shortNameExists("MGH (N)"));
    }

    @Test
    public void longNameForShort() {
        assertEquals(campusMap.longNameForShort("CSE"), "Paul G. Allen Center for Computer Science & Engineering");
        assertEquals(campusMap.longNameForShort("MGH"), "Mary Gates Hall (North Entrance)");
        assertEquals(campusMap.longNameForShort("OUG"), "Odegaard Undergraduate Library");
        assertEquals(campusMap.longNameForShort("BAG"), "Bagley Hall (East Entrance)");
    }

    @Test
    public void buildingNames() {
        List<CampusBuilding> lst = CampusPathsParser.parseCampusBuildings("campus_buildings.tsv");
        for(CampusBuilding b : lst){
            assertEquals(campusMap.longNameForShort(b.getShortName()), b.getLongName());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void findShortestPath() {
        //functionality of dijkstra's tested in spec tests, this test is for other behavior
        campusMap.findShortestPath(null, null);
        campusMap.findShortestPath(null, "MGH");
        campusMap.findShortestPath("MGH", null);
        campusMap.findShortestPath("MGH 1", "CSE");
        campusMap.findShortestPath("CSE", "MGH 1");
    }
}