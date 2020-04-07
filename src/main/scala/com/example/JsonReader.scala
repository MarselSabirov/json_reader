package com.example
import org.apache.spark.rdd.RDD
import org.apache.spark.sql._
import org.json4s._
import org.json4s.jackson.JsonMethods._


object JsonReader extends App {
  override def main(args: Array[String]): Unit = {
      if(args.length==1){
          val spark = SparkSession.builder()
            .appName("JsonReader")
            .master("local[*]")
            .getOrCreate()
          spark.sparkContext.setLogLevel("ERROR")

          import spark.implicits._

          case class WineBody(id: Option[String],
                              country: Option[String],
                              points: Option[String],
                              price: Option[String],
                              title: Option[String],
                              variety: Option[String],
                              winery: Option[String])


          var file = spark.sparkContext.textFile(args(0))
          
          file.foreach(l=>{implicit val formats = DefaultFormats;println(parse(l).extract[WineBody])})
      }
  }
}
