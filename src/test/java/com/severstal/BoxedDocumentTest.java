package com.severstal;

import org.junit.jupiter.api.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class BoxedDocumentTest {
    private final ClassLoader cl = getClass().getClassLoader();

    @Test
    void boxedInZipFile() throws IOException {
        try (ZipOutputStream out = new ZipOutputStream(new FileOutputStream("src/test/resources/out.zip"));
            InputStream stream = cl.getResourceAsStream("docPDF.pdf");
            InputStream stream2 = cl.getResourceAsStream("docXLSX.xlsx");
            InputStream stream3 = cl.getResourceAsStream("docCSV.csv")) {

            out.putNextEntry(new ZipEntry("docPDF.pdf"));
            out.write(stream.readAllBytes());
            out.putNextEntry(new ZipEntry("docXLSX.xlsx"));
            out.write(stream2.readAllBytes());
            out.putNextEntry(new ZipEntry("docCSV.csv"));
            out.write(stream3.readAllBytes());
            out.closeEntry();
        }
    }
}
