package eventoapp.adminTest;

import eventoapp.domain.dto.AdminDTO;
import eventoapp.domain.dto.AdminGetDTO;
import eventoapp.domain.entities.Admin;
import eventoapp.domain.entities.objectsValue.Email;
import eventoapp.domain.repositories.AdminRepository;
import eventoapp.domain.services.AdminService;
import eventoapp.domain.services.functions.AdminServiceFunctions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AdminServiceTest {

    @Mock
    private AdminRepository adminRepository;

    private AdminService adminService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.adminService = new AdminServiceFunctions(adminRepository);
    }

    private Admin buildAdmin() {
        Long adminId = 1L;
        String adminName = "John Doe";
        Email adminEmail = new Email("johndoe@example.com");
        String adminPhoneNumber = "1234567890";

        return new Admin(adminId, adminName, adminEmail, adminPhoneNumber);
    }

//    @Test
//    public void testGetAdmins() {
//        PageRequest pageRequest = PageRequest.of(0, 10);
//        List<Admin> admins = new ArrayList<>();
//        admins.add(buildAdmin());
//
//        when(adminRepository.findAdminPageable(pageRequest)).thenReturn(admins);
//
//        Page<AdminGetDTO> adminGetDTOPage = adminService.getAdmins(pageRequest);
//
//        assertNotNull(adminGetDTOPage);
//        assertEquals(1, adminGetDTOPage.getTotalElements());
//        assertEquals(1, adminGetDTOPage.getContent().size());
//    }

    @Test
    public void testGetAdminDTOById() {
        Long adminId = 1L;
        Admin admin = buildAdmin();
        admin.setId(adminId);

        when(adminRepository.findById(adminId)).thenReturn(Optional.of(admin));

        AdminGetDTO adminGetDTO = adminService.getAdminDTOById(adminId);

        assertNotNull(adminGetDTO);
        assertEquals(adminId, adminGetDTO.getId());
        assertEquals(admin.getName(), adminGetDTO.getName());
        assertEquals(admin.getEmail().getEmail(), adminGetDTO.getEmail());
        assertEquals(admin.getPhoneNumber(), adminGetDTO.getPhoneNumber());
    }

    @Test
    public void testGetAdminDTOByIdNotFound() {
        Long adminId = 1L;

        when(adminRepository.findById(adminId)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> adminService.getAdminDTOById(adminId));
    }

    @Test
    public void testDeleteAdmin() {
        Long adminId = 1L;
        Admin admin = buildAdmin();
        admin.setId(adminId);

        when(adminRepository.findById(adminId)).thenReturn(Optional.of(admin));

        adminService.deleteAdmin(adminId);

        verify(adminRepository, times(1)).deleteById(adminId);
    }

    @Test
    public void testDeleteAdminNotFound() {
        Long adminId = 1L;

        when(adminRepository.findById(adminId)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> adminService.deleteAdmin(adminId));

        verify(adminRepository, never()).deleteById(adminId);
    }



    @Test
    public void testInsertAdmin() {
        // Arrange
        AdminDTO adminDTO = new AdminDTO();
        adminDTO.setName("John Doe");
        adminDTO.setEmail("johndoe@example.com");
        adminDTO.setPhoneNumber("1234567890");

        // Arrange
        Admin admin = new Admin();
        admin.setName("John Doe");
        admin.setEmail(new Email("johndoe@example.com"));
        admin.setPhoneNumber("1234567890");


        when(adminRepository.findAdminByEmail(adminDTO.getEmail())).thenReturn(new ArrayList<>());
        when(adminRepository.save(any(Admin.class))).thenReturn(admin);


        // Act
        Admin result = adminService.insertAdmin(adminDTO);

        // Assert
        assertNotNull(result);
        assertEquals(adminDTO.getName(), result.getName());
        assertEquals(adminDTO.getEmail(), result.getEmail().getEmail());
        assertEquals(adminDTO.getPhoneNumber(), result.getPhoneNumber());
        verify(adminRepository, times(1)).save(any(Admin.class));
    }


    @Test
    public void testInsertAdminWithExistingEmail() {
        // Arrange
        AdminDTO adminDTO = new AdminDTO();
        adminDTO.setName("John Doe");
        adminDTO.setEmail("johndoe@example.com");
        adminDTO.setPhoneNumber("1234567890");

        when(adminRepository.findAdminByEmail(adminDTO.getEmail())).thenReturn(Collections.singletonList(new Admin()));


        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> adminService.insertAdmin(adminDTO));
        verify(adminRepository, never()).save(any(Admin.class));
    }



    @Test
    public void testUpdateEvent() {
        // Arrange
        Long adminId = 1L;
        AdminDTO adminDTO = new AdminDTO();
        adminDTO.setName("John Doe");
        adminDTO.setEmail("johndoe@example.com");
        adminDTO.setPhoneNumber("1234567890");

        Admin admin = new Admin(adminId, "Old Name", new Email("oldemail@example.com"), "9876543210");

        when(adminRepository.getOne(adminId)).thenReturn(admin);
        when(adminRepository.save(any(Admin.class))).thenAnswer(invocation -> invocation.getArgument(0));


        // Act
        AdminDTO result = adminService.updateEvent(adminId, adminDTO);

        // Assert
        assertNotNull(result);
        assertEquals(adminDTO.getName(), result.getName());
        assertEquals(adminDTO.getEmail(), result.getEmail());
        assertEquals(adminDTO.getPhoneNumber(), result.getPhoneNumber());
        verify(adminRepository, times(1)).save(any(Admin.class));
    }


    @Test
    public void testUpdateEventWithNonExistingAdmin() {
        // Arrange
        Long adminId = 1L;
        AdminDTO adminDTO = new AdminDTO();
        adminDTO.setName("John Doe");
        adminDTO.setEmail("johndoe@example.com");
        adminDTO.setPhoneNumber("1234567890");

        when(adminRepository.getOne(adminId)).thenThrow(new EntityNotFoundException());

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> adminService.updateEvent(adminId, adminDTO));
        verify(adminRepository, never()).save(any(Admin.class));
    }
}