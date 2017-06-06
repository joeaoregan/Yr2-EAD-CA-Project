/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.ResultSet;
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
@WebServlet(urlPatterns = {"/manage_workshops"})
public class manage_workshops extends HttpServlet {
    Connection conn;
    PreparedStatement prepStat;
    Statement stat;
    
    String title = "Manage Workshops";
    String ws_id;
    String ws_name;
    String ws_pres1;
    String ws_pres2;;
    String ws_info;
    int ws_num = 1;
       
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
                    
                    java.sql.Statement stmt = conn.createStatement();  
                }
                catch(Exception e){System.err.println(e);}
                
    } // end init
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
        //sched_location = request.getParameter("schedule_location");
        response.setContentType("text/html;charset=UTF-8");
        
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>" +
                        "<html>" +
                        "<head>" +
                            "<link rel=\"stylesheet\" type=\"text/css\" href=\"CAstyle.css\">" +
                        "<title>"+title+"</title>" +
                    "</head>");
            out.println("<body>");
// Heading
            out.println("<div class=\"heading\">" +
                        "<table>" +
                            "<tr><td>&nbsp;</td></tr>" +
                            "<tr><td>&nbsp;</td></tr>" +
                            "<tr><td><a align=\"left\" href=\"index\" title=\"Return To Homepage (Alt + 7)\" accesskey=\"7\"><img src=\"http://s21.postimg.org/gyukaf1l3/Logo.png\" alt=\"Event Logo\" id=\"img150\"></a></td>" +
                            "<td><h1>" + title + "</h1></td></tr>" +
                        "</table>" +
                    "</div>");
            
// Navigation menu (Workshops Highlighted)
            out.println("<div class=\"navigation\"><span>" +
                            "<form action=\"show_schedule\" method=\"get\"><button name=\"buttonEventSchedule\" title=\"Event Schedule (Alt + 3)\">Event Schedule</button></form>" +
                            "<form action=\"manage_speakers\" method=\"get\"><button name=\"buttonSpeaker\" title=\"Add Speaker Details (Alt + h)\">Manage Speakers</button></form>" +
                            "<form action=\"manage_workshops\" method=\"get\"><button name=\"buttonWorkshop\" id=\"active\" title=\"Add Workshop Details (Alt + j)\">Manage Workshops</button></form>" +
                            "<form action=\"manage_schedule\" method=\"get\"><button name=\"buttonSchedule\" title=\"Add Schedule Details (Alt + k)\">Manage Schedule</button></form>" +
                            "<form action=\"manage_exhibitors\" method=\"get\"><button name=\"buttonExhibitor\" title=\"Add Exhibitor Details (Alt + l)\">Manage Exhibitors</button></form>" +
                            "<form action=\"index\" method=\"get\"><button name=\"buttonHome\" title=\"Return To Homepage (Alt + 7)\">Home</button></form>" +
                        "</span></div>");
// Workshop Form Input
            out.println("<div class=\"mainbody\">" +
                            "<form action=\"add_workshop\" method=\"POST\"><br>" +
                                "<table align=\"center\">" +
                                    "<tr><td class=\"tbhead\" colspan=\"3\">Enter Workshop Details</td></tr>" +
                                    "<tr><td colspan=\"3\">&nbsp;</td></tr>" +
                                    "<tr>" +
                                        "<th>Name:</th>" + // Required field, max length of 60
                                        "<td><input type=\"text\" name=\"ws_name\" autofocus=\"autofocus\" title=\"Enter a name for the workshop\" maxlength=\"60\" placeholder=\"Required Field\" required></td>" +
                                    "</tr>" +
                                    "<tr>" +
                                        "<th>Presenter 1:</th>" + // Required field, max length of 40
                                        "<td><input type=\"text\" name=\"ws_presenter1\" title=\"Enter first presenters name\" maxlength=\"40\" placeholder=\"Required Field\" required></td>" +
                                    "</tr>" +
                                    "<tr>" +
                                        "<th>Presenter 2:</th>" + // Max length of 40
                                        "<td><input type=\"text\" name=\"ws_presenter2\" title=\"Enter second presenters name\" maxlength=\"40\"></td>" +
                                    "</tr>" +
                                    "<tr>" +
                                        "<th>Information:</th>" + // Required field
                                        "<td><textarea rows=\"15\" cols=\"50\" name=\"ws_info\" title=\"Enter information about the workshop\" placeholder=\"Required Field\" required></textarea></td>" +
                                    "</tr>" +
                                    "<tr>" +
                                        "<td></td>" +
                                        "<td id=\"bt\"><input type=\"submit\" value=\"Submit\" title=\"Submit Details\"/></td>" +
                                    "</tr>" +
                                "</table>" +
                            "</form><br>" +
                        "</div><br>");

