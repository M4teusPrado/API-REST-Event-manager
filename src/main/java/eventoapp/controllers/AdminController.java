package eventoapp.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public Page<AdminGetDTO> getAdmins(
        @RequestParam(value = "page",           defaultValue = "0")                 Integer page,
        @RequestParam(value = "linesPerPage",   defaultValue = "6")                 Integer linesPerPage,
        @RequestParam(value = "direction",      defaultValue = "ASC")               String  direction,
        @RequestParam(value = "orderBy",        defaultValue = "id")                String  orderBy
    ){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
        Page<AdminGetDTO> listDTO = adminService.getAdmins(pageRequest);
        return listDTO;
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
