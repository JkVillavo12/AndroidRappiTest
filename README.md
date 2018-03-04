# AndroidRappiTest
Proyecto que sirve como prueba para la vacante de android developer en Rappi

- Consumir el API de películas y series de la siguiente pagina: link HECHO
- Debe tener tres categorías de películas y/o series: Popular, Top Rated, Upcoming. - Cada película y/o series debe poder visualizar su detalle. HECHO
- Debe funcionar tanto online como offline (cache). HECHO
    Se hace uso del cache para retrofit
    Se hace uso de ROOM para el manejo de la db local
- Debe tener un buscador offline por categorías. HECHO
    Se crea un mini buscador, el cual ejecuta la búsqueda con películas que haya guardado en la database después de una consulta online. 
				
Valoraciones extras:							 								
- Visualización de los videos en el detalle de cada ítem. HECHO
    al ingresar al detalle de una película, se consultan los videos de esta, si la película tiene videos aparece el botón “play” que aleatoriamente muestra uno de los videos por youtube		
- Transiciones, Animaciones, UI/UX. HECHO
    Al iniciar en el splash, al ingresar al detalle de una película y en el detalle de una película, botón play y campos informativos de página web.
- Buscador Online. HECHO
    Se crea un mini buscador, el cual dada la palabra clave usa el API Rest para realizar dicha búsqueda		 			
- Pruebas Unitarias. HECHO
    se hacen unas pocas pruebas de un método y del uso de Room para la database local							
						
Una vez acabada la prueba describa en un readme brevemente:	

- Las capas de la aplicación (por ejemplo capa de persistencia, vistas, red, negocio, etc) y qué clases pertenecen a cual.
    En la raíz del proyecto coloco las Activity (vistas)
    en “adapter” creo los custom adapters para categorías y películas
    En “commons” uso clases que son transversales para toda la app 
    en “rest” está toda la capa de negocio a través del API rest de “the movie database” allí se encuentra la definición para hacer uso del Retrofit, el ApliClient, el ApiInterface y el modelo de los objetos que retornan dichos rest.
    en “localdb” se encuentra todo lo necesario para el trabajo de persistencia en el dispositivo, se usó Room, allí se encuentran el model, daos y unas clases “manager” que se encargan de todo para que la vista (Activity) solo llame un método de dichos Manager.

- La responsabilidad de cada clase creada.	
SplashActivity: actividad que sirve de splash para la app
PrincipalActivity: Actividad que sirve de inicio a la app, allí el usuario selecciona la categoría que desea ver. 
MoviesListActivity: Actividad que lista las películas según su categoría tanto online como offline dado el caso (Room), allí también puede ejecutar búsquedas.
MovieDetailActivity: Actividad que muestra el detalle de una película, cara online u offline, desde allí se puede ingresar al video o homepage de la pelicula.
AdapterCategories: adapter que permite pintar las categorías en un recyclerView
AdapterMovies: adapter que permite pintar las películas en la lista de peliculas
Commons: Clase que gestiona constantes usadas en la app
Utilidades: contiene métodos que se usan transversalmente en la app
RoomMovieDatabase: Clase que gestiona los daos usados en la app para la presistencia local
RoomMovie: clase que sirve como tabla en la dblocal
RoomMovieDetail: clase que sirve como tabla del detalle de una película en la dblocal
RoomMovieManager: Contiene métodos que son llamados por las vistas para ejecutar acciones en la database
RoomMovieDetailManager: Contiene métodos que son llamados por las vistas para ejecutar acciones en la database
RoomMovieDao: contiene las consultas a la dblocal del objeto RoomMovie que es usado en la lista de películas
RoomMovieDetailDao: contiene las consultas a la dblocal del objeto RoomMovieDetail que es usado en el detalle de la película.
ApiClient: contiene la definición del apirest para la app
ApiInterface: contiene la definición de los métodos usados del API Rest de “the movie database” 
Movie: definición del objeto usado para la lista de películas
MoviesResponse: definición del objeto que parsea la respuesta total de la lista de películas.
VideoByMovieOut: definición del objeto que contiene la información de un video de una película
ResultVideosByMovieOut: definición del objeto que parsea la respuesta total de la lista de videos de una película.
MovieDetail: definición del objeto que tiene información del detalle de una película
Genre: objeto usado en el MovieDetail, género de una película
ProductionCompany: objeto usado en el MovieDetail, compañía de una película
ProductionCountry: objeto usado en el MovieDetail, País de una película
SpokenLanguaje: objeto usado en el MovieDetail, idioma de una película
ExampleUnitTest: Para pruebas unitarias
ExampleInstrumentedTest: pruebas funcionales
SearchShowActivityTest: prueba funcional creada con espresso
						
 * Responda y escriba dentro del Readme con las siguientes preguntas:
				 								
- En qué consiste el principio de responsabilidad única? Cuál es su propósito?
    Hace parte de SOLID y busca que cada clase tengo una responsabilidad única en el proyecto, ejemplo mi clase que gestiona la dbLocal solo debe enfocarse a eso y no hacer llamados a WS, por ejem ps. 

- Qué características tiene, según su opinión, un “buen” código o código limpio?

    Un buen código para mi debe tener lo siguiente
    Definición de métodos bien nombrados (según la intención)
    Una buena definición de clases -> responsabilidad única
    Uso de clases que gestionan constantes
    empaquetado claro y fácil de manejar para cualquier persona que vaya a intervenir el código
    comentarios en tareas que exigen algún tipo de complejidad
    Definición de indentación de código y formateo de este (para facilitar el trabajo en repositorios de código)
    NO clases super extensas.
    cumplir estándares (ejem Java en Android) 
