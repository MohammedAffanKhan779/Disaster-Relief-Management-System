package com.disaster.management.config;

import com.disaster.management.model.entity.*;
import com.disaster.management.model.enums.*;
import com.disaster.management.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * DataLoader - Seeds the database with sample data for testing
 * This runs automatically when the application starts
 */
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private AdministratorRepository administratorRepository;
    
    @Autowired
    private DonorRepository donorRepository;
    
    @Autowired
    private VolunteerRepository volunteerRepository;
    
    @Autowired
    private ReliefStaffRepository reliefStaffRepository;
    
    @Autowired
    private AuthorityRepository authorityRepository;
    
    @Autowired
    private DisasterEventRepository disasterEventRepository;
    
    @Autowired
    private ReliefCenterRepository reliefCenterRepository;
    
    @Autowired
    private ResourceRepository resourceRepository;
    
    @Autowired
    private ResourceRequestRepository resourceRequestRepository;
    
    @Autowired
    private AllocationRepository allocationRepository;
    
    @Autowired
    private DonationRepository donationRepository;
    
    @Autowired
    private ReportRepository reportRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // Check if data already exists
        if (userRepository.count() > 0) {
            System.out.println("Database already contains data. Skipping seed.");
            return;
        }

        System.out.println("🌱 Starting database seeding...");

        // 1. Create Administrators
        List<Administrator> administrators = createAdministrators();
        System.out.println("✅ Created " + administrators.size() + " administrators");

        // 2. Create Donors
        List<Donor> donors = createDonors();
        System.out.println("✅ Created " + donors.size() + " donors");

        // 3. Create Volunteers
        List<Volunteer> volunteers = createVolunteers();
        System.out.println("✅ Created " + volunteers.size() + " volunteers");

        // 4. Create Authorities
        List<Authority> authorities = createAuthorities();
        System.out.println("✅ Created " + authorities.size() + " authorities");

        // 5. Create Disaster Events
        List<DisasterEvent> disasters = createDisasterEvents();
        System.out.println("✅ Created " + disasters.size() + " disaster events");

        // 6. Create Relief Centers (linked to disasters)
        List<ReliefCenter> centers = createReliefCenters(disasters);
        System.out.println("✅ Created " + centers.size() + " relief centers");

        // 7. Create Relief Staff (linked to centers)
        List<ReliefStaff> staff = createReliefStaff(centers);
        System.out.println("✅ Created " + staff.size() + " relief staff");

        // 8. Create Resources (linked to centers)
        List<Resource> resources = createResources(centers);
        System.out.println("✅ Created " + resources.size() + " resources");

        // 9. Create Donations (linked to donors and disasters)
        List<Donation> donations = createDonations(donors, disasters);
        System.out.println("✅ Created " + donations.size() + " donations");

        // 10. Create Resource Requests (linked to centers, resources)
        List<ResourceRequest> requests = createResourceRequests(centers, resources);
        System.out.println("✅ Created " + requests.size() + " resource requests");

        // 11. Approve some requests and create allocations (linked to administrators)
        approveRequestsAndCreateAllocations(requests, administrators, centers, resources);
        System.out.println("✅ Approved requests and created allocations");

        // 12. Create Reports (linked to disasters and administrators)
        List<Report> reports = createReports(disasters, administrators);
        System.out.println("✅ Created " + reports.size() + " reports");

        System.out.println("🎉 Database seeding completed successfully!");
        System.out.println("\n📝 Test Credentials:");
        System.out.println("   Admin: admin@disaster.org / admin123");
        System.out.println("   Donor: john.smith@email.com / donor123");
        System.out.println("   Volunteer: sarah.wilson@email.com / volunteer123");
        System.out.println("   Relief Staff: mike.johnson@relief.org / staff123");
        System.out.println("   Authority: authority@gov.in / authority123");
    }

    private List<Administrator> createAdministrators() {
        List<Administrator> admins = new ArrayList<>();
        
        Administrator admin1 = new Administrator();
        admin1.setName("Admin User");
        admin1.setEmail("admin@disaster.org");
        admin1.setPassword("admin123");
        admin1.setPhone("9876543210");
        admin1.setRole(UserRole.ADMIN);
        admins.add(administratorRepository.save(admin1));

        Administrator admin2 = new Administrator();
        admin2.setName("System Administrator");
        admin2.setEmail("sysadmin@disaster.org");
        admin2.setPassword("admin456");
        admin2.setPhone("9876543211");
        admin2.setRole(UserRole.ADMIN);
        admins.add(administratorRepository.save(admin2));

        return admins;
    }

    private List<Donor> createDonors() {
        List<Donor> donors = new ArrayList<>();
        
        Donor donor1 = new Donor();
        donor1.setName("John Smith");
        donor1.setEmail("john.smith@email.com");
        donor1.setPassword("donor123");
        donor1.setPhone("9123456789");
        donor1.setRole(UserRole.DONOR);
        donor1.setDonorType("Individual");
        donors.add(donorRepository.save(donor1));

        Donor donor2 = new Donor();
        donor2.setName("Tech Corp Foundation");
        donor2.setEmail("foundation@techcorp.com");
        donor2.setPassword("donor456");
        donor2.setPhone("9123456790");
        donor2.setRole(UserRole.DONOR);
        donor2.setDonorType("Corporate");
        donors.add(donorRepository.save(donor2));

        Donor donor3 = new Donor();
        donor3.setName("Community Charity");
        donor3.setEmail("info@communitycharity.org");
        donor3.setPassword("donor789");
        donor3.setPhone("9123456791");
        donor3.setRole(UserRole.DONOR);
        donor3.setDonorType("Organization");
        donors.add(donorRepository.save(donor3));

        return donors;
    }

    private List<Volunteer> createVolunteers() {
        List<Volunteer> volunteers = new ArrayList<>();
        
        Volunteer volunteer1 = new Volunteer();
        volunteer1.setName("Sarah Wilson");
        volunteer1.setEmail("sarah.wilson@email.com");
        volunteer1.setPassword("volunteer123");
        volunteer1.setPhone("9234567890");
        volunteer1.setRole(UserRole.VOLUNTEER);
        volunteer1.setSkillSet("Medical Aid, First Aid, Emergency Response");
        volunteer1.setAvailability(AvailabilityStatus.AVAILABLE);
        volunteers.add(volunteerRepository.save(volunteer1));

        Volunteer volunteer2 = new Volunteer();
        volunteer2.setName("David Kumar");
        volunteer2.setEmail("david.kumar@email.com");
        volunteer2.setPassword("volunteer456");
        volunteer2.setPhone("9234567891");
        volunteer2.setRole(UserRole.VOLUNTEER);
        volunteer2.setSkillSet("Food Distribution, Logistics");
        volunteer2.setAvailability(AvailabilityStatus.AVAILABLE);
        volunteers.add(volunteerRepository.save(volunteer2));

        Volunteer volunteer3 = new Volunteer();
        volunteer3.setName("Lisa Chen");
        volunteer3.setEmail("lisa.chen@email.com");
        volunteer3.setPassword("volunteer789");
        volunteer3.setPhone("9234567892");
        volunteer3.setRole(UserRole.VOLUNTEER);
        volunteer3.setSkillSet("Counseling, Child Care");
        volunteer3.setAvailability(AvailabilityStatus.BUSY);
        volunteers.add(volunteerRepository.save(volunteer3));

        return volunteers;
    }

    private List<Authority> createAuthorities() {
        List<Authority> authorities = new ArrayList<>();
        
        Authority authority1 = new Authority();
        authority1.setName("Government Officer");
        authority1.setEmail("authority@gov.in");
        authority1.setPassword("authority123");
        authority1.setPhone("9345678901");
        authority1.setRole(UserRole.AUTHORITY);
        authority1.setOrganization("National Disaster Management Authority");
        authority1.setDesignation("Senior Officer");
        authorities.add(authorityRepository.save(authority1));

        Authority authority2 = new Authority();
        authority2.setName("State Inspector");
        authority2.setEmail("inspector@state.gov.in");
        authority2.setPassword("authority456");
        authority2.setPhone("9345678902");
        authority2.setRole(UserRole.AUTHORITY);
        authority2.setOrganization("State Emergency Services");
        authority2.setDesignation("Inspector General");
        authorities.add(authorityRepository.save(authority2));

        return authorities;
    }

    private List<DisasterEvent> createDisasterEvents() {
        List<DisasterEvent> disasters = new ArrayList<>();
        
        DisasterEvent disaster1 = new DisasterEvent();
        disaster1.setType(DisasterType.FLOOD);
        disaster1.setSeverity(SeverityLevel.HIGH);
        disaster1.setStatus(DisasterStatus.ACTIVE);
        disaster1.setLocation("Mumbai, Maharashtra");
        disaster1.setDate(LocalDate.now().minusDays(5));
        disaster1.setDescription("Heavy rainfall causing severe flooding in urban areas");
        disasters.add(disasterEventRepository.save(disaster1));

        DisasterEvent disaster2 = new DisasterEvent();
        disaster2.setType(DisasterType.EARTHQUAKE);
        disaster2.setSeverity(SeverityLevel.CRITICAL);
        disaster2.setStatus(DisasterStatus.UNDER_CONTROL);
        disaster2.setLocation("Delhi NCR");
        disaster2.setDate(LocalDate.now().minusDays(10));
        disaster2.setDescription("6.2 magnitude earthquake with aftershocks");
        disasters.add(disasterEventRepository.save(disaster2));

        DisasterEvent disaster3 = new DisasterEvent();
        disaster3.setType(DisasterType.CYCLONE);
        disaster3.setSeverity(SeverityLevel.MEDIUM);
        disaster3.setStatus(DisasterStatus.RESOLVED);
        disaster3.setLocation("Visakhapatnam, Andhra Pradesh");
        disaster3.setDate(LocalDate.now().minusDays(20));
        disaster3.setDescription("Cyclone with wind speeds up to 100 km/h");
        disasters.add(disasterEventRepository.save(disaster3));

        DisasterEvent disaster4 = new DisasterEvent();
        disaster4.setType(DisasterType.FIRE);
        disaster4.setSeverity(SeverityLevel.LOW);
        disaster4.setStatus(DisasterStatus.CLOSED);
        disaster4.setLocation("Bangalore, Karnataka");
        disaster4.setDate(LocalDate.now().minusDays(30));
        disaster4.setDescription("Forest fire contained with minimal damage");
        disasters.add(disasterEventRepository.save(disaster4));

        return disasters;
    }

    private List<ReliefCenter> createReliefCenters(List<DisasterEvent> disasters) {
        List<ReliefCenter> centers = new ArrayList<>();
        
        ReliefCenter center1 = new ReliefCenter();
        center1.setName("Mumbai Central Relief Camp");
        center1.setLocation("Bandra West, Mumbai");
        center1.setCapacity(500);
        center1.setDescription("Primary relief center for flood victims");
        center1.setDisasterEvent(disasters.get(0));
        centers.add(reliefCenterRepository.save(center1));

        ReliefCenter center2 = new ReliefCenter();
        center2.setName("Andheri Relief Station");
        center2.setLocation("Andheri East, Mumbai");
        center2.setCapacity(300);
        center2.setDescription("Secondary relief center with medical facilities");
        center2.setDisasterEvent(disasters.get(0));
        centers.add(reliefCenterRepository.save(center2));

        ReliefCenter center3 = new ReliefCenter();
        center3.setName("Delhi Emergency Center");
        center3.setLocation("Connaught Place, Delhi");
        center3.setCapacity(800);
        center3.setDescription("Main coordination center for earthquake relief");
        center3.setDisasterEvent(disasters.get(1));
        centers.add(reliefCenterRepository.save(center3));

        ReliefCenter center4 = new ReliefCenter();
        center4.setName("Visakhapatnam Cyclone Shelter");
        center4.setLocation("Beach Road, Visakhapatnam");
        center4.setCapacity(400);
        center4.setDescription("Cyclone relief and rehabilitation center");
        center4.setDisasterEvent(disasters.get(2));
        centers.add(reliefCenterRepository.save(center4));

        return centers;
    }

    private List<ReliefStaff> createReliefStaff(List<ReliefCenter> centers) {
        List<ReliefStaff> staff = new ArrayList<>();
        
        ReliefStaff staff1 = new ReliefStaff();
        staff1.setName("Mike Johnson");
        staff1.setEmail("mike.johnson@relief.org");
        staff1.setPassword("staff123");
        staff1.setPhone("9456789012");
        staff1.setRole(UserRole.RELIEF_STAFF);
        staff1.setDesignation("Center Manager");
        staff1.setReliefCenter(centers.get(0));
        staff.add(reliefStaffRepository.save(staff1));

        ReliefStaff staff2 = new ReliefStaff();
        staff2.setName("Priya Sharma");
        staff2.setEmail("priya.sharma@relief.org");
        staff2.setPassword("staff456");
        staff2.setPhone("9456789013");
        staff2.setRole(UserRole.RELIEF_STAFF);
        staff2.setDesignation("Medical Coordinator");
        staff2.setReliefCenter(centers.get(0));
        staff.add(reliefStaffRepository.save(staff2));

        ReliefStaff staff3 = new ReliefStaff();
        staff3.setName("Rajesh Patel");
        staff3.setEmail("rajesh.patel@relief.org");
        staff3.setPassword("staff789");
        staff3.setPhone("9456789014");
        staff3.setRole(UserRole.RELIEF_STAFF);
        staff3.setDesignation("Logistics Head");
        staff3.setReliefCenter(centers.get(2));
        staff.add(reliefStaffRepository.save(staff3));

        ReliefStaff staff4 = new ReliefStaff();
        staff4.setName("Anita Reddy");
        staff4.setEmail("anita.reddy@relief.org");
        staff4.setPassword("staff101");
        staff4.setPhone("9456789015");
        staff4.setRole(UserRole.RELIEF_STAFF);
        staff4.setDesignation("Supply Manager");
        staff4.setReliefCenter(centers.get(1));
        staff.add(reliefStaffRepository.save(staff4));

        return staff;
    }

    private List<Resource> createResources(List<ReliefCenter> centers) {
        List<Resource> resources = new ArrayList<>();
        
        // Resources for Center 1 (Mumbai Central)
        resources.add(createResource("Drinking Water", 5000, DonationType.WATER, centers.get(0), "Bottled water supply"));
        resources.add(createResource("Rice Bags", 300, DonationType.FOOD, centers.get(0), "50kg rice bags"));
        resources.add(createResource("First Aid Kits", 100, DonationType.MEDICINE, centers.get(0), "Complete first aid supplies"));
        resources.add(createResource("Blankets", 500, DonationType.CLOTHES, centers.get(0), "Winter blankets"));

        // Resources for Center 2 (Andheri)
        resources.add(createResource("Medical Supplies", 200, DonationType.MEDICINE, centers.get(1), "Essential medicines"));
        resources.add(createResource("Tents", 50, DonationType.EQUIPMENT, centers.get(1), "Temporary shelter tents"));
        resources.add(createResource("Clothing", 400, DonationType.CLOTHES, centers.get(1), "Mixed clothing items"));

        // Resources for Center 3 (Delhi)
        resources.add(createResource("Emergency Kits", 150, DonationType.EQUIPMENT, centers.get(2), "Earthquake emergency kits"));
        resources.add(createResource("Food Packets", 1000, DonationType.FOOD, centers.get(2), "Ready-to-eat meals"));
        resources.add(createResource("Water Purifiers", 30, DonationType.EQUIPMENT, centers.get(2), "Portable water purifiers"));

        // Resources for Center 4 (Visakhapatnam)
        resources.add(createResource("Generators", 10, DonationType.EQUIPMENT, centers.get(3), "Backup power generators"));
        resources.add(createResource("Canned Food", 800, DonationType.FOOD, centers.get(3), "Canned food supplies"));

        return resources;
    }

    private Resource createResource(String name, int quantity, DonationType type, ReliefCenter center, String description) {
        Resource resource = new Resource();
        resource.setName(name);
        resource.setQuantity(quantity);
        resource.setType(type);
        resource.setReliefCenter(center);
        resource.setDescription(description);
        return resourceRepository.save(resource);
    }

    private List<Donation> createDonations(List<Donor> donors, List<DisasterEvent> disasters) {
        List<Donation> donations = new ArrayList<>();
        
        // Donations from Individual Donor
        Donation donation1 = new Donation();
        donation1.setType(DonationType.MONEY);
        donation1.setAmount(50000.0);
        donation1.setPaymentMethod(PaymentMethod.UPI);
        donation1.setDate(LocalDate.now().minusDays(4));
        donation1.setDonor(donors.get(0));
        donation1.setDisasterEvent(disasters.get(0));
        donation1.setDescription("Emergency flood relief donation");
        donation1.setReceiptNumber("RCP-" + System.currentTimeMillis());
        donations.add(donationRepository.save(donation1));

        // Donations from Corporate Donor
        Donation donation2 = new Donation();
        donation2.setType(DonationType.MONEY);
        donation2.setAmount(500000.0);
        donation2.setPaymentMethod(PaymentMethod.NET_BANKING);
        donation2.setDate(LocalDate.now().minusDays(8));
        donation2.setDonor(donors.get(1));
        donation2.setDisasterEvent(disasters.get(1));
        donation2.setDescription("Corporate CSR donation for earthquake relief");
        donation2.setReceiptNumber("RCP-" + (System.currentTimeMillis() + 1));
        donations.add(donationRepository.save(donation2));

        Donation donation3 = new Donation();
        donation3.setType(DonationType.EQUIPMENT);
        donation3.setAmount(200000.0);
        donation3.setPaymentMethod(PaymentMethod.CARD);
        donation3.setDate(LocalDate.now().minusDays(12));
        donation3.setDonor(donors.get(1));
        donation3.setDisasterEvent(disasters.get(1));
        donation3.setDescription("Medical equipment donation");
        donation3.setReceiptNumber("RCP-" + (System.currentTimeMillis() + 2));
        donations.add(donationRepository.save(donation3));

        // Donations from Organization
        Donation donation4 = new Donation();
        donation4.setType(DonationType.FOOD);
        donation4.setAmount(75000.0);
        donation4.setPaymentMethod(PaymentMethod.CASH);
        donation4.setDate(LocalDate.now().minusDays(15));
        donation4.setDonor(donors.get(2));
        donation4.setDisasterEvent(disasters.get(2));
        donation4.setDescription("Food supplies donation");
        donation4.setReceiptNumber("RCP-" + (System.currentTimeMillis() + 3));
        donations.add(donationRepository.save(donation4));

        Donation donation5 = new Donation();
        donation5.setType(DonationType.CLOTHES);
        donation5.setAmount(30000.0);
        donation5.setPaymentMethod(PaymentMethod.UPI);
        donation5.setDate(LocalDate.now().minusDays(6));
        donation5.setDonor(donors.get(0));
        donation5.setDisasterEvent(disasters.get(0));
        donation5.setDescription("Clothing donation for flood victims");
        donation5.setReceiptNumber("RCP-" + (System.currentTimeMillis() + 4));
        donations.add(donationRepository.save(donation5));

        return donations;
    }

    private List<ResourceRequest> createResourceRequests(List<ReliefCenter> centers, List<Resource> resources) {
        List<ResourceRequest> requests = new ArrayList<>();
        
        // Pending requests
        ResourceRequest request1 = new ResourceRequest();
        request1.setRequestDate(LocalDate.now().minusDays(2));
        request1.setStatus(RequestStatus.PENDING);
        request1.setReliefCenter(centers.get(0));
        request1.setResource(resources.get(0)); // Water
        request1.setRequestedQuantity(1000);
        request1.setDescription("Urgent need for additional drinking water");
        requests.add(resourceRequestRepository.save(request1));

        ResourceRequest request2 = new ResourceRequest();
        request2.setRequestDate(LocalDate.now().minusDays(1));
        request2.setStatus(RequestStatus.PENDING);
        request2.setReliefCenter(centers.get(1));
        request2.setResource(resources.get(4)); // Medical Supplies
        request2.setRequestedQuantity(50);
        request2.setDescription("Emergency medical supplies needed");
        requests.add(resourceRequestRepository.save(request2));

        // Approved requests (will be processed later)
        ResourceRequest request3 = new ResourceRequest();
        request3.setRequestDate(LocalDate.now().minusDays(5));
        request3.setStatus(RequestStatus.PENDING);
        request3.setReliefCenter(centers.get(2));
        request3.setResource(resources.get(7)); // Emergency Kits
        request3.setRequestedQuantity(50);
        request3.setDescription("Emergency kits for earthquake victims");
        requests.add(resourceRequestRepository.save(request3));

        ResourceRequest request4 = new ResourceRequest();
        request4.setRequestDate(LocalDate.now().minusDays(3));
        request4.setStatus(RequestStatus.PENDING);
        request4.setReliefCenter(centers.get(0));
        request4.setResource(resources.get(1)); // Rice
        request4.setRequestedQuantity(100);
        request4.setDescription("Food supplies running low");
        requests.add(resourceRequestRepository.save(request4));

        return requests;
    }

    private void approveRequestsAndCreateAllocations(List<ResourceRequest> requests, 
                                                     List<Administrator> administrators,
                                                     List<ReliefCenter> centers,
                                                     List<Resource> resources) {
        Administrator admin = administrators.get(0);
        
        // Approve and allocate request 3
        ResourceRequest request3 = requests.get(2);
        request3.setStatus(RequestStatus.APPROVED);
        request3.setApprovedBy(admin);
        resourceRequestRepository.save(request3);
        
        Allocation allocation1 = new Allocation();
        allocation1.setAllocationDate(LocalDate.now().minusDays(4));
        allocation1.setStatus(AllocationStatus.DELIVERED);
        allocation1.setResource(request3.getResource());
        allocation1.setReliefCenter(request3.getReliefCenter());
        allocation1.setAllocatedQuantity(request3.getRequestedQuantity());
        allocation1.setResourceRequest(request3);
        allocation1.setNotes("Approved by " + admin.getName() + " - Priority delivery");
        allocationRepository.save(allocation1);

        // Approve and allocate request 4
        ResourceRequest request4 = requests.get(3);
        request4.setStatus(RequestStatus.APPROVED);
        request4.setApprovedBy(admin);
        resourceRequestRepository.save(request4);
        
        Allocation allocation2 = new Allocation();
        allocation2.setAllocationDate(LocalDate.now().minusDays(2));
        allocation2.setStatus(AllocationStatus.IN_TRANSIT);
        allocation2.setResource(request4.getResource());
        allocation2.setReliefCenter(request4.getReliefCenter());
        allocation2.setAllocatedQuantity(request4.getRequestedQuantity());
        allocation2.setResourceRequest(request4);
        allocation2.setNotes("Approved by " + admin.getName() + " - In transit");
        allocationRepository.save(allocation2);

        // Create some standalone allocations (not from requests)
        Allocation allocation3 = new Allocation();
        allocation3.setAllocationDate(LocalDate.now().minusDays(6));
        allocation3.setStatus(AllocationStatus.SCHEDULED);
        allocation3.setResource(resources.get(3)); // Blankets
        allocation3.setReliefCenter(centers.get(0));
        allocation3.setAllocatedQuantity(200);
        allocation3.setNotes("Scheduled allocation for winter supplies");
        allocationRepository.save(allocation3);
    }

    private List<Report> createReports(List<DisasterEvent> disasters, List<Administrator> administrators) {
        List<Report> reports = new ArrayList<>();
        
        Report report1 = new Report();
        report1.setReportType("Daily Status Report");
        report1.setGeneratedDate(LocalDate.now().minusDays(1));
        report1.setDisasterEvent(disasters.get(0));
        report1.setGeneratedBy(administrators.get(0));
        report1.setContent("Mumbai Flood Relief - Day 4 Report:\n\n" +
                "Total affected: 10,000+ people\n" +
                "Relief centers operational: 2\n" +
                "Resources distributed: Food (500 packets), Water (2000 bottles)\n" +
                "Medical cases attended: 150\n" +
                "Status: Situation improving, water levels receding.");
        report1.setFilePath("/reports/flood-mumbai-day4.pdf");
        reports.add(reportRepository.save(report1));

        Report report2 = new Report();
        report2.setReportType("Resource Inventory Report");
        report2.setGeneratedDate(LocalDate.now().minusDays(3));
        report2.setDisasterEvent(disasters.get(1));
        report2.setGeneratedBy(administrators.get(1));
        report2.setContent("Delhi Earthquake Relief - Resource Status:\n\n" +
                "Available Resources:\n" +
                "- Emergency Kits: 150\n" +
                "- Food Packets: 1000\n" +
                "- Water Purifiers: 30\n\n" +
                "Critical Needs: Medical supplies, temporary shelter");
        report2.setFilePath("/reports/earthquake-delhi-inventory.pdf");
        reports.add(reportRepository.save(report2));

        Report report3 = new Report();
        report3.setReportType("Donation Summary Report");
        report3.setGeneratedDate(LocalDate.now().minusDays(2));
        report3.setDisasterEvent(disasters.get(0));
        report3.setGeneratedBy(administrators.get(0));
        report3.setContent("Flood Relief Donation Summary:\n\n" +
                "Total Donations Received: ₹8,05,000\n" +
                "Number of Donors: 3\n" +
                "Breakdown:\n" +
                "- Money: ₹5,50,000\n" +
                "- Equipment: ₹2,00,000\n" +
                "- Clothes: ₹55,000");
        report3.setFilePath("/reports/flood-donations-summary.pdf");
        reports.add(reportRepository.save(report3));

        Report report4 = new Report();
        report4.setReportType("Final Assessment Report");
        report4.setGeneratedDate(LocalDate.now().minusDays(10));
        report4.setDisasterEvent(disasters.get(2));
        report4.setGeneratedBy(administrators.get(1));
        report4.setContent("Cyclone Visakhapatnam - Final Assessment:\n\n" +
                "Event Duration: 3 days\n" +
                "Total affected: 5,000 people\n" +
                "Relief operations completed successfully\n" +
                "All evacuees returned home\n" +
                "Damage assessment: Moderate\n" +
                "Status: RESOLVED");
        report4.setFilePath("/reports/cyclone-visakhapatnam-final.pdf");
        reports.add(reportRepository.save(report4));

        return reports;
    }
}
