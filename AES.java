import java.io.*;
import java.util.*;
import java.lang.*;
public class AES{
	public static final char[][] TABLE = 
	{
	   {0x63, 0x7C, 0x77, 0x7B, 0xF2, 0x6B, 0x6F, 0xC5, 0x30, 0x01, 0x67, 0x2B, 0xFE, 0xD7, 0xAB, 0x76},
	   {0xCA, 0x82, 0xC9, 0x7D, 0xFA, 0x59, 0x47, 0xF0, 0xAD, 0xD4, 0xA2, 0xAF, 0x9C, 0xA4, 0x72, 0xC0},
	   {0xB7, 0xFD, 0x93, 0x26, 0x36, 0x3F, 0xF7, 0xCC, 0x34, 0xA5, 0xE5, 0xF1, 0x71, 0xD8, 0x31, 0x15},
       {0x04, 0xC7, 0x23, 0xC3, 0x18, 0x96, 0x05, 0x9A, 0x07, 0x12, 0x80, 0xE2, 0xEB, 0x27, 0xB2, 0x75},
	   {0x09, 0x83, 0x2C, 0x1A, 0x1B, 0x6E, 0x5A, 0xA0, 0x52, 0x3B, 0xD6, 0xB3, 0x29, 0xE3, 0x2F, 0x84},
	   {0x53, 0xD1, 0x00, 0xED, 0x20, 0xFC, 0xB1, 0x5B, 0x6A, 0xCB, 0xBE, 0x39, 0x4A, 0x4C, 0x58, 0xCF},
	   {0xD0, 0xEF, 0xAA, 0xFB, 0x43, 0x4D, 0x33, 0x85, 0x45, 0xF9, 0x02, 0x7F, 0x50, 0x3C, 0x9F, 0xA8},
	   {0x51, 0xA3, 0x40, 0x8F, 0x92, 0x9D, 0x38, 0xF5, 0xBC, 0xB6, 0xDA, 0x21, 0x10, 0xFF, 0xF3, 0xD2},
	   {0xCD, 0x0C, 0x13, 0xEC, 0x5F, 0x97, 0x44, 0x17, 0xC4, 0xA7, 0x7E, 0x3D, 0x64, 0x5D, 0x19, 0x73},
	   {0x60, 0x81, 0x4F, 0xDC, 0x22, 0x2A, 0x90, 0x88, 0x46, 0xEE, 0xB8, 0x14, 0xDE, 0x5E, 0x0B, 0xDB},
	   {0xE0, 0x32, 0x3A, 0x0A, 0x49, 0x06, 0x24, 0x5C, 0xC2, 0xD3, 0xAC, 0x62, 0x91, 0x95, 0xE4, 0x79},
	   {0xE7, 0xC8, 0x37, 0x6D, 0x8D, 0xD5, 0x4E, 0xA9, 0x6C, 0x56, 0xF4, 0xEA, 0x65, 0x7A, 0xAE, 0x08},
	   {0xBA, 0x78, 0x25, 0x2E, 0x1C, 0xA6, 0xB4, 0xC6, 0xE8, 0xDD, 0x74, 0x1F, 0x4B, 0xBD, 0x8B, 0x8A},
	   {0x70, 0x3E, 0xB5, 0x66, 0x48, 0x03, 0xF6, 0x0E, 0x61, 0x35, 0x57, 0xB9, 0x86, 0xC1, 0x1D, 0x9E},
	   {0xE1, 0xF8, 0x98, 0x11, 0x69, 0xD9, 0x8E, 0x94, 0x9B, 0x1E, 0x87, 0xE9, 0xCE, 0x55, 0x28, 0xDF},
	   {0x8C, 0xA1, 0x89, 0x0D, 0xBF, 0xE6, 0x42, 0x68, 0x41, 0x99, 0x2D, 0x0F, 0xB0, 0x54, 0xBB, 0x16}
	};
	public static final char[][] STABLE =
	{
   {0x52, 0x09, 0x6A, 0xD5, 0x30, 0x36, 0xA5, 0x38, 0xBF, 0x40, 0xA3, 0x9E, 0x81, 0xF3, 0xD7, 0xFB},
   {0x7C, 0xE3, 0x39, 0x82, 0x9B, 0x2F, 0xFF, 0x87, 0x34, 0x8E, 0x43, 0x44, 0xC4, 0xDE, 0xE9, 0xCB},
   {0x54, 0x7B, 0x94, 0x32, 0xA6, 0xC2, 0x23, 0x3D, 0xEE, 0x4C, 0x95, 0x0B, 0x42, 0xFA, 0xC3, 0x4E},
   {0x08, 0x2E, 0xA1, 0x66, 0x28, 0xD9, 0x24, 0xB2, 0x76, 0x5B, 0xA2, 0x49, 0x6D, 0x8B, 0xD1, 0x25},
   {0x72, 0xF8, 0xF6, 0x64, 0x86, 0x68, 0x98, 0x16, 0xD4, 0xA4, 0x5C, 0xCC, 0x5D, 0x65, 0xB6, 0x92},
   {0x6C, 0x70, 0x48, 0x50, 0xFD, 0xED, 0xB9, 0xDA, 0x5E, 0x15, 0x46, 0x57, 0xA7, 0x8D, 0x9D, 0x84},
   {0x90, 0xD8, 0xAB, 0x00, 0x8C, 0xBC, 0xD3, 0x0A, 0xF7, 0xE4, 0x58, 0x05, 0xB8, 0xB3, 0x45, 0x06},
   {0xD0, 0x2C, 0x1E, 0x8F, 0xCA, 0x3F, 0x0F, 0x02, 0xC1, 0xAF, 0xBD, 0x03, 0x01, 0x13, 0x8A, 0x6B},
   {0x3A, 0x91, 0x11, 0x41, 0x4F, 0x67, 0xDC, 0xEA, 0x97, 0xF2, 0xCF, 0xCE, 0xF0, 0xB4, 0xE6, 0x73},
   {0x96, 0xAC, 0x74, 0x22, 0xE7, 0xAD, 0x35, 0x85, 0xE2, 0xF9, 0x37, 0xE8, 0x1C, 0x75, 0xDF, 0x6E},
   {0x47, 0xF1, 0x1A, 0x71, 0x1D, 0x29, 0xC5, 0x89, 0x6F, 0xB7, 0x62, 0x0E, 0xAA, 0x18, 0xBE, 0x1B},
   {0xFC, 0x56, 0x3E, 0x4B, 0xC6, 0xD2, 0x79, 0x20, 0x9A, 0xDB, 0xC0, 0xFE, 0x78, 0xCD, 0x5A, 0xF4},
   {0x1F, 0xDD, 0xA8, 0x33, 0x88, 0x07, 0xC7, 0x31, 0xB1, 0x12, 0x10, 0x59, 0x27, 0x80, 0xEC, 0x5F},
   {0x60, 0x51, 0x7F, 0xA9, 0x19, 0xB5, 0x4A, 0x0D, 0x2D, 0xE5, 0x7A, 0x9F, 0x93, 0xC9, 0x9C, 0xEF},
   {0xA0, 0xE0, 0x3B, 0x4D, 0xAE, 0x2A, 0xF5, 0xB0, 0xC8, 0xEB, 0xBB, 0x3C, 0x83, 0x53, 0x99, 0x61},
   {0x17, 0x2B, 0x04, 0x7E, 0xBA, 0x77, 0xD6, 0x26, 0xE1, 0x69, 0x14, 0x63, 0x55, 0x21, 0x0C, 0x7D}
	};

