package de.uni_potsdam.hpi.wfapp2011.proposals.server.services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.uni_potsdam.hpi.wfapp2011.clientcore.pageframe.ProcessInfo;
import de.uni_potsdam.hpi.wfapp2011.proposals.client.data.Comment;
import de.uni_potsdam.hpi.wfapp2011.proposals.client.data.Person;
import de.uni_potsdam.hpi.wfapp2011.proposals.client.interfaces.CommentInterface;
import de.uni_potsdam.hpi.wfapp2011.servercore.database.DbInterface;
import de.uni_potsdam.hpi.wfapp2011.servercore.database.SQLTableException;
import de.uni_potsdam.hpi.wfapp2011.servercore.logging.ProjectProposalLogger;
/**
 * CommentProvider implements the <code>CommentInterface</code>.
 * It handles DB communication concerning comments.
 * 
 * @author Katrin Honauer, Josefine Harzmann
 */
public class CommentProvider extends RemoteServiceServlet implements CommentInterface {

	private static final long serialVersionUID = 1L;
	ProjectProposalLogger logger = ProjectProposalLogger.getInstance();
	private UtilitiesDB helper = new UtilitiesDB();
	
	private DbInterface dbConnection;
	private String type, semester;
	private int year;

	private static String DATE_FORMAT = "dd/MM/yyyy";
	private static String TIME_FORMAT = "hh:mm";	
	private DateFormat df = new SimpleDateFormat(DATE_FORMAT);
	private DateFormat tf = new SimpleDateFormat(TIME_FORMAT);

	public CommentProvider() {		
		dbConnection = new DbInterface();
		// TODO: Replace with URL
		type = ProcessInfo.getTypeFromURL("");
		semester = ProcessInfo.getSemesterFromURL("");
		year = ProcessInfo.getYearFromURL("");
	}
	
	/**
	 * Creates comment out of parameters and saves it in DB.
	 * Returns the saved comment.
	 * 
	 * @param message, proposalid
	 * @return Comment
	 */
	public Comment submitComment(String message, int proposalId){

		Comment newComment = new Comment();	
		java.util.Date now = new java.util.Date();
		Timestamp sqlDate = new Timestamp(now.getTime());
		
		newComment.setMessage(message);
		newComment.setDate(now);
		newComment.setDateAndTimeString(df.format(now)+" um "+tf.format(now)+" Uhr");
		//TODO woher kommt Person + ID ?
		Person author = new Person("Matthias Weidlich", "matthias.weidlich@hpi.uni-potsdam.de");
		author.setId(21);
		newComment.setAuthor(author);
		try {
			dbConnection.connect(type, semester, year);	
			// find Id for new comment
			String queryMaxCommentId = "SELECT MAX(commentID) AS maxid "+
							    		"FROM COMMENTS";	
			ResultSet maxCommentIds = dbConnection.executeQueryDirectly(queryMaxCommentId);
			if (maxCommentIds.next()) {
				newComment.setId(maxCommentIds.getInt("maxid") + 1);
			}			
			// save comment in DB
			String qSaveComment = "INSERT INTO COMMENTS"+
									  "(COMMENTID, PROJECTID, MESSAGE, AUTHOR, DATE)"+
								  "VALUES ("+
								      newComment.getId()+","+
								      proposalId+",'"+
									  newComment.getMessage()+"',"+
									  newComment.getAuthor().getId()+",'"+
									  sqlDate+"'"+
								  ")";			
			dbConnection.executeUpdate(qSaveComment);	
			dbConnection.disconnect();			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (SQLTableException e) {
			e.printStackTrace();
		}		
		return newComment;
	}
	
	/**
	 * Collects comments with given proposalId 
	 * and returns list of comments.
	 * 
	 * @param proposalId
	 * @return List<Comment>
	 * @throws SQLTableException 
	 */
	public List<Comment> getComments(int proposalId) {
		
		List<Comment> comments = new ArrayList<Comment>();	
		String query = "SELECT * FROM COMMENTS "+
						   "WHERE PROJECTID = "+proposalId;	
		try {
			dbConnection.connect(type, semester, year);
			ResultSet resultset = dbConnection.executeQueryDirectly(query);
			if (resultset.next()) {
				do {
					Comment comment = new Comment();
					comment.setId(resultset.getInt("commentid"));
					comment.setMessage(resultset.getString("message"));
					comment.setAuthor(helper.getPerson(dbConnection, resultset.getInt("author")));
					Timestamp timestmp = resultset.getTimestamp("date");
					if (timestmp != null) {
						java.util.Date createdAt = new java.util.Date(timestmp.getTime());
						comment.setDate(createdAt);
						comment.setDateAndTimeString(df.format(createdAt) + " um " + tf.format(createdAt) + " Uhr");
					}
					comments.add(comment);
				} while (resultset.next());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (SQLTableException e) {
			e.printStackTrace();
		}
		dbConnection.disconnect();
		return comments;
	}
}
