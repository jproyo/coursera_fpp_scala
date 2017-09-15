package recfun

import org.scalatest.FunSuite


import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class BalanceSuite extends FunSuite {
  import Main.balance

  test("balance: '(if (zero? x) max (/ 1 x))' is balanced") {
    assert(balance("(if (zero? x) max (/ 1 x))".toList))
  }

  test("balance: 'I told him ...' is balanced") {
    assert(balance("I told him (that it's not (yet) done).\n(But he wasn't listening)".toList))
  }

  test("balance: 'something easy' is balanced") {
    assert(balance("(something easy)".toList))
  }

  test("balance: 'something more complex' is balanced") {
    assert(balance("(something (more (complex) and) other)".toList))
  }

  test("balance: 'so far more complex' is balanced") {
    assert(balance("(somet()()()()hing (more (complex) and) (ot()()h)er)".toList))
  }

  test("balance: ':-)' is unbalanced") {
    assert(!balance(":-)".toList))
  }

  test("balance: counting is not enough") {
    assert(!balance("())(".toList))
  }

  test("balance: strange cases") {
    assert(!balance("())()/((%&/%/%&/%)&(()))))))))".toList))
  }

}
