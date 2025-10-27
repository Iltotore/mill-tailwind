# Mill Tailwind

A Mill plugin to easily use TailwindCSS in Scala projects without NPM & friends.

Examples:

- [SSR with Cask](https://github.com/Iltotore/mill-tailwind/tree/main/examples/cask)
- [SPA with Tyrian](https://github.com/Iltotore/mill-tailwind/tree/main/examples/tyrian) ([Live example](https://iltotore.github.io/mill-tailwind/examples/tyrian/index.html))
- [SPA with Tyrian+DaisyUI](https://github.com/Iltotore/mill-tailwind/tree/main/examples/tyrian-daisy) ([Live example](https://iltotore.github.io/mill-tailwind/examples/tyrian-daisy/index.html))

[Documentation](https://iltotore.github.io/mill-tailwind/io/github/iltotore/tailwind/TailwindModule.html)

## Usage

You can import Mill Tailwind in your buildscript by using the $ivy import syntax:

<details>

<summary>Mill 0.x</summary>

```scala
import $ivy.`io.github.iltotore::mill-tailwind::<version>`
import io.github.iltotore.tailwind.TailwindModule
```

</details>

<details>

<summary>Mill 1.x</summary>

```scala
//| mvnDeps: ["io.github.iltotore::mill-tailwind::<version>"]

import io.github.iltotore.tailwind.TailwindModule
```

</details>

You can then use `TailwindModule` in your build file:

<details>

<summary>Mill 0.x</summary>

```scala
object main extends ScalaModule with TailwindModule {

  //...

  def tailwindModule = "4.1.16"
}
```

</details>

<details>

<summary>Mill 1.x</summary>

```scala
object main extends ScalaModule, TailwindModule:

  //...

  def tailwindModule = "4.1.16"
```

</details>

By default, `TailwindModule` overrides `resources` to make it depend on the `tailwindCSSOutput` task.
You can disable this behaviour for by changing the definition of `resources` (just like any other Mill task).

For an exhaustive list of tasks see [the documentation](https://iltotore.github.io/mill-tailwind/io/github/iltotore/tailwind/TailwindModule.html).

## Contribute

You can build the project using `mill compile`. If you don't have Mill installed, you can use the wrapper `./millw`
or `./millw.bat` instead (e.g `./millw compile`).

To publish the plugin locally, you can use the `publishLocal` task.