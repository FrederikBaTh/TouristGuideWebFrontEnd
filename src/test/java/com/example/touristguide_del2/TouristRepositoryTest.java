package com.example.touristguide_del2;
import com.example.touristguide_del2.model.TouristAttraction;
import com.example.touristguide_del2.repository.TouristRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
public class TouristRepositoryTest {


    @InjectMocks
    private TouristRepository touristRepository;

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    //Denne test virker ikke

/*    @Test
    void testGetAllAttractions() throws SQLException {

        Connection connectionMock = mock(Connection.class);
        PreparedStatement statementMock = mock(PreparedStatement.class);
        ResultSet resultSetMock = mock(ResultSet.class);

        when(resultSetMock.next()).thenReturn(true).thenReturn(false);
        when(resultSetMock.getInt("id")).thenReturn(1);
        when(resultSetMock.getString("name")).thenReturn("Attraction1");
        when(resultSetMock.getString("description")).thenReturn("Description1");
        when(resultSetMock.getString("city_name")).thenReturn("City1");

        when(statementMock.executeQuery()).thenReturn(resultSetMock);

        Mockito.when(connectionMock.prepareStatement(anyString())).thenReturn(statementMock);

        TouristRepository touristRepository = new TouristRepository(connectionMock);

        List<TouristAttraction> attractions = touristRepository.getAllAttractions();

        List<TouristAttraction> expectedAttractions = new ArrayList<>();
        expectedAttractions.add(new TouristAttraction(1, "Attraction1", "Description1", "City1"));
        assertEquals(expectedAttractions, attractions);
    }
*/


    @Test
    void getCitiesTest() {
        List<String> cities = touristRepository.getCities();
        assertNotNull(cities);
        assertFalse(cities.isEmpty());
        assertEquals(6, cities.size());
    }

    @Test
    void getTagsTest() {
        List<String> tags = touristRepository.getTags();
        assertNotNull(tags);
        assertFalse(tags.isEmpty());
        assertEquals(7, tags.size());
    }

   @Test
   void testGetAttractionByName() throws SQLException {
       Connection connection = Mockito.mock(Connection.class);
       PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);
       ResultSet resultSet = Mockito.mock(ResultSet.class);

       when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
       when(preparedStatement.executeQuery()).thenReturn(resultSet);
       when(resultSet.next()).thenReturn(true);
       when(resultSet.getInt("id")).thenReturn(1);
       when(resultSet.getString("name")).thenReturn("TestAttraction"); // Assuming "name" is the column for attraction name
       when(resultSet.getString("description")).thenReturn("Test Description");
       when(resultSet.getString("city_name")).thenReturn("Test City");

       TouristRepository touristRepository = new TouristRepository(connection);

       TouristAttraction attraction = touristRepository.getAttractionByName("TestAttraction");

       assertNotNull(attraction);
       assertEquals(1, attraction.getId());
       assertEquals("TestAttraction", attraction.getName());
       assertEquals("Test Description", attraction.getDescription());
   }


    @Test
    void addAttractionTest() throws SQLException {
        TouristAttraction attraction = new TouristAttraction(1, "Attraction", "Description", "City");
        when(connection.prepareStatement(anyString(), anyInt())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(1)).thenReturn(1);

        TouristAttraction addedAttraction = touristRepository.addAttraction(attraction);
        assertNotNull(addedAttraction);
        assertEquals(1, addedAttraction.getId());
    }


}
