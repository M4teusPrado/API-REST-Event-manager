package eventoapp.services.functions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import eventoapp.dto.AdminDTO;
import eventoapp.dto.AdminGetDTO;
import eventoapp.models.Admin;
import eventoapp.repositories.AdminRepository;
import eventoapp.services.AdminService;

@Service
public class AdminServiceFunctions implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public List<AdminGetDTO> getAdmins() {
        List<Admin> admins = adminRepository.findAll();
        return toDTOList(admins);
    }

    @Override
    public AdminGetDTO getAdminById(Long id) {
        Optional<Admin> op = adminRepository.findById(id);
        Admin admin = op.orElseThrow( () -> new ResponseStatusException( 
            HttpStatus.NOT_FOUND, "Administrador não encontrado"));
        return new AdminGetDTO(admin);
    }

    @Override
    public void deleteAdmin(Long id) {
        getAdminById(id);
        adminRepository.deleteById(id);
    }

    @Override
    public Admin insertAdmin(AdminDTO adminDTO) {
        Admin admin = new Admin();
        adminDTOtoAdmin(admin, adminDTO);
        return adminRepository.save(admin);
    }

    @Override
    public AdminDTO updateEvent(Long id, AdminDTO adminDTO) {

        try {
            Admin admin = adminRepository.getOne(id);
            adminDTOtoAdmin(admin, adminDTO);
            admin = adminRepository.save(admin);
            return new AdminDTO(admin);
        } 
        catch(EntityNotFoundException ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Administrador não encontrado");
        }
    }

    @Override
    public void adminDTOtoAdmin(Admin admin, AdminDTO adminDTO) {
        admin.setName(adminDTO.getName());
        admin.setEmail(adminDTO.getEmail());
        admin.setPhoneNumber(adminDTO.getPhoneNumber());
    }

    @Override
    public List<AdminGetDTO> toDTOList(List<Admin> admins) {

        List<AdminGetDTO> adminsDTO = new ArrayList<AdminGetDTO>();

        for (Admin admin : admins) {
            AdminGetDTO adminDTO = new AdminGetDTO(admin);
            adminsDTO.add(adminDTO);
        }
        return adminsDTO;
    }
}
