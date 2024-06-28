package metamorphose

import contingency.*

import scala.annotation.*

case class Factoradic(number: BigInt):
  def expand: List[Int] =
    @tailrec
    def recur(current: BigInt, sequence: List[BigInt], result: List[Int]): List[Int] =
      sequence match
        case Nil =>
          result.reverse

        case head :: tail =>
          val next = (current/head).toInt
          recur(current - next*head, tail, next :: result)

    if number == 0 then Nil
    else recur(number, Factorial.sequence(Factorial.magnitude(number) - 1), Nil)

object Factoradic:
  def apply(sequence: List[Int]): Factoradic raises PermutationError =
    def recur(sequence: List[Int], bases: List[BigInt], result: BigInt, base: Int): BigInt =
      sequence match
        case Nil => result
        case head :: tail =>
          if head >= base
          then raise(PermutationError(PermutationError.Reason.BaseRange(head, base)))(())

          recur(tail, bases.tail, result + bases.head*head, base - 1)

    val length = sequence.length
    Factoradic(recur(sequence, Factorial.sequence(length), 0, length))