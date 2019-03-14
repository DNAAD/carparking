package com.util;


import java.io.Console;
import java.io.IOException;

public class KillProcess
{
    private  String X = " /PID ";
    private  String F = " /F ";
    private  String TSKILL_CMD = "taskkill";


    public void kill(int pid) throws IOException
    {
        System.out.println("Please input pid:");
/*        Console console = System.console();
        if (null != console)
        {
                   int pid = Integer.valueOf(System.console().readLine());

        }
        else
        {
            System.out.println("");
        }
        System.in.read();*/



        try
        {
            Runtime.getRuntime().exec(TSKILL_CMD +F+ X + pid);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

}