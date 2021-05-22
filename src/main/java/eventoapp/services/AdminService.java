package eventoapp.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import eventoapp.dto.AdminDTO;
import eventoapp.dto.AdminGetDTO;
import eventoapp.models.Admin;


public interface AdminService {
       
    public Page<AdminGetDTO> getAdmins(PageRequest pageRequest);

    public AdminGetDTO getAdminById(Long id);

    public List<AdminGetDTO> toDTOList(List<Admin> admins);

    public void deleteAdmin(Long id);

    public Admin insertAdmin(AdminDTO adminDTO);

    public AdminDTO updateEvent(Long id, AdminDTO adminDTO);

    public void adminDTOtoAdmin(Admin admin, AdminDTO adminDTO);

}