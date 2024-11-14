/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import dao.TaiKhoanDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.TaiKhoan;

/**
 *
 * @author ADMIN
 */
public class ChangePassServlet extends HttpServlet {
    protected void processRequest (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType ("text/html;charset=UTF-8");
        //Lấy thông tin mật khẩu
        String oldpass = request.getParameter("oldpassword");
        String newpass = request.getParameter("newpassword");
        String confirmpass = request.getParameter("confirmpassword");
        
        if(!newpass.equals(confirmpass))
        {
            request.setAttribute("error", "Mật khẩu mới và mật khẩu mới xác nhận không trùng");
            request.getRequestDispatcher("changepass.jsp").forward(request, response);
        }
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
               
        TaiKhoanDAO tkDAO = new TaiKhoanDAO();
        TaiKhoan tk = tkDAO.checkLogin(username, oldpass);
       
        if(tk!=null) //valid
        {
            tk.setMatkhau(newpass);
            tkDAO.changePassword(tk);
            request.getRequestDispatcher("home.jsp").forward(request, response);
        }else
        {
            request.setAttribute("error", "Mật khẩu cũ không đúng");
            request.getRequestDispatcher("changepass.jsp").forward(request, response);
        }
    }
}
