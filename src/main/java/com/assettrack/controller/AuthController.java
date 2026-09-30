package com.assettrack.controller;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController @RequestMapping("/api/auth")
@CrossOrigin(origins="http://localhost:5173")
public class AuthController {
    @PostMapping("/login")
    public Map<String,String> login(@RequestBody Map<String,String> body){
        String u=body.getOrDefault("username","");
        String p=body.getOrDefault("password","");
        String role = switch(u.toLowerCase()){
            case "admin" -> "ADMIN";
            case "commander" -> "BASE_COMMANDER";
            case "logistics" -> "LOGISTICS_OFFICER";
            default -> "USER";
        };
        if ((u.equals("admin")&&p.equals("admin"))||(u.equals("commander")&&p.equals("commander"))||(u.equals("logistics")&&p.equals("logistics")))
            return Map.of("username",u,"role",role,"token","demo-token");
        throw new RuntimeException("Invalid credentials");
    }
}
