/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.DriverManager;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Joe O'Regan
 * Student Number: K00203642
 */
@WebServlet(urlPatterns = {"/schedule"})
public class schedule extends HttpServlet {
    String schedule_time;
    String schedule_title;
    String schedule_speaker_id;
    String schedule_exhibitor_id;
    String schedule_location;
    
    Connection conn;
    PreparedStatement prepStat;
    Statement stat;
    
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        schedule_time = request.getParameter("schedule_time");
        schedule_title = request.getParameter("schedule_title");
        schedule_speaker_id = request.getParameter("schedule_speaker_id");
        schedule_exhibitor_id = request.getParameter("schedule_exhibitor_id");
        schedule_location = request.getParameter("schedule_location");
        
        try {
            String query = "INSERT INTO Schedule VALUES (?,?,?,?,?)";
            prepStat = (PreparedStatement) conn.prepareStatement(query);
            prepStat.setString(1, schedule_time);
            prepStat.setString(2, schedule_title);
            prepStat.setString(3, schedule_speaker_id);
            prepStat.setString(4, schedule_exhibitor_id);
            prepStat.setString(5, schedule_location);
            prepStat.executeUpdate();
            }
        catch (Exception e)
        {
            System.err.println(e);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        
        response.sendRedirect("in_schedule.html");  // redirects back to schedule.html after form submitted
        
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    public void init() throws ServletException
    {
        String url = "jdbc:mysql://localhost:3306/";
        String dbName = "JoeCA";
        String userName = "root";
        String password = "password";
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn = (Connection) DriverManager.getConnection(url+dbName,userName,password);
            stat = (Statement) conn.createStatement();
            //stat.execute("DROP TABLE Schedule");
            stat.execute("CREATE TABLE IF NOT EXISTS Schedule " + 
                    "(schedule_time TIME, schedule_title CHAR(40), schedule_speaker_id INT, schedule_exhibitor_id INT, schedule_location CHAR(40))");
        } catch (Exception e) 
        {
            System.err.println(e);
        }
    } // end of init() method
}
