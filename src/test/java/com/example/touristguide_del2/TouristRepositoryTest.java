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
import static javax.management.Query.eq;
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


    @Test
    void testGetAllAttractions() throws SQLException {

        // Mock the database connection, statement, and result set
        Connection connectionMock = mock(Connection.class);
        PreparedStatement statementMock = mock(PreparedStatement.class);
        ResultSet resultSetMock = mock(ResultSet.class);

        // Mock data to be returned by the result set
        when(resultSetMock.next()).thenReturn(true).thenReturn(false); // Simulate one row in the result set
        when(resultSetMock.getInt("id")).thenReturn(1); // Mock ID
        when(resultSetMock.getString("name")).thenReturn("Attraction1"); // Mock name
        when(resultSetMock.getString("description")).thenReturn("Description1"); // Mock description
        when(resultSetMock.getString("city_name")).thenReturn("City1"); // Mock city name

        // Mock the execution of the query
        when(statementMock.executeQuery()).thenReturn(resultSetMock);

        // Mock the connection creation
        Mockito.when(connectionMock.prepareStatement(anyString())).thenReturn(statementMock);

        // Create an instance of TouristRepository with the mocked connection
        TouristRepository touristRepository = new TouristRepository(connectionMock);

        // Call the method under test
        List<TouristAttraction> attractions = touristRepository.getAllAttractions();

        // Verify the result
        List<TouristAttraction> expectedAttractions = new ArrayList<>();
        expectedAttractions.add(new TouristAttraction(1, "Attraction1", "Description1", "City1"));
        assertEquals(expectedAttractions, attractions);
    }



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

   /* @Test
    void getAttractionByNameTest() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/tourist_database";
        when(DriverManager.getConnection(eq(url), anyString(), anyString())).thenReturn(connection);
        // Mocking database interaction
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("name")).thenReturn("Attraction");
        when(resultSet.getString("description")).thenReturn("Description");
        when(resultSet.getString("city_name")).thenReturn("City");

        // Calling the method to be tested
        TouristAttraction attraction = touristRepository.getAttractionByName("Attraction");

        // Verifying the result
        if (attraction != null) {
            assertEquals(1, attraction.getId());
            assertEquals("Attraction", attraction.getName());
            assertEquals("Description", attraction.getDescription());
            assertEquals("City", attraction.getCity());
        } else {
            assertNull(attraction); // Assert that the result is null
        }
    }*/
   @Test
   void testGetAttractionByName() throws SQLException {
       // Mocking database interaction
       Connection connection = Mockito.mock(Connection.class);
       PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);
       ResultSet resultSet = Mockito.mock(ResultSet.class);

       // Mock behavior for the database interaction
       when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
       when(preparedStatement.executeQuery()).thenReturn(resultSet);
       when(resultSet.next()).thenReturn(true);
       when(resultSet.getInt("id")).thenReturn(1);
       when(resultSet.getString("name")).thenReturn("TestAttraction"); // Assuming "name" is the column for attraction name
       when(resultSet.getString("description")).thenReturn("Test Description");
       when(resultSet.getString("city_name")).thenReturn("Test City");

       // Creating an instance of TouristRepository
       TouristRepository touristRepository = new TouristRepository(connection);

       // Testing method with a sample name
       TouristAttraction attraction = touristRepository.getAttractionByName("TestAttraction");

       // Verify that the object returned is not null and has correct values
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
