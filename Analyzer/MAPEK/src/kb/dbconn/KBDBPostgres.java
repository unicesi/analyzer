package kb.dbconn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

public class KBDBPostgres {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ArrayList getDataBaseInformation(String sql) {

		ArrayList result = new ArrayList();

		// Configuration Variables
		String urlConn = "jdbc:postgresql://localhost:5432/postgres";
		String user = "postgres";
		String pass = "postgres";

		// System.out
		// .println("Checking if Driver is registered with DriverManager.");

		try {
			Class.forName("org.postgresql.Driver");

		} catch (ClassNotFoundException cnfe) {
			System.out.println("Couldn't find the driver!");
			System.out.println("Let's print a stack trace, and exit.");
			cnfe.printStackTrace();
			System.exit(1);
		}

		// System.out.println("Registered the driver ok, so let's make a connection.");
		Connection c = null;

		try {
			// The second and third arguments are the username and password,
			// respectively. They should be whatever is necessary to connect
			// to the database.
			c = DriverManager.getConnection(urlConn, user, pass);
		} catch (SQLException se) {
			System.out
					.println("Couldn't connect: print out a stack trace and exit.");
			se.printStackTrace();
			System.exit(1);
		}

		if (c != null) {
			// System.out.println("Hooray! We connected to the database!");

			Statement s = null;
			try {
				s = c.createStatement();
			} catch (SQLException se) {
				System.out
						.println("We got an exception while creating a statement:"
								+ "that probably means we're no longer connected.");
				se.printStackTrace();
				System.exit(1);
			}

			ResultSet rs = null;
			try {

				rs = s.executeQuery(sql);
				while (rs.next()) {
					result.add(new String[] { rs.getString(1), rs.getString(2) });
					// System.out.println("Row added");
				}
				s.close();
			} catch (SQLException se) {
				System.out
						.println("We got an exception while executing our query:"
								+ "that probably means our SQL is invalid");
				se.printStackTrace();
				System.exit(1);
			}

		} else
			System.out.println("We should never get here.");

		return result;
	}

	public void saveInHistory(long start, long end, double diff) {
		String urlConn = "jdbc:postgresql://localhost:5432/postgres";
		String user = "postgres";
		String pass = "postgres";
		try {
			Class.forName("org.postgresql.Driver");

		} catch (ClassNotFoundException cnfe) {
			System.out.println("Couldn't find the driver!");
			System.out.println("Let's print a stack trace, and exit.");
			cnfe.printStackTrace();
			System.exit(1);
		}

		Connection c = null;
		try {
			c = DriverManager.getConnection(urlConn, user, pass);
		} catch (SQLException se) {
			System.out
					.println("Couldn't connect: print out a stack trace and exit.");
			se.printStackTrace();
			System.exit(1);
		}
		if (c != null) {
			Statement s = null;
			try {
				s = c.createStatement();
			} catch (SQLException se) {
				System.out
						.println("We got an exception while creating a statement:"
								+ "that probably means we're no longer connected.");
				se.printStackTrace();
				System.exit(1);
			}
			String qoscareOp = "Sales";
			String sql = "insert into \"QoSCareOperationsLog\" "
					+ "(attendant_username, operation_date_ini, operation_time_ini, operation_time_end, time_diff_millis, qoscare_operation, operation_date_end) " + 
					"values('postgres','"+getDate(start)+"',"+ start + "," + end + "," + diff + ",'" + qoscareOp + "','" + getDate(end)+"')";
			try {
				s.execute(sql);
				s.close();
			} catch (SQLException se) {
				System.out
						.println("We got an exception while executing our query:"
								+ "that probably means our SQL is invalid");
				se.printStackTrace();
//				System.out.println(sql);
				System.exit(1);
			}
			finally {
				try {
					c.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else
			System.out.println("We should never get here.");
	}

	private String getDate(long time) {
		long current = TimeUnit.NANOSECONDS.convert(Calendar.getInstance().getTimeInMillis(), TimeUnit.MILLISECONDS);
		
		long dif = current - time;
		
		long nanoCurrent = time + dif;
		
		Date date = new Date(TimeUnit.MILLISECONDS.convert(nanoCurrent, TimeUnit.NANOSECONDS));
		
		GregorianCalendar gcad = new GregorianCalendar();
		
		gcad.setTime(date);
		
//		System.out.println(gcad.get(3)+"/"+(gcad.get(2)+1)+"/"+gcad.get(1));
//		return gcad.get(3)+"-"+(gcad.get(2)+1)+"-"+gcad.get(1);
		return "5-"+(gcad.get(2)+1)+"-"+gcad.get(1);
	}


	public double[][] getHistorics() {
		String urlConn = "jdbc:postgresql://localhost:5432/postgres";
		String user = "postgres";
		String pass = "postgres";
		try {
			Class.forName("org.postgresql.Driver");

		} catch (ClassNotFoundException cnfe) {
			System.out.println("Couldn't find the driver!");
			System.out.println("Let's print a stack trace, and exit.");
			cnfe.printStackTrace();
			System.exit(1);
		}

		Connection c = null;
		try {
			c = DriverManager.getConnection(urlConn, user, pass);
		} catch (SQLException se) {
			System.out
					.println("Couldn't connect: print out a stack trace and exit.");
			se.printStackTrace();
			System.exit(1);
		}
		if (c != null) {
			Statement s = null;
			try {
				s = c.createStatement();
			} catch (SQLException se) {
				System.out
						.println("We got an exception while creating a statement:"
								+ "that probably means we're no longer connected.");
				se.printStackTrace();
				System.exit(1);
			}
			try {
				//TODO Consulta no necesario, llamado funcion
				String sql = "select operation_date_ini, count(*) from \"QoSCareOperationsLog\" group by operation_date_ini";
				ResultSet rs = s.executeQuery(sql);

				ArrayList<Double> data = new ArrayList<Double>();

				while (rs.next()) {
					String temp = rs.getString(1);
//					System.out.println(temp);
					double monitor_time = Double.parseDouble(temp.split("-")[2]);
					double throughput = rs.getDouble(2);
					data.add(monitor_time);
					data.add(throughput);
				}
				s.close();
				if (!data.isEmpty()) {
					int tamanio = data.size() / 2;
					double[][] retorno = new double[tamanio][2];
					for (int i = 0; i < tamanio; i++) {
						retorno[i][0] = data.get(i*2);
						retorno[i][1] = data.get(i*2+1);
						
						System.out.println("[KB Historics Service] - Time = "+retorno[i][0]+" Throughput = "+retorno[i][1]);
					}

					return retorno;
				}

			} catch (SQLException se) {
				System.out
						.println("We got an exception while executing our query:"
								+ "that probably means our SQL is invalid");
				se.printStackTrace();
				System.exit(1);
			}

			
		}
		return null;
	}
}
