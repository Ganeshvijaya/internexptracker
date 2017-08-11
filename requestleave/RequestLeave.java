import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class RequestLeave extends HttpServlet {
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
		HttpSession session= request.getSession(false);
		if(session!=null){
			String id = (String) session.getAttribute("user_id");

		    out.print("You may proceed User "+id);
		    }
		    else{
		        out.print("Please login first");
		        request.getRequestDispatcher("index.html").include(request, response);
			}
	
        String Mem_id = request.getParameter("Mem_id");
		
        String Leave_srtDate = request.getParameter("Leave_srtDate");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date Leave_startDate = sdf.parse(Leave_srtDate);
		
        String Leave_endDate = request.getParameter("Leave_endDate");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date Leave_endingDate = sdf.parse(Leave_endDate);
		
		 String Leave_purpose = request.getParameter("Leave_purpose");
		 
		 
        try{
        
       Connection con= dbConnection.getConnection();

        PreparedStatement ps=con.prepareStatement
                  ("insert into expensetracker.leave values(?,?,?,?,0)");

        ps.setString(1, Mem_id);
        ps.setString(2, Leave_startDate);
        ps.setString(3, Leave_endingDate);
		ps.setString(4, Leave_purpose);
		
        int i=ps.executeUpdate();
        
          if(i>0)
          {
            out.println("Your request has been sent for approval!");
			out.println("<script type=\"text/javascript\">");
			out.println("alert('Your request has been sent for approval!');");
			out.println("location='requestForLeave.html';");
			out.println("</script>");
			
          }
		  else{
			  out.println("Something Went Wrong!");
			  out.println("<script type=\"text/javascript\">");
				out.println("alert('Something Went Wrong! Re-enter the Details');");
				out.println("location='requestForLeave.html';");
				out.println("</script>");
		  }
        
        }
        catch(Exception se)
        {
            se.printStackTrace();
        }
	
      }
  }