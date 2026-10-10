# Java practice

Open this repository folder in IntelliJ IDEA. The local project uses JDK 23
and keeps the original examples in separate modules to avoid class-name clashes.
If IntelliJ asks for an SDK, select `C:\Program Files\Java\jdk-23`.

Select the **Java Practice** run configuration and click Run, or open
`practice/src/main/java/practice/Main.java` and run its `main` method.
Add your own packages under `practice/src/main/java/practice`, for example
`practice.singleton` or `practice.factory`.

IDE settings are local and ignored by Git. On another computer, create a Java
module from the `practice` folder and mark `src/main/java` as Sources Root.

## Save practice to GitHub

`origin` is your fork: https://github.com/taufik0747/LLD-Design-Patterns
`upstream` is the original author's repository.

In IntelliJ, select files in the Commit window, enter a message, then use
**Commit and Push**. Check that the destination is `origin/main`.

From a terminal in the repository:

```powershell
git add practice
git commit -m "Practice Java design patterns"
git push origin main
```

Stage any edited original examples explicitly if you also want to commit them.

## Windows checkout

The upstream `Abstract Factory Design Pattern/Furniture ` folder has a trailing
space. This clone uses sparse checkout to exclude it because Windows cannot
check it out normally. Its contents remain in Git history.
