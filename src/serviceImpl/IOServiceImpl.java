package serviceImpl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;

import service.IOService;

public class IOServiceImpl implements IOService{
	@Override
	public boolean writeFile(String content, String userId, String fileName) throws RemoteException {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(userId + "/" + fileName, true));
			
			String lastVersion = readFile(userId, fileName);
			if(lastVersion.equals(content.substring(0, content.length() - 2))){
				bw.close();
				return true;
			}
			
			bw.write(content);
			bw.flush();
			bw.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public String readFile(String userId, String fileName) throws RemoteException {
		String file = "";

		File f = new File(userId + "/" + fileName);
		try {
			BufferedReader br = new BufferedReader(new FileReader(f));
			
			file = br.readLine();
			
			if(file == null){
				br.close();
				return "";
			}
			
			String flag;
			while(!((flag = br.readLine())==null)){
				file = flag;
			}
			
			br.close();
			return file;
		} catch (IOException e) {
			e.printStackTrace();
			return file;
		}
		
	}

	@Override
	public String readFileList(String userId) throws RemoteException {
		// TODO Auto-generated method stub
		return "OK";
	}

	@Override
	public String[] getFileList(String userId) throws RemoteException {
		File f = new File(userId);
		
		return f.list();
	}

	@Override
	public boolean createFile(String userId, String fileName) throws RemoteException {
		File f = new File(userId + "/" + fileName);
		if(f.exists())
			return false;
		else{
			FileWriter fw;
			try {
				fw = new FileWriter(f, false);
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		
		return true;
	}
	
	@Override
	public int getVersionNumber(String userId, String fileName) throws RemoteException {
		int count = 0;
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(userId + "/" + fileName)));
			String temp;
			
			while(!((temp = br.readLine()) == null)){
				count += 1;
			}
			
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		
		return count;
	}
	
	@Override
	public String getSpecifiedVersion(int versionNumber, String userId, String fileName) throws RemoteException{
		if(getVersionNumber(userId, fileName) >= versionNumber){
			try {
				BufferedReader br = new BufferedReader(new FileReader(new File(userId + "/" + fileName)));
				
				String[] content = new String[versionNumber];
			
				for(int i = 0; i < versionNumber; i ++)
					content[i] = br.readLine();
				 
				String temp = null;
				while(!((temp = br.readLine()) == null)){
					for(int i = 0; i < versionNumber - 1; i ++)
						content[i] = content[i + 1];
					
					content[versionNumber - 1] = temp; 
				}
				
				br.close();
				return content[0];
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "";
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "";
			}
		}

		return "";
	}
}
