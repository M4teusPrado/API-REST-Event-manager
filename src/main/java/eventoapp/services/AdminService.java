package eventoapp.services;

import java.util.List;

import eventoapp.dto.AdminDTO;
import eventoapp.models.Admin;


public interface AdminService {
       
    public List<AdminDTO> getAdmins();

    public AdminDTO getAdminById(Long id);

    public List<AdminDTO> toDTOList(List<Admin> admins);

    public void deleteAdmin(Long id);

    public Admin insertAdmin(AdminDTO adminDTO);

    public AdminDTO updateEvent(Long id, AdminDTO adminDTO);

    public void adminDTOtoAdmin(Admin admin, AdminDTO adminDTO);

}