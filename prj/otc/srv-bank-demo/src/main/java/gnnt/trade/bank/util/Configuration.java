/*******************************************************************
 * CodeTemplate.java   04/12/31
 * Copyright 2004 by GNNT Inc. All Rights Reserved.
 * Author:chenjian
 * 
 ******************************************************************/
package gnnt.trade.bank.util;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;
import java.util.Hashtable;
import java.util.Properties;
import java.io.*;
import java.net.*;
/**
 * 这个类是用来读取xml文件以将所有的配置信息装载。<br>
 * xml文件的地址可以在构造时指定，默认是去寻找classpath下的根目录中的gnnt.properties中的configPath所对应的属性。<br>
 * 方法getConfigInfo，得到一个包含所有配置信息的哈希表；方法getSection，可以得到某段特定的属性。<br>
 */
public class Configuration
{
    /** 
     * 用来显示配置信息是否已装载.<br>
     */
    private static boolean initFlag;

    /** 
     * 用来存放配置信息.<br>
     */
    private static Hashtable<String,Properties> configInfo =new Hashtable<String,Properties>();

    private Properties propertyInfo;

    /**  
     * 用来存放xml文件的路径.<br>
     */
    private String cPath;

    /**  
     * 用来判断当前要解析xml文件是否是最外层的xml文件.<br>
     */
    private boolean ConfigFlag;

    /** 
     * 当link到其他xml文件时,存放已取得的name.<br>
     */
    private String CompName;

    /**  
     * 用来做静态锁.
     */
    private static Object xmlConfigA=new Object();
    
    /**  
     * 用来做静态锁.
     */
    private static Object xmlConfigB=new Object();
         
    /**
     * 构造函数，用以初始化此类, 程序赋予默认路径.   
     */
    public Configuration()
    {  
        this(getDefaultPath());     
    }

    /**
     * 构造函数，用以初始化此类, 由参数中得到路径.   
     */
    public Configuration(String path)
    {       
        
        synchronized(xmlConfigA)
        {
            cPath=path;
            if (!initFlag)
            {
                
                if (resetConfigInfo()==0)
                {
                    initFlag=true;
                }               
            }
        }       
    }

    /**
     * 此方法用以返回一个包含配置信息的Hashtable.<br>
     * <b>返回值：</b>返回一个包含配置信息的Hashtable.
     */
    public Hashtable<String,Properties> getConfigInfo()
    {       
        return configInfo;
    }

    /**
     * 此方法用以返回一个包含部分配置信息的Properties.<br>
     * <b>参  数:</b><ul>
     *          sectionPath:String形参数.<br>   
     * </ul>
     * <b>返回值:</b>返回一个包含部分配置信息的Properties.
     */
    public Properties getSection(String sectionPath)
    {
        propertyInfo = (Properties)configInfo.get(sectionPath);
        return propertyInfo;
    }

    /**
     * 此方法用以将配置信息放入到Hashtable中.<br>  
     * <b>返回值:</b>返回一个int形.0代表运行正常,1代表有错误发生.
     */
    public int resetConfigInfo()
    {   
        synchronized(xmlConfigB)
        {
            int IsParseT=10;
           
            IsParseT=parseXML();
            
            if (IsParseT!=0)
            {               
                return 1;
            }
            return 0;
        }
    }   

