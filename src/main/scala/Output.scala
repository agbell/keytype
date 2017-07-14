package adam

import aiyou._
import aiyou.implicits._
import cats.implicits._
import org.atnos.eff.all._
import org.atnos.eff.future._
import org.atnos.eff.syntax.all._
import org.atnos.eff.syntax.future._
import org.atnos.eff.{ExecutorServices, _}
import util.Data._
import util.IOEffect._
import util.Terminals
import util.Terminals._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
import scala.io.{Source, StdIn}
import scala.util.Random

object Output {

  def makes(word: String, guesses: Set[Char]): String =
    word.flatMap(c => if (guesses.contains(c)) s"$c " else "  ")

  def readLine: IO[String] = IO.primitive(StdIn.readLine)

  def readFile(file: String): IO[List[String]] = IO.primitive(Source.fromFile(file).getLines.toList)

  def outputFile(col: Int, row: Int, file: String): IO[Unit] = {
    for {
      ls <- readFile(file)
      _ <- ls.zipWithIndex.map({ case (l, i) => writeText(col, row + i, l) }).sequence
    } yield ()
  }

  def outputImage(context: Context): IO[Unit] =
    outputFile(0, 8, s"${context.numMisses}-miss.txt")

  def outputStatus(context : Context): IO[Unit] = {
    for {
      _ <- writeText(10, 9, makes(context.word, context.guesses))
      _ <- writeText(10, 10, List.fill(context.word.size)('-').mkString(" "))
      _ <- writeText(10, 12, s"Misses: ${context.misses}")
      _ <- context.calculateResult match {
        case Continue => IO.pure(())
        case YouWin => writeText(10, 14, "You win!!\n")
        case YouLose => writeText(10, 14, s"The word is ${context.word}.  You Lose.\n")
      }
    } yield ()
  }
}
