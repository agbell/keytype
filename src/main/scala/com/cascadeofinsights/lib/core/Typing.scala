package com.cascadeofinsights.lib.core

import aiyou.IO

import scala.concurrent.ExecutionContext.Implicits.global
import scala.io.Source
import scala.util.Random

trait Typing[Text,Result] {
 def nextText() : IO[Text]
 def storeResult(result : Result) : IO[Unit]
 def getResults() : IO[Seq[Result]]
}


object TypingImp extends Typing[Text,Result] {
  lazy val lines = {
    val file = "paragrams.txt"
    IO.primitive(Source.fromFile(file).getLines.toList)
  }

  override def nextText(): IO[Text] ={
    lines.map{ls =>
      Text.create(
        Random.shuffle(ls)
        .head
      )
    }
  }

  var results: List[Result] = List.empty

  override def storeResult(result: Result): IO[Unit] = IO.pure{
    results = result :: results
  }

  override def getResults(): IO[Seq[Result]] = IO.pure(results)
}