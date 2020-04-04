package com.example
import org.apache.spark.sql.SparkSession
import org.json4s._
import org.json4s.jackson.JsonMethods._
import scala.io.Source

object JsonReader extends App {
  override def main(args: Array[String]): Unit = {
      if(args.length==1){
          val spark = SparkSession.builder()
            .appName("JsonReader")
            .master("local[*]")
            .getOrCreate()
          spark.sparkContext.setLogLevel("ERROR")

          case class WineBody(id: Option[String],
                              country: Option[String],
                              points: Option[String],
                              price: Option[String],
                              title: Option[String],
                              variety: Option[String],
                              winery: Option[String])

          implicit val formats = DefaultFormats 

          for(line <- Source.fromFile(args(0)).getLines){
              println(parse(line).extract[WineBody])
          }
      }
  }
}
