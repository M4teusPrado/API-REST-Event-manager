package eventoapp.services.Content;

import java.util.List;

import org.springframework.data.domain.Page;

import eventoapp.dto.AdminDTO;
import eventoapp.models.Admin;
import eventoapp.services.AdminService;

public class AdminServiceFunctions implements AdminService {

    @Override
    public Page<AdminDTO> getAdmins() {
        return null;
    }

    @Override
    public AdminDTO getAdminById(Long id) {
        return null;
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
        return null;
    }

    @Override
    public AdminDTO updateEvent(Long id, AdminDTO adminDTO) {
        return null;
    }

}
