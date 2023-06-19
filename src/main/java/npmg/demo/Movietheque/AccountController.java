package npmg.demo.Movietheque;

import java.sql.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class AccountController {

	String db = "jdbc:mysql://localhost:3306/movietheque";
	String root = "root";
	String pass = "alexa04";
	
	@RequestMapping("/register")
	public String register(HttpServletRequest req) {
		String f_name = req.getParameter("f_name");
		String l_name = req.getParameter("l_name");
		String email = req.getParameter("email");
		String username = req.getParameter("username");
		String password = req.getParameter("password");
        if (password.length() < 8) {
			req.setAttribute("exception", "Password must be at least 8 characters.");
            return "register.jsp";
        }
        else {
            try {
                Connection c = DriverManager.getConnection(db, root, pass);
                PreparedStatement ps = c.prepareStatement("insert into users (f_name, l_name, email, username, password) values (?,?,?,?,?)");
                ps.setString(1, f_name);
                ps.setString(2, l_name);
                ps.setString(3, email);
                ps.setString(4, username);
                ps.setString(5, password);
                int n = ps.executeUpdate();
                if (n > 0){
                    req.setAttribute("result", "User added!");
                }
                ps.close();
                c.close();
            } catch (Exception e) {
                req.setAttribute("exception", e.getMessage());
            }
            return "login.jsp";
        }
	}

    @RequestMapping("/toLogin")
	public String toLogin() {
        return "login.jsp";
	}

    @RequestMapping("/login")
	public String login(HttpServletRequest req) {
		String username = req.getParameter("username");
		String password = req.getParameter("password");
        try {
            Connection c = DriverManager.getConnection(db, root, pass);
            PreparedStatement ps = c.prepareStatement("SELECT * FROM users WHERE username=?");
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt(1);
                String checkPass = rs.getString(6);
                if (password.equals(checkPass)) {
                    MyController.setUser_id(id);
                }
                else {
                    req.setAttribute("exception", "Wrong password.");
                    return "login.jsp";
                }
            }
            else {
                req.setAttribute("exception", "No such user exists.");
                return "login.jsp";
            }
            ps.close();
            rs.close();
            c.close();
        } catch (Exception e) {
            req.setAttribute("exception", e.getMessage());
        }
        return "home.jsp";
	}

    @RequestMapping("/toRegister")
	public String toRegister() {
        return "register.jsp";
	}
}
