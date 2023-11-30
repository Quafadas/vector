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

import slash.Random.defaultRandom as r
import slash.matrix.*

class MatrixSpaceTest extends munit.FunSuite {
  test(" testing kitchen sink ") {
    var runtimeRowDimension:Int = r.nextInt(42)
    runtimeRowDimension += 2
    var runtimeColumnDimension: Int = r.nextInt(42)
    runtimeColumnDimension += 1

    val ms = MatrixSpace(runtimeRowDimension, runtimeColumnDimension)

    assertEquals(ms.rowDimension, runtimeRowDimension)
    assertEquals(ms.columnDimension, runtimeColumnDimension)

    val m: Matrix[ms.M, ms.N] = ms.fill(0.01) + (ms.ones + ms.diagonal(42.0) - ms.diagonal(42.0)) * 7.0

    // this exercises VectorSpace.{ones, zeros, tabulate, and fill}
    var i:Int = 0; while (i < m.values.length) {
      assertEquals(m.values(i), 0.01 + 7.0)
      i += 1
    }
  }

}
