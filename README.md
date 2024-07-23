Please note that this pathfinding code is BY NO MEANS fully optimized. Changed xan be done to increase the preformance of the finder gradually at large ranges. At the moment the pathfinder does not feature them since I really dont see the point in making them. Create pull requests by all means.

---

## <a>How to use</a>

**Forge**

1. Add "maven("https://jitpack.io")" to repositories in "build.gradle"

Example:

```
repositories {
    mavenCentral()
    maven("https://jitpack.io")
}
```

2. Once added, add a function to your project

```
fun DependencyHandlerScope.include(dependency: Any) {
    api(dependency)
    shadowImpl(dependency)
}
```

**IMPORTANT! THE SHADOW JAR IS REQUIRED FOR THIS!**

2.1. Make sure you have the shadow jar by having this in your build.gradle:

- NOTE: IM NOT SURE THIS CODE IS COMPATIBLE WITH YOUR CONFIGURATION! IF YOU KNOW HOW TO DO THIS BETTER, PLEASE DM ME AS I'M NOT THAT GOOD WITH GRADLE STUFF.

```
val shadowImpl: Configuration by configurations.creating {
    configurations.implementation.get().extendsFrom(this)
}

tasks.shadowJar {
    destinationDirectory.set(layout.buildDirectory.dir("intermediates"))
    archiveClassifier.set("non-obfuscated-with-deps")
    configurations = listOf(shadowImpl)
    doLast {
        configurations.forEach {
            println("Copying dependencies into mod: ${it.files}")
        }
    }

    fun relocate(name: String) = relocate(name, "$baseGroup.deps.$name")
}

val remapJar by tasks.named<net.fabricmc.loom.task.RemapJarTask>("remapJar") {
    archiveClassifier.set("")
    from(tasks.shadowJar)
    input.set(tasks.shadowJar.get().archiveFile)
}
```

3. make a new function:

```
fun DependencyHandlerScope.include(dependency: Any) {
    api(dependency)
    shadowImpl(dependency)
}
```

4. Finish by adding "include("com.github.lolhol:Pathfinder3D:1.03")" to "dependencies"

```
dependencies {
    include("com.github.lolhol:Pathfinder3D:1.03") // <- NOTE: "1.03" is jst the latest version
}
```

---

<a>How to use</a>

To use the pathfinder you will need (in the current version) an implementation of the "IWorldProvider" interface containing all of the implemented methods.

Example:

```Java
public class WorldProvider implements IWorldProvider {
    @Override
    public BlockState getBlockState(int[] ints) {
        Block block = mc.theWorld.getBlockState(new BlockPos(ints[0], ints[1],
            ints[2])).getBlock();
        return block == Blocks.stone ? BlockState.OBSTRUCTED :
            BlockState.UNOBSTRUCTED;
    }

    @Override
    public boolean isTranslationValid(Node node, Node parent, IWorldProvider world, NodePickStyle pickStyle) {
        return isWalk(node, parent) || isJump(node, parent) || isFall(node, parent);
    }

    @Override
    public double addToTotalCost(Node node, IWorldProvider world) {
        int[] nodePosition = new int[]{node.x, node.y + 2, node.z};
        double totalCost = 0;

        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                for (int z = -1; z <= 1; z++) {
                    int[] newNode = new int[]{nodePosition[0] + x, nodePosition[1] + y, nodePosition[2] + z};
                    if (getBlockState(newNode) == BlockState.OBSTRUCTED) {
                        totalCost += 1.0;
                    }
                }
            }
        }

        return totalCost;
    }
}
```

---

**For more information on the variaous functions and their usage, look at the javadoc for the IOptionProvider (addToTotalCost, isTranslationValid) and the IStateProvider (getBlockState).**
