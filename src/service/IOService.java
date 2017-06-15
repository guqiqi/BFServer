//需要客户端的Stub
package service;

import java.rmi.Remote;
import java.rmi.RemoteException;
public interface IOService extends Remote{
	public boolean writeFile(String file, String userId, String fileName)throws RemoteException;
	
	public String readFile(String userId, String fileName)throws RemoteException;
	
	public boolean createFile(String userId, String fileName)throws RemoteException;
	
	public String[] getFileList(String userId)throws RemoteException;
	
	public int getVersionNumber(String userId, String fileName) throws RemoteException;
	
	public String getSpecifiedVersion(int versionNumber, String userId, String fileName) throws RemoteException;
}
