package ExpediaTest;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import Expedia.Hotel;
import Expedia.IDatabase;

public class HotelTest
{
	private Hotel targetHotel;
	private int NightsToRentHotel = 5;
	
	@Before
	public void TestInitialize()
	{
		targetHotel = new Hotel(NightsToRentHotel);
	}
	
	@Test
	public void TestThatHotelHasCorrectBasePriceForOneDayStay()
	{
		Hotel target = new Hotel(1);
		Assert.assertEquals(45, target.getBasePrice(), 0.01);
	}
	
	@Test
	public void TestThatHotelHasCorrectBasePriceForTwoDayStay()
	{
		Hotel target = new Hotel(2);
		Assert.assertEquals(90, target.getBasePrice(), 0.01);
	}
	
	@Test
	public void TestThatHotelHasCorrectBasePriceForTenDaysStay()
	{
		Hotel target = new Hotel(10);
		Assert.assertEquals(450, target.getBasePrice(), 0.01);
	}
	
	@Test(expected=RuntimeException.class)
	public void TestThatHotelThrowsOnBadLength()
	{
		new Hotel(-5);
	}

	
	@Test
    public void TestThatHotelDoesGetRoomOccupantFromTheDatabase()
    {
		
		IDatabase mockDB = EasyMock.createMock(IDatabase.class);
        String roomOccupant = "Whale Rider";
        String anotherRoomOccupant = "Raptor Wrangler";
        
        EasyMock.expect(mockDB.getRoomOccupant(24)).andReturn(roomOccupant);
        EasyMock.expect(mockDB.getRoomOccupant(1025)).andReturn(anotherRoomOccupant);

        // stub default return value
        EasyMock.expect(mockDB.getRoomOccupant(EasyMock.anyInt())).andStubReturn("Empty room");
        
        EasyMock.replay(mockDB);
       
        Hotel target = new Hotel(10);
        target.Database = mockDB;

        String result;

        result = target.getRoomOccupant(25);
        Assert.assertEquals("Empty room", result);

        result = target.getRoomOccupant(1025);
        Assert.assertEquals(anotherRoomOccupant, result);

        result = target.getRoomOccupant(24);
        Assert.assertEquals(roomOccupant, result);

        result = target.getRoomOccupant(23);
        Assert.assertEquals("Empty room", result);

        //mocks.VerifyAll();
        EasyMock.verify(mockDB);
    }
    @Test
    public void TestThatHotelDoesGetRoomCountFromDatabase()
    {
    	IDatabase mockDB = EasyMock.createStrictMock(IDatabase.class);
        ArrayList<String> Rooms = new ArrayList();
        for (int i = 0; i < 100; i++)
        {
        	Integer in = (Integer) i;
        	String s = in.toString();
            Rooms.add(s);
        }

        mockDB.Rooms = Rooms;
        EasyMock.replay(mockDB);


        Hotel target = new Hotel(10);
        target.Database = mockDB;

        int roomCount = target.AvailableRooms();
        assertEquals(Rooms.size(), roomCount);

        EasyMock.verify(mockDB);
    }
    
    @Test
    public void TestGetMiles(){
    	Hotel h = new Hotel(10);
    	int miles = h.getMiles();
    	assertEquals(0, miles);
    }
}
