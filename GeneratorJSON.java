package json.generator;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;



public class GenerateJSON {

	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/my_db";

	// Database credentials
	static final String USER = "root";
	static final String PASS = "";
	Connection conn = null;
	public static void main(String[] args) throws IOException {

		GenerateJSON g = new GenerateJSON();
		g.DBconnection();
		g.CreateStadium();
		g.CreateCountry();		
	}
	public void DBconnection() 
	{
		try 
		{		
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			if (conn != null)
				System.out.println("connected");			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
			
	}

	public void CreateStadium() throws IOException 
	{

		Statement stmt1 = null;
		PreparedStatement stmt2 = null;

		String sql1 = "select * from my_db.match_results Group by Stadium;";
		try {
				stmt1 = conn.createStatement();

				ResultSet rs = stmt1.executeQuery(sql1);

				BufferedWriter jsonfile2 = new BufferedWriter(new FileWriter("C:/Users/Manu/Documents/output/stadium.json", true));
				jsonfile2.write("[\n");
			while (rs.next()) {
				// System.out.println("Country_Name : "+rs.getString("Country_name"));
				// System.out.println("Population : "+rs.getInt("Population"));
				// System.out.println("No_of_WorldCup_Won : "+rs.getInt("No_of_WorldCup_Won"));
				// System.out.println("Manager : "+rs.getString("Manager"));

				jsonfile2.write("{\"Stadium\":\"" + rs.getString("Stadium")+ "\",\n");
				// if(MainFuntion.matchResultsMap.get(k2).get)
				jsonfile2.write("\"Stadium_city\":\""+ rs.getString("Host_city") + "\",\n");
				jsonfile2.write("\"Matches\":[\n");
				String sql2 = "select Team1,Team2,Team1score,Team2score,Date from my_db.match_results where Stadium =?";
				stmt2 = conn.prepareStatement(sql2);
				stmt2.setString(1, rs.getString("Stadium"));
				ResultSet rs2 = stmt2.executeQuery();
				while (rs2.next()) {
					jsonfile2.write("\t{\"Team1\":\"" + rs2.getString("Team1")
							+ "\",");
					jsonfile2.write("\t\"Team2\":\"" + rs2.getString("Team2")
							+ "\",");
					jsonfile2.write("\t\"Team1score\":"
							+ rs2.getString("Team1score") + ",");
					jsonfile2.write("\t\"Team2score\":"
							+ rs2.getString("Team2score") + ",");
					jsonfile2.write("\t\"Date\":\"" + rs2.getString("Date")
							+ "\"}" + ",");

				}
				jsonfile2.write("]}," + " ");

			}
			jsonfile2.write("]" + "\n");
			jsonfile2.close();
			File in = new File("C:/Users/Manu/Documents/output/stadium.json");		
			File out = new File("C:/Users/Manu/Documents/output/replacedstadium.json");
			File outt = new File("C:/Users/Manu/Documents/output/replacedstadium1.json");
			Replace.replace("},]},", "}]},", in, out);
			Replace.replace("}]}, ]", "}]}]", out, outt);
		}

		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
	public void CreateCountry() throws IOException
	{
		Statement stmt1 = null;
		PreparedStatement stmt2 = null;

		String sql1 = "select * from my_db.country;";
		try {
				stmt1 = conn.createStatement();

				ResultSet rs = stmt1.executeQuery(sql1);

				BufferedWriter jsonfile1 = new BufferedWriter(new FileWriter("C:/Users/Manu/Documents/output/country.json", true));
				jsonfile1.write("[\n");
			while (rs.next()) 
			{
				// System.out.println("Country_Name : "+rs.getString("Country_name"));
				// System.out.println("Population : "+rs.getInt("Population"));
				// System.out.println("No_of_WorldCup_Won : "+rs.getInt("No_of_WorldCup_Won"));
				// System.out.println("Manager : "+rs.getString("Manager"));

				jsonfile1.write("{\"Country_Name\":\"" + rs.getString("Country_Name")+ "\",\n");
				// if(MainFuntion.matchResultsMap.get(k2).get)
				jsonfile1.write("\"Population\":\""+ rs.getString("Population") + "\",\n");
				jsonfile1.write("\"No_of_WorldCup_won\":\""+ rs.getString("No_of_WorldCup_won") + "\",\n");
				jsonfile1.write("\"Manager\":\""+ rs.getString("Manager") + "\",\n");
				//jsonfile1.write("\"Capital\":\""+ rs.getString("Capital") + "\",\n");

				String sql2 = "select distinct p.Player_id,p.Lname,p.Fname,p.Height,p.DOB,p.Is_captain,p.Position,coalesce(pc.No_of_Yellow_cards, 0) as No_of_Yellow_cards ,coalesce(pc.No_of_Red_cards, 0) as No_of_Red_cards,coalesce(pag.Goals, 0) as Goals,coalesce(pag.Assists,0) as Assists from players p left join player_card pc on p.Player_id=pc.Player_id left join player_assists_goals pag on p.Player_id=pag.Player_id where Country = ?";
				//String sql3 = "SELECT Year, Host FROM my_db.world_cup_history where Winner like ?";
		
				stmt2 = conn.prepareStatement(sql2);
				stmt2.setString(1, rs.getString("Country_Name"));
				ResultSet rs2 = stmt2.executeQuery();
				
//				stmt3 = conn.prepareStatement(sql2);
//				stmt3.setString(1, rs.getString("Country_Name"));
//				ResultSet rs3 = stmt2.executeQuery();
//				
//				stmt4 = conn.prepareStatement(sql2);
//				stmt4.setString(1, rs2.getString("Player_id"));
//				ResultSet rs4 = stmt2.executeQuery();
				jsonfile1.write("\"Players\":[\n");
				while(rs2.next())
				{
					jsonfile1.write("{");
					jsonfile1.write("\"Lname\":\""+rs2.getString("Lname")+ "\",\n");
					jsonfile1.write("\"Fname\":\""+rs2.getString("Fname")+ "\",\n");
					jsonfile1.write("\"Height\":"+rs2.getString("Height")+ ",\n");
					jsonfile1.write("\"DOB\":\""+rs2.getString("DOB")+ "\",\n");
					//jsonfile1.write("\"Is_captain\":\""+rs2.getString("Is_captain")+ "\",\n");
					jsonfile1.write("\"Position\":\""+rs2.getString("Position")+ "\",\n");
					jsonfile1.write("\"No_of_Yellow_cards\":\""+rs2.getString("No_of_Yellow_cards")+ "\",\n");
					jsonfile1.write("\"No_of_Red_cards\":\""+rs2.getString("No_of_Red_cards")+ "\",\n");
					jsonfile1.write("\"Goals\":\""+rs2.getString("Goals")+ "\",\n");
					jsonfile1.write("\"Assists\":\""+rs2.getString("Assists")+ "\" },");
					
				}
				jsonfile1.write("]}, ");
//				while(rs3.first())
//				{
//					jsonfile1.write("\"WorldCups_Won\":[\n");
//					jsonfile1.write("\"Year\":\""+rs3.getString("Year")+"\",\n");
//					jsonfile1.write("\"Host\":\""+rs3.getString("Host")+"\",\n");
//				}
			}
			jsonfile1.write("]");
			jsonfile1.close();
			File in = new File("C:/Users/Manu/Documents/output/country.json");		
			File out = new File("C:/Users/Manu/Documents/output/replacedcountry.json");
			Replace.replace("},]},", "}]},", in, out);
			//File outer = new File("C:/Users/Manu/Documents/output/replacedcountry1.json");
			//Replace.replace("}]}, ]", "}]}]", in, outer);
		}

		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
