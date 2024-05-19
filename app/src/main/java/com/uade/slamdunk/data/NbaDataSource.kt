import android.util.Log
import com.uade.slamdunk.data.NbaAPI
import com.uade.slamdunk.model.Player
import com.uade.slamdunk.model.Team
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.FileInputStream
import java.util.Properties

class NbaDataSource {

    companion object {

        //Constantes
        private const val BASE_URL = "https://v2.nba.api-sports.io/"
        private const val TAG = "NBA_API"
        //private val API_KEY = loadPropertiesFile().getProperty("API_KEY")  TODO: averiguar xq no funca
        private const val API_KEY = ""

        //Interceptor
        private val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val originalRequest = chain.request()
                val modifiedRequest = originalRequest.newBuilder()
                    .header("x-rapidapi-key", API_KEY)
                    .build()
                chain.proceed(modifiedRequest)
            }
            .build()

        //Objeto Retrofit
        private val api : NbaAPI = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build().create(NbaAPI::class.java)

        //Recuperar Equipos
        suspend fun getTeams(): ArrayList<Team> {
            Log.d(TAG, "Nba DataSource GET")

            return try {
                val response = api.getTeams()
                Log.d(TAG, "Nba DataSource SUCCESS")
                Log.d(TAG, response.toString())

                //Filtrar equipos franquicia de nba
                ArrayList(response.response
                    .filter { it.nbaFranchise && it.logo?.isNotEmpty() == true })

            } catch (e: Exception) {
                Log.d(TAG, "Nba DataSource ERROR: ${e.message}")
                ArrayList<Team>()
            }
        }

        //Recuperar Jugadores
        suspend fun getPlayers(teamId: Int): ArrayList<Player> {
            Log.d(TAG, "Nba DataSource GET")

            val result = api.getPlayers(teamId).execute()

            return if (result.isSuccessful) {
                Log.d(TAG, "Nba DataSource SUCCESS")
                result.body() ?: ArrayList<Player>()
            } else {
                Log.d(TAG, "Nba DataSource ERROR: ${result.message()}")
                ArrayList<Player>()
            }
        }

        //Cargar archivo local.properties
        private fun loadPropertiesFile(): Properties {
            val properties = Properties()
            val fileInputStream = FileInputStream("local.properties")
            properties.load(fileInputStream)
            return properties
        }

    }
}