    /**
     * 此方法对xml文件进行解析.<br>    
     * <b>返回值:</b>返回一个int形.0代表运行正常,1代表有错误发生.
     */
    protected int parseXML()
    {
        Document doc = null;
        
        try
        {
            //按层次进行解析，这里先通过Factory把xml读进来
            DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();
            DocumentBuilder db=dbf.newDocumentBuilder();
            if (cPath==null||cPath.equals(""))
            {
                System.out.println("ERROR:File .xml is null or not equals any value");
                return 1;
            }
           // System.out.println(cPath);
            File f=new File(cPath);
            doc=db.parse(f);                       
            NodeList comp = null;
            //得到Component,情况分是最外层的xml和不是两种
            if (!ConfigFlag)
            {
                NodeList conf = doc.getChildNodes();
                for (int i = 0; i < conf.getLength(); i++)
                {   
                    if(!conf.item(0).getNodeName().toUpperCase().equals("CONFIG"))
                    {
                        System.out.println("ERROR:Some tags are in wrong location with CONFIG in "+cPath);
                        return 1;
                    }                   
                }
                ConfigFlag=true;
                comp = conf.item(0).getChildNodes();
            }
            else
            {
                comp = doc.getChildNodes();
            }
            
            for (int j=0; j< comp.getLength() ; j++)
            {           
                //对COMPONENT进行分析，其他的节点不去理睬
                if (comp.item(j).getNodeName().toUpperCase().equals("COMPONENT"))
                {
                    
                    NodeList children = comp.item(j).getChildNodes();
                    //将会存入到Hashtable中做为key
                    String name=null;
                    //用来判断COMPONENT的类型
                    String type=null;   
                    //用来防止出现两次name
                    boolean IsNamed=false;
                    //用来防止出现两次type
                    boolean IsTyped=false;

                    for(int k=0 ;k<children.getLength() ; k++)
                    {
                        String tagName=children.item(k).getNodeName();
                        
                        if (tagName.toUpperCase().equals("NAME"))
                        {
                            if (IsNamed)
                            {
                                System.out.println("ERROR:The name appeared twice in "+cPath);
                                return 1;
                            }
                            name=children.item(k).getFirstChild().getNodeValue();
                            //如果是嵌套在COMPONENT中的COMPONENT的类型是link，则CompName就不会为空
                            if (CompName!=null)
                            {
                                name=CompName+"."+name;
                            }
                            IsNamed=true;                       
                        }
                        else
                        if(tagName.toUpperCase().equals("TYPE"))
                        {
                            if (IsTyped)
                            {
                                System.out.println("ERROR:The type appeared twice in "+cPath);
                                return 1;
                            }
                            type=children.item(k).getFirstChild().getNodeValue();
                            
                            if (type.toLowerCase().equals("link"))
                            {       
                                //如果name为空，就没有文件名
                                if (name==null)
                                {
                                    System.out.println("ERROR:The link filename is not assigned");
                                    return 1;
                                }
                                //得到的cPath是物理路径
                                cPath=vPathToFPath()+"\\"+name;
                                //自调用
                                if(parseXML()!=0)
                                {
                                    System.out.println("ERROR:The link "+name+" in "+cPath+" is found wrong");
                                    return 1;
                                }
                            }
                            IsTyped=true;           
                        }
                        else
                        //存放信息的地方，section的个数就是Hashtable中存放的实际信息的个数
                        if(tagName.toUpperCase().equals("SECTION"))
                        {
                            //如果name或type为空就出错
                            if (name==null||type==null)
                            {                               
                                System.out.println("ERROR:The component's name is not known in "+cPath);
                                return 1;
                            }
                            NodeList sec = children.item(k).getChildNodes();
                            //得到SECTION的名字
                            String sname=null;
                            //存放Property
                            Properties pr=new Properties();
                            for (int n=0; n<sec.getLength() ; n++)
                            {
                                String secName=sec.item(n).getNodeName();
                                //一个SECTION应该对应一个NAME和PROPERTYS，多个的话不会造成影响
                                if (secName.toUpperCase().equals("NAME"))
                                {
                                    sname=name+"."+sec.item(n).getFirstChild().getNodeValue();
                                    
                                }
                                
                                else if (secName.toUpperCase().equals("PROPERTYS"))
                                {
                                    
                                    NodeList prop = sec.item(n).getChildNodes();
                                    for (int m=0;m<prop.getLength() ; m++)
                                    {
                                        String pname=prop.item(m).getNodeName();
                                        if (pname.toUpperCase().equals("PROPERTY"))
                                        {
                                            NodeList kv=prop.item(m).getChildNodes();
                                            String key=null;
                                            String value=null;
                                            for(int l=0;l<kv.getLength();l++)
                                            {
                                                String kname=kv.item(l).getNodeName();
                                                
                                                if (kname.toUpperCase().equals("KEY"))
                                                {
                                                    key=kv.item(l).getFirstChild().getNodeValue();                                                  
                                                }

                                                else if (kname.toUpperCase().equals("VALUE"))
                                                {
                                                    value=kv.item(l).getFirstChild().getNodeValue();                                                    
                                                }                                                   
                                            }
                                            if (key==null||value==null)
                                            {
                                                System.out.println("ERROR:Section "+sname+" has null key or null value in "+cPath);
                                                return 1;
                                            }
                                            try
                                            {
                                                pr.setProperty(key,value);
                                            }
                                            catch(NullPointerException e)
                                            {
                                                System.out.println("ERROR:When you set the property "+key+" in section "+sname+" in "+cPath);
                                            }
                                        }
                                        
                                    }
                                }
                            }                           
                            try
                            {                               
                                configInfo.put(sname,pr);
                            }
                            catch(NullPointerException e)
                            {
                                System.out.println("ERROR:When you set the section "+sname+" into the hashtable in "+cPath);
                            }                           
                        }
                        else
                        //如果嵌套COMPONENT去调用parseComponent，程序极其相似，但是考虑到循环调用，单独作为一个方法
                        if(tagName.toUpperCase().equals("COMPONENT"))
                        {
                            int pc=parseComponent(name,children.item(k).getChildNodes());
                            if (pc!=0)
                            {
                                System.out.println("ERROR:False is in the inner component in "+cPath);
                                return pc;
                            }
                        }                                           
                    }
                }               
            }           
        }
        catch(FileNotFoundException e)
        {            
            System.out.println("ERROR:File "+cPath+" not found");
            return 1;
        }
        catch(IOException e)
        {            
            System.out.println("ERROR:There is something wrong with inputStream");      
            return 1;
        }
        catch(javax.xml.parsers.ParserConfigurationException e)
        {            
            System.out.println("ERROR:There is something wrong with Parsing xml");  
            return 1;
        }
        catch(org.xml.sax.SAXException e)
        {            
            System.out.println("ERROR:There is something wrong with Parsing xml");  
            return 1;
        }
        return 0;
    }   

