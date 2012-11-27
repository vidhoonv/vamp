import java.io.*;
import java.net.*;
import java.util.Calendar;
import java.text.SimpleDateFormat;
//self address => 192.168.0.2
//self location => chennai
//destinations => salem
class chennai {
    public static void main(String[] args) {

  	String[] split_form;
	String[] destination;
	destination=new String[2];
	String[] recv_data;
	int nearest_stations=1;
	String myaddr="192.168.0.2";
	String packheader=myaddr;	
	destination[0]="192.168.0.3";
	String logSentence="";
	String place="";
	while(true)
	{

	try{
		//int dummy=System.in.read();	
	DatagramSocket serverSocket = new DatagramSocket(9876);
	
	
	// to get date
	Calendar cal=Calendar.getInstance();
	SimpleDateFormat sdf=new SimpleDateFormat("'\nDate:\t'dd-MM-yyyy '\nTime:\t'HH:mm:ss");
	String current_date=sdf.format(cal.getTime());
	
            byte[] receiveData = new byte[1024];
            byte[] sendData = new byte[1024];

         DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
         serverSocket.receive(receivePacket);
         String sentence = new String( receivePacket.getData());
	 recv_data=sentence.split("\\|");
	 sentence=myaddr;
		for(int j=1;j<recv_data.length-1;j++)
			sentence+="|"+recv_data[j];
		
			
		sentence+="|dummy";
	//to write content to file
	FileWriter fstream=new FileWriter("chennai_stop_log.txt",true);
	BufferedWriter out=new BufferedWriter(fstream);
       	//preparing content
	
	logSentence="\n-----------------------------------------------------";
	logSentence+="\n"+current_date;
	logSentence+="\nIP Address:\t"+recv_data[1];
	logSentence+="\nCountry:\t"+recv_data[2];
	logSentence+="\nStation:\t"+recv_data[3];
	logSentence+="\nX- Coordinates:\t"+recv_data[4];
	logSentence+="\nY- Coordinates:\t"+recv_data[5];
	logSentence+="\n-----------------------------------------------------";
	//writing to log file
	out.write(logSentence);
	out.close();
	if(recv_data[0].equals("192.168.0.2"))
		place="Chennai";
	else if(recv_data[0].equals("192.168.0.3"))
		place="Salem";
	else if(recv_data[0].equals("192.168.0.4"))
		place="Erode";
	else if(recv_data[0].equals("192.168.0.5"))
		place="Tirupur";
	else if(recv_data[0].equals("192.168.0.6"))
		place="Coimbatore";
	//print sender details		
	String frominfo="Message Received from "+place+" <==> "+recv_data[0];
		if(recv_data[0].equals("frombus"))
			{
				//forward data to nearest stations
			     for(int i=0;i<nearest_stations;i++)
				{
      			     	InetAddress IPAddress = InetAddress.getByName(destination[i]);
				sendData = sentence.getBytes();
				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9877);
		                serverSocket.send(sendPacket);
				}
					
			}
		else
			{
				

			}	
				System.out.println(frominfo);
				System.out.println(logSentence);
			
			//close all descriptors 
             serverSocket.close();
		
	}
	catch (FileNotFoundException e) {
            System.err.println("FileStreamsTest: " + e);
        }
	 catch (IOException e) {
            System.err.println("FileStreamsTest: " + e);
        }
	}
} 
}
