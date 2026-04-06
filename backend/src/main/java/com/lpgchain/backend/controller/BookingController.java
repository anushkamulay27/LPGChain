package com.lpgchain.backend.controller;

import com.lpgchain.backend.model.Booking;
import com.lpgchain.backend.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "*")
public class BookingController {

    @Autowired
    private BookingRepository bookingRepository;

    @GetMapping("/areas")
    public ResponseEntity<List<Map<String, String>>> getAreas() {
        List<Map<String, String>> areas = new ArrayList<>();
        String[] areaNames = {"Khadki", "Fursungi", "Kirkee", "Vishrantwadi", "Parvati Hill",
                "Lohegaon", "Mundhwa", "Dighi", "Hadaspar", "Hinjewadi",
                "Aundh", "Paud", "Wakad", "Kothrud", "Katraj", "Kondhwa",
                "Bibwewadi", "Baner", "Viman Nagar", "Kharadi"};
        for (String area : areaNames) {
            Map<String, String> areaMap = new HashMap<>();
            areaMap.put("name", area);
            areas.add(areaMap);
        }
        return ResponseEntity.ok(areas);
    }

    @GetMapping("/cylinder-types")
    public ResponseEntity<List<Map<String, String>>> getCylinderTypes() {
        List<Map<String, String>> types = new ArrayList<>();
        String[] cylTypes = {"5kg FTL (Domestic)", "14.2kg Domestic", "19kg Commercial"};
        for (String type : cylTypes) {
            Map<String, String> typeMap = new HashMap<>();
            typeMap.put("name", type);
            types.add(typeMap);
        }
        return ResponseEntity.ok(types);
    }

    @GetMapping("/distributor/{area}")
    public ResponseEntity<Map<String, String>> getDistributorForArea(@PathVariable String area) {
        Map<String, String> distributor = new HashMap<>();
        Map<String, String> areaDistributorMap = new HashMap<>();
        areaDistributorMap.put("Khadki", "Indane - Khadki Petroleum Depot");
        areaDistributorMap.put("Fursungi", "Indane - Shri Siddhoba");
        areaDistributorMap.put("Kirkee", "Indane - Army");
        areaDistributorMap.put("Vishrantwadi", "Indane - Samyak");
        areaDistributorMap.put("Parvati Hill", "Indane - Urja");
        areaDistributorMap.put("Lohegaon", "Indane - Eternity");
        areaDistributorMap.put("Mundhwa", "Indane - Shahid Pradip");
        areaDistributorMap.put("Dighi", "Indane - Shree Sai Sayaji");
        areaDistributorMap.put("Hadaspar", "Indane - Vishwa");
        areaDistributorMap.put("Hinjewadi", "Indane - Rajnandini");
        areaDistributorMap.put("Aundh", "Indane - Infantry");
        areaDistributorMap.put("Paud", "Indane - Aapulki Gramin Vitarak");
        areaDistributorMap.put("Wakad", "Indane - Sai Krupa");
        areaDistributorMap.put("Kothrud", "Indane - Mahalaxmi");
        areaDistributorMap.put("Katraj", "Indane - Shivam");
        areaDistributorMap.put("Kondhwa", "Indane - Ganesh");
        areaDistributorMap.put("Bibwewadi", "Indane - Vitthal");
        areaDistributorMap.put("Baner", "Indane - Omkar");
        areaDistributorMap.put("Viman Nagar", "Indane - Shubham");
        areaDistributorMap.put("Kharadi", "Indane - Kharadi Distributor");
        areaDistributorMap.put("Pimpri", "Indane - Pimpri Distributor");
        String distributorName = areaDistributorMap.getOrDefault(area, "Not Found");
        distributor.put("name", distributorName);
        distributor.put("area", area);
        return ResponseEntity.ok(distributor);
    }

    @PostMapping("/create")
    public ResponseEntity<Booking> createBooking(@RequestBody Booking booking) {
        Booking saved = bookingRepository.save(booking);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/all")
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Long id) {
        return bookingRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/customer/{email}")
    public List<Booking> getBookingsByEmail(@PathVariable String email) {
        return bookingRepository.findByCustomerEmail(email);
    }

    @PutMapping("/update-status/{id}")
    public String updateBookingStatus(@PathVariable Long id, @RequestParam String status) {
        Optional<Booking> bookingOpt = bookingRepository.findById(id);
        if (bookingOpt.isPresent()) {
            Booking booking = bookingOpt.get();
            booking.setStatus(status);
            bookingRepository.save(booking);
            return "Status updated to: " + status;
        }
        return "Booking not found!";
    }
}