package com.zope.s3blobserver

import java.io.{File, InputStream, FileOutputStream}
import scala.util.Random

trait SampleData extends org.scalatest.FlatSpec {

  def make_tempfile(
    size: Int = 65536,
    delete: Boolean = true,
    file: File = null,
    exact_size: Boolean = false):
      (File, Array[Byte]) =
  {
    val tmpfile = (
      if (file == null) File.createTempFile("test", "data")
      else file
    )

    if (delete)
      tmpfile.deleteOnExit()

    val random = new Random()
    val bytes = new Array[Byte](if (exact_size) size
                                else random.nextInt(size*2))
    random.nextBytes(bytes)

    val ostream = new FileOutputStream(tmpfile)
    ostream.write(bytes)
    ostream.close()

    (tmpfile, bytes)
  }

  def check_stream(inp: InputStream, bytes: Array[Byte]) {
    val buffer = new Array[Byte](bytes.length)
    assert(inp.read(buffer) == bytes.length, "read")
    assert(buffer.deep == bytes.deep, "data")
    assert(inp.read(buffer) == -1, "eof")
  }
}