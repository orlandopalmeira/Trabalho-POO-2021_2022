import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

public class Generator {
    
    public static SmartDevice lineToDevice(String line){
        String[] data = line.split(";");
        SmartDevice result = null;
        if(data[0].equals("SmartBulb")){
            result = new SmartBulb(data[1],Integer.parseInt(data[2]),Double.parseDouble(data[3]));
        }else if(data[0].equals("SmartSpeaker")){
            result = new SmartSpeaker(data[1],data[3],Integer.parseInt(data[2]),data[4]);
        }else if(data[0].equals("SmartCamera")){
            result = new SmartCamera(data[1],Integer.parseInt(data[2]),Integer.parseInt(data[3]),Integer.parseInt(data[4]));
        }
        return result;
    }

    public static List<SmartDevice> fileToDevices() throws FileNotFoundException{
        List<SmartDevice> result = new ArrayList<>();
        Scanner reader = new Scanner(new File("../files/devices.txt"));
        while(reader.hasNextLine()){
            result.add(lineToDevice(reader.nextLine()));
        }
        reader.close();
        return result;
    }
    
}
