import com.mysql.jdbc.PreparedStatement;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/show_cust_sched"})
public class show_cust_sched extends HttpServlet {
    String title = "Custom Schedule";
    String tableheading = "Times Of Events Selected";
    Connection conn = null; 
    PreparedStatement prepStat;
    com.mysql.jdbc.Statement stat;    
    String scheduletime;
    String schedulelocation;
    String ws_id;
    String workshopname;
    String ws_pres1;
    String ws_pres2;
    String ws_info;
    String checkboxname;
    int checkboxno;
    String checkboxvisible;
    boolean checkFormat;
// Custom Schedule Variables
    String workshop_id;
    String list_item_name;
    String workshop_name;
// Output Custom Schedule
    String sched_time;
    String ws_name;
    String sched_location;
    
    public void init() throws ServletException
    {
        String url = "jdbc:mysql://localhost:3306/";
        String dbName = "JoeCA";
        String userName = "root";
        String password = "password";
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn = (Connection) DriverManager.getConnection
                    (url+dbName,userName,password);
            stat = (com.mysql.jdbc.Statement) conn.createStatement();
        } catch (Exception e) 
        {
            System.err.println(e);
        }
    } // end of init() method
    

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            response.setContentType("text/html;charset=UTF-8");
        
            PrintWriter out = response.getWriter();
            String docType = "<!doctype html >";
            
            out.println(docType + "<html>" +
                    // Hide Non Printing Items
                    "<style type=\"text/css\" media=\"print\">" +
                        ".dontprint" + "{ display: none; }" +
                    "</style>" +
                    
                    "<link rel=\"stylesheet\" type=\"text/css\" href=\"CAstyle.css\">" +
                    "<head ><title>" + title + "</title></head>" +
                    "<body>" +
                        "<div class=\"heading\">" +
                            "<br><h1 style=\"text-align:center\">" + title + "</h1><br>" +
                        "</div>");
// Navigation menu
            out.println("<div class=\"navigation dontprint\">" +
                        "<form style=\"display: inline\" action=\"show_speakers\" method=\"get\"><button name=\"buttonSpeakers\" title=\"Event Speakers (Alt + 1)\">Event Speakers</button></form>" +
                        "<form style=\"display: inline\" action=\"show_workshops\" method=\"get\"><button name=\"buttonWorkshops\" title=\"Event Workshops (Alt + 2)\">Event Workshops</button></form>" +
                        "<form style=\"display: inline\" action=\"show_schedule\" method=\"get\"><button name=\"buttonSchedule\" title=\"Event Schedule (Alt + 3)\">Event Schedule</button></form>" +
                        "<form style=\"display: inline\" action=\"show_exhibitors\" method=\"get\"><button name=\"buttonExhibitors\" title=\"Event Exhibitors (Alt + 4)\">Event Exhibitors</button></form>" +
                        "<form style=\"display: inline\" action=\"reg_admin.html\" method=\"get\"><button name=\"buttonRegAdmin\" title=\"Administrator Registration Page (Alt + 5)\">Administrator Registration</button></form>" +
                        "<form style=\"display: inline\" action=\"reg_attendee.html\" method=\"get\"><button name=\"buttonRegAttendee\" title=\"Attendee Registration Page (Alt + 6)\">Attendee Registration</button></form>" +
                        "<form style=\"display: inline\" action=\"index\" method=\"get\"><button name=\"buttonHome\" title=\"Return To Homepage (Alt + 7)\">Home</button></form>" +
                    "</div>");
