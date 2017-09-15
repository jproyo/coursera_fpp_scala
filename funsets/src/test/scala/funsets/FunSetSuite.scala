package funsets

import org.scalatest.FunSuite
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner


/**
 * This class is a test suite for the methods in object FunSets. To run
 * the test suite, you can either:
 *  - run the "test" command in the SBT console
 *  - right-click the file in eclipse and chose "Run As" - "JUnit Test"
 */
@RunWith(classOf[JUnitRunner])
class FunSetSuite extends FunSuite {

  /**
   * Link to the scaladoc - very clear and detailed tutorial of FunSuite
   *
   * http://doc.scalatest.org/1.9.1/index.html#org.scalatest.FunSuite
   *
   * Operators
   *  - test
   *  - ignore
   *  - pending
   */

  /**
   * Tests are written using the "test" operator and the "assert" method.
   */
  // test("string take") {
  //   val message = "hello, world"
  //   assert(message.take(5) == "hello")
  // }

  /**
   * For ScalaTest tests, there exists a special equality operator "===" that
   * can be used inside "assert". If the assertion fails, the two values will
   * be printed in the error message. Otherwise, when using "==", the test
   * error message will only say "assertion failed", without showing the values.
   *
   * Try it out! Change the values so that the assertion fails, and look at the
   * error message.
   */
  // test("adding ints") {
  //   assert(1 + 2 === 3)
  // }


  import FunSets._

  test("contains is implemented") {
    assert(contains(x => true, 100))
  }

  /**
   * When writing tests, one would often like to re-use certain values for multiple
   * tests. For instance, we would like to create an Int-set and have multiple test
   * about it.
   *
   * Instead of copy-pasting the code for creating the set into every test, we can
   * store it in the test class using a val:
   *
   *   val s1 = singletonSet(1)
   *
   * However, what happens if the method "singletonSet" has a bug and crashes? Then
   * the test methods are not even executed, because creating an instance of the
   * test class fails!
   *
   * Therefore, we put the shared values into a separate trait (traits are like
   * abstract classes), and create an instance inside each test method.
   *
   */

  trait TestSets {
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)
    val s4 = singletonSet(4)
    val s5 = singletonSet(5)
    val s6 = singletonSet(6)
    val s7 = singletonSet(7)
    val s8 = singletonSet(8)
    val s9 = singletonSet(9)
    val s10 = singletonSet(10)

    val s1_ = singletonSet(1)
    val s2_ = singletonSet(2)
    val s3_ = singletonSet(3)
    val s4_ = singletonSet(4)
    val s5_ = singletonSet(5)
    val s6_ = singletonSet(6)
    val s7_ = singletonSet(7)
    val s8_ = singletonSet(8)
    val s9_ = singletonSet(9)
    val s10_ = singletonSet(10)

