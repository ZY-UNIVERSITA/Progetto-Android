# Relazione Finale del Progetto - App per Quiz di Lingue Orientali

## Scopo del Progetto

L’obiettivo del progetto è la realizzazione di un’applicazione Android per l’apprendimento e la memorizzazione di vocaboli delle lingue orientali (come giapponese, cinese, coreano), sfruttando quiz interattivi, uso di immagini e scrittura su schermo e la possibilità di avere una lista dei vocaboli e di riconoscere i caratteri. 
L'app permette di esplorare contenuti teorici, testare le proprie conoscenze con diverse tipologie di giochi linguistici, interagire con l’interfaccia scrivendo i caratteri con le dita e ricevere suggerimenti tramite l’impiego di un sistema basato su intelligenza artificiale e riconoscimento immagini.

L’applicazione permette di ottenere anche dati statistici sull'andamento dei quiz e la possibilità di modificare informazioni di profilo come avatar, nome utente e altre impostazioni di sistema per parte dei giochi.

---

## Struttura del Progetto

Il progetto segue la **Clean Architecture**, con chiara suddivisione in **quattro livelli principali**:

1. **App Module**: punto di ingresso dell'applicazione.
2. **Data Layer**: presenta l'accesso ai dati.
3. **Domain Layer**: presenta la logica di business.
4. **UI Layer**: rappresenta l'interfaccia utente.

Ogni layer è implementato come un **modulo indipendente**, seguendo il principio di `separation of concerns`.

### App Module

Il modulo principale funge da **entry point** dell’applicazione. Contiene la classe che estende `Application()` e gestisce l’inizializzazione generale dei componenti (come Hilt). Inoltre, aggrega tutte le dipendenze e funge da punto di collegamento tra gli altri moduli. Include l’accesso alle configurazioni generali e agli handler globali.

---

### Data Layer

Il layer ha il compito di fornire l’accesso ai dati remoti e locali. È composto da diversi sottomoduli:

#### `di` (Dependency Injection)

Contiene i moduli per Hilt, suddivisi in quattro file principali:

- **DatasourceModule**: fornisce binding di tutte le classi che accedono ai dati locali e remoti.
- **NetworkModule**: fornisce client REST tramite `Retrofit`, `OkHttp`, e un convertitore Moshi per la serializzazione JSON tramite `@Provides` e `@Singleton` per le librerie di terze parti senza poter usare `@Inject` in quanto librerie non modificabili direttamente.
- **RepositoryProviderModule**: definisce le implementazioni concrete dei Repository con `@Binds` e sono dei `@Singleton`.
- **UtilsClassModule**: espone singleton e implementazioni di classi di utilità come mapper con `@Binds` e sono dei `@Singleton`.

#### `local`

Contiene il database SQLite gestito tramite la libreria **Room**. Le sue componenti includono:

- **Entities**: definizione delle classi tabella (parole, quiz, dati degli utenti).
- **DAO**: interfacce con le query su Room.
- **Database.kt**: classe che estende RoomDatabase e rappresenta il punto di creazione del DB.

Include anche la gestione delle preferenze utente tramite **Preferences DataStore**.

#### `remote`

Contiene due sottomoduli:

- **ImageRecognitionModule**: gestisce il riconoscimento caratteri tramite immagine. Contiene:
  - Interfacce API Retrofit.
  - Modelli di risposta.
  - DataSource per gestire le richieste e le risposte remote.
  
- **WordDatabaseModule**: accede ad un archivio remoto JSON tramite HTTP che restituisce un database di vocaboli aggiornato. Include:
  - API Retrofit.
  - Modelli di risposta.
  - DataSource per gestire le richieste e le risposte remote.

- **Synchronization**: permette di sincronizzare i dati con il server remoto. Include:
  - API Retrofit.
  - Modelli di risposta.
  - DataSource per gestire le richieste e le risposte remote.

- **authentication**: permette di gestire le fasi di autenticazione, compreso il processo di registrazione e di login. Include:
  - API Retrofit.
  - Modelli di risposta.
  - DataSource per gestire le richieste e le risposte remote.

#### `repository`

