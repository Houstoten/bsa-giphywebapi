package com.bsa.giphyWebAPI.utils;

import com.bsa.giphyWebAPI.Repository.BaseRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

@Component
public class CsvReaderWriter {

    @Autowired
    private BaseRepository baseRepository;

    public void writeCsv(Date date, String userId, String query, String filePath) {
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
}
