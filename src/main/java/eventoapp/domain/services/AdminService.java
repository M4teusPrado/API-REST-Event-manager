package eventoapp.domain.services;

import eventoapp.domain.dto.AdminDTO;
import eventoapp.domain.dto.AdminGetDTO;
import eventoapp.domain.entities.Admin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;


public interface AdminService {

    Page<AdminGetDTO> getAdmins(PageRequest pageRequest);

    AdminGetDTO getAdminDTOById(Long id);

    Admin getAdminById(Long id);

    List<AdminGetDTO> toDTOList(List<Admin> admins);

    void deleteAdmin(Long id);

    Admin insertAdmin(AdminDTO adminDTO);

    AdminDTO updateEvent(Long id, AdminDTO adminDTO);

    void adminDTOtoAdmin(Admin admin, AdminDTO adminDTO);

}