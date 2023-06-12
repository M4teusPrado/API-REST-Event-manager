package eventoapp.domain.services.functions;

import eventoapp.domain.dto.AdminDTO;
import eventoapp.domain.dto.AdminGetDTO;
import eventoapp.domain.entities.Admin;
import eventoapp.domain.entities.objectsValue.Email;
import eventoapp.domain.repositories.AdminRepository;
import eventoapp.domain.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdminServiceFunctions implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    public AdminServiceFunctions(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public Page<AdminGetDTO> getAdmins(PageRequest pageRequest) {
        Page<Admin> admins = adminRepository.findAdminPageable(pageRequest);
        return admins.map(admin -> new AdminGetDTO(admin));
    }

    @Override
    public AdminGetDTO getAdminDTOById(Long id) {
        try {
            Admin admin = adminRepository.findById(id).get();
            return new AdminGetDTO(admin);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Administradior não encontrado");
        }
    }

    @Override
    public Admin getAdminById(Long id) {
        try {
            return adminRepository.findById(id).get();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Administradior não encontrado");
        }
    }

    @Override
    public void deleteAdmin(Long id) {
        getAdminById(id);
        adminRepository.deleteById(id);
    }

    @Override
    public Admin insertAdmin(AdminDTO adminDTO) {
        Admin admin = new Admin();
        getAdminByEmail(adminDTO);
        adminDTOtoAdmin(admin, adminDTO);
        return adminRepository.save(admin);
    }

    private void getAdminByEmail(AdminDTO adminDTO) {
        List<Admin> admins = adminRepository.findAdminByEmail(adminDTO.getEmail());

        if (!admins.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "email ja cadastrado");
    }

    @Override
    public AdminDTO updateEvent(Long id, AdminDTO adminDTO) {

        try {
            getAdminByEmail(adminDTO);
            Admin admin = adminRepository.getOne(id);
            adminDTOtoAdmin(admin, adminDTO);
            admin = adminRepository.save(admin);
            return new AdminDTO(admin);
        } catch (EntityNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Administrador não encontrado");
        }
    }

    @Override
    public void adminDTOtoAdmin(Admin admin, AdminDTO adminDTO) {
        admin.setName(adminDTO.getName());
        admin.setEmail(new Email(adminDTO.getEmail()));
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
