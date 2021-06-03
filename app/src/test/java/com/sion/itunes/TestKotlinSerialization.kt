package com.sion.itunes

import com.charleskorn.kaml.Yaml
import com.sion.itunes.model.vo.Music
import com.sion.itunes.model.vo.SearchResponse
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.*
import org.junit.Test

class TestKotlinSerialization {
    @Serializable
    data class Credentials(
        val publicKey: String,
        val privateKey: String,
    )

//    @Serializable
//    data class SearchResponse(
//            val resultCount: Int = 0,
//            val privateKey: List<Music> = arrayListOf<Music>()
//    )
//
//
//    @Serializable
//    data class Music(
//            val trackId: Long = -1,
//            val artistName: String = "",
//            val trackName: String = "",
//            val artworkUrl100: String = "",
//            val previewUrl: String = "",
//            var keyword: String = "",
//            var insertIndex: Long = 0
//    )

    @org.junit.jupiter.api.Test
    fun objectsToJsonAndBack() {
        val credentials = Credentials("publicKey", "privateKey")
        val stringValue = Json.encodeToString(credentials)
        println(stringValue)

        val credentialsDecoded = Json.decodeFromString<Credentials>(stringValue)
        println(credentialsDecoded)
    }

//    @Test
//    fun SearchResponseToJsonAndBack() {
//        val musics =arrayListOf<Music>()
//        var music = Music(0,"ewrewqrqw","ewrewqrwe","dfsadfasdf","kggfkfufuf","oouoyuyue",1)
//        musics.add(music)
//        music = Music(1,"oiuouiw","ljkhkhwe","qwedqwef","werttretrtfuf","nmnb",2)
//        musics.add(music)
//        val credentials = SearchResponse(30, musics )
//        val credentials2 = SearchResponse(10, allRemoteMusics )
//        val stringValue = Json.encodeToString(credentials)
//        println(stringValue)
//
//        val credentialsDecoded = Json.decodeFromString<SearchResponse>(stringValue)
//        println(credentialsDecoded)
//    }

    @Test
    fun createJsonManually() {
        val credentials = JsonObject(
            mapOf(
                "publicKey" to JsonPrimitive("publicKey"),
                "privateKey" to JsonPrimitive("privateKey")
            )
        )

        val array = JsonArray(listOf(credentials))
        println(array.toString())
    }

    @Test
    fun createJsonManuallyDsl() {
        val credentials = buildJsonArray {
            addJsonObject {
                put("publicKey", "publickey")
                put("privateKey", "privateKey")
            }
        }

        println(credentials.toString())
    }

    @Test
    fun prettyPrintJson() {
        val format = Json { prettyPrint = true }
        val input = """
            {"publicKey":"publicKey","privateKey":"privateKey"}
        """.trimIndent()

        val jsonElement = format.decodeFromString<JsonElement>(input)
        val bodyInPrettyPrint = format.encodeToString(jsonElement)

        println(bodyInPrettyPrint)
    }

    @Test
    fun customYamlFormatter() {
        val yamlEncoded =
            """
                publicKey: "publicKey"
                privateKey: "privateKey"
            """.trimIndent()

        val credentials =
            Yaml.default.decodeFromString(
                SearchResponse.serializer(),
                yamlEncoded
            )

        println(credentials)
    }
}