package com.findProgDate

import com.google.gson.Gson
import io.ktor.application.*
import io.ktor.content.TextContent
import io.ktor.http.ContentType
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.get
import io.ktor.routing.routing
import java.lang.NumberFormatException
import java.text.SimpleDateFormat
import java.util.*

data class DayResp(val errorCode: Int, val message: String){

}

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    val jsonBuild = Gson()
    routing {
        get("/"){
            try {
                val year: Int? = call.request.queryParameters["year"]?.toInt()
                if (year!! < 0 ){
                    val errorCode = 400;
                    val message = "invalidYear"
                    val resp = DayResp(errorCode, message)
                    call.respond(TextContent(jsonBuild.toJson(resp),  ContentType.Application.Json))
                }
                else{
                    val date = Calendar.getInstance()
                    date.set(year,0,256)
                    val dFormat = SimpleDateFormat("dd/MM/YY")
                    val resp = DayResp(200, dFormat.format(date.time).toString())
                    call.respond(TextContent(jsonBuild.toJson(resp),  ContentType.Application.Json))
                }
            }
            catch (e: NumberFormatException){
                val errorCode = 400;
                val message = "invalidFormat"
                val resp = DayResp(errorCode, message)
                call.respond(TextContent(jsonBuild.toJson(resp),  ContentType.Application.Json))
            }

        }
    }
}

