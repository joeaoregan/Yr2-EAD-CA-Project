/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
@WebServlet(urlPatterns = {"/index"})
public class index extends HttpServlet {
        
        String username;
        String password;
        String DB_username; 
        String DB_password;
        String title = "Administrator Login";
        String docType = "<!doctype html >";
        
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
        response.setContentType("text/html;charset=UTF-8");
        
        
        
        try(PrintWriter out=response.getWriter()){
            username = request.getParameter("username");    // username from html form
            password = request.getParameter("password");    // password form html form
            String passwordToGuess = "password";            // global password for every user
            
            boolean  passwordValidate = password.contentEquals( passwordToGuess );
            
            out.println(docType +
                    "<html>\n" +
                    "<link rel=\"stylesheet\" type=\"text/css\" href=\"CAstyle.css\">" +
                    "<head><title>" + title + "</title></head>\n" +                    
                    "<body bgcolor\"#f0f0f0\">\n" +                    
                    "<h1 align=\"center\">" + title + "</h1>\n" +
                    "</body></html>");
            
            if (passwordValidate == true)
            {
                out.println(docType +
                    "<html>\n" +
                    "<div class=\"navigation\">\n" +
                        "<form style=\"display: inline\" action=\"eventAdministration.html\" method=\"get\"><button name\"buttonEventAdmin\" title=\"Event Administration Page (Alt + 1)\">Event Administration Page</button></form>\n" +
                        "<form style=\"display: inline\" action=\"reg_admin.html\" method=\"get\"><button name=\"buttonRegAdmin\" title=\"Administrator Registration Page (Alt + 2)\">Administrator Registration</button></form>\n" +
                        "<form style=\"display: inline\" action=\"reg_attendee.html\" method=\"get\"><button name=\"buttonRegAttendee\" title=\"Attendee Registration Page (Alt + 3)\">Attendee Registration</button></form>\n" +
                        "<form style=\"display: inline\" action=\"index.html\" method=\"get\"><button name=\"buttonHome\" title=\"Return To Home Page (Alt + 5)\">Return To Home Page</button></form>\n" +
                    "</div>" +
                    "<body bgcolor\"#f0f0f0\">\n" + 
                    "<ul><h2>Hello "
                    + request.getParameter("username") + ", welcome back!!!" +
                    "</h2></ul>\n" +   
                        "<form><a href=\"eventAdministration.html\" title=\"Go To Event Administation Page\"><button name=\"button\" autofocus=\"autofocus\" value=\"OK\" type=\"button\">Continue</button></a></form><br>" +
                            "<a href=\"eventAdministration.html\" title=\"Event Administation Page (Alt + 1)\" accesskey=\"1\">1. Continue To Event Administration Page</a><br>" +
                            "<a href=\"reg_admin.html\" title=\"Administrator Registration Page (Alt + 2)\" accesskey=\"2\">2. Administrator Registration</a><br>" +
                            "<a href=\"reg_attendee.html\" title=\"Attendee Registration Page (Alt + 3)\" accesskey=\"3\">3. Attendee Registration</a><br>" +
                            "<a href=\"index.html\" title=\"Return To Homepage (Alt + 4)\" accesskey=\"4\">4. Return To Home Page</a><br>" +
                    "</body></html>");
            }
            else
            {
                out.println(docType +
                    "<html>\n" +
                    "<div class=\"navigation\">\n" +
                        "<form style=\"display: inline\" action=\"index.html\" method=\"get\"><button name=\"buttonHome\" title=\"Return To Home Page (Alt + 1)\">Return To Home Page</button></form>\n" +
                    "</div>" +
                    "<body bgcolor\"#f0f0f0\">\n" + 
                    "<ul><h2>Hello "
                    + request.getParameter("username") + ", The Password Entered Is Incorrect!!!" +
                    "</h2></ul>\n" +   
                        "<form><a href=\"index.html\" title=\"Return To Homepage (Alt + 1)\"><button name=\"button\" autofocus=\"autofocus\" value=\"OK\" type=\"button\">Go Back</button></a></form><br>" +
                    "<a href=\"index.html\" title=\"Return To Homepage (Alt + 1)\" accesskey=\"1\">Return to Home Page</a>" +
                    "</body></html>");
            }
        } // End Of Try // End Of Try // End Of Try // End Of Try // End Of Try // End Of Try // End Of Try // End Of Try
        catch(Exception e)
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

}