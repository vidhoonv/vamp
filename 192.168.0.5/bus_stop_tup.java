import java.io.*;
import java.net.*;
import java.util.Calendar;
import java.text.SimpleDateFormat;
//self address => 192.168.0.5
//self location => tirupur
//destinations => erode,coimbatore
class tirupur {
    public static void main(String[] args) {

  	String[] split_form;
	String[] destination;
	destination=new String[2];
	String[] recv_data;
	int nearest_stations=2;
	String myaddr="192.168.0.5";
	String packheader=myaddr;	
	destination[0]="192.168.0.4";
	destination[1]="192.168.0.6";	
String logSentence="";
	String place="";
	while(true)
	{
	try{
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
	FileWriter fstream=new FileWriter("tirupur_stop_log.txt",true);
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
			     for(int i=0;i<nearest_stations;i++)
				{
      			     	InetAddress IPAddress = InetAddress.getByName(destination[i]);
				sendData = sentence.getBytes();
				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
		                serverSocket.send(sendPacket);
				}
				//serverSocket.close();
			}
		else
			{
				InetAddress IPAddress1;
				if(recv_data[0].equals(destination[0]))
				{
				IPAddress1 = InetAddress.getByName(destination[1]);	
				sendData = sentence.getBytes();
				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress1, 9876);
		                serverSocket.send(sendPacket);
				}				
				else if(recv_data[0].equals(destination[1]))
				{
			 	IPAddress1 = InetAddress.getByName(destination[0]);
				sendData = sentence.getBytes();
				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress1, 9876);
		                serverSocket.send(sendPacket);
				}

			
			}	
				System.out.println(frominfo);
				System.out.println(logSentence);
			
			 
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
