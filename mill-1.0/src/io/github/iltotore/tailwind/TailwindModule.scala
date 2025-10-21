package io.github.iltotore.tailwind

import mill._, scalalib._

trait TailwindModule extends ScalaModule {

  /**
    * The version of Tailwind to use.
    */
  def tailwindVersion: T[String]

  /**
    * The CSS file passed as to tailwind (`-i ...`).
    */
  def tailwindCSSInput: T[PathRef] = T.source(PathRef(millModuleBasePath.value / "css" / "input.css"))

  /**
    * Sources to watch for tailwind to recompile. Should be the same as specified in the input CSS file.
    */
  def tailwindSources: T[Seq[PathRef]] = sources

  /**
    * Sub-directory in which the generated CSS will be output.
    */
  def tailwindSubdirectory: T[String] = "public"

  override def resources: T[Seq[PathRef]] = T { super.resources() :+ tailwindCSSOutput()}

  /**
    * The artifact name of Tailwind's executable for the current platform on Github.
    */
  def tailwindExecutableArtifact: T[String] = T {
    val systemOsName = System.getProperty("os.name").toLowerCase()
    val osName =
      if(systemOsName.contains("windows")) "windows"
      else if(systemOsName.contains("mac")) "macos"
      else "linux"

    val osArch = System.getProperty("os.arch") match {
      case "amd64" | "x86_64" | "x64" => "x64"
      case "aarch64" | "arm64" => "arm64"
      case arch => arch
    }

    val osExtension = osName match {
      case "windows" => ".exe"
      case _ => ""
    }

    s"tailwindcss-$osName-$osArch$osExtension"
  }

  /**
    * Download Tailwind's executable.
    * 
    * @return the path to the downloaded executable
    */
  def tailwindExecutable: T[PathRef] = T {
    val exePath = T.dest / "tailwindcss"

    os.write(
      exePath,
      requests.get.stream(s"https://github.com/tailwindlabs/tailwindcss/releases/download/v${tailwindVersion()}/${tailwindExecutableArtifact()}")
    )

    os.perms.set(exePath, "rwxrwxrwx")

    PathRef(exePath)
  }

  /**
    * Generate output CSS.
    * 
    * @return the path to the generated CSS file 
    */
  def tailwindCSSOutput: T[PathRef] = T {
    val outputPath = T.dest / tailwindSubdirectory() / "output.css"
    tailwindSources()
    os.proc(tailwindExecutable().path, "-i", tailwindCSSInput().path, "-o", outputPath).call(millModuleBasePath.value)
    PathRef(T.dest)
  }
}