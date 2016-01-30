<%@   page contentType="image/jpeg"
    import="java.awt.*,java.awt.image.*,java.util.*,javax.imageio.*"%>
<%!Color getRandColor(int fc, int bc) {
        Random random = new Random();
        if (fc > 255)
            fc = 255;
        if (bc > 255)
            bc = 255;
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }
%>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);

    int width = 75, height = 20;
    BufferedImage image = new BufferedImage(width, height,
            BufferedImage.TYPE_INT_RGB);

    Graphics g = image.getGraphics();
    Random random = new Random();

    g.setColor(getRandColor(200, 250));
    g.fillRect(0, 0, width, height);

    g.setFont(new Font("Times   New   Roman", Font.PLAIN, 18));

    g.setColor(getRandColor(160, 200));
    for (int i = 0; i < 155; i++) {
        int x = random.nextInt(width);
        int y = random.nextInt(height);
        int xl = random.nextInt(10);
        int yl = random.nextInt(10);
        g.drawLine(x, y, x + xl, y + yl);
    }

    char c[] = new char[62];

    for (int i = 97, j = 0; i < 123; i++, j++) {
    	if(i==105||i==108||i==111)
    	{
    		c[j] = (char) (i+1);
    	}
    	else
    	{
    		c[j] = (char) i;	
    	}
    }
    for (int o = 65, p = 26; o < 91; o++, p++) {
    	if(o==73||o==79||o==76)
    	{
    		c[p] = (char) (o+1);
    	}
    	else
    	{
           c[p] = (char) o;
    	}
    }
    for (int m = 48, n = 52; m < 58; m++, n++) {
    	if(m==48)
    	{
    		c[n] = (char) (m+1);	
    	}else
    	{
            c[n] = (char) m;
    	}
    }
    String sRand = "";
    for (int i = 0; i < 5; i++) {
        int x = random.nextInt(62);
        String rand = String.valueOf(c[x]);
        sRand += rand;

        g.setColor(new Color(20 + random.nextInt(110), 20 + random
        .nextInt(110), 20 + random.nextInt(110)));
        g.drawString(rand, 13 * i + 6, 16);
    }

    
    session.setAttribute("RANDOMICITYNUM", sRand);
    g.dispose();
    //ImageIO.setUseCache(true);
    try{
    ImageIO.write(image, "JPEG", response.getOutputStream());
    }catch(Exception e)
    {
    	
    }
    out.clear();
    out  =  pageContext.pushBody();
%>
