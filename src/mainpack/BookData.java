package mainpack;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class BookData {
        private static final File file = new File("books.dat");
        private static final Properties books = new Properties();


        public Properties loadData () {
            try {
                if (!file.exists()) file.createNewFile();
                FileReader fileReader = new FileReader(file);
                books.load(fileReader);
                fileReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return books;
        }

        public void storeData () {
            try {
                FileWriter fileWriter = new FileWriter(file, false);
                books.store(fileWriter, null);
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public static Properties getBooks() {return books;}
}
