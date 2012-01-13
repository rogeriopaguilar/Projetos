
<%@ page import="java.sql.*, javax.sql.*, javax.naming.*"%>

<%
DataSource ds;
Connection connection = null;
      try
      {
      InitialContext ctx = new InitialContext();
      ds = (DataSource) ctx.lookup("java:comp/env/jdbc/dnsec");
      connection = ds.getConnection ();
	out.println("TESTE");
      }
      catch (NamingException e)
      {
      e.printStackTrace();
      }
      catch (SQLException e)
      {
      e.printStackTrace();
      }
%>