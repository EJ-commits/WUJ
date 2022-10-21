package seacrchNotMatch;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Search {
	
	
	
	public static void main(String[] args) {
		

		try {
			
			String localDB, eastNet; 
			localDB = "D:\\LocalDB.txt";
			eastNet = "D:\\EastNet.txt";

			FileReader localDBFi = new FileReader(localDB);
			FileReader eastNetFi = new FileReader(eastNet);
			
			BufferedReader localDBBf = new BufferedReader(localDBFi);
			BufferedReader eastNetBF = new BufferedReader(eastNetFi);
			
			String LDBNo, EDBNoTemp;
			List<String> EDBNo = new ArrayList<>() ;
			LDBNo = localDBBf.readLine();
			
			boolean flag = false;
			
			
			while( (EDBNoTemp = eastNetBF.readLine()) != null ) {
				EDBNo.add(EDBNoTemp);
			}
			
			LDBNo = localDBBf.readLine();
			
			while(LDBNo != null) {
				for(int i=0; i<EDBNo.size(); i++) {
					if( EDBNo.get(i).equals(LDBNo) )
//							Integer.parseInt(EDBNo.get(i)) == Integer.parseInt(LDBNo)
					{
						flag = true;
					}
				}
				
				if(flag == false) {
					System.out.println(LDBNo + " 일치하는 번호 없음");
				} 
				if(flag == true){
					System.out.println(LDBNo + " 일치 확인");					
				}
				
				flag = false;
				LDBNo = localDBBf.readLine();
			}
			
		} catch(IOException e) {
			System.out.println("error!");
		}
		
		
		
	}
	
	
}
