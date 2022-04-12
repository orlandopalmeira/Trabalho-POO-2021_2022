import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class Generator {
    
    private static SmartDevice lineToDevice(String line){
        String[] data = line.split(";");
        SmartDevice result = null;
        if(data[0].equals("SmartBulb")){
            result = new SmartBulb(data[1],Integer.parseInt(data[2]),Double.parseDouble(data[3]));
        }else if(data[0].equals("SmartSpeaker")){
            result = new SmartSpeaker(data[1],data[2],10,data[3]);
        }else if(data[0].equals("SmartCamera")){
            result = new SmartCamera(data[1],Integer.parseInt(data[2]),Integer.parseInt(data[3]),Integer.parseInt(data[4]));
        }
        return result;
    }

    private static Map<String,SmartDevice> fileToDevices(String path) throws IOException{
        Map<String,SmartDevice> result = new HashMap<>();
        BufferedReader reader = new BufferedReader(new FileReader(new File(path)));
        String line;
        while((line = reader.readLine()) != null){
            SmartDevice aux = lineToDevice(line);
            result.put(aux.getID(), aux);
        }
        reader.close();
        return result;
    }

    private static Map<String,Pessoa> fileToPeople(String path) throws IOException{
        Map<String,Pessoa> result = new HashMap<>();
        BufferedReader reader = new BufferedReader(new FileReader(new File(path)));
        String line;
        while((line = reader.readLine()) != null){
            String[] data = line.split(";");
            result.put(data[0],new Pessoa(data[0],Integer.parseInt(data[1])));
        }
        reader.close();
        return result;
    }

    private static List<String> stringListToList(String listStr, String delim){
        List<String> result = new ArrayList<>();
        String[] devIDs = listStr.substring(1,listStr.length() - 1).split(delim);
        for(String id: devIDs) result.add(id);
        return result;
    }

    private static Map<String,List<String>> tuplesRoomDevicesToLocations(String tupleRoomDevices){
        Map<String,List<String>> result = new HashMap<>();
        String[] tuples = tupleRoomDevices.substring(1, tupleRoomDevices.length()-1).split(",");
        for(String tuple: tuples){
            String[] aux = tuple.substring(1, tuple.length() - 1).split(":");
            result.put(aux[0],stringListToList(aux[1], "\\."));
        }
        return result; 
    }

    private static CasaInteligente lineToHouse(Map<String,SmartDevice> allDevices, Map<String,Pessoa> allPeople, String line){
        String[] data = line.split(";");
        CasaInteligente result = new CasaInteligente(data[0],allPeople.get(data[1]),new EnergyProvider(data[2]));
        Map<String,List<String>> devsPerRoom = tuplesRoomDevicesToLocations(data[3]);
        for(String room: devsPerRoom.keySet()){
            for(String devID: devsPerRoom.get(room)){
                result.addDevice(allDevices.get(devID), room);
            }
        }
        return result;
    }
    
    public static List<CasaInteligente> fileToHouses(String devicesF, String peopleF, String housesF) throws IOException {
        Map<String,SmartDevice> devices = fileToDevices(devicesF);
        Map<String,Pessoa> people = fileToPeople(peopleF);
        List<CasaInteligente> houses = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(new File(housesF)));
        String line;
        while((line = reader.readLine()) != null){
            houses.add(lineToHouse(devices,people,line));
        }
        reader.close();
        return houses;
    }
    
}
