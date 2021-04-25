package eventoapp.services.functions;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import eventoapp.dto.AdminDTO;
import eventoapp.models.Admin;
import eventoapp.repositories.AdminRepository;
import eventoapp.services.AdminService;

@Service
public class AdminServiceFunctions implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public Page<AdminDTO> getAdmins() {
        return null;
    }

    @Override
    public AdminDTO getAdminById(Long id) {
        
        Optional<Admin> op = adminRepository.findById(id);
        
        Admin admin = op.orElseThrow( () -> new ResponseStatusException( 
            HttpStatus.NOT_FOUND, "Administrador não encontrado"));
        return new AdminDTO(admin);
    }

    @Override
    public List<AdminDTO> toDTOList(List<Admin> admins) {

        return null;
    }

    @Override
    public void deleteAdmin(Long id) {
    }

    @Override
    public Admin insertAdmin(Admin admin) {
        return adminRepository.save(admin);
    }

    @Override
    public AdminDTO updateEvent(Long id, AdminDTO adminDTO) {
        return null;
    }

}
