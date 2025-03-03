/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import context.DbContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.TaiKhoan;

/**
 *
 * @author ADMIN
 */
public class TaiKhoanDAO {
    Connection conn;
    PreparedStatement ps;
    ResultSet rs;
   
    //phuong thuc doc thong tin tai khoan theo tendangnhap-matkhau (checklogin)
    public TaiKhoan checkLogin(String username, String password){
        TaiKhoan tk = null;
        conn = DbContext.getConnection();
        try{
            ps = conn.prepareStatement("select * from taikhoan where tendangnhap=? and matkhau=?");
            ps.setString(1, username);
            ps.setString(2, password);
           
            rs = ps.executeQuery();
            if(rs.next())//ton tai
            {
                tk = new TaiKhoan(rs.getString(1), rs.getString(2));
            }
        }catch (Exception ex){
            System.err.println("Loi:" + ex.toString());
        }
        return tk;
    }
     public boolean changePassword(TaiKhoan tk) {
        conn = DbContext.getConnection();
        String sql = "update taikhoan set matkhau=? where tendangnhap=?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, tk.getMatkhau());
            ps.setString(2, tk.getTendangnhap());
            int kq = ps.executeUpdate();
            if (kq > 0) {
                return true;
            }
        } catch (Exception ex) {
            System.out.println("Loi: " + ex.toString());
        }
        return false;
    }
     public static void main(String[] args) {
         TaiKhoanDAO tkDAO = new TaiKhoanDAO();
         
         TaiKhoan tk = new TaiKhoan("quanly", "123456789");
         if(tkDAO.changePassword(tk))
         {
             System.out.println("OK");
         }else
             System.out.println("No OK");
     }
}