# movida
## Informazioni gruppo
Gruppo: Bassoli_Dong

Componenenti del gruppo: Lorenzo Bassoli, Marco Dong

## Algoritmi e strutture dati:
<code>
BubbleSort, MergeSort, AVL, HashIndirizzamentoAperto
</code>

## Come eseguire
Importare nell'IDE come progetto Maven.


## Funzionamento

Come prima cosa è necessario aver importato MovidaCore, nel package movida.bassolidong, per instanziare la classe c'è da passare un paramentro <code>int</code> che rappresenta la dimensione dell'array della Hash Table.

Se non si specifica l'algoritmo di ordinamento oppure la struttura dati, vengono utilizzati di default l'algoritmo MergeSort e la struttura dati HashIndirizzamentoAperto.

Le chiavi della AVL e Hash Table sono i titoli.

### Eccezioni
Ci sono tre tipi di eccezioni che possono sollevarsi, in base all'errore:
-  <code>MovidaFileException</code>, se il file fornito a MovidaCore non è nel formato giusto (sono stati utilizzati espressioni regolari)

    <code>
    title = "Title:\\s+([A-Z0-9]+[a-z0-9]*(\\s)*)+(\\s)*";

    year = "Year:\\s+[0-9]+(\\s)*";

    director = "Director:\\s+([A-Z]+[a-z]*(\\s)*)+(\\s)*";

    cast = "Cast:\\s+(([A-Z]+[a-z]*(\\s)*)+(,\\s)*)+(\\s)*";

    votes = "Votes:\\s+[0-9]+(\\s)*";
    </code>
- <code>ArrayOutOfSizeException</code>, se si cerca di aggiungere una nuova chiave oltre al limite all'interno della Hash Table 
- <code>IOException</code>, se il file fornito non è valido


    