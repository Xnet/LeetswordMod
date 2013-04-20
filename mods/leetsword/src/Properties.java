package mods.leetsword.src;

import java.io.*;
import java.util.*;

public final class Properties
{
    private String fileName;
    private List lines;
    private Map props;

    public Properties(String s)
    {
        lines = new ArrayList();
        props = new HashMap();
        fileName = s;
        File file = new File(fileName);
        if (file.exists())
        {
            try
            {
                load();
            }
            catch (IOException ioexception)
            {
                System.out.println((new StringBuilder()).append("[Props] Unable to load ").append(fileName).append("!").toString());
            }
        }
        else
        {
            save();
        }
    }

    public void load()
    throws IOException
    {
        BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF8"));
        lines.clear();
        props.clear();
        String s;
        while ((s = bufferedreader.readLine()) != null)
        {
            s = new String(s.getBytes(), "UTF-8");
            char c = '\0';
            int i;
            for (i = 0; i < s.length() && Character.isWhitespace(c = s.charAt(i)); i++) { }
            if (s.length() - i == 0 || s.charAt(i) == '#' || s.charAt(i) == '!')
            {
                lines.add(s);
            }
            else
            {
                int j = i;
                boolean flag = s.indexOf('\\', i) != -1;
                StringBuffer stringbuffer = flag ? new StringBuffer() : null;
                if (stringbuffer != null)
                {
                    do
                    {
                        if (i >= s.length() || Character.isWhitespace(c = s.charAt(i++)) || c == '=' || c == ':')
                        {
                            break;
                        }
                        if (flag && c == '\\')
                        {
                            if (i == s.length())
                            {
                                s = bufferedreader.readLine();
                                if (s == null)
                                {
                                    s = "";
                                }
                                i = 0;
                                while (++i < s.length() && Character.isWhitespace(c = s.charAt(i))) ;
                            }
                            else
                            {
                                c = s.charAt(i++);
                            }
                        }
                        else
                        {
                            switch (c)
                            {
                                case 110:
                                    stringbuffer.append('\n');
                                    break;

                                case 116:
                                    stringbuffer.append('\t');
                                    break;

                                case 114:
                                    stringbuffer.append('\r');
                                    break;

                                case 117:
                                    if (i + 4 <= s.length())
                                    {
                                        char c5 = (char)Integer.parseInt(s.substring(i, i + 4), 16);
                                        stringbuffer.append(c5);
                                        i += 4;
                                    }
                                    break;

                                case 111:
                                case 112:
                                case 113:
                                case 115:
                                default:
                                    stringbuffer.append('\0');
                                    break;
                            }
                        }
                    }
                    while (true);
                }
                boolean flag1 = c == ':' || c == '=';
                String s1;
                if (flag)
                {
                    s1 = stringbuffer.toString();
                }
                else if (flag1 || Character.isWhitespace(c))
                {
                    s1 = s.substring(j, i - 1);
                }
                else
                {
                    s1 = s.substring(j, i);
                }
                for (; i < s.length() && Character.isWhitespace(c = s.charAt(i)); i++) { }
                if (!flag1 && (c == ':' || c == '='))
                {
                    char c1;
                    for (i++; i < s.length() && Character.isWhitespace(c1 = s.charAt(i)); i++) { }
                }
                if (!flag)
                {
                    lines.add(s);
                }
                else
                {
                    StringBuilder stringbuilder = new StringBuilder(s.length() - i);
                    do
                    {
                        if (i >= s.length())
                        {
                            break;
                        }
                        char c2 = s.charAt(i++);
                        if (c2 == '\\')
                        {
                            if (i == s.length())
                            {
                                s = bufferedreader.readLine();
                                if (s == null)
                                {
                                    break;
                                }
                                char c3;
                                for (i = 0; i < s.length() && Character.isWhitespace(c3 = s.charAt(i)); i++) { }
                                stringbuilder.ensureCapacity((s.length() - i) + stringbuilder.length());
                                continue;
                            }
                            char c4 = s.charAt(i++);
                            switch (c4)
                            {
                                case 110:
                                    stringbuilder.append('\n');
                                    break;

                                case 116:
                                    stringbuilder.append('\t');
                                    break;

                                case 114:
                                    stringbuilder.append('\r');
                                    break;

                                case 117:
                                    if (i + 4 <= s.length())
                                    {
                                        char c6 = (char)Integer.parseInt(s.substring(i, i + 4), 16);
                                        stringbuilder.append(c6);
                                        i += 4;
                                        break;
                                    }
                                    continue;

                                case 111:
                                case 112:
                                case 113:
                                case 115:
                                default:
                                    stringbuilder.append('\0');
                                    break;
                            }
                        }
                        stringbuilder.append('\0');
                    }
                    while (true);
                    lines.add((new StringBuilder()).append(s1).append("=").append(stringbuilder.toString()).toString());
                }
            }
        }
        bufferedreader.close();
    }

