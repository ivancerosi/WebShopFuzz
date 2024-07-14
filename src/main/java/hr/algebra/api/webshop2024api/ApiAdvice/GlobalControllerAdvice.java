package hr.algebra.webshop2024.Advice;

import hr.algebra.bl.webshop2024bl.Service.UserConnectionService;
import hr.algebra.dal.webshop2024dal.Entity.UserConnection;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalControllerAdvice {
    private final UserConnectionService userConnectionService;

    public GlobalControllerAdvice(UserConnectionService userConnectionService) {
        this.userConnectionService = userConnectionService;
    }

    @ModelAttribute("navbarPath")
    public String navbarPath(Principal principal, HttpServletRequest request) {
        if (principal != null){
            UserConnection connectedUser = new UserConnection();
            connectedUser.setUsername(principal.getName());
            connectedUser.setLastConnection(LocalDateTime.now());
            connectedUser.setIpAddress(request.getRemoteAddr());
            userConnectionService.save(connectedUser);
        }
        return (principal != null) ? "fragments/navbarShopper" : "fragments/navbarPlain";
    }
}
