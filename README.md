# JokesDB

A minimal application to play with JPA and spring data topics.

## Selbsteinschätzung, Features, Fokus
Ich finde, ich sollte für das Projekt die Note 5 bekommen, da ich alle Mindestanforderungen und ein paar weitere Anforderungen erfolgreich
erfüllt habe und mir dabei viel Mühe gegeben habe.
Ich habe zuerst einiges nicht ganz verstanden und hatte
Probleme mit Docker und der API aber als ich diese Probleme
gelöst habe ging es besser voran.
Ich habe im Code keine Getter und Setter und diese Anforderung somit umgesetzt.
Mein Code kann Jokes aus der API holen und diese in meine eigene Postgres Datenbank abfüllen.
Mein Fokus wurde auf die Funktionalitäten gesetzt. Ein Frontend habe ich deswegen nicht.
Meine Jokes, welche gespeichert werden, haben folgende Attribute:

	@Id
	@GeneratedValue
	@Getter
	@Setter
	int id;

	@Column(nullable = false, length = 500)
	@Getter
	@Setter
	String joke;

	private String category;
	private String type;

	private String setup;
	private String delivery;

	private Boolean safe;
	private String lang;

	@Getter
	@Setter
	Integer totalRatings;

	@Version
	private int version;

	@CreationTimestamp
	private Date createdOn;

	@UpdateTimestamp
	private Date updatedOn;

Die erfüllten Anforderungen sind hier aufgelistet:
- WebFlux-Client-Anbindung an https://jokeapi.dev (read-only, d.h. ohne `/submit`)
- ** min: hardcoded client für ein paar Usecases
- think-tank: generischer Jokes-Client, mehrere Parameter (zB `lang` werden supportet)
- Jokes werden local cached.
- min: Remote Jokes werden in der lokalen DB gespeichert und Duplikate werden verhindert.
- Die Datenbank wird durch sinnvolle Felder erweitert und Jokes können mit einem Sterne-Rating pro User versehen werden.
- ** min: Technische Datenbankfelder creation-timestamp, modified-timestamp (and friends) per Tabelle, joke-ratings
- ** think-tank: Techfelder in einer Basis-Klasse, weitere Columns wie "Category" entsprechend jokeapi.dev
- Verwendet sinnvolle https://projectlombok.org/[Lombok] Features
- ** min: keine Getter/Setters in Code
- JUnit Testing mit `@SpringBootTest` und https://assertj.github.io/doc/[AssertJ]
- ** min: `WebTestClient` Tests der eigenen Endpunkte
- ** min: README mit einer Selbsteinschätzung, Diskussion der verwendeten Features und wo der Fokus gesetzt wurde.







## 🐳 Postgres with Docker

A simple solution expects a https://www.baeldung.com/linux/docker-run-without-sudo[running docker without sudo].
To get a Database connection (and associated JPA-autocomplete), run `./gradlew bootRun` (it will hang).

Alternatively launch a postgres docker container similar to the `dockerPostgres`-Task in `build.gradle` by hand.

## 🪣 IntelliJ Database View

View | Tool Windows | Database | + | Data Source from URL
```
jdbc:postgresql://localhost:5432/localdb
User: localuser, Password: localpass
```
