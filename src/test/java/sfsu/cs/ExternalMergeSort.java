package sfsu.cs;

/**
 * @author Rajani Shivaraj Maski
 *
 *          UML
 *
 *
  *******************************************************************

  ExternalMergeSort
 *******************************************************************
   File inputFile
   File outputFile
   int pageSize = 4096;
   int limit = 4;
   int size = 0;
 *******************************************************************
   ExternalMergeSort(String inputFilePath)
   sort():void
   firstPass(File inputFile): ArrayList<File>
   readPageFromFile(InputStream inputStream) : ArrayList<Integer>
   sortFilesData(File file1, File file2) : File
    sortMergeFiles(ArrayList<File> intermediaryFiles) :  ArrayList<File>
   writePageToDisk(ArrayList<Integer> intArray) : File

  *******************************************************************

 */

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Scanner;

public class ExternalMergeSort {

    File inputFile;
    File outputFile = new File("op.txt");
    int pageSize = 4096;
    int limit = 4;
    int size = 0;

    /**
     * Single arg Constructor that requires input age.txt file path.
     * @param inputFilePath
     */
    ExternalMergeSort(String inputFilePath) {
        this.inputFile = new File(inputFilePath);
    }

    /**
     * main method to init/test external merge sort
     *
     * @param args no arguments passed, hardcoded input file path.
     */
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        String inputFilePath = scanner.nextLine();
        ExternalMergeSort externalMergeSort;
        System.out.println("started at " +new Date() );
        long timeNow = System.currentTimeMillis();

        externalMergeSort = new ExternalMergeSort(inputFilePath);
       // externalMergeSort = new ExternalMergeSort("/Users/rajanishivarajmaski1/Downloads/age.txt");

        externalMergeSort.sort();
        System.out.println("Sorting complete");

        System.out.println("Ended at " +new Date() );
        System.out.println("Time taken in  seconds: " + (System.currentTimeMillis()-timeNow)/1000);

