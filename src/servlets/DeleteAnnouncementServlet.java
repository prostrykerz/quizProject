package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import admin.Announcement;

/**
 * Servlet implementation class DeleteAnnouncementServlet
 */
@WebServlet("/DeleteAnnouncementServlet")
public class DeleteAnnouncementServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteAnnouncementServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext context = getServletContext();
		ArrayList<Announcement> announcements = (ArrayList<Announcement>) context.getAttribute("announcements");
		String text = request.getParameter("announcement");
		Iterator<Announcement> it = announcements.iterator();
		while(it.hasNext() && !text.equals("")) {
			Announcement a = it.next();
			if(a.getText().equals(text)) {
				a.delete();
				it.remove();
				break;
			}
		}
		RequestDispatcher dispatch = request.getRequestDispatcher("admin_dashboard.jsp");
		dispatch.forward(request, response);
	}

}