    public void save()
    {
        FileOutputStream fileoutputstream = null;
        try
        {
            fileoutputstream = new FileOutputStream(fileName);
        }
        catch (FileNotFoundException filenotfoundexception)
        {
            System.out.println((new StringBuilder()).append("[Props] Unable to open ").append(fileName).append("!").toString());
        }
        PrintStream printstream = null;
        try
        {
            printstream = new PrintStream(fileoutputstream, true, "UTF-8");
        }
        catch (UnsupportedEncodingException unsupportedencodingexception)
        {
            System.out.println((new StringBuilder()).append("[Props] Unable to write to ").append(fileName).append("!").toString());
        }
        ArrayList arraylist = new ArrayList();
        for (Iterator iterator = lines.iterator(); iterator.hasNext();)
        {
            String s = (String)iterator.next();
            if (s.trim().length() == 0)
            {
                printstream.println(s);
            }
            else if (s.charAt(0) == '#')
            {
                printstream.println(s);
            }
            else if (s.contains("="))
            {
                int i = s.indexOf('=');
                String s1 = s.substring(0, i).trim();
                if (props.containsKey(s1))
                {
                    String s2 = (String)props.get(s1);
                    printstream.println((new StringBuilder()).append(s1).append("=").append(s2).toString());
                    arraylist.add(s1);
                }
                else
                {
                    printstream.println(s);
                }
            }
            else
            {
                printstream.println(s);
            }
        }

        Iterator iterator1 = props.entrySet().iterator();
        do
        {
            if (!iterator1.hasNext())
            {
                break;
            }
            java.util.Map.Entry entry = (java.util.Map.Entry)iterator1.next();
            if (!arraylist.contains(entry.getKey()))
            {
                printstream.println((new StringBuilder()).append((String)entry.getKey()).append("=").append((String)entry.getValue()).toString());
            }
        }
        while (true);
        printstream.close();
        try
        {
            props.clear();
            lines.clear();
            load();
        }
        catch (IOException ioexception)
        {
            System.out.println((new StringBuilder()).append("[Props] Unable to load ").append(fileName).append("!").toString());
        }
    }

    public Map returnMap()
    throws Exception
    {
        HashMap hashmap = new HashMap();
        BufferedReader bufferedreader = new BufferedReader(new FileReader(fileName));
        do
        {
            String s;
            if ((s = bufferedreader.readLine()) == null)
            {
                break;
            }
            if (s.trim().length() != 0 && s.charAt(0) != '#' && s.contains("="))
            {
                int i = s.indexOf('=');
                String s1 = s.substring(0, i).trim();
                String s2 = s.substring(i + 1).trim();
                hashmap.put(s1, s2);
            }
        }
        while (true);
        bufferedreader.close();
        return hashmap;
    }

    public boolean containsKey(String s)
    {
        for (Iterator iterator = lines.iterator(); iterator.hasNext();)
        {
            String s1 = (String)iterator.next();
            if (s1.trim().length() != 0 && s1.charAt(0) != '#' && s1.contains("="))
            {
                int i = s1.indexOf('=');
                String s2 = s1.substring(0, i);
                if (s2.equals(s))
                {
                    return true;
                }
            }
        }

        return false;
    }