	public static void main(String args[]) throws Exception{
	
		
		
	   /*get console arguments*/	
       String option = args[0]
	   		, keyFile = args[1]
	   		, inputFile = args[2];
	   		File file= new File(inputFile);
	   		Scanner scan = new Scanner(file);
	   		char[][] expandKey = key_expansion(keyFile);
	   		int multipleLines = 0;
	   		OutputStream output;
	   		final long startTime = System.currentTimeMillis();
	   		System.out.println("startime: "+startTime);
	   		if (option.toLowerCase().equals("e")){
	   			output = new FileOutputStream(args[2] + ".enc");
	   		}
	   		else {
	   			output = new FileOutputStream(args[2] + ".dec");
	   		}
	   		PrintStream printer = new PrintStream(output);
	   		/*expand key*/
	   		System.out.println("The expanded key is: ");
	   		for (int i = 0; i < expandKey.length; i++){
				for (int j = 0; j < expandKey[0].length; j++){
					System.out.print(String.format("%02x ", (int)expandKey[i][j]));
				}
				System.out.println("\n");
			}	
	   		while (scan.hasNextLine()){
		   		char[][] input = arrayInput(scan.nextLine());
		   		System.out.println("The Plaintext is: ");
		   		for (int i = 0; i < input.length; i++){
					for (int j = 0; j < input[0].length; j++){
						System.out.print(String.format("%02x ", (int)input[i][j]));
					}
					System.out.println();
				}	
				System.out.println();
		   		
		   		if (option.toLowerCase().equals("e")){
		   			/*break line*/
		   			if(multipleLines > 0)
		   				printer.print((char)10);
		   	   			char[][] enc = encrypt(input,expandKey);
		   			for (int j = 0 ; j < enc[0].length; j++){
		   				for (int i = 0; i < enc.length; i++){
		   					//String str = enc[i][j] + "";
		   					printer.print(String.format("%02x",(int)enc[i][j]));//System.out.println(Integer.toHexString(str,16));
		   				}
		   			}
		   			multipleLines++;
		   		}
		   		else if (option.toLowerCase().equals("d")){
		   			if(multipleLines > 0)
		   				/*break line*/
		   				printer.print((char)10);
		   			char[][] dec = decryption(input, expandKey);
		   			for (int j = 0 ; j < dec[0].length; j++){
		   				for (int i = 0; i < dec.length; i++){
		   					printer.print(String.format("%02x",(int)dec[i][j]));

		   			    }
		   		    }
		   		    multipleLines++;
		   	    }

		   		else
		   			System.out.println("Wrong input");
		 }
		 /* Program goes here */
		final long endTime = System.currentTimeMillis() - startTime;
		System.out.println("endtime: "+endTime);
		System.out.println("file length: " + file.length());
		System.out.println(1.0*file.length() / endTime );
	}
	public static char[][] decryption(char[][] input, char[][] key){

		char[][] state = input;
		int round = 14;
		addRoundKey(state,key,round*4);
		round--;
		while(round>0){
			addRoundKey((invertSubBytes(invertShiftRows(state))),key,round*4);
			invertMixColumns(state);
			round--;
		}
		addRoundKey((invertSubBytes(invertShiftRows(state))),key,round*4);
		System.out.println("The decryption of the ciphertext:");
		for (int i = 0; i < state.length; i++){
			for (int j = 0; j < state[0].length; j++){
				System.out.print(String.format("%02x ", (int)state[i][j]));
			}
			System.out.println();
		}
		System.out.println();
		System.out.println("The decryption of the ciphertext:");
		for (int i = 0; i < state.length; i++){
			for (int j = 0; j < state[0].length; j++){
				System.out.print(String.format("%02x", (int)state[j][i]));
			}	
		}
		System.out.println();
		return state;
		
	}
	public static char[][] invertShiftRows(char[][] matrix){

		for (int i = 1; i < matrix.length; i++){
	        
		    char[] temp = Arrays.copyOf(matrix[i], matrix[i].length);
			for (int j = matrix[0].length-1; j >=0;j--){
				matrix[i][(j+i)%4] = temp[j];
			}	
		
		}	

		System.out.println("After invertShiftRows");
		for (int i = 0; i < matrix.length; i++){
			for (int j = 0; j < matrix[0].length; j++){
				System.out.print(String.format("%02x", (int)matrix[j][i]));
			}	
		}
		System.out.println();
		return matrix;	
	}
	public static char[][] invertSubBytes(char[][] matrix){
   		for (int i = 0; i < matrix.length; i++){
			
			for (int j= 0; j < matrix[0].length;j++){
			
		    	int val = matrix[i][j];
		    	//System.out.println(val);
				byte y_number = (byte)(val & 0xf);
				byte x_number = (byte)(val >> 4  & 0xf);
				//System.out.println(x_number + " " + y_number);
				matrix[i][j] = STABLE[x_number][y_number];
			
			}
		
		}	
	
		
		System.out.println("After inverseSubBytes");
		for (int i = 0; i < matrix.length; i++){
			for (int j = 0; j < matrix[0].length; j++){
				System.out.print(String.format("%02x", (int)matrix[j][i]));
			}
			
		}
		System.out.println();
 		return matrix;	
	}
	public static char[][] invertMixColumns(char[][] matrix){
			
		
		for (int i = 0; i < matrix.length; i++){
			matrix = invMixColumn2(i, matrix);
		}


		System.out.println("After invertMixCOlumns");
		for (int i = 0; i < matrix.length; i++){
			for (int j = 0; j < matrix[0].length; j++){
				System.out.print(String.format("%02x", (int)matrix[j][i]));
			}
			
		}
		System.out.println();
		return matrix;
	}
	public static char[][] encrypt(char[][] input, char[][]key){
		char[][] state = input;
		int round = 0;
		addRoundKey(state, key, round*4);
		round++;
		while (round < 14){
			addRoundKey(mixColumns(shiftRows(subBytes(state))), key, round*4);
			round++;
		}
		addRoundKey(shiftRows(subBytes(state)), key, round*4);


		/*output*/
		System.out.println("The ciphertext:");
		for (int i = 0; i < state.length; i++){
			for (int j = 0; j < state[0].length; j++){
				System.out.print(String.format("%02x ", (int)state[i][j]));
			}
			System.out.println();
		}
		return state;
	}

