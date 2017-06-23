//请不要修改本文件名
package serviceImpl;

import java.rmi.RemoteException;
import service.ExecuteService;

public class ExecuteServiceImpl implements ExecuteService {
	/**
	 * 请实现该方法
	 */
	@Override
	public String execute(String code, String param) throws RemoteException {
		if(code.equals(""))
			return "Error!";
		
		//type是此时语言种类,0是错误,1是bf,2是ook
		int type = 0; 
		//result是运行结果
		String result = "";
		//data用来保存不同指针指向的数据
		char[] data = new char[5000];
		//ptr是指针
		int ptr = 0;
		//count表示现在读到传入参数的索引
		int count = 0;
		//loopCount是循环的次数
		int loopCount = 0;
		//endFlag是循环结束标记出现的次数
		int endFlag = 0;	
		//flag用来在后面判断[或]出现的位置
		int flag;
		
		if(code.charAt(0) == '>' || code.charAt(0) == '<' || code.charAt(0) == '+' || code.charAt(0) == '-'
				|| code.charAt(0) == '.' || code.charAt(0) == ',' || code.charAt(0) == '[' || code.charAt(0) == ']')
			type = 1;
		if(code.length() >= 4){
			if(code.substring(0, 3).equals("Ook"))
				type = 2;
		}
		
		//先把ook转换成BF
		if(type == 2){
			String code1 = "";
			for(int i = 0; i < code.length(); i = i + 10){
				if(code.substring(i, i + 10).equals("Ook. Ook? ")) // '>'
					code1 += ">";
				else if(code.substring(i, i + 10).equals("Ook? Ook. ")) // '<'
					code1 += "<";
				else if(code.substring(i, i + 10).equals("Ook. Ook. ")) // '+'
					code1 += "+";
				else if(code.substring(i, i + 10).equals("Ook! Ook! ")) // '-'
					code1 += "-";
				else if(code.substring(i, i + 10).equals("Ook! Ook. ")) // '.'
					code1 += ".";
				else if(code.substring(i, i + 10).equals("Ook. Ook! ")) // ','
					code1 += ",";
				else if(code.substring(i, i + 10).equals("Ook! Ook? ")) // '['
					code1 += "[";
				else if(code.substring(i, i + 10).equals("Ook? Ook! ")) // ']'
					code1 += "]";
			}
			code = code1;
		}
		
		//bf编译
		//先判断有几重循坏，以及确保循坏结构完整
		for(int i = 0; i < code.length(); i ++){
			if(code.charAt(i) == '[')
				loopCount += 1;
			else if(code.charAt(i) == ']')
				endFlag += 1;
		}
					
		if(endFlag < loopCount)
			return "Error!";
		else if(endFlag > loopCount)
			return "Error!";
				
		//i是当前读取的字符在代码中的编号
		try{
			for(int i = 0; i < code.length(); i ++){
				switch(code.charAt(i)){
					case '>':
						ptr += 1;
						break;
					case '<':
						ptr -= 1;
						break;
					case '+': 
						data[ptr] = (char)(data[ptr] + 1);
						break;			
					case '-': 
						data[ptr] = (char)(data[ptr] - 1);
						break;
					case '.': 
						result += (char)data[ptr];
						break;
					case ',': 
						data[ptr] = param.charAt(count);
						count += 1;
						break;
					case '[':
						//如果指针结果不是0，继续往下
						if(data[ptr] != 0)
							break;
						
						//如果指针结果是0，跳转到对应]之后的命令
						//先得到]出现的位置
						flag = 1;
						while(flag != 0){
							i += 1;
							
							if(code.charAt(i) == '[')
								flag += 1;
							if(code.charAt(i) == ']')
								flag -= 1;
						}
						break;	
					case ']':
						//如果指针结果是0，继续往下
						if(data[ptr] == 0)
							break;
						
						//如果指针结果不是0，跳转到对应[之后的命令
						//先得到[出现的位置
						flag = 1;
						while(flag != 0){
							i -= 1;
							
							if(code.charAt(i) == '[')
								flag -= 1;
							if(code.charAt(i) == ']')
								flag += 1;
						}
						break;	
					case ' ': 
						break;
					default:
						return "Error!";
				}						
			} 
			return result;
		} catch(Exception e){
			return "Error!";
		}
	}
	
}