    public String getProperty(String s)
    {
        for (Iterator iterator = lines.iterator(); iterator.hasNext();)
        {
            String s1 = (String)iterator.next();
            if (s1.trim().length() != 0 && s1.charAt(0) != '#' && s1.contains("="))
            {
                int i = s1.indexOf('=');
                String s2 = s1.substring(0, i).trim();
                String s3 = s1.substring(i + 1);
                if (s2.equals(s))
                {
                    return s3;
                }
            }
        }

        return "";
    }

    public void removeKey(String s)
    {
        Boolean boolean1 = Boolean.valueOf(false);
        if (props.containsKey(s))
        {
            props.remove(s);
            boolean1 = Boolean.valueOf(true);
        }
        try
        {
            for (int i = 0; i < lines.size(); i++)
            {
                String s1 = (String)lines.get(i);
                if (s1.trim().length() != 0 && s1.charAt(0) != '#' && s1.contains("="))
                {
                    int j = s1.indexOf('=');
                    String s2 = s1.substring(0, j).trim();
                    if (s2.equals(s))
                    {
                        lines.remove(i);
                        boolean1 = Boolean.valueOf(true);
                    }
                }
            }
        }
        catch (ConcurrentModificationException concurrentmodificationexception)
        {
            removeKey(s);
            return;
        }
        if (boolean1.booleanValue())
        {
            save();
        }
    }

    public boolean keyExists(String s)
    {
        try
        {
            return containsKey(s);
        }
        catch (Exception exception)
        {
            return false;
        }
    }

    public String getString(String s)
    {
        if (containsKey(s))
        {
            return getProperty(s);
        }
        else
        {
            return "";
        }
    }

    public String getString(String s, String s1)
    {
        if (containsKey(s))
        {
            return getProperty(s);
        }
        else
        {
            setString(s, s1);
            return s1;
        }
    }

    public void setString(String s, String s1)
    {
        props.put(s, s1);
        save();
    }

    public int getInt(String s)
    {
        if (containsKey(s))
        {
            return Integer.parseInt(getProperty(s));
        }
        else
        {
            return 0;
        }
    }

    public int getInt(String s, int i)
    {
        if (containsKey(s))
        {
            return Integer.parseInt(getProperty(s));
        }
        else
        {
            setInt(s, i);
            return i;
        }
    }

    public void setInt(String s, int i)
    {
        props.put(s, String.valueOf(i));
        save();
    }

    public double getDouble(String s)
    {
        if (containsKey(s))
        {
            return Double.parseDouble(getProperty(s));
        }
        else
        {
            return 0.0D;
        }
    }

    public double getDouble(String s, double d)
    {
        if (containsKey(s))
        {
            return Double.parseDouble(getProperty(s));
        }
        else
        {
            setDouble(s, d);
            return d;
        }
    }

    public void setDouble(String s, double d)
    {
        props.put(s, String.valueOf(d));
        save();
    }

    public long getLong(String s)
    {
        if (containsKey(s))
        {
            return Long.parseLong(getProperty(s));
        }
        else
        {
            return 0L;
        }
    }

    public long getLong(String s, long l)
    {
        if (containsKey(s))
        {
            return Long.parseLong(getProperty(s));
        }
        else
        {
            setLong(s, l);
            return l;
        }
    }

    public void setLong(String s, long l)
    {
        props.put(s, String.valueOf(l));
        save();
    }

    public boolean getBoolean(String s)
    {
        if (containsKey(s))
        {
            return Boolean.parseBoolean(getProperty(s));
        }
        else
        {
            return false;
        }
    }

    public boolean getBoolean(String s, boolean flag)
    {
        if (containsKey(s))
        {
            return Boolean.parseBoolean(getProperty(s));
        }
        else
        {
            setBoolean(s, flag);
            return flag;
        }
    }

    public void setBoolean(String s, boolean flag)
    {
        props.put(s, String.valueOf(flag));
        save();
    }
}