        System.out.println("Sorted file location: " + externalMergeSort.outputFile.getAbsolutePath());
    }

    /**
     * start sorting
     */
    public void sort() {

        ArrayList<File> fileList = firstPass(inputFile);
        size = fileList.size();

        //Add empty file if the number of intermediate files are not even in number.
        if (size % 2 != 0) {
            fileList.add(null);
            size++;
        }

        File outFile;

        //write final temp file to disk.
        try {
            outFile = sortMergeFiles(fileList).get(0);
            Path source = Paths.get(outFile.getAbsolutePath());
            Path newDirectory = Paths.get(outputFile.getAbsolutePath());
            Files.move(source, newDirectory, StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * This method performs Pass 0 to divide the input file into pages.
     *
     * @param inputFile input file for splitting into pages of size pageSize.
     * @return ArrayList of intermediate files created by Pass 0.
     */
    public ArrayList<File> firstPass(File inputFile) {

        ArrayList<File> fileList = new ArrayList<File>();
        InputStream inputStream = null;
        int bufferContentRead;

        try {

            inputStream = new FileInputStream(inputFile);
            int l = 44;
            while (inputStream.available() > 0) {

                ArrayList<Integer> tempArray = new ArrayList<Integer>();
                byte[] bytesFromPage = new byte[pageSize + limit];

                //Read only buffer sized content
                bufferContentRead = inputStream.read(bytesFromPage, 0, (inputStream.available() >= pageSize) ? pageSize : inputStream.available());

                while (inputStream.available() > 0 && bytesFromPage[bufferContentRead - 1] != l) {
                    inputStream.read(bytesFromPage, bufferContentRead++, 1);
                }

                String s = new String(bytesFromPage);
                String[] splitArray = s.trim().split(",");

                for (String str : splitArray) {
                    tempArray.add(Integer.parseInt(str));
                }

                //sort using java collection sort method
                Collections.sort(tempArray);
                fileList.add(writePageToDisk(tempArray));

            }
        } catch (IOException | NumberFormatException e) {
            System.err.println(e.getLocalizedMessage());
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null)
                    inputStream.close();
            } catch (IOException e) {
                System.err.println(e.getLocalizedMessage());
                e.printStackTrace();
            }
        }

        return fileList;

    }

    /**
     * This method writes Pass 0 temporary files into disk.
     *
     * @param intArray ArrayList of integers parsed from file
     * @return temporary first pass file written in disk
     * @throws IOException
     */
    public File writePageToDisk(ArrayList<Integer> intArray) throws IOException {

        File fileTemp = File.createTempFile("firstPass", ".txt");
        fileTemp.deleteOnExit();

        BufferedWriter bw = new BufferedWriter(new FileWriter(fileTemp));
        try {
            for (Integer r : intArray) {
                bw.write(r + ",");
            }
        } finally {
            bw.close();
        }

        return fileTemp;

    }

    /**
     * To recursively perform external merge sort using two input and an output buffers of size pageSize.
     *
     * @param intermediaryFiles ArrayList of intermediate files on disk written recursively
     * @return ArrayList of intermediate files.
     * @throws IOException
     */
    public ArrayList<File> sortMergeFiles(ArrayList<File> intermediaryFiles) throws IOException {

        if (intermediaryFiles.size() == 1)
            return intermediaryFiles;

        intermediaryFiles.add(sortFilesData(intermediaryFiles.get(0), intermediaryFiles.get(1)));

        intermediaryFiles.remove(0);
        intermediaryFiles.remove(0);

        if (intermediaryFiles.size() != 1 && intermediaryFiles.size() == size / 2 && intermediaryFiles.size() % 2 != 0) {
            intermediaryFiles.add(null);
            size = intermediaryFiles.size();
        }

        return sortMergeFiles(intermediaryFiles);

    }

    /**
     * Read buffer of size pageSize and finally the left over data in file if pageSize is less than available size in file.
     * returns content parsed to list of integers
     *
     * @param inputStream
     * @return ArrayList of Integers
     * @throws IOException
     */
    private ArrayList<Integer> readPageFromFile(InputStream inputStream) throws IOException {

        ArrayList<Integer> tempArray = new ArrayList<Integer>();
        int len;

        byte[] bytesFromPage = new byte[pageSize + limit];

        len = inputStream.read(bytesFromPage, 0, (inputStream.available() >= pageSize) ? pageSize : inputStream.available());

        while (inputStream.available() > 0 && bytesFromPage[len - 1] != 44) {
            inputStream.read(bytesFromPage, len++, 1);

        }

        String data = new String(bytesFromPage);

        String[] splitArray = data.trim().split(",");

        for (String str : splitArray) {
            tempArray.add(Integer.parseInt(str));
        }

        return tempArray;
    }

    /**
     * Performs merge sort on two files content, using 2 input and 1 output buffers of size pageSize.
     *
     * @param file1 temp file 1
     * @param file2 temp file 2
     * @return temporary merged file
     */
    private File sortFilesData(File file1, File file2) {

        if (file2 == null) {
            return file1;
        }

        File fileTemp = null;
        BufferedWriter bufferedWriter = null;
        InputStream is1 = null;
        InputStream is2 = null;

        try {

            fileTemp = File.createTempFile("intermediaryFile", ".txt");
            fileTemp.deleteOnExit();

            bufferedWriter = new BufferedWriter(new FileWriter(fileTemp), pageSize + limit);

            is1 = new FileInputStream(file1);
            ArrayList<Integer> list1 = readPageFromFile(is1);

            is2 = new FileInputStream(file2);
            ArrayList<Integer> list2 = readPageFromFile(is2);

            while (!list1.isEmpty() || !list2.isEmpty()) {

                if (!list1.isEmpty() && list1.get(0) <= list2.get(0)) {
                    bufferedWriter.write(list1.get(0) + ",");
                    list1.remove(0);
                } else if (!list2.isEmpty()) {
                    bufferedWriter.write(list2.get(0) + ",");
                    list2.remove(0);
                }

                if (list1.isEmpty()) {
                    if (is1.available() > 0)
                        list1 = readPageFromFile(is1);
                }
                if (list2.isEmpty()) {
                    if (is2.available() > 0)
                        list2 = readPageFromFile(is2);
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is1 != null)
                    is1.close();

                if (is2 != null)
                    is2.close();

                if (bufferedWriter != null) {
                    bufferedWriter.flush();
                    bufferedWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return fileTemp;

    }

}