	public static char[][] arrayInput(String str) throws FileNotFoundException{
	/*	Scanner input = new Scanner(new File(inputFile));
	
		
		while (input.hasNextLine()){*/
		ArrayList<Character> data = new ArrayList<Character>();
		int value = 0, c = 0;

		if (str.length() < 32){
			while (str.length() < 32)
				str+=str+"0";
		}
		str = str.replaceAll("\\s", "");
		for (int i=0; i < str.length()-1;i+=2){ 
			value = Integer.parseInt(str.substring(i,i+2) + "",16);
			data.add((char)value);
		}

			
		//}
	//	System.out.println("Data: " + data);
		char[][] matrix = new char[4][4];
		/*filling the first part of key expansion with key*/
		for (int j = 0; j < matrix[0].length; j++){
			for (int i = 0 ; i < matrix.length ;i++){
				
				matrix[i][j] = data.get(c++);
				
			}
		}
		return matrix;
	}

	public static char[][] addRoundKey(char [][] matrix, char[][] key, int round){

		for (int i = 0 ; i < matrix.length; i++){
			for (int j = 0 ; j < matrix[0].length ; j++){
				matrix[i][j] = (char)(matrix[i][j] ^ key[i][round + j]);
			}
		}
		System.out.println("After addRoundKey (" + round/4 + ")");
		for (int i = 0; i < matrix.length; i++){
			for (int j = 0; j < matrix[0].length; j++){
				System.out.print(String.format("%02x", (int)matrix[j][i]));
			}
			
		}
		System.out.println();
		return matrix;
	}


