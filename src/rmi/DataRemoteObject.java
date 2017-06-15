package rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import service.ExecuteService;
import service.IOService;
import service.UserService;
import serviceImpl.ExecuteServiceImpl;
import serviceImpl.IOServiceImpl;
import serviceImpl.UserServiceImpl;

public class DataRemoteObject extends UnicastRemoteObject implements IOService, UserService, ExecuteService{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4029039744279087114L;
	private IOService iOService;
	private UserService userService;
	private ExecuteService executeService;
	
	protected DataRemoteObject() throws RemoteException {
		iOService = new IOServiceImpl();
		userService = new UserServiceImpl();
		executeService = new ExecuteServiceImpl();
	}

	@Override
	public boolean writeFile(String file, String userId, String fileName) throws RemoteException{
		return iOService.writeFile(file, userId, fileName);
	}

	@Override
	public String readFile(String userId, String fileName) throws RemoteException{
		return iOService.readFile(userId, fileName);
	}

	@Override
	public String readFileList(String userId) throws RemoteException{
		return iOService.readFileList(userId);
	}

	@Override
	public boolean login(String username, String password) throws RemoteException {
		return userService.login(username, password);
	}

	@Override
	public boolean logout(String username) throws RemoteException {
		return userService.logout(username);
	}
	
	@Override
	public boolean register(String username, String password) throws RemoteException {
		return userService.register(username, password);
	}

	@Override
	public int logInError(String username, String password) throws RemoteException {
		return userService.logInError(username, password);
	}
	
	@Override
	public String execute(String code, String param) throws RemoteException {
		return executeService.execute(code, param);
	}

	@Override
	public String[] getFileList(String userId) throws RemoteException {
		return iOService.getFileList(userId);
	}

	@Override
	public boolean createFile(String userId, String fileName) throws RemoteException {
		return iOService.createFile(userId, fileName);
	}

	@Override
	public int getVersionNumber(String userId, String fileName) throws RemoteException {
		return iOService.getVersionNumber(userId, fileName);
	}

	@Override
	public String getSpecifiedVersion(int versionNumber, String userId, String fileName) throws RemoteException {
		return iOService.getSpecifiedVersion(versionNumber, userId, fileName);
	}
}
