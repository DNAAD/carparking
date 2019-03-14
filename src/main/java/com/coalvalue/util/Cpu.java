/*
package com.coalvalue.util;
*/
/**
 * @author guanchen
 * @creation 2016年10月13日 上午9:35:43 
 *//*

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.Scanner;
 
import org.hyperic.sigar.CpuInfo;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
 
public class Cpu {
	private static Sigar sigar = new Sigar();
 
	public static CpuInfo getCpuInfo() {
		try {
			CpuInfo[] cpuArr = sigar.getCpuInfoList();
			return cpuArr[0];
		} catch (SigarException e) {
			e.printStackTrace();
		}
		return null;
	}
 
	public static String getCpuPerc() {
		try {
			CpuPerc[] cpuPercList = sigar.getCpuPercList();
			double num = 0.0D;
			DecimalFormat df = new DecimalFormat("#.0");
 
			for (int i = 0; i < cpuPercList.length; i++) {
				num += cpuPercList[i].getCombined();
			}
			if (num == 0.0D) {
				return "0";
			}
			return df.format(num * 100.0D);
		} catch (SigarException e) {
			e.printStackTrace();
		}
		return "";
	}
 
	private static String getHDSerial(String drive) {
		String result = "";
		try {
			File file = File.createTempFile("tmp_02", ".vbs");
			file.deleteOnExit();
			FileWriter fw = new java.io.FileWriter(file);
 
			String vbs = "Set objFSO = CreateObject(\"Scripting.FileSystemObject\")\n"
					+ "Set colDrives = objFSO.Drives\n"
					+ "Set objDrive = colDrives.item(\""
					+ drive
					+ "\")\n"
					+ "Wscript.Echo objDrive.SerialNumber";
			fw.write(vbs);
			fw.close();
			Process p = Runtime.getRuntime().exec(
					"cscript //NoLogo " + file.getPath());
			BufferedReader input = new BufferedReader(new InputStreamReader(
					p.getInputStream()));
			String line;
			while ((line = input.readLine()) != null) {
				result += line;
			}
			input.close();
			file.delete();
		} catch (Exception e) {
		}
		if (result.trim().length() < 1 || result == null) {
			result = "";
		}
		return result.trim();
	}
 
	private static String getCPUSerial() {
		String result = "";
		try {
			File file = File.createTempFile("tmp_01", ".vbs");
			file.deleteOnExit();
			FileWriter fw = new java.io.FileWriter(file);
 
			String vbs = "On Error Resume Next \r\n\r\n"
					+ "strComputer = \".\"  \r\n"
					+ "Set objWMIService = GetObject(\"winmgmts:\" _ \r\n"
					+ "    & \"{impersonationLevel=impersonate}!\\\\\" & strComputer & \"\\root\\cimv2\") \r\n"
					+ "Set colItems = objWMIService.ExecQuery(\"Select * from Win32_Processor\")  \r\n "
					+ "For Each objItem in colItems\r\n "
					+ "    Wscript.Echo objItem.ProcessorId  \r\n "
					+ "    exit for  ' do the first cpu only! \r\n"
					+ "Next                    ";
 
			fw.write(vbs);
			fw.close();
			Process p = Runtime.getRuntime().exec(
					"cscript //NoLogo " + file.getPath());
			BufferedReader input = new BufferedReader(new InputStreamReader(
					p.getInputStream()));
			String line;
			while ((line = input.readLine()) != null) {
				result += line;
			}
			input.close();
			file.delete();
		} catch (Exception e) {
			e.fillInStackTrace();
		}
		if (result.trim().length() < 1 || result == null) {
			result = "";
		}
		return result.trim();
	}
 
	public static String generateMachineNo() throws Exception {
		StringBuilder machineNo = new StringBuilder();
		String cpuser = getCPUSerial();
		machineNo.append(cpuser);
		String hdId = getHDSerial("C:/");
		machineNo.append(hdId);
		// String tmp = encrypt(machineNo.toString(), "energyregisterkey");
		return machineNo.toString();
	}
 
	public static String getCPU() {
		Process process;
		String serial = null;
		try {
			process = Runtime.getRuntime().exec(
					new String[] { "wmic", "cpu", "get", "ProcessorId" });
			process.getOutputStream().close();
			Scanner sc = new Scanner(process.getInputStream());
			String property = sc.next();
			serial = sc.next();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return serial;
	}
 
	public static void main(String[] args) throws Exception {
		String sigar = Cpu.getCpuInfo().getModel();
		System.out.println("sigar 获取 : " + sigar);
 
		System.out.println("java 获取 : " + getCPU());
		String vbs = null;
		try {
			vbs = generateMachineNo();
			System.out.println("VB 获取 : " + vbs);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
 
}
*/
