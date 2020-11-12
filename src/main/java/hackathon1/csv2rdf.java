package hackathon1;

import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.riot.Lang;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class csv2rdf {
    private static String[] strs = {"Universitati", "Ramura-stiinta", "Programe-Studiu", "Program", "Facultati", "Domeniu-studiu", "Domeniu-fundamental"};

    public static void main(String[] args) {

        for (String str : strs) {
            Model m = ModelFactory.createDefaultModel();
            // read csv:
            try (InputStream is = new FileInputStream("resources/csv/" + str + ".csv")) {
                RDFDataMgr.read(m, is, "http://example.org", Lang.CSV);
            } catch (IOException e) {
                e.printStackTrace();
            }
            // print to console:
            m.write(System.out, "ttl");

            Path p = null;
            try { // save xml
                p = Files.createTempFile("rdf-", ".xml");
                System.out.println("Save to " + p);
            } catch (IOException e) {
                e.printStackTrace();
            }

            try (OutputStream out = new FileOutputStream("resources/rdf/" + "rdf-" + str + ".xml")) {
                RDFDataMgr.write(out, m, Lang.RDFXML);
                //Files.lines(p).forEach(System.out::println);  // print file to console
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
