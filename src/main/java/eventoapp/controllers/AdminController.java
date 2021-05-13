package eventoapp.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import eventoapp.dto.AdminDTO;
import eventoapp.dto.AdminGetDTO;
import eventoapp.models.Admin;
import eventoapp.services.AdminService;

@RestController
@RequestMapping("/admins")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping()
    public List<AdminGetDTO> getAdmins() {
        return adminService.getAdmins();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdminGetDTO> getAdminById(@PathVariable Long id ) {
        return ResponseEntity.ok(adminService.getAdminById(id));
    }

    @PostMapping()
    public ResponseEntity<Admin> insertAdmin(@RequestBody AdminDTO adminDTO)
    {
        Admin aux = adminService.insertAdmin(adminDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(aux.getId()).toUri();
        return ResponseEntity.created(uri).body(aux);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable Long id) {
        adminService.deleteAdmin(id);
        return ResponseEntity.noContent().build();
    }
    
    @PutMapping("{id}") 
    public ResponseEntity<AdminDTO> updateAdmin(@PathVariable Long id, @RequestBody AdminDTO adminDTO)
    {
        AdminDTO dto = adminService.updateEvent(id, adminDTO);
		return ResponseEntity.ok().body(dto);
    }
}