// Current Workshops            
            out.println("<div class=\"mainbody\">" +
                            "<table align=\"center\">" +
                                "<tr><td class=\"tbhead\" colspan=\"4\">Current Workshops</td></tr>" + // Table heading
                                "<tr><td colspan=\"3\">&nbsp;</td></tr>" + // Blank Line
                                "<tr><td colspan=\"3\">A list of the workshops currently scheduled:</td></tr>" + // Table info
                                "<tr><td colspan=\"3\">&nbsp;</td></tr>"); // Blank Line
            try {
                java.sql.Statement stmt = conn.createStatement();
                ResultSet speakers = stmt.executeQuery("SELECT * FROM Workshops WHERE ws_name NOT LIKE 'Break'");
                
                ws_num=1;  // workshop number
                while (speakers.next()) {  // Display each speakers details
                    ws_id = speakers.getString("ws_id");
                    ws_name = speakers.getString("ws_name");
                    ws_pres1 = speakers.getString("ws_presenter1");
                    ws_pres2 = speakers.getString("ws_presenter2");
                    ws_info = speakers.getString("ws_info");
                    
                    out.println("<tr><th colspan=\"4\" id=\"thc\">Workshop "+ws_num+"</th></tr>" +  // heading
                                "<tr><th>Workshop Name:</th><td>"+ws_name+"</td><th>Workshop DB ID:</th><td>"+ws_id+"</td></tr>" +  // Workshop name & id                
                                "<tr><th>Presenter 1:</th><td>"+ws_pres1+"</td><th>Presenter 2:</th><td>"+ws_pres2+"</td></tr>" +  // presenter names
                                "<tr><th>About:</th><td colspan=\"3\">"+ws_info+"</td>" + // Workshop information
                                "<tr><td colspan=\"4\"><hr></td></tr>");  // line
                    ws_num++;
                }
            }
            catch (Exception e)
            {
                System.err.println(e);
            }            
            out.println("</table></div><br>");
            
// Edit Workshops          
            out.println("<div class=\"mainbody\">" +
                            "<form action=\"delete_workshop\" method=\"POST\">" +
                            "<table align=\"center\">" +
                                "<tr><td class=\"tbhead\" colspan=\"3\">Edit Workshop Details</td></tr>" +
                                "<tr><td colspan=\"3\">&nbsp;</td></tr>" +
                                "<tr><td colspan=\"3\">Select workshop to delete</td></tr>" +
                                "<tr><td colspan=\"3\">&nbsp;</td></tr>" +
                                "<tr><th>Workshop To Delete:</th>" +
                                    "<td><select name=\"delete_workshop\" title=\"Select A Name From The List\">");
            try{
                java.sql.Statement stmt = conn.createStatement();            
                ResultSet Workshop = stmt.executeQuery("SELECT ws_id,ws_name FROM Workshops WHERE ws_name NOT LIKE 'Break'");
                
                while(Workshop.next())
                {
                    ws_id = Workshop.getString("ws_id"); 
                    ws_name = Workshop.getString("ws_name");
                    out.println("<option value=\""+ws_id+"\">" + ws_id + ". " + ws_name + "</option>");
                }
            }
            catch(Exception e)
            {
                System.err.println(e);
            } 
            
            out.println("</select></td>" +
                                "<td id=\"bt\"><input type=\"submit\" value=\"Delete\" title=\"Delete Workshop\"/></td>" +
                            "</tr>"
                    + "</table>" +
                        "</form>" +
                    "<p>Choose the workshop ID & name to select</p>" +
                "</div><br>");
            
// Bottom Links (Manage)             
            out.println("<div  id=\"bl\" class=\"bottomlinks\">" +
                            "<table align=\"center\">" +
                                "<tr><th>Manage:</th><th>Display:</th><th>Register:</th><th>Other:</th><tr>" +
                                "<tr><td><a href=\"manage_speakers\" title=\"Manage Speakers (Alt + h)\" accesskey=\"h\">h. Manage Speakers</a></td>"
                                    + "<td><a href=\"show_speakers\" title=\"Show Speakers (Alt + 1)\" accesskey=\"1\">1. Show Speakers</a></td>"
                                    + "<td><a href=\"reg_admin\" title=\"Administrator Registration Page (Alt + 5)\" accesskey=\"5\">5. Administrator Registration</a></td>"
                                    + "<td><a href=\"index\" title=\"Return To Homepage (Alt + 7)\" accesskey=\"7\">7. Home Page</a></td></tr>" +
                                "<tr><td><a href=\"manage_workshops\" title=\"Manage Workshops (Alt + j)\" accesskey=\"j\">j. Manage Workshops</a></td>"
                                    + "<td><a href=\"show_workshops\" title=\"Show Workshops (Alt + 2)\" accesskey=\"2\">2. Show Workshops</a></td>"
                                    + "<td><a href=\"reg_attendee.html\" title=\"Attendee Registration Page (Alt + 6)\" accesskey=\"6\">6. Attendee Registration</a></td>"
                                    + "<td></td></tr>" +
                                "<tr><td><a href=\"manage_schedule\" title=\"Manage Schedule (Alt + k)\" accesskey=\"k\">k. Manage Schedule</a></td>"
                                    + "<td><a href=\"show_schedule\" title=\"Show Schedule (Alt + 3)\" accesskey=\"3\">3. Show Schedule</a></td>"
                                    + "<td></td>"
                                    + "<td></td></tr>" +
                                "<tr><td><a href=\"manage_exhibitors\" title=\"Manage Exhibitors (Alt + l)\" accesskey=\"l\">l. Manage Exhibitors</a></td>"
                                    + "<td><a href=\"show_exhibitors\" title=\"Show Exhibitors (Alt + 4)\" accesskey=\"4\">4. Show Exhibitors</a></td>"
                                    + "<td></td>"
                                    + "<td></td></tr>" +
                            "</table>" +
                        "</div>" +                    
                    "</body></html>");
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
