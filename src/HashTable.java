import java.util.Arrays;

public class HashTable {

    class Book{
        long ISBN;
        String title, author;
        int year;

        public Book(long ISBN, String title, String author, int year) {
            this.ISBN = ISBN;
            this.title = title;
            this.author = author;
            this.year = year;
        }
    }

    Book[] table;
    int size;
    final int capacity = 1301;

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
    O que faz: Divide o ISBN de três em três números e depois os soma
    Justificativa: Utiliza todos os algarismos para gerar um índice, resultando em menos colisões
     */
    int hash2(long ISBN){
        long sum = 0;
        long temp = ISBN;

        while (temp > 0) {
            sum += temp % 1000; // Pega os últimos 3 digitos
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
    O que faz: O funcionamento matemático é baseado em uma função polinomial do 2º grau
     */
    int quadraticTest(int index) {
        int attempt = 0;
        int probeIndex = index;

        while (table[probeIndex] != null) {
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
            attempt++;
            // ultiplica a tentativa pelo valor de step
            probeIndex = (index + attempt * step) % capacity;

            if (attempt > capacity) {
                return -1; // Tabela cheia
            }
        }

        return probeIndex;
    }

    void addHash1Linear(Book book){
        int index = hash1(book.ISBN);

        if (table[index] == null){
            table[index] = book;
            size++;
            System.out.println("O livro de ISBN " + book.ISBN + " foi adicionado!");
            return;
        }

        index = linearProbing(index);

        if (index == -1){
            System.out.println("A Tabela está cheia!");
            return;
        }

        table[index] = book;
        size++;
    }

    void addHash1Quadratic(Book book){
        int index = hash1(book.ISBN);

        if (table[index] == null){
            table[index] = book;
            size++;
            System.out.println("O livro de ISBN " + book.ISBN + " foi adicionado!");
            return;
        }

        index = quadraticTest(index);

        if (index == -1){
            System.out.println("A Tabela está cheia!");
            return;
        }

        table[index] = book;
        size++;
    }

    void addHash2Linear(Book book){
        int index = hash2(book.ISBN);

        if (table[index] == null){
            table[index] = book;
            size++;
            System.out.println("O livro de ISBN " + book.ISBN + " foi adicionado!");
            return;
        }

        index = linearProbing(index);

        if (index == -1){
            System.out.println("A Tabela está cheia!");
            return;
        }

        table[index] = book;
        size++;
    }

    void addHash2Quadratic(Book book){
        int index = hash2(book.ISBN);

        if (table[index] == null){
            table[index] = book;
            size++;
            System.out.println("O livro de ISBN " + book.ISBN + " foi adicionado!");
            return;
        }

        index = quadraticTest(index);

        if (index == -1){
            System.out.println("A Tabela está cheia!");
            return;
        }

        table[index] = book;
        size++;
    }

    void addHash3Linear(Book book){
        int index = hash3(book.ISBN);

        if (table[index] == null){
            table[index] = book;
            size++;
            System.out.println("O livro de ISBN " + book.ISBN + " foi adicionado!");
            return;
        }

        index = linearProbing(index);

        if (index == -1){
            System.out.println("A Tabela está cheia!");
            return;
        }

        table[index] = book;
        size++;
    }

    void addHash3Quadratic(Book book){
        int index = hash3(book.ISBN);

        if (table[index] == null){
            table[index] = book;
            size++;
            System.out.println("O livro de ISBN " + book.ISBN + " foi adicionado!");
            return;
        }

        index = quadraticTest(index);

        if (index == -1){
            System.out.println("A Tabela está cheia!");
            return;
        }

        table[index] = book;
        size++;
    }

    void removeBook(long ISBN){
        int index = searchBookIndex(ISBN);
        if (index != -1){
            table[index] = null;
            size--;
        }
    }

    Book searchBook(long ISBN) {
        int index = hash3(ISBN);
        int attempt = 0;
        int probeIndex = index;

        // Teste Quadrático
        while (table[probeIndex] != null) {
            if (table[probeIndex].ISBN == ISBN) {
                System.out.println("Livro encontrado!");
                return table[probeIndex];
            }

            //  Próximo índice
            attempt++;
            probeIndex = (index + attempt + (attempt * attempt)) % capacity;

            // Para não percorrer a tabela mais de uma vez
            if (attempt > capacity) {
                break;
            }
        }

        // Não existe
        System.out.println("O livro não existe na tabela!");
        return null;
    }

    int searchBookIndex(long ISBN) {
        int index = hash3(ISBN);
        int attempt = 0;
        int probeIndex = index;

        // Teste Quadrático
        while (table[probeIndex] != null) {
            if (table[probeIndex].ISBN == ISBN) {
                System.out.println("Livro encontrado!");
                return probeIndex;
            }

            // Próximo índice
            attempt++;
            probeIndex = (index + attempt + (attempt * attempt)) % capacity;

            // Para não percorrer a tabela mais de uma vez
            if (attempt > capacity) {
                break;
            }
        }

        // Não existe
        System.out.println("O livro não existe na tabela!");
        return -1;
    }

}
