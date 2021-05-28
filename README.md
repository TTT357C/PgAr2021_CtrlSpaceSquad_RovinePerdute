# PgAr2021_CtrlSpaceSquad_RovinePerdute
<p>
  <img alt="Image" title="icon" src="Logo CTRL_SPACE_SQUAD.jpeg" width="200" height="200" align="left"/>
  <b> Note riguaranti il progetto: </b> <br><br>
  <p> - I file di output (Routes.xml) sono da rigenerare, perche' non e' detto che siano l'ultima versione</p>
  <p> - Durante l'esecuzione e' possibile scegliere il file da cui importare le citta'.</p>
  <p> - Sono stati utilizzati 2 metodi per il pathfinding ed e' possibile decidere quale usare, A* che è il più veloce e trova il percorso migliore, lo yen (deriva da Dijkstra) permette di ricercare le k strade migliori e su file scrive la prima. <br><br>  - Caso due percorsi o sotto-percorsi richiedano lo stesso quantitativo di carburante, viene scelto quello che attraversa meno città. sia per l'A* che lo yen. A* non controlla quello con l'id maggiore, ma Yen controlla quello la cui somma degli id e' maggiore, a pari costo e numero di nodi, cioe' quello in cui le citta' hanno id piu' alto. 
  <br> (Controlla quelli compresi nella variabile k cioe' il numero di route richieste + route, + preciso, - veloce _ P.S. A* e' molto utile se i team volgiono andare il prima possibile alle rovine :P ) </p>
  <p> Riguardo al valore k del Yen, se troppo alto l'esecuzione potrebbe durare qualche minuto nel caso di 2000 e 10000 (Es:10000 - k=10 tempo: ~3 min) di solito 20-30 e' gia abbastanza</p>
  <p> - oltre al file xml e' presente una versione formattata. </p>
  <p> - L'algoritmo Yen e' stato preso come libreria e implementato nel nostro codice (si trova in altro/ksp) </p>
  <p> - Il file PgAr_Map_12.xml l'abbiamo rinominato PgAr_Map_13.xml perche' abbiamo notato che va da 0 a 12 (A differenza degli altri che vanno da 0 a n-1)</p>
  
  
