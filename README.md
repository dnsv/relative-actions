<div align="center">
    <img alt="Logo" src="./src/main/resources/META-INF/pluginIcon.svg" width="80">
    <h1>Relative Actions</h1>

![Build](https://github.com/dnsv/relative-actions/actions/workflows/build.yml/badge.svg?branch=main)
</div>

**Relative Actions** is a plugin for IntelliJ-based IDEs to perform actions (move, copy, cut, delete, select
and comment) relative to your current line. It behaves similarly to Vim but without needing to deal with Vim's modes.

## Features

- Move the caret up/down by *N* lines
- Move the caret to the beginning/end of a line
- Copy, cut, delete, select or comment:
    - *N* lines above/below the current line
    - A range of lines relative to the current line (e.g., comment lines 5 through 10 above the current line)
    - A range of lines in both directions relative to the current line (e.g., comment 5 lines above and 10 lines below
      the current line)

## Installation

Relative Actions can be installed directly from the IDE via
`Settings > Plugins` ([see instructions](https://www.jetbrains.com/help/idea/managing-plugins.html)).

## Usage

> [!TIP]
> To get the best experience, enable relative or hybrid line numbers in the gutter:
> 1. Go to `Settings > Editor > General > Appearance`
> 2. Set *Show line numbers* to `Relative` or `Hybrid`

Press the keyboard shortcut to activate Relative Actions (<kbd>Opt</kbd> + <kbd>K</kbd> on macOS or <kbd>
Alt</kbd> + <kbd>
K</kbd> on Windows/Linux). The caret will change color to indicate that Relative Actions is active.

You can now perform a command relative to your current line. Commands follow a simple pattern: you specify *what* to do,
*how many* lines, and in *which direction*. For example:

- **Move only**: Just specify a line number and direction (e.g., `5k` to move 5 lines up).
- **Move to the beginning/end of a line**: Also specify a position (e.g., `3ek` to move 3 lines up and to the end of the
  line).
- **Perform an action**: Specify a line number, action and direction (e.g., `3ck` to comment 3 lines up).
- **Work with ranges**: Use a comma to specify a range of lines (e.g., `2,5dl` to delete lines 2 through 5 below
  the current line).

More examples:
- `6l`: Move 6 lines down
- `3dl`: Delete 3 lines down
- `10,5sk` Select lines 5 through 10 above the current line
- `5,cl`: Comment only the 5th line below the current line
- `3,5xb`: Cut 3 lines above and 5 below the current line

> [!NOTE]
> You can customize the activation shortcut under `Settings > Keymap` by searching for `Relative Actions`.
>
> The last character in the action must always be the direction (`k`, `l`, or `b`), while all other characters can be
> used in any order. For example, `3ck` is equivalent to `c3k`.
>
> The order of the numbers in a range doesn't matter. For example, `5,2ck` is equivalent to `2,5ck`.
>
> To perform an action only on one line that's not the current line, e.g., to comment the third line above the current
> line, you can use `3,ck`, which is a shortcut for `3,3ck`.

### Key Bindings

The following table shows all available keys you can use to build commands:

| Key | Operation                                                 |
|-----|-----------------------------------------------------------|
| `k` | Direction: Up                                             |
| `l` | Direction: Down                                           |
| `b` | Direction: Bidirectional (only applicable with an action) |
| `w` | Position: Beginning of line (only applicable with *move*) |
| `e` | Position: End of line (only applicable with *move*)       |
| `y` | Action: Copy                                              |
| `x` | Action: Cut                                               |
| `d` | Action: Delete                                            |
| `s` | Action: Select                                            |
| `c` | Action: Comment                                           |

> [!NOTE]
> All key bindings can be customized under `Settings > Tools > Relative Actions`.

Like Vim, Relative Actions has a steep—though arguably gentler—learning curve, so don't be discouraged if you don't get
it right away. The more you use it, the more natural it will feel.