    public static char[][] subBytes( char[][] matrix){
	
	
   		for (int i = 0; i < matrix.length; i++){
			
			for (int j= 0; j < matrix[0].length;j++){
			
		    	int val = matrix[i][j];
		    	//System.out.println(val);
				byte y_number = (byte)(val & 0xf);
				byte x_number = (byte)(val >> 4  & 0xf);
				//System.out.println(x_number + " " + y_number);
				matrix[i][j] = TABLE[x_number][y_number];
			
			}
		
		}	
	
		
		System.out.println("After subBytes");
		for (int i = 0; i < matrix.length; i++){
			for (int j = 0; j < matrix[0].length; j++){
				System.out.print(String.format("%02x", (int)matrix[j][i]));
			}
			
		}
		System.out.println();
 		return matrix;	
	}

	public static char[][] shiftRows( char[][] matrix ){
	
		for (int i = 1; i < matrix.length; i++){
	        
		    char[] temp = Arrays.copyOf(matrix[i], matrix[i].length);
			for (int j = 0; j <matrix[0].length - 1;j++){
				matrix[i][j] = temp[(j+i) % 4];
			}	
			for(int k = i; k > 0; k--){
				matrix[i][matrix[0].length - k] = temp[i-k];
			}
		
		}	

		System.out.println("After shiftRows");
		for (int i = 0; i < matrix.length; i++){
			for (int j = 0; j < matrix[0].length; j++){
				System.out.print(String.format("%02x", (int)matrix[j][i]));
			}
			
		}
		System.out.println();
		return matrix;	
	}

	

