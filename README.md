UTEID: gdl386; dao384;
FIRSTNAME: Gavin; Daniel;
LASTNAME: Leith; Olvera;
CSACCOUNT: gavindl; dolvera1;
EMAIL: johnyd@cs.utexas.edu; bradp@cs.utexas.edu;

[Program 4]
[Description]
There are 3 files in our assignment: AES.java, key(0-3), and inputFile(0-3 based on test case). The program is compiled with javac *.java. In our assignment we take in a plain text file called inputFile and make a char matrix array out of it. We then run through the key to expand it from the given 32 bits to be 256 bits, the algorithm we used is found at https://en.wikipedia.org/wiki/Rijndael_key_schedule. Once the expanded key has been made, we put the plain text through the AES algorithm. To aid us with this algorithm, we followed the pseudocode found at http://csrc.nist.gov/publications/fips/fips197/fips-197.pdf. We also borrowed the code given by Dr. Young for mixColumns. We run the text through 15 total rounds, using subBytes, shiftRows, mixColumns, and addRoundKey until we get a fully encrypted text. From here, we output this text into inputFile.enc. We can then run the program again, with a d flag, to decrypt the program. The initial program is the same, the key is expaned and the file is read. From there, we run the text through decryption where we inverse subBytes, shiftRows, mixColumns, and addRowKeys. Again, we borrowed Dr. Young's code for invMixColumns. Once this is done, the decrypted text is placed in inputFile.enc.dec.
 
[Finish]
We finished the assignment.

[Test Cases]
[command line]
java AES e keyFile inputFile
java AES d keyFile inputFile.enc

inputFile
keyFile

[Output of test 1]
inputFile.enc
inputFile.enc.dec
   
[Input of test 2]
[command line]
java AES e keyFile2 inputFile2
java AES d keyFile2 inputFile2.enc

inputFile2
keyFile2

[Output of test 2]
inputFile2.enc
inputFile2.enc.dec

[Input of test 3]
[command line]
java AES e keyFile3 inputFile3
java AES d keyFile3 inputFile3.enc

inputFile3
keyFile3
[Output of test 3]
inputFile3.enc
inputFile3.enc.dec

[Input of test 4]
[command line]
java AES e keyFile4 inputFile4
java AES d keyFile4 inputFile4.enc

inputFile4
keyFile4
[Output of test 4]
inputFile4.enc
inputFile4.enc.dec