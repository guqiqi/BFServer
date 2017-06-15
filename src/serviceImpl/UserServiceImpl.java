package serviceImpl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;
import service.UserService;

public class UserServiceImpl implements UserService{
	public static String USERINFO = "userInfo.txt";
	
	@Override
	public boolean register(String username, String password) throws RemoteException{
		try {
			BufferedReader br = new BufferedReader(new FileReader(USERINFO));
			
			String name = null;
			
			while((name = br.readLine()) != null){
				if(name.equals(username)){
					br.close();
					
					return false;
				}
				else
					name = br.readLine();
			}
			br.close();
					
			BufferedWriter bw = new BufferedWriter(new FileWriter(USERINFO, true));
			bw.write(username + "\r\n" + password + "\r\n");
			bw.flush();
			bw.close();	
		} catch (FileNotFoundException e) {
			System.out.println("sorry, cannot find the userInfo file");
			return false;
		} catch (IOException e) {
			System.out.println("wrong");
			return false;
		}
		
		File file = new File(username);
		
		if(!file.exists()){
			file.mkdirs();
			//System.out.println(file.getAbsolutePath());
		}
		return true;
	}

	@Override
	public boolean login(String username, String password) throws RemoteException {
		try {
			BufferedReader br = new BufferedReader(new FileReader(USERINFO));
			
			String name = null;
			
			while((name = br.readLine()) != null){
				if(name.equals(username) && br.readLine().equals(password)){
					br.close();
					
					return true;
				}
				else
					name = br.readLine();
			}
			br.close();
		} catch (FileNotFoundException e) {
			System.out.println("sorry, cannot find the userInfo file");
			return false;
		} catch (IOException e) {
			return false;
		}
		
		return false;
	}

	@Override
	public boolean logout(String username) throws RemoteException {
		return true;
	}

	@Override
	public int logInError(String username, String password) throws RemoteException {
		//表示错误的不同原因，0是异常，1是没有该用户,2是密码错误
		try {
			BufferedReader br = new BufferedReader(new FileReader(USERINFO));
			
			String name = null;
			
			while((name = br.readLine()) != null){
				if(name.equals(username) && !br.readLine().equals(password)){
					br.close();
				
					return 2;
				}
				else
					name = br.readLine();
			}
			br.close();
		} catch (FileNotFoundException e) {
			System.out.println("sorry, cannot find the userInfo file");
			return 0;
		} catch (IOException e) {
			return 0;
		}
		
		return 1;
	}

}