	public static char[][] mixColumns ( char[][] matrix ) {
	
		
		for (int i = 0; i < matrix.length; i++){
			matrix = mixColumn2(i, matrix);
		}


		System.out.println("After mixColumns");
		for (int i = 0; i < matrix.length; i++){
			for (int j = 0; j < matrix[0].length; j++){
				System.out.print(String.format("%02x", (int)matrix[j][i]));
			}
			
		}
		System.out.println();
		return matrix;
	}
	

	public static char[][] key_expansion(String keyFile) throws FileNotFoundException{
	
		Scanner input = new Scanner(new File(keyFile));
		int c = 0;
		ArrayList<Character> data = new ArrayList<Character>();
		while (input.hasNextLine()){
			String str = input.nextLine();
			str = str.replaceAll("\\s", "");
			for (int i=0; i < str.length();i++){
				int value = Integer.parseInt(str.charAt(i) + "");
				
				data.add((char)value);
			}

			
		}
		// System.out.println(c);
		return key_expansion(data, new char[4][60]);
        
	
	}
    /*rotates the first index into the back of the array and shifts 
	the other indexes up by one*/
	public static int[] rotateWord(int[] data){
		int temp = data[0];
        for (int i = 0 ; i < data.length - 1; i++){
			data[i] = data[i+1];
		}
		data[3] = temp;
	return data;
	}



	public static char[][] key_expansion(ArrayList<Character> data, char[][] matrix){
		// System.out.println(data.size());
		int c=0;

		/*filling the first part of key expansion with key*/
		for (int j = 0; j < 8; j++){
			for (int i = 0 ; i < matrix.length ;i++){
				
				matrix[i][j] = data.get(c++);
				
			}
		}
        /*expand the rest of the key*/
		int i = 8;
		while ( i < 60){
			int[] temp = {matrix[0][i-1],matrix[1][i-1],matrix[2][i-1],matrix[3][i-1]};
			if(i%8 == 0){
				//temp = sub(rotateWord(temp)) ^ (rcon/8); 
				temp = sub(rotateWord(temp));
				temp[0] = temp[0] ^ (int)Math.pow(2,(i/8)-1);
			}
			else if(i % 8 == 4){
				temp = sub(temp);
			}
			for(int j = 0; j< matrix.length; j++){
			//	matrix[j][i] = matrix[j][i-8] ^ temp;
				matrix[j][i] =(char)(matrix[j][i-8] ^ temp[j]);
			}
			i++;
		}
		return matrix;

		// for (int x = 0; x < matrix.length; x++){
		// 	for (int y = 0; y < matrix[0].length; y++){
		// 		System.out.print(String.format("%02x ", (int)matrix[x][y]));
		// 	}
		// 	System.out.println();
		// }

	}
	public static int[] sub( int[] data ){
	
		for (int i = 0 ; i < data.length ; i++){
		
			int val = data[i];
			byte y_number = (byte) (val & 0xf);
			byte x_number = (byte) (val>>4 & 0xf);
			data[i] = TABLE[x_number][y_number];
		
		}	
		return data;	
	}
	public static void exclusive_or(ArrayList<String> word, ArrayList<Character> data){
		
		System.out.println("word: " + word + " data: " + (int)data.get(0));
	
        for (int i = 0; i < word.size();i++){
	        
            int index = i / 2;
			int word_num = Integer.parseInt(word.get(i),16);
			int data_num = Integer.parseInt( ((int) data.get(index))+"", 16);
            if (i == 0 || i == 2){
		    	data_num=((data_num>>8) & 0xff);	
			
			} else {
			
		       data_num = ((int)((byte)data_num));	

			}
			word.set(i, String.format("%02x",( word_num ^ data_num )));
		
		}	
		// System.out.println("word: " + word + " data: " + (int)data.get(0));
		
	
	}

