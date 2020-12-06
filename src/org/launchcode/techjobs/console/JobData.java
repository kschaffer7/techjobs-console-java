package org.launchcode.techjobs.console;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by LaunchCode
 */
public class JobData {

    private static final String DATA_FILE = "resources/job_data.csv";
    private static Boolean isDataLoaded = false;

    private static ArrayList<HashMap<String, String>> allJobs;

    /**
     * Fetch list of all values from loaded data,
     * without duplicates, for a given column.
     *
     * @param field The column to retrieve values from
     * @return List of all of the values of the given field
     */
    public static ArrayList<String> findAll(String field) {

        // load data, if not already loaded
        loadData();

        ArrayList<String> values = new ArrayList<>();

        for (HashMap<String, String> row : allJobs) {
            String aValue = row.get(field);

            if (!values.contains(aValue)) {
                values.add(aValue);
            }
        }

        return values;
    }

    public static ArrayList<HashMap<String, String>> findAll() {

        // load data, if not already loaded
        loadData();

        return allJobs;
    }

    /**
     * Returns results of search the jobs data by key/value, using
     * inclusion of the search term.
     *
     * For example, searching for employer "Enterprise" will include results
     * with "Enterprise Holdings, Inc".
     *
     * @param column   Column that should be searched.
     * @param value Value of teh field to search for
     * @return List of all jobs matching the criteria
     */
    public static ArrayList<HashMap<String, String>> findByColumnAndValue(String column, String value) {

        // load data, if not already loaded
        loadData();

        ArrayList<HashMap<String, String>> jobs = new ArrayList<>();

        for (HashMap<String, String> row : allJobs) {

            String aValue = row.get(column);
//
            if (aValue.toLowerCase().contains(value.toLowerCase())) {
                jobs.add(row);
            }
        }

        return jobs;
    }

    /**
     * Read in data from a CSV file and store it in a list
     */
    private static void loadData() {

        // Only load data once
        if (isDataLoaded) {
            return;
        }

        try {

            // Open the CSV file and set up pull out column header info and records
            Reader in = new FileReader(DATA_FILE);
            CSVParser parser = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
            List<CSVRecord> records = parser.getRecords();
            Integer numberOfColumns = records.get(0).size();
            String[] headers = parser.getHeaderMap().keySet().toArray(new String[numberOfColumns]);

            allJobs = new ArrayList<>();

            // Put the records into a more friendly format
            for (CSVRecord record : records) {
                HashMap<String, String> newJob = new HashMap<>();

                for (String headerLabel : headers) {
                    newJob.put(headerLabel, record.get(headerLabel));
                }

                allJobs.add(newJob);
            }

            // flag the data as loaded, so we don't do it twice
            isDataLoaded = true;

        } catch (IOException e) {
            System.out.println("Failed to load job data");
            e.printStackTrace();
        }
    }

    //Make case insensitive
    // new (public static) method that will search for a string within each of the columns. Name findByValue.
    // public: any class can call this method aka TechJobs;
    // static: a method of the class that doesn't require an instance of that class to be created
    // ArrayList<HashMap<String, String>>: This method returns an ArrayList of HashMaps where the key and value are both strings
    public static ArrayList<HashMap<String, String>> findByValue(String value) {
        // load data, if not already loaded
        loadData();
        // creates an empty ArrayList that will contain jobs that match the string value from the search
        ArrayList<HashMap<String, String>> jobs = new ArrayList<>();
        // looping through the entire job list by each row
        for (HashMap<String, String> job : allJobs) {
            Boolean jobMatches = false;
            // loop through each value within the HashMap of job
            for (Object jobKey: job.keySet()){
                if (job.get(jobKey).toLowerCase().contains(value.toLowerCase())) {
                    // if there is a match, add the job to the jobs ArrayList
                    jobMatches = true;
                }
            }
            if (jobMatches){
                jobs.add(job);
            }
        }
        // return the successfully matched jobs
        return jobs;
    }
//     case sensitive version
//     new (public static) method that will search for a string within each of the columns. Name findByValue.
//     public: any class can call this method aka TechJobs;
//     static: a method of the class that doesn't require an instance of that class to be created
//     ArrayList<HashMap<String, String>>: This method returns an ArrayList of HashMaps where the key and value are both strings
//    public static ArrayList<HashMap<String, String>> findByValue(String value) {
//
//        // load data, if not already loaded
//        loadData();
//        // creates an empty ArrayList that will contain jobs that match the string value from the search
//        ArrayList<HashMap<String, String>> jobs = new ArrayList<>();
//        // looping through the entire job list by each row
//        for (HashMap<String, String> job : allJobs) {
//            // to see if the typed search matches any column in the job
//            if (job.containsValue(value)){
//                // if there is a match, add the job to the jobs ArrayList
//                jobs.add(job);
//            }
//        }
//        // return the successfully matched jobs
//        return jobs;
//    }
}
