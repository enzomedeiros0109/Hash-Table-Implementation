public class HashTable {

    Book[] table;
    int size;
    final int capacity = 1301;
    int totalCollisions = 0;

    public HashTable() {
        // Load factor = 0.7
        table = new Book[capacity];
        size = 0;
    }

    /*
    O que faz: Pega a chave (ISBN) inteira e calcula o resto da divisão pelo tamanho da tabela.
    Justificativa: Operação eficiente por ser O(1)
     */

    int hash1(long ISBN){
        return (int) (ISBN % capacity);
    }

    /*
    O que faz: Divide o ISBN a cada três números e depois os soma
    Justificativa: Utiliza todos os algarismos para gerar um índice, resultando em menos colisões
     */
    int hash2(long ISBN){
        long sum = 0;
        long temp = ISBN;

        while (temp > 0) {
            sum += temp % 1000; // Pega os últimos 3 dígitos
            temp /= 1000;       // Remove os últimos 3 dígitos de temp
        }

        return (int) (sum % capacity);
    }

    /*
    O que faz: Isola os últimos 3 dígitos e os multiplica por um 8
    Justificativa: Os únicos algarismos que mudam em ISBN são os 3 primeiros (000 a 999), o que
                   torna o método otimizado especificamente para essa tabela.
     */

    int hash3(long ISBN) {
        return (int) ((ISBN % 1000) * 7) % capacity;
    }

    /*
    O que faz: O funcionamento matemático é baseado numa função polinomial do 2º grau
     */
    int quadraticTest(int index) {
        int attempt = 0;
        int probeIndex = index;

        while (table[probeIndex] != null) {
            totalCollisions++;
            attempt++;
            // Teste quadrático (C1, C2 = 1
            probeIndex = (index + attempt + (attempt * attempt)) % capacity;

            if (attempt > capacity) {
                return -1; // Tabela cheia
            }
        }

        return probeIndex;
    }

    /*
    O que faz: Utiliza a abordagem de Sondagem Linear mas com uso de um salto constante.
     */
    int linearProbing(int index) {
        int attempt = 0;
        int probeIndex = index;
        final int step = 7;

        while (table[probeIndex] != null) {
            totalCollisions++;
            attempt++;
            // Multiplica a tentativa pelo valor de step
            probeIndex = (index + attempt * step) % capacity;

            if (attempt > capacity) {
                return -1; // Tabela cheia
            }
        }

        return probeIndex;
    }

    // Adicione um método genérico na sua HashTable
    public void add(Book book, int hashOption, int collisionOption) {
        if (book == null) return;

        int index;
        switch (hashOption) {
            case 1 -> index = hash1(book.ISBN);
            case 2 -> index = hash2(book.ISBN);
            case 3 -> index = hash3(book.ISBN);
            default -> {
                //System.out.println("Opção de Hash inválida.");
                return;
            }
        }

        if (table[index] == null) {
            table[index] = book;
            size++;
            //System.out.println("O livro de ISBN " + book.ISBN + " foi adicionado!");
            return;
        }

        // Tratamento de colisão
        switch (collisionOption) {
            case 1 -> index = linearProbing(index); // Linear com salto 7
            case 2 -> index = quadraticTest(index); // Quadrático
            default -> {
                //System.out.println("Opção de colisão inválida.");
                return;
            }
        }

        if (index == -1) {
            //System.out.println("A Tabela está cheia!");
            return;
        }

        table[index] = book;
        size++;
        //System.out.println("O livro de ISBN " + book.ISBN + " foi adicionado!");
    }

    void removeBook(long ISBN, int hashOption, int collisionOption){

        if (ISBN <= 0) return;

        int index = searchBookIndex(ISBN, hashOption, collisionOption);
        if (index != -1){
            table[index] = null;
            size--;
            //System.out.println("O livro de ISBN " + ISBN + " foi removido!");
        }
    }

    Book searchBook(long ISBN, int hashOption, int collisionOption) {

        if (ISBN <= 0) return null;

        int index;
        switch (hashOption) {
            case 1 -> index = hash1(ISBN);
            case 2 -> index = hash2(ISBN);
            case 3 -> index = hash3(ISBN);
            default -> {
                //System.out.println("Opção de Hash inválida.");
                return null;
            }
        }

        int attempt = 0;
        int probeIndex = index;
        final int step = 7;

        // Teste Quadrático
        while (table[probeIndex] != null) {
            if (table[probeIndex].ISBN == ISBN) {
                //System.out.println("Livro encontrado!");
                return table[probeIndex];
            }

            //  Próximo índice
            attempt++;

            switch (collisionOption) {
                case 1 -> probeIndex = (index + attempt * step) % capacity; // Linear
                case 2 -> probeIndex = (index + attempt + (attempt * attempt)) % capacity; // Quadrático
                default -> {
                    //System.out.println("Opção de colisão inválida.");
                    return null;
                }
            }
            // Para não percorrer a tabela mais de uma vez
            if (attempt > capacity) {
                break;
            }
        }

        // Não existe
        //System.out.println("O livro não existe na tabela!");
        return null;
    }

    int searchBookIndex(long ISBN, int hashOption, int collisionOption) {

        if (ISBN <= 0) return -1;

        int index;
        switch (hashOption) {
            case 1 -> index = hash1(ISBN);
            case 2 -> index = hash2(ISBN);
            case 3 -> index = hash3(ISBN);
            default -> {
                //System.out.println("Opção de Hash inválida.");
                return -1;
            }
        }

        int attempt = 0;
        int probeIndex = index;
        final int step = 7;

        // Procura posição não vazia
        while (table[probeIndex] != null) {
            if (table[probeIndex].ISBN == ISBN) {
                //System.out.println("Livro encontrado!");
                return probeIndex;
            }

            // Próximo índice
            attempt++;
            switch (collisionOption) {
                case 1 -> probeIndex = (index + attempt * step) % capacity; // Linear
                case 2 -> probeIndex = (index + attempt + (attempt * attempt)) % capacity; // Quadrático
                default -> {
                    //System.out.println("Opção de colisão inválida.");
                    return -1;
                }
            }

            if (attempt > capacity) {
                break;
            }
        }

        // Não existe
        //System.out.println("O livro não existe na tabela!");
        return -1;
    }

    public void printConcentrationMap() {
        System.out.println("\n--- Mapa de Concentração da Tabela ---");
        final int BLOCK_SIZE = 130;

        // O laço principal agora lê-se quase como texto em inglês puro
        for (int startIndex = 0; startIndex < capacity; startIndex += BLOCK_SIZE) {

            int endIndex = Math.min(startIndex + BLOCK_SIZE - 1, capacity - 1);
            int occupiedCount = countOccupiedSlots(startIndex, endIndex);

            System.out.printf("Índices [%4d a %4d]: %3d livros ocupados\n", startIndex, endIndex, occupiedCount);
        }
    }

    private int countOccupiedSlots(int start, int end) {
        int count = 0;
        for (int i = start; i <= end; i++) {
            if (table[i] != null) {
                count++;
            }
        }
        return count;
    }
}
