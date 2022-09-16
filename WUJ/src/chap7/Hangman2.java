package chap7;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class Hangman2 extends Hangman {
	
	String[] words = {"fever","poison","victim","drug","hygine"};
	String[] meaning = {"��","��","ȯ��","��","������"}; // ���߿� 2���� �迭�� 

	public Hangman2() {
		Random ran = new Random();
		int random = Math.abs(ran.nextInt() % words.length);
		hiddenString = words[random];
		System.out.println("\n�ǹ�: " + meaning[random]);
	}
	
	public char readChar() {
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		char userInput = 0;
		
		try {
			do{
				System.out.println("���ڸ� �Է��ϼ��� (��Ʈ�� ?) : ");
				userInput = (char) bf.read();
			
			if(userInput == '?') {
				boolean giveHint = false;
				int i = 0;
				while (!giveHint) {
					if(outputString.charAt(i)=='_') {
						System.out.println();
						System.out.println("��Ʈ : "+ hiddenString.charAt(i));
						System.out.println();
						failed++;
						giveHint = true;
					}
					i++;
				}
			}

			}while(userInput == '?');
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
		
		return userInput;
		}
	}
}