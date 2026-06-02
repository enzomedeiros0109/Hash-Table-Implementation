import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CSVReader {

    String file = "src/data/ARQUIVO PARA TRABALHO AV3.csv";
    String line = "";
    String splitter = ",";
    Book[] books = new Book[1000];

    void readAndFill(HashTable hashTable, int hashOption, int collisionOption){

        try {

            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

            while ((line = bufferedReader.readLine()) != null){

                String[] columns = line.split(splitter);

                if (columns[0].trim().equalsIgnoreCase("ISBN")) {
                    continue;
                }

                String stringISBN = columns[0].trim();
                long ISBN = Long.parseLong(stringISBN);

                String title = columns[1].trim();
                String author = columns[2].trim();
                String year = columns[3].trim();

                Book book = new Book(ISBN, title, author, year);
                hashTable.add(book, hashOption, collisionOption);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    void readCSV(){
        int index = 0;
        try {

            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

            while ((line = bufferedReader.readLine()) != null){

                String[] columns = line.split(splitter);

                if (columns[0].trim().equalsIgnoreCase("ISBN")) {
                    continue;
                }

                String stringISBN = columns[0].trim();
                long ISBN = Long.parseLong(stringISBN);

                String title = columns[1].trim();
                String author = columns[2].trim();
                String year = columns[3].trim();

                Book book = new Book(ISBN, title, author, year);
                books[index] = book;
                index++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
