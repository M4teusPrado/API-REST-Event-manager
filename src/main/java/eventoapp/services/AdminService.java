package eventoapp.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import eventoapp.dto.AdminDTO;
import eventoapp.models.Admin;

@Service
public interface AdminService {
       
    public Page<AdminDTO> getAdmins();

    public AdminDTO getAdminById(Long id);

    public List<AdminDTO> toDTOList(List<Admin> admins);

    public void deleteAdmin(Long id);

    public Admin insertAdmin(Admin admin);

    public AdminDTO updateEvent(Long id, AdminDTO adminDTO);
}