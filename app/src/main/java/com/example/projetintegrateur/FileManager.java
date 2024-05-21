package com.example.projetintegrateur;

import android.content.Context;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class FileManager {

    public static ArrayList<String> loadFromFile(String fileName, Context context)
    {
        ArrayList<String> output = new ArrayList<>();
        InputStream inputStream = null;

        try
        {
            inputStream = context.openFileInput(fileName);
            if(inputStream != null)
            {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                Scanner reader = new Scanner(inputStreamReader);
                String content = "";

                while (reader.hasNextLine())
                {
                    content = reader.nextLine();
                    output.add(content);
                }
            }
        }
        catch (FileNotFoundException e)
        {
            Log.e("File Activity", "File not found " + e.toString());
        }
        catch (IOException e)
        {
            Log.e("File Activity", "Can not read file " + e.toString());
        }
        finally
        {
            try
            {
                if(inputStream != null)
                {
                    inputStream.close();
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

        }
        return output;
    }

    public static void saveToFile(ArrayList<String> contents, String fileName, Context context)
    {
        OutputStreamWriter outputStreamWriter = null;

        try
        {
            outputStreamWriter = new OutputStreamWriter(context.openFileOutput(fileName, context.MODE_PRIVATE));
            outputStreamWriter.write(""); //clear file

            outputStreamWriter = new OutputStreamWriter(context.openFileOutput(fileName, context.MODE_PRIVATE | context.MODE_APPEND));
            for (String content : contents) {
                outputStreamWriter.write(content);
            }
        }
        catch(IOException e)
        {
            Log.e("Exception", "File writing Failure " + e.toString());
        }
        finally
        {
            if(outputStreamWriter != null)
            {
                try
                {
                    outputStreamWriter.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

}
