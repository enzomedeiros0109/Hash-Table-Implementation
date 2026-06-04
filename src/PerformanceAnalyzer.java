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

        for (int h = 1; h <= 3; h++) {
            for (int c = 1; c <= 2; c++) {
                PerformanceAnalyzer.runSearchTests(reader.books, h, c);
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
        table.printConcentrationMap();

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

    public static void runSearchTests(Book[] sampleBooks, int hashOption, int collisionOption) {
        System.out.println("\n=======================================================");
        System.out.println("TESTE DE BUSCAS - Hash " + hashOption + " | Colisão " + collisionOption);
        System.out.println("=======================================================");

        HashTable table = new HashTable();
        for (Book book : sampleBooks) {
            if (book != null) table.add(book, hashOption, collisionOption);
        }

        // 50 buscas de ISBN's EXISTENTES
        System.out.println("\n[ Busca 50 ISBNs Existentes ]");
        int foundCount = 0;
        long startExist = System.nanoTime();

        // 50 Primeiros livros do array
        for (int i = 0; i < 50; i++) {
            long targetISBN = sampleBooks[i].ISBN;
            Book result = table.searchBook(targetISBN, hashOption, collisionOption);
            if (result != null) foundCount++;
        }

        long endExist = System.nanoTime();
        double timeExist = (endExist - startExist) / 1e6;
        System.out.printf("Total encontrados: %d/50 | Tempo total: %.4f ms\n", foundCount, timeExist);


        // 20 buscas de ISBN's INEXISTENTES
        System.out.println("\n[ Busca 20 ISBNs Inexistentes ]");
        int notFoundCount = 0;
        long startFake = System.nanoTime();

        long fakeISBNNumber = 9999999999900L;
        for (int i = 1; i <= 20; i++) {
            long fakeISBN = fakeISBNNumber + i;
            Book result = table.searchBook(fakeISBN, hashOption, collisionOption);
            if (result == null) notFoundCount++;
        }

        long endFake = System.nanoTime();
        double timeFake = (endFake - startFake) / 1e6;
        System.out.printf("Total não encontrados (correto): %d/20 | Tempo total: %.4f ms\n", notFoundCount, timeFake);
    }
}