// Custom Event Schedule                    
            out.println("<div class=\"mainbody\">" +
                            "<form action=\"cust_schedule\" method=\"POST\"><br>"
                    + "<table align=\"center\">" +
                    "<tr><td class=\"tbhead\" colspan=\"2\">"+tableheading+"</td></tr>");
            
            try{
                Statement stmt = conn.createStatement();
                //ResultSet result = stmt.executeQuery("SELECT schedule_time,ws_id,ws_name,schedule_location,ws_presenter1,ws_presenter2,ws_info FROM Schedule JOIN Workshops ON Schedule.workshop_id = Workshops.ws_id ORDER BY schedule_time;");
                ResultSet custom_schedule = stmt.executeQuery("SELECT schedule_time,schedule.workshop_id,workshops.ws_name,schedule_location,ws_presenter1,ws_presenter2,ws_info FROM Schedule JOIN CustSched ON Schedule.workshop_id = CustSched.workshop_id JOIN Workshops ON Schedule.workshop_id = Workshops.ws_id AND Workshops.ws_id = CustSched.workshop_id ORDER BY schedule_time ASC;");
                
                checkboxno = 1;
                while(custom_schedule.next())
                {
                    scheduletime = custom_schedule.getString("schedule_time");
                    schedulelocation = custom_schedule.getString("schedule_location");
                    ws_id = custom_schedule.getString("schedule.workshop_id");
                    workshopname = custom_schedule.getString("workshops.ws_name");
                    ws_pres1 = custom_schedule.getString("ws_presenter1");
                    ws_pres2 = custom_schedule.getString("ws_presenter2");
                    ws_info = custom_schedule.getString("ws_info");
                    
                    out.println("<tr class=\"thead\"><td>" + scheduletime + "</td><td>" + workshopname + "</td></tr>");
                    
                    checkFormat = workshopname.contentEquals( "Break" );                                                                  // compare content of workshopname to "break"
                    if(!checkFormat)                                                                                                            // don't output presenters for breaks
                    {       
                        out.println("<tr><td><b>Location:</b></td><td>" + schedulelocation + "</td></tr>");
                        
                        if (ws_pres2.contentEquals( "" )) 
                        { 
                            out.println("<tr><td><b>Presenter:</b></td><td>"+ws_pres1+"</td></tr>");                                   // output if only 1 presenter   
                            out.println("<tr><td><b>About:</b></td><td>"+ws_info+"</td></tr>");                           
                        }
                        else 
                        {
                            out.println("<tr><td><b>Presenters:</b></td><td>"+ws_pres1+" and "+ws_pres2+"</td></tr>");        // output if 2 presenters 
                            out.println("<tr><td><b>About:</b></td><td>"+ws_info+"</td></tr>");                           
                        }
                    }
                    out.println("<td colspan=\"2\">&nbsp;</td>");
                }
                out.println("</table></form>" +
                        "</div>");
        } 
        catch(Exception e)
        {
            System.err.println(e);
        }
            
// Print Custom Schedule
out.println("<div class=\"mainbody dontprint\" align=\"center\">"
                + "<button onclick=\"myFunction()\">Print this page</button>" +
                    "<script>"
                + "function myFunction() {window.print();} "
                + "</script>"
            + "</div>");
                       