    val unionAll = union(union(union(union(union(union(union(union(union(s1, s2),s3),s4),s5),s6),s7),s8),s9),s10)
  }

  /**
   * This test is currently disabled (by using "ignore") because the method
   * "singletonSet" is not yet implemented and the test would fail.
   *
   * Once you finish your implementation of "singletonSet", exchange the
   * function "ignore" by "test".
   */
  test("singletonSet contains elements in TestSets") {

    /**
     * We create a new instance of the "TestSets" trait, this gives us access
     * to the values "s1" to "s3".
     */
    new TestSets {
      /**
       * The string argument of "assert" is a message that is printed in case
       * the test fails. This helps identifying which assertion failed.
       */
      assert(contains(s1, 1), "Singleton 1")
      assert(contains(s2, 2), "Singleton 2")
      assert(contains(s3, 3), "Singleton 3")
      assert(contains(s4, 4), "Singleton 4")
      assert(contains(s5, 5), "Singleton 5")
      assert(contains(s6, 6), "Singleton 6")
      assert(contains(s7, 7), "Singleton 7")
      assert(contains(s8, 8), "Singleton 8")
      assert(contains(s9, 9), "Singleton 9")
      assert(contains(s10, 10), "Singleton 10")
    }
  }

  test("different singletonSet test correct"){
    1 until 500 foreach { i: Int =>
      assert(contains(singletonSet(i), i))
    }
  }

  test("different singletonSet test failed"){
    1 until 500 foreach { i: Int =>
      assert(!contains(singletonSet(i), i-1))
    }
  }

  test("union contains all elements of each set") {
    new TestSets {
      val s = union(s1, s2)
      assert(contains(s, 1), "Union 1")
      assert(contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Not Union 3")

      1 until 10 foreach { i => assert(contains(unionAll, i), "Union All "+i) }

    }
  }

  test("union not contains all elements of each set") {
    new TestSets {
      11 until 500 foreach { i => assert(!contains(unionAll, i), "Not Union All "+i) }
    }
  }

  test("intersect contains all elements on each set") {
    new TestSets {
      assert(contains(intersect(s1, s1_), 1), "Intersect 1")
      assert(contains(intersect(s2, s2_), 2), "Intersect 2")
      assert(!contains(intersect(s3, s4_), 3), "Not Intersect 3")
      assert(contains(intersect(unionAll, s1), 1), "Intersect 1")
    }
  }

  test("diff contains all elements on one set and not in the other") {
    new TestSets {
      assert(contains(diff(s1, s2), 1), "Diff 1")
      assert(contains(diff(s2, s3), 2), "Diff 2")
      assert(!contains(diff(s3, s3_), 3), "Not Diff 3")

      assert(!contains(diff(unionAll, s1), 1), "Not Diff 1")
      2 until 10 foreach { i => assert(contains(diff(unionAll, s1), i), "Diff "+i) }
    }
  }

  test("filter elements set based on predicated") {
    new TestSets {
      assert(contains(filter(s1, x => x > 0), 1), "Filter greater than 0 in [1] = [1]")
      assert(!contains(filter(s1, x => x == 2), 1), "Filter equal 2 in [1] == Empty")

      val even = filter(unionAll, x => x%2 == 0)
      assert(contains(even, 2), "Filter even in [1,2,3,4,5,6,7,8,9,10] == [2,4,6,8,10]")
      assert(contains(even, 4), "Filter even in [1,2,3,4,5,6,7,8,9,10] == [2,4,6,8,10]")
      assert(contains(even, 6), "Filter even in [1,2,3,4,5,6,7,8,9,10] == [2,4,6,8,10]")
      assert(contains(even, 8), "Filter even in [1,2,3,4,5,6,7,8,9,10] == [2,4,6,8,10]")
      assert(contains(even, 10), "Filter even in [1,2,3,4,5,6,7,8,9,10] == [2,4,6,8,10]")

    }
  }

  test("forall set based on predicated") {
    new TestSets {
      assert(forall(s1, x => x > 0), "forall set greater than 0 in [1] == True")
      assert(!forall(s1, x => x == 2), "forall set equal 2 in [1] == False")
      assert(forall(unionAll, x => x > 0), "forall set greater than 0 in [1,2,3,4,5,6,7,8,9,10] == True")
      assert(!forall(unionAll, x => x%21==0), "forall set divisible by 21 in [1,2,3,4,5,6,7,8,9,10] == False")
      assert(!forall(unionAll, x => x == 11), "forall set equal 11 in [1,2,3,4,5,6,7,8,9,10] == False")

      val even = filter(unionAll, x => x%2 == 0)
      assert(forall(even, x => x%2 == 0), "forall set evens in [2,4,6,8,10] == True")
      val odd = filter(unionAll, x => x%2 != 0)
      assert(forall(odd, x => x%2 != 0), "forall set odds in [1,3,5,7,9] == True")

    }
  }


  test("exists set based on predicated") {
    new TestSets {
      assert(exists(s1, x => x == 1), "exists set equal 1 in [1] == True")
      assert(!exists(s1, x => x == 2), "exists set equal 2 in [1] == False")
      assert(exists(unionAll, x => x%7 == 0), "exists set divisible by 7 in [1,2,3,4,5,6,7,8,9,10] == True")
      assert(!exists(unionAll, x => x%21 == 0), "exists set divisible by 21 in [1,2,3,4,5,6,7,8,9,10] == False")
      assert(!exists(unionAll, x => x == 11), "exists set equal 11 in [1,2,3,4,5,6,7,8,9,10] == False")

      val even = filter(unionAll, x => x%2 == 0)
      assert(exists(even, x => x%2 == 0), "exists set evens in [2,4,6,8,10] == True")
      val odd = filter(unionAll, x => x%2 != 0)
      assert(exists(odd, x => x%2 != 0), "exists set odds in [1,3,5,7,9] == True")

    }
  }

  test("map set based on predicated") {
    import scala.math._
    new TestSets {
      assert(contains(map(s1, x => x + 1), 2), "map +1 on [1] == [2]")
      val power = map(unionAll, x => pow(x,2).intValue)
      1 until 10 foreach { i => assert(contains(power, pow(i,2).intValue), "map exp 2 on [1,2,3,4,5,6,7,8,9,10] == [1,4,9,16,25,36,49,64,81,100]")}
      assert(!contains(map(s1, x => x + 1), 1), "map +1 on [1] == [2]")
    }
  }

}
