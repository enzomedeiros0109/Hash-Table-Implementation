public class PerformanceAnalyzer {

    public static void run() {
        CSVReader reader = new CSVReader();
        reader.readCSV();
        Book[] books = reader.books;

        // Roda todas as combinações: Hashes (1 a 3) vs Colisões (1 a 2)
        for (int h = 1; h <= 3; h++) {
            for (int c = 1; c <= 2; c++) {
                benchmark(books, h, c);
            }
        }
    }

    private static void benchmark(Book[] books, int hashOption, int collisionOption) {
        HashTable table = new HashTable();

        long startInsert = System.nanoTime();
        for (Book book : books) {
            table.add(book, hashOption, collisionOption);
        }
        long endInsert = System.nanoTime();

        double fatorCarga = (double) table.size / table.capacity;
        int colisoesTotais = table.totalCollisions;

        double tempoMedioInsercao = ((endInsert - startInsert) / 1000000.0) / books.length;

        // MEDIÇÃO DE BUSCA

        long startSearch = System.nanoTime();

        for (Book book : books) {
            table.searchBook(book.ISBN, hashOption, collisionOption);
        }

        long endSearch = System.nanoTime();
        double tempoMedioBusca = ((endSearch - startSearch) / 1000000.0) / books.length;

        // MEDIÇÃO DE REMOÇÃO

        long startRemove = System.nanoTime();

        for (Book book : books) {
            table.removeBook(book.ISBN, hashOption, collisionOption);
        }

        long endRemove = System.nanoTime();
        double tempoMedioRemocao = ((endRemove - startRemove) / 1000000.0) / books.length;

        // RESULTADOS

        String nomeHash;
        if (hashOption == 1) {
            nomeHash = "Divisão";
        } else if (hashOption == 2) {
            nomeHash = "Dobra";
        } else {
            nomeHash = "Multiplicação";
        }

        String nomeColisao;
        if (collisionOption == 1) {
            nomeColisao = "Linear";
        } else {
            nomeColisao = "Quadrática";
        }

        System.out.println("=======================================================");
        System.out.println("CASO: Hash [" + nomeHash + "] + Colisão [" + nomeColisao + "]");
        System.out.println("=======================================================");
        System.out.println("Quantidade total de colisões : " + colisoesTotais);
        System.out.printf("Fator de carga               : %.6f\n", fatorCarga);
        System.out.printf("Tempo médio de inserção      : %.6f ms\n", tempoMedioInsercao);
        System.out.printf("Tempo médio de busca         : %.6f ms\n", tempoMedioBusca);
        System.out.printf("Tempo médio de remoção       : %.6f ms\n", tempoMedioRemocao);
        System.out.println();
    }
}