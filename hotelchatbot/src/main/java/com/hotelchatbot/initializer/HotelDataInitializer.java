package com.hotelchatbot.initializer;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hotelchatbot.domain.Amenities;
import com.hotelchatbot.domain.Hotel;
import com.hotelchatbot.domain.HotelRoom;
import com.hotelchatbot.domain.RoomType;
import com.hotelchatbot.service.AmenitiesService;
import com.hotelchatbot.service.HotelRoomService;
import com.hotelchatbot.service.HotelService;
import com.hotelchatbot.service.RoomTypeService;

// If the database is not set up yet, populate the Hotel data
// (and in doing so, the HotelEmbedding, HotelRoom, and HotelRoomEmbedding data)
@Component
public class HotelDataInitializer {

    @Autowired
    HotelService hotelService;

    @Autowired
    HotelRoomService hotelRoomService;

    @Autowired
    RoomTypeService roomTypeService;

    @Autowired
    AmenitiesService amenitiesService;

    public void init() {
        // If the database hasn't been setup yet
        if (hotelRoomService.count() == 0 && hotelService.count() == 0) {
            /// Get the previously instantiated data///

            // Hotel Amenities
            Amenities a_Gym = amenitiesService.findByName("Fitness Center");
            Amenities a_Pool = amenitiesService.findByName("Pool");
            Amenities a_BB = amenitiesService.findByName("Breakfast");
            Amenities a_Parking = amenitiesService.findByName("Parking");
            Amenities a_AirportS = amenitiesService.findByName("Airport Shuttle");
            Amenities a_Beach = amenitiesService.findByName("Beach Nearby");
            Amenities a_AmuseParkS = amenitiesService.findByName("Amusement Park Nearby");
            Amenities a_TourBus = amenitiesService.findByName("Tour Bus Shuttle");
            
            // HotelRoom Amenities
            Amenities a_IM = amenitiesService.findByName("Ice Machine");
            Amenities a_Wifi = amenitiesService.findByName("Wifi");
            Amenities a_AC = amenitiesService.findByName("Air Conditioning");
            Amenities a_Kitchen = amenitiesService.findByName("Kitchenette");
            Amenities a_Microwave = amenitiesService.findByName("Microwave");
            Amenities a_Fridge = amenitiesService.findByName("Mini Fridge");
            Amenities a_Coffee = amenitiesService.findByName("Coffee Maker");

            // RoomType(s)
            RoomType rt_Single = roomTypeService.findByName("Single");
            RoomType rt_Double = roomTypeService.findByName("Double");
            RoomType rt_Triple = roomTypeService.findByName("Triple");
            RoomType rt_Deluxe = roomTypeService.findByName("Deluxe");

            HotelRoom hr1 = new HotelRoom(5, 1, 120.99f, 10.99f,
                            "hr1: This is a HotelRoom of type: Single with noRooms:5",
                            "Don't break the ice machine.", rt_Single, new HashSet<>(Set.of(a_IM, a_Wifi, a_AC)), 
                            "05/03/25", "07/05/25");
            HotelRoom hr2 = new HotelRoom(3, 2, 200.99f, 11.99f,
                            "hr2: This is a HotelRoom of type: Double with noRooms:3",
                            "Please enjoy your stay!", rt_Double, new HashSet<>(Set.of(a_IM, a_Wifi, a_AC)),
                            "02/01/25", "03/10/25");
            HotelRoom hr3 = new HotelRoom(3, 3, 415.99f, 15.99f,
                            "hr3: This is a HotelRoom of type: triple with noRooms:3",
                            "No ice machine, sorry!", rt_Triple, new HashSet<>(Set.of(a_Wifi, a_AC)),
                            "05/01/25", "06/10/25");
            HotelRoom hr4 = new HotelRoom(4, 6, 512.99f, 20.99f,
                            "hr4: This is a HotelRoom of type: Deluxe with noRooms:4",
                            "No parties allowed.", rt_Deluxe, new HashSet<>(Set.of(a_Wifi)), 
                            "06/10/25", "07/20/25");
            HotelRoom hr5 = new HotelRoom(12, 1, 65.99f, 0.00f,
                            "hr5: This is a HotelRoom of type: Single with noRooms:12, no Wifi",
                            "No wifi, use personal data plan.", rt_Single, new HashSet<>(Set.of(a_IM, a_AC)), 
                            "09/20/25", "10/31/25");
            HotelRoom hr6 = new HotelRoom(4, 2, 90.99f, 0.00f,
                            "hr6: This is a HotelRoom of type: Double with noRooms:4, no Wifi",
                            "Wifi is down, sorry!", rt_Double, new HashSet<>(Set.of(a_AC, a_Coffee)), 
                            "04/05/25", "05/15/25");
            HotelRoom hr7 = new HotelRoom(6, 6, 130.50f, 5.00f,
                            "hr7: This is a HotelRoom of type: Deluxe with noRooms:6",
                            "Check-in after 3 PM. No pets allowed.", rt_Deluxe,
                            new HashSet<>(Set.of(a_Wifi, a_IM, a_Kitchen, a_Fridge, a_Microwave)),
                            "10/20/25", "11/10/25");
            HotelRoom hr8 = new HotelRoom(2, 3, 185.75f, 20.00f,
                            "hr8: This is a HotelRoom of type: Triple with noRooms:2",
                            "Complimentary breakfast included. Quiet hours after 10 PM.", rt_Triple,
                            new HashSet<>(Set.of(a_Wifi, a_AC, a_IM, a_Microwave)),
                            "08/05/25", "09/01/25");
            HotelRoom hr9 = new HotelRoom(1, 1, 299.99f, 0.00f,
                            "hr9: This is a HotelRoom of type: Triple with noRooms:1",
                            "No smoking allowed. Enjoy a peaceful stay!", rt_Triple,
                            new HashSet<>(Set.of(a_AC, a_Microwave)),
                            "11/30/25", "12/20/25");
            HotelRoom hr10 = new HotelRoom(5, 2, 145.00f, 10.00f,
                            "hr10: This is a HotelRoom of type: Double with noRooms:5",
                            "Please reuse towels to conserve water. Thank you!", rt_Double,
                            new HashSet<>(Set.of(a_Wifi, a_AC, a_Coffee)),
                            "08/15/25", "09/10/25");
            HotelRoom hr11 = new HotelRoom(7, 5, 450.99f, 7.50f,
                            "hr11: This is a HotelRoom of type: Deluxe with noRooms:7",
                            "Early checkout before 10 AM. No room service.", rt_Deluxe,
                            new HashSet<>(Set.of(a_IM, a_AC, a_Fridge)), 
                            "11/15/25", "12/05/25");
            HotelRoom hr12 = new HotelRoom(3, 3, 159.99f, 0.00f,
                            "hr12: This is a HotelRoom of type: Triple with noRooms:3",
                            "Guests are responsible for any damage. No party policy enforced.", rt_Triple,
                            new HashSet<>(Set.of(a_Wifi, a_AC, a_IM, a_Microwave, a_Coffee)),
                            "05/05/25", "05/30/25");
            HotelRoom hr13 = new HotelRoom(4, 2, 275.49f, 12.00f,
                            "hr13: This is a HotelRoom of type: Twin with noRooms:4",
                            "Late checkout available upon request. Pets allowed.", rt_Deluxe,
                            new HashSet<>(Set.of(a_Wifi, a_AC, a_Coffee)), 
                            "06/01/25", "06/20/25");
            HotelRoom hr14 = new HotelRoom(10, 2, 225.99f, 15.00f,
                            "hr14: This is a HotelRoom of type: Double with noRooms:10",
                            "Perfect for business travelers. Free high-speed Wifi.", rt_Double,
                            new HashSet<>(Set.of(a_Wifi, a_AC, a_IM)),
                            "06/05/25", "07/04/25");
            HotelRoom hr15 = new HotelRoom(6, 3, 345.99f, 18.00f,
                            "hr15: This is a HotelRoom of type: Triple with noRooms:6",
                            "Free minibar included. Please report any appliance issues to front desk.",
                            rt_Triple, new HashSet<>(Set.of(a_Wifi, a_AC, a_IM)), 
                            "08/15/25", "09/10/25");
            HotelRoom hr16 = new HotelRoom(3, 1, 105.50f, 0.00f,
                            "hr16: This is a HotelRoom of type: Single with noRooms:3",
                            "Quiet corner rooms. Ideal for solo travelers. No extra guests allowed.",
                            rt_Single, new HashSet<>(Set.of(a_AC, a_Microwave, a_Fridge)), 
                            "07/01/25", "07/18/25");
            HotelRoom hr17 = new HotelRoom(8, 6, 520.00f, 10.00f,
                            "hr17: This is a HotelRoom of type: Deluxe with noRooms:8",
                            "Housekeeping available upon request. No loud music after 9 PM.", rt_Deluxe,
                            new HashSet<>(Set.of(a_Wifi, a_AC, a_Kitchen, a_Fridge, a_Microwave)), 
                            "10/10/25", "11/15/25");
            HotelRoom hr18 = new HotelRoom(2, 2, 399.00f, 25.00f,
                            "hr18: This is a HotelRoom of type: Double with noRooms:2",
                            "Ocean view room. Extra blankets and pillows available upon request.",
                            rt_Double, new HashSet<>(Set.of(a_Wifi, a_AC, a_IM)), 
                            "11/20/25", "12/31/25");

            // Define HotelRoom Set(s) for Hotel(s)
            Set<HotelRoom> h1_Rooms = new HashSet<>(Set.of(hr1, hr2, hr3, hr4));
            Set<HotelRoom> h2_Rooms = new HashSet<>(Set.of(hr5, hr6, hr9, hr11));
            Set<HotelRoom> h3_Rooms = new HashSet<>(Set.of(hr7, hr8));
            Set<HotelRoom> h4_Rooms = new HashSet<>(Set.of(hr10, hr12));
            Set<HotelRoom> h5_Rooms = new HashSet<>(Set.of(hr13, hr14));
            Set<HotelRoom> h6_Rooms = new HashSet<>(Set.of(hr15, hr16));
            Set<HotelRoom> h7_Rooms = new HashSet<>(Set.of(hr17, hr18));

            // Define Amenities Set(s) for Hotel(s)
            Set<Amenities> h1_Amenities = new HashSet<>(Set.of(a_Gym, a_AirportS, a_BB, a_Parking));
            Set<Amenities> h2_Amenities = new HashSet<>(Set.of(a_Gym, a_Pool, a_Parking));
            Set<Amenities> h3_Amenities = new HashSet<>(Set.of(a_BB, a_AirportS));
            Set<Amenities> h4_Amenities = new HashSet<>(Set.of(a_Pool, a_BB, a_Parking));
            Set<Amenities> h5_Amenities = new HashSet<>(Set.of(a_AirportS, a_Parking));
            Set<Amenities> h6_Amenities = new HashSet<>(Set.of(a_AirportS, a_Parking, a_TourBus, a_Beach));
            Set<Amenities> h7_Amenities = new HashSet<>(Set.of(a_AmuseParkS, a_Parking, a_TourBus));

            // Define image URL's here
            String h1Image = "https://tse3.mm.bing.net/th/id/OIP.VUmb5Ysfn48EwDcnWj1MSQHaEK?pid=Api";
            String h2Image = "https://tse4.mm.bing.net/th?id=OIP.VfLRj74cA8rNFzgtuHuOHgHaFj&pid=Api";
            String h3Image = "https://tse4.mm.bing.net/th?id=OIP.6ugNGepyr6j-k_uXBKwuPAHaHa&pid=Api";
            String h4Image = "https://tse2.mm.bing.net/th?id=OIP.Gg2bhfK0zfd0w0mkYExzMQHaFj&pid=Api";
            String h5Image = "https://tse3.mm.bing.net/th?id=OIP.T6dtY18pTEYdjJ0Mt-scIwHaE4&r=0&pid=Api";
            String h6Image = "https://cache.marriott.com/marriottassets/marriott/HNLMC/hnlmc-exterior-4350-hor-feat.jpg";
            String h7Image = "https://tse2.mm.bing.net/th?id=OIP.vF7fqw4_BbkELIeJ3dKyewHaE8&r=0&pid=Api";

            // Define Hotel(s) to save
            Hotel h1 = new Hotel("Amazing Hotel", "1234 Atlas Dr.", "New York City", "New York",
                            5, calculateAveragePrice(h1_Rooms), calculateAverageDiscount(h1_Rooms),
                            "This is an amazing hotel!", "amazinghotel@ABChotel.com", 
                            "(800) 123-4567", 12, h1_Amenities, h1Image);
            Hotel h2 = new Hotel("Beautiful Hotel", "134 Bodega Dr.", "Seattle", "Washington",
                            4, calculateAveragePrice(h2_Rooms), calculateAverageDiscount(h2_Rooms),
                            "This is a beautiful hotel!", "beautifulhotel@ABChotel.com", 
                            "(800) 888-5555", 8, h2_Amenities, h2Image);
            Hotel h3 = new Hotel("Cool Hotel", "23 Calico Ave.", "Orange Grove", "California",
                            4, calculateAveragePrice(h3_Rooms), calculateAverageDiscount(h3_Rooms),
                            "This is a cool hotel!", "coolhotel@ABChotel.com", 
                            "(800) 261-4455", 9, h3_Amenities, h3Image);
            Hotel h4 = new Hotel("Deluxe Hotel", "56 Devonshire St.", "Boston", "Massachusetts",
                            2, calculateAveragePrice(h4_Rooms), calculateAverageDiscount(h4_Rooms),
                            "This is a deluxe hotel!", "deluxehotel@ABChotel.com",
                            "(800) 299-7431", 21, h4_Amenities, h4Image);
            Hotel h5 = new Hotel("Exciting Hotel", "13 Example St.", "Portland", "Oregon",
                            3, calculateAveragePrice(h5_Rooms), calculateAverageDiscount(h5_Rooms),
                            "This is an exciting hotel!", "excitinghotel@ABChotel.com", 
                            "(800) 944-5672", 27, h5_Amenities, h5Image);
            Hotel h6 = new Hotel("Hale Hotel", "555 Honu Rd.", "Honolulu", "Hawaii",
                            5, calculateAveragePrice(h6_Rooms), calculateAverageDiscount(h6_Rooms),
                            "This is hotel feels like home!", "halehotel@ABChotel.com", 
                            "(808) 349-7780", 43, h6_Amenities, h6Image);
            Hotel h7 = new Hotel("Fantastic Hotel", "13 Floral Rd.", "Orlando", "Florida",
                            3, calculateAveragePrice(h7_Rooms), calculateAverageDiscount(h7_Rooms),
                            "This is an fantastic hotel!", "excitinghotel@ABChotel.com",
                            "(800) 934-6739", 27, h7_Amenities, h7Image);

            // Assign the rooms to their Hotels
            h1_Rooms.forEach(h1::addRoom);
            h2_Rooms.forEach(h2::addRoom);
            h3_Rooms.forEach(h3::addRoom);
            h4_Rooms.forEach(h4::addRoom);
            h5_Rooms.forEach(h5::addRoom);
            h6_Rooms.forEach(h6::addRoom);
            h7_Rooms.forEach(h7::addRoom);

            // Save the Hotel objects (this will cascade and save HotelRooms as well)
            try {
                hotelService.save(h1);
                hotelService.save(h2);
                hotelService.save(h3);
                hotelService.save(h4);
                hotelService.save(h5);
                hotelService.save(h6);
                hotelService.save(h7);
            } catch (IOException err) {
                err.printStackTrace();
                System.out.println("Something went wrong while saving Hotel objects!");
            }
        }
    }

    // Helper functions //
    private double calculateAveragePrice(Set<HotelRoom> rooms) {
            return rooms.stream().mapToDouble(HotelRoom::getPrice).average().orElse(0.0);
    }

    private double calculateAverageDiscount(Set<HotelRoom> rooms) {
            return rooms.stream().mapToDouble(HotelRoom::getDiscount).average().orElse(0.0);
    }

}
