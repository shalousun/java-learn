package com.yusun.rpc.register;

import com.yusun.rpc.framework.URL;

import java.io.*;
import java.util.*;

/**
 * @author yu 2020/11/8.
 */
public class RemoteMapRegister {

    private static Map<String, List<URL>> REGISTER = new HashMap<>();

    public static void registry(String interfaceName, URL url){
        List<URL> list = REGISTER.get(interfaceName);
        if(Objects.isNull(list)){
            list = new ArrayList<>();
        }

        list.add(url);

        REGISTER.put(interfaceName,list);
        saveToFile();
    }

    public static List<URL> get(String interfaceName){
        REGISTER = getFile();
        List<URL> list = REGISTER.get(interfaceName);
        return list;
    }

    private static Map<String, List<URL>> getFile(){
        try {
            FileInputStream inputStream = new FileInputStream("/temp.txt");
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            Map<String, List<URL>> map = (Map<String, List<URL>>)objectInputStream.readObject();
            return map;
        }catch (IOException|ClassNotFoundException e){
            e.fillInStackTrace();
        }
        return null;
    }

    private static void saveToFile() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("/temp.txt");
            ObjectOutputStream objectInputStream = new ObjectOutputStream(fileOutputStream);
            objectInputStream.writeObject(REGISTER);
        }catch (IOException e){
            e.fillInStackTrace();
        }
    }
}