// Return To Schedule
out.println("<div class=\"mainbody dontprint\" align=\"center\">" +
                            "<form><a href=\"show_schedule#cs_add\" title=\"Return To Edit Schedule\"><button name=\"button\" value=\"OK\" type=\"button\">Return To Schedule</button></a></form>" +
                        "</div>");
            
            /*
// Choose custom schedule
            out.println("<div class=\"mainbody\">"
                    +"<h2>Choose Custom Schedule</h2>"
                    + "<p>Select a workshop from the list to add to your custom schedule</p>"
                    + "<form action=\"cust_schedule_add\" method=\"POST\">"
                    + "<table align=\"center\">"
                        + "<tr>" +
                "               <th>Select Workshop:</th>\n" +
                "                   <td><select name=\"custom_schedule\" title=\"Select A Workshop From The List\" style=\"width:100%\">");
            
            try{
                java.sql.Statement stmt = conn.createStatement();            
                ResultSet schedule = stmt.executeQuery("SELECT schedule_time, workshop_id,ws_name FROM Schedule JOIN Workshops ON Schedule.workshop_id = Workshops.ws_id WHERE workshop_id NOT IN (SELECT workshop_id FROM CustSched) AND workshop_id != 1 ORDER BY schedule_time;");
                
                while(schedule.next())
                {
                    workshop_id = schedule.getString("workshop_id");
                    workshop_name = schedule.getString("ws_name");
                    list_item_name = schedule.getString("schedule_time") + " " +schedule.getString("ws_name");
                    checkFormat = workshop_name.contentEquals( "Break" );                                                                  // compare content of workshopname to "break"
                    
                    if( !checkFormat )
                    {           
                        out.println("<option value=\""+workshop_id+"\">"+list_item_name+"</option>");
                    }
                    //out.println("<option value=\""+workshop_id+"\">"+list_item_name+"</option>");
                }
            }
            catch(Exception e)
            {
                System.err.println(e);
            }
            
            out.println("</select></td>" +
                                "<td style=\"text-align:right\"><input type=\"submit\" value=\"Add To Custom Schedule\" title=\"Add To Custom Schedule\"/></td>" +
                "           </tr>"
                    + "</table>" +
                    "</form>" +
                "</div><br>");
            
// Delete custom schedule item
            out.println("<div class=\"mainbody\">"
                    +"<h2>Delete From Custom Schedule</h2>"
                    + "<p>Select a workshop from the list remove from your custom schedule</p>"
                    + "<form action=\"cust_schedule_delete\" method=\"POST\">"
                    + "<table align=\"center\">"
                        + "<tr>" +
                "               <th>Select Workshop:</th>\n" +
                "                   <td><select name=\"cust_sched_delete\" title=\"Select A Workshop From The List\" style=\"width:100%\">");
            
            try{
                java.sql.Statement stmt = conn.createStatement();            
                ResultSet schedule = stmt.executeQuery("SELECT schedule_time,schedule.workshop_id,workshops.ws_name,schedule_location FROM Schedule JOIN CustSched ON Schedule.workshop_id = CustSched.workshop_id JOIN Workshops ON Schedule.workshop_id = Workshops.ws_id AND Workshops.ws_id = CustSched.workshop_id ORDER BY schedule_time ASC;");
                
                while(schedule.next())
                {
                    workshop_id = schedule.getString("workshop_id");
                    workshop_name = schedule.getString("ws_name");
                    list_item_name = schedule.getString("schedule_time") + " " +schedule.getString("ws_name");
                    out.println("<option value=\""+workshop_id+"\">"+list_item_name+"</option>");
                    /*
                    checkFormat = workshop_name.contentEquals( "Break" );                                                                  // compare content of workshopname to "break"
                    
                    if( !checkFormat )
                    {           
                        
                    }
                    //out.println("<option value=\""+workshop_id+"\">"+list_item_name+"</option>");
                }
            }
            catch(Exception e)
            {
                System.err.println(e);
            }
            
            out.println("</select></td>" +
                                "<td style=\"text-align:right\"><input type=\"submit\" value=\"Delete From Custom Schedule\" title=\"Delete From Custom Schedule\"/></td>" +
                "           </tr>"
                    + "</table>" +
                    "</form>" +
                "</div>");
*/            
// Navigation menu
            out.println("<div class=\"navigation dontprint\">" +
                        "<form style=\"display: inline\" action=\"show_speakers\" method=\"get\"><button name=\"buttonSpeakers\" title=\"Event Speakers (Alt + 1)\">Event Speakers</button></form>" +
                        "<form style=\"display: inline\" action=\"show_workshops\" method=\"get\"><button name=\"buttonWorkshops\" title=\"Event Workshops (Alt + 2)\">Event Workshops</button></form>" +
                        "<form style=\"display: inline\" action=\"show_schedule\" method=\"get\"><button name=\"buttonSchedule\" title=\"Event Schedule (Alt + 3)\">Event Schedule</button></form>" +
                        "<form style=\"display: inline\" action=\"show_exhibitors\" method=\"get\"><button name=\"buttonExhibitors\" title=\"Event Exhibitors (Alt + 4)\">Event Exhibitors</button></form>" +
                        "<form style=\"display: inline\" action=\"reg_admin.html\" method=\"get\"><button name=\"buttonRegAdmin\" title=\"Administrator Registration Page (Alt + 5)\">Administrator Registration</button></form>" +
                        "<form style=\"display: inline\" action=\"reg_attendee.html\" method=\"get\"><button name=\"buttonRegAttendee\" title=\"Attendee Registration Page (Alt + 6)\">Attendee Registration</button></form>" +
                        "<form style=\"display: inline\" action=\"index\" method=\"get\"><button name=\"buttonHome\" title=\"Return To Homepage (Alt + 7)\">Home</button></form>" +
                    "</div>");
// Bottom Links                    
            out.println("<div id=\"bl\" class=\"bottomlinks dontprint\">\n" +
                "<table align=\"center\">\n" +
                    "<tr><th>Display:</th><th>Register:</th><th>Other:</th><tr>\n" +
                    "<tr><td><a href=\"show_speakers\" title=\"Show Speakers (Alt + 1)\" accesskey=\"1\">1. Show Speakers</a></td><td><a href=\"reg_admin.html\" title=\"Administrator Registration Page (Alt + 5)\" accesskey=\"5\">5. Administrator Registration</a></td><td><a href=\"index\" title=\"Return To Homepage (Alt + 7)\" accesskey=\"7\">7. Home Page</a></td></tr>" +
                    "<tr><td><a href=\"show_workshops\" title=\"Show Workshops (Alt + 2)\" accesskey=\"2\">2. Show Workshops</a></td><td><a href=\"reg_attendee.html\" title=\"Attendee Registration Page (Alt + 6)\" accesskey=\"6\">6. Attendee Registration</a></td><td></td></tr>" +
                    "<tr><td><a href=\"show_schedule\" title=\"Show Schedule (Alt + 3)\" accesskey=\"3\">3. Show Schedule</a></td><td></td><td></td></tr>" +
                    "<tr><td><a href=\"show_exhibitors\" title=\"Show Exhibitors (Alt + 4)\" accesskey=\"4\">4. Show Exhibitors</a></td><td></td><td></td></tr>" +
                "</table>" +
            "</div>");
            out.println("</body></html>");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}