    /**
     * 此方法对xml文件中的嵌套Component进行解析.<br>  
     * <b>参  数:</b><ul>
     *          cname:String形参数.<br>     
     *          children:NodeList形参数.<br>
     * </ul>
     * <b>返回值:</b>返回一个int形.0代表运行正常,1代表有错误发生.
     */
    protected int parseComponent(String cname,NodeList children)
    {       
        
        String name=null;
        String type=null;
        //得到name节点中的text
        String linkName=null;
        //用来防止出现两次name
        boolean IsNamed=false;
        //用来防止出现两次type
        boolean IsTyped=false;

        for(int k=0 ;k<children.getLength() ; k++)
        {
            String tagName=children.item(k).getNodeName();          
            if (tagName.toUpperCase().equals("NAME"))
            {
                if (IsNamed)
                {
                    System.out.println("ERROR:The name appeared twice in "+cPath);
                    return 1;
                }
                name=cname+"."+children.item(k).getFirstChild().getNodeValue();
                linkName=children.item(k).getFirstChild().getNodeValue();
                IsNamed=true;                   
            }
            else
            if(tagName.toUpperCase().equals("TYPE"))
            {
                if (IsTyped)
                {
                    System.out.println("ERROR:The type appeared twice in "+cPath);
                    return 1;
                }
                type=children.item(k).getFirstChild().getNodeValue();
                
                if (type.toLowerCase().equals("link"))
                {           
                    if (linkName==null)
                    {
                        System.out.println("ERROR:The link filename is not assigned in "+cPath);
                        return 1;
                    }
                    
                    cPath=vPathToFPath()+"\\"+linkName;
                    name=cname;
                    CompName=name;
                    if(parseXML()!=0)
                    {
                        System.out.println("ERROR:The link "+name+" in "+cPath+" is found wrong");
                        return 1;
                    }
                }
                IsTyped=true;           
            }
            else
            if(tagName.toUpperCase().equals("SECTION"))
            {
                if (name==null||type==null)
                {                               
                    System.out.println("ERROR:The component's name is not known in "+cPath);
                    return 1;
                }
                NodeList sec = children.item(k).getChildNodes();    
                String sname=null;
                Properties pr=new Properties();
                for (int n=0; n<sec.getLength() ; n++)
                {
                    String secName=sec.item(n).getNodeName();
                    
                    if (secName.toUpperCase().equals("NAME"))
                    {
                        sname=name+"."+sec.item(n).getFirstChild().getNodeValue();
                        
                    }
                    else if (secName.toUpperCase().equals("PROPERTYS"))
                    {
                        
                        NodeList prop = sec.item(n).getChildNodes();
                        for (int m=0;m<prop.getLength() ; m++)
                        {
                            String pname=prop.item(m).getNodeName();
                            if (pname.toUpperCase().equals("PROPERTY"))
                            {
                                NodeList kv=prop.item(m).getChildNodes();
                                String key=null;
                                String value=null;
                                for(int l=0;l<kv.getLength();l++)
                                {
                                    String kname=kv.item(l).getNodeName();
                                    
                                    if (kname.toUpperCase().equals("KEY"))
                                    {
                                        key=kv.item(l).getFirstChild().getNodeValue();                                                  
                                    }

                                    else if (kname.toUpperCase().equals("VALUE"))
                                    {
                                        value=kv.item(l).getFirstChild().getNodeValue();                                                    
                                    }                                                   
                                }
                                if (key==null||value==null)
                                {
                                    System.out.println("ERROR:Section "+sname+" has null key or null value in "+cPath);
                                    return 1;
                                }
                                try
                                {
                                    pr.setProperty(key,value);
                                }
                                catch(NullPointerException e)
                                {
                                    System.out.println("ERROR:When you set the property "+key+" in section "+sname+" in "+cPath);
                                    return 1;
                                }
                            }
                            
                        }
                    }
                }                           
                try
                {                               
                    configInfo.put(sname,pr);
                }
                catch(NullPointerException e)
                {
                    System.out.println("ERROR:When you set the section "+sname+" into the hashtable"+" in "+cPath);
                    return 1;
                }               
            }
            else
            if(tagName.toUpperCase().equals("COMPONENT"))
            {
                int pc=parseComponent(name,children.item(k).getChildNodes());
                if (pc!=0)
                {
                    System.out.println("ERROR:False is in the inner component"+" in "+cPath);
                    return pc;
                }
            }   
        }
        return 0;
    }

    /**
     * 此方法将虚拟路径转化为物理路径.<br>  
     * <b>返回值:</b>返回一个String形.
     */
    private String vPathToFPath()
    {       
        int a=cPath.lastIndexOf("\\");      
        cPath=cPath.substring(0,a);     
        return cPath;
    }

    /**
     * 此方法用来得到classpath中的properties文件中的xml的默认路径.<br>     
     * <b>返回值:</b>返回一个String形.
     */
    private static String getDefaultPath()
    { 
        String configPath="";
        try
        {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            URL url1=loader.getResource("BANK.xml");
            if(url1==null)
            {
                throw new Exception("没有找到xml文件");
            }
            else
            {
                configPath=url1.getPath();
            }
            configPath=URLDecoder.decode(configPath,"UTF-8");
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.out.println("ERROR:When you get default path");
        }
        return configPath;
    }   
}