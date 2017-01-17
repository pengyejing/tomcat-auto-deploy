package com.lsj.tad.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Command {
	public static String exeCmd(String commandStr) {  
        BufferedReader br = null;  
        String res = null;
        try {  
            Process p = Runtime.getRuntime().exec(commandStr);  
            br = new BufferedReader(new InputStreamReader(p.getInputStream()));  
            String line = null;  
            StringBuilder sb = new StringBuilder();  
            while ((line = br.readLine()) != null) {  
                sb.append(line + "\n");  
            }  
            res = new String(sb);
        } catch (Exception e) {  
            e.printStackTrace();  
        }   
        finally  
        {  
            if (br != null)  
            {  
                try {  
                    br.close();  
                } catch (Exception e) {  
                    e.printStackTrace();  
                }  
            }            
        }
        return res;
    }
}