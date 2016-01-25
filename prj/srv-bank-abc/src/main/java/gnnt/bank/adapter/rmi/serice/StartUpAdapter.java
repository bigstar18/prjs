package gnnt.bank.adapter.rmi.serice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class StartUpAdapter extends HttpServlet
{
  private static final long serialVersionUID = 1L;

  public void destroy()
  {
    super.destroy();
  }

  public void init()
    throws ServletException
  {
    System.out.println("-----------------------------------");
    AdapterServer as = new AdapterServer();
    as.startup();
    AdapterClient ac = new AdapterClient();
    ac.initAdapter();

    BufferedReader buf = new BufferedReader(new InputStreamReader(System.in));
    String str = "help";

    while (!"exit".equalsIgnoreCase(str)) {
      try
      {
        str = buf.readLine();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    System.exit(0);
  }
  private static String getHelp() {
    StringBuilder sb = new StringBuilder();
    String str = "\n";

    System.out.println(sb.toString());
    return sb.toString();
  }
}