	public static ArrayList<String> schedule_core(int t, int rcon){
		byte[] data = new byte[4];
		//data[0], least significant
		for(int i = 0; i < data.length; i++){
			data[i] = (byte)(t >>> (i*8));
		}
		byte temp = data[0];
		for( int i = 0; i < 3; i++){
			data[i] = data[i+1];
		}
		data[3] = temp;
		ArrayList<String> dup = new ArrayList<String>();
		String str="";
		for (int i = 0; i < data.length; i++){
			str=String.format("%02x", data[i]);
			dup.add(str);
		}
		//System.out.println(dup);
		/*substition*/
		for (int i = 0 ; i < dup.size();i++){

			String hexes = dup.get(i);
			char x = hexes.charAt(0);
			char y = hexes.charAt(1);
			int x_number = Integer.parseInt(x+"", 16);
			int y_number = Integer.parseInt(y+"", 16);
			String sub = String.format("%x", (int)TABLE[x_number][y_number]);

			dup.set(i, sub);

		}
		//System.out.println(dup);

		/*exclusive or the byte with 2 to the power of i-1*/
		dup.set(0,(Integer.parseInt(dup.get(0),16) ^ (int)Math.pow(2, rcon - 1)) + "");

		return dup;

	}



    ////////////////////////  the mixColumns Tranformation ////////////////////////


    final static int[] LogTable = {
	0,   0,  25,   1,  50,   2,  26, 198,  75, 199,  27, 104,  51, 238, 223,   3, 
	100,   4, 224,  14,  52, 141, 129, 239,  76, 113,   8, 200, 248, 105,  28, 193, 
	125, 194,  29, 181, 249, 185,  39, 106,  77, 228, 166, 114, 154, 201,   9, 120, 
	101,  47, 138,   5,  33,  15, 225,  36,  18, 240, 130,  69,  53, 147, 218, 142, 
	150, 143, 219, 189,  54, 208, 206, 148,  19,  92, 210, 241,  64,  70, 131,  56, 
	102, 221, 253,  48, 191,   6, 139,  98, 179,  37, 226, 152,  34, 136, 145,  16, 
	126, 110,  72, 195, 163, 182,  30,  66,  58, 107,  40,  84, 250, 133,  61, 186, 
	43, 121,  10,  21, 155, 159,  94, 202,  78, 212, 172, 229, 243, 115, 167,  87, 
	175,  88, 168,  80, 244, 234, 214, 116,  79, 174, 233, 213, 231, 230, 173, 232, 
	44, 215, 117, 122, 235,  22,  11, 245,  89, 203,  95, 176, 156, 169,  81, 160, 
	127,  12, 246, 111,  23, 196,  73, 236, 216,  67,  31,  45, 164, 118, 123, 183, 
	204, 187,  62,  90, 251,  96, 177, 134,  59,  82, 161, 108, 170,  85,  41, 157, 
	151, 178, 135, 144,  97, 190, 220, 252, 188, 149, 207, 205,  55,  63,  91, 209, 
	83,  57, 132,  60,  65, 162, 109,  71,  20,  42, 158,  93,  86, 242, 211, 171, 
	68,  17, 146, 217,  35,  32,  46, 137, 180, 124, 184,  38, 119, 153, 227, 165, 
	103,  74, 237, 222, 197,  49, 254,  24,  13,  99, 140, 128, 192, 247, 112,   7};

