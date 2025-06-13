# Mill Tailwind

A Mill plugin to easily use TailwindCSS in Scala projects without NPM & friends.

## Usage

You can import Mill Tailwind in your buildscript by using the $ivy import syntax:

```scala
import $ivy.`io.github.iltotore::mill-tailwind::<version>`
import io.github.iltotore.tailwind.TailwindModule
```

You can then use `TailwindModule` in your build file:

```scala
object main extends ScalaModule with TailwindModule {

  //...

  def tailwindModule = "4.1.8"
}
```

By default, `TailwindModule` overrides `resources` to make it depend on the `tailwindCSSOutput` task.
You can disable this behaviour for by changing the definition of `resources` (just like any other Mill task).

For an exhaustive list of tasks see [the documentation](https://iltotore.github.io/mill-tailwind/io/github/iltotore/tailwind/TailwindModule.html).

## Contribute

You can build the project using `mill compile`. If you don't have Mill installed, you can use the wrapper `./millw`
or `./millw.bat` instead (e.g `./millw compile`).

To publish the plugin locally, you can use the `publishLocal` task.