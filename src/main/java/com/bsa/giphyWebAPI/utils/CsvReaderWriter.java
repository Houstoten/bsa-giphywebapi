package com.bsa.giphyWebAPI.utils;

import com.bsa.giphyWebAPI.Exceptions.InvalidUserException;
import com.bsa.giphyWebAPI.Repository.BaseRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;

@Component
public class CsvReaderWriter {

    @Autowired
    private BaseRepository baseRepository;

    public void writeCsv(LocalDate date, String userId, String query, String filePath) {
        try (
                BufferedWriter writer = new BufferedWriter(
                        new FileWriter(baseRepository.getUsersDirectory() + "/" + userId + "/history.csv"
                                , true));
                CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT);
        ) {
            csvPrinter.printRecord(date, query, filePath);
            csvPrinter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Map<String, String>> readCsv(String userId) {
        try (
                Reader reader = new BufferedReader(
                        new FileReader(baseRepository.getUsersDirectory() + "/" + userId + "/history.csv"));
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);
        ) {
            List<Map<String, String>> history = new ArrayList<>();
            for (CSVRecord record : csvParser) {
                history.add(Map.of("date", record.get(0), "query", record.get(1), "gif", record.get(2)));
            }
            return history;
        } catch (IOException e) {
            throw new InvalidUserException(userId);
        }
    }
}
