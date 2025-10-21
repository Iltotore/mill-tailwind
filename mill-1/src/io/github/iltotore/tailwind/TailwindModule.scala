package io.github.iltotore.tailwind

import mill.*, scalalib.*

trait TailwindModule extends ScalaModule:

  /**
    * The version of Tailwind to use.
    */
  def tailwindVersion: String

  /**
    * The CSS file passed as to tailwind (`-i ...`).
    */
  def tailwindCSSInput: Task[PathRef] = Task.Source("css/input.css")

  /**
    * Sources to watch for tailwind to recompile. Should be the same as specified in the input CSS file.
    */
  def tailwindSources: Task[Seq[PathRef]] = sources

  /**
    * Sub-directory in which the generated CSS will be output.
    */
  def tailwindSubdirectory: Task[String] = Task("public")

  override def resources: Task.Simple[Seq[PathRef]] = Task(super.resources() :+ tailwindCSSOutput())

  /**
    * The artifact name of Tailwind's executable for the current platform on Github.
    */
  def tailwindExecutableArtifact: Task[String] = Task:
    val systemOsName = System.getProperty("os.name").toLowerCase()
    val osName =
      if(systemOsName.contains("windows")) "windows"
      else if(systemOsName.contains("mac")) "macos"
      else "linux"

    val osArch = System.getProperty("os.arch") match
      case "amd64" | "x86_64" | "x64" => "x64"
      case "aarch64" | "arm64" => "arm64"
      case arch => arch

    val osExtension = osName match
      case "windows" => ".exe"
      case _ => ""

    s"tailwindcss-$osName-$osArch$osExtension"

  /**
    * Download Tailwind's executable.
    * 
    * @return the path to the downloaded executable
    */
  def tailwindExecutable: Task[PathRef] = Task:
    val exePath = Task.dest / "tailwindcss"

    os.write(
      exePath,
      requests.get.stream(s"https://github.com/tailwindlabs/tailwindcss/releases/download/v${tailwindVersion}/${tailwindExecutableArtifact()}")
    )

    os.perms.set(exePath, "rwxrwxrwx")

    PathRef(exePath)

  /**
    * Generate output CSS.
    * 
    * @return the path to the generated CSS file 
    */
  def tailwindCSSOutput: Task[PathRef] = Task:
    val outputPath = Task.dest / tailwindSubdirectory() / "output.css"
    tailwindSources()
    os.proc(tailwindExecutable().path, "-i", tailwindCSSInput().path, "-o", outputPath).call()
    PathRef(Task.dest)