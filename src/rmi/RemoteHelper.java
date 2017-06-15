package rmi;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.ExportException;

public class RemoteHelper {
	public RemoteHelper(){
		initServer();
	}
	
	public void initServer(){
		DataRemoteObject dataRemoteObject;
		try {
			// 把远程对象注册到RMI注册服务器上，并命名为DataRemoteObject
			dataRemoteObject = new DataRemoteObject();
			LocateRegistry.createRegistry(8887);
			Naming.bind("rmi://127.0.0.1:8887/DataRemoteObject",
					dataRemoteObject);
			System.out.println("created");
		} catch (ExportException e){
			System.out.println("已存在");
		} catch (RemoteException e) {
			//网络通信失败
			System.out.println("抱歉，你没有联网");
		} catch (MalformedURLException e) {
			System.out.println("地址错误");
			e.printStackTrace();
		} catch (AlreadyBoundException e) {
			//已经存在该对象
			e.printStackTrace();
		}
	}
}