    final static int[] AlogTable = {
	1,   3,   5,  15,  17,  51,  85, 255,  26,  46, 114, 150, 161, 248,  19,  53, 
	95, 225,  56,  72, 216, 115, 149, 164, 247,   2,   6,  10,  30,  34, 102, 170, 
	229,  52,  92, 228,  55,  89, 235,  38, 106, 190, 217, 112, 144, 171, 230,  49, 
	83, 245,   4,  12,  20,  60,  68, 204,  79, 209, 104, 184, 211, 110, 178, 205, 
	76, 212, 103, 169, 224,  59,  77, 215,  98, 166, 241,   8,  24,  40, 120, 136, 
	131, 158, 185, 208, 107, 189, 220, 127, 129, 152, 179, 206,  73, 219, 118, 154, 
	181, 196,  87, 249,  16,  48,  80, 240,  11,  29,  39, 105, 187, 214,  97, 163, 
	254,  25,  43, 125, 135, 146, 173, 236,  47, 113, 147, 174, 233,  32,  96, 160, 
	251,  22,  58,  78, 210, 109, 183, 194,  93, 231,  50,  86, 250,  21,  63,  65, 
	195,  94, 226,  61,  71, 201,  64, 192,  91, 237,  44, 116, 156, 191, 218, 117, 
	159, 186, 213, 100, 172, 239,  42, 126, 130, 157, 188, 223, 122, 142, 137, 128, 
	155, 182, 193,  88, 232,  35, 101, 175, 234,  37, 111, 177, 200,  67, 197,  84, 
	252,  31,  33,  99, 165, 244,   7,   9,  27,  45, 119, 153, 176, 203,  70, 202, 
	69, 207,  74, 222, 121, 139, 134, 145, 168, 227,  62,  66, 198,  81, 243,  14, 
	18,  54,  90, 238,  41, 123, 141, 140, 143, 138, 133, 148, 167, 242,  13,  23, 
	57,  75, 221, 124, 132, 151, 162, 253,  28,  36, 108, 180, 199,  82, 246,   1};

    public static byte mul (int a, byte b) {
	int inda = (a < 0) ? (a + 256) : a;
	int indb = (b < 0) ? (b + 256) : b;

	if ( (a != 0) && (b != 0) ) {
	    int index = (LogTable[inda] + LogTable[indb]);
	    byte val = (byte)(AlogTable[ index % 255 ] );
	    return val;
	}
	else 
	    return 0;
    } // mul

    // In the following two methods, the input c is the column number in
    // your evolving state matrix st (which originally contained 
    // the plaintext input but is being modified).  Notice that the state here is defined as an
    // array of bytes.  If your state is an array of integers, you'll have
    // to make adjustments. 

    public static char[][] mixColumn2 (int c, char[][] matrix) {
	// This is another alternate version of mixColumn, using the 
	// logtables to do the computation.
	
	byte a[] = new byte[4];
	
	// note that a is just a copy of st[.][c]
	for (int i = 0; i < 4; i++) 
	    a[i] = ( byte )matrix[i][c];
	
	// This is exactly the same as mixColumns1, if 
	// the mul columns somehow match the b columns there.
	
	matrix[0][c] = (char)(((byte)(mul(2,a[0]) ^ a[2] ^ a[3] ^ mul(3,a[1]))) & 0xff);
	//System.out.println(String.format("%02x",(int)matrix[0][c]));
	matrix[1][c] = (char)(((byte)(mul(2,a[1]) ^ a[3] ^ a[0] ^ mul(3,a[2]))) & 0xff);
	matrix[2][c] = (char)(((byte)(mul(2,a[2]) ^ a[0] ^ a[1] ^ mul(3,a[3]))) & 0xff);
	matrix[3][c] = (char)(((byte)(mul(2,a[3]) ^ a[1] ^ a[2] ^ mul(3,a[0]))) & 0xff);
    return matrix;
	} // mixColumn2

    public static char[][] invMixColumn2 (int c, char[][] matrix) {
	byte a[] = new byte[4];
	
	// note that a is just a copy of st[.][c]
	for (int i = 0; i < 4; i++) 
	    a[i] = ( byte )matrix[i][c];
	
	matrix[0][c] = (char)(((byte)(mul(0xE,a[0]) ^ mul(0xB,a[1]) ^ mul(0xD, a[2]) ^ mul(0x9,a[3])))& 0xff);
	matrix[1][c] = (char)(((byte)(mul(0xE,a[1]) ^ mul(0xB,a[2]) ^ mul(0xD, a[3]) ^ mul(0x9,a[0])))& 0xff);
	matrix[2][c] = (char)(((byte)(mul(0xE,a[2]) ^ mul(0xB,a[3]) ^ mul(0xD, a[0]) ^ mul(0x9,a[1])))& 0xff);
	matrix[3][c] = (char)(((byte)(mul(0xE,a[3]) ^ mul(0xB,a[0]) ^ mul(0xD, a[1]) ^ mul(0x9,a[2])))& 0xff);
    return matrix;
     } // invMixColumn2

}
