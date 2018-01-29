package com.coalvalue.idCard;

import com.sun.jna.Library;
import com.sun.jna.Native;  
  
public interface RdCard extends Library {
    RdCard instanceDll  = (RdCard)Native.loadLibrary("RdCard",RdCard.class);
/*
    public int add(int a,int b);
    public int factorial(int n);
*/



    //SS628-100 操作

    public int UCommand1(byte[] pCmd, int parg0, int parg1, int parg2);

    public  int GetSamID(byte samIDBuff);
    public  int GetAddr( byte strBuff);
    public  int GetBegin( byte strBuff);
    public  int GetName( byte strBuff);
    public  int GetSex( byte strBuff);
    public  int GetFolk( byte strBuff);
    public  int GetIDNum( byte strBuff);
    public  int GetDep( byte strBuff);
    public  int GetBirth( byte strBuff);
    public  int GetEnd( byte strBuff);
    public  int GetNewAddr( byte strBuff);
}  