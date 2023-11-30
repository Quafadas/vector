/*
 * Copyright 2023 dragonfly.ai
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package slash.matrix

import narr.NArray
import slash.Random
import slash.vector.Vec

import scala.compiletime.ops.int.*

object MatrixSpace {
  def apply(rowDimension:Int, columnDimension:Int):MatrixSpace[rowDimension.type, columnDimension.type] = new MatrixSpace[rowDimension.type, columnDimension.type]
}

class MatrixSpace[M0 <: Int, N0 <: Int](using m0: ValueOf[M0], n0: ValueOf[N0]) {

  val rowDimension:Int = m0.value
  val columnDimension:Int = n0.value

  opaque type M <: Int = M0
  opaque type N <: Int = N0
  opaque type MN <: Int = M * N

  inline def apply(values: NArray[Double]): Matrix[M, N] = Matrix[M, N](values)

  inline def copyFrom(values: NArray[Double]): Matrix[M, N] = Matrix[M, N](narr.copy[Double](values))

  inline def fromVec(v: Vec[MN]): Matrix[M, N] = apply(v.asInstanceOf[NArray[Double]])

  inline def copyFromVec(v: Vec[MN]): Matrix[M, N] = fromVec(v.copy)


  inline def identity: Matrix[M, N] = diagonal(1.0)

  inline def diagonal(value:Double): Matrix[M, N] = Matrix.diagonal[M, N](value)

  inline def fill(d: Double): Matrix[M, N] = Matrix.fill[M, N](d)

  inline def zeros: Matrix[M, N] = Matrix.zeros[M, N]

  inline def ones: Matrix[M, N] = Matrix.ones[M, N]

  import slash.Random.nextMatrix

  inline def random: Matrix[M, N] = Matrix.random[M, N](Random.defaultRandom)
  inline def random(r: scala.util.Random): Matrix[M, N] = Matrix.random[M, N](r)

  inline def random(
    interval: slash.interval.Interval[Double],
    r: scala.util.Random = Random.defaultRandom
  ): Matrix[M, N] = Matrix.random[M, N](interval, r)

  inline def random(
    minNorm: Double,
    normMAX: Double
  ): Matrix[M, N] = random(minNorm, normMAX, Random.defaultRandom)

  inline def random(
     minNorm: Double,
     normMAX: Double,
     r: scala.util.Random
   ): Matrix[M, N] = r.nextMatrix[M, N](minNorm, normMAX)

}