Contiene le implementazioni concrete delle interfacce di repository del domain layer. Si occupa di:
  - Interagire sia con i database locali e remoti
  - Gestire le preferenze locali degli utenti
  - La gestione dei worker
  - La gestione della sincronizzazione e dell'autenticazione e dei servizi remoti.
  - Gestione dei file applicativi.

#### `utils`

Contiene le classi di utilià come le classi di **Mapper** utilizzate per convertire entità tra livelli (da DTO a Entity, da Entity a Model, ecc.).

#### `worker`

Modulo utilizzato per inserire logica di background tramite **WorkManager**. Include una classe `NotificationWorker` che invia una notifica all'utente nel caso l'app non venga aperta per 24 ore e una classe `SynchronizationWorker` per gestire la sincronizzazione automatica dei dati con il server per gli utenti loggati.

---

### Domain Layer

Il domain layer racchiude la logica di business tramite gli `use cases` e la definizione dei modelli usati `data class` sia nel data layer sia nell'UI layer. 

#### `di`

Contiene un modulo Hilt (`UseCaseModule`) che fornisce gli UseCase attraverso `@Binds` e `@Singleton`.

#### `usecase`

Contiene tutti gli scenari applicativi (use case) inseriti come classi (interfaccia funzionale + classe implementativa) Kotlin che richiamano le repository e le funzionalità di applicazioni come ad esempio la scelta randomica di un parola.

Ogni UseCase contiene solo logica di dominio e richiama i repository dove necessario.

#### `repository`

Contiene tutte le **interfacce dei repository**, senza dipendenze specifiche da tecnologie esterne.

#### `model`

I **model** descrivono gli oggetti che rappresentano concetti utilizzati nel dominio, come `Word`, `Quiz`, `UserData`, `Settings`, e sono utilizzabili in fase di test e sviluppo indipendentemente dagli altri 2 layers.

---

### UI Layer

Contiene la logica di presentazione dell’app. Utilizza componenti Android come `Activity`, `Fragment`, `ViewModel` per gestire gli elementi della UI e le coroutines tramite `StateFlow`, `SharedFlow` e `Channel` per supportare una UI reattiva.

#### `customview`

View personalizzate per la scrittura a mano libera dei caratteri. Il componente cattura i tratti dell’utente e restituisce un’immagine bitmap.

#### `findcharacter`

Un'Activity per effettuare il riconoscimento dei caratteri scritti, con chiamate alle API di riconoscimento tramite immagine.

#### `main`

Contiene la `MainActivity` e i fragment navigabili tramite una navigationBar, inclusi:

- Home Page
- Scelta dei giochi
- Elenco parole teoriche
- Profilo utente

#### `settings`

Activity che gestisce configurazioni personalizzate come ad esempio nome utente e le ripetizione delle parole durante la partita. 

#### `games`

Activity che contiene i diversi giochi linguistici, ognuno implementato in un fragment separato, come:

- Quiz a scelta multipla
- Associazione tra parola e significato
- Scrittura del carattere

#### `setup`

Activity mostrata all'avvio per il setup e l'aggiornamento dei dati dell'applicazione come le parole nuove. Si occupa di gestire anche la fase di login e registrazione.

#### `splashscreen`

Activity iniziale mostrata all’avvio app con il logo e il nome dell'applicazione.

#### `utils`

Modulo di uilità che fornisce supporto alla UI, come `UriToRawImageMapper` per la conversione di immagini provenienti dal Picker.

#### `res/`

Contiene le risorse dell'interfaccia:

- **drawable**: immagini vettoriali in formato XML, utilizzate per icone dei giochi, delle lingue e interfaccia.
- **layout**: file `.xml` associati a fragment, activity e custom view.
- **values**: valori statici (stringhe, stili, colori) uniformi su tutta l’app.

---

## Approccio alla Progettazione

Durante la progettazione, si è scelto di adottare un approccio orientato agli oggetti conforme ai **principi SOLID**, garantendo una maggiore facilità nella manutenzione, nella comprensione e nell'estensibilità del codice. In particolare:

- **Single Responsibility Principle (SRP)**: ogni classe ha una sola responsabilità ben definita. Ad esempio, i UseCase si occupano esclusivamente della logica di business, mentre i DataSource esclusivamente dell'accesso ai dati.
- **Open/Closed Principle (OCP)**: gran parte delle logiche (come i repository, i giochi, i riconoscimenti) sono progettate per essere estese (open) senza modificare il codice esistente (closed).
- **Liskov Substitution Principle (LSP)**: tutte le classi che implementano interfacce possono essere sostituite in modo trasparente senza errori a runtime.
- **Interface Segregation Principle (ISP)**: ad esempio le interfacce nel modulo `domain` sono piccole e specifiche, evitando obblighi di implementazione non richiesti.
- **Dependency Inversion Principle (DIP)**: la logica applicativa dipende da astrazioni e non da classi concrete, realizzato attraverso l'uso di interfacce e le dipendenze tra classi viene gestito con la `dependency injection` tramite Hilt.

Questo approccio ha reso il progetto altamente configurabile e scalabile nel tempo.

---

## Punti di Forza

Il progetto implementa tutte le moderne tecnologie di Android, offrendo una soluzione solida, modulare e scalabile. I principali punti di forza includono:

- Adozione completa di **MVVM** con il `viewModel` che si occupa di gestire l'interazione tra la UI e la logica di business: la UI conosce solo il modo di presentare i dati mentre il domain conosce solo la logici di business.
- Adozione della **Clean Architecture**, per una chiara separazione dei ruoli logici tra app layer, data layer, domain layer e UI layer. Questo garantisce alta menutenibilità.
- Progettato seguendo i **principi SOLID**:
  - Singola responsabilità per ogni classe (es. UseCase, ViewModel, Mapper, ecc.)
  - Inversione delle dipendenze tramite **Hilt**
  - Open/Closed nelle interfacce dei repository e formazioni dei UseCase
  - Interfacce ben definite nel layer domain e implementazioni modulabili dal layer data.
- Iniezione delle dipendenze tramite **Hilt**, evitando istanziazioni manuali e aumentando la testabilità.
- Networking basato su **Retrofit** e **OkHttp**, con parsing JSON tramite **Moshi**, semplificandone l'uso generale.
- Uso di **coroutine, Flow e Channel** per una UI asincrona, non bloccante e reattiva.
- Uso di **pochi Activity** con più fragment per una migliore gestione del ciclo di vita.
- Utilizzo di **Recyclew view** per gestire in modo più efficienti elenchi di view.
- Persistenza locale efficace con **Room** per la gestire in un database SQL e **Preferences DataStore** per la gestione di un semplice file costituito da coppie chiavi-valore.
- **Permessi**: compatibilità con la versione più recente di Android (**API 35**) in modo tale da usare funzionalità più avanzate e semplificate, come ad esempio:
  - La gestione dei permessi per l'uso della fotocamera, della galleria e del salvataggio delle immagini, senza la necessità di dichiararne formalmente l'uso, ma gestendolo al momento dell'uso. 
  - Utilizzo dei launcher di Android per gestire i permessi e notificare l'utente con un rational per definire l'utilizzo dei permessi, in particolare è stato usato per gestire le notifiche. 
- Notifiche e sincronizzazione ritardate gestite con **WorkManager**, sicure anche dopo reboot.
- Supporto a **più lingue** per interfaccia tramite l'uso di values a più lingue.

---

## Possibili Migliorie

Nonostante il progetto soddisfi pienamente i requisiti funzionali e architetturali, si possono prevedere le seguenti estensioni future:

- Integrazione di **test automatici** per rilevare problematiche prima del building.
- **Miglioramento della parte UI** per un rendering più moderno e seguire in modo più rigoroso le regole di **Material Design**.
- Implementazione di un **algoritmo adattivo** basato sul livello dell’utente.
- Utilizzare una versione autogestita da Android per la navigazione.


---

## Conclusione

Il progetto è stato realizzato con attenzione particolare alla progettazione modulare seguente le linee guida di Android, all’impiego delle ultime tecnologie Android e alle buone pratiche architetturali, ottenendo un'app educazionale efficace, efficiente, facilmente estendibile e pronta per una eventuale distribuzione reale.
