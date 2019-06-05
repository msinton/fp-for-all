package foldmap

// need cats imported to run this
import cats._
import cats.implicits._

object Present {
  def show(label: String)(a: Any) = {
    println()
    println(label)
    println()
    println(a)
    println()
  }
}

import Present._

object SemigroupExamples extends App {

  def optionCombine[A: Semigroup](a: A, opt: Option[A]): A =
    opt.map(a |+| _).getOrElse(a) // |+| is semigroups combine

  def mergeMap[K, V: Semigroup](lhs: Map[K, V], rhs: Map[K, V]): Map[K, V] =
    lhs.foldLeft(rhs) {
      case (acc, (k, v)) => acc.updated(k, optionCombine(v, acc.get(k)))
    }

  // merge values that share keys using the default monoid for Ints
  val xm1 = Map('a' -> 1, 'b' -> 2)
  val xm2 = Map('a' -> 3, 'b' -> 4)

  show("-- merge map ints")(
    mergeMap(xm1, xm2)
  )

  val ym1 = Map(1 -> List("hello"))
  val ym2 = Map(1 -> List("world"), 2 -> List("lunch and learn"))

  show("-- merge map list strings")(
    mergeMap(ym1, ym2)
  )

  // merge ints with multiply
  val intMultiplicationSemigroup: Semigroup[Int] = new Semigroup[Int] {
    def combine(x: Int, y: Int): Int = x * y
  }

  show("-- merge map ints with multiply")(
    mergeMap[Char, Int](xm1, xm2)(intMultiplicationSemigroup)
  )

  // cant do combineAll
  def combineAll[A: Semigroup](as: List[A]): A = ???
//   as.foldLeft()(_ |+| _)

}

object MonoidExamples extends App {

  val xm1 = Map('a' -> 1, 'b' -> 2)
  val xm2 = Map('a' -> 3, 'b' -> 4)

  show("combine all, merge ints with +")(
    Monoid.combineAll(List(xm1, xm2))
  )
  show("combine all - empty, merge ints with +")(
    Monoid.combineAll(List.empty[Map[Char, Int]])
  )

  def optionCombine[A: Semigroup](a: A, opt: Option[A]): A =
    opt.map(a |+| _).getOrElse(a) // |+| is semigroups combine

  val intMultiplicationMonoid: Monoid[Int] = new Monoid[Int] {
    def empty: Int = 1
    def combine(x: Int, y: Int): Int = x * y
  }
  val mapIntMultMonoid: Monoid[Map[Char, Int]] = new Monoid[Map[Char, Int]] {
    def empty: Map[Char, Int] = Map()
    def combine(x: Map[Char, Int], y: Map[Char, Int]): Map[Char, Int] =
      x.foldLeft(y) {
        case (acc, (k, v)) =>
          acc.updated(k, optionCombine(v, acc.get(k))(intMultiplicationMonoid))
      }
  }

  show("combine all, merge ints with *")(
    Monoid.combineAll(List(xm1, xm2))(mapIntMultMonoid)
  )

  val intMultiplicationSemigroup: Semigroup[Int] = new Semigroup[Int] {
    def combine(x: Int, y: Int): Int = x * y
  }
  val reusedMapIntMultMon: Monoid[Map[Char, Int]] =
    new cats.kernel.instances.MapMonoid()(intMultiplicationSemigroup)

  show("combine all, merge ints with * - simpler")(
    Monoid.combineAll(List(xm1, xm2))(reusedMapIntMultMon)
  )

  case class Student(name: String, classId: Int)

  val juanfi = Student("Juanfi", 1)
  val jack = Student("Jack", 2)
  val mo = Student("Mo", 2)
  val john = Student("Jonathan", 3)

  val students = List(juanfi, jack, mo, john)

  show("merge students!?")(
    Foldable[List].foldMap(students)(_.name)
  )

  show("merge student names")(
    Foldable[List].foldMap(students)(_.name + " ")
  )

  show("students by class")(
    students.foldMap(x => Map(x.classId -> Set(x)))
  )

  show("students in class 2")(
    students.foldMap {
      case Student(name, 2) => Some(Set(name))
      case _                => None
    }
  )

  show("students in class 2 - debloated") {
    val folded = students.foldMap {
      case Student(name, 2) => Some(Set(name))
      case _                => None
    }
    Monoid.combineAll(folded)
  }

  show("students in class 2, name beginning with J") {
    students.foldMap {
      case Student(name, 2) if name.startsWith("J") => Some(Set(name))
      case _                                        => None
    }.combineAll
  }

  // -- This stringy version commented out in favour of more general version below --

  // case class Min(v: String)
  // def minMonoidString: Monoid[Min] =
  //   new Monoid[Min] {
  //     def empty = Min("ZZZ")
  //     def combine(a: Min, b: Min) = if (b.v < a.v) b else a
  //   }

  import Ordering.Implicits._

  case class Min[A](v: A)
  def minMonoid[A: Ordering](minValue: A): Monoid[Min[A]] =
    new Monoid[Min[A]] {
      def empty = Min(minValue)
      def combine(a: Min[A], b: Min[A]) = if (b.v < a.v) b else a
    }

  show("first student alphabetically") {
    students.foldMap(x => Min(x.name))(minMonoid("ZZZ"))
  }
  // Note we could improve this by using Semigroup instead - there is no true minValue, we used "ZZZ" 
  // then we can use Semigroup.combineAllOption - all Semigroups can be lifted to Monoids by using options!

  // functions are Monoids!

  case class Point(x: Int, y: Int)

  val up = (p: Point) => p.copy(y = p.y + 1)
  val down = (p: Point) => p.copy(y = p.y - 1)
  val right = (p: Point) => p.copy(x = p.x + 1)
  val left = (p: Point) => p.copy(x = p.x - 1)
  val stay = (p: Point) => p

  implicit val moveMonoid: Monoid[Point => Point] =
    new Monoid[Point => Point] {
      def empty = stay

      def combine(m1: Point => Point, m2: Point => Point) =
        m1 andThen m2
    }

  show("moving") {
    val moves = List(up, up, up, right, right, down, left, stay).combineAll
    moves(Point(0, 0))
  }


}
