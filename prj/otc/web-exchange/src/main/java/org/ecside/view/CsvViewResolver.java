package org.ecside.view;

import java.io.ByteArrayOutputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.ecside.core.Preferences;

public class CsvViewResolver
  implements ViewResolver
{
  public void resolveView(ServletRequest request, ServletResponse response, Preferences preferences, Object viewData)
    throws Exception
  {
    if (viewData != null)
    {
      ByteArrayOutputStream out = (ByteArrayOutputStream)viewData;
      byte[] contents = out.toByteArray();
      response.setContentLength(contents.length);
      response.getOutputStream().write(contents);
    }
  }
}
