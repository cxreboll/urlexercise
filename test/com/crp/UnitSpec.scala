package com.crp

import org.scalatest._
import org.scalatest.concurrent.ScalaFutures

/**
 * Base trait for unit test specifications.
 */
class UnitSpec extends Suite
  with WordSpecLike
  with MustMatchers
  with BeforeAndAfterAll
  with OptionValues
  with ScalaFutures