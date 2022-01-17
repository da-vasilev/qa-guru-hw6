package com.severstal;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static org.assertj.core.api.Assertions.assertThat;

public class ReadZipFile {

    @Test
    void readZipFiles() throws Exception  {
        ZipFile zipFile = new ZipFile("src\\test\\resources\\out.zip");
        ZipEntry zipEntry;

        zipEntry = zipFile.getEntry("docPDF.pdf");
        try (InputStream stream = zipFile.getInputStream(zipEntry)) {
            PDF parse = new PDF(stream);
            assertThat(parse.text).contains("A Simple PDF File");
        }

        zipEntry = zipFile.getEntry("docCSV.csv");
        try (InputStream stream = zipFile.getInputStream(zipEntry)) {
            CSVReader reader = new CSVReader(new InputStreamReader(stream));
            List<String[]> list = reader.readAll();
            assertThat(list)
                    .hasSize(2)
                    .contains(
                            new String[]{"Text1", "text-1"},
                            new String[]{"Text2", "text-2"});
        }

        zipEntry = zipFile.getEntry("docXLSX.xlsx");
        try (InputStream stream = zipFile.getInputStream(zipEntry)) {
            XLS parsed = new XLS(stream);
            assertThat(parsed.excel.getSheetAt(0).getRow(0).getCell(0).getStringCellValue())
                    .isEqualTo("Text1");
        }
    }
}