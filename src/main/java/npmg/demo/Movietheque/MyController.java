package npmg.demo.Movietheque;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class MyController {
	
	private static int user_id;

	public int getUser_id() {
		return user_id;
	}

	public static void setUser_id(int id) {
		user_id = id;
	}

	String db = "jdbc:mysql://localhost:3306/movietheque";
	String username = "root";
	String password = "alexa04";
	
	@RequestMapping("/home")
	public String getHome() {
		return "home.jsp";
	}
	
	@RequestMapping("/insert")
	public String add(HttpServletRequest req) {
		String title = req.getParameter("movieTitle");
		String director = req.getParameter("movieDirector");
		String year = req.getParameter("movieYear");
		String genre = req.getParameter("movieGenre");
		String watched = req.getParameter("watched");
		try {
			Connection c = DriverManager.getConnection(db, username, password);
			PreparedStatement ps = c.prepareStatement("insert into movie (title, director, year, genre, user_id, watched) values (?,?,?,?,?,?)");
			ps.setString(1, title);
            ps.setString(2, director);
			ps.setInt(3, Integer.parseInt(year));
			ps.setString(4, genre);
			ps.setInt(5, user_id);
			if (watched.equals("watched")) {
				ps.setBoolean(6, true);
			}
			else {
				ps.setBoolean(6, false);
			}
			int n = ps.executeUpdate();
			if (n > 0){
				req.setAttribute("result", "Movie added!");
			}
			ps.close();
			c.close();
		} catch (Exception e) {
			req.setAttribute("exception", e.getMessage());
		}
		return "add.jsp";
	}

	@RequestMapping("/show")
	public String show(HttpServletRequest req) {
		try {
			Connection c = DriverManager.getConnection(db, username, password);
			req.setAttribute("movies", getMovies(c));
			c.close();
		} catch (Exception e) {
			req.setAttribute("exception", e.getMessage());
		}
		return "show.jsp";
	}

	//get a list of all movies
	private List<Movie> getMovies(Connection c) throws SQLException {
		LinkedList<Movie> movies = new LinkedList<Movie>();
		PreparedStatement ps = c.prepareStatement("SELECT * FROM movie where user_id =?");
		ps.setInt(1, user_id);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Movie m = new Movie();
			movies.add(m);
			m.setId(rs.getInt(1));
			m.setTitle(rs.getString(2));
			m.setDirector(rs.getString(3));
			m.setYear(rs.getInt(4));
			m.setGenre(rs.getString(5));
		}
		return movies;
	}

	//get a list of fav movies
	private List<Movie> getFavMovies(Connection c) throws SQLException {
		LinkedList<Movie> movies = new LinkedList<Movie>();
		PreparedStatement ps = c.prepareStatement("SELECT * FROM movie WHERE favorite = true and user_id =?");
		ps.setInt(1, user_id);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Movie m = new Movie();
			movies.add(m);
			m.setId(rs.getInt(1));
			m.setTitle(rs.getString(2));
			m.setDirector(rs.getString(3));
			m.setYear(rs.getInt(4));
			m.setGenre(rs.getString(5));
		}
		return movies;
	}

	//get a list of watched movies
	private List<Movie> getWatchedMovies(Connection c) throws SQLException {
		LinkedList<Movie> movies = new LinkedList<Movie>();
		PreparedStatement ps = c.prepareStatement("SELECT * FROM movie WHERE watched = true and user_id =?");
		ps.setInt(1, user_id);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Movie m = new Movie();
			movies.add(m);
			m.setId(rs.getInt(1));
			m.setTitle(rs.getString(2));
			m.setDirector(rs.getString(3));
			m.setYear(rs.getInt(4));
			m.setGenre(rs.getString(5));
		}
		return movies;
	}

	//get a list of to-watch movies
	private List<Movie> getToWatchMovies(Connection c) throws SQLException {
		LinkedList<Movie> movies = new LinkedList<Movie>();
		PreparedStatement ps = c.prepareStatement("SELECT * FROM movie WHERE watched = false and user_id =?");
		ps.setInt(1, user_id);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Movie m = new Movie();
			movies.add(m);
			m.setId(rs.getInt(1));
			m.setTitle(rs.getString(2));
			m.setDirector(rs.getString(3));
			m.setYear(rs.getInt(4));
			m.setGenre(rs.getString(5));
		}
		return movies;
	}

	//get a random movie
	private List<Movie> getRandomMovie(Connection c) throws SQLException {
		LinkedList<Movie> movies = new LinkedList<Movie>();
		PreparedStatement ps = c.prepareStatement("select count(*) from movie where watched = false");
		ResultSet rs = ps.executeQuery();
		int n = 0;
		while(rs.next()) {
			n = rs.getInt(1);
		}
		rs.close();
		Random r = new Random();
		int chosen = r.nextInt(n);
		PreparedStatement ps2 = c.prepareStatement("select * from movie where watched = false");
		ResultSet rs2 = ps2.executeQuery();
		LinkedList<Movie> all = new LinkedList<Movie>();
		while(rs2.next()) {
			Movie m = new Movie();
			all.add(m);
			m.setId(rs2.getInt(1));
			m.setTitle(rs2.getString(2));
			m.setDirector(rs2.getString(3));
			m.setYear(rs2.getInt(4));
			m.setGenre(rs2.getString(5));
		}
		movies.add(all.get(chosen));
		return movies;
	}

	//get a list of movies with specific string property
	private List<Movie> getMovies(Connection c, String param, String val) throws SQLException {
		LinkedList<Movie> movies = new LinkedList<Movie>();
		PreparedStatement ps = c.prepareStatement("SELECT * FROM movie WHERE " + param + " = \"" + val + "\" and user_id = ?");
		ps.setInt(1, user_id);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Movie m = new Movie();
			movies.add(m);
			m.setId(rs.getInt(1));
			m.setTitle(rs.getString(2));
			m.setDirector(rs.getString(3));
			m.setYear(rs.getInt(4));
			m.setGenre(rs.getString(5));
		}
		return movies;
	}

	//get a list of movies with specific int property
	private List<Movie> getMovies(Connection c, String param, int val) throws SQLException {
		LinkedList<Movie> movies = new LinkedList<Movie>();
		PreparedStatement ps = c.prepareStatement("SELECT * FROM movie WHERE " + param + " = " + val + " and user_id = ?");
		ps.setInt(1, user_id);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Movie m = new Movie();
			movies.add(m);
			m.setId(rs.getInt(1));
			m.setTitle(rs.getString(2));
			m.setDirector(rs.getString(3));
			m.setYear(rs.getInt(4));
			m.setGenre(rs.getString(5));
		}
		return movies;
	}

	@RequestMapping("/search")
	public String search(HttpServletRequest req) {
		String chosen = req.getParameter("chosen");
		switch (chosen) {
			case "title": {
				searchByTitle(req); 
				break;
			}
			case "director": {
				searchByDirector(req); 
				break;
			}
			case "year": {
				searchByYear(req); 
				break;
			}
			case "genre": {
				searchByGenre(req); 
				break;
			}
		}
		return "search.jsp";
	}

	public void searchByTitle(HttpServletRequest req) {
		String title = req.getParameter("searchBar");
		try {
			Connection c = DriverManager.getConnection(db, username, password);
			req.setAttribute("movies", getMovies(c, "title", title));
			c.close();
		} catch (Exception e) {
			req.setAttribute("exception", e.getMessage());
		}
	}

	public void searchByDirector(HttpServletRequest req) {
		String director = req.getParameter("searchBar");
		try {
			Connection c = DriverManager.getConnection(db, username, password);
			req.setAttribute("movies", getMovies(c, "director", director));
			c.close();
		} catch (Exception e) {
			req.setAttribute("exception", e.getMessage());
		}
	}

	public void searchByGenre(HttpServletRequest req) {
		String genre = req.getParameter("searchBar");
		try {
			Connection c = DriverManager.getConnection(db, username, password);
			req.setAttribute("movies", getMovies(c, "genre", genre));
			c.close();
		} catch (Exception e) {
			req.setAttribute("exception", e.getMessage());
		}
	}

	public void searchByYear(HttpServletRequest req) {
		int year = Integer.parseInt(req.getParameter("searchBar"));
		try {
			Connection c = DriverManager.getConnection(db, username, password);
			req.setAttribute("movies", getMovies(c, "year", year));
			c.close();
		} catch (Exception e) {
			req.setAttribute("exception", e.getMessage());
		}
	}

	@RequestMapping("/edit")
	public String edit(HttpServletRequest req) {
		int id = Integer.parseInt(req.getParameter("id"));
		String title = req.getParameter("title");
		String director = (req.getParameter("director"));
		int year = Integer.parseInt(req.getParameter("year"));
		String genre = req.getParameter("genre");
		req.setAttribute("id", id);
		req.setAttribute("title", title);
		req.setAttribute("director", director);
		req.setAttribute("year", year);
		req.setAttribute("genre", genre);
		return "edit.jsp";
	}
	
	@RequestMapping("/editInfo")
	public String editInfo(HttpServletRequest req) {
		String title = req.getParameter("movieTitle");
		String director = req.getParameter("movieDirector");
		String year = req.getParameter("movieYear");
		String genre = req.getParameter("movieGenre");
		try {
			Connection c = DriverManager.getConnection(db, username, password);
			PreparedStatement ps = c.prepareStatement("update movie set title = ?, director = ?, year = ?, genre = ? where id = ? and user_id = ?");
			ps.setString(1, title);
            ps.setString(2, director);
			ps.setInt(3, Integer.parseInt(year));
			ps.setString(4, genre);
			ps.setInt(5, Integer.parseInt(req.getParameter("id")));
			ps.setInt(6, user_id);
			int n = ps.executeUpdate();
			if (n > 0){
				req.setAttribute("result", "Movie edited!");
			}
			ps.close();
			c.close();
		} catch (Exception e) {
			req.setAttribute("exception", e.getMessage());
		}
		return show(req);
	}

	@RequestMapping("/delete")
	public String delete(HttpServletRequest req){
		try {
			Connection c = DriverManager.getConnection(db, username, password);
			int id = Integer.parseInt(req.getParameter("id"));
			PreparedStatement ps = c.prepareStatement("delete from movie where id=? and user_id=?");
			ps.setInt(1, id);
			ps.setInt(2, user_id);
			int n = ps.executeUpdate();
			if (n > 0){
				req.setAttribute("result", "Movie deleted!");
			}
			ps.close();
			c.close();
		} catch (SQLException e) {
			req.setAttribute("exception", e.getMessage());
		}
		return show(req);
	}
	
	@RequestMapping("/addToFav")
	public String addToFav(HttpServletRequest req) {
		try {
			Connection c = DriverManager.getConnection(db, username, password);
			int id = Integer.parseInt(req.getParameter("id"));
			PreparedStatement ps = c.prepareStatement("update movie set favorite = true where id = ? and user_id=?");
			ps.setInt(1, id);
			ps.setInt(2, user_id);
			int n = ps.executeUpdate();
			if (n > 0){
				req.setAttribute("result", "Movie deleted!");
			}
			ps.close();
			c.close();
		} catch (Exception e) {
			req.setAttribute("exception", e.getMessage());
		}
		return favorites(req);
	}
	
	@RequestMapping("/favorites")
	public String favorites(HttpServletRequest req) {
		try {
			Connection c = DriverManager.getConnection(db, username, password);
			req.setAttribute("movies", getFavMovies(c));
			c.close();
		} catch (Exception e) {
			req.setAttribute("exception", e.getMessage());
		}
		return "favorites.jsp";
	}

	@RequestMapping("/removeFromFavorites")
	public String removeFromFavorites(HttpServletRequest req) {
		try {
			Connection c = DriverManager.getConnection(db, username, password);
			int id = Integer.parseInt(req.getParameter("id"));
			PreparedStatement ps = c.prepareStatement("update movie set favorite = false where id = ? and user_id = ?");
			ps.setInt(1, id);
			ps.setInt(2, user_id);
			int n = ps.executeUpdate();
			if (n > 0){
				req.setAttribute("result", "Movie removed from favs!");
			}
			ps.close();
			c.close();
		} catch (Exception e) {
			req.setAttribute("exception", e.getMessage());
		}
		return favorites(req);
	}

	@RequestMapping("/watched")
	public String watched(HttpServletRequest req) {
		try {
			Connection c = DriverManager.getConnection(db, username, password);
			req.setAttribute("movies", getWatchedMovies(c));
			c.close();
		} catch (Exception e) {
			req.setAttribute("exception", e.getMessage());
		}
		return "watched.jsp";
	}

	@RequestMapping("/removeFromWatched")
	public String removeFromWatched(HttpServletRequest req) {
		try {
			Connection c = DriverManager.getConnection(db, username, password);
			int id = Integer.parseInt(req.getParameter("id"));
			PreparedStatement ps = c.prepareStatement("update movie set watched = false where id = ? and user_id = ?");
			ps.setInt(1, id);
			ps.setInt(2, user_id);
			int n = ps.executeUpdate();
			if (n > 0){
				req.setAttribute("result", "Movie removed from watched!");
			}
			ps.close();
			c.close();
		} catch (Exception e) {
			req.setAttribute("exception", e.getMessage());
		}
		return watched(req);
	}

	@RequestMapping("/to-watch")
	public String toWatch(HttpServletRequest req) {
		try {
			Connection c = DriverManager.getConnection(db, username, password);
			req.setAttribute("movies", getToWatchMovies(c));
			c.close();
		} catch (Exception e) {
			req.setAttribute("exception", e.getMessage());
		}
		return "to-watch.jsp";
	}

	@RequestMapping("/addToWatched")
	public String addToWatched(HttpServletRequest req) {
		try {
			Connection c = DriverManager.getConnection(db, username, password);
			int id = Integer.parseInt(req.getParameter("id"));
			PreparedStatement ps = c.prepareStatement("update movie set watched = true where id = ? and user_id = ?");
			ps.setInt(1, id);
			ps.setInt(2, user_id);
			int n = ps.executeUpdate();
			if (n > 0){
				req.setAttribute("result", "Movie added to watched!");
			}
			ps.close();
			c.close();
		} catch (Exception e) {
			req.setAttribute("exception", e.getMessage());
		}
		return toWatch(req);
	}

	@RequestMapping("/randomMovie")
	public String randomMovie(HttpServletRequest req) {
		try {
			Connection c = DriverManager.getConnection(db, username, password);
			req.setAttribute("movie", getRandomMovie(c));
			c.close();
		} catch (Exception e) {
			req.setAttribute("exception", e.getMessage());
		}
		return toWatch(req);
	}	
}